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
	function reloadtree()
	{
		//$('#tree').tree('loadData',[{"id":"1","text":"Root","iconCls":null,"url":null,"state":"closed","attributes":{"type":"system","isload":"false","path":"/","parentId":null}}]);
		$.get('/orgroot',function(data)
			{
				$('#tree').tree({
					data: data
				});
			}
		)
	}
	function addsuborg()
	{
		var frmdata = $('#frm').serialize();
		$.ajax(
			{
				url:'/orgsubsave',
				data:frmdata,
				success:function(data){
					if(data == true)
					{
						reloadtree();
					}
				}
		})
	}
	$(document).ready(function()
		{
			reloadtree();
			$('#tree').tree({
				onBeforeExpand:function(node,param)
				{
					if(node.attributes.isload=='false')
					{
						$.ajaxSettings.async = false;
						var url = '/orgchild?id='+node.id;
						$.get(url,function(data)
							{
								$('#tree').tree('append', {
									parent: node.target,
									data: data
								});
							}
						)
						$.ajaxSettings.async = true;
						node.attributes.isload = 'true';
					}
				},
				onExpand: function(node){

				},
				onClick: function(node){
					$('#id').val(node.id);
					$('#name').textbox('setValue',node.text);
					$('#priority').textbox('setValue',node.attributes.priority);
					$('#parentId').val(node.attributes.parentId);
				}
			})
		}
	);
</script>

</head>
<body class="easyui-layout">
	<div data-options="region:'west',title:'部门导航'" style="width:200px;padding:0px;">
		<ul id="tree" class="easyui-tree" data-options="lines:true">   
		</ul> 
	</div>
	<div data-options="region:'center',title:'部门管理'">
		<form id="frm" method="post">
			<input type="hidden" id="id" name="id"/>
			<input type="hidden" id="parentId" name="parentId"/>
		    <div style="margin-left:50px;margin-top:30px">   
		        <input class="easyui-textbox" type="text" id="name" name="name" data-options="label:'部门名称:'" />   
		    </div>   
		    <div style="margin-left:50px;margin-top:30px"> 
		        <input class="easyui-textbox" type="text" id="priority" name="priority" data-options="label:'部门优先级:'" />   
		    </div>

  		    <div style="margin-left:50px;margin-top:30px"> 
				<a id="btn" onclick="addsuborg()" class="easyui-linkbutton" data-options="iconCls:'icon-search'">添加子部门</a>  
				<a id="btn" onclick="updatesuborg()" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'">修改部门</a>  
				<a id="btn" onclick="deletesuborg()" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'">删除部门</a>  
		    </div>
		</form>
	</div>
</body>
</html>
