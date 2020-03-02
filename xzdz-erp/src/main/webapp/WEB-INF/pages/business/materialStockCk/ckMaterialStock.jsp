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
<title>材料库存</title>
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
<body  style="width:100%;padding:0px; margin:0px;text-align: center;">
	<form class="layui-form">
		<div style="width:1280px;height:auto;padding:0px; margin:0 auto;" id="main">
			<input type="hidden" id="url" value='<c:url value="/"/>'>
			<input type="hidden" id="dms" name="hwdms">
			<input type="hidden" value="${material.raw_Material_Id}" id="raw_Material_Id">
			 <div class="layui-form-item" style="margin-top: 5%;margin-left:170px;">
			    <label class="layui-form-label" style="width: 120px;">材料名称</label>
			    <div class="layui-input-block">
			      <input type="text" name="material_Name" lay-verify="material_Name" autocomplete="off" placeholder="产品名称" class="layui-input bj" style="width:50%" value="${material.material_Name}" disabled="">
			    </div>
			 </div>
			 <div class="layui-form-item" style="margin-left:170px;">
			    <label class="layui-form-label" style="width: 120px;">采购数量</label>
			    <div class="layui-input-block">
			      <input type="text" name="" lay-verify="" autocomplete="off" placeholder="产品名称" class="layui-input bj" style="width:50%" value="${material.numbers}" id="numbers" disabled="">
			    </div>
			 </div>
			 
			  <div class="layui-form-item" style="text-align: left;margin-left:290px;">
			 	<div id="test4" class="demo-transfer"></div>
			  </div>
			  <div class="layui-form-item layui-form-text" style="margin-left:170px;">
				  	<label class="layui-form-label" style="width: 120px;">出库</label>
					  <div class="layui-input-block">
						<table class="table table-bordered" id="khlxrs" style="width:50%">
						  <thead>
						    <tr>
						      <th scope="col" style="text-align: center;width: 10%">序号</th>
						      <th scope="col" style="text-align: center;width: 30%">材料名称</th>
						      <th scope="col" style="text-align: center;width: 15%">库存量</th>
						      <th scope="col" style="text-align: center;width: 15%">出库数量</th>
						      <th scope="col" style="text-align: center;width: 30%">备注</th>
						    </tr>
						  </thead>
						  <tbody>
						  </tbody>
						</table>
					</div>
			</div>
	 </div>
	 <div class="layui-form-item" style="text-align: center;" id="submit">
		    <div class="layui-input-block">
		      <button class="layui-btn" lay-submit="" lay-filter="" style="width:25%;margin-top:10px;margin-left:-315px;" onclick="saveSubmit()" type="button">立即提交</button>
		    </div>
		</div>
 </form>
<script src="../bootstrap-3.3.7-dist/js/bootstrap.js"></script>
<script src="../layui-v2.5.5/layui/layui.js" charset="utf-8"></script>
<script>
layui.use(['form', 'layedit', 'laydate','transfer','util'], function(){
  var form = layui.form
  ,layer = layui.layer
  ,layedit = layui.layedit
  var url=$('#url').val();
  var transfer = layui.transfer;
  var util = layui.util;
  form.render();
  allyrkMatStock(transfer);
  //创建一个编辑器
  var editIndex = layedit.build('LAY_demo_editor');
});

	//ajax实现查询该材料入库的库位
	function allyrkMatStock(transfer){
		var materialId=$('#raw_Material_Id').val();
			$.ajax({
				type : "post",
				url : "<c:url value='/materialStockCk/queryAllStock.do'/>",
				async : false,
				dataType : 'json',
				data:{"materialId":materialId},
				error : function() {
					alert("出错");
				},
				success : function(msg) {
					//显示搜索框
					  transfer.render({
					    elem: '#test4'
					    ,data: msg
					    ,title: ['材料库位', '出库']
					    ,showSearch: true
					    ,onchange: function(data, index){
					    	//左边 索引为0 右边为1
					    	if(index==0){
					    		//遍历结果集
					    		$('#dms').val("")
					    		var dms=$('#dms').val();
					    		for(var i=0;i<data.length;i++){
					    			//获得穿梭的数据代码
						    		var hwdm=data[i].value;
					    			//拼接id字符串
										 dms=dms+","+hwdm;
									 }
					    		$('#dms').val(dms);
					    		CreateTable(dms);
					    	}else{
					    		$('#dms').val("")
					    		var dms=$('#dms').val();
					    		for(var i=0;i<data.length;i++){
					    			//获得穿梭的数据代码
						    		var hwdm=data[i].value;
						    		//拼接id字符串
									dms=dms+","+hwdm;
					    		}
					    		var dm=dms.substring(1, dms.length);
					    		removeRow(dm);
					    	}
					      }
					  })
				}
			});
			
	}

	//ajax根据选择的库存动态展现列表
	var index=0;
	function CreateTable(strs){
		//获得当前库位主键
		var material_Id=$('#raw_Material_Id').val();
		$.ajax({
			type : "post",
			url : "<c:url value='/materialStockCk/queryChoseProduct.do'/>",
			async : false,
			dataType : 'json',
			data:{"strs":strs,"material_Id":material_Id},
			error : function() {
				alert("出错");
			},
			success : function(msg) {
				var tables=$('#khlxrs');
				//遍历结果集
				for(var i=0;i<msg.length;i++){
					index++;
					var addtr = $("<tr>"+
							"<th scope='row' style='text-align: center;line-height:38px;'>"+index+"</th>"+
							"<td><input type='text' class='form-control bj' aria-label='' aria-describedby='' value='"+msg[i].stock+"' disabled=''> <input type='hidden' name='materialId' value='"+msg[i].clId+"'></td>"+
							"<td><input type='text' class='form-control bj' aria-label='' aria-describedby=''  name='rksl' value='"+msg[i].kcl+"' disabled=''><input type='hidden' name='kwId' value='"+msg[i].material_Id+"'></td>"+
							"<td><input type='text' class='form-control' aria-label='' aria-describedby=''  name='cksl' value='0' onblur='ckNumberChange(this)'></td>"+
							"<td><input type='text' class='form-control' aria-label='' aria-describedby=''  name='remarks'></td>"+
							"</tr>");
					 addtr.appendTo(tables);    
				}
			}
		});
	}

	//移除表格某一行
	function removeRow(str){
		//将字符窜转换为数组
		var  stockdms = str.split(",");
		//货物当前表格
		var tables=$('#khlxrs');
		//获得表格所有行
		var rows=tables[0].rows;
		//遍历该数组
		for(var i=0;i<stockdms.length;i++){
			//遍历表格
			for(var j=1;j<rows.length;j++){
				//主键
				var recId=$('input[name="materialId"]')[j-1].value;
				//若字符组中的主键等于表格中的隐藏主键则删除
				if(stockdms[i]==recId){
					var tr=$('input[name="materialId"]')[j-1].parentElement.parentElement;
					var tbody=tr.parentElement;
					tbody.removeChild(tr);
					break;
				}
			}
		}
	}

	//出库数量行改变事件
	function ckNumberChange(obj){
		//获得当前表格行索引
		var index=obj.parentElement.parentElement.rowIndex;
		//获得当前行的入库数量
		var rksl=$('input[name="rksl"]')[index-1].value*1;
		//获得当前行的出库数量
		var cksl=obj.value;
		if(cksl>rksl){
			obj.value=0;
			$('#submit').hide();
			return layer.alert("出库数量不得大于该材料的库存量!",{icon:7});
		}
		if(cksl<0){
			obj.value=0;
			$('#submit').hide();
			return layer.alert("出库数量不得小于0!",{icon:7});
		}else{
			$('#submit').show();
		}
	}


	//ajax提交表单
	function saveSubmit(){
		//获得表格中
		var tables=$('#khlxrs');
		//获得表格所有行
		var rows=tables[0].rows;
		if(rows.length==1){
			return layer.alert("当前不存在出库材料不允许提交!",{icon:7});
		}
		//创建入库数组
		var rkArray=new Array();
		//遍历表格
		for(var j=1;j<rows.length;j++){
			//获得成品主键
			var materialId=$('input[name="materialId"]')[j-1].value;
			//获得库位主键
			var stock_Id=$('input[name="kwId"]')[j-1].value;
			//获得填写的出库数量
			var rknumber=$('input[name="cksl"]')[j-1].value;
			//获得备注
			var remarks=$('input[name="remarks"]')[j-1].value;
			//创建入库对象
			var rk=new Object();
			rk.stock_Id=stock_Id;
			rk.materialId=materialId;
			rk.rknumber=rknumber;
			rk.remarks=remarks;
			rkArray.push(rk);
		}
		$.ajax({
			type : "post",
			url : "<c:url value='/clckStock/saveMatStock.do'/>",
			async : false,
			contentType :"application/json;charsetset=UTF-8",//必须
			dataType : 'json',
			data:JSON.stringify(rkArray),
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