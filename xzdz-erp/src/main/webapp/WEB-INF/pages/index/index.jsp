<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
  <title>新洲电子ERP</title>
  <link rel="stylesheet" href="../layui-v2.5.5/layui/css/layui.css">
  <%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
  <%@page isELIgnored="false" %>
</head>
<body class="layui-layout-body" onload="loadTopWindow()">
<div class="layui-layout layui-layout-admin">
  <div class="layui-header">
    <div class="layui-logo"  style="font-size: 30px;">新洲电子ERP</div>
    <i class="layui-icon" style="font-size: 50px; color: #F0F0F0">&#xe62e;</i>
    <!-- 头部区域（可配合layui已有的水平导航） -->
    <ul class="layui-nav layui-layout-right">
      <li class="layui-nav-item">
      	<i class="layui-icon" style="cursor:pointer;font-size: 20px;" title="通知" onclick="notice()">&#xe667; <span class="layui-badge" style="margin: -11px -3px 0;top:30%;">1</span></i>
      </li>
      <li class="layui-nav-item">
        <a href="javascript:;">
	        <c:if test="${not empty user.photoName  }">
	        	<img src="../photo/${user.photoName}"  class="layui-nav-img"> 
	      			${user.userName}
	        </c:if>
	        <c:if test="${empty user.photoName }">
	        	<img src="../login/images/photo-pic.png"  class="layui-nav-img"> 
	      			${user.userName}
	        </c:if>
        </a>
        <input type="hidden" value="${user.userId}" id="userId">
   		<input type="hidden" value='<c:url value="/"/>' id="url">
   		<input type="hidden" id="materielIds">
   		<input type="hidden" id="clIds">
        <dl class="layui-nav-child">
          <dd><a onclick="userShow()">基本资料</a></dd>
          <dd><a onclick="initSecuritySetting()">修改密码</a></dd>
          <dd><a onclick="imgShow()">上传头像</a></dd>
        </dl>
      </li>
      <li class="layui-nav-item">
       <a  onclick="exit()" style="cursor:pointer;">退出</a>
      </li>
    </ul>
  </div>
  
  <div class="layui-side layui-bg-black">
    <div class="layui-side-scroll">
      <!-- 左侧导航区域（可配合layui已有的垂直导航） -->
      <ul class="layui-nav layui-nav-tree"  lay-filter="test">
      <li class="layui-nav-item"><a href=""><i class="layui-icon">&#xe68e;</i>&nbsp;&nbsp;首页</a></li>
      <c:forEach items="${privilegeTopList}" var="p">
        <li class="layui-nav-item">
        <c:if test="${empty p.fp_Url}">
        	<a class="" href="javascript:;"><i class="layui-icon">${p.fp_Icon}</i>&nbsp;&nbsp;${p.fp_Name}</a>
        </c:if>
        <c:if test="${not empty p.fp_Url}">
        	<a class="" href='<c:url value="${p.fp_Url}"/>' target="iframe_a"><i class="layui-icon">${p.fp_Icon}</i>&nbsp;&nbsp;${p.fp_Name}</a>
        </c:if>
          <c:forEach items="${p.children}" var="c">
	           <dl class="layui-nav-child">
	            <dd><a  href='<c:url value="${c.fp_Url}"/>' mytitle="${c.fp_Name}"  target="iframe_a">${c.fp_Name}</a></dd>
	          </dl>
          </c:forEach> 
        </li>
    </c:forEach> 
      </ul>
    </div>
  </div>
  
  <div class="layui-body">
    <!-- 内容主体区域 -->
   
    <iframe  src='<c:url value="/myTask/indexPage.do"/>' name="iframe_a" id="iframe-page-content"  width="100%" height="100%" frameborder="no" border="0" marginwidth="0" marginheight=" 0" scrolling="no" allowtransparency="yes"></iframe>
    
  </div>
  
  <div class="layui-footer">
    <!-- 底部固定区域 -->
    <a href="http://www.ahajtk.com">安徽爱吉泰克科技有限公司</a>
  </div>
</div>
<script src="../layui-v2.5.5/layui/layui.js"></script>
<script type="text/javascript" src="../jquery/jquery-3.3.1.js"></script>
<script>
//JavaScript代码区域
	layui.use(['element','layer'], function(){
	  var element = layui.element;
	  var $ = layui.$;
	  var layer = layui.layer;
	  warnCpKc();
	  warnClKc();
	}); 

	function reinitIframe(){
		var iframe = document.getElementById("iframe-page-content");
		try{
		var bHeight = iframe.contentWindow.document.body.scrollHeight;
		var dHeight = iframe.contentWindow.document.documentElement.scrollHeight;
		var height = Math.max(bHeight, dHeight);
		iframe.height = height;
		console.log(height);
		}catch (ex){}
	}

	function loadTopWindow(){ 
		if (window.top!=null && window.top.document.URL!=document.URL){ 
			window.top.location= document.URL; //这样就可以让登陆窗口显示在整个窗口了 
		} 
	} 

	//跳转至修改密码页面
	function initSecuritySetting (){
		var url=$('#url').val();
		layer.open({
	  	  	type:2,
	  	  	title:'修改密码',
	  	  	area: ['38%','45%'],
	  		shadeClose: false,
	  		resize:false,
	  	    anim: 1,
	  	  	content:[url+"user/initSecuritySetting.do",'yes']
		 });
	}

	//跳转至基本资料页面
	function userShow(){
		var url=$('#url').val();
		layer.open({
	  	  	type:2,
	  	  	title:'基本资料',
	  	  	area: ['50%','50%'],
	  		shadeClose: false,
	  		resize:false,
	  	    anim: 1,
	  	  	content:[url+"user/baseZL.do",'yes']
		 });
	}

	//跳转至上传头像页面
	function imgShow(){
		var url=$('#url').val();
		layer.open({
	  	  	type:2,
	  	  	title:'上传头像',
	  	  	area: ['50%','50%'],
	  		shadeClose: false,
	  		resize:false,
	  	    anim: 1,
	  	  	content:[url+"user/initImgShow.do",'yes']
		 });
	}

	//退出系统
	function exit(){
		 layer.confirm('您确定要退出系统么？', {
			  btn: ['确定','取消'], //按钮
			  title:'提示',icon:7},function(){
				  location.href='<c:url value="/login/exit.do"/>';
			  }
			)
		}

	//通知
	function notice(){
		var url=$('#url').val();
		layer.open({
	  	  	type:2,
	  	  	title:'通知列表',
	  	  	area: ['50%','50%'],
	  		shadeClose: false,
	  		resize:false,
	  	    anim: 1,
	  	  	content:[url+"notice/initNoticeList.do",'yes']
		 });
	}

	//成品库存警报
	function warnCpKc(){
		var materielIds=$('#materielIds').val();
		//ajax查询所有的库存小于100的进行弹窗提示
		$.ajax({
			type : "post",
			url : "<c:url value='/stock/warnStockList.do'/>",
			async : false,
			dataType : 'json',
			error : function() {
				alert("出错");
			},
			success : function(msg) {
				//遍历结果集
				for(var i=0;i<msg.length;i++){
					materielIds=materielIds+","+msg[i].materielId;
				}
				if(msg.length>0){
					var data=materielIds.substring(1, materielIds.length);
					layer.open({
		                offset: 'rb',
		                title: "库存报警",
		                area: ['350px', '180px'],
		                shade: 0,
		                type: 0,
		                content:"在成品库存中物料Id为:【"+data+"】的成品库存量小于200请知悉!!!",
		                time: 10000,
		                icon: 0,
		                anim: 2
		            });
				}
			}
		});
	}

	//材料库存警报
	function warnClKc(){
		var materielIds=$('#clIds').val();
		//ajax查询所有的库存小于100的进行弹窗提示
		$.ajax({
			type : "post",
			url : "<c:url value='/kc_materialStock/warnMaterialStockList.do'/>",
			async : false,
			dataType : 'json',
			error : function() {
				alert("出错");
			},
			success : function(msg) {
				//遍历结果集
				for(var i=0;i<msg.length;i++){
					materielIds=materielIds+","+msg[i].materielId;
				}
				if(msg.length>0){
					var data=materielIds.substring(1, materielIds.length);
					layer.open({
		                offset: 'rb',
		                title: "库存报警",
		                area: ['350px', '180px'],
		                shade: 0,
		                type: 0,
		                content:"在材料库存中物料Id为:【"+data+"】的材料库存量小于200请知悉!!!",
		                time: 10000,
		                icon: 0,
		                anim: 2
		            });
				}
			}
		});
	}
</script>
</body>
</html>