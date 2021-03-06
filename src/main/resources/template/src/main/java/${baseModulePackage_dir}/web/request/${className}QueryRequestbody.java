package ${requestPackage};
<#include "/java_copyright.include">
<#include "/macro.include"/>
<#assign className = table.className>
<#assign classNameLower = className?uncap_first>
import ${corePackage}.web.dto.request.QueryPageRequestbody;
import ${corePackage}.constant.AppConstant;

import java.util.Date;
import java.math.BigDecimal;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Liangfeng
 * @version 1.0
 * @Title: ${className}QueryRequestbody
 * @Description:
 * @date ${now?string("yyyy-MM-dd")}
 */
@Data
@NoArgsConstructor
public class ${className}QueryRequestbody extends QueryPageRequestbody{
	
<@generateFields/>


<#-- 生成java字段-->
<#macro generateFields>
<#list table.columns as column>
<#if column.isDateColumn>
    /**
     * ${column.columnAlias!}开始 ${column.sqlName}
     */
    @ApiModelProperty(value = "${column.columnAlias!}开始", example = "${column.isDateTimeColumn?string("2018-01-01 00:00:00","2018-01-01")}")
    @JsonFormat(pattern = ${column.isDateTimeColumn?string("AppConstant.PATTERN_DATETIME","AppConstant.PATTERN_DATE")},locale = AppConstant.LOCALE,timezone = AppConstant.TIMEZONE)
    private Date ${column.columnNameLower}Begin;

    /**
     * ${column.columnAlias!}结束 ${column.sqlName}
     */
    @ApiModelProperty(value = "${column.columnAlias!}结束", example = "${column.isDateTimeColumn?string("2018-01-01 00:00:00","2018-01-01")}")
    @JsonFormat(pattern = ${column.isDateTimeColumn?string("AppConstant.PATTERN_DATETIME","AppConstant.PATTERN_DATE")},locale = AppConstant.LOCALE,timezone = AppConstant.TIMEZONE)
    private Date ${column.columnNameLower}End;

<#else>
    /**
     * ${column.columnAlias!} ${column.sqlName}
     */
    @ApiModelProperty(value = "${column.columnAlias!}", example = "${exampleVal(column.javaType,column.columnAlias)}")
    private ${column.javaType} ${column.columnNameLower};

</#if>
</#list>
    @Override
    public String toString() {return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);}
</#macro>
}

