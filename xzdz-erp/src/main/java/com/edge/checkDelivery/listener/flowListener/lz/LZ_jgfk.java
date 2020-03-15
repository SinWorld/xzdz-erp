package com.edge.checkDelivery.listener.flowListener.lz;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.apache.ibatis.type.Alias;

/**
 * 流转至结果反馈，用于设置其formKey
 * 
 * @author NingCG
 *
 */
@Alias("checkDelivery_lz_jgfk")
public class LZ_jgfk implements ExecutionListener {

	public void notify(DelegateExecution execution) throws Exception {
		execution.setVariable("url", "jgfk/initJgfk.do");

	}
}
