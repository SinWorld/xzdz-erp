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
<title>剩余成品入库</title>
<link rel="stylesheet" href="../layui-v2.5.5/layui/css/layui.css">
<script src="../jquery/jquery-3.3.1.js"></script>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@page isELIgnored="false" %>
<style>
  .bj{background-color: #F0F0F0}
 </style>
</head>
<body style="width:100%;padding:0px; margin:0px;text-align: center;" onload="refreshAndClose()">
	<div style="width:1280px;height:auto;padding:0px; margin:0 auto;" id="main">
		<form class="layui-form" action='<c:url value="/proStock/sycprk.do"/>' method="post">
		<input type="hidden" id="url" value='<c:url value="/"/>'>
		<input type="hidden" id="flag" value="${flag}">
		<input type="hidden" value="${product.product_Id }" name="product" id="product_Id">
		<input type="hidden" value="${product_Stock.stock_Id}" name="stock_Id">
		
		 	<div class="layui-form-item" style="margin-top: 50px;">
				<label class="layui-form-label" style="width: 120px;">产品名称</label>
				 <div class="layui-input-inline">
					 <input type="text"  lay-verify="cpmc" autocomplete="off" class="layui-input bj" style="width: 800px;" value="${product.product_Name}" disabled="">
				</div>
			</div>
			
			<div class="layui-form-item">
			
					<div class="layui-inline" style="top:9px;left:-79px;">
					      <label class="layui-form-label" style="width: 90px;">库位</label>
					      <div class="layui-input-inline">
					        <input type="text" name="stock" lay-verify="stock" autocomplete="off" class="layui-input bj" style="width: 200px;" value="${product_Stock.stock}" disabled="">
					      </div>
				    </div>
			
					  <div class="layui-inline" style="top:9px;left: -130px;">
					      <label class="layui-form-label" style="width:90px;">生产数量</label>
					      <div class="layui-input-inline" style="width: 100px;">
					        <input type="text" autocomplete="off" class="layui-input bj" style="width: 100px;" id="scsl" disabled="" value="${product.numbers}">
					      </div>
				     </div>
				     
				     <div class="layui-inline" style="top:9px;left: -160px;">
					      <label class="layui-form-label" style="width:90px;">已入库数量</label>
					      <div class="layui-input-inline" style="width: 100px;">
					        <input type="text"  autocomplete="off" class="layui-input bj" style="width: 100px;" disabled="" id="yrksl">
					      </div>
				     </div>
			     
				     <div class="layui-inline" style="top:9px;left: -200px;">
					      <label class="layui-form-label" style="width:90px;">入库数量</label>
					      <div class="layui-input-inline" style="width: 100px;">
					        <input type="text" name="rknumber" id="rknumber" lay-verify="number" autocomplete="off" class="layui-input" style="width: 100px;" onchange="rkslxz()">
					      </div>
				     </div>
			 </div>
			
			 <div class="layui-form-item layui-form-text">
			    <label class="layui-form-label" style="width:120px;">备注</label>
			    <div class="layui-input-block">
			      <textarea placeholder="请输入内容" name="remarks"  lay-verify="remarks" id="remarks" class="layui-textarea" style="width:68.5%">${product_Stock.remarks}</textarea>
			    </div>
			 </div>
	
		<div class="layui-form-item" style="text-align: center;">
		    <div class="layui-input-block">
		      <button class="layui-btn" lay-submit="" lay-filter="demo1" style="width:25%;margin-top:10px;margin-left:-315px;">立即提交</button>
		    </div>
		</div>
	</form>
 </div>
<script src="../layui-v2.5.5/layui/layui.js" charset="utf-8"></script>
<script>
layui.use(['form', 'layedit', 'laydate','upload'], function(){
  var form = layui.form
  ,layer = layui.layer
  ,layedit = layui.layedit
  ,laydate = layui.laydate
  ,upload = layui.upload;
  var url=$('#url').val();
  allWrkProduct(form);
  productNumber();
  form.render();
  //日期
  laydate.render({
    elem: '#cont_Date'
  });
  
 
  //创建一个编辑器
  var editIndex = layedit.build('LAY_demo_editor');

//监听提交
  form.on('submit(demo1)', function(data){
    layer.alert(JSON.stringify(data.field), {
      title: '最终的提交信息'
    })
    return true;
  });

});

	//加载所有未完全入库的成品
	function allWrkProduct(form){
		$.ajax({
			type : "post",
			url : "<c:url value='/proStock/allWrkProduct.do'/>",
			async : false,
			dataType : 'json',
			error : function() {
				alert("出错");
			},
			success : function(msg) {
				for(var i=0;i<msg.length;i++){
						$("#product").append(
					    	"<option value='"+msg[i].product_Id+"'>"+ msg[i].product_Name +"</option>"); 
				}
			}
		});
		 form.render('select');
	}

	function refreshAndClose(){
		var flag=$('#flag').val();
		if(flag){
			window.parent.location.reload();
			window.close();
		} 
	}

	//入库数量限制
	function rkslxz(){
		var scsl=$('#scsl').val();//生产数量
		var yrksl=$('#yrksl').val();//已入库数量
		var result=(scsl*1)-(yrksl*1)
		//获得填写的入库数量
		var rksl=$('#rknumber').val();
		if(rksl>result){
			$('#rknumber').val("");
			return layer.alert("入库数量不得大于产品剩余入库数量",{icon:7});
		}
	}

	function productNumber(){
		var flag=$('#flag').val();
		var product_Id=$('#product_Id').val();
		if(flag==false){
			//ajax根据Id查询产品并设置其它属性值
			$.ajax({
				type : "post",
				url : "<c:url value='/proStock/queryProductById.do'/>",
				async : false,
				dataType : 'json',
				data :{"product_Id":product_Id},
				error : function() {
					alert("出错");
				},
				success : function(msg) {
					//设置已入库数量
					$('#yrksl').val(msg.yrksl);
				}
			});
		}
	}	

	
</script>
</body>
</html>