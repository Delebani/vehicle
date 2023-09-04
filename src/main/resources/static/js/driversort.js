$(function () {

    layui.config({
        base: '../layui/',   // 第三方模块所在目录
        version: 'v1.6.4' // 插件版本号
    }).extend({
        soulTable: 'soulTable/soulTable.slim'
    });

    layui.use(['form', 'table', 'soulTable'], function() {
        var $ = layui.jquery,//使用了jquery
            form = layui.form,
            table = layui.table,
            soulTable = layui.soulTable
            ;



        table.render({
            elem: '#user'
            , url: '../user/page'//后台接口地址
            , where:{}
            , method:'post'
            , contentType: 'application/json'
            , toolbar: true
            , page: false
            ,parseData: function(res){ //res 即为原始返回的数据
                handleRes(res);

                return {
                    "code": res.code, //解析接口状态
                    "msg": res.message, //解析提示文本
                    "count": res.data.total, //解析数据长度
                    "data": res.data.records //解析数据列表
                };
            }
            , rowDrag: {/*trigger: 'row',*/ done: function (obj) {
                    // 完成时（松开时）触发
                    // 如果拖动前和拖动后无变化，则不会触发此方法
                    console.log(obj.row) // 当前行数据
                    console.log(obj.cache) // 改动后全表数据
                    console.log(obj.oldIndex) // 原来的数据索引
                    console.log(obj.newIndex) // 改动后数据索引
                }
            }
            ,toolbar: '#toolbar'
            , even: true
            , size: 'lg'
            , skin: 'line'//皮肤
            , cols: [[
                {type: 'numbers', title: '序号', fixed: 'left'},
                ,{field: 'name', title: '姓名', width:130, align: 'center'}
                ,{field: 'mobile', title: '手机号', minWidth: 130, align: 'center'}
            ]]
            , done: function () {
                soulTable.render(this)
            }
        })
        //执行渲染
        // table.render({
        //     elem: '#user',
        //     url: '../user/page',//后台接口地址
        //     request: {
        //         pageName: 'current' //页码的参数名称，默认：page
        //         ,limitName: 'pageSize' //每页数据量的参数名，默认：limit
        //     },
        //     method:'post',
        //     where:{'name':'','mobile':''},
        //     contentType: 'application/json',
        //     done: function (res) {
        //         handleRes(res);
        //     },
        //     parseData: function(res){ //res 即为原始返回的数据
        //         handleRes(res);
        //
        //         return {
        //             "code": res.code, //解析接口状态
        //             "msg": res.message, //解析提示文本
        //             "count": res.data.total, //解析数据长度
        //             "data": res.data.records //解析数据列表
        //         };
        //     },
        //     toolbar: '#toolbar',//表头模板id
        //     cols: [[
        //         {field: 'id', title: 'ID', width:80, sort: true, fixed: 'left', align: 'center',type:'checkbox'}
        //         ,{field: 'name', title: '姓名', width:130, align: 'center'}
        //         // ,{field: 'type', title: '用户类型', width:110, sort: true, align: 'center'}
        //         // ,{field: 'sex', title: '性别', width:60, align: 'center'}
        //         // ,{field: 'age', title: '年龄', width: 60, align: 'center'}
        //         ,{field: 'mobile', title: '手机号', width: 130, align: 'center'}
        //         // ,{field: 'idNo', title: '身份证号码', width: 180, align: 'center'}
        //         // ,{field: 'department', title: '部门', width: 100, align: 'center'}
        //         // ,{field: 'duty', title: '职务', width: 100, align: 'center'}
        //         // ,{field: 'post', title: '岗位', width: 100, align: 'center'}
        //         // ,{field: 'stateName', title: '账号状态', width: 100, align: 'center'}
        //         // ,{field: 'creator', title: '创建人', width: 110,hide:true, align: 'center'}
        //         // ,{field: 'createTime', title: '创建时间', width: 180, hide:true, align: 'center'}
        //         // ,{field: 'updater', title: '更新人', width: 80, hide:true, align: 'center'}
        //         // ,{field: 'updateTime', title: '更新时间', width: 180, hide:true, align: 'center'}
        //         //,{field: 'roleIdList', title: '角色', width: 0, hide:true}
        //         ,{ title: '操作', minWidth: 200, toolbar: '#currentTableBar', align: "center" }//toolbar使用了一个模板
        //     ]],
        //     // limits: [10, 15, 20, 25, 50, 100],//分页
        //     limit: 1000,
        //     page: true,
        //     skin: 'line',//皮肤
        //     even: true,
        //     size: 'lg'
        // });
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
            } else if(layEvent === 'del'){ //删除
                layer.confirm('真的删除吗', function(index){
                    // 向服务端发送删除指令
                    $.get('/user/del?id='+data.id,function (res) {
                        if(0 == res.code) {
                            layer.msg('删除成功');
                            obj.del(); //删除对应行（tr）的DOM结构，并更新缓存
                            layer.close(index);
                        }
                    })
                });
            }
        });

        //头部工具栏事件
        table.on('toolbar(user)', function(obj){
            var checkStatus = table.checkStatus(obj.config.id)
                ,data = checkStatus.data //获取选中的数据
                ,checkedid_arr = [];
            for (var i = 0; i < data.length; i++) {
                checkedid_arr.push(data[i].id)
            }
            switch(obj.event){
                case 'add':
                    saveIndex = layer.open({
                        type: 1,
                        title:'添加用户',
                        area: ['900px', '600px'],
                        offset: '100px',
                        content: $('#user-from') //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                        ,cancel: function(index, layero){
                            layer.close(index);
                            $('#user-from')[0].reset();
                            layui.$('#head_route').addClass('layui-hide').find('img').attr('src', '');
                        },
                    });
                    layui.$('#headbtn').show();
                    layui.$('#savebtn').show();
                    layui.$('#head_route').addClass('layui-hide').find('img').attr('src', '');
                    break;
                case 'del':
                    layer.msg('删除成功');
                    break;
                case 'update':
                    layer.msg('编辑');
                    break;
            };
        });
    })
})