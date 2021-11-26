package com.example.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "UNIT")
public class UnitParam {

    @Id
    @Column(name = "UNIT_ID",nullable = false)
    private int unit_id;
    @Column(name = "UNIT_CODE")
    private String unit_code;

}
