package com.edge.stocks.product.kc.entity;

/**
 * 库存状态实体类
 * 
 * @author NingCG
 *
 */
public class ERP_Stock_Status {
	private Integer row_Id;// 主键
	private Integer product_Id;// 成品主键
	private boolean stock_Type;// 成品类型(0:成品1:材料)
	private String status;// 库存状态
	private String oddNumbers;// 单号

	public Integer getRow_Id() {
		return row_Id;
	}

	public void setRow_Id(Integer row_Id) {
		this.row_Id = row_Id;
	}

	public Integer getProduct_Id() {
		return product_Id;
	}

	public void setProduct_Id(Integer product_Id) {
		this.product_Id = product_Id;
	}

	public boolean isStock_Type() {
		return stock_Type;
	}

	public void setStock_Type(boolean stock_Type) {
		this.stock_Type = stock_Type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOddNumbers() {
		return oddNumbers;
	}

	public void setOddNumbers(String oddNumbers) {
		this.oddNumbers = oddNumbers;
	}

	@Override
	public String toString() {
		return "ERP_Stock_Status [row_Id=" + row_Id + ", product_Id=" + product_Id + ", stock_Type=" + stock_Type
				+ ", status=" + status + ", oddNumbers=" + oddNumbers + "]";
	}

}
