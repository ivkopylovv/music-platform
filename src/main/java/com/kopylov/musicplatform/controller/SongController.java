package com.kopylov.musicplatform.controller;

import com.kopylov.musicplatform.dto.request.SaveUpdateSongDTO;
import com.kopylov.musicplatform.dto.response.CountDTO;
import com.kopylov.musicplatform.dto.response.SongAudioDTO;
import com.kopylov.musicplatform.dto.response.SongListDTO;
import com.kopylov.musicplatform.dto.response.SuccessMessageDTO;
import com.kopylov.musicplatform.entity.Song;
import com.kopylov.musicplatform.service.SongService;
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
public class SongController {
    private final SongService songService;

    @GetMapping(value = "/songs/{id}")
    public ResponseEntity<Song> getSongById(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(songService.getSong(id));
    }

    @GetMapping(value = "/songs")
    public ResponseEntity<SongListDTO> getSongs() {
        List<Song> songs = songService.getSongs();
        return ResponseEntity.ok().body(new SongListDTO(songs));
    }

    @GetMapping(value = "/songs", params = {"asc", "attribute"})
    public ResponseEntity<SongListDTO> getSortedSongs(
            @RequestParam("asc") boolean asc, @RequestParam("attribute") String attribute) {
        List<Song> songs = songService.getSortedSongs(asc, attribute);
        return ResponseEntity.ok().body(new SongListDTO(songs));
    }

    @PostMapping(value = "/songs", consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<SuccessMessageDTO> saveSong(@ModelAttribute SaveUpdateSongDTO songDTO) throws IOException {
        songService.saveSong(songDTO);
        return ResponseEntity.ok().body(new SuccessMessageDTO(SONG_WAS_SAVED));
    }

    @DeleteMapping(value = "/songs/{id}")
    public ResponseEntity<SuccessMessageDTO> deleteSong(@PathVariable("id") Long id) {
        songService.deleteSong(id);
        return ResponseEntity.ok().body(new SuccessMessageDTO(SONG_WAS_DELETED));
    }

    @PutMapping(value = "/songs/{id}", consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<SuccessMessageDTO> updateSong(
            @PathVariable("id") Long id, @ModelAttribute SaveUpdateSongDTO songDTO) throws IOException {
        songService.updateSong(id, songDTO);
        return ResponseEntity.ok().body(new SuccessMessageDTO(SONG_WAS_UPDATED));
    }

    @GetMapping(value = "/songs/count")
    public ResponseEntity<CountDTO> getSongsCount() {
        return ResponseEntity.ok().body(new CountDTO(songService.getSongsCount()));
    }

    @GetMapping(value = "songs/find/{title}")
    public ResponseEntity<SongListDTO> findSongsByTitle(@PathVariable String title) {
        List<Song> songs = songService.findSongsByTitle(title);
        return ResponseEntity.ok().body(new SongListDTO(songs));
    }

    @GetMapping(value = "/songs/audio/{id}")
    public ResponseEntity<SongAudioDTO> getSongAudio(@PathVariable Long id) {
        String audioName = songService.getSongAudio(id);
        return ResponseEntity.ok().body(new SongAudioDTO(audioName));
    }

}
