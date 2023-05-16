define(['jquery'], function($) {
// 通知滚动
jQuery.autoNotice = function(){
    var liLen = $(".head-notice ul li").length;

    if (liLen > 0) {
        var maxTop = (liLen - 1) * - 38;
        setInterval(function() {
            var $ul = $(".head-notice ul");
            var top = $ul.get(0).style.top;
            if (!top) {
                top = 0;
            }
            top = parseInt(top);
            top -= 38;
            var marginTop = top + "px";
            $(".head-notice ul").animate({top: marginTop}, "slow", function() {
                if (top <= maxTop) {
                    top = 0;
                    $ul.css("top", "0px");
                }
            });
        }, 5000);
    }
}

});