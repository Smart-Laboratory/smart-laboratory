package com.smart.service.lis;

import java.util.List;

import com.smart.model.lis.ContactInfor;
import com.smart.service.GenericManager;

public interface ContactManager extends GenericManager<ContactInfor, String> {

	List<ContactInfor> searchContact(String name);

}
