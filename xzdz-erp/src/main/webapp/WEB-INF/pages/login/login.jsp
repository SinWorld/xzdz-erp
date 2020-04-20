<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>登录页</title>
<link rel="shortcut icon" href="favicon.ico">

	<link href='https://fonts.googleapis.com/css?family=Open+Sans:400,700,300' rel='stylesheet' type='text/css'>
	
	<link rel="stylesheet" href="../login/loginDemo/css/login.css">
	<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
	<%@page isELIgnored="false"%>
	<!-- Modernizr JS -->
	<script src="../login/newLogin/js/modernizr-2.6.2.min.js"></script>
</head>
	<body>

		<div class="body-bg">
        <div class="center">
            <h1 class="top-logo">
                <a href="javascript::">
                    <img src="../login/loginDemo/img/logo.png" alt="logo">
                </a>
            </h1>
            <div class="body-login-box">
                <div class="box-title">
                    	企业资源一体化管控平台（ERP）
                </div>
                <div class="box-etitle">
                    Enterprise  Resouces  Integrated  Management  and  Control  Platform
                </div>
                <div class="login-form">
                    <div class="box-l"> </div>
                    <div class="box-r">
                        <form>
                            <div class="form-title">用户登录</div>
                            <div class="form-item">
                                <input type="text" name="name" id="loginName" placeholder="请输入您的用户名">
                            </div>
                            <div class="form-item psd-item">
                                <input type="password" name="psd" id="password" placeholder="请输入您的密码">
                            </div>
                            <div class="form-submit">
                                <button class="b-btn" id="login" type="button">立即登录</button>
                            </div>
                        </form>
                    </div>

                </div>
            </div>
            <div class="foot-container">
                <p>Copyright 2020 技术支持单位：安徽爱吉泰克科技有限公司</p>
                <p>联系电话：0551-65527816</p>
                <div class="foot-wexin"><img src="../login/loginDemo/img/erweima.png" alt="微信"></div>
            </div>
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