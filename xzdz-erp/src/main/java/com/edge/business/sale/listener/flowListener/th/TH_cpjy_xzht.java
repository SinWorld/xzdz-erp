package com.edge.business.sale.listener.flowListener.th;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

public class TH_cpjy_xzht implements ExecutionListener {

	@Override
	public void notify(DelegateExecution execution) throws Exception {
		execution.setVariable("url", "sales/initEditSales.do");
	}

}
