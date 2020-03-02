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
			<input type="hidden" value="${material.raw_Material_Id }" id="raw_Material_Id">
			 <div class="layui-form-item" style="margin-top: 5%;margin-left:170px;">
			    <label class="layui-form-label" style="width: 120px;">材料名称</label>
			    <div class="layui-input-block">
			      <input type="text" name="material_Name" lay-verify="material_Name" autocomplete="off" placeholder="产品名称" class="layui-input bj" style="width:50%" value="${material.material_Name}" disabled="">
			    </div>
			 </div>
			 <div class="layui-form-item" style="margin-left:170px;">
			    <label class="layui-form-label" style="width: 120px;">生产数量</label>
			    <div class="layui-input-block">
			      <input type="text" name="" lay-verify="" autocomplete="off" placeholder="产品名称" class="layui-input bj" style="width:50%" value="${material.numbers}" id="numbers" disabled="">
			    </div>
			 </div>
			 <div class="layui-form-item" style="margin-left:170px;">
			    <label class="layui-form-label" style="width: 120px;">已入库数量</label>
			    <div class="layui-input-block">
			      <input type="text"  lay-verify="" autocomplete="off" placeholder="" class="layui-input bj" style="width:50%" value="${rkNumber}" id="yrksl" disabled="">
			    </div>
			 </div>
			  <div class="layui-form-item" style="text-align: left;margin-left:290px;">
			 	<div id="test4" class="demo-transfer"></div>
			  </div>
			  <div class="layui-form-item layui-form-text" style="margin-left:170px;">
				  	<label class="layui-form-label" style="width: 120px;">入库</label>
					  <div class="layui-input-block">
						<table class="table table-bordered" id="khlxrs" style="width:50%">
						  <thead>
						    <tr>
						      <th scope="col" style="text-align: center;width: 10%">序号</th>
						      <th scope="col" style="text-align: center;width: 30%">库位</th>
						      <th scope="col" style="text-align: center;width: 15%">入库数量</th>
						      <th scope="col" style="text-align: center;width: 45%">备注</th>
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
  allStock(transfer);
  //创建一个编辑器
  var editIndex = layedit.build('LAY_demo_editor');
});

	//ajax实现查询所有的成品库存
	function allStock(transfer){
			$.ajax({
				type : "post",
				url : "<c:url value='/matStock/queryAllStock.do'/>",
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
					    ,title: ['材料库位', '入库']
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
		$.ajax({
			type : "post",
			url : "<c:url value='/matStock/queryChoseStock.do'/>",
			async : false,
			dataType : 'json',
			data:{"strs":strs},
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
							"<td><input type='text' class='form-control bj' aria-label='' aria-describedby='' value='"+msg[i].stock+"' disabled=''> <input type='hidden' name='stock_Id' value='"+msg[i].material_Id+"'></td>"+
							"<td><input type='text' class='form-control' aria-label='' aria-describedby=''  name='rksl' value='0' onblur='rkNumberChange(this)'></td>"+
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
				var stodkId=$('input[name="stock_Id"]')[j-1].value;
				//若字符组中的主键等于表格中的隐藏主键则删除
				if(stockdms[i]==stodkId){
					var tr=$('input[name="stock_Id"]')[j-1].parentElement.parentElement;
					var tbody=tr.parentElement;
					tbody.removeChild(tr);
					break;
				}
			}
		}
	}

	//入库数量行改变事件
	function rkNumberChange(obj){
		//获得当前表格行索引
		var index=obj.parentElement.parentElement.rowIndex;
		//货物当前表格
		var tables=$('#khlxrs');
		//获得表格所有行
		var rows=tables[0].rows;
		//遍历表格
		var totalRksl=0;
		for(var j=1;j<rows.length;j++){
			//获得入库数量
			var rksls=$('input[name="rksl"]')[j-1].value*1;
			totalRksl=totalRksl*1+rksls;
		}
		//获得该成品的剩余数量
		var scsl=$('#numbers').val()*1;
		var yrksl=$('#yrksl').val()*1;
		var result=scsl-yrksl;
		if(totalRksl>result){
			$('input[name="rksl"]')[index-1].value=0;
			$('#submit').hide();
			return layer.alert("入库数量不得大于该成品的剩余量!",{icon:7});
		}else{
			$('#submit').show();
		}
	}


	//ajax提交表单
	function saveSubmit(){
		//获得材料主键
		var materialId=$('#raw_Material_Id').val();
		//获得表格中
		var tables=$('#khlxrs');
		//获得表格所有行
		var rows=tables[0].rows;
		if(rows.length==1){
			return layer.alert("当前不存在入库材料不允许提交!",{icon:7});
		}
		//创建入库数组
		var rkArray=new Array();
		//遍历表格
		for(var j=1;j<rows.length;j++){
			//获得库存主键
			var stockId=$('input[name="stock_Id"]')[j-1].value;
			//获得填写的入库数量
			var rknumber=$('input[name="rksl"]')[j-1].value;
			//获得备注
			var remarks=$('input[name="remarks"]')[j-1].value;
			//创建入库对象
			var rk=new Object();
			rk.stock_Id=stockId;
			rk.materialId=materialId;
			rk.rknumber=rknumber;
			rk.remarks=remarks;
			rkArray.push(rk);
		}
		$.ajax({
			type : "post",
			url : "<c:url value='/matStock/saveMaterialStock.do'/>",
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
		                layer.close(index); //再执行关闭
				} 
			}
		});
	}
</script>
</body>
</html>