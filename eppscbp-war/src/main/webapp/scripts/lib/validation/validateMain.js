define(['jquery','lib/validation/validate'], function($,validate) {
	// 自定义验证框架中的密码控件的格式
	jQuery.validator.addMethod('sambPassword', function(value, element) {
        // return this.optional(element) || /(?!^[0-9]+$)(?!^[A-z]+$)(?!^[^A-z0-9]+$)^.{6,20}$/.test(value);
        // return this.optional(element) || /^(?![\d]+$)(?![a-zA-Z]+$)(?![^\da-zA-Z]+$).{6,20}$/.test(value);
        return this.optional(element) || /^(?=.*\d)(?=.*[a-zA-Z])[a-zA-Z\d]{6,20}$/.test(value);
	}, '6~20位字母和数字组合，不允许有空格，注意区分大小写');


    // 必须为数字
    jQuery.validator.addMethod('regNum', function(value, element) {
        return this.optional(element) || /^[0-9]*$/.test(value);
        // return this.optional(element) || /^[0-9]*$/.test(value.replace(/\s+/g,''));
    }, '请输入数字');

    // 组织机构代码
    jQuery.validator.addMethod('regOrgCode', function(value, element) {
        var flag = true,
            arr = value.replace(/\s+/g,'').split('-');

        if (arr.length == 2) {
            var first = arr[0];
            var second = arr[1];
            var isNumF = /^[0-9]*$/.test(first);
            var isNumS = /^[0-9]*$/.test(second);
            if (first.length == 8 && second.length == 1 && isNumF && isNumS) {
                flag = true;
            } else {
                flag = false;
            }
        } else {
            flag = false;
        }

        return this.optional(element) || flag;
    }, '组织机构代码正确形式为：12345678-0');

    // 手机号码验证
    jQuery.validator.addMethod('isMobile', function(value, element) {
        var length = value.length;
        // var mobile = /^(0|86|17951)?(13[0-9]|15[012356789]|17[0678]|18[0-9]|14[57])[0-9]{8}$/;
        var mobile = /^(0|86|17951)?(1[0-9])[0-9]{9}$/;
        return this.optional(element) || (length == 11 && mobile.test(value));
    }, '请正确填写您的手机号码');

    // 数字、英文字符或空格
    jQuery.validator.addMethod('alNum', function(value, element) {
        return this.optional(element) || /^[A-Za-z0-9]+$/.test(value);
    }, '只能包括数字、英文字符');

     // 不包含金融机构
    jQuery.validator.addMethod('noJinrong', function(value, element) {
        return this.optional(element) || !/^(.*银行.*|.*信用合作社.*|.*证券.*|.*保险.*|.*信托.*|.*交易所.*|.*期货.*|.*交易所.*|.*交易中心.*|.*金融.*|.*金融租赁.*|.*资产管理.*|.*理财.*|.*基金.*|.*基金管理.*|.*投资管理.*|.*财富管理.*|.*股权投资基金.*|.*网贷.*|.*网络借贷.*|.*P2P.*|.*股权众筹.*|.*互联网保险.*|.*支付.*)$/.test(value) ;
    }, '只能包括非金融机构');

    // 身份证-验证
    jQuery.validator.addMethod('idNum', function(value, element) {
        return this.optional(element) || /^(^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$)|(^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])((\d{4})|\d{3}[Xx])$)$/.test(value);
    }, '只能包括数字、英文字符或空格');


    // 必须为非中文
    jQuery.validator.addMethod('regNoZh', function(value, element) {
        return this.optional(element) || !(/([\u4E00-\u9FA5]|[\u0391-\uFFE5])+/.test(value));
        // return this.optional(element) || /^[0-9]*$/.test(value.replace(/\s+/g,''));
    }, '请输入非中文');

    // 新密码不能与老密码重复
    jQuery.validator.addMethod("regSame", function(value, element) {
        return this.optional(element) || same(value);  
    }, "新密码不能与老密码重复");

    function same(pwd) {
        var oldPwd = $("#curLoginPassword").val();
        if (oldPwd == pwd)
            return false;
        else  
            return true;
    }

    // 空格校验
    jQuery.validator.addMethod("regBlank", function(value, element) {
        return this.optional(element) || !/\s/g.test(value);
    }, "不能有空格");
});