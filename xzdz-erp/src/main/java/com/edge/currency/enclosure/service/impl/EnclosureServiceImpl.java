package com.edge.currency.enclosure.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.edge.currency.enclosure.dao.EnclosureDao;
import com.edge.currency.enclosure.entity.Enclosure;
import com.edge.currency.enclosure.service.inter.EnclosureService;

/**
 * 附件业务逻辑层
 * 
 * @author NingCG
 *
 */
@Service
public class EnclosureServiceImpl implements EnclosureService {

	@Resource
	private EnclosureDao enclosureDao;

	// 新增附件
	public void saveEnclosure(Enclosure enclosure) {
		enclosureDao.saveEnclosure(enclosure);
	}

	// 加载附件列表
	public List<Enclosure> enclosureList(String OBJDM) {
		return enclosureDao.enclosureList(OBJDM);
	}

}
