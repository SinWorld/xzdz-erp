package com.edge.business.listener.flowListener.lz;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

/**
 * 流转至不合格材料反馈，用于设置该节点的formKey
 * 
 * @author NingCG
 *
 */
public class LZ_UnqualifiedMaterial implements ExecutionListener {

	public void notify(DelegateExecution execution) throws Exception {
		execution.setVariable("url", "unqualifiedMaterial/initUnqualifiedMaterial.do");

	}

}
