<#include "/java_copyright.include">
<#include "/macro.include"/>
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
<#assign currClassName = table.className + "Enum"> 
package ${corePackage}.model.enumeration;



/**
* @Title: ${currClassName}.java
* @Description:
* @author Liangfeng
* @date ${now?string("yyyy-MM-dd")}
* @version 1.0
 */
public class ${currClassName} {
	
	<#list table.columns as column>
	<#if column.columnAlias?contains("：")>
	<#assign ci=column.columnAlias?index_of("：")>
	/**
	 * ${column.columnAlias}
	 */
	public enum ${column.columnName}{
		
		<#list column.columnAlias?substring(ci+1)?split("，") as enum >
		<#assign en=enum?split("-")[1]>
		<#assign ensi=en?index_of("（")>
		/**
		 * ${enum}
		 */
		${en?substring(ensi+1)?replace("）","")?upper_case}("${(enum?split("-"))[0]}")${enum_has_next?string(",",";")}
		</#list>
		
		private final String value;
	
	    private ${column.columnName}(String value) { this.value = value; }
	
	    public String value() { return value;}
	}
		
	</#if>
	</#list>
   
	
}







