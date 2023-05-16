require.config({
	baseUrl: '/eppscbp/scripts/lib/',
	paths: {
		'jquery': 'jquery.1.7.2',
		"select": 'select',
		'calendar': 'calendar/ECode.calendar',
		ajaxfileupload: 'upload/ajaxfileupload',
		dialog: '/eppscbp/scripts/lib/dialog/dialog',//myDialog
		pagination: 'pagination/paginationLocal',//20210319分页新增
		template: 'template.min'
	},
	shim: {
		// 公共头尾需要的js
		'ECode.calendar': ['jquery'],
		dialog: ['jquery'],
		ajaxfileupload: ['jquery'],
	}
});

require(['jquery', 'select', 'calendar', 'dialog', 'pagination', 'ajaxfileupload', 'template'], function ($,select,calendar,dialog, pagination) {
	dateFirstInit();
	$(function (){

		$('body').click(function (){
			$('.select-box').hide()
		})
		$('#status').click(function (e){
			e.stopPropagation()
			$('.select-box').show()
		})
		$('.select-box .select-item').click(function (e){
			e.stopPropagation()
			$('#status').val('')
			if($(this).find('.checkbox').hasClass('choosed')){
				if($(this).find('a').text().trim() == '全部'){
					$('.select-box .select-item .checkbox').removeClass('choosed')
				}else{
					$(this).find('.checkbox').removeClass('choosed')
					$('.select-box .select-item').eq(0).find('.checkbox').removeClass('choosed')
				}
				var str = '';
				for(var i = 0 ; i < $('.select-box .select-item .choosed').length;i++){
					if( i == $('.select-box .select-item .choosed').length - 1){
						str = str +  $('.select-box .select-item .choosed').eq(i).next('a').text().trim()
					}else{
						str = str +  $('.select-box .select-item .choosed').eq(i).next('a').text().trim() + ','
					}
				}
				$('#status').val(str)
			}else{
				if($(this).find('a').text().trim() == '全部'){
					$('.select-box .select-item .checkbox').addClass('choosed')
					$('#status').val('全部')
				}else{
					$(this).find('.checkbox').addClass('choosed')
					var str = '';
					for(var i = 0 ; i < $('.select-box .select-item .choosed').length;i++){
						if(i == $('.select-box .select-item .choosed').length - 1){
							str = str +  $('.select-box .select-item .choosed').eq(i).next('a').text().trim()
						}else{
							str = str +  $('.select-box .select-item .choosed').eq(i).next('a').text().trim() + ','
						}
					}
					$('#status').val(str)
				}
			}

		})

		// 重置按钮
		$('.searchitem .resetbtn').click(function (){
			$('.searchitem input').val('')
		})

		$('.search').click(function () {
			submitQuery()
		})

		$(document).on('click','.menu1',function () {
			showcheckUnPass(1)
		})

		$(document).on('click','.menu2',function () {
			checkPass(1, 0)
		})

		$(document).on('click','.menu3',function () {
			submitExport()
		})

		$('table .checkall').on('click',function (){
			$(this).toggleClass('choosed')
			if($(this).hasClass('choosed')){
				$('#table-box-result tbody .checkicon ').addClass('choosed')
			}else{
				$('#table-box-result tbody .checkicon ').removeClass('choosed')
			}
		})

		$('table tbody .checkicon').on('click',function (){
			$(this).toggleClass('choosed')
			if($(this).hasClass('choosed')){
				var isall = true
				for (var i = 0 ; i < $('#table-box-result tbody .checkicon').length;i++){
					if(!$('#table-box-result tbody .checkicon').eq(i).hasClass('choosed')){
						isall = false
						return
					}
				}
				if(isall){
					$('#table-box-result .checkall').addClass('choosed')
				}else{
					$('#table-box-result .checkall').removeClass('choosed')
				}
			}else{
				$('#table-box-result .checkall').removeClass('choosed')
			}
		})

		// 单个点击复核通过
		$(document).off('click','#table-box-result .checkpass').on('click','#table-box-result .checkpass',function (){
			checkPass(2,$(this).parent().parent().find('td').eq(4).text().trim(),$(this).parent().parent().find('.number').text().trim())
		})
		// 单个点击复核不通过
		$(document).off('click','#table-box-result .checkunpass').on('click','#table-box-result .checkunpass',function (){
			showcheckUnPass(2,$(this).parent().parent().find('.number').text().trim())
		})

		// 单个点击查看批付详情
		$(document).off('click','#table-box-result .number').on('click','#table-box-result .number',function (){
			showDetail($(this).text().trim())
		})
	})

	function dateFirstInit() {
		dateInit('date1');
		dateInit('date2');
	}

	function dateInit(date) {
		var mindate;
		var maxdate;
		var other;
		//日历控件bug.火狐及低版本ie浏览器对日期格式为XX-XX-XX解析不了,替换为XX/XX/XX可正常显示
		if (date == "date1") {
			if ($("#date1").val() == "") {
				mindate = '1981/12/31';
			} else {
				mindate = $("#date1").val().replace(new RegExp(/-/gm), "/");
			}
			maxdate = addDays(new Date(), 0).replace(new RegExp(/-/gm), "/");
			other = "date2"
		} else {
			// 开始日期在结束日期的前90天
			if ($("#date2").val() == "") {
				maxdate = new Date();
				mindate = '1981/12/31';
			} else {
				maxdate = $("#date2").val().replace(new RegExp(/-/gm), "/");
				mindate = addDays(new Date(maxdate), -30).replace(new RegExp(/-/gm), "/");
			}
			other = "date1";
		}
		ECode.calendar({
			inputBox: "#" + other,
			count: 1,
			flag: false,
			isWeek: false,
			isTime: false,
			range: {
				mindate: mindate,
				maxdate: maxdate
			},
			callback: function () {
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
		if (m <= 9) m = "0" + m;
		if (d <= 9) d = "0" + d;
		var cdate = y + "-" + m + "-" + d;

		return cdate;
	}

	/*重置*/
	$(document).on("click", ".reset", function () {
		$(this).parents(".condition-list").find("input").val("");
		$(this).parents(".condition-list").find(".select-input").val("");
		dateFirstInit();
	})
	
	//成功弹窗
	function successD(func){
		 var html = $('#success-tpl')
		 var win = $.dialog({
		 	content : html,
		 	width : 200,
		 	onShow : function($dialog, callback){
		 		setTimeout(function () {
		 			callback.close()
		 			func()
		 		},1500)
		 	},
		 	showClose : false
		 });
		 win.show()
	}

	// 失败弹窗
	function failD(message){
		 var html = template('fail-tpl',{errMessage:message})
		 var win = $.dialog({
		 	content : html,
		 	width : 250,
		 	onShow : function($dialog, callback){
		 		setTimeout(function () {
		 			callback.close()
		 		},1500)
		 	},
		 	showClose : false
		 });
		 win.show()
    }

	//表单提交查询
	function submitQuery(){
		var formObj = $("form[name='f0']");
	    formObj.attr("action", sysconfig.ctx+path+"query.htm");
	    formObj.submit();
	}

	// 复核不通过确认弹窗
	function showcheckUnPass(flag,number) {
		if($('.table-box .result-table').length == 0){
			var html = template('error-tpl',{errMessage:"没有可以操作的数据"})
			var win = $.dialog({
				title : "提示",
				content : html,
				width : 500,
				onShow : function($dialog, callback){
					$('.errorbtn').click(function () {
						callback.close();
					})
				},
				showClose : true
			});
			win.show()
		} else if(flag == 1 && $('#table-box-result tbody .choosed').length == 0){
			var html = template('error-tpl',{errMessage:"未勾选批付批次，请进行勾选"})
			var win = $.dialog({
				title : "提示",
				content : html,
				width : 500,
				onShow : function($dialog, callback){
					$('.errorbtn').click(function () {
						callback.close();
					})
				},
				showClose : true
			});
			win.show()
		} else{
			var html = $('#unpass-tpl')
			var win = $.dialog({
				title : "提示",
				content : html,
				width : 500,
				onShow : function($dialog, callback){
					$('body').off('click','.errorbtns .surebtn').on('click','.errorbtns .surebtn',function () {
						checkUnPass(flag,number);
						callback.close();
					})
					$('body').off('click','.errorbtns .cancelbtn').on('click','.errorbtns .cancelbtn',function () {
						callback.close();
					})
				},
				showClose : true
			});
			win.show()
		}
	}
	// 复核不通过
	function checkUnPass(flag, number) {
		// flag = 1 点击批量复核
		var arr = [];
		if (flag == 1) {
			for(var i = 0 ;i < $('#table-box-result tbody .choosed').length;i++){
				arr.push($('#table-box-result tbody .choosed').eq(i).parent().parent().find('.number').text().trim())
			}
			number = arr.toString()
		}
		if(validate(number)){		
			$.ajax({
			 	type: "POST",
			 	data:{"expendNo":number},
			 	url:sysconfig.ctx+path+"notPass.htm",
			 	dataType: "JSON",
			 	success: function(data) {
			 		//若页面过期跳转至首页
					isIntercepted(data);
			 		if(data.success != "s"){
	           	 		// 如果错误展示错误信息
			 			failD(data.errMessage)
	           	 	} else{
	           	 		successD(submitQuery)
	           	 	}	
			 	},
			 	error: function(msg) {
			 		failD("系统异常，请稍后重试");
			 	}
			});
		}
	}
	
	// 校验是否待复核
	function validate(expendNo){
		var flag = true;
		$.ajax({
		 	type: "POST",
		 	data: {"expendNo":expendNo},
		 	url:sysconfig.ctx+path+"validate.htm",
		 	dataType: "JSON",
		 	async: false,
		 	success: function(data) {
		 		//若页面过期跳转至首页
				isIntercepted(data);
				if(data.success != "s"){
					var html = template('status-error-tpl', {errMessage:data.errMessage})
					var win = $.dialog({
						title : "提示",
						content : html,
						width : 500,
						onShow : function($dialog, callback){
							$('.errorbtn').click(function () {
								callback.close();
							})
						},
						showClose : true
					});
					win.show()
					flag = false
				} 
		 	},
		 	error: function(msg) {
		 		failD("系统异常，请稍后重试");
		 		flag = false
		 	}
		});
		return flag			
	}
	
	// 初始化支付按钮
	function initPay(expendNo, win){
		$('#password').off('input propertychange').on('input propertychange',function () {
			if($(this).val() != ''){
				$('.pay-box .btn').removeClass('noclick')
			}else{
				$('.pay-box .btn').addClass('noclick')
			}
		})

		// 确认支付按钮点击事件
		$('.pay-box .btn').off('click').on('click',function () {
			$('.pay-box .errormsg').text('')
			if($('#password').val().trim() == ''){
				return
			}
			var password = $('#password').val().trim();
			$.ajax({
			 	type: "POST",
			 	data:{"password":password, "expendNo":expendNo},
			 	url:sysconfig.ctx+path+"pay.htm",
			 	dataType: "JSON",
			 	success: function(data) {
			 		//若页面过期跳转至首页
					isIntercepted(data);
			 		if(data.success != "s"){
	           	 		// 如果错误展示错误信息
			 			$('.pay-box .errormsg').text(data.responseMsg)
	           	 	} else{
	           	 		successD(submitQuery)
	           	 	}		 
			 	},
			 	error: function(msg) {
			 	}
			});
		})
	}
	// 复核通过
	function checkPass(flag,money,number) {
		if($('.table-box .result-table').length == 0){
			var html = template('error-tpl',{errMessage:"没有可以操作的数据"})
			var win = $.dialog({
				title : "提示",
				content : html,
				width : 500,
				onShow : function($dialog, callback){
					$('.errorbtn').click(function () {
						callback.close();
					})
				},
				showClose : true
			});
			win.show()
		} else if(flag == 1 && $('#table-box-result tbody .choosed').length == 0){
			var html = template('error-tpl',{errMessage:"未勾选批付批次，请进行勾选"})
			var win = $.dialog({
				title : "提示",
				content : html,
				width : 500,
				onShow : function($dialog, callback){
					$('.errorbtn').click(function () {
						callback.close();
					})
				},
				showClose : true
			});
			win.show()
		} else{
			var arr = [];
			var count = 1;
			// flag为1 代表批量操作
			if(flag == 1){
				for (var i = 0 ; i < $('#table-box-result tbody .choosed').length;i++){
					money = (money*1) + ($('#table-box-result tbody .choosed').eq(i).parent().parent().find('td').eq(4).text().trim() * 1)
					arr.push($('#table-box-result tbody .choosed').eq(i).parent().parent().find('td').eq(3).find('a').text().trim())
				}	
				count = $('#table-box-result tbody .choosed').length	
				number = arr.toString()
			}
			if(validate(number)){
				var html = $('#purchase-pay-tpl')
				var win = $.dialog({
					title : "提示",
					content : html,
					width : 500,
					onShow : function($dialog, callback){
						$('.errorbtn').click(function () {
							callback.close();
						})
					},
					showClose : true
				});
				win.show()
				$('#money').text(money)
				$('#totalcount').text(count)
				$('.pay-box .errormsg').text('')
				initPay(number)
			}
		}
	}
	
	//提交导出
	function submitExport(){
		if($(".result-table").length <=0){
			alert("请先查询数据!");
		}
		else{
			 $.ajax({
			 	url: sysconfig.ctx + '/authStatus',
			 	crossDomain: true,
			 	cache: false,
			 	dataType: 'jsonp',
			 	success: function (data) {
			 		if (data.hasLogin) {
			 			var formObj = $("form[name='f0']");
			 			formObj.attr("action", sysconfig.ctx+path+"export.htm");
			 			formObj.submit();
			 		} else {
			 			window.location.href=sysconfig.ctx+path+"init.htm";
			 		}
			 	},
			 	error : function(data) {
			 		window.location.href=sysconfig.ctx+path+"init.htm";
			 	}
			 });
		}

	}
	
	// 展示批次详情
	function showDetail(number) {
		var calReq = {};
		calReq.expendNo = number;
		calReq.currentPage = "1";
		calReq.pageSize = "10";
		$.ajax({
			type: "POST",
			contentType: "application/json",
		 	url: sysconfig.ctx + path+'queryDetail.htm',
		 	dataType: 'json',
		 	data: JSON.stringify(calReq),
		 	success: function (data) {
		 		//若页面过期跳转至首页
				isIntercepted(data);
		 		if (data.success == 's') {
					//读取数据
		 			var html = template('detail-tpl', data);
		 			var table = template('detail-table-tpl', data);
		 			var win = $.dialog({
									content : html,
									width : 700,
									onShow : function($dialog, callback){
										$(document).on('click','.detailcontent .btn',function () {
											callback.close();
										})
									},
									showClose : true
							   });
					win.show()
					$(".detailtable").html(table);
		 		} else {
		 			failD(data.errMessage);
		 		}
		 	},
		 	error : function(data) {
		 		failD("系统异常，请稍后重试");
		 	}
		});	
	}

	//分页查询,上一页
	$(document).on("click",'#pageClass .prev-page',function(event){
		event.preventDefault();
		var currentPage=$('#currentPage').val();
		var nextPage=parseInt(currentPage)-1;

		//首页无法点击上一页
		if(currentPage==1){
			return;
		}

		if($("#pageClass").length <=0){
			alert("请先查询数据!");
		}else{
			document.getElementById("currentPage").value=nextPage;
			var formObj = $("form[name='f0']");
			formObj.attr("action", sysconfig.ctx+path+"query.htm");
			formObj.submit();
		}
	});

	//分页查询,下一页
	$(document).on("click",'#pageClass .next-page',function(event){
		event.preventDefault();
		var currentPage=$('#currentPage').val();
		var nextPage=parseInt(currentPage)+1;
		var pages=parseInt($('#pageSize').val())

		//末页无法点击下一页
		if(currentPage==pages){
			return;
		}

		if($("#pageClass").length <=0){
			alert("请先查询数据!");
		}else{
			document.getElementById("currentPage").value=nextPage;
			var formObj = $("form[name='f0']");
			formObj.attr("action", sysconfig.ctx+path+"query.htm");
			formObj.submit();
		}
	});

	//分页查询,指定页
	$(document).on("click",'#pageClass .goto',function(event){
		event.preventDefault();
		var currentPage=parseInt($('#targetPage').val());
		var pageSize=parseInt($('#pageSize').val());

		if($("#pageClass").length <=0){
			alert("请先查询数据!");
		}else{
			if(currentPage === "" || currentPage == null){
				alert("请输入页数!");
			}
			else if(currentPage>pageSize){
				alert("指定页数不能超过最大页数!");
			}
			else if(currentPage<=0){
				alert("指定页数不能小于0!");
			}else if(isNaN(currentPage)){
				alert("请输入数字!");
			}
			else{
				document.getElementById("currentPage").value=currentPage;
				var formObj = $("form[name='f0']");
				formObj.attr("action", sysconfig.ctx+path+"query.htm");
				formObj.submit();
			}
		}
	});
	
	// 展示批次详情翻页
	function showDetailPage(currentPage,expendNo) {
		var calReq = {};
		calReq.expendNo = expendNo;
		calReq.currentPage = currentPage;
		//calReq.pageSize = $("#detailpageSize").val();
		$.ajax({
			type: "POST",
			contentType: "application/json",
		 	url: sysconfig.ctx + path+'queryDetail.htm',
		 	dataType: 'json',
		 	data: JSON.stringify(calReq),
		 	success: function (data) {
		 		//若页面过期跳转至首页
				isIntercepted(data);
		 		if (data.success == 's') {
                    //读取数据
                    var html = template('detail-tpl', data);
                    var table = template('detail-table-tpl', data);
                    var win = $.dialog({
                        content : html,
                        width : 700,
                        onShow : function($dialog, callback){
                            $(document).on('click','.detailcontent .btn',function () {
                                callback.close();
                            })
                        },
                        showClose : true
                    });
                    win.show()
                    $(".detailtable").html(table);

		 		} else {
		 			failD(data.errMessage);
		 		}
		 	},
		 	error : function(data) {
		 		failD("系统异常，请稍后重试");
		 	}
		});	
	}


	//批付明细分页查询,上一页
	$(document).on("click",'#detailpage .prev-page',function(event){
		event.preventDefault();
		var currentPage=$('#detailcurrentPage').val();
		var nextPage=parseInt(currentPage)-1;
        var expendNo = $('#detailexpendNo').val();

		//首页无法点击上一页
		if(currentPage==1){
			return;
		}

		if($("#detailpage").length <=0){
			alert("请先查询数据!");
		}else{
			document.getElementById("detailcurrentPage").value=nextPage;
			showDetailPage(nextPage,expendNo);
		}
	});



	//分页查询,下一页
	$(document).on("click",'#detailpage .next-page',function(event){
		event.preventDefault();
		var currentPage=$('#detailcurrentPage').val();
		var nextPage=parseInt(currentPage)+1;
		var pages=parseInt($('#detailpageSize').val())
		var expendNo = $('#detailexpendNo').val();
		//末页无法点击下一页
		if(currentPage==pages){
			return;
		}

		if($("#pageClass").length <=0){
			alert("请先查询数据!");
		}else{
			document.getElementById("detailcurrentPage").value=nextPage;
			showDetailPage(nextPage,expendNo);
		}
	});

	//分页查询,指定页
	$(document).on("click",'#detailpage .goto',function(event){
		event.preventDefault();
		var currentPage=parseInt($('#detailtargetPage').val());
		var pageSize=parseInt($('#detailpageSize').val());
        var expendNo = $('#detailexpendNo').val();

		if($("#detailpage").length <=0){
			alert("请先查询数据!");
		}else{
			if(currentPage === "" || currentPage == null){
				alert("请输入页数!");
			}
			else if(currentPage>pageSize){
				alert("指定页数不能超过最大页数!");
			}
			else if(currentPage<=0){
				alert("指定页数不能小于0!");
			}else if(isNaN(currentPage)){
				alert("请输入数字!");
			}
			else{
				document.getElementById("detailcurrentPage").value=currentPage;
				showDetailPage(currentPage,expendNo);
			}
		}
	});

});