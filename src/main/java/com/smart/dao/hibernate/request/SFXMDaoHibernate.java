package com.smart.dao.hibernate.request;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.smart.dao.hibernate.GenericDaoHibernate;
import com.smart.dao.request.SFXMDao;
import com.smart.model.request.SFXM;

@Repository("sfxmDao")
public class SFXMDaoHibernate extends GenericDaoHibernate<SFXM, Long> implements SFXMDao {

	public SFXMDaoHibernate() {
		super(SFXM.class);
	}

	public int getSFXMCount(String search, String hospitalId) {
		String sql = "select count(*) from gy_sfxm where hospitalid=" + hospitalId;
		if(search != null && !search.isEmpty()) {
			sql = sql + "and (name like '" + search + "%' or english like '" + search + "%' or pinyin like '" + search + "%' or wubi like '" + search + "%'";
		}
		return new Integer(getSession().createSQLQuery(sql).uniqueResult() + "");
	}

	@SuppressWarnings("unchecked")
	public List<SFXM> getPageLIst(String search, String hospitalId, int start, int end) {
		String sql = "from SFXM where hospitalid=" + hospitalId;
		if(search != null && !search.isEmpty()) {
			sql = sql + "and (name like '" + search + "%' or english like '" + search + "%' or pinyin like '" + search + "%' or wubi like '" + search + "%'";
		}
		Query q =  getSession().createQuery(sql);

		if(end != 0){
			q.setFirstResult(start);
			q.setMaxResults(end);
		}
		return  q.list();
	}

	@SuppressWarnings("unchecked")
	public List<SFXM> searchSFXM(String query, Long hospitalid) {
		List<SFXM> list = getSession().createQuery("from SFXM where hospitalid=" + hospitalid + " and (name like '" + query + "%' or pinyin like '" + query + "%' or wubi like '" + query + "%' or english like '" + query + "%')").list();
		if(list.size() > 5) {
			list = list.subList(0, 5);
		}
		return list;
	}

}
