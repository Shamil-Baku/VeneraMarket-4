/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */
package com.mycompany.qarisiqmallar.veneramarket;

import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.sql.Connection;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.MediaPrintableArea;
import javax.print.attribute.standard.MediaSize;
import javax.print.attribute.standard.MediaSizeName;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter;

public class SilentPrint {

    public void printReport(JasperReport jr, String selectedPrinter, HashMap paramaters, Connection c) {

        try{
         
        JasperPrint print;
        print = JasperFillManager.fillReport(jr, paramaters, c);
        PrinterJob job = PrinterJob.getPrinterJob();
        /* Create an array of PrintServices */
        PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
        int selectedService = 0;
        /* Scan found services to see if anyone suits our needs */
        for (int i = 0; i < services.length; i++) {
            if (services[i].getName().toUpperCase().contains(selectedPrinter)) {
                /*If the service is named as what we are querying we select it */
                selectedService = i;
            }
        }
        try {
            job.setPrintService(services[selectedService]);
       

        PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
        MediaSizeName mediaSizeName = MediaSize.findMedia(4, 4, MediaPrintableArea.INCH);
        printRequestAttributeSet.add(mediaSizeName);
        printRequestAttributeSet.add(new Copies(1));

        JRPrintServiceExporter exporter = new JRPrintServiceExporter();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
        /* We set the selected service and pass it as a parameter */
        exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE,
                services[selectedService]);
        exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET,
                services[selectedService].getAttributes());
        exporter.setParameter(JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET,
                printRequestAttributeSet);
        exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG,
                Boolean.FALSE);
        exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG,
                Boolean.TRUE);
            try {
                
                
                exporter.exportReport();
                
            } catch (JRException ex) {
                Logger.getLogger(SilentPrint.class.getName()).log(Level.SEVERE, null, ex);
            }
         } catch (PrinterException ex) {
            Logger.getLogger(SilentPrint.class.getName()).log(Level.SEVERE, null, ex);
        }
        }catch(Exception ex){
            ex.printStackTrace();
        }

    }

}
