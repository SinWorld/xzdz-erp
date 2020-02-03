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
<title>查看单位</title>
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
		<form class="layui-form" action="<c:url value=""/>" method="post">
		<input type="hidden" id="url" value='<c:url value="/"/>'>
		<input type="hidden" name="unit_Id" value="${unit.unit_Id }">
			 <div class="layui-form-item" style="margin-top: 5%">
			    <label class="layui-form-label" style="width:65px;">单位名称</label>
			    <div class="layui-input-block">
			      <input type="text" name="unit_Name" lay-verify="unit_Name" autocomplete="off" placeholder="请输入单位名称" class="layui-input bj" style="width:81.3%" id="unit_Name" value="${unit.unit_Name}" disabled="">
			    </div>
			  </div>
			
			<div class="layui-form-item">
			     <div class="layui-inline" style="top:9px;left: -125px;">
				      <label class="layui-form-label" style="width: 90px;">注册地址</label>
				      <div class="layui-input-inline">
				        <input type="text" name="registered_Address" lay-verify="registered_Address" autocomplete="off" class="layui-input bj" id="registered_Address" value="${unit.registered_Address}" disabled="">
				      </div>
			     </div>
			     
			    <div class="layui-inline" style="top:9px;left: -105px;">
				      <label class="layui-form-label" style="width: 90px;">办公地址</label>
				      <div class="layui-input-inline">
				        <input type="text" name="office_Address" lay-verify="office_Address" autocomplete="off" class="layui-input bj" id="office_Address" value="${unit.office_Address}" disabled="">
				      </div>
			    </div>
			    
			     <div class="layui-inline" style="top:9px;left: -88px;">
				      <label class="layui-form-label" style="width:150px;">社会统一信用代码</label>
				      <div class="layui-input-inline">
				        <input type="text" name="unified_Code" lay-verify="unified_Code" autocomplete="off" class="layui-input bj" id="unified_Code" value="${unit.unified_Code}" disabled="">
				      </div>
			     </div>
			 </div>
			
			<div class="layui-form-item">
			     <div class="layui-inline" style="top:9px;left: -162px;">
				      <label class="layui-form-label" style="width:105px;">法定代表人</label>
				      <div class="layui-input-inline">
				        <input type="text" name="legal_person" lay-verify="legal_person" autocomplete="off" class="layui-input bj" id="legal_person" value="${unit.legal_person}" disabled="">
				      </div>
			     </div>
			     
			    <div class="layui-inline" style="top:9px;left: -142px;">
				      <label class="layui-form-label" style="width: 90px;">开户行</label>
				      <div class="layui-input-inline">
				        <input type="text" name="opening_Bank" lay-verify="opening_Bank" autocomplete="off" class="layui-input bj" id="opening_Bank" value="${unit.opening_Bank}" disabled="">
				      </div>
			    </div>
			    
			     <div class="layui-inline" style="top:9px;left: -65px;">
				      <label class="layui-form-label" style="width: 90px;">账号</label>
				      <div class="layui-input-inline">
				        <input type="text" name="account_Number" lay-verify="account_Number" autocomplete="off" class="layui-input bj" id="account_Number" value="${unit.account_Number}" disabled="">
				      </div>
			     </div>
		   </div>
		   
		   	<div class="layui-form-item">
			     <div class="layui-inline" style="top:9px;left: -154px;">
				      <label class="layui-form-label" style="width: 90px;">税号</label>
				      <div class="layui-input-inline">
				        <input type="text" name="duty_Paragraph" lay-verify="duty_Paragraph" autocomplete="off" class="layui-input bj" id="duty_Paragraph" value="${unit.duty_Paragraph}" disabled="">
				      </div>
			     </div>
			     
			    <div class="layui-inline" style="top:9px;left: -134px;">
				      <label class="layui-form-label" style="width: 90px;">电话</label>
				      <div class="layui-input-inline">
				        <input type="text" name="telPhone" lay-verify="telPhone" autocomplete="off" class="layui-input bj" id="telPhone" value="${unit.telPhone}" disabled="">
				      </div>
			    </div>
			    
			     <div class="layui-inline" style="top:9px;left: -56px;">
				      <label class="layui-form-label" style="width: 90px;">传真</label>
				      <div class="layui-input-inline">
				        <input type="text" name="fax" lay-verify="fax" autocomplete="off" class="layui-input bj" id="fax" value="${unit.fax}" disabled="">
				      </div>
			    </div>
		   </div>
		   
		    <div class="layui-form-item">
	     		<div class="layui-inline" style="top:9px;left: -494px;">
			      <label class="layui-form-label" style="width:105px;">委托代理人</label>
			      <div class="layui-input-inline">
			        <input type="text" name="wtdlr" lay-verify="wtdlr" autocomplete="off" class="layui-input bj" id="wtdlr" value="${unit.wtdlr}" disabled="">
			      </div>
		     	</div>
		  </div>
		
		 <div class="layui-form-item layui-form-text">
		    <label class="layui-form-label" style="width:75px;">备注</label>
		    <div class="layui-input-block">
		      <textarea placeholder="请输入内容" name="remarks"  lay-verify="remarks" id="remarks" class="layui-textarea bj" style="width:81.6%" disabled="">${unit.remarks}</textarea>
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

 
  //创建一个编辑器
  var editIndex = layedit.build('LAY_demo_editor');

  
});


	
</script>
</body>
</html>