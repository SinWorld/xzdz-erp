<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>材料计划</title>
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
			<input type="hidden" value="${contract.sales_Contract_Id}" id="xsddId">
			<input type="hidden" value="${materialPlan.row_Id}" id="cljhId">
			<input type="hidden" value="${materialPlanOrders.size()}" id="cljhorderSize">
			
			<div class="layui-form-item" style="margin-top: 3%;">
			     <div class="layui-inline">
				      <label class="layui-form-label" style="width:150px;">材料计划号</label>
				      <div class="layui-input-inline">
				        <input type="text"  id="material_Code" lay-verify="" autocomplete="off" class="layui-input bj" style="width: 200px;" disabled="" value="${materialPlan.plan_Code}">
				      </div>
			     </div>
			     
				 <div class="layui-inline" style="right: -290px;">
				      <label class="layui-form-label" style="width: 139px;">下订单日期</label>
				      <div class="layui-input-inline">
				        <input type="text"  id="plan_Date" lay-verify="plan_Date" autocomplete="off" class="layui-input bj" style="width: 200px;" disabled="" value="${materialPlan.xddrq}">
				      </div>
			    </div>
			 </div>
			 
			 <div class="layui-form-item">
			     <div class="layui-inline">
				      <label class="layui-form-label" style="width:150px;">计划开工日期</label>
				      <div class="layui-input-inline">
				        <input type="text" name="plan_BeginDate" id="plan_BeginDate" lay-verify="plan_BeginDate" placeholder="yyyy-mm-dd" autocomplete="off" class="layui-input" style="width: 200px;" value="${materialPlan.jhkgrq}">
				      </div>
			     </div>
				 
				 <div class="layui-inline"  style="right: -290px;">
				      <label class="layui-form-label" style="width: 139px;">计划完成日期</label>
				      <div class="layui-input-inline">
				        <input type="text" name="plan_EndDate" id="plan_EndDate" lay-verify="plan_EndDate" placeholder="yyyy-mm-dd" autocomplete="off" class="layui-input" style="width: 200px;" value="${materialPlan.jhwgrq}">
				      </div>
			    </div>
			 </div>
			 
			<div class="layui-form-item layui-form-text">
		  		<label class="layui-form-label" style="width:133px;">生产计划</label>
				  <div class="layui-input-block" style="left: 10px;">
					<table class="table table-bordered" id="scjhorder" name="scjhorder" style="width: 100%">
					  <thead>
					    <tr>
					      <th scope="col" style="text-align: center;width: 5%">序号</th>
					      <th scope="col" style="text-align: center;width: 27%">成品名称</th>
					      <th scope="col" style="text-align: center;width: 27%">规格型号</th>
					      <th scope="col" style="text-align: center;width: 22%">物料Id</th>
					      <th scope="col" style="text-align: center;width: 19%">生产数量</th>
					    </tr>
					  </thead>
					  <tbody>
					  	<c:forEach items="${orders}" var="o">
					  		<tr>
					  			<td>
					  			
								</td>							  			
					  			<td>
					  			    <input type='text' class='form-control bj' aria-label='' aria-describedby='' disabled="" value='${o.productName}'>
					  			</td>
					  			<td>
					  			    <input type='text' class='form-control bj' aria-label='' aria-describedby='' disabled="" value='${o.ggxh}'>
					  			</td>
					  			<td>
					  			    <input type='text' class='form-control bj' aria-label='' aria-describedby='' disabled="" value='${o.materielId}'>
					  			</td>
					  			<td>
					  			    <input type='text' class='form-control bj' aria-label='' aria-describedby=''   disabled="" value='${o.scsl }'>
					  			</td>
					  		</tr>
					  	</c:forEach>
					  </tbody>
					</table>
				</div>
			</div>
		 
		
		<div class="layui-form-item layui-form-text">
	  		<label class="layui-form-label" style="width:133px;">材料计划</label>
	  		<div class="layui-input-block" style="text-align: left;">
				<button type="button" class="layui-btn layui-btn-normal" onclick="addRow()"><i class="layui-icon">&#xe608;</i>新增一行</button>	
			 </div>
			  <div class="layui-input-block" style="left: 10px;">
				<table class="table table-bordered" id="cljh"  style="width: 100%">
				  <thead>
				    <tr>
				      <th scope="col" style="text-align: center;width: 5%">序号</th>
				      <th scope="col" style="text-align: center;width: 24%">材料名称</th>
				      <th scope="col" style="text-align: center;width: 25%">规格型号</th>
				      <th scope="col" style="text-align: center;width: 20%">物料Id</th>
				      <th scope="col" style="text-align: center;width: 16%">计划数量</th>
				      <th scope="col" style="text-align: center;width: 10%">操作</th>
				    </tr>
				  </thead>
				  <tbody>
			  			<c:forEach items="${materialPlanOrders}" var="m">
					  		<tr>
					  			<td style="text-align: center;">
					  			
								</td>							  			
					  			<td>
					  				<input type="hidden" value="${m.row_Id}" name="row_id">
					  			    <input type='text' class='form-control' aria-label='' aria-describedby=''  value='${m.materialName}' name='clmc'>
					  			</td>
					  			<td>
					  			    <input type='text' class='form-control' aria-label='' aria-describedby=''  value='${m.specification_Type}' name='ggxh'>
					  			</td>
					  			<td>
					  			    <input type='text' class='form-control' aria-label='' aria-describedby=''  value='${m.materielId}'  name='materielId'>
					  			</td>
					  			<td>
					  			    <input type='text' class='form-control' aria-label='' aria-describedby=''  value='${m.planNumber}' name='jhsl' onblur='checkjhsl(this)'>
					  			</td>
					  			<td style='text-align:center;'>
					  				<button type='button' class='layui-btn layui-btn-danger' title='删除一行' onclick='deleteData(${m.row_Id})'><i class='layui-icon'>&#xe640;</i></button>
					  			</td>
					  		</tr>
					  	</c:forEach>
				  </tbody>
				</table>
			</div>
		</div>
		
		 <div class="layui-form-item layui-form-text">
		    <label class="layui-form-label" style="width:120px;">备注</label>
		    <div class="layui-input-block">
		      <textarea placeholder="请输入内容"   lay-verify="" id="remarks" class="layui-textarea" style="width:76.5%">${materialPlan.remarks}</textarea>
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
	  scjhorderxh();
	  cljhxh();
});
	

 	function scjhorderxh(){
		var len = $('#scjhorder tr').length;
	    for(var i = 1;i<=len-1;i++){
	        $('#scjhorder tr:eq('+i+') td:first').text(i);
	    }
	}

	//表格新增一行
	var index=0;
	function addRow(){
		//获得表格长度
		var cljhorderSize=$('#cljhorderSize').val();
		if("cljhorderSize"!=""){
			cljhorderSize++;
			$('#cljhorderSize').val(cljhorderSize);
			var tables=$('#cljh');
			var addtr = $("<tr>"+
				"<th scope='row' style='text-align: center;line-height:38px;'>"+cljhorderSize+"</th>"+
				"<td><input type='hidden' value='' name='row_id'><input type='text' class='form-control' aria-label='' aria-describedby=''  name='clmc'></td>"+
				"<td><input type='text' class='form-control' aria-label='' aria-describedby=''  name='ggxh' onblur='ggxhonblur(this)'></td>"+
				"<td><input type='text' class='form-control bj' aria-label='' aria-describedby='' disabled=''  name='materielId'></td>"+
				"<td><input type='text' class='form-control' aria-label='' aria-describedby=''  name='jhsl' onblur='checkjhsl(this)'></td>"+
				"<td style='text-align:center;'><button type='button' class='layui-btn layui-btn-danger' title='删除一行' onclick='deleteTrRow(this)'><i class='layui-icon'>&#xe640;</i></button></td>"+
				"</tr>");
		 		addtr.appendTo(tables);
		}else{
			index++;
			var tables=$('#cljh');
			var addtr = $("<tr>"+
					"<th scope='row' style='text-align: center;line-height:38px;'>"+index+"</th>"+
					"<td><input type='hidden' value='' name='row_id'><input type='text' class='form-control' aria-label='' aria-describedby=''  name='clmc'></td>"+
					"<td><input type='text' class='form-control' aria-label='' aria-describedby=''  name='ggxh' onblur='ggxhonblur(this)'></td>"+
					"<td><input type='text' class='form-control bj' aria-label='' aria-describedby='' disabled=''  name='materielId'></td>"+
					"<td><input type='text' class='form-control' aria-label='' aria-describedby=''  name='jhsl' onblur='checkjhsl(this)'></td>"+
					"<td style='text-align:center;'><button type='button' class='layui-btn layui-btn-danger' title='删除一行' onclick='deleteTrRow(this)'><i class='layui-icon'>&#xe640;</i></button></td>"+
					"</tr>");
					addtr.appendTo(tables);
		}     
	} 

	//表格删除一行
	function deleteTrRow(tr){
	    $(tr).parent().parent().remove();
	    index--;
	}

	function deleteData(row_Id){
		//ajax实现删除数据
		layer.confirm('您确定要删除该数据吗？此操作将不可逆转!!!', {
			  btn: ['确定','取消'], //按钮
			  title:'提示'},function(index){
				//删除数据
				  $.ajax({  
					    type: "post",  
					    url:  "<c:url value='/materialPlan/deleteMaterialOrderById.do'/>",
					    dataType: 'json',
					    async:false,
					    data:{"row_Id":row_Id},
					    error:function(){
					    	alert("出错");
					    },
					    success: function (data) {  
						    if(data.flag){
					    		layer.close(index);
					    		window.location.reload();
						    }
					    }  
					});
			  }
		  );
	}

	//材料规格型号光标失去事件获得相应的物料Id
	function ggxhonblur(obj){
		//获得当前填写的规格型号值
		var ggxh=obj.value;
		//获得当前表格行索引
		var index=obj.parentElement.parentElement.rowIndex;
		$.ajax({
			type : "post",
			url : "<c:url value='/materialPlan/queryMaterielId.do'/>",
			async : false,
			dataType : 'json',
			data:{"ggxh":ggxh},
			error : function() {
				alert("出错");
			},
			success : function(msg) {
				if(msg.materielId!=undefined){
					$('input[name="materielId"]')[index-1].value=msg.materielId;
				}else{
					$('input[name="materielId"]')[index-1].value="";
				}
			}
		});
	}

	//生产计划生产数量
	function checkjhsl(obj){
		//获得当前表格行索引
		var index=obj.parentElement.parentElement.rowIndex;
		//获得当前填写计划数量 
		var jhsl=obj.value;
		if(jhsl<0){
			$('input[name="jhsl"]')[index-1].value=0;
			return layer.alert("计划数量不得小于0",{icon:7});
		}
	}

	function cljhxh(){
		var len = $('#cljh tr').length;
	    for(var i = 1;i<=len-1;i++){
	        $('#cljh tr:eq('+i+') td:first').text(i);
	    }
	}

	//提交表单
	function  saveSubmit(){
		var url=$('#url').val();
		var taskId=$('#taskId').val();
		//创建材料计划对象
		var cljh=new Object();
		//获得材料计划id
		var cljhId=$('#cljhId').val();
		//获取材料计划号
		var cljhh=$('#material_Code').val();
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
		//获得备注
		var bz=$('#remarks').val();
		//货物当前表格
		var tables=$('#cljh');
		//获得表格所有行
		var rows=tables[0].rows;
		//遍历表格
		for(var i=1;i<rows.length;i++){
			//生产数量
			if($('input[name="jhsl"]')[i-1]!=undefined){
				var jhsl=$('input[name="jhsl"]')[i-1].value*1;
				if(jhsl==0){
					return 	layer.alert("第"+i+"行生产数量不允许为0",{icon:7});
				}
			}
					
			//材料名称
			if($('input[name="clmc"]')[i-1]!=undefined){
				var clmc=$('input[name="clmc"]')[i-1].value;
				if(clmc==""){
					return 	layer.alert("第"+i+"行材料名称未填写",{icon:7});
				}
			}
		
			//规格型号
			if($('input[name="ggxh"]')[i-1]!=undefined){
				var ggxh=$('input[name="ggxh"]')[i-1].value;
				if(ggxh==""){
					return 	layer.alert("第"+i+"行规格型号未填写",{icon:7});
				}
			}
			//物料id
			if($('input[name="materielId"]')[i-1]!=undefined){
				var wlId=$('input[name="materielId"]')[i-1].value;
				if(wlId==""){
					return 	layer.alert("第"+i+"行规格型号错误",{icon:7});
				}
			}
		}
		cljh.row_Id=cljhId;
		cljh.plan_Code=cljhh;
		cljh.plan_Date=xddrq;
		cljh.plan_BeginDate=jhkgrq;
		cljh.plan_EndDate=jhwcrq;
		cljh.sales_Contract_Id=xsddId;
		cljh.taskId=taskId;
		cljh.remarks=bz;
		$.ajax({
			type : "post",
			url : "<c:url value='/materialPlan/editMaterialPlan.do'/>",
			async : false,
			contentType :"application/json;charsetset=UTF-8",//必须
			dataType : 'json',
			data:JSON.stringify(cljh),
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

	//编辑或新增材料计划货物项
	function savePlanOder(){
		//创建生产计划货物项数组
		var cljhorders=new Array();
		//货物当前表格
		var tables=$('#cljh');
		//获得表格所有行
		var rows=tables[0].rows;
		//遍历表格
		for(var i=1;i<rows.length;i++){
			//材料货物项id
			var row_Id=$('input[name="row_id"]')[i-1].value;
			//材料名称
			var clmc=$('input[name="clmc"]')[i-1].value;
			//规格型号
			var ggxh=$('input[name="ggxh"]')[i-1].value;
			//物料Id
			var wlId=$('input[name="materielId"]')[i-1].value;
			//计划数量
			var jhsl=$('input[name="jhsl"]')[i-1].value;
			//创建材料计划清单对象
			var cljhOrder=new Object();
			cljhOrder.row_Id=row_Id;
			cljhOrder.materialName=clmc;
			cljhOrder.specification_Type=ggxh;
			cljhOrder.materielId=wlId;
			cljhOrder.planNumber=jhsl;
			cljhorders.push(cljhOrder);
		}
			$.ajax({
				type : "post",
				url : "<c:url value='/materialPlan/saveOrEditMaterialPlanOrder.do'/>",
				async : false,
				contentType :"application/json;charsetset=UTF-8",//必须
				dataType : 'json',
				data:JSON.stringify(cljhorders),
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