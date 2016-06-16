package com.smart.dao.rule;

import com.smart.dao.GenericDao;
import com.smart.model.rule.Item;

public interface ItemDao extends GenericDao<Item, Long> {

	public Item getWithIndex(Long id);
	
	public Item exsitItem(Long indexId, String value);
}
