package com.smart.dao.hibernate.lis;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.smart.dao.hibernate.GenericDaoHibernate;
import com.smart.dao.lis.ContactDao;
import com.smart.model.lis.ContactInfor;

@Repository("contactInforDao")
public class ContactDaoHibernate extends GenericDaoHibernate<ContactInfor, String> implements ContactDao {

	public ContactDaoHibernate() {
		super(ContactInfor.class);
	}

	@SuppressWarnings("unchecked")
	public List<ContactInfor> searchContact(String name) {
		List<ContactInfor> list = getSession().createQuery("from ContactInfor where NAME like '%"+name+"%' or WORKID like '%" + name + "%' order by WORKID,NAME").list();
	    if(list.size() > 5) {
	    	list = list.subList(0, 5);
	    }
		return list;
	}

}
