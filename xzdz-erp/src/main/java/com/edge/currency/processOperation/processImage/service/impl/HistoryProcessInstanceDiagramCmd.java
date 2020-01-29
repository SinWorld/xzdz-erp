package com.edge.currency.processOperation.processImage.service.impl;

import java.io.*;

import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;

import com.edge.currency.processOperation.processImage.entiy.CustomProcessDiagramGenerator;

public class HistoryProcessInstanceDiagramCmd implements Command<InputStream> {

	protected String historyProcessInstanceId;

	public HistoryProcessInstanceDiagramCmd(String historyProcessInstanceId) {
		this.historyProcessInstanceId = historyProcessInstanceId;
	}

	public InputStream execute(CommandContext commandContext) {
		try {
			CustomProcessDiagramGenerator customProcessDiagramGenerator = new CustomProcessDiagramGenerator();

			return customProcessDiagramGenerator.generateDiagram(historyProcessInstanceId);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

}
