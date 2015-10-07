package com.smart.service.impl.lis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart.dao.lis.ContactDao;
import com.smart.model.lis.ContactInfor;
import com.smart.service.impl.GenericManagerImpl;
import com.smart.service.lis.ContactManager;

@Service("contactManager")
public class ContactManagerImpl extends GenericManagerImpl<ContactInfor, String> implements ContactManager {

	@SuppressWarnings("unused")
	private ContactDao contactDao;

	@Autowired
	public void setContactDao(ContactDao contactDao) {
		this.dao = contactDao;
		this.contactDao = contactDao;
	}
	
}
