package com.zhou.test.bds;

import com.lowagie.text.Document;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfWriter;
import org.junit.Test;

import java.awt.*;
import java.io.FileOutputStream;

/**
 * @author eli
 * @date 2018/4/17 18:02
 */
public class PdfTest {
    @Test
    public void test01(){
        Document document = new Document(new Rectangle(0, 0, 300, 200), 0, 0, 0, 0);
        PdfWriter pdfwriter;
        try {
            pdfwriter = PdfWriter.getInstance(document, new FileOutputStream("d:/temp.pdf"));
            document.open();
            PdfContentByte pcb = pdfwriter.getDirectContent();
            Graphics2D g;
            for (int i = 0; i < 9; i++) {
                document.newPage();
                g = pcb.createGraphicsShapes(300, 200);
                g.setColor(Color.BLUE);
                g.setFont(new Font("Tahoma", Font.ITALIC, 10));
                g.drawString("http://www.hgsql.com", 20, 50);
                g.setColor(Color.ORANGE);
                g.setFont(new Font("黑体", Font.PLAIN, 100));
                g.drawString("第" + (i + 1) + "页", 20, 150);
                g.dispose();
            }
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
