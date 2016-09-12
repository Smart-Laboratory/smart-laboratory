package com.smart.dao.lis;

import com.smart.dao.GenericDao;
import com.smart.model.lis.Device;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

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
	@Transactional
    List<Device> getDeviceList(String query, String type, int start, int end, String sidx, String sord);

    /**
     * 根据名称获取设备列表
     * @param name
     * @return
     */
	@Transactional
    List<Device> getDeviceList(String name);
   
	/**
     * 获取记录数
     * @param query
     * @param type
     * @return
     */
	@Transactional
    int getDeviceCount(String query,String type);

    /**
     * 根据编号获取仪器信息
     * @param code  //编号
     * @return
     */
	@Transactional
    Device getDeviceByCode(String code);

	/**
     * 根据实验室部门代号获取仪器信息
     * @param lab  //实验室部门代号
     * @return
     */
	@Transactional
	List<Device> getDeviceByLab(String lab);

	/**
	 * 根据多个id获取device
	 * @param ids
	 * @return
	 */
	@Transactional
	List<Device> getByIds(String ids);
}
