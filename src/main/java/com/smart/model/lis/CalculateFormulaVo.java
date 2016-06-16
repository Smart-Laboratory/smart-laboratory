package com.smart.model.lis;

/**
 * Title: CalculateFormulaVo
 * Description:计算公式Vo
 *
 * @Author:zhou
 * @Date:2016/6/14 10:32
 * @Version:
 */
public class CalculateFormulaVo {
    public CalculateFormulaVo(CalculateFormula calculateFormula,String testName){
        this.calculateFormula = calculateFormula;
        this.testName = testName;
    }

    public CalculateFormula getCalculateFormula() {
        return calculateFormula;
    }

    public void setCalculateFormula(CalculateFormula calculateFormula) {
        this.calculateFormula = calculateFormula;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    private CalculateFormula calculateFormula;      //计算公式
    private String testName;                        //项目名称

}
