package com.kopylov.musicplatform.entity;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.AUTO;

@Entity
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class SongAudio {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    @OneToOne(fetch = EAGER)
    @JoinColumn(name = "song_id", nullable = false)
    private Song song;

    @NonNull
    private String audioName;
}
