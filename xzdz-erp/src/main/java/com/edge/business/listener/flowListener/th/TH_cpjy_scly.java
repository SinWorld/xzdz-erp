package com.edge.business.listener.flowListener.th;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
/**
 * 成品检验退回至生产领用，用于设置其formKey
 * @author NingCG
 *
 */
public class TH_cpjy_scly implements ExecutionListener {

	public void notify(DelegateExecution execution) throws Exception {
		execution.setVariable("url", "productionCollar/initProductionCollar.do");
	}

}
