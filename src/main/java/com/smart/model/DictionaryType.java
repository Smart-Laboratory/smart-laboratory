package com.smart.model;

import javax.persistence.*;

/**
 * Title: DictionaryType
 * Description: 数据字典类别
 *
 * @Author:zhou
 * @Date:2016/5/17 10:55
 * @Version:
 */
@Entity
@Table(name = "lab_dictionarytype")
public class DictionaryType {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }



    private Long id;
    private String typeName;        //类型名称
    private String orderNo;         //排序号
}
