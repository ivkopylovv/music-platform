package com.kopylov.musicplatform.entity.compositekey;


import com.kopylov.musicplatform.entity.Song;
import com.kopylov.musicplatform.entity.User;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class LikedSongId implements Serializable {
    @ManyToOne(fetch = EAGER, cascade = ALL)
    @JoinColumn(name = "song_id", nullable = false)
    private Song song;

    @ManyToOne(fetch = EAGER, cascade = ALL)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
