package ${qoPackage};
<#include "/java_copyright.include">
<#include "/macro.include"/>
<#include "/query.include"/>
<#assign className = table.className>
<#assign classNameLower = className?uncap_first>
import ${baseObjPackage}.BaseQuery;

import java.util.Date;
import java.math.BigDecimal;

import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author Liangfeng
 * @version 1.0
 * @Title: ${className}Query
 * @Description:
 * @date ${now?string("yyyy-MM-dd")}
 */
@Data
@NoArgsConstructor
public class ${className}Query extends BaseQuery {

	<@generateFields/>
	<#-- <@generateProperties/> -->

}







