package com.edge.cghtfk.listener.flowListener.th;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.apache.ibatis.type.Alias;

/**
 * 流转至发起付款申请,用于设置其formKey
 * 
 * @author NingCG
 *
 */
@Alias("cghtsk_th_fqfksq")
public class TH_fqfksq implements ExecutionListener {

	public void notify(DelegateExecution execution) throws Exception {
		execution.setVariable("url", "cghtfk/initEditCghtfk.do");

	}

}
