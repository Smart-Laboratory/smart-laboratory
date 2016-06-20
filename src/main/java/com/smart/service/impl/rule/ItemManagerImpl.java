package com.smart.service.impl.rule;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart.dao.rule.ItemDao;
import com.smart.model.rule.Item;
import com.smart.service.rule.ItemManager;
import com.smart.service.impl.GenericManagerImpl;


@Service("itemManager")
public class ItemManagerImpl extends GenericManagerImpl<Item, Long> implements ItemManager {

	private ItemDao itemDao;
	
	@Autowired
	public void setItemDao(ItemDao itemDao) {
		this.dao = itemDao;
		this.itemDao = itemDao;
	}
	
	public Item addItem(Item item) {
		return itemDao.save(item);
	}

	public boolean isItemConflict(Item item) {
		return false;
	}
	
	public Item exsitItem(Long indexId, String value) {
		return itemDao.exsitItem(indexId, value);
	}

	public List<Item> getByIndexId(Long indexid) {
		return itemDao.getByIndexId(indexid);
	}

}
