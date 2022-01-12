package edu.asoldatov.online.store.api.mapper;

import edu.asoldatov.online.store.api.dto.GenreDto;
import edu.asoldatov.online.store.mogel.Genre;
import org.springframework.stereotype.Component;

@Component
public class GenreMapper {

    public GenreDto to(Genre genre){
        return GenreDto.builder()
                .id(genre.getId())
                .name(genre.getName())
                .build();
    }

    public Genre to(GenreDto genre){
        return Genre.builder()
                .id(genre.getId())
                .name(genre.getName())
                .build();
    }
}
