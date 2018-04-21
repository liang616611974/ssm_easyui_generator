<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>   
package ${mapperPackage};


import java.util.List;
import org.apache.ibatis.annotations.Mapper;
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
public interface ${className}Mapper {

		/**
		 * 保存
		 * @param ${classNameLower}
		 */
		void insert(${className} ${classNameLower});

		/**
		 * 修改
		 * @param ${classNameLower}
		 */
		void update(${className} ${classNameLower});

		/**
		 * 删除
		 * @param id
		 */
		void delete(${table.idColumn.javaType} id);

		/**
		 * 获取
		 * @param id
		 */
		${className} get(${table.idColumn.javaType} id);

		/**
		 * 查询
		 * @return
		 */
		List<${className}> query(${className}Query query);

		/**
		 * 分页查询
		 * @return
		 */
		List<${className}> queryPage(${className}Query query);

		/**
		 * 总记录数
		 * @param query
		 * @return
		 */
		int count(${className}Query query);

	<#list table.columns as column>
	<#if column.unique && !column.pk>
		/**
		 * 根据${column.columnAlias}获取
		 */
		${className} getBy${column.columnName}(${column.javaType} v);
	
	</#if>
	</#list>

}
