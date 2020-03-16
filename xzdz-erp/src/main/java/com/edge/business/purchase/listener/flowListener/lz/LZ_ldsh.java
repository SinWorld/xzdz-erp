package com.edge.business.purchase.listener.flowListener.lz;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
/**
 * 流转至领导审核，用于设置formKey
 * @author NingCG
 *
 */
public class LZ_ldsh implements ExecutionListener {

	public void notify(DelegateExecution execution) throws Exception {
		execution.setVariable("url", "purchaseOrder/initLdsh.do");
	}

}
