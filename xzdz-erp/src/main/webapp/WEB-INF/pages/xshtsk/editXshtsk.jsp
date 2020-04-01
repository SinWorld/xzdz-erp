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
<title>编辑销售合同收款</title>
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
	<div class="m-xm_message_box">
		<div>
			<strong id="_field_xiaoShouHTMC" class="u-header_link">${contract.sales_Contract_Name}</strong>
			<div class="oim-field u-header" style=" margin:10px 0 0 0px;">
	             <label for="" class="oim-field_label u-f13">合同编号</label>
	             <span class="oim-field_text-show u-f13" id="_field_xiangMuXXBH">
	             	${contract.contract_Code}
	             </span>
	         	 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		             <label for="" class="oim-field_label u-f13">累计开票金额</label>
		             <span id="_leiJiKPJE">${ljkpje}</span>
		                <span class="u-rmb">元</span>
						 <div class="layui-progress" lay-showpercent="true" style="width: 6%; display: inline-block;">
						  <div class="layui-progress-bar" lay-percent="${ljkpjebl}"></div>
						 </div>
					 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<label for="" class="oim-field_label u-f13">剩余开票金额</label>
		             <span id="_shengYuKPJE">${sykpje}</span>
		             <span class="u-rmb">元</span>
		              &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<label for="" class="oim-field_label u-f13">累计收款金额</label>
		             <span id="_shengYuKPJE">${ljskje}</span>
		             <span class="u-rmb">元</span>
	             	 <div class="layui-progress" lay-showpercent="true" style="width: 6%; display: inline-block;">
					  <div class="layui-progress-bar" lay-percent="${ljskjebl}"></div>
					 </div>
				     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<label for="" class="oim-field_label u-f13">剩余收款金额</label>
		             <span id="_shengYuKPJE">${syskje}</span>
		             <span class="u-rmb">元</span>
	          </div>	
		</div>
	</div>
	
	<div style="width:1280px;height:auto;padding:0px; margin:0 auto;" id="main">
		<form class="layui-form" action='<c:url value="/xshtsk/editXshtsk.do"/>' method="post"  enctype="multipart/form-data">
			<input type="hidden" id="url" value='<c:url value="/"/>'>
			<input type="hidden" id="flag" value="${flag}">
			<input type="hidden" id="supplier">
			<input type="hidden" id="fjsx" name="fjsx"> 
			<input type="hidden" id="sales_Contract_Id" name="xsht" value="${contract.sales_Contract_Id}">
			<input type="hidden" id="htje" value="${contract.htje}">
			<input type="hidden" id="taskId" value="${taskId}" name="taskId">
			<input type="hidden" value="${xshtsk.is_fpkj}"id="sfkpkj">
			<input type="hidden" value="${xshtsk.xshtskdm}" name="xshtskdm" id="xshtskdm">
			<input type="hidden" value="${syskje}" id="syskje">
			<input type="hidden" value="${sykpje }" id="sykpje">
			
			
		
		<div style="margin-top: 5%;">
			<span style="font-size: 20px;top: -10px;color: #4391e1">付款方信息</span>
		</div>
			
			<div class="layui-form-item">
			     <div class="layui-inline" style="top:9px;">
				      <label class="layui-form-label" style="width:150px;">付款方</label>
				      <div class="layui-input-inline">
				        <input type="text"   lay-verify="" autocomplete="off" class="layui-input bj" style="width:750px;" disabled="" value="${customer.unit_Name}">
				      </div>
			     </div>
			 </div>
			 
			 <div class="layui-form-item">
			      <div class="layui-inline" style="top:9px;">
				      <label class="layui-form-label" style="width:150px;">开户银行</label>
				      <div class="layui-input-inline">
				        <input type="text"  lay-verify="" autocomplete="off" class="layui-input bj" style="width: 280px;" disabled="" value="${customer.opening_Bank}">
				      </div>
			     </div>
			     
			      <div class="layui-inline" style="top:9px;left: 106px;">
				      <label class="layui-form-label" style="width:150px;">银行账号</label>
				      <div class="layui-input-inline">
				        <input type="text"  lay-verify="" autocomplete="off" class="layui-input bj" style="width: 280px;" disabled="" value="${customer.account_Number}">
				      </div>
			     </div>
			 </div>
			 
		<div>
			<span style="font-size: 20px;top: -10px;color: #4391e1">收款信息</span>
		</div>
		
		 <div class="layui-form-item">
			  <div class="layui-inline" style="top:9px;left: 30px;">
				    <label class="layui-form-label" style="width: 120px;">是否发票开具</label>
				    <div class="layui-input-block" style="width: 332px;">
				      <input type="radio" name="is_fpkj" value="1" title="是"  lay-filter="is_LX" checked="checked">
				      <input type="radio" name="is_fpkj" value="0" title="否"  lay-filter="is_LX">
				    </div>
			    </div>
			    
			    <div class="layui-inline" style="top:9px;">
				      <label class="layui-form-label" style="width: 164px;">申请开票日期</label>
				      <div class="layui-input-inline">
				        <input type="text" name="sqkprq" id="sqkprq"  lay-verify="sqkprq" placeholder="yyyy-mm-dd" autocomplete="off" class="layui-input" style="width: 280px;" value="${xshtsk.shenqkprq}">
				      </div>
			    </div>
		 </div>
		 
		 <div class="layui-form-item">
		 
		      <div class="layui-inline" style="top:9px;">
			      <label class="layui-form-label" style="width:150px;">应收款</label>
			      <div class="layui-input-inline">
			        <input type="text"  id="ysk" name="ysk" lay-verify="ysk" autocomplete="off" class="layui-input" style="width:280px;" onblur="checkYsk()" value="${xshtsk.ysk}">
			      </div>
		     </div>
		     
		     <div class="layui-inline" style="top:9px;left: 107px;" id="kpjes">
			      <label class="layui-form-label" style="width:150px;">开票金额</label>
			      <div class="layui-input-inline">
			        <input type="text" id="kpje" name="kpje" lay-verify="kpje" autocomplete="off" class="layui-input" style="width:280px;" value="${xshtsk.kpje}" onblur="checkKpje()">
			      </div>
		     </div>
		     
		 </div>
		 
		 
			
		 <div class="layui-form-item layui-form-text">
		    <label class="layui-form-label" style="width:150px;">备注</label>
		    <div class="layui-input-block">
		      <textarea placeholder="请输入内容" name="remarks"  lay-verify="remarks" id="remarks" class="layui-textarea" style="width:64.2%">${xshtsk.remarks}</textarea>
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
layui.use(['form', 'layedit', 'laydate','upload','element'], function(){
  var form = layui.form
  ,layer = layui.layer
  ,layedit = layui.layedit
  ,laydate = layui.laydate
  var url=$('#url').val();
  var upload = layui.upload;
  var element = layui.element;
  pageLoadfpkj(form);
  form.render();

  //日期
  laydate.render({
    elem: '#sqkprq'
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
			$('#kpjes').show();
		}else{
			$('#kpje').val("");
			$('#kpjes').hide();
		}
  });


	//自定义验证规则
	  form.verify({
		  sqkprq: function(value){
		      if(value==""||value==null){
		        return '申请开票日期不能为空';
		      }
		  }
	      ,ysk:function(value){
		       if(value==""||value==null){
		           return '应收款不能为空';
		       }
	      }
	      ,kpje:function(value){
		       if(value==""||value==null){
		           return '开票金额不能为空';
		         }
	      }
	  });
		
	  fjPageLoad();
});

//验证应收款
function checkYsk(){
	//获取剩余收款金额
	var syskje=$('#syskje').val()*1;
	//获取应收款
	var ysk=$('#ysk').val()*1;
	if(ysk>syskje){
		$('#ysk').val("");
		return layer.alert("应收款金额不得大于剩余收款金额",{icon:7});
	}
}

//验证开票金额
function checkKpje(){
	//获取剩余开票金额
	var sykpje=$('#sykpje').val()*1;
	//获取开票金额
	var kpje=$('#kpje').val()*1;
	if(kpje>sykpje){
		$('#kpje').val("");
		return layer.alert("开票金额不得大于剩余开票金额",{icon:7});
	}
}

	//点击销售合同名称跳转至销售合同查看页
	$('#_field_xiaoShouHTMC').click(function(){
		 var url=$('#url').val();
		 var sales_Contract_Id=$('#sales_Contract_Id').val();
		 parent.layer.open({
		  	  	type:2,
		  	  	title:'查看合同',
		  	  	area: ['100%','100%'],
		  		shadeClose: false,
		  		resize:false,
		  	    anim: 1,
		  	  	content:[url+"sales/salesShow.do?sales_Contract_Id="+sales_Contract_Id,'yes']
		});
	});

	function refreshAndClose(){
		var flag=$('#flag').val();
		if(flag){
			window.parent.location.reload();
			window.close();
		} 
	}

	function pageLoadfpkj(form){
		var sfkpkj=$('#sfkpkj').val();
		var is_fpkj=$('input[name="is_fpkj"]');
		if(sfkpkj=="true"){
			is_fpkj[0].checked=true;
			$('#kpjes').show();
			form.render()
		}else{
			is_fpkj[1].checked=true;
			$('#kpje').val("");
			$('#kpjes').hide();
			form.render()
		}
		
	}



	function  fjPageLoad(){
		var row_Id=$('#xshtskdm').val();
		var demoListView = $('#demoList');
		$.ajax({
			type : "post",
			url : "<c:url value='/xshtsk/pageLoadFj.do'/>",
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
		var row_Id=$('#xshtskdm').val();
		//获得当前表格中的文件名
		var fileName=demoListView[0].rows[index-1].cells[0].innerText;
		layer.confirm('您确定要删除该附件么？', {
			  btn: ['确定','取消'], //按钮
			  title:'提示',icon:7},function(){
				  $.ajax({
						type : "post",
						url : "<c:url value='/xshtsk/removeFj.do'/>",
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