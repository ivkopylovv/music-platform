package com.kopylov.musicplatform.service.impl;

import com.kopylov.musicplatform.dao.*;
import com.kopylov.musicplatform.dto.request.SaveUpdateAlbumDTO;
import com.kopylov.musicplatform.dto.response.AlbumDTO;
import com.kopylov.musicplatform.entity.Album;
import com.kopylov.musicplatform.entity.Song;
import com.kopylov.musicplatform.exception.ResourceNotFoundException;
import com.kopylov.musicplatform.helper.FileHelper;
import com.kopylov.musicplatform.helper.SortHelper;
import com.kopylov.musicplatform.mapper.request.RequestAlbumMapper;
import com.kopylov.musicplatform.mapper.response.ResponseAlbumMapper;
import com.kopylov.musicplatform.service.AlbumService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static com.kopylov.musicplatform.constants.ErrorMessage.ALBUM_NOT_FOUND;
import static com.kopylov.musicplatform.constants.FilePath.DB_IMAGE_PATH;
import static com.kopylov.musicplatform.constants.FilePath.STATIC_IMAGE_PATH;

@Service
@Transactional
@RequiredArgsConstructor
public class AlbumServiceImpl implements AlbumService {
    private final SongDAO songDAO;
    private final AlbumDAO albumDAO;
    private final LikedSongDAO likedSongDAO;
    private final SongAudioDAO songAudioDAO;
    private final PlaylistDAO playlistDAO;

    @Override
    public Album getAlbum(Long id) {
        return albumDAO.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ALBUM_NOT_FOUND));
    }

    @Override
    public AlbumDTO getDetailedAlbum(Long id) {
        List<Song> songs = songDAO.findSongsByAlbumId(id);

        if (songs.isEmpty()) {
            throw new ResourceNotFoundException(ALBUM_NOT_FOUND);
        }

        return ResponseAlbumMapper.songListToAlbumDTO(songs);
    }

    @Override
    public List<Album> getAlbums() {
        return albumDAO.findAll();
    }

    @Override
    public List<Album> getSortedAlbums(boolean asc, String attribute) {
        return albumDAO.findAll(SortHelper.getSortScript(asc, attribute));
    }

    @Override
    public void saveAlbum(SaveUpdateAlbumDTO saveAlbumDTO) throws IOException {
        MultipartFile file = saveAlbumDTO.getImage();
        FileHelper.saveUploadedFile(file, STATIC_IMAGE_PATH);
        String imageName = DB_IMAGE_PATH + file.getOriginalFilename();
        albumDAO.save(RequestAlbumMapper.saveAlbumDTOToEntity(saveAlbumDTO, imageName));
    }

    @Override
    public void deleteAlbum(Long id) {
        playlistDAO.deleteSongBySongsAlbumId(id);
        likedSongDAO.deleteByIdSongAlbumId(id);
        songAudioDAO.deleteBySongAlbumId(id);
        songDAO.deleteSongByAlbumId(id);
        albumDAO.deleteById(id);
    }

    @Override
    public void updateAlbum(Long id, SaveUpdateAlbumDTO dto) throws IOException {
        Album foundAlbum = getAlbum(id);
        FileHelper.deleteFile(foundAlbum.getImageName(), STATIC_IMAGE_PATH);

        MultipartFile file = dto.getImage();
        String imageName = DB_IMAGE_PATH + file.getOriginalFilename();

        Album updatedAlbum = RequestAlbumMapper.saveAlbumDTOToEntity(dto, imageName);
        updatedAlbum.setId(id);

        albumDAO.save(updatedAlbum);
    }

    @Override
    public Long getAlbumsCount() {
        return albumDAO.count();
    }

    @Override
    public List<Album> findAlbumsByTitle(String title) {
        return albumDAO.findAlbumsByTitleContaining(title);
    }

}
