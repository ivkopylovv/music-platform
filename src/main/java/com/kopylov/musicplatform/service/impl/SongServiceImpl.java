package com.kopylov.musicplatform.service.impl;

import com.kopylov.musicplatform.dao.AlbumDAO;
import com.kopylov.musicplatform.dao.ArtistDAO;
import com.kopylov.musicplatform.dao.SongAudioDAO;
import com.kopylov.musicplatform.dao.SongDAO;
import com.kopylov.musicplatform.dto.response.SaveSongDTO;
import com.kopylov.musicplatform.entity.Album;
import com.kopylov.musicplatform.entity.Artist;
import com.kopylov.musicplatform.entity.Song;
import com.kopylov.musicplatform.entity.SongAudio;
import com.kopylov.musicplatform.exception.NotFoundException;
import com.kopylov.musicplatform.helper.FileHelper;
import com.kopylov.musicplatform.mapper.request.SongRequestMapper;
import com.kopylov.musicplatform.service.SongService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static com.kopylov.musicplatform.constants.ErrorMessage.*;
import static com.kopylov.musicplatform.constants.FilePath.*;

@Service
@Transactional
@RequiredArgsConstructor
public class SongServiceImpl implements SongService {
    private final SongDAO songDAO;
    private final AlbumDAO albumDAO;
    private final ArtistDAO artistDAO;
    private final SongAudioDAO songAudioDAO;

    @Override
    public Song getSong(Long id) {
        return songDAO.findById(id)
                .orElseThrow(() -> new NotFoundException(SONG_NOT_FOUND));
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
    public void saveSong(SaveSongDTO songDTO) throws IOException {
        List<Artist> artists = artistDAO.findAllById(songDTO.getArtistIds());

        if (artists.isEmpty()) {
            throw new NotFoundException(ARTISTS_NOT_FOUND);
        }

        Album album = albumDAO.findById(songDTO.getAlbumId())
                .orElseThrow(() -> new NotFoundException(ALBUM_NOT_FOUND));

        MultipartFile audio = songDTO.getAudio();
        FileHelper.saveUploadedFile(audio, STATIC_AUDIO_PATH);
        String audioName = DB_AUDIO_PATH + audio.getOriginalFilename();

        Song savedSong = songDAO.save(SongRequestMapper.saveSongDTOToEntity(songDTO, album, artists));
        songAudioDAO.save(new SongAudio(null, savedSong, audioName));
    }

    @Override
    public void deleteSong(Long id) {
        songAudioDAO.deleteBySongId(id);
        songDAO.deleteById(id);
    }

    @Override
    public void updateSong(Long id, Song song) {
        Song foundSong = songDAO.findById(id)
                .orElseThrow(() -> new NotFoundException(SONG_NOT_FOUND));
        song.setId(foundSong.getId());
        songDAO.save(song);
    }

    @Override
    public Long getSongsCount() {
        return songDAO.count();
    }

    @Override
    public List<Song> findSongsByTitle(String title) {
        return songDAO.findSongsByTitleContaining(title);
    }

    @Override
    public String getSongAudio(Long id) {
        SongAudio songAudio = songAudioDAO.findBySongId(id)
                .orElseThrow(() -> new NotFoundException(AUDIO_NOT_FOUND));
        return songAudio.getAudioName();
    }
}
