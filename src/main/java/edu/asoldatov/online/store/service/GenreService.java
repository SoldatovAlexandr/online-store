package edu.asoldatov.online.store.service;

import edu.asoldatov.online.store.api.dto.GenreDto;

import java.util.List;

public interface GenreService {
    GenreDto find(Long id);

    List<GenreDto> findAll();

    GenreDto add(GenreDto genreDto);

    GenreDto update(GenreDto genreDto, Long id);

    void delete(Long id);
}
