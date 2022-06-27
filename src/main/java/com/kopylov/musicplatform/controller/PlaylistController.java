package com.kopylov.musicplatform.controller;

import com.kopylov.musicplatform.dto.request.AddSongToPlaylist;
import com.kopylov.musicplatform.dto.request.CreatePlaylistDTO;
import com.kopylov.musicplatform.dto.response.PlaylistDTO;
import com.kopylov.musicplatform.dto.response.PlaylistListDTO;
import com.kopylov.musicplatform.dto.response.SongListDTO;
import com.kopylov.musicplatform.dto.response.SuccessMessageDTO;
import com.kopylov.musicplatform.entity.Playlist;
import com.kopylov.musicplatform.entity.Song;
import com.kopylov.musicplatform.service.PlaylistService;
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
public class PlaylistController {
    private final PlaylistService playlistService;

    @GetMapping(value = "/playlists", params = {"username", "title"})
    public ResponseEntity<PlaylistDTO> getPlaylist(
            @RequestParam("username") String username, @RequestParam("title") String title) {
        return ResponseEntity.ok().body(playlistService.getPlaylist(username, title));
    }

    @GetMapping(value = "/playlists/detail", params = {"username", "title"})
    public ResponseEntity<Playlist> getDetailedPlaylist(
            @RequestParam("username") String username, @RequestParam("title") String title) {
        return ResponseEntity.ok().body(playlistService.getDetailPlaylist(username, title));
    }

    @GetMapping(value = "/playlists/{username}")
    public ResponseEntity<PlaylistListDTO> getPlaylists(@PathVariable String username) {
        List<PlaylistDTO> playlists = playlistService.getPlaylists(username);
        return ResponseEntity.ok().body(new PlaylistListDTO(playlists));
    }

    @GetMapping(value = "/playlists/", params = {"username", "asc", "attribute"})
    public ResponseEntity<PlaylistListDTO> getPlaylists(
            @RequestParam("username") String username,
            @RequestParam("asc") boolean asc,
            @RequestParam("attribute") String attribute) {
        List<PlaylistDTO> playlists = playlistService.getSortedPlaylists(asc, attribute, username);
        return ResponseEntity.ok().body(new PlaylistListDTO(playlists));
    }

    @PostMapping(value = "/playlists", consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<SuccessMessageDTO> createPlaylist(@ModelAttribute CreatePlaylistDTO dto) throws IOException {
        playlistService.createPlaylist(dto);
        return ResponseEntity.ok().body(new SuccessMessageDTO(PLAYLIST_WAS_CREATED));
    }

    @PostMapping(value = "/playlists")
    public ResponseEntity<SuccessMessageDTO> addSongToPlaylist(@RequestBody AddSongToPlaylist dto) {
        playlistService.addSongToPlaylist(dto);
        return ResponseEntity.ok().body(new SuccessMessageDTO(SONG_WAS_ADDED));
    }

    @DeleteMapping(value = "/playlists", params = {"id", "username", "title"})
    public ResponseEntity<SuccessMessageDTO> deleteSongFromPlaylist(
            @RequestParam("id") Long id,
            @RequestParam("username") String username,
            @RequestParam("title") String title) {
        playlistService.deleteSongFromPlaylist(username, title, id);
        return ResponseEntity.ok().body(new SuccessMessageDTO(SONG_WAS_DELETED));
    }

    @GetMapping(value = "/playlists/find", params = {"username", "title", "song-title", "album-title", "artist-name"})
    public ResponseEntity<SongListDTO> findPlaylistSongs(
            @RequestParam("username") String username,
            @RequestParam("title") String title,
            @RequestParam("song-title") String songTitle,
            @RequestParam("album-title") String albumTitle,
            @RequestParam("artist-name") String artistName) {
        List<Song> songs = playlistService
                .findPlaylistSongs(username, title, songTitle, albumTitle, artistName);
        return ResponseEntity.ok().body(new SongListDTO(songs));
    }
}
