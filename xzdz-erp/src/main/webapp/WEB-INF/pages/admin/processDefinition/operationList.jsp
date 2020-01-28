<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>流程授权页面</title>
<link rel="stylesheet" href="../layui-v2.5.5/layui/css/layui.css">
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
<div style="width:1250px;height:auto;padding:0px; margin:0 auto;" id="main">
	<form class="layui-form" action="" method="post">
	<input type="hidden" id="url" value='<c:url value="/"/>'>
		<div class="layui-form-item layui-form-text">
			  <div class="layui-input-block">
				<table class="table table-bordered" id="lcsq" style="width: 100%;">
					<caption style="text-align: center;font-size: 20px;font-weight:bold;">${processName}节点列表</caption>
				  <thead>
				    <tr>
				      <th scope="col" style="text-align: center;width: 5%">序号</th>
				      <th scope="col" style="text-align: center;width: 45%">节点名称</th>
				      <th scope="col" style="text-align: center;width: 50%">角色</th>
				    </tr>
				    <c:forEach items="${list}" var="l">
				    	<tr>
				    		<th scope='row' style='text-align: center;line-height:38px;'></th>
				    		<td>
				    			<input type="hidden" name="operation_Id" value="${l.operation_Id}"> 
				    			<input type="hidden" name="s_Id" value="${l.s_Id}"> 
				    			<input type="hidden" name="procdef_Id" value="${l.procdef_Id}"> 
				    			<input type='text' class='form-control bj' aria-label='' aria-describedby='' name='activiti_Name' value="${l.activiti_Name}" disabled="">
				    		</td>
				    		<td>
				    			<input type="hidden" name="pId" value="${l.post_Id}"> 
				    			<div class="layui-input-inline" style="width:100%;text-align: left;">
									<select name="post_Id" id="post_Id" lay-filter="post_Id" lay-verify="post_Id" lay-search="">
										<option value="" selected="selected">请选择所属角色</option>
									</select>
								</div>
				    		</td>
				    	</tr>
				    </c:forEach>
				  </thead>
				  <tbody>
				  </tbody>
				</table>
			</div>
		</div>
		<div class="layui-form-item" style="text-align: center;">
		    <div class="layui-input-block">
		      <button class="layui-btn" lay-submit="" lay-filter="" style="width:25%;margin-top:10px;" onclick="editOpeantion()" type="button">立即提交</button>
		    </div>
		</div>
<script src="../bootstrap-3.3.7-dist/js/bootstrap.js"></script>
<script src="../layui-v2.5.5/layui/layui.js" charset="utf-8"></script>
<script>
	layui.use(['form', 'layedit', 'laydate','upload'], function(){
	  var form = layui.form
	  ,layer = layui.layer
	  ,layedit = layui.layedit
	  ,laydate = layui.laydate
	  ,upload = layui.upload;
	  var url=$('#url').val();
	  form.render();
	  //创建一个编辑器
	  var editIndex = layedit.build('LAY_demo_editor');
	  lcsqxh();
	  allPosts(form);
	  reloadRole(form);
	});

	function lcsqxh(){
		var len = $('table tr').length;
	    for(var i = 1;i<len;i++){
	        $('table tr:eq('+i+') th:first').text(i);
	    }
	     
	}

	//加载所有的未删除的岗位
	function allPosts(form){
		$.ajax({
			type : "post",
			url : "<c:url value='/post/queryAllPost.do'/>",
			async : false,
			dataType : 'json',
			error : function() {
				alert("出错");
			},
			success : function(msg) {
				for(var i=0;i<msg.length;i++){
						$("select").append(
					    	"<option value='"+msg[i].post_Id+"'>"+ msg[i].post_Name +"</option>"); 
				}
			}
		});
		 form.render('select');
	}

	//自动加载所属角色
	function reloadRole(form){
		//获取当前表格
		var tables=$('#lcsq');
		//获得表格所有行
		var rows=tables[0].rows;
		//遍历表格
		for(var i=1;i<rows.length;i++){
			//获取所选角色
			var pId=$('input[name="pId"]')[i-1].value;
			//获取当前行角色下拉选
			var roles=$('select[name="post_Id"]')[i-1];
			for(var j=1;j<roles.length;j++){
				if(roles[j].value==pId){
					roles[j].setAttribute("selected",'true');
					break;
				}
			}

		}
		form.render('select');
	}


	//编辑流程节点列表
	function editOpeantion(){
		//获取当前表格
		var tables=$('#lcsq');
		//获得表格所有行
		var rows=tables[0].rows;
		//创建流程授权数组
		var lcsqs=new Array();
		//遍历表格
		for(var i=1;i<rows.length;i++){
			//获得流程节点信息表格中已存在的主键
			var operation_Id=""
			if($('input[name="operation_Id"]')[i-1]!=undefined){
				operation_Id=$('input[name="operation_Id"]')[i-1].value;
			}
			//获取任务节点主键
			var taskId=$('input[name="s_Id"]')[i-1].value;
			//获取任务节点名称
			var taskName=$('input[name="activiti_Name"]')[i-1].value;
			//获取所选角色
			var post=$('select[name="post_Id"]')[i-1].value;
			if(post==""||post==undefined){
				return layer.alert("当前第"+i+"行未选择角色,请选择角色后方可提交!!!",{icon:7});
			}
			//获取流程部署Id
			var procdefId=$('input[name="procdef_Id"]')[i-1].value;
			//创建流程操作对象
			var lccz=new Object();
			lccz.operation_Id=operation_Id;
			lccz.s_Id=taskId;
			lccz.activiti_Name=taskName;
			lccz.post_Id=post;
			lccz.procdef_Id=procdefId;
			lcsqs.push(lccz);
		}
		$.ajax({
			type : "post",
			url : "<c:url value='/opeantion/empower.do'/>",
			async : false,
			contentType :"application/json;charsetset=UTF-8",//必须
			dataType : 'json',
			data:JSON.stringify(lcsqs),
			error : function() {
				alert("出错");
			},
			success : function(msg) {
				if(msg.flag){
					window.parent.opener.location.reload();
					window.close();
				} 
			}
		});
	}
	

	
</script>
</body>
</html>