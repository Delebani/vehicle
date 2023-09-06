$(function () {

    layui.use(['form', 'table'], function () {
        var $ = layui.jquery,//使用了jquery
            form = layui.form,
            table = layui.table,
            upload = layui.upload,
            laydate = layui.laydate,
            saveIndex = 0
        ;
        $.get('/expense_type/all', function (res) {
            if (0 == res.code) {
                var typeList = res.data;
                $('#typeId4searchselect').append('<option value="">全部</option>');
                for (var i = 0; i < typeList.length; i++) {
                    $('#typeId4searchselect').append('<option value="' + typeList[i].id + '">' + typeList[i].typeName + '</option>');
                    $('#typeId4addselect').append('<option value="' + typeList[i].id + '">' + typeList[i].typeName + '</option>');
                }
                form.render('select'); // 重新渲染 select 元素
            }
        });

        $.get('/vehicle/all', function (res) {
            if (0 == res.code) {
                var vehicleList = res.data;
                for (var i = 0; i < vehicleList.length; i++) {
                    $('#vehicle_select').append('<option value="' + vehicleList[i].id + '">' + vehicleList[i].plateNo + '</option>');
                }
                form.render('select', 'vehicle_select');
            }
        })

        laydate.render({
            elem: '#start'
            , type: 'datetime'
        });
        laydate.render({
            elem: '#end'
            , type: 'datetime'
        });
        //执行渲染
        table.render({
            elem: '#expense',
            url: '../expense/page',//后台接口地址
            request: {
                pageName: 'current' //页码的参数名称，默认：page
                , limitName: 'pageSize' //每页数据量的参数名，默认：limit
            },
            method: 'post',
            where: {'expenseUserName': '', 'plateNo': '', 'expenseTypeId': '', 'approveState': '', 'start': '', 'end': ''},
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
                // {field: 'id', title: 'ID', width: 80, sort: true, fixed: 'left', align: 'center'}
                {field: 'expenseNo', title: '报销编号', width: 160, align: 'center', fixed: 'left'}
                , {field: 'expenseUserName', title: '报销人', width: 80, align: 'center'}
                , {field: 'expenseTime', title: '报销时间', width: 180, align: 'center'}
                , {field: 'expenseTypeName', title: '费用类型', width: 110, align: 'center'}
                , {field: 'plateNo', title: '报销车辆', width: 80, hide: true, align: 'center'}
                , {field: 'amount', title: '金额', width: 180, align: 'center'}
                , {field: 'remark', title: '备注', width: 80, align: 'center'}

                , {field: 'approveStateName', title: '审核状态', width: 130, align: 'center'}
                , {field: 'approveUserName', title: '审核人', width: 130, align: 'center', hide: true}
                , {field: 'approveNotes', title: '审核备注', width: 130, align: 'center', hide: true}
                , {field: 'approveTime', title: '审核时间', width: 130, align: 'center', hide: true}

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
            table.reload('expense', {
                page: {
                    current: 1
                }
                , where: {
                    'expenseUserName': searchdata.applyUserName,
                    'plateNo': searchdata.plateNo,
                    'expenseTypeId': searchdata.expenseTypeId,
                    'approveState': searchdata.approveState,
                    'start': searchdata.start,
                    'end': searchdata.end
                },
            }, 'data');

            return false;
        });

        //工具条事件
        table.on('tool(expense)', function (obj) { //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
            var data = obj.data; //获得当前行数据
            var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
            var tr = obj.tr; //获得当前行 tr 的 DOM 对象（如果有的话）

            if (layEvent === 'detail') { //查看
                form.val("expenseview", {
                    "id": data.id
                    , "expenseUserId": data.expenseUserId
                    , "expenseUserName": data.expenseUserName
                    , "expenseTypeId": data.expenseTypeId
                    , "expenseTypeName": data.expenseTypeName
                    , "amount": data.amount
                    , "vehicleId": data.vehicleId
                    , "plateNo": data.plateNo
                    , "expenseTime": data.expenseTime
                    , "remark": data.remark
                    , "invoiceUrl": data.invoiceUrl
                    , "approveId": data.approveId
                    , "approveUserId": data.approveUserId
                    , "approveUserName": data.approveUserName
                    , "approveState": data.approveState
                    , "approveStateName": data.approveStateName
                    , "approveNotes": data.approveNotes
                    , "approveTime": data.approveTime


                });
                layui.$('#inv_route_url').removeClass('layui-hide').find('img').attr('src', data.invoiceUrl);
                layer.open({
                    type: 1,
                    title: '查看报销单',
                    area: ['900px', '600px'],
                    offset: '100px',
                    content: $('#expenseview') //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                    , cancel: function (index, layero) {
                        layer.close(index);
                        $('#expenseview')[0].reset();
                    },
                });
            }
        });

        //头部工具栏事件
        table.on('toolbar(expense)', function (obj) {
            switch (obj.event) {
                case 'submit':
                    saveIndex = layer.open({
                        type: 1,
                        title: '报销申请',
                        area: ['900px', '600px'],
                        offset: '100px',
                        content: $('#expensefrom') //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                        , cancel: function (index, layero) {
                            layer.close(index);
                            $('#expensefrom')[0].reset();
                        },
                    });
                    layui.$('#inv_route').addClass('layui-hide').find('img').attr('src', '');
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
                url: '/expense/submit',
                data: JSON.stringify(savedata),
                dataType: 'json',
                success: function (res) {
                    console.log(res)
                    if (0 == res.code) {
                        $('#expensefrom')[0].reset();
                        layer.msg("提交成功");
                        //执行搜索重载
                        table.reload('expense', {
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

        //创建一个上传组件
        upload.render({
            elem: '#imgbtn'
            ,url: '/file/upload'
            ,done: function(res, index, upload){ //上传后的回调
                console.log(res);
                if(res.code == 0){
                    //上传完毕回调
                    layer.msg("上传成功");
                    var fileUrl = res.data;
                    console.log(fileUrl);//图片地址
                    layui.$('#inv_route').removeClass('layui-hide').find('img').attr('src', fileUrl);
                    //塞入到input
                    $("#invoiceUrl").val(fileUrl);
                }else{
                    layer.msg("上传失败");
                }
            }
            ,accept: 'images' //允许上传的文件类型
            ,size: 5000 //最大允许上传的文件大小
        })

        // 设置select 与 input 输入框联动
        form.on('select(vehicle_select)', function (data) {
            $("#plateNo").val($("#vehicle_select").find("option:selected").text());
            $("#vehicleId").val(data.value);

            $("#vehicle_select").next().find("dl").css({display: "none"});
            form.render();
        });

        window.search = function () {
            var value = $("#plateNo").val();
            value = value.replace(/^\s*/,"");//利用正则去除左侧空格
            // console.log(value)
            $("#vehicle_select").val(value);
            form.render();
            $("#vehicle_select").next().find(".layui-select-title input").click();
            var dl = $("#vehicle_select").next().find("dl").children();
            var j = -1;

            for (var i =0;i<dl.length;i++){
                if (dl[i].innerHTML.indexOf(value)<=-1){
                    dl[i].style.display="none";
                    j++
                }
                if (j ==dl.length-1){
                    $("#vehicle_select").next().find("dl").css({"display":"none"});
                    layer.msg('车辆未找到');
                    $("#plateNo").val('');
                }
            }
        }

    })
})