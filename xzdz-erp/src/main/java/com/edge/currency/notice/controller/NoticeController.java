package com.edge.currency.notice.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
	public String initNoticeList() {
		return "currency/notice/noticeList";
	}

	// 加载未读的通知集合
	@RequestMapping(value = "/noticeWdList.do")
	@ResponseBody
	public String noticeWdList(Integer page, Integer limit) {
		// new出Notice_QueryVo查询对象
		Notice_QueryVo vo = new Notice_QueryVo();
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		vo.setPage((page - 1) * limit + 1);
		vo.setRows(page * limit);
		// 总页数
		Gson gson = new Gson();
		map.put("code", 0);
		map.put("msg", "");
		map.put("count", noticeService.noticeWdCount(vo));
		map.put("data", noticeService.noticeWdList(vo));
		String json = gson.toJson(map);
		return json.toString();
	}
}
