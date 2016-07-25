package com.smart.model.micro;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

/**
 * Title: .IntelliJ IDEA
 * Description:
 *
 * @Author:zhou
 * @Date:2016/7/17 16:31
 * @Version:
 */
@Entity
@IdClass(TestCaseDetailsPK.class)
@Table(name = "lab_micro_testcasedetails")
public class TestCaseDetails {
    private String testCaseId;
    private String cultureMediumId;
    private String vaccinateMethod;
    private String cultureMethod;
    private String stainingMethod;
    private String humidity;
    private String temperature;
    private String air;
    private String cycle;
    private String orderId;
    private int isDefault;

    @Id
    public String getTestCaseId() {
        return testCaseId;
    }

    public void setTestCaseId(String testCaseId) {
        this.testCaseId = testCaseId;
    }

    @Id
    public String getCultureMediumId() {
        return cultureMediumId;
    }

    public void setCultureMediumId(String cultureMediumId) {
        this.cultureMediumId = cultureMediumId;
    }

    public String getVaccinateMethod() {
        return vaccinateMethod;
    }

    public void setVaccinateMethod(String vaccinateMethod) {
        this.vaccinateMethod = vaccinateMethod;
    }

    public String getCultureMethod() {
        return cultureMethod;
    }

    public void setCultureMethod(String cultureMethod) {
        this.cultureMethod = cultureMethod;
    }

    public String getStainingMethod() {
        return stainingMethod;
    }

    public void setStainingMethod(String stainingMethod) {
        this.stainingMethod = stainingMethod;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getAir() {
        return air;
    }

    public void setAir(String air) {
        this.air = air;
    }

    public String getCycle() {
        return cycle;
    }

    public void setCycle(String cycle) {
        this.cycle = cycle;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(int isDefault) {
        this.isDefault = isDefault;
    }
}
