package com.smart.dao.rule;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.smart.dao.GenericDao;
import com.smart.model.rule.Item;

public interface ItemDao extends GenericDao<Item, Long> {

	@Transactional
    Item exsitItem(Long indexId, String value);

	@Transactional
    List<Item> getByIndexId(Long indexid);
}
