// @author wujing
define(['jquery'], function($) {
	var dropdown = {
		//设置默认值
		defaults: {
			el: '',
			bindBack: '',
			index:'0'
		},
		//初始化
		init: function(opts) {
			var options = $.extend({}, this.defaults, opts || {});
			this.eventBind(options);
			this.txtIndex(options);
		},	
		//默认获取第一个显示
		txtIndex:function(opts){
			var self = this;
			var inputBox = opts.el.find('.drop-input');
			var selectItem = opts.el.find('.drop-list').find('.drop-select-item').eq(opts.index).text();			
            inputBox.val(selectItem);
			inputBox.attr("data-index",opts.index)
			
			},

		//事件绑定
		eventBind: function(opts) {
			var self = this;
			var inputBox = opts.el.find('.drop-input-box');
			var selectItem = opts.el.find('.drop-list').find('.drop-select-item');

			//点击标题	
			inputBox.live("click",function(){
				var $this=$(this);
				var listBox = $this.siblings('.drop-list');
				if(listBox.hasClass("hide")) {
					listBox.removeClass("hide");
					$this.addClass("box-border").find(".seach-arrow-down").addClass("seach-arrow-up");
				} else {
					listBox.addClass("hide");
					$this.removeClass("box-border");
					$(".seach-arrow-down").removeClass("seach-arrow-up");
				}
			});

			//点击下拉里面内容
			selectItem.live("click",function(){
				
				var $this=$(this);
				var inputBox=$this.parent("ul").parent(".drop-list").siblings(".drop-input-box");
				var input = $this.parent("ul").parent(".drop-list").siblings(".drop-input-box").find(".drop-input");
				var listBox=$this.parent("ul").parent(".drop-list");
				var index=$this.attr("data-index");
				$this.addClass("hovers").siblings(".drop-select-item").removeClass("hovers");
				listBox.addClass("hide");
				inputBox.removeClass("box-border");
				inputBox.find(".seach-arrow-down").removeClass("seach-arrow-up");
				input.val($this.html()).attr("data-index",index);
				opts.bindBack.call(this,index);
				
			});

		},
		
		//重置
		reset:function(el){
			var self = this;
			var inputBox = opts.el.find('.drop-input-box');
			var selectItem = opts.el.find('.drop-list').find('.drop-select-item');
			var listBox = opts.el.find('.drop-list');
				inputBox.unbind("click");	
				selectItem.unbind("click");
				listBox.addClass("hide");
			
			}

	}
	
    return {
        //初始化
        init: function(opts) {
            dropdown.init(opts);
        },
        //重置
        reset : function(el){
            dropdown.reset(el);
        }
    };	

})