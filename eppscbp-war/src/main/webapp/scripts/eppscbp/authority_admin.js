require.config({
    baseUrl: '/eppscbp/scripts/lib/',
    paths: {
        'jquery': 'fullpage/jquery.1.8.3.min',
    },
    shim: {
    }
});

require(['jquery'], function ($) {
    var Launch = {
        init: function () {
            bindE.hoveract();
        }

    };
    var czyID="";
	//初始化
	queryAuth();
	
    /**查询权限信息 */
    function queryAuth(){
    	$.ajax({
    	  type: 'GET',
		  url: sysconfig.ctx + "/authManage/authManage/queryAuth.htm",
		  data: { operatorCode: czyID },
		  dataType: 'JSON',
		  success: function (data) {
           	//若页面过期跳转至首页
        	isIntercepted(data)
    		if (data){
    			$('.authorityContent').html("")
    			template.helper("decode", function(arg) {
			        return decodeURI(arg)
			    });
    			for(var key in data){
				  var html = template('authorityContent-tpl',{title:key, list:data[key]})
				  $('.authorityContent').append(html)
				  $(".checkclick").off("click").click(selectOne)
				  $(".authorityTitle").children(".checkbox").off("click").click(selectClass)
				}	
    		} 
          }
        });    	
    }

    /*选择权限*/

    /*全选*/
    $(".checkall").click(function () {
        if(!czyID)
        return;
        if ($(this).hasClass("checked")) {
            $(".checkbox").removeClass("checked");
        } else {
            $(".checkbox").addClass("checked");
        }
    })
    /*选择/取消单个权限*/
    function selectOne() {
        if(!czyID)
        return;
        if ($(this).hasClass("checked")) {
            $(this).removeClass("checked");
        } else {
            $(this).addClass("checked");
        }
        var chkAll=true;
        $(this).parent().parent().find('.checkclick').each(function () {
            if (!$(this).hasClass("checked")) {
                chkAll=false;
                $(this).parent().parent().parent().children("a").removeClass("checked");
            }
        });
        if(chkAll){
            $(this).parent().parent().parent().children("a").addClass("checked");
        }
        var allChk = true;
        $('.authorityContent .checkbox').each(function(){
            if(!$(this).hasClass("checked")){
                allChk=false;
                $(".checkall").removeClass("checked");
            }
        })
        if(allChk){
            $(".checkall").addClass("checked");
        }
    }
    /*全选/取消单类权限*/
    function selectClass() {
        if(!czyID)
        return;
        if ($(this).hasClass("checked")) {
            $(this).removeClass("checked");
            $(this).parent().find(".authorityItem .checkbox").removeClass("checked");
        } else {
            $(this).addClass("checked");
            $(this).parent().find(".authorityItem .checkbox").addClass("checked");
        }
        var allChk = true;
        $('.authorityContent .checkbox').each(function(){
            if(!$(this).hasClass("checked")){
                allChk=false;
                $(".checkall").removeClass("checked");
            }
        })
        if(allChk){
            $(".checkall").addClass("checked");
        }
    }


    /*点击操作员选中样式*/
    $(".operatorList").find("li").click(function () {
        czyID=this.id;
        $(".operatorList").find("li.on").find("a.checked").removeClass("checked");
        $(".operatorList").find("li.on").find("span.color4").removeClass("color4");
        $(".operatorList").find("li.on").removeClass("on");
        $(this).find("a").addClass("checked");
        $(this).find("span").addClass("color4");
        $(this).addClass("on");
        $(".btw124static").addClass("confirm");
        $(".btw124static").removeClass("btw124static");
        //调用查询该操作员权限接口
        queryAuth()
    })

    /**确定修改权限 */
    $("#btnConfirm").click(function(){
        if($(this).hasClass("btw124static")){
            return;
        }

        //获取所选权限
        var authority={"operatorCode": czyID};
        $('.authorityContent .checkclick').each(function(){
            if($(this).hasClass("checked")){
                authority[this.id] = "Y";
            } else{
            	authority[this.id] = "N";
            }
        })

        //调用保存权限接口
        $.ajax({
        	type: 'POST',
		    url: sysconfig.ctx + "/authManage/authManage/setAuth.htm",
		    data: JSON.stringify(authority), 
		    dataType: 'JSON',
		    contentType: "application/json;charset=UTF-8",
		    success: function (data) {
			    //若页面过期跳转至首页
	        	isIntercepted(data)
	    		if (data){
			        $(".cancelshenpipop").removeClass("hide");
	        		$(".mengceng").removeClass("hide");
			        if(data.ischecked == true){
			        	if(data.isSuccess == true){
			        		$(".addtip span").text("授权成功");
			        	} else{
				        	$(".addtip span").text("授权失败");
				        }   
			        } else{
			            $(".addtip span").text("授权未作变动,无需保存授权");
			        }
		        } else{
		        	$(".addtip span").text("授权失败");
		        }
		    },
		    error: function () {
		        $(".addtip span").text("系统异常,请稍后重试");
		    }
		});
    })

    /**取消修改权限 */
    // $("#btnCancel").click(function(){
        
    // })

    /*关闭弹窗*/
    $("#altershenpisure,.cancelpop").click(function () {
        $(".cancelshenpipop").addClass("hide");
        $(".mengceng").addClass("hide");
    });
    $("#cancelshenpisure").click(function () {
        $(".cancelshenpipop").addClass("hide");
        $(".mengceng").addClass("hide");
    });
   

    /*input输入框的默认值相关，兼容IE*/
    $(".placeholder").click(function () {
        $(this).prev().focus();
    });
    /*实时监听搜索框状态变化。oninput 事件在 IE9 以下版本不支持，需要使用 IE 特有的 onpropertychange 事件替代，这个事件在用户界面改变或者使用脚本直接修改内容两种情况下都会触发*/
    $(".placeholder").prev().bind('input propertychange', function () {
        var inputval = $(this).val().replace(/[\t\n ]/g, ""); //搜索框的值
        if ($(this).val().length > 0) {
            $(this).next().hide();
            $("ul.operatorList li").hide()
            $("ul.operatorList li span:contains('"+$(this).val()+"')").each(function() {
            	$(this).parent().show()
            })
        } else {
            $(this).next().show();
            $("ul.operatorList li").show()
        }
    });
    var bindE = {
        /*头部二级导航悬停效果*/
        hoveract: function () {
            $(".oprator_head li").hover(function () {
                $(".oprator_head li").removeClass("addactiveon");
                $(".oprator_head li a").removeClass("activeaon");
                $(this).addClass("addactiveon");
                $(this).find("a").addClass("activeaon");
            });
        }
    };

    Launch.init();
});

