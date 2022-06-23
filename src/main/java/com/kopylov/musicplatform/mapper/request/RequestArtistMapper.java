package com.kopylov.musicplatform.mapper.request;

import com.kopylov.musicplatform.dto.request.SaveUpdateArtistDTO;
import com.kopylov.musicplatform.entity.Artist;
import lombok.experimental.UtilityClass;

@UtilityClass
public class RequestArtistMapper {

    public Artist saveArtistDTOToEntity(SaveUpdateArtistDTO dto, String imageName) {

        return new Artist()
                .setName(dto.getName())
                .setImageName(imageName);
    }
}
