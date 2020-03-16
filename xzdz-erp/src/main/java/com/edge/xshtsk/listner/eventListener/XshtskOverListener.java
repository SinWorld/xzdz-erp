package com.edge.xshtsk.listner.eventListener;

import java.util.Date;
import java.util.List;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

import com.edge.business.sale.entity.ERP_Sales_Contract;
import com.edge.business.sale.service.inter.ERP_Sales_ContractService;
import com.edge.currency.notice.entity.Notice;
import com.edge.currency.notice.service.inter.NoticeService;
import com.edge.xshtsk.entity.ERP_Xshtsk;
import com.edge.xshtsk.service.inter.XshtskService;

/**
 * 销售合同收款流程终止操作，用于设置流程状态,且发送通知给提交收款申请者和财务
 * 
 * @author NingCG
 *
 */
public class XshtskOverListener implements JavaDelegate {

	public void execute(DelegateExecution execution) throws Exception {
		// 取得businessKey
		String key = execution.getProcessBusinessKey();
		// 得到业务数据主键值
		String id = key.substring(key.indexOf(".") + 1);
		// 2.从spring容器中获取UserServiceImpl
		ApplicationContext ac = ContextLoader.getCurrentWebApplicationContext();
		// 获取ERP_Sales_ContractService 接口
		ERP_Sales_ContractService contractService = (ERP_Sales_ContractService) ac
				.getBean("ERP_Sales_ContractServiceImpl");
		// 获取XshtskService接口
		XshtskService xshtskService = (XshtskService) ac.getBean("xshtskServiceImpl");
		// 获取NoticeService接口
		NoticeService noticeService = (NoticeService) ac.getBean("noticeServiceImpl");
		// 获取销售合同收款对象
		ERP_Xshtsk xshtsk = xshtskService.queryXshtskById(Integer.parseInt(id.trim()));
		// 获取销售合同
		ERP_Sales_Contract contract = contractService.queryContractById(xshtsk.getXsht());
		xshtsk.setApprovaldm(3);
		xshtskService.editXshtsk(xshtsk);
		// 发送通知 获取目标用户集合
		List<String> mbyhs = noticeService.mbyhs(execution.getProcessInstanceId());
		// 获取 发送用户
		String fsyh = noticeService.fsyh(execution.getProcessInstanceId());
		// 遍历目标用户集合
		for (String m : mbyhs) {
			// new 出Notice
			Notice notice = new Notice();
			notice.setContent("请知悉：销售合同为【" + contract.getSales_Contract_Name() + "】的销售合同收款流程审批未通过!!!");
			notice.setReady(false);
			notice.setObjId(key);
			notice.setCreateTime(new Date());
			notice.setMbyhs(m);
			notice.setFsyh(Integer.parseInt(fsyh.trim()));
			noticeService.saveNotice(notice);
		}

	}

}
