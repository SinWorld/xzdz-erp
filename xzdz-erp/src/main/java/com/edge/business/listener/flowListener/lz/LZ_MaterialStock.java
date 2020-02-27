package com.edge.business.listener.flowListener.lz;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

/**
 * 流转至材料入库用于设置材料入库的formKey
 * 
 * @author NingCG
 *
 */
public class LZ_MaterialStock implements ExecutionListener {

	@Override
	public void notify(DelegateExecution execution) throws Exception {
		execution.setVariable("url", "materialStock/initMaterialStock.do");
	}

}
