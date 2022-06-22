package com.kopylov.musicplatform.config;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;

import javax.servlet.MultipartConfigElement;

@Configuration
public class MultipartFileConfiguration {
    private static final Long MAX_FILE_SIZE = 40000L;

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(DataSize.ofKilobytes(MAX_FILE_SIZE));
        factory.setMaxRequestSize(DataSize.ofKilobytes(MAX_FILE_SIZE));
        return factory.createMultipartConfig();
    }
}