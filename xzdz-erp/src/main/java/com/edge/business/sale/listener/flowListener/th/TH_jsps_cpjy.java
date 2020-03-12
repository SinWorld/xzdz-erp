package com.edge.business.sale.listener.flowListener.th;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

/**
 * 技术评审退回至新增合同，用于设置新增合同的formKey
 * 
 * @author NingCG
 *
 */
public class TH_jsps_cpjy implements ExecutionListener {

	public void notify(DelegateExecution execution) throws Exception {
		execution.setVariable("url", "cpjy/initCpjy.do");
	}

}
