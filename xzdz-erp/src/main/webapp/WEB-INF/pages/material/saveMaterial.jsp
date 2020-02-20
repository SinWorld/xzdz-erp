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
<style>
  .bj{background-color: #F0F0F0}
 </style>
</head>
<body style="width:100%;padding:0px; margin:0px;text-align: center;" onload="refreshAndClose()">
	<div style="width:1280px;height:auto;padding:0px; margin:0 auto;" id="main">
		<form class="layui-form" action='<c:url value="/material/saveMaterial.do"/>' method="post">
		<input type="hidden" id="url" value='<c:url value="/"/>'>
		<input type="hidden" id="flag" value="${flag}">
			
			<div class="layui-form-item" style="margin-top: 5%">
			    <label class="layui-form-label" style="width: 120px;">材料名称</label>
			    <div class="layui-input-block">
			      <input type="text" name="material_Name" lay-verify="material_Name" autocomplete="off" placeholder="材料名称" class="layui-input" style="width:76.5%">
			    </div>
			</div>
			
			<div class="layui-form-item">
			     
			     <div class="layui-inline" style="top:9px;left: -80px;">
				      <label class="layui-form-label" style="width:150px;">规格型号</label>
				      <div class="layui-input-inline">
				        <input type="text" name="specification_Type" id="specification_Type" lay-verify="specification_Type" autocomplete="off" class="layui-input" onblur="materiel_materielId()">
				      </div>
			     </div>
			     
			      <div class="layui-inline" style="top:9px;left: -134px;">
				      <label class="layui-form-label" style="width:150px;">单位</label>
				      <div class="layui-input-inline">
				        <input type="text" name="unit" lay-verify="unit" autocomplete="off" class="layui-input">
				      </div>
			      </div>
			      
			       <div class="layui-inline" style="top:9px;left: -163px;">
				      <label class="layui-form-label" style="width:150px;">生产数量</label>
				      <div class="layui-input-inline">
				        <input type="text" name="numbers" lay-verify="numbers" autocomplete="off" class="layui-input">
				      </div>
			      </div>
			 </div>
			 
			<div class="layui-form-item" style="top:9px;">
		  	 <label class="layui-form-label" style="width: 120px;">物料Id</label>
		      <div class="layui-input-inline">
		        <input type="text" name="materielId"  autocomplete="off" class="layui-input bj" id="materielId" readonly="readonly">
		      </div>
			</div>
			
		 <div class="layui-form-item layui-form-text">
		    <label class="layui-form-label" style="width:120px;">备注</label>
		    <div class="layui-input-block">
		      <textarea placeholder="请输入内容" name="remarks"  lay-verify="remarks" id="remarks" class="layui-textarea" style="width:76.5%"></textarea>
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

//自定义验证规则
  form.verify({
	  material_Name: function(value){
	      if(value==""||value==null){
	        return '材料名称不能为空';
	      }
	  }
      ,specification_Type:function(value){
	       if(value==""||value==null){
	           return '规格型号不能为空';
	       }
      }
      ,unit:function(value){
	       if(value==""||value==null){
	           return '单位不能为空';
	         }
      }
       ,numbers:function(value){
	        if(value==""||value==null){
	            return '生产数量不能为空';
	          }
       }
  });
  
});


	function refreshAndClose(){
		var flag=$('#flag').val();
		if(flag){
			window.parent.location.reload();
			window.close();
		} 
	}

	//加载材料对应的物料Id
	function materiel_materielId(){
		var specification_Type=$('#specification_Type').val();
		$.ajax({
			type : "post",
			url : "<c:url value='/material/materiel_materielId.do'/>",
			async : false,
			dataType : 'json',
			data:{"specification_Type":specification_Type},
			error : function() {
				alert("出错");
			},
			success : function(msg) {
				$('#materielId').val(msg.materielId);
			}
		});
		
	}


	
</script>
</body>
</html>