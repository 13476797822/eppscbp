/** 
 * ==========================================================
 * Copyright (c) 2014, suning.com All rights reserved.
 * tooltips组件
 * Author: ghj
 * Version: 1.1.0
 * Date: 2014-10-15 21:39:04 705680
 * ==========================================================
 */
 define(['jquery'], function($) {
	'use strict';
	var pluginName = 'tooltips',
		defaults = {
			styleName: 'yellow', // 样式名称
			placement: 'top',	 // 位置
			container: null,	 // 容器-选择器或fn fn时返回(this.$elem, this.$elem)
			showDelayTime: 200,	 // 显示延时时间
			hideDelayTime: 200,	 // 隐藏延时时间
			content: '', 		 // 提示内容
			animate: false, 	 // todo
			width: 'auto', 		 // 会覆盖CSS的width
			css: {}, 			 // CSS 样式
			hoverEvent: true,	 // 是否在目标元素上绑定hover事件
			onShow: $.noop, 	 // 显示回调函数
			onHide: $.noop,		 // 隐藏回调函数
			arrowX: 0,			 // 箭头X方向微调
			arrowY: 0,			 // 箭头Y方向微调
			boundary: $.noop, 	 // 边界元素，用于计算提示自动显示位置
			distance: 2, 		 // 显示方向距离
			position: { 		 // 位置微调
				x: 0,
				y: 0
			}
		};
	var HTML = '<div><div class="ui-tooltips-content"></div><span class="arrow"><i class="arrow-bd"></i><i class="arrow-bg"></i></span></div>';

	var methods = {
		/** 
		 * 显示tooltips
		 * time: 2015-01-13 20:12:10
		 */
		show: function() {
			var $this = $(this);
			var options = typeof arguments[0] === 'object' ? arguments[0] : {};
			if (!$this.data('plugin_' + pluginName)) {
				$this.data('plugin_' + pluginName, new ToolTips(this, options));
			}
			$this.data('plugin_' + pluginName).show();
		},

		/** 
		 * 隐藏tooltips
		 * time: 2015-01-13 20:12:56
		 */
		hide: function() {
			var $this = $(this);
			if ($this.data('plugin_' + pluginName)) {
				$this.data('plugin_' + pluginName).hide();
			}
		},

		/** 
		 * 移除tooltips
		 * time: 2015-01-13 20:13:18
		 */
		destroy: function() {
			var $this = $(this);
			if ($this.data('plugin_' + pluginName)) {
				$this.data('plugin_' + pluginName).destroy();
				$this.removeData('plugin_' + pluginName);
				$this.unbind('hover');
			}
		}
	};

	var ToolTips = function(element, options) {
		this.$container = $('body');
		this.$html = $(HTML);
		this.$cont = $('.ui-tooltips-content', this.$html);
		this.$elem = $(element);
		this.$arrow = this.$html.find('.arrow');

		this.opts = $.extend({}, defaults, options || {});

		this.init();
	};

	ToolTips.prototype = {
		constructor: ToolTips,

		/** 
		 * 页面初始化
		 * time: 2014-10-15 22:05:27
		 */
		init: function() {
			this.content = this.getContent();

			if (!this.content) return false;

			this.getContainer();

			this.setStyle();

			this.render();
		},

		/** 
		 * 页面渲染
		 * time: 2014-10-15 22:06:02
		 */
		render: function() {
			var hover = this.opts.hoverEvent;
			if (typeof hover === 'boolean' && hover) {
				this.events.hover.call(this);
			}
		},

		/** 
		 * 事件绑定
		 * time: 2014-10-15 22:06:42
		 */
		events: {
			hover: function() {
				var that = this,
					showTimer,
					hideTimer;
				this.$elem.hover(function() {
					clearTimeout(hideTimer);
					showTimer = setTimeout(function() {
						that.show();
					}, that.opts.showDelayTime);
				}, function() {
					clearTimeout(showTimer);
					hideTimer = setTimeout(function() {
						that.hide();
					}, that.opts.hideDelayTime);
				});

				that.$html.hover(function() {
					clearTimeout(hideTimer);
					showTimer = setTimeout(function() {
						that.$html.show();
					}, that.opts.showDelayTime);
				}, function() {
					clearTimeout(showTimer);
					hideTimer = setTimeout(function() {
						that.hide();
					}, that.opts.hideDelayTime);
				});
			}
		},

		/** 
		 * 显示提示
		 * time: 2014-10-15 22:07:00
		 */
		show: function() {
			var that = this;
			this.content = this.getContent();
			
			this.$cont.html(this.content);

			this.$container.append(this.$html);

			this.$html.css(that.setPosition()).show();

			if (typeof this.opts.onShow === 'function') {
				this.opts.onShow(this.$html, this.$html);
			}
		},

		/** 
		 * 隐藏提示
		 * time: 2014-10-15 22:07:14
		 */
		hide: function() {
			var that = this;
			setTimeout(function() {
				that.$html.hide();
			}, this.opts.hideDelayTime);

			if (typeof this.opts.onHide === 'function') {
				this.opts.onHide(this.$html, this.$html);
			}
		},

		/** 

		 * 移去提示
		 * time: 2015-01-13 17:23:24
		 */
		destroy: function() {
			this.$html.remove();
		},

		/** 
		 * 设置样式风格
		 * time: 2014-10-15 20:42:59
		 */
		setStyle: function() {
			var cls = 'ui-tooltips ';
			if (typeof this.opts.styleName === 'string') {
				cls += 'ui-tooltips-' + this.opts.styleName;
			}
			this.$html.addClass(cls);
		},

		/** 
		 * 获取提示内容容器
		 * time: 2014-10-15 21:43:18
		 */
		getContainer: function() {
			if (this.opts.container) {
				if (typeof this.opts.container === 'function') {
					this.$container = this.opts.container.call(this.$elem, this.$elem);
				} else {
					this.$container = $(this.opts.container);
				}
			}
		},

		/** 
		 * 获取提示内容
		 * time: 2014-10-15 21:43:18
		 */
		getContent: function() {
			var tooltips = this.$elem.attr('data-tooltips');
			if (this.opts.content) {
				if (typeof this.opts.content === 'string') {
					tooltips = this.opts.content;
				} else if (typeof this.opts.content === 'function') {
					tooltips = this.opts.content.call(this.$elem, this.$elem);
				}
			}
			/*if (!tooltips) {
				tooltips = 'please set the content';
			}*/
			return tooltips;
		},

		/** 
		 * 获取CSS
		 * time: 2014-10-15 21:43:06
		 */
		getCss: function() {
			var css = $.extend({}, this.opts.css, {
				width: this.opts.width
			});
			return css;
		},

		/** 
		 * 计算显示方向
		 * time: 2014-10-15 21:42:00
		 */
		setPosition: function() {

			this.$html.css(this.getCss());

			var posX = parseInt(this.opts.position.x, 10) || 0,
				posY = parseInt(this.opts.position.y, 10) || 0,
				optsArrowX = parseInt(this.opts.arrowX, 10) || 0,
				optsArrowY = parseInt(this.opts.arrowY, 10) || 0,

				distance = parseInt(this.opts.distance, 10) || 0,

				// 箭头宽高度
				arrowWidth = this.$html.find('.arrow-bg').outerWidth() / 2,
				arrowHeight = this.$html.find('.arrow-bg').outerHeight() / 2,

				// 提示文字宽高度
				tipsWidth = this.$html.outerWidth(),
				tipsHeight = this.$html.outerHeight(),

				// 目标元素宽高度
				elemHeight = this.$elem.outerHeight(),
				elemWidth = this.$elem.outerWidth();

			// 重新计算
			var rePosX = 0,
				rePosY = 0,
				// 目录元素位置
				elPosX = 0,
				elPosY = 0,
				// 箭头坐标
				arrowX = 0,
				arrowY = 0;
			var x, y;

			if (this.opts.placement === 'auto') {
				this.setPlacement(arrowWidth, arrowHeight, tipsWidth, tipsHeight);
			}

			if (this.$container[0].nodeName === 'BODY') {
				//console.log(elPosX, 'elPosX');
				elPosX = this.$elem.offset().left;
				elPosY = this.$elem.offset().top;
			} 
			// 当容器不是body时重新计算目标元素位置
			else {				
				elPosX = this.$elem.offset().left - this.$container.offset().left;
				elPosY = this.$elem.offset().top - this.$container.offset().top;
			}

			switch (this.opts.placement) {
				case 'top':
					// tips宽度小于等于元素宽度
					if (tipsWidth <= elemWidth) {
						rePosX = (elemWidth - tipsWidth) / 2;
						arrowX = tipsWidth / 2 - arrowWidth;
					}
					// tips宽度大于元素宽度
					else {
						arrowX = elemWidth / 2 - arrowWidth;
					}

					rePosY = tipsHeight + arrowHeight;
					//console.log(elPosX , posX , rePosX, '0000');
					x = elPosX + posX + rePosX;
					y = elPosY - posY - (rePosY + distance);

					this.$arrow.addClass('arrow-down').css({
						left: optsArrowX ? optsArrowX : arrowX
					});

					break;
				case 'right':
					// tips高度小于等于元素高度
					if (tipsHeight <= elemHeight) {
						arrowY = tipsHeight / 2 - arrowHeight;
						rePosY = (elemHeight - tipsHeight) / 2;
					}
					// tips高度大于等于元素高度
					else {
						arrowY = elemHeight / 2 - arrowHeight;
					}

					rePosX = elemWidth + arrowWidth;

					x = elPosX + posX + rePosX + distance;
					y = elPosY + posY + rePosY;

					this.$arrow.addClass('arrow-left').css({
						top: optsArrowY ? optsArrowY : arrowY
					});

					break;
				case 'bottom':

					// tips宽度小于等于元素宽度
					if (tipsWidth <= elemWidth) {
						rePosX = (elemWidth - tipsWidth) / 2;
						arrowX = tipsWidth / 2 - arrowWidth;
					}
					// tips宽度大于元素宽度
					else {
						arrowX = elemWidth / 2 - arrowWidth;
					}

					rePosY = elemHeight + arrowHeight;

					x = elPosX + posX + rePosX;
					y = elPosY + posY + rePosY + distance;

					this.$arrow.addClass('arrow-top').css({
						left: optsArrowX ? optsArrowX : arrowX
					});
					break;
				case 'left':
					// tips高度小于等于元素高度
					if (tipsHeight <= elemHeight) {
						arrowY = tipsHeight / 2 - arrowHeight;
						rePosY = (elemHeight - tipsHeight) / 2;
					}
					// tips高度大于等于元素高度
					else {
						arrowY = elemHeight / 2 - arrowHeight;
					}

					rePosX = tipsWidth + arrowWidth;

					x = elPosX - posX - (rePosX + distance);
					y = elPosY + posY + rePosY;

					this.$arrow.addClass('arrow-right').css({
						top: optsArrowY ? optsArrowY : arrowY
					});
					break;
				default:
			}
			//console.log(x, 'x');
			return {
				left: x,
				top: y
			};
		},

		/** 
		 * 初始化显示方向
		 * time: 2014-10-15 17:32:36
		 */
		setPlacement: function(arrowWidth, arrowHeight, tipsWidth, tipsHeight) {
			var $boundary = typeof this.opts.boundary === 'function' ? this.opts.boundary.call(this.$elem, this.$elem) : this.$container;

			var elemX = this.$elem.offset().left,
				elemY = this.$elem.offset().top,
				elemW = this.$elem.outerWidth(),
				elemH = this.$elem.outerHeight(),
				tipsW = arrowWidth + tipsWidth,
				tipsH = arrowHeight + tipsHeight;

			if ($boundary.length === 0) return false;
			// 边界元素坐标
			var boundX = $boundary.offset().left,
				boundY = $boundary.offset().top,
				boundW = $boundary.outerWidth(),
				boundH = $boundary.outerHeight();

			if ((elemY - boundY) > tipsH) {
				this.opts.placement = 'top';
			} else if (boundW - ((elemX - boundX) + elemW) > tipsW) {
				this.opts.placement = 'right';
			} else if (boundH - ((elemY - boundY) + elemH) > tipsH) {
				this.opts.placement = 'bottom';
			} else if ((elemX - boundX) > tipsW) {
				this.opts.placement = 'left';
			} else {
				this.opts.placement = 'top';
			}
			return this.opts.placement;
		}
	};

	$.fn[pluginName] = function(options) {
		if (typeof options === 'string') {
			var args = Array.prototype.slice.call(arguments, 1);
			this.each(function() {
				return methods[options].apply(this, args);
			});
		} else if (typeof options === 'object' || !options) {
			this.each(function() {
				var $this = $(this);
				if (!$this.data('plugin_' + pluginName)) {
					$this.data('plugin_' + pluginName, new ToolTips(this, options));
				}
			});
		}
		return this;
	};
});