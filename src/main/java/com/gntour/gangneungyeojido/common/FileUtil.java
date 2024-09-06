package com.gntour.gangneungyeojido.common;

import com.gntour.gangneungyeojido.domain.mytravel.mapper.TravelDiaryMapper;
import com.gntour.gangneungyeojido.domain.mytravel.vo.TravelDiaryFile;
import com.gntour.gangneungyeojido.domain.qna.mapper.QnAMapper;
import com.gntour.gangneungyeojido.domain.qna.vo.QnA;
import com.gntour.gangneungyeojido.domain.qna.vo.QnAFile;
import com.gntour.gangneungyeojido.domain.review.mapper.ReviewMapper;
import com.gntour.gangneungyeojido.domain.review.vo.ReviewFile;
import com.gntour.gangneungyeojido.sample.domain.SampleFile;
import com.gntour.gangneungyeojido.sample.repository.SampleMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static com.gntour.gangneungyeojido.common.FileConfig.FOLDER_PATH;


@Component
@Slf4j
@RequiredArgsConstructor
public class FileUtil {
    private final SampleMapper sampleMapper;
    private final QnAMapper qnaMapper;
    private final TravelDiaryMapper diaryMapper;
    private final ReviewMapper reviewMapper;
    /**
     * 파일을 업로드하고 업로드한 파일 정보를 DB 에 저장합니다.
     * @param uploadCategory: upload 할 파일 종류
     * @param files: upload 할 파일 list
     * @param fkNo: foreign key id
     * @return int: db에 저장된 row 개수
     * @throws IOException: 파일 저장에 오류가 발생할 때
     */
    public int uploadFiles(UploadCategory uploadCategory, List<MultipartFile> files, Long fkNo) throws IOException {
        int result = 0;
        long order = 1;
        for (MultipartFile file : files) {
            if(file.getOriginalFilename() != null && !file.getOriginalFilename().isBlank()) {
                String fileName = file.getOriginalFilename();
                String fileRename = fileRename(fileName);
                File folder = new File(FileConfig.realFolderPath + uploadCategory.toString());
                if(!folder.exists()) {
                    folder.mkdir();
                }
                String filePath = FileConfig.realFolderPath + uploadCategory + "/" + fileRename;
                file.transferTo(new File(folder, fileRename));
                result += insertFileData(uploadCategory, fkNo,fileName, fileRename, filePath, order);
                order++;
            }
        }
        return result;
    }

    /**
     * 파일을 삭제하고 DB 에도 파일 관련된 정보를 삭제할 때 쓰입니다.
     * @param uploadCategory: 삭제할 파일 종류
     * @param fkNo: 삭제할 foreign key id
     * @return int: db에 삭제된 row 개수
     * @throws IOException: 파일 삭제에 오류가 발생할 때
     */
    public int deleteFiles(UploadCategory uploadCategory, Long fkNo) throws IOException {
        List<?> files = selectFileData(uploadCategory, fkNo);
        if(deleteFilesReal(uploadCategory, files)) {
            throw new IOException("file not delete completely");
        }
        return deleteFilesData(uploadCategory, fkNo);
    }

    private int insertFileData(UploadCategory uploadCategory, Long fkNo, String fileName, String fileRename, String filePath, Long order) {
        int result = 0;
        switch(uploadCategory) {
            case SAMPLE -> {
                SampleFile sampleFile = new SampleFile();
                sampleFile.setFileName(fileName);
                sampleFile.setFileRename(fileRename);
                sampleFile.setFilePath(filePath);
                sampleFile.setSampleNo(fkNo);
                result += sampleMapper.insertSampleFile(sampleFile);
            }
            case QNA -> {
                QnAFile qnaFile = new QnAFile();
                qnaFile.setFileName(fileName);
                qnaFile.setFileRename(fileRename);
                qnaFile.setFilePath(filePath);
                qnaFile.setQnaNo(fkNo);
                qnaFile.setWebPath(FOLDER_PATH + uploadCategory + "/" + fileRename);
                qnaFile.setFileOrder(order);
                result += qnaMapper.insertQnAFile(qnaFile);
            }
            case DIARY -> {
                TravelDiaryFile diaryFile = new TravelDiaryFile();
                diaryFile.setFileName(fileName);
                diaryFile.setFileRename(fileRename);
                diaryFile.setFilePath(filePath);
                diaryFile.setDiaryNo(fkNo);
                diaryFile.setWebPath(FOLDER_PATH + uploadCategory + "/"  + fileRename);
                diaryFile.setFileOrder(order);
                result += diaryMapper.insertDiaryFile(diaryFile);
            }
            case REVIEW -> {
                ReviewFile reviewFile = new ReviewFile();
                reviewFile.setFileName(fileName);
                reviewFile.setFileRename(fileRename);
                reviewFile.setFilePath(filePath);
                reviewFile.setReviewNo(fkNo);
                reviewFile.setWebPath(FOLDER_PATH + uploadCategory + "/"  + fileRename);
                reviewFile.setFileOrder(order);
                result += reviewMapper.insertReviewFile(reviewFile);
            }
        }
        return result;
    }

    private List<?> selectFileData(UploadCategory uploadCategory, Long fkNo) {
        switch (uploadCategory) {
            case SAMPLE -> {
                return sampleMapper.selectAllSampleFiles(fkNo);
            }
            case QNA -> {
                return qnaMapper.selectAllQnAFileByQnANo(fkNo);
            }
            case DIARY -> {
                return diaryMapper.selectAllDiariesFileByDiaryNo(fkNo);
            }
            case REVIEW -> {
                return reviewMapper.selectAllReviewsFileByReviewNo(fkNo);
            }
        }
        return Collections.emptyList();
    }

    private boolean deleteFilesReal(UploadCategory uploadCategory, List<?> files) {
        switch (uploadCategory) {
            case SAMPLE -> {
                boolean success = true;
                for(Object file : files) {
                    SampleFile sampleFile = (SampleFile)file;
                    success &= new File(sampleFile.getFilePath()).delete();
                }
                return success;
            }
            case QNA -> {
                boolean success = true;
                for(Object file : files) {
                    QnAFile qnaFile = (QnAFile)file;
                    success &= new File(qnaFile.getFilePath()).delete();
                }
                return success;
            }
            case DIARY -> {
                boolean success = true;
                for(Object file : files) {
                    TravelDiaryFile diaryFile = (TravelDiaryFile)file;
                    success &= new File(diaryFile.getFilePath()).delete();
                }
                return success;
            }
            case REVIEW -> {
                boolean success = true;
                for(Object file : files) {
                    ReviewFile reviewFile = (ReviewFile)file;
                    success &= new File(reviewFile.getFilePath()).delete();
                }
                return success;
            }
        }
        return false;
    }

    private int deleteFilesData(UploadCategory uploadCategory, Long fkNo) {
        int result = 0;
        switch (uploadCategory) {
            case SAMPLE ->  {
                result += sampleMapper.deleteSampleFile(fkNo);
            }
            case QNA -> {
                result += qnaMapper.deleteQnAFile(fkNo);
            }
            case DIARY -> {
                result += diaryMapper.deleteDiaryFile(fkNo);
            }
            case REVIEW -> {
                result += reviewMapper.deleteReviewFile(fkNo);
            }
        }
        return result;
    }

    private static String fileRename(String originalFileName) {
        String ext = originalFileName.substring(originalFileName.lastIndexOf("."));
        String fileRename = UUID.randomUUID() + ext;
        return fileRename;
    }
}
