// $(function(){
	define(['jquery'], function(jquery) {


		   
			$(".orderInput1").focus(function(){
				$(this).val("");								 
				$(this).addClass("u-input-focus-font");
			}).blur(function(){
				if( $(this).val() == "" ){
					$(this).val("请填写订单号" );
				}
				$(this).removeClass("input-focus");
			});
			
			$(".orderInput2").focus(function(){
				$(this).val("");								 
				$(this).addClass("input-focus");
			}).blur(function(){
				if( $(this).val() == "" ){
					$(this).val("请填写交易号");
				}
				$(this).removeClass("u-input-focus-font");
			});
			
			//选择日期
			$(".data-select i,.uitext-select i,").click(function(){
				$(".uitext-select ul").hide();
				$(".data-select ul").hide();
				$(this).next("ul").show();
				return false;
			});
			
			//模拟下拉框划过效果
			$(".data-select ul li").hover(function(){
				$(".data-select ul li.on").removeClass("on");
				$(this).addClass("on");
			},function(){
				$(this).removeClass("on");
			});
			
			$(".uitext-select ul li").hover(function(){
				$(".uitext-select ul li.on").removeClass("on");
				$(this).addClass("on");
			},function(){
				$(this).removeClass("on");
			});
			
			
			//点击li然后把值丢到input框里去
			$(".uitext-select ul li").click(function(){
				var v = $.trim($(this).text());
				$(this).parent().prev().prev().val(v).css({"color":"#333"});
				if(v == "自定义"){
					/*$(".uitext-select").hide();*/
					$(".chioce-data").show();
					$(".w2928 i").click();
					$(document).click();
				}
			});
			$(".w2928 ul li").click(function(){
				var v = $(this).text();
				$(".chioce-data").hide();
				$(".data-select input").val(v);
				$(".data-select").show();
				$(".data-select ul").hide();
			});
			
			$(".data-select ul li").click(function(){
				var v = $.trim($(this).text());
				$(this).parents('.data-select').find('input').val(v).css({"color":"#333"});
				$(this).parent().hide();				
				if(v === "自定义"){
					$(".data-select").hide();
					$(".chioce-data").show();
					$(".w2928 ul").addClass('hide');
				}
			});
			$(".uitext input").focus(function(){
				$(this).parent().addClass("u-input-focus");
				$(this).addClass("u-input-focus-font");
				var plh =$(this).attr('rel');
				 if($(this).val() === plh){
					 $(this).val('');
					 }
			});
			$(".uitext input").blur(function(){
				$(this).parent().removeClass("u-input-focus");
				 var plh =$(this).attr('rel');
				 if($(this).val() === ''){
					 $(this).val(plh);
					 $(this).removeClass("u-input-focus-font");
					 }
			});
			
			$(document).click(function(){
				$(".uitext-select ul").hide();
				$(".data-select ul").hide();
				$(".small-menu ul").hide();
				$(".active-menu ul").hide();
			});
			
	
			$(".child-nav-two").mouseout(function(){
				$("#nav-two-current").addClass("on");
		 		$("#nav-two-current").siblings().removeClass("on"); 
		 	});
			$(".child-nav").mouseout(function(){
				$("#child-nav-current").addClass("on");
		 		$("#child-nav-current").siblings().removeClass("on"); 
		 	});
			$(".small-menu a.mr10").click(function(e){
				e.stopPropagation();
				$(this).siblings('ul').show();
			});
			$(".small-menu li").click(function(){
				var text = $(this).text();
				var val = $(this).attr('data-value');
				$(this).addClass('on').siblings().removeClass('on');
				$(this).parent().siblings('a').text(text);
				$(this).parent().siblings('a').attr('data-value',val);
				$(this).parent().hide();
			});
			$(".small-menu li").hover(function(){
				$(this).addClass('hover');
			},function(){
				$(this).removeClass('hover');
			});
			$('.one-ico em').hover(function(){
				$(this).siblings('.boll').show();
			},function(){
				$(this).siblings('.boll').hide();
			});
			$('.data-name').click(function(e){
				e.stopPropagation();
				$(this).siblings('.red').hide();
				$(this).parents('tr').find('td').removeClass('pb20');
				$(this).parent().removeClass('u-input-error');
				});
			$('.money-area .uitext input').focus(function(){
				$(this).parents('.money-area').find('.uitext ').removeClass('u-input-error');
				$(this).parents('.money-area').find('.red ').hide();
				});
			$('.rel-input').trigger('blur');
			
			
	ECode.tab($(".ui-wrap"),0, function(id){
		if(id=='widget-tab3'){
			ECode.tab($("#demo-wrap"), function(id){
				var obj = $("#"+id);
				if( $.trim(obj.html())=='' ){
					var rnd = new Date().getTime();
					var data = obj.attr("data")+"?"+rnd;
					var dom = '<iframe scrolling="auto" frameborder="0" width="100%" height="500" src="'+ data +'"></iframe>';
					obj.append(dom);
				}
			});
		}
	});
	//数字格式化
    function formatNum(num){//补0
        return num.toString().replace(/^(\d)$/, "0$1");
    }
	//日期格式化
    function formatStrDate(vArg){//格式化日期
        switch(typeof vArg) {
            case "string":
                vArg = vArg.split(/-|\//g);
                return vArg[0] + "-" + formatNum(vArg[1]) + "-" + formatNum(vArg[2]);
                break;
            case "object":
                return vArg.getFullYear() + "-" + formatNum(vArg.getMonth() + 1) + "-" + formatNum(vArg.getDate());
                break;
		}
    };

	//日期默认值
	if ($("#date01").val() == null || $("#date01").val() == "") {
		$("#date01").val(formatStrDate(new Date()));
	}
	if ($("#date02").val() == null || $("#date02").val() == "") {
		$("#date02").val(formatStrDate(new Date()));
	}
	if ($("#hourStartDate").val() == null || $("#hourStartDate").val() == "") {
		$("#hourStartDate").val("00:00");
	}
	if ($("#hourEndDate").val() == null || $("#hourEndDate").val() == "") {
		$("#hourEndDate").val("24:00");
	}

	//日期插件
	ECode.calendar( {
		inputBox : "#date01",
		count : 1,
		flag : false,
		range:{mindate:"2015-10-31",maxdate:"2020-12-31"},
		callback: function(){
			if($("#totalDate").val()== '自定义'){
				var errormsg = '';
				if ($("#date01").val() != '' && $("#date02").val() != '') {
					if(!validatedatestar()) return false;
					var hourStartDate = $("#hourStartDate").val();
					var hourEndDate = $("#hourEndDate").val();
					if (($("#date02").val() + hourEndDate) < ($("#date01").val() + hourStartDate )){
						$("#noData").text("开始日期不能大于结束日期").show();
						return false;
					}
					var startDateTim = parseString2Date($("#date01").val(),hourStartDate).getTime();
					var endDateTim = parseString2Date($("#date02").val(),hourEndDate).getTime();
					if((endDateTim-startDateTim)> 7862400000){
						$("#noData").text("开始日期和结束日期相差不能超过91天").show();
						return false;
					}
				}
				$("#noData").text("").hide();
			}
		}
	});//默认日历
	ECode.calendar( {
		inputBox : "#date02",
		count : 1,
		flag : false,
		range:{mindate:"2015-10-31",maxdate:"2020-12-31"},
		callback: function(){
			if($("#totalDate").val()== '自定义'){
				var errormsg = '';
				if ($("#date01").val() != '' && $("#date02").val() != '') {
					if(!validatedatestar()) return false;
					var hourStartDate = $("#hourStartDate").val();
					var hourEndDate = $("#hourEndDate").val();
					if (($("#date02").val() + hourEndDate) < ($("#date01").val() + hourStartDate )){
						$("#noData").text("开始日期不能大于结束日期").show();
						return false;
					}
					var startDateTim = parseString2Date($("#date01").val(),hourStartDate).getTime();
					var endDateTim = parseString2Date($("#date02").val(),hourEndDate).getTime();
					if((endDateTim-startDateTim)> 7862400000){
						$("#noData").text("开始日期和结束日期相差不能超过91天").show();
						return false;
					}
				}
				$("#noData").text("").hide();
			}
		}
	});
	
});
		