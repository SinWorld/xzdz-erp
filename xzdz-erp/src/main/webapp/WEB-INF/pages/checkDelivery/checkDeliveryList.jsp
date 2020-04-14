<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>送货单核对列表</title>
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
		 
		    <div class="layui-inline" style="width:325px;">
			  	<label class="layui-form-label">销售合同</label>
				<div class="layui-input-inline" style="text-align: left">
					<select name="xsht" id="xsht" lay-filter="xsht" lay-verify="xsht" lay-search="">
						<option value="" selected="selected">请选择销售合同</option>
					</select>
				</div>
			</div>
			
			
			 <div class="layui-inline" style="width:325px;">
			  	<label class="layui-form-label">审批状态</label>
				<div class="layui-input-inline" style="text-align: left">
					<select name="spzt" id="spzt" lay-filter="spzt" lay-verify="spzt" lay-search="">
						<option value="" selected="selected">请选择审批状态</option>
					</select>
				</div>
			</div>
			
			<div class="layui-inline">
		      <label class="layui-form-label" style="width: 90px;">创建日期</label>
		      <div class="layui-input-inline" style="width: 120px;">
	        	<input type="text"  id="date" lay-verify="date" placeholder="yyyy-MM-dd" autocomplete="off" class="layui-input">
		      </div>
		      <div class="layui-form-mid">-</div>
		      <div class="layui-input-inline" style="width: 120px;">
	            <input type="text"  id="date2" lay-verify="date" placeholder="yyyy-MM-dd" autocomplete="off" class="layui-input">
		      </div>
		    </div>
		    
		     <button class="layui-btn" data-type="reload" type="button" id="do_search" style="margin-left:15px;">搜索</button>
			<button type="reset" class="layui-btn layui-btn-primary" >重置</button>
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
  reloadXsht(form);
  reloadSpzt(form);
  laydate.render({
	 elem: '#date'
  });
  laydate.render({
	 elem: '#date2'
  });
  form.render();
  table.render({
    elem: '#test'
    ,url:url+'checkDelivery/checkDeliveryList.do'
    ,toolbar: '#toolbarDemo'
    ,title: '送货单核对列表'
    ,totalRow: true
    ,cols: [[
       {field:'index', width:"8%", title: '序号', sort: true,type:'numbers' ,totalRowText: '合计'}
      ,{field:'xshtmc', width:"32%",align:'center', title: '销售合同'}
      ,{field:'createTime', width:"30%",align:'center', title: '创建日期',templet:'<div>{{ layui.util.toDateString(d.createTime, "yyyy-MM-dd") }}</div>'}
      ,{field:'approvalMc', width:"30%", align:'center', title: '审批状态'}
      
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
    	
    }
  });

//查看（行点击）
  table.on('row(test)', function(obj){
    var data = obj.data;
    var url=$('#url').val();
    var row_Id=data.row_Id;
    var fp_Url="/checkDelivery/checkDeliveryShow.do";
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
   				  	  	title:'查看送货单核对',
   				  	  	area: ['100%','100%'],
   				  		shadeClose: false,
   				  		resize:false,
   				  	    anim: 1,
   				  	  	content:[url+"checkDelivery/checkDeliveryShow.do?row_Id="+row_Id,'yes']
   					  });
    		}else{
				layer.alert("当前用户无此功能权限，请联系管理员授权!!!",{icon:7});
	    	}
    	}
	});
  });
  // 执行搜索，表格重载
  $('#do_search').on('click', function () {
      // 搜索条件
      var xsht=$('#xsht').val();
      var spzt=$('#spzt').val();
      var beginTime=$('#date').val();
      var endTime=$('#date2').val();
      table.reload('testReload', {
          method: 'post', 
          where: {
              'xsht': xsht,
              'spzt':spzt,
              'beginTime':beginTime,
              'endTime':endTime
          }, 
          page: {
              curr: 1
          }
      });
  });
});


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

//ajax加载所有的审批状态
function reloadSpzt(form){
	$.ajax({
		type : "post",
		url : "<c:url value='/checkDelivery/allSpzt.do'/>",
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



</script>
</body>
</html>