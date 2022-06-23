package com.kopylov.musicplatform.dao;

import com.kopylov.musicplatform.entity.Artist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArtistDAO extends JpaRepository<Artist, Long> {
    List<Artist> findArtistsByNameContaining(String name);
}
