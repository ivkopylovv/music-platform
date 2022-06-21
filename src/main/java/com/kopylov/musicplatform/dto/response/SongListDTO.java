package com.kopylov.musicplatform.dto.response;

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
public class SongListDTO {
    List<Song> songs;
}
