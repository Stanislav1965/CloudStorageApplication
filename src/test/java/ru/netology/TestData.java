package ru.netology;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import ru.netology.dto.FileDto;
import ru.netology.dto.UserDto;
import ru.netology.entity.File;
import ru.netology.entity.User;

public class TestData {

    public static final String USERNAME_1 = "Username1";
    public static final String PASSWORD_1 = "Password1";
    public static final User USER_1 = new User(1L,USERNAME_1, PASSWORD_1);

    public static  final UserDto USER_DTO_1 = new UserDto(USERNAME_1,PASSWORD_1);

    public static final String FILENAME_1 = "Filename1";
    public static final Long SIZE_1 = 100L;
    public static final byte[] FILE_CONTENT_1 = FILENAME_1.getBytes();
    public static final File FILE_1 = new File( FILENAME_1, FILE_CONTENT_1, SIZE_1);
    public static final MultipartFile MULTIPART_FILE = new MockMultipartFile(FILENAME_1, FILE_CONTENT_1);

    public static  final FileDto FILE_DTO_1 = new FileDto(FILENAME_1,SIZE_1);

}
