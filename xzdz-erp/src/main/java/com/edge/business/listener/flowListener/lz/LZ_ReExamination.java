package com.edge.business.listener.flowListener.lz;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

/**
 * 领导审核(材料)重新检验
 * 
 * @author NingCG
 *
 */
public class LZ_ReExamination implements ExecutionListener {

	public void notify(DelegateExecution execution) throws Exception {
		execution.setVariable("url", "checkMaterial/initReExamination.do");
	}

}
