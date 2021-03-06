<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>编辑岗位</title>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@page isELIgnored="false" %>
<link rel="stylesheet" href="../jquery-easyui-1.7.0/themes/default/easyui.css"">
<link rel="stylesheet" href="../jquery-easyui-1.7.0/themes/icon.css"">
<script type="text/javascript" src="../jquery-easyui-1.7.0/jquery.min.js" charset="utf-8"></script>
<script type="text/javascript" src="../jquery-easyui-1.7.0/jquery.easyui.min.js" charset="utf-8"></script>
<script type="text/javascript" src="../jquery-easyui-1.7.0/locale/easyui-lang-zh_CN.js" charset="utf-8"></script>
<script type="text/javascript">

	function windowClose() {
		pageReloadsxbm();
	}

	function SubmitForm(){
		$('#myForm').form('submit');
		window.opener.location.reload();
		window.close();
	}

	function clearForm(){
		$('#myForm').form('clear');
	}

	//回显所属部门
  	function pageReloadsxbm(){
	  //获得已选部门下拉选主键
	  var bmdm=$('#sxbmId').val();
	  $("#sxbm").combobox("select",bmdm);
  	}

</script>
</head>
<body onload="windowClose()">
	<div class="easyui-panel" title="New Topic" style="width:100%;">
	  <div class ="easyui-panel" title ="编辑岗位" style ="width:100%;">
		<div style="padding:10px 60px 20px 140px">
		 <form action='<c:url value="/post/editPost.do"/>' id="myForm" method="post">
			<input type="hidden" value="${flag}" id="flag">
			<input type="hidden" value='<c:url value="/"/>' id="url">
				<table cellpadding ="2">
		    		<tr>
		    			<td>
		    				岗位名称
		    			</td>
		    			<td style="width: 280px;">
		    				<input type="hidden" value="${post.post_Id}" name="post_Id">
		    				<input class="easyui-textbox" type="text" name="post_Name" data-options="required:true" style="width:200px;" id="post_Name" value="${post.post_Name}"/>
		    			</td>
		    			
		    		</tr>
		    		
		    		<tr>
		    			<td>
							所属部门
						</td>
						<td>
							<input type="hidden" value="${post.post_Department}" id="sxbmId">
		    				<select class="easyui-combobox" name="post_Department" id="sxbm" style="width: 200px;" data-options="required:true"   url='<c:url value="/user/depList.do"/>' valueField="dep_Id" textField="dep_Name">
		    					
		    				</select>
						</td>
		    		</tr>
		    		
		    		<tr>
		    			<td>
		    				岗位描述
		    			</td>
		    			<td>
		    				<input class="easyui-textbox" name="post_Code" data-options="multiline:true" style="height:60px;width:200px;" value="${post.post_Code}"/>
						</td>
		    		</tr>
		    	</table>
			</form>
			 <div style ="text-align:center;padding:5px;margin-left: -40px;">
		    	<a href="#" class="easyui-linkbutton" onclick="SubmitForm()">提交</a>
		    	<a href="#" class="easyui-linkbutton" onclick="clearForm()">清除</a>
	    	</div>
		</div>
	</div>
  </div>
</body>
</html>