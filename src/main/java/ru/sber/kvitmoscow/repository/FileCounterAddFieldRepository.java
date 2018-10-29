package ru.sber.kvitmoscow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.sber.kvitmoscow.model.FileCounterAddField;

import java.util.List;

public interface FileCounterAddFieldRepository extends JpaRepository<FileCounterAddField, Integer> {
    @Modifying
    @Transactional
    @Query("delete from FileCounterAddField s where s.id=:id")
    int delete(@Param("id") int id);

    @Query("select u from FileCounterAddField u where u.userSetting.id=:userSettingId")
    List<FileCounterAddField> getAllByUserSettingId(@Param("userSettingId") int userSettingId);
}