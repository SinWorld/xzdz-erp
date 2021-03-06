package com.edge.admin.customer.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.omg.CORBA.SystemException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.edge.admin.customer.entity.Customer_QueryVo;
import com.edge.admin.customer.entity.ERP_Customer;
import com.edge.admin.customer.entity.ERP_Customer_Contacts;
import com.edge.admin.customer.service.inter.ContactsService;
import com.edge.admin.customer.service.inter.CustomerService;
import com.edge.admin.user.entity.ERP_User;
import com.edge.currency.enclosure.entity.Enclosure;
import com.edge.currency.enclosure.service.inter.EnclosureService;
import com.edge.utils.ExcelUtils;
import com.google.gson.Gson;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

/**
 * 客户控制跳转层
 * 
 * @author NingCG
 *
 */
@Controller
@RequestMapping(value = "customer")
public class CustomerController {

	public static final String DBDRIVER = "oracle.jdbc.driver.OracleDriver";// 定义MySQL的数据库驱动程序
	public static final String DBURL = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";// 定义MySQL的数据库的连接地址
	public static final String DBUSER = "erp";// MySQL数据库的连接用户名
	public static final String DBPASS = "xzdz_erp";// MySQL数据库的连接用密码

	@Resource
	private CustomerService customerService;

	@Resource
	private ContactsService contactsService;

	@Resource
	private EnclosureService enclosureService;

	// 跳转至客户列表页面
	@RequestMapping(value = "/initCustomerList.do")
	public String initCustomerList() {
		return "admin/customer/customerList";
	}

	// 客户列表查询
	@RequestMapping(value = "/customerList.do")
	@ResponseBody
	public String customerList(@RequestParam Integer page, Integer rows, String dwmc, String zcdz, String bgdz,
			String xydm, String fddbr, String khh, String dh) {
		// new出查询对象
		Customer_QueryVo vo = new Customer_QueryVo();
		Gson gson = new Gson();
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		vo.setPage((page - 1) * rows + 1);
		vo.setRows(page * rows);
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
	public String saveCustomer(@RequestBody ERP_Customer customer, HttpServletRequest request) {
		JSONObject jsonObject = new JSONObject();
		// 新增客户
		customer.setUnit_Code(this.dwbh());
		customerService.saveCustomer(customer);
		this.addXshtFj(customer.getFjsx(), request);
		jsonObject.put("flag", true);
		return jsonObject.toString();
	}

	// 将上传的附件写入数据库
	private void addXshtFj(String fjsx, HttpServletRequest request) {
		HttpSession session = request.getSession();
		ERP_User user = (ERP_User) session.getAttribute("user");
		List<String> list = new ArrayList<String>();
		// 将fjsx进行字符截取
		if (fjsx.hashCode() != 0) {
			String fjvalue = fjsx.substring(1, fjsx.length());
			list.add(fjvalue);
			String value = list.toString();
			Date date = new Date();
			// 根据客户主键获得客户对象
			ERP_Customer customer = customerService.queryCustomerById(customerService.Maxcustomer_Id());
			String key = customer.getClass().getSimpleName();
			// 拼接业务数据主键
			String objId = key + "." + String.valueOf(customerService.Maxcustomer_Id());
			// 将字符串转换为json数组
			JSONArray jsonArray = JSONArray.parseArray(value);
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject obj = jsonArray.getJSONObject(i);
				String localFileName = (String) obj.get("localFileName");// 上传文件名
				String path = (String) obj.get("path");// 上传文件地址
				String fileName = (String) obj.get("fileName");// 上传文件真实名
				// new 出附件对象
				Enclosure fj = new Enclosure();
				fj.setCUNCHUWJM(localFileName);// 上传文件名
				fj.setSHANGCHUANDZ(path);// 上传文件地址
				fj.setREALWJM(fileName);// 上传文件真实名称
				fj.setSHANGCHUANRQ(date);// 上传文件日期
				fj.setSHANGCHUANYHDM(user.getUserId());// 上传用户主键
				fj.setOBJDM(objId);// 上传业务数据主键
				enclosureService.saveEnclosure(fj);// 添加附件
			}
		}
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
	private String addkhlxrs(@RequestBody ERP_Customer_Contacts[] contacts, HttpServletRequest request) {
		JSONObject jsonObject = new JSONObject();
		HttpSession session = request.getSession();
		ERP_User user = (ERP_User) session.getAttribute("user");
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
		jsonObject.put("userId", user.getUserId());
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
	public String editCunstomer(@RequestBody ERP_Customer customer, HttpServletRequest request) {
		JSONObject jsonObject = new JSONObject();
		customerService.editCustomer(customer);
		this.editXshtFj(customer.getFjsx(), request, customer);
		jsonObject.put("flag", true);
		return jsonObject.toString();
	}

	// 编辑客户联系人
	@RequestMapping(value = "/editKhlxr.do")
	@ResponseBody
	public String editKhlxr(@RequestBody ERP_Customer_Contacts[] contacts, HttpServletRequest request) {
		JSONObject jsonObject = new JSONObject();
		HttpSession session = request.getSession();
		ERP_User user = (ERP_User) session.getAttribute("user");
		for (ERP_Customer_Contacts c : contacts) {
			// 如果主键不为空则表示修改,否则为新增
			if (c.getCus_Con_Id() != null) {
				contactsService.editContact(c);
			} else {
				contactsService.saveContacts(c);
			}
		}
		jsonObject.put("flag", true);
		jsonObject.put("userId", user.getUserId());
		return jsonObject.toString();
	}

	// 跳转至客户查看页面
	@RequestMapping(value = "ShowCustomer.do")
	public String ShowCustomer(@RequestParam Integer customer_Id, Model model) {
		// 根据id获得客户对象
		ERP_Customer customer = customerService.queryCustomerById(customer_Id);
		String OBJDM = customer.getClass().getSimpleName() + "." + String.valueOf(customer_Id);
		model.addAttribute("customer", customer);
		// 根据客户获得客户联系人集合
		List<ERP_Customer_Contacts> contactList = contactsService.contactList(customer_Id);
		model.addAttribute("contactList", contactList);
		model.addAttribute("OBJDM", OBJDM);
		return "admin/customer/ShowCustomer";
	}

	// 删除客户信息(物理删除)
	@RequestMapping(value = "/deleteCustomerById.do")
	@ResponseBody
	public String deleteCustomerById(Integer customer_Id) {
		// 若当前客户下存在客户联系人集合则不允许删除,需先删除客户联系人之后才能删除客户
		JSONObject jsonObject = new JSONObject();
		// 根据客户获得客户联系人集合
		List<ERP_Customer_Contacts> contactList = contactsService.contactList(customer_Id);
		if (contactList.size() != 0) {
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
			if (contactList.size() > 0) {
				jsonObject.put("flag", false);
				jsonObject.put("index", i + 1);
			} else {
				customerService.deleteCustomer(Integer.parseInt(depIds[i]));
				jsonObject.put("flag", true);
			}
		}
		return jsonObject.toString();
	}

	// 跳转至文件导入页面
	@RequestMapping(value = "/initFileImport.do")
	public String initFileImport() {
		return "admin/customer/fileImport";
	}

	// 导入Excel
	@RequestMapping({ "/importExcel.do" })
	@ResponseBody
	public String importExcel(@RequestParam(value = "file", required = false) MultipartFile file,
			HttpServletRequest request, Model model)
			throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		JSONObject jsonObject = new JSONObject();
		ERP_User user = null;
		try {
			request.setCharacterEncoding("UTF-8");
			HttpSession session = request.getSession();
			user = (ERP_User) session.getAttribute("user");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String loadExcel = null;
		loadExcel = loadExcel(file.getInputStream());
		jsonObject.put("userId", user.getUserId());
		jsonObject.put("result", loadExcel);
		return jsonObject.toString();
	}

	public String loadExcel(InputStream is)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		Connection con = null;// 定义一个MySQL的连接对象
		PreparedStatement pstmt = null;
		String index = null;
		Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();// MySQL驱动
		con = (Connection) DriverManager.getConnection(DBURL, DBUSER, DBPASS);// 连接本地的MySQL
		con.setAutoCommit(false);// 设置不让事务自动提交
		String sql = "insert into erp_customerInfor(CUSTOMER_ID,UNIT_CODE,UNIT_NAME,REGISTERED_ADDRESS,OFFICE_ADDRESS,"
				+ "UNIFIED_CODE,LEGAL_PERSON,OPENING_BANK,ACCOUNT_NUMBER,DUTY_PARAGRAPH,TELPHONE,FAX,WTDLR,REMARKS) "
				+ "values(seq_customer_Id.nextval,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		try {
			pstmt = con.prepareStatement(sql);
			Workbook wb = null;
			try {
				wb = Workbook.getWorkbook(is);
			} catch (BiffException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			Sheet sheet = wb.getSheet(0);
			int rows = sheet.getRows();
			for (int i = 1; i < rows; i++) {
				index = String.valueOf(i);
				List oneData = new ArrayList();
				Cell[] cells = sheet.getRow(i);
				for (int j = 0; j < cells.length; j++) {
					oneData.add(cells[j].getContents());
				}
				if (oneData.size() > 0) {
					pstmt.setString(1, this.dwbh());
					pstmt.setString(2, String.valueOf(oneData.get(1)));
					pstmt.setString(3, String.valueOf(oneData.get(2)));
					pstmt.setString(4, String.valueOf(oneData.get(3)));
					pstmt.setString(5, String.valueOf(oneData.get(4)));
					pstmt.setString(6, String.valueOf(oneData.get(5)));
					pstmt.setString(7, String.valueOf(oneData.get(6)));
					pstmt.setString(8, String.valueOf(oneData.get(7)));
					pstmt.setString(9, String.valueOf(oneData.get(8)));
					pstmt.setString(10, String.valueOf(oneData.get(9)));
					pstmt.setString(11, String.valueOf(oneData.get(10)));
					pstmt.setString(12, String.valueOf(oneData.get(11)));
					pstmt.setString(13, String.valueOf(oneData.get(12)));
					pstmt.executeUpdate();
				}
			}
		} catch (Exception e1) {
			con.rollback();
			return index;
		} finally {
			con.commit();
			con.close();
		}
		return "0";
	}

	// 导出Excel
	@RequestMapping({ "/exportExcel.do" })
	public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("GBK");
		writeExcel(response);
	}

	public static void writeExcel(HttpServletResponse response) throws Exception {
		String[] titles = { "序号", "单位名称", "注册地址", "办公地址", "社会统一信用代码", "法定代表人", "开户行", "账号", "税号", "电话", "传真", "委托代理人",
				"备注" };
		String sheetTitle = "客户信息";
		ExcelUtils eeu = new ExcelUtils();
		HSSFWorkbook workbook = new HSSFWorkbook();
		OutputStream os = response.getOutputStream();
		List apkDate = getApkDate(); // 取出数据
		sheetTitle = new String(sheetTitle.getBytes("gb2312"), "iso8859-1");
		response.reset();
		response.setContentType("application/msexcel");
		response.setHeader("Content-disposition", "attachment; filename=" + sheetTitle + ".xls");
		eeu.exportExcel(workbook, 0, "客户信息", titles, apkDate, os);
		workbook.write(os);
		os.close();
	}

	private static List<List<String>> getApkDate() throws SQLException, SystemException, InstantiationException,
			IllegalAccessException, ClassNotFoundException {
		Connection con = null;// 定义一个MySQL的连接对象
		Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();// MySQL驱动
		con = (Connection) DriverManager.getConnection(DBURL, DBUSER, DBPASS);// 连接本地的MySQL
		con.setAutoCommit(false);// 设置不让事务自动提交
		Statement stmt;// 创建声明
		stmt = con.createStatement();
		String sql = "select rownum," + "c.unit_name," + "c.registered_address," + "c.office_address,"
				+ "c.unified_code," + "c.legal_person," + "c.opening_bank," + "c.account_number," + "c.duty_paragraph,"
				+ "c.telphone," + "c.fax," + "c.wtdlr," + "c.remarks " + "from erp_customerinfor c";
		ResultSet res = stmt.executeQuery(sql);
		ResultSetMetaData rsmd = res.getMetaData();
		List datelist = new ArrayList();
		int colCnt = rsmd.getColumnCount();
		while (res.next()) {
			List list = new ArrayList();
			for (int j = 1; j < colCnt + 1; j++) {
				String colName = rsmd.getColumnName(j);
				String colValue = res.getString(colName);
				list.add(colValue);
			}
			datelist.add(list);
		}
		return datelist;
	}

	// ajax在编辑页面显示附件
	@RequestMapping(value = "/pageLoadFj.do")
	@ResponseBody
	public String pageLoadFj(Integer row_Id, HttpServletResponse response, HttpServletRequest request) {
		JSONArray jsonArray = new JSONArray();
		ERP_Customer customer = customerService.queryCustomerById(row_Id);
		// 获得objId
		String objId = customer.getClass().getSimpleName() + "." + String.valueOf(row_Id);
		// 根据objId获得附件集合
		List<Enclosure> enclosureList = enclosureService.enclosureList(objId);
		// 遍历该集合
		for (Enclosure e : enclosureList) {
			String filePath = request.getSession().getServletContext()// D:\guildFile\adviceNote_1493028164967_Jellyfish.jpg
					.getRealPath("/fj/" + e.getSHANGCHUANDZ() + "/" + e.getREALWJM());
			File file = new File(filePath.trim());
			String fileName = file.getName();
			DecimalFormat df = new DecimalFormat("#.00");
			String fileSizeString = df.format((double) file.length() / 1024) + "KB";
			// System.out.println("fileName = " + fileName + " " + "size=" +
			// fileSizeString);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("fileName", fileName);
			jsonObject.put("fileSize", fileSizeString);
			jsonArray.add(jsonObject);
		}
		return jsonArray.toString();
	}

	// 删除附件
	@RequestMapping(value = "/removeFj.do")
	@ResponseBody
	public String removeFj(Integer row_Id, String fileName, HttpServletResponse response, HttpServletRequest request) {
		JSONObject jsonObject = new JSONObject();
		ERP_Customer customer = customerService.queryCustomerById(row_Id);
		// 获得objId
		String objId = customer.getClass().getSimpleName() + "." + String.valueOf(row_Id);
		// 根据objId获得附件集合
		List<Enclosure> enclosureList = enclosureService.enclosureList(objId);
		for (Enclosure e : enclosureList) {
			if (fileName.equals(e.getREALWJM())) {
				// 删除数据库中的附件
				enclosureService.deleteFjByObj(e.getFUJIANDM());
			}
			// 删除服务器端的附件文件
			String filePath = request.getSession().getServletContext()// D:\guildFile\adviceNote_1493028164967_Jellyfish.jpg
					.getRealPath("/fj/" + e.getSHANGCHUANDZ() + "/" + e.getREALWJM());
			File file = new File(filePath.trim());
			if (file.exists()) {
				if (file.isFile()) {
					file.delete();
				}
			}
		}
		jsonObject.put("flag", true);
		return jsonObject.toString();
	}

	// 将上传的附件写入数据库
	private void editXshtFj(String fjsx, HttpServletRequest request, ERP_Customer customer) {
		HttpSession session = request.getSession();
		ERP_User user = (ERP_User) session.getAttribute("user");
		List<String> list = new ArrayList<String>();
		// 将fjsx进行字符截取
		if (fjsx.hashCode() != 0) {
			String fjvalue = fjsx.substring(1, fjsx.length());
			list.add(fjvalue);
			String value = list.toString();
			Date date = new Date();
			String key = customer.getClass().getSimpleName();
			// 拼接业务数据主键
			String objId = key + "." + String.valueOf(customer.getCustomer_Id());
			// 将字符串转换为json数组
			JSONArray jsonArray = JSONArray.parseArray(value);
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject obj = jsonArray.getJSONObject(i);
				String localFileName = (String) obj.get("localFileName");// 上传文件名
				String path = (String) obj.get("path");// 上传文件地址
				String fileName = (String) obj.get("fileName");// 上传文件真实名
				// new 出附件对象
				Enclosure fj = new Enclosure();
				fj.setCUNCHUWJM(localFileName);// 上传文件名
				fj.setSHANGCHUANDZ(path);// 上传文件地址
				fj.setREALWJM(fileName);// 上传文件真实名称
				fj.setSHANGCHUANRQ(date);// 上传文件日期
				fj.setSHANGCHUANYHDM(user.getUserId());// 上传用户主键
				fj.setOBJDM(objId);// 上传业务数据主键
				enclosureService.saveEnclosure(fj);// 添加附件
			}
		}
	}
}
