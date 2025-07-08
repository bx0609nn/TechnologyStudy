package com.bx.pdf;

import com.bx.entity.Student;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import jdk.internal.org.objectweb.asm.tree.MultiANewArrayInsnNode;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import static com.bx.enums.StudentSexEnum.MAN;
import static com.bx.enums.StudentSexEnum.WOMEN;

/**
 * @author lili
 * @version 1.0
 * @date 2025/1/6 16:50
 * @description 使用iText5实现打印pdf
 */
public class IText5Pdf {
    public static void main(String[] args) throws Exception {
        File file = new File("D:\\pdf\\pdfExample.pdf");
        if (!file.exists()) {
            file.createNewFile();
        }

        // 1、创建文档，无参页面大小默认为A4
        Document document1 = new Document();
        // 指定页面大小为A4
        Document document2 =new Document(PageSize.A4);
        // 指定页面大小为A4，且自定义页边距(marginLeft、marginRight、marginTop、marginBottom)
        Document document3 =new Document(PageSize.A4,50,50,30,20);

        //1、也可先指定页面的规格，再创建文档
        //1.1.1页面大小
        Rectangle rectangle = new Rectangle(PageSize.A4);
        //1.1.2页面背景颜色
        rectangle.setBackgroundColor(BaseColor.WHITE);

        //Document是iText库中用于创建PDF文档内容的类，通常与PdfDocument一起使用。
        //它用于定义文档的逻辑结构，包括段落、章节、列表、表格等高级元素，并负责组织和格式化文本内容，使其在PDF文档中呈现出合适的样式和排版；
        //还有就是它更侧重于高层次的文档内容创建，如添加段落、列表、表格、链接等

        //1.2、再创建pdf文档
        Document document = new Document(rectangle);
        //1.3、指定页边距
        document.setMargins(30,30,40,40);

        //PdfWriter类用于将文档内容写入到PDF文件中。它负责创建和管理PDF文档的输出流，并将文档内容写入到指定的PDF文件或输出流中。
        //通过PdfWriter，可以将创建的文档内容（如页面、文本、图像等）写入到PDF文件中，完成PDF文档的构建过程。

        //PdfReader类用于读取已存在的PDF文档内容，提取其中的信息并进行相应的操作。
        //通过PdfReader，可以读取文档的页面、文本、图形以及元数据信息，进行文档内容的分析和提取操作。

        //2、创建PdfWriter，通过PdfWriter将文档内容写入到pdf文档中
        //参数1：文档对象
        //参数2：文件的实际名称
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));

        //写入数据之前要打开文档
        document.open();

        //3、创建字体样式
        //参数1：字体名称或路径
        //参数2：字符编码
        //参数3：是否嵌入字体（true：将字体嵌入 PDF 文件中，使字体在没有安装字体的设备上也能正确显示。 false：不嵌入字体，文件更小，但需要系统支持字体的显示。）
//        BaseFont baseFont = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        BaseFont baseFont = BaseFont.createFont("fonts/simsun.ttc,0", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);//系统字体，其实5.0版以后的iText加入字体还是很方便的。

        //4、添加段落到pdf文档中
        //4.1创建段落内容字体的大小，粗细
        Font titleFont = new Font(baseFont, 24,Font.BOLD);
        //4.2创建段落的内容，给段落内容指定字体（itext无法支持中文字体，需要设置字体）
        Paragraph title = new Paragraph("pdf文档",titleFont);
        //4.x还可以在这里给段落指定其他样式，如对齐方式，行间距等等
        title.setAlignment(Element.ALIGN_CENTER);
        //4.3将段落内容添加到pdf文档中
        document.add(title);

        //5、添加列表到pdf文档中
        //5.1创建列表内容字体
        Font listFont = new Font(baseFont, 12, Font.NORMAL);
        //5.2创建列表
        List list = new List();
        //5.3向列表中添加内容，并指定字体
        list.add(new ListItem("C语言",listFont));
        list.add(new ListItem("C++",listFont));
        list.add(new ListItem("Java",listFont));
        list.add(new ListItem("Python",listFont));
        list.add(new ListItem("JavaScript",listFont));
        //5.4将列表添加到pdf文档中
        document.add(list);

        //6添加表格到pdf文档中
        //6.1创建表格内容字体
        Font tableHeadFont = new Font(baseFont, 10, Font.BOLD);
        Font tableBodyFont = new Font(baseFont, 9, Font.NORMAL);

        //创建一个6列的表格
        PdfPTable pdfPTable1 = new PdfPTable(4);
        //设置表格上下边距
        pdfPTable1.setSpacingBefore(10f);
        pdfPTable1.setSpacingAfter(10f);
        //定义每一列的列宽并应用至表格
        float[] widths1 = {3f,8f,2f,3f};
        pdfPTable1.setWidths(widths1);
        //获取表格的行集合
        ArrayList<PdfPRow> pdfPRows1 = pdfPTable1.getRows();
        //第一行
        PdfPCell[] pdfPCells1 = new PdfPCell[4];
        PdfPRow pdfPRow1 = new PdfPRow(pdfPCells1);
        //1-1 订单号
        pdfPCells1[0]=new PdfPCell(new Paragraph("订单号：", tableHeadFont));
        pdfPCells1[0].setHorizontalAlignment(Element.ALIGN_RIGHT);//水平向右
        pdfPCells1[0].setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
        pdfPCells1[0].setBorder(0);
        pdfPCells1[0].setColspan(1);
        //1-2 订单号值
        Chunk chunk1 = new Chunk("eb82f60a-fd81-4f26-9a53-4b30ab3c2425", tableHeadFont);
        chunk1.setUnderline(0.5f,-2f);
        pdfPCells1[1]=new PdfPCell(new Phrase(chunk1));
        pdfPCells1[1].setHorizontalAlignment(Element.ALIGN_LEFT);
        pdfPCells1[1].setVerticalAlignment(Element.ALIGN_MIDDLE);
        pdfPCells1[1].setBorder(0);
        pdfPCells1[1].setColspan(1);
        //1-34
        pdfPCells1[2]=new PdfPCell(new Paragraph(""));
        pdfPCells1[2].setBorder(0);
        pdfPCells1[2].setColspan(2);
        pdfPRows1.add(pdfPRow1);

        //第二行
        PdfPCell[] pdfPCells2 = new PdfPCell[4];
        PdfPRow pdfPRow2 = new PdfPRow(pdfPCells2);
        //2-1 客户名称
        pdfPCells2[0]=new PdfPCell(new Paragraph("客户名称：", tableHeadFont));
        pdfPCells2[0].setHorizontalAlignment(Element.ALIGN_RIGHT);
        pdfPCells2[0].setVerticalAlignment(Element.ALIGN_MIDDLE);
        pdfPCells2[0].setBorder(0);
        pdfPCells2[0].setColspan(1);
        //2-2 客户名称值（单行最大21个中文字符）
        Chunk chunk2 = new Chunk("拉萨综合保税区跨境电商有限公司demo", tableHeadFont);
        chunk2.setUnderline(0.5f,-2f);
        pdfPCells2[1]=new PdfPCell(new Phrase(chunk2));
        pdfPCells2[1].setHorizontalAlignment(Element.ALIGN_LEFT);
        pdfPCells1[1].setVerticalAlignment(Element.ALIGN_MIDDLE);
        pdfPCells2[1].setBorder(0);
        pdfPCells2[1].setColspan(1);
        //2-3 车牌号
        pdfPCells2[2]=new PdfPCell(new Paragraph("车牌号：", tableHeadFont));
        pdfPCells2[2].setHorizontalAlignment(Element.ALIGN_RIGHT);
        pdfPCells2[2].setVerticalAlignment(Element.ALIGN_MIDDLE);
        pdfPCells2[2].setBorder(0);
        pdfPCells2[2].setColspan(1);
        //2-4 车牌号值（单行最大15个英文字符）
        Chunk chunk3 = new Chunk("400100123", tableHeadFont);
        chunk3.setUnderline(0.5f,-2f);
        pdfPCells2[3]=new PdfPCell(new Phrase(chunk3));
        pdfPCells2[3].setHorizontalAlignment(Element.ALIGN_LEFT);
        pdfPCells2[3].setVerticalAlignment(Element.ALIGN_MIDDLE);
        pdfPCells2[3].setBorder(0);
        pdfPCells2[3].setColspan(1);
        pdfPRows1.add(pdfPRow2);
        document.add(pdfPTable1);



        //6.2创建表格，并指定列数
        PdfPTable pdfPTable = new PdfPTable(12);

        //6.3设置表格样式，边距，宽度等
        //设置表格上外边距
        pdfPTable.setSpacingBefore(10f);
        //设置表格下外边距
        pdfPTable.setSpacingAfter(10f);

        //设置每一列的列宽
        float[] widths = {1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f};
        //应用到表格中
        pdfPTable.setWidths(widths);

        //6.4
        //获取表格的所有行
        ArrayList<PdfPRow> listRows = pdfPTable.getRows();

        //6.5创建第一行（表头）
        //创建第一行所有列
        PdfPCell[] cells1 = new PdfPCell[12];
        //创建第一行
        PdfPRow row1 = new PdfPRow(cells1);

        //设置第一行每个单元格的内容，从第0列开始
        cells1[0] =new PdfPCell(new Paragraph("id",tableHeadFont));
        //第一个单元格跨3列
        cells1[0].setColspan(3);

        cells1[3] =new PdfPCell(new Paragraph("姓名",tableHeadFont));
        cells1[3].setColspan(3);

        cells1[6] =new PdfPCell(new Paragraph("年龄",tableHeadFont));
        cells1[6].setColspan(3);

        cells1[9] =new PdfPCell(new Paragraph("性别",tableHeadFont));
        cells1[9].setColspan(3);

        //将第一行添加到表格中
        listRows.add(row1);

        //创建第一行
        PdfPCell[] cells2 = new PdfPCell[12];
        PdfPRow row2 = new PdfPRow(cells2);

        //设置第一行每个单元格的内容，从第0列开始
        //Chunk 是不可拆分的单个文本片段，可以为 Chunk 设置字体、颜色、背景颜色、下划线、删除线等样式
        //组合多个文本样式，可以使用 Phrase 或 Paragraph
        Chunk qtyLabelChunk = new Chunk("件数: ", tableHeadFont);
        Chunk qtyValueChunk = new Chunk("2", tableHeadFont);

        // 设置下划线
        //参数1：下划线的宽度（实际上是指线条的粗细）值越大，下划线越粗
        //参数2：控制下划线相对于文本基线的垂直偏移。负值：下划线位于文本基线下方（常用）。正值：下划线会出现在文本基线上方
        qtyValueChunk.setUnderline(0.5f, -1f);

        //组合多个文本
        Phrase qtyPhrase = new Phrase();
        qtyPhrase.add(qtyLabelChunk);
        qtyPhrase.add(qtyValueChunk);

        //创建一个 PdfPCell（PDF 表格的单元格），并将 qtyPhrase 添加到这个单元格中
        //将该单元格赋值给该表格的第一个位置
        cells2[0] =new PdfPCell(qtyPhrase);
        cells2[0].setBorderWidthRight(0);//右边框粗细，为0即没有
        cells2[0].setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
        cells2[0].setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
        cells2[0].setFixedHeight(20f);//设置高度大小
        //第一个单元格跨3列
        cells2[0].setColspan(3);

        cells2[3] =new PdfPCell(new Paragraph("111",tableHeadFont));
        cells2[3].setBorderWidthLeft(0);
        cells2[3].setBorderWidthRight(0);
        cells2[3].setHorizontalAlignment(Element.ALIGN_CENTER);
        cells2[3].setVerticalAlignment(Element.ALIGN_MIDDLE);
        cells2[3].setColspan(3);

        cells2[6] =new PdfPCell(new Paragraph("111",tableHeadFont));
        cells2[6].setBorderWidthLeft(0);
        cells2[6].setBorderWidthRight(0);
        cells2[6].setHorizontalAlignment(Element.ALIGN_CENTER);
        cells2[6].setVerticalAlignment(Element.ALIGN_MIDDLE);
        cells2[6].setColspan(3);

        cells2[9] =new PdfPCell(new Paragraph("111",tableHeadFont));
        cells2[9].setBorderWidthLeft(0);
        cells2[9].setHorizontalAlignment(Element.ALIGN_CENTER);
        cells2[9].setVerticalAlignment(Element.ALIGN_MIDDLE);
        cells2[9].setColspan(3);

        //将第一行添加到表格中
        listRows.add(row2);

        Student student1 = new Student(1l, "张三", 18, MAN.getValue());
        Student student2 = new Student(2l, "李四", 20, WOMEN.getValue());
        Student student3 = new Student(3l, "王五", 32, MAN.getValue());
        Student student4 = new Student(4l, "赵六", 28, MAN.getValue());
        Student student5 = new Student(5l, "jerry", 24, WOMEN.getValue());
        java.util.List<Student> studentList = new java.util.ArrayList<>();
        studentList.add(student1);
        studentList.add(student2);
        studentList.add(student3);
        studentList.add(student4);
        studentList.add(student5);

        //6.5创建并赋值表体每一行内容
        for (Student student : studentList) {
            //表体每一行的所有列
            PdfPCell cells[] = new PdfPCell[12];
            //每一行
            PdfPRow row = new PdfPRow(cells);

            //设置每一行每个单元格的内容，从第0列开始
            cells[0] =new PdfPCell(new Paragraph(student.getId().toString(),tableBodyFont));
            //第一个单元格跨3列
            cells[0].setColspan(3);

            cells[3] =new PdfPCell(new Paragraph(student.getName(),tableBodyFont));
            cells[3].setColspan(3);

            cells[6] =new PdfPCell(new Paragraph(student.getAge().toString(),tableBodyFont));
            cells[6].setColspan(3);

            cells[9] =new PdfPCell(new Paragraph(student.getSex(),tableBodyFont));
            cells[9].setColspan(3);

            //将每一行添加到表格中
            listRows.add(row);
        }

        //6.6将表格添加到pdf文档中
        document.add(pdfPTable);

        // 关闭文档
        document.close();
        writer.close();
    }
}
