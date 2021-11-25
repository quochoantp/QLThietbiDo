package com.example.backend.services;

import com.example.backend.dto.*;
import com.example.backend.entity.Instrumentation;
import com.example.backend.entity.Instrumentation_param;
import com.example.backend.entity.ParameterType;
import com.example.backend.entity.UnitParam;
import com.example.backend.repository.InstrumentationRepository;
import com.example.backend.repository.Instrumentation_paramRepository;
import com.example.backend.repository.Parameter_typeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class Instrumentation_paramService {
    private Instrumentation_paramRepository instrumentation_paramRepository;
    private UnitParamService unitParamService;

    @Autowired
    public void setInstrumentationRepository(InstrumentationRepository instrumentationRepository) {
        this.instrumentationRepository = instrumentationRepository;
    }

    private InstrumentationRepository instrumentationRepository;

    @Autowired
    public void setParameter_typeRepository(Parameter_typeRepository parameter_typeRepository) {
        this.parameter_typeRepository = parameter_typeRepository;
    }

    private Parameter_typeRepository parameter_typeRepository;
    @Autowired
    public void setInstrumentation_paramRepository(Instrumentation_paramRepository instrumentation_paramRepository) {
        this.instrumentation_paramRepository = instrumentation_paramRepository;
    }

    @Autowired
    public void setUnitParamService(UnitParamService unitParamService) {
        this.unitParamService = unitParamService;
    }


    //Xuat du lieu tu repo chua xu ly
    public List<InstrumentParamRequestFirst> fetchDataFirstRequest() {
        return instrumentation_paramRepository.homeResponse();
    }

    public void addInsParamData( int insId,int paramId,Instrumentation_param ip){

           int id;
            id = instrumentation_paramRepository.getMaxId() + 2;
        Instrumentation i = instrumentationRepository.findByInsId(insId);
        ParameterType p = parameter_typeRepository.findByParametertypeid(paramId);
               ip.setIns_param_id(id);

               ip.setParameter_type(p);
               p.getInstrumentation_params().add(ip);

               ip.setInstrumentation(i);
               i.getInstrumentation_param().add(ip);
               instrumentation_paramRepository.save(ip);

    }
    public List<Instrumentation_param> deleteInsParamData(int insId, int paramId){
        Instrumentation i = instrumentationRepository.findByInsId(insId);
        ParameterType p = parameter_typeRepository.findByParametertypeid(paramId);
        Instrumentation_param ip = instrumentation_paramRepository.findAllByInsIdAndParamId(insId,paramId);
        i.getInstrumentation_param().remove(ip);
        p.getInstrumentation_params().remove(ip);
        instrumentation_paramRepository.deleteAllByInsIdAndParamId(insId,paramId);
        return instrumentation_paramRepository.deleteAllByInsIdAndParamId(insId,paramId);
    }
    public List<InstrumentParamNotParseHome> fetchDataNotParseHome() {
        List<InstrumentParamRequestFirst> instrumentParamRequestFirstList = instrumentation_paramRepository.homeResponse();
        List<InstrumentParamNotParseHome> instrumentParamNotParseHomeList = new ArrayList<>();
        List<UnitParam> unitParamList = unitParamService.getUnitParamData();
        InstrumentParamNotParseHome instrumentParamNotParseHome = new InstrumentParamNotParseHome();
        validateDataExportHome(instrumentParamRequestFirstList, instrumentParamNotParseHome, unitParamList, instrumentParamNotParseHomeList);
        return instrumentParamNotParseHomeList;
    }

    /*
     @Param instrumentParamRequestFirstList List<InstrumentParamRequestFirst> : List du lieu lay tu repository ve de tien hanh validate
     @Param instrumentParamParseHome : Bien trung gian muc dich de gan vao list danh sach du lieu da tien xu ly
     @Param unitParamList: List danh sach cac don vi lay tu UnitParamRepository
     @Param instrumentParamNotParseHomeList: Danh sach du lieu da xu ly de tra ve thuc hien logic tren frontend
     */

    //Lay danh sach Yeutodo va unit_code
    public List<ParaTypeUnitCode> getParaTypeUnitCode() {
        List<InstrumentParamRequestFirst> instrumentParamRequestFirstList = instrumentation_paramRepository.homeResponse();
        List<UnitParam> unitParamList = unitParamService.getUnitParamData();
        List<ParaTypeUnitCode> paraTypeUnitCodeList = new ArrayList<>();
        ParaTypeUnitCode paraTypeUnitCode = new ParaTypeUnitCode();
        for (UnitParam unitParam : unitParamList) {
            for (InstrumentParamRequestFirst instrumentParamRequestFirst : instrumentParamRequestFirstList) {
                if (unitParam.getUnit_id() == instrumentParamRequestFirst.getUnit_id()) {
                    paraTypeUnitCode.setUnit_id(unitParam.getUnit_id());
                    paraTypeUnitCode.setParameter_type_name(instrumentParamRequestFirst.getParameter_type_name());
                    paraTypeUnitCode.setUnit_code(unitParam.getUnit_code());
                    paraTypeUnitCode.setPara_unit_code(instrumentParamRequestFirst.getParameter_type_name() + " - " + unitParam.getUnit_code());
                    paraTypeUnitCodeList.add(paraTypeUnitCode);
                    paraTypeUnitCode = new ParaTypeUnitCode();
                    break;
                }
            }
        }
        return paraTypeUnitCodeList;
    }

    //Lay yeu to do cua 1 thiet bi do de dua ra bang trong frontend
    public List<ParamTypeUnitIntoTable> getParaTypeUnitCodeTable(int id) {
        List<InstrumentParamRequestFirst> instrumentParamRequestFirstList = instrumentation_paramRepository.homeResponse();
        List<UnitParam> unitParamList = unitParamService.getUnitParamData();
        List<ParamTypeUnitIntoTable> paramTypeUnitIntoTables = new ArrayList<>();
        ParamTypeUnitIntoTable paraTypeUnit = new ParamTypeUnitIntoTable();
        for (InstrumentParamRequestFirst instrumentParamRequestFirst : instrumentParamRequestFirstList) {
            if (id == instrumentParamRequestFirst.getIns_id()) {
                for (UnitParam unitParam : unitParamList) {
                    if (unitParam.getUnit_id() == instrumentParamRequestFirst.getUnit_id()) {
                        paraTypeUnit.setIns_id(instrumentParamRequestFirst.getIns_id());
                        paraTypeUnit.setUnit_id(unitParam.getUnit_id());
                        paraTypeUnit.setUnit_code(unitParam.getUnit_code());
                        paraTypeUnit.setParameter_type_name(instrumentParamRequestFirst.getParameter_type_name());
                        paramTypeUnitIntoTables.add(paraTypeUnit);
                        paraTypeUnit = new ParamTypeUnitIntoTable();
                    }
                }
            }
        }
        return paramTypeUnitIntoTables;
    }


    //Tra ve du lieu dung form tren trang HomePage
    public List<InstrumentParamInHomePage> fetchDataHomePage() throws NullPointerException {
        //Khoi tao cac bien can thiet
        List<InstrumentParamInHomePage> instrumentParamInHomePageList = new ArrayList<>();
        List<InstrumentParamRequestFirst> instrumentParamRequestFirstList = instrumentation_paramRepository.homeResponse();
        List<InstrumentParamNotParseHome> instrumentParamNotParseHomeList = new ArrayList<>();
        List<UnitParam> unitParamList = unitParamService.getUnitParamData();
        InstrumentParamNotParseHome instrumentParamNotParseHome = new InstrumentParamNotParseHome();


        validateDataExportHome(instrumentParamRequestFirstList, instrumentParamNotParseHome, unitParamList, instrumentParamNotParseHomeList);

        InstrumentParamNotParseHome paramHome = instrumentParamNotParseHomeList.get(0);
        List<String> paraList = new ArrayList<>();
        InstrumentParamInHomePage instrumentParamInHomePage = new InstrumentParamInHomePage();
        paraList.add(paramHome.getParameter_type_name_unit_name());
        instrumentParamInHomePage.setIns_id(paramHome.getIns_id());
        instrumentParamInHomePage.setIns_name(paramHome.getIns_name());
        instrumentParamInHomePage.setIns_code(paramHome.getIns_code());
        instrumentParamInHomePage.setManufact(paramHome.getManufact());
        instrumentParamInHomePage.setIns_model(paramHome.getIns_model());

        //validate du lieu co truong 0,1
        validateBinaryData(instrumentParamInHomePage, paramHome);

        boolean flag = false;
        for (InstrumentParamNotParseHome paramHome1 : instrumentParamNotParseHomeList) {


            if ((paramHome.getIns_name().equals(paramHome1.getIns_name())) &&
                    (paramHome.getIns_code().equals(paramHome1.getIns_code())) &&
                    (paramHome.getManufact().equals(paramHome1.getManufact())) &&
                    (paramHome.getIns_model().equals(paramHome1.getIns_model())) &&
                    (paramHome.getStatus().equals(paramHome1.getStatus()))) {
                for (String para : paraList) {
                    if (paramHome1.getParameter_type_name_unit_name().equals(para)) {
                        flag = true;
                    }
                }
                if (flag == false) {
                    paraList.add(paramHome1.getParameter_type_name_unit_name());
                    if (paramHome1.equals(instrumentParamNotParseHomeList.get(instrumentParamNotParseHomeList.size() - 1))) {
                        instrumentParamInHomePage.setParameter_type_name_unit(paraList);
                        instrumentParamInHomePageList.add(instrumentParamInHomePage);
                    }
                    continue;
                } else {
                    flag = false;
                }

            } else {
                instrumentParamInHomePage.setParameter_type_name_unit(paraList);
                instrumentParamInHomePageList.add(instrumentParamInHomePage);
                instrumentParamInHomePage = new InstrumentParamInHomePage();
                paramHome = paramHome1;
                paraList = new ArrayList<>();

                paraList.add(paramHome.getParameter_type_name_unit_name());
                instrumentParamInHomePage.setIns_id(paramHome.getIns_id());
                instrumentParamInHomePage.setIns_name(paramHome.getIns_name());
                instrumentParamInHomePage.setIns_code(paramHome.getIns_code());
                instrumentParamInHomePage.setManufact(paramHome.getManufact());
                instrumentParamInHomePage.setIns_model(paramHome.getIns_model());


                //validate du lieu co truong 0,1
                validateBinaryData(instrumentParamInHomePage, paramHome);
            }

        }
        return instrumentParamInHomePageList;
    }


    public List<InstrumentParamInHomePage> fetchHomeParaParse() throws NullPointerException {
        List<InstrumentParamInHomePage> instrumentParamInHomePageList = new ArrayList<>();
        List<InstrumentParamRequestFirst> instrumentParamRequestFirstList = new ArrayList();
        instrumentParamRequestFirstList = instrumentation_paramRepository.homeResponse();
        InstrumentParamRequestFirst instrumentParamRequestFirst = instrumentParamRequestFirstList.get(0);
        List<String> paraList = new ArrayList<>();
        InstrumentParamInHomePage instrumentParamInHomePage = new InstrumentParamInHomePage();
        paraList.add(instrumentParamRequestFirst.getParameter_type_name());
        instrumentParamInHomePage.setIns_name(instrumentParamRequestFirst.getIns_name());
        instrumentParamInHomePage.setIns_code(instrumentParamRequestFirst.getIns_code());
        instrumentParamInHomePage.setManufact(instrumentParamRequestFirst.getManufact());
        instrumentParamInHomePage.setIns_model(instrumentParamRequestFirst.getIns_model());

        validateBinaryData(instrumentParamInHomePage, instrumentParamRequestFirst);

        boolean flag = false;
        for (InstrumentParamRequestFirst instrumentParamRequestFirst1 : instrumentParamRequestFirstList) {


            if ((instrumentParamRequestFirst.getIns_name().equals(instrumentParamRequestFirst1.getIns_name())) &&
                    (instrumentParamRequestFirst.getIns_code().equals(instrumentParamRequestFirst1.getIns_code())) &&
                    (instrumentParamRequestFirst.getManufact().equals(instrumentParamRequestFirst1.getManufact())) &&
                    (instrumentParamRequestFirst.getIns_model().equals(instrumentParamRequestFirst1.getIns_model())) &&
                    (instrumentParamRequestFirst.getStatus().equals(instrumentParamRequestFirst1.getStatus()))) {
                for (String para : paraList) {
                    if (instrumentParamRequestFirst1.getParameter_type_name().equals(para)) {
                        flag = true;
                    }
                }
                if (flag == false) {
                    paraList.add(instrumentParamRequestFirst1.getParameter_type_name());
                    if (instrumentParamRequestFirst1.equals(instrumentParamRequestFirstList.get(instrumentParamRequestFirstList.size() - 1))) {
                        instrumentParamInHomePage.setParameter_type_name_unit(paraList);
                        instrumentParamInHomePageList.add(instrumentParamInHomePage);
                    }
                    continue;
                } else {
                    flag = false;
                }

            } else {
                instrumentParamInHomePage.setParameter_type_name_unit(paraList);
                instrumentParamInHomePageList.add(instrumentParamInHomePage);
                instrumentParamInHomePage = new InstrumentParamInHomePage();
                instrumentParamRequestFirst = instrumentParamRequestFirst1;
                paraList = new ArrayList<>();

                paraList.add(instrumentParamRequestFirst.getParameter_type_name());
                instrumentParamInHomePage.setIns_name(instrumentParamRequestFirst.getIns_name());
                instrumentParamInHomePage.setIns_code(instrumentParamRequestFirst.getIns_code());
                instrumentParamInHomePage.setManufact(instrumentParamRequestFirst.getManufact());
                instrumentParamInHomePage.setIns_model(instrumentParamRequestFirst.getIns_model());

                validateBinaryData(instrumentParamInHomePage, instrumentParamRequestFirst);
            }

        }
        return instrumentParamInHomePageList;
    }


    //Tim kiem tren trang chu
    public List<InstrumentParamInHomePage> findAllInHomePage(String ins_name,
                                                             String ins_code,
                                                             String parameter_type_name,
                                                             String manufact,
                                                             String model,
                                                             String status,
                                                             String is_control_enable,
                                                             String is_observable) throws NullPointerException {
        List<InstrumentParamInHomePage> instrumentParamInHomePageList = new ArrayList<>();
        List<InstrumentParamRequestFirst> instrumentParamRequestFirstList = new ArrayList();
        instrumentParamRequestFirstList = instrumentation_paramRepository.findAllCustom(ins_name, ins_code, parameter_type_name, manufact, model, status, is_control_enable, is_observable);
        InstrumentParamRequestFirst instrumentParamRequestFirst = instrumentParamRequestFirstList.get(0);
        List<String> paraList = new ArrayList<>();
        InstrumentParamInHomePage instrumentParamInHomePage = new InstrumentParamInHomePage();
        paraList.add(instrumentParamRequestFirst.getParameter_type_name());
        instrumentParamInHomePage.setIns_name(instrumentParamRequestFirst.getIns_name());
        instrumentParamInHomePage.setIns_code(instrumentParamRequestFirst.getIns_code());
        instrumentParamInHomePage.setManufact(instrumentParamRequestFirst.getManufact());
        instrumentParamInHomePage.setIns_model(instrumentParamRequestFirst.getIns_model());

        validateBinaryData(instrumentParamInHomePage, instrumentParamRequestFirst);

        boolean flag = false;
        for (InstrumentParamRequestFirst instrumentParamRequestFirst1 : instrumentParamRequestFirstList) {


            if ((instrumentParamRequestFirst.getIns_name().equals(instrumentParamRequestFirst1.getIns_name())) &&
                    (instrumentParamRequestFirst.getIns_code().equals(instrumentParamRequestFirst1.getIns_code())) &&
                    (instrumentParamRequestFirst.getManufact().equals(instrumentParamRequestFirst1.getManufact())) &&
                    (instrumentParamRequestFirst.getIns_model().equals(instrumentParamRequestFirst1.getIns_model())) &&
                    (instrumentParamRequestFirst.getStatus().equals(instrumentParamRequestFirst1.getStatus()))) {
                for (String para : paraList) {
                    if (instrumentParamRequestFirst1.getParameter_type_name().equals(para)) {
                        flag = true;
                    }
                }
                if (flag == false) {
                    paraList.add(instrumentParamRequestFirst1.getParameter_type_name());
                    if (instrumentParamRequestFirst1.equals(instrumentParamRequestFirstList.get(instrumentParamRequestFirstList.size() - 1))) {
                        instrumentParamInHomePage.setParameter_type_name_unit(paraList);
                        instrumentParamInHomePageList.add(instrumentParamInHomePage);
                    }
                    continue;
                } else {
                    flag = false;
                }

            } else {
                instrumentParamInHomePage.setParameter_type_name_unit(paraList);
                instrumentParamInHomePageList.add(instrumentParamInHomePage);
                instrumentParamInHomePage = new InstrumentParamInHomePage();
                instrumentParamRequestFirst = instrumentParamRequestFirst1;
                paraList = new ArrayList<>();

                paraList.add(instrumentParamRequestFirst.getParameter_type_name());
                instrumentParamInHomePage.setIns_name(instrumentParamRequestFirst.getIns_name());
                instrumentParamInHomePage.setIns_code(instrumentParamRequestFirst.getIns_code());
                instrumentParamInHomePage.setManufact(instrumentParamRequestFirst.getManufact());
                instrumentParamInHomePage.setIns_model(instrumentParamRequestFirst.getIns_model());


                validateBinaryData(instrumentParamInHomePage,instrumentParamRequestFirst);
            }

        }
        return instrumentParamInHomePageList;
    }


    public void validateDataExportHome(List<InstrumentParamRequestFirst> instrumentParamRequestFirstList,
                                       InstrumentParamNotParseHome instrumentParamNotParseHome,
                                       List<UnitParam> unitParamList,
                                       List<InstrumentParamNotParseHome> instrumentParamNotParseHomeList) {
        for (InstrumentParamRequestFirst instrumentParamRequestFirst : instrumentParamRequestFirstList) {
            instrumentParamNotParseHome.setIns_id(instrumentParamRequestFirst.getIns_id());
            instrumentParamNotParseHome.setIns_name(instrumentParamRequestFirst.getIns_name());
            instrumentParamNotParseHome.setIns_code(instrumentParamRequestFirst.getIns_code());
            instrumentParamNotParseHome.setParameter_type_name(instrumentParamRequestFirst.getParameter_type_name());
            instrumentParamNotParseHome.setIns_model(instrumentParamRequestFirst.getIns_model());
            instrumentParamNotParseHome.setManufact(instrumentParamRequestFirst.getManufact());
            instrumentParamNotParseHome.setStatus(instrumentParamRequestFirst.getStatus());
            instrumentParamNotParseHome.setIs_control_enable(instrumentParamRequestFirst.getIs_control_enable());
            instrumentParamNotParseHome.setIs_observable(instrumentParamRequestFirst.getIs_observable());
            for (UnitParam unitParam : unitParamList) {
                if (instrumentParamRequestFirst.getUnit_id() == unitParam.getUnit_id()) {
                    instrumentParamNotParseHome.setUnit_code(unitParam.getUnit_code());
                    instrumentParamNotParseHome.setParameter_type_name_unit_name(instrumentParamNotParseHome.getParameter_type_name() + " - " + unitParam.getUnit_code());
                    instrumentParamNotParseHomeList.add(instrumentParamNotParseHome);
                    instrumentParamNotParseHome = new InstrumentParamNotParseHome();
                    break;
                }
            }
        }
    }

    public void validateBinaryData(InstrumentParamInHomePage instrumentParamInHomePage, InstrumentParamNotParseHome paramHome) {
        if (Integer.parseInt(paramHome.getStatus()) == 1) {
            instrumentParamInHomePage.setStatus("Có sử dụng");
        } else {
            instrumentParamInHomePage.setStatus("Không sử dụng");
        }
        if (Integer.parseInt(paramHome.getIs_control_enable()) == 1) {
            instrumentParamInHomePage.setIs_control_enable("Có thể điều khiển");
        } else {
            instrumentParamInHomePage.setIs_control_enable("Không thể điều khiển");
        }
        if (Integer.parseInt(paramHome.getIs_observable()) == 1) {
            instrumentParamInHomePage.setIs_observable("Có thể quan sát");
        } else {
            instrumentParamInHomePage.setIs_observable("Không thể quan sát");
        }
    }
    public void validateBinaryData(InstrumentParamInHomePage instrumentParamInHomePage, InstrumentParamRequestFirst instrumentParamRequestFirst) {
        if (Integer.parseInt(instrumentParamRequestFirst.getStatus()) == 1) {
            instrumentParamInHomePage.setStatus("Có sử dụng");
        } else {
            instrumentParamInHomePage.setStatus("Không sử dụng");
        }
        if (Integer.parseInt(instrumentParamRequestFirst.getIs_control_enable()) == 1) {
            instrumentParamInHomePage.setIs_control_enable("Có thể điều khiển");
        } else {
            instrumentParamInHomePage.setIs_control_enable("Không thể điều khiển");
        }
        if (Integer.parseInt(instrumentParamRequestFirst.getIs_observable()) == 1) {
            instrumentParamInHomePage.setIs_observable("Có thể quan sát");
        } else {
            instrumentParamInHomePage.setIs_observable("Không thể quan sát");
        }
    }
}
