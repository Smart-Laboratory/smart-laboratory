package com.smart.dao.lis;

import com.smart.dao.GenericDao;
import com.smart.model.lis.Device;

import java.util.List;

public interface DeviceDao extends GenericDao<Device, Long> {
    /**
     * 获取仪器列表
     * @param query
     * @param type  仪器类别
     * @param start
     * @param end
     * @param sidx
     * @param sord
     * @return
     */
    List<Device> getDeviceList(String query, String type, int start, int end, String sidx, String sord);

    /**
     * 根据名称获取设备列表
     * @param name
     * @return
     */
    List<Device> getDeviceList(String name);
    /**
     * 获取记录数
     * @param query
     * @param type
     * @return
     */
    int getDeviceCount(String query,String type);


    /**
     * 根据编号获取仪器信息
     * @param code  //编号
     * @return
     */
    Device getDeviceByCode(String code);


}
