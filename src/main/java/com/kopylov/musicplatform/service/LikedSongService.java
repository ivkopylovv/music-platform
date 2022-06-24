package com.kopylov.musicplatform.service;

import com.kopylov.musicplatform.dto.request.AddSongToLikedDTO;
import com.kopylov.musicplatform.dto.response.LikedSongDTO;

import java.util.List;

public interface LikedSongService {
    LikedSongDTO getLikedSong(Long songId, String username);

    List<LikedSongDTO> getLikedSongs(String username);

    List<LikedSongDTO> getSortedLikedSongs(boolean asc, String attribute, String username);

    void addSongToLiked(AddSongToLikedDTO dto);

    void deleteSongFromLiked(Long songId, String username);

    List<LikedSongDTO> findLikedSongs(String username, String songTitle, String albumTitle, String artistName);

    Long getLikedSongsCount();
}
