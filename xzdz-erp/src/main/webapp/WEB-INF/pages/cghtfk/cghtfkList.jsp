<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>采购合同付款列表</title>
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
		 
		      <div class="layui-inline" style="width:345px;">
			  	<label class="layui-form-label">采购合同</label>
				<div class="layui-input-inline" style="text-align: left;width: 224px;">
					<select name="cght" id="cght" lay-filter="cght" lay-verify="cght" lay-search="">
						<option value="" selected="selected">请选择采购合同</option>
					</select>
				</div>
			</div>
			
			 <div class="layui-inline" style="left: -21px;">
			      <label class="layui-form-label" style="width: 100px;">付款类型</label>
			      <div class="layui-input-inline" style="text-align: left;width: 224px;">
					<select name="fklx" id="fklx" lay-filter="fklx" lay-verify="fklx" lay-search="">
						<option value="" selected="selected">请选择付款类型</option>
						<option value="预付款">预付款</option>
						<option value="进度款">进度款</option>
						<option value="质保金">质保金</option>
						<option value="到货/完工款">到货/完工款</option>
					</select>
				</div>
		     </div>
		    
			<div class="layui-inline" style="left: -34px;">
			      <label class="layui-form-label" style="width: 80px;">预算情况</label>
			      <div class="layui-input-inline" style="text-align: left;width: 224px;">
					<select name="ysqk" id="ysqk" lay-filter="ysqk" lay-verify="ysqk" lay-search="">
						<option value="" selected="selected">请选择发票类别</option>
						<option value="预算内付款">预算内付款</option>
						<option value="预算外付款">预算外付款</option>
					</select>
				</div>
		     </div>
		     
	 	</div>
		
		<div class="layui-form-item">
		
		    <div class="layui-inline">
		      <label class="layui-form-label" style="width:79px;">付款金额</label>
		      <div class="layui-input-inline" style="width: 100px;">
		        <input type="text" id="fkje1" placeholder="￥" autocomplete="off" class="layui-input">
		      </div>
		      <div class="layui-form-mid">-</div>
		      <div class="layui-input-inline" style="width: 100px;">
		        <input type="text" id="fkje2" placeholder="￥" autocomplete="off" class="layui-input">
		      </div>
		    </div>
		    
		   <div class="layui-inline" style="left: -11px;">
		      <label class="layui-form-label" style="width: 90px;">申请付款日期</label>
		      <div class="layui-input-inline" style="width: 100px;">
	        	<input type="text"  id="sqfkrq1" lay-verify="sqfkrq1" placeholder="yyyy-MM-dd" autocomplete="off" class="layui-input">
		      </div>
		      <div class="layui-form-mid">-</div>
		      <div class="layui-input-inline" style="width: 100px;">
	            <input type="text"  id="sqfkrq2" lay-verify="sqfkrq2" placeholder="yyyy-MM-dd" autocomplete="off" class="layui-input">
		      </div>
		    </div>
			
			 <div class="layui-inline" style="left: -34px;">
		      <label class="layui-form-label" style="width: 90px;">付款日期</label>
		      <div class="layui-input-inline" style="width: 100px;">
	        	<input type="text"  id="fkrq1" lay-verify="fkrq1" placeholder="yyyy-MM-dd" autocomplete="off" class="layui-input">
		      </div>
		      <div class="layui-form-mid">-</div>
		      <div class="layui-input-inline" style="width: 100px;">
	            <input type="text"  id="fkrq2" lay-verify="fkrq2" placeholder="yyyy-MM-dd" autocomplete="off" class="layui-input">
		      </div>
		    </div>
		    
		   </div>
		   
		    <div class="layui-form-item">
		    
		     <div class="layui-inline" style="width:345px;left: -2px;">
			  	<label class="layui-form-label">审批状态</label>
				<div class="layui-input-inline" style="text-align: left;width: 224px;">
					<select name="spzt" id="spzt" lay-filter="spzt" lay-verify="spzt" lay-search="">
						<option value="" selected="selected">请选择审批状态</option>
					</select>
				</div>
			</div>
		    
			<button class="layui-btn" data-type="reload" type="button" id="do_search" style="margin-left:108px;">搜索</button>
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
  laydate.render({
	 elem: '#sqfkrq1'
  });
  laydate.render({
	 elem: '#sqfkrq2'
  });
  laydate.render({
		 elem: '#fkrq1'
  });
  laydate.render({
	 elem: '#fkrq2'
  });
  reloadCght(form);
  reloadSpzt(form);
  form.render();
  table.render({
    elem: '#test'
    ,url:url+'cghtfk/cghtfkList.do'
    ,toolbar: '#toolbarDemo'
    ,title: '采购合同付款列表'
    ,totalRow: true
    ,cols: [[
       {field:'index', width:"8%", title: '序号', sort: true,type:'numbers' ,totalRowText: '合计'}
      ,{field:'cghtmc', width:"20%",align:'center', title: '采购合同'}
      ,{field:'fklx', width:"13%", align:'center', title: '付款类型'}
      ,{field:'ysqk', width:"13%", align:'center', title: '预算情况'}
      ,{field:'sqfkje', width:"12%", align:'right', title: '付款金额', totalRow: true}
      ,{field:'sqrq', width:"12%",align:'center', title: '申请付款日期',templet:'<div>{{ layui.util.toDateString(d.sqrq, "yyyy-MM-dd") }}</div>'}
      ,{field:'fkrq', width:"12%",align:'center', title: '付款日期',templet:'<div>{{ layui.util.toDateString(d.fkrq, "yyyy-MM-dd") }}</div>'}
      ,{field:'approvalMc', width:"10%", align:'center', title: '审批状态'}
      
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
    var cghtfkdm=data.cghtfk_Id;
    var fp_Url="/cghtfk/cghtfkShow.do";
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
    				  	  	title:'查看采购合同付款',
    				  	  	area: ['100%','100%'],
    				  		shadeClose: false,
    				  		resize:false,
    				  	    anim: 1,
    				  	  	content:[url+"cghtfk/cghtfkShow.do?cghtfkdm="+cghtfkdm,'yes']
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
      var cght = $('#cght').val();
      var fklx=$('#fklx').val();
      var ysqk=$('#ysqk').val();
      var fkje1=$('#fkje1').val();
      var fkje2=$('#fkje2').val();
      var sqfkrq1=$('#sqfkrq1').val();
      var sqfkrq2=$('#sqfkrq2').val();
      var fkrq1=$('#fkrq1').val();
      var fkrq2=$('#fkrq2').val();
      var spzt=$('#spzt').val();
      table.reload('testReload', {
          method: 'post', 
          where: {
              'cght': cght,
              'fklx':fklx,
              'ysqk':ysqk,
              'fkje1':fkje1,
              'fkje2':fkje2,
              'sqfkrq1':sqfkrq1,
              'sqfkrq2':sqfkrq2,
              'fkrq1':fkrq1,
              'fkrq2':fkrq2,
              'spzt':spzt 
          }, 
          page: {
              curr: 1
          }
      });
  });
});


//ajax加载所有的采购合同
function reloadCght(form){
	$.ajax({
		type : "post",
		url : "<c:url value='/cghtfk/allCght.do'/>",
		async : false,
		dataType : 'json',
		error : function() {
			alert("出错");
		},
		success : function(msg) {
			for (var i = 0; i < msg.length; i++) {
				$("#cght").append(
						"<option value='"+msg[i].pur_Order_Id+"'>"+ msg[i].purchaseOrderName +"</option>");
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