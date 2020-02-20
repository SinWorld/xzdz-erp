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
<title>编辑物料Id</title>
<link rel="stylesheet" href="../layui-v2.5.5/layui/css/layui.css">
<link rel="stylesheet" href="../bootstrap-3.3.7-dist/css/bootstrap.min.css"> 
<link rel="stylesheet" href="../bootstrap-3.3.7-dist/css/bootstrap-theme.min.css"> 
<script src="../jquery/jquery-3.3.1.js"></script>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@page isELIgnored="false" %>
</head>
<body style="width:100%;padding:0px; margin:0px;text-align: center;" onload="refreshAndClose()">
	<div style="width:1100px;height:auto;padding:0px; margin:0 auto;" id="main">
		<form class="layui-form" action='<c:url value="/materielId/editMaterielId.do"/>' method="post">
			<input type="hidden" id="url" value='<c:url value="/"/>'>
			<input type="hidden" id="flag" value="${flag}">
			<input type="hidden" value="${materielId.type}" id="lx">
			<input type="hidden" name="row_Id" value="${materielId.row_Id}">
			<div class="layui-form-item" style="margin-top:5%;">
			     <div class="layui-inline" style="top:9px;">
				      <label class="layui-form-label" style="width: 90px;">物料Id</label>
				      <div class="layui-input-inline">
				        <input type="text" name="materiel_Id" lay-verify="materiel_Id" autocomplete="off" class="layui-input" id="materiel_Id" value="${materielId.materiel_Id}" onblur="checkWlid()">
				      </div>
			     </div>
			     
			    <div class="layui-inline" style="top:9px;">
				      <label class="layui-form-label" style="width: 90px;">规格型号</label>
				      <div class="layui-input-inline">
				        <input type="text" name="specification_Type" lay-verify="specification_Type" autocomplete="off" class="layui-input" id="specification_Type" value="${materielId.specification_Type}">
				      </div>
			    </div>
			    
			     <div class="layui-inline" style="top:9px;">
				      <label class="layui-form-label" style="width:150px;">保质期</label>
				      <div class="layui-input-inline">
				        <input type="text" name="bzq" lay-verify="bzq" autocomplete="off" class="layui-input" id="bzq" value="${materielId.bzq}">
				      </div>
			     </div>
			 </div>
			
			<div class="layui-form-item">
			     <div class="layui-inline" style="left: -140px;">
				      <label class="layui-form-label" style="width:105px;">参考单价</label>
				      <div class="layui-input-inline">
				        <input type="text" name="ckdj" lay-verify="ckdj" autocomplete="off" class="layui-input" id="ckdj" value="${materielId.ckdj}">
				      </div>
			     </div>
			     
			  	<div class="layui-inline" style="left: -150px;">
				  	<label class="layui-form-label" style="width:100px;">类型</label>
					<div class="layui-input-inline" style="text-align: left;width: 280px;">
						<select name="type" id="type" lay-filter="type" lay-verify="type" onblur="checkWlid()">
							<option value="false" selected="selected">成品</option>
							<option value="true">材料</option>
						</select>
					</div>
				 </div>
			     
		   </div>
		   
			
		 <div class="layui-form-item layui-form-text">
		    <label class="layui-form-label" style="width:155px;">物料描述</label>
		    <div class="layui-input-block">
		      <textarea placeholder="请输入内容" name="remarks"  lay-verify="remarks" id="remarks" class="layui-textarea" style="width:86.6%">${materielId.remarks}</textarea>
		    </div>
		 </div>
	
		 

		<div class="layui-form-item" style="text-align: center;">
		    <div class="layui-input-block">
		      <button class="layui-btn" lay-submit="" lay-filter="demo1" style="width:25%;margin-top:10px;margin-left:-315px;">立即提交</button>
		    </div>
		</div>
	</form>
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
  reloadType();
  form.render();
 
  //创建一个编辑器
  var editIndex = layedit.build('LAY_demo_editor');

  //监听提交
  form.on('submit(demo1)', function(data){
    layer.alert(JSON.stringify(data.field), {
      title: '最终的提交信息'
    })
    return true;
  });

  form.on('select(type)', function(data){
		//获得填写的物料Id
		var wlId=$('#materiel_Id').val();
		//获得类型
		var type=$('#type').val();
		$.ajax({
			type : "post",
			url : "<c:url value='/materielId/wlIdbcf.do'/>",
			async : false,
			dataType : 'json',
			data:{"materiel_Id":wlId,"type":type},
			error : function() {
				alert("出错");
			},
			success : function(msg) {
				if(msg.flag){
					$('#materiel_Id').val("");
					layer.alert("当前物料Id已存在,不允许重复!",{icon:7})
				}
			}
		});
	});

//自定义验证规则
  form.verify({
	  materiel_Id: function(value){
	      if(value==""||value==null){
	        return '物料Id不能为空';
	      }
	  }
      ,specification_Type:function(value){
	       if(value==""||value==null){
	           return '规格型号不能为空';
	       }
      }
      ,bzq:function(value){
	       if(value==""||value==null){
	           return '保质期不能为空';
	         }
      }
       ,ckdj:function(value){
	        if(value==""||value==null){
	            return '参考单价不能为空';
	          }
       }
  });
  
});

	function checkWlid(){
		//获得填写的物料Id
		var wlId=$('#materiel_Id').val();
		//获得类型
		var type=$('#type').val();
		$.ajax({
			type : "post",
			url : "<c:url value='/materielId/wlIdbcf.do'/>",
			async : false,
			dataType : 'json',
			data:{"materiel_Id":wlId,"type":type},
			error : function() {
				alert("出错");
			},
			success : function(msg) {
				if(msg.flag){
					$('#materiel_Id').val("");
					layer.alert("当前物料Id已存在,不允许重复!",{icon:7})
				}
			}
		});
	}

	function reloadType(){
		//获得类型代码
		var lx=$('#lx').val();
		//遍历类型下拉选
		var lxs=$('#type').find('option');
		for(var i=0;i<lxs.length;i++){
			if(lxs[i].value==lx){
				lxs[i].setAttribute("selected",'true');
				break;
			}
		}
	}
	
	function refreshAndClose(){
		var flag=$('#flag').val();
		if(flag){
			window.parent.opener.location.reload();
			window.close();
		} 
	}
	
	
	
</script>
</body>
</html>