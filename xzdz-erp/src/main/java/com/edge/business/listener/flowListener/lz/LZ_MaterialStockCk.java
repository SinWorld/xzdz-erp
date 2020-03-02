package com.edge.business.listener.flowListener.lz;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

/**
 * 流转至材料出库，设置材料出库的formKey
 * 
 * @author NingCG
 *
 */
public class LZ_MaterialStockCk implements ExecutionListener {

	public void notify(DelegateExecution execution) throws Exception {
		execution.setVariable("url", "materialStockCk/initMaterialStockCk.do");

	}

}
