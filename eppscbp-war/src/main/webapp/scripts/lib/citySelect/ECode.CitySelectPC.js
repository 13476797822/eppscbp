/**
 * 	Upgrade By gujun @ 2013.08.22
 *	V1.0
 */
var ECode = ECode||{};
ECode.CitySelectPC = function() {
    //城市切换总控制元素
    var city = $(".J-citybox"), //初始不可见包裹容器
        cityBtn = $(".J-citybox_btn"), //初始可见城市选择
        cityData = $(".J-cityData"), //省、市、区数据载入容器
        pArea = city.find(".chooseArea").find("p"); //选择省市元素
    var ref; //this
    var cb = null;
    return {
    	//
        init: function(callback) {
        	if(arguments.length > 0){
        		cb = callback;
        	}
            ref = this;
            //激活选择省面板，toggle功能
            cityBtn.click(function(e) {
                if(city.css('display')=="none" || city.hasClass("hide")){
                    $(this).addClass('select');
                    city.removeClass("hide").show();
                    //fix for ie6
                    if($.browser.msie && ($.browser.version == "6.0")){
                    	cityData.css("zoom", 1)
                    }
					pArea.eq(0).click();
                }else{
                    $(this).removeClass('select');  
                    city.hide().addClass("hide");
                }
                e.stopPropagation();                           
                e.preventDefault();
            })
			city.click(function(e){
				e.stopPropagation();
			})
			$(document).click(function(){
				if(city.css('display') == "block"){
					ref.reset();
				}
	            return;
			});
			//内部省市区切换
            pArea.click(function(){
            	var index = $(this).index();
            	if($(this).hasClass("disable")){
            		return;
            	}
            	$(this).addClass("cur").siblings().removeClass("cur");
            	city.find(".arriveBox").show();
            	cityData.find("div.city_show").eq(index).show().siblings().hide();
            	if($("#temp_iframe").length > 0){
            		$("#temp_iframe").height(city[0].offsetHeight);
            		//return;
            	}
            	//fix for ie6 select
            	if($.browser.msie && ($.browser.version == "6.0") && $("#temp_iframe").length < 1){
                    var iframe = document.createElement("iframe");
                    iframe.id = "temp_iframe";
                    city.after(iframe);
                    $(iframe).css({
                        width : city.width(),
                        height : city[0].offsetHeight,
                        position : "absolute",
                        "z-index" : 10,
                        opacity : 0,
                        top : 25, // 微调数据
                        left : 75  //微调数据
                    });
                }
                if($(this).hasClass("loaded")){
                	return;
                }
                ref.getAjaxData($(this).attr("data-url"));
            });
            this.choose();
			/*关闭按钮*/
			$('div.closeSelector').click(function(){
				city.addClass('hide').hide();
				cityBtn.removeClass('select');
				//ref.reset();
			});
        },
        /**
         * [choose description]
         * 选择省市区
         */
        choose: function(){
        	var pCur;
        	var p1 = pArea.eq(0), p2 = pArea.eq(1);
        	//绑定方式后期优化
        	cityData.find("a").click(function(e){
	        	pCur = city.find(".chooseArea").find("p.cur").index();
            	e.preventDefault();
            	if(this.href.indexOf("javascript") > -1){
            		alert("很抱歉，西藏目前无法送达");
            		return;
            	}
            	//直辖市
            	if($(this).attr("data-city") == "true"){
            		p1.find('span').text($(this).text());
            		cityBtn.find(".ptext").show().text(p1.find('span').text());
            		cityBtn.find(".ctext").text('').hide().next('em').hide();
					ref.reset();
            		return;
            	}
				if(pCur == 0){
					p1.find('span').text($(this).text());
					p2.find('span').text('请选择市/区');
            		p2.addClass("disable").attr("data-url", $(this).attr("href"));
					p2.addClass("cur").siblings().removeClass("cur");
					cityData.find("div.city_show").eq(1).show().siblings().hide();
				}
            	//最后一步市
            	if(pCur == 1){
            		/**
            		 * 最后执行回调作用域
            		 */
            		p2.find('span').text($(this).text());
            		cityBtn.find(".ctext").show().text(p2.find('span').text());
            		cityBtn.find(".ptext").text(p1.find('span').text());
            		ref.reset();
            		//alert("你选择了 [" +pArea.eq(0).text()+pArea.eq(1).text()+pArea.eq(2).text() + "]. 城市选择操作展示完毕！");
            		if(typeof cb == "function"){
            			//内可以传参
            			cb(this.innerHTML);
            		}
            		return;
            	}
            	ref.getAjaxData(this.href);
            });
        },
    	//异步获取省市区的数据
    	//数据源格式定义为
    	//C9183常州市||H9181淮安市||L9182连云港市||N9173南京市||N9177南通市||S9176苏州市||S9185宿迁市||T9184泰州市||W9174无锡市||X9180徐州市||Y9178扬州市||Y9179盐城市||Z9175镇江市 
        getAjaxData: function(url) {
        	var index = city.find(".chooseArea").find("p.cur").index();
        	if(index == 0){
		    	return;
		    }
	        var re = /([A-Za-z]+)([0-9]+)(.[^\|]+)()/g;
	        var arr = []  //
	        var s = null; //
	        var template = '';
		    cityData.find("div.city_show").eq(index).html('<tr><td>加载中...</td></tr>');
	        $.get(url, function(data) {
	        	while(s = re.exec(data)) {
	                //print_r >> ["Z9175镇江市", "Z", "9175", "镇江市", ""]
	                arr.push([s[2],s[3]]);
	            }
	            for(var i = 0;i<arr.length; i++){
	                template += '<span><a href="data/' + arr[i][0] + '.html">' + arr[i][1] + '</a></span>';
	            }
	           // template = template + '</tr>';
	    		cityData.find("div.city_show").eq(index).html(template);
	    		cityData.find("a").unbind("click");
	    		ref.choose();
	           	pArea.eq(index).removeClass("disable").addClass("loaded");
	           	$("#temp_iframe").height(city[0].offsetHeight);
	        });
        },
        //初始化
        reset: function(){
            city.hide();
	        $("#temp_iframe").remove();
	        cityBtn.removeClass('select');
	        pArea.removeClass("cur");
	        cityData.hide();
	        //fix for ie6
	        if($.browser.msie && ($.browser.version == "6.0")){
            	cityData.css("zoom", 0)
            }
        }
    }
}
