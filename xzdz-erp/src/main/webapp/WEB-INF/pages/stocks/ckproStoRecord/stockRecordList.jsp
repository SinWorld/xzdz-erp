<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>成品入库列表</title>
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
		    <div class="layui-inline" style="width:325px;">
			  	<label class="layui-form-label">成品</label>
				<div class="layui-input-inline" style="text-align: left">
					<select name="cp" id="cp" lay-filter="cp" lay-verify="cp" lay-search="">
						<option value="" selected="selected">请选择成品</option>
					</select>
				</div>
			</div>
			 <div class="layui-inline" style="width:325px;">
			  	<label class="layui-form-label">库位</label>
				<div class="layui-input-inline" style="text-align: left">
					<select name="kw" id="kw" lay-filter="kw" lay-verify="kw" lay-search="">
						<option value="" selected="selected">请选择库位</option>
					</select>
				</div>
			</div>
			 <div class="layui-inline" style="width:325px;">
			  	<label class="layui-form-label">经办人</label>
				<div class="layui-input-inline" style="text-align: left">
					<select name="jbr" id="jbr" lay-filter="jbr" lay-verify="jbr" lay-search="">
						<option value="" selected="selected">请选择经办人</option>
					</select>
				</div>
			</div>
	 	</div>
		
		<div class="layui-form-item">
			<div class="layui-inline">
			      <label class="layui-form-label" style="width:80px;">出库数量</label>
			      <div class="layui-input-inline">
			        <input type="text" name="rksl" lay-verify="rksl"
					autocomplete="off" class="layui-input" style="width:191px;" id="rksl">
			      </div>
		     </div>
			
		    <div class="layui-inline">
		      <label class="layui-form-label" style="width: 95px;">出库时间</label>
		      <div class="layui-input-inline" style="width: 120px;">
	        	<input type="text" name="time1" id="date" lay-verify="date" placeholder="yyyy-MM-dd" autocomplete="off" class="layui-input">
		      </div>
		      <div class="layui-form-mid">-</div>
		      <div class="layui-input-inline" style="width: 120px;">
	            <input type="text" name="time2" id="date2" lay-verify="date" placeholder="yyyy-MM-dd" autocomplete="off" class="layui-input">
		      </div>
		    </div>
		    <button class="layui-btn" data-type="reload" type="button" id="do_search" style="margin-left:51px;">搜索</button>
		    
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
  $('#gjssq').hide();
  laydate.render({
	 elem: '#date'
  });
	  laydate.render({
	 elem: '#date2'
  });
  reloadCp(form);
  reloadKw(form);
  reloadJbr(form);
  form.render();
  table.render({
	    elem: '#test'
	    ,url:url+'ckRecord/stockRecodList.do'
	    ,toolbar: '#toolbarDemo'
	    ,title: '成品出库记录'
	    ,cols: [[
    	  {field:'index', width:"8%", title: '序号', sort: true,type:'numbers'}
    	  ,{field:'productName', width:"18%", align:'center', title: '成品名称'}
    	  ,{field:'product', width:"18%", align:'center', title: '成品名称',hide:true}
    	  ,{field:'stockName', width:"25%", align:'center', title: '库位'}
    	  ,{field:'sl', width:"13%", align:'center', title: '出库数量'}
    	  ,{field:'sj', width:"18%", align:'center', title: '出库时间',templet:'<div>{{ layui.util.toDateString(d.sj, "yyyy-MM-dd HH:mm:dd") }}</div>'}
    	  ,{field:'userName', width:"18%", align:'center', title: '经办人'}
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
    if(obj.event=='gjss'){
    	if(flag=='false'){
    		$('#gjssq').fadeIn();
    		$('#flag').val(true);
    	}else{
    		$('#gjssq').fadeOut();
    		$('#flag').val(false);
    	}
    	
    }
  });
  
//查看（行点击）
  table.on('row(test)', function(obj){
    var data = obj.data;
    var url=$('#url').val();
    var record_Id=data.record_Id;
    layer.open({
  	  	type:2,
  	  	title:'查看',
  	  	area: ['100%','100%'],
  		shadeClose: false,
  		resize:false,
  	    anim: 1,
  	  	content:[url+"ckRecord/ShowStockRecod.do?record_Id="+record_Id,'yes']
	  });
	  
  });
  
  // 执行搜索，表格重载
  $('#do_search').on('click', function () {
      // 搜索条件
      var cp = $('#cp').val();
      var kw=$('#kw').val();
      var jbr=$('#jbr').val();
      var rksl=$('#rksl').val();
      var date=$('#date').val();
      var date2=$('#date2').val();
      table.reload('testReload', {
          method: 'post'
          , where: {
              'cp': cp,
              'kw':kw,
              'jbr':jbr,
              'rksl':rksl,
              'time1':date,
              'time2':date2,
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
	
	//ajax加载所有的成品
	function reloadJbr(form){
		$.ajax({
			type : "post",
			url : "<c:url value='/stockRecod/allJbrList.do'/>",
			async : false,
			dataType : 'json',
			error : function() {
				alert("出错");
			},
			success : function(msg) {
				for (var i = 0; i < msg.length; i++) {
					$("#jbr").append(
							"<option value='"+msg[i].userId+"'>"+ msg[i].userName +"</option>");
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