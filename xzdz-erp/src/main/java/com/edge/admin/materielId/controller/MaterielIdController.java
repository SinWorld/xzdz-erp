package com.edge.admin.materielId.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.edge.admin.materielId.entity.ERP_MaterielId;
import com.edge.admin.materielId.entity.ERP_MaterielId_QueryVo;
import com.edge.admin.materielId.service.inter.MaterielIdService;
import com.edge.admin.user.entity.ERP_User;
import com.edge.currency.enclosure.entity.Enclosure;
import com.edge.currency.enclosure.service.inter.EnclosureService;
import com.edge.utils.ExcelUtils;
import com.google.gson.Gson;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

/**
 * 物料Id控制跳转层
 * 
 * @author NingCG
 *
 */
@Controller
@RequestMapping(value = "materielId")
public class MaterielIdController {

	public static final String DBDRIVER = "oracle.jdbc.driver.OracleDriver";// 定义MySQL的数据库驱动程序
	public static final String DBURL = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";// 定义MySQL的数据库的连接地址
	public static final String DBUSER = "erp";// MySQL数据库的连接用户名
	public static final String DBPASS = "xzdz_erp";// MySQL数据库的连接用密码

	@Resource
	private MaterielIdService materielIdService;

	@Resource
	private EnclosureService enclosureService;

	// 跳转至物料Id列表页面
	@RequestMapping(value = "/initMaterielIdList.do")
	public String initMaterielIdList() {
		return "admin/materielId/materielIdList";
	}

	// easyUI 列表查询
	@RequestMapping(value = "/materielIdList.do")
	@ResponseBody
	public String materielIdList(@RequestParam Integer page, Integer rows, String wlId, String ggxh, String bzq,
			String type, String ckj1, String ckj2) {
		// new出查询对象
		ERP_MaterielId_QueryVo vo = new ERP_MaterielId_QueryVo();
		Gson gson = new Gson();
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		vo.setPage((page - 1) * rows + 1);
		vo.setRows(page * rows);
		vo.setWlId(wlId);
		vo.setGgxh(ggxh);
		vo.setBzq(bzq);
		vo.setType(type);
		if (ckj1 != null && ckj1 != "") {
			vo.setCkj1(Double.valueOf(ckj1.trim()));
		}
		if (ckj2 != null && ckj2 != "") {
			vo.setCkj2(Double.valueOf(ckj2.trim()));
		}
		List<ERP_MaterielId> materielIdList = materielIdService.materielIdList(vo);
		for (ERP_MaterielId m : materielIdList) {
			if (m.getType()) {
				m.setTypeName("材料");
			} else {
				m.setTypeName("成品");
			}
		}
		map.put("total", materielIdService.materielIdCount(vo));
		map.put("rows", materielIdList);
		String json = gson.toJson(map);
		return json.toString();
	}

	// 跳转至新增页面
	@RequestMapping(value = "/initSaveMaterielId.do")
	public String initSaveMaterielId() {
		return "admin/materielId/saveMaterielId";
	}

	// ajax物料Id不重复
	@RequestMapping(value = "/wlIdbcf.do")
	@ResponseBody
	public String wlIdbcf(String materiel_Id, boolean type) {
		JSONObject jsonObject = new JSONObject();
		ERP_MaterielId erp_MaterielId = materielIdService.queryERP_MaterielIdByWLID(materiel_Id.trim(), type);
		if (erp_MaterielId != null) {
			jsonObject.put("flag", true);
		} else {
			jsonObject.put("flag", false);
		}
		return jsonObject.toString();
	}

	// 新增物料Id
	@RequestMapping(value = "/saveMaterielId.do")
	public String saveMaterielId(@RequestParam String fjsx, ERP_MaterielId materielId, Model model,
			HttpServletRequest request) {
		materielIdService.saveMaterielId(materielId);
		this.addXshtFj(fjsx, request);
		model.addAttribute("flag", true);
		return "admin/materielId/saveMaterielId";
	}

	// 跳转至物料编辑页面
	@RequestMapping(value = "/initEditMaterielId.do")
	public String initEditMaterielId(@RequestParam Integer row_Id, Model model) {
		ERP_MaterielId materielId = materielIdService.queryMaterielIdById(row_Id);
		model.addAttribute("materielId", materielId);
		return "admin/materielId/editMaterielId";
	}

	// 编辑操作
	@RequestMapping(value = "/editMaterielId.do")
	public String editMaterielId(@RequestParam String fjsx, ERP_MaterielId materielId, Model model,
			HttpServletRequest request) {
		materielIdService.editMaterielId(materielId);
		this.editXshtFj(fjsx, request, materielId);
		model.addAttribute("flag", true);
		return "admin/materielId/editMaterielId";
	}

	// 删除操作
	@RequestMapping(value = "/deleteMaterielId.do")
	@ResponseBody
	public String deleteMaterielId(Integer row_Id) {
		JSONObject jsonObject = new JSONObject();
		materielIdService.deleteMaterelId(row_Id);
		jsonObject.put("flag", true);
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
			materielIdService.deleteMaterelId(Integer.parseInt(id.trim()));
		}
		jsonObject.put("flag", true);
		return jsonObject.toString();
	}

	// 查看操作
	@RequestMapping(value = "/showMaterielId.do")
	public String showMaterielId(@RequestParam Integer row_Id, Model model) {
		ERP_MaterielId materielId = materielIdService.queryMaterielIdById(row_Id);
		String businessKey = materielId.getClass().getSimpleName() + "." + String.valueOf(row_Id);
		if (materielId.getType()) {
			materielId.setTypeName("材料");
		} else {
			materielId.setTypeName("成品");
		}
		model.addAttribute("OBJDM", businessKey);
		model.addAttribute("materielId", materielId);
		return "admin/materielId/showMaterielId";
	}

	// 查询成品的物料Id
	@RequestMapping(value = "/queryProWlId.do")
	@ResponseBody
	public String queryProWlId() {
		JSONArray jsonArray = materielIdService.queryProWlId();
		return jsonArray.toString();
	}

	// 查询材料的物料Id
	@RequestMapping(value = "/queryMatWlId.do")
	@ResponseBody
	public String queryMatWlId() {
		JSONArray jsonArray = materielIdService.queryMatWlId();
		return jsonArray.toString();
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
			// 根据物料Id主键获得物料Id对象
			ERP_MaterielId materielId = materielIdService.queryMaterielIdById(materielIdService.queryMaxMaterielId());
			String key = materielId.getClass().getSimpleName();
			// 拼接业务数据主键
			String objId = key + "." + String.valueOf(materielIdService.queryMaxMaterielId());
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

	// 将上传的附件写入数据库
	private void editXshtFj(String fjsx, HttpServletRequest request, ERP_MaterielId materielId) {
		HttpSession session = request.getSession();
		ERP_User user = (ERP_User) session.getAttribute("user");
		List<String> list = new ArrayList<String>();
		// 将fjsx进行字符截取
		if (fjsx.hashCode() != 0) {
			String fjvalue = fjsx.substring(1, fjsx.length());
			list.add(fjvalue);
			String value = list.toString();
			Date date = new Date();
			String key = materielId.getClass().getSimpleName();
			// 拼接业务数据主键
			String objId = key + "." + String.valueOf(materielId.getRow_Id());
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

	// ajax在编辑页面显示附件
	@RequestMapping(value = "/pageLoadFj.do")
	@ResponseBody
	public String pageLoadFj(Integer row_Id, HttpServletResponse response, HttpServletRequest request) {
		JSONArray jsonArray = new JSONArray();
		ERP_MaterielId materielId = materielIdService.queryMaterielIdById(row_Id);
		// 获得objId
		String objId = materielId.getClass().getSimpleName() + "." + String.valueOf(row_Id);
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
		ERP_MaterielId materielId = materielIdService.queryMaterielIdById(row_Id);
		// 获得objId
		String objId = materielId.getClass().getSimpleName() + "." + String.valueOf(row_Id);
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

	// 跳转至文件导入页面
	@RequestMapping(value = "/initFileImport.do")
	public String initFileImport() {
		return "admin/materielId/fileImport";
	}

	// 导入Excel
	@RequestMapping({ "/importExcel.do" })
	public String importExcel(@RequestParam MultipartFile file, HttpServletRequest request, Model model) {
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		try {
			loadExcel(file.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		model.addAttribute("flag", true);
		return "admin/materielId/fileImport";
	}

	public void loadExcel(InputStream is) {
		try {
			Workbook wb = Workbook.getWorkbook(is);
			Sheet sheet = wb.getSheet(0);
			int rows = sheet.getRows();
			for (int i = 1; i < rows; i++) {
				List oneData = new ArrayList();
				Cell[] cells = sheet.getRow(i);
				for (int j = 0; j < cells.length; j++) {
					oneData.add(cells[j].getContents().trim());
				}
				if (oneData.size() > 0) {
					// new 出物料Id对象
					ERP_MaterielId materielId = new ERP_MaterielId();
					materielId.setMateriel_Id(String.valueOf(oneData.get(1)));
					materielId.setSpecification_Type(String.valueOf(oneData.get(2)));
					materielId.setBzq(String.valueOf(oneData.get(3)));
					Double ckdj = Double.parseDouble(String.valueOf(oneData.get(4)));
					materielId.setCkdj(ckdj);
					materielId.setRemarks(String.valueOf(oneData.get(5)));
					String type = (String) oneData.get(6);
					if ("成品".equals(type)) {
						materielId.setType(false);
					} else {
						materielId.setType(true);
					}
					/**
					 * 物料id不允许重复，若重复了则将该数据修改，否则新增
					 */
					ERP_MaterielId erp_MaterielId = materielIdService
							.queryERP_MaterielIdByWLID(materielId.getMateriel_Id().trim(), materielId.getType());
					if (erp_MaterielId != null) {
						// 说明存在 则修改
						materielId.setRow_Id(erp_MaterielId.getRow_Id());
						materielIdService.editMaterielId(materielId);
					} else {
						materielIdService.saveMaterielId(materielId);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 导出Excel
	@RequestMapping({ "/exportExcel.do" })
	public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("GBK");
		writeExcel(response);
	}

	public static void writeExcel(HttpServletResponse response) throws Exception {
		String[] titles = { "序号", "物料Id", "规格型号", "保质期", "参考报价", "物料描述", "类型" };
		String sheetTitle = "物料Id";
		ExcelUtils eeu = new ExcelUtils();
		HSSFWorkbook workbook = new HSSFWorkbook();
		OutputStream os = response.getOutputStream();
		List apkDate = getApkDate(); // 取出数据
		sheetTitle = new String(sheetTitle.getBytes("gb2312"), "iso8859-1");
		response.reset();
		response.setContentType("application/msexcel");
		response.setHeader("Content-disposition", "attachment; filename=" + sheetTitle + ".xls");
		eeu.exportExcel(workbook, 0, "物料Id", titles, apkDate, os);
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
		String sql = "select rownum,a.materiel_id, a.specification_type,a.bzq,a.ckdj,a.remarks,"
				+ "case when a.type=0 then '成品' when a.type=1 then '材料' else 'other' end " + "from erp_materielid a";
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
