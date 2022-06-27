package com.kopylov.musicplatform.dto.request;

import com.kopylov.musicplatform.enums.AlbumGenre;
import com.kopylov.musicplatform.enums.AlbumType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;
import java.sql.Time;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SaveUpdateAlbumDTO {
    private String title;
    private AlbumType type;
    private AlbumGenre genre;
    private Time duration;
    private Date releaseDate;
    private MultipartFile image;
}
