<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>文件导入</title>
<link rel="stylesheet" href="../layui-v2.5.5/layui/css/layui.css">
<script src="../jquery/jquery-3.3.1.js"></script>
<script src="../layui-v2.5.5/layui/layui.js" charset="utf-8"></script>
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
	function fileImport(){
		//验证文件是否符合
		var attach = document.getElementById("file").value;
		if(attach == undefined || attach == ""){
			alert("请选择文件");
			return false;
		}
		var extStart = attach.lastIndexOf(".");
		var ext = attach.substring(extStart, attach.length).toUpperCase();
		if (ext != ".XLS") {
			alert("导入文件格式不正确");
			return false;
		}
		var form=document.getElementById('myForm');
		var url=$('#url').val();
		form.action=url+"materielId/importExcel.do";
		form.submit();
	}
</script>
</head>
<body onload="windowClose()">
	<form action='' method="post" enctype="multipart/form-data" id="myForm">
		<div style="margin-top: 10%">
			<label class="layui-form-label" style="width:33%;padding-top: 0%">请选择文件(.XLS)</label>
			<div>
				<input type="file" name="file" class="InputStyle" style="width:357px;" id="file" multiple="multiple"/> 
				<input type="hidden" id="flag" value="${flag}">
				<input type="hidden" value='<c:url value="/"/>' id="url">
			</div>
		</div>
		 <div class="layui-input-block">
	      <button class="layui-btn"  lay-submit="" type="button" lay-filter="" style="width:35%;margin-top:5%;" onclick="fileImport()">立即提交</button>
	    </div>
    </form>
</body>
</html>