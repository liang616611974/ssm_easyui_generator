<#include "/custom.include">
<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameFirstLower = className?uncap_first>   
<#assign classNameLowerCase = className?lower_case>   
<#assign pkJavaType = table.idColumn.javaType>   

package ${basepackage}.controller;

import java.util.HashMap;
import java.util.Map;

import java.text.SimpleDateFormat;
import java.util.Date;

import javacommon.base.BaseRestSpringController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sdp.framework.page.Page;
import com.sdp.framework.web.scope.Flash;
import com.sdp.project.model.Disuser;
import com.sdp.project.vo.query.DisuserQuery;

<#include "/java_imports.include">
@Controller
@RequestMapping("/${classNameLowerCase}")
public class ${className}Controller extends BaseRestSpringController<${className},${pkJavaType}>{
	//默认多列排序,example: username desc,createTime asc
	protected static final String DEFAULT_SORT_COLUMNS = null; 
	
	private ${className}Manager ${classNameFirstLower}Manager;
	
	private final String LIST_ACTION = "redirect:/${classNameLowerCase}";
	
	@Autowired
	private HttpSession session;
	
	/** 
	 * 增加setXXXX()方法,spring就可以通过autowire自动设置对象属性,注意大小写
	 **/
	public void set${className}Manager(${className}Manager manager) {
		this.${classNameFirstLower}Manager = manager;
	}
	
	/** binder用于bean属性的设置 */
	@InitBinder  
	public void initBinder(WebDataBinder binder) {  
	        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));  
	}
	   
	/**
	 * 增加了@ModelAttribute的方法可以在本controller方法调用前执行,可以存放一些共享变量,如枚举值,或是一些初始化操作
	 */
	@ModelAttribute
	public void init(ModelMap model) {
		model.put("now", new java.sql.Timestamp(System.currentTimeMillis()));
	}
	
	/** 列表 */
	@RequestMapping(value="/index")
	public String index(ModelMap model,${className}Query query,HttpServletRequest request,HttpServletResponse response) {
		Page page = this.${classNameFirstLower}Manager.findPage(query);
		
		model.addAllAttributes(toModelMap(page, query));
		return "/${classNameLowerCase}/index";
	}
	
	
	/** 通用跳转页面 */
	@RequestMapping(value="/gl/{filename}")
	public String gl(ModelMap model,${className}Query query,@PathVariable(value = "filename") String filename,HttpServletRequest request,HttpServletResponse response) {
		return "/${classNameLowerCase}/"+filename;
	}
	
	/** ajax data */
	@RequestMapping(value="/list")
	public ModelAndView list(ModelMap model,${className}Query query,HttpServletRequest request,HttpServletResponse response) {
		Page page = this.${classNameFirstLower}Manager.findPage(query);
		Map resultMap=new HashMap();  
		resultMap.put("Rows", page.getResult());  
	    resultMap.put("Total", page.getTotalCount());  
	    
		return new ModelAndView("ajax",resultMap);
	}
	
	/** 显示 */
	@RequestMapping(value="/{id}/show")
	public String show(ModelMap model,@PathVariable ${pkJavaType} id) throws Exception {
		${className} ${classNameFirstLower} = (${className})${classNameFirstLower}Manager.getById(id);
		model.addAttribute("${classNameFirstLower}",${classNameFirstLower});
		return "/${classNameLowerCase}/show";
	}

	/** 进入新增 */
	@RequestMapping(value="/new")
	public String _new(ModelMap model,${className} ${classNameFirstLower},HttpServletRequest request,HttpServletResponse response) throws Exception {
		model.addAttribute("${classNameFirstLower}",${classNameFirstLower});
		return "/${classNameLowerCase}/new";
	}
	
	/** 保存新增,@Valid标注spirng在绑定对象时自动为我们验证对象属性并存放errors在BindingResult  */
	@RequestMapping(value="/save",method=RequestMethod.POST)
	public @ResponseBody String create(ModelMap model,@Valid ${className} ${classNameFirstLower},BindingResult errors,HttpServletRequest request,HttpServletResponse response) throws Exception {
		if(errors.hasErrors()) {
			return  "/${classNameLowerCase}/new";
		}
		Disuser user = (Disuser)session.getAttribute("user");
		if(user!=null){
			${classNameFirstLower}.setCruser(user.getUsername());
			${classNameFirstLower}.setModifieduser(user.getUsername());
		}
		${classNameFirstLower}.setCrtime(new Date());
		${classNameFirstLower}.setModifiedtime(new Date());
		${classNameFirstLower}Manager.save(${classNameFirstLower});
		//Flash.current().success(CREATED_SUCCESS); //存放在Flash中的数据,在下一次http请求中仍然可以读取数据,error()用于显示错误消息
		String n = "0";
		if(${classNameFirstLower}.getId()>0){
			n = "1";
		}
		return n;
	}
	
	/** 编辑 */
	@RequestMapping(value="/{id}/edit")
	public String edit(ModelMap model,@PathVariable ${pkJavaType} id) throws Exception {
		${className} ${classNameFirstLower} = (${className})${classNameFirstLower}Manager.getById(id);
		model.addAttribute("${classNameFirstLower}",${classNameFirstLower});
		return "/${classNameLowerCase}/edit";
	}
	
	/** 保存更新,@Valid标注spirng在绑定对象时自动为我们验证对象属性并存放errors在BindingResult  */
	@RequestMapping(value="/{id}/update")
	public @ResponseBody String update(ModelMap model,@PathVariable ${pkJavaType} id,@Valid ${className} ${classNameFirstLower},BindingResult errors,HttpServletRequest request,HttpServletResponse response) throws Exception {
		if(errors.hasErrors()) {
			return "/${classNameLowerCase}/edit";
		}
		Disuser user = (Disuser)session.getAttribute("user");
		${classNameFirstLower}.setModifiedtime(new Date());
		if(user!=null){
			${classNameFirstLower}.setModifieduser(user.getUsername());
		}
		${classNameFirstLower}Manager.update(${classNameFirstLower});
		return "1";
	}
	
	/** 删除 */
	@RequestMapping(value="/{id}/delete",method=RequestMethod.DELETE)
	public @ResponseBody String delete(ModelMap model,@PathVariable ${pkJavaType} id) {
		${classNameFirstLower}Manager.removeById(id);
		//Flash.current().success(DELETE_SUCCESS);
		return "1";
	}

	/** 批量删除 */
	@RequestMapping(value="/batchDelete")
	public ModelAndView batchDelete(ModelMap model,@RequestParam("items[]") Long[] items) {
		int j=0;
		for(int i = 0; i < items.length; i++) {
			${classNameFirstLower}Manager.removeById(items[i]);
			if(${classNameFirstLower}Manager.getById(items[i])==null){
				j++;
			}
		}
		Map resultMap=new HashMap();  
		resultMap.put("success", j);  
		return new ModelAndView("ajax",resultMap);
	}
	
}

