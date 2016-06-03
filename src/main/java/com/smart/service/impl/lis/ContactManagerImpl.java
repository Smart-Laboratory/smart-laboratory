package com.smart.service.impl.lis;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart.dao.lis.ContactDao;
import com.smart.model.lis.ContactInfor;
import com.smart.service.impl.GenericManagerImpl;
import com.smart.service.lis.ContactManager;

@Service("contactManager")
public class ContactManagerImpl extends GenericManagerImpl<ContactInfor, String> implements ContactManager {

	private ContactDao contactDao;

	@Autowired
	public void setContactDao(ContactDao contactDao) {
		this.dao = contactDao;
		this.contactDao = contactDao;
	}

	public List<ContactInfor> searchContact(String name) {
		return contactDao.searchContact(name);
	}
	
}
