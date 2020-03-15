package com.edge.business.listener.flowListener.th;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

/**
 * 出库发货退回到成品核对连线监听器，用于设置成品核对的formKey
 * 
 * @author NingCG
 *
 */
public class TH_ckfh_cphd implements ExecutionListener {

	public void notify(DelegateExecution execution) throws Exception {
		execution.setVariable("url", "initCheckProduct/initEditCheckProduct.do");

	}
}
