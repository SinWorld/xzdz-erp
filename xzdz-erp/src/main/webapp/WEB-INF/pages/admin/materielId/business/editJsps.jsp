<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>领导审核</title>
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
	<div style="width:625px;height:auto;padding:0px; margin:0 auto;" id="main">
		<form class="layui-form" action='<c:url value="/technicalReview/editTechnicalReview.do"/>' method="post" id="myForm" enctype="multipart/form-data">
			<input type="hidden" id="url" value='<c:url value="/"/>'>
			<input type="hidden" id="flag" value="${flag}">
			<input type="hidden" name="task_Id" id="taskId" value="${taskId}">
			<input type="hidden" id="fjsx" name="fjsx"> 
			<input type="hidden" value="${materielIdDm}" name="row_Id" id="row_Id">
		
			<div class="layui-form-item" style="margin-top: 3%;margin-left:80px;">
			    <label class="layui-form-label"  style="width: 125px;">审批结果</label>
			    <div class="layui-input-block">
			      <input type="radio" name="out_come" value="可行" title="可行" lay-verify="result" >
			      <input type="radio" name="out_come" value="不可行" title="不可行" lay-verify="result">
			    </div>
			  </div>

			<div class="layui-form-item layui-form-text" style="margin-left:80px;">
				<label class="layui-form-label" style="width: 125px;">审批意见</label>
				<div class="layui-input-block">
					<textarea placeholder="请输入内容" lay-verify="remark"
						class="layui-textarea" style="width: 56.5%" name="advice_" id="advice"></textarea>
				</div>
			</div>
			
			<!--附件 -->
			 <div class="layui-upload">
				  <button type="button" class="layui-btn layui-btn-normal" id="testList" style="margin-left: 100px;">选择多文件</button> 
				  <div class="layui-upload-list">
				    <table class="layui-table" style="width:63%;margin-left: 100px;">
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
				  <button type="button" class="layui-btn" id="testListAction" style="margin-left: 100px;">开始上传</button>
			</div> 
	
		<div class="layui-form-item" style="text-align: center;">
		    <div class="layui-input-block">
		      <button class="layui-btn" lay-submit="" lay-filter="demo1" style="width:25%;margin-left:-100px;">立即提交</button>
		    </div>
		</div>
	</form>
 </div>
<script src="../bootstrap-3.3.7-dist/js/bootstrap.js"></script>
<script src="../layui-v2.5.5/layui/layui.js" charset="utf-8"></script>
<script type="text/javascript" src="../jquery-easyui-1.7.0/jquery.easyui.min.js"></script>
<script>
layui.use(['form', 'layedit', 'laydate','upload'], function(){
  var form = layui.form
  ,layer = layui.layer
  ,layedit = layui.layedit
  ,laydate = layui.laydate;
  var url=$('#url').val();
  var upload = layui.upload;
  fjPageLoad();
  form.render();
  //创建一个编辑器
  var editIndex = layedit.build('LAY_demo_editor');
  //监听提交
  form.on('submit(demo1)', function(data){
    /* layer.alert(JSON.stringify(data.field), {
      title: '最终的提交信息'
    }) */
    return true;
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
  

	//自定义验证规则
	  form.verify({
		  result: function(value,item){
		      var verifyName=$(item).attr('name')
		      , verifyType=$(item).attr('type')
		      ,formElem=$(item).parents('.layui-form')//获取当前所在的form元素，如果存在的话
				,verifyElem=formElem.find('input[name='+verifyName+']')//获取需要校验的元素
				,isTrue= verifyElem.is(':checked')//是否命中校验
				,focusElem = verifyElem.next().find('i.layui-icon');//焦点元素
				if(!isTrue || !value){
				        //定位焦点
				        focusElem.css(verifyType=='radio'?{"color":"#FF5722"}:{"border-color":"#FF5722"});
				        //对非输入框设置焦点
				        focusElem.first().attr("tabIndex","1").css("outline","0").blur(function() {
				            focusElem.css(verifyType=='radio'?{"color":""}:{"border-color":""});
				         }).focus();
				        return '审批结果不能为空';
				}
		    }
	      ,remark(value){
		       if(value==""||value==null){
		           return '审批意见不能为空';
		       }
	      }
	  });
		  
});
	
	function refreshAndClose(){
		var flag=$('#flag').val();
		if(flag){
			window.parent.location.reload();
			window.close();
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