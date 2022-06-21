package com.kopylov.musicplatform.mapper;

import com.kopylov.musicplatform.dto.response.AlbumDTO;
import com.kopylov.musicplatform.dto.response.SongArtistDTO;
import com.kopylov.musicplatform.entity.Song;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class AlbumMapper {

    public AlbumDTO songListToAlbumDTO(List<Song> songs) {
        List<SongArtistDTO> songArtistDTOs = songs
                .stream()
                .map(song -> new SongArtistDTO(
                        song.getId(),
                        song.getTitle(),
                        song.getAudioName(),
                        song.getDuration(),
                        song.getReleaseDate(),
                        song.getArtists()))
                .collect(Collectors.toList());

        return new AlbumDTO(songs.get(0).getAlbum(), songArtistDTOs);
    }

}
