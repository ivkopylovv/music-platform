package com.kopylov.musicplatform.controller;

import com.kopylov.musicplatform.dto.request.SaveUpdateArtistDTO;
import com.kopylov.musicplatform.dto.response.ArtistDTO;
import com.kopylov.musicplatform.dto.response.ArtistListDTO;
import com.kopylov.musicplatform.dto.response.SuccessMessageDTO;
import com.kopylov.musicplatform.entity.Artist;
import com.kopylov.musicplatform.service.ArtistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

import static com.kopylov.musicplatform.constants.SuccessMessage.*;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class ArtistController {
    private final ArtistService artistService;

    @GetMapping(value = "/artists/{id}")
    ResponseEntity<Artist> getArtistById(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(artistService.getArtist(id));
    }

    @GetMapping(value = "/artists/detail/{id}")
    ResponseEntity<ArtistDTO> getDetailedArtistById(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(artistService.getDetailedArtist(id));
    }

    @GetMapping(value = "/artists")
    ResponseEntity<ArtistListDTO> getArtists() {
        List<Artist> artists = artistService.getArtists();
        return ResponseEntity.ok().body(new ArtistListDTO(artists));
    }

    @GetMapping(value = "/artists", params = {"asc", "attribute"})
    ResponseEntity<ArtistListDTO> getSortedArtists(
            @RequestParam("asc") boolean asc, @RequestParam("attribute") String attribute) {
        List<Artist> artists = artistService.getSortedArtists(asc, attribute);
        return ResponseEntity.ok().body(new ArtistListDTO(artists));
    }

    @PostMapping(value = "/artists", consumes = MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<SuccessMessageDTO> saveArtist(@ModelAttribute SaveUpdateArtistDTO dto) throws IOException {
        artistService.saveArtist(dto);
        return ResponseEntity.ok().body(new SuccessMessageDTO(ARTIST_WAS_SAVED));
    }

    @DeleteMapping(value = "/artists/{id}")
    ResponseEntity<SuccessMessageDTO> deleteArtist(@PathVariable("id") Long id) {
        artistService.deleteArtist(id);
        return ResponseEntity.ok().body(new SuccessMessageDTO(ARTIST_WAS_UPDATED));
    }

    @PutMapping(value = "/artists/{id}", consumes = MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<SuccessMessageDTO> updateArtist(
            @PathVariable("id") Long id, @ModelAttribute SaveUpdateArtistDTO dto) throws IOException {
        artistService.updateArtist(id, dto);
        return ResponseEntity.ok().body(new SuccessMessageDTO(ARTIST_WAS_DELETED));
    }

    @GetMapping(value = "artists/{name}")
    ResponseEntity<ArtistListDTO> findArtistsByTitle(@PathVariable String name) {
        List<Artist> artists = artistService.findArtistByName(name);
        return ResponseEntity.ok().body(new ArtistListDTO(artists));
    }
}
