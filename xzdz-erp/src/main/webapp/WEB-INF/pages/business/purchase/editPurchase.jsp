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
<title>编辑采购订单</title>
<link rel="stylesheet" href="../layui-v2.5.5/layui/css/layui.css">
<link rel="stylesheet"
	href="../bootstrap-3.3.7-dist/css/bootstrap.min.css">
<link rel="stylesheet"
	href="../bootstrap-3.3.7-dist/css/bootstrap-theme.min.css">
<script src="../jquery/jquery-3.3.1.js"></script>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@page isELIgnored="false"%>
<style>
.bj {
	background-color: #F0F0F0
}
</style>
</head>
<body
	style="width: 100%; padding: 0px; margin: 0px; text-align: center;">
	<div style="width: 1280px; height: auto; padding: 0px; margin: 0 auto;"
		id="main">
		<form class="layui-form" action='<c:url value=""/>' method="post">
			<input type="hidden" id="url" value='<c:url value="/"/>'> <input
				type="hidden" value="${purchaseOrder.sales_Contract_Id}" id="xshtId">
			<input type="hidden" value="${taskId}" id="taskId"> <input
				type="hidden" value="${purchaseOrder.supplier}" id="ghdw"> <input
				type="hidden" value="${purchaseList.size()}" id="khlxrSize">
			<input type="hidden" value="${purchaseOrder.pur_Order_Id}"
				id="cghtId"> <input type="hidden" id="fjsx" name="fjsx">


			<div class="layui-form-item" style="margin-top: 3%;">
				<label class="layui-form-label" style="width: 148px;">合同名称</label>
				<div class="layui-input-block">
					<input type="text" id="purchaseOrderName"
						lay-verify="purchaseOrderName" autocomplete="off"
						placeholder="合同名称" class="layui-input" style="width: 72%"
						value="${purchaseOrder.purchaseOrderName}">
				</div>
			</div>

			<div class="layui-form-item">
				<div class="layui-inline" style="left: -225px;">
					<label class="layui-form-label" style="width: 100px;">供货单位</label>
					<div class="layui-input-inline"
						style="text-align: left; width: 300px;">
						<select name="supplier" id="supplier" lay-filter="supplier"
							lay-verify="supplier">
							<option value="" selected="selected">请选择供货单位</option>
						</select>
					</div>
				</div>

				<div class="layui-inline" style="left: -5px;">
					<label class="layui-form-label" style="width: 100px;">订单编号</label>
					<div class="layui-input-inline">
						<input type="text" id="pur_Code" lay-verify="pur_Code"
							autocomplete="off" class="layui-input bj" style="width: 200px;"
							disabled="" value="${purchaseOrder.pur_Code}">
					</div>
				</div>
			</div>

			<div class="layui-form-item">

				<div class="layui-inline" style="left: -103px;">
					<label class="layui-form-label" style="width: 100px;">电话</label>
					<div class="layui-input-inline">
						<input type="text" id="telPhone" lay-verify="telPhone"
							autocomplete="off" class="layui-input bj" style="width: 200px;"
							disabled="">
					</div>
				</div>

				<div class="layui-inline" style="left: -100px;">
					<label class="layui-form-label" style="width: 100px;">收件人</label>
					<div class="layui-input-inline">
						<input type="text" id="sjr" lay-verify="sjr" autocomplete="off"
							class="layui-input" style="width: 200px;"
							value="${purchaseOrder.sjr}">
					</div>
				</div>

				<div class="layui-inline" style="left: -126px;">
					<label class="layui-form-label" style="width: 139px;">订购日期</label>
					<div class="layui-input-inline">
						<input type="text" name="pur_Date" id="pur_Date"
							lay-verify="pur_Date" placeholder="yyyy-mm-dd" autocomplete="off"
							class="layui-input" style="width: 200px;"
							value="${purchaseOrder.dgrq}">
					</div>
				</div>
			</div>

			<div class="layui-form-item layui-form-text">
				<label class="layui-form-label" style="width: 133px;">加工配料项</label>
				<div class="layui-input-block" style="left: 10px;">
					<table class="table table-bordered" id="jgpl" style="width: 100%">
						<thead>
							<tr>
								<th scope="col" style="text-align: center; width: 7%">序号</th>
								<th scope="col" style="text-align: center; width: 26%">材料名称</th>
								<th scope="col" style="text-align: center; width: 27%">规格型号</th>
								<th scope="col" style="text-align: center; width: 28%">物料Id</th>
								<th scope="col" style="text-align: center; width: 18%">采购数量</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${orders}" var="o">
								<tr>
									<td style="text-align: center;"></td>
									<td><input type='text' class='form-control bj'
										aria-label='' aria-describedby='' disabled=""
										value='${o.materialName}'></td>
									<td><input type='text' class='form-control bj'
										aria-label='' aria-describedby='' disabled=""
										value='${o.specification_Type}'></td>
									<td><input type='text' class='form-control bj'
										aria-label='' aria-describedby='' disabled=""
										value='${o.materielId}'></td>
									<td><input type='text' class='form-control bj'
										aria-label='' aria-describedby='' disabled=""
										value='${o.cgsl }'></td>

								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>

			<div class="layui-form-item layui-form-text">
				<label class="layui-form-label" style="width: 155px;">材料采购项</label>
				<br>
				<div class="layui-input-block"
					style="text-align: left; left: -35px; top: 10px;">
					<button type="button" class="layui-btn layui-btn-normal"
						onclick="addRow()">
						<i class="layui-icon">&#xe608;</i>新增一行
					</button>
				</div>
				<div class="layui-input-block" style="top: 15px; left: 10px;">
					<table class="table table-bordered" id="khlxrs"
						style="width: 1170px;">
						<thead>
							<tr>
								<th scope="col" style="text-align: center; width: 100px;">序号</th>
								<th scope="col" style="text-align: center; width: 250px;">品名</th>
								<th scope="col" style="text-align: center; width: 250px;">型号</th>
								<th scope="col" style="text-align: center; width: 250px;">物料Id</th>
								<th scope="col" style="text-align: center; width: 100px;">单位</th>
								<th scope="col" style="text-align: center; width: 150px;">数量</th>
								<th scope="col" style="text-align: center; width: 150px;">单价</th>
								<th scope="col" style="text-align: center; width: 150px;">金额</th>
								<th scope="col" style="text-align: center; width: 250px;">交货日期</th>
								<th scope="col" style="text-align: center; width: 200px;">图号</th>
								<th scope="col" style="text-align: center; width: 300px;">备注</th>
								<th scope="col" style="text-align: center; width: 430px;">操作</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${purchaseList}" var="p">
								<tr>
									<td scope='row' style='text-align: center; line-height: 38px;'></td>
									<td>
										<input type="hidden" name="pur_Id" value="${p.pur_Id}">
										<input type='text' class='form-control' aria-label='' aria-describedby='' name='pro_Name' value="${p.pro_Name}" title="${p.pro_Name}">
									</td>
									<td>
										<input type='text' class='form-control bj' aria-label='' aria-describedby='' name='model'   value="${p.model}" title="${p.model}" readonly='readonly'>
									</td>
									<td>
										<input type='text' class='form-control bj' aria-label='' aria-describedby='' name='materielId' readonly='readonly' value="${p.materielId}"title="${p.materielId}">
									</td>
									<td>
										<input type='text' class='form-control' aria-label='' aria-describedby='' name='company' value="${p.company}" title="${p.company}">
									</td>
									<td>
										<input type='text' class='form-control' aria-label='' aria-describedby='' name='sl' onblur='jejs(this)'value="${p.sl}" title="${p.sl}">
									</td>
									<td>
										<input type='text' class='form-control' aria-label='' aria-describedby='' name='price' onblur='jejs(this)' value="${p.price}" title="${p.price}">
									</td>
									<td>
										<input type='text' class='form-control bj'aria-label='' aria-describedby='' name='zje' readonly='readonly' value="${p.zje}" title="${p.zje}">
									</td>
									<td>
										<input type='date' class='form-control' name='delivery_date' value="${p.delivery_date}" title="${p.delivery_date}">
									</td>
									<td>
										<input type='text' class='form-control' aria-label='' aria-describedby='' name='map_Number' value="${p.map_Number}" title="${p.map_Number }">
									</td>
									<td>
										<input type='text' class='form-control' aria-label='' aria-describedby='' name='bz' value="${p.bz}" title="${p.bz}">
									</td>
									<td>
										<button type='button'class='layui-btn layui-btn-danger' title='删除一行' onclick='deleteData(${p.pur_Id})'>
											<i class='layui-icon'>&#xe640;</i>
										</button>
									</td>
								</tr>
							</c:forEach>
							<tr>
								<td></td>
								<td>合计总金额</td>
								<td colspan="10">
									<input type='text' class='form-control bj' aria-label='' aria-describedby='' style="width: 165px;" disabled="" id="totalprice">
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>

			<div class="layui-form-item layui-form-text">
				<label class="layui-form-label" style="width: 120px;">备注</label>
				<div class="layui-input-block">
					<textarea placeholder="请输入内容" lay-verify="" id="remarks"
						class="layui-textarea" style="width: 76.5%">${purchaseOrder.remarks}</textarea>
				</div>
			</div>

			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 120px;">1.</label>
				<div class="layui-input-block">
					<input type="text" id="order_One" lay-verify="" autocomplete="off"
						placeholder="" class="layui-input" style="width: 76.5%"
						value="${purchaseOrder.order_One}">
				</div>
			</div>

			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 120px;">2.</label>
				<div class="layui-input-block">
					<input type="text" id="order_Two" lay-verify="" autocomplete="off"
						placeholder="" class="layui-input" style="width: 76.5%"
						value="${purchaseOrder.order_Two}">
				</div>
			</div>

			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 120px;">3.</label>
				<div class="layui-input-block">
					<input type="text" id="order_Three" lay-verify=""
						autocomplete="off" placeholder="" class="layui-input"
						style="width: 76.5%" value="${purchaseOrder.order_Three}">
				</div>
			</div>

			<!--附件 -->
			<div class="layui-upload" id="fj">
				<button type="button" class="layui-btn layui-btn-normal"
					id="testList" style="margin-left: -91.5%">选择多文件</button>
				<div class="layui-upload-list">
					<table class="layui-table" style="width: 100%;">
						<thead>
							<tr>
								<th style="text-align: center;">文件名</th>
								<th style="text-align: center;">大小</th>
								<th style="text-align: center;">状态</th>
								<th style="text-align: center;">操作</th>
							</tr>
						</thead>
						<tbody id="demoList"></tbody>
					</table>
				</div>
				<button type="button" class="layui-btn" id="testListAction"
					style="margin-left: -91.5%">开始上传</button>
			</div>

			<div class="layui-form-item" style="text-align: center;">
				<div class="layui-input-block">
					<button class="layui-btn" lay-submit="" lay-filter=""
						style="width: 25%; margin-top: 10px; margin-left: -315px;"
						onclick="saveContract()" type="button">立即提交</button>
				</div>
			</div>
		</form>
	</div>
	<script src="../bootstrap-3.3.7-dist/js/bootstrap.js"></script>
	<script src="../layui-v2.5.5/layui/layui.js" charset="utf-8"></script>
	<script>
layui.use(['form', 'layedit', 'laydate','upload'], function(){
  var form = layui.form
  ,layer = layui.layer
  ,layedit = layui.layedit
  ,laydate = layui.laydate
  ,upload = layui.upload;
  var url=$('#url').val();
  form.render();
  allSuppplier(form);
  loadGhdw(form);
  khlxrxh();
  jgplxh();
  checkFjPermission();
  //日期
  laydate.render({
    elem: '#pur_Date'
    ,format: 'yyyy-MM-dd'
  });
 
  //创建一个编辑器
  var editIndex = layedit.build('LAY_demo_editor');

	//ajax实现下拉需方单位带出客户的相关属性
	form.on('select(supplier)', function(data){
		//获得项目信息主键
		var supplier_Id=data.value;
		if(supplier_Id==""||supplier_Id==undefined){
			$('#telPhone').val("");//电话
			return;
		}else{
		//ajax根据Id查询供应商并设置其它属性值
			$.ajax({
				type : "post",
				url : "<c:url value='/purchase/supplier.do'/>",
				async : false,
				dataType : 'json',
				data :{"supplier_Id":supplier_Id},
				error : function() {
					alert("出错");
				},
				success : function(msg) {
					$('#telPhone').val(msg.supplier.phone);//电话
				}
			});
		}
	});

	//多文件列表示例
	  var fjsx=$('#fjsx').val();
	  var demoListView = $('#demoList')
	  ,uploadListIns = upload.render({
	    elem: '#testList'
	    ,url: '<c:url value="/sales/upload.do"/>'
	    ,accept: 'file'
	    ,multiple: true
	    ,auto: false
	    ,bindAction: '#testListAction'
	    ,choose: function(obj){   
	      var files = this.files = obj.pushFile(); //将每次选择的文件追加到文件队列
	      //读取本地文件
	      obj.preview(function(index, file, result){
	        var tr = $(['<tr id="upload-'+ index +'">'
	          ,'<td>'+ file.name +'</td>'
	          ,'<td>'+ (file.size/1014).toFixed(1) +'kb</td>'
	          ,'<td>等待上传</td>'
	          ,'<td>'
	            ,'<button class="layui-btn layui-btn-xs demo-reload layui-hide">重传</button>'
	            ,'<button class="layui-btn layui-btn-xs layui-btn-danger demo-delete">删除</button>'
	          ,'</td>'
	        ,'</tr>'].join(''));
	        
	        //单个重传
	        tr.find('.demo-reload').on('click', function(){
	          obj.upload(index, file);
	        });
	        
	        //删除
	        tr.find('.demo-delete').on('click', function(){
	          delete files[index]; //删除对应的文件
	          tr.remove();
	          uploadListIns.config.elem.next()[0].value = ''; //清空 input file 值，以免删除后出现同名文件不可选
	        });
	        
	        demoListView.append(tr);
	      });
	    }
	    ,done: function(res, index, upload){
	      if(res.code == 0){ //上传成功
	        var tr = demoListView.find('tr#upload-'+ index)
	        ,tds = tr.children();
	        tds.eq(2).html('<span style="color: #5FB878;">上传成功</span>');
	        tds.eq(3).html(''); //清空操作
	        //将附件属性拼接字符串提交至后端
	         var fj=res.data;
			 //将json串转换为字符串
			 var str = JSON.stringify(fj);
	        if(undefined!=fjsx){
				 fjsx=fjsx+","+str;
				 $('#fjsx').val(fjsx);
			 }else{
				 fjsx=str;
				 $('#fjsx').val(fjsx);
			 }
	        return delete this.files[index]; //删除文件队列已经上传成功的文件
	      }
	      this.error(index, upload);
	    }
	    ,error: function(index, upload){
	      var tr = demoListView.find('tr#upload-'+ index)
	      ,tds = tr.children();
	      tds.eq(2).html('<span style="color: #FF5722;">上传失败</span>');
	      tds.eq(3).find('.demo-reload').removeClass('layui-hide'); //显示重传
	    }
	  });
	  fjPageLoad();
});


	//表格新增一行
	var index=0;
	function addRow(){
		var length=$("#khlxrs tbody").find("tr").length;
		//获取表格中的第一行
		var fristRow;
		for(var i=0;i<length;i++){
			var text="	合计总金额	";
			if($("#khlxrs tbody").find("tr")[i].innerText==text){
				fristRow=$("#khlxrs tbody").find("tr:eq('"+i+"')");
				break;
			}
		}
		//获得表格长度
		var khlxrSize=$('#khlxrSize').val();
		if("khlxrSize"!=""){
			khlxrSize++;
			$('#khlxrSize').val(khlxrSize);
			var tables=$('#khlxrs');
			var addtr = $("<tr>"+
					"<th scope='row' style='text-align: center;line-height:38px;'>"+khlxrSize+"</th>"+
					"<td><input type='hidden' name='pur_Id' value=''><input type='text' class='form-control' aria-label='' aria-describedby=''  name='pro_Name'></td>"+
					"<td><input type='text' class='form-control bj' aria-label='' aria-describedby=''  name='model' readonly='readonly'></td>"+
					"<td><input type='text' class='form-control bj' aria-label='' aria-describedby=''  name='materielId' readonly='readonly'></td>"+
					"<td><input type='text' class='form-control' aria-label='' aria-describedby=''  name='company'></td>"+
					"<td><input type='text' class='form-control' aria-label='' aria-describedby=''  name='sl' onblur='jejs(this)'></td>"+
					"<td><input type='text' class='form-control' aria-label='' aria-describedby=''  name='price' onblur='jejs(this)'></td>"+
					"<td><input type='text' class='form-control bj' aria-label='' aria-describedby=''  name='zje'readonly='readonly'></td>"+
					"<td><input type='date' class='form-control' name='delivery_date'></td>"+
					"<td><input type='text' class='form-control' aria-label='' aria-describedby=''  name='map_Number'></td>"+
					"<td><input type='text' class='form-control' aria-label='' aria-describedby=''  name='bz'></td>"+
					"<td><button type='button' class='layui-btn layui-btn-normal' onclick='MaterielIDInfo(this)'>物料ID详情</button> <button type='button' class='layui-btn layui-btn-danger' title='删除一行' onclick='deleteTrRow(this)'><i class='layui-icon'>&#xe640;</i></button></td>"+
					"</tr>");
		 		fristRow.before(addtr);  
		}else{
			index++;
			var tables=$('#khlxrs');
			var addtr = $("<tr>"+
					"<th scope='row' style='text-align: center;line-height:38px;'>"+index+"</th>"+
					"<td><input type='hidden' name='pur_Id' value=''><input type='text' class='form-control' aria-label='' aria-describedby=''  name='pro_Name'></td>"+
					"<td><input type='text' class='form-control bj' aria-label='' aria-describedby=''  name='model' readonly='readonly'></td>"+
					"<td><input type='text' class='form-control bj' aria-label='' aria-describedby=''  name='materielId' readonly='readonly'></td>"+
					"<td><input type='text' class='form-control' aria-label='' aria-describedby=''  name='company'></td>"+
					"<td><input type='text' class='form-control' aria-label='' aria-describedby=''  name='sl' onblur='jejs(this)'></td>"+
					"<td><input type='text' class='form-control' aria-label='' aria-describedby=''  name='price' onblur='jejs(this)'></td>"+
					"<td><input type='text' class='form-control bj' aria-label='' aria-describedby=''  name='zje'readonly='readonly'></td>"+
					"<td><input type='date' class='form-control' name='delivery_date'></td>"+
					"<td><input type='text' class='form-control' aria-label='' aria-describedby=''  name='map_Number'></td>"+
					"<td><input type='text' class='form-control' aria-label='' aria-describedby=''  name='bz'></td>"+
					"<td><button type='button' class='layui-btn layui-btn-normal' onclick='MaterielIDInfo(this)'>物料ID详情</button> <button type='button' class='layui-btn layui-btn-danger' title='删除一行' onclick='deleteTrRow(this)'><i class='layui-icon'>&#xe640;</i></button></td>"+
					"</tr>");
			fristRow.before(addtr); 
		}     
	} 



	function deleteData(pur_Id){
		//ajax实现删除数据
		layer.confirm('您确定要删除该数据吗？此操作将不可逆转!!!', {
			  btn: ['确定','取消'], //按钮
			  title:'提示'},function(index){
				//删除数据
				  $.ajax({  
					    type: "post",  
					    url:  "<c:url value='/purchase/deletePuraseListById.do'/>",
					    dataType: 'json',
					    async:false,
					    data:{"pur_Id":pur_Id},
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

	//表格删除一行
	function deleteTrRow(tr){
	    $(tr).parent().parent().remove();
	    index--;
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

	//ajax加载所有的供货单位
	function  allSuppplier(form){
		$.ajax({
			type : "post",
			url : "<c:url value='/purchase/allSupplier.do'/>",
			async : false,
			dataType : 'json',
			error : function() {
				alert("出错");
			},
			success : function(msg) {
				for (var i = 0; i < msg.length; i++) {
					$("#supplier").append(
							"<option value='"+msg[i].supplier_Id+"'>"+ msg[i].supplier_Name +"</option>");
				}
				form.render('select');
			}
		});
	}

	//自动加载供货单位
	function loadGhdw(form){
		//获得需求方代码
		var ghdw=$('#ghdw').val();
		//遍历客户下拉选
		var ghdws=$('#supplier').find('option');
		for(var i=0;i<ghdws.length;i++){
			if(ghdws[i].value==ghdw){
				ghdws[i].setAttribute("selected",'true');
				break;
			}
		}
		//ajax根据Id查询供应商并设置其它属性值
		$.ajax({
			type : "post",
			url : "<c:url value='/purchase/supplier.do'/>",
			async : false,
			dataType : 'json',
			data :{"supplier_Id":ghdw},
			error : function() {
				alert("出错");
			},
			success : function(msg) {
				$('#telPhone').val(msg.supplier.phone);//电话
			}
		});
		form.render('select');
	}


	

	//设置金额自动计算
	function jejs(obj){
		//获得当前表格行索引
		var index=obj.parentElement.parentElement.rowIndex;
		//获得数量
		var sl=$('input[name="sl"]')[index-1].value;
		//获得单价
		var dj=$('input[name="price"]')[index-1].value;
		if(sl!=""&&dj!=""){
			//设置金额
			$('input[name="zje"]')[index-1].value=(sl*1)*(dj*1);
		}
		if(sl==""||dj==""){
			//设置金额
			$('input[name="zje"]')[index-1].value="";
		}
		var data=$('input[name="zje"]');
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

	

	function jgplxh(){
		var len = $('#jgpl tr').length;
	    for(var i = 1;i<=len-1;i++){
	        $('#jgpl tr:eq('+i+') td:first').text(i);
	    }
	}

	function khlxrxh(){
		var len = $('#khlxrs tr').length;
		var totalPrice=0;
	    for(var i = 1;i<len-1;i++){
	        $('#khlxrs tr:eq('+i+') td:first').text(i);
	    }
	    var prices=$('input[name="zje"]');
	    for(var i=0;i<prices.length;i++){
	    	totalPrice=(totalPrice*1)+(prices[i].value*1);
		}
		$('#totalprice').val(totalPrice);
	}

	//新增采购合同
	function saveContract(){
		//货物当前表格
		var tables=$('#khlxrs');
		//获得表格所有行
		var rows=tables[0].rows;
		//遍历表格
		for(var i=1;i<rows.length;i++){
			//物料Id
			if($('input[name="materielId"]')[i-1]!=undefined){
				var wlId=$('input[name="materielId"]')[i-1].value;
				if(wlId==""){
					return 	layer.alert("第"+i+"行物料Id为空，请核对规格型号!!!",{icon:7});
				}
			}
		}
		//创建采购合同对象
		var cght=new Object();
		//获得合同主键
		var cghtId=$('#cghtId').val();
		//获得合同名称
		var htmc=$('#purchaseOrderName').val();
		if(htmc==""){
			return layer.alert("合同名称不能为空!",{icon:7});
		}
		//获得供货单位
		var ghdw=$('#supplier').val();
		if(ghdw==""){
			return layer.alert("供货单位不能为空!",{icon:7});
		}
		//合同编号
		var htbh=$('#pur_Code').val();
		//收件人
		var sjr=$('#sjr').val();
		//订购日期
		var dgrq=$('#pur_Date').val();
		//备注
		var remarks=$('#remarks').val();
		//条款一
		var order_One=$('#order_One').val();
		//条款二
		var order_Two=$('#order_Two').val();
		//条款三
		var order_Three=$('#order_Three').val();
		//销售合同主键
		var xshtId=$('#xshtId').val();
		//任务Id
		var taskId=$('#taskId').val();
		//合同总金额
		var htzje=$('#totalprice').val();
		//附件
		var fjsx=$('#fjsx').val();
		cght.pur_Order_Id=cghtId;
		cght.purchaseOrderName=htmc;
		cght.pur_Code=htbh;
		cght.supplier=ghdw;
		cght.sjr=sjr;
		cght.pur_Date=dgrq;
		cght.remarks=remarks;
		cght.order_One=order_One;
		cght.order_Two=order_Two;
		cght.order_Three=order_Three;
		cght.sales_Contract_Id=xshtId;
		cght.taskId=taskId;
		cght.our_uint="";
		cght.qd_Date="";
		cght.gfqd_Date="";
		cght.sub_Date="";
		cght.gf_user="";
		cght.status="";
		cght.userId="";
		cght.is_Availability="";
		cght.approvalDm="";
		cght.task_Describe="";
		cght.totalPrice=htzje;
		cght.fjsx=fjsx;
		cght.is_wcsk="";
		$.ajax({
			type : "post",
			url : "<c:url value='/purchase/editPurchaseOrder.do'/>",
			async : false,
			contentType :"application/json;charsetset=UTF-8",//必须
			dataType : 'json',
			data:JSON.stringify(cght),
			error : function() {
				alert("出错");
			},
			success : function(data) {
				if(data.flag){
					saveContractOrder();
				}
			}
		});
	}


	//新增采购计划项
	function saveContractOrder(){
		//货物当前表格
		var tables=$('#khlxrs');
		//获得表格所有行
		var rows=tables[0].rows;
		//创建货物清单数组
		var orders=new Array();
		//遍历表格
		for(var i=0;i<rows.length;i++){
			if(i+1==rows.length-1){
				break
				//主键
				var pur_Id=$('input[name="pur_Id"]')[i].value;
				//品名
				var pm=$('input[name="pro_Name"]')[i].value;
				//型号
				var xh=$('input[name="model"]')[i].value;
				//物料Id
				var wlId=$('input[name="materielId"]')[i].value;
				//单位
				var dw=$('input[name="company"]')[i].value;
				//数量
				var sl=$('input[name="sl"]')[i].value;
				//单价
				var dj=$('input[name="price"]')[i].value;
				//金额
				var zje=$('input[name="zje"]')[i].value;
				//交货日期
				var jhrq=$('input[name="delivery_date"]')[i].value;
				//图号
				var th=$('input[name="map_Number"]')[i].value;
				//备注
				var bz=$('input[name="bz"]')[i].value;
				//销售合同主键
				var xshtId=$('#xshtId').val();
				//创建货物清单对象
				var order=new Object();
				order.pur_Id=pur_Id
				order.pro_Name=pm;
				order.model=xh;
				order.materielId=wlId;
				order.company=dw;
				order.sl=sl;
				order.price=dj;
				order.zje=zje;
				order.delivery_date=jhrq;
				order.map_Number=th;
				order.bz=bz;
				order.xshtdm=xshtId;
				orders.push(order);
			}else{
				//主键
				var pur_Id=$('input[name="pur_Id"]')[i].value;
				//品名
				var pm=$('input[name="pro_Name"]')[i].value;
				//型号
				var xh=$('input[name="model"]')[i].value;
				//物料Id
				var wlId=$('input[name="materielId"]')[i].value;
				//单位
				var dw=$('input[name="company"]')[i].value;
				//数量
				var sl=$('input[name="sl"]')[i].value;
				//单价
				var dj=$('input[name="price"]')[i].value;
				//金额
				var zje=$('input[name="zje"]')[i].value;
				//交货日期
				var jhrq=$('input[name="delivery_date"]')[i].value;
				//图号
				var th=$('input[name="map_Number"]')[i].value;
				//备注
				var bz=$('input[name="bz"]')[i].value;
				//销售合同主键
				var xshtId=$('#xshtId').val();
				//创建货物清单对象
				var order=new Object();
				order.pur_Id=pur_Id
				order.pro_Name=pm;
				order.model=xh;
				order.materielId=wlId;
				order.company=dw;
				order.sl=sl;
				order.price=dj;
				order.zje=zje;
				order.delivery_date=jhrq;
				order.map_Number=th;
				order.bz=bz;
				order.xshtdm=xshtId;
				orders.push(order);
			}
		}
		$.ajax({
			type : "post",
			url : "<c:url value='/purchase/saveOrEditPurchaseList.do'/>",
			async : false,
			contentType :"application/json;charsetset=UTF-8",//必须
			dataType : 'json',
			data:JSON.stringify(orders),
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


	function  fjPageLoad(){
		var row_Id=$('#cghtId').val();
		var demoListView = $('#demoList');
		$.ajax({
			type : "post",
			url : "<c:url value='/purchase/pageLoadFj.do'/>",
			async : false,
			dataType : 'json',
			data:{"row_Id":row_Id},
			error : function() {
				alert("出错");
			},
			success : function(msg) {
				for(var i=0;i<msg.length;i++){
					  var tr = $(['<tr id="upload-'+ i+1 +'">'
			          ,'<td>'+msg[i].fileName+'</td>'
			          ,'<td>'+msg[i].fileSize+'</td>'
			          ,'<td>已经上传</td>'
			          ,'<td>'
			            ,'<button class="layui-btn layui-btn-xs demo-reload layui-hide">重传</button>'
			            ,'<button class="layui-btn layui-btn-xs layui-btn-danger demo-delete" onclick="removeFile(this)" type="button">删除</button>'
			          ,'</td>'
			        ,'</tr>'].join(''));
				  demoListView.append(tr);
				}
			}
		});
	}

	function removeFile(obj){
		//获得当前表格行索引
		var index=obj.parentElement.parentElement.rowIndex;
		var demoListView = $('#demoList');
		var row_Id=$('#cghtId').val();
		//获得当前表格中的文件名
		var fileName=demoListView[0].rows[index-1].cells[0].innerText;
		layer.confirm('您确定要删除该附件么？', {
			  btn: ['确定','取消'], //按钮
			  title:'提示',icon:7},function(){
				  $.ajax({
						type : "post",
						url : "<c:url value='/purchase/removeFj.do'/>",
						async : false,
						dataType : 'json',
						data:{"row_Id":row_Id,"fileName":fileName},
						error : function() {
							alert("出错");
						},
						success : function(msg) {
							if(msg.flag){
								demoListView[0].rows[index-1].remove();
							    var rowNum = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
		                        location.reload();//刷新父页面，注意一定要在关闭当前iframe层之前执行刷新
		                        layer.close(rowNum); //再执行关闭
							}
						}
					});
			  }
			)
	}

	//附件权限验证
	function checkFjPermission(){
		var fp_Url="/purchaseOrder/upload.do";
	    //权限验证
		 $.ajax({
	    		type : "post",
	    		url : "<c:url value='/PermissionVerification/checkPermission.do'/>",
	    		async : false,
	    		dataType : 'json',
	    		data:{"fp_Url":fp_Url},
	    		error : function() {
	    			alert("出错");
	    		},
	    		success : function(data) {
	    			if(data.flag){
	    				 $('#fj').show();
	    		}else{
	    			 $('#fj').hide();
		    	}
	    	}
  		});
	}
</script>
</body>
</html>