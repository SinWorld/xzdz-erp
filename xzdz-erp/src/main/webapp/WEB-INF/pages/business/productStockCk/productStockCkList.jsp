<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>成品列表</title>
<link rel="stylesheet" href="../layui-v2.5.5/layui/css/layui.css">
<link rel="stylesheet" href="../login/css/static.css">
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@page isELIgnored="false" %>
</head>
<body>
	<input type="hidden" value='<c:url value="/"/>' id="url">
	<input type="hidden" id="flag" value="${flag}">
	<input type="hidden" value="${xsddId}" id="xsddId">
	<input type="hidden" value="${taskId}" id="taskId">
	<div style="position:relative;top: -10px;">
		<table class="layui-hide" id="test" lay-filter="test"></table>
	</div>
	<form class="layui-form" action='<c:url value="/materialStock/submitForm.do"/>' method="post" id="myForm">
		
		<div class="layui-form-item" style="text-align: center;" id="tjan">
		    <div class="layui-input-block">
		      <button class="layui-btn" lay-submit="" lay-filter="" style="width:25%;margin-top:10px;margin-left:-315px;" type="button" onclick="submitForm()">立即提交</button>
		    </div>
		</div>
	</form>
<script type="text/html" id="barDemo">
 {{#  if(d.is_allck !=true){ }}
  <a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="ck">出库</a>
 {{# } else { }}
 {{#  } }}
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
  var xsddId=$('#xsddId').val();
  form.render();
  table.render({
    elem: '#test'
    ,url:url+'productStockCk/initProductStockCkList.do?xsddId='+xsddId
    ,title: '成品列表'
    ,cols: [[
       {field:'index', width:"8%", title: '序号', sort: true,type:'numbers'}
      ,{field:'product_Name', width:"22%",align:'center', title: '产品名称'}
      ,{field:'specification_Type', width:"22%", align:'center', title: '规格型号'}
      ,{field:'numbers', width:"10%", align:'center', title: '数量'}
      ,{field:'unit', width:"10%", align:'center', title: '单位'}
      ,{field:'is_allck', width:"5%", align:'center', title: '是否全部出库',hide:true}
      ,{field:'sales_Contract_Name', width:"18%", align:'center', title: '销售订单'}
      ,{fixed: 'right', title:'操作', toolbar: '#barDemo', width:"10%",align:'center'}
    ]]
    ,id:'testReload'
    ,page: false
  });
  
  
//监听行工具事件
  table.on('tool(test)', function(obj){
    var data = obj.data;
    var url=$('#url').val();
    var product_Id=data.product_Id;
	if(obj.event === 'ck'){
   		parent.layer.open({
        	  	type:2,
        	  	title:'成品出库',
        	  	area: ['100%','100%'],
        		shadeClose: false,
         		resize:false,
         	    anim: 1,
        	  	content:[url+"productStockCk/initProductCk.do?product_Id="+product_Id,'yes']
      	  	});
    }
  });
  
});

	




	//提交表单
	function submitForm(){
		//获得销售订单Id
		var taskId=$("#taskId").val();
		layer.confirm('您已完成出库操作了么？', {
			  btn: ['确定','取消'], //按钮
			  title:'提示'},function(index){
				$.ajax({
					type : "post",
					url : "<c:url value='/productStockCk/submitForm.do'/>",
					async : false,
					dataType : 'json',
					data:{"taskId":taskId},
					error : function() {
						alert("出错");
					},
					success : function(msg) {
						if(msg.flag){
							  var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
				              parent.location.reload();//刷新父页面，注意一定要在关闭当前iframe层之前执行刷新
				              parent.layer.close(index); //再执行关闭
						}
					}
				});
		});
	}
</script>
</body>
</html>