package org.koreait.restcontrollers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.koreait.commons.constants.JSONData;
import org.koreait.entities.file.FileEntity;
import org.koreait.services.flie.FileUploadService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Slf4j
@RestController
@RequiredArgsConstructor
public class FileUploadController {
    private final FileUploadService uploadService;

    @PostMapping("/file/upload")
    public ResponseEntity<JSONData<List<FileEntity>>> upload(MultipartFile[] files, String gId, String location){
        List<FileEntity> datas =  uploadService.upload(files,gId,location);

        JSONData<List<FileEntity>> jsonData  = new JSONData<>();
        jsonData.setSuccess(true);
        jsonData.setData(datas);

        return ResponseEntity.ok(jsonData);
    }
}
