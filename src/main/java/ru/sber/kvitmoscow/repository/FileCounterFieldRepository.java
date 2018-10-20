package ru.sber.kvitmoscow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.sber.kvitmoscow.model.FileCounterField;

import java.util.List;

public interface FileCounterFieldRepository extends JpaRepository<FileCounterField, Integer> {
    @Modifying
    @Transactional
    @Query("delete from FileCounterField f where f.id=:id")
    int delete (@Param("id") int id);

    @Query("select u from FileCounterField u where u.userSetting.id=:userSettingId")
    List<FileCounterField> getAllByUserSettingId (@Param("userSettingId") int userSettingId);
}
