/**
 * Created by 14033254 on 2014/6/29.
 */
//New.CitySelect.V2主要优化结构,开放自定义接口
(function ($) {
	
    $.fn.SnAddress = function (options, datas) {
		
		
        return this.each(function () {
            var $this = $(this);
            var opts = $.extend({}, $.fn.SnAddress.defaults, options);
            var methord = {
                //初始化
                init: function ($this, options) {
                    this.options = options;
					this.registerExceptAreas(options.exceptAreas);
					this.registerFlag(options.flag);
                    this.element = $this;
                    var g = this, p = this.options;
                    g.element.addClass("citySelect");
                    g.dom = g.dom || g._dom();
                    //全局变量数组
                    g.selected = [], g.records = [], g.rows = [];
                    //初始化数据
                    p.datas = datas || p.datas;
                    //基本验证
                    if (p.columns.length < p.datas.length) {
                        return;
                    }
                    //初始化头部
                    g.renderHeader();
                    //初始化事件
                    g.setEvent();
                },
                //添加事件
                setEvent: function () {
                    var g = this, p = this.options;
                    g.dom["cityboxbtn"].click(function (event) {
                        g.dom["cityboxbtn"].addClass("select");
						//
						$(".add .info-content").addClass("z-top");
                        g.dom["citybox"].show().css('top', g.inputHeight + 1 + "px");
                        //勿删
                        g.dom["chooseArea"].children("p").each(function () {
                            if ($(this).hasClass("disable") && $(this).hasClass("cur")) {
                                g.active_count = 0;
                                g.getData(g._getParam(0));
                            }
                        });

                        return event.stopPropagation();
                    });
                    g.dom["closeSelector"].click(function (event) {
                        g.reset();
                        return event.stopPropagation();
                    });
                    g.dom["chooseArea"].children("p").click(function (event) {
                        if ($(this).hasClass("disable")) {
                            return event.stopPropagation();
                        } else {
                            g.active_count = $(this).index();
                            g.getData(g._getParam(g.active_count));
                            return event.stopPropagation();
                        }
                    });
                    g.dom["cityshow"].on('click', 'span>a', function (event) {
                        var rowid = $(this).attr("data").split('_')[1];
                        g.select(g.records[rowid]);
                        if (p.stepMet != null && g.selected.length != p.datas.length) {
                            p.stepMet(g.selected, g.active_count);
                        }
                        g.changeVolume();
                        return event.stopPropagation();
                    });
                    $(document).click(function () {
                        if (g.dom["citybox"].css('display') == "block") {
                            g.reset();
                            //return false;
                        }
                    });
                },
                //点击父级数据引起子级数据切换,添加自提点，直辖市过滤
                changeVolume: function () {
                    var g = this, p = this.options;
                    var _data = g.selected[g.active_count];
                    $("span", g.dom["chooseArea"]).eq(g.active_count).html(_data.name);
                    g.selected[g.active_count].isdirect = _data.item ? true : false;
                    var _cur = g.active_count;


                    for (var i = g.active_count + 1; i < p.datas.length; i++) {
                        $("span", g.dom["chooseArea"]).eq(i).html(p.columns[i].text);
                    }

                    //name: '请选择省', code: '00000'
                    if (g.active_count + 1 <= p.datas.length - 1) {
                        //清除其他tab
                        g.active_count++;
                        var _next = g.active_count;
                        //自提点
                        if (_data.item) {
                            _cur = g.active_count;
                            $("p", g.dom["chooseArea"]).eq(g.active_count).addClass("disable");
                            $("span", g.dom["chooseArea"]).eq(g.active_count).html(_data.item[0].name);
                            g.selected[g.selected.length] = _data.item[0];
                            g.active_count++;
                            _next = g.active_count;
                        }
                        if (g.active_count <= p.datas.length - 1) {
                            g.getData(g._getParam(_next));
                        } else {
                            //直辖市结束请求
                            g.active_count = 0;
                            g.setHeadVal();
                            g.reset();
                        }

                    } else {
                        //正常结束请求
                        g.setHeadVal();
                        g.reset();
                    }
                },
                getData: function (_param) {
                    var g = this, p = this.options;
                    $("p", g.dom['chooseArea']).eq(g.active_count).removeClass("disable").addClass('cur').siblings("p").removeClass("cur");
                    for (var i = g.active_count + 1; i < p.datas.length; i++) {
                        if ($("span", g.dom["chooseArea"]).eq(i).html() == p.columns[i].text) {
                            $("p", g.dom['chooseArea']).eq(i).addClass("disable");
                        }
                    }
                    g.getAjax(_param);
                },
                //ajax获取数据
                getAjax: function (param) {
                    var g = this, p = this.options;
                    var ajaxOptions = {
                        type: 'GET',
                        url: p.url,
                        data: param,
                        dataType: "jsonp",
                        jsonp: "callback",
                        beforeSend: function (msg) {
                            g.dom["cityshow"].html("加载中...");
                        },
                        success: function (items) {
                            g.dom["cityshow"].html("");
                            g.records.length = 0;
                            g.rows.length = 0;
							//
							items = g.filter(items,g.areas);						
                            if (items.data.length == 1 && param.state == "town") {
                                g.records.push(0);
                                g.rows.push(items.data[0]);
                                g.selected[g.selected.length] = g.rows[parseInt(0)];
                                $("p", g.dom["chooseArea"]).eq(g.active_count).addClass("disable");
                                $("span", g.dom["chooseArea"]).eq(g.active_count).html(items.data[0].name);
                                //if (items.data[0].name == "全区" || items.data[0].name == "全境") {
                                //p.columns[g.active_count].ishide = true;
                                //}
                                g.active_count = 0;
                                g.setHeadVal();
                                g.reset();
                            } else {
                                $.each(items.data, function (count, item) {
                                    var b = $("<span/>");
                                    var a = $("<a/>");
                                    if (item.ishot == "true") {
                                        a.addClass("imp");
                                    }
                                    a.attr("data", "Sns_" + count).html(item.name);
                                    b.append(a);
                                    g.dom["cityshow"].append(b);

                                    g.records.push(count);
                                    g.rows.push(item);
                                });
                                if ($("iframe", g.dom['citybox']).length > 0) {
                                    $("iframe", g.dom['citybox']).height(g.element[0].offsetHeight);
                                }
                            }

                        },
                        error: function (e1, e2) {
                            //console.log(e1);
                        }
                    }
                    $.ajax(ajaxOptions);
                },
                //数据选择完成，数组装载所需数据，并且通过complete抛出,供外界调用
                setHeadVal: function () {
                    var g = this, p = this.options;
                    $.each(g.selected, function (count, item) {
                        $("span", g.dom["cityboxbtn"]).eq(count).show().html(item.name);
                        if (item.isdirect || p.columns[count].hide) {
                            if (p.datas.length != 1)
                                $("span", g.dom["cityboxbtn"]).eq(count).hide();
                            
                            // luj 地址空间样式兼容js
                            $('#areaRun').show();
                        }
                    });
                    p.datas.length = 0;
                    $.each(g.selected, function (count, item) {
                        p.datas.push(item);
                    });
                    if (p.complete != null) {
                        p.complete(p.datas, false);
                    }
					this.flag=true;
					//
					$("#hideadd").click();
                },
                //初始化基本样式以及赋值
                renderHeader: function () {
                    var g = this, p = this.options;

                    function _buildHtml(_count, items, isdisable) {
                        var _tempfun1 = function (_count, item, isdisable) {
                            var _html = '';
                            var _txt = $.trim(item.name) == "" ? p.columns[_count].text : item.name;
                            _html += '<span ';
                            if (item.isdirect || p.columns[_count].hide) {
                                _html += 'style="display: none"';
                            }
                            if (p.columns[_count].addclass) {
                                _html += ' class="' + p.columns[_count].addclass + '"';
                            }
                            _html += '>' + _txt + '</span>';
                            _html += '<em></em>';
                            return _html;
                        }
                        var _tempfun2 = function (count, item, isdisable) {
                            var _txt = $.trim(item.name) == "" ? p.columns[_count].text : item.name;
                            var _html = '<p eq="' + count + '"><span>' + _txt + '</span><b></b></p>';
                            if (isdisable || item.id == "") {
                                _html = '<p class="disable"><span>' + _txt + '</span><b></b></p>';
                            }
                            return _html;
                        }
                        g.dom['cityboxbtn'].append(_tempfun1(_count, items, isdisable));
                        g.dom['chooseArea'].append(_tempfun2(_count, items, isdisable));
//                        if (_count == 0) {
//                            g.active_count = _count;
//                            g.getData(g._getParam(g.active_count));
//                            _count++;
//                        }
                    }

                    if (p.datas && p.columns && p.datas.length > 0) {
                        var _isdirect = false;
                        $.each(p.datas, function (count, item) {
                            if (item.id) {
                                g.selected[count] = item;
                            }
                            _buildHtml(count, item, _isdirect);
                            _isdirect = p.datas[count].isdirect;
                        });
                        if(g.selected.length>0&&!g.selected[g.selected.length-1].isdirect){
                            if(g.selected.length!= p.datas.length){
                                g.active_count = g.selected.length;
                                g.getData(g._getParam(g.active_count));
                            }else{
                                g.active_count = g.selected.length-1;
                                g.getData(g._getParam(g.active_count));
                            }

                        }else{
                            g.active_count = 0;
                            g.getData(g._getParam(g.active_count));
                        }

                        _isdirect = false;
                        if (p.complete != null) {
                            p.complete(p.datas, true);
                        }
                    }
                    g.dom.chooseArea['append']('<div class="clear"></div>');
                    g.dom.cityboxbtn['append']('<b></b>');
                    g.inputHeight = g.dom.cityboxbtn['height']();
                    //兼容
                    g.dom['chooseArea'].addClass("fix");
                    var _isIE6 = window.VBArray && !window.XMLHttpRequest;
                    if (_isIE6 && $("iframe", g.dom['citybox']).length < 1) {
                        var iframe = document.createElement("iframe");
                        g.dom['citybox'].after(iframe);
                        $(iframe).css({
                            width: g.element.width(),
                            height: g.element[0].offsetHeight,
                            position: "absolute",
                            "z-index": 10,
                            opacity: 0,
                            top: 25, // 微调数据
                            left: 75  //微调数据
                        });
                    }
                },
                //关闭组件
                reset: function () {
                    var g = this, p = this.options;
                    g.dom["cityboxbtn"].removeClass("select");
					$(".add .info-content").removeClass("z-top");
                    g.dom["citybox"].hide();
                    // luj 地址空间样式兼容js
                    $('#areaRun').show();
                },
                //获取数据
                select: function (rp) {
                    var g = this, p = this.options;
                    var row = g.rows[parseInt(rp)];
                    g.selected.length = g.active_count;
                    g.selected[g.selected.length] = row;
                },
                //获取参数
                _getParam: function (rp) {
                    var g = this, p = this.options;
                    var defaults = {
                        state: p.columns[rp].state,
                        selectId: rp - 1 > -1 ? g.selected[rp - 1].id : 0
                    }
                    var setting = {};
                    if (p.otherParam) {
                        var obj = p.otherParam(rp, p.columns, g.selected);
                        if (obj && typeof (obj) == "object") {
                            setting = obj;
                        }
                    }
                    var params = $.extend(defaults, setting);
                    return params;
                },
                //获取DOM对象
                _dom: function () {
                    var g = this, p = this.options;
                    g.element.html(p.innerHtml);
                    var wrap = g.element;
                    var name, i = 0,
                        DOM = { wrap: $(wrap) },
                        els = wrap[0].getElementsByTagName('*'),
                        elsLen = els.length;
                    for (; i < elsLen; i++) {
                        name = els[i].className;
                        if (name) DOM[name] = $(els[i], wrap);
                    }
                    ;
                    return DOM;
                },
                //获取最终数据
                getAddress: function () {
                    var g = this, p = this.options;
                    return p.datas;
                },
                //获取选择数据
                getRows: function () {
                    var g = this, p = this.options;
                    return g.selected;
                },
                //用于显示
                setCurrent: function (rp) {
                    var g = this, p = this.options;
                    g.dom["cityboxbtn"].addClass("select");
                    g.dom["citybox"].show().css('top', g.inputHeight + "px");
                    if (g.selected.length - parseInt(rp) >= 0) {
                        g.active_count = parseInt(rp);
                        g.getData(g._getParam(g.active_count));
                    } else {
                        g.active_count = 0;
                        g.getData(g._getParam(g.active_count));
                    }

                    return event.stopPropagation();
                },
				filter: function(wholeAreas){
					var areas = this.areas;
					for(var i=0;i<areas.length;i++){
						for(var j=0;j<wholeAreas.data.length;j++){
							if(wholeAreas.data[j].name==areas[i].pro){
								wholeAreas.data.splice(j,1);
								break;
							}
						}
					}
					return wholeAreas;
				},
				registerExceptAreas: function(areas){
					this.areas = areas;
				},
				registerFlag: function(flag){
					this.flag = flag;
				}
            }
            methord.init($this, opts);
            $this.data("suning.address", methord);
        })
    }
    $.fn.SnAddress.defaults = {
        url: 'http://b2cpre.cnsuning.com/emall/SNAddressQueryCmd',
        innerHtml: ' <a href="javascript:void(0);"  class="cityboxbtn" ></a>'
            + '<div class="citybox">'
            + '<div class="chooseArea">'
            + '</div>'
            + '<div  class="arriveBox">'
            + '<div class="cityshow"></div>'
            + '</div>'
            + '<div class="closeSelector"></div>'
            + '</div>',
        level: 1,
        columns: [
            //state：读取数据库的标示,hide:显示是否隐藏,addclass:添加显示样式
            {state: "prov", text: "请选择省", hide: false, addclass: ""},
            {state: "city", text: "请选择市", hide: false, addclass: ""},
            {state: "area", text: "请选择区", hide: false, addclass: ""},
            {state: "town", text: "请选择乡镇", hide: false, addclass: ""}
        ],
        datas: [
            //name:名称,code:行政编码,id:唯一编号(用于读取数据库的表示),
            //isdirect:是否是直辖市
            {name: '', code: '', id: ''},
            {name: '', code: '', id: ''},
            {name: '', code: '', id: ''},
            {name: '', code: '', id: ''}
        ],
        otherParam: null,//添加接口参数,尽量避免使用state和id字段,防止覆盖默认
        stepMet: null,//每点击一次就触发一次,返回的是操作过程中，所选择的数据,非必须数据
        complete: null//所有操作完毕进行回调函数,所返回是正确数据
    }
})(jQuery)
