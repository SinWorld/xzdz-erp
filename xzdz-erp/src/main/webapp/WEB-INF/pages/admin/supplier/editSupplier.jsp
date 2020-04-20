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
<title>新增供应商</title>
<link rel="stylesheet" href="../layui-v2.5.5/layui/css/layui.css">
<link rel="stylesheet" href="../bootstrap-3.3.7-dist/css/bootstrap.min.css"> 
<link rel="stylesheet" href="../bootstrap-3.3.7-dist/css/bootstrap-theme.min.css"> 
<script src="../jquery/jquery-3.3.1.js"></script>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@page isELIgnored="false" %>
</head>
<body style="width:100%;padding:0px; margin:0px;text-align: center;" onload="refreshAndClose()">
	<div style="width:1280px;height:auto;padding:0px; margin:0 auto;" id="main">
		<form class="layui-form" action='<c:url value="/supplier/editSupplier.do"/>' method="post">
		<input type="hidden" id="url" value='<c:url value="/"/>'>
		<input type="hidden" id="flag" value="${flag}">
		<input type="hidden" value="${supplier.supplier_Id}" name="supplier_Id">
		<input type="hidden" value="${userId}" id="userId">
		
		
			 <div class="layui-form-item" style="margin-top: 5%">
			    <label class="layui-form-label" style="width: 120px;">供应商名称</label>
			    <div class="layui-input-block">
			      <input type="text" name="supplier_Name" lay-verify="supplier_Name" autocomplete="off" placeholder="请输入供应商名称" class="layui-input" style="width:76.5%" id="supplier_Name" value="${supplier.supplier_Name}">
			    </div>
			  </div>
			
			<div class="layui-form-item">
			     <div class="layui-inline" style="top:9px;left: -125px;">
				      <label class="layui-form-label" style="width: 90px;">注册地址</label>
				      <div class="layui-input-inline">
				        <input type="text" name="registered_Address" lay-verify="registered_Address" autocomplete="off" class="layui-input" id="registered_Address" value="${supplier.registered_Address}">
				      </div>
			     </div>
			     
			    <div class="layui-inline" style="top:9px;left: -105px;">
				      <label class="layui-form-label" style="width: 90px;">办公地址</label>
				      <div class="layui-input-inline">
				        <input type="text" name="office_Address" lay-verify="office_Address" autocomplete="off" class="layui-input" id="office_Address" value="${supplier.office_Address}">
				      </div>
			    </div>
			    
			     <div class="layui-inline" style="top:9px;left: -88px;">
				      <label class="layui-form-label" style="width:150px;">社会统一信用代码</label>
				      <div class="layui-input-inline">
				        <input type="text" name="unified_Code" lay-verify="unified_Code" autocomplete="off" class="layui-input" id="unified_Code" value="${supplier.unified_Code}">
				      </div>
			     </div>
			 </div>
			
			<div class="layui-form-item">
			     <div class="layui-inline" style="top:9px;left: -162px;">
				      <label class="layui-form-label" style="width:105px;">法定代表人</label>
				      <div class="layui-input-inline">
				        <input type="text" name="legal_person" lay-verify="legal_person" autocomplete="off" class="layui-input" id="legal_person" value="${supplier.legal_person}">
				      </div>
			     </div>
			     
			    <div class="layui-inline" style="top:9px;left: -142px;">
				      <label class="layui-form-label" style="width: 90px;">开户行</label>
				      <div class="layui-input-inline">
				        <input type="text" name="opening_Bank" lay-verify="opening_Bank" autocomplete="off" class="layui-input" id="opening_Bank" value="${supplier.opening_Bank}">
				      </div>
			    </div>
			    
			     <div class="layui-inline" style="top:9px;left: -65px;">
				      <label class="layui-form-label" style="width: 90px;">账号</label>
				      <div class="layui-input-inline">
				        <input type="text" name="account_Number" lay-verify="account_Number" autocomplete="off" class="layui-input" id="account_Number" value="${supplier.account_Number}">
				      </div>
			     </div>
		   </div>
		   
		   	<div class="layui-form-item">
			     <div class="layui-inline" style="top:9px;left: -154px;">
				      <label class="layui-form-label" style="width: 90px;">税号</label>
				      <div class="layui-input-inline">
				        <input type="text" name="duty_Paragraph" lay-verify="duty_Paragraph" autocomplete="off" class="layui-input" id="duty_Paragraph" value="${supplier.duty_Paragraph}">
				      </div>
			     </div>
			     
			    <div class="layui-inline" style="top:9px;left: -134px;">
				      <label class="layui-form-label" style="width: 90px;">电话</label>
				      <div class="layui-input-inline">
				        <input type="text" name="phone" lay-verify="" autocomplete="off" class="layui-input" id="phone" value="${supplier.phone}">
				      </div>
			    </div>
			    
			     <div class="layui-inline" style="top:9px;left: -56px;">
				      <label class="layui-form-label" style="width: 90px;">传真</label>
				      <div class="layui-input-inline">
				        <input type="text" name="fax" lay-verify="fax" autocomplete="off" class="layui-input" id="fax" value="${supplier.fax}">
				      </div>
			    </div>
		   </div>
		   
		  <div class="layui-form-item">
	     		<div class="layui-inline" style="top:9px;left: -465px;">
			      <label class="layui-form-label" style="width:105px;">联系人</label>
			      <div class="layui-input-inline">
			        <input type="text" name="contacts" lay-verify="contacts" autocomplete="off" class="layui-input" id="contacts" value="${supplier.contacts}">
			      </div>
		     	</div>
		  </div>
		  
		 <div class="layui-form-item layui-form-text">
		    <label class="layui-form-label" style="width:122px;">主营产品</label>
		    <div class="layui-input-block">
		      <textarea placeholder="请输入内容" name="productInfor"  lay-verify="productInfor" id="productInfor" class="layui-textarea" style="width:76.6%">${supplier.productInfor}</textarea>
		    </div>
		 </div>
			
		 <div class="layui-form-item layui-form-text">
		    <label class="layui-form-label" style="width:122px;">备注</label>
		    <div class="layui-input-block">
		      <textarea placeholder="请输入内容" name="remarks"  lay-verify="remarks" id="remarks" class="layui-textarea" style="width:76.6%">${supplier.remarks}</textarea>
		    </div>
		 </div>
	
		<div class="layui-form-item" style="text-align: center;">
		    <div class="layui-input-block">
		      <button class="layui-btn" lay-submit="" lay-filter="demo1" style="width:25%;margin-top:10px;margin-left:-315px;">立即提交</button>
		    </div>
		</div>
	</form>
 </div>
<script src="../bootstrap-3.3.7-dist/js/bootstrap.js"></script>
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


  //监听提交
  $(document).keydown(function (e) {
	if (e.keyCode != 13) {
		 form.on('submit(demo1)', function(data){
			  return true;
		 });
	}else{
		form.on('submit(demo1)', function(data){
			  return false;
		 });
	}
  });
  
});

function refreshAndClose(){
	var flag=$('#flag').val();
	var userId=$('#userId').val();
	if(flag){
		if(userId==-1){
			window.parent.opener.location.reload();
			window.close();
		}else{
			var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
            parent.location.reload();//刷新父页面，注意一定要在关闭当前iframe层之前执行刷新
            parent.layer.close(index); //再执行关闭
		}
	} 
}


	
</script>
</body>
</html>