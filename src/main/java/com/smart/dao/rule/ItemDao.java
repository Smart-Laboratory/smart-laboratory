package com.smart.dao.rule;

import org.springframework.transaction.annotation.Transactional;

import com.smart.dao.GenericDao;
import com.smart.model.rule.Item;

public interface ItemDao extends GenericDao<Item, Long> {

	@Transactional
	public Item getWithIndex(Long id);
	
	@Transactional
	public Item exsitItem(Long indexId, String value);
}
