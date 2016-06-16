package com.smart.model.lis;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Title: CalculateFormula
 * Description:计算公式
 *
 * @Author:zhou
 * @Date:2016/6/14 8:35
 * @Version:
 */

@Entity
@Table(name = "l_calculateformula")
public class CalculateFormula {
    public int getFormulaType() {
        return formulaType;
    }

    public void setFormulaType(int formulaType) {
        this.formulaType = formulaType;
    }

    @Id
    public String getTestId() {
        return testId;
    }

    public void setTestId(String testId) {
        this.testId = testId;
    }

    public String getSampleType() {
        return sampleType;
    }

    public void setSampleType(String sampleType) {
        this.sampleType = sampleType;
    }

    public String getFormulaDescribe() {
        return formulaDescribe;
    }

    public void setFormulaDescribe(String formulaDescribe) {
        this.formulaDescribe = formulaDescribe;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public String getFormulaItem() {
        return formulaItem;
    }

    public void setFormulaItem(String formulaItem) {
        this.formulaItem = formulaItem;
    }

    public String getExclude() {
        return exclude;
    }

    public void setExclude(String exclude) {
        this.exclude = exclude;
    }

    public int getTestNumb() {
        return testNumb;
    }

    public void setTestNumb(int testNumb) {
        this.testNumb = testNumb;
    }

    public String getExcludeItem() {
        return excludeItem;
    }

    public void setExcludeItem(String excludeItem) {
        this.excludeItem = excludeItem;
    }

    public String getExcludeDescribe() {
        return excludeDescribe;
    }

    public void setExcludeDescribe(String excludeDescribe) {
        this.excludeDescribe = excludeDescribe;
    }

    private int formulaType;            //公式类型
    private String testId;              //项目ID
    private String sampleType;           //标本类型
    private String formulaDescribe;     //公式说明
    private String formula;             //计算公式
    private String formulaItem;         //公式项目
    private String exclude;             //排除
    private int testNumb;               //项目个数
    private String excludeItem;         //排除项目
    private String excludeDescribe;     //排除说明





}
