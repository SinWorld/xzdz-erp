package com.edge.admin.materielId.listener.eventListener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

import com.edge.admin.materielId.entity.ERP_MaterielId;
import com.edge.admin.materielId.service.inter.MaterielIdService;

/**
 * 物料Id流程结束，用于设置该流程状态为终止
 * 
 * @author NingCG
 *
 */
public class MaterielIdOverListener implements JavaDelegate {

	public void execute(DelegateExecution execution) throws Exception {
		// 取得businessKey
		String key = execution.getProcessBusinessKey();
		// 得到业务数据主键值
		String id = key.substring(key.indexOf(".") + 1);
		// 2.从spring容器中获取UserServiceImpl
		ApplicationContext ac = ContextLoader.getCurrentWebApplicationContext();
		// 获取MaterielIdService 接口
		MaterielIdService materielIdService = (MaterielIdService) ac.getBean("materielIdServiceImpl");
		// 获取物料Id对象
		ERP_MaterielId materielId = materielIdService.queryMaterielIdById(Integer.parseInt(id.trim()));
		materielId.setApprovaldm(3);
		materielIdService.editMaterielId(materielId);
	}

}
