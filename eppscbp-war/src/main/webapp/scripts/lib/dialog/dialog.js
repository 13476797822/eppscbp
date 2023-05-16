/** 
 * ==========================================================
 * Copyright (c) 2014, suning.com All rights reserved.
 * Dialog弹窗组件
 * Version: 1.1.0
 * Author: ghj
 * Date: 2015-07-08 16:39:16 385372
 * ==========================================================
 */
;(function($, window, document, undefined) {
	'use strict';
	var defaults = {
		title: '',				// 标题
		content: '',			// 内容
		custom: '',				// 自定义内容
		esc: true,              // 按ESC键关闭弹窗
		blur: false,			// 背景高斯模糊
		modal: true,			// 显示遮罩
		fixed: true,			// 固定在页面中间
		fadeIn: 150,			// 渐显时间
		fadeOut: 150,			// 渐隐时间
		width: 450,				// 宽度
		zIndex: 1001,			// zindex
		onMask: $.noop, 		// todo
		onShow: $.noop,			// 显示回调函数，返回$dialog, callback
		onClose: $.noop,		// 关闭回调
		onReset: $.noop,		// 重新计算位置
		css: {},				// css
		maskCss: {},            // 遮罩层背景
		footCss: {},			// 底部css
		showClose: true,		// 显示右上角关闭按钮
		draggable: true, 		// todo
		buttonsAlign: 'right',	// 按钮对齐
		buttons: [				// 按钮
			/*{
				value: '取消',
				className: 'btn',
				iconClass: '',
				css: {},
				disabled: false,
				callback: $.noop
			}, {
				value: '确认',
				className: 'btn',
				iconClass: '',
				css: {},
				disabled: false,
				callback: $.noopbeforeShow
			}*/
		]
	};
	var isIE6 = !('minWidth' in $('html')[0].style);
	var HTML = '<div class="ui-dialog-wrap"><div class="ui-dialog-outer"><div class="ui-dialog-inner"><div class="ui-dialog-cont"></div></div></div></div>',
		HEAD = '<div class="ui-dialog-head"><div class="ui-dialog-title"></div><a class="ui-dialog-close">×</a></div>',
		FOOT = '<div class="ui-dialog-foot"></div>',
		MASK = '<div class="ui-dialog-mask"></div>';

	function Dialog(options) {
		this.$body = $('body');
		this.$html = $(HTML);
		this.$head = $(HEAD);
		this.$cont = $('.ui-dialog-cont', this.$html);
		this.$foot = $(FOOT);
		this.$mask = $(MASK);

		this.opts = $.extend(true, {}, defaults, options || {});

		this.render();
	};

	Dialog.prototype = {
		constructor: Dialog,

		/** 
		 * 页面渲染
		 * time: 2014-11-10 15:38:34
		 */
		render: function() {
			this.create();
			this.events.close.call(this);
			this.events.keyup.call(this);
			this.events.resize.call(this);
		},

		/** 
		 * 事件绑定
		 * time: 2014-11-10 15:38:37
		 */
		events: {
			/** 
			 * 右上角关闭事件
			 * time: 2014-11-28 16:15:34
			 */
			close: function() {
				var that = this;
				var $close = $('.ui-dialog-close', this.$head);
				if ($close.length < 1) return;
				$close.click(function() {
					that.close();
				});
			},

			/** 
			 * 按ESC键退出弹窗
			 * time: 2015-07-08 16:07:04
			 */			
			keyup: function() {
				var that = this;
				var esc = this.opts.esc;
				if (typeof esc === 'boolean' && esc || esc === 'true') {
					this.$body.keyup(function(e) {
						if (e.keyCode === 27) {
							that.close();
						}
					});
				}
			},

			/** 
			 * 改变窗口时重置蒙板/弹窗位置
			 * time: 2014-11-28 16:15:54
			 */
			resize: function() {
				var that = this;
				$(window).resize(function() {
					that.reset();
					that.mask();
				});
			}

			/*drag: function() {
				var that = this;
				var $document = $(document),
					start;
				this.$head.mousedown(function(e) {
					var x = e.pageX,
						y = e.pageY,
						offset = that.$html.offset(),
						maxTop = 0,
						maxRight = $(window).width() - that.$html.outerWidth(),
						maxBottom = $(window).height() - that.$html.outerHeight(),
						maxLeft = 0;

					start = function() {
						$document.on('mousemove', function(e) {
							var style = that.$html[0].style;
							var left = Math.max(Math.min(e.pageX - (x - offset.left), maxRight), maxLeft),
								top = Math.max(Math.min(e.pageY - (y - offset.top), maxBottom), maxTop);
							style.left = left + 'px';
							style.top = top + 'px';
						});
					};
					start();
				});
				this.$head.mouseup(function() {
					start = null;
					$document.off('mousemove');
				});
			}*/
		},

		create: function() {
			this.head();
			this.cont();
			this.foot();
			this.mask();
		},

		// 显示弹窗
		show: function() {
			var that = this;		

			this.showMask();
			this.showCont();
			this.blur(true);
			this.reset();

			this.opts.onShow.call(this.$html, this.$html, {
				close: function() {
					that.close();
				},
				reset: function() {
					that.reset();
				}
			});
		},
		
		// 关闭弹窗
		close: function() {
			var that = this;
			this.$html.fadeOut(this.opts.fadeOut, function() {
				$(this).remove();
			});
			this.$mask.fadeOut(this.opts.fadeOut, function() {
				$(this).remove();
			});
			this.blur(false);

			this.opts.onClose.call(this.$html);
		},

		// 计算弹窗位置
		reset: function() {
			var $window = $(window),
				fixed = this.opts.fixed,
				position = (!isIE6 && (fixed === 'true' || typeof fixed === 'boolean' && fixed)) ? 'fixed' : 'absolute',
				scrollTop = (position === 'absolute' && $window.scrollTop() > 0) ? $window.scrollTop() : 0,
				left = ($window.width() - this.$html.outerWidth()) / 2,
				top = ($window.height() - this.$html.outerHeight()) / 2 + scrollTop,
				css = $.extend({}, {
					left: left,
					top: top
				}, this.opts.css);

			this.$html.css(css);
		},

		// 自定义内容
		custom: function() {
			var custom = this.opts.custom;

			if (typeof custom === 'string' && custom) {
					custom = custom;
				} else if (typeof custom === 'object' && custom[0] && custom[0].innerHTML) {
					custom = custom[0].innerHTML;
				} else {
					custom = '';
				}

			return custom;
		},

		// 背景遮罩
		mask: function() {
			var css = this.opts.maskCss;
			var width = document.body.clientWidth <= document.body.scrollWidth ? document.body.clientWidth : document.body.scrollWidth;
			css = Object.prototype.toString.call(css) === '[object Object]' ? css : {};
			css = $.extend({}, {
				zIndex: this.opts.zIndex - 1,
				// width: width,
				height: $(document).height()
			}, css);
			this.$mask.css(css);
		},

		// 背景高斯模糊
		blur: function(flag) {
			var blur = this.opts.blur;
			if (typeof blur === 'boolean' && blur || blur === 'true') {
				if (flag) {
					this.$html.siblings().not('.ui-dialog-wrap, .ui-dialog-mask').addClass('ui-dialog-blur');
				} else {
					this.$html.siblings().not('.ui-dialog-wrap, .ui-dialog-mask').removeClass('ui-dialog-blur');
				}
			}
		},

		// 显示遮罩层
		showMask: function() {
			var modal = this.opts.modal,
				$iframe = $('<iframe style="position:absolute;left:0;top:0;border:0;filter:alpha(opacity=0);width:100%;height:100%;z-index:0;" src="javascript:false;"></iframe>');
			modal = (typeof modal === 'boolean' && modal) || (modal === 'true') ? true : false;
			if (!modal) return;

			if (isIE6) {
				this.$mask.html($iframe);
			}

			this.$body.append(this.$mask);

			var opacity = this.$mask.css('opacity');

			this.$mask.css({
				opacity: 0
			}).show().animate({
				opacity: opacity
			}, this.opts.fadeIn);
		},

		// 显示内容
		showCont: function() {
			var modal = this.opts.modal,
				timer = (modal === 'true' || typeof modal === 'boolean' && modal) ? this.opts.fadeIn : this.opts.fadeIn;
			this.$body.append(this.$html.fadeIn(timer));
		},

		/** 
		 * head内容
		 * time: 2014-11-28 16:07:41
		 */
		head: function() {
			var title = this.opts.title,
				close = this.opts.showClose;

			if (this.custom()) return;

			if (typeof title === 'string' && !title) return;
			this.$head.find('.ui-dialog-title').html(title);

			if ((typeof close === 'boolean' && !close) || (close === 'true')) {
				this.$head.find('.ui-dialog-close').remove();
			}

			this.$cont.before(this.$head);
		},

		/** 
		 * cont内容
		 * time: 2014-11-28 16:08:43
		 */
		cont: function() {
			// set css
			var fixed = this.opts.fixed;
			var pos = (!isIE6 && (fixed === 'true' || typeof fixed === 'boolean' && fixed)) ? 'fixed' : 'absolute',
				css = this.opts.css;
			this.opts.css = $.extend({
				width: this.opts.width,
				zIndex: this.opts.zIndex,
				position: pos
			}, css);

			this.$html.css(this.opts.css);

			// set content
			var content = this.opts.content;

			if (this.custom()) {
				this.$html.html(this.custom());
			} else {
				if (typeof content === 'string' && content) {
					content = content;
				} else if (typeof content === 'object' && content[0] && content[0].innerHTML) {
					content = content[0].innerHTML;
				} else {
					content = '';
				}

				this.$cont.html(content);
			}
		},

		/** 
		 * foot内容
		 * time: 2014-11-28 16:09:00
		 */
		foot: function() {
			if (this.custom()) return;

			var that = this;
			var btns = this.opts.buttons;
			if (Object.prototype.toString.call(btns) === '[object Array]' && btns.length > 0) {
				$.each(btns, function() {
					that.button(this);
				});				
			}
		},

		/** 
		 * foot按钮
		 * @param {obj} [btn] [{value:'文字', className:'btn', iconClass:'', disabled:false, callback: $.noop}]
		 * time: 2014-11-28 16:09:20
		 */
		button: function(btn) {
			var that = this;
			if (typeof btn.value === 'string' && !btn.value) return false;
			var disabled = typeof btn.disabled === 'boolean' && btn.disabled ? 'disabled' : '',				
				callback = typeof btn.callback === 'function' ? btn.callback : $.noop,				
				iconNode = typeof btn.iconClass === 'string' && btn.iconClass !== '' ? '<i class="' + btn.iconClass + '"></i>' : '',
				className = typeof btn.className === 'string' && btn.className !== '' ? (btn.className + ' ' + disabled) : disabled,
				buttonCss = Object.prototype.toString.call(btn.css) === '[object Object]' && !$.isEmptyObject(btn.css) ? btn.css : {},
				btnAlign;
			switch (this.opts.buttonsAlign) {
				case 'left':
					btnAlign = 'left';
					break;
				case 'center':
					btnAlign = 'center';
					break;
				case 'right':
					btnAlign = 'right';
					break;
				default:
					btnAlign = 'right';
			}
			var $button = $('<a href="javascript:;" class="' + className + '">' + iconNode + btn.value + '</a>').css(buttonCss).click(function(e) {
				e.preventDefault();
				// api
				if (disabled) return false;
				callback.call(that.$html, {
					close: function() {
						that.close();
					},
					reset: function() {
						that.reset();
					}
				});
			});
			var footCss = Object.prototype.toString.call(this.opts.footCss) === '[object Object]' ? this.opts.footCss : {};
			var css = $.extend({}, {'text-align': btnAlign}, footCss);
			this.$foot.css(css).append($button);
			this.$cont.after(this.$foot);
		}
	};

	$.dialog = function(options) {
		return new Dialog(options);
	};

}(jQuery, window, document));