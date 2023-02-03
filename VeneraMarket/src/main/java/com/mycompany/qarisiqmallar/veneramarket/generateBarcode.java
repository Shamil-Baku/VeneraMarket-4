/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qarisiqmallar.veneramarket;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import org.krysalis.barcode4j.impl.code128.Code128Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;

/**
 *
 * @author Shamil
 */
public class generateBarcode {




 public static String main(String barcode, String malinadi, String olcusu, String rengi, String qiymeti ) throws DocumentException, IOException {
//     String barcode = "1234500864";
//     String malinadi,olcusu,rengi;
//     malinadi = "Kisi Salvari";
//     olcusu = "XXL";
//     rengi = "Qara";
//     double qiymeti = 15.00;

    


        Code128Bean code128 = new Code128Bean();
        code128.setHeight(7f);
        code128.setModuleWidth(0.3);
        code128.setQuietZone(9);
        code128.doQuietZone(true);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BitmapCanvasProvider canvas = new BitmapCanvasProvider(baos, "image/x-png", 400, BufferedImage.TYPE_BYTE_BINARY, false, 0);
        code128.generateBarcode(canvas, barcode);
        canvas.finish();

//write to png file
        FileOutputStream fos = new FileOutputStream("C:\\Alis Qaimeleri\\barcode.png");
        fos.write(baos.toByteArray());
        fos.flush();
        fos.close();

//write to pdf
        Image png = Image.getInstance(baos.toByteArray());
        png.setAbsolutePosition(0, 705);
        png.scalePercent(25);

        PdfPTable table = new PdfPTable(1);
        table.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
        table.setWidthPercentage(100);
        table.addCell(png);
        table.getDefaultCell().setBorder(0);

        Font pageNumberFont = new Font(Font.FontFamily.UNDEFINED, (float) 4, Font.BOLDITALIC);
        Font pageNumberFont3 = new Font(Font.FontFamily.UNDEFINED, (float) 2.2, Font.BOLD);
        Font pageNumberFont4 = new Font(Font.FontFamily.UNDEFINED, (float) 2.5, Font.BOLD);
        Font pageNumberFont5 = new Font(Font.FontFamily.UNDEFINED, (float) 4.0, Font.BOLD);
        Document doc = new Document(new Rectangle(59.0f, 39.0f));
        doc.setMargins(0, 0, 0, 0);
       
        try {

            PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream("C:\\Alis Qaimeleri\\barcodes.pdf"));
           
          Paragraph p2 = new Paragraph("                     Venera", pageNumberFont);
            Paragraph p3 = new Paragraph("        Geyim ve Tekstil Dunyasi", pageNumberFont5);
           


            List orderlist = new List(List.ALIGN_CENTER);
            orderlist.add(new ListItem("                             Malin adi :" +" "+ malinadi, pageNumberFont3));
            orderlist.add(new ListItem("                                Ölçüsü :" +" "+ olcusu, pageNumberFont3));
            orderlist.add(new ListItem("                                 Rengi :" +" "+ rengi, pageNumberFont3));
            orderlist.add(new ListItem("                        Qiymeti :" +" "+ qiymeti, pageNumberFont4));

           doc.open();
           p2.setSpacingBefore(0.0001f);
           p2.setSpacingAfter(0.0001f);
           p3.setSpacingBefore(0.0001f);
           p3.setSpacingAfter(0.0001f);
           doc.add(p2);
           doc.add(p3);
           doc.add(orderlist);
           table.setSpacingBefore(0.0001f);
           table.setSpacingAfter(0.0001f);
           table.setHorizontalAlignment(100);


           doc.add(table);

            doc.close();
            writer.close();
        } catch (DocumentException | FileNotFoundException ex) {
            ex.printStackTrace();
        }
     return null;

    }
 public static String test2(double satisQiymeti) {

        String sss = null;

        String alis = Double.toString(satisQiymeti);
        int qiymetinuzunlugu = alis.length();

        System.out.println(qiymetinuzunlugu);

        char ss = alis.charAt(qiymetinuzunlugu - 1);

        String son = String.valueOf(ss);

        int sonInt = Integer.parseInt(son);

        System.out.println(ss);

        char ss2 = alis.charAt(qiymetinuzunlugu - 2);

        System.out.println(ss2);

        System.out.println(ss);

        if (sonInt > 0) {

            if (qiymetinuzunlugu == 3) {

                sss = satisQiymeti + "0 " + "qepik";

                System.out.println(sss);

            }
            if (qiymetinuzunlugu == 4) {

                sss = satisQiymeti + " qepik";

                System.out.println(sss);

            }

        }

        if (sonInt == 00) {

            sss = satisQiymeti + "0 " + " man";

            System.out.println(sss);

        }

        return sss;

    


    }
    
}
