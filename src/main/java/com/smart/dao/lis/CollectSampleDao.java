package com.smart.dao.lis;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.smart.dao.GenericDao;
import com.smart.model.lis.CollectSample;

public interface CollectSampleDao extends GenericDao<CollectSample, Long> {

	/**
	 * 获取某一用户的样本收藏信息
	 * @param username 用户的名称
	 * @return
	 */
	@Transactional
	List<CollectSample> getCollectSample(String username);
	
	/**
	 * 判断样本是否被用户收藏
	 * @param username 用户名称
	 * @param sampleno 样本id
	 * @return
	 */
	@Transactional
	boolean isSampleCollected(String username, String sampleno);
	
	/**
	 * 获取所有的样本收藏信息
	 * @return
	 */
	@Transactional
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
	@Transactional
	List<String> getAllCollectType();

	/**
	 * 获取该分类的所有样本收藏信息
	 * @param type 分类名称，字符串，2个分类之间用逗号隔开
	 * @return
	 */
	@Transactional
	List<CollectSample> getCollectSampleByType(String type);
}
