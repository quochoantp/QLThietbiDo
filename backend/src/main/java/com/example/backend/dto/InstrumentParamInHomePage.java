package com.example.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InstrumentParamInHomePage {
    private int ins_id;
    private String ins_name;
    private String ins_code;
    private List<String> parameter_type_name_unit;
    private String manufact;
    private String ins_model;
    private String status;
    private String is_control_enable;
    private String is_observable;
}
