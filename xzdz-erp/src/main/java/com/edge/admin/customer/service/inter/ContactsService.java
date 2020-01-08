package com.edge.admin.customer.service.inter;

import java.util.List;

import com.edge.admin.customer.entity.ERP_Customer_Contacts;

public interface ContactsService {
	// 新增客户联系人
	public void saveContacts(ERP_Customer_Contacts contacts);

	// 根据客户主键获得客户联系人集合
	public List<ERP_Customer_Contacts> contactList(Integer customer);

	// 根据Id删除客户联系人(物理删除)
	public void deletContact(Integer cus_Con_Id);

	// 编辑客户联系人
	public void editContact(ERP_Customer_Contacts contacts);
}
