$(function () {

    layui.use(['form', 'table'], function() {
        var $ = layui.jquery,//使用了jquery
            form = layui.form,
            table = layui.table,
            saveIndex = 0
            ;

        $.get('/menu/parent',function (res) {
            if(0 == res.code) {
                $('#parentIdselect').append('<option value="0" selected>无</option>');
                var parentList = res.data;
                for (var i = 0; i < parentList.length; i++) {
                    $('#parentIdselect').append('<option value="'+ parentList[i].id +'">'+ parentList[i].menuName +'</option>');
                }
                form.render('select'); // 重新渲染 select 元素
            }
        });


        //执行渲染
        table.render({
            elem: '#menu',
            url: '../menu/page',//后台接口地址
            request: {
                pageName: 'current' //页码的参数名称，默认：page
                ,limitName: 'pageSize' //每页数据量的参数名，默认：limit
            },
            method:'post',
            where:{'menuName':''},
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
                ,{field: 'parentName', title: '父级菜单', width:130, align: 'center'}
                ,{field: 'menuName', title: '菜单名称', width:130, align: 'center'}
                ,{field: 'menuUrl', title: '菜单地址', width:130, align: 'center'}
                ,{field: 'menuSort', title: '菜单排序', width:130, align: 'center'}
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
            table.reload('menu', {
                page: {
                    current: 1
                }
                , where: {
                    'menuName':searchdata.menuName,
                }
            }, 'data');

            return false;
        });

        //工具条事件
        table.on('tool(menu)', function(obj){ //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
            var data = obj.data; //获得当前行数据
            var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
            var tr = obj.tr; //获得当前行 tr 的 DOM 对象（如果有的话）

            if(layEvent === 'detail'){ //查看
                form.val("menuview", {
                    "id":data.id
                    ,"parentId":data.parentId
                    ,"parentName":data.parentName
                    ,"menuName": data.menuName
                    ,"menuUrl": data.menuUrl
                    ,"menuSort": data.menuSort
                });
                layui.$('#savebtn').hide();
                layer.open({
                    type: 1,
                    title:'查看菜单',
                    area: ['900px', '300px'],
                    offset: '100px',
                    content: $('#menuview') //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                    ,cancel: function(index, layero){
                            layer.close(index);
                            $('#menuview')[0].reset();
                    },
                });
            } else if(layEvent === 'del'){ //删除
                layer.confirm('真的删除吗', function(index){
                    // 向服务端发送删除指令
                    $.get('/menu/del?id='+data.id,function (res) {
                        if(0 == res.code) {
                            layer.msg('删除成功');
                            obj.del(); //删除对应行（tr）的DOM结构，并更新缓存
                            layer.close(index);
                        }
                    })
                });
            } else if(layEvent === 'edit'){ //编辑
                form.val("menufrom", {
                    "id":data.id
                    ,"parentId":data.parentId
                    ,"parentName":data.parentName
                    ,"menuName": data.menuName
                    ,"menuUrl": data.menuUrl
                    ,"menuSort": data.menuSort

                });
                layui.$('#savebtn').show();
                saveIndex = layer.open({
                    type: 1,
                    title:'编辑菜单',
                    area: ['900px', '300px'],
                    offset: '100px',
                    content: $('#menufrom') //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                    ,cancel: function(index, layero){
                        layer.close(index);
                        $('#menufrom')[0].reset();
                    },
                });

                //同步更新缓存对应的值
                // obj.update({
                //     username: '123'
                //     ,title: 'xxx'
                // });
            }
        });

        //头部工具栏事件
        table.on('toolbar(menu)', function(obj){
            var checkStatus = table.checkStatus(obj.config.id);
            switch(obj.event){
                case 'add':
                    saveIndex = layer.open({
                        type: 1,
                        title:'添加菜单',
                        area: ['900px', '300px'],
                        offset: '100px',
                        content: $('#menufrom') //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                        ,cancel: function(index, layero){
                            layer.close(index);
                            $('#menufrom')[0].reset();
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
        form.on('submit(savebtn)', function (data) {
            var savedata = data.field;//将数据进行json话发送给后台
            // console.log(savedata)
            $.ajax({
                type: "POST",
                contentType: "application/json; charset=utf-8",
                url: '/menu/save_or_update',
                data: JSON.stringify(savedata),
                dataType: 'json',
                success: function (res) {
                    console.log(res)
                    if(0 == res.code){
                        $('#menufrom')[0].reset();
                        layer.msg("保存成功");
                        //执行搜索重载
                        table.reload('menu', {
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
