$(function () {

    layui.config({
        base: '../layui/',   // 第三方模块所在目录
        version: 'v1.6.4' // 插件版本号
    }).extend({
        soulTable: 'soulTable/soulTable.slim'
    });

    layui.use(['form', 'table', 'soulTable'], function () {
        var $ = layui.jquery,//使用了jquery
            form = layui.form,
            table = layui.table,
            soulTable = layui.soulTable
        ;


        table.render({
            elem: '#user'
            , url: '../driver/all'//后台接口地址
            , method: 'get'
            , toolbar: true
            , page: false
            , parseData: function (res) { //res 即为原始返回的数据
                handleRes(res);

                return {
                    "code": res.code, //解析接口状态
                    "msg": res.message, //解析提示文本
                    // "count": res.data.total, //解析数据长度
                    "data": res.data //解析数据列表
                };
            }
            , rowDrag: {
                done: function (obj) {
                    // 完成时（松开时）触发
                    // 如果拖动前和拖动后无变化，则不会触发此方法
                    // console.log(obj.row) // 当前行数据
                    // console.log(obj.cache) // 改动后全表数据
                    // console.log(obj.oldIndex) // 原来的数据索引
                    // console.log(obj.newIndex) // 改动后数据索引
                }
            }
            , toolbar: '#toolbar'
            , even: true
            , size: 'lg'
            , skin: 'line'//皮肤
            , cols: [[
                {type: 'numbers', title: '序号', fixed: 'left'}
                , {field: 'name', title: '姓名', width: 200, align: 'center'}
                , {field: 'mobile', title: '手机号', width: 200, align: 'center'}
                , {field: 'idNo', title: '身份证', width: 200, align: 'center'}
                , {field: 'department', title: '部门', width: 200, align: 'center'}
                , {field: 'duty', title: '职务', width: 200, align: 'center'}
                , {field: 'post', title: '岗位', minwidth: 200, align: 'center', fixed: 'right'}

            ]]
            , done: function () {
                soulTable.render(this)
            }
        })

        //头部工具栏事件
        table.on('toolbar(user)', function (obj) {
            switch (obj.event) {
                case 'save':
                    var data = table.getData('user');
                    var savejson = [];
                    for (var i = 0; i < data.length; i++) {
                        savejson.push({'id': data[i].id, 'userSort': i});
                    }
                    $.ajax({
                        type: "POST",
                        contentType: "application/json; charset=utf-8",
                        url: '/driver/sort',
                        data: JSON.stringify(savejson),
                        dataType: 'json',
                        success: function (res) {
                            if (0 == res.code) {
                                layer.msg('保存成功');
                            }
                        },
                        error: function (e) {
                            console.log(e);
                        }
                    })
                    break;
                case 'refresh':
                    table.reload('user', {
                    }, 'data');
                    layer.msg('刷新成功');
                    break;
                case 'del':
                    layer.msg('删除成功');
                    break;
                case 'update':
                    layer.msg('编辑');
                    break;
            }
            ;
        });
    })
})