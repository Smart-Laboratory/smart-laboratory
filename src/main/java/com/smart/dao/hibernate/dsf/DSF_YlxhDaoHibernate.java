package com.smart.dao.hibernate.dsf;

import com.smart.dao.dsf.DSF_YlxhDao;
import com.smart.dao.hibernate.GenericDaoHibernate;
import com.smart.model.dsf.DSF_ylxh;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository("dsf_ylxhDao")
public class DSF_YlxhDaoHibernate extends GenericDaoHibernate<DSF_ylxh, Long> implements DSF_YlxhDao {

	public DSF_YlxhDaoHibernate() {
		super(DSF_ylxh.class);
	}

	public List<DSF_ylxh> getYlxh() {
		return getSession().createQuery("from DSF_ylxh order by profiletest").list();
	}
	
	public List<DSF_ylxh> getTest(String lab){
		String labs = "";
		if(!lab.equals("")){
			for (String s : lab.split(",")) {
				if(!labs.equals("")) labs += ",";
				labs +="'"+s+"'";
			}
		}
		return getSession().createQuery("from DSF_ylxh where ksdm in ("+labs+") order by profiletest").list();
	}
	
	public List<DSF_ylxh> getLabofYlmcBylike(String lab,String ylmc){
		String labs = "";
		if(!lab.equals("")){
			for (String s : lab.split(",")) {
				if(!labs.equals("")) labs += ",";
				labs +="'"+s+"'";
			}
		}
		return getSession().createQuery("from DSF_ylxh where ksdm in("+labs+") and ylmc like '%"+ylmc+"%'order by profiletest").list();
	}

	public List<DSF_ylxh> getSearchData(String text){
		return getSession().createQuery("from DSF_ylxh where ylmc like '%"+text+"%'").list();
	}

	public String getRelativeTest(String ylxh) {
		List<String> list = new ArrayList<String>();
		if(ylxh == null || ylxh.isEmpty()) {
			return null;
		}
		if(ylxh.contains("+")) {
			String sql = "select profiletest3 from DSF_ylxh where ylxh in (";
			for (String s : ylxh.split("[+]")) {
				sql += Long.parseLong(s) + ",";
			}
			list = getSession().createQuery(sql.substring(0, sql.length()-1) + ")").list();
		} else if (ylxh.contains("[")) {
			list = getSession().createQuery("select profiletest3 from DSF_ylxh where ylxh=" + Long.parseLong(ylxh.substring(0, ylxh.indexOf("[")))).list();
		} else {
			list = getSession().createQuery("select profiletest3 from DSF_ylxh where ylxh=" + Long.parseLong(ylxh)).list();
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
		String sql = "select count(*)  from DSF_ylxh where ksdm='" + lab + "'";
		if(query!= null && !query.isEmpty()) {
			sql +=" and (ylmc like '" + query + "%' or pinyin like '" + query + "%' or wubi like '" + query + "%')";
		}
		Query q =  getSession().createQuery(sql);
		return new Integer(q.uniqueResult() + "");
	}

	public List<DSF_ylxh> getYlxhByLab(String query, String lab, int start, int end, String sidx, String sord,String customerid) {
		String sql = "from DSF_ylxh where ksdm='" + lab + "' and customerid='"+customerid+"' ";
		if(query!= null && !query.isEmpty()) {
			sql +=" and (ylmc like '" + query + "%' or pinyin like '" + query + "%' or wubi like '" + query + "%')";
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
}
