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
			      <label class="layui-form-label" style="width: 100px;">材料名称</label>
			      <div class="layui-input-inline">
			        <input type="text"  lay-verify="material_Name" autocomplete="off" class="layui-input" style="width: 200px;" id="material_Name">
			      </div>
		     </div>
		    
			<div class="layui-inline">
			      <label class="layui-form-label" style="width: 100px;">规格型号</label>
			      <div class="layui-input-inline">
			        <input type="text"  lay-verify="specification_Type"
					autocomplete="off" class="layui-input" style="width: 200px;" id="specification_Type">
			      </div>
		     </div>
		     
		     <div class="layui-inline">
			      <label class="layui-form-label" style="width: 100px;">单位</label>
			      <div class="layui-input-inline">
			        <input type="text" lay-verify="unit" autocomplete="off" class="layui-input" style="width: 200px;" id="unit">
			      </div>
		     </div>
		     <button class="layui-btn" data-type="reload" type="button" id="do_search" >搜索</button>
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
  $('#gjssq').hide();
  form.render();
  table.render({
    elem: '#test'
    ,url:url+'sales/salesList.do'
    ,toolbar: '#toolbarDemo'
    ,title: '销售合同'
    ,totalRow: true
    ,cols: [[
       {field:'index', width:"8%", title: '序号', sort: true,type:'numbers'}
      ,{field:'sales_Contract_Name', width:"22%",align:'center', title: '合同名称'}
      ,{field:'contract_Code', width:"22%",align:'center', title: '合同编号'}
      ,{field:'qd_Date', width:"10%", align:'center', title: '签订时间',templet:'<div>{{ layui.util.toDateString(d.qd_Date, "yyyy-MM-dd") }}</div>'}
      ,{field:'supplierName', width:"19%", align:'center', title: '供方'}
      ,{field:'customerName', width:"19%", align:'center', title: '需求方'}
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
      var material_Name = $('#material_Name').val();
      var specification_Type=$('#specification_Type').val();
      var unit=$('#unit').val();
      table.reload('testReload', {
          method: 'post', 
          where: {
              'material_Name': material_Name,
              'specification_Type':specification_Type,
              'unit':unit
          }, 
          page: {
              curr: 1
          }
      });
  });
});





</script>
</body>
</html>