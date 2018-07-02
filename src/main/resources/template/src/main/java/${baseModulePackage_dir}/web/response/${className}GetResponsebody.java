package ${responsePackage};
<#include "/java_copyright.include">
<#include "/macro.include"/>
<#assign className = table.className>
<#assign classNameLower = className?uncap_first>
import ${corePackage}.constant.AppConstant;

import java.util.Date;
import java.math.BigDecimal;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.fasterxml.jackson.annotation.JsonFormat;


/**
 * @author Liangfeng
 * @version 1.0
 * @Title: ${className}GetResponsebody
 * @Description:
 * @date ${now?string("yyyy-MM-dd")}
 */
@Data
public class ${className}GetResponsebody {

<@generateFields/>


<#-- 生成java字段-->
<#macro generateFields>
<#list table.columns as column>
<#if column.isDateColumn>
    /**
     * ${column.columnAlias!} ${column.sqlName}
     */
    @ApiModelProperty(value = "${column.columnAlias!}",example = "${column.isDateTimeColumn?string("2018-01-01 00:00:00","2018-01-01")}")
    @JsonFormat(pattern = ${column.isDateTimeColumn?string("AppConstant.PATTERN_DATETIME","AppConstant.PATTERN_DATE")},locale = AppConstant.LOCALE,timezone = AppConstant.TIMEZONE)
    private Date ${column.columnNameLower};

<#else>
    /**
     * ${column.columnAlias!} ${column.sqlName}
     */
    @ApiModelProperty(value = "${column.columnAlias!}",example = "${column.javaType?contains("String")?string(column.columnAlias!,"")}")
    private ${column.javaType} ${column.columnNameLower};

</#if>
</#list>
    @Override
    public String toString() {return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);}
</#macro>
}

