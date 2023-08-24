function addScript(){
    document.write("<script language=javascript src=/js/xcConfirm.js></script>");
}
// 添加全局请求头
$(document).ajaxSend(function(event, xhr) {
    xhr.setRequestHeader('token', localStorage.getItem("token")); // 增加一个自定义请求头
    xhr.setRequestHeader('contentType', "application/json; charset=utf-8");
});

// 统一处理异常
function redirectHandle(xhr) {

    var res = JSON.parse(xhr.responseText);
    console.log(res);
    if(999 == res.code){
        var win = window;
        while(win != win.top){
            win = win.top;
        }
        win.location.href = '/login';
    }else if(res.code > 0){
        console.log(res.message);
        window.wxc.xcConfirm(res.message, window.wxc.xcConfirm.typeEnum.error);
    }
}
$(function () {
    $(document).ajaxComplete(function (event, xhr, settings) {
        console.log("ajaxComplete 执行")
        redirectHandle(xhr);
    })
})