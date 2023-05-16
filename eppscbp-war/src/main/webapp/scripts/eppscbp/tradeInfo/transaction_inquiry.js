/**
 * ==========================================================
 * Copyright (c) 2021, suning.com All rights reserved.
 * 易付宝企业版 - 我的账户js
 * Author: jt
 * Date: 2021-02-23
 * ==========================================================
 */
if (require) require.config({
	baseUrl: 'scripts',
	paths: {
		jquery: '/eppscbp/scripts/lib/jquery.1.7.2',
		tabmin: '/eppscbp/scripts/lib/jiaoyi/ECode.tab.min',
		calendar: '/eppscbp/scripts/lib/jiaoyi/ECode.calendar',
		dialog: '/eppscbp/scripts/lib/dialog/dialog-ie8'
	},
	shim: {
		valid: ['jquery'],
		bankSelect: ['jquery'],
		verCitySelect: ['jquery'],
		dialog: ['jquery']
	}
});

require(['jquery', 'calendar', 'tabmin', 'dialog'], function ($, calendar, tabmin, dialog, BankSelect) {
	var jiaoyiIndex = 0;//交易类型选框1索引
	var jiaoyList = $("#choose01 li");
	//删除缓存结果页数据
	sessionStorage.removeItem("resultData");
	//交易类型初始化
	jiaoyList.each(function(index,item){
		if($("#choose01").find('input.value').val() == $(item).attr('data-value')){
			jiaoyiIndex = index;
		}
	});
	//表格全选/全不选
	$("#chkAll").change(function() { 
		if($("#chkAll").is(':checked')){
			$("input.chk").attr("checked","true");
		}else{
			$("input.chk").removeAttr("checked"); 
		}
	});
	//选择表格项存入本地缓存
	var saveChooseTable = sessionStorage.getItem("chooseTable");
	saveChooseTable = saveChooseTable ? JSON.parse(saveChooseTable) : [];
	var saveFileAddress = sessionStorage.getItem("fileAddress");
	saveFileAddress = saveFileAddress ? JSON.parse(saveFileAddress) : [];
	
	// 展示选中的表格
	if(saveChooseTable.length > 0){
		var list = $("input.chk");
		var chooseAllFlag = true;
		var currentIndex = $("#currentPage").val() - 1;
		if(saveChooseTable[currentIndex]){
			var saveChooseList = saveChooseTable[currentIndex].split(',');
			list.each(function(index,item){
				for(var i = 0;i<saveChooseList.length;i++){
					if(saveChooseList[i] == $(item).attr('data-value')){
						$(item).attr("checked","true");
					}
				}
				if(!$(item).is(':checked')){
					chooseAllFlag = false;
				}
			});
			if(chooseAllFlag){
				$("#chkAll").attr("checked","true");
			}
		}
		var chooseList = [];
		for(var i= 0;i<saveChooseTable.length;i++){
			if(saveChooseTable[i]){
				chooseList.push(saveChooseTable[i])
			}
		}
		$("#chooseTable").val(chooseList.join(','));
	}
	var fileAddress = '';
	if(saveFileAddress.length > 0){
		var chooseList = [];
		for(var i= 0;i<saveFileAddress.length;i++){
			if(saveFileAddress[i]){
				chooseList.push(saveFileAddress[i])
			}
		}
		fileAddress = chooseList.join(',');
	}
	$("#chkAll,input.chk").on('change',function(){
		var list = $("input.chk");
		var chooseTable = [];
		var chooseFileAddress = [];
		var chooseAllFlag = true;
		var currentIndex = $('#currentPage').val() - 1;
		list.each(function(index,item){
			if($(this).is(':checked')){
				//防重复选项
				chooseTable.push($(item).attr('data-value'));
				chooseFileAddress.push($(item).attr('data-fileAddress'));
			}
			else{
				chooseAllFlag = false;
			}
		});
		if(chooseAllFlag){
			$("#chkAll").attr("checked","true");
		}
		else{
			$("#chkAll").removeAttr("checked");
		}
		
		saveChooseTable[currentIndex] = chooseTable.join(',');
		sessionStorage.setItem("chooseTable", JSON.stringify(saveChooseTable));
		saveFileAddress[currentIndex] = chooseFileAddress.join(',');
		sessionStorage.setItem("fileAddress", JSON.stringify(saveFileAddress));
		//存储id
		var chooseList = [];
		for(var i= 0;i<saveChooseTable.length;i++){
			if(saveChooseTable[i]){
				chooseList.push(saveChooseTable[i])
			}
		}
		$("#chooseTable").val(chooseList.join(','));
		//存储fileAddress
		var chooseList2 = [];
		for(var i= 0;i<saveFileAddress.length;i++){
			if(saveFileAddress[i]){
				chooseList2.push(saveFileAddress[i])
			}
		}
		fileAddress = chooseList2.join(',')
	})

	//合计信息切换
	var curIndex = 0;
	var maxIndex = $(".coloum-total-info").length;
	if(maxIndex < 5){
		$('.switchArea').hide();
	}
	else{
		$('.column-total').css('height','50px')
	}
	$(".coloum-total-info").each(function(index,item){
		if($(item).index()>3){
			$(item).addClass('hide');
		}
	});

	$('#btnUp').click(
		function(){
			switchTabIndex('down');
		}
	);
	$('#btnDown').click(
		function(){
			switchTabIndex('up');
		}
	);
	function switchTabIndex(type){
		if(maxIndex <= 3)return;
		if(type==='up'){
			if(curIndex + 4 < maxIndex){
				curIndex = curIndex + 2;
				$(".coloum-total-info").eq(curIndex - 1).hide();
				$(".coloum-total-info").eq(curIndex - 2).hide();
				$(".coloum-total-info").eq(curIndex + 2).show();
				$(".coloum-total-info").eq(curIndex + 3).show();
			}
		}else if(type==='down'){
			if(curIndex > 0){
				curIndex = curIndex - 2;
				$(".coloum-total-info").eq(curIndex).show();
				$(".coloum-total-info").eq(curIndex + 1).show();
				$(".coloum-total-info").eq(curIndex + 4).hide();
				$(".coloum-total-info").eq(curIndex + 5).hide();
			}
		}
		
	}

	//日期初始化
	dateFirstInit();
	function dateFirstInit() {
		//日期默认值
		if ($("#date01").val() == null || $("#date01").val() == "") {
			console.log(addDays(new Date(), -6).replace(new RegExp(/-/gm), "-"))
			$("#date01").val(addDays(new Date(), -6).replace(new RegExp(/-/gm), "-"));
		}
		if ($("#date02").val() == null || $("#date02").val() == "") {
			$("#date02").val(formatStrDate(new Date()));
		}
		dateInit('date01');
		dateInit('date02');
	}
	//数字格式化
    function formatNum(num) { //补0
        return num.toString().replace(/^(\d)$/, "0$1");
    }
	//日期格式化
    function formatStrDate(vArg) { //格式化日期
        switch (typeof vArg) {
            case "string":
                vArg = vArg.split(/-|\//g);
                return vArg[0] + "-" + formatNum(vArg[1]) + "-" + formatNum(vArg[2]);
                break;
            case "object":
                return vArg.getFullYear() + "-" + formatNum(vArg.getMonth() + 1) + "-" + formatNum(vArg.getDate());
                break;
        }
    };
	function dateInit(date) {
		var mindate;
		var maxdate;
		var other;
		//日历控件bug.火狐及低版本ie浏览器对日期格式为XX-XX-XX解析不了,替换为XX/XX/XX可正常显示
		if (date == "date01") {
			if ($("#date01").val() == "") {
				mindate = '1981/12/31';
			} else {
				mindate = $("#date01").val().replace(new RegExp(/-/gm), "/");
			}
			maxdate = addDays(new Date(), 0).replace(new RegExp(/-/gm), "/");
			other = "date02"
		} else {
			// 开始日期在结束日期的前90天
			if ($("#date02").val() == "") {
				maxdate = new Date();
				mindate = '1981/12/31';
			} else {
				maxdate = $("#date02").val().replace(new RegExp(/-/gm), "/");
				mindate = addDays(new Date(maxdate), -90).replace(new RegExp(/-/gm), "/");
			}
			other = "date01";
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
	
	//初始化输入框
	function initWrite(){
		//清空输入框
		$(".data-long-form input.data-input2,.data-long-form input.input").val("");
		//业务类型赋值
		$(".check-select2 input.value").val($(".check-select2 li").eq(0).attr("data-value"));
		$(".check-select2 input.name").val($(".check-select2 li").eq(0).attr("title"));
		//订单状态赋值
		$(".check-select input.value").val($(".check-select li").eq(1).attr("data-value"));
		$(".check-select input.name").val($(".check-select li").eq(1).attr("title"));
		initCheckBox($('.check-select'),0);
		//交易类型赋值
		$("#choose01 input.value").val($(".choose-list1 li").eq(0).attr("data-value"));
		$("#choose01 input.name").val($(".choose-list1 li").eq(0).attr("title"));
		// $("#choose02 input.value").val($(".choose-list2").eq(0).find("li").eq(0).attr("data-value"));
		jiaoyiIndex = 0;
		// initCheckBox($('#choose02'),jiaoyiIndex);
		$(".choose-list2").find("input").attr("checked","true");
		writeCheckValue($(".choose-list2").eq(0).find('input.all'));
	}
	//初始化查询条件的checkbox是否选中
	function initCheckBox(parent,index,flag){
		var box = parent,
			list = box.find('ul').eq(index).find('li'),
			value = box.find('input.value').val();
		var nameList = [];
		var allFlag = true;
		list.each(function(index,item){
			if($(item).attr('data-value')){
				if(value.match($(item).attr('data-value'))){
					$(item).find('input').attr("checked","true");
					if(flag){
						nameList.push($(item).attr('title'));
					}
				}
				else{
					$(item).find('input').removeAttr("checked");
					allFlag = false;
				}
			}
			if(flag && nameList.length>0){
				box.find('input.name').val(nameList.join(','))
			}
		});
		if(allFlag){
			box.find('ul').eq(index).find('.all').attr("checked","true");
			if(flag){
				box.find('input.name').val('全部,'+ nameList.join(','))
			}
		}
	}
	function initSingleChoose(parent){
		var box = parent,
			list = box.find('li'),
			value = box.find('input.value').val();
		list.each(function(index,item){
			if($(item).attr('data-value') == value){
				box.find('input.name').val($(item).attr('title'));
			}
		});
	}
	initCheckBox($('.check-select'),0,1);
	initCheckBox($('#choose02'),jiaoyiIndex,1);
	initSingleChoose($('.check-select2'));
	initSingleChoose($('#choose01'));
	//业务类型单选
	$('.check-list2 li').on('click',function(){
		$(this).parents('.uitext-select').find('.name').val($(this).attr('title'));
		$(this).parents('.uitext-select').find('.value').val($(this).attr('data-value'));
	});
	//订单状态多选
	$(".check-select i,.check-select2 i").click(function(event){
		event.stopPropagation();
  		event.cancelBubble = true;
		var $this = $(this);
		$this.parents('.uitext-select').addClass('current');
		var selectList = $('.uitext-select');
		selectList.each(function(index,item){
			if($(item).hasClass('current')){
				if($this.hasClass('up')){
					$this.removeClass('up');
					$this.next("ul").hide();
				}
				else{
					$this.addClass('up');
					$this.next("ul").show();
				}
				$(item).removeClass('current')
			}
			else{
				$(item).find('ul').hide();
				$(item).find('i').removeClass('up');
			}
		});
	});
	//订单状态多选
	$('.check-list input').on('change',function(){
		writeCheckValue($(this));
	});
	//阻止冒泡
	$('.check-list,.choose-list2').click(function(event){
		event.stopPropagation();
  		event.cancelBubble = true;
	});
	//点击空白区域隐藏选择框
	$("body").click(function(event){
		$(".uitext-select ul").hide();
		$(".uitext-select i").removeClass('up');
	});
	function writeCheckValue($this){
		var list = $this.parents('ul').find('li');
		var value = [],name = [];
		//点击全部
		if($this.hasClass('all')){
			if($this.is(':checked')){
				list.find('input').attr("checked","true");
			}
			else{
				list.find('input').removeAttr("checked");
			}
		}
		else if(!$this.is(':checked')){
			$this.parents('ul').find('.all').removeAttr("checked");
		}
		list.each(function(index,item){
			if($(item).find('input').is(':checked')){
				name.push($(item).attr('title'));
				if(!$(item).find('input').hasClass('all')){
					value.push($(item).attr('data-value'));
				}
			}
		});
		$this.parents('.uitext-select').find('.name').val(name.join(','))
		$this.parents('.uitext-select').find('.value').val(value.join(','))
	}
	$('#choose01 i').click(function(event){
		event.stopPropagation();
  		event.cancelBubble = true;
		$(this).parents('#choose01').find('ul').show();
		if($(this).hasClass('up')){
			$(this).removeClass('up');
			$(this).parents('#choose01').find('ul').hide();
		}
		else{
			$(this).addClass('up');
			$(this).parents('#choose01').find('ul').show();
		}
		$('.check-select i,.check-select2 i').removeClass('up');
		$('.check-select ul,.check-select2 ul').hide();
	});
	$('#choose02 i').click(function(event){
		event.stopPropagation();
  		event.cancelBubble = true;
		var $this = $(this);
		if($('#choose01 .value').val()){
			if($this.hasClass('up')){
				$this.removeClass('up');
				$this.parents('#choose02').find('ul').eq(jiaoyiIndex).hide();
			}
			else{
				$this.addClass('up');
				$this.parents('#choose02').find('ul').eq(jiaoyiIndex).show();
			}
		}
		$('.check-select i,.check-select2 i').removeClass('up');
		$('.check-select ul,.check-select2 ul').hide();
	});
	//交易类型单选
	$('#choose01 li').click(function(){
		var $parent = $(this).parents('.uitext-select');
		$parent.find('ul').hide();
		$parent.find('.value').val($(this).attr('data-value'));
		$parent.find('.name').val($(this).attr('title'));
		var index = $(this).index();
		//存储交易类型索引
		jiaoyiIndex = index;
		$('#choose02').find('.value').val('');
		$('#choose02').find('.name').val('');
		$(".choose-list2").eq(index).find('input.all').attr("checked","true");
		writeCheckValue($(".choose-list2").eq(index).find('input.all'));
		setTimeout(function(){
			$('#choose02 ul').eq(index).show();
			$('#choose02 i').addClass('up');
		},10);
	});
	$('.choose-list2 input').on('change',function(){
		writeCheckValue($(this));
	});

	/*重置*/
	$(document).on("click", ".btnreset", function () {
		initWrite();
		dateFirstInit();
	});
	//金额保留2位小数
	$("#amtStart").on('input',function(){
		var val = $(this).val();
		$(this).val(val.match(/\d+(\.\d{0,2})?/) ? val.match(/\d+(\.\d{0,2})?/)[0] : '')
	});
	$("#amtEnd").on('input',function(){
		var val = $(this).val();
		$(this).val(val.match(/\d+(\.\d{0,2})?/) ? val.match(/\d+(\.\d{0,2})?/)[0] : '')
	});
	//提交查询
	$('.btnquery').click(function(){
		submitQuery('none');
	});
	function submitQuery(flag){
		if($("#amtStart").val() && $("#amtStart").val() && $("#amtStart").val() - 0 > $("#amtEnd").val() - 0){
			var amtStart = $("#amtEnd").val(),
				amtEnd = $("#amtStart").val();
			$("#amtStart").val(amtStart);
			$("#amtEnd").val(amtEnd);
		}
		if(flag=='none'){
		  if($("#pageClass").length>0){
			$("#currentPage").attr("value","1");					
		  }				
		}
		sessionStorage.removeItem("chooseTable");
		var formObj = $("form[name='f0']");
		formObj.attr("action", sysconfig.ctx+"/tradeQueryAuth/tradeInfo/queryTradeInfo.htm");
		formObj.submit(); 
	}

	//提交导出
	$('.outport-btn').click(function(){
		if($("#tableResult").length <= 0){
			var win;
			var opts = {
				title: '提示', // 标题
				content: $('#exportError').html(),   // 内容
				width: 508,    // 宽度
				onShow: function($dialog, callback) {
					$('.exportErrorBtn').click(function(){
						win.close();
					});
				},  // 显示回调
				showClose: true,
				buttonsAlign: 'center',	// 按钮对齐
			}
			win = $.dialog(opts);
			win.show();
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
						formObj.attr("action", sysconfig.ctx + "/tradeQueryAuth/tradeInfo/exportTradeInfo.htm");
						formObj.submit(); 
					} else {
						window.location.href= sysconfig.ctx + "/tradeQueryAuth/tradeInfo/init.htm";
					}
				},
				error : function(data) {
					window.location.href=sysconfig.ctx + "/tradeQueryAuth/tradeInfo/init.htm";
				}
			}); 
			
		}
	});
	var summaryFlag = true;
	//汇总电子表单
	$('.summary-btn').click(function(){
		if($("#tableResult").length <= 0){
			var win;
			var opts = {
				title: '提示', // 标题
				content: $('#exportError').html(),   // 内容
				width: 508,    // 宽度
				onShow: function($dialog, callback) {
					$('.exportErrorBtn').click(function(){
						win.close();
					});
				},  // 显示回调
				showClose: true,
				buttonsAlign: 'center',	// 按钮对齐
			}
			win = $.dialog(opts);
			win.show();
		}
		var params;
		if($("#chooseTable").val()){
			params = {
				orderCreateTimeStart: "",
				orderCreateTimeEnd: "",
				reveiverName: "",
				reveiverCardNo: "",
				amtStart: "",
				amtEnd: "",
				bizType: "",
				status: "",
				flag:$("input[name=flag]").val(),
				orderNo: "",
				transType:"",
				// id: $("#chooseTable").val() // 仅为有效值，上面的是凑参数
				fileAddress: fileAddress
			}
		}
		else{
			params = {
				orderCreateTimeStart: $("input[name=orderCreateTimeStart]").val(),
				orderCreateTimeEnd: $("input[name=orderCreateTimeEnd]").val(),
				reveiverName: $("input[name=reveiverName]").val(),
				reveiverCardNo: $("input[name=reveiverCardNo]").val(),
				amtStart: $("input[name=amtStart]").val(),
				amtEnd: $("input[name=amtEnd]").val(),
				bizType: $("input[name=bizType]").val(),
				status: $("input[name=status]").val(),
				flag: $("input[name=flag]").val(),
				orderNo: $("input[name=orderNo]").val(),
				transType: $("input[name=transType]").val(),
				fileAddress: ""
				// id:"" // 无效值，凑参数
			}
		}
		if(!summaryFlag)return;
		setTimeout(function(){
			summaryFlag = true;
		},10000);
		summaryFlag = false;
		$.ajax({
			type: "POST",
			url: sysconfig.ctx + "/tradeQueryAuth/tradeInfo/queryBatchEleReceiptPDFAddress.htm",
			data: params,
			dataType: "json",
			success: function(data) {
				console.log(data);
				
				sessionStorage.setItem("resultData", JSON.stringify(data));
				if(data.success){
					//成功
					window.location.href = sysconfig.ctx + "/tradeQueryAuth/tradeInfo/batchEleResult.htm?success=1&msg="+data.msg+"&pdfAddress="+data.pdfAddress;
				}else{
					//失败
					window.location.href = sysconfig.ctx + "/tradeQueryAuth/tradeInfo/batchEleResult.htm?success=0&msg="+data.msg+"&pdfAddress="+data.pdfAddress;
					
				}
			},
			error: function(msg) {
				//失败
				window.location.href =  sysconfig.ctx + "/tradeQueryAuth/tradeInfo/batchEleResult.htm?success=2";
			}
		});  
	});
	  //分页查询,上一页
	$(".prev-page").on("click",function(event){  
		event.preventDefault();  
		
		var currentPage=$('#currentPage').val(); 
		var nextPage=parseInt(currentPage)-1;
		
		//首页无法点击上一页
		if(currentPage==1){
			return;
		}
		
		if($("#pageClass").length <=0){
			alert("请先查询数据!");
		}
		else{ 
			document.getElementById("currentPage").value=nextPage;          	
			var formObj = $("form[name='f0']");
			formObj.attr("action", sysconfig.ctx+"/tradeQueryAuth/tradeInfo/queryTradeInfo.htm");
			formObj.submit();            
		}            
	}); 	
	
	//分页查询,下一页
	$(".next-page").on("click",function(event){  
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
		formObj.attr("action", sysconfig.ctx+"/tradeQueryAuth/tradeInfo/queryTradeInfo.htm");
		formObj.submit();            
		}            
			}); 
			
	$('.goto').on("click",function(event){
		targetQuery()
	})	
	//分页查询,指定页
	function targetQuery(){
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
				formObj.attr("action", sysconfig.ctx+"/tradeQueryAuth/tradeInfo/queryTradeInfo.htm");
				formObj.submit(); 
			}    	           
		}             
	} 
});