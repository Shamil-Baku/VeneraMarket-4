/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qarisiqmallar.veneramarket;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.HashPrintServiceAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.PrintServiceAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.OrientationRequested;
import javax.print.attribute.standard.PrinterName;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimplePrintServiceExporterConfiguration;

/**
 *
 * @author Shamil
 */
public class SilentPrint2 {

    public void PrintReportToPrinter(JasperPrint jasperPrint, String printName, int numberCopies) throws JRException {

//Get the printers names
        PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);

//Lets set the printer name based on the registered printers driver name (you can see the printer names in the services variable at debugging) 
        String selectedPrinter = printName;
// String selectedPrinter = "\\\\S-BPPRINT\\HP Color LaserJet 4700"; // examlpe to network shared printer

        System.out.println("Number of print services: " + services.length);
        PrintService selectedService = null;

//Set the printing settings
        PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
        //printRequestAttributeSet.add(MediaSizeName.ISO_A4);
        printRequestAttributeSet.add(new Copies(numberCopies));
        if (jasperPrint.getOrientationValue() == net.sf.jasperreports.engine.type.OrientationEnum.LANDSCAPE) {
            printRequestAttributeSet.add(OrientationRequested.LANDSCAPE);
        } else {
            printRequestAttributeSet.add(OrientationRequested.PORTRAIT);
        }
        PrintServiceAttributeSet printServiceAttributeSet = new HashPrintServiceAttributeSet();
        printServiceAttributeSet.add(new PrinterName(selectedPrinter, null));

        JRPrintServiceExporter exporter = new JRPrintServiceExporter();
        SimplePrintServiceExporterConfiguration configuration = new SimplePrintServiceExporterConfiguration();
        configuration.setPrintRequestAttributeSet(printRequestAttributeSet);
        configuration.setPrintServiceAttributeSet(printServiceAttributeSet);
        configuration.setDisplayPageDialog(false);
        configuration.setDisplayPrintDialog(false);

        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setConfiguration(configuration);

//Iterate through available printer, and once matched with our <selectedPrinter>, go ahead and print!
        if (services != null && services.length != 0) {
            for (PrintService service : services) {
                String existingPrinter = service.getName();
                if (existingPrinter.equals(selectedPrinter)) {
                    selectedService = service;
                    break;
                }
            }
        }
        if (selectedService != null) {
            try {
                //Lets the printer do its magic!
                exporter.exportReport();
            } catch (Exception e) {
                System.out.println("JasperReport Error: " + e.getMessage());
            }
        } else {
            System.out.println("JasperReport Error: Printer not found!");
        }
    }

}
