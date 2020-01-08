package com.edge.admin.customer.controller;

import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.edge.admin.customer.entity.Customer_QueryVo;
import com.edge.admin.customer.entity.ERP_Customer;
import com.edge.admin.customer.entity.ERP_Customer_Contacts;
import com.edge.admin.customer.service.inter.ContactsService;
import com.edge.admin.customer.service.inter.CustomerService;
import com.edge.admin.post.entity.ERP_DM_Post;
import com.google.gson.Gson;

/**
 * 客户控制跳转层
 * 
 * @author NingCG
 *
 */
@Controller
@RequestMapping(value = "customer")
public class CustomerController {
	@Resource
	private CustomerService customerService;

	@Resource
	private ContactsService contactsService;

	// 跳转至客户列表页面
	@RequestMapping(value = "/initCustomerList.do")
	public String initCustomerList() {
		return "admin/customer/customerList";
	}

	// easyUI 客户列表查询
	@RequestMapping(value = "/customerList.do")
	@ResponseBody
	public String customerList(@RequestParam Integer page, Integer rows, String dwmc, String zcdz, String bgdz,
			String xydm, String fddbr, String khh, String dh) {
		// new出查询对象
		Customer_QueryVo vo = new Customer_QueryVo();
		Gson gson = new Gson();
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		vo.setPage((page - 1) * 10);
		vo.setRows(rows);
		if (dwmc != null && dwmc != "") {
			vo.setDwmc(dwmc.trim());
		}
		if (zcdz != null && zcdz != "") {
			vo.setZcdz(zcdz.trim());
		}
		if (bgdz != null && bgdz != "") {
			vo.setBgdz(bgdz.trim());
		}
		if (xydm != null && xydm != "") {
			vo.setXydm(xydm.trim());
		}
		if (fddbr != null && fddbr != "") {
			vo.setFddbr(fddbr.trim());
		}
		if (khh != null && khh != "") {
			vo.setKhh(khh.trim());
		}
		if (dh != null && dh != "") {
			vo.setDh(dh.trim());
		}
		List<ERP_Customer> cusList = customerService.customerList(vo);
		map.put("total", customerService.customerCount(vo));
		map.put("rows", cusList);
		String json = gson.toJson(map);
		return json.toString();
	}

	// 跳转至客户新增页面
	@RequestMapping(value = "/initSaveCustomer.do")
	public String initSaveCustomer() {
		return "admin/customer/saveCustomer";
	}

	// 新增客户
	@RequestMapping(value = "/saveCustomer.do")
	@ResponseBody
	public String saveCustomer(@RequestBody ERP_Customer customer) {
		JSONObject jsonObject = new JSONObject();
		// 新增客户
		customer.setUnit_Code(this.dwbh());
		customerService.saveCustomer(customer);
		jsonObject.put("flag", true);
		return jsonObject.toString();
	}

	// 生成单位编号
	private String dwbh() {
		// 1.设置项目编号 编号规则为’P’+年、月、日、时、分、秒+六位随机数 如：P20190604172610123456
		// 获取当前系统时间 并获取年月日时分秒
		Calendar now = Calendar.getInstance();
		String year = String.valueOf(now.get(Calendar.YEAR));
		String month = String.valueOf(now.get(Calendar.MONTH) + 1);
		if (now.get(Calendar.MONTH) + 1 <= 9) {
			month = 0 + month;
		}
		String day = String.valueOf(now.get(Calendar.DAY_OF_MONTH));
		if (now.get(Calendar.DAY_OF_MONTH) <= 9) {
			day = 0 + day;
		}
		String Hourse = String.valueOf(now.get(Calendar.HOUR_OF_DAY));
		if (now.get(Calendar.HOUR_OF_DAY) <= 9) {
			Hourse = 0 + Hourse;
		}
		String minute = String.valueOf(now.get(Calendar.MINUTE));
		if (now.get(Calendar.MINUTE) <= 9) {
			minute = 0 + minute;
		}
		String second = String.valueOf(now.get(Calendar.SECOND));
		if (now.get(Calendar.SECOND) <= 9) {
			second = 0 + second;
		}
		// 产生六位随机数
		int a = (int) ((Math.random() * 9 + 1) * 100000);
		String x = String.valueOf(a);
		String time = year + month + day + Hourse + minute + second;
		String bh = "DW" + "-" + time + x;
		return bh;
	}

	// 新增客户联系人集合
	@RequestMapping(value = "/saveKhlxr.do")
	@ResponseBody
	private String addkhlxrs(@RequestBody ERP_Customer_Contacts[] contacts) {
		JSONObject jsonObject = new JSONObject();
		for (ERP_Customer_Contacts c : contacts) {
			// new 出客户联系人对象
			ERP_Customer_Contacts khlxr = new ERP_Customer_Contacts();
			khlxr.setCus_Con_Name(c.getCus_Con_Name());
			khlxr.setCun_Con_Posstation(c.getCun_Con_Posstation());
			khlxr.setCun_Con_Post(c.getCun_Con_Post());
			khlxr.setCun_Con_Dep(c.getCun_Con_Dep());
			khlxr.setCun_Con_Phone(c.getCun_Con_Phone());
			khlxr.setCun_Con_Tel(c.getCun_Con_Tel());
			khlxr.setCun_Con_Email(c.getCun_Con_Email());
			khlxr.setCun_Con_QQ(c.getCun_Con_QQ());
			khlxr.setCun_Con_WeChat(c.getCun_Con_WeChat());
			khlxr.setCun_Con_Remarks(c.getCun_Con_Remarks());
			khlxr.setCustomer(customerService.Maxcustomer_Id());
			khlxr.setCus_Con_Code(this.khlxrbh());
			contactsService.saveContacts(khlxr);
		}
		jsonObject.put("flag", true);
		return jsonObject.toString();

	}

	// 生成客户联系人编号
	private String khlxrbh() {
		// 1.设置项目编号 编号规则为’P’+年、月、日、时、分、秒+六位随机数 如：P20190604172610123456
		// 获取当前系统时间 并获取年月日时分秒
		Calendar now = Calendar.getInstance();
		String year = String.valueOf(now.get(Calendar.YEAR));
		String month = String.valueOf(now.get(Calendar.MONTH) + 1);
		if (now.get(Calendar.MONTH) + 1 <= 9) {
			month = 0 + month;
		}
		String day = String.valueOf(now.get(Calendar.DAY_OF_MONTH));
		if (now.get(Calendar.DAY_OF_MONTH) <= 9) {
			day = 0 + day;
		}
		String Hourse = String.valueOf(now.get(Calendar.HOUR_OF_DAY));
		if (now.get(Calendar.HOUR_OF_DAY) <= 9) {
			Hourse = 0 + Hourse;
		}
		String minute = String.valueOf(now.get(Calendar.MINUTE));
		if (now.get(Calendar.MINUTE) <= 9) {
			minute = 0 + minute;
		}
		String second = String.valueOf(now.get(Calendar.SECOND));
		if (now.get(Calendar.SECOND) <= 9) {
			second = 0 + second;
		}
		// 产生六位随机数
		int a = (int) ((Math.random() * 9 + 1) * 100000);
		String x = String.valueOf(a);
		String time = year + month + day + Hourse + minute + second;
		String bh = "KHLXR" + "-" + time + x;
		return bh;
	}

	// 跳转至客户修改页面
	@RequestMapping(value = "/initEditCustomer.do")
	public String initEditCustomer(@RequestParam Integer customer_Id, Model model) {
		// 根据id获得客户对象
		ERP_Customer customer = customerService.queryCustomerById(customer_Id);
		model.addAttribute("customer", customer);
		// 根据客户获得客户联系人集合
		List<ERP_Customer_Contacts> contactList = contactsService.contactList(customer_Id);
		model.addAttribute("contactList", contactList);
		return "admin/customer/editCustomer";
	}

	// 删除客户联系人
	@RequestMapping(value = "/deleteKhlxrById.do")
	@ResponseBody
	public String deleteKhlxrById(Integer id) {
		JSONObject jsonObject = new JSONObject();
		contactsService.deletContact(id);
		jsonObject.put("flag", true);
		return jsonObject.toString();
	}

	// 编辑客户
	@RequestMapping(value = "/editCunstomer.do")
	@ResponseBody
	public String editCunstomer(@RequestBody ERP_Customer customer) {
		JSONObject jsonObject = new JSONObject();
		customerService.editCustomer(customer);
		jsonObject.put("flag", true);
		return jsonObject.toString();
	}

	// 编辑客户联系人
	@RequestMapping(value = "/editKhlxr.do")
	@ResponseBody
	public String editKhlxr(@RequestBody ERP_Customer_Contacts[] contacts) {
		JSONObject jsonObject = new JSONObject();
		for (ERP_Customer_Contacts c : contacts) {
			// 如果主键不为空则表示修改,否则为新增
			if (c.getCus_Con_Id() != null) {
				contactsService.editContact(c);
			} else {
				contactsService.saveContacts(c);
			}
		}
		jsonObject.put("flag", true);
		return jsonObject.toString();
	}

	// 跳转至客户查看页面
	@RequestMapping(value = "ShowCustomer.do")
	public String ShowCustomer(@RequestParam Integer customer_Id, Model model) {
		// 根据id获得客户对象
		ERP_Customer customer = customerService.queryCustomerById(customer_Id);
		model.addAttribute("customer", customer);
		// 根据客户获得客户联系人集合
		List<ERP_Customer_Contacts> contactList = contactsService.contactList(customer_Id);
		model.addAttribute("contactList", contactList);
		return "admin/customer/ShowCustomer";
	}

	// 删除客户信息(物理删除)
	@SuppressWarnings("null")
	@RequestMapping(value = "/deleteCustomerById.do")
	@ResponseBody
	public String deleteCustomerById(Integer customer_Id) {
		// 若当前客户下存在客户联系人集合则不允许删除,需先删除客户联系人之后才能删除客户
		JSONObject jsonObject = new JSONObject();
		// 根据客户获得客户联系人集合
		List<ERP_Customer_Contacts> contactList = contactsService.contactList(customer_Id);
		if (contactList != null || contactList.size() > 0) {
			jsonObject.put("flag", false);
		} else {
			customerService.deleteCustomer(customer_Id);
			jsonObject.put("flag", true);
		}
		return jsonObject.toString();
	}

	// 批量删除(逻辑删除)
	@SuppressWarnings("null")
	@RequestMapping(value = "/batchDeleteCustomer.do")
	@ResponseBody
	public String batchDeleteCustomer(String ids) {
		JSONObject jsonObject = new JSONObject();
		// 将ids拆分为字符组
		String[] depIds = ids.split(",");
		// 遍历该数组
		for (int i = 0; i < depIds.length; i++) {
			// 若当前客户下存在客户联系人集合则不允许删除,需先删除客户联系人之后才能删除客户
			// 根据客户获得客户联系人集合
			List<ERP_Customer_Contacts> contactList = contactsService.contactList(Integer.parseInt(depIds[i]));
			if (contactList != null || contactList.size() > 0) {
				jsonObject.put("flag", false);
				jsonObject.put("index", i + 1);
			} else {
				customerService.deleteCustomer(Integer.parseInt(depIds[i]));
				jsonObject.put("flag", true);
			}
		}
		return jsonObject.toString();
	}
}
