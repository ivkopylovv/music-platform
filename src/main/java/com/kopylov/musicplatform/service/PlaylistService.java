package com.kopylov.musicplatform.service;

import com.kopylov.musicplatform.dto.request.AddSongToPlaylist;
import com.kopylov.musicplatform.dto.request.CreatePlaylistDTO;
import com.kopylov.musicplatform.dto.response.PlaylistDTO;
import com.kopylov.musicplatform.entity.Playlist;
import com.kopylov.musicplatform.entity.Song;

import java.io.IOException;
import java.util.List;

public interface PlaylistService {
    PlaylistDTO getPlaylist(String username, String title);

    Playlist getDetailPlaylist(String username, String title);

    List<PlaylistDTO> getPlaylists(String username);

    List<PlaylistDTO> getSortedPlaylists(boolean asc, String attribute, String username);

    void createPlaylist(CreatePlaylistDTO dto) throws IOException;

    void addSongToPlaylist(AddSongToPlaylist dto);

    void deleteSongFromPlaylist(String username, String title, Long songId);

    List<Song> findPlaylistSongs(String username, String playlistTitle, String songTitle, String albumTitle, String artistName);

}
