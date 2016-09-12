package com.smart.model.lis;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by zcw on 2016/9/9.
 * 试管基础信息表
 */
@Entity
@Table(name = "lab_testtube")
public class TestTube implements Serializable{
    private static final long serialVersionUID = -5592217688167360956L;
    private Long id;
    private String name;        //名称
    private Double price;       //单价
    private String feeItemId;   //费用项目ID HIS对应

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="SEQ_TESTTUBE")
    @SequenceGenerator(name = "SEQ_TESTTUBE", sequenceName = "TESTTUBE_SEQUENCE", allocationSize=1)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getFeeItemId() {
        return feeItemId;
    }

    public void setFeeItemId(String feeItemId) {
        this.feeItemId = feeItemId;
    }
}
