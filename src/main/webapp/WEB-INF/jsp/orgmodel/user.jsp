<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String webCtx = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<script type="text/javascript" src="<%=webCtx %>/easyui/jquery.min.js"></script>
<script type="text/javascript" src="<%=webCtx %>/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=webCtx %>/easyui/easyui-lang-zh_CN.js"></script>
<link rel="stylesheet" href="<%=webCtx %>/easyui/themes/default/easyui.css"/>
<link rel="stylesheet" href="<%=webCtx %>/easyui/themes/icon.css"/>
<link rel="stylesheet" href="<%=webCtx %>/easyui/myicon.css"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<script>
	function showstatus(cellVal,rowObj,rowNo)
	{
		if(cellVal == '0')
			return '正常';
		if(cellVal == '1')
			return '删除';
		if(cellVal == '2')
			return '锁定';
	}
	function showbtns(cellVal,rowObj,rowNo)
	{
		var btn = '<input type="button" value="查看" onclick="viewuser('+rowNo+')"/>';
		btn = btn + '<input type="button" value="修改" onclick="viewuser('+rowNo+')"/>';
		btn = btn + '<input type="button" value="删除" onclick="dodeleteone('+rowObj.id+')"/>';
		return btn;
	}
	function viewuser(rowno)
	{
		var rows = $('#dg').datagrid('getRows');
		var rowObj = rows[rowno];
		$('#id').val(rowObj.id);
		$('#loginId').textbox('setValue',rowObj.loginId);
		$('#name').textbox('setValue',rowObj.name);
		$('#birthday').datebox('setValue',rowObj.birthday);
		$('#pwd').textbox('setValue',rowObj.pwd);
		$('#telNo').textbox('setValue',rowObj.telNo);
		$('#email').textbox('setValue',rowObj.email);
		$('#status').combobox('setValue',rowObj.status);
		$('#oname').textbox('setValue',rowObj.oname);
		$('#win').window('open');
		
		$('#showimg').empty();
		$('#showimg').append('<img width="50" height="50" src="/downfile?id='+rowObj.imgId+'"/>');
	}
	function closewin()
	{
		$('#win').window('close');
	}
	function savewin()
	{
		var isok = $('#frm').form('validate');
		if(!isok)
		{
			$.messager.alert('警告','数据校验失败'); 
			return;
		}
		$('#frm').form('submit', {    
		    url:'<%=webCtx %>/usersave',    
		    onSubmit: function(){    
  
		    },    
		    success:function(data){    
		    	$('#win').window('close');
		    	$('#dg').datagrid("reload");
		    }    
		});  
	}
	function doquery()
	{
		var qname = $('#qname').val();
		$('#dg').datagrid({
			url:'<%=webCtx%>/userload?qname='+qname
		})
	}
	function doadd()
	{
		$('#id').val('');
		$('#loginId').textbox('setValue','');
		$('#name').textbox('setValue','');
		$('#birthday').datebox('setValue','');
		$('#telNo').textbox('setValue','');
		$('#email').textbox('setValue','');
		$('#status').combobox('setValue',0);
		$('#oname').textbox('setValue','');
		$('#win').window('open');
	}
	function dodelete()
	{
		var rowObj = $('#dg').datagrid('getChecked');
		var ids = '';
		for(var i=0;i<rowObj.length;i++)
		{
			ids = ids + rowObj[i].id+',';
		}
		$.post('<%=webCtx%>/userdel?ids='+ids,'',function(data){
			$('#dg').datagrid("reload");
		});
	}
	function dodeleteone(id)
	{
		$.post('<%=webCtx%>/userdel?ids='+id,'',function(data){
			$('#dg').datagrid("reload");
		});
	}
	$(document).ready(function(){
		$('#win').window('close');
	})
</script>

</head>
<body class="easyui-layout">
<div data-options="region:'center',title:'用户管理'">
	<table id="dg" class="easyui-datagrid" style="width:100%;height:300px"   
	        data-options="url:'<%=webCtx %>/userload',toolbar:tb,fitColumns:true,singleSelect:false,
	        	pagination:true,pageSize:4,pageList:[4,8]">   
	    <thead>   
	        <tr>
				<th data-options="field:'id',width:100,checkbox:true">全选</th>
				<th data-options="field:'loginId',width:100">登录名</th>
				<th data-options="field:'name',width:100">姓名</th>
				<th data-options="field:'birthday',width:100">生日</th>
				<th data-options="field:'telNo',width:100">电话</th>
				<th data-options="field:'email',width:100">邮箱</th>
				<th data-options="field:'pwd',width:100,hidden:true">邮箱</th>
				<th data-options="field:'imgId',width:100">头像Id</th>
				<th data-options="field:'status',width:100,formatter:showstatus">状态</th>
				<th data-options="field:'oname',width:100">所在部门</th>
				<th data-options="field:'cmd',width:100,formatter:showbtns">操作</th>
	        </tr>   
	    </thead>   
	</table>
	<div id="tb" style="padding:2px 5px;">
		<input class="easyui-textbox" id="qname" name="qname" value="" data-options="label:'查询姓名'" style="width:200px">
		<a id="btn" onclick="doquery()" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
		<a id="btn" onclick="doadd()" class="easyui-linkbutton" data-options="iconCls:'icon-add'">添加</a>
		<a id="btn" onclick="dodelete()" class="easyui-linkbutton" data-options="iconCls:'icon-remove'">批量删除</a>  
	</div>
	<div id="win" class="easyui-window" title="用户维护" style="width:600px;height:450px"   
	        data-options="iconCls:'icon-save',modal:true">
		<form id="frm" action="/usersave" method="POST" enctype="multipart/form-data">
			<input type="hidden" id="id" name="id" value=""/>
		    <div style="margin-left:20px;margin-top:20px">
		    	<input class="easyui-textbox" id="loginId" name="loginId" value="" style="width:70%" data-options="required:true,label:'登录名',multiline:false">
		    </div>
		    <div style="margin-left:20px;margin-top:20px">
		    	<input class="easyui-textbox" id="name" name="name" value="" style="width:70%" data-options="label:'姓名',multiline:false,required:true">
		    </div>
		    <div style="margin-left:20px;margin-top:20px">
		    	<input type="password" class="easyui-textbox" id="pwd" name="pwd" value="" style="width:70%" data-options="label:'密码',validType:'password',multiline:false,required:true">
		    </div>
		    <div style="margin-left:20px;margin-top:20px">
		    	<input class="easyui-datebox" id="birthday" name="birthday" value="" style="width:70%" data-options="label:'生日',validType:'password',multiline:false,validType:'date'">
		    </div>
		    <div style="margin-left:20px;margin-top:20px">
		    	<input class="easyui-textbox" id="telNo" name="telNo" value="" style="width:70%" data-options="label:'电话',multiline:false">
		    </div>
		    <div style="margin-left:20px;margin-top:20px">
		    	<input class="easyui-textbox" id="email" name="email" value="" style="width:70%" data-options="label:'邮箱',multiline:false">
		    </div>
		    <div style="margin-left:20px;margin-top:20px">
				<select id="status" name="status" class="easyui-combobox" style="width:200px;" data-options="label:'状态'">   
				    <option value="0">正常</option>   
	 				<option value="1">删除</option>
	 				<option value="2">锁定</option>
				</select> 
		    </div>
		    <div style="margin-left:20px;margin-top:20px">
		    	<input id="upfile" name="upfile" class="easyui-filebox" data-options="label:'选择上传文件'" style="width:300px">
		    </div>
		    <div style="margin-left:20px;margin-top:20px">
		    	<div id="showimg"></div>
		    </div>
		    <div style="margin-left:20px;margin-top:20px">
				<select id="oid" name="oid" class="easyui-combobox" style="width:200px;" 
					url="/orgload" data-options="label:'所在部门',valueField:'id',textField:'name'">   
				</select> 
		    </div>
		    <div style="margin-left:20px;margin-top:20px">
				<a id="btn" onclick="savewin()" class="easyui-linkbutton" data-options="iconCls:'icon-save'">保存</a>
				<a id="btn" onclick="closewin()" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'">关闭</a>
			</div>
		</form>
	</div>
</div>
</body>
</html>
