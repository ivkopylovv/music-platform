package com.kopylov.musicplatform.mapper.response;

import com.kopylov.musicplatform.dto.response.PlaylistDTO;
import com.kopylov.musicplatform.entity.Playlist;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class ResponsePlaylistMapper {

    public PlaylistDTO entityToPlaylistDTO(Playlist playlist) {
        return new PlaylistDTO(
                playlist.getId().getTitle(),
                playlist.getImageName());
    }

    public List<PlaylistDTO> playlistsTOPlaylistDTOList(List<Playlist> playlists) {
        return playlists
                .stream()
                .map(ResponsePlaylistMapper::entityToPlaylistDTO)
                .collect(Collectors.toList());
    }
}
