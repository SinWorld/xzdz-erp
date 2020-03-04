package com.edge.business.listener.flowListener.lz;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

/**
 * 流转至成品出库节点，设置其formKey
 * 
 * @author NingCG
 *
 */
public class LZ_ProductStockCk implements ExecutionListener {

	public void notify(DelegateExecution execution) throws Exception {
		execution.setVariable("url", "productStockCk/initProductStockCk.do");

	}

}
