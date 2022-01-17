package edu.asoldatov.online.store.service;

import edu.asoldatov.online.store.mogel.File;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {
    File upload(MultipartFile multipartFile) throws IOException;

    File download(String id);
}
