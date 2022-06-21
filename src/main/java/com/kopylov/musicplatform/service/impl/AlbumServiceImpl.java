package com.kopylov.musicplatform.service.impl;

import com.kopylov.musicplatform.constants.ErrorMessage;
import com.kopylov.musicplatform.dao.AlbumDAO;
import com.kopylov.musicplatform.dao.SongDAO;
import com.kopylov.musicplatform.dto.response.AlbumDTO;
import com.kopylov.musicplatform.entity.Album;
import com.kopylov.musicplatform.entity.Song;
import com.kopylov.musicplatform.exception.NotFoundException;
import com.kopylov.musicplatform.mapper.AlbumMapper;
import com.kopylov.musicplatform.service.AlbumService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AlbumServiceImpl implements AlbumService {
    private final SongDAO songDAO;
    private final AlbumDAO albumDAO;

    @Override
    public AlbumDTO getAlbum(Long id) {
        List<Song> songs = songDAO.findSongsByAlbumId(id);

        if (songs.isEmpty()) {
            throw new NotFoundException(ErrorMessage.ALBUM_NOT_FOUND);
        }

        return AlbumMapper.songListToAlbumDTO(songs);
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
    public void saveAlbum(Album album) {
        albumDAO.save(album);
    }

    @Override
    public void deleteAlbum(Long id) {
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
    public List<Album> findAlbumByTitle(String title) {
        return albumDAO.findAlbumsByTitleContaining(title);
    }

}
