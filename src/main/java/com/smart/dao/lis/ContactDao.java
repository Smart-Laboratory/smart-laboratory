package com.smart.dao.lis;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.smart.dao.GenericDao;
import com.smart.model.lis.ContactInfor;

public interface ContactDao extends GenericDao<ContactInfor, String> {

	@Transactional
	List<ContactInfor> searchContact(String name);

}
