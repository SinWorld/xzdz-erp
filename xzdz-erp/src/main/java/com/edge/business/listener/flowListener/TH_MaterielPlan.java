package com.edge.business.listener.flowListener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
/**
 *  退回到材料计划连线监听器，用于设置材料计划的formKey
 * @author NingCG
 *
 */
public class TH_MaterielPlan implements ExecutionListener {

	@Override
	public void notify(DelegateExecution execution) throws Exception {
		execution.setVariable("url", "checkProduct/initEditCheckProduct.do");
	}

}
