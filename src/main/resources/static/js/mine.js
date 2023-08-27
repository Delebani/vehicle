$(function () {

    layui.use(['form', 'table'], function() {
        var $ = layui.jquery,//使用了jquery
            form = layui.form,
            table = layui.table,
            upload = layui.upload,
            minedata ={}
            ;

        $.get('/user/mine',function (res) {
            if(0 == res.code) {
                minedata = res.data;
                layui.$('#head_route').removeClass('layui-hide').find('img').attr('src', minedata.headUrl);
                form.val("user-from", {
                    "id":minedata.id
                    ,"name": minedata.name
                    // ,"sex": data.sex
                    // ,"age": data.age
                    ,"type": minedata.type
                    ,"mobile": minedata.mobile
                    ,"idNo": minedata.idNo
                    // ,"department": data.department
                    ,"duty": minedata.duty
                    ,"post": minedata.post
                    ,"headUrl": minedata.headUrl
                })
                form.render();
            }
        })


        form.on('submit(savebtn)', function (data) {
            var savedata = data.field;//将数据进行json话发送给后台
            $.ajax({
                type: "POST",
                contentType: "application/json; charset=utf-8",
                url: '/user/save_mine',
                data: JSON.stringify(savedata),
                dataType: 'json',
                success: function (res) {
                    console.log(res)
                    if(0 == res.code){
                        layer.msg("保存成功");
                        localStorage.setItem('headUrl', res.data.headUrl);
                        localStorage.setItem('username', res.data.name);
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
    })
})