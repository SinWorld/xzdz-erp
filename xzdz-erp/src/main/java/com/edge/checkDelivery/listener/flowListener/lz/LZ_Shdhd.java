package com.edge.checkDelivery.listener.flowListener.lz;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.apache.ibatis.type.Alias;

/**
 * 流转至送货单审核，用于设置其formKey
 * 
 * @author NingCG
 *
 */
@Alias("checkDelivery_lz_Shdhd")
public class LZ_Shdhd implements ExecutionListener {

	public void notify(DelegateExecution execution) throws Exception {
		execution.setVariable("url", "shdsh/initShdsh.do");

	}

}
