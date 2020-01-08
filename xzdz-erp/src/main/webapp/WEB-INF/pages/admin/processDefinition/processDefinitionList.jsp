<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>流程定义列表</title>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@page isELIgnored="false"%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/jquery-easyui-1.7.0/themes/default/easyui.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/jquery-easyui-1.7.0/themes/icon.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/jquery-easyui-1.7.0/themes/default/datagrid.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery-easyui-1.7.0/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery-easyui-1.7.0/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery-easyui-1.7.0/plugins/jquery.pagination.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery-easyui-1.7.0/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">
/*初始化数据表格  */
function procdefList(){
	$('#dg').datagrid({
	    url:'<c:url value="/procdef/procdefList.do"/>',
	    singleSelect: true,
	    collapsible:true,
	    pagination: true,
	    singleSelect:false,//多选
	    pageSize: 10,
	    pageNumber: 1,
	    pageList: [10, 20, 30,40,50],
	    toolbar:"#tb",
	    columns:[[
	    	{title: '全选', field: 'ck',checkbox : true, sortable: false},  //添加checkbox   
			{field:'NAME_',title:'流程名称',width:'55%'},
			{field:'VERSION_',title:'最新版本',width:'22%'},
            {field:'op',title:'操作',width:'22%',
				formatter:function (val,row,index){
            		return '<a href="#" onclick="deleteProcdef('+index+')">删除</a> <a href="${pageContext.request.contextPath}/procdef/downloadImage.do?id='+row.ID_+'">下载流程图</a> <a href="#" onclick="showImg('+index+')">查看流程图</a>';
            	}
			}
	    ]]
	});
}

	//打开新增页面
	function newProcessd(){
		var url=$('#url').val();
		 window.open(url+'procdef/initSaveProcdef.do','newwindow','height=300, width=600, top=230, left=500, toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no');
	}
	
	//删除流程部署
	function deleteProcdef(index){
		$('#dg').datagrid('selectRow',index);// 关键在这里
		var row = $('#dg').datagrid('getSelected');
		var pdId=row.DEPLOYMENT_ID_;
		$.messager.confirm("提示", "您确定要删除该数据么？", function (data) {
			if(data){
				$.ajax({
					type : "post",
					url : "<c:url value='/procdef/deleteByKey.do'/>",
					data:{"key":pdId},
					async : false,
					dataType : 'json',
					error : function() {
						alert("出错");
					},
					success : function(data) {
						if(data.flag){
							window.location.reload();
						}
					}
				});
			}
		});
	}
	
	//查看流程图
	function showImg(index){
		var url=$('#url').val();
		$('#dg').datagrid('selectRow',index);// 关键在这里
		var row = $('#dg').datagrid('getSelected');
		var pdId=row.ID_;
	    window.open(url+'procdef/showPng.do?pdId='+pdId,'newwindow','height="100%", width="100%", top=230, left=500, toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no');
	}

	function selectDate(){
		 var rows = $('#dg').datagrid('getSelections');//获取所有复选框选中的数据
		 var ids=$('#userIds').val();
		 //如果数据为空，则没有勾选给出弹窗提示，否则进行批量删除操作
		 var length=rows.length;
		 if(length==0){
			return $.messager.alert('提示','请勾选需要删除的数据');
		 }else{
			 $.messager.confirm("提示", "您确定要删除该数据么？", function (data) {
				if(data) {
					 //获取所有选中数据的主键 进行字符串拼接
					 for(var i=0;i<length;i++){
						 if(null!=ids&&""!=ids){
							 ids=ids+","+rows[i].DEPLOYMENT_ID_;
						 }else{
							 ids=rows[i].DEPLOYMENT_ID_;
						 }
					 }
					 //批量删除
					 $.ajax({
							type : "post",
							url : "<c:url value='/procdef/batchDeleteProcedf.do'/>",
							data:{"ids":ids},
							async : false,
							dataType : 'json',
							error : function() {
								alert("出错");
							},
							success : function(data) {
								if(data.flag){
									window.location.reload();
								}
							}
						});
				}
			 });
		 }
	}
</script>
</head>
<body onload="procdefList()">
	<table id="dg"></table>
	 <div id="tb" style="padding:5px;height:auto">
        <div style="margin-bottom:5px">
        	<input type="hidden" value='<c:url value="/"/>' id="url">
        	<input type="hidden" id="userIds">
            <a href='#' class="easyui-linkbutton" iconCls="icon-add" plain="true" title="新增" onclick="newProcessd()"></a>
            <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" title="一键删除" onclick="selectDate()"></a>
        </div>
	</div>
</body>
</html>
