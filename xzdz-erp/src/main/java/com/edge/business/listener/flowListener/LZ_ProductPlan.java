package com.edge.business.listener.flowListener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
/**
 * 退回生产计划连线监听器，用于设置生产计划的formKey
 * @author NingCG
 *
 */
public class LZ_ProductPlan implements ExecutionListener {

	public void notify(DelegateExecution execution) throws Exception {
		execution.setVariable("url", "prouctPlan/initProuctPlan.do");

	}

}
