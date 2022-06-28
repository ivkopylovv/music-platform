package com.kopylov.musicplatform.entity.compositekey;


import com.kopylov.musicplatform.entity.Song;
import com.kopylov.musicplatform.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

import static javax.persistence.FetchType.EAGER;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LikedSongId implements Serializable {
    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "song_id", nullable = false)
    private Song song;

    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LikedSongId)) return false;
        LikedSongId id = (LikedSongId) o;
        return song != null && song.equals(id.song) &&
                user != null && user.equals(id.user);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
