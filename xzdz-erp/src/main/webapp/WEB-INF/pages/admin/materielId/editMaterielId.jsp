<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8">
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">
<title>编辑物料Id</title>
<link rel="stylesheet" href="../layui-v2.5.5/layui/css/layui.css">
<link rel="stylesheet" href="../bootstrap-3.3.7-dist/css/bootstrap.min.css"> 
<link rel="stylesheet" href="../bootstrap-3.3.7-dist/css/bootstrap-theme.min.css"> 
<script src="../jquery/jquery-3.3.1.js"></script>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@page isELIgnored="false" %>
<style>
  .bj{background-color: #F0F0F0}
 </style>
</head>
<body style="width:100%;padding:0px; margin:0px;" onload="refreshAndClose()">
	<div style="width:1100px;height:auto;padding:0px; margin:0 auto;" id="main">
		<form class="layui-form" action='<c:url value="/materielId/editMaterielId.do"/>' method="post">
			<input type="hidden" id="url" value='<c:url value="/"/>'>
			<input type="hidden" id="flag" value="${flag}">
			<input type="hidden" name="row_Id" value="${materielId.row_Id}" id="row_Id">
			<input type="hidden" id="fjsx" name="fjsx">
			<input type="hidden" id="userId" value="${userId}"> 
			<input type="hidden" name="task_Id" id="taskId" value="${taskId}">
			<input type="hidden" value="${materielId.materielNumber}" id="materielNumber"> 
			 
			<div class="layui-form-item" style="margin-top:5%;">
			     <div class="layui-inline" style="top:9px;">
				      <label class="layui-form-label" style="width: 90px;">物料Id</label>
				      <div class="layui-input-inline">
				        <input type="text" name="materiel_Id" lay-verify="materiel_Id" autocomplete="off" class="layui-input" id="materiel_Id" value="${materielId.materiel_Id}" onblur="checkWlid()">
				      </div>
			     </div>
			     
			    <div class="layui-inline" style="top:9px;">
				      <label class="layui-form-label" style="width: 90px;">规格型号</label>
				      <div class="layui-input-inline">
				        <input type="text" name="specification_Type" lay-verify="specification_Type" autocomplete="off" class="layui-input" id="specification_Type" value="${materielId.specification_Type}">
				      </div>
			    </div>
			    
			     <div class="layui-inline" style="top:9px;">
				      <label class="layui-form-label" style="width:150px;">保质期</label>
				      <div class="layui-input-inline">
				        <input type="text" name="bzq" lay-verify="bzq" autocomplete="off" class="layui-input" id="bzq" value="${materielId.bzq}">
				      </div>
			     </div>
			 </div>
			
			<div class="layui-form-item">
			     <div class="layui-inline" style="left: -15px;">
				      <label class="layui-form-label" style="width:105px;">参考单价</label>
				      <div class="layui-input-inline">
				        <input type="text" name="ckdj" lay-verify="ckdj" autocomplete="off" class="layui-input" id="ckdj" value="${materielId.ckdj}">
				      </div>
			     </div>
			     
			  	<div class="layui-inline" style="left: -30px;">
				      <label class="layui-form-label" style="width:105px;">物料Id类型</label>
				      <div class="layui-input-inline">
				        <input type="text" name="ckdj" lay-verify="ckdj" autocomplete="off" class="layui-input bj" id="ckdj" value="${materielId.materielTypeName}" disabled="">
				      </div>
			     </div>
			     
			     <div class="layui-inline">
				      <label class="layui-form-label" style="width:120px;">物料Id类型号</label>
				      <div class="layui-input-inline">
				        <input type="text" name="ckdj" lay-verify="ckdj" autocomplete="off" class="layui-input bj" id="ckdj" value="${materielId.materielNumberName}" disabled="">
				      </div>
			     </div>
		   </div>
		   
			
		 <div class="layui-form-item layui-form-text">
		    <label class="layui-form-label" style="width:90px;">物料描述</label>
		    <div class="layui-input-block" style="left: -20px;">
		      <textarea placeholder="请输入内容" name="remarks"  lay-verify="remarks" id="remarks" class="layui-textarea" style="width:86.6%">${materielId.remarks}</textarea>
		    </div>
		 </div>
		 
		  <!--附件 -->
		 <div class="layui-upload">
			  <button type="button" class="layui-btn layui-btn-normal" id="testList" style="margin-left: 10px;">选择多文件</button> 
			  <div class="layui-upload-list">
			    <table class="layui-table" style="width:86%;">
			      <thead>
			        <tr>
				        <th style="text-align: center;">文件名</th>
				        <th style="text-align: center;">大小</th>
				        <th style="text-align: center;">状态</th>
				        <th style="text-align: center;">操作</th>
			      	</tr>
			      </thead>
			      <tbody id="demoList"></tbody>
			    </table>
			  </div>
			  <button type="button" class="layui-btn" id="testListAction" style="margin-left: 10px;">开始上传</button>
		</div> 
	
		 

		<div class="layui-form-item" style="text-align: center;">
		    <div class="layui-input-block">
		      <button class="layui-btn" lay-submit="" lay-filter="demo1" style="width:25%;margin-top:10px;margin-left:-315px;">立即提交</button>
		    </div>
		</div>
	</form>
 </div>
<script src="../bootstrap-3.3.7-dist/js/bootstrap.js"></script>
<script src="../layui-v2.5.5/layui/layui.js" charset="utf-8"></script>
<script>
layui.use(['form', 'layedit', 'laydate','upload'], function(){
  var form = layui.form
  ,layer = layui.layer
  ,layedit = layui.layedit
  ,laydate = layui.laydate
  ,upload = layui.upload;
  var url=$('#url').val();
  form.render();
 
  //创建一个编辑器
  var editIndex = layedit.build('LAY_demo_editor');

  //监听提交
  $(document).keydown(function (e) {
	if (e.keyCode != 13) {
		 form.on('submit(demo1)', function(data){
			  return true;
		 });
	}else{
		form.on('submit(demo1)', function(data){
			  return false;
		 });
	}
  });

  form.on('select(type)', function(data){
		//获得填写的物料Id
		var wlId=$('#materiel_Id').val();
		//获得类型
		var type=$('#type').val();
		$.ajax({
			type : "post",
			url : "<c:url value='/materielId/wlIdbcf.do'/>",
			async : false,
			dataType : 'json',
			data:{"materiel_Id":wlId,"type":type},
			error : function() {
				alert("出错");
			},
			success : function(msg) {
				if(msg.flag){
					$('#materiel_Id').val("");
					layer.alert("当前物料Id已存在,不允许重复!",{icon:7})
				}
			}
		});
	});

//自定义验证规则
  form.verify({
	  materiel_Id: function(value){
	      if(value==""||value==null){
	        return '物料Id不能为空';
	      }
	  }
      ,specification_Type:function(value){
	       if(value==""||value==null){
	           return '规格型号不能为空';
	       }
      }
      ,bzq:function(value){
	       if(value==""||value==null){
	           return '保质期不能为空';
	         }
      }
       ,ckdj:function(value){
	        if(value==""||value==null){
	            return '参考单价不能为空';
	          }
       }
  });


//多文件列表示例
  var fjsx=$('#fjsx').val();
  var demoListView = $('#demoList')
  ,uploadListIns = upload.render({
    elem: '#testList'
    ,url: '<c:url value="/sales/upload.do"/>'
    ,accept: 'file'
    ,multiple: true
    ,auto: false
    ,bindAction: '#testListAction'
    ,choose: function(obj){   
      var files = this.files = obj.pushFile(); //将每次选择的文件追加到文件队列
      //读取本地文件
      obj.preview(function(index, file, result){
        var tr = $(['<tr id="upload-'+ index +'">'
          ,'<td>'+ file.name +'</td>'
          ,'<td>'+ (file.size/1014).toFixed(1) +'kb</td>'
          ,'<td>等待上传</td>'
          ,'<td>'
            ,'<button class="layui-btn layui-btn-xs demo-reload layui-hide">重传</button>'
            ,'<button class="layui-btn layui-btn-xs layui-btn-danger demo-delete">删除</button>'
          ,'</td>'
        ,'</tr>'].join(''));
        
        //单个重传
        tr.find('.demo-reload').on('click', function(){
          obj.upload(index, file);
        });
        
        //删除
        tr.find('.demo-delete').on('click', function(){
          delete files[index]; //删除对应的文件
          tr.remove();
          uploadListIns.config.elem.next()[0].value = ''; //清空 input file 值，以免删除后出现同名文件不可选
        });
        
        demoListView.append(tr);
      });
    }
    ,done: function(res, index, upload){
      if(res.code == 0){ //上传成功
        var tr = demoListView.find('tr#upload-'+ index)
        ,tds = tr.children();
        tds.eq(2).html('<span style="color: #5FB878;">上传成功</span>');
        tds.eq(3).html(''); //清空操作
        //将附件属性拼接字符串提交至后端
         var fj=res.data;
		 //将json串转换为字符串
		 var str = JSON.stringify(fj);
        if(undefined!=fjsx){
			 fjsx=fjsx+","+str;
			 $('#fjsx').val(fjsx);
		 }else{
			 fjsx=str;
			 $('#fjsx').val(fjsx);
		 }
        return delete this.files[index]; //删除文件队列已经上传成功的文件
      }
      this.error(index, upload);
    }
    ,error: function(index, upload){
      var tr = demoListView.find('tr#upload-'+ index)
      ,tds = tr.children();
      tds.eq(2).html('<span style="color: #FF5722;">上传失败</span>');
      tds.eq(3).find('.demo-reload').removeClass('layui-hide'); //显示重传
    }
  });

  fjPageLoad();
});

	function checkWlid(){
		//获得填写的物料Id
		var wlId=$('#materiel_Id').val();
		var materielNumber=$('#materielNumber').val();
		$.ajax({
			type : "post",
			url : "<c:url value='/materielId/wlIdbcf.do'/>",
			async : false,
			dataType : 'json',
			data:{"materiel_Id":wlId,"materielNumber":materielNumber},
			error : function() {
				alert("出错");
			},
			success : function(msg) {
				if(msg.flag){
					$('#materiel_Id').val("");
					layer.alert("当前物料Id已存在,不允许重复!",{icon:7})
				}
			}
		});
	}

	
	function refreshAndClose(){
		var userId=$('#userId').val();
		var flag=$('#flag').val();
		if(flag){
			if(userId==-1){
				window.parent.opener.location.reload();
				window.close();
			}else{
				var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
	            parent.location.reload();//刷新父页面，注意一定要在关闭当前iframe层之前执行刷新
	            parent.layer.close(index); //再执行关闭
			}
		} 
	}

	function  fjPageLoad(){
		var row_Id=$('#row_Id').val();
		var demoListView = $('#demoList');
		$.ajax({
			type : "post",
			url : "<c:url value='/materielId/pageLoadFj.do'/>",
			async : false,
			dataType : 'json',
			data:{"row_Id":row_Id},
			error : function() {
				alert("出错");
			},
			success : function(msg) {
				for(var i=0;i<msg.length;i++){
					  var tr = $(['<tr id="upload-'+ i+1 +'">'
			          ,'<td>'+msg[i].fileName+'</td>'
			          ,'<td>'+msg[i].fileSize+'</td>'
			          ,'<td>已经上传</td>'
			          ,'<td>'
			            ,'<button class="layui-btn layui-btn-xs demo-reload layui-hide">重传</button>'
			            ,'<button class="layui-btn layui-btn-xs layui-btn-danger demo-delete" onclick="removeFile(this)" type="button">删除</button>'
			          ,'</td>'
			        ,'</tr>'].join(''));
				  demoListView.append(tr);
				}
			}
		});
	}

	function removeFile(obj){
		//获得当前表格行索引
		var index=obj.parentElement.parentElement.rowIndex;
		var demoListView = $('#demoList');
		var row_Id=$('#row_Id').val();
		//获得当前表格中的文件名
		var fileName=demoListView[0].rows[index-1].cells[0].innerText;
		layer.confirm('您确定要删除该附件么？', {
			  btn: ['确定','取消'], //按钮
			  title:'提示',icon:7},function(){
				  $.ajax({
						type : "post",
						url : "<c:url value='/materielId/removeFj.do'/>",
						async : false,
						dataType : 'json',
						data:{"row_Id":row_Id,"fileName":fileName},
						error : function() {
							alert("出错");
						},
						success : function(msg) {
							if(msg.flag){
								demoListView[0].rows[index-1].remove();
							    var rowNum = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
		                        location.reload();//刷新父页面，注意一定要在关闭当前iframe层之前执行刷新
		                        layer.close(rowNum); //再执行关闭
							}
						}
					});
			  }
			)
	}
</script>
</body>
</html>