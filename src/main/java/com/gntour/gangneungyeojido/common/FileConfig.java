package com.gntour.gangneungyeojido.common;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class FileConfig implements WebMvcConfigurer {
    public static final String FOLDER_PATH = "/sample/";
    public static final String REAL_PATH = "C:/sample/";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String webPath = FOLDER_PATH + "**";
        String realPath = "file:" + REAL_PATH;
        registry.addResourceHandler(webPath)
                .addResourceLocations(realPath);
    }
}
