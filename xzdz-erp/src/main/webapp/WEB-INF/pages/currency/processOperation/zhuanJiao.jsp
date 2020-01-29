<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>转交</title>
<link rel="stylesheet" href="../layui-v2.5.5/layui/css/layui.css">
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@page isELIgnored="false" %>
</head>
<body onload="refreshAndClose()">
<div style="margin-top:20px; margin-left: auto; margin-right: auto; width: auto;">
		<form class="layui-form" id="form" method="post">
			<input type="hidden" id="url" value='<c:url value="/"/>'>
			<input type="hidden" value="${taskId}" id="taskId">
			<input type="hidden" id="flag" value="${flag}">
			<input type="hidden" id="userIds">
			<div class="layui-form-item" pane="">
			    <label class="layui-form-label">人员</label>
			    <div class="layui-input-block">
			    <c:forEach items="${list }" var="l">
			      <input type="checkbox" name="users" lay-skin="primary" title="${l.userName}" value="${l.userId}">
			    </c:forEach>
			    </div>
			</div>
			
			<div class="layui-form-item" style="text-align: center;">
				<div class="layui-input-block">
					<button class="layui-btn" lay-submit="" lay-filter="demo1"
						style="width: 35%; margin-top: 10px;margin-left: -80px;" type="button" id="sub">立即提交</button>
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
	 
	  //监听提交
	  $('#sub').click(function(){
		  var form=document.getElementById('form');
		  var url=$('#url').val();
		  var task_id=$('#taskId').val();
		  $('#userIds').val("");
		  var userIds=$('#userIds').val();
		  //获得复选框集合
		  var users=$("input[name='users']");
		  //遍历该集合
		  for(var i=0;i<users.length;i++){
			  //判断该元素是否处于选中
			  if(users[i].checked){
				  userIds=userIds+","+users[i].value;
				}
			}
		  form.action=url+"zj/zhuanJiao.do?taskId="+task_id+"&userIds="+userIds;
		  form.submit();
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