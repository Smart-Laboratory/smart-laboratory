package com.smart.service.impl.rule;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smart.dao.rule.DescriptionDao;
import com.smart.model.rule.Description;
import com.smart.service.impl.GenericManagerImpl;
import com.smart.service.rule.DescriptionManager;

@Service("descriptionManager")
public class DescriptionManagerImpl extends GenericManagerImpl<Description, Long> implements DescriptionManager{

	private DescriptionDao descriptionDao;
	
	@Autowired
	public void setDescriptionDao(DescriptionDao descriptionDao){
		this.dao = descriptionDao;
		this.descriptionDao = descriptionDao;
	}
	
	public List<Description> getDescription(int pageNum, String field, boolean isAsc){
		return descriptionDao.getDescription(pageNum, field, isAsc);
	}
	
	/**
	 *  返回规则数
	 * @return
	 */
	
	public int getDescriptionsCount(){
		return descriptionDao.getDescriptionsCount();
	}
	
	/**
	 *  根据类别，搜索规则
	 * @param category  类别
	 * @return
	 */
	
	public List<Description> getDescriptionsByBagID(Long bagId){
		return descriptionDao.getDescriptionsByBagID(bagId);
	}
	
	/**
	 *  根据类别，分页搜索规则
	 * @param bagId 包ID
	 * @param pageNum
	 * @param field
	 * @param isAsc
	 * @return
	 */
	
	public List<Description> getDescriptionsByBagID(Long bagId, int pageNum, String field, boolean isAsc){
		return descriptionDao.getDescriptionsByBagID(bagId, pageNum, field, isAsc);
	}
	
	/**
	 *  该包下的规则数
	 * @param bagId
	 * @return
	 */
	
	public int getDescriptionsCount(Long bagId){
		return descriptionDao.getDescriptionsCount();
	}
	
	/**
	 *   根据规则的部分名称，模糊搜索匹配的规则
	 * @param ruleName
	 * @return
	 */
	
	public List<Description> getDescriptionsByName(String ruleName){
		return descriptionDao.getDescriptionsByName(ruleName);
	}
	
	/**
	 * 
	 * @param type
	 * @return
	 */
	
	public List<Description> getDescriptionByType(int type){
		return descriptionDao.getDescriptionByType(type);
	}

	
	public List<Description> getDescriptionList(String descriptionIds){
		return descriptionDao.getDescriptionList(descriptionIds);
	}
	
	
	public List<Description> getDescriptionManual(String descriptionIds){
		return descriptionDao.getDescriptionManual(descriptionIds);
	}

	
	public List<Description> getDescriptionByTypes(String type){
		return descriptionDao.getDescriptionByTypes(type);
	}
}
