package com.edge.applicationCenter.materielIdProcess.listener.flowListener.lz;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

public class LZ_MaterielId implements ExecutionListener {

	public void notify(DelegateExecution execution) throws Exception {
		execution.setVariable("url", "materielIdProcess/initEditMaterielIdProcess.do");
	}

}
