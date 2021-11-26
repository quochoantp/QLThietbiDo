package com.example.backend.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "INSTRUMENTATION_PARAM")
public class Instrumentation_param {
    @Id
    @Column(name = "INS_PARAM_ID",nullable = false)
    private int ins_param_id;

    @Column(name = "INS_ID",nullable = false,insertable = false,updatable = false)
    private int insId;
    @Column(name = "PARAM_ID",nullable = false,insertable = false,updatable = false)
    private int paramId;


    @Column(name = "STATUS" , nullable = false)
    private String status;

    @Column(name = "CREATED_DATE" , nullable = false)
    private Date created_date;

    @Column(name = "CREATED_USER" , nullable = false)
    private String created_user;

    @Column(name = "UPDATED_DATE")
    private Date updated_date;

    @Column(name = "UPDATE_USER")
    private String update_user;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "INS_ID",referencedColumnName = "INS_ID")
    private Instrumentation instrumentation;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "PARAM_ID", referencedColumnName = "PARAMETER_TYPE_ID")
    private ParameterType parameter_type;




}
