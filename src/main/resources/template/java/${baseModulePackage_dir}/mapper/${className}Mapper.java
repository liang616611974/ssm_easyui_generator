<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>   
package ${mapperPackage};

import org.apache.ibatis.annotations.Mapper;
import ${baseObjPackage}.BaseMapper;
import ${pojoPackage}.${className};
import ${qoPackage}.${className}Query;





/**
* @Title: ${className}Mapper
* @Description:
* @author Liangfeng
* @date ${now?string("yyyy-MM-dd")}
* @version 1.0
 */
@Mapper
public interface ${className}Mapper extends BaseMapper<${className},${className}Query,${table.idColumn.javaType}> {


}
