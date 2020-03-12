<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>领导审核(材料)</title>
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
<body style="width:100%;padding:0px; margin:0px;" onload="refreshAndClose()">
	<div style="width:1280px;height:auto;padding:0px; margin:0 auto;" id="main">
		<form class="layui-form" action='<c:url value="/leadershipAuditMaterial/leadershipAuditMaterial.do"/>' method="post" id="myForm">
			<input type="hidden" id="url" value='<c:url value="/"/>'>
			<input type="hidden" id="flag" value="${flag}">
			<input type="hidden" name="task_Id" id="taskId" value="${taskId}">
			
			 <div class="layui-form-item layui-form-text" style="margin-top: 5%;">
		  		<label class="layui-form-label" style="width:120px;">不合格材料</label>
				  <div class="layui-input-block" style="left:-65px;">
					<table class="table table-bordered" id="khlxrs" style="width: 100%">
					  <thead>
					    <tr>
					      <th scope="col" style="text-align: center;width: 5%">序号</th>
					      <th scope="col" style="text-align: center;width: 15%">材料名称</th>
					      <th scope="col" style="text-align: center;width: 15%">规格型号</th>
					      <th scope="col" style="text-align: center;width: 10%">物料Id</th>
					      <th scope="col" style="text-align: center;width: 10%">单位</th>
					      <th scope="col" style="text-align: center;width: 10%">数量</th>
					      <th scope="col" style="text-align: center;width: 15%">备注</th>
					      <th scope="col" style="text-align: center;width: 15%">物料质量</th>
					    </tr>
					  </thead>
					  <tbody>
					  	<c:forEach items="${materialList}" var="m">
					  		<tr>
					  			<td style="text-align: center;">
					  			
								</td>							  			
					  			<td>
					  				<input type="hidden" value="${m.raw_Material_Id}" name="raw_Material_Id">
					  			    <input type='text' class='form-control bj' aria-label='' aria-describedby='' disabled="" value='${m.material_Name}'>
					  			</td>
					  			<td>
					  			    <input type='text' class='form-control bj' aria-label='' aria-describedby='' disabled="" value='${m.specification_Type}'>
					  			</td>
					  			<td>
					  			    <input type='text' class='form-control bj' aria-label='' aria-describedby='' disabled="" value='${m.materielId}'>
					  			</td>
					  			<td>
					  			    <input type='text' class='form-control bj' aria-label='' aria-describedby='' disabled="" value='${m.unit}'>
					  			</td>
					  			<td>
					  			    <input type='text' class='form-control bj' aria-label='' aria-describedby='' disabled="" value='${m.numbers}'>
					  			</td>
					  			<td>
					  			    <input type='text' class='form-control bj' aria-label='' aria-describedby=''  disabled="" value='${m.remarks}'>
					  			</td>
					  			<td>
					  			    <input type='text' class='form-control bj' aria-label='' aria-describedby=''  disabled="" value='${m.materialQuality}'>
					  			</td>
					  		</tr>
					  	</c:forEach>
					  </tbody>
					</table>
				</div>
			</div>
		
			<div class="layui-form-item" style="margin-top: 3%;">
			    <label class="layui-form-label"  style="width: 125px;">审批结果</label>
			    <div class="layui-input-block">
			      <input type="radio" name="out_come" value="重新检验" title="重新检验" lay-verify="result" >
			      <input type="radio" name="out_come" value="不合格入库" title="不合格入库" lay-verify="result">
			      <input type="radio" name="out_come" value="让步使用" title="让步使用" lay-verify="result">
			      <input type="radio" name="out_come" value="退货" title="退货" lay-verify="result">
			    </div>
			  </div>

			<div class="layui-form-item layui-form-text">
				<label class="layui-form-label" style="width: 125px;">审批意见</label>
				<div class="layui-input-block">
					<textarea placeholder="请输入内容" lay-verify="remark"
						class="layui-textarea" style="width: 86.5%" name="advice_" id="advice"></textarea>
				</div>
			</div>
	
		<div class="layui-form-item" style="text-align: center;">
		    <div class="layui-input-block">
		      <button class="layui-btn" lay-submit="" lay-filter="demo1" style="width:25%;margin-left:-100px;">立即提交</button>
		    </div>
		</div>
	</form>
 </div>
<script src="../bootstrap-3.3.7-dist/js/bootstrap.js"></script>
<script src="../layui-v2.5.5/layui/layui.js" charset="utf-8"></script>
<script type="text/javascript" src="../jquery-easyui-1.7.0/jquery.easyui.min.js"></script>
<script>
layui.use(['form', 'layedit', 'laydate'], function(){
	  var form = layui.form
	  ,layer = layui.layer
	  ,layedit = layui.layedit
	  ,laydate = layui.laydate;
	  var url=$('#url').val();
	  khlxrxh();
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

	//自定义验证规则
	  form.verify({
		  result: function(value,item){
		      var verifyName=$(item).attr('name')
		      , verifyType=$(item).attr('type')
		      ,formElem=$(item).parents('.layui-form')//获取当前所在的form元素，如果存在的话
				,verifyElem=formElem.find('input[name='+verifyName+']')//获取需要校验的元素
				,isTrue= verifyElem.is(':checked')//是否命中校验
				,focusElem = verifyElem.next().find('i.layui-icon');//焦点元素
				if(!isTrue || !value){
				        //定位焦点
				        focusElem.css(verifyType=='radio'?{"color":"#FF5722"}:{"border-color":"#FF5722"});
				        //对非输入框设置焦点
				        focusElem.first().attr("tabIndex","1").css("outline","0").blur(function() {
				            focusElem.css(verifyType=='radio'?{"color":""}:{"border-color":""});
				         }).focus();
				        return '审批结果不能为空';
				}
		    }
	      ,remark(value){
		       if(value==""||value==null){
		           return '审批意见不能为空';
		       }
	      }
	  });
		  
});

	function khlxrxh(){
		var len = $('#khlxrs tr').length;
	    for(var i = 1;i<=len-1;i++){
	        $('#khlxrs tr:eq('+i+') td:first').text(i);
	    }
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