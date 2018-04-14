<#include "/custom.include">
<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameFirstLower = className?uncap_first>   
<#assign classNameLowerCase = className?lower_case>   
<#assign pkJavaType = table.idColumn.javaType>   

package ${basePackage}.${module}.dev.controller;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lf.common.Result;
import com.lf.model.admin.${className};
import com.lf.vo.query.admin.${className}Query;
import com.lf.web.controller.base.BaseController;
import com.lf.web.mvc.interceptor.token.annotation.Token;
import com.sdp.framework.page.Page;

/**
* @Title: ${className}Controller.java
* @Description:
* @author Liangfeng
* @date ${now?string("yyyy-MM-dd")}
* @version 1.0
 */
@Controller
public class ${className}Controller extends BaseController {
	
	private static final Logger logger = LoggerFactory.getLogger(${className}Controller.class );
	
	/** 列表页 */
	@Token(save=true)
	@RequestMapping(value="/${classNameFirstLower}/list.do")
	public String list(ModelMap model) {
		return  HOME_ADMIN_JSP_PATH + "/${classNameFirstLower}/${classNameFirstLower}_list";
	}
	
	/** 列表页数据 */
	@RequestMapping(value="/${classNameFirstLower}/listData.do")
	@ResponseBody
	public Map<String,Object> listData(${className}Query query,int page,int rows) {
		//1.初始化
		if(query==null){
			query = new ${className}Query();
		}
		//2.分页查询
		//设置参数
		query.setPageNumber(page);
		query.setPageSize(rows);
		query.setSortColumns("create_time desc");
		Map<String,Object> strObjs = new HashMap<String,Object>();
		try{
			Page<${className}> ${classNameFirstLower}Page = ${classNameFirstLower}Service.queryPage(query);
			strObjs.put("total", ${classNameFirstLower}Page.getAllCount());
			strObjs.put("rows", ${classNameFirstLower}Page.getResult());
		}catch (Exception e) {
			logger.error("分页查询${className}列表数据发生异常 查询参数query:{}",query,e);
		}
		//3.返回结果
		return strObjs;
	}
	
	/** 显示 */
	@RequestMapping(value="/${classNameFirstLower}/detail.do")
	public String detail(ModelMap model,Long id){
		${className} ${classNameFirstLower} = ${classNameFirstLower}Service.get(id);
		model.addAttribute("${classNameFirstLower}",${classNameFirstLower});
		return HOME_ADMIN_JSP_PATH + "/${classNameFirstLower}/${classNameFirstLower}_detail";
	}
	
	/**新增*/
	@Token(valid=true)
	@RequestMapping(value="/${classNameFirstLower}/save.do",method=RequestMethod.POST)
	@ResponseBody
	public Result<String> save(@Validated ${className} ${classNameFirstLower},BindingResult errors,HttpServletRequest request){
		// 1.初始化
		Result<String> result = new Result<String>();
		// 2.校验参数
		if (errors.hasErrors()) {
			result.setCode("9998");
			result.setMsg("提交参数错误");
			return result;
		}
		// 3.保存数据
		long id = idManager.generateId();
		${classNameFirstLower}.setId(id);
		${classNameFirstLower}.setCreateTime(new Date());
		${className} user = (${className}) request.getSession().getAttribute(ADMINUSER_SESSION_KEY);
		if (user != null) {
			${classNameFirstLower}.setCreateUser(user.getId());
		}
		try{
			${classNameFirstLower}Service.save(${classNameFirstLower});
			result.setFlag(true);
		}catch (Exception e) {
			result.setCode("9999");
			result.setMsg("保存后台用户 异常");
			logger.error("保存后台用户 异常 ${classNameFirstLower}:{}",${classNameFirstLower},e);
		}
		//4.返回结果
		return result;
	}
	
	/**编辑 */
	@RequestMapping(value="/${classNameFirstLower}/edit.do")
	@ResponseBody
	public Result<${className}> edit(Long id){
		// 1.初始化
		Result<${className}> result = new Result<${className}>();
		// 2.校验参数
		if (id==null) {
			result.setCode("9998");
			result.setMsg("提交参数错误");
			return result;
		}
		try{
			//3.查询数据
			${className} ${classNameFirstLower} = ${classNameFirstLower}Service.get(id);
			result.setData(${classNameFirstLower});
			result.setFlag(true);
		}catch (Exception e) {
			result.setCode("9999");
			result.setMsg("获取后台用户编辑信息 异常" );
			logger.error("获取后台用户编辑信息 异常 id:{}",id,e);
		}
		//4.返回结果
		return result;
	}
	
	/**修改*/
	@RequestMapping(value="/${classNameFirstLower}/modify.do", method=RequestMethod.POST)
	@ResponseBody
	public Result<String> modify(@Validated ${className} ${classNameFirstLower},BindingResult errors,HttpServletRequest request){
		// 1.初始化
		Result<String> result = new Result<String>();
		// 2.校验参数
		if (errors.hasErrors()) {
			result.setCode("9998");
			result.setMsg("参数错误");
			return result;
		}
		// 3.保存数据
		${classNameFirstLower}.setModifyTime(new Date());
		${className} user = (${className}) request.getSession().getAttribute(ADMINUSER_SESSION_KEY);
		if (user != null) {
			${classNameFirstLower}.setModifyUser(user.getId());
		}
		${classNameFirstLower}.setModifyTime(new Date());
		try{
			${classNameFirstLower}Service.modify(${classNameFirstLower});
			result.setFlag(true);
		}catch (Exception e) {
			result.setCode("9999");
			result.setMsg("修改后台用户 异常" );
			logger.error("修改后台用户 异常 ${classNameFirstLower}:{}",${classNameFirstLower},e);
		}
		//4.返回结果
		return result;
	}
	
	/** 删除 */
	@RequestMapping(value="/${classNameFirstLower}/remove")
	public @ResponseBody Result<String> remove(Long id) {
		//1.初始化
		Result<String> result = new Result<String>();
		try{
			${classNameFirstLower}Service.remove(id);
			result.setFlag(true);
		}catch (Exception e) {
			result.setCode("9999");
			result.setMsg("删除后台用户 异常" );
			logger.error("删除后台用户 异常 id:{}",id,e);
		}
		//4.返回结果
		return result;
	}
	
	
	
	
}

