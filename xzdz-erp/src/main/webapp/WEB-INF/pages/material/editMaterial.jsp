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
<title>新增材料</title>
<link rel="stylesheet" href="../layui-v2.5.5/layui/css/layui.css">
<script src="../jquery/jquery-3.3.1.js"></script>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@page isELIgnored="false" %>
</head>
<body style="width:100%;padding:0px; margin:0px;text-align: center;" onload="refreshAndClose()">
	<div style="width:1280px;height:auto;padding:0px; margin:0 auto;" id="main">
		<form class="layui-form" action='<c:url value="/material/editMaterial.do"/>' method="post">
		<input type="hidden" id="url" value='<c:url value="/"/>'>
		<input type="hidden" id="flag" value="${flag}">
		<input type="hidden" name="raw_Material_Id" value="${material.raw_Material_Id }">
			
		<div class="layui-form-item" style="margin-top: 5%">
		    <label class="layui-form-label" style="width: 120px;">材料名称</label>
		    <div class="layui-input-block">
		      <input type="text" name="material_Name" lay-verify="material_Name" autocomplete="off" placeholder="材料名称" class="layui-input" style="width:76.5%" value="${material.material_Name}">
		    </div>
		</div>
		
		<div class="layui-form-item">
		     <div class="layui-inline" style="top:9px;left: -80px;">
			      <label class="layui-form-label" style="width:150px;">规格型号</label>
			      <div class="layui-input-inline">
			        <input type="text" name="specification_Type" lay-verify="specification_Type" autocomplete="off" class="layui-input" value="${material.specification_Type}">
			      </div>
		     </div>
		     
		      <div class="layui-inline" style="top:9px;left: -134px;">
			      <label class="layui-form-label" style="width:150px;">单位</label>
			      <div class="layui-input-inline">
			        <input type="text" name="unit" lay-verify="unit" autocomplete="off" class="layui-input" value="${material.unit}">
			      </div>
		      </div>
		      
		       <div class="layui-inline" style="top:9px;left: -163px;">
			      <label class="layui-form-label" style="width:150px;">生产数量</label>
			      <div class="layui-input-inline">
			        <input type="text" name="numbers" lay-verify="numbers" autocomplete="off" class="layui-input" value="${material.numbers}">
			      </div>
		      </div>
		</div>
		
		<div class="layui-form-item layui-form-text">
		    <label class="layui-form-label" style="width:120px;">备注</label>
		    <div class="layui-input-block">
		      <textarea placeholder="请输入内容" name="remarks"  lay-verify="remarks" id="remarks" class="layui-textarea" style="width:76.5%">${material.remarks}</textarea>
		    </div>
		 </div>
	
		<div class="layui-form-item" style="text-align: center;">
		    <div class="layui-input-block">
		      <button class="layui-btn" lay-submit="" lay-filter="demo1" style="width:25%;margin-top:10px;margin-left:-315px;">立即提交</button>
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

//监听提交
  form.on('submit(demo1)', function(data){
    layer.alert(JSON.stringify(data.field), {
      title: '最终的提交信息'
    })
    return true;
  });
  
});


	function refreshAndClose(){
		var flag=$('#flag').val();
		if(flag){
			window.parent.location.reload();
			window.close();
		} 
	}


	
</script>
</body>
</html>