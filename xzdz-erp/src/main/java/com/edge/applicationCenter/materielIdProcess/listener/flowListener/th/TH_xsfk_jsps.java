package com.edge.applicationCenter.materielIdProcess.listener.flowListener.th;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

public class TH_xsfk_jsps implements ExecutionListener {

	public void notify(DelegateExecution execution) throws Exception {
		execution.setVariable("url", "technicalReview/initEditTechnicalReview.do");
	}

}
