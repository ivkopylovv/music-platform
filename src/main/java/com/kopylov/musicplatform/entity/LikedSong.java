package com.kopylov.musicplatform.entity;

import com.kopylov.musicplatform.entity.compositekey.LikedSongId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.util.Date;

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
    private Date addedDate;

}
