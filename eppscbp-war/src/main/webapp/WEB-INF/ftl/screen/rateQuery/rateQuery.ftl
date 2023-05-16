<meta charset="UTF-8">
    <title>苏汇通</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chorme=1">
    <meta name="keywords" content=""/>
    <meta name="description" content=""/>
    <meta name="author" content=""/>
    <link rel="shortcut icon" href="http://www.suning.com/favicon.ico" type="image/x-icon">
    <link rel="stylesheet" href="${ctx}/style/css/deal-center.css" type="text/css"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/style/css/cross-border-payment.css">
    


    <div class="batch-payment">
        <#include "/screen/title.ftl">
        <div class="content">
            <div class="btn-list rate-title">
                可选数据来源：<a href="${ctx}/rateQuery/rateQuery/init.htm?channelId=11"><i class="refresh"></i>中信银行</a>
                <a href="${ctx}/rateQuery/rateQuery/init.htm?channelId=07"><i class="refresh"></i>光大银行</a>
                <p class="left-title">上次刷新时间：<em id="Date">${quoteTime}</em></p>

            </div>
            
            <div class="table-box">
            	<#if resultList??>
                <div class="result-table rate-table">
                    <table style="table-layout:  fixed;">
                        <thead>
                            <tr>
                                <th width="60">序号</th>
                                <th width="110">交易货币</th>
                                <th width="100">汇兑信息</th>
                                <th width="80" >汇兑单位</th>
                                <th width="110">现汇买入价</th>
                                <th width="110">现汇卖出价</th>
                                <th width="110">中间价</th>
                                <th width="175">报价时间</th>
                            </tr>
                        </thead>
                        <tbody>
                        <#list resultList as item>
	        				<tr>
	        					<td>${item_index+1}</td>
		        				<td>${item.tradeCur}</td>
		        				<td>${item.remitInfo}</td>
		        				<td>${item.remitUnit}</td>
		        				<td>${item.buyingRate}</td>
		        				<td>${item.sellingRate}</td>
		        				<td>${item.middleRate}</td>
		        				<td>${item.quoteTime}</td>		        				
		    			   </tr>
	        			 </#list>
                            
                            
                        </tbody>
                    </table>
                </div>
				</#if>
				
            </div>
        </div>
    </div>



    <script type="text/javascript">
        window.onload=function(){
            var date=new Date();
            var year=date.getFullYear(); //获取当前年份
            var mon=date.getMonth()+1; //获取当前月份
            if(mon<10){
                mon = "0"+mon
            }
            var da=date.getDate(); //获取当前日
            if(da<10){
                da = "0"+da
            }
            var h=date.getHours(); //获取小时
            if(h<10){
                h = "0"+h
            }
            var m=date.getMinutes(); //获取分钟
            if(m<10){
                m = "0"+m
            }
            var s=date.getSeconds(); //获取秒
                if(s<10){
                s = "0"+s
            }
            var d=document.getElementById('Date'); 
            d.innerHTML=year+'-'+mon+'-'+da+' '+h+':'+m+':'+s;

        }
    
</script>
    