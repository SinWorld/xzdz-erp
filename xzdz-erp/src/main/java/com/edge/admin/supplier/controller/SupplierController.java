package com.edge.admin.supplier.controller;

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
import java.util.ArrayList;
import java.util.Calendar;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.edge.admin.supplier.entity.ERP_Supplier;
import com.edge.admin.supplier.entity.Supplier_QueryVo;
import com.edge.admin.supplier.service.inter.SupplierService;
import com.edge.admin.user.entity.ERP_User;
import com.edge.utils.ExcelUtils;
import com.google.gson.Gson;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import net.sf.json.JSONObject;

/**
 * 供应商控制跳转层
 * 
 * @author NingCG
 *
 */
@Controller
@RequestMapping(value = "supplier")
public class SupplierController {

	public static final String DBDRIVER = "oracle.jdbc.driver.OracleDriver";// 定义MySQL的数据库驱动程序
	public static final String DBURL = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";// 定义MySQL的数据库的连接地址
	public static final String DBUSER = "erp";// MySQL数据库的连接用户名
	public static final String DBPASS = "xzdz_erp";// MySQL数据库的连接用密码

	@Resource
	private SupplierService supplierService;

	// 跳转至供应商列表页面
	@RequestMapping(value = "/initSupplierList.do")
	public String initSupplierList() {
		return "admin/supplier/supplierList";
	}

	// easyUI 列表查询
	@RequestMapping(value = "/supplierList.do")
	@ResponseBody
	public String supplierList(@RequestParam Integer page, Integer rows, String gysmc, String zcdz, String bgdz,
			String shtyxydm, String fddbr, String khh, String dh) {
		// new出查询对象
		Supplier_QueryVo vo = new Supplier_QueryVo();
		Gson gson = new Gson();
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		vo.setPage((page - 1) * rows + 1);
		vo.setRows(page * rows);
		vo.setGysmc(gysmc);
		vo.setZcdz(zcdz);
		vo.setBgdz(bgdz);
		vo.setShtyxydm(shtyxydm);
		vo.setFddbr(fddbr);
		vo.setKhh(khh);
		vo.setDh(dh);
		map.put("total", supplierService.supplierCount(vo));
		map.put("rows", supplierService.supplierList(vo));
		String json = gson.toJson(map);
		return json.toString();
	}

	// 跳转至供应商新增页面
	@RequestMapping(value = "/initSaveSupplier.do")
	public String initSaveSupplier() {
		return "admin/supplier/saveSupplier";
	}

	// 新增供应商
	@RequestMapping(value = "/saveSupplier.do")
	public String saveSupplier(ERP_Supplier supplier, Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		ERP_User user = (ERP_User) session.getAttribute("user");
		// 设置编号
		supplier.setSupplier_Code(this.dwbh());
		supplierService.saveSupplier(supplier);
		model.addAttribute("flag", true);
		model.addAttribute("userId", user.getUserId());
		return "admin/supplier/saveSupplier";
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
		String bh = "GYS" + "-" + time + x;
		return bh;
	}

	// 跳转至供应商编辑页面
	@RequestMapping(value = "/initEditSupplier.do")
	public String initEditSupplier(@RequestParam Integer row_Id, Model model) {
		// 根据Id获得供应商对象
		ERP_Supplier supplier = supplierService.querySupplierById(row_Id);
		model.addAttribute("supplier", supplier);
		return "admin/supplier/editSupplier";
	}

	// 编辑供应商
	@RequestMapping(value = "/editSupplier.do")
	public String editSupplier(ERP_Supplier supplier, Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		ERP_User user = (ERP_User) session.getAttribute("user");
		supplierService.editSupplier(supplier);
		model.addAttribute("flag", true);
		model.addAttribute("userId", user.getUserId());
		return "admin/supplier/editSupplier";
	}

	// 查看供应商
	@RequestMapping(value = "/showSupplier.do")
	public String showSupplier(@RequestParam Integer row_Id, Model model) {
		// 根据Id获得供应商对象
		ERP_Supplier supplier = supplierService.querySupplierById(row_Id);
		model.addAttribute("supplier", supplier);
		return "admin/supplier/showSupplier";
	}

	// 删除供应商
	@RequestMapping(value = "/deleteSupplier.do")
	@ResponseBody
	public String deleteSupplier(Integer row_Id, HttpServletRequest request) {
		HttpSession session = request.getSession();
		ERP_User user = (ERP_User) session.getAttribute("user");
		JSONObject jsonObject = new JSONObject();
		supplierService.deleteSupplier(row_Id);
		jsonObject.put("flag", true);
		jsonObject.put("userId", user.getUserId());
		return jsonObject.toString();
	}

	// 批量删除
	@RequestMapping(value = "/batchDeletePost.do")
	@ResponseBody
	public String batchDeletePost(String ids) {
		JSONObject jsonObject = new JSONObject();
		// 将ids拆分为字符组
		String[] depIds = ids.split(",");
		// 遍历该数组
		for (String id : depIds) {
			supplierService.deleteSupplier(Integer.parseInt(id.trim()));
		}
		jsonObject.put("flag", true);
		return jsonObject.toString();
	}

	// 跳转至文件导入页面
	@RequestMapping(value = "/initFileImport.do")
	public String initFileImport() {
		return "admin/supplier/fileImport";
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
		String sql = "insert into ERP_SUPPLIER(SUPPLIER_ID,SUPPLIER_CODE,SUPPLIER_NAME,REGISTERED_ADDRESS,OFFICE_ADDRESS,"
				+ "UNIFIED_CODE,LEGAL_PERSON,OPENING_BANK,ACCOUNT_NUMBER,DUTY_PARAGRAPH,PHONE,FAX,CONTACTS,PRODUCTINFOR,REMARKS) "
				+ "values(seq_supplier_Id.nextval,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
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
					pstmt.setString(14, String.valueOf(oneData.get(13)));
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
		String[] titles = { "序号", "供应商名称", "注册地址", "办公地址", "社会统一信用代码", "法定代表人", "开户行", "账号", "税号", "电话", "传真", "联系人",
				"主营产品", "备注" };
		String sheetTitle = "供应商信息";
		ExcelUtils eeu = new ExcelUtils();
		HSSFWorkbook workbook = new HSSFWorkbook();
		OutputStream os = response.getOutputStream();
		List apkDate = getApkDate(); // 取出数据
		sheetTitle = new String(sheetTitle.getBytes("gb2312"), "iso8859-1");
		response.reset();
		response.setContentType("application/msexcel");
		response.setHeader("Content-disposition", "attachment; filename=" + sheetTitle + ".xls");
		eeu.exportExcel(workbook, 0, "供应商信息", titles, apkDate, os);
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
		String sql = "select rownum, " + "s.supplier_name," + "s.registered_address," + "s.office_address,"
				+ "s.unified_code," + "s.legal_person," + "s.opening_bank," + "s.account_number," + "s.duty_paragraph,"
				+ "s.phone," + "s.fax," + "s.contacts," + "s.productinfor," + "s.remarks " + "from erp_supplier s";
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

}
