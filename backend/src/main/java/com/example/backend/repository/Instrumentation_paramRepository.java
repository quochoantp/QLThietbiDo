package com.example.backend.repository;

import com.example.backend.dto.InstrumentParamRequestFirst;
import com.example.backend.entity.Instrumentation_param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Instrumentation_paramRepository extends JpaRepository<Instrumentation_param,Integer> {


    @Query("select distinct new  com.example.backend.dto.InstrumentParamRequestFirst(ip.instrumentation.insId,ip.instrumentation.ins_name,ip.instrumentation.ins_code,ip.parameter_type.parameter_type_name,ip.parameter_type.unit_id,ip.instrumentation.manufact,ip.instrumentation.ins_model,ip.instrumentation.status,ip.instrumentation.is_control_enable,ip.instrumentation.is_observable) " +
            "from Instrumentation_param ip " +
            "order by ip.instrumentation.ins_name" )
    public List<InstrumentParamRequestFirst> homeResponse();

//    @Query(value = "select i.ins_id,i.ins_name,i.ins_code,pt.parameter_type_name,pt.unit_id,i.manufact,i.ins_model,i.status,i.is_control_enable,i.is_observable\n" +
//            "            from Instrumentation_param ip, Parameter_type  pt, Instrumentations i \n" +
//            "            where ((i.ins_id = ip.ins_id) and(ip.param_id = pt.parameter_type_id)) \n" +
//            "            order by i.ins_name",nativeQuery = true)
//    public List<ParamHome> homeResponse();

      //      public void deleteAllByInsId(int ins_id);



    @Query("select max(ip.ins_param_id) from Instrumentation_param ip")
    Integer getMaxId();
    @Query("select new  com.example.backend.dto.InstrumentParamRequestFirst(i.insId,i.ins_name,i.ins_code,pt.parameter_type_name,pt.unit_id,i.manufact,i.ins_model,i.status,i.is_control_enable,i.is_observable) " +
            "from Instrumentation_param ip, ParameterType pt, Instrumentation i " +
            "where ((i.insId = ip.instrumentation.insId)" +
            "and(ip.parameter_type.parametertypeid = pt.parametertypeid) " +
            "and ((i.ins_name like concat('%',?1,'%'))or(?1 is null ))" +
            "and ((i.ins_code like concat('%',?2,'%'))or(?2 is null ))" +
            "and ((pt.parameter_type_name like concat('%',?3,'%'))or(?3 is null ))" +
            "and ((i.manufact like concat('%',?4,'%'))or(?4 is null ))" +
            "and ((i.ins_model like concat('%',?5,'%'))or(?5 is null ))" +
            "and ((i.status like concat('%',?6,'%'))or(?6 is null )) " +
            "and ((i.is_control_enable like concat('%',?7,'%'))or(?7 is null )) " +
            "and ((i.is_observable like concat('%',?8,'%'))or(?8 is null ))) " +
            "order by i.ins_name")
    public List<InstrumentParamRequestFirst> findAllCustom(String ins_name, String ins_code, String parameter_type_name, String manufact, String model, String status, String is_control_enable, String is_observable);

    public List<Instrumentation_param> deleteAllByInsIdAndParamId(int insId,int paramId);
    public Instrumentation_param findAllByInsIdAndParamId(int ins, int param);

//    @Query("select new  com.example.backend.dto.ParamHome(i.ins_name,i.ins_code,pt.parameter_type_name,i.manufact,i.ins_model,i.status) " +
//            "from Instrumentation_param ip, Parameter_type  pt, Instrumentation i " +
//            "where ((i.ins_id = ip.instrumentation.ins_id) and(ip.parameter_type.parameter_type_id = pt.parameter_type_id) and (i.ins_code = :ins_code)) " +
//            "order by i.ins_name")
//    public List<ParamHome> findByIns_code(@Param("ins_code") String ins_code);
//
//    @Query("select new  com.example.backend.dto.ParamHome(i.ins_name,i.ins_code,pt.parameter_type_name,i.manufact,i.ins_model,i.status) " +
//            "from Instrumentation_param ip, Parameter_type  pt, Instrumentation i " +
//            "where ((i.ins_id = ip.instrumentation.ins_id) and(ip.parameter_type.parameter_type_id = pt.parameter_type_id) and (pt.parameter_type_name = :parameter_type_name)) " +
//            "order by i.ins_name")
//    public List<ParamHome> findByParameter_type_name(@Param("parameter_type_name") String parameter_type_name);
//
//    @Query("select new  com.example.backend.dto.ParamHome(i.ins_name,i.ins_code,pt.parameter_type_name,i.manufact,i.ins_model,i.status) " +
//            "from Instrumentation_param ip, Parameter_type  pt, Instrumentation i " +
//            "where ((i.ins_id = ip.instrumentation.ins_id) and(ip.parameter_type.parameter_type_id = pt.parameter_type_id) and (i.manufact = :manufact)) " +
//            "order by i.ins_name")
//    public List<ParamHome> findByManufact(@Param("manufact") String manufact);
//
//    @Query("select new  com.example.backend.dto.ParamHome(i.ins_name,i.ins_code,pt.parameter_type_name,i.manufact,i.ins_model,i.status) " +
//            "from Instrumentation_param ip, Parameter_type  pt, Instrumentation i " +
//            "where ((i.ins_id = ip.instrumentation.ins_id) and(ip.parameter_type.parameter_type_id = pt.parameter_type_id) and (i.ins_model = :ins_model)) " +
//            "order by i.ins_name")
//    public List<ParamHome> findByIns_Model(@Param("ins_model") String ins_model);
//
//    @Query("select new  com.example.backend.dto.ParamHome(i.ins_name,i.ins_code,pt.parameter_type_name,i.manufact,i.ins_model,i.status) " +
//            "from Instrumentation_param ip, Parameter_type  pt, Instrumentation i " +
//            "where ((i.ins_id = ip.instrumentation.ins_id) and(ip.parameter_type.parameter_type_id = pt.parameter_type_id) and (i.status = :status)) " +
//            "order by i.ins_name")
//    public List<ParamHome> findByIns_status(@Param("status") int status);

}
