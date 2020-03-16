<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>财务付款</title>
<link rel="stylesheet" href="../layui-v2.5.5/layui/css/layui.css">
<link rel="stylesheet" href="../bootstrap-3.3.7-dist/css/bootstrap.min.css"> 
<link rel="stylesheet" href="../bootstrap-3.3.7-dist/css/bootstrap-theme.min.css">
<script src="../jquery/jquery-3.3.1.js"></script>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@page isELIgnored="false" %>
<style>
  .bj{background-color: #F0F0F0}
 </style>
</head>
<body style="width:100%;padding:0px; margin:0px;" onload="refreshAndClose()">
	<div style="width:650px;height:auto;padding:0px; margin:0 auto;" id="main">
		<form class="layui-form" action='<c:url value="/cghtfk_cwfk/saveCwfk.do"/>' method="post" id="myForm">
			<input type="hidden" id="url" value='<c:url value="/"/>'>
			<input type="hidden" id="flag" value="${flag}">
			<input type="hidden" name="task_Id" id="taskId" value="${taskId}">
			<input type="hidden" name="cghtfk_Id" value="${cghtfk.cghtfk_Id}">
		
			<div class="layui-form-item">
		 
		  	  <div class="layui-inline" style="margin-top: 3%;left: 54px;">
				      <label class="layui-form-label" style="width: 150px;">付款金额</label>
				      <div class="layui-input-inline">
				        <input type="text"  lay-verify="" placeholder="" autocomplete="off" class="layui-input bj" style="width: 280px;" value="${cghtfk.sqfkje}" readonly="readonly">
				      </div>
			    </div>
		 
		      <div class="layui-inline" style="top:9px;left: 54px;">
			      <label class="layui-form-label" style="width:150px;">付款日期</label>
			      <div class="layui-input-inline">
			        <input type="text"   name="fkrq" lay-verify="" autocomplete="off" class="layui-input bj" style="width:280px;" value="${fkrq}" readonly="readonly">
			      </div>
		     </div>
		 </div>
		 
			<div class="layui-form-item layui-form-text" style="margin-left:80px;">
				<label class="layui-form-label" style="width: 125px;">付款说明</label>
				<div class="layui-input-block">
					<textarea placeholder="请输入内容" lay-verify="fksm" class="layui-textarea" style="width:60.5%" name="fksm"></textarea>
				</div>
			</div>
	
		<div class="layui-form-item" style="text-align: center;">
		    <div class="layui-input-block">
		      <button class="layui-btn" lay-submit="" lay-filter="demo1" style="width:25%;margin-left:-100px;">立即提交</button>
		    </div>
		</div>
	</form>
 </div>
<script src="../bootstrap-3.3.7-dist/js/bootstrap.js"></script>
<script src="../layui-v2.5.5/layui/layui.js" charset="utf-8"></script>
<script type="text/javascript" src="../jquery-easyui-1.7.0/jquery.easyui.min.js"></script>
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
	  //监听提交
	  form.on('submit(demo1)', function(data){
	   /*  layer.alert(JSON.stringify(data.field), {
	      title: '最终的提交信息'
	    }) */
	    return true;
	  });

	//自定义验证规则
	  form.verify({
		  fksm(value){
		       if(value==""||value==null){
		           return '付款说明不能为空';
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
</script>
</body>
</html>