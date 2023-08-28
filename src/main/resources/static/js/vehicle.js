$(function () {

    layui.use(['form', 'table'], function() {
        var $ = layui.jquery,//使用了jquery
            form = layui.form,
            vehicleform = layui.form,
            table = layui.table,
            tree = layui.tree,
            saveIndex = 0,
            vehicleIndex = 0,
            plateNoReg ="^([京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[a-zA-Z](([DF]((?![IO])[a-zA-Z0-9](?![IO]))[0-9]{4})|([0-9]{5}[DF]))|[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[A-Z0-9]{4}[A-Z0-9挂学警港澳]{1})$";

        form.verify({
            plateNo: function (value, item) { //value：表单的值、item：表单的DOM对象
                if (!new RegExp(plateNoReg).test(value)) {
                    return '车牌号格式不正确，请重新填写';
                }
            }
        })

        $.get('/vehicle_type/all',function (res) {
            if(0 == res.code) {
                var typeList = res.data;
                $('#vehicleTypeId4searchselect').append('<option value="">全部</option>');
                for (var i = 0; i < typeList.length; i++) {
                    $('#vehicleTypeId4searchselect').append('<option value="'+ typeList[i].id +'">'+ typeList[i].typeName +'</option>');
                    $('#vehicleTypeId4addselect').append('<option value="'+ typeList[i].id +'">'+ typeList[i].typeName +'</option>');
                }
                form.render('select'); // 重新渲染 select 元素
            }
        });

        //执行渲染
        table.render({
            elem: '#vehicle',
            url: '../vehicle/page',//后台接口地址
            request: {
                pageName: 'current' //页码的参数名称，默认：page
                ,limitName: 'pageSize' //每页数据量的参数名，默认：limit
            },
            method:'post',
            where:{'plateNo':'','vehicleTypeId':'','state':''},
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
                ,{field: 'plateNo', title: '车牌号', width:130, align: 'center'}
                ,{field: 'vehicleTypeName', title: '车辆类型', width:130, align: 'center'}
                ,{field: 'stateName', title: '出车状态', width:130, align: 'center'}
                ,{field: 'creator', title: '创建人', width: 110,hide:true, align: 'center'}
                ,{field: 'createTime', title: '创建时间', width: 180, hide:true, align: 'center'}
                ,{field: 'updater', title: '更新人', width: 80, hide:true, align: 'center'}
                ,{field: 'updateTime', title: '更新时间', width: 180, hide:true, align: 'center'}
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
            console.log(searchdata);
            //执行搜索重载
            table.reload('vehicle', {
                page: {
                    current: 1
                }
                , where: {
                    'plateNo':searchdata.plateNo,
                    'vehicleTypeId':searchdata.vehicleTypeId,
                    'state':searchdata.state,
                }
            }, 'data');

            return false;
        });

        //工具条事件
        table.on('tool(vehicle)', function(obj){ //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
            var data = obj.data; //获得当前行数据
            var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
            var tr = obj.tr; //获得当前行 tr 的 DOM 对象（如果有的话）

            if(layEvent === 'detail'){ //查看
                form.val("vehicleview", {
                    "id":data.id,
                    'plateNo':data.plateNo,
                    'vehicleTypeName':data.vehicleTypeName,
                    'stateName':data.stateName,

                });
                layui.$('#savebtn').hide();
                layer.open({
                    type: 1,
                    title:'查看角色',
                    area: ['900px', '300px'],
                    offset: '100px',
                    content: $('#vehicleview') //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                    ,cancel: function(index, layero){
                            layer.close(index);
                            $('#vehicleview')[0].reset();
                    },
                });
            } else if(layEvent === 'del'){ //删除
                layer.confirm('真的删除吗', function(index){
                    // 向服务端发送删除指令
                    $.get('/vehicle/del?id='+data.id,function (res) {
                        if(0 == res.code) {
                            layer.msg('删除成功');
                            obj.del(); //删除对应行（tr）的DOM结构，并更新缓存
                            layer.close(index);
                        }
                    })
                });
            } else if(layEvent === 'edit'){ //编辑
                form.val("vehiclefrom", {
                    "id":data.id,
                    'plateNo':data.plateNo,
                    'vehicleTypeId':data.vehicleTypeId,
                    'state':data.state,

                });
                layui.$('#savebtn').show();
                saveIndex = layer.open({
                    type: 1,
                    title:'编辑角色',
                    area: ['900px', '500px'],
                    offset: '100px',
                    content: $('#vehiclefrom') //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                    ,cancel: function(index, layero){
                        layer.close(index);
                        $('#vehiclefrom')[0].reset();
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
        table.on('toolbar(vehicle)', function(obj){
            var checkStatus = table.checkStatus(obj.config.id);
            switch(obj.event){
                case 'add':
                    saveIndex = layer.open({
                        type: 1,
                        title:'添加角色',
                        area: ['900px', '500px'],
                        offset: '100px',
                        content: $('#vehiclefrom') //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                        ,cancel: function(index, layero){
                            layer.close(index);
                            $('#vehiclefrom')[0].reset();
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
        vehicleform.on('submit(savebtn)', function (data) {
            var savedata = data.field;//将数据进行json话发送给后台
            // console.log(savedata)
            $.ajax({
                type: "POST",
                contentType: "application/json; charset=utf-8",
                url: '/vehicle/save_or_update',
                data: JSON.stringify(savedata),
                dataType: 'json',
                success: function (res) {
                    console.log(res)
                    if(0 == res.code){
                        $('#vehiclefrom')[0].reset();
                        layer.msg("保存成功");
                        //执行搜索重载
                        table.reload('vehicle', {
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
