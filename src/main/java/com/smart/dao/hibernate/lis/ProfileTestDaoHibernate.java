package com.smart.dao.hibernate.lis;

import java.util.List;

import com.smart.dao.hibernate.GenericDaoHibernate;
import com.smart.dao.lis.ProfileTestDao;
import com.smart.model.lis.ProfileTest;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 * Title: ProfileTestDaoHibernate
 * Description:套餐组合
 *
 * @Author:zhou
 * @Date:2016/6/7 15:27
 * @Version:
 */
@Repository("profileTestDao")
public class ProfileTestDaoHibernate extends GenericDaoHibernate<ProfileTest, Long> implements ProfileTestDao {
    public ProfileTestDaoHibernate() {
        super(ProfileTest.class);
    }
    
    @SuppressWarnings("unchecked")
    public List<ProfileTest> getBySection(String lab){
    	List s = null;
    	try {
			s =  getSession().createQuery(
					"from ProfileTest where section='" + lab + "' ").list();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return s;
    }
    @SuppressWarnings("unchecked")
	public List <ProfileTest> getByProfileName(String profileName){
    	return getSession().createQuery("from ProfileTest where profileName='" + profileName + "' ").list();
	}

    /**
     *  获取组合试验
     * @param query
     * @param start
     * @param end
     * @param sidx
     * @param sord
     * @return
     */
    public List<ProfileTest> getProfileTestList(String query, int start, int end, String sidx, String sord){
        String sql = "  from ProfileTest d where 1=1 ";
        if(query != null && !query.equals(""))
            sql += " and  d.profileDescribe like '%" +query +"%'";
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
     * 试验组合记录数
     * @param query
     * @return
     */
    public int getProfileTestSize(String query){
        String sql = "select count(1) cnt from l_profiletest ";
        if(query != null && !query.equals(""))
            sql += " and  d.profileDescribe like '%" +query +"%'";
        Query q =  getSession().createSQLQuery(sql);
        return new Integer(q.uniqueResult() + "");
    }
}
