package edu.asoldatov.online.store.repository;

import edu.asoldatov.online.store.mogel.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Long> {

    Genre findByName(String name);
}
