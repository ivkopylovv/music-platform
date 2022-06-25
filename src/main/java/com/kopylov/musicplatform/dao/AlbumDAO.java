package com.kopylov.musicplatform.dao;

import com.kopylov.musicplatform.entity.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlbumDAO extends JpaRepository<Album, Long> {
    List<Album> findAlbumsByTitleContaining(String title);
}
