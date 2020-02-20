package com.edge.business.productionPlan.entity;

import com.edge.product.entity.ERP_Products;

/**
 * 生产计划 购物项
 * 
 * @author NingCG
 *
 */
public class ProductionPlanOrder {
	private Integer row_Id;// 主键
	private String materielId;// 物料id
	private Integer product;// 成品Id
	private Integer productionPlanId;// 生产计划Id
	private Integer scsl;// 生产数量
	private ERP_Products erp_product;// 成品

	public Integer getRow_Id() {
		return row_Id;
	}

	public void setRow_Id(Integer row_Id) {
		this.row_Id = row_Id;
	}

	public String getMaterielId() {
		return materielId;
	}

	public void setMaterielId(String materielId) {
		this.materielId = materielId;
	}

	public Integer getProduct() {
		return product;
	}

	public void setProduct(Integer product) {
		this.product = product;
	}

	public Integer getProductionPlanId() {
		return productionPlanId;
	}

	public void setProductionPlanId(Integer productionPlanId) {
		this.productionPlanId = productionPlanId;
	}

	public Integer getScsl() {
		return scsl;
	}

	public void setScsl(Integer scsl) {
		this.scsl = scsl;
	}

	public ERP_Products getErp_product() {
		return erp_product;
	}

	public void setErp_product(ERP_Products erp_product) {
		this.erp_product = erp_product;
	}

	@Override
	public String toString() {
		return "ProductionPlanOrder [row_Id=" + row_Id + ", materielId=" + materielId + ", product=" + product
				+ ", productionPlanId=" + productionPlanId + ", scsl=" + scsl + ", erp_product=" + erp_product + "]";
	}

}
