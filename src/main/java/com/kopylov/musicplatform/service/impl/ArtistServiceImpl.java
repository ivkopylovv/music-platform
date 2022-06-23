package com.kopylov.musicplatform.service.impl;

import com.kopylov.musicplatform.dao.ArtistDAO;
import com.kopylov.musicplatform.dao.LikedSongDAO;
import com.kopylov.musicplatform.dao.SongAudioDAO;
import com.kopylov.musicplatform.dao.SongDAO;
import com.kopylov.musicplatform.dto.request.SaveUpdateArtistDTO;
import com.kopylov.musicplatform.dto.response.ArtistDTO;
import com.kopylov.musicplatform.entity.Artist;
import com.kopylov.musicplatform.entity.Song;
import com.kopylov.musicplatform.exception.NotFoundException;
import com.kopylov.musicplatform.helper.FileHelper;
import com.kopylov.musicplatform.mapper.request.RequestArtistMapper;
import com.kopylov.musicplatform.mapper.response.ResponseArtistMapper;
import com.kopylov.musicplatform.service.ArtistService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static com.kopylov.musicplatform.constants.ErrorMessage.ARTIST_NOT_FOUND;
import static com.kopylov.musicplatform.constants.FilePath.DB_IMAGE_PATH;
import static com.kopylov.musicplatform.constants.FilePath.STATIC_IMAGE_PATH;

@Service
@Transactional
@RequiredArgsConstructor
public class ArtistServiceImpl implements ArtistService {
    private final ArtistDAO artistDAO;
    private final SongDAO songDAO;
    private final LikedSongDAO likedSongDAO;
    private final SongAudioDAO songAudioDAO;

    @Override
    public Artist getArtist(Long id) {
        return artistDAO.findById(id)
                .orElseThrow(() -> new NotFoundException(ARTIST_NOT_FOUND));
    }

    @Override
    public ArtistDTO getDetailedArtist(Long id) {
        Artist artist = getArtist(id);
        List<Song> songs = songDAO.findSongsByArtistsId(id);

        if (songs.isEmpty()) {
            throw new NotFoundException(ARTIST_NOT_FOUND);
        }

        return ResponseArtistMapper.songListToArtistDTO(songs, artist);
    }

    @Override
    public List<Artist> getArtists() {
        return artistDAO.findAll();
    }

    @Override
    public List<Artist> getSortedArtists(boolean asc, String attribute) {
        return artistDAO.findAll(asc ? Sort.by(attribute).ascending() : Sort.by(attribute).descending());
    }

    @Override
    public void saveArtist(SaveUpdateArtistDTO dto) throws IOException {
        MultipartFile file = dto.getImage();
        FileHelper.saveUploadedFile(file, STATIC_IMAGE_PATH);
        String imageName = DB_IMAGE_PATH + file.getOriginalFilename();
        artistDAO.save(RequestArtistMapper.saveArtistDTOToEntity(dto, imageName));
    }

    @Override
    public void updateArtist(Long id, SaveUpdateArtistDTO dto) throws IOException {
        Artist artist = getArtist(id);

        MultipartFile file = dto.getImage();
        FileHelper.saveUploadedFile(file, STATIC_IMAGE_PATH);
        String imageName = DB_IMAGE_PATH + file.getOriginalFilename();

        artist.setImageName(imageName);
        artist.setName(dto.getName());

        artistDAO.save(artist);
    }

    @Override
    public void deleteArtist(Long id) {
        likedSongDAO.deleteByIdSongArtistsId(id);
        songAudioDAO.deleteBySongArtistsId(id);
        songDAO.deleteSongByArtistsId(id);
        artistDAO.deleteById(id);
    }

    @Override
    public List<Artist> findArtistByName(String name) {
        return artistDAO.findArtistsByNameContaining(name);
    }
}
