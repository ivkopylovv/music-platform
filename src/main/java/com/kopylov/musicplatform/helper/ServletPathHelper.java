package com.kopylov.musicplatform.helper;

import lombok.experimental.UtilityClass;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

@UtilityClass
public class ServletPathHelper {

    public String getServletPath(WebRequest request) {
        return ((ServletWebRequest) request).getRequest().getServletPath();
    }
}
