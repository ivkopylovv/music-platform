package com.kopylov.musicplatform.entity;

import com.kopylov.musicplatform.enums.AlbumGenre;
import com.kopylov.musicplatform.enums.AlbumType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import java.sql.Time;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.AUTO;

@Entity
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class Album {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    @NonNull
    private String albumTitle;

    @Enumerated(STRING)
    private AlbumType type;

    @Enumerated(STRING)
    private AlbumGenre genre;

    private Time albumDuration;
    private String albumImage;
    private Date addedDate;

    @ManyToMany(fetch = EAGER, cascade = ALL)
    private Set<Artist> artists = new HashSet<>();
}
