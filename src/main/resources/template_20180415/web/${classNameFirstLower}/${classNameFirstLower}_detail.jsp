<#include "/macro.include"/> 
<#include "/custom.include"/> 
<#assign className = table.className>   
<#assign classNameFirstLower = className?uncap_first> 
<#assign classNameLowerCase = className?lower_case> 
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="../../../common/resource.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>${table.tableAlias}详情</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
  	
  	<style type="text/css">
	  	label{
	  		font-weight: bold;
	  	}
  	</style>
  	
  </head>
  
  <body>
  	<h2>${table.tableAlias}详情</h2>
	<div style="margin:20px 0;"></div>
	<div class="easyui-panel" title="${table.tableAlias}详情" style="width:100%;max-width:400px;padding:30px 60px;">
		<#list table.columns as column>
		<#if !column.htmlHidden>
		<div style="margin-bottom:20px">
			<#if column.isDateTimeColumn>
			<label class="label-top">${column.columnAlias} </label> : <@jspEl classNameFirstLower+"."+column.columnNameLower+"Str"/>
			<#else>
			<label class="label-top">${column.columnAlias} </label> : <@jspEl classNameFirstLower+"."+column.columnNameLower/>
			</#if>
		</div>
		</#if>
		</#list>
	</div>
  </body>
</html>
