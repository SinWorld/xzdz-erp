<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>销售合同收款列表</title>
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
			  	<label class="layui-form-label">销售合同</label>
				<div class="layui-input-inline" style="text-align: left;width: 224px;">
					<select name="xsht" id="xsht" lay-filter="xsht" lay-verify="xsht" lay-search="">
						<option value="" selected="selected">请选择销售合同</option>
					</select>
				</div>
			</div>
			
			 <div class="layui-inline" style="left: -21px;">
			      <label class="layui-form-label" style="width: 100px;">是否发票开具</label>
			      <div class="layui-input-inline" style="text-align: left;width: 224px;">
					<select name="fpkj" id="fpkj" lay-filter="fpkj" lay-verify="fpkj" lay-search="">
						<option value="" selected="selected">请选择发票开具</option>
						<option value="1">是</option>
						<option value="0">否</option>
					</select>
				</div>
		     </div>
		    
			<div class="layui-inline" style="left: -34px;">
			      <label class="layui-form-label" style="width: 80px;">发票类别</label>
			      <div class="layui-input-inline" style="text-align: left;width: 224px;">
					<select name="fplb" id="fplb" lay-filter="fplb" lay-verify="fplb" lay-search="">
						<option value="" selected="selected">请选择发票类别</option>
						<option value="1">增值税专用发票</option>
						<option value="0">增值税普通发票</option>
					</select>
				</div>
		     </div>
		     
	 	</div>
		
		<div class="layui-form-item">
		
		    <div class="layui-inline">
		      <label class="layui-form-label" style="width:79px;">发票金额</label>
		      <div class="layui-input-inline" style="width: 100px;">
		        <input type="text" id="fpje1" placeholder="￥" autocomplete="off" class="layui-input">
		      </div>
		      <div class="layui-form-mid">-</div>
		      <div class="layui-input-inline" style="width: 100px;">
		        <input type="text" id="fpje2" placeholder="￥" autocomplete="off" class="layui-input">
		      </div>
		    </div>
		    
		     <div class="layui-inline">
		      <label class="layui-form-label" style="width:80px;">应收款</label>
		      <div class="layui-input-inline" style="width: 100px;">
		        <input type="text" id="ysk1" placeholder="￥" autocomplete="off" class="layui-input">
		      </div>
		      <div class="layui-form-mid">-</div>
		      <div class="layui-input-inline" style="width: 100px;">
		        <input type="text" id="ysk2" placeholder="￥" autocomplete="off" class="layui-input">
		      </div>
		    </div>
			
			<div class="layui-inline" style="left: -13px;">
		      <label class="layui-form-label">实际收款</label>
		      <div class="layui-input-inline" style="width: 100px;">
		        <input type="text" id="sjsk1" placeholder="￥" autocomplete="off" class="layui-input">
		      </div>
		      <div class="layui-form-mid">-</div>
		      <div class="layui-input-inline" style="width: 100px;">
		        <input type="text" id="sjsk2" placeholder="￥" autocomplete="off" class="layui-input">
		      </div>
		    </div>
		    
		   </div>
		   
		    <div class="layui-form-item">
		    
		    <div class="layui-inline" style="left: -11px;">
		      <label class="layui-form-label" style="width: 90px;">开票日期</label>
		      <div class="layui-input-inline" style="width: 100px;">
	        	<input type="text"  id="date" lay-verify="date" placeholder="yyyy-MM-dd" autocomplete="off" class="layui-input">
		      </div>
		      <div class="layui-form-mid">-</div>
		      <div class="layui-input-inline" style="width: 100px;">
	            <input type="text"  id="date2" lay-verify="date" placeholder="yyyy-MM-dd" autocomplete="off" class="layui-input">
		      </div>
		    </div>
		    
		     <div class="layui-inline" style="width:345px;left: -11px;">
			  	<label class="layui-form-label">审批状态</label>
				<div class="layui-input-inline" style="text-align: left;width: 224px;">
					<select name="spzt" id="spzt" lay-filter="spzt" lay-verify="spzt" lay-search="">
						<option value="" selected="selected">请选择审批状态</option>
					</select>
				</div>
			</div>
		    
			<button class="layui-btn" data-type="reload" type="button" id="do_search" style="margin-left:85px;">搜索</button>
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

<script type="text/html" id="barDemo">
  <a class="layui-btn layui-btn-xs" lay-event="edit">补款</a>
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
  laydate.render({
		 elem: '#date'
	  });
	  laydate.render({
		 elem: '#date2'
  });
  reloadXsht(form);
  reloadSpzt(form);
  form.render();
  table.render({
    elem: '#test'
    ,url:url+'xshtsk/xshtskList.do'
    ,toolbar: '#toolbarDemo'
    ,title: '销售合同收款列表'
    ,totalRow: true
    ,cols: [[
       {field:'index', width:"8%", title: '序号', sort: true,type:'numbers' ,totalRowText: '合计'}
      ,{field:'xshtName', width:"17%",align:'center', title: '销售合同'}
      ,{field:'sfkpkj', width:"9%", align:'center', title: '是否发票开具'}
      ,{field:'fplb', width:"10%", align:'center', title: '发票类别'}
      ,{field:'fpje', width:"8%", align:'right', title: '发票金额', totalRow: true}
      ,{field:'ysk', width:"8%", align:'right', title: '应收款', totalRow: true}
      ,{field:'sjsk', width:"8%", align:'right', title: '实际收款',  totalRow: true}
      ,{field:'kprq', width:"12%",align:'center', title: '开票日期',templet:'<div>{{ layui.util.toDateString(d.kprq, "yyyy-MM-dd") }}</div>'}
      ,{field:'approvalmc', width:"10.3%", align:'center', title: '审批状态'}
      ,{fixed: 'right', title:'操作', toolbar: '#barDemo', width:"10%",align:'center'}
      
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
  

  //监听行工具事件
  table.on('tool(test)', function(obj){
    var data = obj.data;
    var url=$('#url').val();
    var xshtskdm=data.xshtskdm;
   	if(obj.event === 'edit'){
   		var fp_Url="/xshtsk/initBk.do";
   		$.ajax({
			type : "post",
			url : "<c:url value='/xshtsk/checkbk.do'/>",
			async : false,
			dataType : 'json',
			error : function() {
				alert("出错");
			},
			data:{"xshtskdm":xshtskdm},
			success : function(msg) {
				if(msg.flag){
					return layer.alert(msg.infor,{icon:7});
				}else{
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
						        	  	title:'补款',
						        	  	area: ['40%','45%'],
						        		shadeClose: false,
						         		resize:false,
						         	    anim: 1,
						        	  	content:[url+"xshtsk/initBk.do?xshtskdm="+xshtskdm,'yes']
						      	  	});
				    		}else{
								layer.alert("当前用户无此功能权限，请联系管理员授权!!!",{icon:7});
					    	}
				    	}
			  		});
				}
			}
		});
    }else if(obj.event==='detail'){
    	var fp_Url="/xshtsk/xshtskShow.do";
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
	    		      	  	title:'查看销售合同收款',
	    		      	  	area: ['100%','100%'],
	    		      		shadeClose: false,
	    		      		resize:false,
	    		      	    anim: 1,
	    		      	  	content:[url+"xshtsk/xshtskShow.do?xshtskdm="+xshtskdm,'yes']
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
      var xsht = $('#xsht').val();
      var fpkj=$('#fpkj').val();
      var fplb=$('#fplb').val();
      var fpje1=$('#fpje1').val();
      var fpje2=$('#fpje2').val();
      var ysk1=$('#ysk1').val();
      var ysk2=$('#ysk2').val();
      var sjsk1=$('#sjsk1').val();
      var sjsk2=$('#sjsk2').val();
      var beginTime=$('#date').val();
      var endTime=$('#date2').val();
      var spzt=$('#spzt').val();
      table.reload('testReload', {
          method: 'post', 
          where: {
              'xsht': xsht,
              'fpkj':fpkj,
              'fplb':fplb,
              'fpje1':fpje1,
              'fpje2':fpje2,
              'ysk1':ysk1,
              'ysk2':ysk2,
              'sjsk1':sjsk1,
              'sjsk2':sjsk2,
              'beginTime':beginTime,
              'endTime':endTime,
              'spzt':spzt,
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