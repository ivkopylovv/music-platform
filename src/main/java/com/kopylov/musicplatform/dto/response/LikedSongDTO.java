package com.kopylov.musicplatform.dto.response;

import com.kopylov.musicplatform.entity.Song;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LikedSongDTO {
    private Song song;
    private Date addedDate;
}
