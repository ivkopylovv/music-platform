package com.kopylov.musicplatform.controller;

import com.kopylov.musicplatform.dto.request.AddSongToLikedDTO;
import com.kopylov.musicplatform.dto.response.CountDTO;
import com.kopylov.musicplatform.dto.response.LikedSongDTO;
import com.kopylov.musicplatform.dto.response.LikedSongListDTO;
import com.kopylov.musicplatform.dto.response.SuccessMessageDTO;
import com.kopylov.musicplatform.service.LikedSongService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.kopylov.musicplatform.constants.SuccessMessage.SONG_WAS_ADDED;
import static com.kopylov.musicplatform.constants.SuccessMessage.SONG_WAS_DELETED;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class LikedSongController {
    private final LikedSongService likedSongService;

    @GetMapping(value = "/liked-songs", params = {"id", "username"})
    public ResponseEntity<LikedSongDTO> getLikedSong(
            @RequestParam("id") Long id, @RequestParam("username") String username) {
        return ResponseEntity.ok().body(likedSongService.getLikedSong(id, username));
    }

    @GetMapping(value = "/liked-songs/{username}")
    public ResponseEntity<LikedSongListDTO> getLikedSongs(@PathVariable("username") String username) {
        List<LikedSongDTO> songs = likedSongService.getLikedSongs(username);
        return ResponseEntity.ok().body(new LikedSongListDTO(songs));
    }

    @GetMapping(value = "/liked-songs", params = {"username", "asc", "attribute"})
    public ResponseEntity<LikedSongListDTO> getSortedLikedSongs(
            @RequestParam("username") String username,
            @RequestParam("asc") boolean asc,
            @RequestParam("attribute") String attribute) {
        List<LikedSongDTO> songs = likedSongService.getSortedLikedSongs(asc, attribute, username);
        return ResponseEntity.ok().body(new LikedSongListDTO(songs));
    }

    @PostMapping(value = "/liked-songs")
    public ResponseEntity<SuccessMessageDTO> addSongToLiked(@RequestBody AddSongToLikedDTO dto) {
        likedSongService.addSongToLiked(dto);
        return ResponseEntity.ok().body(new SuccessMessageDTO(SONG_WAS_ADDED));
    }

    @DeleteMapping(value = "/liked-songs", params = {"id", "username"})
    public ResponseEntity<SuccessMessageDTO> deleteSongFromLiked(
            @RequestParam("id") Long id, @RequestParam("username") String username) {
        likedSongService.deleteSongFromLiked(id, username);
        return ResponseEntity.ok().body(new SuccessMessageDTO(SONG_WAS_DELETED));
    }

    @GetMapping(value = "/liked-songs/find", params = {"username", "song-title", "album-title", "artist-name"})
    public ResponseEntity<LikedSongListDTO> findLikedSongs(
            @RequestParam("username") String username,
            @RequestParam("song-title") String songTitle,
            @RequestParam("album-title") String albumTitle,
            @RequestParam("artist-name") String artistName) {
        List<LikedSongDTO> songs = likedSongService.findLikedSongs(username, songTitle, albumTitle, artistName);
        return ResponseEntity.ok().body(new LikedSongListDTO(songs));
    }

    @GetMapping(value = "/liked-songs/count")
    public ResponseEntity<CountDTO> getLikedSongsCount() {
        return ResponseEntity.ok().body(new CountDTO(likedSongService.getLikedSongsCount()));
    }
}
