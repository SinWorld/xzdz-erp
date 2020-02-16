package com.edge.business.listener.flowListener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

/**
 * 退回生产计划连线监听器，用于设置成品核对的formKey
 * 
 * @author NingCG
 *
 */
public class TH_ProductPlan implements ExecutionListener {

	public void notify(DelegateExecution execution) throws Exception {
		execution.setVariable("url", "checkProduct/initEditCheckProduct.do");
	}

}
