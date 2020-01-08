<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@page isELIgnored="false" %>
<link rel="stylesheet" href="../jquery-easyui-1.7.0/themes/default/easyui.css"">
<link rel="stylesheet" href="../jquery-easyui-1.7.0/themes/icon.css"">
<script type="text/javascript" src="../jquery-easyui-1.7.0/jquery.min.js"></script>
<script type="text/javascript" src="../jquery-easyui-1.7.0/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../jquery-easyui-1.7.0/locale/easyui-lang-zh_CN.js"></script>
<title>系统后台</title>
<script type="text/javascript">
	var data;
	$.ajax({
		type:"post",
		url:"<c:url value='/adminIndex/privilegeList.do'/>",
		async:false,
		dataType:'json',
		error:function() {
			alert("出错");
		},
		success:function(msg) {
			data=msg
		}
	}); 
	function url(str){
		var url=$('#url').val();
		parent.document.getElementById('iframe-page-content').src=url+str;
	}

	//退出系统
	function exit(){
		$.messager.confirm("提示", "您确定要退出系统么？", function (data) {
			if(data){
				 location.href='<c:url value="/login/exit.do"/>';
				}
		});
	}
</script>
</head>
<body class="easyui-layout">
	<input type="hidden" id="url" value='<c:url value="/"/>'>
    <div data-options="region:'north',title:'导航栏',split:true" style="height:120px;overflow:hidden;" >
    	<span style="color: red;float:right;background-color:#2fbcea;width:100%;">用户名:${user.userName}&nbsp;&nbsp;<a href="#" onclick="exit()">退出</a></span>
    	<img alt="" src="${pageContext.request.contextPath}/admin/images/top_100.jpg" style="background-size: 100%;width:100%"> 
    </div>
    <div data-options="region:'west',title:'菜单栏',split:true" style="width:210px;">
    	 <div id="sm"  class="easyui-sidemenu" data-options="data:data,border:false,onSelect:function(item){$(this).click(url(item.url,$(this)));}">
    	 </div>
    </div>
    <div data-options="region:'center',title:'内容主体'" style="padding:5px;background:#eee;">
    	   <iframe  src='http://www.tlxzkj.com' name="iframe_a" id="iframe-page-content"  width="100%" height="100%" frameborder="no" border="0" marginwidth="0" marginheight=" 0" scrolling="yes" allowtransparency="yes"></iframe>
    </div>
</body>
</html>