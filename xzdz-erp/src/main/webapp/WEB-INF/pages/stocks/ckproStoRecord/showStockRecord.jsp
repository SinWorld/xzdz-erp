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
<title>查看成品</title>
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
		<input type="hidden" id="url" value='<c:url value="/"/>'>
		<input type="hidden" id="flag" value="${flag}">
		<input type="hidden" name="raw_Material_Id" value="${material.raw_Material_Id }">
			
		<div class="layui-form-item" style="margin-top: 5%">
		    <label class="layui-form-label" style="width: 120px;">成品名称</label>
		    <div class="layui-input-block">
		      <input type="text" name="productName" lay-verify="productName" autocomplete="off" placeholder="材料名称" class="layui-input bj" style="width:76.5%" value="${record.productName}" disabled="">
		    </div>
		</div>
		
		<div class="layui-form-item">
		    <label class="layui-form-label" style="width: 120px;">库位</label>
		    <div class="layui-input-block">
		      <input type="text" name="stockName" lay-verify="stockName" autocomplete="off" placeholder="材料名称" class="layui-input bj" style="width:76.5%" value="${record.stockName}" disabled="">
		    </div>
		</div>
		
		<div class="layui-form-item">
		     <div class="layui-inline" style="top:9px;left: -80px;">
			      <label class="layui-form-label" style="width:150px;">出库数量</label>
			      <div class="layui-input-inline">
			        <input type="text" name="sl" lay-verify="sl" autocomplete="off" class="layui-input bj" value="${record.sl}" disabled=""> 
			      </div>
		     </div>
		     
		      <div class="layui-inline" style="top:9px;left: -134px;">
			      <label class="layui-form-label" style="width:150px;">出库时间</label>
			      <div class="layui-input-inline">
			        <input type="text" name="sj" lay-verify="sj" autocomplete="off" class="layui-input bj" value="${sj}" disabled="">
			      </div>
		      </div>
		      
		       <div class="layui-inline" style="top:9px;left: -163px;">
			      <label class="layui-form-label" style="width:150px;">经办人</label>
			      <div class="layui-input-inline">
			        <input type="text" name="userName" lay-verify="userName" autocomplete="off" class="layui-input bj" value="${record.userName}" disabled="">
			      </div>
		      </div>
		</div>
		
		<div class="layui-form-item layui-form-text">
		    <label class="layui-form-label" style="width:120px;">备注</label>
		    <div class="layui-input-block">
		      <textarea placeholder="请输入内容" name="remarks"  lay-verify="remarks" id="remarks" class="layui-textarea bj" style="width:76.5%" disabled="">${record.remarks}</textarea>
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
  
});


	

	
</script>
</body>
</html>