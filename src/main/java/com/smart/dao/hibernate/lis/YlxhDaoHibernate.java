package com.smart.dao.hibernate.lis;

import java.util.ArrayList;
import java.util.List;

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
		return getSession().createQuery("from Ylxh where ksdm in("+labs+") and ylmc like '%"+ylmc+"%'order by profiletest").list();
	}

	@SuppressWarnings("unchecked")
	public List<Ylxh> getSearchData(String text){
		return getSession().createQuery("from Ylxh where ylmc like '%"+text+"%'").list();
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
}
