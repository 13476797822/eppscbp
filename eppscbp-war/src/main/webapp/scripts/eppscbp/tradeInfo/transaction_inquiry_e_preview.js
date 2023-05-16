/**
 * ==========================================================
 * Copyright (c) 2015, suning.com All rights reserved.
 * 易付宝企业版 - 我的账户js
 * Author: luj
 * Date: 2015-08-18 16:30:00 897183
 * ==========================================================
 */
if (require) require.config({
	baseUrl: 'scripts',
	paths: {
		jquery: '/eppscbp/scripts/lib/jquery.1.7.2',
		tabmin: '/eppscbp/scripts/lib/jiaoyi/ECode.tab.min',
		dialog: '/eppscbp/scripts/lib/dialog/dialog-ie8'
	},
	shim: {
		valid: ['jquery'],
		bankSelect: ['jquery'],
		verCitySelect: ['jquery'],
		dialog: ['jquery']
	}
});

require(['jquery', 'tabmin', 'dialog'], function ($, abmin, dialog) {
	$('[data-href]').click(function(){
		if($(this).attr('data-href')){
			window.location.href = $(this).attr('data-href');
		}
	});
    $("#btnDownload").click(function(){
		if($(this).hasClass('gray')){
			return;
		}
		var formObj = $("form[name='f0']");
		formObj.attr("action", sysconfig.ctx + "/tradeQueryAuth/tradeInfo/downloadBatchElectronicPDF.htm");
		formObj.submit();
		$(this).addClass('gray');
    });
});
