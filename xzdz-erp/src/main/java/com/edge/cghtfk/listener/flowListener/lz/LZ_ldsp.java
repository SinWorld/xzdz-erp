package com.edge.cghtfk.listener.flowListener.lz;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.apache.ibatis.type.Alias;

/**
 * 流转至领导审批,设置其formKey
 * 
 * @author NingCG
 *
 */
@Alias("cghtsk_lz_ldsp")
public class LZ_ldsp implements ExecutionListener {

	public void notify(DelegateExecution execution) throws Exception {
		execution.setVariable("url", "cghtfk_ldsp/initLdsp.do");

	}

}
