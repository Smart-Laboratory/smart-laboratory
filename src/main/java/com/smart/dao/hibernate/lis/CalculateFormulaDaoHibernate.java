package com.smart.dao.hibernate.lis;

import com.smart.Constants;
import com.smart.dao.hibernate.GenericDaoHibernate;
import com.smart.dao.lis.CalculateFormulaDao;
import com.smart.model.lis.CalculateFormula;
import com.smart.model.lis.CalculateFormulaVo;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Title: CalculateFormulaDaoHibernate
 * Description: 计算公式
 *
 * @Author:zhou
 * @Date:2016/6/14 8:45
 * @Version:
 */
@Repository("calculateFormulaDao")
public class CalculateFormulaDaoHibernate extends GenericDaoHibernate<CalculateFormula,Long> implements CalculateFormulaDao {
    public CalculateFormulaDaoHibernate() {
        super(CalculateFormula.class);
    }

    /**
     * 计算公式列表
     * @param query
     * @param start
     * @param end
     * @param sidx
     * @param sord
     * @return
     */
    @Override
    public List<CalculateFormulaVo> getCalculateFormulaList(String query, int start, int end, String sidx, String sord) {
        String sql = "select new com.smart.model.lis.CalculateFormulaVo(c, i.name) from CalculateFormula c,Index i where c.testId = i.indexId ";
        if(query != null && !query.equals(""))
            sql += " and  i.name like '%" +query +"%'";
        sidx = sidx.equals("") ? "testId" : sidx;
        sql +=" order by  c." +sidx + " " + sord;
        Query q = getSession().createQuery(sql);

        if(end !=0){
            q.setFirstResult(start);
            q.setMaxResults(end);
        }
        return q.list();
    }

    /**
     * 查询记录数
     * @param query
     * @param start
     * @param end
     * @param sidx
     * @param sord
     * @return
     */
    @Override
    public int getCalculateFormulaListCount(String query, int start, int end, String sidx, String sord) {
        String sql = "select count(*) from CalculateFormula c,Index i where c.testId = i.indexId ";
        if(query != null && !query.equals(""))
            sql += " and  i.name like '%" +query +"%'";
        sidx = sidx.equals("") ? "testId" : sidx;
        sql +=" order by  c." +sidx + " " + sord;
        Query q = getSession().createQuery(sql);
        return ((Long)q.uniqueResult()).intValue();
    }

    /**
     * 根据testid获取计算公式
     * @param testId    //项目ID
     * @return
     */
    @Override
    public CalculateFormulaVo getCalculateFormulaByTestId(String testId) {
        String sql = "select new com.smart.model.lis.CalculateFormulaVo(c, i.name) from CalculateFormula c,Index i where c.testId = i.indexId ";
        sql += " and c.testId=:testId";
        Query q = getSession().createQuery(sql);
        q.setString("testId",testId);
        List<CalculateFormulaVo> list = q.list();
        if(list.size()>0){
            return list.get(0);
        }
        return null;
    }
}
