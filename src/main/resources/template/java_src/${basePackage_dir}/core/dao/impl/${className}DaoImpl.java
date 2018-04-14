<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>   
package ${corePackage}.dao.impl;

import org.springframework.stereotype.Repository;
import ${corePackage}.model.pojo.${className};
import ${corePackage}.model.query.${className}Query;

import ${corePackage}.dao.${className}Dao;
import ${framePackage}.base.impl.BaseMyBatisDaoImpl;

/**
* @Title: ${className}DaoImpl.java
* @Description:
* @author Liangfeng
* @date ${now?string("yyyy-MM-dd")}
* @version 1.0
 */
@Repository
public class ${className}DaoImpl extends BaseMyBatisDaoImpl<${className},${className}Query,${table.idColumn.javaType}> implements ${className}Dao{
	
	@Override
	public String getIbatisMapperNamesapce() {
		return "${className}";
	}
	
	<#list table.columns as column>
	<#if column.unique && !column.pk>
	@Override
	public ${className} getBy${column.columnName}(${column.javaType} v) {
		return (${className})getSqlSessionTemplate().selectOne("${className}.getBy${column.columnName}",v);
	}	
	
	</#if>
	</#list>

}
