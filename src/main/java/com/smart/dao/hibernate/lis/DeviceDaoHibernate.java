package com.smart.dao.hibernate.lis;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import com.smart.dao.hibernate.GenericDaoHibernate;
import com.smart.dao.lis.DeviceDao;
import com.smart.model.lis.Device;

import java.util.List;

@Repository("deviceDao")
public class DeviceDaoHibernate extends GenericDaoHibernate<Device, Long> implements DeviceDao {
	
	public DeviceDaoHibernate(){
		super(Device.class);
	}

	/**
	 * 获取仪器信息
	 * @param query
	 * @param type  仪器类别
	 * @param start
	 * @param end
	 * @param sidx
     * @param sord
     * @return
     */
	@SuppressWarnings("unchecked")
	@Override
	public List<Device> getDeviceList(String query,String type,int start, int end, String sidx, String sord) {
		String sql = "  from Device d where 1=1 ";
		if(type != null && !type.equals(""))
			sql += " and  d.type = " +type;
		if(query != null && !query.equals(""))
			sql += " and  d.name like '%" +query +"%'";
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
	 * 获取记录数
	 * @param query
	 * @param type
     * @return
     */
	@Override
	public int getDeviceCount(String query, String type) {
		String sql = "select count(1) cnt from l_device ";
		if(type != null && !type.equals(""))
			sql += " and  d.type = " +type;
		if(query != null && !query.equals(""))
			sql += " and  d.name like '%" +query +"%'";

		Query q =  getSession().createSQLQuery(sql);
		return new Integer(q.uniqueResult() + "");
	}

	/**
	 * 根据获取仪器
	 * @param code  //编号
	 * @return
     */
	@SuppressWarnings("unchecked")
	public Device getDeviceByCode(String code) {
		Query query = getSession().createQuery("from Device d where d.id=:code");
		query.setString("code",code);
		List<Device> list = query.list();
		if(list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public List<Device> getDeviceList(String name){
		return getSession().createQuery("from Device where name like '" + name + "%'  or id like '"+name+"%' order by name,id").list();
	}

	@SuppressWarnings("unchecked")
	public List<Device> getDeviceByLab(String lab) {
		return getSession().createQuery("from Device where lab='" + lab + "' order by id").list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Device> getByIds(String ids){
		return getSession().createQuery("from Device where id in ("+ids+")").list();
	}
}