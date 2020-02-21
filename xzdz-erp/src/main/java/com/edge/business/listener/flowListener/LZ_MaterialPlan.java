package com.edge.business.listener.flowListener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

/**
 * 流转材料计划连线监听器，用于设置出库发货的formKey
 * 
 * @author NingCG
 *
 */
public class LZ_MaterialPlan implements ExecutionListener {

	public void notify(DelegateExecution execution) throws Exception {
		execution.setVariable("url", "materialPlan/initMaterialPlan.do");

	}

}
