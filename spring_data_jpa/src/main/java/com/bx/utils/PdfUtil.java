package com.bx.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.extern.slf4j.Slf4j;

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
 * @description PDF打印工具类
 * @design 设计思路：
 *   PDF单据通常由四部分组成：
 *     1. 【title区】  单据title
 *     2. 【表头区】  多行多列的 label+value 信息格
 *     3. 【表体区】  固定列结构（title+标题行+数据行），格式统一，由 export 系列方法负责
 *     4. 【签名区】  每种单据的签名列数、label 不同
 *
 *   工具类负责：构造器：赋值表体标题行、字段行属性、字体文件、表体列宽比例、Document和Writer初始化、输出流获取、字体创建、大标题写入
 *   调用方负责：表头区拼装、表体区拼装、签名区拼装
 *
 * @use 调用示例（以"商品出入库单"为例）：
 *
 *   // 1. 初始化
 *   String[] headers = {"序号", "备案序号", "商品名称", "申报数量", "申报单位", "法定数量", "法定单位", "仓位", "货架"};
 *   String[] fieldList = {"seqNo", "putrcSeqno", "gName", "gQty", "gUnit", "firstQty", "firstUnit", "repertoryPositionId", "storageRackId"};
 *   float[] columnWidths = {1f, 1.5f, 4f, 1.5f, 1.5f, 1.5f, 1.5f, 1.5f, 1.5f};
 *   PdfExportUtil util = new PdfExportUtil("商品出入库单", headers, fieldList, columnWidths, null);
 *
 *   // 2. 顶部日期/经办人行
 *   util.writeDateOperatorRow(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"), storeDocHead.getPreservedPerson());
 *
 *   // 3. 表头区
 *   // 表头表格总列数
 *   int totalCols = 12;
 *   // 第一行：入/出仓号 | 客户
 *   String[] row1Texts = {warehouseLabel, storeDocHead.getWarehouseNo(), "客户：", dataUtil.getCustomerName(storeDocHead.getCustomerCode())};
 *   //各label所跨列数，value所跨列数，加起来等于总列数totalCols
 *   int[] row1Spans = {2, 4, 2, 4};
 *
 *   // 第二行：订单号 | 账册号
 *   String[] row2Texts = {"订单号：", storeDocHead.getOrderCode(), "账册号：", storeDocHead.getBookNum()};
 *   int[] row2Spans = {2, 4, 2, 4};
 *
 *   // 第三行：仓库编码 | 出入库类型
 *   String[] row3Texts = {"仓库编码：", dataUtil.getRepertoryName(storeDocHead.getRepertoryCode()), "出入库类型：", type};
 *   int[] row3Spans = {2, 4, 2, 4};
 *   //将各行的label和value，跨列数放入总list中
 *   List<String[]> textList = Arrays.asList(row1Texts, row2Texts, row3Texts);
 *   List<int[]> colspanList = Arrays.asList(row1Spans, row2Spans, row3Spans);
 *   //创建并写入表头
 *   util.writeHeader(totalCols, textList, colspanList);
 *
 *   // 4. 表体小标题
 *   util.writeTableTitle("商品出入库单表体列表");
 *
 *   // 5. 表体数据 （把 DetailBook 列表转成 Map 列表）
 *   List<DetailBook> detailBookSet = storeDocHead.getDetailBookSet();
 *   List<Map<String, Object>> list = new ArrayList<>();
 *
 *   //写入表体数据
 *   util.export(list);
 *
 *   // 6. 签名区
 *   util.writeSignRow(new String[]{"核对人签名", "司机签名", "客户签名"});
 *
 *   // 7. 获取输入流
 *   InputStream is = util.getDocument();
 */
@Slf4j
public class PdfUtil {
    //默认字体路径
    private static final String DEFAULT_FONT_PATH = "fonts/simsun.ttc,0";

    //文档相关变量
    private Document document; //文档
    private ByteArrayOutputStream baos;//输出流
    private boolean closed = false;//文档是否已关闭

    //表体相关变量
    private String[] headers;//表体标题行数组
    private String[] fieldList;//表体字段名数组
    private float[]  columnWidths;//表体列宽比例数组

    //文档各字体变量
    private Font titleFont;//文档title字体
    private Font subTitleFont;//日期经办人字体
    private Font headerLabelFont;//表头标题字体
    private Font headerValueFont;//表头数据字体
    private Font tableTitleFont;//表体title字体
    private Font tableHeadFont;//表体标题字体
    private Font tableBodyFont;//表体数据字体
    private Font signFont;//签名人字体
    private Font underlineFont;//下划线字体

    /**
     * @param title        文档title
     * @param headers      表体标题行数组
     * @param fieldList    表体字段名数组，顺序与 headers 一一对应
     * @description 文档默认字体和表体列宽的构造器(需传入文档title，表体标题行，表体字段行)
     */
    public PdfUtil(String title, String[] headers, String[] fieldList) throws DocumentException, IOException {
        this(title, headers, fieldList, null, DEFAULT_FONT_PATH);
    }

    /**
     * @param title        文档title
     * @param headers      表体标题行数组
     * @param fieldList    表体字段名数组，顺序与 headers 一一对应
     * @param columnWidths 表体列宽比例数组，null 则等宽
     * @param fontPath     中文字体路径
     * @description 文档自定义字体和表体自定义列宽的构造器(需传入文档title，表体标题行，表体字段行，表体各列宽数组，字体路径)
     */
    public PdfUtil(String title, String[] headers, String[] fieldList, float[] columnWidths, String fontPath) throws DocumentException, IOException {

        if (headers.length != fieldList.length) { //校验表体标题行数组和字段名数组长度
            throw new RuntimeException("headers 和 fieldList 长度必须一致");
        }
        if (columnWidths != null && columnWidths.length != headers.length) { //校验列宽比例数组长度
            throw new RuntimeException("columnWidths 长度必须与 headers 一致");
        }

        this.headers = headers;
        this.fieldList = fieldList;
        if (columnWidths != null) { //有传列宽则用传入值，否则生成等宽数组
            this.columnWidths = columnWidths;
        } else {
            this.columnWidths = buildEqualWidths(headers.length);
        }
        if (StrUtil.isBlank(fontPath)) {
            fontPath = DEFAULT_FONT_PATH;
        }

        // 初始化输出流和文档（A4，左右上下边距 36/36/36/36）
        this.baos = new ByteArrayOutputStream();
        this.document = new Document(PageSize.A4, 36, 36, 36, 36); //创建A4文档，设置左/右/上/下页边距
        PdfWriter.getInstance(document, baos); //将文档与输出流绑定，后续document的内容都会写入baos
        document.open(); //打开文档，写入内容之前调用

        // 初始化所有字体
        BaseFont bf = BaseFont.createFont(fontPath, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED); //加载中文字体文件，IDENTITY_H表示横排Unicode编码，NOT_EMBEDDED表示不内嵌字体到PDF（减小文件体积）
        this.titleFont = new Font(bf, 18, Font.BOLD); //文档title字体：18号粗体
        this.subTitleFont = new Font(bf, 10, Font.BOLD); //日期/经办人行字体：10号粗体
        this.headerLabelFont = new Font(bf, 10, Font.BOLD); //表头区label字体：10号粗体
        this.headerValueFont = new Font(bf, 10, Font.NORMAL); //表头区value字体：10号常规
        this.tableTitleFont = new Font(bf, 11, Font.BOLD); //表体title字体：11号粗体
        this.tableHeadFont = new Font(bf, 10, Font.BOLD); //表体列标题字体：10号粗体
        this.tableBodyFont = new Font(bf, 9, Font.NORMAL); //表体数据行字体：9号常规
        this.signFont = new Font(bf, 11, Font.BOLD); //签名区字体：11号粗体
        this.underlineFont = new Font(bf, 12, Font.BOLD);//下划线字体：12号粗体

        // 写入文档title
        this.writeTitle(title);
    }

    /**
     * @param title 文档title
     * @return
     * @description 写入文档title
     */
    private void writeTitle(String title) throws DocumentException {
        Paragraph paragraph = new Paragraph(title, titleFont); //创建文档title段落
        paragraph.setAlignment(Element.ALIGN_CENTER); //设置段落水平居中
        paragraph.setSpacingAfter(6f); //设置段落下方间距为6pt
        document.add(paragraph); //将文档title段落写入文档
    }

    /**
     * @param date     日期
     * @param operator 经办人
     * @description 通用写入"日期：xxx    经办人：xxx"行
     */
    public void writeDateOperatorRow(String date, String operator) throws DocumentException {
        PdfPTable table = new PdfPTable(new float[]{5f, 5f}); //创建两列等宽的无边框表格，左列放日期，右列放经办人
        table.setWidthPercentage(100); //表格宽度占满整个页面可用宽度
        table.setSpacingAfter(6f); //表格下方留4pt间距

        PdfPCell left = new PdfPCell(new Phrase("日期：" + toStr(date), subTitleFont)); //创建左侧单元格，内容为"日期：+日期值"
        left.setBorder(0); //去掉单元格边框
        left.setHorizontalAlignment(Element.ALIGN_LEFT); //左侧内容左对齐
        left.setPaddingLeft(30f); //左内边距30pt
        table.addCell(left); //将单元格加入表格

        PdfPCell right = new PdfPCell(new Phrase("经办人：" + toStr(operator), subTitleFont)); //创建右侧单元格，内容为"经办人：+经办人名"
        right.setBorder(0); //去掉单元格边框
        right.setHorizontalAlignment(Element.ALIGN_RIGHT); //右侧内容右对齐，与左侧日期形成两端分布效果
        right.setPaddingRight(30f); //右内边距30pt
        table.addCell(right);//将单元格加入表格

        document.add(table); //写入文档
    }

    /**
     * @param totalCols    表头表格总列数
     * @param textsList    每行文字内容
     * @param colspansList 每行各格跨列数，与 textsList 一一对应
     *
     */
    public void writeHeader(int totalCols, List<String[]> textsList, List<int[]> colspansList) throws DocumentException {
        if (CollUtil.isEmpty(textsList)) {
            return;
        }
        if (CollUtil.isEmpty(colspansList) || textsList.size() != colspansList.size()) {
            throw new RuntimeException("textsList 和 colspansList 长度必须一致");
        }
        // 创建表头表格
        PdfPTable table = new PdfPTable(totalCols);
        table.setWidthPercentage(100);

        // 填充表头所有行，依次遍历 textsList 中的每一行数据
        for (int rowIndex = 0; rowIndex < textsList.size(); rowIndex++) {
            // 取出当前行的文字内容和跨列数
            String[] texts = textsList.get(rowIndex);
            int[] colspans = colspansList.get(rowIndex);

            // 校验该行的 texts 和 colspans 长度是否一致
            if (texts.length != colspans.length) {
                throw new RuntimeException("第 " + rowIndex + " 行的 texts 和 colspans 长度不一致");
            }

            // 遍历本行的每一个单元格
            for (int cellIndex = 0; cellIndex < texts.length; cellIndex++) {
                boolean isLabel = (cellIndex % 2 == 0);//奇数是label，偶数是value。因为cellIndex从0开始，所以奇数单元格的索引是偶数，偶数单元格的索引是奇数
                Font textFont = isLabel ? headerLabelFont : headerValueFont;//根据索引判断当前格是label还是value，选择字体
                String text = toStr(texts[cellIndex]);//取出当前格文字内容
                PdfPCell cell = new PdfPCell(new Phrase(text, textFont)); //用当前格文字内容和字体创建单元格
                cell.setFixedHeight(22f);//固定行高22pt，保持表头各行高度一致
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);//单元格内容水平居中
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);//单元格内容垂直居中
                cell.setColspan(colspans[cellIndex]);
                table.addCell(cell);
            }

        }
        document.add(table);
    }

    /**
     * @param tableTitle 表体title
     * @return void
     * @description 写入表体的居中title
     */
    public void writeTableTitle(String tableTitle) throws DocumentException {
        PdfPTable table = new PdfPTable(1); //创建1列的表格
        table.setWidthPercentage(100); //表格宽度占满页面
        PdfPCell cell = new PdfPCell(new Paragraph(tableTitle, tableTitleFont)); //用表头字体（粗体）创建标题单元格
        cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中对齐
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中对齐
        cell.setFixedHeight(22f); //固定行高22pt
        table.addCell(cell); //将表体标题段落加入表格
        document.add(table); //将表体标题段落写入文档
    }

    /**
     * @param list 表体数据列表
     * @return void
     * @description 通用写入表体数据行
     */
    public void export(List<Map<String, Object>> list) throws DocumentException {
        exportFVDict(list, new HashMap<>(), new HashMap<>());
    }

    /**
     * @param list 表体数据列表
     * @param dictMap 字典数据集合（Map 形式），key为字段名，value为字典类
     * @return void
     * @description 含字典字段的通用写入表体数据行
     */
    public void exportDict(List<Map<String, Object>> list, Map<String, Class<?>> dictMap) throws DocumentException {
        exportFVDict(list, new HashMap<>(), dictMap);
    }

    /**
     * @param list 表体数据列表
     * @param fieldValueMap 字段值映射（Map 形式），key为字段名，value为Map（key为字段值，value为字段显示值）
     * @param dictMap 字典数据集合（Map 形式），key为字段名，value为字典类
     * @return void
     * @description 含枚举字段和字典字段的通用写入表体数据行
     */
    public void exportFVDict(List<Map<String, Object>> list, Map<String, Map<String, String>> fieldValueMap, Map<String, Class<?>> dictMap) throws DocumentException {
        if (CollUtil.isEmpty(list)) {
            return;
        }
        if (fieldValueMap == null) {
            fieldValueMap = new HashMap<>();
        }
        if (dictMap == null) {
            dictMap = new HashMap<>();
        }

        PdfPTable table = new PdfPTable(headers.length); //按表体标题创建表格
        table.setWidthPercentage(100); //表格宽度占满页面
        table.setWidths(columnWidths); //按构造时传入的列宽比例数组设置各列宽度

        //表体标题行
        for (String header : headers) {
            PdfPCell cell = new PdfPCell(new Paragraph(header, tableHeadFont)); //用表头字体（粗体）创建标题单元格
            cell.setHorizontalAlignment(Element.ALIGN_CENTER); //标题列居中对齐
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //标题列垂直居中
            cell.setFixedHeight(20f); //固定行高20pt
            table.addCell(cell); //将标题单元格加入表格
        }

        // 表体数据行
        for (Map<String, Object> map : list) { //遍历每一条数据行
            for (String field : fieldList) {//遍历每一列字段名
                Object value = map.get(field);
                String text;
                if (value == null) {
                    text = "";
                } else if (fieldValueMap.containsKey(field)) {
                    String code = String.valueOf(value);
                    text = fieldValueMap.get(field).getOrDefault(code, code);
                } else if (field.contains("Date") || field.contains("Time")) {
                    text = formatDate(String.valueOf(value));
                } else if (dictMap.containsKey(field)) {
                    String code = String.valueOf(value);
                    if (StrUtil.isBlank(code)) {
                        text = "";
                    } else {
                        text = code + "-" /*+ DataDictUtil.toGetName(code, dictMap.get(field))*/; //"1-入库"
                    }
                } else if (value instanceof Double || value instanceof BigDecimal) {
                    text = String.valueOf(NumberUtil.toBigDecimal(String.valueOf(value)).doubleValue());
                } else {
                    text = String.valueOf(value);
                }
                PdfPCell cell = new PdfPCell(new Paragraph(text, tableBodyFont)); //用数据行文字内容和字体创建单元格
                cell.setHorizontalAlignment(Element.ALIGN_CENTER); //数据列居中对齐
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //数据列垂直居中
                cell.setFixedHeight(20f); //固定行高20pt
                table.addCell(cell); //将数据单元格加入表格
            }
        }
        document.add(table);
    }

    /**
     * @param signLabels 签名列数组 {"核对人签名：", "司机签名："}
     * @return void
     * @description 写入签名行
     */
    public void writeSignRow(String[] signLabels) throws DocumentException {
        PdfPTable table = new PdfPTable(signLabels.length); //按签名列数创建表格
        table.setWidthPercentage(100); //表格宽度占满页面
        table.setSpacingBefore(30f); //签名区上方留30pt间距，与表体拉开距离

        for (String label : signLabels) { //遍历每个签名列
            Phrase phrase = new Phrase(); //创建复合文字片段，用于在同一单元格内组合label和下划线
            phrase.add(new Chunk(label, signFont)); //添加签名label文字，如"核对人签名 ："
            phrase.add(new Chunk("_______________", underlineFont));
            PdfPCell cell = new PdfPCell(phrase); //将复合文字片段放入单元格
            cell.setBorder(0); //去掉单元格边框，签名区不需要表格线
            cell.setHorizontalAlignment(Element.ALIGN_CENTER); //签名内容居中对齐
            table.addCell(cell); //将签名单元格加入表格
        }
        document.add(table); //将签名行表格写入文档
    }

    /**
     * @param
     * @return InputStream 输入流
     * @description 关闭文档并返回PDF输入流，调用后，工具类对象不可再使用
     */
    public InputStream getDocument() {
        if (!closed) { //防止重复关闭，只有第一次调用时才执行关闭操作
            document.close(); //关闭文档，触发iText将所有内容最终写入baos
            closed = true; //标记为已关闭，防止后续误操作
        }
        return new ByteArrayInputStream(baos.toByteArray()); //将写入baos中的PDF字节数组包装成输入流返回给调用方
    }

    /**
     * @param totalCols 表格总列数
     * @return PdfPTable 表格
     * @description 根据总列数创建一个空表格
     */
    public PdfPTable createTable(int totalCols) {
        PdfPTable table = new PdfPTable(totalCols); //按指定列数创建表格
        table.setWidthPercentage(100); //表格宽度占满页面
        return table; //返回空白表格
    }

    /**
     * @param table    表格
     * @param texts    每一行的各单元格文字内容，长度 = 本行单元格数(传入2个参数表示该行2个单元格，传入4个参数表示该行4个单元格，依此类推)
     * @param colspans 每一个单元格所跨列数，长度与 texts 一致，各单元格所跨列数之和必须等于所创建的表格table的列数
     * @return void
     * @description    向表格中追加一行
     */
    public void addTableRow(PdfPTable table, String[] texts, int[] colspans) {
        // 校验该行的texts 和 colspans 长度是否一致
        if (texts == null || colspans == null || texts.length != colspans.length) {
            throw new RuntimeException("texts 和 colspans 长度不一致");
        }
        for (int i = 0; i < texts.length; i++) { //遍历该行的每一个单元格
            boolean isLabel = (i % 2 == 0); //奇数是label列，偶数是value列。因为i从0开始，所以奇数单元格的索引是偶数，偶数单元格的索引是奇数
            String text = toStr(texts[i]); //取出当前格文字内容
            Font textFont = isLabel ? headerLabelFont : headerValueFont;
            PdfPCell cell = new PdfPCell(new Phrase(text, textFont)); //用当前格文字内容和字体创建单元格
            cell.setHorizontalAlignment(Element.ALIGN_CENTER); //单元格内容水平居中
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //单元格内容垂直居中
            cell.setColspan(colspans[i]); //设置该单元格跨列数
            cell.setFixedHeight(22f); //固定行高22pt，保持表头各行高度一致
            table.addCell(cell); //将单元格加入表格
        }
    }

    /**
     * @param element 元素
     * @return void
     * @description 向文档中添加元素
     */
    public void writeElement(Element element) throws DocumentException {
        document.add(element);
    }

    /**
     * @param count 列数
     * @return float[] 等宽数组
     * @description 根据列数创建一个等宽数组
     */
    private float[] buildEqualWidths(int count) {
        float[] widths = new float[count]; //创建与列数等长的float数组
        for (int i = 0; i < count; i++) {
            widths[i] = 1f; //每列宽度都设为1f，iText会按等比例分配各列宽度
        }
        return widths; //返回等宽数组
    }

    /**
     * @param date 日期字符串
     * @return String 格式化后的日期字符串
     * @description 格式化日期字符串
     */
    private String formatDate(String date) {
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date dater = dateFormat.parse(date);
            return dateFormat.format(dater);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * @param s 字符串
     * @return String 字符串
     * @description 将字符串转换为字符串，如果字符串为null则返回""
     */
    private String toStr(String s) {
        return s == null ? "" : s;
    }

}
