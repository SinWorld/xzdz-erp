package com.edge.currency.notice.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.edge.admin.user.entity.ERP_User;
import com.edge.currency.notice.entity.Notice;
import com.edge.currency.notice.entity.Notice_QueryVo;
import com.edge.currency.notice.service.inter.NoticeService;
import com.google.gson.Gson;

/**
 * 通知控制跳转层
 * 
 * @author NingCG
 *
 */
@Controller
@RequestMapping(value = "notice")
public class NoticeController {

	@Resource
	private NoticeService noticeService;

	// 跳转至通知列表页面
	@RequestMapping(value = "/initNoticeList.do")
	public String initNoticeList(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		ERP_User user = (ERP_User) session.getAttribute("user");
		Notice_QueryVo vo = new Notice_QueryVo();
		vo.setMbyhs(String.valueOf(user.getUserId()));
		model.addAttribute("wdCount", noticeService.noticeWdCount(vo));
		model.addAttribute("ydCount", noticeService.noticeYdCount(vo));
		model.addAttribute("qbCount", noticeService.noticeQbCount(vo));
		return "currency/notice/noticeList";
	}

	// 加载未读的通知集合
	@RequestMapping(value = "/noticeWdList.do")
	@ResponseBody
	public String noticeWdList(Integer page, Integer limit, HttpServletRequest request) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:dd");
		HttpSession session = request.getSession();
		ERP_User user = (ERP_User) session.getAttribute("user");
		// new出Notice_QueryVo查询对象
		Notice_QueryVo vo = new Notice_QueryVo();
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		vo.setPage((page - 1) * limit + 1);
		vo.setRows(page * limit);
		vo.setMbyhs(String.valueOf(user.getUserId()));
		// 总页数
		Gson gson = new Gson();
		map.put("code", 0);
		map.put("msg", "");
		map.put("count", noticeService.noticeWdCount(vo));
		List<Notice> list = noticeService.noticeWdList(vo);
		for (Notice l : list) {
			if (l.getYdsj() != null) {
				l.setYdshij(sdf.format(l.getYdsj()));
			}
		}
		map.put("data", list);
		String json = gson.toJson(map);
		return json.toString();
	}

	// 加载未读的通知集合
	@RequestMapping(value = "/noticeYdList.do")
	@ResponseBody
	public String noticeYdList(Integer page, Integer limit, HttpServletRequest request) {
		HttpSession session = request.getSession();
		ERP_User user = (ERP_User) session.getAttribute("user");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:dd");
		// new出Notice_QueryVo查询对象
		Notice_QueryVo vo = new Notice_QueryVo();
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		vo.setPage((page - 1) * limit + 1);
		vo.setRows(page * limit);
		vo.setMbyhs(String.valueOf(user.getUserId()));
		// 总页数
		Gson gson = new Gson();
		map.put("code", 0);
		map.put("msg", "");
		map.put("count", noticeService.noticeYdCount(vo));
		List<Notice> list = noticeService.noticeYdList(vo);
		for (Notice l : list) {
			if (l.getYdsj() != null) {
				l.setYdshij(sdf.format(l.getYdsj()));
			}
		}
		map.put("data", list);
		String json = gson.toJson(map);
		return json.toString();
	}

	// 加载全部的通知集合
	@RequestMapping(value = "/noticeQbList.do")
	@ResponseBody
	public String noticeQbList(Integer page, Integer limit, HttpServletRequest request) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:dd");
		HttpSession session = request.getSession();
		ERP_User user = (ERP_User) session.getAttribute("user");
		// new出Notice_QueryVo查询对象
		Notice_QueryVo vo = new Notice_QueryVo();
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		vo.setPage((page - 1) * limit + 1);
		vo.setRows(page * limit);
		vo.setMbyhs(String.valueOf(user.getUserId()));
		// 总页数
		Gson gson = new Gson();
		map.put("code", 0);
		map.put("msg", "");
		map.put("count", noticeService.noticeQbCount(vo));
		List<Notice> list = noticeService.noticeQbList(vo);
		for (Notice l : list) {
			if (l.getYdsj() != null) {
				l.setYdshij(sdf.format(l.getYdsj()));
			}
		}
		map.put("data", list);
		String json = gson.toJson(map);
		return json.toString();
	}

	// 已阅
	@RequestMapping(value = "/yready.do")
	@ResponseBody
	public String yready(Integer id) {
		JSONObject jsonObject = new JSONObject();
		Notice notice = noticeService.queryNoticeById(id);
		notice.setReady(true);
		notice.setYdsj(new Date());
		noticeService.editNotice(notice);
		jsonObject.put("flag", true);
		return jsonObject.toString();
	}
}
