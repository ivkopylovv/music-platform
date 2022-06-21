package com.kopylov.musicplatform.dao;

import com.kopylov.musicplatform.entity.Album;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlbumDAO extends JpaRepository<Album, Long> {
    List<Album> findAlbumsByTitleContaining(String title);
}
