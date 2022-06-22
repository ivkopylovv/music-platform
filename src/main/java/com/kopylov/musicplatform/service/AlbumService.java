package com.kopylov.musicplatform.service;

import com.kopylov.musicplatform.dto.request.SaveAlbumDTO;
import com.kopylov.musicplatform.dto.response.AlbumDTO;
import com.kopylov.musicplatform.entity.Album;

import java.io.IOException;
import java.util.List;

public interface AlbumService {
    Album getAlbum(Long id);

    AlbumDTO getDetailedAlbum(Long id);

    List<Album> getAlbums();

    List<Album> getSortedAlbums(boolean asc, String attribute);

    void saveAlbum(SaveAlbumDTO saveAlbumDTO) throws IOException;

    void deleteAlbum(Long id);

    void updateAlbum(Long id, Album album);

    Long getAlbumsCount();

    List<Album> findAlbumsByTitle(String title);

}
