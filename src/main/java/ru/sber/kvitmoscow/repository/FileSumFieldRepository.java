package ru.sber.kvitmoscow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.sber.kvitmoscow.model.FileSumField;

import java.util.List;

public interface FileSumFieldRepository extends JpaRepository<FileSumField, Integer> {
    @Modifying
    @Transactional
    @Query("delete from FileSumField f where f.id=:id")
    int delete (@Param("id") int id);

    @Query("select u from FileSumField u where u.userSetting.id=:userSettingId")
    List<FileSumField> getAllByUserSettingId (@Param("userSettingId") int userSettingId);
}
