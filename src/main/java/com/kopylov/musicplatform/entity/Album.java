package com.kopylov.musicplatform.entity;

import com.kopylov.musicplatform.enums.AlbumGenre;
import com.kopylov.musicplatform.enums.AlbumType;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Date;
import java.sql.Time;

import static com.kopylov.musicplatform.constants.DateFormat.RELEASE_MUSIC_FORMAT;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class Album {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @NonNull
    private String title;

    @Enumerated(STRING)
    private AlbumType type;

    @Enumerated(STRING)
    private AlbumGenre genre;

    private Time duration;
    private String imageName;

    @DateTimeFormat(pattern = RELEASE_MUSIC_FORMAT)
    private Date releaseDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Album)) return false;
        Album album = (Album) o;
        return id != null && id.equals(album.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
