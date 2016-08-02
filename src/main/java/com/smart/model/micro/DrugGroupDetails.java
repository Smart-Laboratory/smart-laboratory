package com.smart.model.micro;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Title: .IntelliJ IDEA
 * Description:抗生素组合明细
 *
 * @Author:zhou
 * @Date:2016/7/8 15:59
 * @Version:
 */
@Entity
@IdClass(DrugGroupDetailsPK.class)
@Table(name = "lab_micro_drugdetails")
public class DrugGroupDetails implements Serializable {
    private static final long serialVersionUID = 1656664832378986431L;
    private String groupId;                    //组合ID
    private String drugId;                     //抗生素ID
    private String quantitativeResult;      //定量结果
    private String qualitativeResult;       //定性结果
    private String method;                  //默认方法
    private String micrange;                //MIC法默认浓度范围
    private String kbrange;                 //KB法默认范围
    private String spellcode;               //拼音简码
    private int state;                      //使用状态

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    @Id
    @Column(name = "groupid")
    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    @Id
    @Column(name = "drugid")
    public String getDrugId() {
        return drugId;
    }

    public void setDrugId(String drugId) {
        this.drugId = drugId;
    }

    public String getQuantitativeResult() {
        return quantitativeResult;
    }

    public void setQuantitativeResult(String quantitativeResult) {
        this.quantitativeResult = quantitativeResult;
    }

    public String getQualitativeResult() {
        return qualitativeResult;
    }

    public void setQualitativeResult(String qualitativeResult) {
        this.qualitativeResult = qualitativeResult;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getMicrange() {
        return micrange;
    }

    public void setMicrange(String micrange) {
        this.micrange = micrange;
    }

    public String getKbrange() {
        return kbrange;
    }

    public void setKbrange(String kbrange) {
        this.kbrange = kbrange;
    }

    public String getSpellcode() {
        return spellcode;
    }

    public void setSpellcode(String spellcode) {
        this.spellcode = spellcode;
    }

}
