package com.edge.admin.materielId.controller;

import java.io.File;
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

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.edge.admin.materielId.entity.ERP_MaterielId;
import com.edge.admin.materielId.entity.ERP_MaterielId_QueryVo;
import com.edge.admin.materielId.service.inter.MaterielIdService;
import com.edge.admin.user.entity.ERP_User;
import com.edge.currency.enclosure.entity.Enclosure;
import com.edge.currency.enclosure.service.inter.EnclosureService;
import com.google.gson.Gson;

/**
 * 物料Id控制跳转层
 * 
 * @author NingCG
 *
 */
@Controller
@RequestMapping(value = "materielId")
public class MaterielIdController {

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

}
