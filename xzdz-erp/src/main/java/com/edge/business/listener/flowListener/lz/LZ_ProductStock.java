package com.edge.business.listener.flowListener.lz;

/**
 * 流转至成品入库设置其formKey
 * @author NingCG
 *
 */

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

public class LZ_ProductStock implements ExecutionListener {

	public void notify(DelegateExecution execution) throws Exception {
		execution.setVariable("url", "productStock/initProductStock.do");

	}

}
