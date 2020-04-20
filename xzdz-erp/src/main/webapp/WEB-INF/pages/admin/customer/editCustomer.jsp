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
<title>编辑客户</title>
<link rel="stylesheet" href="../layui-v2.5.5/layui/css/layui.css">
<link rel="stylesheet" href="../bootstrap-3.3.7-dist/css/bootstrap.min.css"> 
<link rel="stylesheet" href="../bootstrap-3.3.7-dist/css/bootstrap-theme.min.css"> 
<script src="../jquery/jquery-3.3.1.js"></script>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@page isELIgnored="false" %>
</head>
<body style="width:100%;padding:0px; margin:0px;text-align: center;">
	<div style="width:1280px;height:auto;padding:0px; margin:0 auto;" id="main">
		<form class="layui-form" action="" method="post">
		<input type="hidden" id="url" value='<c:url value="/"/>'>
		<input type="hidden" value="${contactList.size()}" id="khlxrSize">
		<input type="hidden" value="${customer.customer_Id}" id="customer_Id" name="customer_Id">
		
			 <div class="layui-form-item" style="margin-top: 5%">
			    <label class="layui-form-label" style="width: 120px;">单位名称</label>
			    <div class="layui-input-block">
			      <input type="text" name="unit_Name" lay-verify="unit_Name" autocomplete="off" placeholder="请输入单位名称" class="layui-input" style="width:76.5%" id="unit_Name" value="${customer.unit_Name}">
			    </div>
			  </div>
			
			<div class="layui-form-item">
			     <div class="layui-inline" style="top:9px;left: -125px;">
				      <label class="layui-form-label" style="width: 90px;">注册地址</label>
				      <div class="layui-input-inline">
				        <input type="text" name="registered_Address" lay-verify="registered_Address" autocomplete="off" class="layui-input" id="registered_Address" value="${customer.registered_Address}">
				      </div>
			     </div>
			     
			    <div class="layui-inline" style="top:9px;left: -105px;">
				      <label class="layui-form-label" style="width: 90px;">办公地址</label>
				      <div class="layui-input-inline">
				        <input type="text" name="office_Address" lay-verify="office_Address" autocomplete="off" class="layui-input" id="office_Address" value="${customer.office_Address}">
				      </div>
			    </div>
			    
			     <div class="layui-inline" style="top:9px;left: -88px;">
				      <label class="layui-form-label" style="width:150px;">社会统一信用代码</label>
				      <div class="layui-input-inline">
				        <input type="text" name="unified_Code" lay-verify="unified_Code" autocomplete="off" class="layui-input" id="unified_Code" value="${customer.unified_Code}">
				      </div>
			     </div>
			 </div>
			
			<div class="layui-form-item">
			     <div class="layui-inline" style="top:9px;left: -162px;">
				      <label class="layui-form-label" style="width:105px;">法定代表人</label>
				      <div class="layui-input-inline">
				        <input type="text" name="legal_person" lay-verify="legal_person" autocomplete="off" class="layui-input" id="legal_person" value="${customer.legal_person}">
				      </div>
			     </div>
			     
			    <div class="layui-inline" style="top:9px;left: -142px;">
				      <label class="layui-form-label" style="width: 90px;">开户行</label>
				      <div class="layui-input-inline">
				        <input type="text" name="opening_Bank" lay-verify="opening_Bank" autocomplete="off" class="layui-input" id="opening_Bank" value="${customer.opening_Bank}">
				      </div>
			    </div>
			    
			     <div class="layui-inline" style="top:9px;left: -65px;">
				      <label class="layui-form-label" style="width: 90px;">账号</label>
				      <div class="layui-input-inline">
				        <input type="text" name="account_Number" lay-verify="account_Number" autocomplete="off" class="layui-input" id="account_Number" value="${customer.account_Number}">
				      </div>
			     </div>
		   </div>
		   
		   	<div class="layui-form-item">
			     <div class="layui-inline" style="top:9px;left: -154px;">
				      <label class="layui-form-label" style="width: 90px;">税号</label>
				      <div class="layui-input-inline">
				        <input type="text" name="duty_Paragraph" lay-verify="duty_Paragraph" autocomplete="off" class="layui-input" id="duty_Paragraph" value="${customer.duty_Paragraph}">
				      </div>
			     </div>
			     
			    <div class="layui-inline" style="top:9px;left: -134px;">
				      <label class="layui-form-label" style="width: 90px;">电话</label>
				      <div class="layui-input-inline">
				        <input type="text" name="telPhone" lay-verify="telPhone" autocomplete="off" class="layui-input" id="telPhone" value="${customer.telPhone}">
				      </div>
			    </div>
			    
			     <div class="layui-inline" style="top:9px;left: -56px;">
				      <label class="layui-form-label" style="width: 90px;">传真</label>
				      <div class="layui-input-inline">
				        <input type="text" name="fax" lay-verify="fax" autocomplete="off" class="layui-input" id="fax" value="${customer.fax}">
				      </div>
			    </div>
		   </div>
		   
		    <div class="layui-form-item">
	     		<div class="layui-inline" style="top:9px;left: -465px;">
			      <label class="layui-form-label" style="width:105px;">委托代理人</label>
			      <div class="layui-input-inline">
			        <input type="text" name="wtdlr" lay-verify="wtdlr" autocomplete="off" class="layui-input" id="wtdlr" value="${customer.wtdlr}">
			      </div>
		     	</div>
		  </div>
		
		 <div class="layui-form-item layui-form-text">
		    <label class="layui-form-label" style="width:122px;">备注</label>
		    <div class="layui-input-block">
		      <textarea placeholder="请输入内容" name="remarks"  lay-verify="remarks" id="remarks" class="layui-textarea" style="width:76.6%">${customer.remarks}</textarea>
		    </div>
		 </div>
	
		 <div class="layui-form-item layui-form-text">
		  	<label class="layui-form-label" style="width: 150px;">客户联系人</label>
		  	<div class="layui-input-block" style="text-align: left;">
				<button type="button" class="layui-btn layui-btn-normal" onclick="addRow()"><i class="layui-icon">&#xe608;</i>新增一行</button>	
			 </div>
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
				      <th scope="col" style="text-align: center;width: 9%">邮箱</th>
				      <th scope="col" style="text-align: center;width: 9%">QQ号</th>
				      <th scope="col" style="text-align: center;width: 9%">微信号</th>
				      <th scope="col" style="text-align: center;width: 9%">备注</th>
				      <th scope="col" style="text-align: center;width: 8%">操作</th>
				    </tr>
				    <c:forEach items="${contactList}" var="c">
				    	<tr>
				    		<th scope='row' style='text-align: center;line-height:38px;'></th>
				    		<td>
				    			<input type="hidden" name="cus_Con_Id" value="${c.cus_Con_Id}"> 
				    			<input type='text' class='form-control' aria-label='' aria-describedby='' name='cus_Con_Name' value="${c.cus_Con_Name }">
				    		</td>
				    		<td>
				    			<input type='text' class='form-control' aria-label='' aria-describedby='' value="${c.cun_Con_Posstation }" name='cun_Con_Posstation'>
				    		</td>
				    		<td>
				    			<input type='text' class='form-control' aria-label='' aria-describedby='' value="${c.cun_Con_Post }" name='cun_Con_Post'>
				    		</td>
				    		<td>
				    			<input type='text' class='form-control' aria-label='' aria-describedby='' value="${c.cun_Con_Dep }" name='cun_Con_Dep'>
				    		</td>
				    		<td>
				    			<input type='text' class='form-control' aria-label='' aria-describedby='' value="${c.cun_Con_Phone }" name='cun_Con_Phone'>
				    		</td>
				    		<td>
				    			<input type='text' class='form-control' aria-label='' aria-describedby='' value="${c.cun_Con_Tel }" name='cun_Con_Tel'>
				    		</td>
				    		<td>
				    			<input type='text' class='form-control' aria-label='' aria-describedby='' value="${c.cun_Con_Email }"  name='cun_Con_Email'>
				    		</td>
				    		<td>
				    			<input type='text' class='form-control' aria-label='' aria-describedby='' value="${c.cun_Con_QQ }"  name='cun_Con_QQ'>
				    		</td>
				    		<td>
				    			<input type='text' class='form-control' aria-label='' aria-describedby='' value="${c.cun_Con_WeChat }" name='cun_Con_WeChat'>
				    		</td>
				    		<td>
				    			<input type='text' class='form-control' aria-label='' aria-describedby='' value="${c.cun_Con_Remarks }" name='cun_Con_Remarks'>
				    		</td>
				    		<td>
				    			<button type='button' class='layui-btn layui-btn-danger' title='删除一行' onclick='deleteData(${c.cus_Con_Id})'><i class='layui-icon'>&#xe640;</i></button></td>
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
		      <button class="layui-btn" lay-submit="" lay-filter="" style="width:25%;margin-top:10px;margin-left:-315px;" onclick="editCustomer()">立即提交</button>
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
  form.render();
  //日期
  laydate.render({
    elem: '#cont_Date'
  });
  
 
  //创建一个编辑器
  var editIndex = layedit.build('LAY_demo_editor');
  khlxrxh();
});

	//编辑客户信息
	function editCustomer(){
		//客户主键
		var customer_Id=$('#customer_Id').val();
		//创建客户对象
		var kh=new Object();
		//获得单位名称
		var dwmc=$('#unit_Name').val();
		//注册地址
		var zcdz=$('#registered_Address').val();
		//办公地址
		var bgdz=$('#office_Address').val();
		//社会统一信用代码
		var shtyxydm=$('#unified_Code').val();
		//法定代表人
		var fddbr=$('#legal_person').val();
		//开户行
		var khh=$('#opening_Bank').val();
		//账号
		var zh=$('#account_Number').val();
		//税号
		var sh=$('#duty_Paragraph').val();
		//电话
		var dh=$('#telPhone').val();
		//传真
		var cz=$('#fax').val();
		//委托代理人
		var wtdlr=$('#wtdlr').val();
		//备注
		var bz=$('#remarks').val();
		kh.customer_Id=customer_Id;
		kh.unit_Name=dwmc;
		kh.registered_Address=zcdz;
		kh.office_Address=bgdz;
		kh.unified_Code=shtyxydm;
		kh.legal_person=fddbr;
		kh.opening_Bank=khh;
		kh.account_Number=zh;
		kh.duty_Paragraph=sh;
		kh.telPhone=dh;
		kh.fax=cz;
		kh.wtdlr=wtdlr;
		kh.remarks=bz;
		$.ajax({
			type : "post",
			url : "<c:url value='/customer/editCunstomer.do'/>",
			async : false,
			contentType :"application/json;charsetset=UTF-8",//必须
			dataType : 'json',
			data:JSON.stringify(kh),
			error : function() {
				alert("出错");
			},
			success : function(data) {
				if(data.flag){
					editCustomerlxr(customer_Id);
				}
			}
		});

	}
	
	//编辑客户联系人
	function editCustomerlxr(customer_Id){
		//联系人
		var lxrs=$('#lxrs').val();
		//货物当前表格
		var tables=$('#khlxrs');
		//获得表格所有行
		var rows=tables[0].rows;
		//创建客户联系人数组
		var khlxrs=new Array();
		//遍历表格
		for(var i=1;i<rows.length;i++){
			//获得客户联系人表格中已存在的主键
			var khlxrId=""
			if($('input[name="cus_Con_Id"]')[i-1]!=undefined){
				khlxrId=$('input[name="cus_Con_Id"]')[i-1].value;
			}
			//客户联系人名称
			var lxrmc=$('input[name="cus_Con_Name"]')[i-1].value;
			//岗位
			var gw=$('input[name="cun_Con_Posstation"]')[i-1].value;
			//职务
			var zw=$('input[name="cun_Con_Post"]')[i-1].value;
			//部门
			var bm=$('input[name="cun_Con_Dep"]')[i-1].value;
			//手机号
			var sjh=$('input[name="cun_Con_Phone"]')[i-1].value;
			//办公电话
			var bgdh=$('input[name="cun_Con_Tel"]')[i-1].value;
			//邮箱
			var yx=$('input[name="cun_Con_Email"]')[i-1].value;
			//QQ号
			var qq=$('input[name="cun_Con_QQ"]')[i-1].value;
			//微信号
			var wx=$('input[name="cun_Con_WeChat"]')[i-1].value;
			//备注
			var bz=$('input[name="cun_Con_Remarks"]')[i-1].value;
			//创建客户联系人对象
			var khlxr=new Object();
			khlxr.cus_Con_Id=khlxrId;
			khlxr.cus_Con_Name=lxrmc;
			khlxr.cun_Con_Posstation=gw;
			khlxr.cun_Con_Post=zw;
			khlxr.cun_Con_Dep=bm;
			khlxr.cun_Con_Phone=sjh;
			khlxr.cun_Con_Tel=bgdh;
			khlxr.cun_Con_Email=yx;
			khlxr.cun_Con_QQ=qq;
			khlxr.cun_Con_WeChat=wx;
			khlxr.cun_Con_Remarks=bz;
			khlxr.customer=customer_Id;
			khlxrs.push(khlxr);
		}
		$.ajax({
			type : "post",
			url : "<c:url value='/customer/editKhlxr.do'/>",
			async : false,
			contentType :"application/json;charsetset=UTF-8",//必须
			dataType : 'json',
			data:JSON.stringify(khlxrs),
			error : function() {
				alert("出错");
			},
			success : function(msg) {
				if(msg.flag){
					if(msg.userId==-1){
						window.parent.opener.location.reload();
						window.close();
					}else{
						var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
			            parent.location.reload();//刷新父页面，注意一定要在关闭当前iframe层之前执行刷新
			            parent.layer.close(index); //再执行关闭
					}
				} 
			}
		});
	}

	function khlxrxh(){
		var len = $('table tr').length;
	    for(var i = 1;i<len;i++){
	        $('table tr:eq('+i+') th:first').text(i);
	    }
	     
	}

	//表格新增一行
	var index=0;
	function addRow(){
		//获得表格长度
		var khlxrSize=$('#khlxrSize').val();
		if("khlxrSize"!=""){
			khlxrSize++;
			$('#khlxrSize').val(khlxrSize);
			var tables=$('#khlxrs');
			var addtr = $("<tr>"+
				"<th scope='row' style='text-align: center;line-height:38px;'>"+khlxrSize+"</th>"+
				"<td><input type='text' class='form-control' aria-label='' aria-describedby=''  name='cus_Con_Name'></td>"+
				"<td><input type='text' class='form-control' aria-label='' aria-describedby=''  name='cun_Con_Posstation'></td>"+
				"<td><input type='text' class='form-control' aria-label='' aria-describedby=''  name='cun_Con_Post'></td>"+
				"<td><input type='text' class='form-control' aria-label='' aria-describedby=''  name='cun_Con_Dep'></td>"+
				"<td><input type='text' class='form-control' aria-label='' aria-describedby=''  name='cun_Con_Phone'></td>"+
				"<td><input type='text' class='form-control' aria-label='' aria-describedby=''  name='cun_Con_Tel'></td>"+
				"<td><input type='text' class='form-control' aria-label='' aria-describedby=''  name='cun_Con_Email'></td>"+
				"<td><input type='text' class='form-control' aria-label='' aria-describedby=''  name='cun_Con_QQ'></td>"+
				"<td><input type='text' class='form-control' aria-label='' aria-describedby=''  name='cun_Con_WeChat'></td>"+
				"<td><input type='text' class='form-control' aria-label='' aria-describedby=''  name='cun_Con_Remarks'></td>"+
				"<td><button type='button' class='layui-btn layui-btn-danger' title='删除一行' onclick='deleteTrRow(this)'><i class='layui-icon'>&#xe640;</i></button></td>"+
				"</tr>");
		 		addtr.appendTo(tables);
		}else{
			index++;
			var tables=$('#khlxrs');
			var addtr = $("<tr>"+
					"<th scope='row' style='text-align: center;line-height:38px;'>"+index+"</th>"+
					"<td><input type='text' class='form-control' aria-label='' aria-describedby=''  name='cus_Con_Name'></td>"+
					"<td><input type='text' class='form-control' aria-label='' aria-describedby=''  name='cun_Con_Posstation'></td>"+
					"<td><input type='text' class='form-control' aria-label='' aria-describedby=''  name='cun_Con_Post'></td>"+
					"<td><input type='text' class='form-control' aria-label='' aria-describedby=''  name='cun_Con_Dep'></td>"+
					"<td><input type='text' class='form-control' aria-label='' aria-describedby=''  name='cun_Con_Phone'></td>"+
					"<td><input type='text' class='form-control' aria-label='' aria-describedby=''  name='cun_Con_Tel'></td>"+
					"<td><input type='text' class='form-control' aria-label='' aria-describedby=''  name='cun_Con_Email'></td>"+
					"<td><input type='text' class='form-control' aria-label='' aria-describedby=''  name='cun_Con_QQ'></td>"+
					"<td><input type='text' class='form-control' aria-label='' aria-describedby=''  name='cun_Con_WeChat'></td>"+
					"<td><input type='text' class='form-control' aria-label='' aria-describedby=''  name='cun_Con_Remarks'></td>"+
					"<td><button type='button' class='layui-btn layui-btn-danger' title='删除一行' onclick='deleteTrRow(this)'><i class='layui-icon'>&#xe640;</i></button></td>"+
					"</tr>");
					addtr.appendTo(tables);
		}     
	} 


	//表格删除一行
	function deleteTrRow(tr){
		//获得表格长度
		var khlxrSize=$('#khlxrSize').val();
		if(khlxrSize!=""){
			$(tr).parent().parent().remove();
			khlxrSize--;
			$('#khlxrSize').val(khlxrSize);
		}else{
			 $(tr).parent().parent().remove();
			 index--;
		}
	}

	function deleteData(cus_Con_Id){
		//ajax实现删除数据
		layer.confirm('您确定要删除该数据吗？此操作将不可逆转!!!', {
			  btn: ['确定','取消'], //按钮
			  title:'提示'},function(index){
				//删除数据
				  $.ajax({  
					    type: "post",  
					    url:  "<c:url value='/customer/deleteKhlxrById.do'/>",
					    dataType: 'json',
					    async:false,
					    data:{"id":cus_Con_Id},
					    error:function(){
					    	alert("出错");
					    },
					    success: function (data) {  
						    if(data.flag){
					    		layer.close(index);
					    		window.location.reload();
						    }
					    }  
					});
			  }
		  );
	}
	
</script>
</body>
</html>