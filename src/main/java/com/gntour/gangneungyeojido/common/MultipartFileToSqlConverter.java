package com.gntour.gangneungyeojido.common;

@FunctionalInterface
public interface MultipartFileToSqlConverter {
    int fromMultipartFile(String fileName, String fileRename, String filePath);
}
