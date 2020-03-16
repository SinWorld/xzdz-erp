package com.edge.business.checkProduct.entity;

/**
 * 评审意见成品核对
 * 
 * @author NingCG
 *
 */
public class SYS_WorkFlow_Cphd implements Comparable<SYS_WorkFlow_Cphd> {
	private Integer cphd_Id;// 主键
	private String cphd_Cpmc;// 成品名称
	private String cphd_Ggxh;// 规格型号
	private String cphd_Kw;// 库位
	private Integer cphd_Kcsl;// 库存数量
	private Integer cphd_Cksl;// 出库数量
	private Integer cphd_ObjId;// 评审意见外键

	public Integer getCphd_Id() {
		return cphd_Id;
	}

	public void setCphd_Id(Integer cphd_Id) {
		this.cphd_Id = cphd_Id;
	}

	public String getCphd_Cpmc() {
		return cphd_Cpmc;
	}

	public void setCphd_Cpmc(String cphd_Cpmc) {
		this.cphd_Cpmc = cphd_Cpmc;
	}

	public String getCphd_Ggxh() {
		return cphd_Ggxh;
	}

	public void setCphd_Ggxh(String cphd_Ggxh) {
		this.cphd_Ggxh = cphd_Ggxh;
	}

	public String getCphd_Kw() {
		return cphd_Kw;
	}

	public void setCphd_Kw(String cphd_Kw) {
		this.cphd_Kw = cphd_Kw;
	}

	public Integer getCphd_Kcsl() {
		return cphd_Kcsl;
	}

	public void setCphd_Kcsl(Integer cphd_Kcsl) {
		this.cphd_Kcsl = cphd_Kcsl;
	}

	public Integer getCphd_Cksl() {
		return cphd_Cksl;
	}

	public void setCphd_Cksl(Integer cphd_Cksl) {
		this.cphd_Cksl = cphd_Cksl;
	}

	public Integer getCphd_ObjId() {
		return cphd_ObjId;
	}

	public void setCphd_ObjId(Integer cphd_ObjId) {
		this.cphd_ObjId = cphd_ObjId;
	}

	@Override
	public String toString() {
		return "SYS_WorkFlow_Cphd [cphd_Id=" + cphd_Id + ", cphd_Cpmc=" + cphd_Cpmc + ", cphd_Ggxh=" + cphd_Ggxh
				+ ", cphd_Kw=" + cphd_Kw + ", cphd_Kcsl=" + cphd_Kcsl + ", cphd_Cksl=" + cphd_Cksl + ", cphd_ObjId="
				+ cphd_ObjId + "]";
	}

	public int compareTo(SYS_WorkFlow_Cphd c) {
		if (this.cphd_Id > c.cphd_Id) {
			return 1;
		} else {
			return -1;
		}
	}

}
