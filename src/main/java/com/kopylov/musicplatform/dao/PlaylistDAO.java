package com.kopylov.musicplatform.dao;

import com.kopylov.musicplatform.entity.Playlist;
import com.kopylov.musicplatform.entity.Song;
import com.kopylov.musicplatform.entity.compositekey.PlaylistId;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlaylistDAO extends JpaRepository<Playlist, PlaylistId> {
    Optional<Playlist> findByIdUserUsernameAndIdTitle(String username, String title);

    List<Playlist> findByIdUserUsername(String username);

    List<Playlist> findByIdUserUsername(String username, Sort sort);

    List<Song> findPlaylistSongsByIdUserUsernameAndIdTitleAndSongsTitleOrSongsAlbumTitleOrSongsArtistsName(
            String username, String playlistTitle, String songTitle, String albumTitle, String artistName);

    void deleteSongBySongsId(Long songId);

    void deleteSongBySongsAlbumId(Long albumId);

    void deleteSongBySongsArtistsId(Long artistId);
}
