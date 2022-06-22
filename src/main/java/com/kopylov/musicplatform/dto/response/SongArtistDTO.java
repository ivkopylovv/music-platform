package com.kopylov.musicplatform.dto.response;

import com.kopylov.musicplatform.entity.Artist;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.sql.Time;
import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SongArtistDTO {
    private Long id;
    private String title;
    private Time duration;
    private Date releaseDate;
    private Set<Artist> artists;
}
