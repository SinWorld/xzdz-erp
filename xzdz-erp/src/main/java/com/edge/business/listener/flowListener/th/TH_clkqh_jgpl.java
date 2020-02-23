package com.edge.business.listener.flowListener.th;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

/**
 * 材料库取货退回至加工配料，用于设置加工配料的formkey
 * 
 * @author NingCG
 *
 */
public class TH_clkqh_jgpl implements ExecutionListener {

	public void notify(DelegateExecution execution) throws Exception {
		execution.setVariable("url", "processingIngredients/initEditProcessingIngredients.do");
	}

}
