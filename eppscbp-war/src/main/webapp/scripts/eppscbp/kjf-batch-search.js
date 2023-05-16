require.config({
    baseUrl: 'scripts',
    paths: {
        'jquery': '/eppscbp/scripts/lib/jquery.1.7.2',
        "select": '/eppscbp/scripts/lib/select',
        'ECode.calendar': '/eppscbp/scripts/lib/calendar/ECode.calendar',
        dialog: '/eppscbp/scripts/lib/dialog/dialog',
        template: '/eppscbp/scripts/lib/template.min'


    },
    shim: {
        // 公共头尾需要的js
        'ECode.calendar': ['jquery'],
        dialog: ['jquery']
    }
});

require(['jquery','select','ECode.calendar','dialog'], function($) {

	dateFirstInit();
	
	function dateFirstInit(){
		dateInit('date1');
		dateInit('date2');
	}
    
    function dateInit(date) {
    	var mindate;
    	var maxdate;
    	var other;
        //日历控件bug.火狐及低版本ie浏览器对日期格式为XX-XX-XX解析不了,替换为XX/XX/XX可正常显示
    	if(date=="date1"){
    		if($("#date1").val() == ""){
    			mindate = '1981/12/31';
    		} else{
    			mindate = $("#date1").val().replace(new RegExp(/-/gm),"/");
    		}
	        maxdate = addDays(new Date(), 0).replace(new RegExp(/-/gm), "/");
	        other = "date2"
    	} else {
	        // 开始日期在结束日期的前90天
    		if($("#date2").val() == ""){
    			maxdate = new Date();
    			mindate = '1981/12/31';
    		} else{
		        maxdate = $("#date2").val().replace(new RegExp(/-/gm), "/");
		        mindate = addDays(new Date(maxdate), -90).replace(new RegExp(/-/gm), "/");
    		}
	        other = "date1";
    	}
        ECode.calendar({
            inputBox: "#"+other,
            count: 1,
            flag: false,
            isWeek: false,
            isTime: false,
            range: {
                mindate: mindate,
                maxdate: maxdate
            },
        	callback: function() {
            	dateInit(other);
            }
        });
    }
    
    function addDays(date, days) {
        var nd = new Date(date);
        nd = nd.valueOf();
        nd = nd + days * 24 * 60 * 60 * 1000;
        nd = new Date(nd);

        var y = nd.getFullYear();
        var m = nd.getMonth() + 1;
        var d = nd.getDate();
        var h = nd.getHours();
        var minu = nd.getMinutes();
        if(m <= 9) m = "0" + m;
        if(d <= 9) d = "0" + d;
        var cdate = y + "-" + m + "-" + d;

        return cdate;
    }

    /*重置*/
    $(document).on("click",".reset",function(){
        $(this).parents(".condition-list").find("input").val("");
        $(this).parents(".condition-list").find(".select-input").val("");
        dateFirstInit();
    })


    /*tab切切换*/

    $(document).on("click",".batch-tab-list .tab-item",function(){
        $(".batch-tab-list .tab-item").removeClass("hover");
        $(this).addClass("hover");
        var $index = $(this).index();
        $(".condition-list").hide().eq($index).show();
    })

    // 去支付
    $(document).on("click",".gopay",function(){
    	if ($(this).hasClass("disabled")) return;
    	var businessNo = $(this).attr("data-num");
        var bizType = $(this).attr("data-biz-type");
        var productType = $(this).attr("data-product-type");
        var url = sysconfig.ctx + "/batchQuery/batchOrderQuery/toPay.htm?" + "businessNo=" + businessNo + "&bizType=" + bizType + "&productType=" + productType;
        window.location.href = url;
    }); 
    
    // 关闭订单
    $(document).on("click",".goclose",function(){
    	if ($(this).hasClass("disabled")) return;
    	var businessNo = $(this).attr("data-num");
	    var win = $.dialog({
		    title : "确认关闭订单",
		    content : "<p style='text-align:center;padding:20px 0;font-size: 16px;'>确认关闭订单？</p>" +
		    		  "<input type='button' value = '确认' class='submit-btn' id='close-btn' style='margin-left:83px;'/>",
		    width : 300,
		    onShow : function($dialog, callback){
			    $("#close-btn").click(function(){
			    	$.get(sysconfig.ctx + "/batchQuery/batchOrderQuery/toClose.htm",{ businessNo: businessNo },function(data){
			    		//若页面过期跳转至首页
                    	isIntercepted(data);
			    		
			    		if (data.success){
			    			callback.close();
			    			submitQuery();
			    		} else {
			    			var error = $.dialog({
			    			    title : "关闭失败",
			    			    content : "<p style='text-align:center;padding:20px 0;font-size: 16px;'>" + data.message + "</p>" +
			    			    		  "<input type='button' value = '关闭' class='submit-btn' id='close-btn1' style='margin-left:83px;'/>",
			    			    width : 300,
			    			    onShow : function($dialog, callback){
			    				    $("#close-btn1").click(function(){
			    				    	callback.close();
			    				    }
			    			    )},
			    			    showClose : true
			    		    });
			    			callback.close();
			    			error.show();
			    		}
			    	});		    	
			    }
		    )},
		    showClose : true
	    });
	    win.show();
    });
      
});