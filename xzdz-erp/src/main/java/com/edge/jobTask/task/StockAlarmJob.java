package com.edge.jobTask.task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;

import com.edge.admin.jobTask.entity.SYS_JobTask;
import com.edge.admin.jobTask.service.inter.SYS_JobTaskService;
import com.edge.admin.user.entity.ERP_User;
import com.edge.admin.user.service.inter.ERP_UserService;
import com.edge.currency.notice.entity.Notice;
import com.edge.currency.notice.service.inter.NoticeService;
import com.edge.stocks.material.kc.service.inter.KC_MaterialStockService;
import com.edge.stocks.product.kc.entity.ERP_WarnStock;
import com.edge.stocks.product.kc.service.inter.KC_StockService;

/**
 * 发送通知的定时任务(库存报警)
 * 
 * @author NingCG
 *
 */
public class StockAlarmJob implements Job {

	public void execute(JobExecutionContext context) throws JobExecutionException {
		SimpleDateFormat ft = new SimpleDateFormat("hh:mm:ss");
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		Date startTime = null;
		try {
			startTime = ft.parse("13:00:00");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Date endTime = null;
		try {
			endTime = ft.parse("15:00:00");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Date notTime = new Date();
		Date nowTime = null;
		try {
			nowTime = ft.parse(sdf.format(notTime));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Boolean flag = belongCalendar(nowTime, startTime, endTime);
		if (flag) {
			// 获取spring容器
			ApplicationContext ac = (ApplicationContext) context.getJobDetail().getJobDataMap()
					.get("applicationContext");
			ERP_UserService userService = (ERP_UserService) ac.getBean("ERP_UserServiceImpl");
			// 获取KC_StockService(成品库存)
			KC_StockService proStockService = (KC_StockService) ac.getBean("KC_StockServiceImpl");
			// 获取材料库存KC_MaterialStockService
			KC_MaterialStockService matStockService = (KC_MaterialStockService) ac
					.getBean("KC_MaterialStockServiceImpl");
			// 获取定时任务SYS_JobTaskService
			SYS_JobTaskService jobTaskService = (SYS_JobTaskService) ac.getBean("SYS_JobTaskServiceImpl");
			// 获取NoticeService
			NoticeService noticeService = (NoticeService) ac.getBean("noticeServiceImpl");
			// 获取定时任务对象
			SYS_JobTask jobTask = jobTaskService
					.queryJobByClassName(context.getJobDetail().getJobClass().getSimpleName());
			String proWlId = "";
			String matWlId = "";
			// 根据岗位代码获取用户集合
			if (jobTask != null) {
				List<ERP_User> userList = userService.queryUserByPost(jobTask.getJob_post());
				// 获取成品库存集合
				List<ERP_WarnStock> proStockList = proStockService.warnStockList();
				// 遍历成品库存集合
				for (ERP_WarnStock p : proStockList) {
					if (p.getKcl() < 100) {
						// 拼接成品物料Id
						if (proWlId == "") {
							proWlId = proWlId + p.getMaterielId();
						} else {
							proWlId = proWlId + "、" + p.getMaterielId();
						}
					}

				}
				if (proWlId != "" && proWlId != null) {
					String noticeTitle = "请知悉:" + "成品库存中物料Id为【" + proWlId + "】库存量小于100请及时补充库存量谢谢！！！";
					for (ERP_User u : userList) {
						// 查询当前内容在通知中是否存在若存在则不再新增反之则新增
						Notice notice = noticeService.queryNoticeByTitleAndMbyh(noticeTitle,
								String.valueOf(u.getUserId()));
						if (notice == null) {
							Notice kctz = new Notice();
							kctz.setContent(noticeTitle);
							kctz.setReady(false);
							kctz.setObjId(jobTask.getClass().getSimpleName() + "."
									+ String.valueOf(jobTask.getJob_Task_Id_()));
							kctz.setCreateTime(new Date());
							kctz.setMbyhs(String.valueOf(u.getUserId()));
							kctz.setFsyh(null);
							kctz.setYdsj(null);
							noticeService.saveNotice(kctz);
						}
					}
				}
				// 获取材料库存集合
				List<ERP_WarnStock> matStockList = matStockService.warnStockList();
				// 遍历材料库存集合
				for (ERP_WarnStock m : matStockList) {
					if (m.getKcl() < 100) {
						// 拼接成品物料Id
						if (matWlId == "") {
							matWlId = matWlId + m.getMaterielId();
						} else {
							matWlId = matWlId + "、" + m.getMaterielId();
						}
					}
				}
				if (matWlId != null && matWlId != "") {
					String noticeMatTitle = "请知悉:" + "材料库存中物料Id为【" + matWlId + "】库存量小于100请及时补充库存量谢谢！！！";
					for (ERP_User u : userList) {
						// 查询当前内容在通知中是否存在若存在则不再新增反之则新增
						Notice notice = noticeService.queryNoticeByTitleAndMbyh(noticeMatTitle,
								String.valueOf(u.getUserId()));
						if (notice == null) {
							Notice kctz = new Notice();
							kctz.setContent(noticeMatTitle);
							kctz.setReady(false);
							kctz.setObjId(jobTask.getClass().getSimpleName() + "."
									+ String.valueOf(jobTask.getJob_Task_Id_()));
							kctz.setCreateTime(new Date());
							kctz.setMbyhs(String.valueOf(u.getUserId()));
							kctz.setFsyh(null);
							kctz.setYdsj(null);
							noticeService.saveNotice(kctz);
						}
					}
				}
			}
		}

	}

	public static boolean belongCalendar(Date nowTime, Date beginTime, Date endTime) {
		Calendar date = Calendar.getInstance();
		date.setTime(nowTime);

		Calendar begin = Calendar.getInstance();
		begin.setTime(beginTime);

		Calendar end = Calendar.getInstance();
		end.setTime(endTime);

		if (date.after(begin) && date.before(end)) {
			return true;
		} else {
			return false;
		}
	}
}
