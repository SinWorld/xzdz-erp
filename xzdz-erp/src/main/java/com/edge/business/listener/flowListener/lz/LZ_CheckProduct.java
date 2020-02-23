package com.edge.business.listener.flowListener.lz;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

/**
 * 流转成品核对连线监听器，用于设置成品核对的formKey
 * 
 * @author NingCG
 *
 */
public class LZ_CheckProduct implements ExecutionListener {

	public void notify(DelegateExecution execution) throws Exception {
		execution.setVariable("url", "checkProduct/initCheckProduct.do");
	}

}
