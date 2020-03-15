package com.edge.business.sale.listener.flowListener.lz;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

/**
 * 流转至产品检验，用于设置其formKey
 * 
 * @author NingCG
 *
 */
public class LZ_cpyj implements ExecutionListener {

	public void notify(DelegateExecution execution) throws Exception {
		execution.setVariable("url", "cpjy/initCpjy.do");
	}

}
