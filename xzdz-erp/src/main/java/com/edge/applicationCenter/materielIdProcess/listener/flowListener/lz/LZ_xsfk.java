package com.edge.applicationCenter.materielIdProcess.listener.flowListener.lz;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

/**
 * 流转至销售反馈
 * 
 * @author NingCG
 *
 */
public class LZ_xsfk implements ExecutionListener {

	public void notify(DelegateExecution execution) throws Exception {
		execution.setVariable("url", "SalesFeedback/initSalesFeedback.do");
	}

}
