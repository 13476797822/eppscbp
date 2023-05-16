<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>${title}</title>
<#include "/WEB-INF/ftl/commons/meta.ftl"/>
${head}
<link rel="shortcut icon" href="https://respay.suning.com/sfbmws/style/temp/logo.ico?stimestamp=${stimestamp}" type="image/x-icon">
<link rel="icon" href="https://respay.suning.com/sfbmws/style/temp/logo.ico?stimestamp=${stimestamp}" type="image/x-icon">
<script language="javascript">
	//公共头尾接入-菜单编码-交易中心
    var menuCode= "GJK01";
</script>
<!--[if lt IE 10]>
<script type="text/javascript"src="/eppscbp/js/lib/jquery-1.8.3.min.js" charset="utf-8"></script>
<script type="text/javascript"src="/eppscbp/js/lib/placeholder.js" charset="utf-8"></script>
<![endif]-->
</head>
<body>
${top?default("")}
${bannerNavigation?default("")}
${bannerNavSecond?default("")}
${body?default("")}
${bottom?default("")}
</body>
</html>