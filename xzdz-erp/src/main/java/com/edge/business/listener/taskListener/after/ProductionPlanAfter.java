package com.edge.business.listener.taskListener.after;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

import com.edge.stocks.product.kc.entity.ERP_Stock_Status;
import com.edge.stocks.product.kc.service.inter.KC_StatusService;

/**
 * 生产计划操作之后用于更新流程变量中的状态为待出库
 * 
 * @author NingCG
 *
 */
public class ProductionPlanAfter implements TaskListener {

	public void notify(DelegateTask delegateTask) {
		String cphds = (String) delegateTask.getVariable("cphd");
		ApplicationContext ac = ContextLoader.getCurrentWebApplicationContext();
		// 获取 KC_StatusService
		KC_StatusService statusService = (KC_StatusService) ac.getBean("KC_StatusServiceImpl");
		if (cphds != "" && cphds != null) {
			String[] variables = cphds.split(",");
			// 遍历该集合
			for (String v : variables) {
				Integer productId = null;
				String[] datas = v.split(":");
				for (int i = 0; i < datas.length; i++) {
					productId = Integer.parseInt(datas[1].trim().trim());
				}
				// 根据成品Id获得成品库存状态对象
				ERP_Stock_Status status = statusService.queryStastusByCpId(productId);
				// 设置库存状态
				status.setStatus("待出库");
				statusService.editStockStatus(status);
			}
		}
	}

}
