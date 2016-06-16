package com.smart.dao.hibernate.lis;

import com.smart.dao.hibernate.GenericDaoHibernate;
import com.smart.dao.lis.TestReferenceDao;
import com.smart.model.lis.TestReference;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Title: TestReferenceDaoHibernate
 * Description:检验项目参考范围Hibernate
 *
 * @Author:zhou
 * @Date:2016/6/6 22:32
 * @Version:
 */
@Repository("testReferenceDao")
public class TestReferenceDaoHibernate  extends GenericDaoHibernate<TestReference, Long> implements TestReferenceDao {
    public TestReferenceDaoHibernate() {
        super(TestReference.class);
    }

    /**
     * 根据检验项目获取参考范围
     * @param testid
     * @return
     */
    public List<TestReference> getTestRefenreceListByTestId(String testid) {
        String sql = "from TestReference t where t.testId=:testid order by t.testId,t.sex,t.orderNo Asc ";
        Query query = getSession().createQuery(sql);
        query.setString("testid",testid);
        return query.list();
    }
    /**
     * 批量保存参考范围
     * @param testReferences  //仪器通道List
     */
    @Override
    public void saveTestReferences(List<TestReference> testReferences) {
        Session session = null;
        if(testReferences !=null && testReferences.size()>0){
            try{
                session = getSession();     //获取session
                session.beginTransaction(); //开启事务
                TestReference testReference  = null;
                for(int i = 0; i < testReferences.size(); i++){
                    testReference = testReferences.get(i);
                    session.saveOrUpdate(testReference);
                }
                // 批插入的对象立即写入数据库并释放内存
                session.flush();
                session.clear();
                session.getTransaction().commit(); // 提交事务
            }catch (Exception e){
                log.error(e);
                session.getTransaction().rollback(); // 出错将回滚事务
            }finally{
                session.close(); // 关闭Session
            }
        }
    }

    /**
     * 获取参考范围
     * @param testid
     * @param sex
     * @param orderNo
     * @return
     */
    public TestReference getTestReference(String testid, int sex, int orderNo)  {
        String Sql = "from TestReference t where t.testId=:testid and t.sex=:sex and t.orderno=:orderNo";
        Query query = getSession().createQuery(Sql);
        query.setString("testid",testid);
        query.setInteger("sex",sex);
        query.setInteger("orderNo",orderNo);
        List<TestReference> list = query.list();
        if(list.size()>0){
            return  list.get(0);
        }
        return null;
    }

    /**
     * 删除参考范围
     * @param testid
     * @param sex
     * @param orderNo
     * @return
     */
    public void deleteTestReference(String testid, int sex, int orderNo)  {
        String Sql="delete TestReference as t where t.testId=:testid and t.sex=:sex and t.orderNo=:orderNo";
        Query query = getSession().createQuery(Sql);
        query.setString("testid",testid);
        query.setInteger("sex",sex);
        query.setInteger("orderNo",orderNo);
        query.executeUpdate();
    }
}
