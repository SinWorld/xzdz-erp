package com.edge.applicationCenter.wjg.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.edge.admin.user.entity.ERP_User;
import com.edge.admin.user.service.inter.ERP_UserService;
import com.edge.applicationCenter.wjg.entity.SYS_WenJianG;
import com.edge.applicationCenter.wjg.entity.SYS_WenJianJ;
import com.edge.applicationCenter.wjg.entity.Wjg_QueryVo;
import com.edge.applicationCenter.wjg.service.inter.WenJianGService;
import com.google.gson.Gson;

@Controller
@RequestMapping(value = "wjg")
public class WenJianGController {

	@Resource
	private WenJianGService wenJianGService;

	@Resource
	private ERP_UserService userService;

	// 跳转至文件柜页面
	@RequestMapping(value = "/initWjgList.do")
	public String wjgList() {
		return "applicationCenter/wjg/wjgList";
	}

	// 初始化文件夹
	@RequestMapping(value = "/initWenJianJ.do")
	@ResponseBody
	private String initWenJianJ() {
		List<SYS_WenJianJ> topWenJianJ = topWenJianJ();
		Gson gson = new Gson();
		String str = gson.toJson(topWenJianJ);
		return str.toString();
	}

	private List<SYS_WenJianJ> topWenJianJ() {
		// 获得顶级节点
		List<SYS_WenJianJ> topList = wenJianGService.allWenJianJ();
		List<SYS_WenJianJ> list = new ArrayList<SYS_WenJianJ>();
		// 遍历该集合
		for (SYS_WenJianJ t : topList) {
			// 根据集合主键查询子集和
			String id = String.valueOf(t.getParent_Id());
			if (id == "null") {
				String pid = null;
				if (StringUtils.isBlank(pid)) {
					list.add(findChildren(t, topList));
				}
			} else {
				if (StringUtils.isBlank(id)) {
					list.add(findChildren(t, topList));

				}
			}

		}
		return list;
	}

	// 递归查询子节点
	private SYS_WenJianJ findChildren(SYS_WenJianJ wjj, List<SYS_WenJianJ> childrenList) {
		for (SYS_WenJianJ c : childrenList) {
			if (c.getParent_Id() == wjj.getId()) {
				if (wjj.getChildren() == null) {
					wjj.setChildren(new ArrayList<SYS_WenJianJ>());
				}
				wjj.getChildren().add(findChildren(c, childrenList));
			}
		}
		return wjj;
	}

	// 跳转至文件夹新增页面
	@RequestMapping(value = "/initSaveWjj.do")
	public String initSaveWjj(@RequestParam Integer id, Model model) {
		// 通过Id查询文件夹
		SYS_WenJianJ wjj = wenJianGService.queryWjjById(id);
		model.addAttribute("wjj", wjj);
		return "applicationCenter/wjg/saveWjg";
	}

	// 新增文件夹
	@RequestMapping(value = "/saveWjj.do")
	public String saveWjj(SYS_WenJianJ wjj, @RequestParam Integer flwjj, Model model) {
		// 新增文件夹
		wjj.setParent_Id(flwjj);
		wenJianGService.saveWjj(wjj);
		model.addAttribute("flag", true);
		return "applicationCenter/wjg/saveWjg";
	}

	// 跳转至编辑页面
	@RequestMapping(value = "/initEditWJg.do")
	public String initEditWJg(@RequestParam Integer id, Model model) {
		// 根据Id查询当前节点名称
		SYS_WenJianJ wjj = wenJianGService.queryWjjById(id);
		// 得到上级文件夹的主键 根据该主键去查询上级文件夹名称
		// 如果该文件夹为顶级文件夹则上级文件夹主键为空
		if (wjj.getParent_Id() == null) {
			model.addAttribute("wjj", wjj);
			model.addAttribute("title", "请选择文件夹");
		} else {
			SYS_WenJianJ sys_wjj = (SYS_WenJianJ) wenJianGService.queryWjjById(wjj.getParent_Id());
			model.addAttribute("wjj", sys_wjj);
			model.addAttribute("title", sys_wjj.getTitle());
		}
		model.addAttribute("wj", wjj);
		return "applicationCenter/wjg/editWjg";
	}

	// 编辑页面初始化文件机构树
	@RequestMapping(value = "/orgWJJTree.do", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String initEditWjj() {
		// new出JSONArray数组对象
		JSONArray jsonArray = new JSONArray();
		// 准备文件夹列表数据，用于select框显示
		List<SYS_WenJianJ> topList = wenJianGService.queryTopWJJTree();
		List<SYS_WenJianJ> treeList = this.getTreeList(topList, null);
		jsonArray.add(treeList);
		return jsonArray.toString();

	}

	private List<SYS_WenJianJ> getTreeList(List<SYS_WenJianJ> topList, Long removeId) {
		List<SYS_WenJianJ> treeList = new ArrayList<SYS_WenJianJ>();
		this.walkTree(topList, treeList, "┣", removeId, wenJianGService);
		return treeList;
	}

	/**
	 * 组织树形部门数据
	 */
	private void walkTree(Collection<SYS_WenJianJ> topList, List<SYS_WenJianJ> treeList, String prefix, Long removeId,
			WenJianGService wenJianGService) {
		for (SYS_WenJianJ d : topList) {
			if (removeId != null && d.getId().equals(removeId)) {
				continue;
			}
			SYS_WenJianJ copy = new SYS_WenJianJ();
			copy.setId(d.getId());
			copy.setTitle((prefix + d.getTitle()));
			// 顶点
			treeList.add(copy);
			// 子树
			Integer dep_id = d.getId();
			List<SYS_WenJianJ> children = wenJianGService.queryChildrenWJJTree(dep_id);
			walkTree(children, treeList, "　" + prefix, removeId, wenJianGService);
		}
	}

	// 编辑文件夹
	@RequestMapping(value = "/editWJJ.do")
	public String editWJJ(SYS_WenJianJ wjj, Model model) {
		wenJianGService.editWJJ(wjj);
		model.addAttribute("flag", true);
		return "applicationCenter/wjg/editWjg";
	}

	// 删除文件夹
	@RequestMapping(value = "/deleteWJJ.do", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String deleteWJJ(@RequestParam Integer id) {
		// new出JSONObject对象
		JSONObject jsonObject = new JSONObject();
		// 根据id查询当前节点
		SYS_WenJianJ wjj = wenJianGService.queryWjjById(id);
		// 查询当前节点的子节点
		List<SYS_WenJianJ> queryChildrenWJJTree = wenJianGService.queryChildrenWJJTree(wjj.getId());
		if (queryChildrenWJJTree.size() == 0) {
			// 表示不存在子节点可直接删除
			wenJianGService.deleteWJJ(wjj.getId());
			jsonObject.put("flag", true);
		} else {
			jsonObject.put("flag", false);
			jsonObject.put("infor", "当前节点存在子节点请先删除子节点");
		}
		return jsonObject.toString();
	}

	// 点击上传按钮打开上传页面
	@RequestMapping(value = "/initupload.do")
	public String initupload() {
		return "applicationCenter/wjg/saveWj";
	}

	// 将上传的附件写入数据库
	@RequestMapping(value = "/saveWJ.do")
	private String saveWJ(@RequestParam String fjsx, Integer wenJianJDM, HttpServletRequest request, Model model) {
		// 从session中获取当前登录用户主键
		HttpSession session = request.getSession();
		ERP_User user = (ERP_User) session.getAttribute("user");
		List<String> list = new ArrayList<String>();
		// 将fjsx进行字符截取
		String fjvalue = fjsx.substring(1, fjsx.length());
		list.add(fjvalue);
		String value = list.toString();
		Date date = new Date();
		// 将字符串转换为json数组
		JSONArray jsonArray = JSONArray.parseArray(value);
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject obj = jsonArray.getJSONObject(i);
			String localFileName = (String) obj.get("localFileName");// 上传文件名
			String path = (String) obj.get("path");// 上传文件地址
			String fileName = (String) obj.get("fileName");// 上传文件真实名
			// new 出文件柜对象
			SYS_WenJianG wjg = new SYS_WenJianG();
			wjg.setScwjm(localFileName);// 上传文件名
			wjg.setScdz(path);// 上传文件地址
			wjg.setRealWJM(fileName);// 上传文件真实名称
			wjg.setStartTime(date);// 上传文件日期
			wjg.setUserDM(user.getUserId());// 上传用户主键
			wjg.setWenJianJDM(wenJianJDM);
			wjg.setWenJianM(fileName);
			wenJianGService.saveWJG(wjg);
		}
		model.addAttribute("flag", true);
		return "applicationCenter/wjg/saveWj";
	}

	// (点击文件夹展现相应文件柜文件)
	@RequestMapping(value = "/wjgList.do")
	@ResponseBody
	public String wjgList(@RequestParam Integer page, Integer limit, String time1, String time2, Integer wjjdm,
			String wenJianM, Integer wenJianJDM, Integer userDM) {
		// new出QueryVo查询对象
		Wjg_QueryVo vo = new Wjg_QueryVo();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		// new 出List集合用于存储点击文件夹后对应的文件柜对象
		List<SYS_WenJianG> clickWJGS = new ArrayList<SYS_WenJianG>();
		// 根据文件夹代码查询子文件夹
		if (wjjdm != null) {// 点击文件夹展现对应的文件柜数据
			List<Integer> wjjdms = queryWJJId(wjjdm);
			List<SYS_WenJianG> wjgParent = wenJianGService.queryWJGByWJJDM(wjjdm);
			if (wjgParent.size() > 0) {
				for (SYS_WenJianG p : wjgParent) {
					clickWJGS.add(p);
				}
			}
			// 遍历 wjjdms集合
			for (Integer dm : wjjdms) {
				// 根据代码查询对应的文件柜对象
				List<SYS_WenJianG> wjg = wenJianGService.queryWJGByWJJDM(dm);
				if (wjg.size() > 0) {
					for (SYS_WenJianG w : wjg) {
						clickWJGS.add(w);
					}
				}
			}
			if (vo != null) {
				vo.setPage((page - 1) * limit + 1);
				vo.setRows(page * limit);
				vo.setWenJianM(wenJianM);
				vo.setWenJianJDM(wenJianJDM);
				vo.setUserDM(userDM);
				if (time1 != null && time1 != "") {
					// 将String类型转换为Date类型
					try {
						vo.setDate(sdf.parse(time1));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				if (time2 != null && time2 != "") {
					try {
						vo.setDate2(sdf.parse(time2));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
			}
			Gson gson = new Gson();
			map.put("code", 0);
			map.put("msg", "");
			map.put("count", clickWJGS.size());
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
			// 遍历该集合
			for (SYS_WenJianG row : clickWJGS) {
				// 设置文件夹名称
				SYS_WenJianJ wjj = wenJianGService.queryWjjById(row.getWenJianJDM());
				if (wjj != null) {
					row.setTitle(wjj.getTitle());// 设置文件夹名称
				}

				// 根据用户id查询用户对象
				ERP_User user = userService.queryUserById(row.getUserDM());
				if (user != null) {
					// 设置提交用户名
					row.setUserName(user.getUserName());
				}
				// 格式化提交日期
				row.setBeginTime((df.format(row.getStartTime())));
			}
			map.put("data", clickWJGS);
			String json = gson.toJson(map);
			return json.toString();
		} else {// 分页展现所有的文件柜对象
			// 每页数
			if (vo != null) {
				vo.setPage((page - 1) * limit + 1);
				vo.setRows(page * limit);
				vo.setWenJianM(wenJianM);
				vo.setWenJianJDM(wenJianJDM);
				vo.setUserDM(userDM);
				if (time1 != null && time1 != "") {
					// 将String类型转换为Date类型
					try {
						vo.setDate(sdf.parse(time1));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				if (time2 != null && time2 != "") {
					try {
						vo.setDate2(sdf.parse(time2));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
			}
			Gson gson = new Gson();
			map.put("code", 0);
			map.put("msg", "");
			map.put("count", wenJianGService.queryAllWenJCount(vo));
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
			List<SYS_WenJianG> rows = wenJianGService.queryAllWenJ(vo);
			// 遍历该集合
			for (SYS_WenJianG row : rows) {
				// 设置文件夹名称
				SYS_WenJianJ wjj = wenJianGService.queryWjjById(row.getWenJianJDM());
				if (wjj != null) {
					row.setTitle(wjj.getTitle());// 设置文件夹名称
				}

				// 根据用户id查询用户对象
				ERP_User user = userService.queryUserById(row.getUserDM());
				if (user != null) {
					// 设置提交用户名
					row.setUserName(user.getUserName());
				}
				// 格式化提交日期
				row.setBeginTime((df.format(row.getStartTime())));
			}
			map.put("data", rows);
			String json = gson.toJson(map);
			return json.toString();
		}
	}

	// 点击文件夹查询该文件夹下所有的子节点文件夹主键
	private List<Integer> queryWJJId(Integer wjjdm) {
		// new 出List集合用于存储所有的文件夹代码
		List<Integer> wjjdms = new ArrayList<Integer>();
		// 根据文件夹代码查询子文件夹集合
		List<SYS_WenJianJ> childrenWJJTree = wenJianGService.queryChildrenWJJTree(wjjdm);
		this.queryWJJTree(childrenWJJTree, wjjdms);
		return wjjdms;

	}

	// 递归查询该文件夹下所有的子文件夹
	private void queryWJJTree(List<SYS_WenJianJ> childrens, List<Integer> wjjdms) {
		// 遍历该集合
		for (SYS_WenJianJ c : childrens) {
			wjjdms.add(c.getId());
			List<SYS_WenJianJ> childrenWJJ = wenJianGService.queryChildrenWJJTree(c.getId());
			queryWJJTree(childrenWJJ, wjjdms);
		}

	}

	// 下载附件
	@RequestMapping(value = "/dowLoand.do")
	public void dowLoand(String wjgdm, HttpServletRequest request, HttpServletResponse response)
			throws FileNotFoundException {
		// 将wjgdm进行字符截取
		// String wjdm = wjgdm.substring(1, wjgdm.length());
		// 转换为数组
		ZipOutputStream out = new ZipOutputStream(new FileOutputStream("文件"));
		String[] ids = wjgdm.split(",");
		// 遍历该数组
		for (int i = 0; i < ids.length; i++) {
			// 根据id查询该文件柜对象
			SYS_WenJianG wjg = wenJianGService.queryWJGById(Integer.parseInt(ids[i].trim()));
			// 获得ftpath
			String ftpPath = wjg.getScdz();
			// 获得存储在数据库中的文件真正名称
			String rEALWJM = wjg.getRealWJM();
			String filePath = request.getSession().getServletContext()// D:\guildFile\adviceNote_1493028164967_Jellyfish.jpg
					.getRealPath("/fj/" + ftpPath);
			String file_name = null;
			try {
				file_name = new String(rEALWJM.getBytes(), "ISO-8859-1");
			} catch (UnsupportedEncodingException e2) {
				e2.printStackTrace();
			}
			// 1.设置文件ContentType类型，这样设置，会自动判断下载文件类型
			response.setContentType("application/x-download");
			// 设置response对象的头参数,attachment就是附件,filename就是文件名
			response.setHeader("Content-disposition", "attachment;filename=" + file_name);

			// 通过文件路径获得File对象(假如此路径中有一个download.pdf文件)
			String finalPath = filePath + "/" + rEALWJM;
			File file = new File(finalPath);
			InputStream inputStream = null;
			try {
				inputStream = new FileInputStream(file);
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} // 文件输入流
				// --创建输出流,绑定到response对象
			ServletOutputStream outputStream = null;
			try {
				outputStream = response.getOutputStream();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			// 流复制
			byte[] b = new byte[1024];
			int len = -1;
			// 文件内容拷贝
			try {
				while ((len = inputStream.read(b, 0, 1024)) != -1) {
					outputStream.write(b, 0, b.length);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				outputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				outputStream.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// 删除文件
	@RequestMapping(value = "/deleteWjById.do", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String deleteWj(@RequestParam Integer id, HttpServletRequest request) {
		SYS_WenJianG wjg = wenJianGService.queryWJGById(id);
		// 删除服务器端的附件文件
		String filePath = request.getSession().getServletContext()// D:\guildFile\adviceNote_1493028164967_Jellyfish.jpg
				.getRealPath("/fj/" + wjg.getScdz() + "/" + wjg.getRealWJM());
		File file = new File(filePath.trim());
		if (file.exists()) {
			if (file.isFile()) {
				file.delete();
			}
		}
		wenJianGService.deleteWJById(id);
		// new 出JSONObject对象
		JSONObject jSONObject = new JSONObject();
		jSONObject.put("flag", true);
		return jSONObject.toString();
	}

}
