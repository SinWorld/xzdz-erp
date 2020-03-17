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
	 <div class="demoTable" style="background-color: #CAE1FF;display: none;" id="gjssq">
		<div class="layui-form-item" style="width:1280px;height:auto;padding:0px; margin:0 auto;" id="main"">
		 <div class="layui-form-item">
		    <div class="layui-inline" style="width:325px;">
			  	<label class="layui-form-label">成品</label>
				<div class="layui-input-inline" style="text-align: left">
					<select name="cp" id="cp" lay-filter="cp" lay-verify="cp" lay-search="">
						<option value="" selected="selected">请选择成品</option>
					</select>
				</div>
			</div>
			 <div class="layui-inline" style="width:400px;">
			  	<label class="layui-form-label">库位</label>
				<div class="layui-input-inline" style="text-align: left;width: 265px;">
					<select name="kw" id="kw" lay-filter="kw" lay-verify="kw" lay-search="">
						<option value="" selected="selected">请选择库位</option>
					</select>
				</div>
			</div>
			<button class="layui-btn" data-type="reload" type="button" id="do_search" style="margin-left:30px;">搜索</button>
	 	</div>
		
		<div class="layui-form-item">
			<div class="layui-inline">
			      <label class="layui-form-label" style="width:80px;">物料Id</label>
			     <div class="layui-input-inline" style="text-align: left">
					<select name="wlId" id="wlId" lay-filter="wlId" lay-verify="wlId" lay-search="">
						<option value="" selected="selected">请选择物料Id</option>
					</select>
				</div>
		     </div>
			
		    <div class="layui-inline">
		      <label class="layui-form-label" style="width: 95px;">库存量</label>
		      <div class="layui-input-inline" style="width: 120px;">
	        	<input type="text" name="kcl1" id="kcl1"  placeholder="" autocomplete="off" class="layui-input">
		      </div>
		      <div class="layui-form-mid">-</div>
		      <div class="layui-input-inline" style="width: 120px;">
	            <input type="text" name="kcl2" id="kcl2" placeholder="" autocomplete="off" class="layui-input">
		      </div>
		    </div>
		    
			<button type="reset" class="layui-btn layui-btn-primary" style="margin-left:45px;">重置</button>
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
  reloadCp(form);
  reloadKw(form);
  reloadWlId(form);
  form.render();
  table.render({
	    elem: '#test'
	    ,url:url+'stock/kcList.do'
	    ,toolbar: '#toolbarDemo'
	    ,title: '成品库存列表'
	    ,totalRow: true
	    ,cols: [[
    	  {field:'index', width:"8%", title: '序号', sort: true,type:'numbers',totalRowText: '合计'}
    	  ,{field:'productName', width:"30%", align:'center', title: '成品名称'}
    	  ,{field:'product_Id', width:"27%", align:'center', title: '成品名称',hide:true}
    	  ,{field:'stockName', width:"15%", align:'center', title: '库位'}
    	  ,{field:'materielId', width:"17%", align:'center', title: '物料Id'}
    	  ,{field:'sl', width:"15%", align:'center', title: '库存量', totalRow: true}
    	  ,{field:'zkcl', width:"15%", align:'center', title: '该成品总库存量'}
	    ]]
	    ,id:'testReload'
	    ,page: true
	    ,done : function(res, curr, count) {
			merge(res, curr, count);
			var that = this.elem.next();
		    res.data.forEach(function (item, index) {
		    	if(res.data[index].zkcl<=100){
		    		 var tr = that.find(".layui-table-box tbody tr[data-index='" + index + "']").css({"background-color":"#FFFF00","font-weight":"bold"});
		    	}
	        });
		}
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
  

  
  // 执行搜索，表格重载
  $('#do_search').on('click', function () {
      // 搜索条件
      var cp = $('#cp').val();
      var kw=$('#kw').val();
      var wlId=$('#wlId').val();
      var kcl1=$('#kcl1').val();
      var kcl2=$('#kcl2').val();
      table.reload('testReload', {
          method: 'post'
          , where: {
              'cp': cp,
              'kw':kw,
              'wlId':wlId,
              'kcl1':kcl1,
              'kcl2':kcl2,
          }
          , page: {
              curr: 1
          }
      });
  });
});

	//ajax加载所有的成品
	function reloadCp(form){
		$.ajax({
			type : "post",
			url : "<c:url value='/stockRecod/allCpList.do'/>",
			async : false,
			dataType : 'json',
			error : function() {
				alert("出错");
			},
			success : function(msg) {
				for (var i = 0; i < msg.length; i++) {
					$("#cp").append(
							"<option value='"+msg[i].product_Id+"'>"+ msg[i].product_Name +"</option>");
				}
				form.render('select');
			}
		});
	}

	//ajax加载所有的库位
	function reloadKw(form){
		$.ajax({
			type : "post",
			url : "<c:url value='/stockRecod/allKwList.do'/>",
			async : false,
			dataType : 'json',
			error : function() {
				alert("出错");
			},
			success : function(msg) {
				for (var i = 0; i < msg.length; i++) {
					$("#kw").append(
							"<option value='"+msg[i].stock_Id+"'>"+ msg[i].stock +"</option>");
				}
				form.render('select');
			}
		});
	}

	//ajax加载所有的物料Id
	function reloadWlId(form){
		$.ajax({
			type : "post",
			url : "<c:url value='/materielId/queryProWlId.do'/>",
			async : false,
			dataType : 'json',
			error : function() {
				alert("出错");
			},
			success : function(msg) {
				for (var i = 0; i < msg.length; i++) {
					$("#wlId").append(
							"<option value='"+msg[i].materiel_Id+"'>"+ msg[i].materiel_Id +"</option>");
				}
				form.render('select');
			}
		});
	}

	//合并单元格
	function merge(res, curr, count) {
		var data = res.data;
		var mergeIndex = 0;//定位需要添加合并属性的行数
		var mark = 1; //这里涉及到简单的运算，mark是计算每次需要合并的格子数
		var columsName = ['materielId'];//需要合并的列名称
		var columsIndex = [6];//需要合并的列索引值
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
						$(this).css({"text-align":"center"}); 
						
					});
					tdCurArr.each(function() {//当前行隐藏
						$(this).css("display", "none");
					});
				} else {
					tdPreArr.each(function() {//相同列的第一列增加rowspan属性
						$(this).css({"text-align":"center"}); 
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