package com.kopylov.musicplatform.dao;

import com.kopylov.musicplatform.entity.Artist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtistDAO extends JpaRepository<Artist, Long> {
}
