package com.smart.model.dsf;

import com.smart.model.BaseObject;
import org.hibernate.search.annotations.DocumentId;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by zjn on 2016/8/23.
 */
@Entity
@Table(name = "DSF_CUSTOMERBASEDISCOUNT")
public class DSF_CustomerBaseDiscount extends BaseObject implements Serializable {
    private Long id;
    private String customerid;
    private String begintime;
    private String endtime;
    private String discountrate;
    private String thebasis;
    private String customername;
    private String remark;

    public DSF_CustomerBaseDiscount() {
    }

    public DSF_CustomerBaseDiscount(Long id, String customerid, String begintime, String endtime, String discountrate, String thebasis, String customername, String remark) {
        this.id = id;
        this.customerid = customerid;
        this.begintime = begintime;
        this.endtime = endtime;
        this.discountrate = discountrate;
        this.thebasis = thebasis;
        this.customername = customername;
        this.remark = remark;
    }

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_DSF_CUSTOMERBASEDISCOUNT")
    @SequenceGenerator(name = "SEQ_DSF_CUSTOMERBASEDISCOUNT", sequenceName = "DSF_CUSTOMERBASEDISCOUNT_SEQ", allocationSize = 1)
    @DocumentId
    @Id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerid() {
        return customerid;
    }

    public void setCustomerid(String customerid) {
        this.customerid = customerid;
    }

    public String getBegintime() {
        return begintime;
    }

    public void setBegintime(String begintime) {
        this.begintime = begintime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getDiscountrate() {
        return discountrate;
    }

    public void setDiscountrate(String discountrate) {
        this.discountrate = discountrate;
    }

    public String getThebasis() {
        return thebasis;
    }

    public void setThebasis(String thebasis) {
        this.thebasis = thebasis;
    }

    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        return false;
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
