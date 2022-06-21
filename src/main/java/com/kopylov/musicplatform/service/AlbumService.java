package com.kopylov.musicplatform.service;

import com.kopylov.musicplatform.dto.response.AlbumDTO;
import com.kopylov.musicplatform.entity.Album;

import java.util.List;

public interface AlbumService {
    AlbumDTO getAlbum(Long id);

    List<Album> getAlbums();

    List<Album> getSortedAlbums(boolean asc, String attribute);

    void saveAlbum(Album album);

    void deleteAlbum(Long id);

    void updateAlbum(Long id, Album album);

    Long getAlbumsCount();

    List<Album> findAlbumByTitle(String title);

}
