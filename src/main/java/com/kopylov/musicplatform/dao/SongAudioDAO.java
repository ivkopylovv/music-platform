package com.kopylov.musicplatform.dao;

import com.kopylov.musicplatform.entity.SongAudio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SongAudioDAO extends JpaRepository<SongAudio, Long> {
    Optional<SongAudio> findBySongId(Long id);

    void deleteBySongId(Long id);

    void deleteBySongAlbumId(Long albumId);

    void deleteBySongArtistsId(Long artistId);
}
