package ru.sber.kvitmoscow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.sber.kvitmoscow.model.Function;
import ru.sber.kvitmoscow.model.Template;

public interface FunctionRepository extends JpaRepository<Function, Integer> {
    @Modifying
    @Transactional
    @Query("delete from Function f where f.id=:id")
    int delete(@Param("id") int id);
}
