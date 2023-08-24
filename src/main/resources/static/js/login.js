function login() {

    var mobileCheck = /^1[3456789]\d{9}$/;
    var error = false;
    var logindata = $("#login").serializeJSON();
    if(mobileCheck.test(logindata.mobile)){
        $("#tel_msg").hide();
    }else {
        error = true;
        $("#tel_msg").show();
    }
    if(logindata.password.length = 0){
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
        url: '/login/pc_login',
        data: JSON.stringify(logindata),
        dataType: 'json',
        success: function (result) {
            //console.log(result)
            if(0 == result.code){
                localStorage.setItem('token', result.data.token);
                window.location.href = '/index';
            }else{
                window.wxc.xcConfirm(result.message, window.wxc.xcConfirm.typeEnum.error);
            }
        },
        error: function (e) {
            console.log(e);
        }
    })
}
function register() {
    window.location.href = '/pages/register';
}
function forget() {
    window.location.href = '/pages/forget';
}


