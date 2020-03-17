<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>通知列表页</title>
<link rel="stylesheet" href="../layui-v2.5.5/layui/css/layui.css">
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@page isELIgnored="false"%>
</head>
<body>
<div class="layui-tab">
  <ul class="layui-tab-title">
    <li class="layui-this">未读 <span class="layui-badge">${wdCount}</span></li>
    <li>已读<span class="layui-badge layui-bg-orange">${ydCount}</span></li>
    <li>全部<span class="layui-badge layui-bg-green">${qbCount}</span> </li>
  </ul>
  <div class="layui-tab-content">
    <div class="layui-tab-item layui-show">
    	<input type="hidden" value='<c:url value="/"/>' id="url">
     	<table class="layui-hide" id="wd" lay-filter="wd"></table>
    </div>
   
    <div class="layui-tab-item">
    	<table class="layui-hide" id="yd" lay-filter="yd"></table>
    </div>
    
    <div class="layui-tab-item">
    	<table class="layui-hide" id="qb" lay-filter="qb"></table>
    </div>
   
  </div>
</div>
<script type="text/html" id="checkboxTpl">
  <input type="checkbox" name="lock" value="{{d.row_Id}}" title="已阅" lay-filter="lockDemo" {{ d.id == 10006 ? 'checked' : '' }}>
</script>
<script src="../jquery/jquery-3.3.1.js"></script>
<script src="../layui-v2.5.5/layui/layui.js"></script>
<script  type="text/javascript">
//注意：选项卡 依赖 element 模块，否则无法进行功能性操作
layui.use(['element','form','table'], function(){
  var element = layui.element;
  var table = layui.table;
  var url=$('#url').val();
  var form = layui.form;
  table.render({
    elem: '#wd'
    ,url:url+'notice/noticeWdList.do'
    ,title: '未读'
    ,cols: [[
       {field:'index', width:"8%", title: '序号', sort: true,type:'numbers'}
      ,{field:'content', width:"62%", title: '通知内容'}
      ,{field:'createTime', width:"15%", align:'center', title: '创建时间' ,templet:'<div>{{ layui.util.toDateString(d.createTime, "yyyy-MM-dd HH:mm:dd") }}</div>'}
      ,{field:'row_Id', title:'是否已阅', width:"15%", templet: '#checkboxTpl', unresize: true,align:'center',}
    ]]
    ,page: true
  });
  //…
    table.render({
    elem: '#yd'
    ,url:url+'notice/noticeYdList.do'
    ,title: '已读'
    ,cols: [[
       {field:'index', width:"8%", title: '序号', sort: true,type:'numbers'}
      ,{field:'content', width:"62%", title: '通知内容'}
      ,{field:'createTime', width:"15%", align:'center', title: '创建时间' ,templet:'<div>{{ layui.util.toDateString(d.createTime, "yyyy-MM-dd HH:mm:dd") }}</div>'}
      ,{field:'ydshij', width:"15%", align:'center', title: '已阅时间'}
    ]]
    ,page: true
  });

  //…
   table.render({
    elem: '#qb'
    ,url:url+'notice/noticeQbList.do'
    ,title: '全部'
    ,cols: [[
       {field:'index', width:"8%", title: '序号', sort: true,type:'numbers'}
      ,{field:'content', width:"62%", title: '通知内容'}
      ,{field:'createTime', width:"15%", align:'center', title: '创建时间' ,templet:'<div>{{ layui.util.toDateString(d.createTime, "yyyy-MM-dd HH:mm:dd") }}</div>'}
      ,{field:'ydshij', width:"15%", align:'center', title: '已阅时间'}
    ]]
    ,page: true
  });
   
  //我的代办 监听行工具事件
  table.on('row(db)', function(obj){
    var data = obj.data;
    var url=$('#url').val();
    var id=data.ID_;
    //console.log(obj)
	$.ajax({  
	    type: "post",  
	    url:  "<c:url value='/myTask/querObjId.do'/>",
	    dataType: 'json',
	    async:false,
	    data:{"task_id":id},
	    error:function(){
	    	alert("出错");
	    },
	    success: function (data) {
	    	var obj=data.obj;//获得数据对像类型
	    	if(obj=='ERP_Sales_Contract'){
	    		layer.open({
		       	  	type:2,
		       	  	title:'任务信息',
		       	  	area: ['100%','100%'],
		       		shadeClose: false,
		       		resize:false,
		       	    anim: 1,
		       	  	content:[url+"myTask/taskInfor.do?taskId="+data.taskId,'yes']
	     	  });
	    	}
	    }  
	});
	  
  });
  
  //已办 监听行工具事件
  table.on('row(yb)', function(obj){
	var data = obj.data;
    var url=$('#url').val();
    var id=data.ID_;
	layer.open({
   	  	type:2,
   	  	title:'任务信息',
   	  	area: ['100%','100%'],
   		shadeClose: false,
   		resize:false,
   	    anim: 1,
   	  	content:[url+"alreadyTask/alreadyTaskInfor.do?id="+id,'yes']
	  });
  });

//已完成 监听行工具事件
  table.on('row(ywc)', function(obj){
	var data = obj.data;
    var url=$('#url').val();
    var id=data.ID_;
	layer.open({
   	  	type:2,
   	  	title:'任务信息',
   	  	area: ['100%','100%'],
   		shadeClose: false,
   		resize:false,
   	    anim: 1,
   	  	content:[url+"completed/completedTaskInfor.do?id="+id,'yes']
	  });
  });

  //监听锁定操作
  form.on('checkbox(lockDemo)', function(obj){
    //layer.tips(this.value + ' ' + this.name + '：'+ obj.elem.checked, obj.othis);
	var objId=obj.value;
	$.ajax({
		type : "post",
		url : "<c:url value='/notice/yready.do'/>",
		async : false,
		dataType : 'json',
		data:{"id":objId},
		error : function() {
			alert("出错");
		},
		success : function(msg) {
			if(msg.flag){
				window.parent.location.reload();
			}
		}
	});
    
  });
});
</script>
</body>
</html>