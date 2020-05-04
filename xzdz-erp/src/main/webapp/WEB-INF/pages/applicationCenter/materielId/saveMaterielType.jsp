<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新增物料Id类型</title>
<link rel="stylesheet" href="../layui-v2.5.5/layui/css/layui.css">
<script src="../jquery/jquery-3.3.1.js"></script>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@page isELIgnored="false" %>
<style>
  .bj{background-color: #F0F0F0}
 </style>
</head>
<body onload="refreshAndClose()" style="width:100%;padding:0px; margin:0px;text-align: center;">
<div style="height:auto;padding:0px; margin:0 auto;" id="main">
	<form class="layui-form" action="<c:url value='/materielType/saveMaterielType.do'/>" method="post">
		<input type="hidden" id="url" value='<c:url value="/"/>'>
		<input type="hidden" id="flag" value="${flag}">
		<input type="hidden" name="flwjj" value="${materielType.id}" id="parent_id"> 
			
			<div class="layui-form-item" style="margin-top: 5%">
			    <label class="layui-form-label" style="width: 150px;">父节点名称</label>
			    <div class="layui-input-block">
			      <input type="text"  autocomplete="off" value="${materielType.title}" class="layui-input bj" style="width: 51.4%" disabled="">
			    </div>
			</div>
			
			<div class="layui-form-item" style="margin-top: 5%">
			    <label class="layui-form-label" style="width: 150px;">子节点名称</label>
			    <div class="layui-input-block">
			      <input type="text" name="title" lay-verify="title" autocomplete="off" placeholder="请输入文件夹名称" class="layui-input" style="width: 51.4%" id="title" onblur="checkMaterielType()">
			    </div>
			</div>
		
			<div class="layui-form-item">
			    <div class="layui-input-block">
			      <button class="layui-btn" lay-submit="" lay-filter="demo1" style="width:35%;margin-top:10px;margin-left: -135px;">立即提交</button>
			    </div>
			</div>
	</form>
 </div>
 <script src="../layui-v2.5.5/layui/layui.js" charset="utf-8"></script>
 <script>
	layui.use(['form', 'layedit',], function(){
	  var form = layui.form
	  ,layer = layui.layer
	  ,layedit = layui.layedit;
	 
	  //自定义验证规则
	  form.verify({
		  title: function(value,item){
	      if(value==""||value==null){
	        return '文件夹名称不能为空';
	      }
	    }
	  });
	  
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
		if(flag){
			window.parent.location.reload();
			window.close();
		} 
	}

	//ajax新增检测物料Id类型号不重复
	function checkMaterielType() {
		var parent_id=$('#parent_id').val();
		var title=$('#title').val();
		$.ajax({
			type : "post",
			url : "<c:url value='/materielType/checkMaterielType.do'/>",
			async : false,
			dataType : 'json',
			data:{"parent_id":parent_id,"title":title},
			error : function() {
				alert("出错");
			},
			success : function(msg) {
				if(msg.flag==false){
					$('#title').val('');
					layer.alert("当前物料Id号已存在同级目录中，不允许新增!!!",{icon:7});
				}
			}
		});
	}
</script>
</body>
</html>