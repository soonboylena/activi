package com.github.soonboylena.activiti.vModel;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@NoArgsConstructor
@Entity
@DynamicUpdate
@Table(name = "address")
public class Address implements Serializable {

    private static final long serialVersionUID = 7648972413005017794L;

    @Id
    private int id;

    @Column
    private String areaCode;
    @Column
    private String cityCode;
    @Column
    private double lat;
    @Column
    private int level;
    @Column
    private double lng;
    @Column
    private String mergerName;
    @Column
    private String name;
    @Column
    private int parentId;
    @Column
    private String pinyin;
    @Column
    private String zipCode;
}
