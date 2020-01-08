<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>部署流程定义</title>
<link rel="stylesheet" href="../layui-v2.5.5/layui/css/layui.css">
<script src="../jquery/jquery-3.3.1.js"></script>
<script src="../layui-v2.5.5/layui/layui.js" charset="utf-8"></script>
<script src="../js/admin/processDefinition/saveProcessDefintion.js"></script>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@page isELIgnored="false" %>
<script type="text/javascript">
function windowClose() {
	var flag = document.getElementById("flag").value;
	if ("true" == flag) {
		window.parent.opener.location.reload();
		window.close();
	}
}
</script>
</head>
<body onload="windowClose()">
	<form action='<c:url value="/procdef/deploy.do"/>' method="post" enctype="multipart/form-data">
		<div style="margin-top: 10%">
			<label class="layui-form-label" style="width:31%;padding-top: 0%">请选择流程定义文档(zip格式)</label>
			<div>
				<input type="file" name="file" class="InputStyle" style="width:357px;" id="file"/> 
				<input type="hidden" id="flag" value="${flag}">
			</div>
		</div>
		 <div class="layui-input-block">
	      <button class="layui-btn" lay-submit="" lay-filter="demo1" style="width:35%;margin-top:5%;">立即提交</button>
	    </div>
    </form>
</body>
</html>