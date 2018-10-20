package ru.sber.kvitmoscow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.sber.kvitmoscow.model.FileTemplate;

public interface FileTemplateRepository extends JpaRepository<FileTemplate, Integer> {
    @Modifying
    @Transactional
    @Query("delete from FileTemplate t where t.id=:id")
    int delete(@Param("id") int id);
}
