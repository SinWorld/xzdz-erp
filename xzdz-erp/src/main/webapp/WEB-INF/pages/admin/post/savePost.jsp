<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新增岗位</title>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@page isELIgnored="false" %>
<link rel="stylesheet" href="../jquery-easyui-1.7.0/themes/default/easyui.css"">
<link rel="stylesheet" href="../jquery-easyui-1.7.0/themes/icon.css"">
<script type="text/javascript" src="../jquery-easyui-1.7.0/jquery.min.js" charset="utf-8"></script>
<script type="text/javascript" src="../jquery-easyui-1.7.0/jquery.easyui.min.js" charset="utf-8"></script>
<script type="text/javascript" src="../jquery-easyui-1.7.0/locale/easyui-lang-zh_CN.js" charset="utf-8"></script>
<script type="text/javascript">

	function windowClose() {
		initOrgTree();
		var flag = document.getElementById("flag").value;
		if ("true" == flag) {
			window.parent.opener.location.reload();
			window.close();
		}
	}

	function saveRole() {
		var url = $('#url').val();
		var form = document.getElementById('myForm');
		form.action = url + "post/savePost.do";
		form.submit();
	}
	
	function clearForm() {
		var form = document.getElementById("myForm");
		form.reset();
	}


	//ajax实现部门树初始化
	function initOrgTree() {
		$.ajax({
			type : "post",
			url : "<c:url value='/department/orgDepartment.do'/>",
			async : false,
			dataType : 'json',
			error : function() {
				alert("出错");
			},
			success : function(msg) {
				for (var i = 0; i < msg.length; i++) {
					for(var j=0;j<msg[i].length;j++){
						$("#sxbm").append(
						    "<option value='"+msg[i][j].dep_Id+"'>"+ msg[i][j].dep_Name +"</option>"); 
					}
				}
			}
		});
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
						岗位信息
					</caption>
					<tr>
						<th>
							岗位名称
						</th>
						<td>
							<input name="post_Name" type="text" style="width: 70%">
						</td>
					</tr>
					<tr>
						<th>
							所属部门
						</th>
						<td>
							<select name="post_Department" style="width: 70%" id="sxbm">
								<option value="">--请选择部门--</option>
							</select>
						</td>
					</tr>
					<tr>
						<th>
							岗位描述
						</th>
						<td>
							<textarea rows="5" cols="5"style="width: 70%" name="post_Code"></textarea>
						</td>
					</tr>
					
					<tr>
						<td colspan="2" align="center" style="text-align: center;">
							<a href='#' class="easyui-linkbutton" iconCls="icon-save" onclick="saveRole()">保存</a>&nbsp;
							<a href="#" class="easyui-linkbutton" onclick="clearForm();" iconCls="icon-undo">清空</a>&nbsp;
						</td>
					</tr>
				</table>
		</form>
</body>
</html>