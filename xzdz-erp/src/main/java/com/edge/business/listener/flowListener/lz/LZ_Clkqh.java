package com.edge.business.listener.flowListener.lz;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

/**
 * 流转材料库取货连线监听器，用于设置出库发货的formKey
 * 
 * @author NingCG
 *
 */
public class LZ_Clkqh implements ExecutionListener {

	public void notify(DelegateExecution execution) throws Exception {
		execution.setVariable("url", "clkqh/initClkqh.do");
	}

}
