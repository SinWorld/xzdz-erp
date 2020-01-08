<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户列表</title>
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
	function userList(){
		$('#dg').datagrid({
		    url:'<c:url value="/user/userList.do"/>',
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
				{field:'userName',title:'姓名',width:'10%'},
				{field:'gender',title:'性别',width:'9%'},
				{field:'dep_Name',title:'部门',width:'12%'},
				{field:'duties',title:'职务',width:'10%'},
				{field:'phoneNumber',title:'手机号',width:'11%'},
				{field:'email',title:'邮箱',width:'9%'},
				{field:'education',title:'学历',width:'10%'},
				{field:'birthday',title:'出生年月',width:'12%',
					formatter : function(birthday){
	                    var date = new Date(birthday);
	                    var y = date.getFullYear();
	                    var m = date.getMonth() + 1;
	                    var d = date.getDate();
	                    return y + '-' +m + '-' + d;}},
	            {field:'op',title:'操作',width:'16%',
					formatter:function (val,row,index){
	            		return '<a href="#" onclick="editUser('+index+')">修改</a> <a href="#" onclick="deleteUser('+index+')">删除</a> <a href="#" onclick="setRole('+index+')">角色设置</a> <a href="#" onclick="resertPassword('+index+')">重置密码</a>';
	            	}
				}
		    ]]
		});
	}
	/*新增用户  */
	function newUser(){
		var url=$('#url').val();
		 window.open(url+"user/initSaveUser.do",'newwindow','height=600, width=400, top=100, left=500, toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no');
	}
	
	/*编辑用户  */
	function editUser(index){
		var url=$('#url').val();
		$('#dg').datagrid('selectRow',index);// 关键在这里
		var row = $('#dg').datagrid('getSelected');
		 window.open(url+"user/initEditUser.do?userId="+row.userId,'newwindow','height=600, width=400, top=100, left=500, toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no');
	}
	
	/* 删除用户 */
	function deleteUser(index) {
		$('#dg').datagrid('selectRow',index);// 关键在这里
		var row = $('#dg').datagrid('getSelected');
		$.messager.confirm("提示", "您确定要删除该数据么？", function (data) {
			if(data) {
				$.ajax({
					type : "post",
					url : "<c:url value='/user/deleteUser.do'/>",
					data:{"userId":row.userId},
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
	
	/* 重置密码	 */
	function resertPassword(index){
		$('#dg').datagrid('selectRow',index);// 关键在这里
		var row = $('#dg').datagrid('getSelected');
		 $.messager.confirm("提示", "您确定要重置该用户的密码吗？", function (data) {
				if(data){
					  $.ajax({
				    		type : "post",
				    		url : "<c:url value='/user/resertPassword.do'/>",
				    		async : false,
				    		dataType : 'json',
				    		data:{"user_id":row.userId},
				    		error : function() {
				    			alert("出错");
				    		},
				    		success : function(msg) {
				    			if(msg.flag=='success'){
				    				$.messager.alert('提示','您当前密码已被管理员重置,新密码已发送至您邮箱!!!');
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
							 ids=ids+","+rows[i].userId;
						 }else{
							 ids=rows[i].userId;
						 }
					 }
					 //批量删除
					 $.ajax({
							type : "post",
							url : "<c:url value='/user/batchDeleteUser.do'/>",
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
		var genders=$("input[name='sex']");
		for(var i=0;i<genders.length;i++){
			if(genders[i].checked){
				$('#sexValue').val(genders[i].value);
			}
		}
		var sex=$('#sexValue').val();
		$('#dg').datagrid('load',{
			userName: $('#userName').val(),//姓名
			gender:	 sex,//性别
			department:$('#department').val(),//部门
			zw:$('#zw').val(),//职务
			sjh:$('#sjh').val(),//手机号
			yx:$('#yx').val(),//邮箱
			xl:$('#xl').val(),//手机号
			beginTime: $('#beginTime').val(),
			endTime: $('#endTime').val(),
		});
	}

	/*设置用户角色  */
	function setRole(index){
		var url=$('#url').val();
		$('#dg').datagrid('selectRow',index);// 关键在这里
		var row = $('#dg').datagrid('getSelected');
		 window.open(url+"userRole/initRoleList.do?userId="+row.userId,'newwindow','height=500, width=400, top=230, left=500, toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no');
	}

	/*设置用户岗位  */
	function setPost(index){
		var url=$('#url').val();
		$('#dg').datagrid('selectRow',index);// 关键在这里
		var row = $('#dg').datagrid('getSelected');
		 window.open(url+"userPost/initPostList.do?userId="+row.userId,'newwindow','height=500, width=400, top=230, left=500, toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no');
	}
</script>
</head>
<body onload="userList()">
	<table id="dg"></table>
	 <div id="tb" style="padding:5px;height:auto">
	        <div style="margin-bottom:5px">
	        	<input type="hidden" value='<c:url value="/"/>' id="url">
	        	<input type="hidden" id="userIds">
	            <a href='#' class="easyui-linkbutton" iconCls="icon-add" plain="true" title="新增" onclick="newUser()"></a>
	           <!--  <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true"></a>
	            <a href="#" class="easyui-linkbutton" iconCls="icon-save" plain="true"></a>
	            <a href="#" class="easyui-linkbutton" iconCls="icon-cut" plain="true"></a> -->
	            <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" title="一键删除" onclick="selectDate()"></a>
	        </div>
	        <div>
	        	 姓名: <input class="easyui-textbox" style="width:200px" id="userName">
	        	 性别: <input class="easyui-radio" type="radio"  data-options="required:true" value="男" name="sex"/>男
					  <input class="easyui-radio" type="radio"  data-options="required:true" value="女" name="sex"/>女
					  <input type="hidden" id="sexValue">
				部门: 
	            <input class="easyui-combobox" style="width:200px"
	                    url='<c:url value="/user/depList.do"/>'
	                    valueField="dep_Id" textField="dep_Name" id="department">
	        	 职务: <input class="easyui-textbox" style="width:200px" id="zw">
	        	 手机号: <input class="easyui-textbox" style="width:200px" id="sjh">
	        	 <br/>
	        	 邮箱: <input class="easyui-textbox" style="width:200px" id="yx">
	        	 学历: <input class="easyui-textbox" style="width:200px" id="xl">
	                         出生年月: <input class="easyui-datebox" style="width:200px" id="beginTime">
	            To: <input class="easyui-datebox" style="width:200px" id="endTime">
	           
	            <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="doSearch()">查询</a>
	        </div>
	    </div>
</body>
</html>