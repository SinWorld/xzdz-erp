package com.edge.business.sale.listener.flowListener.lz;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

/**
 * 流转至技术评审，用于设置其表单的formKey
 * 
 * @author NingCG
 *
 */
public class LZ_jsps implements ExecutionListener {

	public void notify(DelegateExecution execution) throws Exception {
		execution.setVariable("url", "jsps/initJsps.do");
	}

}
