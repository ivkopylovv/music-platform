package com.kopylov.musicplatform.entity;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class SongAudio {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @OneToOne(fetch = EAGER)
    @JoinColumn(name = "song_id", nullable = false)
    private Song song;

    @NonNull
    private String audioName;
}
