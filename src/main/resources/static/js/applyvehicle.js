$(function () {

    layui.use(['form', 'table'], function () {
        var $ = layui.jquery,//使用了jquery
            form = layui.form,
            table = layui.table,
            laydate = layui.laydate,
            saveIndex = 0
        ;
        $.get('/vehicle_type/all', function (res) {
            if (0 == res.code) {
                var typeList = res.data;
                $('#vehicleTypeId4searchselect').append('<option value="">全部</option>');
                for (var i = 0; i < typeList.length; i++) {
                    $('#vehicleTypeId4searchselect').append('<option value="' + typeList[i].id + '">' + typeList[i].typeName + '</option>');
                    $('#vehicleTypeId4addselect').append('<option value="' + typeList[i].id + '">' + typeList[i].typeName + '</option>');
                }
                form.render('select'); // 重新渲染 select 元素
            }
        });
        $.get('/apply_reason/all', function (res) {
            if (0 == res.code) {
                var reasonList = res.data;
                for (var i = 0; i < reasonList.length; i++) {
                    $('#applyReason4addselect').append('<option value="' + reasonList[i].id + '">' + reasonList[i].reason + '</option>');
                }
                form.render('select'); // 重新渲染 select 元素
            }
        });

        laydate.render({
            elem: '#startTime'
            ,type: 'datetime'
        });
        laydate.render({
            elem: '#endTime'
            ,type: 'datetime'
        });
        //执行渲染
        table.render({
            elem: '#applyvehicle',
            url: '../apply_vehicle/page',//后台接口地址
            request: {
                pageName: 'current' //页码的参数名称，默认：page
                , limitName: 'pageSize' //每页数据量的参数名，默认：limit
            },
            method: 'post',
            where: {'applyUserName': '', 'plateNo': '', 'vehicleTypeId': '', 'approveState': ''},
            contentType: 'application/json',
            done: function (res) {
                handleRes(res);
            },
            parseData: function (res) { //res 即为原始返回的数据
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
                {field: 'id', title: 'ID', width: 80, sort: true, fixed: 'left', align: 'center'}
                , {field: 'applyUserName', title: '申请人', width: 80, align: 'center'}
                , {field: 'vehicleTypeName', title: '车辆类型', width: 110, align: 'center'}
                , {field: 'peopleNum', title: '使用人数', width: 80, hide: true, align: 'center'}
                , {field: 'applyTime', title: '申请时间', width: 180, align: 'center'}
                , {field: 'departure', title: '出发地', width: 100, align: 'center'}
                , {field: 'dest', title: '目的地', width: 100, align: 'center'}
                , {field: 'startTime', title: '开始时间', width: 180, align: 'center'}
                , {field: 'endTime', title: '结束时间', width: 180, align: 'center'}

                , {field: 'applyReason', title: '申请原因', width: 180,  align: 'center'}
                , {field: 'remark', title: '备注', width: 80, align: 'center'}
                , {field: 'stateName', title: '申请单状态', width: 120, align: 'center'}

                , {field: 'plateNo', title: '出车车辆', width: 130, align: 'center'}
                , {field: 'driverUserName', title: '出车司机', width: 130, align: 'center'}
                , {field: 'returnTime', title: '归还时间', width: 130, align: 'center'}
                , {field: 'mileage', title: '行驶里程', width: 130, align: 'center'}

                , {field: 'approveStateName', title: '审核状态', width: 130, align: 'center'}
                , {field: 'approveUsreName', title: '审核人', width: 130, align: 'center'}
                , {field: 'approveNotes', title: '审核备注', width: 130, align: 'center'}
                , {field: 'approveTime', title: '审核时间', width: 130, align: 'center'}

                , {field: 'creator', title: '创建人', width: 110, hide: true, align: 'center'}
                , {field: 'createTime', title: '创建时间', width: 180, hide: true, align: 'center'}
                , {field: 'updater', title: '更新人', width: 80, hide: true, align: 'center'}
                , {field: 'updateTime', title: '更新时间', width: 180, hide: true, align: 'center'}
                , {title: '操作', minWidth: 200, toolbar: '#currentTableBar', align: "center"}//toolbar使用了一个模板
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
            table.reload('applyvehicle', {
                page: {
                    current: 1
                }
                , where: {
                    'applyUserName': searchdata.applyUserName,
                    'plateNo': searchdata.plateNo,
                    'vehicleTypeId': searchdata.vehicleTypeId,
                    'approveState': searchdata.approveState
                },
            }, 'data');

            return false;
        });

        //工具条事件
        table.on('tool(applyvehicle)', function (obj) { //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
            var data = obj.data; //获得当前行数据
            var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
            var tr = obj.tr; //获得当前行 tr 的 DOM 对象（如果有的话）

            if (layEvent === 'detail') { //查看
                form.val("applyvehicleview", {
                    "id": data.id
                    , "applyUserId": data.applyUserId
                    , "applyUserName": data.applyUserName
                    , "vehicleTypeId": data.vehicleTypeId
                    , "vehicleTypeName": data.vehicleTypeName
                    , "vehicleId": data.vehicleId
                    , "plateNo": data.plateNo
                    , "applyTime": data.applyTime
                    , "departure": data.departure
                    , "dest": data.dest
                    , "startTime": data.startTime
                    , "endTime": data.endTime
                    , "peopleNum": data.peopleNum
                    , "applyReasonId": data.applyReasonId
                    , "applyReason": data.applyReason
                    , "remark": data.remark
                    , "driverUserId": data.driverUserId
                    , "driverUserName": data.driverUserName
                    , "returnTime": data.returnTime
                    , "mileage": data.mileage
                    , "approveId": data.approveId
                    , "approveUsreId": data.approveUsreId
                    , "approveUsreName": data.approveUsreName
                    , "approveState": data.approveState
                    , "approveStateName": data.approveStateName
                    , "approveNotes": data.approveNotes
                    , "approveTime": data.approveTime
                    , "state": data.state
                    , "stateName": data.stateName



                });
                layer.open({
                    type: 1,
                    title: '查看申请单',
                    area: ['900px', '600px'],
                    offset: '100px',
                    content: $('#applyvehicleview') //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                    , cancel: function (index, layero) {
                        layer.close(index);
                        $('#applyvehicleview')[0].reset();
                    },
                });
            }
        });

        //头部工具栏事件
        table.on('toolbar(applyvehicle)', function (obj) {
            switch (obj.event) {
                case 'submit':
                    form.val("applyvehiclefrom", {
                        "applyType": 1
                    });
                    saveIndex = layer.open({
                        type: 1,
                        title: '用车申请',
                        area: ['900px', '600px'],
                        offset: '100px',
                        content: $('#applyvehiclefrom') //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                        , cancel: function (index, layero) {
                            layer.close(index);
                            $('#applyvehiclefrom')[0].reset();
                        },
                    });
                    break;
                case 'pressing':
                    form.val("applyvehiclefrom", {
                        "applyType": 2
                    });
                    saveIndex = layer.open({
                        type: 1,
                        title: '紧急用车申请',
                        area: ['900px', '600px'],
                        offset: '100px',
                        content: $('#applyvehiclefrom') //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                        , cancel: function (index, layero) {
                            layer.close(index);
                            $('#applyvehiclefrom')[0].reset();
                        },
                    });
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

        // 新增
        form.on('submit(savebtn)', function (data) {
            var savedata = data.field;//将数据进行json话发送给后台
            $.ajax({
                type: "POST",
                contentType: "application/json; charset=utf-8",
                url: '/apply_vehicle/submit',
                data: JSON.stringify(savedata),
                dataType: 'json',
                success: function (res) {
                    console.log(res)
                    if (0 == res.code) {
                        $('#applyvehiclefrom')[0].reset();
                        layer.msg("提交成功");
                        //执行搜索重载
                        table.reload('applyvehicle', {
                            page: {
                                current: 1
                            }
                            , where: {}
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