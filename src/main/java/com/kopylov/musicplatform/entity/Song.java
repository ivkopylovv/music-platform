package com.kopylov.musicplatform.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

import java.sql.Time;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.AUTO;

@Entity
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class Song {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    @NonNull
    private String songTitle;

    @NonNull
    private String audio;

    private Time songDuration;

    @ManyToOne(fetch = EAGER, cascade = ALL)
    @JoinColumn(name = "album_id", nullable = false)
    private Album album;
}
