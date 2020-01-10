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
<title>成品查看</title>
<link rel="stylesheet" href="../layui-v2.5.5/layui/css/layui.css">
<script src="../jquery/jquery-3.3.1.js"></script>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@page isELIgnored="false" %>
<style>
  .bj{background-color: #F0F0F0}
 </style>
</head>
<body style="width:100%;padding:0px; margin:0px;text-align: center;">
	<div style="width:1280px;height:auto;padding:0px; margin:0 auto;" id="main">
		<form class="layui-form" action='' method="post">
		<input type="hidden" name="product_Id" value="${product.product_Id}">
			<div class="layui-form-item" style="margin-top: 30px;">
			     <div class="layui-inline" style="left: -125px;top:10px;">
				      <label class="layui-form-label" style="width: 90px;">产品名称</label>
				      <div class="layui-input-inline">
				        <input type="text" name="product_Name" lay-verify="product_Name" autocomplete="off" class="layui-input bj" disabled="" value="${product.product_Name}">
				      </div>
			     </div>
			     
			    <div class="layui-inline" style="top:9px;left: -105px;">
				      <label class="layui-form-label" style="width: 90px;">规格型号</label>
				      <div class="layui-input-inline">
				        <input type="text" name="specification_Type" lay-verify="specification_Type" autocomplete="off" class="layui-input bj" disabled="" value="${product.specification_Type}">
				      </div>
			    </div>
			    
			     <div class="layui-inline" style="top:9px;left: -88px;">
				      <label class="layui-form-label" style="width:150px;">单位</label>
				      <div class="layui-input-inline">
				        <input type="text" name="unit" lay-verify="unit" autocomplete="off" class="layui-input bj" disabled="" value="${product.unit}">
				      </div>
			     </div>
			 </div>
			
			<div class="layui-form-item">
			     <div class="layui-inline" style="top:9px;left: -162px;">
				      <label class="layui-form-label" style="width:105px;">出厂价</label>
				      <div class="layui-input-inline">
				        <input type="text" name="factory_Price" lay-verify="factory_Price" autocomplete="off" class="layui-input bj" disabled=""   value="${product.factory_Price}">
				      	<span style="position: relative;top: -25px;right: -105px;">元</span>
				      </div>
			     </div>
			     
			    <div class="layui-inline" style="top:9px;left: -142px;">
				      <label class="layui-form-label" style="width: 90px;">渠道价</label>
				      <div class="layui-input-inline">
				        <input type="text" name="channel_Price" lay-verify="channel_Price" autocomplete="off" class="layui-input bj" disabled=""   value="${product.channel_Price}">
				        <span style="position: relative;top: -25px;right: -105px;">元</span>
				      </div>
			    </div>
			    
			     <div class="layui-inline" style="top:9px;left: -65px;">
				      <label class="layui-form-label" style="width: 90px;">市场价</label>
				      <div class="layui-input-inline">
				        <input type="text" name="market_Value" lay-verify="market_Value" autocomplete="off" class="layui-input bj" disabled=""   value="${product.market_Value}">
				        <span style="position: relative;top: -25px;right: -105px;">元</span>
				      </div>
			     </div>
		   </div>
		  
		 <div class="layui-form-item layui-form-text">
		    <label class="layui-form-label" style="width:79px;">备注</label>
		    <div class="layui-input-block">
		      <textarea placeholder="请输入内容" name="remarks"  lay-verify="remarks" id="remarks" class="layui-textarea bj" disabled="" style="width:81.5%">${product.remarks}</textarea>
		    </div>
		 </div>
	</form>
 </div>
<script src="../layui-v2.5.5/layui/layui.js" charset="utf-8"></script>
<script>
layui.use(['form', 'layedit', 'laydate','upload'], function(){
  var form = layui.form
  ,layer = layui.layer
  ,layedit = layui.layedit
  ,laydate = layui.laydate
  ,upload = layui.upload;
  var url=$('#url').val();
  form.render();
  //日期
  laydate.render({
    elem: '#cont_Date'
  });
  
 
  //创建一个编辑器
  var editIndex = layedit.build('LAY_demo_editor');

  
});




	
</script>
</body>
</html>