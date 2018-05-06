<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>   
package ${basePackage}.${module}.service;

import java.util.Date;
import java.util.List;




<#--
import ${corePackage}.model.pojo.${className};
import ${corePackage}.model.query.${className}Query;
import ${framePackage}.base.BaseTest;
import ${framePackage}.page.Page;
import ${commonPackage}.helper.UUIDHelper;
import ${corePackage}.model.pojo.${className};
import ${basePackage}.${module}.dev.service.${className}Service;
-->
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;


/**
* @Title: ${className}Test.java
* @Description:
* @author Liangfeng
* @date ${now?string("yyyy-MM-dd")}
* @version 1.0
 */
@Transactional
@Rollback
@RunWith(SpringRunner.class)
@SpringBootTest
public class ${className}Test extends BaseTest{
	
	@Autowired
	private ${className}Service service;
	
	
    @Test
	public void testSave(){
		${className} ${classNameLower} = new ${className}();
		${classNameLower}.setId(UUIDHelper.getUUID());
<#list table.columns as column>
	<#if column.isNotIdOrVersionField>
	  <#if column.isDateTimeColumn>
	  	${classNameLower}.set${column.columnName}(new ${column.javaType}());
	  <#else>
	  	${classNameLower}.set${column.columnName}(new ${column.javaType}("1"));
	  </#if>
	</#if>
</#list>
		service.save(${classNameLower});
	}
    
    @Test
	public void testModify(){
		${className} ${classNameLower} = new ${className}();
		${classNameLower}.setId(UUIDHelper.getUUID());
<#list table.columns as column>
	<#if column.isNotIdOrVersionField>
	  <#if column.isDateTimeColumn>
	  	${classNameLower}.set${column.columnName}(new ${column.javaType}());
	  <#else>
	  	${classNameLower}.set${column.columnName}(new ${column.javaType}("2"));
	  </#if>
	</#if>
</#list>
		service.modify(${classNameLower});
	}
	
	@Test
	public void testRemove(){
		${className} ${classNameLower} = new ${className}();
		${classNameLower}.setId(UUIDHelper.getUUID());
		service.remove(${classNameLower}.getId());
	}
	
	@Test
	public void testQuery(){
		List<${className}> ${classNameLower}s = service.query();
		for(${className} ${classNameLower} : ${classNameLower}s){
			System.out.println(${classNameLower});
		}
	}
	
	@Test
	public void testQueryByQuery(){
		${className}Query ${classNameLower}Query = new ${className}Query();
		${classNameLower}Query.setId(UUIDHelper.getUUID());
		List<${className}> ${classNameLower}s = service.query(${classNameLower}Query);
		for(${className} ${classNameLower} : ${classNameLower}s){
			System.out.println(${classNameLower});
		}
	}
	
	@Test
	public void testQueryPage(){
		${className}Query ${classNameLower}Query = new ${className}Query();
		${classNameLower}Query.setPageNumber(1);
		${classNameLower}Query.setPageSize(10);
		${classNameLower}Query.setPageWidth(10);
		Page<AdminUser> page = service.queryPage(${classNameLower}Query);
		List<${className}> ${classNameLower}s = page.getResult();
		for(${className} ${classNameLower} : ${classNameLower}s){
			System.out.println(${classNameLower});
		}
	}
	
	
}
