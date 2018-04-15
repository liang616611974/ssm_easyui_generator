package cn.org.rapid_framework.generator.web.controller;


import cn.org.rapid_framework.generator.core.GeneratorFacade;
import cn.org.rapid_framework.generator.core.GeneratorProperties;
import cn.org.rapid_framework.generator.util.ZipHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Liangfeng
 * @version 1.0
 * @Title: generatorController
 * @Description:
 * @date  2018/4/14 0014 下午 8:52
 */
@Controller
public class generatorController {

    private static final String TEMPLATE_PATH = "src/main/resources/template";

    @RequestMapping(value = "index")
    public String index(ModelMap map) {
        map.put("title", "thymeleaf hello word");
        return "index";
    }

    @RequestMapping(value = "download")
    public void download(@RequestParam String tableName, HttpServletResponse response) throws Exception {
        downloadZip(tableName,response);
    }

    /**
     * 下载生成的源码包
     * @param tableName
     * @param response
     * @throws Exception
     */
    private synchronized void downloadZip(String tableName,HttpServletResponse response)throws Exception{
        GeneratorFacade g = new GeneratorFacade();
        //g.printAllTableNames();				//打印数据库中的表名称
        g.deleteOutRootDir();                            //删除生成器的输出目录
        g.generateByTable(tableName, TEMPLATE_PATH);//通过数据库表生成文件,template为模板的根目录
        //g.generateByAllTable("template");	//自动搜索数据库中的所有表并生成文件,template为模板的根目录
        //g.generateByClass(Blog.class,"template_clazz");
        //g.deleteByTable("table_name", "template"); ///删除生成的文件
        String outRootDir = GeneratorProperties.getProperty("outRoot");
        /** 3.设置response的header */
        response.setContentType("application/zip");
        response.setHeader("Content-Disposition", "attachment; filename=generator.zip");
        ZipHelper.toZip(outRootDir, response.getOutputStream(), true);
    }
}
