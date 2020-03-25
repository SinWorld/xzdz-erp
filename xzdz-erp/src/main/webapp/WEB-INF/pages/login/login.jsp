<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>登录页</title>
<link rel="shortcut icon" href="favicon.ico">

	<link href='https://fonts.googleapis.com/css?family=Open+Sans:400,700,300' rel='stylesheet' type='text/css'>
	
	<link rel="stylesheet" href="../login/newLogin/css/bootstrap.min.css">
	<link rel="stylesheet" href="../login/newLogin/css/animate.css">
	<link rel="stylesheet" href="../login/newLogin/css/style.css">
	<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
	<%@page isELIgnored="false"%>
	<!-- Modernizr JS -->
	<script src="../login/newLogin/js/modernizr-2.6.2.min.js"></script>
</head>
<body class="style-2">

		<div class="container">
			<div class="row">
				<div style="text-align: left;">
					<img src="../login/images/logo.png" style="width: 15%;">
				</div>
				<div class="col-md-12 text-center">
					<ul class="menu">
						<p style="font-size: 30px;font-weight: bold;">新洲电子ERP管理平台</p>
					</ul>
				</div>
			</div>
			<div class="row">
				<div class="col-md-4">
					<!-- Start Sign In Form -->
					<form action="#" class="fh5co-form animate-box" data-animate-effect="fadeInLeft">
						<h2>登录</h2>
						<div class="form-group">
							<label for="username" class="sr-only">用户名</label>
							<input type="text" class="form-control" id="loginName" placeholder="用户名" autocomplete="off">
						</div>
						<div class="form-group">
							<label for="password" class="sr-only">密码</label>
							<input type="password" class="form-control" id="password" placeholder="密码" autocomplete="off">
						</div>
						<div class="form-group">
							<input type="button" id="login" value="登录" class="btn btn-primary">
						</div>
					</form>
					<!-- END Sign In Form -->

				</div>
			</div>
			<div class="row" style="padding-top: 60px; clear: both;">
			</div>
		</div>
	
	<!-- jQuery -->
	<script src="../login/newLogin/js/jquery.min.js"></script>
	<!-- Bootstrap -->
	<script src="../login/newLogin/js/bootstrap.min.js"></script>
	<!-- Placeholder -->
	<script src="../login/newLogin/js/jquery.placeholder.min.js"></script>
	<!-- Waypoints -->
	<script src="../login/newLogin/js/jquery.waypoints.min.js"></script>
	<!-- Main JS -->
	<script src="../login/newLogin/js/main.js"></script>
	<script src="../layui-v2.5.5/layui/layui.js"></script>
	<script type="text/javascript">
      layui.use([ 'form', 'layer', 'jquery' ], function() {
			$(document).on('click', '#login', function() {
				// console.log(data.field);
				var userName = $('#loginName').val();
				var password = $('#password').val();
				if (userName == "") {
					layer.msg('登录名不能为空');
					return;
				}
				if (password == "") {
					layer.msg('密码不能为空');
					return;
				}
				$.ajax({
					url : '<c:url value="/login/doLogin.do"/>',
					data : {
						"loginName" : userName,
						"password" : password
					},
					dataType : 'json',
					type : 'post',
					async : false,
					success : function(data) {
						if (data.flag == 'qt') {
							location.href = '<c:url value="/index/initIndex.do"/>';
							return;
						}
						if (data.flag == 'ht') {
							location.href = '<c:url value="/adminIndex/initIndex.do"/>';
							return;
						}
						if (data.flag == 'fail') {
							layer.alert('用户名或密码错误，请重新输入', {
								icon : 2
							});
							return;
						}
					}
				});
			});
  	});
    	
		document.onkeydown = function(e) {
			var a = e || window.event;//加这个火狐下不会报 event is  undefind
			var userName = $('#loginName').val();
			var password = $('#password').val();
			if (a.keyCode == 13) {
				if (userName == "") {
					layer.msg('登录名不能为空');
					return;
				}
				if (password == "") {
					layer.msg('密码不能为空');
					return;
				}
				$.ajax({
					url : '<c:url value="/login/doLogin.do"/>',
					data : {
						"loginName" : userName,
						"password" : password
					},
					dataType : 'json',
					type : 'post',
					async : false,
					success : function(data) {
						if (data.flag == 'qt') {
							location.href = '<c:url value="/index/initIndex.do"/>';
							return;
						}
						if (data.flag == 'ht') {
							location.href = '<c:url value="/adminIndex/initIndex.do"/>';
							return;
						}
						if (data.flag == 'fail') {
							layer.alert('用户名或密码错误，请重新输入', {
								icon : 2
							});
							return;
						}
					}
				});
		
			}
		}

		$(document).ready(function () {
		    if (window != top) {
		        top.location.href ='<c:url value="/login/initLogin.do"/>';
		    }
		});

	</script>

	</body>
</html>