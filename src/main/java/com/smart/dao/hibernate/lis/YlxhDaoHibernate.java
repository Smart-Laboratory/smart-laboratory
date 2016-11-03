package com.smart.dao.hibernate.lis;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.hql.internal.ast.HqlASTFactory;
import org.springframework.stereotype.Repository;

import com.smart.dao.hibernate.GenericDaoHibernate;
import com.smart.dao.lis.YlxhDao;
import com.smart.model.lis.Ylxh;

@Repository("ylxhDao")
public class YlxhDaoHibernate extends GenericDaoHibernate<Ylxh, Long> implements YlxhDao {

	public YlxhDaoHibernate() {
		super(Ylxh.class);
	}

	@SuppressWarnings("unchecked")
	public List<Ylxh> getYlxh() {
		return getSession().createQuery("from Ylxh order by profiletest").list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Ylxh> getTest(String lab){
		String labs = "";
		if(!lab.equals("")){
			for (String s : lab.split(",")) {
				if(!labs.equals("")) labs += ",";
				labs +="'"+s+"'";
			}
		}
		return getSession().createQuery("from Ylxh where ksdm in ("+labs+") order by profiletest").list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Ylxh> getLabofYlmcBylike(String lab,String ylmc){
		String labs = "";
		if(!lab.equals("")){
			for (String s : lab.split(",")) {
				if(!labs.equals("")) labs += ",";
				labs +="'"+s+"'";
			}
		}
		String hql = "";
		if(ylmc==null){
			hql = "from Ylxh where ksdm in("+labs+") and (ylmc like '%"+ylmc+"%') order by profiletest";
		}else{
			hql = "from Ylxh where ksdm in("+labs+") and (ylmc like '%"+ylmc+"%' or ylxh like '"+ylmc+"') order by profiletest";
		}
		return getSession().createQuery(hql).list();
	}

	@SuppressWarnings("unchecked")
	public List<Ylxh> searchData(String text, String lab){
		List<Ylxh> list = getSession().createQuery("from Ylxh where (ylmc like '"+text+"%' or pinyin like '" + text + "%' or wubi like '" + text + "%') and ksdm='" +lab + "'").list();
		if(list.size() > 5) {
			list = list.subList(0, 5);
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public String getRelativeTest(String ylxh) {
		List<String> list = new ArrayList<String>();
		if(ylxh == null || ylxh.isEmpty()) {
			return null;
		}
		if(ylxh.contains("+")) {
			String sql = "select profiletest3 from Ylxh where ylxh in (";
			for (String s : ylxh.split("[+]")) {
				sql += Long.parseLong(s) + ",";
			}
			list = getSession().createQuery(sql.substring(0, sql.length()-1) + ")").list();
		} else if (ylxh.contains("[")) {
			list = getSession().createQuery("select profiletest3 from Ylxh where ylxh=" + Long.parseLong(ylxh.substring(0, ylxh.indexOf("[")))).list();
		} else {
			list = getSession().createQuery("select profiletest3 from Ylxh where ylxh=" + Long.parseLong(ylxh)).list();
		}
		if(list.size() > 0) {
			String str = "";
			for (String s : list) {
				if(s != null) {
					str += s;
				}
			}
			return str;
		}
		return null;
	}

	
	public int getSizeByLab(String query, String lab) {
		String sql = "select count(*)  from Ylxh where ksdm='" + lab + "'";
		if(query!= null && !query.isEmpty()) {
			sql +=" and (ylmc like '" + query + "%' or pinyin like '" + query + "%' or wubi like '" + query + "%')";
		}
		Query q =  getSession().createQuery(sql);
		return new Integer(q.uniqueResult() + "");
	}

	@SuppressWarnings("unchecked")
	public List<Ylxh> getYlxhByLab(String query, String lab, int start, int end, String sidx, String sord) {
		String sql = "from Ylxh where ksdm='" + lab + "'";
		if(query!= null && !query.isEmpty()) {
			sql +=" and (ylmc like '%" + query + "%' or pinyin like '" + query + "%' or wubi like '" + query + "%')";
		}
		sidx = sidx.equals("") ? "ylxh" : sidx;
		sql +=" order by  " +sidx + " " + sord;
		Query q = getSession().createQuery(sql);
		if(end !=0){
			q.setFirstResult(start);
			q.setMaxResults(end);
		}
		return q.list();
	}

	public String getLatestYlxh() {
		return getSession().createSQLQuery("select ylxh from L_YLXHDESCRIBE where rownum=1 ORDER BY ylxh desc ").uniqueResult().toString();
	}

	public void saveAll(List<Ylxh> list) {
		Session s = getSessionFactory().openSession();
		for (Ylxh ylxh : list) {
			s.merge(ylxh);
		}
		s.flush();
		s.close();
	}
}
