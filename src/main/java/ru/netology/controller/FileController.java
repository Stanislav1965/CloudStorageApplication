package ru.netology.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.netology.dto.FileDto;
import ru.netology.entity.File;
import ru.netology.exception.NotFoundException;
import ru.netology.service.FileService;
import java.io.IOException;
import java.util.List;

@Controller
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping("/file")
    public ResponseEntity<String> uploadFile(
            @RequestParam("file")  MultipartFile multipartFile,
            @RequestParam("filename") String fileName
            ) {
        String message = "";
        try {
            fileService.uploadFile(fileName, multipartFile);

            message = "Успешно обновлен файл : " + fileName;
            return ResponseEntity.status(HttpStatus.OK).body(message);
        } catch (Exception e) {
            message = "Не удалось обновить: " + fileName + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
        }
    }


    @DeleteMapping("/file")
    public ResponseEntity<Void> deleteFile(@RequestParam("filename") String fileName) throws NotFoundException {
        if (fileService.deleteFile(fileName)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/file")
    public ResponseEntity<byte[]> getFile(@RequestParam("filename") String fileName) throws NotFoundException {
        File file = fileService.getFile(fileName);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"")
                .body(file.getContent());
    }

    @PutMapping("/file")
    public ResponseEntity<String> updateFile(@RequestParam(name = "filename") String fileName,
                                             @RequestParam(name = "fileData") MultipartFile multipartFile) throws IOException {
        return ResponseEntity.status(HttpStatus.OK).body(fileService.updateFile(fileName,multipartFile));
    }

    @GetMapping("/list")
    public ResponseEntity<List<FileDto>> getFiles(@RequestParam("limit") Integer limit) {
        return ResponseEntity.status(HttpStatus.OK).body(fileService.getAllFiles(limit).toList());
    }
}

