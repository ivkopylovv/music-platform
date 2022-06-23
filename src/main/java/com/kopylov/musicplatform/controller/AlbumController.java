package com.kopylov.musicplatform.controller;

import com.kopylov.musicplatform.dto.request.SaveAlbumDTO;
import com.kopylov.musicplatform.dto.response.AlbumDTO;
import com.kopylov.musicplatform.dto.response.AlbumListDTO;
import com.kopylov.musicplatform.dto.response.CountDTO;
import com.kopylov.musicplatform.dto.response.SuccessMessageDTO;
import com.kopylov.musicplatform.entity.Album;
import com.kopylov.musicplatform.service.AlbumService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

import static com.kopylov.musicplatform.constants.SuccessMessage.*;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AlbumController {
    private final AlbumService albumService;

    @GetMapping(value = "/albums/{id}")
    ResponseEntity<Album> getAlbumById(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(albumService.getAlbum(id));
    }

    @GetMapping(value = "/albums/detail/{id}")
    ResponseEntity<AlbumDTO> getDetailedAlbumById(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(albumService.getDetailedAlbum(id));
    }

    @GetMapping(value = "/albums")
    ResponseEntity<AlbumListDTO> getAlbums() {
        List<Album> albums = albumService.getAlbums();
        return ResponseEntity.ok().body(new AlbumListDTO(albums));
    }

    @GetMapping(value = "/albums", params = {"asc", "attribute"})
    ResponseEntity<AlbumListDTO> getSortedAlbums(
            @RequestParam("asc") boolean asc, @RequestParam("attribute") String attribute) {
        List<Album> albums = albumService.getSortedAlbums(asc, attribute);
        return ResponseEntity.ok().body(new AlbumListDTO(albums));
    }

    @PostMapping(value = "/albums", consumes = MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<SuccessMessageDTO> saveAlbum(@ModelAttribute SaveAlbumDTO dto) throws IOException {
        albumService.saveAlbum(dto);
        return ResponseEntity.ok().body(new SuccessMessageDTO(ALBUM_WAS_SAVED));
    }

    @DeleteMapping(value = "/albums/{id}")
    ResponseEntity<SuccessMessageDTO> deleteAlbum(@PathVariable("id") Long id) {
        albumService.deleteAlbum(id);
        return ResponseEntity.ok().body(new SuccessMessageDTO(ALBUM_WAS_DELETED));
    }

    @PutMapping(value = "/albums/{id}")
    ResponseEntity<SuccessMessageDTO> updateAlbum(@PathVariable("id") Long id, @RequestBody Album album) {
        albumService.updateAlbum(id, album);
        return ResponseEntity.ok().body(new SuccessMessageDTO(ALBUM_WAS_UPDATED));
    }

    @GetMapping(value = "/albums/count")
    ResponseEntity<CountDTO> getAlbumsCount() {
        return ResponseEntity.ok().body(new CountDTO(albumService.getAlbumsCount()));
    }

    @GetMapping(value = "albums/{title}")
    ResponseEntity<AlbumListDTO> findAlbumsByTitle(@PathVariable String title) {
        List<Album> albums = albumService.findAlbumsByTitle(title);
        return ResponseEntity.ok().body(new AlbumListDTO(albums));
    }
}
