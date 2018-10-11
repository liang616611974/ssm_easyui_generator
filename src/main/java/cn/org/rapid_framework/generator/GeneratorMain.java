package cn.org.rapid_framework.generator;


import cn.org.rapid_framework.generator.core.GeneratorFacade;
import cn.org.rapid_framework.generator.core.GeneratorProperties;

/**
 * @author badqiu
 * @email badqiu(a)gmail.com
 */

public class GeneratorMain {

    private static final String OUT_ROOT_DIR = "outRoot"; // 输出目录

    private static final String BASE_PACKAGE_KEY = "basePackage";

    private static final String BASE_PACKAGE_VALUE = "com.liangfeng.study";

    private static final String MODULE_KEY = "module";

    private static final String BASE_MODULE_PACKAGE_KEY = "baseModulePackage";

    /**
     * 请直接修改以下代码调用不同的方法以执行相关生成任务.
     */
    public static void main(String[] args) throws Exception {

        // 初始化配置属性
        GeneratorProperties.setProperty(OUT_ROOT_DIR, "C:\\Users\\61661\\Desktop\\generator");
        //GeneratorProperties.setProperty(OUT_ROOT_DIR, "C:\\Users\\Administrator\\Desktop\\generator\\temp");
        GeneratorProperties.setProperty(MODULE_KEY, "goods");
        initProperties();
        GeneratorFacade g = new GeneratorFacade();
        //g.printAllTableNames();				//打印数据库中的表名称
        g.deleteOutRootDir();                            //删除生成器的输出目录
        GeneratorProperties.setProperty("tableChName", "商品");
        g.generateByTable("scd_goods", "src/main/resources/template");//通过数据库表生成文件,template为模板的根目录
//        g.generateByTable("article", "src/main/resources/template");//通过数据库表生成文件,template为模板的根目录
        //g.generateByAllTable("template");	//自动搜索数据库中的所有表并生成文件,template为模板的根目录
        //g.generateByClass(Blog.class,"template_clazz");
        //g.deleteByTable("table_name", "template"); ///删除生成的文件

        //生成代码后，打开所在的文件夹
        Runtime.getRuntime().exec("cmd.exe /c start " + GeneratorProperties.getRequiredProperty("outRoot"));
    }

    private static void initProperties() {
        GeneratorProperties.setProperty(BASE_PACKAGE_KEY, BASE_PACKAGE_VALUE);
        GeneratorProperties.setProperty(BASE_MODULE_PACKAGE_KEY, getBasePackage() + "." + GeneratorProperties.getRequiredProperty(MODULE_KEY));
        GeneratorProperties.setProperty("corePackage", getBasePackage() + ".core");
        GeneratorProperties.setProperty("baseObjPackage", getBasePackage() + ".core.framework.base");
        GeneratorProperties.setProperty("pojoPackage", getBaseModulePackage() + ".model.auto.pojo");
        GeneratorProperties.setProperty("qoPackage", getBaseModulePackage() + ".model.auto.qo");
        GeneratorProperties.setProperty("mapperPackage", getBaseModulePackage() + ".mapper");
        GeneratorProperties.setProperty("servicePackage", getBaseModulePackage() + ".service");
        GeneratorProperties.setProperty("controllerPackage", getBaseModulePackage() + ".web.controller");
        GeneratorProperties.setProperty("requestPackage", getBaseModulePackage() + ".web.request");
        GeneratorProperties.setProperty("responsePackage", getBaseModulePackage() + ".web.response");
        GeneratorProperties.setProperty("testServicePackage", getBaseModulePackage() + ".test.service");
        GeneratorProperties.setProperty("commonColumn", "cre_time,cre_user,mdf_time,mdf_user");
        GeneratorProperties.setProperty("BASE_MODULE_PACKAGE_KEY" + "_dir", getBaseModulePackage().replace('.', '/'));
    }

    private static String getBasePackage() {
        return GeneratorProperties.getRequiredProperty(BASE_PACKAGE_KEY);
    }

    private static String getBaseModulePackage() {
        return GeneratorProperties.getRequiredProperty(BASE_MODULE_PACKAGE_KEY);
    }
}
