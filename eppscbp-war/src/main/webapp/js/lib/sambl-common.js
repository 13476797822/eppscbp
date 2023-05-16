/**
 * ==========================================================
 * Copyright (c) 2015, suning.com All rights reserved.
 * 易付宝企业版 - 公共头js
 * Author: luj
 * Date: 2015-08-18 16:30:00 897183
 * ==========================================================
 */
sambl = {}
sambl.common = function(){
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
    dropDown('.com_toolbar_more','.com_toolbar_more_ul');

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

    // nav横向滚动
    if($('.com_nav').length>0){
        
        var $navBox = $('.com_nav'),
            $liOn = $navBox.find('ul li.on'),
            curP = $liOn.position().left,
            curW = $liOn.outerWidth(true),
            $targetEle = $navBox.find('ul a'),
            $slider = $('.com_cur_bg');

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
}();