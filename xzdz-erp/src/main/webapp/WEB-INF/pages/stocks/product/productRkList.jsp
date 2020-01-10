<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>成品入库列表</title>
<link rel="stylesheet" href="../layui-v2.5.5/layui/css/layui.css">
<link rel="stylesheet" href="../login/css/static.css">
<link rel="stylesheet" href="../bootstrap-3.3.7-dist/css/bootstrap.min.css"> 
<link rel="stylesheet" href="../bootstrap-3.3.7-dist/css/bootstrap-theme.min.css">
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@page isELIgnored="false"%>
</head>
<body onload="refreshAndClose()">
<form class="layui-form" action="<c:url value='/xmcgrk/rk.do'/>" method="post" id="myForm">
	<input type="hidden" value='<c:url value="/"/>' id="url">
	<input type="hidden" id="hwnrData" name="hwnrData">
	<input type="hidden" id="flag" value="${flag}"> 
	<div class="layui-form-item layui-form-text">
	  <label class="layui-form-label" style="width: 135px;"></label>
	  <div class="layui-input-block">
		<table class="table table-bordered" id="hwcpnrs" style="width: 100%;margin-left: -40px;">
		  <thead>
		    <tr>
		      <th scope="col" style="text-align: center;width: 1%;">
				    <input type="checkbox" lay-skin="primary" id="dx" lay-filter="dx">
		      </th>
		      <th scope="col" style="text-align: center;width: 4%">序号</th>
		      <th scope="col" style="text-align: center;width: 12%">产品名称</th>
		      <th scope="col" style="text-align: center;width: 12%">规格型号</th>
		      <th scope="col" style="text-align: center;width: 6%">入库数量</th>
		   	  <th scope="col" style="text-align: center;width: 6%">入库数量</th>
		      <th scope="col" style="text-align: center;width: 12%">备注</th>
		    </tr>
		    <c:forEach items="${list}" var="l">
		    	<tr>
		    		<td style='text-align: center;'>
						   <input type="checkbox"  lay-skin="primary" name="qx" lay-filter="qx">
					</td> 
		    		<th scope='row' style='text-align: center;line-height:75px;'></th>
		    		<td>
		    			<input type="hidden" value="${l.product_Id}" name="product_Id">
		    			<input type='text' class='form-control' aria-label='' aria-describedby='' name='product_Name' value="${l.product_Name }" title="${l.product_Name }" disabled=""  style="margin-top: 20px;">
		    		</td>
		    		<td><input type='text' class='form-control' aria-label='' aria-describedby='' name="specification_Type"   value="${l.specification_Type }" title="${l.specification_Type }" disabled=""  style="margin-top: 20px;"></td>
		    		<td><input type='text' class='form-control' aria-label='' aria-describedby='' name="numbers"  style="margin-top: 20px;"></td>
		    		<td><input type='text' class='form-control' aria-label='' aria-describedby='' name="unit" value="${l.unit }"  title="${l.unit }" disabled="" style="margin-top: 20px;"></td>
		    		<td><input type='text' class='form-control' aria-label='' aria-describedby='' name="factory_Price"   value="${l.factory_Price }" title="${l.factory_Price }" disabled="" style="margin-top: 20px;"></td>
		    		<td><input type='text' class='form-control' aria-label='' aria-describedby='' name="channel_Price"  value="${l.channel_Price }"  title="${l.channel_Price}" disabled="" style="margin-top: 20px;" ></td>
		    		<td><input type='text' class='form-control' aria-label='' aria-describedby='' name="market_Value"   value="${l.market_Value }" title="${l.market_Value }"  disabled="" style="margin-top: 20px;"></td>
		    		<td><textarea class="form-control" rows="3" name="remarks"></textarea></td>
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
	      <button class="layui-btn" type="button" id="tj" style="width:35%;margin-top:10px;margin-left: -190px;">确认入库</button>
	    </div>
	</div>
</form>
<script src="../layui-v2.5.5/layui/layui.js" charset="utf-8"></script>
<script type="text/javascript" src="../jquery/jquery-3.3.1.js"></script>
<!-- 注意：如果你直接复制所有代码到本地，上述js路径需要改成你本地的 -->
<script>
layui.use(['form','layedit', 'laydate'], function(){
  var url=$('#url').val();
  var form= layui.form;
  var layer = layui.layer;
  var layedit = layui.layedit;
  var hwdms=$('#hwdms').val();
  hwnrsh();
  
  form.on('checkbox(dx)', function (data) {
      var a = data.elem.checked;
      if (a == true) {
          $('input[name="qx"]').prop("checked", true);
          form.render('checkbox');
      } else {
    	  $('input[name="qx"]').prop("checked", false);
          form.render('checkbox');
      }
  });

});

	function hwnrsh(){
		var len = $('table tr').length;
	    for(var i = 1;i<len;i++){
	        $('table tr:eq('+i+') th:first').text(i);
	    }
	     
	}

	//提交编辑后的货物产品内容
	function editHWCPNRS(){
		var hwnrData=$('#hwnrData').val();
		//货物当前表格
		var tables=$('#hwcpnrs');
		//获得表格所有行
		var rows=tables[0].rows;
		var kg=false;
		//遍历表格
		for(var i=1;i<rows.length;i++){
			//获得表格前的复选框
			var checboxs=$('input[name="qx"]')[i-1].checked;
			if(checboxs==true){
				//获得表格数据中的已存在的成品主键
				product_Id=$('input[name="product_Id"]')[i-1].value;
				//产品名称
				var product_Name=$('input[name="product_Name"]')[i-1].value;
				//规格型号
				var specification_Type=$('input[name="specification_Type"]')[i-1].value;
				//单位
				var unit=$('input[name="unit"]')[i-1].value;
				//出厂价
				var factory_Price=$('input[name="factory_Price"]')[i-1].value;
				//渠道价
				var channel_Price=$('input[name="channel_Price"]')[i-1].value;
				//市场价
				var market_Value=$('input[name="market_Value"]')[i-1].value;
				//数量
				var numbers=$('input[name="numbers"]')[i-1].value;
				//备注
				var remarks=$('input[name="remarks"]')[i-1].value;
				if(numbers==""){
					return layer.alert("请填写入库数量");
				}
				//创建成品对象
				var product=new Object();
				product.cus_Con_Name=lxrmc;
				khlxr.cun_Con_Posstation=gw;
				khlxr.cun_Con_Post=zw;
				khlxr.cun_Con_Dep=bm;
				khlxr.cun_Con_Phone=sjh;
				khlxr.cun_Con_Tel=bgdh;
				khlxr.cun_Con_Email=yx;
				khlxr.cun_Con_QQ=qq;
				khlxr.cun_Con_WeChat=wx;
				khlxr.cun_Con_Remarks=bz;
				khlxrs.push(khlxr);
				
				
		   }
		}
		$.ajax({
			type : "post",
			url : "<c:url value='/customer/saveCustomer.do'/>",
			async : false,
			contentType :"application/json;charsetset=UTF-8",//必须
			dataType : 'json',
			data:JSON.stringify(kh),
			error : function() {
				alert("出错");
			},
			success : function(data) {
				if(data.flag){
					saveCustomerlxr();
				}
			}
		});
	
	}

	function refreshAndClose(){
		var flag=$('#flag').val();
		if(flag){
			window.parent.location.reload();
			window.close();
		} 
	}

	//提交表单
	$('#tj').click(function(){
		var flag=editHWCPNRS();
		if(flag==false){
			return layer.alert('请勾选需要入库的成品',{icon: 0});
		}
		var form=document.getElementById('myForm');
		form.submit();
	});
</script>
</body>
</html>