package com.smart.dao.hibernate.rule;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.smart.dao.hibernate.GenericDaoHibernate;
import com.smart.dao.rule.ItemDao;
import com.smart.model.rule.Item;

@Repository("itemDao")
public class ItemDaoHibernate extends GenericDaoHibernate<Item, Long> implements ItemDao {

	public ItemDaoHibernate() {
		super(Item.class);
	}

	@SuppressWarnings("unchecked")
	public Item exsitItem(Long indexId, String value) {
		
		List<Item> items = getSession().createQuery("from Item where indexId=" + indexId + " and value='" + value + "'").list(); 
		
		if (items == null || items.size() == 0) {
			return null;
		} else {
			return items.get(0);
		}
	}

	@SuppressWarnings("unchecked")
	public List<Item> getByIndexId(Long indexid) {
		return getSession().createQuery("from Item where indexId=" + indexid).list();
	}
}
