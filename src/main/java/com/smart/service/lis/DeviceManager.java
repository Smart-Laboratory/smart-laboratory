package com.smart.service.lis;

import com.smart.model.lis.Device;
import com.smart.service.GenericManager;

import java.util.List;

public interface DeviceManager extends GenericManager<Device, Long> {
    List<Device> getDeviceList(String query,String type,int start,int end,String sidx,String sord);
    int getDeviceCount(String query,String type);
    Device getDeviceByCode(String code);
}
