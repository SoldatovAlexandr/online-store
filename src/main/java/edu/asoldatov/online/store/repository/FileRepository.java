package edu.asoldatov.online.store.repository;

import edu.asoldatov.online.store.mogel.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, String> {

}
