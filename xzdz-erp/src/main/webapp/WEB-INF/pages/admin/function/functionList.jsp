<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>功能列表</title>
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
		    url:'<c:url value="/function/funList.do"/>',
		    collapsible:true,
		    pagination: true,
		    pageSize: 10,
		    pageNumber: 1,
		    pageList: [10, 20, 30,40,50],
		    toolbar:"#tb",
		    columns:[[
				{field:'fp_Name',title:'功能名称',width:'21%'},
				{field:'fp_Url',title:'功能路径',width:'21%'},
				{field:'parentName',title:'上级功能',width:'21%'},
				{field:'fp_Icon',title:'字体图标',width:'21%'},
	            {field:'op',title:'操作',width:'17%',
					formatter:function (val,row,index){
	            		return '<a href="#" onclick="editFunction('+index+')">修改</a> <a href="#" onclick="deleteFunction('+index+')">删除</a>';
	            	}
				}
		    ]]
		});
	}
	
	/* 新增功能点 */	
	function saveFunction(){
		var url=$('#url').val();
		window.open(url+"function/initSaveFunction.do",'newwindow','height=700, width=800, top=40, left=400, toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no');
	}
	
	/*编辑功能点  */
	function editFunction(index){
		var url=$('#url').val();
		$('#dg').datagrid('selectRow',index);// 关键在这里
		var row = $('#dg').datagrid('getSelected');
		 window.open(url+"function/initEditFunction.do?fp_Id="+row.fp_Id,'newwindow','height=700, width=800, top=40, left=400, toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no');
	}

	/* 删除功能点*/
	function deleteFunction(index) {
		$('#dg').datagrid('selectRow',index);// 关键在这里
		var row = $('#dg').datagrid('getSelected');
		$.messager.confirm("提示", "您确定要删除该数据么？", function (data) {
			if(data) {
				$.ajax({
					type : "post",
					url : "<c:url value='/function/deleteFunction.do'/>",
					data:{"fp_Id":row.fp_Id},
					async : false,
					dataType : 'json',
					error : function() {
						alert("出错");
					},
					success : function(data) {
						if(data.flag){
							window.location.reload();
						}else{
						    $.messager.alert('提示','当前功能点存在子功能,请先删除子功能!!!');
						}
					}
				});
			}
		});
	}
	
	/* 查询 */
	function doSearch(){
		$('#dg').datagrid('load',{
			gnmc: $('#gnmc').val(),//功能名称
			gndz: $('#gndz').val(),//功能地址
			sjgn: $('#sjgn').val(),//上级功能
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
	            <a href='#' class="easyui-linkbutton" iconCls="icon-add" plain="true" title="新增" onclick="saveFunction()"></a>
	        </div>
	        <div>
	        	功能名称: <input class="easyui-textbox" style="width:200px" id="gnmc">
	        	功能地址: <input class="easyui-textbox" style="width:200px" id="gndz">
	        	上级功能: 
	            <input class="easyui-combobox" style="width:200px"
	                    url='<c:url value="/function/allFunction.do"/>'
	                    valueField="fp_Id" textField="fp_Name" id="sjgn">
	            <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="doSearch()">查询</a>
	        </div>
	    </div>
</div>
</body>
</html>