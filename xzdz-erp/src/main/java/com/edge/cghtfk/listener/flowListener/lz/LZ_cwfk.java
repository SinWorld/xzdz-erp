package com.edge.cghtfk.listener.flowListener.lz;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.apache.ibatis.type.Alias;

/**
 * 流转至财务付款,设置其formKey
 * 
 * @author NingCG
 *
 */
@Alias("cghtsk_lz_cwfk")
public class LZ_cwfk implements ExecutionListener {

	public void notify(DelegateExecution execution) throws Exception {
		execution.setVariable("url", "cghtfk_cwfk/initCwfk.do");

	}

}
