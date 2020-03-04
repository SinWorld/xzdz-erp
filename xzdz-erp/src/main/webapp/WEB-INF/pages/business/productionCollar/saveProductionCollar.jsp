<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>生产领用列表</title>
<link rel="stylesheet" href="../layui-v2.5.5/layui/css/layui.css">
<link rel="stylesheet" href="../bootstrap-3.3.7-dist/css/bootstrap.min.css"> 
<link rel="stylesheet" href="../bootstrap-3.3.7-dist/css/bootstrap-theme.min.css">
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@page isELIgnored="false" %>
</head>
<body style="width:100%;padding:0px; margin:0px;" onload="refreshAndClose()">
	<div style="width:1280px;height:auto;padding:0px; margin:0 auto;" id="main">
		<form class="layui-form" action='<c:url value="/leadershipAuditMaterial/leadershipAuditMaterial.do"/>' method="post" id="myForm">
			<input type="hidden" id="url" value='<c:url value="/"/>'>
			<input type="hidden" id="flag" value="${flag}">
			<input type="hidden" id="taskId" value="${taskId}">
			<input type="hidden" id="str">
			
			 <div class="layui-form-item layui-form-text" style="margin-top: 5%;">
		  		<label class="layui-form-label" style="width:130px;">出库材料</label>
				  <div class="layui-input-block" style="left:-50px;">
					<table class="table table-bordered" id="khlxrs" style="width: 100%">
					  <thead>
					    <tr>
					      <th scope="col" style="text-align: center;width: 5%">序号</th>
					      <th scope="col" style="text-align: center;width: 20%">材料名称</th>
					      <th scope="col" style="text-align: center;width: 17%">规格型号</th>
					      <th scope="col" style="text-align: center;width: 11%">物料Id</th>
					      <th scope="col" style="text-align: center;width: 10%">库位</th>
					      <th scope="col" style="text-align: center;width: 10%">入库数量</th>
					      <th scope="col" style="text-align: center;width: 10%">出库数量</th>
					      <th scope="col" style="text-align: center;width: 12%">操作</th>
					    </tr>
					  </thead>
					  <tbody>
					  	<c:forEach items="${materialList}" var="m">
					  		<tr>
					  			<td style="text-align: center;">
					  			
								</td>							  			
					  			<td>
					  				<input type="hidden" value="${m.product_Id}" name="raw_Material_Id">
					  			    <input type='text' class='form-control bj' aria-label='' aria-describedby='' disabled="" value='${m.productName}' title="${m.productName}">
					  			</td>
					  			<td>
					  			    <input type='text' class='form-control bj' aria-label='' aria-describedby='' disabled="" value='${m.ggxh}' title="${m.ggxh}">
					  			</td>
					  			<td>
					  			    <input type='text' class='form-control bj' aria-label='' aria-describedby='' disabled="" value='${m.materielId}' title="${m.materielId}">
					  			</td>
					  			<td>
					  			    <input type='text' class='form-control bj' aria-label='' aria-describedby=''  disabled="" value='${m.stock}' title="${m.stock}">
					  			</td>
					  			<td>
					  			    <input type='text' class='form-control bj' aria-label='' aria-describedby=''  disabled="" value='${m.rksl}' title="${m.rksl}">
					  			</td>
					  			<td>
					  			    <input type='text' class='form-control bj' aria-label='' aria-describedby=''  disabled="" value='${m.cksl}' title="${m.cksl}">
					  			</td>
					  			<td>
								    <div class="layui-input-block" style="width:205px;margin-left: 10px;">
								      <input type="radio" name="jyjg.${m.product_Id}" value="合格" title="合格" checked="checked">
								      <input type="radio" name="jyjg.${m.product_Id}" value="部分不合格" title="部分不合格">
								    </div>
				  				</td>
					  		</tr>
					  	</c:forEach>
					  </tbody>
					</table>
				</div>
			</div>
		
			<div class="layui-form-item" style="margin-top: 3%;margin-left:5px;">
			    <label class="layui-form-label"  style="width: 125px;">审批结果</label>
			    <div class="layui-input-block">
			      <input type="radio" name="outcome" value="合格" title="合格" lay-verify="result" >
			      <input type="radio" name="outcome" value="部分不合格" title="部分不合格" lay-verify="result">
			    </div>
			  </div>

			<div class="layui-form-item layui-form-text" style="margin-left:5px;">
				<label class="layui-form-label" style="width: 125px;">审批意见</label>
				<div class="layui-input-block">
					<textarea placeholder="请输入内容" lay-verify="remark"
						class="layui-textarea" style="width: 86.5%" name="advice" id="advice"></textarea>
				</div>
			</div>
	
		<div class="layui-form-item" style="text-align: center;">
		    <div class="layui-input-block">
		      <button class="layui-btn" lay-submit="" lay-filter="" style="width:25%;margin-left:-100px;" type="button" onclick="saveSubmit()">立即提交</button>
		    </div>
		</div>
	</form>
 </div>
<script src="../jquery/jquery-3.3.1.js"></script>
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

	//校验材料项表格
	function cljytable(){
		//获得闲置成品表格
		var tables=$('#khlxrs');
		//获得表格所有行
		var rows=tables[0].rows;
		$('#str').val();
		var str=$('#str').val();
		//遍历表格
		for(var i=1;i<rows.length;i++){
			//获得表格中已存在的材料主键
			var clzj=""
			if($('input[name="raw_Material_Id"]')[i-1]!=undefined){
				clzj=$('input[name="raw_Material_Id"]')[i-1].value;
			}
			//获得材料检验结果
			var cljg;
			var cljgs=$('input[name="jyjg.'+clzj+'"]');
			for(var j=0;j<cljgs.length;j++){
				if(cljgs[j].checked){
					cljg=cljgs[j].value;
					break;
				}
			}
			var data=clzj+":"+cljg;
			if(null!=str&&""!=str){
				str=str+","+data;
			 }else{
				 str=data;
			 }
		}
		return str;
	}

	//提交表单
	function  saveSubmit(){
		var url=$('#url').val();
		var taskId=$('#taskId').val();
		var spjgs=$('input[name="outcome"]');
		var spjg;
		for(var i=0;i<spjgs.length;i++){
			if(spjgs[i].checked){
				spjg=spjgs[i].value;
				break;
			}
		}
		var spyj=$("#advice").val();
		var data=cljytable();
		if(spjg==undefined){
			return layer.alert("审批结果不能为空",{icon:7});
		}
		if(spyj==""){
			return layer.alert("审批意见不能为空",{icon:7});
		}
		var form=document.getElementById('myForm');
		form.action=url+"productionCollar/productionCollar.do?task_Id="+taskId+"&out_come="+spjg+"&advice_="+spyj+"&data="+data;
		form.submit(); 
	}
</script>
</body>
</html>