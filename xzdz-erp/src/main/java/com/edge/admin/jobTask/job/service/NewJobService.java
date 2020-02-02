package com.edge.admin.jobTask.job.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class NewJobService implements Job {

	private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public void execute(JobExecutionContext context) throws JobExecutionException {
		Date now = new Date();
		String currentDate = sdf.format(now);
		System.out.println("现在时间是：" + currentDate + "：开始执行任务生成表格，或者发送邮件");

	}

}
