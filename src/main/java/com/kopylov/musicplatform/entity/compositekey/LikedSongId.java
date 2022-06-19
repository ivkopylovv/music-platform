package com.kopylov.musicplatform.entity.compositekey;


import com.kopylov.musicplatform.entity.Song;
import com.kopylov.musicplatform.entity.User;
import lombok.*;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

import static javax.persistence.FetchType.EAGER;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class LikedSongId implements Serializable {
    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "song_id", nullable = false)
    private Song song;

    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
