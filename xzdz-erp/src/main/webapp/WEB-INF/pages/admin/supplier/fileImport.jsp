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
layui.use(['form', 'layedit', 'laydate'], function(){
	  var form = layui.form
	  ,layer = layui.layer
	  ,layedit = layui.layedit
	  ,laydate = layui.laydate
	  ,upload = layui.upload;
	  var url=$('#url').val();
	  form.render();
	});

	//导入Excel
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
			alert("导入文件格式不正确，只允许.XLS格式");
			return false;
		}
		var v_url=$('#url').val() +"supplier/importExcel.do";
		//上传文件
		var fileObj = document.getElementById("file").files[0];
		var formFile = new FormData();
		formFile.append("file", fileObj);
		var data = formFile;
		$.ajax({
			url: v_url,
			data: data,
			type: "Post",
			dataType: "json",
			cache: false,//上传文件无需缓存
			processData: false,//用于对data参数进行序列化处理 这里必须false
			contentType: false, //必须
			success: function (result) {
				if(result.result=="0"){
					if(result.userId!=-1){
						var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
			            parent.location.reload();//刷新父页面，注意一定要在关闭当前iframe层之前执行刷新
			            parent.layer.close(index); //再执行关闭
					}else{
						window.parent.opener.location.reload();
						window.close();
					}
				}else{
					layer.alert("excel中第"+result.result+"行数据有异常，请处理后在导入!",{icon:7});
				}
			},
			error:function(msg){
				layer.alert("文件上传失败!",{icon:2});
			}
		});
		
	}
</script>
</head>
<body>
	<form action='' method="post" enctype="multipart/form-data" id="myForm">
		<div style="margin-top: 10%">
			<label class="layui-form-label" style="width:33%;padding-top: 0%">请选择文件(.XLS)</label>
			<div>
				<input type="file" name="file" class="InputStyle" style="width:357px;" id="file" multiple="multiple"/> 
				<input type="hidden" value='<c:url value="/"/>' id="url">
			</div>
		</div>
		 <div class="layui-input-block">
	      <button class="layui-btn"  lay-submit="" type="button" lay-filter="" style="width:35%;margin-top:5%;" onclick="fileImport()">立即提交</button>
	    </div>
    </form>
</body>
</html>