package com.smart.util;

import java.awt.*;
import java.io.*;

import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;
import com.lowagie.text.pdf.BaseFont;
import org.zefer.pd4ml.PD4Constants;
import org.zefer.pd4ml.PD4ML;

/**
 * 报告单生成PDF
 * Created by zcw on 2016/9/14.
 */
public final class GenericPdfUtil {

    private GenericPdfUtil() {
    }

    /**
     * HTML转PDF
     * @param fileName  //文件名称
     * @param html      //HTML内容
     */
    public static synchronized void html2Pdf(String fileName, String html){
        File pdfFile = new File(Config.getPdfPath()+fileName);
        //System.out.println(html);
        StringReader strReader = new StringReader(html);
        try {
            FileOutputStream fos = new FileOutputStream(pdfFile);
            PD4ML pd4ml = new PD4ML();

            pd4ml.setPageInsets(new Insets(5, 5, 0, 5));
            pd4ml.setHtmlWidth(750);
            pd4ml.setPageSize(pd4ml.changePageOrientation(PD4Constants.A5));
            pd4ml.setPageInsetsMM(new Insets(5, 5, 0, 5));
            //pd4ml.fitPageVertically();
            //pd4ml.enableImgSplit(false);
            //pd4ml.disableHyperlinks();
            //pd4ml.addStyle(".result{height:6.3cm !important}",true);
            pd4ml.useTTF("java:fonts", true);
            pd4ml.setDefaultTTFs("SimSun", "Arial", "Courier New");
            pd4ml.resetAddedStyles();
            pd4ml.enableDebugInfo();
            pd4ml.render(strReader, fos);
            fos.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static synchronized void createPdf(String fileName, String html){
        OutputStream os = null;
        try {
            os = new FileOutputStream(Config.getPdfPath()+fileName);
            ITextRenderer renderer = new ITextRenderer(800,600);
            System.out.println("====>"+html);
            renderer.setDocumentFromString(html);
            //解决中文支持问题
            ITextFontResolver fontResolver = renderer.getFontResolver();
            renderer.getDotsPerPoint();
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
