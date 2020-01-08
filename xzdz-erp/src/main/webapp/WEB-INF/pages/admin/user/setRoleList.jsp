<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>角色授予</title>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@page isELIgnored="false"%>
<link rel="stylesheet" type="text/css" href="../jquery-easyui-1.7.0/themes/default/easyui.css"/>
<link rel="stylesheet" type="text/css" href="../jquery-easyui-1.7.0/themes/icon.css"/>
<script type="text/javascript" src="../jquery-easyui-1.7.0/jquery.min.js"></script>
<script type="text/javascript" src="../jquery-easyui-1.7.0/jquery.easyui.min.js"></script>
<link rel="stylesheet" href="../layui-v2.5.5/layui/css/layui.css">
</head>
<body onload="refreshAndClose()">
	<div>
		<span style="color: red">&nbsp;&nbsp;正在为【${userName}】配置角色</span>
	</div>
	<form id="form" method="post">
		<div style="padding-bottom: 10%; border: 1px solid #000; background: #FFFACD" class="layui-form-item">
			<!-- <span><h2 style="text-align: center;">权限机构</h2></span>-->	
			<input type="hidden" id="selectId" value="" name="selectId">
			<input type="hidden" id="user_id" value="${user_id}" name="userId">
			<input type="hidden" id="flag" value="${flag}">
			<input type="hidden" value='<c:url value="/"/>' id="url">	
			<div id="tree" style="width:30%;margin-top: 2%">
				全选:<input type="checkbox" id="allCheckked" onchange="allSelect()">
				<ul id="tt"></ul>
			</div>
			<div class="layui-form-item" style="text-align: center;">
				<div class="layui-input-block">
					<button class="layui-btn" lay-submit="" lay-filter="demo1"
						style="width: 35%; margin-top: 10px;margin-left: -105px;" type="button" onclick="setRole()">立即提交</button>
				</div>
			</div>
		</div>
	</form>
	<script src="../layui-v2.5.5/layui/layui.js" charset="utf-8"></script>
	<script>
	layui.use(['form', 'layedit', 'laydate'], function(){
	  var form = layui.form
	  ,layer = layui.layer
	  ,layedit = layui.layedit
	  ,laydate = layui.laydate;
	  form.render();
	  initTree();
});
	
	function initTree(){
		//初始化角色
		  $.ajax({  
			    type: "post",  
			    url:  "<c:url value='/userRole/allRoleList.do'/>",
			    dataType: 'json',
			    async:false,
			    error:function(){
			    	alert("出错");
			    },
			    success: function (data) {  
			    	$("#tt").tree({  
			           	data:data,
			           	dnd: false, //可拖放
			    		lines: true, //显示树线条
			    		checkbox: true, //显示复选框
			    		animate: true,//展开动画效果
			    		cascadeCheck:false,
			    		onLoadSuccess:function(node,data){//数据加载成功出发
			    			var userId=$('#user_id').val();
			    			$.ajax({  
			    			    type: "post",  
			    			    url:  "<c:url value='/userRole/defaultRole.do'/>",
			    			    dataType: 'json',
			    			    async:false,
			    			    data:{"userId":userId},
			    			    error:function(){
			    			    	//alert("出错");
			    			    },
			    			    success: function (msg) { 
			    			    	//遍历结果集
			    			    	for(var i=0;i<msg.length;i++){
			    			    		//根据结果集中的权限主键去查询对应的树结构中的节点并设置为选中
			    			    		var node = $('#tt').tree('find', msg[i].role_Id);
			    			    		//node.checkState='checked';
			    			    		$('#tt').tree("check",node.target);
			    			    	}
			    			    }  
			    			});
			    		}
			        });  
			    }  
			});
	}
			
	//复选框选中
	  function allSelect(){
		var select=document.getElementById("allCheckked");
		var flag=select.checked;
		//得到树中的所有子节点
		var nodes = $("#tt").tree("getChildren");
		if(flag){
				//遍历所有节点 并设置为选中
				for(var i=0;i<nodes.length;i++){
					$('#tt').tree("check",nodes[i].target);
				}
		}else{
				//遍历所有节点 并设置为不选中
				for(var i=0;i<nodes.length;i++){
					$('#tt').tree("uncheck",nodes[i].target);
				}
		}
	}
	
	//获取所有选中的节点数据
	function setRole(){
		//获取当前表单
		var form=document.getElementById('form');
		var url=$('#url').val();
		//获取所有选中的节点
		var allChildren=$("#tt").tree('getChecked','checked');
		//用于存储所有选中节点的主键
		var selectId=$('#selectId').val();
		//遍历所有的子节点
		for(var i=0;i<allChildren.length;i++){
			//得到所有的子节点主键 用于拼接在字符串中
			 if(undefined!=selectId){
				 var childrenId=allChildren[i].id;
					 selectId=selectId+","+childrenId;
			 }else{
					selectId=childrenId;
			 }
		}
		$('#selectId').val(selectId);
		var ids=$('#selectId').val()
		var allRoleIds=ids.substring(1,ids.length);
		$('#selectId').val(allRoleIds);
		//提交表单
		form.action=url+"userRole/setUserRole.do";
		form.submit();
	}
	
	function refreshAndClose(){
		var flag=$('#flag').val();
		if(flag){
			window.parent.location.reload();
			window.close();
		}
	}
</script>	
</body>
</html>