package com.edge.business.listener.flowListener;

/**
 * 退回到成品核对连线监听器，用于设置成品核对的formKey
 * @author NingCG
 *
 */

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

public class TH_Delivery implements ExecutionListener {

	public void notify(DelegateExecution execution) throws Exception {
		execution.setVariable("url", "checkProduct/initEditCheckProduct.do");

	}
}
