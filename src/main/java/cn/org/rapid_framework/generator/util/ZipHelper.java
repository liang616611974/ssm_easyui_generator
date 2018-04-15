package cn.org.rapid_framework.generator.util;


import org.apache.commons.compress.archivers.zip.ZipUtil;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author Liangfeng
 * @version 1.0
 * @Title: ZipHelper 压缩工具类
 * @Description:
 * @date  2018/4/14 0014 下午 11:07
 */
public class ZipHelper {

    private static final Logger logger = LoggerFactory.getLogger(ZipHelper.class);

    private static final int BUFFER_SIZE = 2 * 1024;

    // 私有化构造函数
    private ZipHelper() {
    }

    /**
     * 压缩文件
     * @param srcDirPath
     * @param zipFilePath
     * @param KeepDirStructure 是否保留原来的目录结构
     */
    public static void toZip(String srcDirPath, String zipFilePath,boolean KeepDirStructure){
        try{
            toZip(srcDirPath,new FileOutputStream(zipFilePath),KeepDirStructure);
        }catch (Exception e){
            throw new RuntimeException("压缩文件发生异常",e);
        }
    }


    /**
     * 压缩成ZIP 方法1
     * @param srcDirPath 压缩文件夹路径
     * @param out    压缩文件输出流
     * @param KeepDirStructure  是否保留原来的目录结构,true:保留目录结构;
     * 							false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
     */
    public static void toZip(String srcDirPath, OutputStream out, boolean KeepDirStructure) {
        // 1.定义参数
        long start = System.currentTimeMillis(); // 开始时间
        ZipOutputStream zos = null ; // 压缩文件输出流
        try {
            zos = new ZipOutputStream(out);
            File sourceFile = new File(srcDirPath);
            compress(sourceFile,zos,sourceFile.getName(),KeepDirStructure);
            long end = System.currentTimeMillis();
            logger.info("压缩完成，耗时：{} ms",(end - start));
        } catch (Exception e) {
            throw new RuntimeException("压缩文件发生异常",e);
        }finally{
            if(zos != null){
                try {
                    zos.close();
                } catch (IOException e) {
                    throw new RuntimeException("关闭压缩文件输出流发生异常",e);
                }
            }
        }

    }

    /**
     * 压缩成ZIP 方法2
     * @param srcFiles 需要压缩的文件列表
     * @param out 	        压缩文件输出流
     */
    public static void toZip(List<File> srcFiles , OutputStream out){
        long start = System.currentTimeMillis();
        ZipOutputStream zos = null ;
        try {
            zos = new ZipOutputStream(out);
            for (File srcFile : srcFiles) {
                byte[] buf = new byte[BUFFER_SIZE];
                zos.putNextEntry(new ZipEntry(srcFile.getName()));
                int len;
                FileInputStream in = new FileInputStream(srcFile);
                while ((len = in.read(buf)) != -1){
                    zos.write(buf, 0, len);
                }
                zos.closeEntry();
                in.close();
            }
            long end = System.currentTimeMillis();
            logger.info("压缩完成，耗时：{} ms",(end - start));
        } catch (Exception e) {
            throw new RuntimeException("压缩文件发生异常",e);
        }finally{
            if(zos != null){
                try {
                    zos.close();
                } catch (IOException e) {
                    throw new RuntimeException("关闭压缩文件输出流发生异常",e);
                }
            }
        }
    }

    /**
     * 递归压缩方法
     *
     * @param sourceFile       源文件
     * @param zos              zip输出流
     * @param name             压缩后的名称
     * @param KeepDirStructure 是否保留原来的目录结构,true:保留目录结构;
     *                         false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
     * @throws Exception
     */
    private static void compress(File sourceFile, ZipOutputStream zos, String name,
                                 boolean KeepDirStructure) throws Exception {
        // 1.定义参数
        byte[] buf = new byte[BUFFER_SIZE];

        // 2.如果是文件
        if (sourceFile.isFile()) {
            // 向zip输出流中添加一个zip实体，构造器中name为zip实体的文件的名字
            zos.putNextEntry(new ZipEntry(name));
            // copy文件到zip输出流中
            int len;
            FileInputStream in = new FileInputStream(sourceFile);
            while ((len = in.read(buf)) != -1) {
                zos.write(buf, 0, len);
            }
            // Complete the entry
            zos.closeEntry();
            in.close();
            return;
        }

        // 3.如果是目录
        File[] listFiles = sourceFile.listFiles();
        // 3.1 空目录处理
        if (listFiles == null || listFiles.length == 0) {
            // 需要保留原来的文件结构时,需要对空文件夹进行处理
            if (KeepDirStructure) {
                // 空文件夹的处理
                zos.putNextEntry(new ZipEntry(name + "/"));
                // 没有文件，不需要文件的copy
                zos.closeEntry();
            }
            return;
        }
        // 3.2 非空目录
        for (File file : listFiles) {
            // 判断是否需要保留原来的文件结构
            if (KeepDirStructure) {
                // 注意：file.getName()前面需要带上父文件夹的名字加一斜杠,
                // 不然最后压缩包中就不能保留原来的文件结构,即：所有文件都跑到压缩包根目录下了
                compress(file, zos, name + "/" + file.getName(), KeepDirStructure);
            } else {
                compress(file, zos, file.getName(), KeepDirStructure);
            }

        }

    }

    public static void main(String[] args) {
        String srcDirPath = "C:\\Users\\Administrator\\Desktop\\generator\\temp";
        String zipFilePath = "C:\\Users\\Administrator\\Desktop\\generator\\temp.zip";
        ZipHelper.toZip(srcDirPath, zipFilePath,false);
    }


}
