package com.edge.business.sale.listener.flowListener.th;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

/**
 * 领导评审退回至交货期评审，用于设置其页面的formKey
 * 
 * @author NingCG
 *
 */
public class TH_ldps_jhqps implements ExecutionListener {

	public void notify(DelegateExecution execution) throws Exception {
		execution.setVariable("url", "jhqps/initJhqps.do");
	}

}
