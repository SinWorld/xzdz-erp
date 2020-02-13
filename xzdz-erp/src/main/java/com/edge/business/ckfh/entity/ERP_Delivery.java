package com.edge.business.ckfh.entity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 送货实体类
 * 
 * @author NingCG
 *
 */
public class ERP_Delivery {
	private Integer delivery_Id;// 主键
	private String delivery_Code;// 编号
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date delivery_Date;// 送货时间
	private Integer songHuojbr;// 送货经办人
	private String shouHuojbr;// 收货经办人
	private Integer sales_Contract_Id;// 所属销售合同
	private Integer delivery_Customer;// 收货单位

	// 辅助属性
	private String shdwName;// 收货单位
	private String shjbr;// 送货经办人
	private String contractName;// 销售合同名称
	private String qdrq;// 签订日期

	public Integer getDelivery_Id() {
		return delivery_Id;
	}

	public void setDelivery_Id(Integer delivery_Id) {
		this.delivery_Id = delivery_Id;
	}

	public String getDelivery_Code() {
		return delivery_Code;
	}

	public void setDelivery_Code(String delivery_Code) {
		this.delivery_Code = delivery_Code;
	}

	public Date getDelivery_Date() {
		return delivery_Date;
	}

	public void setDelivery_Date(Date delivery_Date) {
		this.delivery_Date = delivery_Date;
	}

	public Integer getSongHuojbr() {
		return songHuojbr;
	}

	public void setSongHuojbr(Integer songHuojbr) {
		this.songHuojbr = songHuojbr;
	}

	public String getShouHuojbr() {
		return shouHuojbr;
	}

	public void setShouHuojbr(String shouHuojbr) {
		this.shouHuojbr = shouHuojbr;
	}

	public Integer getSales_Contract_Id() {
		return sales_Contract_Id;
	}

	public void setSales_Contract_Id(Integer sales_Contract_Id) {
		this.sales_Contract_Id = sales_Contract_Id;
	}

	public String getShdwName() {
		return shdwName;
	}

	public void setShdwName(String shdwName) {
		this.shdwName = shdwName;
	}

	public String getShjbr() {
		return shjbr;
	}

	public void setShjbr(String shjbr) {
		this.shjbr = shjbr;
	}

	public String getContractName() {
		return contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	public String getQdrq() {
		return qdrq;
	}

	public void setQdrq(String qdrq) {
		this.qdrq = qdrq;
	}

	public Integer getDelivery_Customer() {
		return delivery_Customer;
	}

	public void setDelivery_Customer(Integer delivery_Customer) {
		this.delivery_Customer = delivery_Customer;
	}

	@Override
	public String toString() {
		return "ERP_Delivery [delivery_Id=" + delivery_Id + ", delivery_Code=" + delivery_Code + ", delivery_Date="
				+ delivery_Date + ", songHuojbr=" + songHuojbr + ", shouHuojbr=" + shouHuojbr + ", sales_Contract_Id="
				+ sales_Contract_Id + ", delivery_Customer=" + delivery_Customer + ", shdwName=" + shdwName + ", shjbr="
				+ shjbr + ", contractName=" + contractName + ", qdrq=" + qdrq + "]";
	}

}
