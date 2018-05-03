package ${servicePackage};
<#include "/java_copyright.include">
<#include "/macro.include"/>
<#assign className = table.className>
<#assign tableName = tableChName!>
<#assign classNameLower = className?uncap_first>
import ${corePackage}.web.dto.request.GetRequestbody;
import ${corePackage}.web.dto.request.RemoveRequestbody;
import ${corePackage}.web.dto.response.AddResponsebody;
import ${requestPackage}.${className}QueryRequestbody;
import ${requestPackage}.${className}AddOrMdfRequestbody;
import ${responsePackage}.${className}GetResponsebody;
import ${responsePackage}.${className}QueryResponsebody;


/**
 * @author Liangfeng
 * @version 1.0
 * @Title: ${className}Service
 * @Description:
 * @date ${now?string("yyyy-MM-dd")}
 */
public interface ${className}Service {

    /**
     * 添加${tableName}
     * @param requestbody
     */
    AddResponsebody add(${className}AddOrMdfRequestbody requestbody);

    /**
     * 修改${tableName}
     * @param requestbody
     */
    void modify(${className}AddOrMdfRequestbody requestbody);

    /**
     * 删除${tableName}
     * @param requestbody
     */
    void remove(RemoveRequestbody requestbody);

    /**
     * 获取${tableName}
     * @param requestbody
     * @return
     */
    ${className}GetResponsebody get(GetRequestbody requestbody);

    /**
     * 查询${tableName}
     * @param requestbody
     * @return
     */
    ${className}QueryResponsebody query(${className}QueryRequestbody requestbody);

    /**
     * 分页查询${tableName}
     * @param requestbody
     */
    ${className}QueryResponsebody queryPage(${className}QueryRequestbody requestbody);

}

