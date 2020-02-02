<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>基本资料</title>
<link rel="stylesheet" href="../layui-v2.5.5/layui/css/layui.css">
<link rel="stylesheet" href="../bootstrap-3.3.7-dist/css/bootstrap.min.css"> 
<link rel="stylesheet" href="../bootstrap-3.3.7-dist/css/bootstrap-theme.min.css"> 
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@page isELIgnored="false" %>
<style>
  .bj{background-color: #F0F0F0}
 </style>
</head>
<body  style="width:100%;padding:0px; margin:0px;">
	<div style="width:740px;height:auto;padding:0px; margin:0 auto;" id="main">
		<form class="layui-form" action='' method="post" id="myForm" style="margin-top:30px;">
			<input type="hidden" id="url" value='<c:url value="/"/>'>
			<input type="hidden" id="flag" value="${flag}"> 
			
			 
			<div class="layui-form-item">
			 	<div class="layui-inline" style="left:10px;">
				      <label class="layui-form-label" style="width: 120px;">登录名</label>
				      <div class="layui-input-inline">
				        <input type="text" name="loginName" lay-verify="loginName" autocomplete="off" class="layui-input bj" id="loginName" value="${user.loginName}" disabled="">
				      </div>
			    </div>
			    
				<div class="layui-inline" style="left:30px;">
				      <label class="layui-form-label" style="width: 120px;">用户名</label>
				      <div class="layui-input-inline">
				        <input type="text" name="userName" lay-verify="userName" autocomplete="off" class="layui-input bj" id="userName" value="${user.userName}" disabled="">
				      </div>
			    </div>
			    
			 </div>
		 
		 	<div class="layui-form-item">
		 	
		 		<div class="layui-inline" style="left:30px;">
				      <label class="layui-form-label" style="width: 102px;">性别</label>
				      <div class="layui-input-inline">
				          <input type="text" name="gender" lay-verify="gender" autocomplete="off" class="layui-input bj" id="gender" value="${user.gender}" disabled="">
				      </div>
			    </div>
		 	
				  <div class="layui-inline" style="left:48px;">
				      <label class="layui-form-label" style="width: 120px;">所属部门</label>
				      <div class="layui-input-inline">
				        <input type="text" name="dep_Name" lay-verify="dep_Name" autocomplete="off" class="layui-input bj" id="dep_Name" value="${user.dep_Name}"disabled="">
				      </div>
			    </div>
				 
			    
			 </div>
			 
			 <div class="layui-form-item">
			 
			 	<div class="layui-inline" style="left:10px;">
				      <label class="layui-form-label" style="width: 120px;">所属岗位</label>
				      <div class="layui-input-inline">
				        <input type="text" name="post_Name" lay-verify="post_Name" autocomplete="off" class="layui-input bj" id="post_Name" value="${user.post_Name}" disabled="">
				      </div>
			    </div>
			    
			    <div class="layui-inline" style="left:30px;">
				      <label class="layui-form-label" style="width: 120px;">联系方式</label>
				      <div class="layui-input-inline">
				        <input type="text" name="phoneNumber" lay-verify="phoneNumber" autocomplete="off" class="layui-input bj" id="phoneNumber" value="${user.phoneNumber}" disabled="">
				      </div>
			    </div>
				 
		 	</div>
		 	
		 	 <div class="layui-form-item">
			 	 <div class="layui-inline" style="left:10px;">
				      <label class="layui-form-label" style="width: 120px;">邮箱</label>
				      <div class="layui-input-inline">
				        <input type="text" name="email" lay-verify="email" autocomplete="off" class="layui-input bj" id="email" value="${user.email}" disabled="">
				      </div>
				 </div>
			</div>
	</form>
 </div>
<script src="../layui-v2.5.5/layui/layui.js" charset="utf-8"></script>
<script src="../jquery/jquery-3.3.1.js"></script>
<script>
layui.use(['form', 'layedit', 'laydate'], function(){
  var form = layui.form
  ,layer = layui.layer
  ,layedit = layui.layedit
  ,laydate = layui.laydate;
  var url=$('#url').val();
  form.render();

  //创建一个编辑器
  var editIndex = layedit.build('LAY_demo_editor');
  

});
 
  

  

</script>
</body>
</html>