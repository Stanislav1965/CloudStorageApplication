package ru.netology.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.netology.dto.FileDto;
import ru.netology.entity.File;
import ru.netology.exception.NotFoundException;
import ru.netology.repository.FileRepository;

import java.io.IOException;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@Slf4j
public class FileService {

    @Autowired
    private FileRepository fileRepository;

    @Transactional()
    public boolean deleteFile(String fileName) throws NotFoundException {
        File file = checkFile(fileName);
        fileRepository.delete(file);
        log.info("Из хранилища удален файл: {}", fileName);
        return true;
    }

    @Transactional
    public File getFile(String fileName) throws NotFoundException {
        File file = checkFile(fileName);
        log.info("Из хранилища выгружен файл: {}", fileName);
        return file;
    }


    public Stream<FileDto> getAllFiles(Integer limit) {
        return fileRepository.findAll(limit).stream().map(o -> new FileDto(o.getFileName(), o.getSize()));
    }

    @Transactional()
    public boolean uploadFile(String fileName, MultipartFile multipartFile) throws IOException {
        File newFile = new File(fileName, multipartFile.getBytes(), multipartFile.getSize());
        fileRepository.save(newFile);
        return true;
    }

    @Transactional()
    public String updateFile(String fileName, MultipartFile multipartFile) throws IOException {
        File file = checkFile(fileName);
        fileRepository.update(fileName, multipartFile.getBytes(), multipartFile.getSize());
        log.info("В хранилище обновлен файл: {}", file.getFileName());
        return "Обновлен файл " + file.getFileName();
    }

    private File checkFile(String fileName) throws NotFoundException {
        Optional<File> file = fileRepository.findByFileName(fileName);
        if (file.isEmpty()) {
            log.error("В хранилище не найден файл: {}", fileName);
            throw new NotFoundException("Файл " + fileName + " не найден");
        }
        return file.get();
    }
}


