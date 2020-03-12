package com.edge.business.purchase.listener.flowListener.th;

import org.activiti.engine.delegate.DelegateExecution;

import org.activiti.engine.delegate.ExecutionListener;
import org.apache.ibatis.type.Alias;

@Alias("aaaa")
public class TH_ldsh_fqcg implements ExecutionListener {

	public void notify(DelegateExecution execution) throws Exception {
		execution.setVariable("url", "purchaseOrder/initEditPurchase.do");
	}

}
