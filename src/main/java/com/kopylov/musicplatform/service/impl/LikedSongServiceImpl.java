package com.kopylov.musicplatform.service.impl;

import com.kopylov.musicplatform.dao.AuthDAO;
import com.kopylov.musicplatform.dao.LikedSongDAO;
import com.kopylov.musicplatform.dao.SongDAO;
import com.kopylov.musicplatform.dto.request.AddSongToLikedDTO;
import com.kopylov.musicplatform.dto.response.LikedSongDTO;
import com.kopylov.musicplatform.entity.LikedSong;
import com.kopylov.musicplatform.entity.Song;
import com.kopylov.musicplatform.entity.User;
import com.kopylov.musicplatform.entity.compositekey.LikedSongId;
import com.kopylov.musicplatform.exception.ResourceNotFoundException;
import com.kopylov.musicplatform.helper.SortHelper;
import com.kopylov.musicplatform.mapper.response.ResponseLikedSongMapper;
import com.kopylov.musicplatform.service.LikedSongService;
import lombok.RequiredArgsConstructor;
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
    private final AuthDAO authDAO;

    @Override
    public LikedSongDTO getLikedSong(Long songId, String username) {
        LikedSong likedSong = likedSongDAO.findByIdSongIdAndIdUserUsername(songId, username)
                .orElseThrow(() -> new ResourceNotFoundException(LIKED_SONG_NOT_FOUND));
        return ResponseLikedSongMapper.likedSongToDTO(likedSong);
    }

    @Override
    public List<LikedSongDTO> getLikedSongs(String username) {
        List<LikedSong> likedSongs = likedSongDAO.findByIdUserUsername(username);

        if (likedSongs.isEmpty()) {
            throw new ResourceNotFoundException(LIKED_SONG_NOT_FOUND);
        }

        return ResponseLikedSongMapper.likedSongListToDTOList(likedSongs);
    }

    @Override
    public List<LikedSongDTO> getSortedLikedSongs(boolean asc, String attribute, String username) {
        List<LikedSong> likedSongs = likedSongDAO
                .findByIdUserUsername(
                        username,
                        SortHelper.getSortScript(asc, attribute)
                );

        if (likedSongs.isEmpty()) {
            throw new ResourceNotFoundException(LIKED_SONG_NOT_FOUND);
        }

        return ResponseLikedSongMapper.likedSongListToDTOList(likedSongs);
    }

    @Override
    public void addSongToLiked(AddSongToLikedDTO dto) {
        Song song = songDAO.findById(dto.getSongId())
                .orElseThrow(() -> new ResourceNotFoundException(SONG_NOT_FOUND));
        User user = authDAO.findUserByUsername(dto.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND));

        LikedSongId id = new LikedSongId(song, user);
        LikedSong likedSong = new LikedSong(id, dto.getAddedDate());

        likedSongDAO.save(likedSong);
    }

    @Override
    public void deleteSongFromLiked(Long songId, String username) {
        Song song = songDAO.findById(songId)
                .orElseThrow(() -> new ResourceNotFoundException(SONG_NOT_FOUND));
        User user = authDAO.findUserByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND));

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
