package com.smart.service.lis;

import java.util.List;

import com.smart.model.lis.CollectSample;
import com.smart.service.GenericManager;

public interface CollectSampleManager extends GenericManager<CollectSample, Long>{
	

	/**
	 * 获取某一用户的样本收藏信息
	 * @param username 用户的名称
	 * @return
	 */
	List<CollectSample> getCollectSample(String username);
	
	/**
	 * 收藏样本
	 * @param cs 一条样本收藏信息
	 * @return
	 */
	void collectSample(CollectSample cs);
	
	/**
	 * 判断样本是否被用户收藏
	 * @param username 用户名称
	 * @param sampleno 样本id
	 * @return
	 */
	boolean isSampleCollected(String username, String sampleno);
	
	/**
	 * 获取所有的样本收藏信息
	 * @return
	 */
	List<CollectSample> getAllCollectSample();

	/**
	 * 取消收藏
	 * @param collector 收藏者id
	 * @param sampleno 样本id
	 * @return
	 */
	void removeCollectSample(String collector, String sampleno);

	/**
	 * 获取样本收藏信息
	 * @param name 病案名称
	 * @return
	 */
	List<CollectSample> getCollectSampleByName(String name);

	/**
	 * 获取样本收藏信息的所有分类
	 * @return
	 */
	List<String> getAllCollectType();

	/**
	 * 获取该分类的所有样本收藏信息
	 * @param type 分类名称，字符串，2个分类之间用逗号隔开
	 * @return
	 */
	List<CollectSample> getCollectSampleByType(String type);
}
