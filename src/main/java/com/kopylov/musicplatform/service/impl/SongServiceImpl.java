package com.kopylov.musicplatform.service.impl;

import com.kopylov.musicplatform.dao.*;
import com.kopylov.musicplatform.dto.request.SaveUpdateSongDTO;
import com.kopylov.musicplatform.entity.Album;
import com.kopylov.musicplatform.entity.Artist;
import com.kopylov.musicplatform.entity.Song;
import com.kopylov.musicplatform.entity.SongAudio;
import com.kopylov.musicplatform.exception.NotFoundException;
import com.kopylov.musicplatform.helper.FileHelper;
import com.kopylov.musicplatform.helper.SortHelper;
import com.kopylov.musicplatform.mapper.request.RequestSongMapper;
import com.kopylov.musicplatform.service.SongService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static com.kopylov.musicplatform.constants.ErrorMessage.*;
import static com.kopylov.musicplatform.constants.FilePath.DB_AUDIO_PATH;
import static com.kopylov.musicplatform.constants.FilePath.STATIC_AUDIO_PATH;

@Service
@Transactional
@RequiredArgsConstructor
public class SongServiceImpl implements SongService {
    private final SongDAO songDAO;
    private final AlbumDAO albumDAO;
    private final ArtistDAO artistDAO;
    private final SongAudioDAO songAudioDAO;
    private final LikedSongDAO likedSongDAO;
    private final PlaylistDAO playlistDAO;

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
        return songDAO.findAll(SortHelper.getSortScript(asc, attribute));
    }

    @Override
    public void saveSong(SaveUpdateSongDTO songDTO) throws IOException {
        List<Artist> artists = artistDAO.findAllById(songDTO.getArtistIds());

        if (artists.isEmpty()) {
            throw new NotFoundException(ARTISTS_NOT_FOUND);
        }

        Album album = albumDAO.findById(songDTO.getAlbumId())
                .orElseThrow(() -> new NotFoundException(ALBUM_NOT_FOUND));

        MultipartFile audio = songDTO.getAudio();
        FileHelper.saveUploadedFile(audio, STATIC_AUDIO_PATH);
        String audioName = DB_AUDIO_PATH + audio.getOriginalFilename();

        Song savedSong = songDAO.save(RequestSongMapper.saveSongDTOToEntity(songDTO, album, artists));
        songAudioDAO.save(new SongAudio(null, savedSong, audioName));
    }

    @Override
    public void deleteSong(Long id) {
        playlistDAO.deleteSongBySongsId(id);
        likedSongDAO.deleteByIdSongId(id);
        songAudioDAO.deleteBySongId(id);
        songDAO.deleteById(id);
    }

    @Override
    public void updateSong(Long id, SaveUpdateSongDTO songDTO) throws IOException {
        SongAudio foundAudio = songAudioDAO.findBySongId(id)
                .orElseThrow(() -> new NotFoundException(AUDIO_NOT_FOUND));

        FileHelper.deleteFile(foundAudio.getAudioName());
        MultipartFile audio = songDTO.getAudio();
        FileHelper.saveUploadedFile(audio, STATIC_AUDIO_PATH);
        String audioName = DB_AUDIO_PATH + audio.getOriginalFilename();
        foundAudio.setAudioName(audioName);

        songAudioDAO.save(foundAudio);

        Album album = albumDAO.findById(songDTO.getAlbumId())
                .orElseThrow(() -> new NotFoundException(ALBUM_NOT_FOUND));
        List<Artist> artists = artistDAO.findAllById(songDTO.getArtistIds());

        if (artists.isEmpty()) {
            throw new NotFoundException(ARTISTS_NOT_FOUND);
        }

        Song updatedSong = RequestSongMapper.saveSongDTOToEntity(songDTO, album, artists);
        updatedSong.setId(id);

        songDAO.save(updatedSong);
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
