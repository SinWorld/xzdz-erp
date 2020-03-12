package com.edge.stocks.product.kc.entity;

/**
 * 库存警报实体类
 * 
 * @author NingCG
 *
 */
public class ERP_WarnStock {

	private Integer kcl;// 库存量
	private String materielId;// 物料Id

	public Integer getKcl() {
		return kcl;
	}

	public void setKcl(Integer kcl) {
		this.kcl = kcl;
	}

	public String getMaterielId() {
		return materielId;
	}

	public void setMaterielId(String materielId) {
		this.materielId = materielId;
	}

	@Override
	public String toString() {
		return "ERP_WarnStock [kcl=" + kcl + ", materielId=" + materielId + "]";
	}

}
