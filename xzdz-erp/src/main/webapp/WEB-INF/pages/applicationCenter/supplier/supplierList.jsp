<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>供应商列表</title>
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
			      <label class="layui-form-label" style="width: 100px;">供应商名称</label>
			      <div class="layui-input-inline">
			        <input type="text"  lay-verify="material_Name" autocomplete="off" class="layui-input" style="width: 200px;" id="gysmc">
			      </div>
		     </div>
		    
			<div class="layui-inline">
			      <label class="layui-form-label" style="width: 100px;">注册地址</label>
			      <div class="layui-input-inline">
			        <input type="text"  lay-verify="specification_Type"
					autocomplete="off" class="layui-input" style="width: 200px;" id="zcdz">
			      </div>
		     </div>
		     
		     <div class="layui-inline">
			      <label class="layui-form-label" style="width: 100px;">办公地址</label>
			      <div class="layui-input-inline">
			        <input type="text" lay-verify="unit" autocomplete="off" class="layui-input" style="width: 200px;" id="bgdz">
			      </div>
		     </div>
	 	</div>
	 	
	 	<div class="layui-form-item">
			 <div class="layui-inline" style="left: -20px;">
			      <label class="layui-form-label" style="width: 120px;">社会统一信用代码</label>
			      <div class="layui-input-inline">
			        <input type="text"  lay-verify="material_Name" autocomplete="off" class="layui-input" style="width: 200px;" id="shtyxydm">
			      </div>
		     </div>
		    
			<div class="layui-inline">
			      <label class="layui-form-label" style="width: 80px;">法定代表人</label>
			      <div class="layui-input-inline">
			        <input type="text"  lay-verify="specification_Type"
					autocomplete="off" class="layui-input" style="width: 200px;" id="fddbr">
			      </div>
		     </div>
		     
		     <div class="layui-inline">
			      <label class="layui-form-label" style="width: 100px;">开户行</label>
			      <div class="layui-input-inline">
			        <input type="text" lay-verify="unit" autocomplete="off" class="layui-input" style="width: 200px;" id="khh">
			      </div>
		     </div>
	 	</div>
	 	
	 	<div class="layui-form-item">
			 <div class="layui-inline">
			      <label class="layui-form-label" style="width: 100px;">电话</label>
			      <div class="layui-input-inline">
			        <input type="text"  lay-verify="material_Name" autocomplete="off" class="layui-input" style="width: 200px;" id="dh">
			      </div>
		     </div>
		     <button class="layui-btn" data-type="reload" type="button" id="do_search" style="margin-left: 130px;">搜索</button>
		     <button type="reset" class="layui-btn layui-btn-primary">重置</button>
	 	</div>
	</div> 
	</div>
</form>
	
	<input type="hidden" value='<c:url value="/"/>' id="url">
	<input type="hidden" id="flag" value="false">
	<input type="hidden" id="userIds">
	<div style="position:relative;top: -10px;">
		<table class="layui-hide" id="test" lay-filter="test"></table>
	</div>
<script type="text/html" id="toolbarDemo">
  <div class="layui-btn-container" style="width:30%;">
    <button class="layui-btn layui-btn-sm" lay-event="getCheckData" type="button">新增</button>
 	<button class="layui-btn layui-btn-sm" lay-event="gjss" type="button">高级搜索</button>
	<button class="layui-btn layui-btn-sm" lay-event="yjsc" type="button">一键删除</button>
	<button class="layui-btn layui-btn-sm" lay-event="dr" type="button">导入</button>
	<button class="layui-btn layui-btn-sm" lay-event="dc" type="button">导出</button>
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
  form.render();
  table.render({
    elem: '#test'
    ,url:url+'gys/supplierList.do'
    ,toolbar: '#toolbarDemo'
    ,title: '供应商管理列表'
    ,height: 'full-20'
    ,cols: [[
       {type:'checkbox'}
      ,{field:'index', width:"5%", title: '序号', sort: true,type:'numbers'}
      ,{field:'supplier_Name', width:"12.5%",align:'center', title: '供应商名称'}
      ,{field:'registered_Address', width:"12%", align:'center', title: '注册地址'}
      ,{field:'office_Address', width:"12%", align:'center', title: '办公地址'}
      ,{field:'unified_Code', width:"12%", align:'center', title: '社会统一信用代码'}
      ,{field:'legal_person', width:"10%", align:'center', title: '法定代表人'}
      ,{field:'opening_Bank', width:"10%", align:'center', title: '开户行'}
      ,{field:'phone', width:"10%", align:'center', title: '电话'}
      ,{fixed: 'right', title:'操作', toolbar: '#barDemo', width:"13%",align:'center'}
    ]]
    ,id:'testReload'
    ,page: true
  });
  
  //头工具栏事件
  table.on('toolbar(test)', function(obj){
    var url=$('#url').val();
    var flag=$('#flag').val();
    if(obj.event=='getCheckData'){
    	var fp_Url="/supplier/initSaveSupplier.do";
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
	    				 layer.open({
	    			      	  	type:2,
	    			      	  	title:'新增供应商',
	    			      	  	area: ['100%','100%'],
	    			      	  	shadeClose: false,
	    			      		resize:false,
	    			      	    anim: 1,
	    			      	  	content:[url+"supplier/initSaveSupplier.do",'yes']
    			    	 });
	    		}else{
					layer.alert("当前用户无此功能权限，请联系管理员授权!!!",{icon:7});
		    	}
	    	}
  		});
    }else if(obj.event=='gjss'){
    	if(flag=='false'){
    		//$('#gjssq').fadeIn();
    		$('#gjssq').css('display','block');
    		$('#flag').val(true);
    	}else{
    		//$('#gjssq').fadeOut();
    		$('#gjssq').css('display','none');
    		$('#flag').val(false);
    	}
    }else if(obj.event=='yjsc'){
    	var fp_Url="/supplier/batchDeletePost.do";
    	var checkStatus=table.checkStatus(obj.config.id);
    	var is_xz=checkStatus.data;//获取选中数据
    	var ids=$('#userIds').val();
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
	    				 if(is_xz==''){
	    						return layer.alert('请勾选需要删除的数据!',{icon:7});
	    					 }else{
	    						 layer.confirm('您确定要删除该数据么？', {
	    							  btn: ['确定','取消'], //按钮
	    							  title:'提示',icon:7},function(){
	    								 //获取所有选中数据的主键 进行字符串拼接
	    								 for(var i=0;i<checkStatus.data.length;i++){
	    									 if(null!=ids&&""!=ids){
	    										 ids=ids+","+checkStatus.data[i].supplier_Id;
	    									 }else{
	    										 ids=checkStatus.data[i].supplier_Id;
	    									 }
	    								 }
	    								 //批量删除
	    								 $.ajax({
	    										type : "post",
	    										url : "<c:url value='/supplier/batchDeletePost.do'/>",
	    										data:{"ids":ids},
	    										async : false,
	    										dataType : 'json',
	    										error : function() {
	    											alert("出错");
	    										},
	    										success : function(data) {
	    											if(data.flag){
	    												 var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
	    				    				             location.reload();//刷新父页面，注意一定要在关闭当前iframe层之前执行刷新
	    				    				             layer.close(index); //再执行关闭
	    											}
	    										}
	    									});
	    							  }
	    							)
	    					 }
	    		}else{
					layer.alert("当前用户无此功能权限，请联系管理员授权!!!",{icon:7});
		    	}
	    	}
		});
    }else if(obj.event=='dr'){
    	var fp_Url="/supplier/initFileImport.do";
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
	    				 layer.open({
	    			      	  	type:2,
	    			      	  	title:'文件导入',
	    			      	  	area: ['600px','300px'],
	    			      	  	shadeClose: false,
	    			      		resize:false,
	    			      	    anim: 1,
	    			      	  	content:[url+"supplier/initFileImport.do",'yes']
   			    	 });
	    		}else{
					layer.alert("当前用户无此功能权限，请联系管理员授权!!!",{icon:7});
		    	}
	    	}
 		});
    }else if(obj.event=='dc'){
    	var fp_Url="/supplier/importExcel.do";
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
	    				layer.confirm('您确定导出Excel文件么？', {
	    					  btn: ['确定','取消'], //按钮
	    					  title:'提示',icon:7},function(){
	    						  location.href='<c:url value="/supplier/exportExcel.do"/>';
	    						  //var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
    				              //parent.location.reload();//刷新父页面，注意一定要在关闭当前iframe层之前执行刷新
    				             // parent.layer.close(index); //再执行关闭
    				              layer.close(layer.index);
	    					  }
	    					)
	    		}else{
					layer.alert("当前用户无此功能权限，请联系管理员授权!!!",{icon:7});
		    	}
	    	}
 		});
    }
  });
  
  //监听行工具事件
  table.on('tool(test)', function(obj){
    var data = obj.data;
    var url=$('#url').val();
    var row_Id=data.supplier_Id;
   	if(obj.event === 'edit'){
   		var fp_Url="/supplier/initEditSupplier.do";
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
	    				layer.open({
	    	        	  	type:2,
	    	        	  	title:'编辑供应商',
	    	        	  	area: ['100%','100%'],
	    	        		shadeClose: false,
	    	         		resize:false,
	    	         	    anim: 1,
	    	        	  	content:[url+"supplier/initEditSupplier.do?row_Id="+row_Id,'yes']
	    	      	  	});
	    		}else{
					layer.alert("当前用户无此功能权限，请联系管理员授权!!!",{icon:7});
		    	}
	    	}
  		});
    }else if(obj.event==='del'){
    	var fp_Url="/supplier/deleteSupplier.do";
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
	    				layer.confirm('您确定要删除该数据吗？', {
	    					  btn: ['确定','取消'], //按钮
	    					  title:'提示'},function(index){
	    						  $.ajax({
	    								type : "post",
	    								url : "<c:url value='/supplier/deleteSupplier.do'/>",
	    								data:{"row_Id":row_Id},
	    								async : false,
	    								dataType : 'json',
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
	    		}else{
					layer.alert("当前用户无此功能权限，请联系管理员授权!!!",{icon:7});
		    	}
	    	}
 		});
	}else if(obj.event==='detail'){
		var fp_Url="/supplier/showSupplier.do";
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
	    				layer.open({
	    			  	  	type:2,
	    			  	  	title:'查看供应商',
	    			  	  	area: ['100%','100%'],
	    			  		shadeClose: false,
	    			  		resize:false,
	    			  	    anim: 1,
	    			  	  	content:[url+"supplier/showSupplier.do?row_Id="+row_Id,'yes']
	    				  });
	    		}else{
					layer.alert("当前用户无此功能权限，请联系管理员授权!!!",{icon:7});
		    	}
	    	}
  		});
    }
  });
  
  // 执行搜索，表格重载
  $('#do_search').on('click', function () {
	  // 搜索条件
      var gysmc = $('#gysmc').val();
      var zcdz=$('#zcdz').val();
      var bgdz=$('#bgdz').val();
      var shtyxydm=$('#shtyxydm').val();
      var fddbr=$('#fddbr').val();
      var khh=$('#khh').val();
      var dh=$('#dh').val();
      table.reload('testReload', {
          method: 'post', 
          where: {
        	  'gysmc': gysmc,
              'zcdz':zcdz,
              'bgdz':bgdz,
              'shtyxydm':shtyxydm,
              'fddbr':fddbr,
              'khh':khh,
              'dh':dh
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