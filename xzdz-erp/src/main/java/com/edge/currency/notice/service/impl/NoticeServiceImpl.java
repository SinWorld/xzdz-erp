package com.edge.currency.notice.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.edge.currency.notice.dao.NoticeDao;
import com.edge.currency.notice.entity.Notice;
import com.edge.currency.notice.entity.Notice_QueryVo;
import com.edge.currency.notice.service.inter.NoticeService;

/**
 * 通知业务逻辑侧
 * 
 * @author NingCG
 *
 */
@Service
public class NoticeServiceImpl implements NoticeService {

	@Resource
	private NoticeDao noticeDao;

	// 分页显示未读通知内容
	public List<Notice> noticeWdList(Notice_QueryVo vo) {
		return noticeDao.noticeWdList(vo);
	}

	// 显示通知内容未读数量
	public Integer noticeWdCount(Notice_QueryVo vo) {
		return noticeDao.noticeWdCount(vo);
	}

	// 新增通知内容
	public void saveNotice(Notice notice) {
		noticeDao.saveNotice(notice);
	}

	// 根据流程实例获取目标用户集合
	public List<String> mbyhs(String ProcessInstanceId) {
		return noticeDao.mbyhs(ProcessInstanceId);
	}

	// 获取发送用户
	public String fsyh(String ProcessInstanceId) {
		return noticeDao.fsyh(ProcessInstanceId);
	}

	// 分页显示已读通知内容
	public List<Notice> noticeYdList(Notice_QueryVo vo) {
		return noticeDao.noticeYdList(vo);
	}

	// 显示通知内容已读数量
	public Integer noticeYdCount(Notice_QueryVo vo) {
		return noticeDao.noticeYdCount(vo);
	}

	// 分页显示全部通知内容
	public List<Notice> noticeQbList(Notice_QueryVo vo) {
		return noticeDao.noticeQbList(vo);
	}

	// 显示通知内容全部数量
	public Integer noticeQbCount(Notice_QueryVo vo) {
		return noticeDao.noticeQbCount(vo);
	}

	// 根据Id获得通知对象
	public Notice queryNoticeById(Integer row_Id) {
		return noticeDao.queryNoticeById(row_Id);
	}

	// 编辑通知
	public void editNotice(Notice notice) {
		noticeDao.editNotice(notice);
	}

	// 根据通知名称和目标用户Id去查询通知对象
	public Notice queryNoticeByTitleAndMbyh(String content, String mbyhs) {
		return noticeDao.queryNoticeByTitleAndMbyh(content, mbyhs);
	}

}
