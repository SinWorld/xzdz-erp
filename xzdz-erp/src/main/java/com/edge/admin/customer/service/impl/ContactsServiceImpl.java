package com.edge.admin.customer.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.edge.admin.customer.dao.ContactsDao;
import com.edge.admin.customer.entity.ERP_Customer_Contacts;
import com.edge.admin.customer.service.inter.ContactsService;

/**
 * 客户联系人业务逻辑层
 * 
 * @author NingCG
 *
 */
@Service
public class ContactsServiceImpl implements ContactsService {

	@Resource
	private ContactsDao contactsDao;

	// 新增客户联系人
	public void saveContacts(ERP_Customer_Contacts contacts) {
		contactsDao.saveContacts(contacts);
	}

	// 根据客户获得客户联系人集合
	public List<ERP_Customer_Contacts> contactList(Integer customer) {
		return contactsDao.contactList(customer);
	}

	// 根据Id删除客户联系人(物理删除)
	public void deletContact(Integer cus_Con_Id) {
		contactsDao.deletContact(cus_Con_Id);
	}

	// 编辑客户联系人
	public void editContact(ERP_Customer_Contacts contacts) {
		contactsDao.editContact(contacts);
	}

}
