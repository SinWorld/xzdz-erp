package com.edge.currency.enclosure.entity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 附件实体类
 * 
 * @author NingCG
 *
 */
public class Enclosure {
	private Integer FUJIANDM;// 主键
	private String CUNCHUWJM;// 上传文件名
	private Integer SHANGCHUANYHDM;// 上传用户
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date SHANGCHUANRQ;// 上传日期
	private String OBJDM;// 业务主键
	private String SHANGCHUANDZ;// 上传文件地址
	private String REALWJM;// 文件真实名称

	public Integer getFUJIANDM() {
		return FUJIANDM;
	}

	public void setFUJIANDM(Integer fUJIANDM) {
		FUJIANDM = fUJIANDM;
	}

	public String getCUNCHUWJM() {
		return CUNCHUWJM;
	}

	public void setCUNCHUWJM(String cUNCHUWJM) {
		CUNCHUWJM = cUNCHUWJM;
	}

	public Integer getSHANGCHUANYHDM() {
		return SHANGCHUANYHDM;
	}

	public void setSHANGCHUANYHDM(Integer sHANGCHUANYHDM) {
		SHANGCHUANYHDM = sHANGCHUANYHDM;
	}

	public Date getSHANGCHUANRQ() {
		return SHANGCHUANRQ;
	}

	public void setSHANGCHUANRQ(Date sHANGCHUANRQ) {
		SHANGCHUANRQ = sHANGCHUANRQ;
	}

	public String getOBJDM() {
		return OBJDM;
	}

	public void setOBJDM(String oBJDM) {
		OBJDM = oBJDM;
	}

	public String getSHANGCHUANDZ() {
		return SHANGCHUANDZ;
	}

	public void setSHANGCHUANDZ(String sHANGCHUANDZ) {
		SHANGCHUANDZ = sHANGCHUANDZ;
	}

	public String getREALWJM() {
		return REALWJM;
	}

	public void setREALWJM(String rEALWJM) {
		REALWJM = rEALWJM;
	}

	@Override
	public String toString() {
		return "Enclosure [FUJIANDM=" + FUJIANDM + ", CUNCHUWJM=" + CUNCHUWJM + ", SHANGCHUANYHDM=" + SHANGCHUANYHDM
				+ ", SHANGCHUANRQ=" + SHANGCHUANRQ + ", OBJDM=" + OBJDM + ", SHANGCHUANDZ=" + SHANGCHUANDZ
				+ ", REALWJM=" + REALWJM + "]";
	}

}
