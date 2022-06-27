package com.kopylov.musicplatform.mapper.request;

import com.kopylov.musicplatform.dto.request.SaveUpdateAlbumDTO;
import com.kopylov.musicplatform.entity.Album;
import lombok.experimental.UtilityClass;

@UtilityClass
public class RequestAlbumMapper {

    public Album saveAlbumDTOToEntity(SaveUpdateAlbumDTO dto, String imageName) {
        return new Album()
                .setTitle(dto.getTitle())
                .setType(dto.getType())
                .setGenre(dto.getGenre())
                .setDuration(dto.getDuration())
                .setReleaseDate(dto.getReleaseDate())
                .setImageName(imageName);
    }
}
