<#include "/java_copyright.include">
<#include "/macro.include"/>
<#include "/pojo.include"/>
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
package ${pojoPackage};


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
<#-- <@generateConstructor className/>
<#-- <@generateJavaColumns/> -->
<@generateJavaOneToMany/>
<@generateJavaManyToOne/>

}

