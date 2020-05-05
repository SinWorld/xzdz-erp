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
<title>查看客户</title>
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
	<div style="width:1280px;height:auto;padding:0px; margin:0 auto;" id="main">
		<form class="layui-form" action="" method="post" id="downForm" enctype="multipart/form-data">
		<input type="hidden" id="url" value='<c:url value="/"/>'>
		<input type="hidden" value="${contactList.size()}" id="khlxrSize">
		<input type="hidden" value="${customer.customer_Id}" id="customer_Id" name="customer_Id">
		<input type="hidden" id="OBJDM" value="${OBJDM}">
		<input type="hidden" id="ftpPath">
		<input type="hidden" id="rEALWJM">
		
			 <div class="layui-form-item" style="margin-top: 5%">
			    <label class="layui-form-label" style="width: 120px;">单位名称</label>
			    <div class="layui-input-block">
			      <input type="text" name="unit_Name" lay-verify="unit_Name" autocomplete="off" placeholder="请输入单位名称" class="layui-input bj" style="width:76.5%" id="unit_Name" value="${customer.unit_Name}" disabled="">
			    </div>
			  </div>
			
			<div class="layui-form-item">
			     <div class="layui-inline" style="top:9px;left: -125px;">
				      <label class="layui-form-label" style="width: 90px;">注册地址</label>
				      <div class="layui-input-inline">
				        <input type="text" name="registered_Address" lay-verify="registered_Address" autocomplete="off" class="layui-input bj" id="registered_Address" value="${customer.registered_Address}" disabled="">
				      </div>
			     </div>
			     
			    <div class="layui-inline" style="top:9px;left: -105px;">
				      <label class="layui-form-label" style="width: 90px;">办公地址</label>
				      <div class="layui-input-inline">
				        <input type="text" name="office_Address" lay-verify="office_Address" autocomplete="off" class="layui-input bj" id="office_Address" value="${customer.office_Address}" disabled="">
				      </div>
			    </div>
			    
			     <div class="layui-inline" style="top:9px;left: -88px;">
				      <label class="layui-form-label" style="width:150px;">社会统一信用代码</label>
				      <div class="layui-input-inline">
				        <input type="text" name="unified_Code" lay-verify="unified_Code" autocomplete="off" class="layui-input bj" id="unified_Code" value="${customer.unified_Code}" disabled="">
				      </div>
			     </div>
			 </div>
			
			<div class="layui-form-item">
			     <div class="layui-inline" style="top:9px;left: -162px;">
				      <label class="layui-form-label" style="width:105px;">法定代表人</label>
				      <div class="layui-input-inline">
				        <input type="text" name="legal_person" lay-verify="legal_person" autocomplete="off" class="layui-input bj" id="legal_person" value="${customer.legal_person}" disabled="">
				      </div>
			     </div>
			     
			    <div class="layui-inline" style="top:9px;left: -142px;">
				      <label class="layui-form-label" style="width: 90px;">开户行</label>
				      <div class="layui-input-inline">
				        <input type="text" name="opening_Bank" lay-verify="opening_Bank" autocomplete="off" class="layui-input bj" id="opening_Bank" value="${customer.opening_Bank}" disabled="">
				      </div>
			    </div>
			    
			     <div class="layui-inline" style="top:9px;left: -65px;">
				      <label class="layui-form-label" style="width: 90px;">账号</label>
				      <div class="layui-input-inline">
				        <input type="text" name="account_Number" lay-verify="account_Number" autocomplete="off" class="layui-input bj" id="account_Number" value="${customer.account_Number}" disabled="">
				      </div>
			     </div>
		   </div>
		   
		   	<div class="layui-form-item">
			     <div class="layui-inline" style="top:9px;left: -154px;">
				      <label class="layui-form-label" style="width: 90px;">税号</label>
				      <div class="layui-input-inline">
				        <input type="text" name="duty_Paragraph" lay-verify="duty_Paragraph" autocomplete="off" class="layui-input bj" id="duty_Paragraph" value="${customer.duty_Paragraph}" disabled="">
				      </div>
			     </div>
			     
			    <div class="layui-inline" style="top:9px;left: -134px;">
				      <label class="layui-form-label" style="width: 90px;">电话</label>
				      <div class="layui-input-inline">
				        <input type="text" name="telPhone" lay-verify="telPhone" autocomplete="off" class="layui-input bj" id="telPhone" value="${customer.telPhone}" disabled="">
				      </div>
			    </div>
			    
			     <div class="layui-inline" style="top:9px;left: -56px;">
				      <label class="layui-form-label" style="width: 90px;">传真</label>
				      <div class="layui-input-inline">
				        <input type="text" name="fax" lay-verify="fax" autocomplete="off" class="layui-input bj" id="fax" value="${customer.fax}" disabled="">
				      </div>
			    </div>
		   </div>
		   
		   <div class="layui-form-item">
	     		<div class="layui-inline" style="top:9px;left: -465px;">
			      <label class="layui-form-label" style="width:105px;">委托代理人</label>
			      <div class="layui-input-inline">
			        <input type="text" name="wtdlr" lay-verify="wtdlr" autocomplete="off" class="layui-input bj" id="wtdlr" value="${customer.wtdlr}" disabled="">
			      </div>
		     	</div>
		  </div>
		
		 <div class="layui-form-item layui-form-text">
		    <label class="layui-form-label" style="width:122px;">备注</label>
		    <div class="layui-input-block">
		      <textarea placeholder="请输入内容" name="remarks"  lay-verify="remarks" id="remarks" class="layui-textarea bj" style="width:76.6%" disabled="">${customer.remarks}</textarea>
		    </div>
		 </div>
	
		 <div class="layui-form-item layui-form-text">
		  	<label class="layui-form-label" style="width: 150px;">客户联系人</label>
			  <div class="layui-input-block">
				<table class="table table-bordered" id="khlxrs" style="width: 100%">
				  <thead>
				    <tr>
				      <th scope="col" style="text-align: center;width: 4%">序号</th>
				      <th scope="col" style="text-align: center;width: 8%">名称</th>
				      <th scope="col" style="text-align: center;width: 8%">岗位</th>
				      <th scope="col" style="text-align: center;width: 9%">职务</th>
				      <th scope="col" style="text-align: center;width: 9%">部门</th>
				      <th scope="col" style="text-align: center;width: 9%">手机号</th>
				      <th scope="col" style="text-align: center;width: 9%">办公电话</th>
				      <th scope="col" style="text-align: center;width: 10%">邮箱</th>
				      <th scope="col" style="text-align: center;width: 9%">QQ号</th>
				      <th scope="col" style="text-align: center;width: 9%">微信号</th>
				      <th scope="col" style="text-align: center;width: 16%">备注</th>
				     
				    </tr>
				    <c:forEach items="${contactList}" var="c">
				    	<tr>
				    		<th scope='row' style='text-align: center;line-height:38px;'></th>
				    		<td>
				    			<input type="hidden" name="cus_Con_Id" value="${c.cus_Con_Id}"> 
				    			<input type='text' class='form-control bj' aria-label='' aria-describedby='' name='cus_Con_Name' title="${c.cus_Con_Name }" value="${c.cus_Con_Name }" disabled="">
				    		</td>
				    		<td>
				    			<input type='text' class='form-control bj' aria-label='' aria-describedby='' title="${c.cun_Con_Posstation}" value="${c.cun_Con_Posstation }" name='cun_Con_Posstation' disabled="">
				    		</td>
				    		<td>
				    			<input type='text' class='form-control bj' aria-label='' aria-describedby='' title="${c.cun_Con_Post }" value="${c.cun_Con_Post }" name='cun_Con_Post' disabled="">
				    		</td>
				    		<td>
				    			<input type='text' class='form-control bj' aria-label='' aria-describedby='' title="${c.cun_Con_Dep }" value="${c.cun_Con_Dep }" name='cun_Con_Dep' disabled="">
				    		</td>
				    		<td>
				    			<input type='text' class='form-control bj' aria-label='' aria-describedby='' title="${c.cun_Con_Phone}" value="${c.cun_Con_Phone }" name='cun_Con_Phone' disabled="">
				    		</td>
				    		<td>
				    			<input type='text' class='form-control bj' aria-label='' aria-describedby='' title="${c.cun_Con_Tel}" value="${c.cun_Con_Tel }" name='cun_Con_Tel' disabled="">
				    		</td>
				    		<td>
				    			<input type='text' class='form-control bj' aria-label='' aria-describedby='' title="${c.cun_Con_Email}" value="${c.cun_Con_Email }"  name='cun_Con_Email' disabled="">
				    		</td>
				    		<td>
				    			<input type='text' class='form-control bj' aria-label='' aria-describedby='' title="${c.cun_Con_QQ}" value="${c.cun_Con_QQ }"  name='cun_Con_QQ' disabled="">
				    		</td>
				    		<td>
				    			<input type='text' class='form-control bj' aria-label='' aria-describedby='' title="${c.cun_Con_WeChat }" value="${c.cun_Con_WeChat }" name='cun_Con_WeChat' disabled="">
				    		</td>
				    		<td>
				    			<input type='text' class='form-control bj' aria-label='' aria-describedby='' title="${c.cun_Con_Remarks }" value="${c.cun_Con_Remarks }" name='cun_Con_Remarks' disabled="">
				    		</td>
				    	</tr>
				    </c:forEach>
				  </thead>
				  <tbody>
				  </tbody>
				</table>
			</div>
		</div>
		
		<div class="layui-form-item">
		        <label class="layui-form-label" style="width:100px;">附件</label>
		        <div class="layui-input-inline" style="width: 77%;">
					<table class="layui-hide" id="test" lay-filter="test"></table>
				</div>
		 </div>

	</form>
 </div>
<script src="../bootstrap-3.3.7-dist/js/bootstrap.js"></script>
<script src="../layui-v2.5.5/layui/layui.js" charset="utf-8"></script>
<script type="text/html" id="barDemo">
  <!--<a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="yl" name="defaultAD">预览</a>-->
  <a class="layui-btn layui-btn-xs" lay-event="xz">下载</a>
</script>
<script>
layui.use(['form', 'layedit', 'laydate','upload','table'], function(){
  var form = layui.form
  ,layer = layui.layer
  ,layedit = layui.layedit
  ,laydate = layui.laydate
  ,upload = layui.upload;
  var url=$('#url').val();
  var table = layui.table;
  var OBJId=$('#OBJDM').val();
  form.render();
  //日期
  laydate.render({
    elem: '#cont_Date'
  });
  
 
  //创建一个编辑器
  var editIndex = layedit.build('LAY_demo_editor');
  khlxrxh();
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
    	var fp_Url="/customer/downLoad.do";
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
	    				//下载文件
	    		    	form.action=url+"sales/downloadFtpFile.do?ftpPath="+ftpPath+"&"+"rEALWJM="+rEALWJM;
	    		    	form.submit();
	    		}else{
					layer.alert("当前用户无此功能权限，请联系管理员授权!!!",{icon:7});
		    	}
	    	}
 		});
    }
  });
});


	function khlxrxh(){
		var len = $('table tr').length;
	    for(var i = 1;i<len;i++){
	        $('table tr:eq('+i+') th:first').text(i);
	    }
	     
	}

</script>
</body>
</html>