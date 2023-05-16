require.config({
    paths: {
        jquery: baseJSPath + 'lib/jquery.min',
        template: baseJSPath + 'lib/template.min',
        calendar: baseJSPath + 'lib/calendar-alter',
        echarts:baseJSPath + 'lib/echarts.min'
        
    },
    shim: {
        calendar: ['jquery']
    }
})
require(['jquery','template','calendar','echarts'],function($,template,calendar,echarts){
    var main = {
        //初始化执行
        init:function(){
            //初始化数据加载
            this.initEchartFun();
            //滚动事件处理
            this.scrollTopFun()
        },
        //滚动事件处理
        scrollTopFun:function(){
            $('.notice-bord').each(function(){
               var $timer;
               var $detailsTop =  $('.details-top').height();
               var scroll_top=0;
               var obj = $(this);
               var $height = obj.find(".notice-box").height();
               //obj.find(".notice-box").clone().appendTo(obj);
               obj.hover(function(){
                   clearInterval($timer);
               },function(){
                   $timer = setInterval(function(){
                      scroll_top++;
                      if(scroll_top > $height){
                         scroll_top = 0;
                      }
                      if(scroll_top > $detailsTop){
                          $('.more').hide();
                      }else{
                        $('.more').show();
                      }
                      obj.find(".notice-box").first().css("margin-top",-scroll_top);
                    },100);
                 }).trigger("mouseleave");
            })
        },
        initEchartFun:function(){
            var self = this,
                $dataLast = $('.transaction-overview .datalast'),
                $datafirst = $('.transaction-overview .datafirst');
            var day = new Date();
            var dataTime = day.getFullYear()+"-" + (day.getMonth()+1) + "-" + day.getDate();
            $datafirst.calendar({
                count: 1,
                isWeek: false,
                isTime: false,
                callback:function(){

                },
                range: {
                    mindate: "1999-12-31",
                    maxdate:dataTime
                },
                selectRange: {
                    min: 2000,
                    max: 2099
                }
            })  
            $dataLast.calendar({
                count: 1,
                isWeek: false,
                isTime: false,
                callback:function(){

                },
                range: {
                    mindate: "1999-12-31",
                    maxdate:dataTime
                },
                selectRange: {
                    min: 2000,
                    max: 2099
                }
            }) 

            //获取当前月第一天
            var getCurrentMonthFirst = function getCurrentMonthFirst(){
                var date = new Date();
                date.setDate(1);
                var month = parseInt(date.getMonth()+1);
                var day = date.getDate();
                if (month < 10) {
                    month = '0' + month
                }
                if (day < 10) {
                    day = '0' + day
                }
                return date.getFullYear() + '-' + month + '-' + day;
            }

            //获取当月最后一天
            var getCurrentMonthLast = function getCurrentMonthLast(){
                    var date=new Date();
                    var currentMonth=date.getMonth();
                    var nextMonth=++currentMonth;
                    var nextMonthFirstDay=new Date(date.getFullYear(),nextMonth,1);
                    var oneDay=1000*60*60*24;
                    var lastTime = new Date(nextMonthFirstDay-oneDay);
                    var month = parseInt(lastTime.getMonth()+1);
                    var day = lastTime.getDate();
                    if (month < 10) {
                        month = '0' + month
                    }
                    if (day < 10) {
                        day = '0' + day
                    }
                    return date.getFullYear() + '-' + month + '-' + day;
            }
            $('.datafirst').val(getCurrentMonthFirst);
            $('.datalast').val(getCurrentMonthLast);
            this.transactionOverviewFun();
            $('.sumbitn').click(function(){
                self.transactionOverviewFun();               
            }) 

        },
       
        transactionOverviewFun:function(){  
            var myChartOrder = echarts.init(document.getElementById('orderquan'));
            var myChartjeOrder = echarts.init(document.getElementById('ordermany'));
            var myChartjsOrder = echarts.init(document.getElementById('orderjs'));
            var params = {
            		fromTime:$('.datafirst').val(),
            		toTime:$('.datalast').val()
            }
            $.ajax({
                url: sysconfig.ctx + "/oca/ocaHomeController/query.htm",
                type: 'POST',
                data: params,
                dataType: 'JSON',
                success:function(json){
                	var bgColor = ['#FF8000','#FFC890','#FFECD9']; 
                    if(json && json.success){
                       $('.djsl').text(json.result.orderNumSum);
                        $('.djje').text(json.result.orderAmountSum);
                        $('.jsje').text(json.result.settleAmountSum);
                        for(var k = 0; k < bgColor.length; k++){
                            var listColor = {
                                itemStyle: {
                                    color:  bgColor[k]
                                }
                            }
                            json.result.orderNum[k].emphasis = listColor;
                            json.result.orderAmount[k].emphasis = listColor;
                            json.result.settleAmount[k].emphasis = listColor;
                        }
                        //订单数量
                        var myChartOption = {
                            tooltip: {
                                backgroundColor: null,	//tooltip背景色
                                borderColor:null,		//tooltip边框颜色
                                formatter:function(param){
                                    //console.log(param)
                                    var divTxt = '';
                                    divTxt += '<div class="tooltipbox"><div class="tooltipname">'+param.name+':</div><div class="tooltipvalue">'+param.value+'笔</div></div>';
                                    return divTxt;
                                }
                            },
                            
                            legend: {
                                orient: 'horizontal',
                                bottom:15,
                                icon:'circle',
                                itemHeight: 12,
                                selectedMode:false,
                                data: [json.result.orderNum[0].name, json.result.orderNum[1].name, json.result.orderNum[2].name],
                                textStyle:{    //图例文字的样式
                                    color:'#333333',  //文字颜色
                                    fontSize:14    //文字大小
                                }
                            },
                            series: [
                                {
                                    name: '访问来源',
                                    type: 'pie',
                                    radius: ['58%', '72%'],
                                    //color:['#FF8000','#FFC890','#FFECD9'],
                                    avoidLabelOverlap: false,
                                    center: ["50%", "40%"], 
                                    itemStyle: {
                                        color: function(params) {                         
                                            return bgColor[params.dataIndex]
                                        }  
                                      },
                                    label:{
                                        normal: {  //正常的样式
                                            show: false,
                                            color:['#FF8000','#FFC890','#FFECD9'],
                                            position: 'left'
                                        },
                                        emphasis: { //选中时候的样式
                                            show: false,
                                            color:['#FF8000','#FFC890','#000000'],
                                            textStyle: {
                                                fontSize: '20',
                                                fontWeight: 'bold'
                                            }
                                        }
                                    },
                                    labelLine: {
                                        normal: {
                                            show: false
                                        }
                                    },
                                    data:json.result.orderNum
                                }
                            ]
                        };
                        //订单金额
                        var myChartjeOption = {
                            tooltip: {
                                backgroundColor: null,	//tooltip背景色
                                borderColor:null,		//tooltip边框颜色
                                formatter:function(param){
                                    //console.log(param)
                                    var divTxt = '';
                                    divTxt += '<div class="tooltipbox"><div class="tooltipname">'+param.name+':</div><div class="tooltipvalue">'+param.value+'元</div><div class="tooltipvalue">'+param.percent+'%</div></div>';
                                    return divTxt ;
                                }
                            },
                            
                            legend: {
                                orient: 'horizontal',
                                bottom:15,
                                icon:'circle',
                                itemHeight: 12,
                                selectedMode:false,
                                data: [json.result.orderAmount[0].name, json.result.orderAmount[1].name, json.result.orderAmount[2].name],
                                textStyle:{    //图例文字的样式
                                    color:'#333333',  //文字颜色
                                    fontSize:14    //文字大小
                                }
                            },
                            series: [
                                {
                                    name: '访问来源',
                                    type: 'pie',
                                    radius: ['58%', '72%'],
                                    //color:['#FF8000','#FFC890','#FFECD9'],
                                    avoidLabelOverlap: false,
                                    center: ["50%", "40%"],
                                    itemStyle: {
                                        color: function(params) {                         
                                            return bgColor[params.dataIndex]
                                        }  
                                    },
                                    label:{
                                        normal: {  //正常的样式
                                            show: false,
                                            position: 'left'
                                        },
                                        emphasis: { //选中时候的样式
                                            show: false,
                                            textStyle: {
                                                fontSize: '20',
                                                fontWeight: 'bold'
                                            }
                                        }
                                    },
                                    labelLine: {
                                        normal: {
                                            show: false
                                        }
                                    },
                                    data:json.result.orderAmount
                                }
                            ]
                        };  
                        
                        //结算金额
                        var myChartjsOption = {
                            tooltip: {
                                backgroundColor: null,	//tooltip背景色
                                borderColor:null,		//tooltip边框颜色
                                formatter:function(param){
                                    //console.log(param)
                                    var divTxt = '';
                                   	divTxt += '<div class="tooltipbox"><div class="tooltipname">'+param.name+':</div><div class="tooltipvalue">'+param.value+'元</div></div>';
                                    return divTxt ;
                                }
                            },
                            
                            legend: {
                                orient: 'horizontal',
                                bottom:15,
                                icon:'circle',
                                itemHeight: 12,
                                selectedMode:false,
                                data: [json.result.settleAmount[0].name, json.result.settleAmount[1].name, json.result.settleAmount[2].name],
                                textStyle:{    //图例文字的样式
                                    color:'#333333',  //文字颜色
                                    fontSize:14    //文字大小
                                }
                            },
                            series: [
                                {
                                    name: '访问来源',
                                    type: 'pie',
                                    radius: ['58%', '72%'],
                                    //color:['#FF8000','#FFC890','#FFECD9'],
                                    avoidLabelOverlap: true,
                                    center: ["50%", "40%"],
                                    itemStyle: {
                                        color: function(params) {                         
                                            return bgColor[params.dataIndex]
                                        }  
                                      },
                                    label:{
                                        normal: {  //正常的样式
                                            show: false,
                                            position: 'left'
                                        },
                                        emphasis: { //选中时候的样式
                                            show: false,
                                            textStyle: {
                                                fontSize: '20',
                                                fontWeight: 'bold'
                                            }
                                        }
                                    },
                                    labelLine: {
                                        normal: {
                                            show: false
                                        }
                                    },
                                    data:json.result.settleAmount
                                }
                            ]
                        };  
                        //画图
                        myChartOrder.setOption(myChartOption);
                        myChartjeOrder.setOption(myChartjeOption);
                        myChartjsOrder.setOption(myChartjsOption);
                    }else{
                        $('.djslbox').text('--');
                        $('.djjebox').text('--');
                        $('.jsjebox').text('--');

                    }

                }
            })           
        }
    }
    main.init();

})