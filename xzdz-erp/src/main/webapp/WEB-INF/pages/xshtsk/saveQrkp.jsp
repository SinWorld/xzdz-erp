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
<title>确认开票</title>
<link href="../login/css/xshtfp.css" rel="stylesheet"/>
<link rel="stylesheet" href="../layui-v2.5.5/layui/css/layui.css">
<link rel="stylesheet" href="../login/css/static.css">
<link rel="stylesheet" href="../bootstrap-3.3.7-dist/css/bootstrap.min.css"> 
<link rel="stylesheet" href="../bootstrap-3.3.7-dist/css/bootstrap-theme.min.css"> 
<script src="../jquery/jquery-3.3.1.js"></script>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@page isELIgnored="false" %>
<style>
  .bj{background-color: #F0F0F0}
 </style>
</head>
<body style="width:100%;padding:0px; margin:0px;"onload="refreshAndClose()">
	
	<div style="width:1280px;height:auto;padding:0px; margin:0 auto;" id="main">
		<form class="layui-form" action='<c:url value="/qrkp/saveQrkp.do"/>' method="post"  enctype="multipart/form-data">
			<input type="hidden" id="url" value='<c:url value="/"/>'>
			<input type="hidden" id="flag" value="${flag}">
			<input type="hidden" id="fjsx" name="fjsx"> 
			<input type="hidden" id="sales_Contract_Id" name="xsht" value="${contract.sales_Contract_Id}">
			<input type="hidden" id="htje" value="${contract.htje}">
			<input type="hidden" value="${taskId}" name="task_Id">
			<input type="hidden" value="${xshtskdm}" name="xshtskdm">
			
		
		<div style="margin-top: 3%;">
			<span style="font-size: 20px;top: -10px;color: #4391e1">开票信息</span>
		</div>
			
			<div class="layui-form-item">
			
				<div class="layui-inline" style="top:9px;left:-15px;">
				      <label class="layui-form-label" style="width: 164px;">是否发票开具</label>
				      <div class="layui-input-inline">
				        <input type="text"   lay-verify=""  autocomplete="off" class="layui-input bj" style="width: 260px;" value="${fpkj}" disabled="" id="fpkj">
				      </div>
			    </div>
			    
				<div class="layui-inline" style="top:9px;left: 30px;" id="fplb">
				    <label class="layui-form-label" style="width: 120px;">发票类别</label>
				    <div class="layui-input-block" style="width: 332px;">
				      <input type="radio" name="is_fplb" value="1" title="增值税专用发票"  lay-filter="is_LX">
				      <input type="radio" name="is_fplb" value="0" title="增值税普通发票"  lay-filter="is_LX">
				    </div>
			    </div>
			</div>
			 
			 
			 <div class="layui-form-item" id="gsmc">
			 	 <div class="layui-inline" style="top:9px;">
				      <label class="layui-form-label" style="width:150px;">公司名称</label>
				      <div class="layui-input-inline">
				        <input type="text"   lay-verify="" autocomplete="off" class="layui-input bj" style="width:260px;" disabled="" value="${customer.unit_Name}">
				      </div>
			     </div>
			     
			      <div class="layui-inline" style="top:9px;" id="sh">
				      <label class="layui-form-label" style="width:150px;">税号</label>
				      <div class="layui-input-inline">
				        <input type="text"  lay-verify="" autocomplete="off" class="layui-input bj" style="width: 260px;" disabled="" value="${customer.duty_Paragraph}">
				      </div>
			     </div>
			     
			      <div class="layui-inline" style="top:9px;left: 106px;" id="yydz">
				      <label class="layui-form-label" style="width:150px;">营业地址</label>
				      <div class="layui-input-inline">
				        <input type="text"  lay-verify="" autocomplete="off" class="layui-input bj" style="width: 260px;" disabled="" value="${customer.office_Address}">
				      </div>
			     </div>
			 </div>
			 
			  <div class="layui-form-item" id="khh">
			 	 <div class="layui-inline" style="top:9px;">
				      <label class="layui-form-label" style="width:150px;">开户行</label>
				      <div class="layui-input-inline">
				        <input type="text"   lay-verify="" autocomplete="off" class="layui-input bj" style="width:260px;" disabled="" value="${customer.opening_Bank}">
				      </div>
			     </div>
			     
			      <div class="layui-inline" style="top:9px;" id="khhzh">
				      <label class="layui-form-label" style="width:150px;">开户行账号</label>
				      <div class="layui-input-inline">
				        <input type="text"  lay-verify="" autocomplete="off" class="layui-input bj" style="width: 260px;" disabled="" value="${customer.account_Number}">
				      </div>
			     </div>
			     
			      <div class="layui-inline" style="top:9px;left: 106px;" id="lxdh">
				      <label class="layui-form-label" style="width:150px;">联系电话</label>
				      <div class="layui-input-inline">
				        <input type="text"  lay-verify="" autocomplete="off" class="layui-input bj" style="width: 260px;" disabled="" value="${customer.telPhone}">
				      </div>
			     </div>
			 </div>
			 
		
		 <div class="layui-form-item">
		 
		 	 <div class="layui-inline" style="top:9px;" id="fpjes">
			      <label class="layui-form-label" style="width:150px;">发票金额</label>
			      <div class="layui-input-inline">
			        <input type="text"  id="fpje" name="fpje" lay-verify="sjsk" autocomplete="off" class="layui-input" style="width:260px;" onblur="checkFpje()">
			      </div>
		     </div>
		 
		 	 <div class="layui-inline" style="top:9px;">
			      <label class="layui-form-label" style="width:150px;">实际收款金额</label>
			      <div class="layui-input-inline">
			        <input type="text"  id="sjsk" name="sjsk" lay-verify="sjsk" autocomplete="off" class="layui-input" style="width:260px;" onblur="checkSjskje()" value="0">
			      </div>
		     </div>
			    
		    <div class="layui-inline" style="top:9px;">
			      <label class="layui-form-label" style="width: 150px;">开票日期</label>
			      <div class="layui-input-inline">
			        <input type="text" name="kprq" id="kprq"  lay-verify="kprq" placeholder="yyyy-mm-dd" autocomplete="off" class="layui-input" style="width: 260px;">
			      </div>
		    </div>
		 </div>
		 
		  <div class="layui-form-item" style="top:9px;">
			  	<div class="layui-inline" style="top:9px;left: 150px;" id="sl">
				      <label class="layui-form-label" style="width: 150px;">税率</label>
				      <div class="layui-input-inline">
				        <input type="text"  name="sl" lay-verify="sl" autocomplete="off" class="layui-input" style="width:260px;">
				      </div>
			    </div>
		  </div>
		 
		<!--附件 -->
			 <div class="layui-upload">
				  <button type="button" class="layui-btn layui-btn-normal" id="testList">选择多文件</button> 
				  <div class="layui-upload-list">
				    <table class="layui-table" style="width: 100%;">
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
				  <button type="button" class="layui-btn" id="testListAction">开始上传</button>
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
<script type="text/javascript" src="../jquery-easyui-1.7.0/jquery.easyui.min.js"></script>
<script>
layui.use(['form', 'layedit', 'laydate','upload'], function(){
  var form = layui.form
  ,layer = layui.layer
  ,layedit = layui.layedit
  ,laydate = layui.laydate
  var url=$('#url').val();
  var upload = layui.upload;
  pageLoad();
  pageLoadSffpkj();
  form.render();

  //日期
  laydate.render({
    elem: '#kprq'
    ,value: new Date()
  });
  //创建一个编辑器
  var editIndex = layedit.build('LAY_demo_editor');

//监听提交
  form.on('submit(demo1)', function(data){
    layer.alert(JSON.stringify(data.field), {
      title: '最终的提交信息'
    })
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

	//是否立项数据改变监听
	form.on('radio(is_LX)', function (data) {
		if(data.value==1){//是
			$('#gsmc').show();
			$('#sh').show();
			$('#yydz').show();
			$('#khh').show();
			$('#khhzh').show();
			$('#lxdh').show();
		}else{
			$('#gsmc').show();
			$('#sh').show();
			$('#yydz').hide();
			$('#khh').hide();
			$('#khhzh').hide();
			$('#lxdh').hide();
		}
  });


	//自定义验证规则
	  form.verify({
		  sjsk: function(value){
		      if(value==""||value==null){
		        return '实际收款金额不能为空';
		      }
		  }
	      ,kprq:function(value){
		       if(value==""||value==null){
		           return '开票日期不能为空';
		       }
	      }
	  });
		

});

	//验证实际收款金额
	function checkSjskje(){
		//获取合同金额
		var htje=$('#htje').val()*1;
		//获取实际收款金额
		var sjskje=$('#sjsk').val()*1;
		if(sjskje>htje){
			$('#sjsk').val("");
			return layer.alert("实际收款金额不得大于合同金额"+" "+htje+"元",{icon:7});
		}
	}

	//验证发票金额
	function checkFpje(){
		//获取合同金额
		var htje=$('#htje').val()*1;
		//获取实际收款金额
		var fpje=$('#fpje').val()*1;
		if(fpje>htje){
			$('#fpje').val("");
			return layer.alert("发票金额不得大于合同金额"+" "+htje+"元",{icon:7});
		}
	}


	function refreshAndClose(){
		var flag=$('#flag').val();
		if(flag){
			window.parent.location.reload();
			window.close();
		} 
	}

	//是否发票开具页面加载事件
	function pageLoadSffpkj(){
		var fpkj=$('#fpkj').val();
		if(fpkj=="是"){
			$('#fplb').show();
			$('#sl').show();
			$('#fpje').show();
		}else{
			$('#fplb').hide();
			$('#sl').val("");
			$('#sl').hide();
			$('#fpje').val("");
			$('#fpje').hide();
		}
	}

	//页面加载事件
	function pageLoad(){
		$('#fpje').hide();
		$('#gsmc').hide();
		$('#sh').hide();
		$('#yydz').hide();
		$('#khh').hide();
		$('#khhzh').hide();
		$('#lxdh').hide();
		$('#sl').hide();
	}

</script>
</body>
</html>