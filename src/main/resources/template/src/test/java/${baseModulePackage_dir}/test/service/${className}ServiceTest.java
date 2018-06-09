package ${testServicePackage};
<#include "/java_copyright.include">
<#assign className = table.className>
<#assign classNameLower = className?uncap_first>
<#assign tableName = tableChName!>
import ${corePackage}.constant.AppConstant;
import ${corePackage}.framework.base.BaseTest;
import ${corePackage}.helper.ObjectHelper;
import ${corePackage}.web.dto.request.GetRequestbody;
import ${corePackage}.web.dto.request.RemoveRequestbody;
import ${corePackage}.web.dto.response.AddResponsebody;
import ${pojoPackage}.${className};
import ${mapperPackage}.${className}Mapper;
import ${servicePackage}.${className}Service;
import ${requestPackage}.${className}AddOrMdfRequestbody;
import ${requestPackage}.${className}QueryRequestbody;
import ${responsePackage}.${className}GetResponsebody;
import ${responsePackage}.${className}QueryResponsebody;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Liangfeng
 * @version 1.0
 * @Title: ${className}ServiceTest
 * @Description:
 * @date ${now?string("yyyy-MM-dd")}
 */
@Transactional
@Rollback
@RunWith(SpringRunner.class)
@SpringBootTest
public class ${className}ServiceTest extends BaseTest{

    // 不需要校验的字段名称数组
    private static final String[] noneValidFileds = {AppConstant.SESSION_ATTR_NAME_USERID, AppConstant.SESSION_ATTR_NAME_USERROLES};

    @Autowired
    ${className}Mapper ${classNameLower}Mapper;

    @Autowired
    ${className}Service service;

    @Before
    public void before() {
        // 1.清除已有的数据，避免影响测试
        List<${className}> ${classNameLower}s = ${classNameLower}Mapper.query(null);
        for (${className} ${classNameLower} : ${classNameLower}s) {
            ${classNameLower}Mapper.delete(${classNameLower}.getId());
        }
    }

    @Test
    public void testAdd() {
        // 1.定义参数
        ${className}AddOrMdfRequestbody requestbody;
        AddResponsebody responsebody;
        ${className} ${classNameLower};
        String errMsg = "测试添加${tableName}失败";

        // 2.测试情景
        // 2.1 测试情景一
        // 构造测试数据
        requestbody = new ${className}AddOrMdfRequestbody();
        ObjectHelper.initFieldsVal(requestbody, 1);
        // 执行
        responsebody = service.add(requestbody);
        // 验证结果
        ${classNameLower} = ${classNameLower}Mapper.get(requestbody.getId());
        compareObjProps(errMsg, requestbody, ${classNameLower}, noneValidFileds);
        Assert.assertEquals(errMsg, requestbody.getId(), responsebody.getId());
    }

    @Test
    public void testModify() {
        // 1.定义参数
        ${className}AddOrMdfRequestbody requestbody;
        ${className} ${classNameLower};
        String errMsg = "测试修改${tableName}失败";

        // 2.测试情景
        // 2.1 测试情景一
        // 构造测试数据
        ${classNameLower} = new ${className}();
        ObjectHelper.initFieldsVal(${classNameLower}, 1);
        ${classNameLower}Mapper.insert(${classNameLower});
        requestbody = new ${className}AddOrMdfRequestbody();
        ObjectHelper.initFieldsVal(requestbody, 2);
        requestbody.setId(${classNameLower}.getId());
        // 运行
        service.modify(requestbody);
        // 验证结果
        ${classNameLower} = ${classNameLower}Mapper.get(requestbody.getId());
        compareObjProps(errMsg, requestbody, ${classNameLower}, noneValidFileds);
    }

    @Test
    public void testRemove() {
        // 1.定义参数
        RemoveRequestbody requestbody;
        ${className} ${classNameLower};
        String errMsg = "测试删除${tableName}失败";

        // 2.测试情景
        // 2.1 测试情景一
        // 构造测试数据
        requestbody = new RemoveRequestbody();
        for (int i = 1; i <= 3; i++) {
            ${classNameLower} = new ${className}();
            ObjectHelper.initFieldsVal(${classNameLower}, i);
            ${classNameLower}Mapper.insert(${classNameLower});
            requestbody.add(${classNameLower}.getId());
        }
        // 运行
        service.remove(requestbody);
        List<${className}> ${classNameLower}s = ${classNameLower}Mapper.query(null);
        Assert.assertEquals(errMsg, 0, ${classNameLower}s.size());
    }

    @Test
    public void testGet() {
        // 1.定义参数
        GetRequestbody requestbody;
        ${className}GetResponsebody responsebody;
        ${className} ${classNameLower};
        String errMsg = "测试获取${tableName}失败";

        // 2.测试情景
        // 2.1 测试情景一
        // 构造测试数据
        ${classNameLower} = new ${className}();
        ObjectHelper.initFieldsVal(${classNameLower}, 1);
        ${classNameLower}Mapper.insert(${classNameLower});
        requestbody = new GetRequestbody();
        requestbody.setId(${classNameLower}.getId());
        // 运行
        responsebody = service.get(requestbody);
        // 验证结果
        compareObjProps(errMsg, ${classNameLower}, responsebody, noneValidFileds);
    }

    @Test
    public void testQuery() {
        // 1.定义参数
        ${className}QueryRequestbody requestbody;
        ${className}QueryResponsebody responsebody;
        ${className} ${classNameLower};
        String errMsg = "测试查询${tableName}失败";

        // 2.测试情景
        // 2.1 测试情景一
        // 构造测试数据
        List<${className}> ${classNameLower}s = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            ${classNameLower} = new ${className}();
            ObjectHelper.initFieldsVal(${classNameLower}, i);
            ${classNameLower}Mapper.insert(${classNameLower});
            ${classNameLower}s.add(${classNameLower});
        }
        requestbody = new ${className}QueryRequestbody();
        // 运行
        responsebody = service.query(requestbody);
        // 验证结果
        List<${className}GetResponsebody> getResponsebodies = responsebody.getRows();
        for (int i = 0; i < 3; i++) {
            compareObjProps(errMsg, ${classNameLower}s.get(i), getResponsebodies.get(i));
        }
    }

    @Test
    public void testQueryPage() {
        // 1.定义参数
        ${className}QueryRequestbody requestbody;
        ${className}QueryResponsebody responsebody;
        ${className} ${classNameLower};
        String errMsg = "测试查询${tableName}失败";

        // 2.测试情景
        // 2.1 测试情景一
        // 构造测试数据
        List<${className}> ${classNameLower}s = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            ${classNameLower} = new ${className}();
            ObjectHelper.initFieldsVal(${classNameLower}, i);
            ${classNameLower}Mapper.insert(${classNameLower});
            ${classNameLower}s.add(${classNameLower});
        }
        requestbody = new ${className}QueryRequestbody();
        requestbody.setPageNum(1);
        requestbody.setPageSize(1);
        requestbody.setSortColumns("id desc");
        // 运行
        responsebody = service.queryPage(requestbody);
        // 验证结果
        Assert.assertEquals(errMsg, 3, responsebody.getTotal());
        Assert.assertEquals(errMsg, 1, responsebody.getRows().size());
        compareObjProps(errMsg, ${classNameLower}s.get(2), responsebody.getRows().get(0));
    }

}
