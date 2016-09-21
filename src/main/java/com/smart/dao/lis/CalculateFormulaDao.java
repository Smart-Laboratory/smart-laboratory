package com.smart.dao.lis;

import com.smart.dao.GenericDao;
import com.smart.model.lis.CalculateFormula;
import com.smart.model.lis.CalculateFormulaVo;

import java.util.List;

/**
 * Title: CalculateFormulaDao
 * Description:计算公式
 *
 * @Author:zhou
 * @Date:2016/6/14 8:44
 * @Version:
 */
public interface CalculateFormulaDao extends GenericDao<CalculateFormula, Long> {
	
	List<CalculateFormulaVo> getCalculateFormulaList(String query, String lab, int start, int end, String sidx, String sord);
    
	int getCalculateFormulaListCount (String query, String lab, int start, int end, String sidx, String sord);
    
	CalculateFormulaVo getCalculateFormulaByTestId(String testId);
}
