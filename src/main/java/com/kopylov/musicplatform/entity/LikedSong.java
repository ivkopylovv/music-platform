package com.kopylov.musicplatform.entity;

import com.kopylov.musicplatform.entity.compositekey.LikedSongId;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.sql.Date;

import static com.kopylov.musicplatform.constants.DateFormat.ADDED_TO_LIKED_FORMAT;

@Entity
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class LikedSong {
    @EmbeddedId
    private LikedSongId id;

    @NonNull
    @DateTimeFormat(pattern = ADDED_TO_LIKED_FORMAT)
    private Date addedDate;

}
