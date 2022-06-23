package com.kopylov.musicplatform.dto.response;

import com.kopylov.musicplatform.entity.Album;
import com.kopylov.musicplatform.entity.Song;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ArtistDTO {
    private Long id;
    private String name;
    private String imageName;
    private List<Song> songs;
    private List<Album> albums;
}
