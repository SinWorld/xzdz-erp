package com.edge.business.listener.flowListener.lz;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

/**
 * 流转材料检验连线监听器，用于设置成品核对的formKey
 * 
 * @author NingCG
 *
 */
public class LZ_Bhgclfk_Cljy implements ExecutionListener {

	public void notify(DelegateExecution execution) throws Exception {
		execution.setVariable("url", "checkMaterial/initEditCheckMaterial.do");
	}

}
