package com.smart.service.impl.lis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart.dao.lis.DeviceDao;
import com.smart.model.lis.Device;
import com.smart.service.impl.GenericManagerImpl;
import com.smart.service.lis.DeviceManager;

import java.util.List;

@Service("deviceManager")
public class DeviceManagerImpl extends GenericManagerImpl<Device, Long> implements DeviceManager {
	
	@SuppressWarnings("unused")
	private DeviceDao deviceDao;

	@Autowired
	public void setDeviceDao(DeviceDao deviceDao) {
		this.dao = deviceDao;
		this.deviceDao = deviceDao;
	}

	@Override
	public List<Device> getDeviceList(String query, String type, int start, int end, String sidx, String sord) {
		return deviceDao.getDeviceList(query,type,start,end,sidx,sord);
	}

	@Override
	public int getDeviceCount(String query, String type) {
		return deviceDao.getDeviceCount(query,type);
	}

	@Override
	public Device getDeviceByCode(String code) {
		return deviceDao.getDeviceByCode(code);
	}

	@Override
	public List<Device> getDeviceList(String name) {
		return deviceDao.getDeviceList(name);
	}
}
