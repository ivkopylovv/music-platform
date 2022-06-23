package com.kopylov.musicplatform.service.impl;

import com.kopylov.musicplatform.constants.ErrorMessage;
import com.kopylov.musicplatform.dao.AlbumDAO;
import com.kopylov.musicplatform.dao.LikedSongDAO;
import com.kopylov.musicplatform.dao.SongAudioDAO;
import com.kopylov.musicplatform.dao.SongDAO;
import com.kopylov.musicplatform.dto.request.SaveAlbumDTO;
import com.kopylov.musicplatform.dto.response.AlbumDTO;
import com.kopylov.musicplatform.entity.Album;
import com.kopylov.musicplatform.entity.Song;
import com.kopylov.musicplatform.exception.NotFoundException;
import com.kopylov.musicplatform.helper.FileHelper;
import com.kopylov.musicplatform.mapper.request.RequestAlbumMapper;
import com.kopylov.musicplatform.mapper.response.ResponseAlbumMapper;
import com.kopylov.musicplatform.service.AlbumService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

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

    @Override
    public Album getAlbum(Long id) {
        return albumDAO.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.ALBUM_NOT_FOUND));
    }

    @Override
    public AlbumDTO getDetailedAlbum(Long id) {
        List<Song> songs = songDAO.findSongsByAlbumId(id);

        if (songs.isEmpty()) {
            throw new NotFoundException(ErrorMessage.ALBUM_NOT_FOUND);
        }

        return ResponseAlbumMapper.songListToAlbumDTO(songs);
    }

    @Override
    public List<Album> getAlbums() {
        return albumDAO.findAll();
    }

    @Override
    public List<Album> getSortedAlbums(boolean asc, String attribute) {
        return albumDAO.findAll(asc ? Sort.by(attribute).ascending() : Sort.by(attribute).descending());
    }

    @Override
    public void saveAlbum(SaveAlbumDTO saveAlbumDTO) throws IOException {
        MultipartFile file = saveAlbumDTO.getImage();
        FileHelper.saveUploadedFile(file, STATIC_IMAGE_PATH);
        String imageName = DB_IMAGE_PATH + file.getOriginalFilename();
        albumDAO.save(RequestAlbumMapper.saveAlbumDTOToEntity(saveAlbumDTO, imageName));
    }

    @Override
    public void deleteAlbum(Long id) {
        likedSongDAO.deleteByIdSongAlbumId(id);
        songAudioDAO.deleteBySongAlbumId(id);
        songDAO.deleteSongByAlbumId(id);
        albumDAO.deleteById(id);
    }

    @Override
    public void updateAlbum(Long id, Album album) {
        Album foundAlbum = albumDAO.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.ALBUM_NOT_FOUND));
        album.setId(foundAlbum.getId());
        albumDAO.save(album);
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
