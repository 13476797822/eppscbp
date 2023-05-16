<#include "/WEB-INF/ftl/commons/reference.ftl">
<meta http-equiv="Cache-Control" content="no-cache" />   
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chorme=1">
<script language="javascript">
	var sysconfig = {
		ctx : "${ctx}",
    	stimestamp : '${stimestamp}'   
	};

	function isIntercepted(data){
	  //如果被passport拦截，就跳转到passport登陆
	  if(typeof(data) == "undefined"||data.idsIntercepted){
          window.location.href=sysconfig.ctx+"/home/homeInit/init.htm";
          throw "idsIntercepted";
	  }
	}
</script>
