package com.kopylov.musicplatform.service;

import com.kopylov.musicplatform.dto.request.SaveSongDTO;
import com.kopylov.musicplatform.entity.Song;

import java.io.IOException;
import java.util.List;

public interface SongService {
    Song getSong(Long id);

    List<Song> getSongs();

    List<Song> getSortedSongs(boolean asc, String attribute);

    void saveSong(SaveSongDTO songDTO) throws IOException;

    void deleteSong(Long id);

    void updateSong(Long id, Song song);

    Long getSongsCount();

    List<Song> findSongsByTitle(String title);

    String getSongAudio(Long id);
}
