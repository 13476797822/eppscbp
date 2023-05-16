define(function() {
	// 埋点代码脚本
    function _loadAsyncJs(src){                             
          var _src = src; 
          var _scripts = document.getElementsByTagName( 'script' ); 
          for ( var i = 0; i < _scripts.length; i++){ 
                if (_scripts[i].src==_src){ 
                    return ; 
                } 
          } 
          var _script = document.createElement( 'script' ); 
          _script.type = 'text/javascript' ; 
          _script.async = true ; 
          _script.src = _src; 
          var _s = _scripts[0]; 
          _s.parentNode.insertBefore(_script, _s); 
    } 
    function _getJsFilePath(js_file){ 
         var _hostName = document.location.hostname; 
         // 一般生产环境的域名                             
         var _prd_reg = /^\w*?.suning.com$/; 
         // 一般 pre 环境的域名 
         var _pre_reg = /^\w*?pre.cnsuning.com$/; 
         // 一般 sit 环境的域名 
         var _sit_reg = /^\w*?sit.cnsuning.com$/; 
         var sa_src = "" ; 
         if (_prd_reg.test(_hostName)){ 
                sa_src = ( "https:" == document.location.protocol) ? "https://imgssl.suning.com" : "http://script.suning.cn";
         } else if (_pre_reg.test(_hostName)){
                sa_src = ( "https:" == document.location.protocol) ? "https://preimgssl.suning.com" : "http://prescript.suning.cn";
         } else if (_sit_reg.test(_hostName)){ 
               sa_src = ( "https:" == document.location.protocol) ? "https://sit1imgssl.suning.com" : "http://sit1script.suning.cn";
         } else { 
               sa_src = ( "https:" == document.location.protocol) ? "https://preimgssl.suning.com" : "http://prescript.suning.cn";
         } 
         sa_src = sa_src + "/javascript/sn_da/" +js_file; 
         return sa_src; 
    }
    // 埋点代码脚本  
 
    //JS懒加载start
    function isArray(obj) {
        return Object.prototype.toString.call(obj) === '[object Array]';
    }
    var windowOnLoadEventQueue = [];
    var scriptOnLoadEventQueue = [];
    window.onload = function() {
        for ( var aFunc in windowOnLoadEventQueue) {
            windowOnLoadEventQueue[aFunc]();
        }
    }
    function addOnLoad(func) {
        windowOnLoadEventQueue = windowOnLoadEventQueue.concat(func);
    }
    var lazyScriptMap = {};
    function lazyLoadScript(src, callback) {
        if (!lazyScriptMap[src]) {
            lazyScriptMap[src] = callback;
            var scriptNode = document.createElement("script");
            if ('function' === typeof callback) {
                if (!/msie/i.test(navigator.userAgent.toLowerCase())){
                    scriptNode.onload = callback;
                }
                scriptNode.onreadystatechange = function() {
                    if ("loaded" == scriptNode.readyState
                            || "complete" == scriptNode.readyState) {
                        callback();
                    }
                }
            } else if (isArray(callback)) {
                var callbackSequence = function() {
                    for ( var i = 0; i < callback.length; i++) {
                        (callback[i])();
                    }
                };
                scriptNode.onload = callbackSequence;
                scriptNode.onreadystatechange = function() {
                    if ("loaded" == scriptNode.readyState
                            || "complete" == scriptNode.readyState) {
                        callbackSequence();
                    }
                }
            }
            scriptNode.type = "text/javascript";
            scriptNode.src = src;
            var scriptContainer = document.getElementsByTagName("head")[0];
            scriptContainer.appendChild(scriptNode);
        } else {
        }
    }
    function lazyLoadScripts(srcs, callback) {
        var srcNum = srcs.length;
        var loadingProgress = 0;
        if (srcNum > 0) {
            for ( var i = 0; i < srcNum; i++) {
                var currSrc = srcs[i];
                lazyLoadScript(currSrc, function() {
                    loadingProgress++;
                    if (srcNum == loadingProgress) {
                        if ('function' === typeof callback) {
                            callback();
                        } else if (isArray(callback)) {
                            for ( var i = 0; i < callback.length; i++) {
                                (callback[i])();
                            }
                        }
                    }
                });
            }
        }
    }
    var isTimeout = false;
    var lazyLoadFunction = null;
    var lazyScriptLoaded = false;
    var lazyScriptTimeout = 2000; //默认的超时时间2秒，这个数字2是业务部或用户体验部门来提出要求。
    lazyLoadFunction = function() {
        lazyScriptLoaded =true;//flag置为true
        _loadAsyncJs(_getJsFilePath("da_opt.js"));
    }
    function checkLazyScriptTimeout(){
        isTimeout = true;
        if(!lazyScriptLoaded){//检测lazy script是否已经加载
            if(!!lazyLoadFunction){//检测下函数空间，以免servlet还没加载完全
                lazyLoadFunction();//
            }else{
                setTimeout(checkLazyScriptTimeout, 1000);//在servlet还没加载完全的情况下，每过1秒，重新检测一次
            }
        }
    }
    setTimeout(checkLazyScriptTimeout, lazyScriptTimeout);
    addOnLoad(lazyLoadFunction);
    //JS懒加载end---------------------------------------------------------------------------------------------------------------
});
