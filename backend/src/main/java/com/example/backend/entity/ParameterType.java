package com.example.backend.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "PARAMETER_TYPE")
public class ParameterType {
    @Id
    @Column(name = "PARAMETER_TYPE_ID",nullable = false)
    private int parametertypeid;

    @Column(name = "PARAMETER_TYPE_NAME")
    private String parameter_type_name;

    @Column(name = "PARAMETER_TYPE_DESCRIPTION")
    private String parameter_type_description;

    @Column(name = "UNIT_ID")
    private Integer unit_id;

    @Column(name = "PARAMETER_TYPE_CODE")
    private String parameter_type_code;

    @Column(name = "CUSTOMS_POSITION_ID")
    private Integer customs_position_id;

    @Column(name = "FEATURES")
    private String features;

    @Column(name = "CUSTOM_POSITION_ID")
    private Integer custom_position_id;

    @Column(name = "METEOR_POSITION_ID")
    private Integer meteor_position_id;


    @OneToMany(mappedBy = "parameter_type", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
//    @OneToMany
//    @JoinColumn(name = "PARAMETER_TYPE_NAME")
    private List<Instrumentation_param> instrumentation_params;
}
