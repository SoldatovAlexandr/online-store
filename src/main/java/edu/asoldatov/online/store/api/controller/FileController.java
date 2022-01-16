package edu.asoldatov.online.store.api.controller;

import edu.asoldatov.online.store.api.dto.FileDto;
import edu.asoldatov.online.store.mogel.File;
import edu.asoldatov.online.store.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/upload")
public class FileController {

    private final FileService fileService;

    @PostMapping(value = "/")
    public ResponseEntity<FileDto> upload(@RequestParam MultipartFile image) throws IOException {
        log.info("Upload image [{}]", image.getName());
        File file = fileService.upload(image);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "application/json; Accept: application/json")
                .body(FileDto.builder()
                        .id(file.getId())
                        .name(file.getName())
                        .build());
    }

    @Transactional
    @GetMapping(value = "/{id}")
    public ResponseEntity<Resource> download(@PathVariable String id) {
        File file = fileService.download(id);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName())
                .body(new ByteArrayResource(file.getData()));
    }
}
