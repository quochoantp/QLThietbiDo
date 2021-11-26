package com.example.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InstrumentParamRequestFirst {
    private int ins_id;
    private String ins_name;
    private String ins_code;
    private String parameter_type_name;
    private int unit_id;
    private String manufact;
    private String ins_model;
    private String status;
    private String is_control_enable;
    private String is_observable;
//    private String status_String;
//
//    public void setStatus_String(String status_String) {
//        if(this.status_String.equals(0)) {
//            this.status_String = "Chưa xử lý";
//        } else {
//            this.status_String = "Đã xử lý";
//        }
//    }
}
