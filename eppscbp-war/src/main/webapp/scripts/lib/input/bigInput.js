define(['jquery'], function($) {
    // 全局的放大器

    var bigInput = (function(){
        return {
            init : function(options){
                var options = this.options = options || {};
                this.input = options.input;
                this.numTip();
            },
            numTip : function(){ //数字放大效果显示&&类型判断
                var input = this.input,
                    _body = input.parents().find('body'),
                    _s1,_s2,_s3,_s4,_s5,_s6,_s7,_s8,_tipText;
                _body.append('<div class="mod-enlarge-num"></div>');
                var _dynNumTip = _body.find('.mod-enlarge-num');
                function subPone(_v){
                    _s1 = _v.substring(0,3);
                    _s2 = _v.substring(3,7);
                    _s3 = _v.substring(7,11);
                    _tipText = _s1+' '+_s2+' '+_s3;
                    return _tipText;
                }
                function subCard(_v){
                    _s1 = _v.substring(0,4);
                    _s2 = _v.substring(4,8);
                    _s3 = _v.substring(8,12);
                    _s4 = _v.substring(12,16);
                    _s5 = _v.substring(16,20);
                    _tipText = _s1+' '+_s2+' '+_s3+' '+_s4 + ' '+_s5;
                    return _tipText;
                }
                function subCardPub(_v){
                    _s1 = _v.substring(0,4);
                    _s2 = _v.substring(4,8);
                    _s3 = _v.substring(8,12);
                    _s4 = _v.substring(12,16);
                    _s5 = _v.substring(16,20);
                    _s6 = _v.substring(20,24);
                    _s7 = _v.substring(24,28);
                    _s8 = _v.substring(28,30);
                    _tipText = _s1+' '+_s2+' '+_s3+' '+_s4 + ' '+_s5+' '+_s6+' '+_s7 + ' '+_s8;
                    return _tipText;
                }
                function subId(_v){
                    _s1 = _v.substring(0,3);
                    _s2 = _v.substring(3,6);
                    _s3 = _v.substring(6,10);
                    _s4 = _v.substring(10,14);
                    _s5 = _v.substring(14,18);
                    _tipText = _s1+' '+_s2+' '+_s3+' '+_s4 + ' '+_s5;
                    return _tipText;
                }
                function showTip(_r,_v,_t){
                    if(_r=='phone'){
                        _t.attr('maxlength','11');
                        _dynNumTip.text(subPone(_v));
                    }else if(_r=='cardpublic'){
                        _t.attr('maxlength','30');
                        _dynNumTip.text(subCardPub(_v));
                    }else if(_r=='card'){
                        _t.attr('maxlength','20');
                        _dynNumTip.text(subCard(_v));
                    }else if(_r=='id'){
                        _t.attr('maxlength','18');
                        _dynNumTip.text(subId(_v));
                    }else{
                        return false;   
                    }
                }
                input.focus(function(){
                    var _t = $(this),
                        _v = _t.val().replace(/[ ]/g,''),
                        _l = _v.length,
                        _r = _t.attr('enlarge'),
                        _x = _t.offset().left,
                        _y = _t.offset().top,
                        _fTxt = _v.substring(0,1),
                        _reg =  /^[a-z0-9]+$/i;
                    _dynNumTip.css({top:_y-37,left:_x});
                    showTip(_r,_v,_t);
                    if(_l>0){
                        _dynNumTip.show();
                    }
                }).keyup(function(){
                    var _t = $(this),
                    _v = _t.val().replace(/[ ]/g,''),
                    _l = _v.length,
                    _r = _t.attr('enlarge'),//[phone,card,id]:["手机","银行卡","身份证"];
                    _x = _t.offset().left,
                    _y = _t.offset().top;
                    _dynNumTip.css({top:_y-37,left:_x});
                    showTip(_r,_v,_t);
                    if(_l==0){
                        _dynNumTip.hide();
                    }
                    if(_l>0){
                        _dynNumTip.show();  
                    }
                }).blur(function(){
                    _dynNumTip.hide();  
                });
            }
            
        }
    })();
    
    $(function(){
        bigInput.init({input:$('.bigInput')});
    });
});