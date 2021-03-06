<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8">
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">
<title>查看物料Id</title>
<link rel="stylesheet" href="../layui-v2.5.5/layui/css/layui.css">
<link rel="stylesheet" href="../login/css/static.css">
<link rel="stylesheet" href="../bootstrap-3.3.7-dist/css/bootstrap.min.css"> 
<link rel="stylesheet" href="../bootstrap-3.3.7-dist/css/bootstrap-theme.min.css"> 
<script src="../jquery/jquery-3.3.1.js"></script>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@page isELIgnored="false" %>
<style>
  .bj{background-color: #F0F0F0}
 </style>
</head>
<body style="width:100%;padding:0px; margin:0px;">
	<div class="layui-tab">
		  <ul class="layui-tab-title">
		    <li class="layui-this">基本信息</li>
		    <li>评审意见</li>
		    <li>任务附件</li>
		    <li>流程检视</li>
		    <li>流程图</li>
		  </ul>
    <div class="layui-tab-content">
		<div class="layui-tab-item layui-show">
			<div style="width:1100px;height:auto;padding:0px; margin:0 auto;" id="main">
				<form class="layui-form" action='' method="post" id="downForm" enctype="multipart/form-data">
					<input type="hidden" id="url" value='<c:url value="/"/>'>
					<input type="hidden" id="flag" value="${flag}">
					<input type="hidden" name="row_Id" value="${materielId.row_Id}">
					<input type="hidden" id="OBJDM" value="${OBJDM}">
					<input type="hidden" id="processInstanceId" value="${processInstanceId}">
					<input type="hidden" value="${taskId}" id="taskId">
					
					<div class="layui-form-item" style="margin-top:5%;">
					     <div class="layui-inline" style="top:9px;">
						      <label class="layui-form-label" style="width: 90px;">物料Id</label>
						      <div class="layui-input-inline">
						        <input type="text" name="materiel_Id" lay-verify="materiel_Id" autocomplete="off" class="layui-input bj" id="materiel_Id" value="${materielId.materiel_Id}" disabled="">
						      </div>
					     </div>
					     
					    <div class="layui-inline" style="top:9px;">
						      <label class="layui-form-label" style="width: 90px;">规格型号</label>
						      <div class="layui-input-inline">
						        <input type="text" name="specification_Type" lay-verify="specification_Type" autocomplete="off" class="layui-input bj" id="specification_Type" disabled="" value="${materielId.specification_Type}">
						      </div>
					    </div>
					    
					     <div class="layui-inline" style="top:9px;">
						      <label class="layui-form-label" style="width:150px;">保质期</label>
						      <div class="layui-input-inline">
						        <input type="text" name="bzq" lay-verify="bzq" autocomplete="off" class="layui-input bj" id="bzq" value="${materielId.bzq}" disabled="">
						      </div>
					     </div>
					 </div>
					
					<div class="layui-form-item">
					     <div class="layui-inline" style="left: -16px;">
						      <label class="layui-form-label" style="width:105px;">参考单价</label>
						      <div class="layui-input-inline">
						        <input type="text" name="ckdj" lay-verify="ckdj" autocomplete="off" class="layui-input bj" id="ckdj" value="${materielId.ckdj}" disabled="">
						      </div>
					     </div>
					     
					  	 <div class="layui-inline" style="left: -30px;">
						      <label class="layui-form-label" style="width:105px;">物料Id类型</label>
						      <div class="layui-input-inline">
						        <input type="text" name="ckdj" lay-verify="ckdj" autocomplete="off" class="layui-input bj" id="ckdj" value="${materielId.materielTypeName}" disabled="">
						      </div>
					     </div>
					     
					     <div class="layui-inline" style="left: 15px;">
						      <label class="layui-form-label" style="width:105px;">物料Id号</label>
						      <div class="layui-input-inline">
						        <input type="text" name="ckdj" lay-verify="ckdj" autocomplete="off" class="layui-input bj" id="ckdj" value="${materielId.materielNumberName}" disabled="">
						      </div>
					     </div>
				   </div>
				   
				   <div class="layui-form-item">
					     <div class="layui-inline" style="left: -16px;">
						      <label class="layui-form-label" style="width:105px;">录入日期</label>
						      <div class="layui-input-inline">
						        <input type="text" name="lrrq" lay-verify="lrrq" autocomplete="off" class="layui-input bj" id="lrrq" value="${lrrq}" disabled="">
						      </div>
					     </div>
				   </div>
				   
					
				 <div class="layui-form-item layui-form-text">
				    <label class="layui-form-label" style="width:100px;">物料描述</label>
				    <div class="layui-input-block">
				      <textarea placeholder="请输入内容" name="remarks"  lay-verify="remarks" id="remarks" class="layui-textarea bj" style="width:86.6%;left: -20px;" disabled="">${materielId.remarks}</textarea>
				    </div>
				 </div>
				 
			</form>
		 </div>
	</div>
	
		<!--评审意见  -->
			<div class="layui-tab-item">
				<div class="layui-collapse">
					<c:forEach items="${reviewOpinions}" var="r">
						 <div class="layui-colla-item" >
				   			<h2 class="layui-colla-title">${r.time}&nbsp;&nbsp;&nbsp;${r.TASK_NAME_}&nbsp;&nbsp;&nbsp;${r.userName}---->${r.TITLE_}</h2>
					   			<c:if test="${(not empty r.MESSAGE_RESULT_)||(not empty r.MESSAGE_INFOR_)}">
								    <div class="layui-colla-content layui-show">
								    		审批结果:<span style="color: green">${r.MESSAGE_RESULT_ }</span> </br>
											审批意见:<span style="color: green">${r.MESSAGE_INFOR_ }</span>
								    </div>
							   	</c:if> 
				 		 </div>
				 	</c:forEach>
				</div>
			</div>
		
			<!--附件  -->
			<div class="layui-tab-item">
				<p>任务附件</p>
				 <div class="layui-input-inline" style="width:100%;">
					<table class="layui-hide" id="test" lay-filter="test"></table>
				 </div>
				<form id="downForm" method="post" action="" enctype="multipart/form-data">
					<input type="hidden" id="ftpPath">
					<input type="hidden" id="rEALWJM">
				</form>
			</div>
			
			<!--流程检视  -->
			<div class="layui-tab-item">
			 	 <div class="layui-input-inline" style="width:100%;">
					<table class="layui-hide" id="lcjs" lay-filter="lcjs"></table>
				 </div>
			</div>
			
			<!--流程图 -->
			<div class="layui-tab-item">
				<img style="position: absolute; top:100px; left:10%;width:90%;" id="lct" src=''>
			</div>
		</div>
	</div>
	
	<!-- 操作 Begin -->
			<div id="myMenu" class="m-operation_box" style="width: 140px;display: none;">
				<h3 class="u-operation_title">操作</h3>
				<div>
					<ul class="u-menu_option">
						<li id="_zxys_deal_btn">处理</li>
						<li id="_zxys_retake_btn">退回</li> 
						<li id="_zxys_transmit_btn">转交</li>
						<!-- <li id="_zxys_reject_btn">退回上一步</li> -->
						<li id="_zxys_gaveUp_btn">放弃流程</li>
						<li id="_zxys_restart_btn">重启流程</li>
					</ul>
				</div>
			</div>
		<!-- 操作 End -->
<script type="text/html" id="barDemo">
  <!--<a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="yl" name="defaultAD">预览</a>-->
  <a class="layui-btn layui-btn-xs" lay-event="xz">下载</a>
</script>
<script src="../bootstrap-3.3.7-dist/js/bootstrap.js"></script>
<script src="../layui-v2.5.5/layui/layui.js" charset="utf-8"></script>
<script type="text/javascript" src="../jquery-easyui-1.7.0/jquery.easyui.min.js"></script>
<script>
layui.use(['form', 'layedit', 'laydate','upload','table','element'], function(){
  var form = layui.form
  ,layer = layui.layer
  ,layedit = layui.layedit
  ,laydate = layui.laydate
  ,upload = layui.upload;
  var url=$('#url').val();
  var table = layui.table;
  var OBJId=$('#OBJDM').val();
  var objId=$('#taskId').val();
  var element = layui.element;
  lct();
  menue();
  form.render();
 
  //创建一个编辑器
  var editIndex = layedit.build('LAY_demo_editor');
  table.render({
	    elem: '#test'
	    ,url:url+'enclosure/enclosureList.do?OBJDM='+OBJId
	    ,title: '任务附件'
	    ,cols: [[
	       {field:'index', width:"10%", title: '序号', sort: true,type:'numbers'}
	      ,{field:'REALWJM', width:"80%",align:'left', title: '文件名称'}
	      ,{fixed: 'right', title:'操作', toolbar: '#barDemo', width:"10%",align:'center'}
	    ]]
	  });

  table.render({
	    elem: '#lcjs'
	    ,url:url+'processView/processViewList.do?OBJDM='+OBJId
	    ,title: '流程检视'
	    ,cols: [[
	       {field:'index', width:"5%", title: '序号', sort: true,type:'numbers'}
	      ,{field:'nodeName', width:"15%",align:'left', title: '节点名称'}
	      ,{field:'processingUsers', width:"30%",align:'left', title: '办理用户组'}
	      ,{field:'nodeInfo', width:"20%",align:'left', title: '办理详情'}
	      ,{field:'beginTime', width:"15%",align:'left', title: '开始时间'}
	      ,{field:'endTime', width:"15%",align:'left', title: '结束时间'}
	    ]]
	  });

  //监听行工具事件
  table.on('tool(test)', function(obj){
    var data = obj.data;
    //存储在ftp服务器端的地址
    var ftpPath=data.SHANGCHUANDZ;
    //存储在ftp的真实文件名
    var rEALWJM=data.REALWJM;
    var url=$('#url').val();
    $('#ftpPath').val(ftpPath);
    $('#rEALWJM').val(rEALWJM);
	var form = document.getElementById('downForm');
    //console.log(obj)
    if(obj.event === 'xz'){
    	//下载文件
    	form.action=url+"sales/downloadFtpFile.do?ftpPath="+ftpPath+"&"+"rEALWJM="+rEALWJM;
    	form.submit();
    }
  });
  
});

	$("#myMenu").draggable();

	 function lct(){
	 	var img=$('#lct');
	 	var processInstanceId=$('#processInstanceId').val();
	 	var url=$('#url').val();
	 	img.attr("src",url+"viewImage/graphHistoryProcessInstance.do?processInstanceId="+processInstanceId)
	  }



	//点击处理跳转相应页面
	 $('#_zxys_deal_btn').click(function(){
			 var url=$('#url').val();
			 var taskId=$('#taskId').val();
			 $.ajax({
					type : "post",
					url : "<c:url value='/myTask/dealWith.do'/>",
					async : false,
					dataType : 'json',
					data:{"taskId":taskId},
					error : function() {
						alert("出错");
					},
					success : function(msg) {
						if(msg.flag){
							return layer.alert(msg.infor,{icon:7});
						}else{
							var result=msg.result;
							var objId=msg.business_key;
							var task_Id=msg.taskId;
							var narrow=msg.narrow;
							if(narrow){
								parent.layer.open({
							       	  	type:2,
							       	  	title:msg.taskName,
							       	  	area: ['60%','70%'],
							       		shadeClose: false,
							       		resize:false,
							       	    anim: 4,
							       	 	content:[url+result+"?objId="+objId+"&taskId="+task_Id,'yes']
							     });
							}else{
								parent.layer.open({
						       	  	type:2,
						       	  	title:msg.taskName,
						       	  	area: ['100%','100%'],
						       		shadeClose: false,
						       		resize:false,
						       	    anim: 4,
						       	 	content:[url+result+"?objId="+objId+"&taskId="+task_Id,'yes']
						    	 });
							}
						}
					}
				});
		 });


	//点击放弃流程跳转相应页面
	 $('#_zxys_transmit_btn').click(function(){
			 var url=$('#url').val();
			 var taskId=$('#taskId').val();
			 layer.open({
		       	  	type:2,
		       	  	title:'选择用户',
		       	  	area: ['60%','60%'],
		       		shadeClose: false,
		       		resize:false,
		       	    anim: 4,
		       	 	content:[url+"zj/initZhuanJiao.do?taskId="+taskId,'yes']
		     });
		 });

	//点击转交跳转相应页面
	 $('#_zxys_gaveUp_btn').click(function(){
			 var url=$('#url').val();
			 var taskId=$('#taskId').val();
			 var xshtskdm=$('#xshtskdm').val();
			 layer.open({
		       	  	type:2,
		       	  	title:'放弃流程',
		       	  	area: ['40%','50%'],
		       		shadeClose: false,
		       		resize:false,
		       	    anim: 4,
		       	 	content:[url+"fqlc/initFqlc.do?taskId="+taskId+"&objId="+xshtskdm,'yes']
		     });
		 });

	 //点击重启流程
	 $('#_zxys_restart_btn').click(function(){
			 var url=$('#url').val();
			 var taskId=$('#taskId').val();
			 layer.open({
		       	  	type:2,
		       	  	title:'重启流程',
		       	  	area: ['40%','30%'],
		       		shadeClose: false,
		       		resize:false,
		       	    anim: 4,
		       	 	content:[url+"cqlc/initCqlc.do?taskId="+taskId,'yes']
		     });
		 });

		//点击退回操作
		$('#_zxys_retake_btn').click(function(){
				 var url=$('#url').val();
				 var taskId=$('#taskId').val();
				 layer.open({
			       	  	type:2,
			       	  	title:'退回流程',
			       	  	area: ['40%','50%'],
			       		shadeClose: false,
			       		resize:false,
			       	    anim: 4,
			       	 	content:[url+"takeBack/initTakeBack.do?taskId="+taskId,'yes']
			     });
		 });

		//显示/隐藏操作面板
		function menue(){
			//获得任务Id
			var taskId=$('#taskId').val();
			if(taskId!=""){
				 $('#myMenu').css('display','block');
			}else{
				 $('#myMenu').css('display','none');
			}
		}

		//点击销售合同名称跳转至销售合同查看页
		$('#_field_xiaoShouHTMC').click(function(){
			 var url=$('#url').val();
			 var sales_Contract_Id=$('#sales_Contract_Id').val();
			 parent.layer.open({
			  	  	type:2,
			  	  	title:'查看合同',
			  	  	area: ['100%','100%'],
			  		shadeClose: false,
			  		resize:false,
			  	    anim: 1,
			  	  	content:[url+"sales/salesShow.do?sales_Contract_Id="+sales_Contract_Id,'yes']
			});
		});

		 function ycth(){
				var ldsh=$('#ldsh').val();
				if(ldsh){
					$('#_zxys_retake_btn').hide();
				}
		 }
</script>
</body>
</html>