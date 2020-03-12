<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>采购合同列表</title>
<link rel="stylesheet" href="../layui-v2.5.5/layui/css/layui.css">
<link rel="stylesheet" href="../login/css/static.css">
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@page isELIgnored="false" %>
</head>
<body>
	<form class="layui-form" action="" style="margin-top: 10px;">
	 <div class="demoTable" style="background-color: #CAE1FF;display: none;" id="gjssq">
		<div class="layui-form-item" style="width:1280px;height:auto;padding:0px; margin:0 auto;" id="main"">
		 <div class="layui-form-item">
		 	<div class="layui-inline">
			      <label class="layui-form-label" style="width: 100px;">合同名称</label>
			      <div class="layui-input-inline">
			        <input type="text"  lay-verify="" autocomplete="off" class="layui-input" style="width: 200px;" id="htmc">
			      </div>
		     </div>
		     
			 <div class="layui-inline">
			      <label class="layui-form-label" style="width: 100px;">合同编号</label>
			      <div class="layui-input-inline">
			        <input type="text"  lay-verify="" autocomplete="off" class="layui-input" style="width: 200px;" id="htbh">
			      </div>
		     </div>
		     
		     <div class="layui-inline" style="width:325px;">
			  	<label class="layui-form-label">供货单位</label>
				<div class="layui-input-inline" style="text-align: left">
					<select  id="ghdw" lay-filter="" lay-verify="" lay-search="">
						<option value="" selected="selected">请选择供货单位</option>
					</select>
				</div>
			</div>
	 	</div>
	 	
	 	 <div class="layui-form-item">
			<div class="layui-inline" style="width:325px;left: 20px;">
			  	<label class="layui-form-label">我方单位</label>
				<div class="layui-input-inline" style="text-align: left">
					<select  id="wfdw" lay-filter="" lay-verify="" lay-search="">
						<option value="" selected="selected">请选择我方单位</option>
					</select>
				</div>
			</div>
			
			<div class="layui-inline" style="width:325px;left: 25px;">
			  	<label class="layui-form-label">销售合同</label>
				<div class="layui-input-inline" style="text-align: left">
					<select  id="xsht" lay-filter="" lay-verify="" lay-search="">
						<option value="" selected="selected">请选择销售合同</option>
					</select>
				</div>
			</div>
			
			<div class="layui-inline" style="width:325px;left: 10px;">
			  	<label class="layui-form-label">经办人</label>
				<div class="layui-input-inline" style="text-align: left">
					<select  id="jbr" lay-filter="" lay-verify="" lay-search="">
						<option value="" selected="selected">请选择经办人</option>
					</select>
				</div>
			</div>
			
	 	 </div>
	 	
	 	 <div class="layui-form-item">
	 	 	
	 	 	 <div class="layui-inline">
			      <label class="layui-form-label" style="width: 100px;">合同状态</label>
			      <div class="layui-input-inline">
			        <input type="text"  lay-verify="" autocomplete="off" class="layui-input" style="width: 200px;" id="htzt">
			      </div>
		     </div>
			
			<div class="layui-inline">
		      <label class="layui-form-label" style="width:100px;">订购日期</label>
		      <div class="layui-input-inline" style="width: 120px;">
	        	<input type="text"  id="date" lay-verify="date" placeholder="yyyy-MM-dd" autocomplete="off" class="layui-input">
		      </div>
		      <div class="layui-form-mid">-</div>
		      <div class="layui-input-inline" style="width: 120px;">
	            <input type="text"  id="date2" lay-verify="date" placeholder="yyyy-MM-dd" autocomplete="off" class="layui-input">
		      </div>
		    </div>
		     <button class="layui-btn" data-type="reload" type="button" id="do_search" style="margin-left: 60px;">搜索</button>
	 	 	 <button type="reset" class="layui-btn layui-btn-primary">重置</button>
	 	 </div>
	</div> 
	</div>
</form>
	
	<input type="hidden" value='<c:url value="/"/>' id="url">
	<input type="hidden" id="flag" value="false">
	<div style="position:relative;top: -10px;">
		<table class="layui-hide" id="test" lay-filter="test"></table>
	</div>
<script type="text/html" id="toolbarDemo">
  <div class="layui-btn-container" style="width:25%;">
    <button class="layui-btn layui-btn-sm" lay-event="getCheckData" type="button">新增</button>
 	<button class="layui-btn layui-btn-sm" lay-event="gjss" type="button">高级搜索</button>
  </div>
</script>

<script src="../layui-v2.5.5/layui/layui.js" charset="utf-8"></script>
<!-- 注意：如果你直接复制所有代码到本地，上述js路径需要改成你本地的 --> 
<script type="text/javascript" src="../jquery/jquery-3.3.1.js"></script>
<!-- 注意：如果你直接复制所有代码到本地，上述js路径需要改成你本地的 --> 
<script>
layui.use(['table','form','layedit', 'laydate'], function(){
  var table = layui.table;
  var url=$('#url').val();
  var form= layui.form;
  var layer = layui.layer;
  var layedit = layui.layedit;
  var laydate = layui.laydate;
  laydate.render({
	 elem: '#date'
  });
  laydate.render({
	 elem: '#date2'
  });
  reloadGhdw(form);
  reloadJbr(form);
  reloadXsht(form);
  reloadOurCompany(form);
  form.render();
  table.render({
    elem: '#test'
    ,url:url+'purchase/purchaseOrderList.do'
    ,toolbar: '#toolbarDemo'
    ,title: '采购合同'
    ,cols: [[
       {field:'index', width:"8%", title: '序号', sort: true,type:'numbers'}
       ,{field:'purchaseOrderName', width:"13%",align:'center', title: '合同名称'}
       ,{field:'pur_Code', width:"12%",align:'center', title: '合同编号'}
       ,{field:'supplierName', width:"12%",align:'center', title: '供货单位'}
       ,{field:'uintName', width:"12%",align:'center', title: '我方单位'}
      ,{field:'pur_Date', width:"11%",align:'center', title: '订购日期',templet:'<div>{{ layui.util.toDateString(d.pur_Date, "yyyy-MM-dd") }}</div>'}
      ,{field:'sales_Contract_Name', width:"12%", align:'center', title: '销售合同'}
      ,{field:'userName', width:"10%", align:'center', title: '经办人'}
      ,{field:'status', width:"10%", align:'center', title: '合同状态'}
    ]]
    ,id:'testReload'
    ,page: true
  });
  
  //头工具栏事件
  table.on('toolbar(test)', function(obj){
    var url=$('#url').val();
    var flag=$('#flag').val();
    if(obj.event=='gjss'){
    	if(flag=='false'){
    		//$('#gjssq').fadeIn();
    		$('#gjssq').css('display','block');
    		$('#flag').val(true);
    	}else{
    		//$('#gjssq').fadeOut();
    		$('#gjssq').css('display','none');
    		$('#flag').val(false);
    	}
    } else if(obj.event=='getCheckData'){
    	layer.open({
      	  	type:2,
      	  	title:'新增合同',
      	  	area: ['100%','100%'],
      		shadeClose: false,
      		resize:false,
      	    anim: 1,
      	  	content:[url+"purchaseOrder/initSavePurchaseOrder.do",'yes']
    	  });
    }
  });
  
  

  //查看（行点击）
  table.on('row(test)', function(obj){
    var data = obj.data;
    var url=$('#url').val();
    var pur_Order_Id=data.pur_Order_Id;
    var xsht=data.sales_Contract_Id;
    if(xsht!=""){
    	layer.open({
      	  	type:2,
      	  	title:'查看合同',
      	  	area: ['100%','100%'],
      		shadeClose: false,
      		resize:false,
      	    anim: 1,
      	  	content:[url+"purchaseOrder/purchaseOrderShow.do?pur_Order_Id="+pur_Order_Id,'yes']
    	  });
     }else{
    	 layer.open({
    	  	  	type:2,
    	  	  	title:'查看合同',
    	  	  	area: ['100%','100%'],
    	  		shadeClose: false,
    	  		resize:false,
    	  	    anim: 1,
    	  	  	content:[url+"purchase/purchaseOrderShow.do?pur_Order_Id="+pur_Order_Id,'yes']
    		  });
        }
  });
  
  // 执行搜索，表格重载
  $('#do_search').on('click', function () {
      // 搜索条件
      var htmc = $('#htmc').val();
      var htbh=$('#htbh').val();
      var ghdw=$('#ghdw').val();
      var wfdw=$('#wfdw').val();
      var xsht=$('#xsht').val();
      var jbr=$('#jbr').val();
      var htzt=$('#htzt').val();
      var date=$('#date').val();
      var date2=$('#date2').val();
      table.reload('testReload', {
          method: 'post', 
          where: {
              'htmc': htmc,
              'htbh':htbh,
              'ghdw':ghdw,
              'wfdw':wfdw,
              'xsht':xsht,
              'jbr':jbr,
              'htzt':htzt,
              'beginTime':date,
              'endTime':date2,
          }, 
          page: {
              curr: 1
          }
      });
  });
});

	//ajax加载所有的供货单位
	function reloadGhdw(form){
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
					$("#ghdw").append(
							"<option value='"+msg[i].supplier_Id+"'>"+ msg[i].supplier_Name +"</option>");
				}
				form.render('select');
			}
		});
	}


	//ajax加载我方单位
	function reloadOurCompany(form){
		$.ajax({
			type : "post",
			url : "<c:url value='/purchase/allUnit.do'/>",
			async : false,
			dataType : 'json',
			error : function() {
				alert("出错");
			},
			success : function(msg) {
				for (var i = 0; i < msg.length; i++) {
					$("#wfdw").append(
							"<option value='"+msg[i].unit_Id+"'>"+ msg[i].unit_Name +"</option>");
				}
				form.render('select');
			}
		});
	}



	//ajax加载所有的经办人
	function reloadJbr(form){
		$.ajax({
			type : "post",
			url : "<c:url value='/stockRecod/allJbrList.do'/>",
			async : false,
			dataType : 'json',
			error : function() {
				alert("出错");
			},
			success : function(msg) {
				for (var i = 0; i < msg.length; i++) {
					$("#jbr").append(
							"<option value='"+msg[i].userId+"'>"+ msg[i].userName +"</option>");
				}
				form.render('select');
			}
		});
	}

	//ajax加载所有的销售合同
	function reloadXsht(form){
		$.ajax({
			type : "post",
			url : "<c:url value='/sales/allxsht.do'/>",
			async : false,
			dataType : 'json',
			error : function() {
				alert("出错");
			},
			success : function(msg) {
				for (var i = 0; i < msg.length; i++) {
					$("#xsht").append(
							"<option value='"+msg[i].sales_Contract_Id+"'>"+ msg[i].sales_Contract_Name +"</option>");
				}
				form.render('select');
			}
		});
	}



</script>
</body>
</html>