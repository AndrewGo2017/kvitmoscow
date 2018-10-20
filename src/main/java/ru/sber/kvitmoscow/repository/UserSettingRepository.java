package ru.sber.kvitmoscow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.sber.kvitmoscow.model.UserSetting;

import java.util.List;

public interface UserSettingRepository extends JpaRepository<UserSetting, Integer> {
    @Modifying
    @Transactional
    @Query("delete from UserSetting u where u.id=:id")
    int delete (@Param("id") int id);

    @Query("select u from UserSetting u where u.user.id=:userId")
    List<UserSetting> getAllByUserId (@Param("userId") int userId);
}
