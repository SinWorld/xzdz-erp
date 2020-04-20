<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新增用户</title>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@page isELIgnored="false" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/jquery-easyui-1.7.0/themes/default/easyui.css"">
<link rel="stylesheet" href="${pageContext.request.contextPath}/jquery-easyui-1.7.0/themes/icon.css"">
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery-easyui-1.7.0/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery-easyui-1.7.0/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery-easyui-1.7.0/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">

	function SubmitForm(){
		$('#myForm').form('submit');
		window.opener.location.reload();
		window.close();
	}

	function clearForm(){
		$('#myForm').form('clear');
	}


	//新增用户 登录名重复检查
	$(function(){		
	    $("input",$("#loginName").next("span")).blur(function(){
	    	//获得登录名
			var loginName=$('#loginName').val();
			$.ajax({
				type : "post",
				url : "<c:url value='/user/checkLoginName.do'/>",
				async : false,
				dataType : 'json',
				data:{"loginName":loginName},
				error : function() {
					alert("出错");
				},
				success : function(data) {
					if(data.flag==false){
						$('#loginName').textbox('setValue','');
						return  $.messager.alert('提示','当前登录名已存在,不可用');
					}
				}
			});
	    });
	});

</script>
</head>
<body>
	<div class="easyui-panel" title="New Topic" style="width:100%;">
	  <div class ="easyui-panel" title ="新增用户" style ="width:100%;">
		<div style="padding:10px 60px 20px 90px">
		 <form action='<c:url value="/user/saveUser.do"/>' id="myForm" method="post">
				<input type="hidden" value="${flag}" id="flag">
				<input type="hidden" value='<c:url value="/"/>' id="url">
				<table cellpadding ="4">
		    		<tr>
		    			<td>
		    				登录名
		    			</td>
		    			<td style="width: 280px;">
		    				<input class="easyui-textbox" type="text" name="loginName" data-options="required:true" style="width:200px;" id="loginName"/>
		    			</td>
		    			<td>
		    				密码
		    			</td>
		    			<td>
		    				<input class="easyui-textbox" type="password" name="password" data-options="required:true" style="width: 200px;"/>
		    			</td>
		    		</tr>
		    		
		    		<tr>
		    			<td>
		    				姓名
		    			</td>
		    			<td>
		    				<input class="easyui-textbox" type="text" name="userName" data-options="required:true" style="width: 200px;"/>
		    			</td>
		    			
		    			<td>
							出生年月
						</td>
						<td>
							<input class="easyui-datebox" name="birthday" labelPosition="top" style="width: 200px;" data-options="required:true"/>						
						</td>
		    		</tr>
		    		
		    		
		    		<tr>
		    			<td>
		    				性别
		    			</td>
		    			<td>
 							<input type="radio" value="男" name="gender">男
 							<input type="radio" value="女" name="gender">女							
 						</td>
		    			<td>
							部门
						</td>
						<td>
		    				<select class="easyui-combobox" name="dep_Id" id="sjbm" style="width: 200px;" data-options="required:true"   url='<c:url value="/user/depList.do"/>' valueField="dep_Id" textField="dep_Name">
		    					
		    				</select>
						</td>
		    		</tr>
		    		
		    		
		    		<tr>
		    			<td>
		    				岗位
		    			</td>
		    			<td>
		    				<select class="easyui-combobox" name="post_Id" id="post_Id" style="width: 200px;" data-options="required:true"  url='<c:url value="/user/postList.do"/>'  valueField="post_Id" textField="post_Name">
		    					
		    				</select>
		    			</td>
		    			
		    			<td>
							民族
						</td>
						<td>
		    				<input class="easyui-textbox" type="text" name="nation" data-options="required:false" style="width: 200px;"/>
						</td>
		    		</tr>
		    		
		    		<tr>
		    			<td>
		    				职务
		    			</td>
		    			<td>
		    				<input class="easyui-textbox" type="text" name="duties" data-options="required:false" style="width: 200px;"/>
		    			</td>
		    			
		    			<td>
							手机号
						</td>
						<td>
		    				<input class="easyui-textbox" type="text" name="phoneNumber" data-options="required:true" style="width: 200px;"/>
						</td>
		    		</tr>
		    		
		    		<tr>
		    			<td>
		    				办公电话
		    			</td>
		    			<td>
		    				<input class="easyui-textbox" type="text" name="telPhone" data-options="required:false" style="width: 200px;"/>
		    			</td>
		    			
		    			<td>
							邮箱
						</td>
						<td>
		    				<input class="easyui-textbox" type="text" name="email" data-options="required:true" style="width: 200px;"/>
						</td>
		    		</tr>
		    		
		    		<tr>
		    			<td>
		    				QQ号
		    			</td>
		    			<td>
		    				<input class="easyui-textbox" type="text" name="QQNumber" data-options="required:false" style="width: 200px;"/>
		    			</td>
		    			
		    			<td>
							微信
						</td>
						<td>
		    				<input class="easyui-textbox" type="text" name="weChat" data-options="required:false" style="width: 200px;"/>
						</td>
		    		</tr>
		    		<tr>
		    			<td>
		    				学历
		    			</td>
		    			<td>
		    				<input class="easyui-textbox" type="text" name="education" data-options="required:false" style="width: 200px;"/>
		    			</td>
		    			
		    			<td>
							备注
						</td>
						<td>
		    				<input class="easyui-textbox" name="remarks" data-options="multiline:true" style="height:60px;width:200px;"/>
						</td>
		    		</tr>
		    	</table>
			</form>
			 <div style ="text-align:center;padding:5px;">
		    	<a href="#" class="easyui-linkbutton" onclick="SubmitForm()">提交</a>
		    	<a href="#" class="easyui-linkbutton" onclick="clearForm()">清除</a>
	    	</div>
		</div>
	</div>
  </div>
</body>
</html>