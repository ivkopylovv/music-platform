package com.kopylov.musicplatform.mapper.request;

import com.kopylov.musicplatform.dto.request.SaveUpdateSongDTO;
import com.kopylov.musicplatform.entity.Album;
import com.kopylov.musicplatform.entity.Artist;
import com.kopylov.musicplatform.entity.Song;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class RequestSongMapper {

    public Song saveSongDTOToEntity(SaveUpdateSongDTO dto, Album album, List<Artist> artists) {
        Song song = new Song();
        song.getArtists().addAll(artists);

        return song
                .setTitle(dto.getTitle())
                .setDuration(dto.getDuration())
                .setAlbum(album)
                .setReleaseDate(dto.getReleaseDate());
    }
}
