<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
package ${basepackage}.service.${module}.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lf.exception.ServiceException;
import com.sdp.framework.base.BaseDao;
import com.sdp.framework.base.BaseServiceImpl;

import ${corePackage}.model.pojo.${className};
import ${corePackage}.model.query.${className}Query;
import ${corePackage}.dao.${className}Dao;
import ${basePackage}.${module}.dev.service.${className}Service;

/**
* @Title: ${className}ServiceImpl.java
* @Description:
* @author Liangfeng
* @date ${now?string("yyyy-MM-dd")}
* @version 1.0
 */
@Service
@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
public class ${className}ServiceImpl extends BaseServiceImpl<${className},${className}Query,${table.idColumn.javaType}> implements ${className}Service{

	private static final Logger logger = LoggerFactory.getLogger(${className}Service.class);
	
	@Autowired
	private ${className}Dao ${classNameLower}Dao;
	
	@Override
	public BaseDao<${className},${className}Query,${table.idColumn.javaType}> getBaseDao() {
		return this.${classNameLower}Dao;
	}

	
<#list table.columns as column>
 <#if column.unique && !column.pk>
 	@Override
	public ${className} getBy${column.columnName}(${column.javaType} v) {
		return ${classNameLower}Dao.getBy${column.columnName}(v);
	}	
	
 </#if>
</#list>
}
