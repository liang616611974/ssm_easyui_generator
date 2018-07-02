package ${controllerPackage};
<#include "/java_copyright.include">
<#include "/macro.include"/>
<#assign className = table.className>
<#assign tableName = tableChName!>
<#assign classNameLower = className?uncap_first>
import ${corePackage}.helper.ExcelHelper;
import ${corePackage}.web.dto.request.GetRequestbody;
import ${corePackage}.web.dto.request.RemoveRequestbody;
import ${corePackage}.web.dto.request.Request;
import ${corePackage}.web.dto.response.AddResponsebody;
import ${corePackage}.web.dto.response.Response;
import ${servicePackage}.${className}Service;
import ${requestPackage}.${className}AddOrMdfRequestbody;
import ${requestPackage}.${className}QueryRequestbody;
import ${responsePackage}.${className}GetResponsebody;
import ${responsePackage}.${className}QueryResponsebody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @author Liangfeng
 * @version 1.0
 * @Title: ${className}Controller
 * @Description:
 * @date ${now?string("yyyy-MM-dd")}
 */
@RestController
@Api(description = "${tableName}模块接口")
public class ${className}Controller {

    @Autowired
    ${className}Service service;

    @ApiOperation(value = "创建${tableName}")
    @PostMapping("/${classNameLower}/add")
    public Response<AddResponsebody> add(@Validated(Request.Add.class) @RequestBody ${className}AddOrMdfRequestbody requestbody) {
        return Response.success(service.add(requestbody));
    }

    @ApiOperation(value = "修改${tableName}")
    @PostMapping("/${classNameLower}/modify")
    public Response modify(@Validated({Request.Modify.class}) @RequestBody ${className}AddOrMdfRequestbody requestbody) {
        service.modify(requestbody);
        return Response.success();
    }

    @ApiOperation(value = "删除${tableName}")
    @PostMapping("/${classNameLower}/remove")
    public Response remove(@Validated @RequestBody RemoveRequestbody requestbody) {
        service.remove(requestbody);
        return Response.success();
    }

    @ApiOperation(value = "获取${tableName}详细信息")
    @PostMapping("/${classNameLower}/get")
    public Response<${className}GetResponsebody> get(@Validated @RequestBody GetRequestbody requestbody) {
        return Response.success(service.get(requestbody));
    }

    @ApiOperation(value = "分页查询${tableName}")
    @PostMapping("/${classNameLower}/queryPage")
    public Response<${className}QueryResponsebody> queryPage(@Validated @RequestBody ${className}QueryRequestbody requestbody) {
        return Response.success(service.queryPage(requestbody));
    }

    @ApiOperation(value = "导出${tableName}")
    @RequestMapping(value = "/${classNameLower}/export",method = {RequestMethod.GET,RequestMethod.POST})
    public void export(${className}QueryRequestbody requestbody,String dowloadName,HttpServletRequest request, HttpServletResponse response){
        ${className}QueryResponsebody responsebody = service.query(requestbody);
        ExcelHelper.exportForDownloadByObj(request,response,dowloadName,responsebody.getRows(),${className}GetResponsebody.class);
    }
   
}

