package com.example.backend.services;

import com.example.backend.entity.UnitParam;
import com.example.backend.repository.UnitParamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UnitParamService {
    private UnitParamRepository unitParamRepository;
    @Autowired
    public void setUnitParamRepository(UnitParamRepository unitParamRepository) {
        this.unitParamRepository = unitParamRepository;
    }
    public List<UnitParam> getUnitParamData(){
        return unitParamRepository.UnitParamResponse();
    }
}
