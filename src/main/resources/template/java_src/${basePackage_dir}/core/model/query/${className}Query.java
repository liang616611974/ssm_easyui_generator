<#include "/java_copyright.include">
<#include "/macro.include"/>
<#include "/query.include"/>
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







