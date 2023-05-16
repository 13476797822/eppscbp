<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>店铺管理</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chorme=1">
    <meta name="keywords" content=""/>
    <meta name="description" content=""/>
    <meta name="author" content=""/>
    <link rel="shortcut icon" href="http://www.suning.com/favicon.ico" type="image/x-icon">
    <link rel="stylesheet" href="${ctx}/style/css/shop-manage.css" type="text/css"/>
    <link rel="stylesheet" href="${ctx}/style/css/reset.css" type="text/css"/>
    <link rel="stylesheet" href="${ctx}/style/css/header.css" type="text/css"/>
</head>
<body>
 <#include "/screen/header.ftl">
<div class="platform-container">
    <div class="platform-panel">
        <div class="top-info-box clearfix">
            <div class="left-info">
                <div class="info-title"><i class="info-title-icon withdraw"></i>可提现金额</div>
                <div class="info-content clearfix">
                    <dl>
                        <dt>美元：</dt>
                        <dd>$<#if criteria.usd??>${criteria.usd}<#else>0.00</#if></dd>
                    </dl>
                    <dl>
                        <dt>欧元：</dt>
                        <dd>€<#if criteria.euro??>${criteria.euro}<#else>0.00</#if></dd>
                    </dl>
                    <dl>
                        <dt>日元：</dt>
                        <dd>¥<#if criteria.yen??>${criteria.yen}<#else>0.00</#if></dd>
                    </dl>
                </div>
            </div>
            <div class="right-info">
                <div class="info-title"><i class="info-title-icon batch"></i>待批付金额</div>
                <div class="info-content clearfix">
                    <dl>
                        <dt>人民币：</dt>
                        <dd>¥${criteria.rmb}</dd>
                    </dl>
                </div>
            </div>
        </div>
        <div class="shop-toggle-wrap clearfix">
            <div class="shop-toggle-btn fl J_shopToggle">
                <#if requestDto.eCommercePlatform == '1'>
                    <a href="javascript:void(0);" class="left active"><input hidden value="1">亚马逊</a>
                    <a href="javascript:void(0);" class="right"><input hidden value="2">EBay</a>
                <#elseif  requestDto.eCommercePlatform == '2'>
                <a href="javascript:void(0);" class="left"><input hidden value="1">亚马逊</a>
                <a href="javascript:void(0);" class="right active"><input hidden value="2">EBay</a>
                <#else>
                <a href="javascript:void(0);" class="left active"><input hidden value="1">亚马逊</a>
                <a href="javascript:void(0);" class="right"><input hidden value="2">EBay</a>
                </#if>
            </div>
        </div>
        <form method='post' id="f0" name="f0">
            <div class="search-form-box clearfix J_searchForm">
                <input hidden class="platform_value" name="eCommercePlatform" value="${requestDto.eCommercePlatform}"/>
                <dl>
                    <dt>店铺名称：</dt>
                    <dd><input type="text" class="platform-input" name="storeName" value="${(requestDto.storeName)!''}">
                    </dd>
                </dl>

                <dl>
                    <dt>店铺站点：</dt>
                    <dd>
                        <div class="platform-select jr-select">
                            <i class="select-input-arrow-icon arrow-icon-closed"></i>
                            <input type="text" readonly class="select-input" name="site" value="${(requestDto.site)!''}"
                                   key="">
                            <ul class="select-box">
                                <li class="select-item">
                                    <a class="sel-val" href="javascript:void(0);" key="0" value="全部">
                                        全部
                                    </a>
                                </li>

                               <#if site??>
                                   <#list site as site>
                                <li class="select-item">
                                    <a class="sel-val" href="javascript:void(0);" key="1" value=${site}>${site}</a>
                                </li>
                                   </#list>
                               </#if>
                            </ul>
                        </div>
                    </dd>
                </dl>
                <dl>
                    <a href="javascript:void(0);" class="platform-button active J_searchBtn"
                       onclick="submitQuery();">查询</a>
                    <a href="javascript:void(0);" class="platform-button J_resetBtn">重置</a>
                </dl>
            </div>
        </form>
        <div class="shop-list-table-wrap">
            <div class="top-ctrl clearfix">
                <a href="javascript:void(0);" class="platform-button fr J_addShopBtn"><i class="btn-icon icon-add"></i>添加</a>
            </div>
              <#if errMessage??>
              <div class="no-result"><i></i>${errMessage}</div>
              </#if>

        </div>

      <#if resultList??>
    <div class="batch-detail-table">
        <table class="platform-table">
            <thead>
            <tr>
                <th>店铺名称</th>
                <th>店铺站点</th>
                <th>收款方银行账号</th>
                <th>未提现余额</th>
                <th>币种</th>
                <th>审核状态</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
        <#list resultList as item>
        <tr>
            <td style="display:none">${(item.id)!''}</td>
            
            <td><a href="javascript:void(0);" class="J_shopName" id="storeName${item.id}">${(item.storeName)!''}</a>
            </td>
            <td id="site${item.id}">${(item.site)!''}</td>
            <td><a href="javascript:void(0);" class="J_account" id="custAccount${item.id}">${(item.custAccount)!''}</a>
            </td>
            <td>${(item.withdrawBal)!''}</td>
            <td id="cur${item.id}">${(item.cur)!''}</td>
            <td>${(item.status)!''}</td>
            <td style="display:none" id="storeUrl${item.id}">${(item.storeUrl)!''}</td>
            <td style="display:none" id="bankName${item.id}">${(item.bankName)!''}</td>
            <td style="display:none" id="sellGoods${item.id}">${(item.sellGoods)!''}</td>
            <td style="display:none" id="runTime${item.id}">${(item.runTime)!''}</td>
            <td style="display:none" id="mountAmt${item.id}">${(item.mountAmt)!''}</td>
            <td style="display:none" id="entityName${item.id}">${(item.entityName)!''}</td>
            <td style="display:none" id="sellerId${item.id}">${(item.sellerId)!''}</td>
            <td style="display:none" id="bankAddr${item.id}">${(item.bankAddr)!''}</td>
            <td style="display:none" id="custName${item.id}">${(item.custName)!''}</td>
            <td style="display:none" id="bankCountryCode${item.id}">${(item.bankCountryCode)!''}</td>
            <td style="display:none" id="swiftCode${item.id}">${(item.swiftCode)!''}</td>
            <td style="display:none" id="storeId${item.id}">${(item.storeId)!''}</td>
            <td style="display:none" id="storeNo${item.id}">${(item.storeNo)!''}</td>
            <#if item.status=="通过">
                <td class="opration J_withdraw" data-id="1234">
                    <a href="javascript:void(0);" class="J_withdraw batch-add-btn">提现</a>
                </td>
            <#elseif item.status=="驳回">
                 <td>
                     <a href="javascript:void(0);" class="J_modify">修改</a>
                     <a href="javascript:void(0)" class="J_delete" onclick="deleteConfig('${item.id}')"
                        class="blueLink">删除</a>
                 </td>
            <#else>
                 <td class="opration J_withdraw">
                 </td>
            </#if>

        </tr>
        </#list>
            </tbody>
        </table>
    </div>
      </#if>
            <#if page??>
            <form method='post' id="f6" name="f6">
                <div class="page" id="pageClass">
                    <div class="posi-right">
                        <span class="fl">每页10条</span>
                        <a class="prev-page page-num border <#if page.currentPage==1> disable </#if> fl"
                           href="javascript:;"><i class="arrow-left"></i>上一页</a>
                        <a class="next-page page-num border <#if page.currentPage==page.pages> disable </#if> fl"
                           href="javascript:;">下一页<i class="arrow-right"></i></a>

                        <span class="fl m-left">第${page.currentPage}页/共${page.pages}页   &nbsp;&nbsp;	向第</span><input
                        type="text" name="" class="fl page-num input-num" id="targetPage"><span class="fl">页</span>
                        <a class="fl goto border" href="javascript:;" onclick="targetQuery();">跳转</a>


                    </div>
                    <input hidden class="platform_value" name="eCommercePlatform"
                           value="${requestDto.eCommercePlatform}"/>
                    <input style="display:none" id="currentPage" name="currentPage" value="${page.currentPage}">
                    <input style="display:none" id="pageSize" value="${page.pages}">
                </div>
            </form>
            </#if>

    </div>
</div>

<!-- 表格列表模板 -->
<#--<script type="text/html" id="list-tpl">-->

<#--</script>-->
<script type="text/html" id="wait-tpl">
    <div class="wait-wrap"><i></i>请求中...</div>
</script>
<script type="text/html" id="empty-tpl">
    <div class="no-result"><i></i>没有找到相关数据</div>
</script>


<!-- 添加或修改店铺弹窗模板 -->
<script type="text/html" id="add-shop-dialog-tpl">
    <form id="f2" name="f2" method="post">
        <div class="add-shop-dialog-body">
            <div class="add-shop-form J_form">
                <dl class="clearfix">
                    <dt>店铺名称：</dt>
                    <dd>
                            <input hidden class="store_add" name="eCommercePlatform" value="x">
                            <input hidden name="operationType" class="operate" value="N">
                            <input hidden name="storeNo" class="storeNo" value="">
                            <input hidden name="storeId" class="storeId" value="x">
                        <div class="input-field">
                            <input type="text" class="platform-input" name="storeName" id="storeName">
                            <p class="error-tip">请填写店铺名称</p>
                            <p class="error-show">长度超过100个字符</p>
                        </div>
                    </dd>
                </dl>
                <dl class="clearfix">
                    <dt>店铺站点：</dt>
                    <dd>
                        <div class="input-field">
                            <div class="platform-select jr-select">
                                <i class="select-input-arrow-icon arrow-icon-closed"></i>
                                <input type="text" readonly class="select-input" name="site" key="" id="sites" >
                                <ul class="select-box">

                               <#if site??>
                                   <#list site as site>
                                <li class="select-item">
                                    <a class="sel-val" onblur="checkEntityName()" href="javascript:void(0);" key="1" value=${site}>${site}</a>
                                </li>
                                   </#list>
                               </#if>
                                </ul>
                            </div>
                            <p class="error-tip">请选择店铺站点</p>
                        </div>
                    </dd>
                </dl>
                <dl class="clearfix">
                    <dt>店铺主体名称：</dt>
                    <dd>
                        <div class="input-field">
                            <input type="text" class="platform-input" name="entityName" id="entityName">
                            <p class="error-tip">请填写店铺主体名称(站点为美国填英文)</p>
                        </div>
                    </dd>
                </dl>
                <dl class="clearfix">
                    <dt>店铺网址：</dt>
                    <dd>
                        <div class="input-field">
                            <input type="text" class="platform-input" name="storeUrl" id="storeUrl">
                            <p class="error-tip">请填写店铺网址</p>
                            <p class="error-show">长度超过200个字符</p>
                        </div>
                    </dd>
                </dl>
                <dl class="clearfix" id="sellerId">
                    <dt>卖家ID：</dt>
                    <dd>
                        <div class="input-field">
                            <input type="text" class="platform-inputs" name="sellerId" id="sellerId">
                            <p class="error-show">长度必须为10到16个字符</p>
                        </div>
                    </dd>
                </dl>
                <dl class="clearfix">
                    <dt>销售商品：</dt>
                    <dd>
                        <div class="input-field">
                            <input type="text" class="platform-input" name="sellGoods" id="sellGoods">
                            <p class="error-tip">请填写销售商品</p>
                            <p class="error-show">长度超过100个字符</p>
                        </div>
                    </dd>
                </dl>
                <dl class="clearfix">
                    <dt>运营时间：</dt>
                    <dd>
                        <div class="input-field">
                            <input type="text" class="platform-input" name="runTime" id="runTime" placeholder="例:'6个月至12个月'">
                            <p class="error-tip">请填写运营时间</p>
                        </div>
                    </dd>
                </dl>
                <dl class="clearfix">
                    <dt>预估收入总额(万美元)：</dt>
                    <dd>
                        <div class="input-field">
                            <input type="text" onkeyup="value=value.replace(/[^0-9]/g,'')" class="platform-input" name="mountAmt" id="mountAmt">
                            <p class="error-tip">请填写预估月收入总额</p>
                            <p class="error-show">必须为正整数,且最大金额为:2147483647</p>
                        </div>
                    </dd>
                </dl>
            </div>
            <div class="dialog-ctrl">
                <a href="javascript:void(0);" class="platform-button active mr J_submit">确定</a>
                <a href="javascript:void(0);" class="platform-button J_cancel">取消</a>
            </div>
        </div>
    </form>
</script>

<!-- 店铺详情弹窗模板 -->
<script type="text/html" id="shop-detail-dialog-tpl">
    <div class="detail-dialog-body">
        <div class="detail-list-wrap">
            <dl class="clearfix">
                <dt>店铺名称：</dt>
                <dd id="storeName"></dd>
            </dl>
            <dl class="clearfix">
                <dt>店铺站点：</dt>
                <dd id="site"></dd>
            </dl>
            <dl class="clearfix">
                <dt>店铺主体名称：</dt>
                <dd id="entityName"></dd>
            </dl>
            <dl class="clearfix">
                <dt>店铺网址：</dt>
                <dd id="storeUrl"></dd>
            </dl>
            <dl class="clearfix" id="sellerID">
                <dt>卖家ID：</dt>
                <dd id="sellerId"></dd>
            </dl>
            <dl class="clearfix">
                <dt>销售商品：</dt>
                <dd id="sellGoods"></dd>
            </dl>
            <dl class="clearfix">
                <dt>运营时间：</dt>
                <dd id="runTime"></dd>
            </dl>
            <dl class="clearfix">
                <dt>预估月收入总额(万美元)：</dt>
                <dd id="mountAmt" style="margin-top:6px;"></dd>
            </dl>
        </div>
        <div class="dialog-ctrl">
            <a href="javascript:void(0);" class="platform-button active J_close">关闭</a>
        </div>
    </div>
</script>

<!-- 提现导入 -->
<script type="text/html" id="store_upload">
    <form method='post' id="f3" name="f3">
        <div class="pop-upload upload-files">
            <div>
                <label>文件上传：</label>
                <input type="text" name="detailAmount" id="detailAmount" hidden>
                <input type="text" class="input file-path" readonly="true" name="filePath" value="" id="file">
                <input type="text" name="fileAddress" id="fileAddress" value="" class="input file-path hide">
                <input name="cur" value="" id="curType" hidden>
                <input name="id" value="" id="id" hidden>
                <div class="file-operation">
                    <a class="up" href="javascript:;">选择文件
                        <form id="uploadForm" enctype="multipart/form-data">
                            <input type="file" id="file-btn" name="file"
                                   accept="application/vnd.ms-excel, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet">
                    </a>
    </form>
    <a class="download" href="${ctx}/template/file/cp_store_handle.xlsx">下载模版</a>
    </div>
    <div class="clear"></div>
    </div>
    <!-- 上传文件报错 -->
    <div class="result-text">
        <div class="wait" id="wait">
            <img src="${ctx}/style/images/empty-tip.jpg">
            <p class="title">请上传模版文件</p>
        </div>
        <div class="success hide" id="uploadSuccess">
            <img src="${ctx}/style/images/icon_success_24.png">
            <p class="title">上传成功</p>
        </div>
        <div class="fail hide" id="uploadFail">
            <img src="${ctx}/style/images/icon_error_32.png">
            <p class="title">上传失败，请修改后重新上传</p>
            <span class="err-text"></span>
        </div>
    </div>
    <a href="javascript:;" class="submit-btn J_setfile disabled" id="J_placeOrder" style="margin-left:144px;">保 存</a>
    <a href="javascript:;" class="cancel-btn">取 消</a>
    </div>
    </form>
</script>


<!-- 账号详情弹窗模板 -->
<script type="text/html" id="account-detail-dialog-tpl">
    <div class="detail-dialog-body">
        <div class="detail-list-wrap">
            <dl class="clearfix">
                <dt>店铺名称：</dt>
                <dd id="storeName"></dd>
            </dl>
            <dl class="clearfix">
                <dt>收款银行名称：</dt>
                <dd id="bankName"></dd>
            </dl>
            <dl class="clearfix">
                <dt>收款银行开户名：</dt>
                <dd id="custName"></dd>
            </dl>
            <dl class="clearfix">
                <dt>收款方银行账号：</dt>
                <dd id="custAccount"></dd>
            </dl>
            <dl class="clearfix">
                <dt>币种：</dt>
                <dd id="cur"></dd>
            </dl>
            <dl class="clearfix">
                <dt>收款银行地区：</dt>
                <dd id="bankCountryCode"></dd>
            </dl>
        </div>
        <div class="dialog-ctrl">
            <a href="javascript:void(0);" class="platform-button active J_close">关闭</a>
        </div>
    </div>
</script>



<script type="text/javascript">
    //提交查询
    function submitQuery() {
        if ($('.J_shopToggle a').hasClass('active')) {
            $('.platform_value').val($('.J_shopToggle .active input').val());
        }
        var formObj = $("form[name='f0']");
        formObj.attr("action", "${ctx}" + "/cpStoreHandle/cpStoreHandleQuery/query.htm");
        formObj.submit();
    }

    //删除
    function deleteConfig(id, eCommercePlatform) {
        if (confirm("确定执行【删除】操作?")) {
            $.post("${ctx}/cpStoreHandle/cpStoreHandleQuery/deleteStore.htm?id=" + id + "&eCommercePlatform=" + eCommercePlatform + "&radom=" + Math.random(), function (data) {
                var result = eval(data);
                if (result != null) {
                    if (result.success) {
                        alert("操作成功");
                        /*$("#query-btn").click();*/
                        submitQuery();
                    } else {
                        alert("操作失败【" + result.message + "】")
                    }
                }
            });
        }
    }

    function checkEntityName(){
        if ($('#sites').val() == '美国站') {
            $('.add-shop-dialog-body #entityName').attr("onkeyup","value=value.replace(/[^a-zA-Z\\,\\.\\:\\-\\'\\+\\{\\} ]/ig,'')");
            $('.add-shop-dialog-body #entityName').attr("onbeforepaste","clipboardData.setData('text',clipboardData.getData('text').replace(/[^a-zA-Z\\,\\.\\:\\-\\'\\+\\{\\} ]/ig,''))");
        } else {
            $('.add-shop-dialog-body #entityName').removeAttr("onkeyup");
            $('.add-shop-dialog-body #entityName').removeAttr("onbeforepaste");
        }
    }


    //分页查询,上一页
    $(".prev-page").on("click", function (event) {
        event.preventDefault();

        var currentPage = $('#currentPage').val();
        var nextPage = parseInt(currentPage) - 1;

        //首页无法点击上一页
        if (currentPage == 1) {
            return;
        }

        if ($("#pageClass").length <= 0) {
            alert("请先查询数据!");
        } else {
            document.getElementById("currentPage").value = nextPage;
            var formObj = $("form[name='f6']");
            formObj.attr("action", sysconfig.ctx + "/cpStoreHandle/cpStoreHandleQuery/query.htm");
            formObj.submit();
        }
    });


    //分页查询,下一页
    $(".next-page").on("click", function (event) {
        event.preventDefault();


        var currentPage = $('#currentPage').val();
        var nextPage = parseInt(currentPage) + 1;
        var pages = parseInt($('#pageSize').val())

        //末页无法点击下一页
        if (currentPage == pages) {
            return;
        }

        if ($("#pageClass").length <= 0) {
            alert("请先查询数据!");
        } else {
            document.getElementById("currentPage").value = nextPage;
            var formObj = $("form[name='f6']");
            formObj.attr("action", sysconfig.ctx + "/cpStoreHandle/cpStoreHandleQuery/query.htm");
            formObj.submit();
        }
    });

    //分页查询,指定页
    function targetQuery() {
        var currentPage = parseInt($('#targetPage').val());
        var pageSize = parseInt($('#pageSize').val());

        if ($("#pageClass").length <= 0) {
            alert("请先查询数据!");
        } else {
            if (currentPage === "" || currentPage == null) {
                alert("请输入页数!");
            }
            else if (currentPage > pageSize) {
                alert("指定页数不能超过最大页数!");
            }
            else if (currentPage <= 0) {
                alert("指定页数不能小于0!");
            } else if (isNaN(currentPage)) {
                alert("请输入数字!");
            }
            else {
                document.getElementById("currentPage").value = currentPage;
                var formObj = $("form[name='f6']");
                formObj.attr("action", "${ctx}" + "/cpStoreHandle/cpStoreHandleQuery/query.htm");
                formObj.submit();
            }
        }

    }
</script>

<script type="text/javascript" data-main="${ctx}/js/cpStoreHandle/main"
        src="${ctx}/scripts/lib/require/require.min.js"></script>
</body>
</html>