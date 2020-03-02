package com.edge.business.listener.flowListener.lz;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

/**
 * 流转至生产领用节点，设置生产领用的formKey
 * @author NingCG
 *
 */
public class LZ_ProductionCollar  implements ExecutionListener {

	public void notify(DelegateExecution execution) throws Exception {
		execution.setVariable("url", "productionCollar/initProductionCollar.do");		
	}

}
