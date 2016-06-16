package com.smart.dao.lis;

import java.util.List;

import com.smart.dao.GenericDao;
import com.smart.model.lis.ContactInfor;

public interface ContactDao extends GenericDao<ContactInfor, String> {

	List<ContactInfor> searchContact(String name);

}
