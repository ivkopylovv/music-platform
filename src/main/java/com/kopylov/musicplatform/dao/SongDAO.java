package com.kopylov.musicplatform.dao;

import com.kopylov.musicplatform.entity.Song;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface SongDAO extends JpaRepository<Song, Long> {
    List<Song> findSongsByTitleContaining(String title);

    List<Song> findSongsByAlbumId(Long id);

    void deleteSongByAlbumId(Long id);
}
