function login() {

    var mobileCheck = /^1[3456789]\d{9}$/;
    var error = false;
    var logindata = $("#login").serializeJSON();
    if (mobileCheck.test(logindata.mobile)) {
        $("#tel_msg").hide();
    } else {
        error = true;
        $("#tel_msg").show();
    }
    if (logindata.password.length = 0) {
        error = true;
        $("#pswd_msg").show();
    } else {
        $("#pswd_msg").hide();
    }
    if (error) {
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
            if (0 == result.code) {
                localStorage.setItem('token', result.data.token);
                localStorage.setItem('headUrl', result.data.headUrl);
                localStorage.setItem('username', result.data.name);
                sessionStorage.setItem('sessionId', result.data.token)
                window.location.href = '/index';
            } else {
                window.wxc.xcConfirm(result.message, window.wxc.xcConfirm.typeEnum.error);
            }
        },
        error: function (e) {
            console.log(e);
        }
    })
}

function scanlogin() {
    $.get('/wechat/getQrcode?scene='+getScene(6), function (res) {
        $("#qrcode").attr('src', res.data)
    })
    $("#qrcodecontainer").show();
}
function checking() {
    setTimeout(() => {
        // 假设 checkOrderStatus是一个异步函数
        checkLogin().then((res) => {
            // 满足条件停止递归
            if (res) {
                //
            } else {
                checking()
            }
        })
}, 3000);
}
function checkLogin() {

}
// 生成随机密码
function getScene(length) {
    var characters = 'abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789';
    var scene = '';
    for (var i = 0; i < length; i++) {
        var randomIndex = Math.floor(Math.random() * characters.length);
        scene += characters[randomIndex];
    }
    return scene;
}

function register() {
    window.location.href = '/pages/register';
}

function forget() {
    window.location.href = '/pages/forget';
}

$(function () {
    $(".close-btn").on("click", function () {
        $("#qrcodecontainer").hide();
    })
})

