package com.bx.hutool_;

import cn.hutool.core.io.FileUtil;

import java.io.File;
import java.util.Arrays;

/**
 * @author lili
 * @version 1.0
 * @date 2024/10/30 16:27
 * @description
 */
public class FileUtil_ {
    public static void main(String[] args) {

        //列出该目录下的文件和目录
        File[] files = FileUtil.ls("D:\\bajinsiFiles\\ftp");
        Arrays.stream(files).forEach(file -> {
            System.out.println(file);
        });

        //创建文件，如果父目录不存在自动创建，本机D:\files文件夹不存在，运行后成功创建files目录和ftp.txt文件
        FileUtil.touch("D:\\files\\ftp.txt");
        FileUtil.mkdir("D:\\files\\dir");

        //创建目录，逐级递归创建，本机D盘下不存在dir目录，运行后成功创建dir目录及它的子目录
        FileUtil.mkdir("D:\\dir\\dir");

//        //清空文件夹(即删除该目录里面的文件和目录,保留改目录)
//        FileUtil.clean("D:\\files");
//        FileUtil.clean("D:\\dir");

//        //删除文件或目录(递归删除，不会判断是否为空)
//        FileUtil.del("D:\\files");
//        FileUtil.del("D:\\dir");

        //获取文件扩展名，不带"."
        System.out.println("扩展名=" + FileUtil.extName("D:\\files\\ftp.txt"));

        //创建文件对象
        File file = FileUtil.file("D:\\files\\ftp.txt");

        //返回文件名
        System.out.println("文件名=" + FileUtil.getName(file));//ftp.txt

        //返回主文件名(去掉扩展名的)
        System.out.println("主文件名=" + FileUtil.getPrefix(file));//ftp

        //获取文件后缀名(即扩展名不带.的)
        System.out.println("主文件名=" + FileUtil.getSuffix(file));//txt、

        File file1 = FileUtil.file("D:\\bajinsiFiles\\ftp\\CEB509Message_zhongya_202104231421131951NSHL.xml");
        //读取文件内容
        System.out.println("FileUtil.readUtf8String(file1) = " + FileUtil.readUtf8String(file1));
    }

}
