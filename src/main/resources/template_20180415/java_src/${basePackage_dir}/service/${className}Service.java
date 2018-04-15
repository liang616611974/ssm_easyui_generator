<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
package ${basePackage}.${module}.dev.service;



import ${framePackage}.base.BaseService;
import ${corePackage}.model.pojo.${className};
import ${corePackage}.model.query.${className}Query;

/**
* @Title: ${className}Service.java
* @Description:
* @author Liangfeng
* @date ${now?string("yyyy-MM-dd")}
* @version 1.0
 */
public interface ${className}Service extends BaseService<${className},${className}Query,${table.idColumn.javaType}>{


<#list table.columns as column>
	<#if column.unique && !column.pk>
	/**
	 * 根据${column.columnName}查询
	 * @param v
	 * @return
	 */
	${className} getBy${column.columnName}(${column.javaType} v);
	</#if>
</#list>
	
}
