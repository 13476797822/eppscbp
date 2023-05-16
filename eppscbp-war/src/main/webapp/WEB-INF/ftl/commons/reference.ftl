<#macro basePath><#if request.contextPath=="/"><#else>${request.contextPath}</#if></#macro>
<#global ctx><@basePath/></#global>