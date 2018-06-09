package ${servicePackage}.impl;
<#include "/java_copyright.include">
<#include "/macro.include"/>
<#assign className = table.className>
<#assign classNameLower = className?uncap_first>
import ${pojoPackage}.${className};
import ${qoPackage}.${className}Query;
import ${mapperPackage}.${className}Mapper;
import ${servicePackage}.${className}Service;
import ${corePackage}.web.dto.request.GetRequestbody;
import ${corePackage}.web.dto.request.RemoveRequestbody;
import ${corePackage}.web.dto.response.AddResponsebody;
import ${requestPackage}.${className}QueryRequestbody;
import ${requestPackage}.${className}AddOrMdfRequestbody;
import ${responsePackage}.${className}GetResponsebody;
import ${responsePackage}.${className}QueryResponsebody;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


/**
 * @author Liangfeng
 * @version 1.0
 * @Title: ${className}ServiceImpl
 * @Description:
 * @date ${now?string("yyyy-MM-dd")}
 */
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
@Service
public class ${className}ServiceImpl implements ${className}Service {

    @Autowired
    ${className}Mapper ${classNameLower}Mapper;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public AddResponsebody add(${className}AddOrMdfRequestbody requestbody) {
        // 1.定义参数
        ${className} ${classNameLower} = new ${className}();
        // 2.复制属性值
        BeanUtils.copyProperties(requestbody,${classNameLower});
        // 3.插入数据
        ${classNameLower}Mapper.insert(${classNameLower});
        // 4.返回主键
        return new AddResponsebody(${classNameLower}.getId());
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void modify(${className}AddOrMdfRequestbody requestbody) {
        // 1.定义参数
        ${className} ${classNameLower} = new ${className}();
        // 2.复制属性值
        BeanUtils.copyProperties(requestbody,${classNameLower});
        // 3.修改数据
        ${classNameLower}Mapper.update(${classNameLower});
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void remove(RemoveRequestbody requestbody) {
        // 1.遍历删除
        for (${table.idColumn.javaType} id : requestbody.getIds()) {
            ${classNameLower}Mapper.delete(id);
        }
    }

    @Override
    public ${className}GetResponsebody get(GetRequestbody requestbody) {
        // 1.定义参数
        ${className}GetResponsebody responseBody = new ${className}GetResponsebody();
        // 2.查询对象
        ${className} ${classNameLower} = ${classNameLower}Mapper.get(requestbody.getId());
        // 3.复制属性
        BeanUtils.copyProperties(${classNameLower},responseBody);
        // 4.返回数据
        return responseBody;
    }

    @Override
    public ${className}QueryResponsebody query(${className}QueryRequestbody requestbody) {
        // 1.定义参数
        ${className}QueryResponsebody responseBody = new ${className}QueryResponsebody();
        ${className}Query ${classNameLower}Query = new ${className}Query();
        BeanUtils.copyProperties(requestbody,${classNameLower}Query);

        // 2.查询
        requestbody.setSortColumns("id desc");
        List<${className}> ${classNameLower}s = ${classNameLower}Mapper.query(${classNameLower}Query);
        List<${className}GetResponsebody> getResponseBodies = responseBody.getRows();
        for (${className} ${classNameLower} : ${classNameLower}s) {
            ${className}GetResponsebody getResponseBody = new ${className}GetResponsebody();
            BeanUtils.copyProperties(${classNameLower}, getResponseBody);
            getResponseBodies.add(getResponseBody);
        }
        return responseBody;
    }

    @Override
    public ${className}QueryResponsebody queryPage(${className}QueryRequestbody requestbody) {
        // 1.定义参数
        ${className}QueryResponsebody responseBody = new ${className}QueryResponsebody();
        ${className}Query ${classNameLower}Query = new ${className}Query();
        BeanUtils.copyProperties(requestbody,${classNameLower}Query);
        int total = 0;

        // 2.查询总数
        total = ${classNameLower}Mapper.count(${classNameLower}Query);
        responseBody.setTotal(total);

        // 3.分页查询集合
        // 3.1 如何没有数据则直接返回。
        if(responseBody.getTotal()==0){
            return responseBody;
        }

        // 3.2 有数据
        requestbody.setSortColumns("id desc");
        List<${className}> ${classNameLower}s = ${classNameLower}Mapper.queryPage(${classNameLower}Query);
        List<${className}GetResponsebody> getResponseBodies = responseBody.getRows();
        for (${className} ${classNameLower} : ${classNameLower}s) {
            ${className}GetResponsebody getResponseBody = new ${className}GetResponsebody();
            BeanUtils.copyProperties(${classNameLower}, getResponseBody);
            getResponseBodies.add(getResponseBody);
        }
        return responseBody;
    }
}

