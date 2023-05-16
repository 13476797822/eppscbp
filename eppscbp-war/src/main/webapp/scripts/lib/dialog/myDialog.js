/** 
 * ==========================================================
 * Copyright (c) 2015, suning.com All rights reserved.
 * 弹窗组件
 * Version: 1.2.0
 * Author: ghj
 * Date: 2015-10-27 14:39:49 587874
 * ==========================================================
 */
define(['jquery'], function($) {
	'use strict';
	var defaults = {
		title: '',				// 标题
		content: '',			// 内容
		custom: '',				// 自定义内容
		esc: true,              // 按ESC键关闭弹窗
		blur: true,				// 背景高斯模糊
		modal: true,			// 显示遮罩
		fixed: true,			// 固定在页面中间
		fadeIn: 200,			// 渐显时间
		fadeOut: 200,			// 渐隐时间
		width: 450,				// 宽度
		zIndex: 99999,			// zindex
		beforeShow: $.noop,		// 显示回调函数，返回$dialog, callback
		afterShow: $.noop,		// 显示回调函数，返回$dialog, callback
		beforeClose: $.noop,	// 关闭回调
		afterClose: $.noop,		// 关闭回调
		css: {},				// css
		maskCss: {},            // 遮罩层背景
		footCss: {},			// 底部css
		showClose: true,		// 显示右上角关闭按钮
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
				callback: $.noop
			}*/
		]
	};
	var isIE = function(ver) {
		var b = document.createElement('b');
		b.innerHTML = '<!--[if IE ' + ver + ']><i></i><![endif]-->';
		return b.getElementsByTagName('i').length === 1;
	};
	var $body = $('body'),
		zIndex = -1,
		// 弹窗中弹窗ESC事件阵列
		custom = [],
		events = {};
	var MASK = '<div class="ui-dialog-mask"></div>',
		WRAP = [
		'<div class="ui-dialog-wrap"> ',
			'<div class="ui-dialog-outer">',
				'<div class="ui-dialog-inner">',
					'<div class="ui-dialog-head"><div class="ui-dialog-title"></div><a class="ui-dialog-close">×</a></div>',
					'<div class="ui-dialog-cont"></div>',
					'<div class="ui-dialog-foot"></div>',
				'</div>',
			'</div>',
		'</div>'
	];

	// ESC自定义事件
	$(document).on('keyup.dialog', function(e) {
		if (custom.length === 0) return;

		if (e.keyCode === 27) {
			$body.trigger(events[custom[custom.length - 1]]);
		}
	});

	function Dialog(options) {
		zIndex++;

		this.$wrap = $(WRAP.join(''));
		this.$mask = $(MASK);

		// ie9对有filter滤镜使用scale效果时有bug
		// 所以对ie11以下的浏览器不使用scale效果
		if (!(navigator.userAgent.indexOf('MSIE') >= 0)) {
			this.$wrap.find('.ui-dialog-outer').addClass('ui-dialog-scale');
		}

		this.opts = $.extend(true, {}, defaults, options || {});
		this.opts.zIndex = this.opts.zIndex + zIndex;
	};

	Dialog.prototype = {
		constructor: Dialog,
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
				var $close = this.$wrap.find('.ui-dialog-close');
				if ($close.length < 1) return;
				$close.click(function() {
					that.close();
				});
			},

			/** 
			 * 绑定自定义退出弹窗事件
			 * time: 2015-07-08 16:07:04
			 */			
			keyup: function() {
				var that = this;
				if (this.opts.esc) {
					$body.on('esc' + this.opts.zIndex, function() {
						that.close();
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
				});
			}
		},

		// 创建节点
		create: function() {
			this.header();
			this.content();
			this.footer();
			this.mask();
		},

		// 显示弹窗
		show: function() {
			var that = this;
			// 创建节点
			this.create();
			// 背景模糊
			this.blur(true);
			// 弹出效果
			setTimeout(function() {
				that.effect(true);
			});
			// 显示前回调
			this.opts.beforeShow.call(this.$wrap, this.$wrap, {
				close: function() {
					that.close();
				},
				reset: function() {
					that.reset();
				}
			});
			// 添加节点
			$body.append(this.$wrap.fadeIn(this.opts.fadeIn, function() {
				// 显示后回调
				that.opts.afterShow.call(that.$wrap, that.$wrap, {
					close: function() {
						that.close();
					},
					reset: function() {
						that.reset();
					}
				});				
			}));

			// 弹窗定位
			this.reset();

			// 绑定事件
			this.events.close.call(this);
			this.events.keyup.call(this);
			this.events.resize.call(this);

			// ESC键阵列
			var esc = 'esc' + that.opts.zIndex;
			if (!events[esc]) {
				custom.push(esc);
				events[esc] = esc;
			}
			//console.log(events, 'push');
		},
		
		// 关闭弹窗
		close: function() {
			var that = this;

			this.effect(false);
			this.blur(false);

			this.$wrap.fadeOut(this.opts.fadeOut, function() {

				$(this).remove();
				that.opts.afterClose.call(this.$wrap, this.$wrap);
			});

			this.$mask.fadeOut(this.opts.fadeOut, function() {
				$(this).remove();
			});

			this.opts.beforeClose.call(this.$wrap, this.$wrap);

			// 解绑当前自定义弹窗事件
			var esc = 'esc' + this.opts.zIndex;
			if (events[esc]) {
				$body.off(esc);
				custom.pop();
				events[esc] = null;
			}
			//console.log(events, 'pop');
		},

		// 计算弹窗位置
		reset: function() {
			var $window = $(window),
				fixed = this.opts.fixed,
				position = (!isIE(6) && this.opts.fixed) ? 'fixed' : 'absolute',
				scrollTop = (position === 'absolute' && $window.scrollTop() > 0) ? $window.scrollTop() : 0,
				left = ($window.width() - this.$wrap.outerWidth()) / 2,
				top = ($window.height() - this.$wrap.outerHeight()) / 2 + scrollTop,
				css = $.extend({}, {
					left: left,
					top: top
				}, this.opts.css);

			this.$wrap.css(css);
		},

		// 背景高斯模糊
		blur: function(flag) {
			if (!this.opts.modal) return;

			if (this.opts.blur) {
				if (flag) {
					$body.addClass('ui-dialog-blur');
				} else {
					$body.removeClass('ui-dialog-blur');
				}
			}
		},

		// 弹窗效果
		effect: function(flag) {
			if (flag) {
				this.$wrap.addClass('ui-dialog-effect');
			} else {
				this.$wrap.removeClass('ui-dialog-effect');
			}
		},

		// 头部
		header: function() {
			// 标题
			if (this.opts.title) {
				this.$wrap.find('.ui-dialog-title').html(this.opts.title);
				// 关闭
				if (typeof this.opts.showClose === 'boolean' && !this.opts.showClose || typeof this.opts.showClose === 'string' && this.opts.showClose !== 'true') {
					this.$wrap.find('.ui-dialog-close').remove();
				}
			} else {
				this.$wrap.find('.ui-dialog-head').remove();
			}
		},

		// 自定义内容
		custom: function() {
			if (typeof this.opts.custom === 'string' && this.opts.custom) {
				return this.opts.custom;
			} 
			else if (typeof this.opts.custom === 'object' && this.opts.custom[0] && this.opts.custom[0].innerHTML) {
				return this.opts.custom[0].innerHTML;
			} 
			else {
				return '';
			}
		},

		// 内容
		content: function() {
			var content = '';
			var position = !isIE(6) && this.opts.fixed ? 'fixed' : 'absolute',
				css = $.extend({
				width: this.opts.width,
				zIndex: this.opts.zIndex + 1,
				position: position
			}, this.opts.css);

			this.$wrap.css(css);

			if (this.custom()) {
				content = this.custom();
				this.$wrap.html(content);
				return;
			}
			else if (typeof this.opts.content === 'string' && this.opts.content) {
				content = this.opts.content;
			}
			else if (typeof this.opts.content === 'object' && this.opts.content[0] && this.opts.content[0].innerHTML) {
				content = this.opts.content[0].innerHTML;
			}
			else {
				content = '';
			}
			this.$wrap.find('.ui-dialog-cont').html(content);
		},

		// 底部按钮
		footer: function() {
			if (this.custom()) return;

			var that = this;
			if (this.opts.buttons.length > 0) {
				this.$wrap.find('.ui-dialog-foot').html('');
				$.each(this.opts.buttons, function() {
					that.button(this);
				});
			} else {
				this.$wrap.find('.ui-dialog-foot').remove();
			}
		},

		/** 
		 * footer按钮
		 * @param {obj} [btn] [{value:'文字', className:'btn', iconClass:'', disabled:false, callback: $.noop}]
		 */
		button: function(btn) {
			if (typeof btn.value === 'string' && !btn.value) return;

			var that = this;
			var btnAlign = this.opts.buttonsAlign ? this.opts.buttonsAlign : 'right',
				disabled = btn.disabled ? 'disabled' : '',
				callback = btn.callback ? btn.callback : $.noop,
				iconNode = btn.iconClass ? '<i class="' + btn.iconClass + '"></i>' : '',
				className = btn.className ? (btn.className + '' + disabled) : '',
				buttonCss = btn.css ? btn.css : {};

			var $button = $('<a href="javascript:;" class="' + className + '">' + iconNode + btn.value + '</a>').css(buttonCss).on('click', function(e) {
				e.preventDefault();
				// api
				if (disabled) return;

				callback.call(that.$wrap, {
					close: function() {
						that.close();
					},
					reset: function() {
						that.reset();
					}
				});
			});

			var css = $.extend({}, {'text-align': btnAlign}, this.opts.footCss || {});

			this.$wrap.find('.ui-dialog-foot').css(css).append($button);
		},

		// 背景遮罩
		mask: function() {
			var opts = this.opts;
			var modal = opts.modal ? true : false;
			if (!modal) return;

			var css = $.extend({}, {
				zIndex: opts.zIndex,
				height: $(document).height()
			}, opts.maskCss);

			if (isIE(6)) {
				this.$mask.html('<iframe style="position:absolute;left:0;top:0;border:0;filter:alpha(opacity=0);width:100%;height:100%;z-index:0;" src="javascript:false;"></iframe>');
			}

			$body.append(this.$mask.css(css));

			this.$mask.show().animate({
				opacity: .4
			});
		}
	};

	$.dialog = function(options) {
		return new Dialog(options);
	};
});