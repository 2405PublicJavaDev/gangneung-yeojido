package com.gntour.gangneungyeojido.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;


@Slf4j
public class FileUtil {
    @Value("${GANGNEUNG_UPLOAD_FOLDER_PATH}")
    private String realFolderPath;
    /**
     * 파일을 업로드하고 업로드한 파일 정보를 DB 에 저장합니다.
     * @param uploadFolder: upload 할 폴더 위치
     * @param files: upload 할 파일 list
     * @param converter: multipart file 을 db 에 저장할 때 필요한 converter
     * @return int: db에 저장된 row 개수
     * @throws IOException: 파일 저장에 오류가 발생할 때
     */
    public static int uploadFiles(UploadFolder uploadFolder, List<MultipartFile> files, MultipartFileToSqlConverter converter) throws IOException {
        int result = 0;
        for (MultipartFile file : files) {
            if(file.getOriginalFilename() != null && !file.getOriginalFilename().isBlank()) {
                String fileName = file.getOriginalFilename();
                String fileRename = fileRename(fileName);
                File folder = new File(FileConfig.realFolderPath + uploadFolder.toString());
                if(!folder.exists()) {
                    folder.mkdir();
                }
                String filePath = FileConfig.realFolderPath + fileRename;
                file.transferTo(new File(folder, fileName));
                result += converter.fromMultipartFile(fileName, fileRename, filePath);
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
