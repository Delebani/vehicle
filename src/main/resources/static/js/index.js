// $(function(){
//     $(".main_left li").on("click",function(){
//         var address =$(this).attr("data-src");
//         $("iframe").attr("src",address);
//     });
//     //根据窗口高度在设置iframe的高度
//     var frame = $("#aa");
//
//     var frameheight = $(window).height();
//     console.log(frameheight);
//     frame.css("height",frameheight);
//
// });

var layer,$,element;
$(function(){
    $("#menu a").on("click",function(){
        var address =$(this).attr("data-src");
        console.log(address)
        $("iframe").attr("src",address);
    });

    layui.use(['element', 'layer', 'util'], function(){
        element = layui.element
        ,layer = layui.layer
        ,util = layui.util
        ,$ = layui.$;

        //头部事件
        util.event('lay-header-event', {
            //左侧菜单事件
            menuLeft: function(othis){
                layer.msg('展开左侧菜单的操作', {icon: 0});
            }
            ,menuRight: function(){
                layer.open({
                    type: 1
                    ,content: '<div style="padding: 15px;">处理右侧面板的操作</div>'
                    ,area: ['260px', '100%']
                    ,offset: 'rt' //右上角
                    ,anim: 5
                    ,shadeClose: true
                });
            }
        });

        // 菜单树
        $.ajax({
            url:"/user/menu_tree",
            success:function(res){
                if(res.code > 0){
                    return;
                }
                var data = res.data;
                var htmlStr = '';
                $.each(data, function(i, n) {
                    htmlStr += '<li class="layui-nav-item layui-nav-itemed">';
                    htmlStr += '<a class="" href="javascript:;" data-src="'+n.menuUrl+'">' + n.menuName + '</a>';
                    if (n.children.length > 0) {
                        var children = n.children;
                        htmlStr += '<dl class="layui-nav-child">';
                        $.each(children, function(idx, child) { // 使用 children 进行遍历
                            htmlStr += '<dd><a onclick="openTab(\''+child.menuName+'\',\''+child.menuUrl+'\',\''+child.id+'\')" href="javascript:;" data-src="'+child.menuUrl+'" target="_top">' + child.menuName + '</a></dd>'; // 生成子节点的HTML字符串
                        });
                        htmlStr += '</dl>'; // 注意闭合 <dl> 标签位置
                    }
                    htmlStr += '</li>'; // 注意闭合 <li> 标签位置
                });

                $("#menu").html(htmlStr);
                element.render('menu');
            }
        })
    });
    // $("#menu li").on("click",function(){
    //     var address =$(this).attr("data-src");
    //     console.log(address)
    //     $("iframe").attr("src",address);
    // });
    //获取src值
    // $("#menu a").on("click",function(){
    //     var address =$(this).attr("data-src");
    //     console.log(address)
    //     $("iframe").attr("src",address);
    // });
    // //一下代码是根据窗口高度在设置iframe的高度
    // var frame = $("#aa");
    //
    // var frameheight = $(window).height();
    // console.log(frameheight);
    // frame.css("height",frameheight);
    var frameheight = $(window).height();
    var frame = $(".layui-show").css("height",frameheight);

});
function openTab(title,url,id){
    var frameheight = $(window).height();
//	获取相同id组件的长度
    var $node = $("li[lay-id='"+id+"']");
//	如果长度为0，代表没有这个tab
    if($node.length == 0){
//		那就添加这个tab
        element.tabAdd('openTab', {
            title: title
            ,content: "<iframe frameborder='0' src='"+url+"' scrolling='auto' style='width:100%;height:"+frameheight+"px;'></iframe>"
            ,id: id
        })
    }
//	否则就选中打开这个tab，根据id
    element.tabChange('openTab', id);


}