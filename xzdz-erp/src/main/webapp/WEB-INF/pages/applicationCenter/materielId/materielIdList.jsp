<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>物料Id列表</title>
<link rel="stylesheet" href="../layui-v2.5.5/layui/css/layui.css">
<link rel="stylesheet" href="../login/css/static.css">
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@page isELIgnored="false" %>
</head>
<body>
<div  style="width:100%;">
	<input type="hidden" value='<c:url value="/"/>' id="url">
	<input type="hidden" id="flag" value="false">
	<input type="hidden" id="userIds">
	<div style="margin-top: 30px;width: 20%;float: left;">
		<span style="font-size: 20px;font-weight: 20px;">物料ID类型</span>
		<div id="test9" class="demo-tree demo-tree-box" style="width:268px;height:550px;background:#eee;overflow-y:auto;overflow-x:auto;"></div>
	</div>
	<div style="width:80%;float:right;margin-top: 20px;">
		<span style="font-size: 20px;font-weight: 20px;">物料ID</span>
		<form class="layui-form" action="" style="margin-top: 10px;">
		 <div class="demoTable" style="background-color: #CAE1FF;display: none;" id="gjssq">
			<div class="layui-form-item" style="width:1280px;height:auto;padding:0px; margin:0 auto;" id="main"">
			 <div class="layui-form-item">
				 <div class="layui-inline">
				      <label class="layui-form-label" style="width: 100px;">物料ID</label>
				      <div class="layui-input-inline">
				        <input type="text"  lay-verify="wlId" autocomplete="off" class="layui-input" style="width: 224px;" id="wlId">
				      </div>
			     </div>
			    
				<div class="layui-inline">
				      <label class="layui-form-label" style="width: 100px;">规格型号</label>
				      <div class="layui-input-inline">
				        <input type="text"  lay-verify="specification_Type" autocomplete="off" class="layui-input" style="width: 200px;" id="ggxh">
				      </div>
			     </div>
			     
			     <div class="layui-inline">
				      <label class="layui-form-label" style="width: 100px;">保质期</label>
				      <div class="layui-input-inline">
				        <input type="text"  lay-verify="bzq"
						autocomplete="off" class="layui-input" style="width: 200px;" id="bzq">
				      </div>
			     </div>
		 	</div>
		 	<div class="layui-form-item">
		 	
			 	<div class="layui-inline">
				      <label class="layui-form-label" style="width:100px;">参考单价</label>
				      <div class="layui-input-inline" style="width: 100px;">
				        <input type="text" id="ckj1" placeholder="￥" autocomplete="off" class="layui-input">
				      </div>
				      <div class="layui-form-mid">-</div>
				      <div class="layui-input-inline" style="width: 100px;">
				        <input type="text" id="ckj2" placeholder="￥" autocomplete="off" class="layui-input">
				      </div>
				 </div>
				 
			     <div class="layui-inline" style="left:-40px;">
				    <label class="layui-form-label" style="width: 100px;">物料ID类型</label>
				    <div class="layui-input-block" style="width:280px;" id="wlidType">
				     
				    </div>
			    </div>
			    
			    <div class="layui-inline" style="left:-80px;">
				  	<label class="layui-form-label" style="width:85px;">物料ID号</label>
					<div class="layui-input-inline" style="text-align: left;width: 201px;">
						<select name="materielNumber" id="materielNumber" lay-filter="materielNumber" lay-verify="materielNumber">
							<option value="" selected="selected">请选择物料Id号</option>
						</select>
					</div>
			 	</div>
			</div>
			
			<div class="layui-form-item">
			
				 <div class="layui-inline">
				      <label class="layui-form-label" style="width: 100px;">物料ID描述</label>
				      <div class="layui-input-inline">
				        <input type="text"  lay-verify="wlId" autocomplete="off" class="layui-input" style="width: 224px;" id="remarks">
				      </div>
			     </div>
			
			   <button class="layui-btn" data-type="reload" type="button" id="do_search" style="margin-left:130px;">搜索</button>
			   <button type="reset" class="layui-btn layui-btn-primary">重置</button>
			</div>
		</div> 
	 </div>
	</form>
		<table class="layui-hide" id="test" lay-filter="test"></table>
  </div>
</div>
<script type="text/html" id="toolbarDemo">
  <div class="layui-btn-container" style="width:40%;">
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
layui.use(['table','form','layedit', 'laydate','tree','util'], function(){
  var table = layui.table;
  var url=$('#url').val();
  var form= layui.form;
  var layer = layui.layer;
  var layedit = layui.layedit;
  var laydate = layui.laydate;
  var tree = layui.tree;
  var util = layui.util;
  initTree(layui,form,tree,table);
  reloadMaterielType(form);
  form.render();
  table.render({
    elem: '#test'
    ,url:url+'wlId/materielIdList.do'
    ,toolbar: '#toolbarDemo'
    ,title: '物料Id列表'
    ,totalRow: true
    ,cols: [[
       {type:'checkbox'}
      ,{field:'index', width:"5%", title: '序号', sort: true,type:'numbers',totalRowText: '合计'}
      ,{field:'materiel_Id', width:"15%",align:'center', title: '物料Id'}
      ,{field:'specification_Type', width:"15%", align:'center', title: '规格型号'}
      ,{field:'bzq', width:"11%", align:'center', title: '保质期'}
      ,{field:'ckdj', width:"10%", align:'right', title: '参考单价',totalRow: true}
      ,{field:'materielTypeName', width:"12%", align:'center', title: '物料Id类型'}
      ,{field:'materielNumberName', width:"12%", align:'center', title: '物料Id类型号'}
      ,{fixed: 'right', title:'操作', toolbar: '#barDemo', width:"16%",align:'center'}
    ]]
    ,id:'testReload'
    ,page: true
  });
  
  //头工具栏事件
  table.on('toolbar(test)', function(obj){
    var url=$('#url').val();
    var flag=$('#flag').val();
    if(obj.event=='getCheckData'){
    	var fp_Url="/materielId/initSaveMaterielId.do";
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
	    			      	  	title:'新增物料ID',
	    			      	  	area: ['100%','100%'],
	    			      	  	shadeClose: false,
	    			      		resize:false,
	    			      	    anim: 1,
	    			      	  	content:[url+"materielId/initSaveMaterielId.do",'yes']
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
    	var fp_Url="/materielId/batchDeletePost.do";
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
	    									 ids=ids+","+checkStatus.data[i].row_Id;
	    								 }else{
	    									 ids=checkStatus.data[i].row_Id;
	    								 }
	    							 }
	    							 //批量删除
	    							 $.ajax({
	    									type : "post",
	    									url : "<c:url value='/materielId/batchDeletePost.do'/>",
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
    	var fp_Url="/materielId/initFileImport.do";
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
	    			      	  	content:[url+"materielId/initFileImport.do",'yes']
   			    	 });
	    		}else{
					layer.alert("当前用户无此功能权限，请联系管理员授权!!!",{icon:7});
		    	}
	    	}
 		});
    }else if(obj.event=='dc'){
    	var fp_Url="/materielId/exportExcel.do";
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
	    						  location.href='<c:url value="/materielId/exportExcel.do"/>';
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
    var row_Id=data.row_Id;
   	if(obj.event === 'edit'){
   		var fp_Url="/materielId/initEditMaterielId.do";
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
	    	        	  	title:'编辑物料Id',
	    	        	  	area: ['100%','100%'],
	    	        		shadeClose: false,
	    	         		resize:false,
	    	         	    anim: 1,
	    	        	  	content:[url+"materielId/initEditMaterielId.do?row_Id="+row_Id,'yes']
	    	      	  	});
	    		}else{
					layer.alert("当前用户无此功能权限，请联系管理员授权!!!",{icon:7});
		    	}
	    	}
  		});
    }else if(obj.event==='del'){
    	var fp_Url="/materielId/deleteMaterielId.do";
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
	    								url : "<c:url value='/materielId/deleteMaterielId.do'/>",
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
		var fp_Url="/materielId/showMaterielId.do";
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
	    			  	  	title:'查看物料Id',
	    			  	  	area: ['100%','100%'],
	    			  		shadeClose: false,
	    			  		resize:false,
	    			  	    anim: 1,
	    			  	  	content:[url+"materielId/showMaterielId.do?row_Id="+row_Id,'yes']
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
	  var wlidlx="";
	  var materielType=$('input[name="materielType"]');
		for(var i=0;i<materielType.length;i++){
			if(materielType[i].checked){
				wlidlx=materielType[i].value;
				break;
			}
	  }
      var wlidh=$('#materielNumber').val();
      var wlId = $('#wlId').val();
      var ggxh=$('#ggxh').val();
      var bzq=$('#bzq').val();
      var ckj1=$('#ckj1').val();
      var ckj2=$('#ckj2').val();
      var remarks=$('#remarks').val();
      table.reload('testReload', {
          method: 'post', 
          where: {
              'wlId': wlId,
              'ggxh':ggxh,
              'bzq':bzq,
              'ckj1':ckj1,
              'ckj2':ckj2,
              'wlidlx':wlidlx,
              'wlidh':wlidh,
              'remarks':remarks
          }, 
          page: {
              curr: 1
          }
      });
  });

	//是否立项数据改变监听
	form.on('radio(is_LX)', function (data) {
			$('#materielNumber').html('');
			var id=data.value;
			//加载物料ID类型
			$.ajax({
				type : "post",
				url : "<c:url value='/materielId/reloadMaterielNumber.do'/>",
				async : false,
				dataType : 'json',
				data:{"id":id},
				error : function() {
					alert("出错");
				},
				success : function(msg) {
					for (var i = 0; i < msg.length; i++) {
						$("#materielNumber").append(
								"<option value='"+msg[i].id+"'>"+ msg[i].title +"</option>");
					}
					form.render('select');
				}
			});
	});
});

 function initTree(layui,form,tree,table){
	var layer = layui.layer;
	var url = $('#url').val();
	//初始化文件夹树
	  $.ajax({  
		    type: "post",  
		    url:  "<c:url value='/materielType/initMaterielType.do'/>",
		    dataType: 'json',
		    async:false,
		    error:function(){
		    	alert("出错");
		    },
		    success: function (data) { 
		        //开启节点操作图标
		    	tree.render({  
		            elem: '#test9',  
		            data: data,
		            edit: ['add', 'update', 'del'], //操作节点的图标
		            onlyIconControl:true,
		        	click: function(obj){
		        		//获得点击文件夹的主键
		        		var materielTypeId=obj.data.id;
		        		//执行表格重载
		        		  table.reload('testReload', {
					          method: 'post'
					          , where: {
					              'materielTypeId': materielTypeId,
					          }
					          , page: {
					              curr: 1
					          }
					      }); 
		            //layer.msg(JSON.stringify(obj.data));
		          },
		          operate: function(obj){
		        	    var type = obj.type; //得到操作类型：add、edit、del
		        	    var data = obj.data; //得到当前节点的数据
		        	    var elem = obj.elem; //得到当前节点元素
		        	    //Ajax 操作
		        	    var id = data.id; //得到节点索引
		        	    if(type === 'add'){ //增加节点
		        	    	var fp_Url="/materielType/initSaveMaterielType.do";
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
					    					type : 2,
					    					title : '物料Id类型新增',
					    					area : [ '50%', '60%' ],
					    					shadeClose : false,
					    					resize : false,
					    					anim : 1,
					    					content : [ url + "materielType/initSaveMaterielType.do?id="+id, 'yes' ]
					    				});
					    			}else{
					    				return layer.alert("无此功能权限，请联系管理员授权！",{icon:7});
					    			}
					    		}
					    	});
		        	    } else if(type === 'update'){ //修改节点
			        	    if(id!=undefined){
			        	    	var fp_Url="/materielType/initEditMaterielType.do";
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
						    					type : 2,
						    					title : '物料Id类型修改',
						    					area : [ '50%', '60%' ],
						    					shadeClose : false,
						    					resize : false,
						    					anim : 1,
						    					content : [ url + "materielType/initEditMaterielType.do?id="+id, 'yes' ]
						    				});
						    			}else{
						    				return layer.alert("无此功能权限，请联系管理员授权！",{icon:7});
						    			}
						    		}
						    	});
			        	    }
		        	    } else if(type === 'del'){ //删除节点
		        	    	var fp_Url="/materielType/deleteMaterielType.do";
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
  					    			   $.ajax({  
  					        	 		    type: "post",  
  					        	 		    url:  "<c:url value='/materielType/deleteMaterielType.do'/>",
  					        	 		    data:{"id":id},
  					        	 		    dataType: 'json',
  					        	 		    async:false,
  					        	 		    error:function(){
  					        	 		    	alert("出错");
  					        	 		    },
  					        	 		    success: function (data) {
	  					        	 		    if(data.flag){
		  					  			        	layer.close(index);
		  					        	 		 }else{
		  					        	 			layer.msg(data.infor);
					  					        }
  					        	 		    }  
  					        	 		});
  					    			}else{
  					    				return layer.alert("无此功能权限，请联系管理员授权！",{icon:7});
  					    			}
  					    		}
  					    	});
		        		  }
		        	  }
		        });  
		    }  
		});
	}


	//ajax加载所有的物料Id类型
	function reloadMaterielType(form){
		$.ajax({
			type : "post",
			url : "<c:url value='/materielId/reloadMaterielType.do'/>",
			async : false,
			dataType : 'json',
			error : function() {
				alert("出错");
			},
			success : function(msg) {
				for (var i = 0; i < msg.length; i++) {
					$("#wlidType").append(
							"<input type='radio' name='materielType' value='"+msg[i].id+"' title='"+ msg[i].title +"' lay-filter='is_LX'>");
				}
				form.render('radio');
			}
		});
	}
</script>
</body>
</html>