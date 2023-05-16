define(['lib/monitor/anchor', 'lib/monitor/collect'], function() {
	//入口：B2C项目-数据采集系统-点击数据埋点
	$("[name^=xd_none]").live("click",function(){
	   sa.click.sendDatasIndex(this);
	 });
});

