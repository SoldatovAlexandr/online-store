package edu.asoldatov.online.store.service.impl;

import edu.asoldatov.online.store.exception.NotFoundException;
import edu.asoldatov.online.store.mogel.File;
import edu.asoldatov.online.store.repository.FileRepository;
import edu.asoldatov.online.store.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;

    @Override
    public File upload(MultipartFile multipartFile) throws IOException {
        File file = File.builder()
                .data(multipartFile.getBytes())
                .type(multipartFile.getContentType())
                .name(multipartFile.getOriginalFilename())
                .build();
        return fileRepository.save(file);
    }

    @Override
    public File download(String id) {
        return fileRepository.findById(id).orElseThrow(NotFoundException::new);
    }
}
