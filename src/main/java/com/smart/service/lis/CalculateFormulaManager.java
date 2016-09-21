package com.smart.service.lis;

import com.smart.model.lis.CalculateFormula;
import com.smart.model.lis.CalculateFormulaVo;
import com.smart.service.GenericManager;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    List<CalculateFormulaVo> getCalculateFormulaList(String query, String lab, int start, int end, String sidx, String sord);

    @Transactional
    int getCalculateFormulaListCount(String query, String lab, int start, int end, String sidx, String sord);

    @Transactional
    CalculateFormulaVo getCalculateFormulaByTestId(String testId);
}
