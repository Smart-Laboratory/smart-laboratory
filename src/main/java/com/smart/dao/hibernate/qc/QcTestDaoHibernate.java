package com.smart.dao.hibernate.qc;

import com.smart.dao.hibernate.GenericDaoHibernate;
import com.smart.dao.qc.QcTestDao;
import com.smart.model.qc.QcTest;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Title: .IntelliJ IDEA
 * Description:
 *
 * @Author:zhou
 * @Date:2016/7/28 11:07
 * @Version:
 */
@Repository("qcTestDao")
public class QcTestDaoHibernate  extends GenericDaoHibernate<QcTest, Long> implements QcTestDao {
    public QcTestDaoHibernate() {
        super(QcTest.class);
    }

    /**
     * 记录总数
     * @param qcBatch
     * @param start
     * @param end
     * @param sidx
     * @param sord
     * @return
     */
    public int getCount(String qcBatch, int start, int end, String sidx, String sord) {
        String sql = "select count(*)  from QcTest  c where c.qcBatch=:qcBatch";
        sidx = sidx.equals("") ? "id" : sidx;
        sql +=" order by  " +sidx + " " + sord;
        Query q =  getSession().createQuery(sql);
        q.setString("qcBatch",qcBatch);
        return new Integer(q.uniqueResult() + "");
    }

    /**
     * 获取列表数据
     * @param qcBatch
     * @param start
     * @param end
     * @param sidx
     * @param sord
     * @return
     */
    @SuppressWarnings("unchecked")
	public List<QcTest> getDetails(String qcBatch, int start, int end, String sidx, String sord) {
        String sql = "from QcTest c where c.qcBatch=:qcBatch ";
        sidx = sidx.equals("") ? "id" : sidx;
        sql +=" order by  " +sidx + " " + sord;
        Query q = getSession().createQuery(sql);
        q.setString("qcBatch",qcBatch);
        if(end !=0){
            q.setFirstResult(start);
            q.setMaxResults(end);
        }
        return q.list();
    }

    /**
     * 保存明细
     * @param qcTestList
     */
    public void saveDetails(List<QcTest> qcTestList) {
        Session session = null;
        if(qcTestList !=null && qcTestList.size()>0){
            try{
                session = getSession();     //获取session
                session.beginTransaction(); //开启事务
                QcTest qcTest = null;
                for(int i = 0; i < qcTestList.size(); i++){
                    qcTest = qcTestList.get(i);
                    session.saveOrUpdate(qcTest);
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
}
