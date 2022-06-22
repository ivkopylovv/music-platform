package com.kopylov.musicplatform.dao;

import com.kopylov.musicplatform.entity.SongAudio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SongAudioDAO extends JpaRepository<SongAudio, Long> {
    void deleteBySongId(Long id);
    Optional<SongAudio> findBySongId(Long id);
}