package com.smart.service.rule;

import com.smart.model.rule.Item;
import com.smart.service.GenericManager;


public interface ItemManager extends GenericManager<Item, Long> {
	
	/**
	 *  添加知识点并获取id
	 * @param item
	 * @return 新增知识点
	 */
	Item addItem(Item item);
	
	/**
	 *  该知识点是否冲突，包括已存在的情况
	 * @param item
	 * @return
	 */
	boolean isItemConflict(Item item);
	
	/**
	 *  该知识点是否已存在
	 * @param indexId 指标id
	 * @param value
	 * @return
	 */
	Item exsitItem(Long indexId, String value);
	
	/**
	 * 相关知识点
	 * @param id 指标id
	 * @return
	 */
	Item getWithIndex(Long id);
}
