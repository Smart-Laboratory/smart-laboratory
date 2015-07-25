package com.smart.dao.hibernate.lis;

import org.springframework.stereotype.Repository;

import com.smart.dao.hibernate.GenericDaoHibernate;
import com.smart.dao.lis.DeviceDao;
import com.smart.model.lis.Device;

@Repository("deviceDao")
public class DeviceDaoHibernate extends GenericDaoHibernate<Device, Long> implements DeviceDao {
	
	public DeviceDaoHibernate(){
		super(Device.class);
	}

}