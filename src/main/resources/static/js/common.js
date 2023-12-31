function addScript(){
    document.write("<script language=javascript src=/js/xcConfirm.js></script>");
}
// 添加全局请求头
$(document).ajaxSend(function(event, xhr) {
    xhr.setRequestHeader('token', localStorage.getItem("token")); // 增加一个自定义请求头
    // xhr.setRequestHeader('content-Type', "application/json; charset=utf-8");
});

// 统一处理异常
function redirectHandle(xhr) {

    var res = JSON.parse(xhr.responseText);
    console.log(res);
    handleRes(res);
}

function handleRes(res){
    if(999 == res.code){
        layer.confirm('登录超时，请重新登录', function(index){
            var win = window;
            while(win != win.top){
                win = win.top;
            }
            win.location.href = '/login';
        })
    }else if(res.code > 0){
        console.log(res.message);
        layer.msg(res.message);
    }
}

$(function () {
    $(document).ajaxComplete(function (event, xhr, settings) {
        console.log("ajaxComplete 执行")
        redirectHandle(xhr);
    })
})