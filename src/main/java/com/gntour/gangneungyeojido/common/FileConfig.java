package com.gntour.gangneungyeojido.common;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Getter
public class FileConfig implements WebMvcConfigurer {
    public static final String FOLDER_PATH = "/sample/";
    public static String realFolderPath;

    @Value("${GANGNEUNG_UPLOAD_FOLDER_PATH}")
    public void setRealFolderPath(String realFolderPath) {
        FileConfig.realFolderPath = realFolderPath;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String webPath = FOLDER_PATH + "**";
        String realPath = "file:" + realFolderPath;
        registry.addResourceHandler(webPath)
                .addResourceLocations(realPath);
    }
}