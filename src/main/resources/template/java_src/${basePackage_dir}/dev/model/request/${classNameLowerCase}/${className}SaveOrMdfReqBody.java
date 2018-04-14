<#include "/java_copyright.include">
<#include "/pojo.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
<#assign classNameLowerCase = table.classNameLowerCase>
<#assign currClassName = table.className + "SaveOrMdfReqBody">   
package ${basePackage}.${module}.dev.model.request.${classNameLowerCase};

import org.hibernate.validator.constraints.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import java.util.Date;
import java.math.BigDecimal;
import ${commonPackage}.helper.DateHelper;
import ${basePackage}.${module}.dev.model.request.BaseReqBody;


/**
* @Title: ${currClassName}.java
* @Description:
* @author Liangfeng
* @date ${now?string("yyyy-MM-dd")}
* @version 1.0
 */
public class ${currClassName} extends BaseReqBody {
	
	<#list table.columns as column>
	<#if column.isPkColumn>
	/** 
	 * ${column.columnAlias!}
	 */
	private ${column.javaType} ${column.columnNameLower};
	<#else>
	/** 
	 * ${column.columnAlias!}
	 */
	${column.hibernateValidatorExprssion}
	private ${(column.javaType?contains("Timestamp")||column.javaType?contains("Time"))?string("Date",column.javaType)} ${column.columnNameLower};
	</#if>
	</#list>
	

<@generateConstructor currClassName/>
<@generateJavaColumns/>

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
	
}



