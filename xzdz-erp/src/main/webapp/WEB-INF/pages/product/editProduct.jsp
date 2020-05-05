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
<title>编辑成品</title>
<link rel="stylesheet" href="../layui-v2.5.5/layui/css/layui.css">
<script src="../jquery/jquery-3.3.1.js"></script>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@page isELIgnored="false" %>
<style>
  .bj{background-color: #F0F0F0}
 </style>
</head>
<body style="width:100%;padding:0px; margin:0px;text-align: center;" onload="refreshAndClose()">
	<div style="width:1280px;height:auto;padding:0px; margin:0 auto;" id="main">
		<form class="layui-form" action='<c:url value="/product/editProduct.do"/>' method="post">
		<input type="hidden" id="url" value='<c:url value="/"/>'>
		<input type="hidden" id="flag" value="${flag}">
		<input type="hidden" name="product_Id" value="${product.product_Id}">
		<input type="hidden" value="${product.sales_Contract_Id}" id="xsddId">
		
			<div class="layui-form-item" style="margin-top: 5%">
				<label class="layui-form-label" style="width: 120px;">销售订单</label>
				<div class="layui-input-inline" style="width: 895px;text-align: left;">
					<select name="sales_Contract_Id" id="sales_Contract_Id" lay-filter="sales_Contract_Id" lay-verify="sales_Contract_Id" lay-search="">
						<option value="" selected="selected">请选择销售订单</option>
					</select>
				</div>
			</div>
			
			 <div class="layui-form-item">
			    <label class="layui-form-label" style="width: 120px;">产品名称</label>
			    <div class="layui-input-block">
			      <input type="text" name="product_Name" lay-verify="product_Name" autocomplete="off" placeholder="产品名称" class="layui-input" style="width:76.5%" value="${product.product_Name}">
			    </div>
			</div>
			     
			 <div class="layui-form-item">
			    <div class="layui-inline" style="top:9px;left: -50px;">
				      <label class="layui-form-label" style="width: 90px;">规格型号</label>
				      <div class="layui-input-inline">
				        <input type="text" name="specification_Type" id="specification_Type" lay-verify="specification_Type" autocomplete="off" class="layui-input" value="${product.specification_Type}">
				      </div>
			    </div>
			    
			     <div class="layui-inline" style="top:9px;left: -88px;">
				      <label class="layui-form-label" style="width:150px;">单位</label>
				      <div class="layui-input-inline">
				        <input type="text" name="unit" lay-verify="unit" autocomplete="off" class="layui-input" value="${product.unit}">
				      </div>
			     </div>
			     
			      <div class="layui-inline" style="top:9px;left: -134px;">
				      <label class="layui-form-label" style="width:150px;">生产数量</label>
				      <div class="layui-input-inline">
				        <input type="text" name="numbers" lay-verify="numbers" autocomplete="off" class="layui-input" value="${product.numbers}">
				      </div>
			      </div>
			 </div>
			
			<div class="layui-form-item">
			     <div class="layui-inline" style="top:9px;left: -117px;">
				      <label class="layui-form-label" style="width:105px;">出厂价</label>
				      <div class="layui-input-inline">
				        <input type="text" name="factory_Price" lay-verify="factory_Price" autocomplete="off" class="layui-input" id="factory_Price" onchange="ccje()" value="${product.factory_Price}">
				      	<span style="position: relative;top: -25px;right: -105px;">元</span>
				      </div>
			     </div>
			     
			    <div class="layui-inline" style="top:9px;left: -95px;">
				      <label class="layui-form-label" style="width: 90px;">渠道价</label>
				      <div class="layui-input-inline">
				        <input type="text" name="channel_Price" lay-verify="channel_Price" autocomplete="off" class="layui-input" id="channel_Price" onchange="qdje()" value="${product.channel_Price}">
				        <span style="position: relative;top: -25px;right: -105px;">元</span>
				      </div>
			    </div>
			    
			     <div class="layui-inline" style="top:9px;left: -82px;">
				      <label class="layui-form-label" style="width: 90px;">市场价</label>
				      <div class="layui-input-inline">
				        <input type="text" name="market_Value" lay-verify="market_Value" autocomplete="off" class="layui-input" id="market_Value" onchange="scje()" value="${product.market_Value}">
				        <span style="position: relative;top: -25px;right: -105px;">元</span>
				      </div>
			     </div>
		   </div>
		   
		   <div class="layui-form-item" style="top:9px;">
		     <div class="layui-inline" style="top:9px;left: -400px;">
			  	 <label class="layui-form-label" style="width: 120px;">物料Id</label>
			      <div class="layui-input-inline">
			        <input type="text" name="materielid"  autocomplete="off" class="layui-input bj" id="materielId" readonly="readonly" value="${product.materielid}">
			      </div>
		      </div>
		      
		      <div class="layui-inline" style="top:9px;left: -255px;">
		     	  <button type='button' class='layui-btn layui-btn-normal' onclick='MaterielIDInfo(this)'>物料ID详情</button>
		     </div>
		</div>
		  
		 <div class="layui-form-item layui-form-text">
		    <label class="layui-form-label" style="width:122px;">备注</label>
		    <div class="layui-input-block">
		      <textarea placeholder="请输入内容" name="remarks"  lay-verify="remarks" id="remarks" class="layui-textarea" style="width:76.5%">${product.remarks}</textarea>
		    </div>
		 </div>
	
		<div class="layui-form-item" style="text-align: center;">
		    <div class="layui-input-block">
		      <button class="layui-btn" lay-submit="" lay-filter="demo1" style="width:25%;margin-top:10px;margin-left:-315px;">立即提交</button>
		    </div>
		</div>
	</form>
 </div>
<script src="../layui-v2.5.5/layui/layui.js" charset="utf-8"></script>
<script>
layui.use(['form', 'layedit', 'laydate','upload'], function(){
  var form = layui.form
  ,layer = layui.layer
  ,layedit = layui.layedit
  ,laydate = layui.laydate
  ,upload = layui.upload;
  var url=$('#url').val();
  allSales(form);
  pageReloadSales(form);
  form.render();
  //日期
  laydate.render({
    elem: '#cont_Date'
  });
  
 
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
	  product_Name: function(value){
	      if(value==""||value==null){
	        return '产品名称不能为空';
	      }
	  }
      ,specification_Type:function(value){
	       if(value==""||value==null){
	           return '规格型号不能为空';
	       }
      }
      ,unit:function(value){
	       if(value==""||value==null){
	           return '单位不能为空';
	         }
      }
       ,numbers:function(value){
	        if(value==""||value==null){
	            return '数量不能为空';
	          }
       }
       ,factory_Price:function(value){
	         if(value==""||value==null){
	             return '出厂价不能为空';
	           }
	        }
  });
  
});

	//出厂价带两位小数点
	function ccje(){
		//获得出厂金额输入的值
		var ccje=$('#factory_Price').val()*1;
		if(ccje!=""){
			var je=ccje.toFixed(2); 
			$('#factory_Price').val(je);
		}else{
			$('#factory_Price').val("0.00");
		}
	}

	//渠道价带两位小数点
	function qdje(){
		//获得渠道金额输入的值
		var qdje=$('#channel_Price').val()*1;
		if(qdje!=""){
			var je=qdje.toFixed(2); 
			$('#channel_Price').val(je);
		}else{
			$('#channel_Price').val("0.00");
		}
	}

	//市场价带两位小数点
	function scje(){
		//获得市场金额输入的值
		var scje=$('#market_Value').val()*1;
		if(scje!=""){
			var je=scje.toFixed(2); 
			$('#market_Value').val(je);
		}else{
			$('#market_Value').val("0.00");
		}
	}

	//加载所有的销售订单
	function allSales(form){
		$.ajax({
			type : "post",
			url : "<c:url value='/product/allSales.do'/>",
			async : false,
			dataType : 'json',
			error : function() {
				alert("出错");
			},
			success : function(msg) {
				for(var i=0;i<msg.length;i++){
						$("#sales_Contract_Id").append(
					    	"<option value='"+msg[i].sales_Contract_Id+"'>"+ msg[i].sales_Contract_Name +"</option>"); 
				}
			}
		});
		 form.render('select');
	}

	//回显销售订单
  	function pageReloadSales(){
	  //获得所属销售订单下拉选
	  var xsdds=$('#sales_Contract_Id').find('option');
	  //获得已选销售订单下拉选主键
	  var xsdd=$('#xsddId').val();
	  for(var i=0;i<xsdds.length;i++){
		  if(xsdds[i].value==xsdd){
			  xsdds[i].setAttribute("selected",'true');
			  break;
		  }
	  }
  	}

	function refreshAndClose(){
		var flag=$('#flag').val();
		if(flag){
			window.parent.location.reload();
			window.close();
		} 
	}


	//跳转至物料ID列表页面
	function MaterielIDInfo(obj){
		var url=$('#url').val();
		layer.open({
	  	  	type:2,
	  	  	title:'',
	  	  	area: ['100%','100%'],
	  		shadeClose: false,
	  		resize:false,
	  	    anim: 1,
	  		content:[url+"salesMaterielId/initSalesMaterielIdList.do?index="+1,'yes']
		  });
	}

	//子页面传递值给父页面
    function ChooseAdidValues(index,wlId,ggxh) {
        if (index != ""&&wlId!=""&&ggxh!="") {
        	$('#materielId').val(wlId);
        	$('#specification_Type').val(ggxh);
        }
    }


	
</script>
</body>
</html>