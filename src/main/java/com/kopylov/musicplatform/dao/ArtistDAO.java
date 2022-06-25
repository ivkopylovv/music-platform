package com.kopylov.musicplatform.dao;

import com.kopylov.musicplatform.entity.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArtistDAO extends JpaRepository<Artist, Long> {
    List<Artist> findArtistsByNameContaining(String name);
}
