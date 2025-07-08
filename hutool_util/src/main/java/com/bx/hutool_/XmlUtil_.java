package com.bx.hutool_;

import cn.hutool.core.util.XmlUtil;
import org.springframework.util.ResourceUtils;
import org.w3c.dom.Document;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * @author lili
 * @version 1.0
 * @date 2024/9/13 16:17
 */
public class XmlUtil_ {
    //xml工具类
    public static void main(String[] args) throws FileNotFoundException {
        //将xml文件加载进来
        //1、先得到文件对象
        File file = ResourceUtils.getFile("D:\\JavaDocument\\MyBatis-Plus.pdf");
        Document document = XmlUtil.readXML(file);
    }
}
