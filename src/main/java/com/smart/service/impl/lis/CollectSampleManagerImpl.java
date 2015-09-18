package com.smart.service.impl.lis;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart.dao.lis.CollectSampleDao;
import com.smart.model.lis.CollectSample;
import com.smart.service.impl.GenericManagerImpl;
import com.smart.service.lis.CollectSampleManager;

@Service("collectSampleManager")
public class CollectSampleManagerImpl extends GenericManagerImpl<CollectSample, Long> implements CollectSampleManager{
	
	private CollectSampleDao collectSampleDao;

	@Autowired
	public void setCollectSampleDao(CollectSampleDao collectSampleDao){
		this.dao = collectSampleDao;
		this.collectSampleDao = collectSampleDao;
	}
	
    public List<CollectSample> getCollectSample(String username){
    	return collectSampleDao.getCollectSample(username);
    }
	
	public void collectSample(CollectSample cs){
		collectSampleDao.save(cs);
	}
	
	
	public boolean isSampleCollected(String username, String sampleno){
		return collectSampleDao.isSampleCollected(username, sampleno);
	}
	
	/**
	 * 获取所有的样本收藏信息
	 * @return
	 */
	public List<CollectSample> getAllCollectSample(){
		return collectSampleDao.getAllCollectSample();
	}

	/**
	 * 取消收藏
	 * @param collector 收藏者id
	 * @param sampleno 样本id
	 * @return
	 */
	public void removeCollectSample(String collector, String sampleno){
		collectSampleDao.removeCollectSample(collector, sampleno);
	}

	/**
	 * 获取样本收藏信息
	 * @param name 病案名称
	 * @return
	 */
	public List<CollectSample> getCollectSampleByName(String name){
		return collectSampleDao.getCollectSampleByName(name);
	}

	/**
	 * 获取样本收藏信息的所有分类
	 * @return
	 */
	public List<String> getAllCollectType(){
		return collectSampleDao.getAllCollectType();
	}

	/**
	 * 获取该分类的所有样本收藏信息
	 * @param type 分类名称，字符串，2个分类之间用逗号隔开
	 * @return
	 */
	public List<CollectSample> getCollectSampleByType(String type){
		return collectSampleDao.getCollectSampleByType(type);
	}
}
