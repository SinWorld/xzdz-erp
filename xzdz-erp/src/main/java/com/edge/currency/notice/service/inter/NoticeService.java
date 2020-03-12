package com.edge.currency.notice.service.inter;

import java.util.List;

import com.edge.currency.notice.entity.Notice;
import com.edge.currency.notice.entity.Notice_QueryVo;

public interface NoticeService {

	// 分页显示未读通知内容
	public List<Notice> noticeWdList(Notice_QueryVo vo);

	// 显示通知内容未读数量
	public Integer noticeWdCount(Notice_QueryVo vo);

	// 新增通知内容
	public void saveNotice(Notice notice);
}
