    //用户超时或者登出，将当前表单提交给当前的url
    function submit2CurrentUrl(){
        var form = $('<form action="'+window.location.href+'"></form>');
        form.appendTo(document.body);
        form.submit();
    }
    function probeAuthStatus(hasLoginCallback) {
        $.ajax({
            url: sysconfig.ctx + '/authStatus',
            crossDomain: true,
            cache: false,
            dataType: 'jsonp',
            //async: false,
            //dataType: 'json',
            success: function (data) {
                if (data.hasLogin) {
                    hasLoginCallback();
                } else {
                    //用户超时或者登出，将当前表单提交给当前的url
                    submit2CurrentUrl();
                    //form.jsonpClose();
                }
            }
        });
    }
    /**
     * 同域 Ajax请求
     * @param url 请求URL
     * @param param 请求参数
     * @param success 成功回调
     */
    function authorAjax(options) {
        var opt = $.extend({
            type: "post",
            cache: false
        }, options);
        opt.success = function(result){
            if (result.idsIntercepted) {
                var requestAgain = function () {
                    authorAjax(options);
                };
                // 当前请求的资源是GATEWAY访问策略，
                // 且当前登录状态为UNKNOWN，则检查用户登录状态
                if (result.policy == "GATEWAY" && result.status == "UNKNOWN") {
                    probeAuthStatus(requestAgain);
                    return;
                }
                // 当前请求的资源是RESTRICTED策略
                if (result.policy = "RESTRICTED") {
                    if (result.status == "ANONYMOUS") {
                        // 且当前用户未登录
                        //window.location.href = window.location.href;
                        submit2CurrentUrl();
                        return;
                    } else if (result.status == "UNKNOWN") {
                        // 当前登录状态为UNKNOWN，首先拉取登录状态，
                        // 根据获得的登录状态进行不同处理
                        probeAuthStatus(requestAgain);
                        return;
                    }
                }
                return;
            }
            if(options.success)options.success(result);
        };
        $.ajax(opt);
    }





