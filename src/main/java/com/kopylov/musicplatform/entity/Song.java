package com.kopylov.musicplatform.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import java.sql.Date;
import java.sql.Time;
import java.util.HashSet;
import java.util.Set;

import static com.kopylov.musicplatform.constants.DateFormat.RELEASE_MUSIC_FORMAT;
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
    private String title;

    @NonNull
    private String audioName;

    @NonNull
    private Time duration;

    @NonNull
    @DateTimeFormat(pattern = RELEASE_MUSIC_FORMAT)
    private Date releaseDate;

    @ManyToOne(fetch = EAGER, cascade = ALL)
    @JoinColumn(name = "album_id", nullable = false)
    private Album album;

    @ManyToMany(fetch = EAGER, cascade = ALL)
    private Set<Artist> artists = new HashSet<>();
}
