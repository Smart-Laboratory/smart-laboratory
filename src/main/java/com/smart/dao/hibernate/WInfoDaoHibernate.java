package com.smart.dao.hibernate;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.smart.model.pb.WInfo;
import com.smart.dao.WInfoDao;

@Repository("wInfoDao")
public class WInfoDaoHibernate extends GenericDaoHibernate<WInfo, Long> implements WInfoDao {

	public WInfoDaoHibernate() {
		super(WInfo.class);
	}

	@SuppressWarnings("unchecked")
	public List<WInfo> getBySection(String section, String type) {
		if(section=="" || section ==null)
			return getSession().createQuery("from WInfo where type = '"+type+"' order by ord2").list();
		if(section.equals("1300000")) {
			if(type.equals("1")) {
				return getSession().createQuery("from WInfo where ord1>0 order by ord1").list();
			} else if (type.equals("2")) {
				return getSession().createQuery("from WInfo where ord3>0 order by ord3").list();
			} else if (type.equals("3")) {
				return getSession().createQuery("from WInfo where ord4>0 order by ord4").list();
			}else if (type.equals("5")) {
				return getSession().createQuery("from WInfo where ord5>0 order by ord5").list();
			} else if (type.equals("6")) {
				return getSession().createQuery("from WInfo where ord6>0 order by ord6").list();
			}  else if (type.equals("4")) {
				return getSession().createQuery("from WInfo where (type=1 or type=2) and ord2>0 order by ord2 asc").list();
			} else {
				return getSession().createQuery("from WInfo where type=0 order by ord2").list();
			} 
		}
		return getSession().createQuery("from WInfo where section like '%"+section+"%' and type=0 order by ord2").list();
	}

	public WInfo getByName(String name) {
		@SuppressWarnings("unchecked")
		List<WInfo> list = getSession().createQuery("from WInfo where name='"+name+"'").list();
		return list.get(0);
	}

	@SuppressWarnings("unchecked")
	public List<WInfo> getAll(String sidx, String sord) {
		return getSession().createQuery("from WInfo order by " + sidx + " " + sord).list();
	}

	@SuppressWarnings("unchecked")
	public List<WInfo> getBySection(String section) {
		if(section.equals("1300000")){
			return getSession().createQuery("from WInfo order by ord1").list();
		}
		return getSession().createQuery("from WInfo where section like '%"+section+"%' order by ord2").list();
	}
	
	@SuppressWarnings("unchecked")
	public List<WInfo> getBySection(String section, String sidx, String sord) {
		if(section.equals("1300000")){
			return getSession().createQuery("from WInfo order by "+sidx+" "+sord).list();
		}
		return getSession().createQuery("from WInfo where section like '%"+section+"%' order by "+sidx+" "+sord).list();
	}

	@SuppressWarnings("unchecked")
	public List<WInfo> getBySearch(String field, String string){
		String hql = "from WInfo where "+field+" like '"+string+"%'";
		System.out.println(hql);
		return getSession().createQuery(hql).list();
	}
	
}
