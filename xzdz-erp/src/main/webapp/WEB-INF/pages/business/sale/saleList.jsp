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
    ,url:url+'sales/salesList.do'
    ,toolbar: '#toolbarDemo'
    ,title: '销售合同'
    ,totalRow: true
    ,cols: [[
       {field:'index', width:"8%", title: '序号', sort: true,type:'numbers'}
      ,{field:'contract_Code', width:"17%",align:'center', title: '合同编号'}
      ,{field:'qd_Date', width:"20%", align:'center', title: '签订时间',templet:'<div>{{ layui.util.toDateString(d.qd_Date, "yyyy-MM-dd") }}</div>'}
      ,{field:'supplierName', width:"20%", align:'center', title: '供方'}
      ,{field:'customerName', width:"20%", align:'center', title: '需求方'}
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
  
  //监听行工具事件
  table.on('tool(test)', function(obj){
    var data = obj.data;
    var url=$('#url').val();
    var raw_Material_Id=data.raw_Material_Id;
   	if(obj.event === 'edit'){
   		layer.open({
        	  	type:2,
        	  	title:'编辑材料',
        	  	area: ['100%','100%'],
        		shadeClose: false,
         		resize:false,
         	    anim: 1,
        	  	content:[url+"material/initEditMaterial.do?raw_Material_Id="+raw_Material_Id,'yes']
      	  	});
    }else if(obj.event==='del'){
    	layer.confirm('您确定要删除该数据吗？', {
			  btn: ['确定','取消'], //按钮
			  title:'提示'},function(index){
				  $.ajax({
			    		type : "post",
			    		url : "<c:url value='/material/deleteMaterial.do'/>",
			    		async : false,
			    		dataType : 'json',
			    		data:{"raw_Material_Id":raw_Material_Id},
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
	  	  	title:'查看',
	  	  	area: ['100%','100%'],
	  		shadeClose: false,
	  		resize:false,
	  	    anim: 1,
	  	  	content:[url+"material/showMaterial.do?raw_Material_Id="+raw_Material_Id,'yes']
		  });
    }
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