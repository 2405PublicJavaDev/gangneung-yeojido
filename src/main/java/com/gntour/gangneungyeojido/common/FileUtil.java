package com.gntour.gangneungyeojido.common;

import com.gntour.gangneungyeojido.domain.qna.vo.QnA;
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


@Component
@Slf4j
@RequiredArgsConstructor
public class FileUtil {
    private final SampleMapper sampleMapper;
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
                result += insertFileData(uploadCategory, fkNo,fileName, fileRename, filePath);
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

    private int insertFileData(UploadCategory uploadCategory, Long fkNo, String fileName, String fileRename, String filePath) {
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
            case QNA -> {}
            case DIARY -> {}
            case REVIEW -> {}
        }
        return result;
    }

    private List<?> selectFileData(UploadCategory uploadCategory, Long fkNo) {
        switch (uploadCategory) {
            case SAMPLE -> {
                return sampleMapper.selectAllSampleFiles(fkNo);
            }
            case QNA -> {}
            case DIARY -> {}
            case REVIEW -> {}
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
            case QNA -> {}
            case DIARY -> {}
            case REVIEW -> {}
        }
        return false;
    }

    private int deleteFilesData(UploadCategory uploadCategory, Long fkNo) {
        int result = 0;
        switch (uploadCategory) {
            case SAMPLE ->  {
                result += sampleMapper.deleteSampleFile(fkNo);
            }
            case QNA -> {}
            case DIARY -> {}
            case REVIEW -> {}
        }
        return result;
    }

    private static String fileRename(String originalFileName) {
        String ext = originalFileName.substring(originalFileName.lastIndexOf("."));
        String fileRename = UUID.randomUUID() + ext;
        return fileRename;
    }
}
