<meta charset="UTF-8">
	<title>设置操作员权限</title>
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chorme=1">
	<meta name="keywords" content=""/>
	<meta name="description" content=""/>
	<meta name="author" content=""/>
	<link rel="shortcut icon" href="http://www.suning.com/favicon.ico" type="image/x-icon">
	<link rel="stylesheet" href="${ctx}/style/css/authority_admin.css" type="text/css"/>
	<div class="auth-manage">
		<div class="content">
            <div class="contentall">
                <div class="content-top">设置操作员权限</div>
                <div class="content-left rel">
                    <input class="sousuoop" type="" name="" placeholder="">
                    <div class="placeholder left51 top26">搜索操作员</div>
                    <ul class="operatorList">
                    	<#if resultList??>
	                    	<#list resultList as item>
		                        <li id = "${item}">
		                            <span class="color3">${result}#${item}</span>
		                        </li>
	                        </#list>
                        </#if>                        
                    </ul>
                </div>
                <div class="content-right">
                    <div class="line-authority mrt26">
                        <div class="selectall">
                            操作权限：&nbsp;&nbsp;<a href="javascript:void(0);"
                                class="checkbox checkall"></a>&nbsp;&nbsp;<span class="color9">全选</span>
                        </div>
                        <div class="authorityContent">                           
                        </div>
                    </div>
                </div>
            </div>
            <div style="width:1190px;text-align: right;">
                <a id="btnConfirm" target="_self" class="btn160 J_submit btw124static" href="javascript:;">确定</a>
                <a id="btnCancel" target="_self" class="btn160 J_submit cancel" href="javascript:window.opener=null;window.open('','_self');window.close();">取消</a>
            </div>
        </div>
    </div>
    <!-- 确定操作弹窗 -->
    <div class="Fanxi-window-pop dongjieok hide cancelshenpipop zindex4">
        <div class="mimatitile">&nbsp;&nbsp;提示<span class="cancelpop"></span></div>
        <div class="dongjiecont marb40">
            <p class="warning addtip">
                <span></span>
            </p>
        </div>
        <div>
            <a class="btn160" id="cancelshenpisure" href="javascript:;">确定</a>
        </div>
    </div>
    <div class="mengceng hide"></div>
    
    <script type="text/html" id="authorityContent-tpl">
		<div class="authorityTitle">
            <a href="javascript:void(0);" class="checkbox"></a>&nbsp;&nbsp;<span>{{decode title}}</span>
            <div class="authorityItem">
            	{{each list as item}}
                <div><a id="{{item.code}}" href="javascript:void(0);"
                        class="checkbox checkclick {{if item.status == "Y"}}checked{{/if}}"></a>&nbsp;&nbsp;<span>{{decode item.description}}</span></div>
                {{/each}}   
            </div>
        </div>
	</script>

    <script type="text/javascript" data-main="${ctx}/scripts/eppscbp/authority_admin.js" src="${ctx}/scripts/lib/require/require.min.js"></script>  
    