package com.example.backend.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "INSTRUMENTATIONS")
@SecondaryTable(name = "INSTRUMENTATION_PARAM")
public class Instrumentation {
    @Id
    @Column(name = "INS_ID",nullable = false)
    private int insId;

    @Column(name = "INS_NAME", nullable = false)
    private String ins_name;

    @Column(name = "INS_CODE",nullable = false)
    private String ins_code;

    @Column(name = "MANUFACT",nullable = false)
    private String manufact;

    @Column(name = "INS_MODEL",nullable = false)
    private String ins_model;

    @Column(name = "CREATED_DATE",nullable = false)
    private Date created_date;

    @Column(name = "CREATED_USER",nullable = false)
    private String created_user;

    @Column(name = "UPDATED_DATE",nullable = true)
    private Date updated_date;

    @Column(name = "UPDATED_USER")
    private String updated_user;

    @Column(name = "STATUS",nullable = false)
    private String status;

    @Column(name = "IS_CONTROL_ENABLE")
    private String is_control_enable;

    @Column(name = "IS_OBSERVABLE")
    private String is_observable;


    @OneToMany(mappedBy = "instrumentation", cascade = {CascadeType.ALL,CascadeType.MERGE,CascadeType.REFRESH,CascadeType.REMOVE},fetch = FetchType.LAZY)
    //@OneToMany
    //@JoinColumn(name = "INS_ID")
    private List<Instrumentation_param> instrumentation_param;

    public void addIns(Instrumentation_param is){
        if(instrumentation_param != null){
            instrumentation_param = new ArrayList<Instrumentation_param>();
        }
        instrumentation_param.add(is);
        is.setInstrumentation(this);
    }

}
