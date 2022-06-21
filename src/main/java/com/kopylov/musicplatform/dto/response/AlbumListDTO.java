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
public class AlbumListDTO {
    List<Album> albums;
}
