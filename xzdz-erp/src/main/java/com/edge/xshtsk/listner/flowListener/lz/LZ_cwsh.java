package com.edge.xshtsk.listner.flowListener.lz;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

/**
 * 流转至财务审核，用于设置其formKey
 * 
 * @author NingCG
 *
 */
public class LZ_cwsh implements ExecutionListener {

	public void notify(DelegateExecution execution) throws Exception {
		execution.setVariable("url", "cwsh/initCwsh.do");
	}

}
