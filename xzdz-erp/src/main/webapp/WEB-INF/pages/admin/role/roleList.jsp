<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>角色列表</title>
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
		    url:'<c:url value="/role/roleList.do"/>',
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
				{field:'role_Name',title:'角色名称',width:'25%'},
				{field:'role_Infor',title:'角色说明',width:'60%'},
	            {field:'op',title:'操作',width:'14%',
					formatter:function (val,row,index){
	            		return '<a href="#" onclick="privilegeList('+index+')">授权</a> <a href="#" onclick="editUser('+index+')">修改</a> <a href="#" onclick="deleteUser('+index+')">删除</a>';
	            	}
				}
		    ]]
		});
	}

	/*授权 */
	function privilegeList(index){
		var url=$('#url').val();
		$('#dg').datagrid('selectRow',index);// 关键在这里
		var row = $('#dg').datagrid('getSelected');
		 window.open(url+"privilege/initPrivilegeList.do?roleId="+row.role_Id,'newwindow','height=500, width=600, top=230, left=500, toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no');
	}
	
	/*新增角色  */
	function newRole(){
		var url=$('#url').val();
		 window.open(url+"role/initSaveRole.do",'newwindow','height=400, width=600, top=230, left=500, toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no');
	}
	
	/*编辑角色  */
	function editUser(index){
		var url=$('#url').val();
		$('#dg').datagrid('selectRow',index);// 关键在这里
		var row = $('#dg').datagrid('getSelected');
		 window.open(url+"role/initEditRole.do?role_Id="+row.role_Id,'newwindow','height=400, width=600, top=230, left=500, toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no');
	}
	
	/* 删除角色 */
	function deleteUser(index) {
		$('#dg').datagrid('selectRow',index);// 关键在这里
		var row = $('#dg').datagrid('getSelected');
		$.messager.confirm("提示", "您确定要删除该数据么？", function (data) {
			if(data) {
				$.ajax({
					type : "post",
					url : "<c:url value='/role/deletRole.do'/>",
					data:{"role_Id":row.role_Id},
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
							 ids=ids+","+rows[i].role_Id;
						 }else{
							 ids=rows[i].role_Id;
						 }
					 }
					 //批量删除
					 $.ajax({
							type : "post",
							url : "<c:url value='/role/batchDeleteRole.do'/>",
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
	
	/* 查询 */
	function doSearch(){
		$('#dg').datagrid('load',{
			roleName: $('#roleName').val(),
			roleInfor: $('#roleInfor').val(),
		});
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
	            <a href='#' class="easyui-linkbutton" iconCls="icon-add" plain="true" title="新增" onclick="newRole()"></a>
	            <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" title="一键删除" onclick="selectDate()"></a>
	        </div>
	        <div>
	        	 角色名称: <input class="easyui-textbox" style="width:200px" id="roleName">
	        	 角色说明: <input class="easyui-textbox" style="width:200px" id="roleInfor">
	            <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="doSearch()">查询</a>
	        </div>
	    </div>
</div>
</body>
</html>