package com.example.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ParaTypeUnitCode {
    private String para_unit_code;
    private int unit_id;
    private String parameter_type_name;
    private String unit_code;
}
