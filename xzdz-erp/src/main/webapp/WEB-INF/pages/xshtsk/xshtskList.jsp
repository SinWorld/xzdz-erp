<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>销售合同收款列表</title>
<link rel="stylesheet" href="../layui-v2.5.5/layui/css/layui.css">
<link rel="stylesheet" href="../login/css/static.css">
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@page isELIgnored="false" %>
</head>
<body>
	<form class="layui-form" action="" style="margin-top: 10px;">
	 <div class="demoTable" style="background-color: #CAE1FF;display: none;" id="gjssq">
		<div class="layui-form-item" style="width:1280px;height:auto;padding:0px; margin:0 auto;" id="main"">
		 <div class="layui-form-item">
		 
		    <div class="layui-inline" style="width:325px;">
			  	<label class="layui-form-label">销售订单</label>
				<div class="layui-input-inline" style="text-align: left">
					<select name="kw" id="kw" lay-filter="kw" lay-verify="kw" lay-search="">
						<option value="" selected="selected">请选择销售订单</option>
					</select>
				</div>
			</div>
			
			 <div class="layui-inline">
			      <label class="layui-form-label" style="width: 100px;">产品名称</label>
			      <div class="layui-input-inline">
			        <input type="text"  autocomplete="off" class="layui-input" style="width: 200px;" id="productName">
			      </div>
		     </div>
		    
			<div class="layui-inline">
			      <label class="layui-form-label" style="width: 100px;">规格型号</label>
			      <div class="layui-input-inline">
			        <input type="text" autocomplete="off" class="layui-input" style="width: 200px;" id="specificationType">
			      </div>
		     </div>
		     
	 	</div>
		
		<div class="layui-form-item">
		
		    <div class="layui-inline">
		      <label class="layui-form-label" style="width:79px;">出厂价</label>
		      <div class="layui-input-inline" style="width: 100px;">
		        <input type="text" id="ccj1" placeholder="￥" autocomplete="off" class="layui-input">
		      </div>
		      <div class="layui-form-mid">-</div>
		      <div class="layui-input-inline" style="width: 100px;">
		        <input type="text" id="ccj2" placeholder="￥" autocomplete="off" class="layui-input">
		      </div>
		    </div>
		    
		     <div class="layui-inline">
		      <label class="layui-form-label" style="width:80px;">渠道价</label>
		      <div class="layui-input-inline" style="width: 100px;">
		        <input type="text" id="qdj1" placeholder="￥" autocomplete="off" class="layui-input">
		      </div>
		      <div class="layui-form-mid">-</div>
		      <div class="layui-input-inline" style="width: 100px;">
		        <input type="text" id="qdj2" placeholder="￥" autocomplete="off" class="layui-input">
		      </div>
		    </div>
			
			<div class="layui-inline" style="left: -13px;">
		      <label class="layui-form-label">市场价</label>
		      <div class="layui-input-inline" style="width: 100px;">
		        <input type="text" id="scj1" placeholder="￥" autocomplete="off" class="layui-input">
		      </div>
		      <div class="layui-form-mid">-</div>
		      <div class="layui-input-inline" style="width: 100px;">
		        <input type="text" id="scj2" placeholder="￥" autocomplete="off" class="layui-input">
		      </div>
		    </div>
		    
		   </div>
		   
		    <div class="layui-form-item">
		    
		     <div class="layui-inline">
			      <label class="layui-form-label" style="width:78px;">单位</label>
			      <div class="layui-input-inline" style="width: 205px;">
			        <input type="text"  autocomplete="off" class="layui-input" style="width: 200px;" id="dw">
			      </div>
		     </div>
		    
		     <div class="layui-inline">
			      <label class="layui-form-label" style="width:100px;">数量</label>
			      <div class="layui-input-inline" style="width: 205px;">
			        <input type="text"  autocomplete="off" class="layui-input" style="width: 200px;" id="sl">
			      </div>
		     </div>
			<button class="layui-btn" data-type="reload" type="button" id="do_search" style="margin-left:118px;">搜索</button>
			<button type="reset" class="layui-btn layui-btn-primary" >重置</button>
	 	</div>
	
	</div> 
	</div>
</form>
	
	<input type="hidden" value='<c:url value="/"/>' id="url">
	<input type="hidden" id="flag" value="false">
	<div style="position:relative;top: -10px;">
		<table class="layui-hide" id="test" lay-filter="test"></table>
	</div>
<script type="text/html" id="toolbarDemo">
  <div class="layui-btn-container" style="width:25%;">
 	<button class="layui-btn layui-btn-sm" lay-event="gjss" type="button">高级搜索</button>
  </div>
</script>

<script type="text/html" id="barDemo">
  <a class="layui-btn layui-btn-xs" lay-event="edit">补款</a>
  <a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="detail">查看</a>
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
  form.render();
  table.render({
    elem: '#test'
    ,url:url+'xshtsk/xshtskList.do'
    ,toolbar: '#toolbarDemo'
    ,title: '销售合同收款列表'
    ,totalRow: true
    ,cols: [[
       {field:'index', width:"8%", title: '序号', sort: true,type:'numbers' ,totalRowText: '合计'}
      ,{field:'xshtName', width:"17%",align:'center', title: '销售合同'}
      ,{field:'sfkpkj', width:"9%", align:'center', title: '是否发票开具'}
      ,{field:'fplb', width:"10%", align:'center', title: '发票类别'}
      ,{field:'fpje', width:"8%", align:'right', title: '发票金额', totalRow: true}
      ,{field:'ysk', width:"8%", align:'right', title: '应收款', totalRow: true}
      ,{field:'sjsk', width:"8%", align:'right', title: '实际收款',  totalRow: true}
      ,{field:'kprq', width:"12%",align:'center', title: '开票日期',templet:'<div>{{ layui.util.toDateString(d.kprq, "yyyy-MM-dd") }}</div>'}
      ,{field:'approvalmc', width:"10.3%", align:'center', title: '审批状态'}
      ,{fixed: 'right', title:'操作', toolbar: '#barDemo', width:"10%",align:'center'}
      
    ]]
    ,id:'testReload'
    ,page: true
  });
  
  //头工具栏事件
  table.on('toolbar(test)', function(obj){
    var url=$('#url').val();
    var flag=$('#flag').val();
    if(obj.event=='gjss'){
    	if(flag=='false'){
    		//$('#gjssq').fadeIn();
    		$('#gjssq').css('display','block');
    		$('#flag').val(true);
    	}else{
    		//$('#gjssq').fadeOut();
    		$('#gjssq').css('display','none');
    		$('#flag').val(false);
    	}
    	
    }
  });
  

  //监听行工具事件
  table.on('tool(test)', function(obj){
    var data = obj.data;
    var url=$('#url').val();
    var xshtskdm=data.xshtskdm;
   	if(obj.event === 'edit'){
   		$.ajax({
			type : "post",
			url : "<c:url value='/xshtsk/checkbk.do'/>",
			async : false,
			dataType : 'json',
			error : function() {
				alert("出错");
			},
			data:{"xshtskdm":xshtskdm},
			success : function(msg) {
				if(msg.flag){
					return layer.alert(msg.infor,{icon:7});
				}else{
					layer.open({
		        	  	type:2,
		        	  	title:'补款',
		        	  	area: ['40%','45%'],
		        		shadeClose: false,
		         		resize:false,
		         	    anim: 1,
		        	  	content:[url+"xshtsk/initBk.do?xshtskdm="+xshtskdm,'yes']
		      	  	});
				}
			}
		});
    }else if(obj.event==='detail'){
    	layer.open({
      	  	type:2,
      	  	title:'查看销售合同收款',
      	  	area: ['100%','100%'],
      		shadeClose: false,
      		resize:false,
      	    anim: 1,
      	  	content:[url+"xshtsk/xshtskShow.do?xshtskdm="+xshtskdm,'yes']
    	  });
    }
  });
  
  // 执行搜索，表格重载
  $('#do_search').on('click', function () {
      // 搜索条件
      var productName = $('#productName').val();
      var specificationType=$('#specificationType').val();
      var dw=$('#dw').val();
      var sl=$('#sl').val();
      var ccj1=$('#ccj1').val();
      var ccj2=$('#ccj2').val();
      var qdj1=$('#qdj1').val();
      var qdj2=$('#qdj2').val();
      var scj1=$('#scj1').val();
      var scj2=$('#scj2').val();
      table.reload('testReload', {
          method: 'post', 
          where: {
              'productName': productName,
              'specificationType':specificationType,
              'dw':dw,
              'sl':sl,
              'ccj1':ccj1,
              'ccj2':ccj2,
              'qdj1':qdj1,
              'qdj2':qdj2,
              'scj1':scj1,
              'scj2':scj2 
          }, 
          page: {
              curr: 1
          }
      });
  });
});





</script>
</body>
</html>