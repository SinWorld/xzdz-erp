<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新增部门</title>
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
			url : "<c:url value='/department/orgDepartment.do'/>",
			async : false,
			dataType : 'json',
			error : function() {
				alert("出错");
			},
			success : function(msg) {
				for (var i = 0; i < msg.length; i++) {
					for(var j=0;j<msg[i].length;j++){
						$("#sjbm").append(
						    "<option value='"+msg[i][j].dep_Id+"'>"+ msg[i][j].dep_Name +"</option>"); 
					}
				}
			}
		});
	}

	//回显上级部门
  	function pageReloadsjbm(){
	  //获得上级部门下拉选
	  var sjbms=$('#sjbm').find('option');
	  //获得已选部门下拉选主键
	  var bmdm=$('#sjbmId').val();
	  for(var i=0;i<sjbms.length;i++){
		  if(sjbms[i].value==bmdm){
			  sjbms[i].setAttribute("selected",'true');
			  break;
		  }
	  }
  	}

	function windowClose(){
		initOrgTree();
		pageReloadsjbm();
		var flag=$('#flag').val();
		if("true" == flag){
			window.parent.opener.location.reload();
			window.close();
		}
	}

	function saveDepartment(){
		var url=$('#url').val();
		var form=document.getElementById('myForm');
		form.action=url+"department/editDepartment.do";
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
						部门信息
					</caption>
					<tr>
						<th>
							部门名称
						</th>
						<td>
							<input type="hidden" value="${department.dep_parentId}" id="sjbmId">
							<input type="hidden" value="${department.dep_Id}" name="dep_Id">
							<input name="dep_Name" type="text" style="width: 70%" value="${department.dep_Name}">
						</td>
					</tr>
					<tr>
						<th>
							上级部门
						</th>
						<td>
							<select name="dep_parentId" style="width: 70%" id="sjbm">
								<option value="">--请选择部门--</option>
							</select>
						</td>
					</tr>
					<tr>
						<th>
							部门描述
						</th>
						<td>
							<textarea rows="5" cols="5" style="width: 70%" name="remarks">${department.remarks}</textarea>
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