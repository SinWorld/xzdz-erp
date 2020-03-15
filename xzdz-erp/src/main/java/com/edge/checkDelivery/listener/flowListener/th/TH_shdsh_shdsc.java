package com.edge.checkDelivery.listener.flowListener.th;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.apache.ibatis.type.Alias;

/**
 * 送货单审核退回至送货单上传,用于设置其formKey
 * 
 * @author NingCG
 *
 */
@Alias("checkDelivery_th_shdsh_shdsc")
public class TH_shdsh_shdsc implements ExecutionListener {

	public void notify(DelegateExecution execution) throws Exception {
		execution.setVariable("url", "checkDelivery/initEditCheckDelivery.do");

	}

}
