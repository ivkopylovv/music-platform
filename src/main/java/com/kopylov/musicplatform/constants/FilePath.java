package com.kopylov.musicplatform.constants;

public class FilePath {
    public static final String COMMON_PATH = System.getProperty("user.dir");

    public static final String STATIC_IMAGE_PATH = COMMON_PATH + "/src/main/resources/static/image/";
    public static final String STATIC_AUDIO_PATH = COMMON_PATH + "/src/main/resources/static/audio/";

    public static final String DB_IMAGE_PATH = "//localhost:8081/image/";
    public static final String DB_AUDIO_PATH = "//localhost:8081/audio/";
}
