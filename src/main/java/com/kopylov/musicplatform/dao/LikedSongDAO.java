package com.kopylov.musicplatform.dao;

import com.kopylov.musicplatform.entity.LikedSong;
import com.kopylov.musicplatform.entity.compositekey.LikedSongId;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikedSongDAO extends JpaRepository<LikedSong, LikedSongId> {
    Optional<LikedSong> findByIdSongIdAndIdUserUsername(Long songId, String username);

    List<LikedSong> findByIdUserUsername(String username);

    List<LikedSong> findByIdUserUsername(String username, Sort sort);

    List<LikedSong> findByIdSongTitleContainingOrIdSongAlbumTitleContainingOrIdSongArtistsNameContaining
            (String songTitle, String albumTitle, String artistName);

    void deleteByIdSongId(Long songId);

    void deleteByIdSongAlbumId(Long albumId);

    void deleteByIdSongArtistsId(Long artistId);
}
