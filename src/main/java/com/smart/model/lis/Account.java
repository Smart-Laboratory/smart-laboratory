package com.smart.model.lis;

import com.smart.model.BaseObject;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 样本费用记账
 * Created by yuzh on 2016/9/22.
 */
@Entity
@Table(name = "l_account")
public class Account extends BaseObject implements Serializable {

    private Long id;
    private String ylxh;
    private String ylmc;
    private String barcode;
    private String feeId;
    private String feeName;
    private String accountId;
    private String operator;
    private Date operateTime;
    private double price;
    private int count;


    /**
     * 主键id，自增
     */
    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ACCOUNT")
    @SequenceGenerator(name = "SEQ_ACCOUNT", sequenceName = "account_sequence", allocationSize = 1)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column
    public String getYlxh() {
        return ylxh;
    }

    public void setYlxh(String ylxh) {
        this.ylxh = ylxh;
    }

    @Column
    public String getYlmc() {
        return ylmc;
    }

    public void setYlmc(String ylmc) {
        this.ylmc = ylmc;
    }

    @Column
    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    @Column
    public String getFeeId() {
        return feeId;
    }

    public void setFeeId(String feeId) {
        this.feeId = feeId;
    }

    @Column
    public String getFeeName() {
        return feeName;
    }

    public void setFeeName(String feeName) {
        this.feeName = feeName;
    }

    @Column
    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    @Column
    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    @Column
    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

    @Column
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Column
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String toString() {
        return null;
    }

    public boolean equals(Object o) {
        return false;
    }

    public int hashCode() {
        return 0;
    }
}
