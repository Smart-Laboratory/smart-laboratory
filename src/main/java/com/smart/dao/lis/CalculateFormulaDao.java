package com.smart.dao.lis;

import com.smart.dao.GenericDao;
import com.smart.model.lis.CalculateFormula;
import com.smart.model.lis.CalculateFormulaVo;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

/**
 * Title: CalculateFormulaDao
 * Description:计算公式
 *
 * @Author:zhou
 * @Date:2016/6/14 8:44
 * @Version:
 */
public interface CalculateFormulaDao extends GenericDao<CalculateFormula, Long> {
	
	@Transactional
	List<CalculateFormulaVo> getCalculateFormulaList(String query, int start, int end, String sidx, String sord);
    
	@Transactional
	int getCalculateFormulaListCount (String query,  int start, int end, String sidx, String sord);
    
	@Transactional
	CalculateFormulaVo getCalculateFormulaByTestId(String testId);
}
