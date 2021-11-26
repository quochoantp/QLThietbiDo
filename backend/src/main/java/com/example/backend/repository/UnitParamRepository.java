package com.example.backend.repository;

import com.example.backend.entity.UnitParam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UnitParamRepository extends JpaRepository<UnitParam,Integer> {
    @Query(value = "select u.unit_id, u.unit_code\n" +
            "            from unit u \n"
           ,nativeQuery = true)
    public List<UnitParam> UnitParamResponse();
}
