package com.kopylov.musicplatform.service.impl;

import com.kopylov.musicplatform.dao.AuthDAO;
import com.kopylov.musicplatform.dao.PlaylistDAO;
import com.kopylov.musicplatform.dao.SongDAO;
import com.kopylov.musicplatform.dto.request.AddSongToPlaylist;
import com.kopylov.musicplatform.dto.request.CreatePlaylistDTO;
import com.kopylov.musicplatform.dto.response.PlaylistDTO;
import com.kopylov.musicplatform.entity.Playlist;
import com.kopylov.musicplatform.entity.Song;
import com.kopylov.musicplatform.entity.User;
import com.kopylov.musicplatform.entity.compositekey.PlaylistId;
import com.kopylov.musicplatform.exception.NotFoundException;
import com.kopylov.musicplatform.helper.FileHelper;
import com.kopylov.musicplatform.helper.SortHelper;
import com.kopylov.musicplatform.mapper.response.ResponsePlaylistMapper;
import com.kopylov.musicplatform.service.PlaylistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static com.kopylov.musicplatform.constants.ErrorMessage.*;
import static com.kopylov.musicplatform.constants.FilePath.DB_IMAGE_PATH;
import static com.kopylov.musicplatform.constants.FilePath.STATIC_IMAGE_PATH;

@Service
@Transactional
@RequiredArgsConstructor
public class PlaylistServiceImpl implements PlaylistService {
    private final PlaylistDAO playlistDAO;
    private final SongDAO songDAO;
    private final AuthDAO authDAO;

    @Override
    public PlaylistDTO getPlaylist(String username, String title) {
        Playlist playlist = playlistDAO.findByIdUserUsernameAndIdTitle(username, title)
                .orElseThrow(() -> new NotFoundException(PLAYLIST_NOT_FOUND));
        return ResponsePlaylistMapper.entityToPlaylistDTO(playlist);
    }

    @Override
    public Playlist getDetailPlaylist(String username, String title) {
        return playlistDAO.findByIdUserUsernameAndIdTitle(username, title)
                .orElseThrow(() -> new NotFoundException(PLAYLIST_NOT_FOUND));
    }

    @Override
    public List<PlaylistDTO> getPlaylists(String username) {
        List<Playlist> playlists = playlistDAO.findByIdUserUsername(username);

        if (playlists.isEmpty()) {
            throw new NotFoundException(PLAYLIST_NOT_FOUND);
        }

        return ResponsePlaylistMapper.playlistsTOPlaylistDTOList(playlists);
    }

    @Override
    public List<PlaylistDTO> getSortedPlaylists(boolean asc, String attribute, String username) {
        List<Playlist> playlists = playlistDAO.findByIdUserUsername(
                username,
                SortHelper.getSortScript(asc, attribute)
        );

        return ResponsePlaylistMapper.playlistsTOPlaylistDTOList(playlists);
    }

    @Override
    public void createPlaylist(CreatePlaylistDTO dto) throws IOException {
        MultipartFile file = dto.getImage();
        FileHelper.saveUploadedFile(file, STATIC_IMAGE_PATH);
        String imageName = DB_IMAGE_PATH + file.getOriginalFilename();

        User user = authDAO.findUserByUsername(dto.getUsername())
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
        PlaylistId id = new PlaylistId(user, dto.getTitle());
        Playlist playlist = new Playlist(id, imageName, dto.getDescription(), null);

        playlistDAO.save(playlist);
    }

    @Override
    public void addSongToPlaylist(AddSongToPlaylist dto) {
        Playlist playlist = playlistDAO
                .findByIdUserUsernameAndIdTitle(dto.getUsername(), dto.getTitle())
                .orElseThrow(() -> new NotFoundException(PLAYLIST_NOT_FOUND));
        Song song = songDAO.findById(dto.getSongId())
                .orElseThrow(() -> new NotFoundException(SONG_NOT_FOUND));
        playlist.getSongs().add(song);
    }

    @Override
    public void deleteSongFromPlaylist(String username, String title, Long songId) {
        Playlist playlist = playlistDAO.findByIdUserUsernameAndIdTitle(username, title)
                .orElseThrow(() -> new NotFoundException(PLAYLIST_NOT_FOUND));
        Song song = songDAO.findById(songId)
                .orElseThrow(() -> new NotFoundException(SONG_NOT_FOUND));
        playlist.getSongs().remove(song);
    }

    @Override
    public List<Song> findPlaylistSongs(
            String username, String playlistTitle, String songTitle, String albumTitle, String artistName) {
        return playlistDAO.findPlaylistSongsByIdUserUsernameAndIdTitleAndSongsTitleOrSongsAlbumTitleOrSongsArtistsName(
                username, playlistTitle, songTitle, albumTitle, artistName);
    }
}
