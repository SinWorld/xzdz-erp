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
<title>成品入库</title>
<link rel="stylesheet" href="../layui-v2.5.5/layui/css/layui.css">
<script src="../jquery/jquery-3.3.1.js"></script>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@page isELIgnored="false" %>
</head>
<body  style="width:100%;padding:0px; margin:0px;">
	<form class="layui-form">
		<div style="width:1280px;height:auto;padding:0px; margin:0 auto;" id="main">
			<input type="hidden" id="url" value='<c:url value="/"/>'>
			<input type="hidden" id="dms" name="hwdms">
			 
			  <div class="layui-form-item" style="text-align: center;margin-top: 30px;">
			 	<div id="test4" class="demo-transfer"></div>
			  </div>
	 </div>
 </form>
<script src="../layui-v2.5.5/layui/layui.js" charset="utf-8"></script>
<script>
layui.use(['form', 'layedit', 'laydate','transfer','util'], function(){
  var form = layui.form
  ,layer = layui.layer
  ,layedit = layui.layedit
  var url=$('#url').val();
  var transfer = layui.transfer;
  var util = layui.util;
  allRkProduct();
  form.render();
  //创建一个编辑器
  var editIndex = layedit.build('LAY_demo_editor');
  
	//ajax实现下拉展现所有的成品
	 function  allRkProduct(){
				$.ajax({
					type : "post",
					url : "<c:url value='/proStock/allWrkProduct.do'/>",
					async : false,
					dataType : 'json',
					error : function() {
						alert("出错");
					},
					success : function(msg) {
						//显示搜索框
						  transfer.render({
						    elem: '#test4'
						    ,data: msg
						    ,title: ['成品', '入库']
						    ,showSearch: true
						    ,onchange: function(data, index){
						    	//左边 索引为0 右边为1
						    	if(index==0){
						    		//遍历结果集
						    		for(var i=0;i<data.length;i++){
						    			//获得穿梭的数据代码
							    		var product_Id=data[i].value;
							    		var dms=$('#dms').val();
						    			//拼接id字符串
							    		if(undefined!=dms){
											 dms=dms+","+product_Id;
											 $('#dms').val(dms);
										 }else{
											 dms=product_Id;
											 $('#dms').val(dms);
										 }
						    		}
						    		//弹出页面查看成品信息
						    		var d=$('#dms').val();
						    		parent.layer.open({
						          	  	type:2,
						          	  	title:'入库内容',
						          	  	area: ['100%','100%'],
						          	  	shadeClose: true,
						          		resize:false,
						          	    anim: 1,
						          	  	content:[url+"proStock/rkProductShow.do?hwdms="+d,'yes']
						        	});
						    	}else{
						    		for(var i=0;i<data.length;i++){
						    			var dm=$('#dms').val();
						    			//获得穿梭的数据代码
							    		var hwdm=data[i].value;
						    			//拼接id字符串
							    		var str=dm.replace(","+hwdm,"");
							    		$('#dms').val(str);
						    		}
						    	}
						      } 
						  })
					}
				});
	}
});




</script>
</body>
</html>