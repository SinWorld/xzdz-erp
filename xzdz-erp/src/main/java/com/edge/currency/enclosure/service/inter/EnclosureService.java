package com.edge.currency.enclosure.service.inter;

import java.util.List;

import com.edge.currency.enclosure.entity.Enclosure;

public interface EnclosureService {
	// 新增附件
	public void saveEnclosure(Enclosure enclosure);

	// 加载附件列表
	public List<Enclosure> enclosureList(String OBJDM);
}
