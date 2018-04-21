<#include "/java_copyright.include">
<#include "/macro.include"/>
<#include "/query.include"/>
<#assign className = table.className>
<#assign classNameLower = className?uncap_first> 
package ${qoPackage};


import java.util.Date;
import java.math.BigDecimal;

import lombok.Data;
import ${baseObjPackage}.BaseQuery;



/**
* @Title: ${className}Query.java
* @Description:
* @author Liangfeng
* @date ${now?string("yyyy-MM-dd")}
* @version 1.0
 */
@Data
public class ${className}Query extends BaseQuery {

	<@generateFields/>
	<#-- <@generateProperties/> -->

}







