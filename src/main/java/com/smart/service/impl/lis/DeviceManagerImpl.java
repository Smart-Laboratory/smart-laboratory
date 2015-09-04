package com.smart.service.impl.lis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart.dao.lis.DeviceDao;
import com.smart.model.lis.Device;
import com.smart.service.impl.GenericManagerImpl;
import com.smart.service.lis.DeviceManager;

@Service("deviceManager")
public class DeviceManagerImpl extends GenericManagerImpl<Device, Long> implements DeviceManager {
	
	private DeviceDao deviceDao;

	@Autowired
	public void setDeviceDao(DeviceDao deviceDao) {
		this.dao = deviceDao;
		this.deviceDao = deviceDao;
	}

}
