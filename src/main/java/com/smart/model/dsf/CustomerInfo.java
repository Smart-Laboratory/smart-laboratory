package com.smart.model.dsf;

import com.smart.model.BaseObject;

import javax.persistence.*;

/**
 * Created by zjn on 2016/8/4.
 */

@Entity
@Table(name="DSF_CUSTOMER_BASE_INFO")
public class CustomerInfo extends BaseObject{
    private Long customerid;
    private String customername;
    private String address;

    public CustomerInfo() {
    }

    public CustomerInfo(Long customerid, String customername, String address) {
        this.customerid = customerid;
        this.customername = customername;
        this.address = address;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="customer_seq")
    @SequenceGenerator(name = "customer_seq", sequenceName = "DSF_CUSTOMER_SEQ", allocationSize=1)
    @Column(name = "customerid")
    public Long getCustomerid() {
        return customerid;
    }

    public void setCustomerid(Long customerid) {
        this.customerid = customerid;
    }
    @Column
    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername;
    }
    @Column
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
