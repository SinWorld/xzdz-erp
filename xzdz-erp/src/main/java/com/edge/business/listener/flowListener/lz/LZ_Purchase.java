package com.edge.business.listener.flowListener.lz;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

/**
 * 流转发起采购连线监听器，用于设置发起采购的formKey
 * 
 * @author NingCG
 *
 */
public class LZ_Purchase implements ExecutionListener {

	public void notify(DelegateExecution execution) throws Exception {
		execution.setVariable("url", "purchase/initPurchase.do");
	}

}
