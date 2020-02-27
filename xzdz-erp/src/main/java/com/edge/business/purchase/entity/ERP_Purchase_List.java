package com.edge.business.purchase.entity;

/**
 * 采购清单实体类
 * 
 * @author NingCG
 *
 */
public class ERP_Purchase_List {
	private Integer pur_Id;// 主键
	private Integer pur_Order_Id;// 采购订单
	private String pro_Name;// 品名
	private String model;// 型号
	private String delivery_date;// 交货日期
	private String company;// 单位
	private String map_Number;// 图号
	private String bz;// 备注
	private Double price;// 单价
	private Integer sl;// 数量
	private Double zje;// 总金额
	private String materielId;// 物料id
	private Integer xshtdm;// 销售合同id
	private String status;

	public Integer getPur_Id() {
		return pur_Id;
	}

	public void setPur_Id(Integer pur_Id) {
		this.pur_Id = pur_Id;
	}

	public Integer getPur_Order_Id() {
		return pur_Order_Id;
	}

	public void setPur_Order_Id(Integer pur_Order_Id) {
		this.pur_Order_Id = pur_Order_Id;
	}

	public String getPro_Name() {
		return pro_Name;
	}

	public void setPro_Name(String pro_Name) {
		this.pro_Name = pro_Name;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getDelivery_date() {
		return delivery_date;
	}

	public void setDelivery_date(String delivery_date) {
		this.delivery_date = delivery_date;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getMap_Number() {
		return map_Number;
	}

	public void setMap_Number(String map_Number) {
		this.map_Number = map_Number;
	}

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getSl() {
		return sl;
	}

	public void setSl(Integer sl) {
		this.sl = sl;
	}

	public Double getZje() {
		return zje;
	}

	public void setZje(Double zje) {
		this.zje = zje;
	}

	public String getMaterielId() {
		return materielId;
	}

	public void setMaterielId(String materielId) {
		this.materielId = materielId;
	}

	public Integer getXshtdm() {
		return xshtdm;
	}

	public void setXshtdm(Integer xshtdm) {
		this.xshtdm = xshtdm;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "ERP_Purchase_List [pur_Id=" + pur_Id + ", pur_Order_Id=" + pur_Order_Id + ", pro_Name=" + pro_Name
				+ ", model=" + model + ", delivery_date=" + delivery_date + ", company=" + company + ", map_Number="
				+ map_Number + ", bz=" + bz + ", price=" + price + ", sl=" + sl + ", zje=" + zje + ", materielId="
				+ materielId + ", xshtdm=" + xshtdm + ", status=" + status + "]";
	}

}
