package com.kopylov.musicplatform.mapper.response;

import com.kopylov.musicplatform.dto.response.LikedSongDTO;
import com.kopylov.musicplatform.entity.LikedSong;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class ResponseLikedSongMapper {

    public LikedSongDTO likedSongToDTO(LikedSong likedSong) {
        return new LikedSongDTO(
                likedSong.getId().getSong(),
                likedSong.getAddedDate()
        );
    }

    public List<LikedSongDTO> likedSongListToDTOList(List<LikedSong> likedSongs) {
        return likedSongs
                .stream()
                .map(ResponseLikedSongMapper::likedSongToDTO)
                .collect(Collectors.toList()
                );
    }
}
