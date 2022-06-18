package com.kopylov.musicplatform.service.impl;

import com.kopylov.musicplatform.constants.ErrorMessage;
import com.kopylov.musicplatform.dao.SongDAO;
import com.kopylov.musicplatform.entity.Song;
import com.kopylov.musicplatform.exception.SongNotFoundException;
import com.kopylov.musicplatform.service.SongService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SongServiceImpl implements SongService {
    private final SongDAO songDAO;

    @Override
    public Song getSong(Long id) {
        return songDAO.findById(id)
                .orElseThrow(() -> new SongNotFoundException(ErrorMessage.SONG_NOT_FOUND));
    }

    @Override
    public List<Song> getSongs() {
        return songDAO.findAll();
    }

    @Override
    public List<Song> getSortedSongs(boolean asc, String attribute) {
        return songDAO.findAll(asc ? Sort.by(attribute).ascending() : Sort.by(attribute).descending());
    }

    @Override
    public void saveSong(Song song) {
        songDAO.save(song);
    }

    @Override
    public void deleteSong(Long id) {
        songDAO.deleteById(id);
    }

    @Override
    public void updateSong(Long id, Song song) {
        Song foundSong = songDAO.findById(id)
                .orElseThrow(() -> new SongNotFoundException(ErrorMessage.SONG_NOT_FOUND));
        song.setId(foundSong.getId());
        songDAO.save(song);
    }

    @Override
    public Long getSongsCount() {
        return songDAO.count();
    }

    @Override
    public List<Song> findSongs(String title) {
        return songDAO.findSongsByTitleContaining(title);
    }
}
