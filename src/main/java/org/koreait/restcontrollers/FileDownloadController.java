package org.koreait.restcontrollers;

import lombok.RequiredArgsConstructor;
import org.koreait.services.flie.FileDownloadService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FileDownloadController {
    private final FileDownloadService downloadService;


    @GetMapping("/file/download/{id}")
    public void download(@PathVariable Long id) {
        downloadService.download(id);
    }
}
