package com.edge.business.listener.flowListener.lz;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

/**
 * 流转至领导审核（成品）设置该节点的formkey
 * 
 * @author NingCG
 *
 */
public class LZ_LeadershipAuditProduct implements ExecutionListener {

	public void notify(DelegateExecution execution) throws Exception {
		execution.setVariable("url", "leadershipAuditProduct/initLeadershipAuditProduct.do");

	}

}
