package com.kopylov.musicplatform.service;

import com.kopylov.musicplatform.entity.Song;

import java.util.List;

public interface SongService {
    Song getSong(Long id);

    List<Song> getSongs();

    List<Song> getSortedSongs(boolean asc, String sortParam);

    void saveSong(Song song);

    void deleteSong(Long id);

    void updateSong(Long id, Song song);

    Long getSongsCount();

    List<Song> findSongs(String title);
}
