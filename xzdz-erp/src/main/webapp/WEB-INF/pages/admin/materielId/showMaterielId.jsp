<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8">
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">
<title>查看物料Id</title>
<link rel="stylesheet" href="../layui-v2.5.5/layui/css/layui.css">
<link rel="stylesheet" href="../bootstrap-3.3.7-dist/css/bootstrap.min.css"> 
<link rel="stylesheet" href="../bootstrap-3.3.7-dist/css/bootstrap-theme.min.css"> 
<script src="../jquery/jquery-3.3.1.js"></script>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@page isELIgnored="false" %>
<style>
  .bj{background-color: #F0F0F0}
 </style>
</head>
<body style="width:100%;padding:0px; margin:0px;">
	<div style="width:1100px;height:auto;padding:0px; margin:0 auto;" id="main">
		<form class="layui-form" action='' method="post" id="downForm" enctype="multipart/form-data">
			<input type="hidden" id="url" value='<c:url value="/"/>'>
			<input type="hidden" id="flag" value="${flag}">
			<input type="hidden" value="${materielId.type}" id="lx">
			<input type="hidden" name="row_Id" value="${materielId.row_Id}">
			<input type="hidden" id="OBJDM" value="${OBJDM}">
			<input type="hidden" id="ftpPath">
			<input type="hidden" id="rEALWJM">
			<div class="layui-form-item" style="margin-top:5%;">
			     <div class="layui-inline" style="top:9px;">
				      <label class="layui-form-label" style="width: 90px;">物料Id</label>
				      <div class="layui-input-inline">
				        <input type="text" name="materiel_Id" lay-verify="materiel_Id" autocomplete="off" class="layui-input bj" id="materiel_Id" value="${materielId.materiel_Id}" disabled="">
				      </div>
			     </div>
			     
			    <div class="layui-inline" style="top:9px;">
				      <label class="layui-form-label" style="width: 90px;">规格型号</label>
				      <div class="layui-input-inline">
				        <input type="text" name="specification_Type" lay-verify="specification_Type" autocomplete="off" class="layui-input bj" id="specification_Type" disabled="" value="${materielId.specification_Type}">
				      </div>
			    </div>
			    
			     <div class="layui-inline" style="top:9px;">
				      <label class="layui-form-label" style="width:150px;">保质期</label>
				      <div class="layui-input-inline">
				        <input type="text" name="bzq" lay-verify="bzq" autocomplete="off" class="layui-input bj" id="bzq" value="${materielId.bzq}" disabled="">
				      </div>
			     </div>
			 </div>
			
			<div class="layui-form-item">
			     <div class="layui-inline" style="left: -16px;">
				      <label class="layui-form-label" style="width:105px;">参考单价</label>
				      <div class="layui-input-inline">
				        <input type="text" name="ckdj" lay-verify="ckdj" autocomplete="off" class="layui-input bj" id="ckdj" value="${materielId.ckdj}" disabled="">
				      </div>
			     </div>
			     
			  	 <div class="layui-inline" style="left: -30px;">
				      <label class="layui-form-label" style="width:105px;">类型</label>
				      <div class="layui-input-inline">
				        <input type="text" name="ckdj" lay-verify="ckdj" autocomplete="off" class="layui-input bj" id="ckdj" value="${materielId.typeName}" disabled="">
				      </div>
			     </div>
		   </div>
		   
			
		 <div class="layui-form-item layui-form-text">
		    <label class="layui-form-label" style="width:100px;">物料描述</label>
		    <div class="layui-input-block">
		      <textarea placeholder="请输入内容" name="remarks"  lay-verify="remarks" id="remarks" class="layui-textarea bj" style="width:86.6%;left: -20px;" disabled="">${materielId.remarks}</textarea>
		    </div>
		 </div>
		 
		  <div class="layui-form-item">
		        <label class="layui-form-label" style="width:100px;">附件</label>
		        <div class="layui-input-inline" style="width: 77%;">
					<table class="layui-hide" id="test" lay-filter="test"></table>
				</div>
		 </div>
		
	</form>
 </div>
<script type="text/html" id="barDemo">
  <!--<a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="yl" name="defaultAD">预览</a>-->
  <a class="layui-btn layui-btn-xs" lay-event="xz">下载</a>
</script>
<script src="../bootstrap-3.3.7-dist/js/bootstrap.js"></script>
<script src="../layui-v2.5.5/layui/layui.js" charset="utf-8"></script>
<script>
layui.use(['form', 'layedit', 'laydate','upload','table'], function(){
  var form = layui.form
  ,layer = layui.layer
  ,layedit = layui.layedit
  ,laydate = layui.laydate
  ,upload = layui.upload;
  var url=$('#url').val();
  var table = layui.table;
  var OBJId=$('#OBJDM').val();
  form.render();
 
  //创建一个编辑器
  var editIndex = layedit.build('LAY_demo_editor');

  table.render({
	    elem: '#test'
	    ,url:url+'enclosure/enclosureList.do?OBJDM='+OBJId
	    ,title: '任务附件'
	    ,cols: [[
	       {field:'index', width:"10%", title: '序号', sort: true,type:'numbers'}
	      ,{field:'REALWJM', width:"80%",align:'left', title: '文件名称'}
	      ,{fixed: 'right', title:'操作', toolbar: '#barDemo', width:"10%",align:'center'}
	    ]]
	  });

  //监听行工具事件
  table.on('tool(test)', function(obj){
    var data = obj.data;
    //存储在ftp服务器端的地址
    var ftpPath=data.SHANGCHUANDZ;
    //存储在ftp的真实文件名
    var rEALWJM=data.REALWJM;
    var url=$('#url').val();
    $('#ftpPath').val(ftpPath);
    $('#rEALWJM').val(rEALWJM);
	var form = document.getElementById('downForm');
    //console.log(obj)
    if(obj.event === 'xz'){
    	//下载文件
    	form.action=url+"sales/downloadFtpFile.do?ftpPath="+ftpPath+"&"+"rEALWJM="+rEALWJM;
    	form.submit();
    }
  });
  
});
</script>
</body>
</html>