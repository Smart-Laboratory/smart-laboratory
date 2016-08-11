package com.smart.model.micro;

import javax.persistence.*;

/**
 * Title: .IntelliJ IDEA
 * Description:培养基信息
 *
 * @Author:zhou
 * @Date:2016/7/6 11:01
 * @Version:
 */
@Entity
@Table(name = "LAB_MICRO_CULTUREMEDIUM")
public class CultureMedium {
    private Long id;
    private String name;
    private String shortName;
    private String vaccinateMethod;
    private String cultureMethod;
    private String stainingMethod;
    private String temperature;
    private String humidity;
    private String air;
    private String cycle;
    private int isVaccinate;
    private int isCulture;
    private int isSmear;
    private int isInstrument;
    private int isIdentification;
    private String style;
    private String spellcode;
    private int state;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CULTUREMEDIUM")
    @SequenceGenerator(name = "SEQ_CULTUREMEDIUM", sequenceName = "culturemedium_sequences", allocationSize = 1)
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

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
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

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
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

    public int getIsVaccinate() {
        return isVaccinate;
    }

    public void setIsVaccinate(int isVaccinate) {
        this.isVaccinate = isVaccinate;
    }

    public int getIsCulture() {
        return isCulture;
    }

    public void setIsCulture(int isCulture) {
        this.isCulture = isCulture;
    }

    public int getIsSmear() {
        return isSmear;
    }

    public void setIsSmear(int isSmear) {
        this.isSmear = isSmear;
    }

    public int getIsInstrument() {
        return isInstrument;
    }

    public void setIsInstrument(int isInstrument) {
        this.isInstrument = isInstrument;
    }

    public int getIsIdentification() {
        return isIdentification;
    }

    public void setIsIdentification(int isIdentification) {
        this.isIdentification = isIdentification;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getSpellcode() {
        return spellcode;
    }

    public void setSpellcode(String spellcode) {
        this.spellcode = spellcode;
    }

}
