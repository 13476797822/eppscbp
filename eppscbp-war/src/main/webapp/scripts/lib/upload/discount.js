define(['jquery','ajaxfileupload'], function($) {
var discountWin1 = {
    scrollInfo: function() {
        var popBoxMacEl = document.getElementById("popBoxMac");
        if (popBoxMacEl) {
            popBoxMacEl.style.top = (document.documentElement.scrollTop || document.body.scrollTop) + 150 + "px";
        }
    },

    resizePopInfo: function() {
        var maskEl = document.getElementById("popMask");
        var bodyEl = document.getElementsByTagName("body")[0];
        var popBoxMacEl = document.getElementById("popBoxMac");
        if (maskEl && popBoxMacEl){ 
            var width = popBoxMacEl.clientWidth;
            popBoxMacEl.style.marginLeft = "-" + (width / 2) + "px";

            maskEl.style.height = document.documentElement.clientHeight + "px";
            
            // 提示框top
            var infoTop = 0;
            var heightTmp = maskEl.clientHeight - popBoxMacEl.clientHeight;
            if(heightTmp > 0) {
                infoTop = heightTmp / 2;
            }
            discountWin1.scrollInfo();
        }
    },

    hideWin: function() {
        var bodyEl = document.getElementsByTagName("body")[0];
        var maskEl = document.getElementById("popMask");
        var popBoxMacEl = document.getElementById("popBoxMac");
        if (maskEl) {
            bodyEl.removeChild(maskEl);
        }
        if (popBoxMacEl) {
            bodyEl.removeChild(popBoxMacEl);
        }
    },

    showWin: function(target) {
        this.hideWin();
        
        var maskEl = document.createElement("div");
        var bodyEl = document.getElementsByTagName("body")[0];
        
        maskEl.className = "pop-mask";
        maskEl.id = "popMask";
        bodyEl.appendChild(maskEl);
        
        var infoEl = document.createElement("div");
        infoEl.className = "pop-box-mac";
        infoEl.id = "popBoxMac";
        infoEl.innerHTML =  '<div class="pop-bg"></div>' +
            '<div class="pop-con">' +
                '<div class="pop-title">' +
                    '<b>上传附件</b>' +
                    '<a href="javascript:void(0);" onclick="return false;" class="close">×</a>' +
                '</div>' +
                '<div class="present">' +
                '<div class="upload-area">' +
                    '<input class="file-name" disabled="disabled">' + 
                    '<div class="upload-wrap">' +
                        '<input type="file" id="fileDom" size="0">' +
                    '</div>'+
                    '<div class="err-msg"></div>'+
                    '<a class="select-file" href="javascript:void(0);" onclick="return false;">浏览</a>' +
                    '<a class="upload-btn" href="javascript:void(0);" onclick="return false;">上传</a>' +
                '</div>' +
                '</div>' +
                '<div class="clear"></div>' +
            '</div>';

        bodyEl.appendChild(infoEl);
        uploaddetail(target);
        this.resizePopInfo();
        this.initConfirmApply();
        this.initConfirmCancel();
    },

    // 线下还款通知
    showOfflineWin: function() {
        this.hideWin();
        
        var maskEl = document.createElement("div");
        var bodyEl = document.getElementsByTagName("body")[0];
        
        maskEl.className = "pop-mask";
        maskEl.id = "popMask";
        bodyEl.appendChild(maskEl);
        
        var infoEl = document.createElement("div");
        infoEl.className = "pop-box-mac";
        infoEl.id = "popBoxMac";
        infoEl.style.width = "480px";
        infoEl.style.height = "342px";
        infoEl.innerHTML = 
            '<div class="pop-bg"></div>' +
            '<div class="pop-con">' +
                '<div class="pop-title">' +
                    '<b>信息确认</b>' +
                    '<a href="javascript:void(0);" onclick="return false;" class="close">×</a>' +
                '</div>' +
                '<div class="present" style="width:480px;height:304px;">' +
                    '<table style="margin:34px 0 0 138px;">' +
                        '<tr>' +
                            '<td><em style="letter-spacing:4px;">回单号</em>：</td>' +
                            '<td class="val">' +
                                '<span class="num" id="receiptNo">123546789101112</span>' +
                            '</td>' +
                        '</tr>' +
                        '<tr>' +
                            '<td>汇款银行：</td>' +
                            '<td class="val">' +
                                '<span id="bank">上海银行</span>' +
                            '</td>' +
                        '</tr>' +
                        '<tr>' +
                            '<td>汇款帐号：</td>' +
                            '<td class="val num" id="remittanceAccount">6227001100750070123</td>' +
                        '</tr>' +
                        '<tr>' +
                            '<td>还款日期：</td>' +
                            '<td class="val num" id="repaymentDay">2015.06.02</td>' +
                        '</tr>' +
                        '<tr>' +
                            '<td>还款金额：</td>' +
                            '<td class="val"><span class="money" id="repaymentAmount">500,000.00</span>元</td>' +
                        '</tr>' +
                    '</table>' +
                    '<a class="apply-btn" style="margin-left:139px;" href="javascript:void(0);" onclick="return false;">确认提交</a>' +
                    '<a class="cancel-btn" href="javascript:void(0);" onclick="return false;">取&nbsp;消</a>' +
                '</div>' +
                '<div class="clear"></div>' +
            '</div>';

        bodyEl.appendChild(infoEl);
        discountWin1.setOfflineVal();
        discountWin1.resizePopInfo();
        discountWin1.initConfirmApply();
        discountWin1.initConfirmCancel();
    },

    setOfflineVal: function() {
        // TODO 设置弹出框内容
    },

    // 弹出框的确认提交
    initConfirmApply: function() {
        $(".present .apply-btn").click(function() {
            // TODO 确认提交
        });
    },

    // 弹出框的取消
    initConfirmCancel: function() {
        $(".present .cancel-btn, .pop-box-mac .close").click(function() {
            discountWin1.hideWin();
        });
    }
};

window.onresize = discountWin1.resizePopInfo;
window.onscroll = discountWin1.scrollInfo;

function uploaddetail(target){
    var self=target;
    $("#popBoxMac .upload-btn").click(function() {
    
    // 如果要移除报错信息用下面的表达式
    // $("#popBoxMac .err-msg").hide();
    
    var fileName = $("#popBoxMac .file-name").val();
    // 如果没有选择文件，不允许上传文件
    if (fileName) {
        var dotIndex = fileName.lastIndexOf(".");
        // 后缀名
        var ext = fileName.substring(dotIndex + 1);
        // 判断是否为excel文件
        if (ext === "jpg" || ext === "rar") {
            self.hide();
            self.siblings('.upld-tips').hide();
            self.siblings('.uping').show();
            
            $.ajaxFileUpload({
                url: '',//用于文件上传的服务器端请求地址
                secureuri:false,//是否需要安全协议，一般设置为false
                fileElementId: 'fileDom',//文件选择框的id属性
                dataType:'json',//服务器返回的格式，可以是json
                success: function (data,status){//服务器成功响应处理函数
                    // 如果不能成功上传
                    if (true) {
                        self.siblings('.upload-tip').find('.up-name').html(data.name);
                        self.siblings('.uping').hide().siblings('.upload-tip').show();
                    }
                },
                error: function(data){

                }
            });
            // 上传成功后提示信息
            $("#popBoxMac .err-msg").hide();
            discountWin1.hideWin();
            
        } else {
            // 在报错信息用以下方式显示
            $("#popBoxMac .err-msg").show().html("上传内容与要求不符，请重新上传");
        }
    } else {
        // 在报错信息用以下方式显示
        $("#popBoxMac .err-msg").show().html("请选择文件");
    }
    });
            /* ie8 不支持
            $("#fileDom").live('change', function() {
                $(".file-name").val($(this).val());
            });*/
            // 为了支持ie8
    document.getElementById("fileDom").onchange = function() {
            $(".file-name").val(this.value);
    }
}

$('.mbtn-upld').click(function(){
    discountWin1.showWin($(this));
});
$('.delet-file').click(function(){
    var parent=$(this).parent().hide();
    parent.siblings('.mbtn-upld').show();
});



});