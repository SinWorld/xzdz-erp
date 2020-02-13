<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>销售合同列表</title>
<link rel="stylesheet" href="../layui-v2.5.5/layui/css/layui.css">
<link rel="stylesheet" href="../login/css/static.css">
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@page isELIgnored="false" %>
</head>
<body>
	<form class="layui-form" action="" style="margin-top: 10px;">
	 <div class="demoTable" style="background-color: #CAE1FF" id="gjssq">
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
			        <input type="text"  lay-verify=""
					autocomplete="off" class="layui-input" style="width: 200px;" id="htbh">
			      </div>
		     </div>
		     
		     <div class="layui-inline" style="width:325px;">
			  	<label class="layui-form-label">审批状态</label>
				<div class="layui-input-inline" style="text-align: left">
					<select  id="spzt" lay-filter="" lay-verify="" lay-search="">
						<option value="" selected="selected">请选择审批状态</option>
					</select>
				</div>
			</div>
		     <button class="layui-btn" data-type="reload" type="button" id="do_search" style="margin-left: 60px;">搜索</button>
	 	</div>
	 	
	 	 <div class="layui-form-item">
	 	 	<div class="layui-inline" style="width:325px;left: 20px;">
			  	<label class="layui-form-label">供方</label>
				<div class="layui-input-inline" style="text-align: left">
					<select  id="gf" lay-filter="" lay-verify="" lay-search="">
						<option value="" selected="selected">请选择供方</option>
					</select>
				</div>
			</div>
			
			<div class="layui-inline" style="width:325px;left: 25px;">
			  	<label class="layui-form-label">需方</label>
				<div class="layui-input-inline" style="text-align: left">
					<select id="xf"  lay-filter="" lay-verify="" lay-search="">
						<option value="" selected="selected">请选择需方</option>
					</select>
				</div>
			</div>
			
			<div class="layui-inline">
		      <label class="layui-form-label" style="width: 90px;">签订日期</label>
		      <div class="layui-input-inline" style="width: 120px;">
	        	<input type="text"  id="date" lay-verify="date" placeholder="yyyy-MM-dd" autocomplete="off" class="layui-input">
		      </div>
		      <div class="layui-form-mid">-</div>
		      <div class="layui-input-inline" style="width: 120px;">
	            <input type="text"  id="date2" lay-verify="date" placeholder="yyyy-MM-dd" autocomplete="off" class="layui-input">
		      </div>
		    </div>
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
  $('#gjssq').hide();
  reloadApproval(form);
  reloadCompany(form);
  reloadCustomer(form);
  form.render();
  table.render({
    elem: '#test'
    ,url:url+'sales/salesList.do'
    ,toolbar: '#toolbarDemo'
    ,title: '销售合同'
    ,cols: [[
       {field:'index', width:"8%", title: '序号', sort: true,type:'numbers'}
      ,{field:'sales_Contract_Name', width:"18%",align:'center', title: '合同名称'}
      ,{field:'contract_Code', width:"18%",align:'center', title: '合同编号'}
      ,{field:'qd_Date', width:"10%", align:'center', title: '签订日期',templet:'<div>{{ layui.util.toDateString(d.qd_Date, "yyyy-MM-dd") }}</div>'}
      ,{field:'supplierName', width:"18%", align:'center', title: '供方'}
      ,{field:'customerName', width:"18%", align:'center', title: '需求方'}
      ,{field:'approvalName', width:"10%", align:'center', title: '审批状态'}
    ]]
    ,id:'testReload'
    ,page: true
  });
  
  //头工具栏事件
  table.on('toolbar(test)', function(obj){
    var url=$('#url').val();
    var flag=$('#flag').val();
    if(obj.event=='getCheckData'){
		 layer.open({
	      	  	type:2,
	      	  	title:'新增合同',
	      	  	area: ['100%','100%'],
	      	  	shadeClose: false,
	      		resize:false,
	      	    anim: 1,
	      	  	content:[url+"sales/initSaveSales.do",'yes']
	    	 });
    }else if(obj.event=='gjss'){
    	if(flag=='false'){
    		$('#gjssq').fadeIn();
    		$('#flag').val(true);
    	}else{
    		$('#gjssq').fadeOut();
    		$('#flag').val(false);
    	}
    	
    }
  });
  
  

  //查看（行点击）
  table.on('row(test)', function(obj){
    var data = obj.data;
    var url=$('#url').val();
    var sales_Contract_Id=data.sales_Contract_Id;
    layer.open({
  	  	type:2,
  	  	title:'查看合同',
  	  	area: ['100%','100%'],
  		shadeClose: false,
  		resize:false,
  	    anim: 1,
  	  	content:[url+"sales/salesShow.do?sales_Contract_Id="+sales_Contract_Id,'yes']
	  });
	  
  });
  
  // 执行搜索，表格重载
  $('#do_search').on('click', function () {
      // 搜索条件
      var htmc = $('#htmc').val();
      var htbh=$('#htbh').val();
      var spzt=$('#spzt').val();
      var gf=$('#gf').val();
      var xf=$('#xf').val();
      var date=$('#date').val();
      var date2=$('#date2').val();
      table.reload('testReload', {
          method: 'post', 
          where: {
              'htmc': htmc,
              'htbh':htbh,
              'spzt':spzt,
              'gf':gf,
              'xf':xf,
              'beginTime':date,
              'endTime':date2,
          }, 
          page: {
              curr: 1
          }
      });
  });
});

	//ajax加载所有的审批状态
	function reloadApproval(form){
		$.ajax({
			type : "post",
			url : "<c:url value='/sales/allApproval.do'/>",
			async : false,
			dataType : 'json',
			error : function() {
				alert("出错");
			},
			success : function(msg) {
				for (var i = 0; i < msg.length; i++) {
					$("#spzt").append(
							"<option value='"+msg[i].approvaldm+"'>"+ msg[i].approvalmc +"</option>");
				}
				form.render('select');
			}
		});
	}

	//ajax加载所有的供方
	function reloadCompany(form){
		$.ajax({
			type : "post",
			url : "<c:url value='/sales/allCompany.do'/>",
			async : false,
			dataType : 'json',
			error : function() {
				alert("出错");
			},
			success : function(msg) {
				for (var i = 0; i < msg.length; i++) {
					$("#gf").append(
							"<option value='"+msg[i].unit_Id+"'>"+ msg[i].unit_Name +"</option>");
				}
				form.render('select');
			}
		});
	}

	//ajax加载所有的需求放
	function reloadCustomer(form){
		$.ajax({
			type : "post",
			url : "<c:url value='/sales/allCustomer.do'/>",
			async : false,
			dataType : 'json',
			error : function() {
				alert("出错");
			},
			success : function(msg) {
				for (var i = 0; i < msg.length; i++) {
					$("#xf").append(
							"<option value='"+msg[i].customer_Id+"'>"+ msg[i].unit_Name +"</option>");
				}
				form.render('select');
			}
		});
	}



</script>
</body>
</html>