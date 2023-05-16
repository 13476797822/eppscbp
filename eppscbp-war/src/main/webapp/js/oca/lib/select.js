/*
Author: 王平 11060522
Website: https://github.com/wang-ping
*/
(function(factory) {
	"use strict";
	if (typeof define === "function" && define.amd) {
		define(["jquery"], factory);
	} else if (typeof exports !== "undefined") {
		module.exports = factory(require("jquery"));
	} else {
		factory(jQuery);
	}
})(function($) {
	$(function() {
		var Select = function($jrSelect) {
			this.init($jrSelect);
		};

		Select.prototype = {
			constructor: Select,

			$jrSelect: null,

			init: function($jrSelect) {
				this.$jrSelect = $jrSelect || $(".jr-select");

				this.initClickSelect();
				this.initSelectValue();
				this.initCloseSelect();
			},

			initClickSelect: function() {
				var self = this;

				this.$jrSelect.find(".select-input").click(function(e) {
					var $this = $(this);
					if (!self.isSelectBoxVisible($this)) {
						self.showSelectBox($this);
						self.arrowOpen($this);
						e.stopPropagation();
					}
				});
			},

			showSelectBox: function($selectInput) {
				var $parent = $selectInput.parents(".jr-select"),
					$selectBox = $parent.find(".select-box");

				$selectBox.show();
				this.resetSelectBoxPosition($selectInput, $selectBox);
			},

			resetSelectBoxPosition: function($selectInput, $selectBox) {
				var top = $selectInput.outerHeight() - 1 + "px";
				$selectBox.css("top", top);
			},

			isSelectBoxVisible: function($selectInput) {
				var $parent = $selectInput.parents(".jr-select"),
					$selectBox = $parent.find(".select-box:visible");

				return $selectBox.length > 0;
			},

			arrowOpen: function($selectInput) {
				$selectInput
					.siblings(".select-input-arrow-icon")
					.removeClass("arrow-icon-closed")
					.addClass("arrow-icon-open");
			},

			arrowClosed: function() {
				this.$jrSelect
					.find(".select-input-arrow-icon")
					.removeClass("arrow-icon-open")
					.addClass("arrow-icon-closed");
			},

			initSelectValue: function() {
				this.$jrSelect.find(".sel-val").click(function() {
					var $this = $(this),
						$parent = $this.parents(".jr-select"),
						$input = $parent.find(".select-input"),
						$selectBox = $parent.find(".select-box"),
						val = $this.attr("value"),
						key = $this.attr("key");

					$input.val(val).attr("key", key).change();

					$selectBox.hide();
				});
			},

			initCloseSelect: function() {
				var self = this;

				$(document).click(function() {
					var $selectBox = $(".select-box");

					$selectBox.hide();
					self.arrowClosed();
				});
			}
		};

		new Select();

		$.fn.extend({
			// 动态插入的 dom 节点初始化select
			initSelect: function() {
				new Select(this);
			}
		});
	});
});
