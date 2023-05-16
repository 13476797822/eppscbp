require.config({
    paths: {
        jquery: 'jquery-1.8.3.min'
    }
});
define(['jquery'],function($){
	var cache = {cssSupport:{}};
	var Sys = {};
	var ua = navigator.userAgent.toLowerCase();
	var s;
	(s = ua.match(/msie ([\d.]+)/)) ? Sys.ie = s[1] :
	(s = ua.match(/firefox\/([\d.]+)/)) ? Sys.firefox = s[1] :
	(s = ua.match(/chrome\/([\d.]+)/)) ? Sys.chrome = s[1] :
	(s = ua.match(/opera.([\d.]+)/)) ? Sys.opera = s[1] :
	(s = ua.match(/version\/([\d.]+).*safari/)) ? Sys.safari = s[1] : 0;
	return {	
		/*
		* 判断css属性是否支持
		*/
		cssSupports : (function() { 
			var support = cache.cssSupport;
			return function(prop) {
				var div = document.createElement('div'),
				vendors = 'khtml o moz webkit'.split(' '),
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
		getTop: function(e){ 
			var offset = 0, a = jQuery(e);
			//alert(a.css("position"));
			if(e.offsetParent != null){
				 offset = e.offsetTop;
				 offset += arguments.callee(e.offsetParent); 
				 //alert("relative absolute fixed".indexOf(a.css("position")) + "		"+offset);
			}
			else if(e.offsetParent != null){
				 //alert("relative absolute fixed".indexOf(a.css("position")) + "		"+offset);
			}
			return offset; 
		}, 
		getLeft: function(e){ 
			var offset = 0, a = jQuery(e);; 
			if(e.offsetParent != null/* && "relative absolute fixed".indexOf(a.css("position")) == -1*/){
				offset = e.offsetLeft;
				offset += arguments.callee(e.offsetParent);
			}
			return offset; 
		},
		getVersion:function(){
			return Sys;
		},
		// 顶部通知滚动
        initHeadNotice: function() {
            var liLen = $(".head-notice ul li").length;
            if (liLen > 0) {
                var maxTop = (liLen) * - 38;
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
	}
});
