package com.smart.service.impl.lis;

import com.smart.dao.lis.CalculateFormulaDao;
import com.smart.model.lis.CalculateFormula;
import com.smart.model.lis.CalculateFormulaVo;
import com.smart.service.impl.GenericManagerImpl;
import com.smart.service.lis.CalculateFormulaManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Title: CalculateFormulaManagerImpl
 * Description: 计算公式
 *
 * @Author:zhou
 * @Date:2016/6/14 8:50
 * @Version:
 */
@Service(value = "calculateFormulaManager")
public class CalculateFormulaManagerImpl  extends GenericManagerImpl<CalculateFormula,Long> implements CalculateFormulaManager {
    private CalculateFormulaDao calculateFormulaDao = null;

    @Autowired
    public void setCalculateFormulaDao(CalculateFormulaDao calculateFormulaDao) {
        this.calculateFormulaDao = calculateFormulaDao;
        this.dao = calculateFormulaDao;
    }

    public List<CalculateFormulaVo> getCalculateFormulaList(String query, String lab, int start, int end, String sidx, String sord) {
        return calculateFormulaDao.getCalculateFormulaList(query,lab,start,end,sidx,sord);
    }

    public int getCalculateFormulaListCount(String query, String lab, int start, int end, String sidx, String sord) {
        return calculateFormulaDao.getCalculateFormulaListCount(query,lab,start,end,sidx,sord);
    }

    public CalculateFormulaVo getCalculateFormulaByTestId(String testId) {
        return calculateFormulaDao.getCalculateFormulaByTestId(testId);
    }
}
