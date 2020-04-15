<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>物料列表</title>
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
		    url:'<c:url value="/materielId/materielIdList.do"/>',
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
				{field:'materiel_Id',title:'物料Id',width:'20%'},
				{field:'specification_Type',title:'规格型号',width:'20%'},
				{field:'bzq',title:'保质期',width:'15%'},
				{field:'ckdj',title:'参考单价',width:'15%'},
				{field:'typeName',title:'类型',width:'15%'},
	            {field:'op',title:'操作',width:'14%',
					formatter:function (val,row,index){
	            		return '<a href="#" onclick="editMaterielId('+index+')">修改</a> <a href="#" onclick="deletePost('+index+')">删除</a> <a href="#" onclick="showMaterielId('+index+')">查看</a>';
	            	}
				}
		    ]]
		});
	}
	
	/* 新增物料 */	
	function saveMaterielId(){
		var url=$('#url').val();
		window.open(url+"materielId/initSaveMaterielId.do",'newwindow','height=500, width=1100, top=40, left=100, toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no');
	}
	
	/*编辑物料  */
	function editMaterielId(index){
		var url=$('#url').val();
		$('#dg').datagrid('selectRow',index);// 关键在这里
		var row = $('#dg').datagrid('getSelected');
		 window.open(url+"materielId/initEditMaterielId.do?row_Id="+row.row_Id,'newwindow','height=500, width=1100, top=40, left=100, toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no');
	}

	/*查看物料  */
	function showMaterielId(index){
		var url=$('#url').val();
		$('#dg').datagrid('selectRow',index);// 关键在这里
		var row = $('#dg').datagrid('getSelected');
		 window.open(url+"materielId/showMaterielId.do?row_Id="+row.row_Id,'newwindow','height=500, width=1100, top=40, left=100, toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no');
	}
	
	
	/* 删除 */
	function deletePost(index) {
		$('#dg').datagrid('selectRow',index);// 关键在这里
		var row = $('#dg').datagrid('getSelected');
		$.messager.confirm("提示", "您确定要删除该数据么？", function (data) {
			if(data) {
				$.ajax({
					type : "post",
					url : "<c:url value='/materielId/deleteMaterielId.do'/>",
					data:{"row_Id":row.row_Id},
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
							 ids=ids+","+rows[i].row_Id;
						 }else{
							 ids=rows[i].row_Id;
						 }
					 }
					 //批量删除
					 $.ajax({
							type : "post",
							url : "<c:url value='/materielId/batchDeletePost.do'/>",
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
		var lx;
		var radios=$('input[name="type"]');
		for(var i=0;i<radios.length;i++){
				if(radios[i].checked){
					lx=radios[i].value;
				}
			}
		$('#dg').datagrid('load',{
			wlId: $('#wlId').val(),//物料Id
			ggxh: $('#ggxh').val(),//规格型号
			bzq: $('#bzq').val(),//保质期
			type:lx,//类型
			ckj1:$('#ckj1').val(),//参考价1
			ckj2:$('#ckj2').val(),//参考价2
		});
	}

	//打开导入页面
	function fileImport(){
		var url=$('#url').val();
		 window.open(url+'materielId/initFileImport.do','newwindow','height=300, width=600, top=230, left=500, toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no');
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
	            <a href='#' class="easyui-linkbutton" iconCls="icon-add" plain="true" title="新增" onclick="saveMaterielId()"></a>
	            <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" title="一键删除" onclick="selectDate()"></a>
	            <a href='#' class="easyui-linkbutton" iconCls="icon-undo" plain="true" title="导入" onclick="fileImport()"></a>
	            <a href='${pageContext.request.contextPath}/materielId/exportExcel.do' class="easyui-linkbutton" iconCls="icon-redo" plain="true" title="导出"></a>
	        </div>
	        <div>
	        	物料Id: <input class="easyui-textbox" style="width:200px" id="wlId">
	        	规格型号:<input class="easyui-textbox" style="width:200px" id="ggxh">
	        	保质期: <input class="easyui-textbox" style="width:200px" id="bzq">
	                        类型:<input type="radio" value="0" name="type">成品
	                 <input type="radio" value="1" name="type">材料 
	           <br>
	          	参考价: <input class="easyui-textbox" style="width:200px" id="ckj1">- <input class="easyui-textbox" style="width:200px" id="ckj2">
	            <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="doSearch()">查询</a>
	        </div>
	    </div>
</div>
</body>
</html>