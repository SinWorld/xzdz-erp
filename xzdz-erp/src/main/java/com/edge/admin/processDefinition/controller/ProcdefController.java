package com.edge.admin.processDefinition.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.UserTask;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.edge.admin.processDefinition.entity.ACT_RE_Procdef;
import com.edge.admin.processDefinition.entity.Procdef_QueryVo;
import com.edge.admin.processDefinition.entity.SYS_WorkFlow_Operation;
import com.edge.admin.processDefinition.service.inter.ProcdefService;
import com.google.gson.Gson;

/**
 * 流程部署控制跳转层
 * 
 * @author NingCG
 *
 */
@Controller
@RequestMapping(value = "procdef")
public class ProcdefController {
	@Resource
	private ProcdefService procdefService;

	@Resource
	private RepositoryService repositoryService;

	// 跳转至流程部署列表页面
	@RequestMapping(value = "/initprocdefList.do")
	public String initprocdefList() {
		return "admin/processDefinition/processDefinitionList";
	}

	// easyUI 流程部署列表查询
	@RequestMapping(value = "/procdefList.do")
	@ResponseBody
	public String procdefList(@RequestParam Integer page, Integer rows) {
		// new出查询对象
		Procdef_QueryVo vo = new Procdef_QueryVo();
		Gson gson = new Gson();
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		vo.setPage((page - 1) * rows + 1);
		vo.setRows(page * rows);
		List<ACT_RE_Procdef> procdefList = procdefService.procdefList(vo);
		map.put("total", procdefService.procdefCount(vo));
		map.put("rows", procdefList);
		String json = gson.toJson(map);
		return json.toString();
	}

	// 跳转至流程新增页面
	@RequestMapping(value = "/initSaveProcdef.do")
	public String initSaveProcdef() {
		return "admin/processDefinition/saveProcess";
	}

	// 部署流程定义
	@RequestMapping(value = "/deploy.do")
	public String deploy(@RequestParam MultipartFile file, HttpServletRequest request, Model model) {
		if (!file.isEmpty()) {
			String realPath = request.getSession().getServletContext().getRealPath("/uploadFile");
			byte[] data = null;
			try {
				data = file.getBytes();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			File out = new File(realPath, file.getOriginalFilename());
			// 文件的生成
			try {
				FileUtils.writeByteArrayToFile(out, data);
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (FilenameUtils.getExtension(file.getOriginalFilename()).equals("zip")
					|| FilenameUtils.getExtension(file.getOriginalFilename()).equals("bar")) {
				ZipInputStream zipInputStream = null;
				try {
					zipInputStream = new ZipInputStream(file.getInputStream());
				} catch (IOException e) {
					e.printStackTrace();
				}
				// 部署流程定义
				DeploymentBuilder deployment = repositoryService.createDeployment();
				deployment.addZipInputStream(zipInputStream);
				deployment.deploy();

			}
		}
		model.addAttribute("flag", true);
		return "admin/processDefinition/saveProcess";
	}

	@RequestMapping(value = "/deleteByKey.do")
	@ResponseBody
	public String deleteByKey(String key) {
		JSONObject jsonObject = new JSONObject();
		// 根据key查询流程定义列表
		repositoryService.deleteDeployment(key, true);
		jsonObject.put("flag", true);
		return jsonObject.toString();
	}

	// 流程图下载
	@RequestMapping(value = "downloadImage.do")
	public String downloadImage(String id, HttpServletResponse response) throws IOException {
		// 设置response对象的头参数,attachment就是附件,filename就是文件名
		response.setHeader("Content-disposition", "attachment;filename=" + id + ".zip");
		// 下载文件类型是zip文件
		response.setContentType("application/x-zip-compressed");
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(id)
				.singleResult();
		// 部署Id
		String deploymentId = processDefinition.getDeploymentId();
		// bpmn资源文件名称
		String resourceName = processDefinition.getResourceName();
		// bpmn资源文件输入流
		InputStream inputStream_bpmn = repositoryService.getResourceAsStream(deploymentId, resourceName);
		// png文件名称
		String diagramResourceName = processDefinition.getDiagramResourceName();
		// png资源文件输入流
		InputStream inputStream_png = repositoryService.getResourceAsStream(deploymentId, diagramResourceName);
		// --创建输出流,绑定到response对象
		ServletOutputStream outputStream = response.getOutputStream();
		// 创建Zip文件输出对象,绑定到输出流
		ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream);
		// 流复制
		byte[] b = new byte[1024];
		int len = -1;
		// 定义zip文件包中的压缩对象(zip实体)
		ZipEntry zipEntry = new ZipEntry(resourceName);
		// 把创建实体对象放到压缩包中
		zipOutputStream.putNextEntry(zipEntry);
		// 文件内容拷贝
		while ((len = inputStream_bpmn.read(b, 0, 1024)) != -1) {
			zipOutputStream.write(b, 0, b.length);
		}
		zipOutputStream.closeEntry();
		ZipEntry zipEntry1 = new ZipEntry(diagramResourceName);
		zipOutputStream.putNextEntry(zipEntry1);
		while ((len = inputStream_png.read(b, 0, 1024)) != -1) {
			zipOutputStream.write(b, 0, b.length);
		}
		// 关闭流
		inputStream_bpmn.close();
		inputStream_png.close();
		zipOutputStream.flush();
		zipOutputStream.flush();
		outputStream.flush();
		outputStream.close();
		return null;
	}

	// 显示流程图（文件下载）
	@RequestMapping(value = "/showPng.do")
	public String showPng(@RequestParam String pdId, HttpServletResponse response) {
		// 显示流程图
		InputStream in = repositoryService.getProcessDiagram(pdId);
		response.setContentType("image/png");
		response.setCharacterEncoding("utf-8");
		try {
			ServletOutputStream outputStream = response.getOutputStream();
			int len = 0;
			byte[] buf = new byte[1024];
			while ((len = in.read(buf, 0, 1024)) != -1) {
				outputStream.write(buf, 0, len);
			}
			outputStream.close();
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "admin/processDefinition/ShowImage";
	}

	// 批量删除(物理删除)
	@RequestMapping(value = "/batchDeleteProcedf.do")
	@ResponseBody
	public String batchDeleteProcedf(String ids) {
		JSONObject jsonObject = new JSONObject();
		// 将ids拆分为字符组
		String[] depIds = ids.split(",");
		// 遍历该数组
		for (String id : depIds) {
			// 进行删除
			repositoryService.deleteDeployment(id, true);
		}
		jsonObject.put("flag", true);
		return jsonObject.toString();
	}

}
