package com.edge.business.sale.listener.flowListener.lz;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

/**
 * 流转至价格评审，用于设置其表单的formKey
 * 
 * @author NingCG
 *
 */
public class LZ_jgps implements ExecutionListener {

	public void notify(DelegateExecution execution) throws Exception {
		execution.setVariable("url", "jgps/initJgps.do");

	}

}
