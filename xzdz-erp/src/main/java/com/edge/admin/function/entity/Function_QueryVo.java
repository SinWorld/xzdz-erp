package com.edge.admin.function.entity;

/**
 * 查询对象
 * 
 * @author NingCG
 *
 */
public class Function_QueryVo {
	private Integer page;// 当前页
	private Integer rows;// 每页显示数量

	private String gnmc;// 功能名称
	private String gndz;// 功能地址
	private Integer sjgn;// 上级功能

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

	public String getGnmc() {
		return gnmc;
	}

	public void setGnmc(String gnmc) {
		this.gnmc = gnmc;
	}

	public String getGndz() {
		return gndz;
	}

	public void setGndz(String gndz) {
		this.gndz = gndz;
	}

	public Integer getSjgn() {
		return sjgn;
	}

	public void setSjgn(Integer sjgn) {
		this.sjgn = sjgn;
	}

	@Override
	public String toString() {
		return "Function_QueryVo [page=" + page + ", rows=" + rows + ", gnmc=" + gnmc + ", gndz=" + gndz + ", sjgn="
				+ sjgn + "]";
	}

}
