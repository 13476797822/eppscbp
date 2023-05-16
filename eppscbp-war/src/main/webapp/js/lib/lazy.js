define(['jquery','template','public','countUp'], function($,template,SN,CountUp) {
	var cache = {cssSupport:{}};
	var Sys = {};
	var ua = navigator.userAgent.toLowerCase();
	var s;
	(s = ua.match(/msie ([\d]+)/)) ? Sys.ie = s[1] :
	(s = ua.match(/firefox\/([\d]+)/)) ? Sys.firefox = s[1] :
	(s = ua.match(/chrome\/([\d]+)/)) ? Sys.chrome = s[1] :
	(s = ua.match(/opera.([\d]+)/)) ? Sys.opera = s[1] :
	(s = ua.match(/version\/([\d]+).*safari/)) ? Sys.safari = s[1] : 0;
	this.sn = new SN();
	function Lazy(){
		this.init();
	}
	Lazy.prototype = {
		init:function(){
			this.horn();
			this.fixedMenu();
			this.assetToggle();
			this.echart();
			this.helperList();
			this.sectionSwitch();
			if($(".asset-toggle").length>0&&!$(".asset-toggle").hasClass('closed')){
				var endVal = parseFloat($("#totalAssets").text().replace(/,/g,'')).toFixed(2);
				if(endVal>0) this.countUp("totalAssets", 0, endVal, 2, .8);
			}
		},
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
		horn:function(){//公告喇叭图标动画及文本滚动
			setInterval(function(){
				$("#horn").stop(false,true).animate({'width':'15px'},2000,function(){
					$(this).css('width','10px')
				});
			},2000);
			var timer,slider, $nodeTip = $(".note-list");
	        if($nodeTip.find('li').length<2){return false;}
	        slider = function(){
	            var $tipLi = $(".note-list li:first");
	            $tipLi.stop(false,true).animate({'margin-top':-16},800,function(){
	                $(this).removeAttr('style').appendTo($nodeTip);
	            });
	        };
	        timer = setInterval(slider,5000);
	        $nodeTip.on('mouseenter mouseleave',function(evt){
	            if (evt.type==='mouseenter') {
	                clearInterval(timer)
	            }else{
	                timer = setInterval(slider,5000);
	            };
	        });
		},
		fixedMenu:function(){
			if($(".menu-wrapper").length>0){
				var menuTop = $(".menu-wrapper").offset().top;
				$(window).scroll(function(){
					var top = $(document).scrollTop();
					$(".menu-list")[top>menuTop?'addClass':'removeClass']('menu-list-fixed')
				})
			}
		},
		assetToggle:function(){//资产显示隐藏切换
			var $toggleBtn = $(".asset-toggle"),
				$toggleItem = $(".js-bit-toggle"),
				arr = [],
				self = this;
			$toggleItem.each(function(i){
				arr.push($(this).text());
				if(i!=0){
					var Val = parseFloat($(this).text().replace(/,/g,'')).toFixed(2);
					$(this).text(self.formatNumber(Val));
				}
			});
			$toggleBtn.click(function(){
				var flag = $(this).hasClass('closed');
				$(this)[flag?'removeClass':'addClass']('closed');
				if(!flag){
					$toggleItem.text('---');
				}else{
					$toggleItem.each(function(i){
						$(this).text(arr[i]);
					});
				}
			});
		},
		formatNumber:function(nStr) {
	        nStr += '';
	        var x, x1, x2, rgx;
	        x = nStr.split('.');
	        x1 = x[0];
	        x2 = x.length > 1 ? '.' + x[1] : '';
	        rgx = /(\d+)(\d{3})/;
	        if (true) {
	            while (rgx.test(x1)) {
	                x1 = x1.replace(rgx, '$1' + ',' + '$2');
	            }
	        }
	        return x1 + x2;
	    },
		countUp: function($target,startVal,endVal,Decimals,Duration,options){
			new CountUp($target||"totalAssets", startVal||0, endVal, Decimals||2, Duration||.8, options||{
				useEasing : true, 
				useGrouping : true, 
				separator : ',', 
				decimal : '.'
			}).start();
		},
		echart:function(){
			var $ele = $('.echart-toggle'),
				$box = $('.asset-echarts');
			$ele.click(function(){
				var flag = $(this).hasClass('show');
				$(this)[flag?'removeClass':'addClass']('show');
				if(Sys.ie<=7){
					$box[flag?'hide':'show']();
				}else{
					$box.stop(false,true)[flag?'slideUp':'slideDown']();
				};
			});
			$(".asset-echarts .tip a").click(function(){
				var index = $(this).index(),
					title = $(this).text();
				sn.dialog({
					msg:$("#assetExplain"+index).html(),
					width:460,
					title:title,
					ConfirmFun:function(){
						sn.close();
					}
				});
			});
		},
		helperList:function(){
			var $ele = $("#helperList .item:not(:last)"),
				that = this,
				timer;
			$ele.on('mouseenter mouseleave',function(evt){
				var _this = $(this);
				if(evt.type==='mouseenter'){
					timer = setTimeout(function(){
						_this.find('.icon-txt').nextAll().show().removeClass('animated fadeOutDown').addClass('animated fadeInUp');
					},200);
				}else{
					clearTimeout(timer);
					if(that.cssSupports('animation-duration')){
						_this.find('.icon-txt').nextAll().removeClass('fadeInUp').addClass('fadeOutDown');
					}else{
						_this.find('.icon-txt').nextAll().hide();
					}
				}
			});
		},
		helperManage:function(){
			var $ele = $(".helper-manage .icon-txt:not(:first)"),
				timer;
			$ele.on('mouseenter mouseleave',function(evt){
				var _this = $(this);
				if(evt.type==='mouseenter'){
					timer = setTimeout(function(){
						_this.addClass('hover');
					},200);
				}else{
					clearTimeout(timer);
					_this.removeClass('hover');
				}
			});
			var $subEle = $("#subItem"),
				$addEle = $("#addItem"),
				flag = true;
			$subEle.on('click','.icon-txt:not(:first)',function(){
				if (!flag) {return;};
				flag = false;
				$subEle.find('.icon-txt:first')[$subEle.find('.icon-txt:not(:first)').length<=1?'show':'hide']();
				$addEle.find('.icon-txt:first')[$addEle.find('.icon-txt:not(:first)').length<0?'show':'hide']();
				var loca = +$(this).attr('data-loca'),
					_this = $(this).find('.arrow').text('+').end(),
					$addEleItems = $addEle.find('.icon-txt:not(:first)'),
					scrollTop = $(document).scrollTop(),
					cleft = $(this).offset().left,
					ctop = $(this).offset().top-scrollTop,
					$clone = $(this).clone().css({"position":"fixed","top":ctop,"left":cleft,opacity:.5});
					$subEle.append($clone);
					
				if($addEleItems.length==0||loca>+$addEle.find('.icon-txt:last').attr('data-loca')){
					$addEle.append(_this.css("opacity",.5));
					setTimeout(function(){
						cloneMove($clone,_this,scrollTop);
						$addEle.find('.icon-txt').removeClass('hover');
					},100);
					return false;
				}
				$addEleItems.each(function(){
					var tmp = +$(this).attr('data-loca');
					if(tmp>loca){
						$(this).before(_this.css("opacity",.5));
						setTimeout(function(){
							cloneMove($clone,_this,scrollTop);
							$addEle.find('.icon-txt').removeClass('hover');
						},100);
						return false;
					}
				});
			});
			$addEle.on('click','.icon-txt:not(:first)',function(){
				if($(this).index()===0||!flag) return;
				flag = false;
				$subEle.find('.icon-txt:first')[$subEle.find('.icon-txt:not(:first)').length<0?'show':'hide']();
				$addEle.find('.icon-txt:first')[$addEle.find('.icon-txt:not(:first)').length<=1?'show':'hide']();
				var _this = $(this).find('.arrow').text('×').end(),
					scrollTop = $(document).scrollTop(),
					cleft = $(this).offset().left,
					ctop = $(this).offset().top-scrollTop,
					$clone = $(this).clone().css({"position":"fixed","top":ctop,"left":cleft,opacity:.5});
					$addEle.append($clone);
				_this.appendTo("#subItem");
				setTimeout(function(){
					cloneMove($clone,_this,scrollTop);
					$subEle.find('.icon-txt').removeClass('hover');
				},100);
			});
			function cloneMove($clone,$that,scrollTop){
				$clone.animate({
					top:$that.offset().top-scrollTop,
					left:$that.offset().left
				},'200',function(){
					$(this).remove();
					$that.animate({opacity:1},600);
					flag = true;
				});
			}
		},
		sectionSwitch:function(){
			var $ele  = $(".question-section"),
				$input = $ele.find('input'),
				$item = $ele.find('.section'),
				$page = $(".question-section-bottom"),
				$cur = $page.find(".cur"),
				len = $item.length,
				winWidth = $ele.parent().width();
				
			$ele.css('width',winWidth*len);
			$page.on('click','.prev,.next',function(){
				section($(this));
			});
			$input.click(function(){
				var $obj = $ele.find('.item'),
					flag = true;
				$obj.each(function(){
					if($(this).find("input").filter(':checked').length<=0){
						flag = false;
						return;
					};
				});
				$('.question-detail .define-button')[flag?'removeClass':'addClass']('hide');
			});
			function section($obj){
				var cur = parseInt($cur.text());
				if($ele.is(":animated")||$obj.hasClass('disabled')||($obj.hasClass('next')&&cur>=len)) {
					return;
				}
				if($obj.hasClass('prev')){
					$cur.text(cur-1);
					$ele.animate({'margin-left':'+='+winWidth});
				}else{
					$cur.text(cur+1);
					$ele.animate({'margin-left':'-='+winWidth});
				}
				if(parseInt($cur.text())==1){
					$page.find('.prev').addClass('disabled');
					$page.find('.next').removeClass('disabled');
				}else if(parseInt($cur.text())==len){
					$page.find('.prev').removeClass('disabled');
					$page.find('.next').addClass('disabled');
				}else{
					$page.find('.prev').removeClass('disabled');
					$page.find('.next').removeClass('disabled');
				};
			}
		}
	}
	return Lazy;
});