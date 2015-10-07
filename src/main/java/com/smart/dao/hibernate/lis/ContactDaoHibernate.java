package com.smart.dao.hibernate.lis;

import org.springframework.stereotype.Repository;

import com.smart.dao.hibernate.GenericDaoHibernate;
import com.smart.dao.lis.ContactDao;
import com.smart.model.lis.ContactInfor;

@Repository("contactInforDao")
public class ContactDaoHibernate extends GenericDaoHibernate<ContactInfor, String> implements ContactDao {

	public ContactDaoHibernate() {
		super(ContactInfor.class);
	}

}
