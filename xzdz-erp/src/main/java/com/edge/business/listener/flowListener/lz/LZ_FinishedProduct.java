package com.edge.business.listener.flowListener.lz;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

/**
 * 流转至成品检验用于设置该节点的formKey
 * 
 * @author NingCG
 *
 */
public class LZ_FinishedProduct implements ExecutionListener {

	public void notify(DelegateExecution execution) throws Exception {
		execution.setVariable("url", "finishedProduct/initFinishedProduct.do");
	}

}
