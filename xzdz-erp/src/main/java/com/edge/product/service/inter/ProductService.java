package com.edge.product.service.inter;

import java.util.List;

import com.edge.business.sale.entity.ERP_Sales_Contract;
import com.edge.product.entity.ERP_Products;
import com.edge.product.entity.ERP_Products_QueryVo;

public interface ProductService {
	// 分页展现产品信息
	public List<ERP_Products> productList(ERP_Products_QueryVo vo);

	// 产品数量
	public Integer productCount(ERP_Products_QueryVo vo);

	// 新增产品
	public void saveProduct(ERP_Products products);

	// 根据id获得成品对象
	public ERP_Products queryProductById(Integer product_Id);

	// 编辑成品
	public void editProduct(ERP_Products products);

	// 删除成品(物理删除)
	public void deleteProductById(Integer product_Id);

	// 加载所有的销售订单
	public List<ERP_Sales_Contract> salesList();

	// 获得刚入库的成品主键
	public Integer queryMaxProductId();

	// 根据销售Id获得成品集合
	public List<ERP_Products> queryProductsByXsht(Integer sales_Contract_Id);
}
