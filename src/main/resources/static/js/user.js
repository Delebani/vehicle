$(function () {

    layui.use(['form', 'table'], function() {
        var $ = layui.jquery,//使用了jquery
            form = layui.form,
            userform = layui.form,
            roleform = layui.form,
            table = layui.table,
            upload = layui.upload,
            saveIndex = 0,
            roleIndex = 0
            ;
        $.get('/role/find_all',function (res) {
            if(0 == res.code) {
                var roleList = res.data;
                for (var i = 0; i < roleList.length; i++) {
                    $('#rolecheckbox').append('<input type="checkbox" name="role" value="'+roleList[i].id+'" title="'+roleList[i].roleName+'">');
                }
            }
        });

        // 重新渲染复选框
        form.render('checkbox');

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
                {field: 'id', title: 'ID', width:80, sort: true, fixed: 'left', align: 'center',type:'checkbox'}
                ,{field: 'name', title: '姓名', width:130, align: 'center'}
                ,{field: 'type', title: '用户类型', width:110, sort: true, align: 'center'}
                // ,{field: 'sex', title: '性别', width:60, align: 'center'}
                // ,{field: 'age', title: '年龄', width: 60, align: 'center'}
                ,{field: 'mobile', title: '手机号', width: 130, align: 'center'}
                ,{field: 'idNo', title: '身份证号码', width: 180, align: 'center'}
                ,{field: 'department', title: '部门', width: 100, align: 'center'}
                ,{field: 'duty', title: '职务', width: 100, align: 'center'}
                ,{field: 'post', title: '岗位', width: 100, align: 'center'}
                ,{field: 'stateName', title: '账号状态', width: 100, align: 'center'}
                ,{field: 'creator', title: '创建人', width: 110,hide:true, align: 'center'}
                ,{field: 'createTime', title: '创建时间', width: 180, hide:true, align: 'center'}
                ,{field: 'updater', title: '更新人', width: 80, hide:true, align: 'center'}
                ,{field: 'updateTime', title: '更新时间', width: 180, hide:true, align: 'center'}
                //,{field: 'roleIdList', title: '角色', width: 0, hide:true}
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
                form.val("userview", {
                    "id":data.id
                    ,"name": data.name
                    // ,"sex": data.sex
                    // ,"age": data.age
                    ,"type": data.type
                    ,"mobile": data.mobile
                    ,"idNo": data.idNo
                    ,"department": data.department
                    ,"duty": data.duty
                    ,"post": data.post
                    ,"headUrl": data.headUrl

                });
                layui.$('#headbtn').hide();
                layui.$('#savebtn').hide();
                layui.$('#head_route_url').removeClass('layui-hide').find('img').attr('src', data.headUrl);
                layer.open({
                    type: 1,
                    title:'查看用户',
                    area: ['900px', '600px'],
                    offset: '100px',
                    content: $('#userview') //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                    ,cancel: function(index, layero){
                            layer.close(index);
                            $('#userview')[0].reset();
                            layui.$('#head_route').addClass('layui-hide').find('img').attr('src', '');
                    },
                });
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
            } else if(layEvent === 'edit'){ //编辑
                form.val("user-from", {
                    "id":data.id
                    ,"name": data.name
                    // ,"sex": data.sex
                    // ,"age": data.age
                    ,"type": data.type
                    ,"mobile": data.mobile
                    ,"idNo": data.idNo
                    ,"department": data.department
                    ,"duty": data.duty
                    ,"post": data.post
                    ,"headUrl": data.headUrl

                });
                layui.$('#headbtn').show();
                layui.$('#savebtn').show();
                layui.$('#head_route').removeClass('layui-hide').find('img').attr('src', data.headUrl);
                saveIndex = layer.open({
                    type: 1,
                    title:'编辑用户',
                    area: ['900px', '600px'],
                    offset: '100px',
                    content: $('#user-from') //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                    ,cancel: function(index, layero){
                        layer.close(index);
                        $('#user-from')[0].reset();
                        layui.$('#head_route').addClass('layui-hide').find('img').attr('src', '');
                    },
                });

                //同步更新缓存对应的值
                // obj.update({
                //     username: '123'
                //     ,title: 'xxx'
                // });
            } else if(layEvent === 'role'){
                console.log(data.roleIdList)
                for (var i = 0; i < data.roleIdList.length; i++) {
                    $('input[name=role]').each(function () {
                        if (data.roleIdList[i] ==$(this).val()) {
                            $(this).prop('checked', true);
                        }
                    })
                }
                form.val("rolefrom", {
                    "id":data.id
                })
                // 重新渲染复选框
                form.render('checkbox');
                roleIndex = layer.open({
                    type: 1,
                    title:'用户角色授权',
                    area: ['900px', '300px'],
                    offset: '100px',
                    content: $('#rolefrom') //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                    // ,success: function(layero, index){
                    //     console.log(layero, index);
                    // }
                    ,cancel: function(index, layero){
                        layer.close(index);
                        $('#rolefrom')[0].reset();
                    },

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
                case 'enable':
                    var req = {userIdList:checkedid_arr,state:1}
                    $.ajax({
                        type: "POST",
                        contentType: "application/json; charset=utf-8",
                        url: '/user/enable_freeze',
                        data: JSON.stringify(req),
                        dataType: 'json',
                        success: function (res) {
                            if(0 == res.code){
                                $('#user-from')[0].reset();
                                layer.msg('启用成功');
                                //执行搜索重载
                                table.reload('user', {
                                    page: {
                                        current: 1
                                    }
                                    , where: {
                                    }
                                }, 'data');
                            }
                        },
                        error: function (e) {
                            console.log(e);
                        }
                    });
                    break;
                case 'freeze':
                    var req = {userIdList:checkedid_arr,state:0}
                    $.ajax({
                        type: "POST",
                        contentType: "application/json; charset=utf-8",
                        url: '/user/enable_freeze',
                        data: JSON.stringify(req),
                        dataType: 'json',
                        success: function (res) {
                            if(0 == res.code){
                                $('#user-from')[0].reset();
                                layer.msg('冻结成功');
                                //执行搜索重载
                                table.reload('user', {
                                    page: {
                                        current: 1
                                    }
                                    , where: {
                                    }
                                }, 'data');
                            }
                        },
                        error: function (e) {
                            console.log(e);
                        }
                    });
                    break;
            };
        });

        // 新增
        userform.on('submit(save-btn)', function (data) {
            var savedata = data.field;//将数据进行json话发送给后台
            // console.log(savedata)
            $.ajax({
                type: "POST",
                contentType: "application/json; charset=utf-8",
                url: '/user/save_or_update',
                data: JSON.stringify(savedata),
                dataType: 'json',
                success: function (res) {
                    console.log(res)
                    if(0 == res.code){
                        $('#user-from')[0].reset();
                        layui.$('#head_route').addClass('layui-hide').find('img').attr('src', '');
                        layer.msg("保存成功");
                        //执行搜索重载
                        table.reload('user', {
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

        //创建一个上传组件
        upload.render({
            elem: '#headbtn'
            ,url: '/file/upload'
            ,done: function(res, index, upload){ //上传后的回调
                console.log(res);
                if(res.code == 0){
                    //上传完毕回调
                    layer.msg("上传成功");
                    var fileUrl = res.data;
                    console.log(fileUrl);//图片地址
                    layui.$('#head_route').removeClass('layui-hide').find('img').attr('src', fileUrl);
                    //塞入到input
                    $("#headUrl").val(fileUrl);
                }else{
                    layer.msg("上传失败");
                }
            }
            ,accept: 'images' //允许上传的文件类型
            ,size: 5000 //最大允许上传的文件大小
        })

        roleform.on('submit(rolebtn)', function (data) {
            console.log(data.field)
            //将页面全部复选框选中的值拼接到一个数组中
            var roleid_arr = [];
            $('input[type=checkbox]:checked').each(function() {
                roleid_arr.push($(this).val());
            });
            var savejson = {'id': data.field.id,'roleIdList':roleid_arr}
            console.log(savejson);
            $.ajax({
                type: "POST",
                contentType: "application/json; charset=utf-8",
                url: '/user/save_role',
                data: JSON.stringify(savejson),
                dataType: 'json',
                success: function (res) {
                    console.log(res)
                    if(0 == res.code){
                        $('#rolefrom')[0].reset();
                        layer.msg("保存成功");
                        layer.close(roleIndex);
                        //执行搜索重载
                        table.reload('user', {
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