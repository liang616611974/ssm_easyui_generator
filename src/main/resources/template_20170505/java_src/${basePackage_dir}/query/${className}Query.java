<#include "/macro.include"/>
<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
package ${corePackage}.model.query;

import java.util.Date;
import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import ${commonPackage}.helper.DateHelper;
import ${framePackage}.base.BaseQuery;


/**
* @Title: ${className}Query.java
* @Description:
* @author Liangfeng
* @date ${now?string("yyyy-MM-dd")}
* @version 1.0
 */
public class ${className}Query extends BaseQuery {
    private static final long serialVersionUID = 3148176768559230877L;
    
	<@generateFields/>
	<@generateProperties/>

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
	
}

<#macro generateFields>

	<#list table.columns as column>
	/** ${column.columnAlias} */
	<#if column.isDateTimeColumn && !column.contains("**")>
	private ${column.javaType} ${column.columnNameLower}Begin;
	private ${column.javaType} ${column.columnNameLower}End;
	<#else>
	private ${column.javaType} ${column.columnNameLower};
	</#if>
	</#list>

</#macro>

<#macro generateProperties>
	<#list table.columns as column>
	<#if column.isDateTimeColumn && !column.contains("**")>
	public ${column.javaType} get${column.columnName}Begin() {
		return this.${column.columnNameLower}Begin;
	}
	
	public void set${column.columnName}Begin(${column.javaType} value) {
		this.${column.columnNameLower}Begin = value;
	}
	
	public ${column.javaType} get${column.columnName}End() {
		return this.${column.columnNameLower}End;
	}
	
	public void set${column.columnName}End(${column.javaType} value) {
		this.${column.columnNameLower}End = value;
	}
	
	public String get${column.columnName}BeginStr() {
		return DateHelper.format(get${column.columnName}Begin(), DATETIME_PATTERN);
	}
	
	public void set${column.columnName}BeginStr(String value) {
		set${column.columnName}Begin(DateHelper.parse(value, DATETIME_PATTERN));
	}
	
	public String get${column.columnName}EndStr() {
		return DateHelper.format(get${column.columnName}End(), DATETIME_PATTERN);
	}
	
	public void set${column.columnName}EndStr(String value) {
		set${column.columnName}End(DateHelper.parse(value, DATETIME_PATTERN));
	}
	
	<#else>
	public ${column.javaType} get${column.columnName}() {
		return this.${column.columnNameLower};
	}
	
	public void set${column.columnName}(${column.javaType} value) {
		this.${column.columnNameLower} = value;
	}
	
	</#if>	
	</#list>
</#macro>



