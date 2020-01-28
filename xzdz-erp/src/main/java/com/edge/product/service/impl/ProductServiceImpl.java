package com.edge.product.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.edge.business.sale.entity.ERP_Sales_Contract;
import com.edge.product.dao.ProductDao;
import com.edge.product.entity.ERP_Products;
import com.edge.product.entity.ERP_Products_QueryVo;
import com.edge.product.service.inter.ProductService;

/**
 * 成品信息业务逻辑层
 * 
 * @author NingCG
 *
 */
@Service
public class ProductServiceImpl implements ProductService {

	@Resource
	private ProductDao productDao;

	// 分页展现产品信息
	public List<ERP_Products> productList(ERP_Products_QueryVo vo) {
		return productDao.productList(vo);
	}

	// 产品数量
	public Integer productCount(ERP_Products_QueryVo vo) {
		return productDao.productCount(vo);
	}

	// 新增产品
	public void saveProduct(ERP_Products products) {
		productDao.saveProduct(products);
	}

	// 根据id获得成品对象
	public ERP_Products queryProductById(Integer product_Id) {
		return productDao.queryProductById(product_Id);
	}

	// 编辑成品
	public void editProduct(ERP_Products products) {
		productDao.editProduct(products);
	}

	// 删除成品
	public void deleteProductById(Integer product_Id) {
		productDao.deleteProductById(product_Id);
	}

	// 加载所有的销售订单
	public List<ERP_Sales_Contract> salesList() {
		return productDao.salesList();
	}

}
