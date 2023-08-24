$(function () {
    layui.use(['form', 'table'], function() {
        var $ = layui.jquery,//使用了jquery
            form = layui.form,
            table = layui.table;

        //执行渲染
        table.render({
            elem: '#user',
            url: '../user/page',//后台接口地址
            request: {
                pageName: 'current' //页码的参数名称，默认：page
                ,limitName: 'pageSize' //每页数据量的参数名，默认：limit
            },
            method:'post',
            where:{'name':'','mobile':'','type':''},
            contentType: 'application/json',
            headers:{token: localStorage.getItem("token")},
            parseData: function(res){ //res 即为原始返回的数据
                return {
                    "code": res.code, //解析接口状态
                    "msg": res.message, //解析提示文本
                    "count": res.data.total, //解析数据长度
                    "data": res.data.records //解析数据列表
                };
            },
            toolbar: '#toolbar',//表头模板id
            cols: [[
                {field: 'id', title: 'ID', width:80, sort: true, fixed: 'left'}
                ,{field: 'name', title: '姓名', width:130}
                ,{field: 'type', title: '用户类型', width:110, sort: true}
                ,{field: 'sex', title: '性别', width:60}
                ,{field: 'age', title: '年龄', width: 60}
                ,{field: 'mobile', title: '手机号', width: 130, sort: true}
                ,{field: 'idNo', title: '身份证号码', width: 180}
                ,{field: 'department', title: '部门', width: 100, sort: true}
                ,{field: 'duty', title: '职务', width: 100, sort: true}
                ,{field: 'post', title: '岗位', width: 100, sort: true}
                ,{field: 'creator', title: '创建人', width: 110, sort: true,hide:true}
                ,{field: 'createTime', title: '创建时间', width: 120, sort: true,hide:true}
                ,{field: 'updater', title: '更新人', width: 80, sort: true,hide:true}
                ,{field: 'updateTime', title: '更新时间', width: 100, sort: true,hide:true}
                ,{ title: '操作', minWidth: 200, toolbar: '#currentTableBar', align: "center" }//toolbar使用了一个模板
            ]],
            limits: [10, 15, 20, 25, 50, 100],//分页
            limit: 10,
            page: true,
            skin: 'line',//皮肤
            even: true,
            size: 'lg',
        });

        form.on('submit(data-search-btn)', function (data) {
            var searchdata = data.field;//将数据进行json话发送给后台

            //执行搜索重载
            table.reload('user', {
                page: {
                    current: 1
                }
                , where: {
                    'name':searchdata.name,
                    'mobile':searchdata.mobile,
                    'type':searchdata.type,
                }
            }, 'data');

            return false;
        });

        //工具条事件
        table.on('tool(user)', function(obj){ //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
            var data = obj.data; //获得当前行数据
            var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
            var tr = obj.tr; //获得当前行 tr 的 DOM 对象（如果有的话）

            if(layEvent === 'detail'){ //查看
                //do somehing
            } else if(layEvent === 'del'){ //删除
                layer.confirm('真的删除吗', function(index){
                    obj.del(); //删除对应行（tr）的DOM结构，并更新缓存
                    layer.close(index);
                    // 向服务端发送删除指令
                    console.log("向服务端发送删除指令")
                });
            } else if(layEvent === 'edit'){ //编辑
                //do something

                //同步更新缓存对应的值
                obj.update({
                    username: '123'
                    ,title: 'xxx'
                });
            } else if(layEvent === 'LAYTABLE_TIPS'){
                layer.alert('Hi，头部工具栏扩展的右侧图标。');
            }
        });

        //头部工具栏事件
        table.on('toolbar(user)', function(obj){
            var checkStatus = table.checkStatus(obj.config.id);
            switch(obj.event){
                case 'add':
                    layer.msg('添加');
                    break;
                case 'delete':
                    layer.msg('删除');
                    break;
                case 'update':
                    layer.msg('编辑');
                    break;
            };
        });
        table.on('checkbox(user)', function(obj){
            console.log(obj); //当前行的一些常用操作集合
            console.log(obj.checked); //当前是否选中状态
            console.log(obj.data); //选中行的相关数据
            console.log(obj.type); //如果触发的是全选，则为：all，如果触发的是单选，则为：one
        });
    })
})