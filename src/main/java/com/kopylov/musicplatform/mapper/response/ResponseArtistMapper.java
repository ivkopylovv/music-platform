package com.kopylov.musicplatform.mapper.response;

import com.kopylov.musicplatform.dto.response.ArtistDTO;
import com.kopylov.musicplatform.entity.Album;
import com.kopylov.musicplatform.entity.Artist;
import com.kopylov.musicplatform.entity.Song;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class ResponseArtistMapper {

    public ArtistDTO songListToArtistDTO(List<Song> songs, Artist artist) {
        List<Album> albums = songs
                .stream()
                .map(Song::getAlbum)
                .distinct()
                .collect(Collectors.toList());

        return new ArtistDTO(
                artist.getId(),
                artist.getName(),
                artist.getImageName(),
                songs,
                albums
        );
    }
}
