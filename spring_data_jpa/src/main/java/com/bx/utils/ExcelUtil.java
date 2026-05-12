package com.bx.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lili
 * @version 1.0
 * @date 2026/4/17 16:38
 * @description Excel导出工具类
 * @use 说明：1.该工具类一个对象只能导出一种报表，但同一种报表可以按分类导出多个工作表。
 *           2.如果需要导出多种报表，请创建多个对象
 *      准备：
 *           1.标题行数组headers，数据行字段数组fieldList，列宽columnWidth，title名
 *           2.sheetName工作表名，要导出的数据集合List<Map<String, Object>> list，枚举字段转换fieldValueMap，字典类字段转换dictMap
 *      调用：
 *           1.ExcelUtil excelUtil = new ExcelUtil(columnWidth, title, headers, fieldList);
 *           2.excelUtil.export(sheetName, list);
 *           2.excelUtil.exportDict(sheetName, list, dictMap);
 *           2.excelUtil.exportFVDict(sheetName, list, fieldValueMap, dictMap);
 *           3.InputStream inputStream = excelUtil.getWorkbook();
 */
public class ExcelUtil {
    private SXSSFWorkbook workbook;// 工作簿
    private String title;// title名
    private String[] headers;// 标题行数组
    private String[] fieldList;// 字段名数组，顺序与标题行数组一一对应
    private CellStyle titleStyle;// title样式
    private CellStyle headerStyle;// 标题行样式
    private CellStyle cellStyle;// 字段名样式
    private int columnWidth;// 列宽

    /**
     * @param title     title名
     * @param headers   标题行数组
     * @param fieldList 数据行字段数组，顺序与 headers 一一对应
     * @param columnWidth 每列宽度
     */
    public ExcelUtil(int columnWidth, String title, String[] headers, String[] fieldList) {
        //创建工作簿并设置导出excel数据行数不受限制
        this.workbook = new SXSSFWorkbook(-1);
        //title
        this.title = title;
        //标题行数组
        this.headers = headers;
        //数据行字段数组
        this.fieldList = fieldList;
        //创建title样式
        this.titleStyle = createTitleStyle();
        //创建标题行样式
        this.headerStyle = createHeaderStyle();
        //创建数据行样式
        this.cellStyle = createCellStyle();
        //列宽
        this.columnWidth = columnWidth;
    }

    //创建title行样式
    private CellStyle createTitleStyle() {
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);//设置水平对齐方式为居中
        style.setVerticalAlignment(VerticalAlignment.CENTER);//设置垂直对齐方式为居中
        Font font = workbook.createFont();
        font.setBold(true);//设置字体为粗体
        font.setFontHeightInPoints((short) 16);//设置字体大小为16
        style.setFont(font);
        return style;
    }

    //创建标题行样式
    private CellStyle createHeaderStyle() {
        CellStyle style = workbook.createCellStyle();
        style.setBorderTop(BorderStyle.THIN);//设置上边框样式为细实线
        style.setBorderBottom(BorderStyle.THIN);//设置下边框样式为细实线
        style.setBorderLeft(BorderStyle.THIN);//设置左边框样式为细实线
        style.setBorderRight(BorderStyle.THIN);//设置右边框样式为细实线
        style.setAlignment(HorizontalAlignment.CENTER);//设置水平对齐方式为居中
        style.setVerticalAlignment(VerticalAlignment.CENTER);//设置垂直对齐方式为居中
        Font font = workbook.createFont();
        font.setBold(true);//设置字体为粗体
        style.setFont(font);
        return style;
    }

    //创建数据行样式
    private CellStyle createCellStyle() {
        CellStyle style = workbook.createCellStyle();
        style.setBorderTop(BorderStyle.THIN);//设置上边框样式为细实线
        style.setBorderBottom(BorderStyle.THIN);//设置下边框样式为细实线
        style.setBorderLeft(BorderStyle.THIN);//设置左边框样式为细实线
        style.setBorderRight(BorderStyle.THIN);//设置右边框样式为细实线
        style.setAlignment(HorizontalAlignment.CENTER);//设置水平对齐方式为居中
        style.setVerticalAlignment(VerticalAlignment.CENTER);//设置垂直对齐方式为居中
        return style;
    }

    /**
     * @param
     * @return SXSSFSheet 工作表
     * @description 创建工作表，写入title行和标题行，返回该Sheet
     */
    private SXSSFSheet createSheet(String sheetName) {
        //1.创建工作表
        SXSSFSheet sheet = workbook.createSheet(sheetName);
        //2.设置默认列宽
        sheet.setDefaultColumnWidth(columnWidth);

        //3.写入title行（第0行）
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, headers.length - 1));
        Row titleRow = sheet.createRow(0);
        titleRow.setHeightInPoints(35);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue(title);
        titleCell.setCellStyle(titleStyle);

        //4.写入headers行（第1行）
        Row headerRow = sheet.createRow(1);
        headerRow.setHeightInPoints(20);
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellStyle(headerStyle);
            cell.setCellValue(headers[i]);
        }
        //5.返回已写入title和标题行的工作表
        return sheet;
    }

    /**
     * @param list 数据列表
     * @description 通用导出数据行
     */
    public void export(String sheetName, List<Map<String, Object>> list) {
        this.exportFVDict(sheetName, list, new HashMap<>(), new HashMap<>());
    }

    /**
     * @param list 数据列表
     * @param dictMap 字典数据集合（Map 形式），key为字段名，value为字典类
     * @description 含有字典字段的通用导出数据行
     */
    public void exportDict(String sheetName, List<Map<String, Object>> list, Map<String, Class<?>> dictMap) {
        this.exportFVDict(sheetName, list, new HashMap<>(), dictMap);
    }

    /**
     * @param list 数据列表
     * @param fieldValueMap 字段值映射（Map 形式），key为字段名，value为Map（key为字段值，value为字段显示值）
     * @param dictMap 字典数据集合（Map 形式），key为字段名，value为字典类
     * @description 含有枚举字段和字典字段的通用导出数据行
     */
    public void exportFVDict(String sheetName, List<Map<String, Object>> list, Map<String, Map<String, String>> fieldValueMap, Map<String, Class<?>> dictMap) {
        //判空处理
        if (CollUtil.isEmpty(list)) {
            this.createSheet(sheetName);
            return;
        }
        if (fieldValueMap == null) {
            fieldValueMap = new HashMap<>();
        }
        if (dictMap == null) {
            dictMap = new HashMap<>();
        }
        //1.获取写入title和标题行的工作表
        SXSSFSheet sheet = this.createSheet(sheetName);
        //2.写入数据行
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> map = list.get(i);
            //从第2行开始写入导出数据
            Row row = sheet.createRow(i + 2);
            for (int j = 0; j < fieldList.length; j++) {
                //依次填充每行下的每一列数据
                Cell cell = row.createCell(j);
                cell.setCellStyle(cellStyle);
                //每一列的字段名
                String field = fieldList[j];
                //每一列字段名所对应的值
                Object value = map.get(field);
                //设置单元格值
                if (value == null) {
                    cell.setCellValue("");
                } else if (field.contains("Date") || field.contains("Time")) {
                    String date = String.valueOf(value);
                    cell.setCellValue(this.formatDate(date));
                } else if (fieldValueMap.containsKey(field)) {
                    String code = String.valueOf(value);
                    String name = fieldValueMap.get(field).getOrDefault(code, code);
                    cell.setCellValue(name);
                } else if (dictMap.containsKey(field)) {
                    String code = String.valueOf(value);
                    if (StrUtil.isBlank(code)) {
                        cell.setCellValue("");
                    } else {
                        Class<?> dictClass = dictMap.get(field);
//                        String name = DataDictUtil.toGetName(code, dictClass);
//                        cell.setCellValue(code + "-" + name);
                    }
                } else {
                    if (value instanceof Integer) {
                        cell.setCellValue((Integer) value);
                    } else if (value instanceof Long) {
                        cell.setCellValue((Long) value);
                    } else if (value instanceof Double) {
                        cell.setCellValue(NumberUtil.toBigDecimal(String.valueOf(value)).doubleValue());
                    } else if (value instanceof BigDecimal) {
                        cell.setCellValue(NumberUtil.toBigDecimal(String.valueOf(value)).doubleValue());
                    } else {
                        cell.setCellValue(String.valueOf(value));
                    }
                }
            }
        }
    }

    /**
     * @param
     * @return InputStream
     * @description 返回Excel数据流
     */
    public InputStream getWorkbook() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            workbook.write(baos);
        } finally {
            workbook.dispose();
            workbook.close();
        }
        return new ByteArrayInputStream(baos.toByteArray());
    }

    /**
     * @param date 日期
     * @return String
     * @description 将日期进行格式化
     */
    private String formatDate(String date) {
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date dater = dateFormat.parse(date);
            return dateFormat.format(dater);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}