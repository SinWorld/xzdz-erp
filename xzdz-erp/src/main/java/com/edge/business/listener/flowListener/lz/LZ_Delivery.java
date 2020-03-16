package com.edge.business.listener.flowListener.lz;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

/**
 * 流转出库发货连线监听器，用于设置出库发货的formKey
 * 
 * @author NingCG
 *
 */
public class LZ_Delivery implements ExecutionListener {

	public void notify(DelegateExecution execution) throws Exception {
		execution.setVariable("url", "delivery/initDelivery.do");
	}

}
