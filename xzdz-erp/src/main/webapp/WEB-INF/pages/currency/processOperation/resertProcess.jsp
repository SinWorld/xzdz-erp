<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>重启流程</title>
<link rel="stylesheet" href="../layui-v2.5.5/layui/css/layui.css">
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@page isELIgnored="false" %>
	<style type="text/css">
		.demo{width: auto;padding: 20px;}
	</style>
</head>
<body onload="refreshAndClose()">
<div style="margin-top:20px; margin-left: auto; margin-right: auto; width: auto;">
		<form class="layui-form" id="form" method="post">
			<input type="hidden" id="url" value='<c:url value="/"/>'>
			<input type="hidden" value="${taskId}" id="taskId">
			<input type="hidden" value="${zt}" id="zt">
			<input type="hidden" id="flag" value="${flag}">
			<div class="demo">
				<span style="margin-left: 100px;">
					<button type="button" class="layui-btn layui-btn-danger layui-btn-lg" id="zdlc">中断流程</button>
					<button type="button" class="layui-btn layui-btn-normal layui-btn-lg" id="hflc" >恢复流程</button>
				</span>
			</div
		</form>
	</div>
	<script src="../layui-v2.5.5/layui/layui.js" charset="utf-8"></script>
<!-- 注意：如果你直接复制所有代码到本地，上述js路径需要改成你本地的 --> 
 <script type="text/javascript" src="../jquery/jquery-3.3.1.js"></script>
<script>
layui.use(['form', 'layedit', 'laydate'], function(){
	  var form = layui.form
	  ,layer = layui.layer
	  ,layedit = layui.layedit
	  ,laydate = layui.laydate;
	  setStatus();
	  form.render();
});

	//中断流程
	$('#zdlc').click(function (){
	  	var form=document.getElementById('form');
	 	var url=$('#url').val();
	  	var taskId=$('#taskId').val();
		form.action=url+"cqlc/zdlc.do?taskId="+taskId;
		form.submit();  	
	});

	//恢复流程
	$('#hflc').click(function (){
	  	var form=document.getElementById('form');
	 	var url=$('#url').val();
	  	var taskId=$('#taskId').val();
		form.action=url+"cqlc/hflc.do?taskId="+taskId;
		form.submit();  	
	});

	//设置按钮状态
	function setStatus(){
		var flag=$('#zt').val();
		if(flag=="true"){
			//处于中断状态
			$('#zdlc').addClass("layui-btn-disabled");
			$("#zdlc").attr("disabled", true);
		}else{
			//处于恢复状态
			$('#hflc').addClass("layui-btn-disabled");
			$("#hflc").attr("disabled", true);
		}
	}

	function refreshAndClose(){
		  var flag=$('#flag').val();
		  if(flag){
			  var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
		      parent.location.reload();//刷新父页面，注意一定要在关闭当前iframe层之前执行刷新
		      parent.layer.close(index); //再执行关闭
		  }
	}
	
</script>
</body>
</html>