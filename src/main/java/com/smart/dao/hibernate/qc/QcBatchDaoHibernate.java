package com.smart.dao.hibernate.qc;

import com.smart.dao.hibernate.GenericDaoHibernate;
import com.smart.dao.qc.QcBatchDao;
import com.smart.model.qc.QcBatch;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Title: QcBathDaoHibernate
 * Description: 质控项目设定
 *
 * @Author:zhou
 * @Date:2016/7/27 11:10
 * @Version:
 */

@Repository("qcBathDao")
public class QcBatchDaoHibernate  extends GenericDaoHibernate<QcBatch, Long> implements QcBatchDao {

    public QcBatchDaoHibernate() {
        super(QcBatch.class);
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
    public int getCount(String lab, String deviceId, int start, int end, String sidx, String sord) {
        String sql = "select count(*)  from QcBatch  c where c.labdepart='"+lab+"' ";
        if(deviceId!=null && !deviceId.isEmpty()){
        	sql += " and c.deviceid like '%"+deviceId+"%' ";
        }
        
        sidx = sidx.equals("") ? "id" : sidx;
        sql +=" order by  " +sidx + " " + sord;
        Query q =  getSession().createQuery(sql);
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
	public List<QcBatch> getDetails(String lab, String deviceId, int start, int end, String sidx, String sord) {
        String sql = "from QcBatch c where c.labdepart='"+lab+"' and c.deviceid like '%"+deviceId+"%'";
        sidx = sidx.equals("") ? "id" : sidx;
        sql +=" order by  " +sidx + " " + sord;
        Query q = getSession().createQuery(sql);
        if(end !=0){
            q.setFirstResult(start);
            q.setMaxResults(end);
        }
        return q.list();
    }

    /**
     * 保存明细
     * @param qcBatchList
     */
    public void saveDetails(List<QcBatch> qcBatchList) {
        Session session = null;
        if(qcBatchList !=null && qcBatchList.size()>0){
            try{
                session = getSession();     //获取session
                session.beginTransaction(); //开启事务
                QcBatch qcBatch = null;
                for(int i = 0; i < qcBatchList.size(); i++){
                    qcBatch = qcBatchList.get(i);
                    session.saveOrUpdate(qcBatch);
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

	@SuppressWarnings("unchecked")
	public List<QcBatch> getByDevice(String deviceid) {
		return getSession().createQuery("from QcBatch where deviceid like '%" + deviceid + "%'").list();
	}
}
