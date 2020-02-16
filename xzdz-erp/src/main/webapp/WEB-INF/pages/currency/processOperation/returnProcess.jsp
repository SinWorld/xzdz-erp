<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>退回流程</title>
<link rel="stylesheet" href="../layui-v2.5.5/layui/css/layui.css">
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@page isELIgnored="false" %>
</head>
<body onload="refreshAndClose()">
<div style="margin-top:20px; margin-left: auto; margin-right: auto; width: auto;">
	<form class="layui-form"  method="post" action='<c:url value="/takeBack/takeBack.do"/>'>
		<input type="hidden" id="url" value='<c:url value="/"/>'>
			<input type="hidden" value="${taskId}" name="taskId" id="taskId">
			<input type="hidden" id="flag" value="${flag}">
			<div class="layui-form-item layui-form-text">
				<label class="layui-form-label" style="width: 125px;">退回原因</label>
				<div class="layui-input-block">
					<textarea placeholder="请输入内容" lay-verify="remark"
						class="layui-textarea" style="width: 56.5%" name="advice" id="advice"></textarea>
				</div>
			</div>

			<div class="layui-form-item" style="text-align: center;">
				<div class="layui-input-block">
					<button class="layui-btn" lay-submit="" lay-filter="demo1"
						style="width: 35%; margin-top: 10px;margin-left: -80px;">立即提交</button>
					<!--  <button type="reset" class="layui-btn layui-btn-primary">重置</button> -->
				</div>
			</div>
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
	  
	form.verify({
		remark: function(value){
		     if(value==""){
		     	 return '原因不能为空';
		     }
		   }
	}); 
	 
});

function refreshAndClose(){
	var flag=$('#flag').val();
	if(flag){
		window.top.location.reload();
		window.parent.close();
	} 
}
	
</script>
</body>
</html>