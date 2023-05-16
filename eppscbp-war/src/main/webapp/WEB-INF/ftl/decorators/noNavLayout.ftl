<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>${title}</title>
<#include "/WEB-INF/ftl/commons/meta.ftl"/>
${head}
<link rel="shortcut icon" href="https://respay.suning.com/sfbmws/style/temp/logo.ico?stimestamp=${stimestamp}" type="image/x-icon">
<link rel="icon" href="https://respay.suning.com/sfbmws/style/temp/logo.ico?stimestamp=${stimestamp}" type="image/x-icon">
<script language="javascript">
	//公共头尾接入-菜单编码-审批中心
    var menuCode= "QYZX02";
</script>
</head>
<body>
${top?default("")}
${banner?default("")}
${body?default("")}
${bottom?default("")}
<script language="javascript">
	window.onload = function(){
        var logoTxt = document.getElementsByClassName('com_logo_txt');
        var hasLogo = logoTxt.length;
        if(hasLogo>0){
            logoTxt[0].innerHTML = "企业版";
        }
    };
    
	if (!document.getElementsByClassName) { //如果不存在这个方法
		document.getElementsByClassName = function(cls) { //就自定义一个方法，并传入这个方法的参数，即class名
			var ret = []; //定义一个空数组用来存储获取到指定className元素
			var els = document.getElementsByTagName('*'); //获取页面所有元素
			for (var i = 0; i < els.length; i++) {
				if (els[i].className === cls //获取页面元素的className等于传入的那个名字
					|| els[i].className.indexOf(cls + ' ') >= 0 || els[i].className.indexOf(' ' + cls + ' ') >= 0 || els[i].className.indexOf(' ' + cls) >= 0) {
					ret.push(els[i]); //把获取到的元素压入空数组ret中
				}
			}
			return ret; //返回这个结果集，相当于之前的getElementsByClassName返回的 结果集。
		}
	}
</script>
</body>
</html>