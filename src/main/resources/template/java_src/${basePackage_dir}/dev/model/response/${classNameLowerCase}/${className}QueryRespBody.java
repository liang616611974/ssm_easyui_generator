<#include "/java_copyright.include">
<#include "/pojo.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
<#assign classNameLowerCase = table.classNameLowerCase>
<#assign currClassName = table.className + "QueryRespBody">
package ${basePackage}.${module}.dev.model.response.${classNameLowerCase};


import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import java.util.Date;
import java.math.BigDecimal;
import ${commonPackage}.helper.DateHelper;
import ${basePackage}.${module}.dev.model.response.BaseRespBody;


/**
* @Title: ${currClassName}.java
* @Description:
* @author Liangfeng
* @date ${now?string("yyyy-MM-dd")}
* @version 1.0
 */
public class ${currClassName} extends BaseRespBody{
	
<@generateFields/>
<@generateConstructor currClassName/>
<@generateJavaColumns/>

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
	
}

<#macro generateJavaColumns>
	<#list table.columns as column>
	<#if column.isDateColumn>
	public void set${column.columnName}(Date value) {
		this.${column.columnNameLower} = value;
	}
	
	public Date get${column.columnName}() {
		return this.${column.columnNameLower};
	}
	
	public String get${column.columnName}Str() {
		return DateHelper.format(get${column.columnName}(), ${column.isDateTimeColumn?string("DATETIME_PATTERN","DATE_PATTERN")});
	}
	
	public void set${column.columnName}Str(String value) {
		set${column.columnName}(DateHelper.parse(value, ${column.isDateTimeColumn?string("DATETIME_PATTERN","DATE_PATTERN")}));
	}
	
	<#else>
	public void set${column.columnName}(${column.javaType} value) {
		this.${column.columnNameLower} = value;
	}
	
	public ${column.javaType} get${column.columnName}() {
		return this.${column.columnNameLower};
	}
	
	</#if>
	</#list>
</#macro>




