<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>编辑定时任务</title>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@page isELIgnored="false" %>
<link rel="stylesheet" href="../jquery-easyui-1.7.0/themes/default/easyui.css"">
<link rel="stylesheet" href="../jquery-easyui-1.7.0/themes/icon.css"">
<script type="text/javascript" src="../jquery-easyui-1.7.0/jquery.min.js" charset="utf-8"></script>
<script type="text/javascript" src="../jquery-easyui-1.7.0/jquery.easyui.min.js" charset="utf-8"></script>
<script type="text/javascript" src="../jquery-easyui-1.7.0/locale/easyui-lang-zh_CN.js" charset="utf-8"></script>
<script type="text/javascript">

	function windowClose() {
		var flag = document.getElementById("flag").value;
		if ("true" == flag) {
			window.parent.opener.location.reload();
			window.close();
		}
	}

	function editJobTask() {
		var url = $('#url').val();
		var form = document.getElementById('myForm');
		form.action = url + "jobTask/editJobTask.do";
		form.submit();
	}
	
	function clearForm() {
		var form = document.getElementById("myForm");
		form.reset();
	}


	

</script>
</head>
<body onload="windowClose()">
	<form action='' id="myForm" method="post">
			<input type="hidden" value="${flag}" id="flag">
			<input type="hidden" value='<c:url value="/"/>' id="url">
			<div class="submitdata">
				<table width="100%">
					<caption>
						任务信息
					</caption>
					<tr>
						<th>
							任务名称
						</th>
						<td>
							<input type="hidden" name="job_Task_Id_" value="${jobTask.job_Task_Id_ }">
							<input name="job_Task_Name_" type="text" style="width: 70%" value="${jobTask.job_Task_Name_}">
						</td>
					</tr>
					<tr>
						<th>
							类名
						</th>
						<td>
							<input name="job_Task_Class_Name_" type="text" style="width: 70%" value="${jobTask.job_Task_Class_Name_}">
						</td>
					</tr>
					<tr>
						<th>
							任务描述
						</th>
						<td>
							<textarea rows="5" cols="5"style="width: 70%" name="job_Task_Remark_">${jobTask.job_Task_Remark_}</textarea>
						</td>
					</tr>
					
					<tr>
						<td colspan="2" align="center" style="text-align: center;">
							<a href='#' class="easyui-linkbutton" iconCls="icon-save" onclick="editJobTask()">保存</a>&nbsp;
							<a href="#" class="easyui-linkbutton" onclick="clearForm();" iconCls="icon-undo">清空</a>&nbsp;
						</td>
					</tr>
				</table>
		</form>
</body>
</html>