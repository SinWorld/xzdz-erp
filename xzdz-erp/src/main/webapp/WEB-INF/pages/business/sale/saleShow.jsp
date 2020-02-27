<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8">
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">
<title>销售合同查看页</title>
<link rel="stylesheet" href="../layui-v2.5.5/layui/css/layui.css">
<link rel="stylesheet" href="../login/css/static.css">
<link rel="stylesheet" href="../bootstrap-3.3.7-dist/css/bootstrap.min.css"> 
<link rel="stylesheet" href="../bootstrap-3.3.7-dist/css/bootstrap-theme.min.css"> 
<script src="../jquery/jquery-3.3.1.js"></script>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@page isELIgnored="false" %>
<style>
.bj{background-color: #F0F0F0}
</style>
</head>
<body style="width:100%;padding:0px; margin:0px;">
	<div class="layui-tab">
		  <ul class="layui-tab-title">
		    <li class="layui-this">基本信息</li>
		    <li>评审意见</li>
		    <li>任务附件</li>
		    <li>流程检视</li> 
		    <li>报表打印</li>
		  </ul>
		<div class="layui-tab-content">
		 <div class="layui-tab-item layui-show">
		   	<div style="width:1280px;height:auto;padding:0px; margin:0 auto;" id="main">
					<form class="layui-form" action='<c:url value=""/>' method="post" id="form">
						<input type="hidden" id="url" value='<c:url value="/"/>'>
					    <input type="hidden" value="${taskId }" id="taskId">
					     <input type="hidden" value="${processInstanceId }" id="processInstanceId">
					    <input type="hidden" value="${contract.sales_Contract_Id }" id="sales_Contract_Id">
						<input type="hidden" id="fjsx" name="fjsx"> 
						<input type="hidden" id="OBJDM" value="${OBJDM}">
						<input type="hidden" value="${ldsh}" id="ldsh"> 
					
						<div class="layui-form-item" style="margin-top: 2%;">
						    <label class="layui-form-label" style="width: 122px;">合同名称</label>
						    <div class="layui-input-block">
						      <input type="text" id="sales_Contract_Name" lay-verify="sales_Contract_Name" autocomplete="off" placeholder="合同名称" class="layui-input bj" style="width:74.5%" value="${contract.sales_Contract_Name}" disabled="">
						    </div>
						</div>
			
					
						<div class="layui-form-item">
						     <div class="layui-inline" style="top:9px;left: -29px;">
							      <label class="layui-form-label" style="width:150px;">供货单位</label>
							      <div class="layui-input-inline">
							        <input type="text"  autocomplete="off" class="layui-input bj" style="width: 280px;" disabled="" value="${our_Unit.unit_Name}">
							      </div>
						     </div>
						     
						      <div class="layui-inline" style="top:9px;left: 200px;">
							      <label class="layui-form-label" style="width:150px;">合同编号</label>
							      <div class="layui-input-inline">
							        <input type="text"   autocomplete="off" class="layui-input bj" style="width: 280px;" disabled="" value="${contract.contract_Code}">
							      </div>
						     </div>
						 </div>
					 
						 <div class="layui-form-item">
						      <div class="layui-inline" style="left: -29px;">
							      <label class="layui-form-label" style="width:150px;">需方单位</label>
							      <div class="layui-input-inline">
							        <input type="text"  autocomplete="off" class="layui-input bj" style="width: 280px;" disabled="" value="${customer.unit_Name}">
							      </div>
						     </div>
						     
						      <div class="layui-inline" style="left:210px;">
							      <label class="layui-form-label" style="width: 139px;">签订日期</label>
							      <div class="layui-input-inline">
							        <input type="text"  autocomplete="off" class="layui-input bj" style="width: 280px;" disabled="" value="${qdrq}">
							      </div>
						    </div>
						 </div>
					 
						  <div class="layui-form-item layui-form-text">
					  		<label class="layui-form-label" style="width: 295px;">一、产品名称、规格型号、单价</label>
							  <div class="layui-input-block" style="top:15px;left: 10px;">
								<table class="table table-bordered" id="khlxrs" style="width: 100%">
								  <thead>
								    <tr>
								      <th scope="col" style="text-align: center;width: 5%">序号</th>
								      <th scope="col" style="text-align: center;width: 15%">物资名称</th>
								      <th scope="col" style="text-align: center;width: 15%">规格型号</th>
								      <th scope="col" style="text-align: center;width: 10%">物料Id</th>
								      <th scope="col" style="text-align: center;width: 10%">数量</th>
								      <th scope="col" style="text-align: center;width: 10%">单位</th>
								      <th scope="col" style="text-align: center;width: 10%">单价(元)</th>
								      <th scope="col" style="text-align: center;width: 10%">金额(元)</th>
								      <th scope="col" style="text-align: center;width: 15%">备注</th>
								    </tr>
								  </thead>
								  <tbody>
								  	<c:forEach items="${orderList}" var="o">
								  		<tr>
								  			<td>
								  			
											</td>							  			
								  			<td>
								  			    <input type='text' class='form-control bj' aria-label='' aria-describedby='' disabled="" value='${o.material_Name}'>
								  			</td>
								  			<td>
								  			    <input type='text' class='form-control bj' aria-label='' aria-describedby='' disabled="" value='${o.specification_Type}'>
								  			</td>
								  			<td>
								  			    <input type='text' class='form-control bj' aria-label='' aria-describedby='' disabled="" value='${o.materielId}'>
								  			</td>
								  			<td>
								  			    <input type='text' class='form-control bj' aria-label='' aria-describedby='' disabled="" value='${o.sl}'>
								  			</td>
								  			<td>
								  			    <input type='text' class='form-control bj' aria-label='' aria-describedby='' disabled="" value='${o.unit}'>
								  			</td>
								  			<td>
								  			    <input type='text' class='form-control bj' aria-label='' aria-describedby='' disabled="" value='${o.price}'>
								  			</td>
								  			<td>
								  			    <input type='text' class='form-control bj' aria-label='' aria-describedby='' disabled="" name="total_price"  value='${o.total_price}'>
								  			</td>
								  			<td>
								  			    <input type='text' class='form-control bj' aria-label='' aria-describedby=''  disabled="" value='${o.bz}'>
								  			</td>
								  		</tr>
								  	
								  	</c:forEach>
								  	<tr>
								  		<td>
								  		</td>
								  		<td>
								  			合计总金额
								  		</td>
								  		<td colspan="7">
								  			<input type='text' class='form-control bj' aria-label='' aria-describedby='' style="width: 165px;" disabled="" id="totalprice">
								  		</td>
								  	</tr>
								  </tbody>
								</table>
							</div>
						</div>
					
						 <div class="layui-form-item layui-form-text">
						    <label class="layui-form-label" style="width:120px;">备注</label>
						    <div class="layui-input-block">
						      <textarea placeholder="请输入内容"  class="layui-textarea bj" style="width:76.5%" disabled="">${contract.remarks}</textarea>
						    </div>
						 </div>
				 
						  <div class="layui-form-item">
						    <label class="layui-form-label" style="width: 200px;">二、质量技术要求标准</label>
						    <div class="layui-input-block">
						      <input type="text" autocomplete="off" placeholder="" class="layui-input bj" style="width:69.5%" disabled="" value="${contract.zljsyq}">
						    </div>
						  </div>
				  
						   <div class="layui-form-item">
						    <label class="layui-form-label" style="width: 200px;">三、交货时间及地点 </label>
						    <div class="layui-input-block">
						      <input type="text" autocomplete="off" placeholder="" class="layui-input bj" style="width:69.5%" disabled="" value="${contract.jhsjjdd}">
						    </div>
						  </div>
						  
						   <div class="layui-form-item">
						    <label class="layui-form-label" style="width: 200px;">四、运输及费用 </label>
						    <div class="layui-input-block">
						      <input type="text" autocomplete="off" placeholder="" class="layui-input bj" style="width:69.5%" disabled="" value="${contract.ysjfy}">
						    </div>
						  </div>
						  
						  <div class="layui-form-item">
						    <label class="layui-form-label" style="width: 200px;">五、付款方式  </label>
						    <div class="layui-input-block">
						      <input type="text"  autocomplete="off" placeholder="" class="layui-input bj" style="width:69.5%" disabled="" value="${contract.fkfs}">
						    </div>
						  </div>
						  
						   <div class="layui-form-item">
						    <label class="layui-form-label" style="width: 200px;">六、违约责任   </label>
						    <div class="layui-input-block">
						      <input type="text"  autocomplete="off" placeholder="" class="layui-input bj" style="width:69.5%" disabled="" value="${contract.wyzr}">
						    </div>
						  </div>
						  
						   <div class="layui-form-item">
						    <label class="layui-form-label" style="width: 200px;">七 </label>
						    <div class="layui-input-block">
						      <input type="text" autocomplete="off" placeholder="" class="layui-input bj" style="width:69.5%;margin-left: 90px;" disabled="" value="${contract.wjsy}">
						    </div>
						  </div>
						  
						   <div class="layui-form-item">
						    <label class="layui-form-label" style="width: 200px;">八 </label>
						    <div class="layui-input-block">
						      <input type="text" id="htfs"  autocomplete="off" placeholder="" class="layui-input bj" style="width:69.5%;margin-left: 90px;" disabled="" value="${contract.htfs}">
						    </div>
						  </div>
						  
						   <div class="layui-form-item">
						    <label class="layui-form-label" style="width: 200px;">九 </label>
						    <div class="layui-input-block">
						      <input type="text" id="sxrq"  autocomplete="off" placeholder="" class="layui-input bj" style="width:69.5%;margin-left: 90px;"  disabled="" value="${contract.sxrq}">
						    </div>
						  </div>
				  
						  <div class="layui-form-item layui-form-text">
							  <div class="layui-input-block">
								<table class="table table-bordered"  style="width: 100%">
								  <thead>
								    <tr>
								      <th scope="col" style="text-align: center;width: 50%">供方</th>
								      <th scope="col" style="text-align: center;width: 50%">需方</th>
								    </tr>
								  </thead>
								  <tbody>
								  <tr>
								    	<td>
											<div class="layui-form-item">
											 <label class="layui-form-label" style="width:90px;">单位名称  </label>
											    <div class="layui-input-block">
											      <input type="text"  autocomplete="off" placeholder="" class="layui-input bj" disabled="" value="${our_Unit.unit_Name}">
											    </div>
											 </div>
										</td>				    	
								    	<td>
											<div class="layui-form-item">
											 <label class="layui-form-label" style="width:90px;">单位名称  </label>
											    <div class="layui-input-block">
											      <input type="text"  autocomplete="off" placeholder="" class="layui-input bj" disabled="" value="${customer.unit_Name}">
											    </div>
											 </div>
										</td>
								    </tr>
								     <tr>
								    	<td>
											<div class="layui-form-item">
											 <label class="layui-form-label" style="width:90px;">单位地址 </label>
											    <div class="layui-input-block">
											      <input type="text" id="gfdwdz" lay-verify="gfdwdz" autocomplete="off" placeholder="" class="layui-input bj" disabled="" value="${our_Unit.office_Address }">
											    </div>
											 </div>
										</td>
								    	<td>
											<div class="layui-form-item">
											 <label class="layui-form-label" style="width:90px;">单位地址 </label>
											    <div class="layui-input-block">
											      <input type="text" id="xfdwdz" lay-verify="xfdwdz" autocomplete="off" placeholder="" class="layui-input bj" disabled="" value="${customer.office_Address }">
											    </div>
											 </div>
										</td>
								    </tr>
								    <tr>
								    	<td>
											<div class="layui-form-item">
											 <label class="layui-form-label" style="width:110px;">法定代表人 </label>
											    <div class="layui-input-block">
											      <input type="text"  autocomplete="off" placeholder="" class="layui-input bj" disabled="" value="${our_Unit.legal_person}">
											    </div>
											 </div>
										</td>
								    	<td>
											<div class="layui-form-item">
											 <label class="layui-form-label" style="width:110px;">法定代表人 </label>
											    <div class="layui-input-block">
											      <input type="text"  autocomplete="off" placeholder="" class="layui-input bj" disabled="" value="${customer.legal_person}">
											    </div>
											 </div>
										</td>
								    </tr>
								     <tr>
								    	<td>
											<div class="layui-form-item">
											 <label class="layui-form-label" style="width:110px;">委托代理人</label>
											    <div class="layui-input-block">
											      <input type="text"  autocomplete="off" placeholder="" class="layui-input bj" disabled="" value="${our_Unit.wtdlr}">
											    </div>
											 </div>
										</td>
								    	<td>
											<div class="layui-form-item">
											 <label class="layui-form-label" style="width:110px;">委托代理人</label>
											    <div class="layui-input-block">
											      <input type="text"  autocomplete="off" placeholder="" class="layui-input bj" disabled="" value="${customer.wtdlr}">
											    </div>
											 </div>
										</td>
								    </tr>
								    <tr>
								    	<td>
											<div class="layui-form-item">
											 <label class="layui-form-label" style="width:90px;">开户行</label>
											    <div class="layui-input-block">
											      <input type="text" autocomplete="off" placeholder="" class="layui-input bj" disabled="" value="${our_Unit.opening_Bank}">
											    </div>
											 </div>
										</td>
								    	<td>
											<div class="layui-form-item">
											 <label class="layui-form-label" style="width:90px;">开户行</label>
											    <div class="layui-input-block">
											      <input type="text" autocomplete="off" placeholder="" class="layui-input bj" disabled="" value="${customer.opening_Bank}">
											    </div>
											 </div>
										</td>
								    </tr>
								    <tr>
								    	<td>
											<div class="layui-form-item">
											 <label class="layui-form-label" style="width:90px;">帐号</label>
											    <div class="layui-input-block">
											      <input type="text" autocomplete="off" placeholder="" class="layui-input bj" disabled="" value="${our_Unit.account_Number}">
											    </div>
											 </div>
										</td>
								    	<td>
											<div class="layui-form-item">
											 <label class="layui-form-label" style="width:90px;">帐号</label>
											    <div class="layui-input-block">
											      <input type="text" autocomplete="off" placeholder="" class="layui-input bj" disabled="" value="${customer.account_Number}">
											    </div>
											 </div>
										</td>
								    </tr>
								    <tr>
								    	<td>
											<div class="layui-form-item">
											 <label class="layui-form-label" style="width:90px;">税号</label>
											    <div class="layui-input-block">
											      <input type="text"  autocomplete="off" placeholder="" class="layui-input bj" disabled="" value="${our_Unit.duty_Paragraph}">
											    </div>
											 </div>
										</td>
								    	<td>
											<div class="layui-form-item">
											 <label class="layui-form-label" style="width:90px;">税号</label>
											    <div class="layui-input-block">
											      <input type="text"  autocomplete="off" placeholder="" class="layui-input bj" disabled="" value="${customer.duty_Paragraph}">
											    </div>
											 </div>
										</td>
								    </tr>
								    <tr>
								    	<td>
											<div class="layui-form-item">
											 <label class="layui-form-label" style="width:90px;">电话 </label>
											    <div class="layui-input-block">
											      <input type="text"  autocomplete="off" placeholder="" class="layui-input bj" disabled="" value="${our_Unit.telPhone}">
											    </div>
											 </div>
										</td>
								    	<td>
											<div class="layui-form-item">
											 <label class="layui-form-label" style="width:90px;">电话 </label>
											    <div class="layui-input-block">
											      <input type="text" autocomplete="off" placeholder="" class="layui-input bj" disabled="" value="${customer.telPhone}">
											    </div>
											 </div>
										</td>
								    </tr>
								     <tr>
								    	<td>
											<div class="layui-form-item">
											 <label class="layui-form-label" style="width:90px;">传真  </label>
											    <div class="layui-input-block">
											      <input type="text"  autocomplete="off" placeholder="" class="layui-input bj" disabled="" value="${our_Unit.fax}">
											    </div>
											 </div>
										</td>
								    	<td>
											<div class="layui-form-item">
											 <label class="layui-form-label" style="width:90px;">传真  </label>
											    <div class="layui-input-block">
											      <input type="text"  autocomplete="off" placeholder="" class="layui-input bj" disabled="" value="${customer.fax}">
											    </div>
											 </div>
										</td>
								    </tr>
								  </tbody>
								</table>
							</div>
						</div>
				  	</form>
				</div>
			</div>
			<!--评审意见  -->
			<div class="layui-tab-item">
				<div class="layui-collapse">
					<c:forEach items="${reviewOpinions}" var="r">
						 <div class="layui-colla-item" >
				   			<h2 class="layui-colla-title">${r.time}&nbsp;&nbsp;&nbsp;${r.TASK_NAME_}&nbsp;&nbsp;&nbsp;${r.userName}---->${r.TITLE_}</h2>
					   			<c:if test="${r.TASK_NAME_ eq '成品核对'}">
					   				<c:if test="${not empty cphds}">
						   				 <div class="layui-colla-content layui-show">
												<div class="layui-form-item layui-form-text">
													  <div class="layui-input-block" style="left:-50px;">
														<table class="table table-bordered" name="cphd" style="width: 100%">
														  <thead>
														    <tr>
														      <th scope="col" style="text-align: center;width: 5%">序号</th>
														      <th scope="col" style="text-align: center;width: 25%">成品名称</th>
														      <th scope="col" style="text-align: center;width: 25%">规格型号</th>
														      <th scope="col" style="text-align: center;width: 15%">库位</th>
														      <th scope="col" style="text-align: center;width: 15%">库存数量</th>
														      <th scope="col" style="text-align: center;width: 15%">出库数量</th>
														    </tr>
														  </thead>
														  <tbody>
														  	<c:forEach items="${cphds}" var="c">
														  		<tr>
														  			<td style="text-align: center;">
														  			
																	</td>							  			
														  			<td>
														  			    <input type='text' class='form-control bj' aria-label='' aria-describedby='' disabled="" value='${c.cphd_Cpmc}'>
														  			</td>
														  			<td>
														  			    <input type='text' class='form-control bj' aria-label='' aria-describedby='' disabled="" value='${c.cphd_Ggxh}'>
														  			</td>
														  			<td>
														  			    <input type='text' class='form-control bj' aria-label='' aria-describedby='' disabled="" value='${c.cphd_Kw}'>
														  			</td>
														  			<td>
														  			    <input type='text' class='form-control bj' aria-label='' aria-describedby='' disabled="" value='${c.cphd_Kcsl}'>
														  			</td>
														  			<td>
														  			    <input type='text' class='form-control bj' aria-label='' aria-describedby='' disabled="" value="${c.cphd_Cksl}">
														  			</td>
														  		</tr>
														  	</c:forEach>
														  </tbody>
														</table>
													</div>
												</div>
						   			  	 </div>
					   			  	 </c:if>
					   			</c:if>
					   			 <c:if test="${r.TASK_NAME_ eq '出库发货' }">
					   			 	<c:if test="${not empty deliveryOrder }">
					   			 	   <div class="layui-colla-content layui-show">
							   				<div class="layui-form-item layui-form-text">
												  <div class="layui-input-block" style="left: -50px;">
													<table class="table table-bordered" name="ckfh" style="width: 100%" >
													  <thead>
													    <tr>
													      <th scope="col" style="text-align: center;width: 5%">序号</th>
													      <th scope="col" style="text-align: center;width: 20%">物料名称</th>
													      <th scope="col" style="text-align: center;width: 20%">规格型号</th>
													      <th scope="col" style="text-align: center;width: 15%">单位</th>
													      <th scope="col" style="text-align: center;width: 15%">送货数量</th>
													      <th scope="col" style="text-align: center;width: 25%">备注</th>
													    </tr>
													  </thead>
													  <tbody>
													  	<c:forEach items="${deliveryOrder}" var="d">
													  		<tr>
													  			<td style="text-align: center;">
													  			
																</td>							  			
													  			<td>
													  			    <input type='text' class='form-control bj' aria-label='' aria-describedby='' disabled="" value='${d.material_Name}'>
													  			</td>
													  			<td>
													  			    <input type='text' class='form-control bj' aria-label='' aria-describedby='' disabled="" value='${d.specification_Type}'>
													  			</td>
													  			<td>
													  			    <input type='text' class='form-control bj' aria-label='' aria-describedby='' disabled="" value='${d.company}'>
													  			</td>
													  			<td>
													  			    <input type='text' class='form-control bj' aria-label='' aria-describedby='' disabled="" value='${d.delivery_Number}' >
													  			</td>
													  			<td>
													  			    <input type='text' class='form-control bj' aria-label='' aria-describedby='' disabled="" value="${d.remarks}">
													  			</td>
													  		</tr>
													  	</c:forEach>
													  </tbody>
													</table>
												</div>
											</div>
										</div>
									</c:if>
					   			</c:if>
					   			<c:if test="${r.TASK_NAME_ eq '生产计划' }">
					   				<c:if test="${not empty productionPlan ||not empty productionPlanOrders}">
					   				   <div class="layui-colla-content layui-show">
						   					<div class="layui-form-item">
												     <div class="layui-inline">
													      <label class="layui-form-label" style="width:150px;">生产计划号</label>
													      <div class="layui-input-inline">
													        <input type="text"  lay-verify="" autocomplete="off" class="layui-input bj" style="width: 200px;" disabled="" value="${productionPlan.plan_Code}">
													      </div>
												     </div>
												     
												    <div class="layui-inline">
													      <label class="layui-form-label" style="width:150px;">部门</label>
													      <div class="layui-input-inline">
													        <input type="text"  lay-verify="" autocomplete="off" class="layui-input bj" style="width: 200px;" disabled="" value="${productionPlan.plan_DepartmentName}">
													      </div>
												     </div>
													 
													 <div class="layui-inline" >
													      <label class="layui-form-label" style="width: 139px;">下订单日期</label>
													      <div class="layui-input-inline">
													        <input type="text"  lay-verify="" autocomplete="off" class="layui-input bj" style="width: 200px;" disabled="" value="${productionPlan.xddrq}">
													      </div>
												    </div>
												 </div>
												 
												 <div class="layui-form-item">
												     <div class="layui-inline">
													      <label class="layui-form-label" style="width:150px;">计划开工日期</label>
													      <div class="layui-input-inline">
													        <input type="text" lay-verify=""  autocomplete="off" class="layui-input bj" style="width: 200px;" disabled="" value="${productionPlan.jhkgrq }">
													      </div>
												     </div>
													 
													 <div class="layui-inline">
													      <label class="layui-form-label" style="width: 150px;">计划完成日期</label>
													      <div class="layui-input-inline">
													        <input type="text" lay-verify=""  autocomplete="off" class="layui-input bj" style="width: 200px;" disabled="" value="${productionPlan.jhwgrq}">
													      </div>
												    </div>
												 </div>
												 
						   					<div class="layui-form-item layui-form-text">
										  		<label class="layui-form-label" style="width:133px;">生产计划</label>
												  <div class="layui-input-block" style="left:-52px;">
													<table class="table table-bordered"  name="scjhorder" style="width: 100%">
													  <thead>
													    <tr>
													      <th scope="col" style="text-align: center;width: 5%">序号</th>
													      <th scope="col" style="text-align: center;width: 27%">成品名称</th>
													      <th scope="col" style="text-align: center;width: 27%">规格型号</th>
													      <th scope="col" style="text-align: center;width: 22%">物料Id</th>
													      <th scope="col" style="text-align: center;width: 19%">生产数量</th>
													    </tr>
													  </thead>
													  <tbody>
													  	<c:forEach items="${productionPlanOrders}" var="p">
													  		<tr>
													  			<td style="text-align: center;">
													  			
																</td>							  			
													  			<td>
													  			    <input type='text' class='form-control bj' aria-label='' aria-describedby='' disabled="" value='${p.productName}'>
													  			</td>
													  			<td>
													  			    <input type='text' class='form-control bj' aria-label='' aria-describedby='' disabled="" value='${p.ggxh}'>
													  			</td>
													  			<td>
													  			    <input type='text' class='form-control bj' aria-label='' aria-describedby='' disabled="" value='${p.materielId}'>
													  			</td>
													  			<td>
													  			    <input type='text' class='form-control bj' aria-label='' aria-describedby='' disabled="" value='${p.scsl}'>
													  			</td>
													  		</tr>
													  	</c:forEach>
													  </tbody>
													</table>
												</div>
											</div>
										</div>
						   			</c:if>
					   			</c:if>
					   			<c:if test="${r.TASK_NAME_ eq '材料计划' }">
					   				<c:if test="${not empty materialPlan ||not empty materialPlanOrder}">
					   				   <div class="layui-colla-content layui-show">
						   					<div class="layui-form-item">
												     <div class="layui-inline">
													      <label class="layui-form-label" style="width:150px;">材料计划号</label>
													      <div class="layui-input-inline">
													        <input type="text"  lay-verify="" autocomplete="off" class="layui-input bj" style="width: 200px;" disabled="" value="${materialPlan.plan_Code}">
													      </div>
												     </div>
												     
													 <div class="layui-inline" >
													      <label class="layui-form-label" style="width: 150px;">下订单日期</label>
													      <div class="layui-input-inline">
													        <input type="text"  lay-verify="" autocomplete="off" class="layui-input bj" style="width: 200px;" disabled="" value="${materialPlan.xddrq}">
													      </div>
												    </div>
												 </div>
												 
												 <div class="layui-form-item">
												     <div class="layui-inline">
													      <label class="layui-form-label" style="width:150px;">计划开工日期</label>
													      <div class="layui-input-inline">
													        <input type="text" lay-verify=""  autocomplete="off" class="layui-input bj" style="width: 200px;" disabled="" value="${materialPlan.jhkgrq }">
													      </div>
												     </div>
													 
													 <div class="layui-inline">
													      <label class="layui-form-label" style="width: 150px;">计划完成日期</label>
													      <div class="layui-input-inline">
													        <input type="text" lay-verify=""  autocomplete="off" class="layui-input bj" style="width: 200px;" disabled="" value="${materialPlan.jhwgrq}">
													      </div>
												    </div>
												 </div>
												 
						   					<div class="layui-form-item layui-form-text">
										  		<label class="layui-form-label" style="width:133px;">材料计划</label>
												  <div class="layui-input-block" style="left:-52px;">
													<table class="table table-bordered"  name="cljhorder" style="width: 100%">
													  <thead>
													    <tr>
													      <th scope="col" style="text-align: center;width: 5%">序号</th>
													      <th scope="col" style="text-align: center;width: 27%">材料名称</th>
													      <th scope="col" style="text-align: center;width: 27%">规格型号</th>
													      <th scope="col" style="text-align: center;width: 22%">物料Id</th>
													      <th scope="col" style="text-align: center;width: 19%">计划数量</th>
													    </tr>
													  </thead>
													  <tbody>
													  	<c:forEach items="${materialPlanOrder}" var="m">
													  		<tr>
													  			<td style="text-align: center;">
													  			
																</td>							  			
													  			<td>
													  			    <input type='text' class='form-control bj' aria-label='' aria-describedby='' disabled="" value='${m.materialName}'>
													  			</td>
													  			<td>
													  			    <input type='text' class='form-control bj' aria-label='' aria-describedby='' disabled="" value='${m.specification_Type}'>
													  			</td>
													  			<td>
													  			    <input type='text' class='form-control bj' aria-label='' aria-describedby='' disabled="" value='${m.materielId}'>
													  			</td>
													  			<td>
													  			    <input type='text' class='form-control bj' aria-label='' aria-describedby='' disabled="" value='${m.planNumber}'>
													  			</td>
													  		</tr>
													  	</c:forEach>
													  </tbody>
													</table>
												</div>
											</div>
										</div>
						   			</c:if>
					   			</c:if>
					   			<c:if test="${r.TASK_NAME_ eq '加工配料' }">
					   				<c:if test="${not empty ingredients}">
					   				   <div class="layui-colla-content layui-show">
						   						<div class="layui-form-item layui-form-text">
										  		<label class="layui-form-label" style="width:133px;">加工配料</label>
												  <div class="layui-input-block" style="left:-55px;">
													<table class="table table-bordered" name="jgpl"  style="width: 100%">
													  <thead>
													    <tr>
													      <th scope="col" style="text-align: center;width: 7%">序号</th>
													      <th scope="col" style="text-align: center;width: 26%">材料名称</th>
													      <th scope="col" style="text-align: center;width: 27%">规格型号</th>
													      <th scope="col" style="text-align: center;width: 28%">物料Id</th>
													      <th scope="col" style="text-align: center;width: 18%">采购数量</th>
													    </tr>
													  </thead>
													  <tbody>
														  <c:forEach items="${ingredients}" var="o">
														  		<tr>
														  			<td style="text-align: center;">
														  			
																	</td>							  			
														  			<td>
														  			    <input type='text' class='form-control bj' aria-label='' aria-describedby='' disabled="" value='${o.materialName}'>
														  			</td>
														  			<td>
														  			    <input type='text' class='form-control bj' aria-label='' aria-describedby='' disabled="" value='${o.specification_Type}'>
														  			</td>
														  			<td>
														  			    <input type='text' class='form-control bj' aria-label='' aria-describedby='' disabled="" value='${o.materielId}'>
														  			</td>
														  			<td>
														  			    <input type='text' class='form-control bj' aria-label='' aria-describedby=''  disabled="" value='${o.cgsl }'>
														  			</td>
														  			
														  		</tr>
														  	</c:forEach>
													  </tbody>
													</table>
												</div>
											</div>	
										</div>
						   			</c:if>
					   			</c:if>
					   			<c:if test="${r.TASK_NAME_ eq '发起采购' }">
					   				<c:if test="${not empty purchaseList}">
					   				   <div class="layui-colla-content layui-show">
						   					<div class="layui-form-item layui-form-text">
										  		<label class="layui-form-label" style="width:155px;">材料采购项</label>
												  <div class="layui-input-block" style="top:15px;left:-40px;">
													<table class="table table-bordered" name="clcgx" style="width:100%;">
													  <thead>
													    <tr>
													      <th scope="col" style="text-align: center;width:100px;">序号</th>
													      <th scope="col" style="text-align: center;width: 250px;">品名</th>
													      <th scope="col" style="text-align: center;width: 250px;">型号</th>
													      <th scope="col" style="text-align: center;width: 250px;">物料Id</th>
													      <th scope="col" style="text-align: center;width: 100px;">单位</th>
													      <th scope="col" style="text-align: center;width: 150px;">数量</th>
													      <th scope="col" style="text-align: center;width: 150px;">单价</th>
													      <th scope="col" style="text-align: center;width: 150px;">金额</th>
													      <th scope="col" style="text-align: center;width: 200px;">交货日期</th>
													      <th scope="col" style="text-align: center;width: 200px;">图号</th>
													      <th scope="col" style="text-align: center;width: 300px;">备注</th>
													    </tr>
													  </thead>
													  <tbody>
													  	<c:forEach items="${purchaseList}" var="p">
													  		<tr>
															  	<td scope='row' style='text-align: center;line-height:38px;'></td>
																<td><input type='text' class='form-control' aria-label='' aria-describedby='' value="${p.pro_Name}" title="${p.pro_Name}" disabled=""></td>
																<td><input type='text' class='form-control bj' aria-label='' aria-describedby=''    value="${p.model}" title="${p.model}" disabled=""></td>
																<td><input type='text' class='form-control bj' aria-label='' aria-describedby=''  value="${p.materielId}" title="${p.materielId}" disabled=""></td>
																<td><input type='text' class='form-control bj' aria-label='' aria-describedby=''  value="${p.company}" title="${p.company}" disabled=""></td>
																<td><input type='text' class='form-control bj' aria-label='' aria-describedby=''  value="${p.sl}" title="${p.sl}" disabled=""></td>
																<td><input type='text' class='form-control bj' aria-label='' aria-describedby=''   value="${p.price}" title="${p.price}" disabled=""></td>
																<td><input type='text' class='form-control bj' aria-label='' aria-describedby=''  value="${p.zje}" title="${p.zje}" disabled=""></td>
																<td><input type='text' class='form-control bj' aria-label='' aria-describedby=''   value="${p.delivery_date}" title="${p.delivery_date}" disabled=""></td>
																<td><input type='text' class='form-control bj' aria-label='' aria-describedby=''  value="${p.map_Number}" title="${p.map_Number }" disabled=""></td>
																<td><input type='text' class='form-control bj' aria-label='' aria-describedby=''  value="${p.bz}" title="${p.bz}" disabled=""></td>
															</tr>
													  	</c:forEach>
													  </tbody>
													</table>
												</div>
											</div>
										</div>
						   			</c:if>
					   			</c:if>
					   			<c:if test="${(not empty r.MESSAGE_RESULT_)&&(not empty r.MESSAGE_INFOR_)}">
								    <div class="layui-colla-content layui-show">
								    		审批结果:<span style="color: green">${r.MESSAGE_RESULT_ }</span> </br>
											审批意见:<span style="color: green">${r.MESSAGE_INFOR_ }</span>
								    </div>
							   	</c:if> 
				 		 </div>
				 	</c:forEach>
				</div>
			</div>
			<!--附件  -->
			<div class="layui-tab-item">
				<p>任务附件</p>
				<table class="layui-hide" id="test" lay-filter="test"></table>
				<form id="downForm" method="post" action="" enctype="multipart/form-data">
					<input type="hidden" id="ftpPath">
					<input type="hidden" id="rEALWJM">
				</form>
			</div>
			<!--流程检视  -->
			<div class="layui-tab-item">
				<img style="position: absolute; top: 70px; left:10%;width: 80%;" id="lct" src=''>
			</div>
			<!--报表  -->
			<div class="layui-tab-item">
 				<iframe  src=''  width="100%" height="100%" frameborder="no" border="0" marginwidth="0" marginheight=" 0" scrolling="no" allowtransparency="yes" onload="changeFrameHeight(this)"></iframe>			
 			</div>
		</div>
	</div>
	
	<!-- 操作 Begin -->
		<div id="myMenu" class="m-operation_box" style="width: 140px;">
			<h3 class="u-operation_title">操作</h3>
			<div>
				<ul class="u-menu_option">
					<li id="_zxys_deal_btn">处理</li>
					<li id="_zxys_retake_btn">退回</li> 
					<li id="_zxys_transmit_btn">转交</li>
					<!-- <li id="_zxys_reject_btn">退回上一步</li> -->
					<li id="_zxys_gaveUp_btn">放弃流程</li>
					<li id="_zxys_restart_btn">重启流程</li>
				</ul>
			</div>
		</div>
	<!-- 操作 End -->
<script type="text/html" id="barDemo">
  <!--<a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="yl" name="defaultAD">预览</a>-->
  <a class="layui-btn layui-btn-xs" lay-event="xz">下载</a>
</script>
<script src="../bootstrap-3.3.7-dist/js/bootstrap.js"></script>
<script src="../layui-v2.5.5/layui/layui.js" charset="utf-8"></script>
<script type="text/javascript" src="../jquery-easyui-1.7.0/jquery.easyui.min.js"></script>
<script>
layui.use(['form', 'layedit', 'laydate','element','table'], function(){
  var form = layui.form
  ,layer = layui.layer
  ,layedit = layui.layedit
  ,laydate = layui.laydate
  ,upload = layui.upload;
  var url=$('#url').val();
  var element = layui.element;
  var table = layui.table;
  var OBJId=$('#OBJDM').val();
  var objId=$('#taskId').val();
  form.render();
  
  //创建一个编辑器
  var editIndex = layedit.build('LAY_demo_editor');
  table.render({
	    elem: '#test'
	    ,url:url+'enclosure/enclosureList.do?OBJDM='+OBJId
	    ,title: '任务附件'
	    ,cols: [[
	       {field:'index', width:"10%", title: '序号', sort: true,type:'numbers'}
	      ,{field:'REALWJM', width:"80%",align:'left', title: '文件名称'}
	      ,{fixed: 'right', title:'操作', toolbar: '#barDemo', width:"10%",align:'center'}
	    ]]
	  });
  
//监听行工具事件
  table.on('tool(test)', function(obj){
    var data = obj.data;
    //存储在ftp服务器端的地址
    var ftpPath=data.SHANGCHUANDZ;
    //存储在ftp的真实文件名
    var rEALWJM=data.REALWJM;
    var url=$('#url').val();
    $('#ftpPath').val(ftpPath);
    $('#rEALWJM').val(rEALWJM);
	var form = document.getElementById('downForm');
    //console.log(obj)
    if(obj.event === 'xz'){
    	//下载文件
    	form.action=url+"sales/downloadFtpFile.do?ftpPath="+ftpPath+"&"+"rEALWJM="+rEALWJM;
    	form.submit();
    }
  });

  lct();
  khlxrxh();
  $('#myMenu').hide();
  menue();
  bbsrc();
  cphdxh();
  ckfhxh();
  scjhxh();
  cljhxh();
  jgplxh();
  ycth();
  clcgxh();
});

$("#myMenu").draggable(); 

//点击处理跳转相应页面
 $('#_zxys_deal_btn').click(function(){
		 var url=$('#url').val();
		 var taskId=$('#taskId').val();
		 $.ajax({
				type : "post",
				url : "<c:url value='/myTask/dealWith.do'/>",
				async : false,
				dataType : 'json',
				data:{"taskId":taskId},
				error : function() {
					alert("出错");
				},
				success : function(msg) {
					if(msg.flag){
						return layer.alert(msg.infor,{icon:7});
					}else{
						var result=msg.result;
						var objId=msg.business_key;
						var task_Id=msg.taskId;
						var narrow=msg.narrow;
						if(narrow){
							parent.layer.open({
						       	  	type:2,
						       	  	title:msg.taskName,
						       	  	area: ['35%','40%'],
						       		shadeClose: false,
						       		resize:false,
						       	    anim: 4,
						       	 	content:[url+result+"?objId="+objId+"&taskId="+task_Id,'yes']
						     });
						}else{
							parent.layer.open({
					       	  	type:2,
					       	  	title:msg.taskName,
					       	  	area: ['100%','100%'],
					       		shadeClose: false,
					       		resize:false,
					       	    anim: 4,
					       	 	content:[url+result+"?objId="+objId+"&taskId="+task_Id,'yes']
					    	 });
						}
					}
				}
			});
	 });


//点击放弃流程跳转相应页面
 $('#_zxys_transmit_btn').click(function(){
		 var url=$('#url').val();
		 var taskId=$('#taskId').val();
		 layer.open({
	       	  	type:2,
	       	  	title:'选择用户',
	       	  	area: ['60%','60%'],
	       		shadeClose: false,
	       		resize:false,
	       	    anim: 4,
	       	 	content:[url+"zj/initZhuanJiao.do?taskId="+taskId,'yes']
	     });
	 });

//点击转交跳转相应页面
 $('#_zxys_gaveUp_btn').click(function(){
		 var url=$('#url').val();
		 var taskId=$('#taskId').val();
		 var sales_Contract_Id=$('#sales_Contract_Id').val();
		 layer.open({
	       	  	type:2,
	       	  	title:'放弃流程',
	       	  	area: ['40%','50%'],
	       		shadeClose: false,
	       		resize:false,
	       	    anim: 4,
	       	 	content:[url+"fqlc/initFqlc.do?taskId="+taskId+"&sales_Contract_Id="+sales_Contract_Id,'yes']
	     });
	 });

 //点击重启流程
 $('#_zxys_restart_btn').click(function(){
		 var url=$('#url').val();
		 var taskId=$('#taskId').val();
		 layer.open({
	       	  	type:2,
	       	  	title:'重启流程',
	       	  	area: ['40%','30%'],
	       		shadeClose: false,
	       		resize:false,
	       	    anim: 4,
	       	 	content:[url+"cqlc/initCqlc.do?taskId="+taskId,'yes']
	     });
	 });

	//点击退回操作
	$('#_zxys_retake_btn').click(function(){
			 var url=$('#url').val();
			 var taskId=$('#taskId').val();
			 layer.open({
		       	  	type:2,
		       	  	title:'退回流程',
		       	  	area: ['40%','50%'],
		       		shadeClose: false,
		       		resize:false,
		       	    anim: 4,
		       	 	content:[url+"takeBack/initTakeBack.do?taskId="+taskId,'yes']
		     });
		 });

	 function ycth(){
		var ldsh=$('#ldsh').val();
		if(ldsh){
			$('#_zxys_retake_btn').hide();
		}
	}

	 
	function lct(){
	 	var img=$('#lct');
	 	var processInstanceId=$('#processInstanceId').val();
	 	var url=$('#url').val();
	 	img.attr("src",url+"viewImage/graphHistoryProcessInstance.do?processInstanceId="+processInstanceId)
	 }

	function khlxrxh(){
		var len = $('#khlxrs tr').length;
		var totalPrice=0;
	    for(var i = 1;i<len-1;i++){
	        $('table tr:eq('+i+') td:first').text(i);
	    }
	    var prices=$('input[name="total_price"]');
	    for(var i=0;i<prices.length;i++){
	    	totalPrice=(totalPrice*1)+(prices[i].value*1);
		}
		$('#totalprice').val(totalPrice);
	}

	//显示/隐藏操作面板
	function menue(){
		//获得任务Id
		var taskId=$('#taskId').val();
		if(taskId!=""){
			 $('#myMenu').show();
		}else{
			$('#myMenu').hide();
		}
	}

	//设置报表src
	function bbsrc(){
		var id=$('#sales_Contract_Id').val();
		 $('iframe').attr('src','http://127.0.0.1:8080/WebReport/ReportServer?reportlet=gxht.cpt&id='+id);
	}

	function changeFrameHeight(that){
        //电脑屏幕高度-iframe上面其他组件的高度
        //例:我这里iframe上面还有其他一些div组件，一共的高度是120，则减去120
        $(that).height(document.documentElement.clientHeight - 90);
        
    }

	function cphdxh(){
	    var tables=$('table[name="cphd"]');
	    for(var i=0;i<tables.length;i++){
			var len=tables[i].rows.length;
			for(var j=1;j<len;j++){
				tables[i].rows[j].cells[0].innerHTML=j;
			}
		}
	}

	function ckfhxh(){
		var tables=$('table[name="ckfh"]');
		for(var i=0;i<tables.length;i++){
			var len=tables[i].rows.length;
			for(var j=1;j<len;j++){
				tables[i].rows[j].cells[0].innerHTML=j;
			}
		}
	}

	function scjhxh(){
		var tables=$('table[name="scjhorder"]');
		for(var i=0;i<tables.length;i++){
			var len=tables[i].rows.length;
			for(var j=1;j<len;j++){
				tables[i].rows[j].cells[0].innerHTML=j;
			}
		}
	}
	
	function cljhxh(){
		var tables=$('table[name="cljhorder"]');
		for(var i=0;i<tables.length;i++){
			var len=tables[i].rows.length;
			for(var j=1;j<len;j++){
				tables[i].rows[j].cells[0].innerHTML=j;
			}
		}
	}

	function jgplxh(){
		var tables=$('table[name="jgpl"]');
		for(var i=0;i<tables.length;i++){
			var len=tables[i].rows.length;
			for(var j=1;j<len;j++){
				tables[i].rows[j].cells[0].innerHTML=j;
			}
		}
	}
	
	function clcgxh(){
		var tables=$('table[name="clcgx"]');
		for(var i=0;i<tables.length;i++){
			var len=tables[i].rows.length;
			for(var j=1;j<len;j++){
				tables[i].rows[j].cells[0].innerHTML=j;
			}
		}
	}
	
</script>
</body>
</html>