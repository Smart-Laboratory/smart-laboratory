package com.smart.dao.hibernate.micro;

import com.smart.dao.hibernate.GenericDaoHibernate;
import com.smart.dao.micro.TestCaseDetailsDao;
import com.smart.model.micro.TestCaseDetails;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Title: 微生物培养基方案
 * Description:
 *
 * @Author:zhou
 * @Date:2016/7/17 17:45
 * @Version:
 */
@Repository("testCaseDetailsDao")
public class TestCaseDetailsDaoHibernate extends GenericDaoHibernate<TestCaseDetails, Long> implements TestCaseDetailsDao {
    public TestCaseDetailsDaoHibernate() {
        super(TestCaseDetails.class);
    }

    public void saveDetails(List<TestCaseDetails> testCaseDetailsList) {

        Session session = null;
        if(testCaseDetailsList !=null && testCaseDetailsList.size()>0){
            try{
                session = getSession();     //获取session
                session.beginTransaction(); //开启事务
                TestCaseDetails testCaseDetails = null;
                for(int i = 0; i < testCaseDetailsList.size(); i++){
                    testCaseDetails = testCaseDetailsList.get(i);
                    session.saveOrUpdate(testCaseDetails);
                }
                // 批插入的对象立即写入数据库并释放内存
                session.flush();
                session.clear();
                session.getTransaction().commit(); // 提交事务
            }catch (Exception e){
                e.printStackTrace();
                session.getTransaction().rollback(); // 出错将回滚事务
            }finally{
                session.close(); // 关闭Session
            }
        }
    }

    public int getDetailsCount(String testCaseId, int start, int end, String sidx, String sord) {
        String sql = "select count(*)  from TestCaseDetails  c where c.testCaseId=:testCaseId";
        sidx = sidx.equals("") ? "id" : sidx;
        sql +=" order by  " +sidx + " " + sord;
        Query q =  getSession().createQuery(sql);
        q.setString("testCaseId",testCaseId);
        return new Integer(q.uniqueResult() + "");
    }

    @SuppressWarnings("unchecked")
	public List<Object[]> getDetails(String testCaseId, int start, int end, String sidx, String sord) {
        String sql = "from TestCaseDetails c,CultureMedium i where c.cultureMediumId = i.id and c.testCaseId=:testCaseId  ";
        sidx = sidx.equals("") ? "id" : sidx;
        sql +=" order by  " +sidx + " " + sord;
        Query q = getSession().createQuery(sql);
        q.setString("testCaseId",testCaseId);
        //System.out.println(sql);
        if(end !=0){
            q.setFirstResult(start);
            q.setMaxResults(end);
        }
        return q.list();
    }

    public void removeById(String testCaseId, String cultureMediumId) {
        String sql = "delete  TestCaseDetails d where d.testCaseId=:testCaseId and d.cultureMediumId=:cultureMediumId ";
        Query q = getSession().createQuery(sql);
        q.setString("testCaseId",testCaseId);
        q.setString("cultureMediumId",cultureMediumId);
        q.executeUpdate();
    }

    public void removeByTestCaseId(String testCaseId) {
        String sql = "delete  TestCaseDetails d where d.testCaseId=:testCaseId  ";
        Query q = getSession().createQuery(sql);
        q.setString("testCaseId",testCaseId);
        q.executeUpdate();
    }
}
