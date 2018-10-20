package ru.sber.kvitmoscow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.sber.kvitmoscow.model.FileUniqueField;
import ru.sber.kvitmoscow.model.UserSetting;

import java.util.List;

public interface FileUniqueFieldRepository extends JpaRepository<FileUniqueField, Integer> {
    @Modifying
    @Transactional
    @Query("delete from FileUniqueField f where f.id=:id")
    int delete (@Param("id") int id);

    @Query("select u from FileUniqueField u where u.userSetting.id=:userSettingId")
    List<FileUniqueField> getAllByUserSettingId (@Param("userSettingId") int userSettingId);
}
