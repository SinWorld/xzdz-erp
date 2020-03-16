package com.edge.cghtfk.entity;

/**
 * 查询对象
 * 
 * @author NingCG
 *
 */
public class ERP_Cghtfk_QueryVo {
	private Integer page;// 当前页
	private Integer rows;// 每页显示数量

	private Integer cght;
	private String fklx;
	private String ysqk;
	private Double fkje1;
	private Double fkje2;
	private String sqfkrq1;
	private String sqfkrq2;
	private String fkrq1;
	private String fkrq2;
	private Integer spzt;

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public Integer getCght() {
		return cght;
	}

	public void setCght(Integer cght) {
		this.cght = cght;
	}

	public String getFklx() {
		return fklx;
	}

	public void setFklx(String fklx) {
		this.fklx = fklx;
	}

	public String getYsqk() {
		return ysqk;
	}

	public void setYsqk(String ysqk) {
		this.ysqk = ysqk;
	}

	public Double getFkje1() {
		return fkje1;
	}

	public void setFkje1(Double fkje1) {
		this.fkje1 = fkje1;
	}

	public Double getFkje2() {
		return fkje2;
	}

	public void setFkje2(Double fkje2) {
		this.fkje2 = fkje2;
	}

	public String getSqfkrq1() {
		return sqfkrq1;
	}

	public void setSqfkrq1(String sqfkrq1) {
		this.sqfkrq1 = sqfkrq1;
	}

	public String getSqfkrq2() {
		return sqfkrq2;
	}

	public void setSqfkrq2(String sqfkrq2) {
		this.sqfkrq2 = sqfkrq2;
	}

	public Integer getSpzt() {
		return spzt;
	}

	public void setSpzt(Integer spzt) {
		this.spzt = spzt;
	}

	public String getFkrq1() {
		return fkrq1;
	}

	public void setFkrq1(String fkrq1) {
		this.fkrq1 = fkrq1;
	}

	public String getFkrq2() {
		return fkrq2;
	}

	public void setFkrq2(String fkrq2) {
		this.fkrq2 = fkrq2;
	}

	@Override
	public String toString() {
		return "ERP_Cghtfk_QueryVo [page=" + page + ", rows=" + rows + ", cght=" + cght + ", fklx=" + fklx + ", ysqk="
				+ ysqk + ", fkje1=" + fkje1 + ", fkje2=" + fkje2 + ", sqfkrq1=" + sqfkrq1 + ", sqfkrq2=" + sqfkrq2
				+ ", fkrq1=" + fkrq1 + ", fkrq2=" + fkrq2 + ", spzt=" + spzt + "]";
	}

}
