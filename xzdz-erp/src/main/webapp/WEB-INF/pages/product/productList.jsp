<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>成品列表</title>
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
			      <label class="layui-form-label" style="width: 100px;">产品名称</label>
			      <div class="layui-input-inline">
			        <input type="text"  lay-verify="product_Name" autocomplete="off" class="layui-input" style="width: 200px;" id="product_Name">
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
	 	</div>
		
		<div class="layui-form-item">
		
		 	<div class="layui-inline" style="left:24px;width:330px;">
			      <label class="layui-form-label" style="width: 76px;">出厂价</label>
			      <div class="layui-input-inline" style="width: 118px;">
			        <input type="text"  id="factory_Price1" lay-verify="factory_Price1"  autocomplete="off" class="layui-input" style="width:92px;">
			      </div>
			       <i class="u-date_line" style="margin-left:-40px;line-height: 35px;">—</i>
			      <div class="layui-input-inline" style="width: 78px;">
			        <input type="text"  id="factory_Price2" lay-verify="factory_Price2" autocomplete="off" class="layui-input" style="width:92px;margin-left: -19px;">
			      </div>
			</div>
			
			<div class="layui-inline" style="left:24px;width: 330px;">
			      <label class="layui-form-label" style="width: 76px;">渠道价</label>
			      <div class="layui-input-inline" style="width: 118px;">
			        <input type="text"  id="channel_Price1" lay-verify="channel_Price1"  autocomplete="off" class="layui-input" style="width: 100px;">
			      </div>
			       <i class="u-date_line" style="margin-left: -40px;line-height: 35px;">—</i>
			      <div class="layui-input-inline" style="width: 78px;">
			        <input type="text"  id="channel_Price2" lay-verify="channel_Price2" autocomplete="off" class="layui-input" style="width: 92px;margin-left: -19px;">
			      </div>
			</div>
			
			<div class="layui-inline" style="left:24px;width: 330px;">
			      <label class="layui-form-label" style="width: 76px;">市场价</label>
			      <div class="layui-input-inline" style="width: 118px;">
			        <input type="text"  id="market_Value1" lay-verify="market_Value1"  autocomplete="off" class="layui-input" style="width: 100px;">
			      </div>
			       <i class="u-date_line" style="margin-left: -40px;line-height: 35px;">—</i>
			      <div class="layui-input-inline" style="width: 78px;">
			        <input type="text"  id="market_Value2" lay-verify="market_Value2" autocomplete="off" class="layui-input" style="width: 92px;margin-left: -19px;">
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

<script type="text/html" id="barDemo">
  <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
  <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
  <a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="detail">查看</a>
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
    ,url:url+'product/productList.do'
    ,toolbar: '#toolbarDemo'
    ,title: '成品列表'
    ,totalRow: true
    ,cols: [[
       {field:'index', width:"8%", title: '序号', sort: true,type:'numbers' ,totalRowText: '合计'}
      ,{field:'product_Name', width:"17%",align:'center', title: '产品名称'}
      ,{field:'specification_Type', width:"20%", align:'center', title: '规格型号'}
      ,{field:'numbers', width:"5%", align:'center', title: '数量'}
      ,{field:'unit', width:"5%", align:'center', title: '单位'}
      ,{field:'factory_Price', width:"10%", align:'right', title: '出厂价', totalRow: true}
      ,{field:'channel_Price', width:"10%", align:'right', title: '渠道价', totalRow: true}
      ,{field:'market_Value', width:"10%", align:'right', title: '市场价',  totalRow: true}
      ,{fixed: 'right', title:'操作', toolbar: '#barDemo', width:"15%",align:'center'}
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
	      	  	title:'新增产品',
	      	  	area: ['100%','100%'],
	      	  	shadeClose: false,
	      		resize:false,
	      	    anim: 1,
	      	  	content:[url+"product/initSaveProduct.do",'yes']
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
  
  //监听行工具事件
  table.on('tool(test)', function(obj){
    var data = obj.data;
    var url=$('#url').val();
    var product_Id=data.product_Id;
   	if(obj.event === 'edit'){
   		layer.open({
        	  	type:2,
        	  	title:'编辑成品',
        	  	area: ['100%','100%'],
        		shadeClose: false,
         		resize:false,
         	    anim: 1,
        	  	content:[url+"product/initEditProduct.do?product_Id="+product_Id,'yes']
      	  	});
    }else if(obj.event==='del'){
    	layer.confirm('您确定要删除该数据吗？', {
			  btn: ['确定','取消'], //按钮
			  title:'提示'},function(index){
				  $.ajax({
			    		type : "post",
			    		url : "<c:url value='/product/deleteProduct.do'/>",
			    		async : false,
			    		dataType : 'json',
			    		data:{"product_Id":product_Id},
			    		error : function() {
			    			alert("出错");
			    		},
			    		success : function(data) {
			    			if(data.flag){
					    		layer.close(index);
					    		window.location.reload();
			    		}
			    	}
			  });
	    });
	}else if(obj.event==='detail'){
	   	 layer.open({
	  	  	type:2,
	  	  	title:'查看成品',
	  	  	area: ['100%','100%'],
	  		shadeClose: false,
	  		resize:false,
	  	    anim: 1,
	  	  	content:[url+"product/showProduct.do?product_Id="+product_Id,'yes']
		  });
    }
  });
  
  // 执行搜索，表格重载
  $('#do_search').on('click', function () {
      // 搜索条件
      var product_Name = $('#product_Name').val();
      var specification_Type=$('#specification_Type').val();
      var unit=$('#unit').val();
      var factory_Price1=$('#factory_Price1').val();
      var factory_Price2=$('#factory_Price2').val();
      var channel_Price1=$('#channel_Price1').val();
      var channel_Price2=$('#channel_Price2').val();
      var market_Value2=$('#market_Value2').val();
      var market_Value2=$('#market_Value2').val();
      table.reload('testReload', {
          method: 'post', 
          where: {
              'product_Name': product_Name,
              'specification_Type':specification_Type,
              'unit':unit,
              'factory_Price1':factory_Price1,
              'factory_Price2':factory_Price2,
              'channel_Price1':channel_Price1,
              'channel_Price2':channel_Price2,
              'channel_Price1':channel_Price1,
              'channel_Price2':channel_Price2,
              'market_Value1':market_Value1,
              'market_Value2':market_Value2
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