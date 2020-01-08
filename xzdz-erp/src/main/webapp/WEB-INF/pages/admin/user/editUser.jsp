<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>编辑用户</title>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@page isELIgnored="false" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/jquery-easyui-1.7.0/themes/default/easyui.css"">
<link rel="stylesheet" href="${pageContext.request.contextPath}/jquery-easyui-1.7.0/themes/icon.css"">
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery-easyui-1.7.0/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery-easyui-1.7.0/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery-easyui-1.7.0/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">
	function saveUser(){
		var url=$('#url').val();
		var form=document.getElementById('myForm');
		form.action=url+"user/editUser.do";
		form.submit();
	}

	function windowClose(){
		initOrgTree();
		initPost();
		pageReloadPost();
		pageReloadsjbm();
		var flag=$('#flag').val();
		if("true" == flag){
			window.parent.opener.location.reload();
			window.close();
		}
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

	//新增岗位
	function initPost(){
		$.ajax({
			type : "post",
			url : "<c:url value='/user/postList.do'/>",
			async : false,
			dataType : 'json',
			error : function() {
				alert("出错");
			},
			success : function(msg) {
				for(var i=0;i<msg.length;i++){
					$("#post_Id").append(
					    "<option value='"+msg[i].post_Id+"'>"+ msg[i].post_Name +"</option>"); 
				}
			}
		});
	}

	//回显所属岗位
  	function pageReloadPost(){
	  //获得所属岗位下拉选
	  var sxgws=$('#post_Id').find('option');
	  //获得已选岗位下拉选主键
	  var gwdm=$('#gwdm').val();
	  for(var i=0;i<sxgws.length;i++){
		  if(sxgws[i].value==gwdm){
			  sxgws[i].setAttribute("selected",'true');
			  break;
		  }
	  }
  	}
	
</script>
</head>
<body onload="windowClose()">
	<form action='' id="myForm" method="post">
			<input type="hidden" value="${flag}" id="flag">
			<input type="hidden" value='<c:url value="/"/>' id="url">
			<input type="hidden" value="${user.userId}" name="userId">
			<div class="submitdata">
				<table width="100%">
					<caption>
						用户信息
					</caption>
					<tr>
						<th>
							登录名
						</th>
						<td>
							<input name="loginName" type="text" style="width: 70%" value="${user.loginName}">
						</td>
					</tr>
					<tr>
						<th>
							密码
						</th>
						<td>
							<input name="password" type="password" style="width: 70%" value="${user.password}">
						</td>
					</tr>
					<tr>
						<th>
							姓名
						</th>
						<td>
							<input name="userName" type="text" style="width: 70%" value="${user.userName}">
						</td>
					</tr>
					<tr>
						<th>
							出生年月
						</th>
						<td>
 							<input class="easyui-datebox" id="birthday" name="birthday"  value="${user_brithday}" labelPosition="top" style="width:70%;">						
 						</td>
					</tr>
					<tr>
						<th>
							性别
						</th>
						<td>
							<input type="radio" value="男" name="gender" <c:if test="${user.gender eq '男'}">checked</c:if>>男
 							<input type="radio" value="女" name="gender" <c:if test="${user.gender eq '女'}">checked</c:if>>女			
 						</td>
					</tr>
					<tr>
						<th>
							部门
						</th>
						<td>
							<input type="hidden" value="${user.dep_Id}" id="sjbmId">
							<select name="dep_Id" style="width: 70%" id="sjbm">
								<option value="">--请选择部门--</option>
							</select>
						</td>
					</tr>
					<tr>
						<th>
							岗位
						</th>
						<td>
							<input type="hidden" value="${user.post_Id}" id="gwdm">
							<select name="post_Id" style="width: 70%" id="post_Id">
								<option value="">--请选择岗位--</option>
							</select>
						</td>
					</tr>
					<tr>
						<th>
							民族
						</th>
						<td>
							<input name="nation" type="text" style="width: 70%" value="${user.nation}">
						</td>
					</tr>
					<tr>
						<th>
							职务
						</th>
						<td>
							<input name="duties" type="text" style="width: 70%" value="${user.duties }">
						</td>
					</tr>
					<tr>
						<th>
							手机号
						</th>
						<td>
							<input name="phoneNumber" type="text" style="width: 70%" value="${user.phoneNumber}">
						</td>
					</tr>
					<tr>
						<th>
							办公电话
						</th>
						<td>
							<input name="telPhone" type="text" style="width: 70%" value="${user.telPhone}">
						</td>
					</tr>
					<tr>
						<th>
							邮箱
						</th>
						<td>
							<input name="email" type="text" style="width: 70%" value="${user.email}">
						</td>
					</tr>
					<tr>
						<th>
							QQ号
						</th>
						<td>
							<input name="QQNumber" type="text" style="width: 70%" value="${user.QQNumber}">
						</td>
					</tr>
					<tr>
						<th>
							微信
						</th>
						<td>
							<input name="weChat" type="text" style="width: 70%" value="${user.weChat}">
						</td>
					</tr>
					<tr>
						<th>
							学历
						</th>
						<td>
							<input type="text" style="width: 70%" name="education" value="${user.education}">
						</td>
					</tr>
					<tr>
						<th>
							备注
						</th>
						<td>
							<textarea rows="5" cols="5" name="remarks" style="width: 70%">${user.remarks}</textarea>
						</td>
					</tr>
					<tr>
						<td colspan="2" align="center" style="text-align: center;">
							<a href='#' class="easyui-linkbutton" iconCls="icon-save" onclick="saveUser()">保存</a>&nbsp;
							<a href="#" class="easyui-linkbutton" onclick="clearForm();" iconCls="icon-undo">清空</a>&nbsp;
						</td>
					</tr>
				</table>
		</form>
</body>
</html>