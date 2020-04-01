package com.edge.currency.enclosure.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.edge.currency.enclosure.entity.Enclosure;

public interface EnclosureDao {
	// 新增附件
	public void saveEnclosure(Enclosure enclosure);

	// 加载附件列表
	public List<Enclosure> enclosureList(@Param("OBJDM") String OBJDM);

	// 根据Id删除附件
	public void deleteFjByObj(@Param("FUJIANDM") Integer FUJIANDM);

}
