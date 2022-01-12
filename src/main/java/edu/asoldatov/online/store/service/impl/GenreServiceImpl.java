package edu.asoldatov.online.store.service.impl;

import edu.asoldatov.online.store.api.dto.GenreDto;
import edu.asoldatov.online.store.api.mapper.GenreMapper;
import edu.asoldatov.online.store.exception.NotFoundException;
import edu.asoldatov.online.store.mogel.Genre;
import edu.asoldatov.online.store.repository.GenreRepository;
import edu.asoldatov.online.store.service.GenreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;
    private final GenreMapper genreMapper;

    @Override
    public GenreDto find(Long id) {
        return genreMapper.to(genreRepository.findById(id).orElseThrow(NotFoundException::new));
    }

    @Override
    public List<GenreDto> findAll() {
        List<Genre> genres = genreRepository.findAll();
        return genres.stream()
                .map(genreMapper::to)
                .collect(Collectors.toList());
    }

    @Override
    public GenreDto add(GenreDto genreDto) {
        Genre genre = genreMapper.to(genreDto);
        genreRepository.save(genre);
        return genreMapper.to(genre);
    }

    @Transactional
    @Override
    public GenreDto update(GenreDto genreDto, Long id) {
        Genre genre = genreRepository.findById(id).orElseThrow(NotFoundException::new);
        genre.setName(genreDto.getName());
        genreRepository.save(genre);
        return genreMapper.to(genre);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        Genre genre = genreRepository.findById(id).orElseThrow(NotFoundException::new);
        genreRepository.delete(genre);
    }
}
