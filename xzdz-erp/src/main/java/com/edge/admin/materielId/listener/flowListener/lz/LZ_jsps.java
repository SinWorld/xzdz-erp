package com.edge.admin.materielId.listener.flowListener.lz;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.apache.ibatis.type.Alias;

/**
 * 流转至产品检验,用于设置其formKey
 * 
 * @author NingCG
 *
 */
@Alias("materielId_jsps")
public class LZ_jsps implements ExecutionListener {

	public void notify(DelegateExecution execution) throws Exception {
		execution.setVariable("url", "technicalReview/initTechnicalReview.do");
	}

}
