package com.edge.xshtsk.listner.flowListener.lz;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.apache.ibatis.type.Alias;

/**
 * 流转至领导审核，用于设置其formKey
 * @author NingCG
 *
 */
@Alias("xshtsk_ldsh")
public class LZ_ldsh implements ExecutionListener{

	public void notify(DelegateExecution execution) throws Exception {
		execution.setVariable("url", "ldsh/initLdsh.do");		
	}

}
