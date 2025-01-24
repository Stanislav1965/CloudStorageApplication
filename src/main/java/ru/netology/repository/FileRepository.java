package ru.netology.repository;

import org.hibernate.sql.Update;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;
import ru.netology.entity.File;
import java.util.List;
import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {

    @Query(value="select f.* from Files f where upper(f.name) = upper(:filename)", nativeQuery = true)
    Optional<File> findByFileName(@Param("filename") String filename);

    @Query(value="select f.* from Files f limit :limit", nativeQuery = true)
    List<File> findAll(@Param("limit") Integer limit);

    @Modifying
    @Query(value="update Files set content = (:data) , size = (:size)  where upper(name) = upper(:filename)", nativeQuery = true)
    void update(@Param ("filename") String fileName, @Param("data") byte[] data, @Param("size") Long size );

}
