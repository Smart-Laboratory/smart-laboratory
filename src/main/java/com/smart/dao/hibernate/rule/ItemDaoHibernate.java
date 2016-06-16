package com.smart.dao.hibernate.rule;

import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.smart.dao.hibernate.GenericDaoHibernate;
import com.smart.dao.rule.ItemDao;
import com.smart.model.rule.Item;

@Repository("itemDao")
public class ItemDaoHibernate extends GenericDaoHibernate<Item, Long> implements ItemDao {

	public ItemDaoHibernate() {
		super(Item.class);
	}

	public Item getWithIndex(Long id) {
		Session sses = getSession();
		Item item = (Item) sses.createQuery("from Item where id=" + id).list().get(0);
		item.getIndex().getIndexId();
		sses.close();
		return item;
	}

	@SuppressWarnings("unchecked")
	public Item exsitItem(Long indexId, String value) {
		
		List<Item> items = getSession().createQuery("from Item where index.id=" + indexId + " and value='" + value + "'").list(); 
		
		if (items == null || items.size() == 0) {
			return null;
		} else {
			return items.get(0);
		}
	}
}
