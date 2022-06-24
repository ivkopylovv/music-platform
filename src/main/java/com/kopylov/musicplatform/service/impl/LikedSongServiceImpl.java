package com.kopylov.musicplatform.service.impl;

import com.kopylov.musicplatform.dao.LikedSongDAO;
import com.kopylov.musicplatform.dao.SongDAO;
import com.kopylov.musicplatform.dao.UserDAO;
import com.kopylov.musicplatform.dto.request.AddSongToLikedDTO;
import com.kopylov.musicplatform.dto.response.LikedSongDTO;
import com.kopylov.musicplatform.entity.LikedSong;
import com.kopylov.musicplatform.entity.Song;
import com.kopylov.musicplatform.entity.User;
import com.kopylov.musicplatform.entity.compositekey.LikedSongId;
import com.kopylov.musicplatform.exception.NotFoundException;
import com.kopylov.musicplatform.mapper.response.ResponseLikedSongMapper;
import com.kopylov.musicplatform.service.LikedSongService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.kopylov.musicplatform.constants.ErrorMessage.*;

@Service
@Transactional
@RequiredArgsConstructor
public class LikedSongServiceImpl implements LikedSongService {
    private final LikedSongDAO likedSongDAO;
    private final SongDAO songDAO;
    private final UserDAO userDAO;

    @Override
    public LikedSongDTO getLikedSong(Long songId, String username) {
        LikedSong likedSong = likedSongDAO.findByIdSongIdAndIdUserUsername(songId, username)
                .orElseThrow(() -> new NotFoundException(LIKED_SONG_NOT_FOUND));
        return ResponseLikedSongMapper.likedSongToDTO(likedSong);
    }

    @Override
    public List<LikedSongDTO> getLikedSongs(String username) {
        List<LikedSong> likedSongs = likedSongDAO.findByIdUserUsername(username);

        if (likedSongs.isEmpty()) {
            throw new NotFoundException(LIKED_SONG_NOT_FOUND);
        }

        return ResponseLikedSongMapper.likedSongListToDTOList(likedSongs);
    }

    @Override
    public List<LikedSongDTO> getSortedLikedSongs(boolean asc, String attribute, String username) {
        List<LikedSong> likedSongs = likedSongDAO
                .findByIdUserUsername(
                        username,
                        asc ? Sort.by(attribute).ascending() : Sort.by(attribute).descending()
                );

        if (likedSongs.isEmpty()) {
            throw new NotFoundException(LIKED_SONG_NOT_FOUND);
        }

        return ResponseLikedSongMapper.likedSongListToDTOList(likedSongs);
    }

    @Override
    public void addSongToLiked(AddSongToLikedDTO dto) {
        Song song = songDAO.findById(dto.getSongId())
                .orElseThrow(() -> new NotFoundException(SONG_NOT_FOUND));
        User user = userDAO.findByUsername(dto.getUsername())
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));

        LikedSongId id = new LikedSongId(song, user);
        LikedSong likedSong = new LikedSong(id, dto.getAddedDate());

        likedSongDAO.save(likedSong);
    }

    @Override
    public void deleteSongFromLiked(Long songId, String username) {
        Song song = songDAO.findById(songId)
                .orElseThrow(() -> new NotFoundException(SONG_NOT_FOUND));
        User user = userDAO.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));

        LikedSongId id = new LikedSongId(song, user);

        likedSongDAO.deleteById(id);
    }

    @Override
    public List<LikedSongDTO> findLikedSongs(String username, String songTitle, String albumTitle, String artistName) {
        List<LikedSong> songs = likedSongDAO
                .findByIdUserUsernameAndIdSongTitleContainingOrIdSongAlbumTitleContainingOrIdSongArtistsNameContaining(
                        username, songTitle, albumTitle, artistName);

        return ResponseLikedSongMapper.likedSongListToDTOList(songs);
    }

    @Override
    public Long getLikedSongsCount() {
        return likedSongDAO.count();
    }
}