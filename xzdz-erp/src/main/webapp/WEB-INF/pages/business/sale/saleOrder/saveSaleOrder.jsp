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
<title>新增销售订单</title>
<link href="../login/css/xshtfp.css" rel="stylesheet"/>
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
<body style="width:100%;padding:0px; margin:0px;" onload="refreshAndClose()">
	<div class="m-xm_message_box">
		<div>
			<strong id="_field_xiaoShouHTMC" class="u-header_link">${contract.sales_Contract_Name}</strong>
			<div class="oim-field u-header" style=" margin:10px 0 0 0px;">
	             <label for="" class="oim-field_label u-f13">合同编号</label>
	             <span class="oim-field_text-show u-f13" id="_field_xiangMuXXBH">
	             	${contract.contract_Code}
	             </span>
	         	 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		             <label for="" class="oim-field_label u-f13">合同金额</label>
		              <span class="u-num" id="_heTongJE" style="color:#666;">${contract.htje}</span>
		              <span class="u-rmb">元</span>
		             &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		             <label for="" class="oim-field_label u-f13">累计开票金额</label>
		             <span id="_leiJiKPJE">${ljkpje}</span>
		                <span class="u-rmb">元</span>
						 <div class="layui-progress" lay-showpercent="true" style="width: 6%; display: inline-block;">
						  <div class="layui-progress-bar" lay-percent="${ljkpjebl}"></div>
						 </div>
					 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<label for="" class="oim-field_label u-f13">剩余开票金额</label>
		             <span id="_shengYuKPJE">${sykpje}</span>
		             <span class="u-rmb">元</span>
		              &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<label for="" class="oim-field_label u-f13">累计收款金额</label>
		             <span id="_shengYuKPJE">${ljskje}</span>
		             <span class="u-rmb">元</span>
	             	 <div class="layui-progress" lay-showpercent="true" style="width: 6%; display: inline-block;">
					  <div class="layui-progress-bar" lay-percent="${ljskjebl}"></div>
					 </div>
				     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<label for="" class="oim-field_label u-f13">剩余收款金额</label>
		             <span id="_shengYuKPJE">${syskje}</span>
		             <span class="u-rmb">元</span>
	          </div>	
		</div>
	</div>
	
	<div style="width:1280px;height:auto;padding:0px; margin:0 auto;" id="main">
		<form class="layui-form" action='<c:url value="/saleOrder/submitForm.do"/>' method="post">
			<input type="hidden" id="url" value='<c:url value="/"/>'>
			<input type="hidden" id="flag" value="${flag}">
			<input type="hidden" id="xsddId" name="xsddId" value="${xsddId}">
				
		  <div class="layui-form-item layui-form-text" style="margin-top: 5%;">
	  		<label class="layui-form-label" style="width:190px;">销售订单</label>
			  <div class="layui-input-block" style="top:15px;left: 10px;">
				<table class="table table-bordered" id="khlxrs" style="width: 100%">
				  <thead>
				    <tr>
				      <th scope="col" style="text-align: center;width: 5%">序号</th>
				      <th scope="col" style="text-align: center;width: 15%">物资名称</th>
				      <th scope="col" style="text-align: center;width: 15%">规格型号</th>
				      <th scope="col" style="text-align: center;width: 10%">数量</th>
				      <th scope="col" style="text-align: center;width: 10%">单位</th>
				      <th scope="col" style="text-align: center;width: 10%">单价(元)</th>
				      <th scope="col" style="text-align: center;width: 10%">金额(元)</th>
				      <th scope="col" style="text-align: center;width: 15%">备注</th>
				    </tr>
				  </thead>
				  <tbody>
				  	<c:forEach items="${orderList}" var="o">
					  <tr>
					  	<th scope='row' style='text-align: center;line-height:38px;'></th>
						<td>
							<input type="hidden" value="${o.sales_Contract_Id}" name="contract_Id">
							<input type='text' class='form-control bj' aria-label='' aria-describedby=''  name='material_Name' value="${o.material_Name}" disabled="disabled">
						</td>
						<td><input type='text' class='form-control bj' aria-label='' aria-describedby=''  name='specification_Type' value="${o.specification_Type}" disabled="disabled"></td>
						<td><input type='text' class='form-control bj' aria-label='' aria-describedby=''  name='sl' value="${o.sl}" disabled="disabled"></td>
						<td><input type='text' class='form-control bj' aria-label='' aria-describedby=''  name='unit' value="${o.unit}" disabled="disabled"></td>
						<td><input type='text' class='form-control bj' aria-label='' aria-describedby=''  name='price' value="${o.price}" disabled="disabled"></td>
						<td><input type='text' class='form-control bj' aria-label='' aria-describedby=''  name='total_price' disabled="disabled" value="${o.total_price}"></td>
						<td><input type='text' class='form-control bj' aria-label='' aria-describedby=''  name='bz' value="${o.bz}" disabled="disabled"></td>
					 </tr>
					</c:forEach>
				  </tbody>
				</table>
			</div>
		</div>
	
		<div class="layui-form-item" style="text-align: center;">
		    <div class="layui-input-block">
		      <button class="layui-btn" lay-submit="" lay-filter="demo1" style="width:25%;margin-top:10px;margin-left:-315px;">立即提交</button>
		    </div>
		</div>
	</form>
 </div>
<script src="../bootstrap-3.3.7-dist/js/bootstrap.js"></script>
<script src="../layui-v2.5.5/layui/layui.js" charset="utf-8"></script>
<script>
layui.use(['form', 'layedit', 'laydate','element'], function(){
  var form = layui.form
  ,layer = layui.layer
  ,layedit = layui.layedit
  ,laydate = layui.laydate
  var url=$('#url').val();
  var element = layui.element;
  form.render();
  khlxrxh();
  jszje();

 
  //创建一个编辑器
  var editIndex = layedit.build('LAY_demo_editor');

//监听提交
  form.on('submit(demo1)', function(data){
    /* layer.alert(JSON.stringify(data.field), {
      title: '最终的提交信息'
    }) */
    return true;
  });

  
});
	
	function khlxrxh(){
		var len = $('#khlxrs tr').length;
	    for(var i = 1;i<len;i++){
	        $('#khlxrs tr:eq('+i+') th:first').text(i);
	    }
	}

	function refreshAndClose(){
		var flag=$('#flag').val();
		if(flag){
			window.parent.location.reload();
			window.close();
		} 
	}

	//页面加载时自动计算总金额
	function jszje(){
		var data=$('input[name="total_price"]');
		//遍历该数组
		var totalPrice=0;
		for(var i=0;i<data.length;i++){
			if(data[i].value!=undefined){
				//设置总金额
				totalPrice=(totalPrice*1)+(data[i].value*1);
			}
		}
		$('#totalprice').val(totalPrice);
	}

	//点击销售合同名称跳转至销售合同查看页
	$('#_field_xiaoShouHTMC').click(function(){
		 var url=$('#url').val();
		 var xsddId=$('#xsddId').val();
		 parent.layer.open({
		  	  	type:2,
		  	  	title:'查看合同',
		  	  	area: ['100%','100%'],
		  		shadeClose: false,
		  		resize:false,
		  	    anim: 1,
		  	  	content:[url+"sales/salesShow.do?sales_Contract_Id="+xsddId,'yes']
		});
	});
	


</script>
</body>
</html>