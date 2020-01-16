<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新增功能</title>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@page isELIgnored="false" %>
<link rel="stylesheet" href="../jquery-easyui-1.7.0/themes/default/easyui.css"">
<link rel="stylesheet" href="../jquery-easyui-1.7.0/themes/icon.css"">
<script type="text/javascript" src="../jquery-easyui-1.7.0/jquery.min.js" charset="utf-8"></script>
<script type="text/javascript" src="../jquery-easyui-1.7.0/jquery.easyui.min.js" charset="utf-8"></script>
<script type="text/javascript" src="../jquery-easyui-1.7.0/locale/easyui-lang-zh_CN.js" charset="utf-8"></script>
<script type="text/javascript">
	//ajax实现部门树初始化
	function initOrgTree() {
		$.ajax({
			type : "post",
			url : "<c:url value='/function/orgFunction.do'/>",
			async : false,
			dataType : 'json',
			error : function() {
				alert("出错");
			},
			success : function(msg) {
				for (var i = 0; i < msg.length; i++) {
					for(var j=0;j<msg[i].length;j++){
						$("#sjbm").append(
						    "<option value='"+msg[i][j].fp_Id+"'>"+ msg[i][j].fp_Name +"</option>"); 
					}
				}
			}
		});
	}

	function windowClose(){
		initOrgTree();
		var flag=$('#flag').val();
		if("true" == flag){
			window.parent.opener.location.reload();
			window.close();
		}
	}

	function saveDepartment(){
		var url=$('#url').val();
		var form=document.getElementById('myForm');
		form.action=url+"function/saveFunction.do";
		form.submit();
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
						功能信息
					</caption>
					<tr>
						<th>
							功能名称
						</th>
						<td>
							<input name="fp_Name" type="text" style="width: 70%">
						</td>
					</tr>
					<tr>
						<th>
							功能URL
						</th>
						<td>
							<input name="fp_Url" type="text" style="width: 70%">
						</td>
					</tr>
					<tr>
						<th>
							上级功能
						</th>
						<td>
							<select name="fp_parentId" style="width: 70%" id="sjbm">
								<option value="">--请选择功能--</option>
							</select>
						</td>
					</tr>
					<tr>
						<th>
							是否管理员可见
						</th>
						<td>
 							<input type="radio" value="1" name="Is_Xs">是
 							<input type="radio" value="0" name="Is_Xs">否							
 						</td>
					</tr>
					<tr>
						<th>
							字体图标
						</th>
						<td>
							<input name="fp_Icon" type="text" style="width: 70%">
						</td>
					</tr>
					
					<tr>
						<td colspan="2" align="center" style="text-align: center;">
							<a href='#' class="easyui-linkbutton" iconCls="icon-save" onclick="saveDepartment()">保存</a>&nbsp;
							<a href="#" class="easyui-linkbutton" onclick="clearForm();" iconCls="icon-undo">清空</a>&nbsp;
						</td>
					</tr>
				</table>
		</form>
</body>
</html>