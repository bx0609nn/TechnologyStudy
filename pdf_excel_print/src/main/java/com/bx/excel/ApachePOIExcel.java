package com.bx.excel;

import com.bx.entity.Student;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.math.BigDecimal;

/**
 * @author lili
 * @version 1.0
 * @date 2025/1/7 15:54
 * @description 使用Apache POI导出数据到Excel
 */
public class ApachePOIExcel {
    public static void main(String[] args) throws IOException {
        ClassPathResource classPathResource = new ClassPathResource("/complete/excelExample.xlsx");
        InputStream inputStream = classPathResource.getInputStream();

        //1、创建一个空的工作簿，也可以通过输入流从已有文件中读取
//        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);

        //2、创建一个工作表或者从已有模版中读取工作表
//        XSSFSheet studentSheet = workbook.createSheet("学生信息表");
        XSSFSheet studentSheet = workbook.getSheet("学生信息表");

        //获取该工作簿中工作表的个数
        int sheets = workbook.getNumberOfSheets();

        //3、创建单元格的样式，可以用来定义字体、边框、对齐方式、背景颜色等单元格的格式
        CellStyle styleForCell = workbook.createCellStyle();
        //定义对齐方式
        styleForCell.setAlignment(HorizontalAlignment.CENTER);//水平居中
        styleForCell.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中

//        //4、创建第一行（表头行，行和列的索引都是从0开始，如果是通过读取模版已有表头则应该从第二行开始赋值）
//        XSSFRow sheetRow = studentSheet.createRow(0);
//        //在第一行创建第1个单元格
//        XSSFCell cell0 = sheetRow.createCell(0);
//        //为单元格赋值
//        cell0.setCellValue("id");
          //为单元格设置样式
//        cell0.setCellStyle(styleForCell);
//        //第一行第2个单元格
//        XSSFCell cell1 = sheetRow.createCell(1);
//        cell1.setCellValue("姓名");
//        cell1.setCellStyle(styleForCell);
//
//        //第一行第3个单元格
//        XSSFCell cell2 = sheetRow.createCell(2);
//        cell2.setCellValue("年龄");
//        cell2.setCellStyle(styleForCell);
//
//        //第一行第4个单元格
//        XSSFCell cell3 = sheetRow.createCell(3);
//        cell3.setCellValue("性别");
//        cell3.setCellStyle(styleForCell);

        Student student1 = new Student(1l, "张三", 18, "男");
        Student student2 = new Student(2l, "李四", 20, "女");
        Student student3 = new Student(3l, "王五", 32, "男");
        Student student4 = new Student(4l, "赵六", 28, "男");
        Student student5 = new Student(5l, "jerry", 24, "女");
        java.util.List<Student> studentList = new java.util.ArrayList<>();
        studentList.add(student1);
        studentList.add(student2);
        studentList.add(student3);
        studentList.add(student4);
        studentList.add(student5);

        int i = 1;
        //5、创建并赋值表体每一行内容
        for (Student student : studentList) {

            //创建行
            XSSFRow row = studentSheet.createRow(i);

            for (int j = 0; j < 4; j++) {
                XSSFCell cell = row.createCell(j);
                //给该行每一个单元格设置样式
                cell.setCellStyle(styleForCell);
                //给该行每一个单元格赋值
                switch (j) {
                    case 0:
                        cell.setCellValue(String.valueOf(student.getId()));
                        break;
                    case 1:
                        cell.setCellValue(student.getName());
                        break;
                    case 2:
                        cell.setCellValue(String.valueOf(student.getAge()));
                        break;
                    case 3:
                        cell.setCellValue(student.getSex());
                        break;
                }
            }
            //进入下一行
            i++;

            //如果该工作表行达到最大行数4，则创建一个新的工作表
            if (studentSheet.getLastRowNum() == 3) {
                studentSheet = workbook.createSheet("学生信息表" + (workbook.getNumberOfSheets() - sheets + 1));
                i = 0;
            }

        }

        //获取工作表打印配置
        XSSFPrintSetup ps = studentSheet.getPrintSetup();
        //打印方向（false 表示竖向，true 表示横向）
        ps.setLandscape(false);
        //打印纸张大小
        ps.setPaperSize(XSSFPrintSetup.A4_PAPERSIZE);

        //6、写入磁盘文件中
        File file = new File("D:\\pdf\\excelExample.xlsx");
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        workbook.write(fileOutputStream);

    }
}
