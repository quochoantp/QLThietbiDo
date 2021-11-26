package com.example.backend.repository;

import com.example.backend.entity.ParameterType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Parameter_typeRepository extends JpaRepository<ParameterType,Integer> {
    public ParameterType findByParametertypeid(int id);
}
