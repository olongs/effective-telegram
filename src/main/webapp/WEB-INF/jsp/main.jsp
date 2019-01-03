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
function logout()
{
	window.open("/",'_self');
}

$(function(){
	$("#leftAccordion").accordion({
		fillSpace:true,
		fit:true,
		border:false,
		animate:false  
	});
	$.post("<%=webCtx%>/gettree", { "id": "0" },
		function (data) {
			$.each(data, function (i, e) {
				var id = e.id;
				$('#leftAccordion').accordion('add', {
				    title: e.text,
				    content: "<ul id='tree"+id+"' ></ul>",
				    selected: true,
				    iconCls:e.iconCls
				});
				$.parser.parse();
				$.post("<%=webCtx%>/gettreebyid?id="+id,  function(data) 
				{
					$("#tree" + id).tree(
						{
							data: data,
							onBeforeExpand:function(node,param)
							{
								if(node.attributes.isload=='false')
								{
									$.ajaxSettings.async = false;
									$.get("<%=webCtx%>/gettreebyid?id="+node.id,function(rtn){
				    					$("#tree" + id).tree("append", {
					    					parent: node.target,
					    					data: rtn});
				    					node.attributes.isload = 'true';
									})
									 $.ajaxSettings.async = true;
								}
							},  
							onClick : function(node)
							{
								if (node.state == 'closed'){  
									$(this).tree('expand', node.target);
								}else if (node.state == 'open'){  
									//$(this).tree('collapse', node.target);
									var tabTitle = node.text;
									var url = "<%=webCtx%>" + node.url;
									var icon = node.iconCls;
									addTabCheckExist(tabTitle, url, icon);
								}else{
									var tabTitle = node.text;
									var url = "<%=webCtx%>" + node.url;
									var icon = node.iconCls;
									addTabCheckExist(tabTitle, url, icon);
								}
						}
					});//end tree
			},'json');//end post gettreebyid
		});
	}, "json");//end gettree
});
//当点击导航树的节点时，在办公区域显示页面
//此方法检查是否存在打开页面
var h = $(window).height() - 125;
function addTabCheckExist(title, url,icon){
	if ($('#tabui').tabs('exists', title)){
		$('#tabui').tabs('select', title);
	} else {
		var content = '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:'+h+'px;"></iframe>';
		$('#tabui').tabs('add',{
			title:title,
			content:content,
			closable:true,
			iconCls:icon
		});
	}
}
</script>

</head>
<body class="easyui-layout">
	<!-- 顶部区域 -->
	<div data-options="region:'north',border:false" style="height:80px;background:#B3DFDA;padding:0px 0px">
		<!-- 定义面板 -->
		<div class="easyui-panel" style="height:50px;padding-bottom:0px;background:#B3DFDA;" data-options="border:false">
			<!-- 显示Logo Div -->
			<div style="width:38%;float:left;margin-left:0%">
				<img height="50px" src="<%=webCtx %>/images/logo.png"/>
			</div>
			<!-- 显示系统名称 Div -->
			<div style="width:60%;float:right;margin:0;padding:0">
				<p style="font-size:18pt;font-weight:bold;color:black;margin:0;padding:0">项目快速开发框架原型</font></p>
			</div>
			<!-- 显示登录用户 Div -->
			<div id="userdiv" style="position:absolute;bottom:30px;right:20px;">
				<font color="black"><b>当前用户:${user.name}</b></font>
			</div>
		</div>
		<!-- 根据后台动态生成功能菜单 -->
	<!-- 根据后台动态生成功能菜单 -->
	<div class="easyui-panel" style="padding-bottom:0px;background:#B3DFDA;">
		<a class="easyui-menubutton" data-options="menu:'#0'">系统管理</a>
		<a class="easyui-menubutton" data-options="menu:'#1'">微信管理</a>
		<a class="easyui-menubutton" data-options="menu:'#2'">网上办公</a>
		<a class="easyui-menubutton" data-options="menu:'#3'">财务管理</a>
		<a class="easyui-linkbutton" data-options="plain:true" onclick="javascript:logout()">退出系统</a>
	</div>
	<div id="0" style="width:150px;">
		<div>
			<span>组织机构</span>
			<div>
				<div onclick="javascript:addTab('011','部门管理','?jsessionid=80449683B37933FF74772662A5DDFBAA')">部门管理</div>
				<div onclick="javascript:addTab('012','用户管理','?jsessionid=80449683B37933FF74772662A5DDFBAA')">用户管理</div>
				<div onclick="javascript:addTab('013','角色管理','?jsessionid=80449683B37933FF74772662A5DDFBAA')">角色管理</div>
			</div>
		</div>
	</div>
	<div id="1" style="width:150px;">
		<div onclick="javascript:addTab('11','实体店','/wxshop?jsessionid=80449683B37933FF74772662A5DDFBAA')">实体店</div>
		<div onclick="javascript:addTab('12','活动管理','/wxact?jsessionid=80449683B37933FF74772662A5DDFBAA')">活动管理</div>
	</div>
	<div id="2" style="width:150px;">
	</div>
	<div id="3" style="width:150px;">
	</div>

	</div>
	
	<!-- 定义左侧导航部分 -->
	<div data-options="region:'west',split:true" style="width:200px;padding:0px;">
		<!-- 定义树状控件 -->
		<!--
		<div class="easyui-panel" style="padding:0px" data-options="border:false">
			<ul id="tt" class="easyui-tree" style="width:100%" data-options="url:'${pageContext.request.contextPath}/tree_data1.json',method:'get'"></ul>
		</div>
		-->
		<div id="leftAccordion" class="easyui-accordion"></div>
	</div>
	<!-- 定义右侧办公区域 -->
	<div data-options="region:'center',title:''" style="overflow:hidden">
		<div id="tabui" class="easyui-tabs" style="width:100%;height:100%;margin:0px;margin-right:0px;">
		</div>
	</div>

</body>
</html>
