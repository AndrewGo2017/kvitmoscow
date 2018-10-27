package ru.sber.kvitmoscow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.sber.kvitmoscow.model.FileSumAddField;

import java.util.List;

public interface FileSumURepository extends JpaRepository<FileSumAddField, Integer> {
    @Modifying
    @Transactional
    @Query("delete from FileSumAddField s where s.id=:id")
    int delete(@Param("id") int id);

    @Query("select u from FileSumAddField u where u.userSetting.id=:userSettingId")
    List<FileSumAddField> getAllByUserSettingId (@Param("userSettingId") int userSettingId);
}
