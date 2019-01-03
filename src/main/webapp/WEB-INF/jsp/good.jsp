<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title></title>
    <link rel="stylesheet" href="/easyui/themes/icon.css">
    <link rel="stylesheet" href="/easyui/themes/default/easyui.css">
    <script type="text/javascript" src="/easyui/jquery.min.js"></script>
    <script type="text/javascript" src="/easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="/easyui/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript">
        function showbuts(value, row, index) {
            btn = '<input type="button" value="修改" onclick="doupdate(' + index + ')"/>'
            return btn
        }
        function doupdate(index) {
            $('#showchkbox').empty()
            $('#win').window('open')
            var rows = $('#dg').datagrid('getRows')
            obj = rows[index];
            $('#id').val(obj.id)
            $('#gname').textbox('setValue', obj.gname)
            $('#brand').textbox('setValue', obj.brand)
            $('#size').textbox('setValue', obj.size)
            $('#update').datebox('setValue', obj.update)
            tids = obj.tids;
            alert(tids)
            showCheckbox(tids)
        }

        function showCheckbox(tids) {
            $.ajax({
                url: '/loadtype',
                success: function (result) {
                    for (var i = 0; i < result.length; i++) {
                        if (tids != null && '' != tids) {
                            if (tids.indexOf(result[i].tid) >= 0) {
                                $('#showchkbox').append('<input type="checkbox"  name="tids" value="' + result[i].tid + '" checked>'
                                    + result[i].tname)

                            } else {
                                $('#showchkbox').append('<input type="checkbox"  name="tids" value="' + result[i].tid + '" >'
                                    + result[i].tname)
                            }
                        } else {
                            $('#showchkbox').append('<input type="checkbox"  name="tids" value="' + result[i].tid + '" >'
                                + result[i].tname)
                        }
                    }
                }
            })
        }
        function tosave() {
            $('#ff').form('clear')
            $('#win').window('open')
            $('#showchkbox').empty()
            showCheckbox('')
        }
        function dodelete() {
            var rows = $('#dg').datagrid('getChecked')
            ids = ''
            for (var i = 0; i < rows.length; i++) {
                ids = ids + rows[i].id + ","
            }
            $.ajax({
                url: '/deleteall?ids=' + ids,
                success: function (result) {
                    alert(result)
                    $('#dg').datagrid('reload')
                }
            })
        }
        function dosave() {
            alert($('#ff').serialize())
            $.ajax({
                url: '/savegood',
                data: $('#ff').serialize(),
                success: function (result) {
                    if (result == true) {
                        $('#dg').datagrid('reload')
                        $('#win').window('close')
                    }
                }
            })
        }
        function doclose() {
            $('#win').window('close')
        }
        $(function () {
            $('#win').window('close')
        })
    </script>
</head>
<body class="easyui-layout">
<div data-options="region:'center'" style="padding:5px;background:#eee;">
    <table id="dg" class="easyui-datagrid" style="width:100%;height:450px"
           data-options="url:'/loadgood',fitColumns:true,singleSelect:false,toolbar: '#tb'">
        <thead>
        <tr>
            <th data-options="field:'id',width:100,checkbox:true">编号</th>
            <th data-options="field:'gname',width:100">名称</th>
            <th data-options="field:'brand',width:100,align:'right'">品牌</th>
            <th data-options="field:'size',width:100,align:'right'">尺寸</th>
            <th data-options="field:'tnames',width:100,align:'right'">类型</th>
            <th data-options="field:'update',width:100,align:'right'">上架日期</th>
            <th data-options="field:'cz',width:100,align:'right',formatter:showbuts">操作</th>
        </tr>
        </thead>
    </table>
</div>
<div id="tb">
    <a href="#" onclick="tosave()" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true">添加</a>
    <a href="#" onclick="dodelete()" class="easyui-linkbutton" data-options="iconCls:'icon-help',plain:true">全部删除</a>
</div>

<div id="win" class="easyui-window" title="My Window" style="width:600px;height:400px"
     data-options="iconCls:'icon-save',modal:true">
    <div class="easyui-layout" data-options="fit:true">
        <form id="ff" action="">
            <div data-options="region:'center'" style="padding-left: 100px">
                <input type="hidden" id="id" name="id">
                <br/><input id="gname" name="gname" class="easyui-textbox"
                            data-options="label: '名称:'" style="width:300px"><br/>
                <br/><input id="brand" name="brand" class="easyui-textbox"
                            data-options="label: '品牌:'" style="width:300px"><br/>
                <br/><input id="size" name="size" class="easyui-textbox"
                            data-options="label: '尺寸:'" style="width:300px"><br/>
                <br/><input id="update" name="update" type="text" data-options="label: '上架日期:'" class="easyui-datebox"
                            required="required"><br/>
                <br/>平台：
                <div id="showchkbox"></div>
                <br/><a href="#" onclick="dosave()" class="easyui-linkbutton"
                        data-options="iconCls:'icon-save',plain:true">确认</a>
                &nbsp;&nbsp;&nbsp;<a href="#" onclick="doclose()" class="easyui-linkbutton"
                                     data-options="iconCls:'icon-remove',plain:true">取消</a>
            </div>
        </form>
    </div>
</div>
</body>
</html>