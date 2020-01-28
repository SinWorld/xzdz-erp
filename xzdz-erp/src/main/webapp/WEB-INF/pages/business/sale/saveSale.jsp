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
<title>新增销售合同</title>
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
<body style="width:100%;padding:0px; margin:0px;text-align: center;" onload="refreshAndClose()">
	<div style="width:1280px;height:auto;padding:0px; margin:0 auto;" id="main">
		<form class="layui-form" action='<c:url value=""/>' method="post"  enctype="multipart/form-data">
			<input type="hidden" id="url" value='<c:url value="/"/>'>
			<input type="hidden" id="flag" value="${flag}">
			<input type="hidden" id="supplier" name="supplier">
			<input type="hidden" id="fjsx" name="fjsx"> 
		
			<div class="layui-form-item" style="margin-top: 3%;">
			    <label class="layui-form-label" style="width: 148px;">合同名称</label>
			    <div class="layui-input-block">
			      <input type="text" id="sales_Contract_Name" lay-verify="sales_Contract_Name" autocomplete="off" placeholder="合同名称" class="layui-input" style="width:72%">
			    </div>
			</div>
			
			<div class="layui-form-item">
			     <div class="layui-inline" style="top:9px;left: -280px;">
				      <label class="layui-form-label" style="width:150px;">供货单位</label>
				      <div class="layui-input-inline">
				        <input type="text"  id="ghdw" lay-verify="ghdw" autocomplete="off" class="layui-input bj" style="width: 280px;" disabled="">
				      </div>
			     </div>
			     
			      <div class="layui-inline" style="top:9px;left: -80px;">
				      <label class="layui-form-label" style="width:150px;">合同编号</label>
				      <div class="layui-input-inline">
				        <input type="text" name="contract_Code" id="contract_Code" lay-verify="contract_Code" autocomplete="off" class="layui-input bj" style="width: 280px;" disabled="">
				      </div>
			     </div>
			 </div>
			 
			 <div class="layui-form-item">
			     <div class="layui-inline" style="left: -215px;">
				  	<label class="layui-form-label" style="width:100px;">需方单位</label>
					<div class="layui-input-inline" style="text-align: left;width: 280px;">
						<select name="customer" id="customer" lay-filter="customer" lay-verify="customer">
							<option value="" selected="selected">请选择需方单位</option>
						</select>
					</div>
				 </div>
			     
			      <div class="layui-inline" style="left: -95px;">
				      <label class="layui-form-label" style="width: 139px;">签订日期</label>
				      <div class="layui-input-inline">
				        <input type="text" name="qd_Date" id="qd_Date" lay-verify="qd_Date" placeholder="yyyy-mm-dd" autocomplete="off" class="layui-input" style="width: 280px;">
				      </div>
			    </div>
			 </div>
			 
		  <div class="layui-form-item layui-form-text">
	  		<label class="layui-form-label" style="width: 295px;">一、产品名称、规格型号、单价</label>
	  		<br>
		  	<div class="layui-input-block" style="text-align: left;left: -165px;top:10px;">
				<button type="button" class="layui-btn layui-btn-normal" onclick="addRow()"><i class="layui-icon">&#xe608;</i>新增一行</button>	
			 </div>
			  <div class="layui-input-block" style="top:15px;left: 10px;">
				<table class="table table-bordered" id="khlxrs" style="width: 100%">
				  <thead>
				    <tr>
				      <th scope="col" style="text-align: center;width: 5%">序号</th>
				      <th scope="col" style="text-align: center;width: 15%">物资名称</th>
				      <th scope="col" style="text-align: center;width: 15%">规格型号</th>
				      <th scope="col" style="text-align: center;width: 10%">数量</th>
				      <th scope="col" style="text-align: center;width: 10%">单位</th>
				      <th scope="col" style="text-align: center;width: 10%">单价(元)</th>
				      <th scope="col" style="text-align: center;width: 10%">金额(元)</th>
				      <th scope="col" style="text-align: center;width: 15%">备注</th>
				      <th scope="col" style="text-align: center;width: 10%">操作</th>
				    </tr>
				  </thead>
				  <tbody>
				  	<tr>
				  		<td>
				  		</td>
				  		<td>
				  			合计总金额
				  		</td>
				  		<td colspan="7">
				  			<input type='text' class='form-control bj' aria-label='' aria-describedby='' style="width: 165px;" disabled="" id="totalprice">
				  		</td>
				  	</tr>
				  </tbody>
				</table>
			</div>
		</div>
			
		 <div class="layui-form-item layui-form-text">
		    <label class="layui-form-label" style="width:120px;">备注</label>
		    <div class="layui-input-block">
		      <textarea placeholder="请输入内容" name="remarks"  lay-verify="remarks" id="remarks" class="layui-textarea" style="width:76.5%"></textarea>
		    </div>
		 </div>
		 
		  <div class="layui-form-item">
		    <label class="layui-form-label" style="width: 200px;">二、质量技术要求标准</label>
		    <div class="layui-input-block">
		      <input type="text" id="zljsyq" name="zljsyq" lay-verify="zljsyq" autocomplete="off" placeholder="" class="layui-input" style="width:69.5%" value="按JB8168-1999。质保期限为发货之日起壹年。">
		    </div>
		  </div>
		  
		   <div class="layui-form-item">
		    <label class="layui-form-label" style="width: 200px;">三、交货时间及地点 </label>
		    <div class="layui-input-block">
		      <input type="text" id="jhsjjdd" name="jhsjjdd" lay-verify="jhsjjdd" autocomplete="off" placeholder="" class="layui-input" style="width:69.5%" value="需方仓库。">
		    </div>
		  </div>
		  
		   <div class="layui-form-item">
		    <label class="layui-form-label" style="width: 200px;">四、运输及费用 </label>
		    <div class="layui-input-block">
		      <input type="text" id="ysjfy" name="ysjfy" lay-verify="ysjfy" autocomplete="off" placeholder="" class="layui-input" style="width:69.5%" value="运输费用由供方承担。">
		    </div>
		  </div>
		  
		  <div class="layui-form-item">
		    <label class="layui-form-label" style="width: 200px;">五、付款方式  </label>
		    <div class="layui-input-block">
		      <input type="text"  id="fkfs" name="fkfs" lay-verify="fkfs" autocomplete="off" placeholder="" class="layui-input" style="width:69.5%" value="货到付款。">
		    </div>
		  </div>
		  
		   <div class="layui-form-item">
		    <label class="layui-form-label" style="width: 200px;">六、违约责任   </label>
		    <div class="layui-input-block">
		      <input type="text" id="wyzr" name="wyzr" lay-verify="wyzr" autocomplete="off" placeholder="" class="layui-input" style="width:69.5%" value="按合同法">
		    </div>
		  </div>
		  
		   <div class="layui-form-item">
		    <label class="layui-form-label" style="width: 200px;">七 </label>
		    <div class="layui-input-block">
		      <input type="text" id="wjsy" name="wjsy" lay-verify="wjsy" autocomplete="off" placeholder="" class="layui-input" style="width:69.5%;margin-left: 90px;" value="未尽事宜，双方协商解决，如协商无效，通过司法程序解决。">
		    </div>
		  </div>
		  
		   <div class="layui-form-item">
		    <label class="layui-form-label" style="width: 200px;">八 </label>
		    <div class="layui-input-block">
		      <input type="text" id="htfs" name="htfs" lay-verify="htfs" autocomplete="off" placeholder="" class="layui-input" style="width:69.5%;margin-left: 90px;" value="本合同一式两份，双方各执一份，双方签字盖章后生效。">
		    </div>
		  </div>
		  
		   <div class="layui-form-item">
		    <label class="layui-form-label" style="width: 200px;">九 </label>
		    <div class="layui-input-block">
		      <input type="text" id="sxrq" name="sxrq" id="sxrq" lay-verify="sxrq" autocomplete="off" placeholder="" class="layui-input" style="width:69.5%;margin-left: 90px;">
		    </div>
		  </div>
		  
		  <div class="layui-form-item layui-form-text">
			  <div class="layui-input-block">
				<table class="table table-bordered"  style="width: 100%">
				  <thead>
				    <tr>
				      <th scope="col" style="text-align: center;width: 50%">供方</th>
				      <th scope="col" style="text-align: center;width: 50%">需方</th>
				    </tr>
				  </thead>
				  <tbody>
				  <tr>
				    	<td>
							<div class="layui-form-item">
							 <label class="layui-form-label" style="width:90px;">单位名称  </label>
							    <div class="layui-input-block">
							      <input type="text" id="gfdwmc" lay-verify="gfdwmc" autocomplete="off" placeholder="" class="layui-input bj" disabled="">
							    </div>
							 </div>
						</td>				    	
				    	<td>
							<div class="layui-form-item">
							 <label class="layui-form-label" style="width:90px;">单位名称  </label>
							    <div class="layui-input-block">
							      <input type="text" id="xfdwmc" lay-verify="xfdwmc" autocomplete="off" placeholder="" class="layui-input bj" disabled="">
							    </div>
							 </div>
						</td>
				    </tr>
				     <tr>
				    	<td>
							<div class="layui-form-item">
							 <label class="layui-form-label" style="width:90px;">单位地址 </label>
							    <div class="layui-input-block">
							      <input type="text" id="gfdwdz" lay-verify="gfdwdz" autocomplete="off" placeholder="" class="layui-input bj" disabled="">
							    </div>
							 </div>
						</td>
				    	<td>
							<div class="layui-form-item">
							 <label class="layui-form-label" style="width:90px;">单位地址 </label>
							    <div class="layui-input-block">
							      <input type="text" id="xfdwdz" lay-verify="xfdwdz" autocomplete="off" placeholder="" class="layui-input bj" disabled="">
							    </div>
							 </div>
						</td>
				    </tr>
				    <tr>
				    	<td>
							<div class="layui-form-item">
							 <label class="layui-form-label" style="width:110px;">法定代表人 </label>
							    <div class="layui-input-block">
							      <input type="text" id="gffddbr" lay-verify="gffddbr" autocomplete="off" placeholder="" class="layui-input bj" disabled="">
							    </div>
							 </div>
						</td>
				    	<td>
							<div class="layui-form-item">
							 <label class="layui-form-label" style="width:110px;">法定代表人 </label>
							    <div class="layui-input-block">
							      <input type="text" id="xffddbr" lay-verify="xffddbr" autocomplete="off" placeholder="" class="layui-input bj" disabled="">
							    </div>
							 </div>
						</td>
				    </tr>
				     <tr>
				    	<td>
							<div class="layui-form-item">
							 <label class="layui-form-label" style="width:110px;">委托代理人</label>
							    <div class="layui-input-block">
							      <input type="text" id="gfwtdlr" lay-verify="gfwtdlr" autocomplete="off" placeholder="" class="layui-input bj" disabled="">
							    </div>
							 </div>
						</td>
				    	<td>
							<div class="layui-form-item">
							 <label class="layui-form-label" style="width:110px;">委托代理人</label>
							    <div class="layui-input-block">
							      <input type="text" id="xfwtdlr" lay-verify="xfwtdlr" autocomplete="off" placeholder="" class="layui-input bj" disabled="">
							    </div>
							 </div>
						</td>
				    </tr>
				    <tr>
				    	<td>
							<div class="layui-form-item">
							 <label class="layui-form-label" style="width:90px;">开户行</label>
							    <div class="layui-input-block">
							      <input type="text" id="gfkhh" lay-verify="gfkhh" autocomplete="off" placeholder="" class="layui-input bj" disabled="">
							    </div>
							 </div>
						</td>
				    	<td>
							<div class="layui-form-item">
							 <label class="layui-form-label" style="width:90px;">开户行</label>
							    <div class="layui-input-block">
							      <input type="text" id="xfkhh" lay-verify="xfkhh" autocomplete="off" placeholder="" class="layui-input bj" disabled="">
							    </div>
							 </div>
						</td>
				    </tr>
				    <tr>
				    	<td>
							<div class="layui-form-item">
							 <label class="layui-form-label" style="width:90px;">帐号</label>
							    <div class="layui-input-block">
							      <input type="text" id="gfzh" lay-verify="gfzh" autocomplete="off" placeholder="" class="layui-input bj" disabled="">
							    </div>
							 </div>
						</td>
				    	<td>
							<div class="layui-form-item">
							 <label class="layui-form-label" style="width:90px;">帐号</label>
							    <div class="layui-input-block">
							      <input type="text" id="xfzh" lay-verify="xfzh" autocomplete="off" placeholder="" class="layui-input bj" disabled="">
							    </div>
							 </div>
						</td>
				    </tr>
				    <tr>
				    	<td>
							<div class="layui-form-item">
							 <label class="layui-form-label" style="width:90px;">税号</label>
							    <div class="layui-input-block">
							      <input type="text" id="gfsh" lay-verify="gfsh" autocomplete="off" placeholder="" class="layui-input bj" disabled="">
							    </div>
							 </div>
						</td>
				    	<td>
							<div class="layui-form-item">
							 <label class="layui-form-label" style="width:90px;">税号</label>
							    <div class="layui-input-block">
							      <input type="text" id="xfsh" lay-verify="xfsh" autocomplete="off" placeholder="" class="layui-input bj" disabled="">
							    </div>
							 </div>
						</td>
				    </tr>
				    <tr>
				    	<td>
							<div class="layui-form-item">
							 <label class="layui-form-label" style="width:90px;">电话 </label>
							    <div class="layui-input-block">
							      <input type="text" id="gfdh" lay-verify="gfdh" autocomplete="off" placeholder="" class="layui-input bj" disabled="">
							    </div>
							 </div>
						</td>
				    	<td>
							<div class="layui-form-item">
							 <label class="layui-form-label" style="width:90px;">电话 </label>
							    <div class="layui-input-block">
							      <input type="text" id="xfdh" lay-verify="xfdh" autocomplete="off" placeholder="" class="layui-input bj" disabled="">
							    </div>
							 </div>
						</td>
				    </tr>
				     <tr>
				    	<td>
							<div class="layui-form-item">
							 <label class="layui-form-label" style="width:90px;">传真  </label>
							    <div class="layui-input-block">
							      <input type="text" id="gfcz" lay-verify="gfcz" autocomplete="off" placeholder="" class="layui-input bj" disabled="">
							    </div>
							 </div>
						</td>
				    	<td>
							<div class="layui-form-item">
							 <label class="layui-form-label" style="width:90px;">传真  </label>
							    <div class="layui-input-block">
							      <input type="text" id="xfcz" lay-verify="wyzr" autocomplete="off" placeholder="" class="layui-input bj" disabled="">
							    </div>
							 </div>
						</td>
				    </tr>
				    
				  </tbody>
				</table>
			</div>
		</div>
		
		<!--附件 -->
			 <div class="layui-upload">
				  <button type="button" class="layui-btn layui-btn-normal" id="testList" style="margin-left: -91.5%">选择多文件</button> 
				  <div class="layui-upload-list">
				    <table class="layui-table" style="width: 100%;">
				      <thead>
				        <tr>
					        <th style="text-align: center;">文件名</th>
					        <th style="text-align: center;">大小</th>
					        <th style="text-align: center;">状态</th>
					        <th style="text-align: center;">操作</th>
				      	</tr>
				      </thead>
				      <tbody id="demoList"></tbody>
				    </table>
				  </div>
				  <button type="button" class="layui-btn" id="testListAction" style="margin-left: -91.5%">开始上传</button>
			</div> 
	
		<div class="layui-form-item" style="text-align: center;">
		    <div class="layui-input-block">
		      <button class="layui-btn" lay-submit="" lay-filter="" style="width:25%;margin-top:10px;margin-left:-315px;" onclick="saveContract()" type="button">立即提交</button>
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
  allCustomer(form);
  allCompany(form);
  htbh();
  //日期
  laydate.render({
    elem: '#qd_Date'
    ,value: new Date()
    ,format: 'yyyy-MM-dd'
  });

  qdrqValue();
 
  //创建一个编辑器
  var editIndex = layedit.build('LAY_demo_editor');



//多文件列表示例
  var fjsx=$('#fjsx').val();
  var demoListView = $('#demoList')
  ,uploadListIns = upload.render({
    elem: '#testList'
    ,url: '<c:url value="/sales/upload.do"/>'
    ,accept: 'file'
    ,multiple: true
    ,auto: false
    ,bindAction: '#testListAction'
    ,choose: function(obj){   
      var files = this.files = obj.pushFile(); //将每次选择的文件追加到文件队列
      //读取本地文件
      obj.preview(function(index, file, result){
        var tr = $(['<tr id="upload-'+ index +'">'
          ,'<td>'+ file.name +'</td>'
          ,'<td>'+ (file.size/1014).toFixed(1) +'kb</td>'
          ,'<td>等待上传</td>'
          ,'<td>'
            ,'<button class="layui-btn layui-btn-xs demo-reload layui-hide">重传</button>'
            ,'<button class="layui-btn layui-btn-xs layui-btn-danger demo-delete">删除</button>'
          ,'</td>'
        ,'</tr>'].join(''));
        
        //单个重传
        tr.find('.demo-reload').on('click', function(){
          obj.upload(index, file);
        });
        
        //删除
        tr.find('.demo-delete').on('click', function(){
          delete files[index]; //删除对应的文件
          tr.remove();
          uploadListIns.config.elem.next()[0].value = ''; //清空 input file 值，以免删除后出现同名文件不可选
        });
        
        demoListView.append(tr);
      });
    }
    ,done: function(res, index, upload){
      if(res.code == 0){ //上传成功
        var tr = demoListView.find('tr#upload-'+ index)
        ,tds = tr.children();
        tds.eq(2).html('<span style="color: #5FB878;">上传成功</span>');
        tds.eq(3).html(''); //清空操作
        //将附件属性拼接字符串提交至后端
         var fj=res.data;
		 //将json串转换为字符串
		 var str = JSON.stringify(fj);
        if(undefined!=fjsx){
			 fjsx=fjsx+","+str;
			 $('#fjsx').val(fjsx);
		 }else{
			 fjsx=str;
			 $('#fjsx').val(fjsx);
		 }
        return delete this.files[index]; //删除文件队列已经上传成功的文件
      }
      this.error(index, upload);
    }
    ,error: function(index, upload){
      var tr = demoListView.find('tr#upload-'+ index)
      ,tds = tr.children();
      tds.eq(2).html('<span style="color: #FF5722;">上传失败</span>');
      tds.eq(3).find('.demo-reload').removeClass('layui-hide'); //显示重传
    }
  });

	//ajax实现下拉需方单位带出客户的相关属性
	form.on('select(customer)', function(data){
		//获得项目信息主键
		var customer_Id=data.value;
		if(customer_Id==""||customer_Id==undefined){
			$('#xfdwmc').val("");//需方单位名称
			$('#xfdwdz').val("");//需方单位地址
			$('#xffddbr').val("");//需方法定代表人
			$('#xfwtdlr').val("");//需方委托代理人
			$('#xfkhh').val("");//需方开户行
			$('#xfzh').val("");//需方账户
			$('#xfsh').val("");//需方税号
			$('#xfdh').val("");//需方电话
			$('#xfcz').val("");//需方传真
			return;
		}
		//ajax根据Id查询需方单位并设置其它属性值
		$.ajax({
			type : "post",
			url : "<c:url value='/sales/customer.do'/>",
			async : false,
			dataType : 'json',
			data :{"customer_Id":customer_Id},
			error : function() {
				alert("出错");
			},
			success : function(msg) {
				$('#xfdwmc').val(msg.customer.unit_Name);//需方单位名称
				$('#xfdwdz').val(msg.customer.registered_Address);//需方单位地址
				$('#xffddbr').val(msg.customer.legal_person);//需方法定代表人
				$('#xfwtdlr').val(msg.customer.legal_person);//需方委托代理人
				$('#xfkhh').val(msg.customer.opening_Bank);//需方开户行
				$('#xfzh').val(msg.customer.account_Number);//需方账户
				$('#xfsh').val(msg.customer.duty_Paragraph);//需方税号
				$('#xfdh').val(msg.customer.telPhone);//需方电话
				$('#xfcz').val(msg.customer.fax);//需方传真
			}
		});
	});
  
});

	//表格新增一行
	var index=0;
	function addRow(){
		index++;
		//var tables=$('#khlxrs');
		var length=$("#khlxrs tbody").find("tr").length;
		//获取表格中的第一行
		var fristRow;
		for(var i=0;i<length;i++){
			var text="	合计总金额	";
			if($("#khlxrs tbody").find("tr")[i].innerText==text){
				fristRow=$("#khlxrs tbody").find("tr:eq('"+i+"')");
				break;
			}
		}
		var addtr = $("<tr>"+
				"<th scope='row' style='text-align: center;line-height:38px;'>"+index+"</th>"+
				"<td><input type='text' class='form-control' aria-label='' aria-describedby=''  name='material_Name'></td>"+
				"<td><input type='text' class='form-control' aria-label='' aria-describedby=''  name='specification_Type'></td>"+
				"<td><input type='text' class='form-control' aria-label='' aria-describedby=''  name='sl' onchange='jejs("+index+")'></td>"+
				"<td><input type='text' class='form-control' aria-label='' aria-describedby=''  name='unit'></td>"+
				"<td><input type='text' class='form-control' aria-label='' aria-describedby=''  name='price' onchange='jejs("+index+")'></td>"+
				"<td><input type='text' class='form-control bj' aria-label='' aria-describedby=''  name='total_price' readonly='readonly'></td>"+
				"<td><input type='text' class='form-control' aria-label='' aria-describedby=''  name='bz'></td>"+
				"<td><button type='button' class='layui-btn layui-btn-danger' title='删除一行' onclick='deleteTrRow(this)'><i class='layui-icon'>&#xe640;</i></button></td>"+
				"</tr>");
		fristRow.before(addtr);  
		//addtr.appendTo(tables);     
	}

	//表格删除一行
	function deleteTrRow(tr){
	    $(tr).parent().parent().remove();
	    index--;
		var data=$('input[name="total_price"]');
		//遍历该数组
		var totalPrice=0;
		for(var i=0;i<data.length;i++){
			if(data[i].value!=undefined){
				//设置总金额
				totalPrice=(totalPrice*1)+(data[i].value*1);
			}
		}
		$('#totalprice').val(totalPrice);
	} 

	//ajax实现查询所有的客户
	function  allCustomer(form){
		$.ajax({
			type : "post",
			url : "<c:url value='/sales/customerList.do'/>",
			async : false,
			dataType : 'json',
			error : function() {
				alert("出错");
			},
			success : function(msg) {
				for (var i = 0; i < msg.length; i++) {
					$("#customer").append(
							"<option value='"+msg[i].customer_Id+"'>"+ msg[i].unit_Name +"</option>");
				}
				form.render('select');
			}
		});
	}


	//ajax查询供货方
	function  allCompany(form){
		$.ajax({
			type : "post",
			url : "<c:url value='/sales/company.do'/>",
			async : false,
			dataType : 'json',
			error : function() {
				alert("出错");
			},
			success : function(msg) {
				$('#supplier').val(msg.unit.unit_Id);//供货方
				$("#ghdw").val(msg.unit.unit_Name);//供方单位名称
				$('#gfdwmc').val(msg.unit.unit_Name);//供方单位名称
				$('#gfdwdz').val(msg.unit.registered_Address);//供方单位地址
				$('#gffddbr').val(msg.unit.legal_person);//供方法定代表人
				$('#gfwtdlr').val(msg.unit.legal_person);//供方委托代理人
				$('#gfkhh').val(msg.unit.opening_Bank);//供方开户行
				$('#gfzh').val(msg.unit.account_Number);//供方账户
				$('#gfsh').val(msg.unit.duty_Paragraph);//供方税号
				$('#gfdh').val(msg.unit.telPhone);//供方电话
				$('#gfcz').val(msg.unit.fax);//供方传真
			}
		});
		form.render();
	}

	function qdrqValue(){
		var qdrq=$('#qd_Date').val();
		$('#sxrq').val("本协议从"+qdrq.substr(0,8)+"起执行，有效期一年。");
	}
	

	function refreshAndClose(){
		var flag=$('#flag').val();
		if(flag){
			window.parent.location.reload();
			window.close();
		} 
	}

	//设置金额自动计算
	function jejs(index){
		//获得数量
		var sl=$('input[name="sl"]')[index-1].value;
		//获得单价
		var dj=$('input[name="price"]')[index-1].value;
		if(sl!=""&&dj!=""){
			//设置金额
			$('input[name="total_price"]')[index-1].value=(sl*1)*(dj*1);
		}
		if(sl==""||dj==""){
			//设置金额
			$('input[name="total_price"]')[index-1].value="";
		}
		var data=$('input[name="total_price"]');
		//遍历该数组
		var totalPrice=0;
		for(var i=0;i<data.length;i++){
			if(data[i].value!=undefined){
				//设置总金额
				totalPrice=(totalPrice*1)+(data[i].value*1);
			}
		}
		$('#totalprice').val(totalPrice);
	}

	//编号生成
	function htbh(){
		$.ajax({
			type : "post",
			url : "<c:url value='/sales/htbh.do'/>",
			async : false,
			dataType : 'json',
			error : function() {
				alert("出错");
			},
			success : function(msg) {
				$('#contract_Code').val(msg.htbh);
			}
		});
	}

	//新增销售合同
	function saveContract(){
		//创建销售合同对象
		var xsht=new Object();
		//获得合同名称
		var htmc=$('#sales_Contract_Name').val();
		//获得供货单位
		var ghdw=$('#supplier').val();
		//合同编号
		var htbh=$('#contract_Code').val();
		//需求方
		var xqf=$('#customer').val();
		//签订日期
		var qdrq=$('#qd_Date').val();
		//备注
		var bz=$('#remarks').val();
		//质量技术要求
		var zljsyq=$('#zljsyq').val();
		//交货时间及地点
		var jhsjjdd=$('#jhsjjdd').val();
		//运输及费用
		var ysjfy=$('#ysjfy').val();
		//付款方式
		var fkfs=$('#fkfs').val();
		//违约责任
		var wyzr=$('#wyzr').val();
		//未尽事宜
		var wjsy=$('#wjsy').val();
		//合同份数
		var htfs=$('#htfs').val();
		//生效日期
		var sxrq=$('#sxrq').val();
		//附件
		var fjsx=$('#fjsx').val();
		xsht.sales_Contract_Name=htmc;
		xsht.contract_Code=htbh;
		xsht.customer=xqf;
		xsht.qd_Date=qdrq;
		xsht.remarks=bz;
		xsht.zljsyq=zljsyq;
		xsht.jhsjjdd=jhsjjdd;
		xsht.ysjfy=ysjfy;
		xsht.fkfs=fkfs;
		xsht.wyzr=wyzr;
		xsht.wjsy=wjsy;
		xsht.htfs=htfs;
		xsht.sxrq=sxrq;
		xsht.supplier=ghdw;
		xsht.unitAddress="";
		xsht.agent="";
		xsht.cus_Address="";
		xsht.fjsx=fjsx;
		$.ajax({
			type : "post",
			url : "<c:url value='/sales/saveSales.do'/>",
			async : false,
			contentType :"application/json;charsetset=UTF-8",//必须
			dataType : 'json',
			data:JSON.stringify(xsht),
			error : function() {
				alert("出错");
			},
			success : function(data) {
				if(data.flag){
					saveContractOrder();
				}
			}
		});
	}


	//新增产品名称
	function saveContractOrder(){
		//货物当前表格
		var tables=$('#khlxrs');
		//获得表格所有行
		var rows=tables[0].rows;
		//创建货物清单数组
		var orders=new Array();
		//遍历表格
		for(var i=0;i<rows.length;i++){
			if(i+1==rows.length-1){
				break
				//物资名称
				var wzmc=$('input[name="material_Name"]')[i].value;
				//规格型号
				var ggxh=$('input[name="specification_Type"]')[i].value;
				//数量
				var sl=$('input[name="sl"]')[i].value;
				//单位
				var dw=$('input[name="unit"]')[i].value;
				//单价
				var dj=$('input[name="price"]')[i].value;
				//金额
				var je=$('input[name="total_price"]')[i].value;
				//备注
				var bz=$('input[name="bz"]')[i].value;
				//创建货物清单对象
				var order=new Object();
				order.material_Name=wzmc;
				order.specification_Type=ggxh;
				order.sl=sl;
				order.unit=dw;
				order.price=dj;
				order.total_price=je;
				order.bz=bz;
				orders.push(order);
			}else{
				//物资名称
				var wzmc=$('input[name="material_Name"]')[i].value;
				//规格型号
				var ggxh=$('input[name="specification_Type"]')[i].value;
				//数量
				var sl=$('input[name="sl"]')[i].value;
				//单位
				var dw=$('input[name="unit"]')[i].value;
				//单价
				var dj=$('input[name="price"]')[i].value;
				//金额
				var je=$('input[name="total_price"]')[i].value;
				//备注
				var bz=$('input[name="bz"]')[i].value;
				//创建货物清单对象
				var order=new Object();
				order.material_Name=wzmc;
				order.specification_Type=ggxh;
				order.sl=sl;
				order.unit=dw;
				order.price=dj;
				order.total_price=je;
				order.bz=bz;
				orders.push(order);
			}
		}
		$.ajax({
			type : "post",
			url : "<c:url value='/sales/saveOrder.do'/>",
			async : false,
			contentType :"application/json;charsetset=UTF-8",//必须
			dataType : 'json',
			data:JSON.stringify(orders),
			error : function() {
				alert("出错");
			},
			success : function(msg) {
				if(msg.flag){
					window.location.reload();
					window.close();
				} 
			}
		});
	}
</script>
</body>
</html>