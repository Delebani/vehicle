function forget() {

    var mobileCheck = /^1[3456789]\d{9}$/;
    var error = false;
    var forgetdata = $("#forget").serializeJSON();
    if(mobileCheck.test(forgetdata.mobile)){
        $("#tel_msg").hide();
    }else {
        error = true;
        $("#tel_msg").show();
    }
    if(forgetdata.password != forgetdata.password2){
        error = true;
        $("#pswd_msg").show();
    }else{
        $("#pswd_msg").hide();
    }
    if(error){
        return
    }

    $.ajax({
        type: "POST",
        contentType: "application/json; charset=utf-8",
        url: '/login/pc_reset_password',
        data: JSON.stringify(forgetdata),
        dataType: 'json',
        success: function (result) {
            if(0 == result.code){
                window.wxc.xcConfirm("重置密码成功，请登录", window.wxc.xcConfirm.typeEnum.success,{
                    onOk:function(){
                        window.location.href = '/login';
                    }
                }
                );

            }else{
                window.wxc.xcConfirm(result.message, window.wxc.xcConfirm.typeEnum.error);
            }
        },
        error: function (e) {
            console.log(e);
        }
    })
};
function sendCode() {
    var mobile = $("#tel").val();
    $.get('/mobile/send_code?mobile='+mobile,function (res) {
        window.wxc.xcConfirm("验证码发送成功，请查看手机", window.wxc.xcConfirm.typeEnum.success);
    })
};

function login() {
    window.location.href = '/login';
}


