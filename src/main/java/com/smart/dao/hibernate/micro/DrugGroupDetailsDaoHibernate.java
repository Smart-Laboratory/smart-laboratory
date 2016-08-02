package com.smart.dao.hibernate.micro;

import com.smart.dao.hibernate.GenericDaoHibernate;
import com.smart.dao.micro.DrugGroupDetailsDao;
import com.smart.model.micro.DrugGroupDetails;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Title: .IntelliJ IDEA
 * Description:
 *
 * @Author:zhou
 * @Date:2016/7/8 16:56
 * @Version:
 */
@Repository("drugGroupDetailsDao")
public class DrugGroupDetailsDaoHibernate extends GenericDaoHibernate<DrugGroupDetails, Long> implements DrugGroupDetailsDao {
    public DrugGroupDetailsDaoHibernate() {
        super(DrugGroupDetails.class);
    }

    /**
     * 批量保存明细数据
     * @param drugGroupDetailses
     */
    public void saveDetails(List<DrugGroupDetails> drugGroupDetailses) {
        Session session = null;
        if(drugGroupDetailses !=null && drugGroupDetailses.size()>0){
            try{
                session = getSession();     //获取session
                session.beginTransaction(); //开启事务
                DrugGroupDetails drugGroupDetails = null;
                for(int i = 0; i < drugGroupDetailses.size(); i++){
                    drugGroupDetails = drugGroupDetailses.get(i);
                    session.saveOrUpdate(drugGroupDetails);
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

    public int getDrugDetailsCount(String groupId, int start, int end, String sidx, String sord) {
        String sql = "select count(*)  from DrugGroupDetails  c where c.groupId=:groupId";
        sidx = sidx.equals("") ? "id" : sidx;
        sql +=" order by  " +sidx + " " + sord;
        Query q =  getSession().createQuery(sql);
        q.setString("groupId",groupId);
        return new Integer(q.uniqueResult() + "");
    }

    @SuppressWarnings("unchecked")
	public List<Object[]>  getDrugDetails(String groupId, int start, int end, String sidx, String sord) {
        String sql = "from DrugGroupDetails c,Index i where c.drugId = i.id and i.testClass='抗生素' and c.groupId=:groupId  ";
        sidx = sidx.equals("") ? "id" : sidx;
        sql +=" order by  " +sidx + " " + sord;
        Query q = getSession().createQuery(sql);
        q.setString("groupId",groupId);
        //System.out.println(sql);
        if(end !=0){
            q.setFirstResult(start);
            q.setMaxResults(end);
        }
        return q.list();
    }

    public void removeById(String groupId,String drugId){
        String sql = "delete  DrugGroupDetails d where d.groupId=:groupId and d.drugId=:drugId ";
        Query q = getSession().createQuery(sql);
        q.setString("groupId",groupId);
        q.setString("drugId",drugId);
        q.executeUpdate();
    }

    public void removeByGroupId(String groupId){
        String sql = "delete  DrugGroupDetails d where d.groupId=:groupId  ";
        Query q = getSession().createQuery(sql);
        q.setString("groupId",groupId);
        q.executeUpdate();
    }

    @SuppressWarnings("unchecked")
	public List<DrugGroupDetails> getByGroupId(String groupId) {
        String sql = "from DrugGroupDetails c where   c.groupId=:groupId  ";
        Query q = getSession().createQuery(sql);
        q.setString("groupId",groupId);
        List<DrugGroupDetails> list =  q.list();
        return list;
    }
}
