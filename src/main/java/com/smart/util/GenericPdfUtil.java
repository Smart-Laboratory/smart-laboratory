package com.smart.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;
import com.lowagie.text.html.HtmlParser;
import com.lowagie.text.html.simpleparser.HTMLWorker;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.PageSize;
/**
 * Created by zcw on 2016/9/14.
 */
public final class GenericPdfUtil {

    private GenericPdfUtil() {
    }

    public static synchronized void createPdf(String fileName, String html){
        OutputStream os = null;
        try {
            os = new FileOutputStream("d:\\"+fileName);
            ITextRenderer renderer = new ITextRenderer();
            System.out.println("====>"+html);
            renderer.setDocumentFromString(html);

            //renderer.setDocument(html);
//            Document doc = new Document(PageSize.A5.rotate(),10,10,10,10);
//            PdfWriter writer =PdfWriter.getInstance(doc, new FileOutputStream("d:\\"+fileName));
//            writer.setViewerPreferences(PdfWriter.PageModeUseOutlines);
//            doc.open();
//
//            doc.add(new Paragraph(html));
//            doc.close();
            //解决中文支持问题
            ITextFontResolver fontResolver = renderer.getFontResolver();
            fontResolver.addFont("C:/WINDOWS/Fonts/SimSun.ttc",BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);// 宋体字
            fontResolver.addFont("C:/WINDOWS/Fonts/Arial.ttf",BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);// 宋体字
            // 解决图片的相对路径问题
            //BaseFont baseFont = BaseFont.createFont("STSong-Light",BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);
            //renderer.getSharedContext().setBaseURL("file:/D:/Work/Demo2do/Yoda/branch/Yoda%20-%20All/conf/template/");
            renderer.layout();
            renderer.createPDF(os);
            renderer.finishPDF();
           os.close();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(os!=null)
                try {
                    os.close();
                }catch (Exception e){
                    e.printStackTrace();
                }

        }

    }
}
