package com.smart.service.lis;

import com.smart.model.lis.CalculateFormula;
import com.smart.model.lis.CalculateFormulaVo;
import com.smart.service.GenericManager;

import java.util.List;

/**
 * Title: CalculateFormulaManager
 * Description: 计算公式
 *
 * @Author:zhou
 * @Date:2016/6/14 8:49
 * @Version:
 */
public interface CalculateFormulaManager extends GenericManager<CalculateFormula, Long> {
    List<CalculateFormulaVo> getCalculateFormulaList(String query, int start, int end, String sidx, String sord);
    int getCalculateFormulaListCount(String query, int start, int end, String sidx, String sord);
    CalculateFormulaVo getCalculateFormulaByTestId(String testId);
}
