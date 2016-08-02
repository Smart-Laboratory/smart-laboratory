package com.smart.dao.hibernate.micro;

import com.smart.dao.hibernate.GenericDaoHibernate;
import com.smart.dao.micro.MicroItemInfoDao;
import com.smart.model.micro.MicroItemInfo;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Title: .IntelliJ IDEA
 * Description:
 *
 * @Author:zhou
 * @Date:2016/7/6 9:01
 * @Version:
 */
@Repository("microItemDao")
public class MicroItemInfoDaoHibernate extends GenericDaoHibernate<MicroItemInfo, Long> implements MicroItemInfoDao {
    private static final String CULTURERESULT="培养结果";
    public MicroItemInfoDaoHibernate() {
        super(MicroItemInfo.class);
    }

    /**
     *  获取培养结果
     * @param id
     * @return
     */
    public MicroItemInfo getCultureResult(String id){
        return getMicroItemInfo(CULTURERESULT,id);
    }

    public List<MicroItemInfo> getCultureResult(String query, int start, int end, String sidx, String sord){
        return getMicroItemInfos(CULTURERESULT,query,start,end,sidx,sord);
    }

    public int getCultureCount(String query, int start, int end, String sidx, String sord){
        return getMicroItemInfosCount(CULTURERESULT,query,start,end,sidx,sord);
    }

    /**
     *
     * @param query
     * @param className  类别
     * @param start
     * @param end
     * @param sidx
     * @param sord
     */
    public int getMicroItemInfosCount(String className,String query, int start, int end, String sidx, String sord){
        String sql = "select count(*)  from MicroItemInfo  where 1=1 and className=:className";
        if(query != null && !query.equals(""))
            sql += " and name like:name" ;
        sidx = sidx.equals("") ? "id" : sidx;
        sql +=" order by  " +sidx + " " + sord;
        Query q =  getSession().createQuery(sql);
        q.setString("className",className);
        q.setString("name","%" + query + "%");
        return new Integer(q.uniqueResult() + "");
    }

    @SuppressWarnings("unchecked")
	public List<MicroItemInfo> getMicroItemInfos(String className, String query, int start, int end, String sidx, String sord) {
        String sql = "from MicroItemInfo  where className=:className";
        if(query != null && !query.equals(""))
            sql += " and name like:name" ;
        sidx = sidx.equals("") ? "id" : sidx;
        sql +=" order by  " +sidx + " " + sord;
        Query q = getSession().createQuery(sql);
        q.setString("className",className);
        q.setString("name","%" + query + "%");
        //System.out.println(sql);
        if(end !=0){
            q.setFirstResult(start);
            q.setMaxResults(end);
        }
        return q.list();
    }

    @SuppressWarnings("unchecked")
	public MicroItemInfo getMicroItemInfo(String className,Long id) {
        String sql = "from MicroItemInfo where id=:id and className=:className";
        Query query = getSession().createQuery(sql);
        query.setLong("id",id);
        query.setString("className",className);
        List<MicroItemInfo> microItemInfos  = query.list();
        if (microItemInfos == null || microItemInfos.isEmpty()) {
            return null;
        } else {
            return microItemInfos.get(0);
        }
    }

    @SuppressWarnings("unchecked")
	public MicroItemInfo getMicroItemInfo(String className,String indexId) {
        String sql = "from MicroItemInfo where indexId=:indexId and className=:className";
        Query query = getSession().createQuery(sql);
        query.setString("indexId",indexId);
        query.setString("className",className);
        List<MicroItemInfo> microItemInfos  = query.list();
        if (microItemInfos == null || microItemInfos.isEmpty()) {
            return null;
        } else {
            return microItemInfos.get(0);
        }
    }
}


