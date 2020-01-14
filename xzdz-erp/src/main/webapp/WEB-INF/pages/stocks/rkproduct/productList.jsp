<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>成品库存列表</title>
<link rel="stylesheet" href="../layui-v2.5.5/layui/css/layui.css">
<link rel="stylesheet" href="../login/css/static.css">
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@page isELIgnored="false" %>
</head>
<body>
	<form class="layui-form" action="" style="margin-top: 10px;">
	 <div class="demoTable" style="background-color: #CAE1FF" id="gjssq">
		<div class="layui-form-item" style="width:1280px;height:auto;padding:0px; margin:0 auto;" id="main"">
		 <div class="layui-form-item">
			 <div class="layui-inline">
			      <label class="layui-form-label" style="width: 100px;">项目编号</label>
			      <div class="layui-input-inline">
			        <input type="text" name="proj_Code" lay-verify="proj_Code"
					autocomplete="off" class="layui-input" style="width: 200px;" id="proj_Code">
			      </div>
		     </div>
		    <div class="layui-inline" style="width:490px;">
				  	<label class="layui-form-label">项目名称</label>
					<div class="layui-input-inline" style="text-align: left;width: 75%">
						<select name="proj_Id" id="proj_Id" lay-filter="proj_Id" lay-verify="proj_Id" lay-search="">
							<option value="" selected="selected">请选择项目名称</option>
						</select>
					</div>
			</div>
			<div class="layui-inline" style="width: 24.5%;left: -6px;">
			  	<label class="layui-form-label">我方负责人</label>
				<div class="layui-input-inline" style="text-align: left;">
					<select name="user_Id" id="user_Id" lay-filter="required" lay-verify="required">
						<option value="" selected="selected">请选择我方负责人</option>
					</select>
				</div>
		 	</div>
		 	<button class="layui-btn" data-type="reload" type="button" id="do_search" >搜索</button>
	 	</div>
		
		<div class="layui-form-item">
			<div class="layui-inline">
			      <label class="layui-form-label" style="width: 100px;">当前操作</label>
			      <div class="layui-input-inline">
			        <input type="text" name="nextCZ" lay-verify="nextCZ"
					autocomplete="off" class="layui-input" style="width: 200px;" id="nextCZ">
			      </div>
		     </div>
			 <div class="layui-inline" style="width: 25.7%;">
			  	<label class="layui-form-label">审批状态</label>
				<div class="layui-input-inline" style="text-align: left;">
					<select name="appr_Status" id="appr_Status" lay-filter="appr_Status" lay-verify="appr_Status">
						<option value="" selected="selected">请选择审批状态</option>
					</select>
				</div>
		 	</div>
		    <div class="layui-inline" style="left: -44px;width: 512px;">
		      <label class="layui-form-label" style="width: 71px;">提交时间</label>
		      <div class="layui-input-inline">
		        <input type="text" name="time1" id="date" lay-verify="date" placeholder="yyyy-MM-dd" autocomplete="off" class="layui-input">
		      </div>
		       <i class="u-date_line" style="margin-left: -14px;line-height: 35px;">—</i>
		      <div class="layui-input-inline">
		        <input type="text" name="time2" id="date2" lay-verify="date" placeholder="yyyy-MM-dd" autocomplete="off" class="layui-input">
		      </div>
			</div>
			<button type="reset" class="layui-btn layui-btn-primary" style="margin-left: -38px;">重置</button>
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
    <button class="layui-btn layui-btn-sm" lay-event="getCheckData" type="button">成品入库</button>
 	<button class="layui-btn layui-btn-sm" lay-event="gjss" type="button">高级搜索</button>
  </div>
</script>
<script src="../layui-v2.5.5/layui/layui.js" charset="utf-8"></script>
<!-- 注意：如果你直接复制所有代码到本地，上述js路径需要改成你本地的 --> 
<script type="text/javascript" src="../jquery/jquery-3.3.1.js"></script>
<!-- 注意：如果你直接复制所有代码到本地，上述js路径需要改成你本地的 --> 
<script type="text/html" id="barDemo">
  {{#  if(d.is_rk !=true){ }}
	<a class="layui-btn layui-btn-xs" lay-event="edit">剩余成品入库</a>
   {{#  } }}
</script>
<script>
layui.use(['table','form','layedit', 'laydate'], function(){
  var table = layui.table;
  var url=$('#url').val();
  var form= layui.form;
  var layer = layui.layer;
  var layedit = layui.layedit;
  var laydate = layui.laydate;
  $('#gjssq').hide();
  form.render();
  table.render({
    elem: '#test'
    ,url:url+'proStock/proStockList.do'
    ,toolbar: '#toolbarDemo'
    ,title: '成品库存'
    ,cols: [[
       {field:'index', width:"8%", title: '序号', sort: true,type:'numbers'}
      ,{field:'productName', width:"12%",align:'center', title: '成品名称'}
      ,{field:'stock', width:"20%", align:'center', title: '库位'}
      ,{field:'rknumber', width:"10%", align:'center', title: '入库数量'}
      ,{field:'product', width:"25%", align:'center', title: '成品',hide:true}
      ,{field:'remarks', width:"35%", align:'center', title: '备注'}
      ,{fixed: 'right', title:'操作', toolbar: '#barDemo', width:"15%",align:'center'}
    ]]
    ,id:'testReload'
    ,page: true
    ,done : function(res, curr, count) {
		merge(res, curr, count);
	}
  });
  
  //头工具栏事件
  table.on('toolbar(test)', function(obj){
    var url=$('#url').val();
    var flag=$('#flag').val();
    if(obj.event=='getCheckData'){
		 layer.open({
	      	  	type:2,
	      	  	title:'成品入库',
	      	  	area: ['100%','100%'],
	      	  	shadeClose: false,
	      		resize:false,
	      	    anim: 1,
	      	  	content:[url+"proStock/initRkProduct.do",'yes']
	    	 });
    }else if(obj.event=='gjss'){
    	if(flag=='false'){
    		$('#gjssq').fadeIn();
    		$('#flag').val(true);
    	}else{
    		$('#gjssq').fadeOut();
    		$('#flag').val(false);
    	}
    	
    }
  });
  
  //监听行工具事件
  table.on('tool(test)', function(obj){
    var data = obj.data;
    var url=$('#url').val();
    var stock_Id=data.stock_Id;
   	if(obj.event === 'edit'){
   		layer.open({
        	  	type:2,
        	  	title:'剩余成品入库',
        	  	area: ['100%','100%'],
        		shadeClose: false,
         		resize:false,
         	    anim: 1,
        	  	content:[url+"proStock/initSycprk.do?stock_Id="+stock_Id,'yes']
      	  	});
    }
  });
  
  // 执行搜索，表格重载
  $('#do_search').on('click', function () {
      // 搜索条件
      var proj_Code = $('#proj_Code').val();
      var proj_Id=$('#proj_Id').val();
      var user_Id=$('#user_Id').val();
      var nextCZ=$('#nextCZ').val();
      var appr_Status=$('#appr_Status').val();
      var date=$('#date').val();
      var date2=$('#date2').val();
      table.reload('testReload', {
          method: 'post'
          , where: {
              'proj_Code': proj_Code,
              'proj_Id':proj_Id,
              'user_Id':user_Id,
              'nextCZ':nextCZ,
              'appr_Status':appr_Status,
              'time1':date,
              'time2':date2,
          }
          , page: {
              curr: 1
          }
      });
  });
});

	//合并单元格
	function merge(res, curr, count) {
		var data = res.data;
		var mergeIndex = 0;//定位需要添加合并属性的行数
		var mark = 1; //这里涉及到简单的运算，mark是计算每次需要合并的格子数
		var columsName = ['product'];//需要合并的列名称
		var columsIndex = [1];//需要合并的列索引值
		for (var k = 0; k < columsName.length; k++){//这里循环所有要合并的列
			var trArr = $(".layui-table-body>.layui-table").find("tr");//所有行
			for (var i = 1; i < res.data.length; i++) { //这里循环表格当前的数据
				var tdCurArr = trArr.eq(i).find("td").eq(columsIndex[k]);//获取当前行的当前列
				var tdPreArr = trArr.eq(mergeIndex).find("td").eq(
						columsIndex[k]);//获取相同列的第一列
				if (data[i][columsName[k]] === data[i - 1][columsName[k]]) { //后一行的值与前一行的值做比较，相同就需要合并
					mark += 1;
					tdPreArr.each(function() {//相同列的第一列增加rowspan属性
						$(this).attr("rowspan", mark); 
						$(this).css({"background-color":"#DCDCDC","color":"#3192d3","text-align":"center"}); 
						
					});
					tdCurArr.each(function() {//当前行隐藏
						$(this).css("display", "none");
					});
				} else {
					tdPreArr.each(function() {//相同列的第一列增加rowspan属性
						$(this).css({"background-color":"#DCDCDC","color":"#3192d3","text-align":"center"}); 
					});
					mergeIndex = i;
					mark = 1;//一旦前后两行的值不一样了，那么需要合并的格子数mark就需要重新计算
				}
			}
		}
	} 



</script>
</body>
</html>