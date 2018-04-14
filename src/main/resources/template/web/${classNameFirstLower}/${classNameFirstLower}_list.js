<#include "/macro.include"/> 
<#include "/custom.include"/> 
<#assign className = table.className>   
<#assign classNameFirstLower = className?uncap_first> 
<#assign classNameLowerCase = className?lower_case> 
var dgId = "dg";//数据列表对象id
var saveUrl;//保存对象的url
/** 初始化 */
$(function() {
	var dgParam = {
			title : "${table.tableAlias}列表",
			height : 560,
			url : base.getPrjUrl() + "/${classNameFirstLower}/listData.do",
			method : 'POST',
			//queryParams : {id:'1'},
			idField : "id",
			loadMsg : '数据加载中,请稍后……',
			striped : true,
			fitColumns : true,
			singleSelect : true,
			rownumbers : true,
			fitColumns : false,
			pagination : true,
			autoRowHeight : true,
			toolbar:"#toolbar",
			columns :[[
			   {
			       field:'_operate',
				   title:'操作',	
				   width : 100,	
				   formatter: function(value,row,index){				    	
					   var html = "&nbsp;&nbsp;<a href='javascript:void(0);' class='easyui-linkbutton' onclick='detail(\""+row.id+"\")' >查看详情</a>";
					   return html;
				   }
			   },
			  <#-- 遍历行 开始-->
			  <#list table.columns as column>
			  <#if !column.htmlHidden>
			   <#-- 时间列 -->
			   <#if column.isDateTimeColumn>
			   {
				   field : '${column.columnNameLower}Str',
				   title : '${column.columnAlias} ',
				   width : 150		
			   }
			   <#-- 枚举列 -->
			   <#elseif column.columnAlias?contains("：")>
					<#assign ci=column.columnAlias?index_of("：")> 
			   {
			       field : '${column.columnNameLower}',
				   title : '${column.columnAlias?substring(0,ci)} ',
				   width : 150,
				   formatter: function(value,row,index){
				   <#list column.columnAlias?substring(ci+1)?split("，") as enum >
				    <#if enum_index==0>
				   	if(value==${(enum?split("-"))[0]}){
						return '${(enum?split("-"))[1]}';
					<#else>
					}else if(value==${(enum?split("-"))[0]}){
						return '${(enum?split("-"))[1]}';
				    </#if>
					</#list>
					}else{
						return '未知';
					}
				  }		
			   }
			   <#-- 普通列 -->
			   <#else>
			   {
				   field : '${column.columnNameLower}',
				   title : '${column.columnAlias} ',
				   width : 150		
			   }
			   </#if>
			   <#if column_has_next>,</#if>
			   </#if>
			   </#list>
			   <#-- 遍历行 结束-->	 		
			]],
			onLoadSuccess : function(data) {
			},
			onLoadError : function() {
				alert("获取数据 失败");
			}
		};
	adminUI.datagrid(dgId,"show",dgParam);
});

/** 搜索 */
function doSearch(){
	adminUI.datagrid(dgId,"load",{
	 <#list table.columns as column>
	 	<#if !column.htmlHidden>
	 	<#-- 时间列 -->
	 	<#if column.isDateTimeColumn>
		${column.columnNameLower}BeginStr:adminUI.datetimebox("${column.columnNameLower}BeginStr","getValue"),
		${column.columnNameLower}EndStr:adminUI.datetimebox("${column.columnNameLower}EndStr","getValue")
		<#-- 枚举列 -->
		<#elseif column.columnAlias?contains("：")>
		${column.columnNameLower}:adminUI.combobox("${column.columnNameLower}","getValue")
		<#-- 普通列 -->
		<#else>
		${column.columnNameLower}:adminUI.textbox("${column.columnNameLower}","getValue")
		</#if>
		<#if column_has_next>,</#if>
		</#if>
	 </#list>
	});
}

/** 详细 */
function detail(id){
	var url = base.getPrjUrl() + "/${classNameFirstLower}/detail.do?id=" + id;
	adminUI.addTabByParent("${table.tableAlias}详情", url);
}

/** 新建 */
function new${className}(){
	$("#fm").clear();
	adminUI.openDialog("dlg_new_modify","新建${className}");
	//设置保存url
	saveUrl = base.getPrjUrl() + "/${classNameFirstLower}/save.do"+"?token="+$("#token").val();
}

/** 保存 */
function save${className}() {
	var fm = $("#fm");
	//1.校验
	if (!fm.validate())
		return false;
	//2.显示进度条
	adminUI.openProgress();
	//3.提交保存
	ajax.post(window.saveUrl, 'json', fm.serializeObject(), function(result) {
		if (result.flag) {
			adminUI.alertInfo("保存${table.tableAlias}成功");
		} else {
			adminUI.alertErr(result.msg);
		}
	}, function(xhr,textStatus){
		adminUI.closeProgress();
		adminUI.closeDialog("dlg_new_modify");
		adminUI.datagrid(dgId,"reload");
	});
}

/** 编辑 */
function edit${className}(){
	var fm = $("#fm");
	//获取选中的行
	var row = adminUI.datagrid(dgId,'getSelected');
	if(row==null){
		adminUI.alertInfo("请选择要修改的行");
		return false;
	}
	fm.clear();
	//设置保存url
	saveUrl = base.getPrjUrl() + "/${classNameFirstLower}/modify.do";
	//打开编辑页面
	var url = base.getPrjUrl() + "/${classNameFirstLower}/edit.do";
	ajax.post(url,'json',{id:row.id},function(result){
		if(result.flag) {
			fm.load(result.data);
			adminUI.openDialog("dlg_new_modify", "编辑${className}");
		}else {
			adminUI.alertErr(result.msg);
		}
	});
	
}

/** 删除 */
function remove${className}() {
	var row = adminUI.datagrid(dgId,"getSelected");
	if(row==null){
		adminUI.alertInfo("请选择要删除的行");
		return false;
	}
	adminUI.confirm("删除","确认删除该条数据吗？",function(r){
		if(r){
			//1.定义需要的变量
			var url = base.getPrjUrl() + "/${classNameFirstLower}/remove.do";
			//2.显示进度条
			adminUI.openProgress();
			//3.提交保存
			ajax.post(url, 'json', {id:row.id}, function(result) {
				if (result.flag) {
					adminUI.alertInfo("删除${table.tableAlias}成功");
				} else {
					adminUI.alertErr(result.msg);
				}
			}, function(){
				adminUI.closeProgress();
				adminUI.datagrid(dgId,"reload");
			});
		}
	});
}
