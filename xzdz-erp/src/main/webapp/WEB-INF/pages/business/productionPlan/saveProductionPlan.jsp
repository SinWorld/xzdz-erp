<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>生产计划</title>
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
		<form class="layui-form" action='' method="post" id="myForm">
			<input type="hidden" id="url" value='<c:url value="/"/>'>
			<input type="hidden" id="flag" value="${flag}">
			<input type="hidden" name="taskId" id="taskId" value="${taskId}">
			<input type="hidden" value="${contract.sales_Contract_Id}" id="xsddId">
			<input type="hidden" value="${planOrders.size()}" id="scjhlength">
			<input type="hidden" value="" id="kg">
			<input type="hidden" id="str">
			
			<div class="layui-form-item" style="margin-top: 3%;">
			     <div class="layui-inline">
				      <label class="layui-form-label" style="width:150px;">生产计划号</label>
				      <div class="layui-input-inline">
				        <input type="text"  id="plan_Code" lay-verify="plan_Code" autocomplete="off" class="layui-input bj" style="width: 200px;" disabled="" value="${plan_Code}">
				      </div>
			     </div>
			     
			     <div class="layui-inline">
				  	<label class="layui-form-label" style="width:138px;">生产部门</label>
					<div class="layui-input-inline" style="text-align: left;width: 200px;">
						<select name="plan_Department" id="plan_Department" lay-filter="plan_Department" lay-verify="plan_Department">
							<option value="" selected="selected">请选择生产部门</option>
						</select>
					</div>
				 </div>
				 
				 <div class="layui-inline" >
				      <label class="layui-form-label" style="width: 139px;">下订单日期</label>
				      <div class="layui-input-inline">
				        <input type="text"  id="plan_Date" lay-verify="plan_Date" autocomplete="off" class="layui-input bj" style="width: 200px;" disabled="" value="${plan_Date}">
				      </div>
			    </div>
			 </div>
			 
			 <div class="layui-form-item">
			     <div class="layui-inline">
				      <label class="layui-form-label" style="width:150px;">计划开工日期</label>
				      <div class="layui-input-inline">
				        <input type="text" name="plan_BeginDate" id="plan_BeginDate" lay-verify="plan_BeginDate" placeholder="yyyy-mm-dd" autocomplete="off" class="layui-input" style="width: 200px;">
				      </div>
			     </div>
				 
				 <div class="layui-inline">
				      <label class="layui-form-label" style="width: 139px;">计划完成日期</label>
				      <div class="layui-input-inline">
				        <input type="text" name="plan_EndDate" id="plan_EndDate" lay-verify="plan_EndDate" placeholder="yyyy-mm-dd" autocomplete="off" class="layui-input" style="width: 200px;">
				      </div>
			    </div>
			 </div>
			 
		  <div class="layui-form-item layui-form-text">
	  		<label class="layui-form-label" style="width:133px;">订单货物项</label>
			  <div class="layui-input-block" style="left: 10px;">
				<table class="table table-bordered" id="khlxrs" style="width: 100%">
				  <thead>
				    <tr>
				      <th scope="col" style="text-align: center;width: 5%">序号</th>
				      <th scope="col" style="text-align: center;width: 13%">物资名称</th>
				      <th scope="col" style="text-align: center;width: 13%">规格型号</th>
				      <th scope="col" style="text-align: center;width: 10%">物料ID</th>
				      <th scope="col" style="text-align: center;width: 9%">数量</th>
				      <th scope="col" style="text-align: center;width: 9%">单位</th>
				      <th scope="col" style="text-align: center;width: 10%">单价(元)</th>
				      <th scope="col" style="text-align: center;width: 10%">金额(元)</th>
				      <th scope="col" style="text-align: center;width: 13%">备注</th>
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
				  			    <input type='text' class='form-control bj' aria-label='' aria-describedby='' disabled="" value='${o.materielId}' name="xswlId">
				  			</td>
				  			<td>
				  			    <input type='text' class='form-control bj' aria-label='' aria-describedby='' disabled="" value='${o.sl}' name="xsddcpsl">
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
				  		<td colspan="7">
				  			<input type='text' class='form-control bj' aria-label='' aria-describedby='' style="width: 165px;" disabled="" id="totalprice">
				  		</td>
				  	</tr>
				  </tbody>
				</table>
			</div>
		</div>
		
		 <div class="layui-form-item layui-form-text">
	  		<label class="layui-form-label" style="width:133px;">成品核对反馈</label>
			  <div class="layui-input-block" style="left: 10px;">
				<table class="table table-bordered" id="ckcp" name="ckcp" style="width: 100%">
				  <thead>
				    <tr>
				      <th scope="col" style="text-align: center;width: 5%">序号</th>
				      <th scope="col" style="text-align: center;width: 20%">成品名称</th>
				      <th scope="col" style="text-align: center;width: 20%">规格型号</th>
				      <th scope="col" style="text-align: center;width: 15%">物料Id</th>
				      <th scope="col" style="text-align: center;width: 15%">库位</th>
				      <th scope="col" style="text-align: center;width: 13%">库存数量</th>
				      <th scope="col" style="text-align: center;width: 12%">出库数量</th>
				    </tr>
				  </thead>
				  <tbody>
				  	<c:forEach items="${list}" var="l">
				  		<tr>
				  			<td>
				  			
							</td>							  			
				  			<td>
				  				<input type="hidden" value="${l.stock_Id }" name="stock">
				  				<input type="hidden" value="${l.product_Id }" name="product_Id">
				  			    <input type='text' class='form-control bj' aria-label='' aria-describedby='' disabled="" value='${l.productName}' >
				  			</td>
				  			<td>
				  			    <input type='text' class='form-control bj' aria-label='' aria-describedby='' disabled="" value='${l.ggxh}'>
				  			</td>
				  			<td>
				  			    <input type='text' class='form-control bj' aria-label='' aria-describedby='' disabled="" value='${l.materielId}'>
				  			</td>
				  			<td>
				  			    <input type='text' class='form-control bj' aria-label='' aria-describedby='' disabled="" value='${l.stock}'>
				  			</td>
				  			<td>
				  			    <input type='text' class='form-control bj' aria-label='' aria-describedby='' disabled="" value='${l.kcNumber}' name="kcsl">
				  			</td>
				  			<td>
				  			    <input type='text' class='form-control bj' aria-label='' aria-describedby='' disabled="" name="qcsl" value="${l.cksl}">
				  			</td>
				  		</tr>
				  	
				  	</c:forEach>
				  </tbody>
				</table>
			</div>
		</div>
		
		<div class="layui-form-item layui-form-text">
	  		<label class="layui-form-label" style="width:133px;">生产计划</label>
	  		<br>
	  		<div class="layui-input-block" style="text-align: left;left:-10px;top:-4px;" id="xzyh">
				<button type="button" class="layui-btn layui-btn-normal" onclick="addRow()"><i class="layui-icon">&#xe608;</i>新增一行</button>	
			 </div>
			  <div class="layui-input-block" style="left: 10px;">
				<table class="table table-bordered" id="scjhorder" name="scjhorder" style="width: 100%">
				  <thead>
				    <tr>
				      <th scope="col" style="text-align: center;width: 5%">序号</th>
				      <th scope="col" style="text-align: center;width: 24%">成品名称</th>
				      <th scope="col" style="text-align: center;width: 24%">规格型号</th>
				      <th scope="col" style="text-align: center;width: 15%">物料Id</th>
				      <th scope="col" style="text-align: center;width: 12%">生产数量</th>
				      <th scope="col" style="text-align: center;width: 10%">单位</th>
				      <th scope="col" style="text-align: center;width: 10%">操作</th>
				    </tr>
				  </thead>
				  <tbody>
				  	<c:forEach items="${planOrders}" var="p">
				  		<tr>
				  			<td>
				  			
							</td>							  			
				  			<td>
				  				<input type="hidden" value="${p.product}" name="productId">
				  			    <input type='text' class='form-control bj' aria-label='' aria-describedby='' disabled="" value='${p.erp_product.product_Name}' name="productionName">
				  			</td>
				  			<td>
				  			    <input type='text' class='form-control bj' aria-label='' aria-describedby='' disabled="" value='${p.erp_product.specification_Type}' name="ggxh">
				  			</td>
				  			<td>
				  			    <input type='text' class='form-control bj' aria-label='' aria-describedby='' disabled="" value='${p.erp_product.materielid}' name="materielid">
				  			</td>
				  			<td>
				  			    <input type='text' class='form-control' aria-label='' aria-describedby='' value='0'  name="scsl">
				  			</td>
				  			<td>
				  			    <input type='text' class='form-control' aria-label='' aria-describedby='' value='${p.erp_product.unit}'  name="dw">
				  			</td>
				  			<td>
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
	  //日期
	  laydate.render({
	    elem: '#plan_BeginDate'
	    ,format: 'yyyy-MM-dd'
	  });
	  //日期
	  laydate.render({
	    elem: '#plan_EndDate'
	    ,format: 'yyyy-MM-dd'
	  });
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
	  scjhorderxh();
	  hideButton();
	  reloadDepartment(form);
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


 	function scjhorderxh(){
		var len = $('#scjhorder tr').length;
	    for(var i = 1;i<=len-1;i++){
	        $('#scjhorder tr:eq('+i+') td:first').text(i);
	    }
	}

	function refreshAndClose(){
		var flag=$('#flag').val();
		if(flag){
			window.parent.location.reload();
			window.close();
		}
	}

	//是否隐藏新增一行按钮
	function hideButton(){
		//获取生产计划计划长度
		var length=$('#scjhlength').val();
		if(length==0){
			//显示按钮
			$('#xzyh').show();
		}else{
			$('#xzyh').hide();
		}
	}


	//ajax加载所有的部门
	function reloadDepartment(form){
		$.ajax({
			type : "post",
			url : "<c:url value='/prouctPlan/allDepartment.do'/>",
			async : false,
			dataType : 'json',
			error : function() {
				alert("出错");
			},
			success : function(msg) {
				for (var i = 0; i < msg.length; i++) {
					$("#plan_Department").append(
							"<option value='"+msg[i].dep_Id+"'>"+ msg[i].dep_Name +"</option>");
				}
				form.render('select');
			}
		});
	}


	//表格新增一行
	var index=0;
	function addRow(){
		index++;
		var tables=$('#scjhorder');
		var addtr = $("<tr>"+
				"<th scope='row' style='text-align: center;line-height:38px;'>"+index+"</th>"+
				"<td><input type='hidden' value='' name='productId'><input type='text' class='form-control' aria-label='' aria-describedby='' name='productionName'></td>"+
			    "<td><input type='text' class='form-control' aria-label='' aria-describedby='' onblur='product_materielId(this)' name='ggxh'></td>"+
				"<td><input type='text' class='form-control bj' aria-label='' aria-describedby='' disabled=''  name='materielid'></td>"+
				"<td><input type='text' class='form-control' aria-label='' aria-describedby='' value='0'  name='scsl'></td>"+
				"<td><input type='text' class='form-control' aria-label='' aria-describedby='' name='dw'></td>"+
				"<td style='text-align:center;'><button type='button' class='layui-btn layui-btn-danger' title='删除一行' onclick='deleteTrRow(this)'><i class='layui-icon'>&#xe640;</i></button></td>"+
				"</tr>"
				);
		 addtr.appendTo(tables);     
	} 

	//表格删除一行
	function deleteTrRow(tr){
	    $(tr).parent().parent().remove();
	    index--;
	}

	



	//检验生产计划生产数量
	function checkScsl(){
		//获取销售订单id
		var xsddId=$('#xsddId').val();
		//获取任务id
		var taskId=$('#taskId').val();
		$.ajax({
			type : "post",
			url : "<c:url value='/prouctPlan/checkScsl.do'/>",
			async : false,
			dataType : 'json',
			data:{"xsddId":xsddId,"taskId":taskId},
			error : function() {
				alert("出错");
			},
			success : function(msg) {
				//遍历结果集
				for(var i=0;i<msg.length;i++){
					//获得物料Id
					var materialId=msg[i].data.materialId;
					//获得数量结果
					var result=msg[i].data.result*1;
					//获得生产计划项
					var scjhorder=$('#scjhorder');
					//获得表格所有行
					var scjhhwxRows=scjhorder[0].rows;
					//遍历表格
					var totalscsl=0;
					for(var j=1;j<scjhhwxRows.length;j++){
						//获得销售订单货物项中的物料Id
						if($('input[name="materielid"]')[j-1]!=undefined && $('input[name="scsl"]')[j-1]!=undefined){
							  var wlId=$('input[name="materielid"]')[j-1].value;
							  if(materialId==wlId){
								//获得所有相同物料id填写的数量
								var scsl=$('input[name="scsl"]')[j-1].value*1;
								totalscsl=totalscsl*1+scsl;
							  }
						}
					}
					if(result>totalscsl){
						$('#kg').val(true);
					 	return	layer.alert("生产计划中所有物料Id为:"+materialId+"的生产数量之和不得小于"+result,{icon:7});
					}else{
						$('#kg').val(false);
					}
				}
			}
		});
	}


	//加载成品对应的物料Id
	function product_materielId(obj){
		//获得当前表格行索引
		var index=obj.parentElement.parentElement.rowIndex;
		var specification_Type=obj.value;
			$.ajax({
				type : "post",
				url : "<c:url value='/product/product_materielId.do'/>",
				async : false,
				dataType : 'json',
				data:{"specification_Type":specification_Type},
				error : function() {
					alert("出错");
				},
				success : function(msg) {
					if(msg.materielId!=undefined){
						$('input[name="materielid"]')[index-1].value=msg.materielId;
					}else{
						$('input[name="materielid"]')[index-1].value="";
					}
				}
			});
	}

	//提交表单
	function  saveSubmit(){
		checkScsl();
		var kg=	$('#kg').val();
		if(kg=='true'){
			return;
		}else{
			var url=$('#url').val();
			var taskId=$('#taskId').val();
			//创建生产计划对象
			var scjh=new Object();
			//获取生产计划号
			var scjhh=$('#plan_Code').val();
			//获取生产部门
			var  scbm=$('#plan_Department').val();
			if(scbm==""){
				return 	layer.alert("生产部门不能为空!",{icon:7});
			}
			//获取下订单日期
			var  xddrq=$('#plan_Date').val();
			//获取计划开工日期
			var  jhkgrq=$('#plan_BeginDate').val();
			if(jhkgrq==""){
				return 	layer.alert("计划开工日期不能为空!",{icon:7});
			}
			//获取计划完成日期
			var jhwcrq=$('#plan_EndDate').val();
			if(jhwcrq==""){
				return 	layer.alert("计划完成日期不能为空!",{icon:7});
			}
			//获取销售订单id
			var xsddId=$('#xsddId').val();
			//获取任务id
			var taskId=$('#taskId').val();
			//货物当前表格
			var tables=$('#scjhorder');
			//获得表格所有行
			var rows=tables[0].rows;
			//遍历表格
			for(var i=1;i<rows.length;i++){
				//生产数量
				var scsl=$('input[name="scsl"]')[i-1].value*1;
				if(scsl==0){
					return 	layer.alert("第"+i+"行生产数量不允许0",{icon:7});
				}
				//获得物料Id
				var wlId=$('input[name="materielid"]')[i-1].value;
				if(wlId==""){
					return 	layer.alert("第"+i+"行规格型号有错误！",{icon:7});
				}
			}
			scjh.plan_Code=scjhh;
			scjh.plan_Department=scbm;
			scjh.plan_Date=xddrq;
			scjh.plan_BeginDate=jhkgrq;
			scjh.plan_EndDate=jhwcrq;
			scjh.sales_Contract_Id=xsddId;
			scjh.taskId=taskId;
			$.ajax({
				type : "post",
				url : "<c:url value='/prouctPlan/saveProductionPlan.do'/>",
				async : false,
				contentType :"application/json;charsetset=UTF-8",//必须
				dataType : 'json',
				data:JSON.stringify(scjh),
				error : function() {
					alert("出错");
				},
				success : function(data) {
					if(data.flag){
						savePlanOder();
					}
				}
			});
		}
	}

	//新增生产计划货物项
	function savePlanOder(){
		//创建生产计划货物项数组
		var scjhorders=new Array();
		//货物当前表格
		var tables=$('#scjhorder');
		//获得表格所有行
		var rows=tables[0].rows;
		//遍历表格
		for(var i=1;i<rows.length;i++){
			//成品名称
			var productionName=$('input[name="productionName"]')[i-1].value;
			//规格型号
			var ggxh=$('input[name="ggxh"]')[i-1].value;
			//物料id
			var wlId=$('input[name="materielid"]')[i-1].value;
			//成品Id
			var cpId=$('input[name="productId"]')[i-1].value;
			//生产数量
			var scsl=$('input[name="scsl"]')[i-1].value;
			//单位
			var dw=$('input[name="dw"]')[i-1].value;
			//创建生产计划清单对象
			var scjhOrder=new Object();
			scjhOrder.productName=productionName;
			scjhOrder.ggxh=ggxh;
			scjhOrder.materielId=wlId;
			scjhOrder.product=cpId;
			scjhOrder.scsl=scsl;
			scjhOrder.unit=dw;
			scjhorders.push(scjhOrder);
		}
			$.ajax({
				type : "post",
				url : "<c:url value='/prouctPlan/saveProductionPlanOrder.do'/>",
				async : false,
				contentType :"application/json;charsetset=UTF-8",//必须
				dataType : 'json',
				data:JSON.stringify(scjhorders),
				error : function() {
					alert("出错");
				},
				success : function(msg) {
					if(msg.flag){
						  var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
			              parent.location.reload();//刷新父页面，注意一定要在关闭当前iframe层之前执行刷新
			              parent.layer.close(index); //再执行关闭
					} 
				}
			});
	}
</script>
</body>
</html>