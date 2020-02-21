<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>加工配料</title>
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
			<input type="hidden" id="str">
			
		<div class="layui-form-item layui-form-text" style="margin-top: 3%;">
	  		<label class="layui-form-label" style="width:133px;">材料计划</label>
			  <div class="layui-input-block" style="left: 10px;">
				<table class="table table-bordered" id="cljh"  style="width: 100%">
				  <thead>
				    <tr>
				      <th scope="col" style="text-align: center;width: 7%">序号</th>
				      <th scope="col" style="text-align: center;width: 26%">材料名称</th>
				      <th scope="col" style="text-align: center;width: 27%">规格型号</th>
				      <th scope="col" style="text-align: center;width: 28%">物料Id</th>
				      <th scope="col" style="text-align: center;width: 18%">计划数量</th>
				    </tr>
				  </thead>
				  <tbody>
					  <c:forEach items="${orders}" var="o">
					  		<tr>
					  			<td>
					  			
								</td>							  			
					  			<td>
					  			    <input type='text' class='form-control bj' aria-label='' aria-describedby='' disabled="" value='${o.materialName}'>
					  			</td>
					  			<td>
					  			    <input type='text' class='form-control bj' aria-label='' aria-describedby='' disabled="" value='${o.specification_Type}'>
					  			</td>
					  			<td>
					  			    <input type='text' class='form-control bj' aria-label='' aria-describedby='' disabled="" value='${o.materielId}' name="cljhwlId">
					  			</td>
					  			<td>
					  			    <input type='text' class='form-control bj' aria-label='' aria-describedby='' disabled="" value='${o.planNumber}' name="jhsl">
					  			</td>
					  			
					  		</tr>
					  	</c:forEach>
				  </tbody>
				</table>
			</div>
		</div>	
			 
		  
		
		 <div class="layui-form-item layui-form-text">
	  		<label class="layui-form-label" style="width:133px;">仓库闲置材料</label>
			  <div class="layui-input-block" style="left: 10px;">
				<table class="table table-bordered" id="ckcp"  style="width: 100%">
				  <thead>
				    <tr>
				      <th scope="col" style="text-align: center;width: 5%">序号</th>
				      <th scope="col" style="text-align: center;width: 15%">材料名称</th>
				      <th scope="col" style="text-align: center;width: 20%">规格型号</th>
				      <th scope="col" style="text-align: center;width: 15%">物料Id</th>
				      <th scope="col" style="text-align: center;width: 15%">库位</th>
				      <th scope="col" style="text-align: center;width: 15%">库存数量</th>
				      <th scope="col" style="text-align: center;width: 15%">出库数量</th>
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
				  			    <input type='text' class='form-control bj' aria-label='' aria-describedby='' disabled="" value='${l.productName}'>
				  			</td>
				  			<td>
				  			    <input type='text' class='form-control bj' aria-label='' aria-describedby='' disabled="" value='${l.ggxh}'>
				  			</td>
				  			<td>
				  			    <input type='text' class='form-control bj' aria-label='' aria-describedby='' disabled="" value='${l.materielId}' name="xzwlId">
				  			</td>
				  			<td>
				  			    <input type='text' class='form-control bj' aria-label='' aria-describedby='' disabled="" value='${l.stock}'>
				  			</td>
				  			<td>
				  			    <input type='text' class='form-control bj' aria-label='' aria-describedby='' disabled="" value='${l.kcNumber}' name="kcsl">
				  			</td>
				  			<td>
				  			    <input type='text' class='form-control' aria-label='' aria-describedby='' name="qcsl" value="0"  onchange="checkedqcsl(this)">
				  			</td>
				  		</tr>
				  	</c:forEach>
				  </tbody>
				</table>
			</div>
		</div>
		
		
		<div class="layui-form-item layui-form-text">
	  		<label class="layui-form-label" style="width:133px;">加工配料</label>
			  <div class="layui-input-block" style="left: 10px;">
				<table class="table table-bordered" id="jgpl"  style="width: 100%">
				  <thead>
				    <tr>
				      <th scope="col" style="text-align: center;width: 7%">序号</th>
				      <th scope="col" style="text-align: center;width: 26%">材料名称</th>
				      <th scope="col" style="text-align: center;width: 27%">规格型号</th>
				      <th scope="col" style="text-align: center;width: 28%">物料Id</th>
				      <th scope="col" style="text-align: center;width: 18%">采购数量</th>
				    </tr>
				  </thead>
				  <tbody>
					  <c:forEach items="${orders}" var="o">
					  		<tr>
					  			<td>
					  			
								</td>							  			
					  			<td>
					  				<input type="hidden" value="${o.row_Id }" name="hwxId">
					  			    <input type='text' class='form-control bj' aria-label='' aria-describedby='' disabled="" value='${o.materialName}'>
					  			</td>
					  			<td>
					  			    <input type='text' class='form-control bj' aria-label='' aria-describedby='' disabled="" value='${o.specification_Type}'>
					  			</td>
					  			<td>
					  			    <input type='text' class='form-control bj' aria-label='' aria-describedby='' disabled="" value='${o.materielId}' name="materielid">
					  			</td>
					  			<td>
					  			    <input type='text' class='form-control ' aria-label='' aria-describedby=''  value='0' name="scsl" onblur="checkScsl(this)">
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
	  form.render();
	  //创建一个编辑器
	  var editIndex = layedit.build('LAY_demo_editor');
	  //监听提交
	  ckcpxh();
	  cljhxh();
	  jgplxh();
});
	

 	function ckcpxh(){
		var len = $('#ckcp tr').length;
	    for(var i = 1;i<=len-1;i++){
	        $('#ckcp tr:eq('+i+') td:first').text(i);
	    }
	}

	function cljhxh(){
		var len = $('#cljh tr').length;
	    for(var i = 1;i<=len-1;i++){
	        $('#cljh tr:eq('+i+') td:first').text(i);
	    }
	}

	function jgplxh(){
		var len = $('#jgpl tr').length;
	    for(var i = 1;i<=len-1;i++){
	        $('#jgpl tr:eq('+i+') td:first').text(i);
	    }
	}

	function refreshAndClose(){
		var flag=$('#flag').val();
		if(flag){
			window.parent.location.reload();
			window.close();
		}
	}

	//校验仓库闲置成品表格取出数量
	function checkedqcsl(obj){
		//获得当前表格行索引
		var index=obj.parentElement.parentElement.rowIndex;
		//获得库存数量
		var kcsl=$('input[name="kcsl"]')[index-1].value*1;
		//获得出库数量
		var cksl=obj.value*1;
		if(cksl>kcsl){
			qcsl=$('input[name="qcsl"]')[index-1].value=0;
			return layer.alert("第"+index+"行出库数量不得大于库存数量",{icon:7});
		}
		if(cksl<0){
			qcsl=$('input[name="qcsl"]')[index-1].value=0;
			return layer.alert("第"+index+"行出库数量不得小于0",{icon:7});
		}
	}

	//加工配料生产数量
	function checkScsl(obj){
		//获得当前表格行索引
		var index=obj.parentElement.parentElement.rowIndex;
		//获得当前行的物料Id
		var materielId=$('input[name="materielid"]')[index-1].value;
		//获得闲置成品表格
		var tables=$('#ckcp');
		//获得表格所有行
		var rows=tables[0].rows;
		//遍历表格
		var rksl=0;
		for(var i=1;i<rows.length;i++){
			//获得闲置成品表格中的物料Id
			if($('input[name="qcsl"]')[i-1]!=undefined){
		    	var xzwlId=$('input[name="xzwlId"]')[i-1].value;
				if(materielId==xzwlId){
					//获得已入库数量值
					var cksl=$('input[name="qcsl"]')[i-1].value*1;
					rksl=rksl*1+cksl;
				}
			}
		}
		//获得材料计划货物项
		var cljhhwx=$('#cljh');
		//获得表格所有行
		var cljhhwxRows=cljhhwx[0].rows;
		//遍历表格
		var sxcpsl=0;
		for(var i=1;i<cljhhwxRows.length;i++){
			//获得销售订单货物项中的物料Id
			if($('input[name="cljhwlId"]')[i-1]!=undefined && $('input[name="jhsl"]')[i-1]!=undefined){
				  var xswlId=$('input[name="cljhwlId"]')[i-1].value;
				  if(materielId==xswlId){
					//获得销售订单中的相同成品的数量
					var jhsl=$('input[name="jhsl"]')[i-1].value*1;
					sxcpsl=sxcpsl*1+jhsl;
				  }
			}
		}
		//订单货物数量-成品核对需要入库的数量
		var result=sxcpsl-rksl;
		//获得当前填写的生产数量
	    var scsl=obj.value*1;
		if(scsl<result){
			layer.alert("当前生产数量不得小于该成品的剩余量:"+result,{icon:7});
			$('input[name="scsl"]')[index-1].value=0;
		} 
		
	}

	//校验仓库加工配料表格
	function xzcptable(){
		//获得闲置成品表格
		var tables=$('#jgpl');
		//获得表格所有行
		var rows=tables[0].rows;
		$('#str').val();
		var str=$('#str').val();
		//遍历表格
		for(var i=1;i<rows.length;i++){
			//获得加工配料表格中已存在的材料计划货物项主键
			var cljhhwxId=""
			if($('input[name="hwxId"]')[i-1]!=undefined){
				cljhhwxId=$('input[name="hwxId"]')[i-1].value;
			}
			//生产数量
			var scsl=""
			if($('input[name="scsl"]')[i-1]!=undefined){
				scsl=$('input[name="scsl"]')[i-1].value*1;
			}
			var data=cljhhwxId+":"+scsl;
			if(null!=str&&""!=str){
				str=str+","+data;
			 }else{
				 str=data;
			 }
		}
		return str;
	}


	//提交表单
	function  saveSubmit(){
		var url=$('#url').val();
		var taskId=$('#taskId').val();
		var spjgs=$('input[name="outcome"]');
		var spjg;
		for(var i=0;i<spjgs.length;i++){
			if(spjgs[i].checked){
				spjg=spjgs[i].value;
				break;
			}
		}
		var spyj=$("#advice").val();
		var data=xzcptable();
		if(spjg==undefined){
			return layer.alert("审批结果不能为空",{icon:7});
		}
		if(spyj==""){
			return layer.alert("审批意见不能为空",{icon:7});
		}
		var form=document.getElementById('myForm');
		form.action=url+"processingIngredients/processingIngredientsSubmit.do?task_Id="+taskId+"&out_come="+spjg+"&advice_="+spyj+"&jgpl="+data;
		form.submit(); 
	}
</script>
</body>
</html>