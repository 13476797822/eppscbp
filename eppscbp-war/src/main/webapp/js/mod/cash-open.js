define(['jquery', 'lib/utils','core'], function($, util) {
    return {
        init: function(type) {
            //事件绑定
            this.EventBind(type);
            //提现金额校验
            this.MoneyCheck();
        },
        //事件绑定
        EventBind : function(type){
            var self = this;
            //点击管理银行卡
            $(".cardmanage").click(function(){
                window.open("entrusted_manage.html","_blank");
            })
            //点击常见问题
            $(".usualques").click(function(){
                window.open("entrusted_question.html","_blank");
            })

            //添加银行卡
            $('.addCard').click(function(e){
                e.stopPropagation();
                if($(".cashCard").length > 4){
                    //添加达上限
                    var $popMsg = $('.addCard .dis_tip'), $tipsIcon = $('.addCard');
                    $popMsg.fadeIn();
                    setTimeout(function(){
                        $popMsg.fadeOut();
                    },3000);
                    /*$tipsIcon.delayHover(function () {
                        util.convertFadeCore($tipsIcon, $popMsg, 'mouseenter', '', 'fadeInUp', 'fadeOutDown');
                    }, function () {
                        util.convertFadeCore($tipsIcon, $popMsg, '', 'mouseleave', 'fadeInUp', 'fadeOutDown');
                    }, 100, 0, $popMsg);*/
                    if($(".card_behave a").hasClass("showCard")){
                        $(".card_behave a").trigger("click");
                    }
                }else{
                    if(!$(this).hasClass('disable')){
                        //public 对公 person 个人 company 企业
                        var win = self.addDialog(type);
                        win.show();
                        util.ulList();
                    }
                }

            })
            $(document).on("click",".description a",function(e){
                e.stopPropagation();
                $(this).parents("dd").addClass("z-index-h2");
                var html = $("#code_area").html();
                $(".area_code").find("ul").remove();
                $(".area_code").append(html).fadeIn();
            })
            // $(document).click(function(evt){
            //     var e = window.event || evt;
            //     $(".area_code").fadeOut();
            //     $(".dialog_bankNo").find("dd").removeClass("z-index-h2");

            //     // 检测数据输入对不对
            //     if($(e.target).attr('attr')!='city' && !$(e.target).hasClass('dialog_input_city')) {
            //         $('.city-list').fadeOut();
            //         if($('#citySelected').val()==0) {
            //             $('.dialog_input_city').addClass('err_input');
            //             $('.add_line .warn').removeClass('hide');
            //         } else {
            //             $('.add_line .warn').addClass('hide');
            //         }
            //     }

            //     if($(e.target).attr('attr')!='sub-bank' && !$(e.target).hasClass('dialog_input_sub_bank')) {
            //         $('.sub-bank-list').fadeOut();
            //         if($('#subBankSelected').val()==0) {
            //             $('.dialog_input_sub_bank').addClass('err_input');
            //             $('.dl-sub-bank .warn').removeClass('hide');
            //         } else {
            //             $('.dl-sub-bank .warn').addClass('hide');
            //         }
            //     }
            // })

            /*$(".check").on("mouseover mouseout",function(event){
                if(event.type == "mouseover"){alert(2)
                    //鼠标悬浮
                    if(!$(this).hasClass('checkOn')){
                        $(this).addClass('checkHov');
                    }
                }else if(event.type == "mouseout"){
                    //鼠标离开
                    $(this).removeClass('checkHov');
                }

            });*/
            //添加银行卡短信通知
            $(document).on("click",'.check',function(){
                if($(this).hasClass('checkOn')){
                    $(this).removeClass('checkOn');
                    $(".dialog_tel").hide();
                }else{
                    $(this).addClass('checkOn');
                    $(".dialog_tel").show();
                }

            })



            //银行卡
            /*$('.cashCard').hover(function(){
                $(this).addClass('on');
            },function(){
                $(this).removeClass('on');
            })*/

            //银行卡列表点击选中上浮到第一个
            $(document).on("click",'.cashCard',function(e){
                if($(e.target).parents('.manage').length >= 1 || $(e.target).hasClass('manage') || $(this).hasClass("disabled")){
                    return;
                }
                var index = $(this).index(),
                    len =  $('.cashCard').length;
                var $node = $(this);
                if(index > 0 && len > 1){
                    var top1 = $node.parents("ul").offset().top,
                        top2 = $(this).offset().top;
                    var height = top2 - top1;
                    $node.addClass('on').css({"position":"absolute","top":height}).siblings().removeClass("on").css("position","static");
                    $node.animate({"top":"0px"}, 500,function(){
                        $(".hideCard").trigger("click");
                    });
                    $node.insertBefore($('.cashCard').eq(0)).css("position","relative");
                }else if(index == 0 && len > 1){
                    $node.addClass('on');
                    $(".hideCard").trigger("click");
                }
            })

            //银行卡操作
            $('.manage').click(function(){
                $(this).find('ul').show();
            })

            $('.manage ul li').hover(function(){
                $(this).addClass('on');
            },function(){
                $(this).removeClass('on');
            })

            $('.manage ul').mouseleave(function(){
                $(this).hide();
            })

            //全部已添加的银行卡
            $('.showCard').click(function(){
                if($(this).hasClass("hideCard")){
                    $('.cashCard').eq(0).addClass("on");
                    $('.cashCard:gt(0)').slideUp();
                    $(this).removeClass("hideCard").addClass("showCard").find("span").text("其他银行卡").parents(".card_behave").css("margin-top","-10px");
                    $(".card_to_show").show();
                }else{
                    $('.cashCard').removeClass("on").slideDown();
                    $(this).removeClass("showCard").addClass("hideCard").find("span").text("收起银行卡").parents(".card_behave").css("margin-top","10px");
                    $(".card_to_show").hide();
                }

            })
            $(document).on("click",".card_to_show",function(){
                $(".showCard").trigger("click");
            })
            //选择其他银行
            $(document).on("click",".bank_other",function(){
                $(this).next("p").slideDown();
                $(".dialog_bank").val("其它银行");
                $("#sle-banks").slideUp();
            })

            //设置默认
            $('.manage .default').click(function(){
                var win = $.dialog({
                    content : $('#SetAccount').html(),
                    width : 222,
                    maskCss: {              // 遮罩层背景
                        opacity: 0
                    },
                    onShow : function($dialog, callback){
                        setTimeout(function(){
                            callback.close();
                        },3000)
                    },
                    showClose : false
                })
                win.show();
            })
            //删除银行卡
            $('.manage .delete').click(function(){
                if($(this).parent().find(".weituolabel").hasClass("hide")){
                var win = $.dialog({
                    title : '删除银行卡',
                    content : $('#DelAccount').html(),
                    width:458,
                    onShow:function($dialog, callback){
                        //取消
                        $('.cancel').click(function(){
                            callback.close();
                        })
                        //确定
                        $('.confirm').click(function(){
                            self.delConfirm();
                            callback.close();
                            var win1 = $.dialog({
                                title : '删除银行卡',
                                content : $('#ForbidDelAccount').html(),
                                width : 458,
                                onShow : function($dialog, callback){
                                    //取消
                                    $('.cancel').click(function(){
                                        callback.close();
                                    })
                                    //确定
                                    $('.confirm').click(function(){
                                        callback.close();
                                        var win2 = $.dialog({
                                            content : $('#DelSuc').html(),
                                            width : 192,
                                            maskCss: {              // 遮罩层背景
                                                opacity: 0
                                            },
                                            onShow : function($dialog, callback){
                                                setTimeout(function(){
                                                    callback.close();
                                                },3000)
                                            },
                                            showClose : false
                                        })
                                        win2.show();
                                    })
                                },
                                showClose : true
                            })
                            win1.show();
                            /*var win1 = $.dialog({
                                content : $('#DelSuc').html(),
                                width : 192,
                                maskCss: {              // 遮罩层背景
                                    opacity: 0
                                },
                                onShow : function($dialog, callback){
                                    setTimeout(function(){
                                        callback.close();
                                    },3000)
                                },
                                showClose : false
                            })
                            win1.show();*/
                        })
                    },
                    showClose: true
                })
                win.show();
                }else{
                    var win = $.dialog({
                    title : '删除银行卡',
                    content : $('#DelWeiTuoAccount').html(),
                    width:710,
                    onShow:function($dialog, callback){
                        //取消
                        $('.cancel').click(function(){
                            callback.close();
                        })
                        //确定
                        $('.confirm').click(function(){
                            self.delConfirm();
                            callback.close();
                            var win2 = $.dialog({
                                content : $('#DelSuc').html(),
                                width : 192,
                                maskCss: {              // 遮罩层背景
                                    opacity: 0
                                },
                                onShow : function($dialog, callback){
                                    setTimeout(function(){
                                        callback.close();
                                    },3000)
                                },
                                showClose : false
                            })
                            win2.show();
                        })
                    },
                    showClose: true
                })
                win.show();
                }
            })
            //修改银行卡
            $('.manage .modify').click(function(){
                var total = cardInfoList.total,
                    lists = cardInfoList.lists,
                    index = $(this).attr("data-index"),
                    win = '';
                var win1 = $.dialog({
                    title : '修改银行卡',
                    content : $('#AlterAccount').html(),
                    width : 458,
                    onShow : function($dialog, callback){
                        //取消
                        $('.cancel').click(function(){
                            callback.close();
                        })
                        //确定
                        var cardInfoList = cardInfoList;
                        $('.confirm').click(function(){
                            callback.close();
                            if($(this).parents('.personCard').length >= 1){
                                win = self.modDialog('person',lists[index]);
                            }else{
                                win = self.modDialog('public',lists[index]);
                            }
                            win.show();
                        })
                    },
                    showClose : true
                })
                win1.show();
            })

        },
        //修改银行卡
        modDialog: function(type,list){
            var self =this;
            var opts = {};
            switch (type){
                case 'public' :
                    opts = {
                        title: '编辑银行卡',             // 标题
                        content: $('#publicAccount').html(),            // 内容
                        width: 800,             // 宽度
                        onShow: function($dialog, callback) {
                            //placeholder
                            $('.dialog_input').placeholder();
                            self.setColor();
                            // 选择银行
                            $('.dialog_bankBox').html($('#banksHtml').html());
                            // 绑定选择银行事件
                            self.transfer($('.dialog_bank'),$('.card-num-input'),$('#sle-banks .title a'),$('#sle-banks .con'),$('#sle-banks'));
                            // 编辑值带入
                            self.valueSet(type,list);
                            // 表单校验
                            self.checkForm(type);
                            // 保存回调
                            self.saveForm(type);
                            // callback.close();
                            $('.infor-list').html($('#provList').html());
                             // 省份选择
                            self.provSelect();
                            // 城市选择
                            self.pullDownSelect($('.dialog_input_city'),$('.city-list'),$('.city-list li'),'city');
                            // 开户支行选择
                            self.pullDownSelect($('.dialog_input_sub_bank'),$('.sub-bank-list'),$('.sub-bank-list li'),'sub-bank');
                            self.documentClick();
                            $(".dialog_bankNo .dialog_input").removeClass("err_input").attr("disabled","yes");
                            $(".dialog_bankNo .warn").addClass("hide");
                        },  // 显示回调
                        showClose: true
                    }
                    break;
                case 'person' :
                    opts = {
                        title: '编辑银行卡',             // 标题
                        content: $('#personAccount').html(),            // 内容
                        width: 800,             // 宽度
                        onShow: function($dialog, callback) {
                            //placeholder
                            $('.dialog_input').placeholder();
                            self.setColor();
                            // 选择银行
                            $('.dialog_bankBox').html($('#banksHtml').html());
                            // 绑定选择银行事件
                            self.transfer($('.dialog_bank'),$('.card-num-input'),$('#sle-banks .title a'),$('#sle-banks .con'),$('#sle-banks'));
                            // 编辑值带入
                            self.valueSet(type,list);
                            // 表单校验
                            self.checkForm(type);
                            // 保存回调
                            self.saveForm(type);
                            // callback.close();
                        },  // 显示回调
                        showClose: true
                    }
                    break;
            }
            var win = $.dialog(opts);
            return win;
        },
        //添加银行卡
        addDialog : function(type){
            var self =this;
            var opts = {};
            switch (type){
                case 'public' :
                    opts = {
                        title: '添加银行卡',             // 标题
                        content: $('#publicAccount').html(),            // 内容
                        width: 800,             // 宽度
                        onShow: function($dialog, callback) {
                            //placeholder
                            $('.dialog_input').placeholder();
                            self.setColor();
                            // 选择银行
                            $('.dialog_bankBox').html($('#banksHtml').html());
                            // 绑定选择银行事件
                            self.transfer($('.dialog_bank'),$('.card-num-input'),$('#sle-banks .title a'),$('#sle-banks .con'),$('#sle-banks'));
                            // 选择地址
                            // self.address();
                            // 表单校验
                            self.checkForm(type);
                            // 保存回调
                            self.saveForm(type);
                            // callback.close();

                            $('.infor-list').html($('#provList').html());
                             // 省份选择
                            self.provSelect();
                            // 城市选择
                            self.pullDownSelect($('.dialog_input_city'),$('.city-list'),$('.city-list li'),'city');
                            // 开户支行选择
                            self.pullDownSelect($('.dialog_input_sub_bank'),$('.sub-bank-list'),$('.sub-bank-list li'),'sub-bank');
                            self.documentClick();
                        },  // 显示回调
                        showClose: true
                    }
                    break;
                case 'person' :
                    opts = {
                        title: '添加银行卡',             // 标题
                        content: $('#personAccount').html(),            // 内容
                        width: 800,             // 宽度
                        onShow: function($dialog, callback) {
                            //placeholder
                            $('.dialog_input').placeholder();
                            self.setColor();
                            // 选择银行
                            $('.dialog_bankBox').html($('#banksHtml').html());
                            // 绑定选择银行事件
                            self.transfer($('.dialog_bank'),$('.card-num-input'),$('#sle-banks .title a'),$('#sle-banks .con'),$('#sle-banks'));
                            // 表单校验
                            self.checkForm(type);
                            // 保存回调
                            self.saveForm(type);
                            self.documentClick();
                            // callback.close();
                        },  // 显示回调
                        showClose: true
                    }
                    break;
                case 'company' :
                    opts = {
                        title: '添加银行卡',             // 标题
                        content: $('#companyAccount').html(),            // 内容
                        width: 800,             // 宽度
                        onShow: function($dialog, callback) {
                            //placeholder
                            $('.dialog_input').placeholder();
                            self.setColor();
                            // 选择银行
                            $('.dialog_bankBox').html($('#banksHtml').html());
                            //切换账户类型
                            self.switchType();
                            // 绑定选择银行事件
                            self.transfer($('.dialog_bank'),$('.card-num-input'),$('#sle-banks .title a'),$('#sle-banks .con'),$('#sle-banks'));
                            // 选择地址
                            // self.address();
                            // 表单校验
                            self.checkForm(type);
                            // 保存回调
                            self.saveForm(type);
                            // callback.close();

                            $('.infor-list').html($('#provList').html());

                            // 省份选择
                            self.provSelect();
                            // 城市选择
                            self.pullDownSelect($('.dialog_input_city'),$('.city-list'),$('.city-list li'),'city');
                            // 开户支行选择
                            self.pullDownSelect($('.dialog_input_sub_bank'),$('.sub-bank-list'),$('.sub-bank-list li'),'sub-bank');
                            self.documentClick();
                        },  // 显示回调
                        showClose: true
                    }
                    break;
            }
            var win = $.dialog(opts);
            return win;
        },
        //个体工商户添加银行卡切换账户类型
        switchType:function(){
            var self = this;
            var select = $(".ui-dialog-cont").find("dl").eq(0);
            var htmlPerson = $($('#personAccount').html()).find("dl:gt(0)");
            var htmlPublicAccount = $($('#publicAccount').html()).find("dl:gt(0)");
            $(document).on("change","#companyType",function(){
                if($(this).val() == "个人借记卡"){
                    $(".ui-dialog-cont").find("dl:gt(0)").remove();
                    htmlPerson.insertAfter(select);
                    $('.dialog_input').placeholder();
                    self.setColor();
                    // 选择银行
                    $('.dialog_bankBox').html($('#banksHtml').html());
                    self.transfer($('.dialog_bank'),$('.card-num-input'),$('#sle-banks .title a'),$('#sle-banks .con'),$('#sle-banks'));
                }else{
                    $(".ui-dialog-cont").find("dl:gt(0)").remove();
                    htmlPublicAccount.insertAfter(select);
                    $('.dialog_input').placeholder();
                    self.setColor();
                    // self.address();
                    $('.infor-list').html($('#provList').html());
                    // 省份选择
                    self.provSelect();
                    // 城市选择
                    self.pullDownSelect($('.dialog_input_city'),$('.city-list'),$('.city-list li'),'city');
                    // 开户支行选择
                    self.pullDownSelect($('.dialog_input_sub_bank'),$('.sub-bank-list'),$('.sub-bank-list li'),'sub-bank');
                    self.documentClick();
                    // 选择银行
                    $('.dialog_bankBox').html($('#banksHtml').html());
                    self.transfer($('.dialog_bank'),$('.card-num-input'),$('#sle-banks .title a'),$('#sle-banks .con'),$('#sle-banks'));

                }
            });
        },
        // 绑定选择银行事件
        transfer : function(sleBank,card,tb2,tc2,bankBox){
            var $icon = $('.sle-bank-btn');
            //弹框内切换
            tb2.click(function(){
                var _t = $(this),
                    _index = _t.index();
                _t.addClass('foc').siblings().removeClass('foc');
                tc2.eq(_index).show().siblings('.con').hide();
            });
            //选择银行
            sleBank.keyup(function(e){
                var e = e || window.event;
                var val = $(this).val();

                if(val) {
                    var n=0;
                    $(this).siblings('.result').find('a').each(function() {
                        if($(this).text().indexOf(val)<0) {
                            $(this).hide();
                        } else {
                            $(this).show();
                            n++;
                        }
                    });
                    if(n>0) {
                        $(this).siblings('.result').show();
                    }
                    $('.dialog_input_sub_bank').val('');
                    $('.lian-num-input').val('');
                    sleBank.siblings('p').hide();
                    sleBank.siblings('p').find('input').val('');
                } else {
                    $(this).siblings('.result').hide();
                }

                e.stopPropagation();
                e.cancelBubble = true;
            });

            sleBank.siblings('.result').find('a').click(function(e) {
                var e = e || window.event;
                if($(this).text()=='其他银行') {
                    sleBank.siblings('p').slideDown();
                }
                sleBank.val($(this).text());
                sleBank.siblings('.result').hide();
                $('.dialog_bank').removeClass('err_input');
                $('.bank_line .warn').hide();
                e.stopPropagation();
                e.cancelBubble = true;
            });


            $('.dialog_bank').bind('blur',vailBankName);
            function vailBankName() {
                var n = 0;
                $('.bank_line .result a').each(function() {
                    var val = $('.dialog_bank').val();
                    if($(this).text()===val) {
                        n++;
                    }
                });
                if(n<=0) {
                    $('.dialog_bank').addClass('err_input');
                    $('.bank_line .warn').show();
                } else {
                    $('.dialog_bank').removeClass('err_input');
                    $('.bank_line .warn').hide();
                }
            }


            $('.select-bank-btn').click(function(e) {
                var e = e || window.event;
                if($icon.hasClass('arrowU')){
                    $icon.removeClass('arrowU');
                    bankBox.hide();
                    return false;
                }else{
                    $icon.addClass('arrowU');
                };             
                var _t = $(this),
                    _x = _t.offset().left,
                    _y = _t.offset().top,
                    _pC = _t.parent().parent().attr('class');
                $('#sle-banks .title a').eq(0).click();
                if(_pC=='s z-index-h'){
                    _bankP ='page';
                }else{
                    _bankP = 'pop';
                }
                //bankBox.css({left:_x-185,top:_y+24}).show();
                bankBox.show();
                _t.siblings('.no-result').hide().siblings('.result');
                e.stopPropagation();
                e.cancelBubble = true;
            });

            bankBox.find('.con a').click(function(){
                if($(this).parent().parent().parent().parent().hasClass('more-infor') || $(this).parent().parent().hasClass('more-infor')){
                    var _t = $(this),
                        _txt = _t.attr('title');
                    $('#support-banks .bank-name').text(_txt);
                    $('#sle-banks').hide();
                }else{
                    $icon.removeClass('arrowU');
                    var _t = $(this),
                        _txt = _t.attr('title');
                    $('#support-banks .bank-name').text();
                    if(_bankP=='page'){
                        $('#c-tab-box .pop-bank').val(_txt);
                        $('#c-tab-box .pop-bank').css({"color":"#333"});
                    }else{
                        $('.dialog_bank').val(_txt);

                        if(_txt==='其他银行') {
                            console.info($('.dialog_bank').siblings("p").attr('class'));
                            $('.dialog_bank').siblings("p").slideDown();
                            // $(".dialog_bank").val("其它银行");
                            $("#sle-banks").slideUp();
                        } else {
                            $('.dialog_bank').siblings("p").hide();    
                        }

                        $('.dialog_bank').css({"color":"#333"});
                        
                    }
                    bankBox.hide();

                    $('.dialog_input_sub_bank').val('');
                    $('.lian-num-input').val('');
                    $('.dialog_bank').removeClass('err_input');
                    $('.bank_line .warn').hide();
                    $('#c-tab-box').find('.no-result').hide();
                    $('#c-tab-box').find('.result').hide();
                    $('.dialog_content').find('.no-result').hide();
                    $('.dialog_content').find('.result').hide();     
                }
            });
            bankBox.find('.close').click(function(){$icon.removeClass('arrowU');bankBox.hide();});
            $('.pop-bank').keyup(function(){
                var _t = $(this)
                    _l = _t.val().length,
                    _rL = _t.parent().find('.result').find('a').length;
                if(!_t.parent().parent().parent().hasClass('chose-funds')){
                    bankBox.hide();     
                }
                if(_rL>6){
                    _t.parent().find('.result').addClass('result-m');
                }
                if(_l==0){
                    _t.parent().find('.no-result').hide().siblings('.result').hide();   
                }
                if(_l==1){
                    _t.parent().find('.result-m').show().siblings('.result').hide();
                }
                if(_l>1)
                {
                    _t.parent().find('.no-result').show().siblings('.result').hide();   
                }
            });
            //银行卡号提示
            $('.card-num-input').keyup(function(){
                $(this).parent().css('z-index',101);
                var _t = $(this),
                    _v = _t.val(),
                    _l = _v.length,
                    _s1,_s2,_s3,_s4,_s5,_s6,_s7,_s8_tipV;
                if(_l > 0){
                    _s1 = _v.substring(0,4);
                    _s2 = _v.substring(4,8);
                    _s3 = _v.substring(8,12);
                    _s4 = _v.substring(12,16);
                    _s5 = _v.substring(16,20);
                    _s6 = _v.substring(20,24);
                    _s7 = _v.substring(24,28);
                    _s8 = _v.substring(28,32);
                    _t.parent().parent().addClass('z-index-h2');
                    _tipV = _s1+" "+_s2+" "+_s3+" "+_s4+" "+_s5+" "+_s6+" "+_s7+" "+_s8;
                    _t.siblings('.card-tip').text(_tipV).show();
                }else{
                    _t.parent().parent().removeClass('z-index-h2');
                    _t.siblings('.card-tip').hide();    
                }
            }).blur(function(){$(this).siblings('.card-tip').hide();$(this).parent().css('z-index',10);$(this).parents("dd").removeClass('z-index-h2')});
            
            //联行号提示
            $('.lian-num-input').keyup(function(){
                $(this).parent().css('z-index',101);
                var _t = $(this),
                    _v = _t.val(),
                    _l = _v.length,
                    _s1,_s2,_s3,_s4,_s5,_s6,_s7,_s8_tipV;
                if(_l > 0){
                    _s1 = _v.substring(0,3);
                    _s2 = _v.substring(3,7);
                    _s3 = _v.substring(7,11);
                    _s4 = _v.substring(11,12);
                    _t.parent().parent().addClass('z-index-h2');
                    _tipV = _s1+" "+_s2+" "+_s3+" "+_s4;
                    _t.siblings('.card-tip').text(_tipV).show();
                }else{
                    _t.parent().parent().removeClass('z-index-h2');
                    _t.siblings('.card-tip').hide();    
                }
            }).blur(function(){$(this).siblings('.card-tip').hide();$(this).parent().css('z-index',10);});
        },
        //设置文本框字体颜色
        setColor:function(){
            var $inputs = $(".dialog_input");
            var color = function(ele){
                var value = $.trim($(ele).val()),
                    placeholdertext = $(ele).attr("placeholder");
                if(value == "" ||　value == placeholdertext ){
                    $(ele).css("color","#999");
                }else{
                    $(ele).css("color","#333");
                }
            };
            $inputs.each(function(){
                color(this);
            })
            $inputs.focus(function(){
                $(this).css("color","#333");
            }).blur(function(){
                color(this);
            });
        },
        // 选择地址
        address : function(){
            //地址选择组件初始化
            var b = $('#citySelect').SnAddress({
                columns: [
                    {state: "prov", text: "请选择省", hide: false, addclass: ""},
                    {state: "city", text: "市", hide: false, addclass: ""}
                ],
                url: 'http://www.suning.com/emall/SNAddressQueryCmd',
                exceptAreas:[],
                flag:false
            }, [
                {name: '', code: '', id: ''},
                {name: '', code: '', id: ''}
            ]).data("suning.address");
        },
       
        //弹框保存按钮事件
        saveForm : function(type){
            var self = this;
            $('.dialog_btn a').click(function(){
                switch (type){
                    case 'public' :
                        //对公 todo
                        var win2 = $.dialog({
                            content : $('#AltSuc').html(),
                            width : 192,
                            maskCss: {              // 遮罩层背景
                                opacity: 0
                            },
                            onShow : function($dialog, callback){
                                setTimeout(function(){
                                    callback.close();
                                },3000)
                            },
                            showClose : false
                        })
                        win2.show();
                        break;
                    case 'person' :
                        //个人 todo
                        var win2 = $.dialog({
                            content : $('#AltSuc').html(),
                            width : 192,
                            maskCss: {              // 遮罩层背景
                                opacity: 0
                            },
                            onShow : function($dialog, callback){
                                setTimeout(function(){
                                    callback.close();
                                },3000)
                            },
                            showClose : false
                        })
                        win2.show();
                        break;
                    case 'company' :
                        //公司 todo
                        var win2 = $.dialog({
                            content : $('#AltSuc').html(),
                            width : 192,
                            maskCss: {              // 遮罩层背景
                                opacity: 0
                            },
                            onShow : function($dialog, callback){
                                setTimeout(function(){
                                    callback.close();
                                },3000)
                            },
                            showClose : false
                        })
                        win2.show();
                        break;
                }
            })
        },
        
        // 表单校验
        checkForm : function(type){
            //todo
        },
        //提现金额校验
        MoneyCheck : function(){
            $('.cash_input').blur(function(){
                //todo
            })
        },
        //删除银行卡
        delConfirm : function(){
            //todo
        },
        //编辑银行卡
        valueSet : function(type,list){
            switch (type){
                case 'public' :
                    //开户公司名称
                    $('.company_name dd').html(list.accountName);
                    //开户银行省市
                    // var b = $('#citySelect').SnAddress({
                    //     columns: [
                    //         {state: "prov", text: list.prov, hide: false, addclass: ""},
                    //         {state: "city", text: list.city, hide: false, addclass: ""}
                    //     ],
                    //     url: 'http://www.suning.com/emall/SNAddressQueryCmd',
                    //     exceptAreas:[],
                    //     flag:false
                    // }, [
                    //     {name: '', code: '', id: ''},
                    //     {name: '', code: '', id: ''}
                    // ]).data("suning.address");

                    $('.dialog_input_prov').val(list.prov);
                    $('.dialog_input_city').val(list.city);
                    //开户银行支行
                    $('.dialog_bankName input').val(list.bankName);
                    break;
                case 'person' :
                    //银行账户名称
                    $('.company_line .dialog_input').val(list.accountName);
                    break;
            }

            //选择银行
            $('.bank_line input').val(list.bank);
        },

        // 省份选择
        provSelect:function() {
            function inforListIn(e) {
                var e = e || window.event;

                $('.pull-down-list').hide();
                $('.infor-list').fadeIn();

                e.stopPropagation();
                e.cancelBubble = true;
            }
            $('.dialog_input_prov').click(inforListIn);
            $('.dialog_input_prov').next().click(inforListIn);

            $('.infor-list .close').click(function(e) {
                var e = e || window.event;
                isShow = true;
                $('.infor-list').fadeOut();
                e.stopPropagation();
                e.cancelBubble = true;
            });

            // 选择省份
            $('.prov-name-list .info-name').click(function(e) {
                var e = e || window.event;
                $('.dialog_input_prov').val($(this).text());
                $('.infor-list').fadeOut();

                $('.dialog_input_city').removeAttr('disabled');
                $('.dialog_input_city').val('');
                $('.dialog_input_sub_bank').attr('disabled','disabled').val('');
                $('.lian-num-input').val('');

                $('.city-list-pull-down').bind('click',bindCityPullDown);
                $('.sub-bank-pull-down').unbind('click');

                e.stopPropagation();
                e.cancelBubble = true;
            });

            function bindCityPullDown(e) {
                var e = e || window.event;
                $('.city-list li').each(function(i) {
                    if((i+1)===$('.city-list li').length) {
                        $(this).hide();
                    } else {
                        $(this).show();
                    }
                });

                $('.infor-list').hide();
                $('.pull-down-list').hide();
                $('.city-list').show();
                e.stopPropagation();
                e.cancelBubble = true;
                return false;
            }
        },

        // document 事件
        documentClick:function() {
            $(document).click(function() {
                $('.dialog_bank').siblings('.result').hide();
                $('.infor-list').hide();
                $('.pull-down-list').hide();    
            });
        },

        // 城市选择与检索
        pullDownSelect : function($input,$list,$lis,str) {
            // 选择城市
            $input.keyup(function() {
                var _this = this;
                var val = $(this).val();
                if(val) {
                    var n = 0;
                    $lis.each(function() {
                        if($(this).text().indexOf(val)<0) {
                            n++;
                            $(this).hide();
                        } else {
                            $(this).show();
                        }
                    });
                    if(n<$lis.length) {
                        $list.show();
                    }
                    
                    if(str==='city') {
                        $('.dialog_input_sub_bank').val('');
                        $('.lian-num-input').val('');
                        $('.sub-bank-pull-down').unbind('click');
                    } else if(str==='sub-bank') {
                        $('.lian-num-input').val('');
                    }
                    
                } else {
                    $list.hide();
                }
            });

            // 绑定解绑  city input 失去焦点事件
            $input.bind('blur',lostBlur);
            $list.mouseenter(function() {
                $input.unbind('blur');
            }).mouseleave(function() {
                $input.bind('blur',lostBlur);
            });

            // 失去焦点事件
            function lostBlur() {
                var val = $input.val();
                if(val) {
                    var n = 0;
                    $lis.each(function() {
                        if(val!=$(this).text()) {
                            n++;
                        }
                    });
                    if(n===$lis.length) {
                        if(str==='city') {
                            $input.addClass('err_input');
                            $('.add_line .warn-city').css('visibility','visible').parent().show();
                        } 
                        // else if(str==='sub-bank') {
                        //     $('.dl-sub-bank .warn').show();
                        // }
                        
                    } 

                    if(str==='sub-bank') {
                        console.info('带出联行号');
                        $('.lian-num-input').val(112345678999);
                    }
                    
                    
                    
                } 
            }

            // var pull_down = str === 'city' ? '.city-list-pull-down' : '.sub-bank-pull-down'
            // // 下来显示所有的城市选项
            // $(pull_down).on('click',function(e) {
            //     var e = e || window.event;
            //     $lis.each(function(i) {
            //         if((i+1)===$lis.length) {
            //             $(this).hide();
            //         } else {
            //             $(this).show();
            //         }
            //     });
            //     $list.show();
            //     e.stopPropagation();
            //     e.cancelBubble = true;
            // });


            $lis.click(function(e) {
                var e = e || window.event;
                var index = $(this).index();
                $list.hide();
                if((index+1)>=$lis.length) {
                    $input.addClass('err_input');
                    if(str==='city') {
                        $('.add_line .warn-city').css('visibility','visible').parent().show();
                    } else if(str==='sub-bank') {
                        $('.dl-sub-bank .warn').show();
                    }
                    
                    return false;
                }
                if(str==='city') {
                    $('.dialog_input_sub_bank').removeAttr('disabled').val('');
                    $('.lian-num-input').val('');
                } else if(str==='sub-bank'){
                    console.info('带出联行号');
                    $('.lian-num-input').val('123456789012');
                } 
                
                $('.sub-bank-pull-down').unbind('click').bind('click',function(e) {
                    // self.pullDownSelect($('.dialog_input_sub_bank'),$('.sub-bank-list'),$('.sub-bank-list li'),'sub-bank');
                    var e = e || window.event;
                    $('.sub-bank-list li').each(function(i) {
                        if((i+1)===$('.sub-bank-list li').length) {
                            $(this).hide();
                        } else {
                            $(this).show();
                        }
                    });

                    $('.infor-list').hide();
                    $('.pull-down-list').hide();
                    $('.sub-bank-list').show();

                    e.stopPropagation();
                    e.cancelBubble = true;
                });


                // $input;
                $input.removeClass('err_input').val($(this).text());
                if(str==='city') {
                    $('.add_line .warn-city').css('visibility','hidden').parent().hide();    
                } else if(str==='sub-bank') {
                    $('.dl-sub-bank .warn').hide();
                }
                
                e.stopPropagation();
                e.cancelBubble = true;
            });
        }
    };
});