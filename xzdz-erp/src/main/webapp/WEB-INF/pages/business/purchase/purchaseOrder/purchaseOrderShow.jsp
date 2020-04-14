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
<title>查看采购合同</title>
<link rel="stylesheet" href="../layui-v2.5.5/layui/css/layui.css">
<link rel="stylesheet" href="../login/css/static.css">
<link href="../login/css/xshtfp.css" rel="stylesheet"/>
<link rel="stylesheet" href="../bootstrap-3.3.7-dist/css/bootstrap.min.css"> 
<link rel="stylesheet" href="../bootstrap-3.3.7-dist/css/bootstrap-theme.min.css"> 
<script src="../jquery/jquery-3.3.1.js"></script>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@page isELIgnored="false" %>
<style>
  .bj{background-color: #F0F0F0}
 </style>
</head>
<body style="width:100%;padding:0px; margin:0px;text-align: center;">
	<div class="layui-tab">
		  <ul class="layui-tab-title">
		    <li class="layui-this">基本信息</li>
		    <li>评审意见</li>
		    <li>任务附件</li>
		    <li>流程检视</li>
		    <li>报表打印</li>
		  </ul>
	   <div class="layui-tab-content">
		 <div class="layui-tab-item layui-show">
				<div style="width:1280px;height:auto;padding:0px; margin:0 auto;" id="main">
					<form class="layui-form" action='<c:url value=""/>' method="post">
						<input type="hidden" id="url" value='<c:url value="/"/>'>
						<input type="hidden" id="kpmb" value="false">
						<input type="hidden" value="${taskId }" id="taskId">
						<input type="hidden" id="cght" value="${purchaseOrder.pur_Order_Id}">
						<input type="hidden" id="processInstanceId" value="${processInstanceId}">
						<input type="hidden" id="OBJDM" value="${OBJDM}">
						
						<div class="layui-form-item" style="margin-top: 3%;">
						    <label class="layui-form-label" style="width: 120px;">合同名称</label>
						    <div class="layui-input-block">
						      <input type="text" lay-verify="" autocomplete="off" placeholder="合同名称" class="layui-input bj" style="width:72%" value="${purchaseOrder.purchaseOrderName}" disabled="">
						    </div>
						</div>
						
						<div class="layui-form-item">
						       <div class="layui-inline" style="left:20px;">
							  	<label class="layui-form-label" style="width:100px;">供货单位</label>
								 <div class="layui-input-inline">
							        <input type="text"   lay-verify="" autocomplete="off" class="layui-input bj" style="width: 200px;" disabled="" value="${purchaseOrder.supplierName}">
							      </div>
							 </div>
						     
						      <div class="layui-inline" style="left:350px;">
							      <label class="layui-form-label" style="width:100px;">订单编号</label>
							      <div class="layui-input-inline">
							        <input type="text"   lay-verify="" autocomplete="off" class="layui-input bj" style="width: 200px;" disabled="" value="${purchaseOrder.pur_Code}">
							      </div>
						     </div>
						 </div>
						 
						 <div class="layui-form-item">
						 
						 	<div class="layui-inline" style="left:20px;">
							      <label class="layui-form-label" style="width:100px;">电话</label>
							      <div class="layui-input-inline">
							        <input type="text"   lay-verify="" autocomplete="off" class="layui-input bj" style="width: 200px;" disabled="" value="${purchaseOrder.telPhone}">
							      </div>
						     </div>
						     
						     <div class="layui-inline" style="left:33px;">
							  	<label class="layui-form-label" style="width:100px;">收件人</label>
								<div class="layui-input-inline">
							        <input type="text"   lay-verify="" autocomplete="off" class="layui-input bj" style="width: 200px;" value="${purchaseOrder.sjr}" disabled="">
							     </div>
							 </div>
						     
						      <div class="layui-inline" style="left:-2px;">
							      <label class="layui-form-label" style="width: 139px;">订购日期</label>
							      <div class="layui-input-inline">
							        <input type="text"  lay-verify="" placeholder="yyyy-mm-dd" autocomplete="off" class="layui-input bj" style="width: 200px;" value="${purchaseOrder.dgrq}" disabled="">
							      </div>
						    </div>
						 </div>
						 
					  <div class="layui-form-item layui-form-text">
				  		<label class="layui-form-label" style="width:155px;">材料采购项</label>
				  		<br>
						  <div class="layui-input-block" style="top:15px;left: 10px;">
							<table class="table table-bordered" id="khlxrs" style="width:1170px;">
							  <thead>
							    <tr>
							      <th scope="col" style="text-align: center;width:100px;">序号</th>
							      <th scope="col" style="text-align: center;width: 250px;">品名</th>
							      <th scope="col" style="text-align: center;width: 250px;">型号</th>
							      <th scope="col" style="text-align: center;width: 250px;">物料Id</th>
							      <th scope="col" style="text-align: center;width: 100px;">单位</th>
							      <th scope="col" style="text-align: center;width: 150px;">数量</th>
							      <th scope="col" style="text-align: center;width: 150px;">单价</th>
							      <th scope="col" style="text-align: center;width: 150px;">金额</th>
							      <th scope="col" style="text-align: center;width: 200px;">交货日期</th>
							      <th scope="col" style="text-align: center;width: 200px;">图号</th>
							      <th scope="col" style="text-align: center;width: 300px;">备注</th>
							    </tr>
							  </thead>
							  <tbody>
							  	<c:forEach items="${purchaseList}" var="p">
									  <tr>
									  	<td scope='row' style='text-align: center;line-height:38px;'></td>
										<td><input type='text' class='form-control bj' aria-label='' aria-describedby='' value="${p.pro_Name}" disabled="" title="${p.pro_Name}"></td>
										<td><input type='text' class='form-control bj' aria-label='' aria-describedby=''  value="${p.model }" disabled="" title="${p.model}"></td>
										<td><input type='text' class='form-control bj' aria-label='' aria-describedby='' value="${p.materielId }" disabled="" title="${p.materielId }"></td>
										<td><input type='text' class='form-control bj' aria-label='' aria-describedby=''  value="${p.company}" disabled="" title="${p.company}"></td>
										<td><input type='text' class='form-control bj' aria-label='' aria-describedby=''  value="${p.sl}" disabled="" title="${p.sl}"></td>
										<td><input type='text' class='form-control bj' aria-label='' aria-describedby=''  value="${p.price}" disabled="" title="${p.price}"></td>
										<td><input type='text' class='form-control bj' aria-label='' aria-describedby='' value="${p.zje}" disabled="" title="${p.zje}" name="price"></td>
										<td><input type='text' class='form-control bj' aria-label='' aria-describedby=''  value="${p.delivery_date}" disabled="" title="${p.delivery_date}"></td>
										<td><input type='text' class='form-control bj' aria-label='' aria-describedby=''  value="${p.map_Number}" disabled="" title="${p.map_Number}"></td>
										<td><input type='text' class='form-control bj' aria-label='' aria-describedby=''  value="${p.bz}" disabled="" title="${p.bz}"></td>
									 </tr>
								  </c:forEach>
								  <tr>
								  		<td>
								  		</td>
								  		<td>
								  			合计总金额
								  		</td>
								  		<td colspan="9">
								  			<input type='text' class='form-control bj' aria-label='' aria-describedby='' style="width: 165px;" disabled="" id="totalprice">
								  		</td>
								  	</tr>
							  </tbody>
							</table>
						</div>
					</div>
						
					 <div class="layui-form-item layui-form-text">
					    <label class="layui-form-label" style="width:120px;">备注</label>
					    <div class="layui-input-block">
					      <textarea placeholder="请输入内容"   lay-verify="" id="remarks" class="layui-textarea bj" style="width:76.5%" disabled="">${purchaseOrder.remarks}</textarea>
					    </div>
					 </div>
					 
					  <div class="layui-form-item">
					    <label class="layui-form-label" style="width: 120px;">1.</label>
					    <div class="layui-input-block">
					      <input type="text" id="order_One"  lay-verify="" autocomplete="off" placeholder="" class="layui-input bj" style="width:76.5%" value="${purchaseOrder.order_One}" disabled="">
					    </div>
					  </div>
					  
					   <div class="layui-form-item">
					    <label class="layui-form-label" style="width: 120px;">2.</label>
					    <div class="layui-input-block">
					      <input type="text" id="order_Two" lay-verify="" autocomplete="off" placeholder="" class="layui-input bj" style="width:76.5%" value="${purchaseOrder.order_Two}" disabled="">
					    </div>
					  </div>
					  
					   <div class="layui-form-item">
					    <label class="layui-form-label" style="width: 120px;">3.</label>
					    <div class="layui-input-block">
					      <input type="text" id="order_Three" lay-verify="" autocomplete="off" placeholder="" class="layui-input bj" style="width:76.5%" value="${purchaseOrder.order_Three}" disabled="">
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
					   			
					   			<c:if test="${(not empty r.MESSAGE_RESULT_)&&(not empty r.MESSAGE_INFOR_)}">
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
				<table class="layui-hide" id="test" lay-filter="test"></table>
				<form id="downForm" method="post" action="" enctype="multipart/form-data">
					<input type="hidden" id="ftpPath">
					<input type="hidden" id="rEALWJM">
				</form>
			</div>
			<!--流程检视  -->
			<div class="layui-tab-item">
				<img style="position: absolute; top: 70px; left:10%;width:90%;" id="lct" src=''>
			</div>
			 <!--报表  -->
			<div class="layui-tab-item">
 				<iframe  src=''  width="100%" height="100%" frameborder="no" border="0" marginwidth="0" marginheight=" 0" scrolling="no" allowtransparency="yes" onload="changeFrameHeight(this)"></iframe>			
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
  <!-- 操作 End -->
   	<div class="m-operation" style="width:180px;height:100%;" id="mb">
		<h2 style="width: 150px;">操作</h2>
		<span id="cghtfkAppend" style="width: 170px;">采购合同付款</span>
		<i id="caoZuo">操作</i>
		<em id="fanHui"></em>
	</div>
<script type="text/html" id="barDemo">
  <!--<a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="yl" name="defaultAD">预览</a>-->
  <a class="layui-btn layui-btn-xs" lay-event="xz">下载</a>
</script>
<script src="../bootstrap-3.3.7-dist/js/bootstrap.js"></script>
<script src="../layui-v2.5.5/layui/layui.js" charset="utf-8"></script>
<script type="text/javascript" src="../jquery-easyui-1.7.0/jquery.easyui.min.js"></script>
<script>
layui.use(['form', 'layedit', 'laydate','upload','element','table'], function(){
  var form = layui.form
  ,layer = layui.layer
  ,layedit = layui.layedit
  ,laydate = layui.laydate
  ,upload = layui.upload;
  var element = layui.element;
  var url=$('#url').val();
  var table = layui.table;
  var OBJId=$('#OBJDM').val();
  var objId=$('#taskId').val();
  $('#mb').width(0);
  khlxrxh();
  bbsrc();
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

	//设置报表src
	function bbsrc(){
		var id=$('#cght').val();
		 $('iframe').attr('src','http://192.168.0.103:8080/WebReport/ReportServer?reportlet=cgdd.cpt&id='+id);
	}
	
	function changeFrameHeight(that){
	    //电脑屏幕高度-iframe上面其他组件的高度
	    //例:我这里iframe上面还有其他一些div组件，一共的高度是120，则减去120
	    $(that).height(document.documentElement.clientHeight - 90);
	    
	}
	function khlxrxh(){
		var len = $('#khlxrs tr').length;
		var totalPrice=0;
	    for(var i = 1;i<len-1;i++){
	        $('table tr:eq('+i+') td:first').text(i);
	    }
	    var prices=$('input[name="price"]');
	    for(var i=0;i<prices.length;i++){
	    	totalPrice=(totalPrice*1)+(prices[i].value*1);
		}
		$('#totalprice').val(totalPrice);
	}

	 $('#caoZuo').click(function(){
		 var flag=$('#kpmb').val();
		 if(flag=='false'){
			 $('#mb').animate({width:'180px'});
			 $('#kpmb').val(true);
		 }else{
			 $('#mb').animate({width:'0px'});
			 $('#kpmb').val(false);
		 }
	 });

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
							       	  	area: ['50%','50%'],
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
			 var cghtId=$('#cght').val();
			 layer.open({
		       	  	type:2,
		       	  	title:'放弃流程',
		       	  	area: ['40%','50%'],
		       		shadeClose: false,
		       		resize:false,
		       	    anim: 4,
		       	 	content:[url+"fqlc/initFqlc.do?taskId="+taskId+"&objId="+cghtId,'yes']
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
				 $('#myMenu').show();
			}else{
				$('#myMenu').hide();
			}
		}

		 $('#cghtfkAppend').click(function(){
			 var cght=$('#cght').val();
				var url=$('#url').val();
				var fp_Url="/cghtfk/initSaveCghtfk.do";
				$.ajax({
					type : "post",
					url : "<c:url value='/cghtfk/checkCght.do'/>",
					async : false,
					dataType : 'json',
					data:{"cght":cght},
					error : function() {
						alert("出错");
					},
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
				    	    				parent.layer.open({
									       	  	type:2,
									       	  	title:'采购合同付款',
									       	  	area: ['100%','100%'],
									       		shadeClose: false,
									       		resize:false,
									       	    anim: 4,
									       	 	content:[url+"cghtfk/initSaveCghtfk.do?cght="+cght,'yes']
									    	 });
				    	    		}else{
				    					layer.alert("当前用户无此功能权限，请联系管理员授权!!!",{icon:7});
				    		    	}
				    	    	}
				    		});
						}
					}
				});
		 });
</script>
</body>
</html>