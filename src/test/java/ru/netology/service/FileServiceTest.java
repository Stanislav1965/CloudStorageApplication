package ru.netology.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.netology.exception.NotFoundException;
import ru.netology.repository.FileRepository;
import java.io.IOException;
import java.util.Collections;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.netology.TestData.*;

@ExtendWith(MockitoExtension.class)
public class FileServiceTest {

    @Mock
    private FileRepository fileRepository;

    @InjectMocks
    private FileService fileService;

    @Test
    void testUploadFile() throws IOException {
        Assertions.assertTrue(fileService.uploadFile(FILENAME_1, MULTIPART_FILE));
    }

    @Test
    void testUploadFile_ReturnException() throws IOException {
        Mockito.when(fileService.uploadFile(FILENAME_1, MULTIPART_FILE)).thenThrow(new RuntimeException());
        assertThrows(RuntimeException.class, () -> fileService.uploadFile(FILENAME_1, MULTIPART_FILE));
    }

    @Test
    public void testDeleteFile() throws NotFoundException {
        Mockito.when(fileRepository.findByFileName(FILENAME_1)).thenReturn(Optional.of(FILE_1));
        Assertions.assertTrue(fileService.deleteFile(FILENAME_1));
    }

    @Test
    void testDeleteFile_ReturnException() throws NotFoundException {
        Mockito.when(fileRepository.findByFileName(FILENAME_1)).thenThrow(new NotFoundException("Файл " + FILENAME_1 + "не существует"));
        assertThrows(NotFoundException.class, () -> fileService.deleteFile(FILENAME_1));
    }

    @Test
    public void testGetAllFiles() {
        Mockito.when(fileRepository.findAll(1)).thenReturn(Collections.singletonList(FILE_1));
        Assertions.assertEquals(fileService.getAllFiles(1).toList().get(0).getFileName(), FILE_DTO_1.getFileName());
    }

    @Test
    public void testGetFile() throws IOException {
        Mockito.when(fileRepository.findByFileName(FILENAME_1)).thenReturn(Optional.of(FILE_1));
        Assertions.assertEquals(fileService.getFile(FILENAME_1).getFileName(), FILENAME_1);
    }

    @Test
    void testGetFile_ReturnException() throws NotFoundException {
        Mockito.when(fileRepository.findByFileName(FILENAME_1)).thenThrow(new NotFoundException("Файл " + FILENAME_1 + "не существует"));
        assertThrows(NotFoundException.class, () -> fileService.getFile(FILENAME_1));
    }

    @Test
    public void testUpdateFile() throws IOException {
        Mockito.when(fileRepository.findByFileName(FILENAME_1)).thenReturn(Optional.of(FILE_1));
        Assertions.assertEquals(fileService.updateFile(FILENAME_1,MULTIPART_FILE),"Обновлен файл "+ FILENAME_1 );
    }

    @Test
    void testUpdateFile_ReturnException() throws NotFoundException {
        Mockito.when(fileRepository.findByFileName(FILENAME_1)).thenThrow(new NotFoundException("Файл " + FILENAME_1 + "не существует"));
        assertThrows(NotFoundException.class, () -> fileService.updateFile(FILENAME_1,MULTIPART_FILE));
    }

}
