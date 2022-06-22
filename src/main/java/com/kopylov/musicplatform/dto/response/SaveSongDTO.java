package com.kopylov.musicplatform.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SaveSongDTO {
    private String title;
    private Time duration;
    private Date releaseDate;
    private Long albumId;
    private List<Long> artistIds = new ArrayList<>();
    private MultipartFile audio;
}
