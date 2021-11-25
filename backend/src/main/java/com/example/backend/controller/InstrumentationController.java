package com.example.backend.controller;

import com.example.backend.dto.*;
import com.example.backend.entity.Instrumentation;
import com.example.backend.entity.Instrumentation_param;
import com.example.backend.repository.InstrumentationRepository;
import com.example.backend.repository.UnitParamRepository;
import com.example.backend.services.InstrumentationService;
import com.example.backend.services.Instrumentation_paramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class InstrumentationController {

    //private Instrumentation_paramRepository instrumentation_paramRepository;
    private UnitParamRepository unitParamRepository;
    private InstrumentationRepository instrumentationRepository;

    private InstrumentationService instrumentationService;

//    @Autowired
//    public void setInstrumentation_paramRepository(Instrumentation_paramRepository instrumentation_paramRepository) {
//        this.instrumentation_paramRepository = instrumentation_paramRepository;
//    }
    @Autowired
    public void setInstrumentationService(InstrumentationService instrumentationService) {
        this.instrumentationService = instrumentationService;
    }
    @Autowired
    public void setInstrumentationRepository(InstrumentationRepository instrumentationRepository) {
        this.instrumentationRepository = instrumentationRepository;
    }
    @Autowired
    public void setUnitParamRepository(UnitParamRepository unitParamRepository) {
        this.unitParamRepository = unitParamRepository;
    }
    @Autowired
    public void setInstrumentation_paramService(Instrumentation_paramService instrumentation_paramService) {
        this.instrumentation_paramService = instrumentation_paramService;
    }

    private Instrumentation_paramService instrumentation_paramService;

   @GetMapping("/firstRequest")
   public List<InstrumentParamRequestFirst> fetchDataFirstRequest(){
       return instrumentation_paramService.fetchDataFirstRequest() ;
   }

    @GetMapping("/datanotparse")
    public List<InstrumentParamNotParseHome> fetchDataNotParse(){
       List<InstrumentParamNotParseHome> instrumentParamNotParseHomeList = instrumentation_paramService.fetchDataNotParseHome();
       return instrumentParamNotParseHomeList;
    }
//    @PutMapping(value = "update/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
//    @ResponseStatus(HttpStatus.OK)
//       public ResponseEntity<Instrumentation> updateInstrumentation(@RequestBody Instrumentation instrumentation, @PathVariable("id") Integer ins_id){
//                  return new ResponseEntity<>(instrumentationService.updateData(instrumentation,ins_id),HttpStatus.OK);
//    }
    @PutMapping(value = "/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Integer> updateInstrumentation(@PathVariable("id") int ins_id,@RequestBody Instrumentation instrumentation){
        return new ResponseEntity<>(instrumentationService.updateData(ins_id,instrumentation),HttpStatus.OK);
    }
    @GetMapping("/getParaType")
    public List<ParaTypeUnitCode> getParaTypeUnitCode(){
        List<ParaTypeUnitCode> paraTypeUnitCodeList = instrumentation_paramService.getParaTypeUnitCode();
        return paraTypeUnitCodeList;
    }

    @GetMapping("/getParaTypeTable/{id}")
    public List<ParamTypeUnitIntoTable> getParaTypeUnitCodeTable(@PathVariable("id") int id) {
        List<ParamTypeUnitIntoTable> paramTypeUnitIntoTables =  instrumentation_paramService.getParaTypeUnitCodeTable(id);
        return paramTypeUnitIntoTables;
    }
    @PostMapping("/addNewIns")
    public void addInstrumentation(@RequestBody Instrumentation instrumentation){
       instrumentationService.addInstrumentation(instrumentation);
    }
    @PostMapping("/addInsParam/{ins_id}/{param_id}")
    public void addInstrumentationParam(@PathVariable(value = "ins_id") int ins_id,
                                        @PathVariable(value = "param_id") int param_id,
                                        @RequestBody Instrumentation_param ip){
        instrumentation_paramService.addInsParamData(ins_id,param_id,ip);
    }

    @GetMapping("/dataHomePage")
    public List<InstrumentParamInHomePage> fetchDataHomePage() throws NullPointerException{
        List<InstrumentParamInHomePage> instrumentParamInHomePageList = instrumentation_paramService.fetchDataHomePage();

        return instrumentParamInHomePageList;
    }

    @DeleteMapping("/delete/{insId}/{paramId}")
    public ResponseEntity<HttpStatus> deleteInsParam(@PathVariable("insId") Integer insId,
                                                     @PathVariable("paramId") Integer paramId){
       try{
            instrumentation_paramService.deleteInsParamData(insId,paramId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
       } catch (Exception e){
           return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
       }
    }
    @GetMapping("/allfix")
    public List<InstrumentParamInHomePage> fetchHomeParaParse() throws NullPointerException{
        List<InstrumentParamInHomePage> instrumentParamInHomePageList = instrumentation_paramService.fetchHomeParaParse();
        return instrumentParamInHomePageList;
    }
    @GetMapping("/search")
    public List<InstrumentParamInHomePage> findAllHomePage(@RequestParam(name = "ins_name",required = false) String ins_name,
                                                          @RequestParam(name = "ins_code",required = false) String ins_code,
                                                          @RequestParam(name = "parameter_type_name",required = false) String parameter_type_name,
                                                          @RequestParam(name = "manufact",required = false) String manufact,
                                                          @RequestParam(name = "ins_model",required = false) String model,
                                                          @RequestParam(name = "status",required = false) String status,
                                                          @RequestParam(name = "is_control_enable",required = false) String is_control_enable,
                                                          @RequestParam(name = "is_observable",required = false) String is_observable) throws NullPointerException{
        List<InstrumentParamInHomePage> instrumentParamInHomePageList = instrumentation_paramService.findAllInHomePage(ins_name,ins_code,parameter_type_name,manufact,model,status,is_control_enable,is_observable);

        return instrumentParamInHomePageList;
    }


}
