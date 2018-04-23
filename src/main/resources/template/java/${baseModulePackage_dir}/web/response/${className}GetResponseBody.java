<#include "/java_copyright.include">
<#include "/macro.include"/>
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
package ${responsePackage};


import java.util.Date;
import java.math.BigDecimal;

import lombok.Data;
import ${baseObjPackage}.BaseEntity;


/**
* @Title: ${className}
* @Description:
* @author Liangfeng
* @date ${now?string("yyyy-MM-dd")}
* @version 1.0
 */
@Data
public class ${className} extends BaseEntity{
	
<@generateFields/>

<#--
<@generateConstructor className/>
<@generateJavaColumns/>
<@generateJavaOneToMany/>
<@generateJavaManyToOne/>
        -->


<#-- 生成java字段-->
<#macro generateFields>
<#list table.columns as column>
/**
 * ${column.columnAlias!} ${column.sqlName}
 */
private ${(column.javaType?contains("Timestamp")||column.javaType?contains("Time"))?string("Date",column.javaType)} ${column.columnNameLower};

</#list>
</#macro>


        }

