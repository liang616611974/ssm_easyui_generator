<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>   
package ${corePackage}.dao;


import ${corePackage}.model.pojo.${className};
import ${corePackage}.model.query.${className}Query;

import ${framePackage}.base.BaseDao;



/**
* @Title: ${className}Dao.java
* @Description:
* @author Liangfeng
* @date ${now?string("yyyy-MM-dd")}
* @version 1.0
 */
public interface ${className}Dao extends BaseDao<${className},${className}Query,${table.idColumn.javaType}>{
	
	<#list table.columns as column>
	<#if column.unique && !column.pk>
	${className} getBy${column.columnName}(${column.javaType} v);
	
	</#if>
	</#list>

}
