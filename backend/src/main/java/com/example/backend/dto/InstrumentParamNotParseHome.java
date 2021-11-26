package com.example.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InstrumentParamNotParseHome {

    private int ins_id;
    private String ins_name;
    private String ins_code;
    private String parameter_type_name;
    private String unit_code;
    private String parameter_type_name_unit_name;
    private String manufact;
    private String ins_model;
    private String status;
    private String is_control_enable;
    private String is_observable;
}
