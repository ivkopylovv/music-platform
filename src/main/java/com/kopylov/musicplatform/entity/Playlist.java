package com.kopylov.musicplatform.entity;

import com.kopylov.musicplatform.entity.compositekey.PlaylistId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.IDENTITY;


@Entity
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class Playlist {
    @EmbeddedId
    @GeneratedValue(strategy = IDENTITY)
    private PlaylistId id;

    private String imageName;
    private String description;

    @ManyToMany(fetch = EAGER)
    private Set<Song> songs = new HashSet<>();
}
