package ru.netology.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.netology.dto.FileDto;
import ru.netology.entity.File;
import ru.netology.exception.NotFoundException;
import ru.netology.repository.FileRepository;
import java.io.IOException;
import java.util.stream.Stream;

@Service
public class FileService {

    @Autowired
    private FileRepository fileRepository;

    @Transactional()
    public boolean deleteFile(String fileName) throws NotFoundException {
        File file = fileRepository.findByFileName(fileName)
                .orElseThrow(() -> new NotFoundException("Файл " + fileName + " не существует"));
        fileRepository.delete(file);
        return true;
    }

    @Transactional
    public File getFile(String fileName) throws NotFoundException {
        return fileRepository.findByFileName(fileName)
                .orElseThrow(() -> new NotFoundException("Файл " + fileName + " не существует"));
    }


    public Stream<FileDto> getAllFiles(Integer limit) {
        return fileRepository.findAll(limit).stream().map(o -> new FileDto(o.getFileName(), o.getSize()));
    }

    @Transactional()
    public boolean uploadFile(String fileName, MultipartFile multipartFile) throws IOException {
        File file = new File(fileName, multipartFile.getBytes(), multipartFile.getSize());
        fileRepository.save(file);
        return true;
    }

    @Transactional()
    public String updateFile(String fileName, MultipartFile multipartFile) throws IOException {
        fileRepository.findByFileName(fileName)
                .orElseThrow(() -> new NotFoundException("Файл " + fileName + " не существует"));
        fileRepository.update(fileName,multipartFile.getBytes(),multipartFile.getSize());
        return "Обновлен файл " + fileName;
    }
}


