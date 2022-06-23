package com.kopylov.musicplatform.service;

import com.kopylov.musicplatform.dto.request.SaveUpdateArtistDTO;
import com.kopylov.musicplatform.dto.response.ArtistDTO;
import com.kopylov.musicplatform.entity.Artist;

import java.io.IOException;
import java.util.List;

public interface ArtistService {
    Artist getArtist(Long id);

    ArtistDTO getDetailedArtist(Long id);

    List<Artist> getArtists();

    List<Artist> getSortedArtists(boolean asc, String attribute);

    void saveArtist(SaveUpdateArtistDTO dto) throws IOException;

    void updateArtist(Long id, SaveUpdateArtistDTO dto) throws IOException;

    void deleteArtist(Long id);

    List<Artist> findArtistByName(String name);
}
