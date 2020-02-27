package com.edge.business.listener.flowListener.lz;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

/**
 * 流转至领导审核（材料）设置该节点的formkey
 * 
 * @author NingCG
 *
 */
public class LZ_LeadershipAuditMaterial implements ExecutionListener {

	public void notify(DelegateExecution execution) throws Exception {
		execution.setVariable("url", "leadershipAuditMaterial/initLeadershipAuditMaterial.do");
	}

}
