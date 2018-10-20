package ru.sber.kvitmoscow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.sber.kvitmoscow.model.FileMainField;

import java.util.List;

public interface FileMainFieldRepository extends JpaRepository<FileMainField, Integer> {
    @Modifying
    @Transactional
    @Query("delete from FileMainField f where f.id=:id")
    int delete(@Param("id") int id);

    @Modifying
    @Transactional
    @Query("delete from FileMainField f where f.userSetting.id=:userSetting")
    void deleteAllBySettingId(@Param("userSetting") int userSetting);

    @Query("select u from FileMainField u where u.userSetting.id=:userSettingId")
    List<FileMainField> getAllByUserSettingId(@Param("userSettingId") int userSettingId);
}
