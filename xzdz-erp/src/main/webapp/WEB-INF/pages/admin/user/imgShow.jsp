<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>上传头像</title>
<link rel="stylesheet" href="../layui-v2.5.5/layui/css/layui.css">
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@page isELIgnored="false" %>
</head>
<body  style="width:100%;padding:0px; margin:0px;">
	<div style="width:740px;height:auto;padding:0px; margin:0 auto;" id="main" enctype="multipart/form-data">
		<form class="layui-form" action='' method="post" id="myForm" style="margin-top:30px;">
			<input type="hidden" id="url" value='<c:url value="/"/>'>
			<input type="hidden" id="flag" value="${flag}"> 
			 <div class="layui-upload">
                 <label class="layui-form-label">头像:</label>
                 <div class="layui-upload layui-input-block">
                     <input type="hidden" name="user_uImage" id="img" required lay-verify="photo" />
                     <button type="button" class="layui-btn layui-btn-primary" id="fileBtn"><i class="layui-icon">&#xe67c;</i>选择文件</button>
                     <button type="button" class="layui-btn layui-btn-warm" id="uploadBtn">开始上传</button>
                     <br>
                      <br>
                     <img alt="头像"  style="width: 10%" id="photo" src='../photo/${photoName}'>
                 </div>
            </div>
		</form>
 	</div>
<script src="../layui-v2.5.5/layui/layui.js" charset="utf-8"></script>
<script src="../jquery/jquery-3.3.1.js"></script>
<script>
layui.use(['form','upload'], function(){
  var form = layui.form
  ,upload = layui.upload;
  var url=$('#url').val();
  form.render();

  var upload = layui.upload;
  upload.render({
      elem: '#fileBtn'
      ,url: "<c:url value='/user/uploadImgShow.do'/>"
      ,accept: 'file'
      ,auto: false
      ,bindAction: '#uploadBtn'
     ,done: function(res){
         var flag=res.flag;
         if(flag){
        	 window.parent.location.reload();
			 window.close();
           }else{
               layer.alert(res.infor,{icon:7});
           }
      }
  });

});
 
  

  

</script>
</body>
</html>