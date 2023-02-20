/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qarisiqmallar.veneramarket;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Shamil
 */
public class TestClass {

    public static Connection con;
    public static PreparedStatement pres;
    public static ResultSet rs;

    public static Connection connect() {
        try {
            //Class.forName("com.mysql.jdbc.Driver");
            String url = ("jdbc:mysql://localhost:3306/mehsullar");
            String usercategoryOfProduct = ("root");
            String password = ("dxdiag92");
            con = DriverManager.getConnection(url, usercategoryOfProduct, password);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return con;

    }

    public static void doCoppyMainCategory() {

        try {

            con = connect();
            pres = con.prepareStatement("select * from mehsullar");
            rs = pres.executeQuery();

            while (rs.next()) {

                int movsumID = rs.getInt("mainCategory");

                pres = con.prepareStatement("update mehsullar set Movsum_id = ? where mainCategory = ?");
                pres.setInt(1, movsumID);
                pres.setInt(2, movsumID);
                pres.executeUpdate();

            }

            con.close();
            rs.close();
            pres.close();

        } catch (Exception ex) {

            ex.printStackTrace();
        }

    }

    public static void doCoppySubCategory() {

        try {

            con = connect();
            pres = con.prepareStatement("select * from mehsullar");
            rs = pres.executeQuery();

            while (rs.next()) {

                int movsumID = rs.getInt("subCategory");

                pres = con.prepareStatement("update mehsullar set Kateqoriya_id = ? where subCategory = ?");
                pres.setInt(1, movsumID);
                pres.setInt(2, movsumID);
                pres.executeUpdate();
            }

        } catch (Exception ex) {

            ex.printStackTrace();
        }

    }

    public static void doCoppySecondSubCategory() {

        try {

            con = connect();
            pres = con.prepareStatement("select * from mehsullar");
            rs = pres.executeQuery();

            while (rs.next()) {

                int movsumID = rs.getInt("2ndSubCategory");

                pres = con.prepareStatement("update mehsullar set Alt_kateqoriya_id = ? where 2ndSubCategory = ?");
                pres.setInt(1, movsumID);
                pres.setInt(2, movsumID);
                pres.executeUpdate();
            }

        } catch (Exception ex) {

            ex.printStackTrace();
        }

    }
    DefaultTableModel df;
    DefaultTableModel df2;

    public void identificationBarcodes() {
        DefaultListModel modelList = new DefaultListModel();
        modelList.removeAllElements();

        df = (DefaultTableModel) IdentificationOfProduuctBarcodes.tblFirstProductTable.getModel();
        df2 = (DefaultTableModel) IdentificationOfProduuctBarcodes.tblSecondProductTable.getModel();
        df.setRowCount(0);
        df2.setRowCount(0);

        String barcode = null;
        String barcode2 = null;
        String nameOfFirstProduct = null;
        String nameOfSecondProduct = null;
        int numberOfTheSameProducts = 0;
        try {
            con = connect();

            pres = con.prepareStatement("select s.Qaliq_say, s.id, s.Barcode, s.Malin_adi from mehsullar s");
            rs = pres.executeQuery();

            while (rs.next()) {

                int qaliqSay = rs.getInt("Qaliq_say");
                int idFirstProduct = rs.getInt("id");
                barcode = rs.getString("Barcode");
                nameOfFirstProduct = rs.getString("Malin_adi");

                if (barcode == null) {

                } else {

                    pres = con.prepareStatement("select * from mehsullar");
                    ResultSet rs2 = pres.executeQuery();

                    while (rs2.next()) {

                        int qaliqSay2 = rs2.getInt("Qaliq_say");
                        int idSecondProduct = rs2.getInt("id");
                        barcode2 = rs2.getString("Barcode");
                        nameOfSecondProduct = rs2.getString("Malin_adi");

                        if (barcode2 == null) {

                        } else {

                            if (barcode.equals(barcode2)) {

                                if (idFirstProduct != idSecondProduct) {
                                    numberOfTheSameProducts++;
                                    modelList.addElement("adi -" + nameOfFirstProduct + " id-si " + idFirstProduct + " olan mehsul ve adi -" + nameOfSecondProduct + " id-si " + idSecondProduct + " olan mehsul ile eyni barkoda sahibdirler");
                                    IdentificationOfProduuctBarcodes.listNameOfClients.setModel(modelList);
                                    System.out.println(nameOfFirstProduct + " id-si " + idFirstProduct + " ve " + nameOfSecondProduct + " id-si " + idSecondProduct + " eyni barkoda sahibdirler");

                                    Vector v2 = new Vector();
                                    for (int i = 0; i < 1; i++) {
                                        v2.add(nameOfFirstProduct);
                                        v2.add(idFirstProduct);
                                        v2.add(barcode);
                                        v2.add(qaliqSay);
                                    }
                                    df.addRow(v2);

                                    Vector v3 = new Vector();
                                    for (int i = 0; i < 1; i++) {
                                        v3.add(nameOfSecondProduct);
                                        v3.add(idSecondProduct);
                                        v3.add(barcode2);
                                        v3.add(qaliqSay2);
                                    }
                                    df2.addRow(v3);

                                } else {

                                }

                            }

                        }

                    }

                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        IdentificationOfProduuctBarcodes.txtNumberOfTheSameProduct.setText(Integer.toString(numberOfTheSameProducts));
    }

    public static void checkBarcode(String barcodeInput, int inputID) {

        String barcode = null;
        String barcode2 = null;
        String nameOfFirstProduct = null;
        String nameOfSecondProduct = null;
        int numberOfTheSameProducts = 0;

        try {

            con = connect();
            pres = con.prepareStatement("select s.Barcode, s.id from mehsullar s");
            rs = pres.executeQuery();

            while (rs.next()) {

                barcode2 = rs.getString("Barcode");
                int id = rs.getInt("id");

                if (barcodeInput.equals(barcode2)) {

                    if (inputID == id) {

                    } else {

                        String uniqueNumber = RandomCodeGenerator.rondomNumbersForBarcode();
                        String newBarcode = "1234500" + uniqueNumber;
                        checkBarcode(newBarcode, inputID);
                    }
                }
            }
            EditProducts.txtBarcode.setText(barcodeInput);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public static String setNewBarcode(String barcodeInput, int inputID) {

        String barcode = null;
        String newBarcode;
        String barcode2 = null;

        try {
            con = connect();
            pres = con.prepareStatement("select s.Barcode, s.id from mehsullar s");
            rs = pres.executeQuery();
            while (rs.next()) {

                barcode2 = rs.getString("Barcode");
                int id = rs.getInt("id");

                if (barcodeInput.equals(barcode2)) {
                    if (inputID == id) {
                    } else {
                        String uniqueNumber = RandomCodeGenerator.rondomNumbersForBarcode();
                        newBarcode = "1234500" + uniqueNumber;
                        setNewBarcode(newBarcode, inputID);
                        barcodeInput = newBarcode;
                    }
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return barcodeInput;
    }

    public static void createNewDate() {

        try {

            con = connect();
            pres = con.prepareStatement("select * from mehsullar");
            rs = pres.executeQuery();
            
            while (rs.next()) {                
                
                Date ss = rs.getDate("Alis_Tarixi");
                int id = rs.getInt("id");
                
                if (ss == null) {
                    
                    pres = con.prepareStatement("update mehsullar set Alis_Tarixi = ? where id = "+id);
                    pres.setString(1, "2023-01-30");
                    pres.executeUpdate();
                    System.out.println("Tarix yenilendi "+id);
                    
                }
                
                
            }
            
            
            
        } catch (Exception ex) {

            ex.printStackTrace();
            
        }

    }

public static void getAllPrinter(){
    
    PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
    
    for (int i = 0; i < services.length; i++) {
        
        String printerName = services[i].getName();
        System.out.println(printerName);
        //System.out.println(services[i]);
    }
    
}


public static void checkAllClients(){
    String clientName;
    try{
     
        con = connect();
        pres = con.prepareStatement("select * from clients");
        ResultSet rsClients = pres.executeQuery();
        
        while (rsClients.next()) {            
            clientName = rsClients.getString("NameAndSurename");
            
            pres = con.prepareStatement("select * from clients_permanently where NameAndSurename =" +"'"+clientName+"'");
            ResultSet rsClientPermanent = pres.executeQuery();
            
            if (rsClientPermanent.next()) {
                
            }else{
                System.out.println("Bu ad elave olunacaq-----> "+clientName);
            }
            
        }
        
        
    }catch(Exception ex){
        ex.printStackTrace();
    }
    
}


    public static void main(String[] args) {

        


        //getAllPrinter();
        checkAllClients();
        //String projectPath = System.getProperty("user.dir");
        //System.out.println(projectPath);
        //System.out.println("C:\\git projects\\VeneraMarket-4\\VeneraMarket\\src\\main\\java\\com\\mycompany\\qarisiqmallar\\veneramarket\\Test333.jrxml");
        //createNewDate();
        //doCoppySecondSubCategory();
        //doCoppySubCategory();
        //doCoppyMainCategory();
    }
}
