package com.edge.cghtfk.listener.eventListener;

import java.util.Date;
import java.util.List;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

import com.edge.business.purchase.entity.ERP_Purchase_Order;
import com.edge.business.purchase.service.inter.PurchaseOrderService;
import com.edge.cghtfk.entity.ERP_Cghtfk;
import com.edge.cghtfk.service.inter.CghtfkService;
import com.edge.currency.notice.entity.Notice;
import com.edge.currency.notice.service.inter.NoticeService;

/**
 * 采购合同付款流程结束设置采购合同的付款状态，且发送通知
 * 
 * @author NingCG
 *
 */
public class CghtfkEndListener implements JavaDelegate {

	public void execute(DelegateExecution execution) throws Exception {
		// 取得businessKey
		String key = execution.getProcessBusinessKey();
		// 得到业务数据主键值
		String id = key.substring(key.indexOf(".") + 1);
		// 2.从spring容器中获取UserServiceImpl
		ApplicationContext ac = ContextLoader.getCurrentWebApplicationContext();
		// 获取PurchaseOrderService 接口
		PurchaseOrderService purchaseOrderService = (PurchaseOrderService) ac.getBean("purchaseOrderServiceImpl");
		// 获取CghtfkService接口
		CghtfkService cghtfkService = (CghtfkService) ac.getBean("cghtfkServiceImpl");
		// 获取NoticeService接口
		NoticeService noticeService = (NoticeService) ac.getBean("noticeServiceImpl");
		// 获取采购合同付款对象
		ERP_Cghtfk cghtfk = cghtfkService.queryCghtfkById(Integer.parseInt(id.trim()));
		// 获取采购合同
		ERP_Purchase_Order cght = purchaseOrderService.queryPurchaseOrderById(cghtfk.getCght());
		cghtfk.setApprovalDm(1);
		cghtfkService.editCghtsk(cghtfk);
		// 获取当前销售合同下所有的实际付款金额总和
		Double sumfkje = cghtfkService.querySumLjfkje(cght.getPur_Order_Id());
		if (sumfkje.equals(cght.getTotalPrice())) {
			cght.setIs_wcfk(true);
		} else {
			cght.setIs_wcfk(false);
		}
		purchaseOrderService.editPurchaseOrder(cght);
		// 发送通知 获取目标用户集合
		List<String> mbyhs = noticeService.mbyhs(execution.getProcessInstanceId());
		// 获取 发送用户
		String fsyh = noticeService.fsyh(execution.getProcessInstanceId());
		// 遍历目标用户集合
		for (String m : mbyhs) {
			// new 出Notice
			Notice notice = new Notice();
			notice.setContent("请知悉：采购合同为【" + cght.getPurchaseOrderName() + "】的采购合同付款流程审批已通过!!!");
			notice.setReady(false);
			notice.setObjId(key);
			notice.setCreateTime(new Date());
			notice.setMbyhs(m);
			notice.setFsyh(Integer.parseInt(fsyh.trim()));
			noticeService.saveNotice(notice);
		}
	}

}
