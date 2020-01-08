package com.edge.admin.customer.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.edge.admin.customer.entity.ERP_Customer_Contacts;

public interface ContactsDao {
	// 新增客户联系人
	public void saveContacts(ERP_Customer_Contacts contacts);

	// 根据客户主键获得客户联系人集合
	public List<ERP_Customer_Contacts> contactList(@Param("customer") Integer customer);

	// 根据Id删除客户联系人(物理删除)
	public void deletContact(@Param("cus_Con_Id") Integer cus_Con_Id);
	
	//编辑客户联系人
	public void editContact(ERP_Customer_Contacts contacts);
}
