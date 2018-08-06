package ${requestPackage};
<#include "/java_copyright.include">
<#include "/macro.include"/>
<#assign className = table.className>
<#assign classNameLower = className?uncap_first>
import ${corePackage}.web.dto.request.AddOrMdfRequestbody;
import ${corePackage}.constant.AppConstant;

import java.util.Date;
import java.math.BigDecimal;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.*;
import javax.validation.constraints.NotNull;


/**
 * @author Liangfeng
 * @version 1.0
 * @Title: ${className}AddOrMdfRequestbody
 * @Description:
 * @date ${now?string("yyyy-MM-dd")}
 */
@Data
public class ${className}AddOrMdfRequestbody extends AddOrMdfRequestbody{
	
<@generateFields/>


<#-- 生成java字段-->
<#macro generateFields>
<#assign arr=(commonColumn?split(","))>
<#list table.notPkColumns as column>
<#--不生成公共字段 创建时间，创建用户等 -->
<#if arr?seq_contains(column.sqlName)>
<#elseif column.isDateColumn>
    /**
     * ${column.columnAlias!} ${column.sqlName}
     */
    @ApiModelProperty(value = "${column.columnAlias!}", example = "${column.isDateTimeColumn?string("2018-01-01 00:00:00","2018-01-01")}")
    @JsonFormat(pattern = ${column.isDateTimeColumn?string("AppConstant.PATTERN_DATETIME","AppConstant.PATTERN_DATE")},locale = AppConstant.LOCALE,timezone = AppConstant.TIMEZONE)
    <#if column.hibernateValidatorExprssion!="">
    ${column.hibernateValidatorExprssion}
    </#if>
    private Date ${column.columnNameLower};

<#else>
    /**
     * ${column.columnAlias!} ${column.sqlName}
     */
    @ApiModelProperty(value = "${column.columnAlias!}", example = "${exampleVal(column.javaType,column.columnAlias)}")
    <#if column.hibernateValidatorExprssion!="">
    ${column.hibernateValidatorExprssion}
    </#if>
    private ${column.javaType} ${column.columnNameLower};

</#if>
</#list>
    @Override
    public String toString() {return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);}
</#macro>
}

