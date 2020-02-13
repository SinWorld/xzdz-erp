<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>新增送货单</title>
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
	<div style="width:1280px;height:auto;padding:0px; margin:0 auto;" id="main">
		<form class="layui-form" action='' method="post" id="myForm">
			<input type="hidden" id="url" value='<c:url value="/"/>'>
			<input type="hidden" name="taskId" id="taskId" value="${taskId}">
			<input type="hidden" value="${contractId}" id="contractId">
			<input type="hidden" value="${customerId}" id="customerId">
			<input type="hidden" value="${shjbr}" id="shjbr">
			
			<div class="layui-form-item" style="margin-top: 5%;">
			     <div class="layui-inline" style="top:9px;left: -29px;">
				      <label class="layui-form-label" style="width:150px;">收货单位</label>
				      <div class="layui-input-inline">
				        <input type="text"  autocomplete="off" class="layui-input bj" style="width: 280px;" disabled="" value="${customerName}">
				      </div>
			     </div>
			     
			      <div class="layui-inline" style="top:9px;left: 200px;">
				      <label class="layui-form-label" style="width:150px;">订单编号</label>
				      <div class="layui-input-inline">
				        <input type="text"   autocomplete="off" class="layui-input bj" style="width: 280px;" disabled="" value="${ddbh}" id="ddbh">
				      </div>
			     </div>
			 </div>
		 
			 <div class="layui-form-item">
			      <div class="layui-inline" style="left: -29px;">
				      <label class="layui-form-label" style="width:150px;">送货日期</label>
				      <div class="layui-input-inline">
				        <input type="text"  autocomplete="off" class="layui-input" style="width: 280px;"  value="${qdrq}" id="qdrq">
				      </div>
			     </div>
			     
			      <div class="layui-inline" style="left:210px;">
				      <label class="layui-form-label" style="width: 139px;">经办人</label>
				      <div class="layui-input-inline">
				        <input type="text"  autocomplete="off" class="layui-input bj" style="width: 280px;" disabled="" value="${shjbrName}">
				      </div>
			    </div>
			 </div>
		
		 <div class="layui-form-item layui-form-text">
	  		<label class="layui-form-label" style="width:133px;">货物明细</label>
			  <div class="layui-input-block" style="left: 10px;">
				<table class="table table-bordered" id="khlxrs" style="width: 100%">
				  <thead>
				    <tr>
				      <th scope="col" style="text-align: center;width: 5%">序号</th>
				      <th scope="col" style="text-align: center;width: 20%">物料名称</th>
				      <th scope="col" style="text-align: center;width: 20%">规格型号</th>
				      <th scope="col" style="text-align: center;width: 15%">单位</th>
				      <th scope="col" style="text-align: center;width: 15%">送货数量</th>
				      <th scope="col" style="text-align: center;width: 25%">备注</th>
				    </tr>
				  </thead>
				  <tbody>
				  	<c:forEach items="${orderList}" var="o">
				  		<tr>
				  			<td>
				  			
							</td>							  			
				  			<td>
				  				<input type="hidden" value="${o.stock }" name="stock">
				  				<input type="hidden" value="${o.product }" name="product">
				  			    <input type='text' class='form-control bj' aria-label='' aria-describedby='' disabled="" value='${o.material_Name}' name="wlmc">
				  			</td>
				  			<td>
				  			    <input type='text' class='form-control bj' aria-label='' aria-describedby='' disabled="" value='${o.specification_Type}' name="ggxh">
				  			</td>
				  			<td>
				  			    <input type='text' class='form-control bj' aria-label='' aria-describedby='' disabled="" value='${o.company}' name="dw">
				  			</td>
				  			<td>
				  			    <input type='text' class='form-control bj' aria-label='' aria-describedby='' disabled="" value='${o.delivery_Number}' name="shsl">
				  			</td>
				  			<td>
				  			    <input type='text' class='form-control' aria-label='' aria-describedby='' name="bz">
				  			</td>
				  		</tr>
				  	</c:forEach>
				  </tbody>
				</table>
			</div>
		</div>
		
		<div class="layui-form-item" style="text-align: center;">
		    <div class="layui-input-block">
		      <button class="layui-btn" lay-submit="" lay-filter="" style="width:25%;margin-top:10px;margin-left:-315px;" type="button" onclick="saveSubmit()">立即提交</button>
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
	  //初始赋值
	  laydate.render({
	    elem: '#qdrq'
	    ,value:new Date()
	    ,isInitValue: true
	  });
	  form.render();
	  //创建一个编辑器
	  var editIndex = layedit.build('LAY_demo_editor');
	  ckcpxh();
});
	
 	
 	function ckcpxh(){
		var len = $('#khlxrs tr').length;
	    for(var i = 1;i<=len-1;i++){
	        $('#khlxrs tr:eq('+i+') td:first').text(i);
	    }
	}

	//提交表单
	function  saveSubmit(){
		var url=$('#url').val();
		var taskId=$('#taskId').val();
		var contractId=$('#contractId').val();//销售订单主键
		var ddbh=$('#ddbh').val();//订单编号
		var qdrq=$('#qdrq').val();//签订日期
		var shjbr=$('#shjbr').val();//送货经办人
		var customerId=$('#customerId').val();//收货单位
		var delibery=new Object();
		delibery.delivery_Code=ddbh;
		delibery.delivery_Date=qdrq;
		delibery.songHuojbr=shjbr;
		delibery.shouHuojbr="";
		delibery.sales_Contract_Id=contractId;
		delibery.delivery_Customer=customerId;
		$.ajax({
			type : "post",
			url : "<c:url value='/delivery/saveDelivery.do'/>",
			async : false,
			contentType :"application/json;charsetset=UTF-8",//必须
			dataType : 'json',
			data:JSON.stringify(delibery),
			error : function() {
				alert("出错");
			},
			success : function(data) {
				if(data.flag){
					saveDeliveryOrder();
				}
			}
		});
	}

	function  saveDeliveryOrder(){
		//货物当前表格
		var tables=$('#khlxrs');
		//获得表格所有行
		var rows=tables[0].rows;
		//创建送货项数组
		var deliveryOrder=new Array();
		//遍历表格
		for(var i=1;i<rows.length;i++){
			//物料名称
			var wlmc=$('input[name="wlmc"]')[i-1].value;
			//规格型号
			var ggxh=$('input[name="ggxh"]')[i-1].value;
			//单位
			var dw=$('input[name="dw"]')[i-1].value;
			//送货数量
			var shsl=$('input[name="shsl"]')[i-1].value;
			//备注
			var bz=$('input[name="bz"]')[i-1].value;
			//库存
			var stock=$('input[name="stock"]')[i-1].value;
			//成品
			var product=$('input[name="product"]')[i-1].value;
			//创建送货项对象
			var order=new Object();
			order.material_Name=wlmc;
			order.specification_Type=ggxh;
			order.company=dw;
			order.delivery_Number=shsl;
			order.remarks=bz;
			order.stock=stock;
			order.product=product;
			deliveryOrder.push(order);
		}
		$.ajax({
			type : "post",
			url : "<c:url value='/delivery/saveDeliveryOrder.do'/>",
			async : false,
			contentType :"application/json;charsetset=UTF-8",//必须
			dataType : 'json',
			data:JSON.stringify(deliveryOrder),
			error : function() {
				alert("出错");
			},
			success : function(msg) {
				if(msg.flag){
					processDefin();
				} 
			}
		});
	}

	//推动流程
	function processDefin(){
		//获得任务Id
		var taskId=$('#taskId').val();
		$.ajax({
			type : "post",
			url : "<c:url value='/delivery/tdlc.do'/>",
			async : false,
			dataType : 'json',
			data:{"taskId":taskId},
			error : function() {
				alert("出错");
			},
			success : function(msg) {
				if(msg.flag){
					window.parent.location.reload();
					window.close();
				} 
			}
		});
	}
	
</script>
</body>
</html>