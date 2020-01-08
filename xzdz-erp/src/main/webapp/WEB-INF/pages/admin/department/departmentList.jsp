<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>部门列表</title>
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
		    url:'<c:url value="/department/departmentList.do"/>',
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
				{field:'dep_Name',title:'部门名称',width:'25%'},
				{field:'parentDepName',title:'上级部门',width:'25%'},
				{field:'remarks',title:'部门描述',width:'25%'},
	            {field:'op',title:'操作',width:'24%',
					formatter:function (val,row,index){
	            		return '<a href="#" onclick="editDepartment('+index+')">修改</a> <a href="#" onclick="deleteDepartment('+index+')">删除</a>';
	            	}
				}
		    ]]
		});
		//初始化部门树
		  $.ajax({  
			    type: "post",  
			    url:  "<c:url value='/department/initDepTree.do'/>",
			    dataType: 'json',
			    async:false,
			    error:function(){
			    	alert("出错");
			    },
			    success: function (data) {  
			    	$("#tt").tree({  
			           	data:data,
			           	dnd: false, //可拖放
			    		lines: true, //显示树线条
			    		checkbox: false, //显示复选框
			    		animate: true,//展开动画效果
			    		cascadeCheck:false,
			    		onClick:function(node){
			    			/*  
			    				1.判断当前节点是否存在子节点集合
			    				1.1若子节点集合为空则表示该节点不存在子节点，则在列表中只显示该节点数据
			    				1.2若子节点不为空则表示该节点存在子节点，则在列表中显示该节点和该节点的所有子节点集合
			    			*/
			    			//得到节点Id
			    			var nodeId=node.id;
			    			var length=node.children.length;
			    			//(kg) 控制是加载一个节点的数据还是加载该节点及该节点的子节点数据
			    			var kg;
			    			if(length==0){
			    				$('#dg').datagrid('load',{
			    					id: nodeId,
			    					flag:true,
			    				});
			    			}else{
			    				$('#dg').datagrid('load',{
			    					id: nodeId,
			    					flag:false,
			    				});
			    			}
			    			//得到当前节点的父节点
			    			//var parent=$('#tt').tree('getParent', node.target);
			    		}
			        });  
			    }  
			});
	}
	
	
		
	/*新增部门  */
	function newDepartment(){
		var url=$('#url').val();
		 window.open(url+"department/initSaveDepartment.do",'newwindow','height=500, width=400, top=230, left=500, toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no');
	}
	
	/*编辑部门 */
	function editDepartment(index){
		var url=$('#url').val();
		$('#dg').datagrid('selectRow',index);// 关键在这里
		var row = $('#dg').datagrid('getSelected');
		 window.open(url+"department/initEditDepartment.do?dep_Id="+row.dep_Id,'newwindow','height=500, width=400, top=230, left=500, toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no');
	}
	
	/* 删除部门 */
	function deleteDepartment(index) {
		$('#dg').datagrid('selectRow',index);// 关键在这里
		var row = $('#dg').datagrid('getSelected');
		$.messager.confirm("提示", "您确定要删除该数据么？", function (data) {
			if(data) {
				$.ajax({
					type : "post",
					url : "<c:url value='/department/deleteDepartment.do'/>",
					data:{"dep_Id":row.dep_Id},
					async : false,
					dataType : 'json',
					error : function() {
						alert("出错");
					},
					success : function(data) {
						if(data.flag){
							window.location.reload();
						}else{
							$.messager.alert('提示',data.infor);
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
							 ids=ids+","+rows[i].dep_Id;
						 }else{
							 ids=rows[i].dep_Id;
						 }
					 }
					 //批量删除
					 $.ajax({
							type : "post",
							url : "<c:url value='/department/batchDeleteDepartment.do'/>",
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
			bmmc: $('#bmmc').val(),
			sjbmdm: $('#sjbmdm').val(),
			bmms: $('#bmms').val(),
		});
	}
</script>
</head>
<body onload="depList()">
	<div  style="width:100%;overflow:hidden">
		<div style="width:15%;float:left;">
			<div style="height:-webkit-fill-available;padding-top:10%;padding-bottom:10%;background:#eee" class="layui-form-item">
				<ul id="tt"></ul>
			</div>
		</div>
		<div style="width:82%;float:right;position:relative;top: -10px;">
			<table id="dg"></table>
		</div>
	</div>
	 <div id="tb" style="padding:5px;height:auto">
	        <div style="margin-bottom:5px">
	        	<input type="hidden" value='<c:url value="/"/>' id="url">
	        	<input type="hidden" id="userIds">
	            <a href='#' class="easyui-linkbutton" iconCls="icon-add" plain="true" title="新增" onclick="newDepartment()"></a>
	            <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" title="一键删除" onclick="selectDate()"></a>
	        </div>
	        <div>
	        	部门名称: <input class="easyui-textbox" style="width:100px" id="bmmc">
	                        上级部门: 
	            <input class="easyui-combobox" style="width:200px"
	                    url='<c:url value="/user/depList.do"/>'
	                    valueField="dep_Id" textField="dep_Name" id="sjbmdm">
	         	部门描述: <input class="easyui-textbox" style="width:100px" id="bmms">
	            <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="doSearch()">查询</a>
	        </div>
	    </div>
</div>
</body>
</html>