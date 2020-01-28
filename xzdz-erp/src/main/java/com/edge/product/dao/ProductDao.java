package com.edge.product.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.edge.business.sale.entity.ERP_Sales_Contract;
import com.edge.product.entity.ERP_Products;
import com.edge.product.entity.ERP_Products_QueryVo;

public interface ProductDao {
	// 分页展现产品信息
	public List<ERP_Products> productList(ERP_Products_QueryVo vo);

	// 产品数量
	public Integer productCount(ERP_Products_QueryVo vo);

	// 新增产品
	public void saveProduct(ERP_Products products);

	// 根据id获得成品对象
	public ERP_Products queryProductById(@Param("product_Id") Integer product_Id);

	// 编辑成品
	public void editProduct(ERP_Products products);

	// 删除成品(物理删除)
	public void deleteProductById(@Param("product_Id") Integer product_Id);

	// 加载所有的销售订单
	// TODO销售订单应是流程未完成的状态（以后再写）
	public List<ERP_Sales_Contract> salesList();
}
