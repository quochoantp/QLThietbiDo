package com.example.backend.services;

import com.example.backend.entity.Instrumentation;
import com.example.backend.repository.InstrumentationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InstrumentationService {


    private InstrumentationRepository instrumentationRepository;
    @Autowired
    public void setInstrumentationRepository(InstrumentationRepository instrumentationRepository) {
        this.instrumentationRepository = instrumentationRepository;
    }

    public void addInstrumentation(Instrumentation i){
        int id = instrumentationRepository.getMaxId() + 1;
        i.setInsId(id);
        instrumentationRepository.save(i);
    }
    public int updateData( int id , Instrumentation i) {

        return instrumentationRepository.updateDataByIns_id(id,i.getIns_name(),i.getIns_code(),i.getManufact(),i.getIns_model(),
                                                          i.getStatus(),i.getIs_control_enable(),i.getIs_observable());
//                .map(instrumentation -> {
//                    instrumentation.setIns_name(updateInstrumentation.getIns_name());
//                    instrumentation.setIns_code(updateInstrumentation.getIns_code());
//                    instrumentation.setManufact(updateInstrumentation.getManufact());
//                    instrumentation.setIns_model(updateInstrumentation.getIns_model());
//                    instrumentation.setStatus(updateInstrumentation.getStatus());
//                    instrumentation.setIs_control_enable(updateInstrumentation.getIs_control_enable());
//                    instrumentation.setIs_observable(updateInstrumentation.getIs_observable());
//                    return instrumentationRepository.save(instrumentation);
//                }).orElseGet(() -> {
//                    newInstrumentation.setIns_id(id);
//                    return instrumentationRepository.save(newInstrumentation);
//                });
         }
//        Optional<Instrumentation> instrumentationData = instrumentationRepository.findById(id);
//        if(!instrumentationData.isPresent()){
//            return ResponseEntity.noContent().build();
//        }
//        updateInstrumentation.setIns_id(id);
//        instrumentationRepository.save(updateInstrumentation);
//        return ResponseEntity.ok().build();



}


