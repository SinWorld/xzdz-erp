package com.edge.business.sale.listener.flowListener.lz;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

/**
 * 流转至领导评审，用于设置其页面的formKey
 * 
 * @author NingCG
 *
 */
public class LZ_ldps implements ExecutionListener {

	public void notify(DelegateExecution execution) throws Exception {
		execution.setVariable("url", "ldps/initLdps.do");
	}

}
