package com.edge.business.listener.flowListener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

/**
 * 退回到加工配料连线监听器，用于设置加工配料的formKey
 * 
 * @author NingCG
 *
 */
public class TH_ProcessingIngredients implements ExecutionListener {

	public void notify(DelegateExecution execution) throws Exception {
		execution.setVariable("url", "processingIngredients/initEditProcessingIngredients.do");
	}

}
