package com.kopylov.musicplatform.controller;

import com.kopylov.musicplatform.dto.response.CountDTO;
import com.kopylov.musicplatform.dto.response.SongListDTO;
import com.kopylov.musicplatform.dto.response.SuccessMessageDTO;
import com.kopylov.musicplatform.entity.Song;
import com.kopylov.musicplatform.service.SongService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.kopylov.musicplatform.constants.SuccessMessage.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class SongController {
    private final SongService songService;

    @GetMapping(value = "/songs/{id}")
    ResponseEntity<Song> getSongById(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(songService.getSong(id));
    }

    @GetMapping(value = "/songs")
    ResponseEntity<SongListDTO> getSongs() {
        List<Song> songs = songService.getSongs();
        return ResponseEntity.ok().body(new SongListDTO(songs));
    }

    @GetMapping(value = "/songs", params = {"asc", "attribute"})
    ResponseEntity<SongListDTO> getSortedSongs(
            @RequestParam("asc") boolean asc, @RequestParam("attribute") String attribute) {
        List<Song> songs = songService.getSortedSongs(asc, attribute);
        return ResponseEntity.ok().body(new SongListDTO(songs));
    }

    @PostMapping(value = "/songs")
    ResponseEntity<SuccessMessageDTO> saveSong(@RequestBody Song song) {
        songService.saveSong(song);
        return ResponseEntity.ok().body(new SuccessMessageDTO(SONG_WAS_SAVED));
    }

    @DeleteMapping(value = "/songs/{id}")
    ResponseEntity<SuccessMessageDTO> deleteSong(@PathVariable("id") Long id) {
        songService.deleteSong(id);
        return ResponseEntity.ok().body(new SuccessMessageDTO(SONG_WAS_DELETED));
    }

    @PutMapping(value = "/songs/{id}")
    ResponseEntity<SuccessMessageDTO> updateSong(@PathVariable("id") Long id, @RequestBody Song song) {
        songService.updateSong(id, song);
        return ResponseEntity.ok().body(new SuccessMessageDTO(SONG_WAS_UPDATED));
    }

    @GetMapping(value = "/songs/count")
    ResponseEntity<CountDTO> getSongsCount() {
        return ResponseEntity.ok().body(new CountDTO(songService.getSongsCount()));
    }

    @GetMapping(value = "songs/search/{title}")
    ResponseEntity<List<Song>> findSongsByTitle(@PathVariable String title) {
        return ResponseEntity.ok().body(songService.findSongs(title));
    }
}
