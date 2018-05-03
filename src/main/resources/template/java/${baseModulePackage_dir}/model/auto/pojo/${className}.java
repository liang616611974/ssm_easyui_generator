package ${pojoPackage};
<#include "/java_copyright.include">
<#include "/macro.include"/>
<#include "/pojo.include"/>
<#assign className = table.className>
<#assign classNameLower = className?uncap_first>
import ${baseObjPackage}.BaseEntity;

import java.util.Date;
import java.math.BigDecimal;

import lombok.Data;


/**
 * @author Liangfeng
 * @version 1.0
 * @Title: ${className}
 * @Description:
 * @date ${now?string("yyyy-MM-dd")}
 */
@Data
public class ${className} extends BaseEntity{
	
<@generateFields/>
<#-- <@generateConstructor className/>
<#-- <@generateJavaColumns/> -->
<@generateJavaOneToMany/>
<@generateJavaManyToOne/>

}

