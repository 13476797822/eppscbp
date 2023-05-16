/**
 * ==========================================================
 * Copyright (c) 2015, suning.com All rights reserved.
 * 易付宝企业版 - 公共头js
 * Author: luj
 * Date: 2015-08-18 16:30:00 897183
 * ==========================================================
 */
(function($) {
    // var pluses = /\+/g;function encode(s) {return config.raw ? s : encodeURIComponent(s);}function decode(s) {return config.raw ? s : decodeURIComponent(s);}function stringifyCookieValue(value) {return encode(config.json ? JSON.stringify(value) : String(value));}function parseCookieValue(s) {if (s.indexOf('"') === 0) {s = s.slice(1, -1).replace(/\\"/g, '"').replace(/\\\\/g, '\\');}try {s = decodeURIComponent(s.replace(pluses, ' '));} catch(e) {return;}try {return config.json ? JSON.parse(s) : s;} catch(e) {}}function read(s, converter) {var value = config.raw ? s : parseCookieValue(s);return $.isFunction(converter) ? converter(value) : value;}var config = $.cookie = function (key, value, options) {if (value !== undefined && !$.isFunction(value)) {options = $.extend({}, config.defaults, options);if (typeof options.expires === 'number') {var days = options.expires, t = options.expires = new Date();t.setDate(t.getDate() + days);}return (document.cookie = [encode(key), '=', stringifyCookieValue(value),options.expires ? '; expires=' + options.expires.toUTCString() : '',options.path    ? '; path=' + options.path : '',options.domain  ? '; domain=' + options.domain : '',options.secure  ? '; secure' : ''].join(''));}var result = key ? undefined : {};var cookies = document.cookie ? document.cookie.split('; ') : [];for (var i = 0, l = cookies.length; i < l; i++) {var parts = cookies[i].split('=');var name = decode(parts.shift());var cookie = parts.join('=');if (key && key === name) {result = read(cookie, value);break;}if (!key && (cookie = read(cookie)) !== undefined) {result[name] = cookie;}}return result;};config.defaults = {};$.removeCookie = function (key, options) {if ($.cookie(key) !== undefined) {$.cookie(key, '', $.extend({}, options, { expires: -1 }));return true;}return false;};

    // 登录未登录通用结构
    var common = function(){
    // 头部下拉
        function dropDown(objp, objc){
            var timer = 0;
            $(objp).hover(function(){
                if(timer){clearTimeout(timer);timer=0;}
                timer = setTimeout(function(){
                    $(objp).addClass('hover');
                    $(objp).find(objc).slideDown('fast');
                }, 100);
            },function(){
                if(timer){clearTimeout(timer);timer=0;}
                timer = setTimeout(function(){
                    $(objp).removeClass('hover');
                    $(objp).find(objc).slideUp('fast');
                }, 230);
            });
        }
        dropDown('.com_toolbar_left','.com_toolbar_qrcode');
        // dropDown('.com_toolbar_more','.com_toolbar_more_ul');

        // 切换
        $('.com_qrcode_tab').find('li').mouseover(function(){
            var $num = $(this).index();
            var $imgs = $('.com_toolbar_qrcode_img');
            $('.com_qrcode_tab').find('li').removeClass('on');
            $(this).addClass('on');
            $imgs.removeClass('hide');

            if($num === 1){
                $imgs.eq(0).addClass('hide');
            }else{
                $imgs.eq(1).addClass('hide');
            }
        });
    }();

    // 环境判断
    // 当前发布环境判断
    var getLocationEppUrl = function(){
        var _hostName = document.location.hostname;
        var ego_pre = /^([\w\.]*)(pre)(\w*)(.cnsuning.com)$/;
        var ego_sit = /^([\w\.]*)(sit)(\w*)(.cnsuning.com)$/;
        var ego_dev = /^([\w\.]*)(dev)(\w*)(.cnsuning.com)$/;
        var url = {
            'loginNickName':'https://eportal.suning.com/epps-mpp/member/getLoginNickName.htm',
            'userMenus':'https://eportal.suning.com/epps-mpp/member/getUserMenus.htm',
            'quit':'https://paypassport.suning.com/ids/logout?service=https%3A%2F%2Fprepaypassport.cnsuning.com%2Fids%2Flogin%3FloginTheme%3Dmpp'
        };
        if (ego_pre.test(_hostName)) {
            url.loginNickName = 'http://eportalpre.cnsuning.com/epps-mpp/member/getLoginNickName.htm';
            url.userMenus = 'http://eportalpre.cnsuning.com/epps-mpp/member/getUserMenus.htm';
            url.quit = 'https://prepaypassport.cnsuning.com/ids/logout?service=https%3A%2F%2Fprepaypassport.cnsuning.com%2Fids%2Flogin%3FloginTheme%3Dmpp';
        } else if (ego_sit.test(_hostName)) {
            url.loginNickName = 'http://eportalsit.cnsuning.com/epps-mpp/member/getLoginNickName.htm';
            url.userMenus = 'http://eportalsit.cnsuning.com/epps-mpp/member/getUserMenus.htm';
            url.quit = 'https://sitpaypassport.cnsuning.com/ids/logout?service=https%3A%2F%2Fsitpaypassport.cnsuning.com%2Fids%2Flogin%3FloginTheme%3Dmpp';
        } else if (ego_dev.test(_hostName)) {
            url.loginNickName = 'http://eportalsit.cnsuning.com/epps-mpp/member/getLoginNickName.htm';
            url.userMenus = 'http://eportalsit.cnsuning.com/epps-mpp/member/getUserMenus.htm';
            url.quit = 'https://sitpaypassport.cnsuning.com/ids/logout?service=https%3A%2F%2Fsitpaypassport.cnsuning.com%2Fids%2Flogin%3FloginTheme%3Dmpp';
        }
        return url;
    }




    // 动画效果
    function headNavMore(){
        // animate
        if($('.com_nav ul li').length > 0){
            
            var $navBox = $('.com_nav'),
                $liOn = $navBox.find('ul li.on'),
                curP = $liOn.position().left,
                curW = $liOn.outerWidth(true),
                $targetEle = $navBox.find('ul a'),
                $slider = $('.com_cur_bg');

            if($slider.is(':hidden')){
                $slider.show();
            }

            $slider.animate({
                'left':curP,
                'width':curW
            });
            $targetEle.mouseenter(function () {
                var $_parent = $(this).parent(),
                _width = $_parent.outerWidth(true),
                posL = $_parent.position().left;
                $slider.stop(true, true).animate({
                    'left':posL,
                    'width':_width
                }, 'fast');
            });
            $navBox.mouseleave(function (cur, wid) {
                cur = curP;
                wid = curW;
                $slider.stop(true, true).animate({
                    'left':cur,
                    'width':wid
                }, 'fast');
            });
           
        }
    }
    // 顶部样式
    var topBarAdd = function(data,quitUrl){
        // 开始组装数据
        if(typeof data != 'undefined' && data && data != 'undefined'){
            // 更改数据
            $('.com_toolbar_user_id').text(data.name);
            $('.com_toolbar_quit_id').attr('href',quitUrl);
        }
    }
    // head
    var headAdd = function(data){
        // 开始组装数据
        if(typeof data != 'undefined' && data && data != 'undefined'){
            // 从cookie中获取选中的值
            // var $cookie = $.cookie('domFirmMenuCookie');
            var $url = window.location.href;
            // $.cookie('domFirmMenuCookie',null);
            // console.log($cookie+'___'+$url);
            var topBarHtml = '';
            var menus = data.menuList;
            var hasCookie = true;

            for (var i = 0; i < menus.length; i++) {
                // 循环时判断是否存在选中项
                // console.log(($url == menus[i].url)+'___'+menus[i].url);
                if($url == menus[i].url){
                    hasCookie = false;
                    topBarHtml += '<li class="on"><a target="_self" name="md" href="'+menus[i].url+'" data-id="'+menus[i].id+'">'+menus[i].name+'</a></li>';
                }else{
                    topBarHtml += '<li><a target="_self" name="md" href="'+menus[i].url+'" data-id="'+menus[i].id+'">'+menus[i].name+'</a></li>';
                }
            };
            // 如果没有选中项则默认选中首页
            $('.com_nav').find('ul').append(topBarHtml);

            // 如果没有选中项则默认选中首页
            if(hasCookie){
                $('.com_nav').find('li').eq(0).addClass('on');
            }

            // 对页面进行触发事件设置，点击后存入cookie
            // $('.com_nav').find('a').click(function(){
            //     var aId = $(this).attr('data-id');
            //     $.cookie('domFirmMenuCookie',aId);
            //     window.location.href = $(this).attr('href');
            // });
            return headNavMore();
        }
    }

    // 通用数据处理
    var dataProcessing = function(){
        var allUrl = getLocationEppUrl();
        // 数据分析
        // 判断并进行数据调用
        if($('.com_toolbar').length > 0 && $('.com_toolbar_user_id').length > 0){
            $.ajax({
                type:'post',
                url: allUrl.loginNickName,
                cache:false,
                dataType:'json',
                success:function(data){
                    if(data.responseCode === '0000'){
                        topBarAdd(data,allUrl.quit);
                    }
                }
            });
        }

        if($('.com_nav ul li').length === 0){
            $.ajax({
                type:'post',
                url: allUrl.userMenus,
                cache:false,
                dataType:'json',
                success:function(data){
                    // console.log(data);
                    if(data.responseCode === '0000'){
                        headAdd(data);
                    }
                }
            });
        }
    }
    
    dataProcessing();
})(jq172);