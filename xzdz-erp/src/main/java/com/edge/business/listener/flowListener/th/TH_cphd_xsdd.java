package com.edge.business.listener.flowListener.th;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

/**
 * 成品核对退回到销售订单连线监听器，用于设置销售订单的formKey
 * 
 * @author NingCG
 *
 */
public class TH_cphd_xsdd implements ExecutionListener {

	public void notify(DelegateExecution execution) throws Exception {
		execution.setVariable("url", "saleOrder/initEditSaleOrder.do");
	}

}
