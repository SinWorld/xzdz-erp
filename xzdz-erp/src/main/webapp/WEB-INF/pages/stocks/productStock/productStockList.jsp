<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>成品库存列表</title>
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
			      <label class="layui-form-label" style="width: 100px;">库位</label>
			      <div class="layui-input-inline">
			        <input type="text" name="kw" lay-verify="kw" autocomplete="off" class="layui-input" style="width: 200px;" id="kw">
			      </div>
		     </div>
		     
		      <div class="layui-inline">
			      <label class="layui-form-label" style="width: 100px;">备注</label>
			      <div class="layui-input-inline">
			        <input type="text" name="bz" lay-verify="bz" autocomplete="off" class="layui-input" style="width: 200px;" id="bz">
			      </div>
		     </div>
		
		 	<button class="layui-btn" data-type="reload" type="button" id="do_search" >搜索</button>
		 	<button type="reset" class="layui-btn layui-btn-primary" style="margin-left: 60px;">重置</button>
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
    <button class="layui-btn layui-btn-sm" lay-event="getCheckData" type="button">新增库存</button>
 	<button class="layui-btn layui-btn-sm" lay-event="gjss" type="button">高级搜索</button>
  </div>
</script>
<script src="../layui-v2.5.5/layui/layui.js" charset="utf-8"></script>
<!-- 注意：如果你直接复制所有代码到本地，上述js路径需要改成你本地的 --> 
<script type="text/javascript" src="../jquery/jquery-3.3.1.js"></script>
<!-- 注意：如果你直接复制所有代码到本地，上述js路径需要改成你本地的 --> 
<script type="text/html" id="barDemo">
 <a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="ck">出库</a>
 <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
 <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
</script>
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
    ,url:url+'proStock/proStockList.do'
    ,toolbar: '#toolbarDemo'
    ,title: '成品库存'
    ,cols: [[
       {field:'index', width:"8%", title: '序号', sort: true,type:'numbers'}
      ,{field:'stock', width:"22%", align:'center', title: '库位'}
      ,{field:'remarks', width:"55%", align:'center', title: '备注'}
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
	      	  	title:'成品入库',
	      	  	area: ['100%','100%'],
	      	  	shadeClose: false,
	      		resize:false,
	      	    anim: 1,
	      	  	content:[url+"proStock/initRkProduct.do",'yes']
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
    var stock_Id=data.stock_Id;
   	if(obj.event === 'edit'){
   		layer.open({
        	  	type:2,
        	  	title:'编辑库存',
        	  	area: ['100%','100%'],
        		shadeClose: false,
         		resize:false,
         	    anim: 1,
        	  	content:[url+"proStock/initEditStock.do?stock_Id="+stock_Id,'yes']
      	  	});
    }else if(obj.event === 'ck'){
	    	layer.open({
	    	  	type:2,
	    	  	title:'成品出库',
	    	  	area: ['100%','100%'],
	    		shadeClose: false,
	     		resize:false,
	     	    anim: 1,
	    	  	content:[url+"ckStock/initckProStockList.do?stock_Id="+stock_Id,'yes']
	  	  	});
        }else if(obj.event==='del'){
        	layer.confirm('您确定要删除该数据吗？', {
  			  btn: ['确定','取消'], //按钮
  			  title:'提示'},function(index){
  				  $.ajax({
  			    		type : "post",
  			    		url : "<c:url value='/proStock/deleteStock.do'/>",
  			    		async : false,
  			    		dataType : 'json',
  			    		data:{"stock_Id":stock_Id},
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
  	}
  });
  
  // 执行搜索，表格重载
  $('#do_search').on('click', function () {
      // 搜索条件
      var kw = $('#kw').val();
      var bz=$('#bz').val();
      table.reload('testReload', {
          method: 'post'
          , where: {
              'kw': kw,
              'bz':bz,
          }
          , page: {
              curr: 1
          }
      });
  });
});

</script>
</body>
</html>