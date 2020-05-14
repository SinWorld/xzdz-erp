package com.edge.applicationCenter.materielIdProcess.listener.eventListener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

import com.edge.applicationCenter.materielIdProcess.entity.MaterielIdProcess;
import com.edge.applicationCenter.materielIdProcess.service.inter.MaterielIdProcessService;

/**
 * 物料Id评审流程结束，用于设置该流程状态为终止
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
		// 获取MaterielIdProcessService 接口
		MaterielIdProcessService materielIdProcessService = (MaterielIdProcessService) ac
				.getBean("materielIdProcessServiceImpl");
		// 获取物料Id对象
		MaterielIdProcess materielIdProcess = materielIdProcessService
				.queryMaterielIdProcessById(Integer.parseInt(id.trim()));
		materielIdProcess.setApprovaldm(3);
		materielIdProcessService.editMaterielIdProcess(materielIdProcess);
	}

}
