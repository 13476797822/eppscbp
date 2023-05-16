require.config({
    paths: {
        jquery: 'jquery-1.8.3.min'
    }
});

define(['jquery'], function($) {
    var cache = {cssSupport:{}};
    var Sys = {};
    var ua = navigator.userAgent.toLowerCase();
    var s;
    (s = ua.match(/msie ([\d]+)/)) ? Sys.ie = s[1] :
        (s = ua.match(/firefox\/([\d]+)/)) ? Sys.firefox = s[1] :
            (s = ua.match(/chrome\/([\d]+)/)) ? Sys.chrome = s[1] :
                (s = ua.match(/opera.([\d]+)/)) ? Sys.opera = s[1] :
                    (s = ua.match(/version\/([\d]+).*safari/)) ? Sys.safari = s[1] : 0;
    var SN = {
        isTouch: function(){
            return navigator.userAgent.match(/(phone|pad|pod|iPhone|iPod|ios|iPad|Android|Mobile|BlackBerry|IEMobile|MQQBrowser|JUC|Fennec|wOSBrowser|BrowserNG|WebOS|Symbian|Windows Phone)/i)?true:false;
        },
        /*
         * 判断css属性是否支持
         */
        cssSupports : (function() {
            var support = cache.cssSupport;
            return function(prop) {
                var div = document.createElement('div'),
                    vendors = 'Khtml O Moz Webkit'.split(' '),
                    len = vendors.length, ret = false;
                prop = prop.replace(/-[\w]/g, function(val) {
                    return val.toUpperCase().substring(1);
                });
                if(prop in support) return support[prop];

                if ('-ms-' + prop in div.style) ret = '-ms-' + prop;
                else{
                    prop = prop.replace(/^[a-z]/, function(val) {
                        return val.toUpperCase();
                    });
                    while(len=len-1){
                        if (vendors[len] + prop in div.style ){
                            ret = vendors[len] + prop;
                        };
                    }
                }

                return support[prop] = ret;
            }
        })(),
        /*
         * 设置css
         */
        css:  function(element, prop, val) {
            var ret = this.cssSupports(prop);
            if(val === undefined)return element[0] && element[0].style[ret];
            else{
                for(var i=0; i<element.length; i++){
                    element[i].style[ret] = val;
                }
            }
        },
        dialog: function(obj, func){
            var _this = this;
            var len = $(".pop-layer").length + 1;
            var html = '<div class="pop-layer" style="width:'+obj.width+'px" id="popLayer'+len+'">';
            if(obj.title != ''){
                html += '<div class="title">'+
                                    ( obj.more ? obj.more : '' )  +
                                    '<h3 class="name">'+obj.title+'</h3>'+
                                    '<a href="javascript:;" class="close">×</a>'+
                                '</div>'+
                                '<div class="pop-body">'+
                                    obj.msg +
                                '</div>';
            }else{
                html += '<div class="pop-body">'+
                            obj.msg +
                        '</div>';
            }
            if(obj.ConfirmFun){
                html += '<div class="btn-box"><a href="javascript:;" class="button-define button-middle">确<em class="w8"></em>定</a></div>';
                $("body").prepend(html);
                $('#popLayer'+len).find('.button-define').on('click',function(){
                    obj.ConfirmFun();
                    if(obj.isClose==undefined||isClose){
                        _this.close();
                    }
                })
            }else{
                html = html + '</div>';
                $("body").prepend(html);
            }
            this.mask();
            this.center($('#popLayer'+len), func);
            this.show($('#popLayer'+len));
            $(".pop-layer .close").on('click',function(){
                _this.close();
            });
            if(obj.title != ''){this.drag('popLayer'+len);}
        },
        show: function(obj){
            var _this = this;
            $(window).scroll(function(){_this.scenter(obj);});
            $(window).resize(function(){_this.scenter(obj);});
        },
        scenter: function(obj){
            var windowWidth = document.documentElement.clientWidth;
            var windowHeight = document.documentElement.clientHeight;
            var popupHeight = $(obj).height();
            var popupWidth = $(obj).width();
            $(obj).css({
                "top": (windowHeight-popupHeight)/2+$(document).scrollTop(),
                "left": (windowWidth-popupWidth)/2
            });
        },
        center: function(obj, func){
            var _this = this;
            var windowWidth = document.documentElement.clientWidth;
            var windowHeight = document.documentElement.clientHeight;
            var popupHeight = $(obj).height();
            var popupWidth = $(obj).width();
            var top = ($(window).height() - popupHeight) / 2 + $(document).scrollTop();
            $(obj).css({
                "opacity": 0,
                "top": top,
                "left": (windowWidth-popupWidth)/2
            });
            $(obj).animate({"top":'+='+'40px',"opacity":"1"},400,function(){
                func();
            });
        },
        close: function(obj){
            var obj = obj || $(".pop-layer:first").find("div.title").find('.close');
            $(obj).closest(".pop-layer").animate({"top":'-='+'60px',"opacity":"0"},400,function(){
                $(this).remove();
                if($("div.pop-layer").length<=0)
                $("#filter").animate({"opacity":"0"},400,function(){$("#filter").remove();});
            })
        },
        mask: function(){
            if($("#filter").length<=0){
                var mybg="<div class='filter' id='filter'></div>";
                var h = $("body").height() > document.documentElement.clientHeight ? $("body").height() : document.documentElement.clientHeight;
                $("body").prepend(mybg);
                $("div#filter").height(h);
                $("#filter").animate({"opacity":".5"},400);
            }
        },
        delMask: function(){
            $("#filter").remove();
        },
        drag: function(dragObj){
            var drag = typeof dragObj === 'string' ? document.getElementById(dragObj) : dragObj;
            var dTitle = drag.getElementsByTagName('div')[0];
            var disX = disY = 0;
            var isDrag = false;
            dTitle.style.cursor = 'move';
            dTitle.onmousedown = function(evt){
                //drag.style.zIndex = parseInt( new Date().getTime()/1000 );
                evt = evt || window.event;
                isDrag = true;
                disX = evt.clientX - drag.offsetLeft;
                disY = evt.clientY - drag.offsetTop;
                this.setCapture && this.setCapture();
                return false;
            }
            dTitle.onmousemove = function(evt){
                evt = evt || window.event;
                if(!isDrag) return false;
                drag.style.margin = 0;
                drag.style.left = evt.clientX - disX + 'px';
                drag.style.top = evt.clientY - disY + 'px';
                return false;
            }
            dTitle.onmouseup = dTitle.onblur = dTitle.onlosecapture = function(){
                isDrag = false;
                dTitle.releaseCapture && dTitle.releaseCapture();
            }
        },
        clearNoNum: function (obj) {
            obj.value = obj.value.replace(/[^\d.]/g, ""); //清除"数字"和"."以外的字符
            obj.value = obj.value.replace(/^\./g, ""); //验证第一个字符是数字而不是
            obj.value = obj.value.replace(/\.{2,}/g, "."); //只保留第一个. 清除多余的
            obj.value = obj.value.replace(".", "$#$").replace(/\./g, "").replace("$#$", ".");
            obj.value = obj.value.replace(/^(\-)*(\d+)\.(\d\d).*$/, '$1$2.$3'); //只能输入两个小数
        },
        // 顶部通知滚动
        initHeadNotice: function() {
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
        },
        // 获取验证码的按钮
        initCheckCodeBtn: function() {
            var index = 0;
            $(document).on("click","#checkCodeBtn",function() {
                if (index === 0) {
                    var thiz = $(this);
                    var timeLeft = 60;
                    thiz.html("60s...");
                    thiz.addClass("get-code-btn-disable");
                    index = setInterval(function() {
                        timeLeft = timeLeft - 1;
                        var html = timeLeft + "s..."
                        if (timeLeft < 0) {
                            html = "获取验证码";
                            clearInterval(index);
                            index = 0;
                            thiz.removeClass("get-code-btn-disable");
                        }
                        thiz.html(html);
                    }, 1000);
                }
            });
        },
        //列表以及状态下拉框
        ulList: function(){
            $(document).on("click",".input-list input",function(e){
                e.stopPropagation();
                $(this).blur().siblings('ul').show();
            });
            $(".input-list li").hover(function(){
                $(this).addClass('hover');
            },function(){
                $(this).removeClass('hover');
            });
            $(document).on("click",".input-list li",function(){
                var val = $(this).text();
                var input = $(this).parents(".input-list").find("input");
                if(val == input.val()){
                    return;
                }else{
                    input.val(val);
                    input.trigger("change");
                    input.siblings(".err-msg").hide();
                }
            });
            $(document).click(function(){
                $('.input-list ul').hide();
            });
        },
        /**
         * 气泡效果的浏览器兼容性处理，有注册事件功能
         * @param: {jQuery} $trigger 触发器元素
         * @param: {jQuery} $target 气泡元素
         * @param: {String} $bindTriggerInEvent 触发显示的事件名称
         * @param: {String} $bindTriggerOutEvent 触发隐藏的事件名称
         * @param: {String/Function} $fadeInAnimate 触发显示的动画
         * @param: {String/Function} $fadeOutAnimate 触发隐藏的动画
         * @param: {Function} $preInCallBack 触发显示的动画的预处理回调
         * @param: {Function} $preOutCallBack 触发隐藏的动画的预处理回调
         */
        convertFade: function($trigger, $target, bindTriggerInEvent, bindTriggerOutEvent, fadeInAnimate, fadeOutAnimate, preInCallBack, preOutCallBack){
            var timer = 0, self = this;;
            if(bindTriggerInEvent && $trigger[bindTriggerInEvent])$trigger[bindTriggerInEvent](function(){
                self.convertFadeCore($trigger, $target, bindTriggerInEvent, '', fadeInAnimate, fadeOutAnimate, preInCallBack, preOutCallBack);
            })
            if(bindTriggerOutEvent && $trigger[bindTriggerOutEvent])$trigger[bindTriggerOutEvent](function(){
                self.convertFadeCore($trigger, $target, '', bindTriggerOutEvent, fadeInAnimate, fadeOutAnimate, preInCallBack, preOutCallBack);
            });
        },
        /**
         * 气泡效果的浏览器兼容性处理，无注册事件功能
         * @param: {jQuery} $trigger 触发器元素
         * @param: {jQuery} $target 气泡元素
         * @param: {String} $bindTriggerInEvent 触发显示的事件名称
         * @param: {String} $bindTriggerOutEvent 触发隐藏的事件名称
         * @param: {String/Function} $fadeInAnimate 触发显示的动画
         * @param: {String/Function} $fadeOutAnimate 触发隐藏的动画
         * @param: {Function} $preInCallBack 触发显示的动画的预处理回调
         * @param: {Function} $preOutCallBack 触发隐藏的动画的预处理回调
         */
        convertFadeCore: function($trigger, $target, bindTriggerInEvent, bindTriggerOutEvent, fadeInAnimate, fadeOutAnimate, preInCallBack, preOutCallBack){
            if(bindTriggerInEvent && $trigger[bindTriggerInEvent]){
                var timer = $target.data('convertTimer');
                if(timer){
                    clearTimeout(timer);
                }
                if(preInCallBack)preInCallBack.call($target, $target);
                $target.convert(function(){
                    this.show().removeClass(fadeOutAnimate).addClass('animated').addClass(fadeInAnimate);
                }, 'show', 'show');
            }
            if(bindTriggerOutEvent && $trigger[bindTriggerOutEvent]){
                if(preOutCallBack)preOutCallBack.call($target, $target);
                $target.convert(function(){

                    this.removeClass(fadeInAnimate).addClass('animated').addClass(fadeOutAnimate);
                    var self = this;
                    var timer = setTimeout(function(){
                        self.hide();
                    }, 150);
                    $target.data('convertTimer', timer);
                }, 'hide', 'hide');
            };
        },
        init:function(){
            var self = this;
            /*hover事件延迟执行
             * @param handlerIn 淡入回调
             * @param handlerOut 淡出回调
             * @param delayInTime 淡入延迟时间
             * @param delayOutTime 淡出延迟时间
             * @param [showHideElement] 固定的气泡元素
             * @param [showHideElementParent] 固定的气泡元素移入触发元素的
             */
            $.fn.delayHover = function (handlerIn, handlerOut, delayInTime, delayOutTime, showHideElement, showHideElementParent) {
                var detailTimer = 0, stopTimer = false, lastTime = new Date(), self = this;
                this.hover(function (event) {
                    if (detailTimer)clearTimeout(detailTimer);
                    var self = this;
                    detailTimer = setTimeout(function () {
                        var current = event.toElement || event.target || event.currentTarget;
                        if (current == self){
                            if (detailTimer)clearTimeout(detailTimer);
                            handlerIn();
                        }
                    }, delayInTime || 0);
                }, function (event) {
                    if (detailTimer)clearTimeout(detailTimer);
                    detailTimer = setTimeout(function () {
                        handlerOut();
                    }, delayOutTime || 150);
                });
                if (showHideElement)showHideElement.hover(function () {
                    if (detailTimer)clearTimeout(detailTimer);
                }, function (event) {
                    if (detailTimer)clearTimeout(detailTimer);
                    detailTimer = setTimeout(function () {
                        var current = event.relatedTarget || event.toElement || event.fromElement;
                        if (current != self[0]
                            && current != showHideElement[0]){
                            if (detailTimer)clearTimeout(detailTimer);
                            handlerOut();
                        }
                    }, delayOutTime || 150);
                });
            }
            $.fn.css3 = function(prop, val){
                this.each(function(i, e){
                    self.cssSet(e, prop, val);
                });
                return this;
            }
            /**
             * 浏览器兼容性处理，新浏览器使用css3的animate， 普通浏览器使用js的animate， ie6使用直接隐藏或显示
             * @param: {String/Function} css3Animate css3的动画
             * @param: {String/Function} jsAnimate js的动画
             * @param: {String/Function} ie6Animate ie6的动画
             */
            $.fn.convert = function(css3Animate, jsAnimate, ie6Animate){
                if(Sys.ie<=6){
                    if(typeof ie6Animate === 'string'){
                        if(this[ie6Animate])this[ie6Animate]();
                    }
                    else if(typeof ie6Animate === 'function'){
                        if(this[ie6Animate])this[ie6Animate].apply(this);
                    }
                }
                else if(self.cssSupports('animation')){
                    if(typeof css3Animate === 'string'){
                        this.addClass(css3Animate);
                    }
                    else if(typeof css3Animate === 'function'){
                        if(css3Animate)css3Animate.apply(this);
                    }
                }
                else{
                    if(typeof jsAnimate === 'string'){
                        if(this[jsAnimate])this[jsAnimate]();
                    }
                    else if(typeof jsAnimate === 'function'){
                        if(jsAnimate)jsAnimate.apply(this);
                    }
                }
            }
            SN.initHeadNotice();
            SN.initCheckCodeBtn();
            SN.ulList();
}
    };
    SN.init();
    return SN;
});
