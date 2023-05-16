require.config({
    baseUrl: "scripts",
    paths: {
        jquery: '/eppscbp/scripts/lib/jquery-1.11.1.min',
    },
    shim: {

    }
});

require(['jquery'], function($) {
    var shtApply = {
        init: function() {
            this.noticSlide();
        },
        noticSlide: function() {
            var $noticeWrap = $('.notic-msg'),
                $noticeBar = $noticeWrap.find('.notic-bar'),
                $list = $noticeBar.find('li'),
                itemHeight = $list.height(),
                len = $list.length,
                barHeight = len * 40,
                listIndex = 0;

            $noticeBar.append($list.first().clone());

            // 关闭公告
            // $noticeWrap.find('.close-btn').click(function() {
            //     $(this).parent().hide('fast', 'linear');
            //     $(this).parents('.main-section').removeClass('pt15');
            // });

            var barScroll = {
                scroll: function() {
                    var top = parseInt($noticeBar.css('top')),
                        toTop = top - itemHeight;

                    if (-toTop > barHeight) {
                        toTop = 0;
                    }

                    $noticeBar.animate({
                        top: toTop
                    }, 500, function() {
                        if (-toTop === barHeight) {
                            $noticeBar.css('top', 0);
                        }
                    });
                },

                start: function() {
                    var self = this;

                    if ($list.length > 1) {
                        this.stop();
                        listIndex = setInterval(function() {
                            self.scroll();
                        }, 1500);
                    }
                }, 

                stop: function() {
                    clearInterval(listIndex);
                },

                init: function() {
                    var self = this;
                    this.start();

                    $noticeWrap.hover(function() {
                        self.stop();
                    }, function() {
                        self.start();
                    });
                }
            };

            barScroll.init();  
        }
    }
    shtApply.init();
});