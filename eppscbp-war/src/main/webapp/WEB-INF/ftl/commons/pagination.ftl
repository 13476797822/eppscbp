<#macro pagination paginationBean pageAction formId>
<#assign currPage = paginationBean.currentPage />
<#assign maxPage = paginationBean.pages />
<#assign rowsPerPage = paginationBean.pageSize />
<#assign nextPage=currPage+1 />
<#assign previousPage=currPage-1 />
<#if nextPage gt maxPage>
	<#assign nextPage=maxPage />
</#if>
<#if previousPage lt 1>
	<#assign previousPage=1 />
</#if>
<script type="text/javascript">
	$(document).ready(function(){
		$("#forwardBt").click(function(){
			var maxPage = $("#maxPage").val();
			var forwardPageNum = $("#forwardPageNum").val();
			if(forwardPageNum.length==0){
				alert("页数不能为空!");
				return;
			}
			if(parseInt(forwardPageNum) < 1){
				forwardPageNum = 1;
			}
			if(parseInt(forwardPageNum) > parseInt(maxPage)){
				forwardPageNum = maxPage;
			}
			if(parseInt(forwardPageNum) > 0){
				submitFormForPagination('${formId}',forwardPageNum,'${pageAction}');
			}else{
				alert("请输入正确页数!");
				return;
			}
		});
	});
	/**
	 * 提交分页表单（分页）
	 * @param formId
	 * @param currPage
	 * @param action
	 * @return
	 */
	function submitFormForPagination(formId,currPage,action){	 
		$("#currPage").val(currPage);
		var form = document.getElementById(formId);    
		form.action = action; 
		form.submit();
	}
</script>
<div class="pageNu">
	<span>共 ${paginationBean.count} 条记录</span>
	<span>${currPage}/${maxPage}</span>
	<span><a href="javascript:void(0);" onclick="submitFormForPagination('${formId}','1','${pageAction}')">首页</a></span>
	<span><a href="javascript:void(0);" onclick="submitFormForPagination('${formId}','${previousPage?string("0")}','${pageAction}')">上页</a></span>
	<span><a href="javascript:void(0);" onclick="submitFormForPagination('${formId}','${nextPage?string("0")}','${pageAction}')">下页</a></span>
	<span><a href="javascript:void(0);" onclick="submitFormForPagination('${formId}','${maxPage?string("0")}','${pageAction}')">末页</a></span>
	<span><input type="text" id="forwardPageNum" size="3" style="width: 20px;" value="${currPage?string("0")}"></span>
	<span>
		<a id="forwardBt" class="pageSel" style="font-size: 13px; font-weight: bold;"  href="javascript:void(0)">Go</a>
	</span>
	<input id="currPage" type="hidden" name="currentPage"  value="${currPage?string("0")}"/>
	<input id="rowsPerPage" type="hidden" name="pageSize" value="${rowsPerPage?string("0")}"/>
	<input id="maxPage" type="hidden" name="pages" value="${maxPage?string("0")}"/>
</div>
</#macro>