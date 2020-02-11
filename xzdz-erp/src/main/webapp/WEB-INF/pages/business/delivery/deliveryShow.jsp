<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>新增送货单</title>
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
<body style="width:100%;padding:0px; margin:0px;">
	<div class="layui-tab">
	   <ul class="layui-tab-title">
	    <li class="layui-this">基本信息</li>
	    <li>报表打印</li>
	   </ul>
	   <div class="layui-tab-content">
           <div class="layui-tab-item layui-show">
					<div style="width:1280px;height:auto;padding:0px; margin:0 auto;" id="main">
						<form class="layui-form" action='' method="post" id="myForm">
							<input type="hidden" value="${delivery.delivery_Id}" id="delivery_Id">
							<div class="layui-form-item" style="margin-top:3%;">
							     <div class="layui-inline" style="top:9px;left: -29px;">
								      <label class="layui-form-label" style="width:150px;">收货单位</label>
								      <div class="layui-input-inline">
								        <input type="text"  autocomplete="off" class="layui-input bj" style="width: 280px;" disabled="" value="${delivery.shdwName}">
								      </div>
							     </div>
							     
							      <div class="layui-inline" style="top:9px;left: 200px;">
								      <label class="layui-form-label" style="width:150px;">订单编号</label>
								      <div class="layui-input-inline">
								        <input type="text"   autocomplete="off" class="layui-input bj" style="width: 280px;" disabled="" value="${delivery.delivery_Code}">
								      </div>
							     </div>
							 </div>
						 
							 <div class="layui-form-item">
							      <div class="layui-inline" style="left: -29px;">
								      <label class="layui-form-label" style="width:150px;">送货日期</label>
								      <div class="layui-input-inline">
								        <input type="text"  autocomplete="off" class="layui-input bj" style="width: 280px;"  value="${delivery.qdrq}" disabled="">
								      </div>
							     </div>
							     
							      <div class="layui-inline" style="left:210px;">
								      <label class="layui-form-label" style="width: 139px;">经办人</label>
								      <div class="layui-input-inline">
								        <input type="text"  autocomplete="off" class="layui-input bj" style="width: 280px;" disabled="" value="${delivery.shjbr}">
								      </div>
							    </div>
							 </div>
						
						 <div class="layui-form-item layui-form-text">
					  		<label class="layui-form-label" style="width:133px;">货物明细</label>
							  <div class="layui-input-block" style="left: 10px;">
								<table class="table table-bordered" id="khlxrs" style="width: 100%">
								  <thead>
								    <tr>
								      <th scope="col" style="text-align: center;width: 5%">序号</th>
								      <th scope="col" style="text-align: center;width: 20%">物料名称</th>
								      <th scope="col" style="text-align: center;width: 20%">规格型号</th>
								      <th scope="col" style="text-align: center;width: 15%">单位</th>
								      <th scope="col" style="text-align: center;width: 15%">送货数量</th>
								      <th scope="col" style="text-align: center;width: 25%">备注</th>
								    </tr>
								  </thead>
								  <tbody>
								  	<c:forEach items="${orderList}" var="o">
								  		<tr>
								  			<td>
								  			
											</td>							  			
								  			<td>
								  			    <input type='text' class='form-control bj' aria-label='' aria-describedby='' disabled="" value='${o.material_Name}'>
								  			</td>
								  			<td>
								  			    <input type='text' class='form-control bj' aria-label='' aria-describedby='' disabled="" value='${o.specification_Type}' >
								  			</td>
								  			<td>
								  			    <input type='text' class='form-control bj' aria-label='' aria-describedby='' disabled="" value='${o.company}' >
								  			</td>
								  			<td>
								  			    <input type='text' class='form-control bj' aria-label='' aria-describedby='' disabled="" value='${o.delivery_Number}' >
								  			</td>
								  			<td>
								  			    <input type='text' class='form-control bj' aria-label='' aria-describedby='' disabled="" value="${o.remarks }">
								  			</td>
								  		</tr>
								  	</c:forEach>
								  </tbody>
								</table>
							</div>
						</div>
					</form>
				 </div>
			</div>
			<div class="layui-tab-item">
 				<iframe  src=''  width="100%" height="100%" frameborder="no" border="0" marginwidth="0" marginheight=" 0" scrolling="no" allowtransparency="yes" onload="changeFrameHeight(this)"></iframe>			
 			</div>
 		</div>
 	</div>
<script src="../bootstrap-3.3.7-dist/js/bootstrap.js"></script>
<script src="../layui-v2.5.5/layui/layui.js" charset="utf-8"></script>
<script type="text/javascript" src="../jquery-easyui-1.7.0/jquery.easyui.min.js"></script>
<script>
layui.use(['form', 'layedit', 'laydate','element'], function(){
  var form = layui.form
  ,layer = layui.layer
  ,layedit = layui.layedit
  ,laydate = layui.laydate;
  var element = layui.element;
  form.render();
  //创建一个编辑器
  var editIndex = layedit.build('LAY_demo_editor');
  ckcpxh();
  bbsrc();
});
	
 	
 	function ckcpxh(){
		var len = $('#khlxrs tr').length;
	    for(var i = 1;i<=len-1;i++){
	        $('#khlxrs tr:eq('+i+') td:first').text(i);
	    }
	}

 	//设置报表src
	function bbsrc(){
		var id=$('#delivery_Id').val();
		 $('iframe').attr('src','http://127.0.0.1:8080/WebReport/ReportServer?reportlet=shd.cpt&id='+id);
	}

	function changeFrameHeight(that){
        //电脑屏幕高度-iframe上面其他组件的高度
        //例:我这里iframe上面还有其他一些div组件，一共的高度是120，则减去120
        $(that).height(document.documentElement.clientHeight - 90);
        
    }
	
</script>
</body>
</html>