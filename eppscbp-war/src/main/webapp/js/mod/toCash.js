if(typeof resRoot == 'undefined'){
	resRoot = '';
}
if(require)require.config({
    baseUrl: (resRoot ? context +"/":'') +'scripts',
    paths: {
        jquery: 'http://script.suning.cn/public/js/jquery.1.7.2',
        base: 'base',
        core: 'core'
    },
    shim: {
        base: ['jquery']
    }
});


require(['jquery', 'mod/cash' ,'lib/monitor/monitor'], function($, cash) {
    //登录用户账户分类 public/company/person
    var type = 'public';
    //交互入口
    cash.init(type);
});