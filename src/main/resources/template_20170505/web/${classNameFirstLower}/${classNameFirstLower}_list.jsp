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
    
    <title>${table.tableAlias}列表页</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
  	<script type="text/javascript" src="<@jspEl 'jsUrl'/>/home/admin/${classNameFirstLower}/${classNameFirstLower}_list.js"></script>
 	<style type="text/css">
		.ftitle{
			font-size: 14px;
			font-weight: bold;
			margin-bottom: 0px;
		}
		.fitem{
			margin-bottom: 10px;
		}
	</style>
  </head>
  
  <body>
  
  	<!-- 搜索栏开始 -->
	<div class="easyui-panel" style="width:100%;max-width:1240px;padding:20px 30px;">
	<#list table.columns as column>
		<#-- 属性名和输入框的间隔 -->
		<#assign separate="&nbsp：">
		<#assign after="&nbsp;&nbsp;&nbsp;&nbsp;">
	<#if !column.htmlHidden>
		<#-- 时间列 -->
		<#if column.isDateTimeColumn>
		<label>${column.columnAlias}开始：</label> <input id="${column.columnNameFirstLower}BeginStr"  class="easyui-datetimebox" />${after}
		<label>${column.columnAlias}结束：</label> <input id="${column.columnNameFirstLower}EndStr"  class="easyui-datetimebox" />${after}
		<#-- 枚举列  -->
		<#elseif column.columnAlias?contains("：")>
		<#assign ci=column.columnAlias?index_of("：")> 
		<label>${column.columnAlias?substring(0,ci)}${separate}</label> <select id="${column.columnNameFirstLower}" class="easyui-combobox" editable="false" style="width: 200px;">
					<option value="">无</option>
					<#list column.columnAlias?substring(ci+1)?split("，") as enum >
					<option value="${(enum?split("-"))[0]}">${(enum?split("-"))[1]}</option>
					</#list>
		</select>${after}
		<#else>
		<label>${column.columnAlias}${separate}</label> <input id="${column.columnNameFirstLower}" class="easyui-textbox" />${after}
		</#if>
	</#if>
	</#list>
		<a href="#" class="easyui-linkbutton" iconCls="icon-search" style="width:80px" onclick="doSearch()">搜索</a>
	</div>
	<!-- 搜索栏结束 -->
	
	<!-- 工具栏开始 -->
	<div id="toolbar">
		<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="new${className}()">新建 AdminUser</a> 
		<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="edit${className}()">编辑 AdminUser</a> 
		<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="remove${className}()">移除 AdminUser</a>
	</div>
	<!-- 工具栏结束 -->
	
	<!--数据列表开始-->
  	<div id="dg"></div>
  	<!--数据列表结束-->
  	
  	<!-- 新增/修改对话框开始-->
	<div id="dlg_new_modify" class="easyui-dialog" style="width:400px;height:380px;padding:10px 20px" closed="true" buttons="#dlg_buttons_new_modify">
		<div class="ftitle">后台用户信息</div>
		<hr style="margin-bottom: 10px;" />
		<form id="fm" method="post">
			<!-- easyui控件内，value=''赋值无效，必须用其独有的方法赋值，form.load或setValue -->
			<#-- 遍历隐藏的主键 开始-->
			<#list table.pkColumns as column>
			<input name="${column.columnNameFirstLower}" type="hidden" />
			</#list>
			<#-- 遍历隐藏的主键 结束-->
		<#-- 遍历列 开始-->
		<#list table.columns as column>
			<#if !column.htmlHidden>
				<#-- 属性名和输入框的间隔 -->
				<#assign separate="&nbsp：">
				<#-- 是否必填表达式 required="true"-->
				<#assign requiredExp=column.nullable?string('','required="true"')>
				<#-- 验证表达式  validType="minLength[3]" --> 
				<#assign validExp=(column.sqlName?contains("mobile")?string('validType="mobile"',''))
				+(column.sqlName?contains("email")?string('validType="email"',''))
				+(column.sqlName?contains("idcard")?string('validType="idcard"',''))> 
			<#-- 时间列 -->
			<#if column.isDateTimeColumn>
			<div class="fitem">
				<label>${column.columnAlias}${separate}</label> <input name="${column.columnNameFirstLower}Str" class="easyui-datetimebox" ${requiredExp} />
			</div>
			<#-- 枚举列  -->
			<#elseif column.columnAlias?contains("：")>
				<#assign ci=column.columnAlias?index_of("：")> 
			<div class="fitem">
				<label>${column.columnAlias?substring(0,ci)}${separate}</label> <select name="${column.columnNameFirstLower}" class="easyui-combobox" editable="false" ${requiredExp} style="width: 200px;">
					<#list column.columnAlias?substring(ci+1)?split("，") as enum >
					<option value="${(enum?split("-"))[0]}">${(enum?split("-"))[1]}</option>
					</#list>
				</select>
			</div>
			<#-- 普通列 -->
			<#else>
			<div class="fitem">
				<label>${column.columnAlias}${separate}</label> <input name="${column.columnNameFirstLower}" class="easyui-${column.columnAlias?contains('密码')?string('passwordbox','textbox')}" ${requiredExp} ${validExp}/>
			</div>
			</#if>
			</#if>
		</#list>
		<#-- 遍历列 结束-->
		</form>
	</div>
	<div id="dlg_buttons_new_modify">
		<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="save${className}()">保存</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:adminUI.closeDialog('dlg_new_modify')">取消</a>
	</div>
	<!-- 新增/修改对话框结束-->

<!-- 注意：如果要value属性赋值(value="aaa")，就不能放到easyui的任何控件内，比如easyui-dialog, 否则赋值失败-->
<input id="token" type="hidden" value="<@jspEl 'token'/>"/>
  </body>
</html>
