package edu.asoldatov.online.store.api.controller;

import edu.asoldatov.online.store.api.dto.GenreDto;
import edu.asoldatov.online.store.service.GenreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/genres")
public class GenreController {

    private final GenreService genreService;

    @GetMapping("/{id}")
    public GenreDto get(final @PathVariable Long id) {
        log.info("Get genre by id [{}]", id);
        return genreService.find(id);
    }

    @GetMapping("/")
    public List<GenreDto> get() {
        log.info("Get all genres");
        return genreService.findAll();
    }

    @PostMapping("/")
    public GenreDto add(final @Valid @RequestBody GenreDto genreDto) {
        log.info("Add genre [{}]", genreDto);
        return genreService.add(genreDto);
    }

    @PutMapping("/{id}")
    public GenreDto update(final @Valid @RequestBody GenreDto genreDto,
                           final @PathVariable Long id) {
        log.info("Update genre [{}] by id [{}]", genreDto, id);
        return genreService.update(genreDto, id);
    }

    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(final @PathVariable Long id) {
        log.info("Delete genre by id [{}]", id);
        genreService.delete(id);
    }
}
