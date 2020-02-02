<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>成品核对</title>
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
	<div style="width:1280px;height:auto;padding:0px; margin:0 auto;" id="main">
		<form class="layui-form" action='<c:url value="/checkProduct/CheckProduct.do"/>' method="post">
			<input type="hidden" id="url" value='<c:url value="/"/>'>
			<input type="hidden" id="flag" value="${flag}">
			<input type="hidden" name="taskId" id="taskId" value="${taskId}">
			
			<div class="layui-form-item" style="margin-top: 3%;">
			    <label class="layui-form-label" style="width: 118px;">合同名称</label>
			    <div class="layui-input-block">
			      <input type="text" id="sales_Contract_Name" lay-verify="sales_Contract_Name" autocomplete="off" placeholder="合同名称" class="layui-input bj" style="width:72%" value="${contract.sales_Contract_Name}" disabled="">
			    </div>
			</div>
			 
		  <div class="layui-form-item layui-form-text">
	  		<label class="layui-form-label" style="width:133px;">订单货物项</label>
			  <div class="layui-input-block" style="left: 10px;">
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
				  			<td>
				  			
							</td>							  			
				  			<td>
				  			    <input type='text' class='form-control bj' aria-label='' aria-describedby='' disabled="" value='${o.material_Name}'>
				  			</td>
				  			<td>
				  			    <input type='text' class='form-control bj' aria-label='' aria-describedby='' disabled="" value='${o.specification_Type}'>
				  			</td>
				  			<td>
				  			    <input type='text' class='form-control bj' aria-label='' aria-describedby='' disabled="" value='${o.sl}'>
				  			</td>
				  			<td>
				  			    <input type='text' class='form-control bj' aria-label='' aria-describedby='' disabled="" value='${o.unit}'>
				  			</td>
				  			<td>
				  			    <input type='text' class='form-control bj' aria-label='' aria-describedby='' disabled="" value='${o.price}'>
				  			</td>
				  			<td>
				  			    <input type='text' class='form-control bj' aria-label='' aria-describedby='' disabled="" name="total_price"  value='${o.total_price}'>
				  			</td>
				  			<td>
				  			    <input type='text' class='form-control bj' aria-label='' aria-describedby=''  disabled="" value='${o.bz}'>
				  			</td>
				  		</tr>
				  	
				  	</c:forEach>
				  	<tr>
				  		<td>
				  		</td>
				  		<td>
				  			合计总金额
				  		</td>
				  		<td colspan="6">
				  			<input type='text' class='form-control bj' aria-label='' aria-describedby='' style="width: 165px;" disabled="" id="totalprice">
				  		</td>
				  	</tr>
				  </tbody>
				</table>
			</div>
		</div>
		
		 <div class="layui-form-item layui-form-text">
	  		<label class="layui-form-label" style="width:133px;">仓库闲置成品</label>
			  <div class="layui-input-block" style="left: 10px;">
				<table class="table table-bordered" id="ckcp" name="ckcp" style="width: 100%">
				  <thead>
				    <tr>
				      <th scope="col" style="text-align: center;width: 5%">序号</th>
				      <th scope="col" style="text-align: center;width: 25%">成品名称</th>
				      <th scope="col" style="text-align: center;width: 25%">规格型号</th>
				      <th scope="col" style="text-align: center;width: 25%">库位</th>
				      <th scope="col" style="text-align: center;width: 20%">库存数量</th>
				    </tr>
				  </thead>
				  <tbody>
				  	<c:forEach items="${list}" var="l">
				  		<tr>
				  			<td>
				  			
							</td>							  			
				  			<td>
				  			    <input type='text' class='form-control bj' aria-label='' aria-describedby='' disabled="" value='${l.productName}'>
				  			</td>
				  			<td>
				  			    <input type='text' class='form-control bj' aria-label='' aria-describedby='' disabled="" value='${l.ggxh}'>
				  			</td>
				  			<td>
				  			    <input type='text' class='form-control bj' aria-label='' aria-describedby='' disabled="" value='${l.stock}'>
				  			</td>
				  			<td>
				  			    <input type='text' class='form-control bj' aria-label='' aria-describedby='' disabled="" value='${l.kcnumber}'>
				  			</td>
				  		</tr>
				  	
				  	</c:forEach>
				  </tbody>
				</table>
			</div>
		</div>
		
		
		
		<div class="layui-form-item">
			    <label class="layui-form-label"  style="width: 125px;">审批结果</label>
			    <div class="layui-input-block">
			      <input type="radio" name="outcome" value="足够" title="足够" lay-verify="result"  lay-filter="erweima">
			      <input type="radio" name="outcome" value="不够" title="不够" lay-verify="result"  lay-filter="erweima">
			    </div>
			  </div>

			<div class="layui-form-item layui-form-text">
				<label class="layui-form-label" style="width: 125px;">审批意见</label>
				<div class="layui-input-block">
					<textarea placeholder="请输入内容" lay-verify="remark"
						class="layui-textarea" style="width: 56.5%" name="advice" id="advice"></textarea>
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
<script type="text/javascript" src="../jquery-easyui-1.7.0/jquery.easyui.min.js"></script>
<script>
layui.use(['form', 'layedit', 'laydate'], function(){
	  var form = layui.form
	  ,layer = layui.layer
	  ,layedit = layui.layedit
	  ,laydate = layui.laydate;
	  var url=$('#url').val();
	  form.render();
	  //创建一个编辑器
	  var editIndex = layedit.build('LAY_demo_editor');
	  //监听提交
	  form.on('submit(demo1)', function(data){
	    layer.alert(JSON.stringify(data.field), {
	      title: '最终的提交信息'
	    })
	    return true;
	  });
	  khlxrxh();
	  ckcpxh();
});
	
 	function khlxrxh(){
		var len = $('#khlxrs tr').length;
		var totalPrice=0;
	    for(var i = 1;i<len-1;i++){
	        $('table tr:eq('+i+') td:first').text(i);
	    }
	    var prices=$('input[name="total_price"]');
	    for(var i=0;i<prices.length;i++){
	    	totalPrice=(totalPrice*1)+(prices[i].value*1);
		}
		$('#totalprice').val(totalPrice);
	}

 	function ckcpxh(){
		var len = $('#ckcp tr').length;
	    for(var i = 1;i<=len-1;i++){
	        $('#ckcp tr:eq('+i+') td:first').text(i);
	    }
	}

	function refreshAndClose(){
		var flag=$('#flag').val();
		if(flag){
			window.parent.location.reload();
			window.close();
		}
	}
	
</script>
</body>
</html>