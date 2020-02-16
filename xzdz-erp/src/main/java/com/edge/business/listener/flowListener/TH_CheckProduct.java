package com.edge.business.listener.flowListener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

/**
 * 退回成品核对连线监听器，用于设置销售订单的formKey
 * 
 * @author NingCG
 *
 */
public class TH_CheckProduct implements ExecutionListener {

	public void notify(DelegateExecution execution) throws Exception {
		execution.setVariable("url", "sales/initEditSales.do");
	}

}
