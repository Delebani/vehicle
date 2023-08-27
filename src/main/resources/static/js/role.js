$(function () {

    layui.use(['form', 'table'], function() {
        var $ = layui.jquery,//使用了jquery
            form = layui.form,
            roleform = layui.form,
            menuform = layui.form,
            table = layui.table,
            tree = layui.tree,
            saveIndex = 0,
            roleIndex = 0
            ;
        $.get('/menu/tree',function (res) {
            console.log(res)
            if(0 == res.code) {
                var menuList = res.data;
                if(null != menuList && menuList.length > 0){
                    var menutree_data  = [];
                    for (var i = 0; i < menuList.length; i++) {
                        var children =  menuList[i].children;
                        var children_tree = [];
                        if(null != children && children.length > 0){
                            for (var j = 0; j < children.length; j++) {
                                children_tree.push({title:children[j].menuName,id:children[j].id,field:'menu'})
                            }
                        }
                        menutree_data.push({title:menuList[i].menuName,id:menuList[i].id,field:'menu',children:children_tree,spread: true})
                    }
                    tree.render({
                        elem: '#menutree'  //绑定元素
                        ,id: 'menutreeid'
                        ,showCheckbox: true
                        ,data: menutree_data
                    });
                }
            }
        });

        //执行渲染
        table.render({
            elem: '#role',
            url: '../role/page',//后台接口地址
            request: {
                pageName: 'current' //页码的参数名称，默认：page
                ,limitName: 'pageSize' //每页数据量的参数名，默认：limit
            },
            method:'post',
            where:{'roleName':''},
            contentType: 'application/json',
            done: function (res) {
                handleRes(res);
            },
            parseData: function(res){ //res 即为原始返回的数据
                handleRes(res);

                return {
                    "code": res.code, //解析接口状态
                    "msg": res.message, //解析提示文本
                    "count": res.data.total, //解析数据长度
                    "data": res.data.records //解析数据列表
                };
            },
            toolbar: '#toolbar',//表头模板id
            cols: [[
                {field: 'id', title: 'ID', width:80, sort: true, fixed: 'left', align: 'center'}
                ,{field: 'roleName', title: '角色名称', width:130, align: 'center'}
                ,{field: 'creator', title: '创建人', width: 110,hide:true, align: 'center'}
                ,{field: 'createTime', title: '创建时间', width: 120, hide:true, align: 'center'}
                ,{field: 'updater', title: '更新人', width: 80, hide:true, align: 'center'}
                ,{field: 'updateTime', title: '更新时间', width: 100, hide:true, align: 'center'}
                ,{ title: '操作', minWidth: 200, toolbar: '#currentTableBar', align: "center" }//toolbar使用了一个模板
            ]],
            limits: [10, 15, 20, 25, 50, 100],//分页
            limit: 10,
            page: true,
            skin: 'line',//皮肤
            even: true,
            size: 'lg'
        });
        form.on('submit(data-search-btn)', function (data) {
            var searchdata = data.field;//将数据进行json话发送给后台

            //执行搜索重载
            table.reload('role', {
                page: {
                    current: 1
                }
                , where: {
                    'roleName':searchdata.roleName,
                }
            }, 'data');

            return false;
        });

        //工具条事件
        table.on('tool(role)', function(obj){ //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
            var data = obj.data; //获得当前行数据
            var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
            var tr = obj.tr; //获得当前行 tr 的 DOM 对象（如果有的话）

            if(layEvent === 'detail'){ //查看
                form.val("roleview", {
                    "id":data.id
                    ,"roleName": data.roleName

                });
                layui.$('#savebtn').hide();
                layer.open({
                    type: 1,
                    title:'查看角色',
                    area: ['900px', '200px'],
                    offset: '100px',
                    content: $('#roleview') //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                    ,cancel: function(index, layero){
                            layer.close(index);
                            $('#roleview')[0].reset();
                    },
                });
            } else if(layEvent === 'del'){ //删除
                layer.confirm('真的删除吗', function(index){
                    // 向服务端发送删除指令
                    $.get('/role/del?id='+data.id,function (res) {
                        if(0 == res.code) {
                            layer.msg('删除成功');
                            obj.del(); //删除对应行（tr）的DOM结构，并更新缓存
                            layer.close(index);
                        }
                    })
                });
            } else if(layEvent === 'edit'){ //编辑
                form.val("rolefrom", {
                    "id":data.id
                    ,"roleName": data.roleName

                });
                layui.$('#savebtn').show();
                saveIndex = layer.open({
                    type: 1,
                    title:'编辑角色',
                    area: ['900px', '200px'],
                    offset: '100px',
                    content: $('#rolefrom') //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                    ,cancel: function(index, layero){
                        layer.close(index);
                        $('#rolefrom')[0].reset();
                    },
                });

                //同步更新缓存对应的值
                // obj.update({
                //     username: '123'
                //     ,title: 'xxx'
                // });
            } else if(layEvent === 'menu'){
                console.log(data.menuIdList)
                tree.setChecked('menutreeid', data.menuIdList);
                form.val("menufrom", {
                    "id":data.id
                })
                // 重新渲染复选框
                form.render('checkbox');
                roleIndex = layer.open({
                    type: 1,
                    title:'角色菜单授权',
                    area: ['900px', '500px'],
                    offset: '100px',
                    content: $('#menufrom') //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                    // ,success: function(layero, index){
                    //     console.log(layero, index);
                    // }
                    ,cancel: function(index, layero){
                        layer.close(index);
                        $('#menufrom')[0].reset();
                    },

                });
            }
        });

        //头部工具栏事件
        table.on('toolbar(role)', function(obj){
            var checkStatus = table.checkStatus(obj.config.id);
            switch(obj.event){
                case 'add':
                    saveIndex = layer.open({
                        type: 1,
                        title:'添加角色',
                        area: ['900px', '200px'],
                        offset: '100px',
                        content: $('#rolefrom') //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                        ,cancel: function(index, layero){
                            layer.close(index);
                            $('#rolefrom')[0].reset();
                        },
                    });
                    layui.$('#savebtn').show();
                    break;
                case 'del':
                    layer.msg('删除成功');
                    break;
                case 'update':
                    layer.msg('编辑');
                    break;
            };
        });

        // 新增
        roleform.on('submit(savebtn)', function (data) {
            var savedata = data.field;//将数据进行json话发送给后台
            // console.log(savedata)
            $.ajax({
                type: "POST",
                contentType: "application/json; charset=utf-8",
                url: '/role/save_or_update',
                data: JSON.stringify(savedata),
                dataType: 'json',
                success: function (res) {
                    console.log(res)
                    if(0 == res.code){
                        $('#rolefrom')[0].reset();
                        layer.msg("保存成功");
                        //执行搜索重载
                        table.reload('role', {
                            page: {
                                current: 1
                            }
                            , where: {
                            }
                        }, 'data');
                        layer.close(saveIndex);
                    }
                },
                error: function (e) {
                    console.log(e);
                }
            })

            return false;
        });


        menuform.on('submit(menubtn)', function (data) {
            var checkData = tree.getChecked('menutreeid');

            //将页面全部复选框选中的值拼接到一个数组中
            var menuid_arr = [];
            $('input[type=checkbox]:checked').each(function() {
                menuid_arr.push($(this).val());
            });
            var savejson = {'id': data.field.id,'menuIdList':menuid_arr}
            console.log(savejson);
            $.ajax({
                type: "POST",
                contentType: "application/json; charset=utf-8",
                url: '/role/save_menu',
                data: JSON.stringify(savejson),
                dataType: 'json',
                success: function (res) {
                    console.log(res)
                    if(0 == res.code){
                        $('#menufrom')[0].reset();
                        layer.msg("保存成功");
                        layer.close(roleIndex);
                        //执行搜索重载
                        table.reload('role', {
                            page: {
                                current: 1
                            }
                            , where: {
                            }
                        }, 'data');
                        layer.close(saveIndex);
                    }
                },
                error: function (e) {
                    console.log(e);
                }
            })

            return false;
        });
    })
})
