package com.edge.currency.jobTask.controller;

import java.io.File;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 创建指定的文件夹及类class文件
 * 
 * @author NingCG
 *
 */
@Controller
@RequestMapping(value = "createJobTask")
public class CreateJobTaskController {

	// 创建指定包结构及class文件
	@RequestMapping(value = "/createJobTaskFile.do")
	public String createJobTaskFile(@RequestParam String path, String fileName) {
		File catalog = new File("D:\\data\\GitHub\\xzdz-erp\\xzdz-erp\\xzdz-erp\\src\\main\\java\\" + path + "\\");
		if (!catalog.exists()) {// 如果文件夹不存在
			catalog.mkdir();// 创建文件夹
		}
		File file = new File(catalog.getPath() + fileName + ".java");
		return null;
	}

}
