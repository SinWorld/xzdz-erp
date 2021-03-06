<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>客户列表</title>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@page isELIgnored="false" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/jquery-easyui-1.7.0/themes/default/easyui.css"">
<link rel="stylesheet" href="${pageContext.request.contextPath}/jquery-easyui-1.7.0/themes/icon.css"">
<link rel="stylesheet" href="${pageContext.request.contextPath}/jquery-easyui-1.7.0/themes/default/datagrid.css"">
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery-easyui-1.7.0/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery-easyui-1.7.0/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery-easyui-1.7.0/plugins/jquery.pagination.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery-easyui-1.7.0/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">

	/*初始化数据表格  */
	function depList(){
		$('#dg').datagrid({
		    url:'<c:url value="/customer/customerList.do"/>',
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
				{field:'unit_Name',title:'单位名称',width:'14%'},
				{field:'registered_Address',title:'注册地址',width:'15%'},
				{field:'office_Address',title:'办公地址',width:'15%'},
				{field:'unified_Code',title:'社会统一信用代码',width:'15%'},
				{field:'legal_person',title:'法定代表人',width:'10%'},
				{field:'opening_Bank',title:'开户行',width:'10%'},
				{field:'telPhone',title:'电话',width:'10%'},
	            {field:'op',title:'操作',width:'10%',
					formatter:function (val,row,index){
	            		return '<a href="#" onclick="editCustomer('+index+')">修改</a> <a href="#" onclick="deleteCustomer('+index+')">删除</a> <a href="#" onclick="ShowCustomer('+index+')">查看</a>';
	            	}
				}
		    ]]
		});
	}
	
	/* 新增客户 */	
	function saveCustomer(){
		var url=$('#url').val();
		window.open(url+"customer/initSaveCustomer.do",'newwindow','height=700, width=1370, top=40, left=100, toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no');
	}
	
	/*编辑客户  */
	function editCustomer(index){
		var url=$('#url').val();
		$('#dg').datagrid('selectRow',index);// 关键在这里
		var row = $('#dg').datagrid('getSelected');
		 window.open(url+"customer/initEditCustomer.do?customer_Id="+row.customer_Id,'newwindow','height=700, width=1370, top=40, left=100, toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no');
	}

	/*查看客户  */
	function ShowCustomer(index){
		var url=$('#url').val();
		$('#dg').datagrid('selectRow',index);// 关键在这里
		var row = $('#dg').datagrid('getSelected');
		 window.open(url+"customer/ShowCustomer.do?customer_Id="+row.customer_Id,'newwindow','height=700, width=1370, top=40, left=100, toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no');
	}
	
	/* 删除用客户*/
	function deleteCustomer(index) {
		$('#dg').datagrid('selectRow',index);// 关键在这里
		var row = $('#dg').datagrid('getSelected');
		$.messager.confirm("提示", "您确定要删除该数据么？", function (data) {
			if(data) {
				$.ajax({
					type : "post",
					url : "<c:url value='/customer/deleteCustomerById.do'/>",
					data:{"customer_Id":row.customer_Id},
					async : false,
					dataType : 'json',
					error : function() {
						alert("出错");
					},
					success : function(data) {
						if(data.flag){
							window.location.reload();
						}else{
						    $.messager.alert('提示','当前客户下关联客户联系人,请优先删除客户联系人才能删除客户!!!');
						}
					}
				});
			}
		});
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
							 ids=ids+","+rows[i].customer_Id;
						 }else{
							 ids=rows[i].customer_Id;
						 }
					 }
					 //批量删除
					 $.ajax({
							type : "post",
							url : "<c:url value='/customer/batchDeleteCustomer.do'/>",
							data:{"ids":ids},
							async : false,
							dataType : 'json',
							error : function() {
								alert("出错");
							},
							success : function(data) {
								if(data.flag){
									window.location.reload();
								}else{
									 var index=data.index;
									 $.messager.alert('提示','第'+index+'行客户下关联客户联系人,请优先删除客户联系人才能删除客户!!!');
								}
							}
						});
				}
			 });
		 }
	}
	
	/* 查询 */
	function doSearch(){
		$('#dg').datagrid('load',{
			dwmc: $('#dwmc').val(),//单位名称
			zcdz: $('#zcdz').val(),//注册地址
			bgdz: $('#bgdz').val(),//办公地址
			xydm: $('#xydm').val(),//信用代码
			fddbr: $('#fddbr').val(),//法定代表人
			khh: $('#khh').val(),//开户行
			dh: $('#dh').val(),//电话
		});
	}

	//打开导入页面
	function fileImport(){
		var url=$('#url').val();
		 window.open(url+'customer/initFileImport.do','newwindow','height=300, width=600, top=230, left=500, toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no');
	}

	
</script>
</head>
<body onload="depList()">
	<div  style="width:100%;overflow:hidden">
		<div style="width:100%;">
			<table id="dg"></table>
		</div>
	</div>
	 <div id="tb" style="padding:5px;height:auto">
	        <div style="margin-bottom:5px">
	        	<input type="hidden" value='<c:url value="/"/>' id="url">
	        	<input type="hidden" id="userIds">
	            <a href='#' class="easyui-linkbutton" iconCls="icon-add" plain="true" title="新增" onclick="saveCustomer()"></a>
	            <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" title="一键删除" onclick="selectDate()"></a>
	            <a href='#' class="easyui-linkbutton" iconCls="icon-undo" plain="true" title="导入" onclick="fileImport()"></a>
	            <a href='${pageContext.request.contextPath}/customer/exportExcel.do' class="easyui-linkbutton" iconCls="icon-redo" plain="true" title="导出"></a>
	        </div>
	        <div>
	        	单位名称: <input class="easyui-textbox" style="width:200px" id="dwmc">
	        	注册地址: <input class="easyui-textbox" style="width:200px" id="zcdz">
	        	办公地址: <input class="easyui-textbox" style="width:200px" id="bgdz">
	        	社会统一信用代码: <input class="easyui-textbox" style="width:200px" id="xydm">
	        	<br>
	        	法定代表人: <input class="easyui-textbox" style="width:200px" id="fddbr">
				开户行: <input class="easyui-textbox" style="width:200px" id="khh">
	        	电话: <input class="easyui-textbox" style="width:200px" id="dh">
	            <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="doSearch()">查询</a>
	        </div>
	    </div>
</div>
</body>
</html>