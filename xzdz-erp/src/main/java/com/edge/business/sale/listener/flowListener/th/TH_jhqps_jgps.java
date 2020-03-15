package com.edge.business.sale.listener.flowListener.th;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

/**
 * 交货期评审退回至价格评审，用于设置其页面的formKey
 * 
 * @author NingCG
 *
 */
public class TH_jhqps_jgps implements ExecutionListener {

	public void notify(DelegateExecution execution) throws Exception {
		execution.setVariable("url", "jgps/initJgps.do");
	}

}
