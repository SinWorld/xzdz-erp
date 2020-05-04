package com.edge.business.sale.listener.flowListener.th;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

/**
 * 价格评审退回至技术评审，用于设置其页面的formKey
 * 
 * @author NingCG
 *
 */
public class TH_jgps_xzht implements ExecutionListener {

	public void notify(DelegateExecution execution) throws Exception {
		execution.setVariable("url", "sales/initEditSales.do");
	}

}
