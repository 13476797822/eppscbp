if(typeof resRoot == 'undefined'){
	resRoot = '';
}
if(require)require.config({
    baseUrl: (resRoot ? context +"/":'') +'js',
    paths: {
        jquery: 'http://script.suning.cn/public/js/jquery.1.7.2'
    },
    shim: {
        base: ['jquery']
    }
});


//require(['jquery', 'mod/cash'], function($, cash) {
//    //登录用户账户分类 public 对公账户/company 个体工商户/person 个人经营
//    var type = 'company';
//    //交互入口
//    cash.init(type);
//});

require(['jquery', 'mod/cash-open'], function($, cash) {
    //登录用户账户分类 public 对公账户/company 个体工商户/person 个人经营
    var type = 'company';
    //交互入口
    cash.init(type);
});