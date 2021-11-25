package com.example.backend.repository;

import com.example.backend.entity.Instrumentation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface InstrumentationRepository extends JpaRepository<Instrumentation,Integer> {
    @Query("select max(i.insId) from Instrumentation i")
    Integer getMaxId();

    @Transactional
    @Modifying
    @Query("update Instrumentation i set i.ins_name = ?2, i.ins_code = ?3,i.manufact = ?4, " +
            "i.ins_model= ?5, i.status = ?6, i.is_control_enable = ?7,i.is_observable = ?8 where i.insId = ?1")
    public int updateDataByIns_id(int id, String ins_name, String ins_code, String manufact, String model, String status, String is_control_enable, String is_observable);

    public Instrumentation findByInsId(int id);
}
