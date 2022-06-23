package com.kopylov.musicplatform.dto.response;

import com.kopylov.musicplatform.entity.Artist;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ArtistListDTO {
    private List<Artist> artists;
}
