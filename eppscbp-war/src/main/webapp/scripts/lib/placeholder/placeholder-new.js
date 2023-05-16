/*
Author: 王平 11060522
Website: https://github.com/wang-ping
*/
;'use strict';
(function(factory) {
    if (typeof define === 'function' && define.amd) {
        define(['jquery'], factory);
    } else if (typeof exports !== 'undefined') {
        module.exports = factory(require('jquery'));
    } else {
        factory(jQuery);
    }
}(function($) {
    $(function() {
        var Placeholder = function($inputs) {
            this.init($inputs);
        };

        var labelForInputClass = 'label-for-input';

        Placeholder.prototype = {
            constructor: Placeholder,

            $inputs: null,

            init: function($inputs) {
                this.$inputs = $inputs || $(':input[placeholder]');

                this.replacePlaceholderWithEmptyholder();
                this.simulationAttributePlaceholder();
                this.simulationEventPlaceholder();
            },

            replacePlaceholderWithEmptyholder: function() {
                this.$inputs.each(function() {
                    var $this = $(this),
                        placeholder = $this.attr('placeholder');

                    $this.attr('emptyholder', placeholder);
                    $this.removeAttr('placeholder');
                });
            },

            simulationAttributePlaceholder: function() {
                var self = this;

                this.$inputs.each(function() {
                    var $input = $(this);

                    $input.siblings('.' + labelForInputClass).remove();
                    $input.after(self.createLabelForInput($input));
                })
            },

            createLabelForInput: function($input) {
                var $label = $('<label></label>'),
                    emptyholder = $input.attr('emptyholder'),
                    outerHeight = $input.outerHeight() + 'px';

                if ($.trim(emptyholder).length > 0) {
                    $label.text(emptyholder).addClass(labelForInputClass).css({
                        lineHeight: outerHeight,
                        height: outerHeight
                    });

                    this.clickLabelForInput($label);

                    return $label;
                }
            },

            clickLabelForInput: function($label) {
                $label.click(function() {
                    var $input = $label.siblings(':input'),
                        disabled = $input.attr('disabled'),
                        readonly = $input.attr('readonly');

                    if (typeof disabled === 'undefined' && typeof readonly === 'undefined') {
                        $input.focus();
                        $input.trigger('click');
                    }
                });
            },

            simulationEventPlaceholder: function($input) {
                this.$inputs.on('keyup input paste blur', function() {
                    var $input = $(this),
                        val = $input.val(),
                        $labelForInput = $input.siblings('.' + labelForInputClass);

                    if (val && val.length > 0) {
                        $labelForInput.hide();
                    } else {
                        $labelForInput.show();
                    }
                });
            }
        };

        new Placeholder();


        $.fn.extend({
            // 动态插入的 dom 节点初始化
            initPlaceholder: function() {
                new Placeholder(this);
            }
        });
    });
}));
