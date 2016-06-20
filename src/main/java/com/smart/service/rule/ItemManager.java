package com.smart.service.rule;

import java.util.List;

import com.smart.model.rule.Item;
import com.smart.service.GenericManager;


public interface ItemManager extends GenericManager<Item, Long> {
	
	
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
	 *  通过indexid获取Item
	 * @param indexId 指标id
	 * @param value
	 * @return
	 */
	List<Item> getByIndexId(Long indexid);
	
}
