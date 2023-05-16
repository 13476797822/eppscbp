define(function(){
	function slidePic(opt){
		this.opt = opt || {};
		this.box = this.opt.box || "#banner";
		this.handle = this.opt.handle || ".banner-handle";
		this.handleEl = this.opt.handleEl || "span";
		this.list = this.opt.list || "ul";
		this.item = this.opt.item || "li";
		this.turnBtn = this.opt.turnBtn || ".banner-btn";
		this.delay = this.opt.delay || 5000;
		this.activeDelay = this.opt.activeDelay || true;
		this.activeDelayTime = this.opt.activeDelayTime || 300;
		this.speed = this.opt.speed || 500;
		this.auto = this.opt.auto || true;
		this.init();	
	}
	slidePic.prototype = {
		init:function(){
			var _this = this;
			_this.index = 0;
            _this.list = _this.box.find(_this.list);
            _this.box = $(_this.box);
			_this.items = _this.list.find(_this.item);
			_this.turnBtn = _this.box.find(_this.turnBtn);
			_this.handle = _this.box.find(_this.handle);
			_this.initHandler();
			_this.btns = _this.box.find(_this.handle).find(_this.handleEl);
			_this.len = _this.items.length;
			_this.prev = _this.items.eq(0);
			_this.items.eq(0).css({'zIndex':3});
			_this.timeHandle = 0;
	        _this.tiptime = 0;
			_this.start();
		},
		initHandler:function(){
			var self = this,
			len =  self.items.length;
			for(var i=0;i<len;i++){
			self.handle.append("<span></span>");
			}
			self.handle.find(self.handleEl).first().addClass("on");
		},
		start:function(){
			var self = this;
			function startAuto(){
				if(self.timeHandle){
					clearInterval(self.timeHandle);
				}
				self.timeHandle = setInterval(function(){
					auto();
				},self.delay);
			}
			self.btns.hover(function(e){
				var _this = $(this),
					_index = _this.index();
				var e = e || window.event;
				if(e.stopPropagation){
					e.stopPropagation();
				}else{
					e.cancelBubble = true;
				}
       		   clearTimeout(self.tiptime);
			   clearInterval(self.timeHandle);
				if(e.isTrigger == true){
					show(_index);
				}else{
					self.tiptime = setTimeout(function(){
						show(_index);
					} ,self.activeDelayTime);
				}
			}, function(){
       		    clearTimeout(self.tiptime);
				startAuto();
			});
			startAuto();
			self.items.hover(function(){
				clearInterval(self.timeHandle);
			},function(){
				startAuto();
			});
			self.box.hover(function(){
				if($.browser.msie &&  $.browser.version > 8){
					self.turnBtn.stop(true, true).fadeIn("fast");
				}
				else{
					self.turnBtn.show();				
				}
			},function(){
				if($.browser.msie &&  $.browser.version > 8){
					self.turnBtn.stop(true, true).fadeOut("fast");
				}
				else{
					self.turnBtn.hide();		
				}
			});
			self.turnBtn.live({
				'mouseover' : function(){
					if(self.timeHandle)clearInterval(self.timeHandle);
					self.tiptime = 0;
					self.timeHandle = 0;
					var el = $(this);
					if(el.hasClass('banner-prev')){
						el.addClass('banner-prev-hover');
					}
					else{
						el.addClass('banner-next-hover');
					}
				},
				'click' : function(){
					var _thisTurn = $(this).hasClass('banner-prev'),
					_itemsIndex = findItem(),
					_itemsLen = self.items.length - 1;
					if(_thisTurn){
						var preIndex = _itemsIndex == 0 ? _itemsLen : _itemsIndex - 1;
						show(preIndex);
					}else{
						var nextIndex = _itemsIndex == _itemsLen ? 0 : _itemsIndex + 1;
						show(nextIndex)
					}
				},
				'mouseleave' : function(){
					var el = $(this);
					if(el.hasClass('banner-prev')){
						el.removeClass('banner-prev-hover');
					}
					else{
						el.removeClass('banner-next-hover');
					}
					startAuto();
				}
			});
			function show(_index){
				if(self.prev.index() == _index){
						return;
					}
					self.items.css({'zIndex':'1'});
					self.prev.css({'zIndex':'2'});
					var _thisImageSrc = self.items.eq(_index).attr("data-backgroundImage");
					if(_thisImageSrc){
						self.items.eq(_index).css("background-image","url("+_thisImageSrc+")").removeAttr('data-backgroundImage');
					}
					self.items.eq(_index).css({'opacity':0,'zIndex':'3'}).stop().animate({opacity:1},self.speed);
	                self.btns.removeClass('on').eq(_index).addClass('on');
	                self.index = _index;
	                self.prev =  self.items.eq(_index);
			}
			function auto(){
				self.index = self.index>=(self.len-1)?self.index=0:self.index+=1;
				self.btns.eq(self.index).trigger('mouseover').trigger('mouseleave');
			}
			function findItem(){
				return self.index;
			}
		}
	};
	return slidePic;
});