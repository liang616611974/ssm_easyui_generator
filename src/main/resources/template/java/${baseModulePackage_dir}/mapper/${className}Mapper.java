package ${mapperPackage};
<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>   
import ${baseObjPackage}.BaseMapper;
import ${pojoPackage}.${className};
import ${qoPackage}.${className}Query;

import org.apache.ibatis.annotations.Mapper;


/**
 * @author Liangfeng
 * @version 1.0
 * @Title: ${className}Mapper
 * @Description:
 * @date ${now?string("yyyy-MM-dd")}
 */
@Mapper
public interface ${className}Mapper extends BaseMapper<${className},${className}Query,${table.idColumn.javaType}> {


}
