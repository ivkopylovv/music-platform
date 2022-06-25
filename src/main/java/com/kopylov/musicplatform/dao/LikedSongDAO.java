package com.kopylov.musicplatform.dao;

import com.kopylov.musicplatform.entity.LikedSong;
import com.kopylov.musicplatform.entity.compositekey.LikedSongId;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikedSongDAO extends JpaRepository<LikedSong, LikedSongId> {
    Optional<LikedSong> findByIdSongIdAndIdUserUsername(Long songId, String username);

    List<LikedSong> findByIdUserUsername(String username);

    List<LikedSong> findByIdUserUsername(String username, Sort sort);

    List<LikedSong> findByIdUserUsernameAndIdSongTitleContainingOrIdSongAlbumTitleContainingOrIdSongArtistsNameContaining(
            String username, String songTitle, String albumTitle, String artistName);

    void deleteByIdSongId(Long songId);

    void deleteByIdSongAlbumId(Long albumId);

    void deleteByIdSongArtistsId(Long artistId);
}
