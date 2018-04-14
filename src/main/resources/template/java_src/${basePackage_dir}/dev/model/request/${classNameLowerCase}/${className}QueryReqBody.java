<#include "/java_copyright.include">
<#include "/query.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
<#assign classNameLowerCase = table.classNameLowerCase>
<#assign currClassName = table.className + "QueryReqBody"> 
package ${basePackage}.${module}.dev.model.request.${classNameLowerCase};

import java.util.Date;
import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

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
    
	<@generateFields/>
	<@generateProperties/>

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
	
}






