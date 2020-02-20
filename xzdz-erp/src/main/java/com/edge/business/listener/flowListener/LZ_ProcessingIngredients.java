package com.edge.business.listener.flowListener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

/**
 * 流转加工配料连线监听器，用于设置加工配料的formKey
 * 
 * @author NingCG
 *
 */
public class LZ_ProcessingIngredients implements ExecutionListener {

	public void notify(DelegateExecution execution) throws Exception {
		execution.setVariable("url", "processingIngredients/initProcessingIngredients.do");
	}

}
