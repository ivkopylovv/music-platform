package com.kopylov.musicplatform.dto.response;

import com.kopylov.musicplatform.entity.Album;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AlbumDTO {
    private Album album;
    private List<SongArtistDTO> songs;
}
