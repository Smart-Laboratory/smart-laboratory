package com.smart.model.micro;

import javax.persistence.*;

/**
 * Title: .IntelliJ IDEA
 * Description: 微生物基础数据
 *
 * @Author:zhou
 * @Date:2016/7/5 16:32
 * @Version:
 */
@Entity
@Table(name = "LAB_MICRO_ITEMINFO")
public class MicroItemInfo {
    private long id;
    private String indexId;             //编号
    private String name;                //名称
    private String english;             //英文名称
    private String sort;                //排序
    private String description;         //说明
    private String type;                //统计分类
    private String className;           //类别名称    培养结果、涂片结果

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ITEMINFO")
    @SequenceGenerator(name = "SEQ_ITEMINFO", sequenceName = "iteminfo_sequences", allocationSize = 1)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getIndexId() {
        return indexId;
    }

    public void setIndexId(String indexId) {
        this.indexId = indexId;
    }
}
