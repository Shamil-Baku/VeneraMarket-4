/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qarisiqmallar.veneramarket;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

/**
 *
 * @author Shamil
 */
public class GetProductsFromExcelFileToPurchaseInvoice1 {

    static Connection con;
    static PreparedStatement pres;
    static ResultSet rs;

    static int qvozdika = 11;
    static int zelen = 19;
    static int liliya = 73;
    static int yoxla = 0;
    static int rowCount = 12;
    static int rowCountMehsullar = 12;
    static int idProduct = 0;
    public static int say;
    public static int say2;
    public static int sayFOrBosluq = 0;

    public static void main(String[] args) {

        try {
            if (say == 0) {

                con = connect();
                pres = con.prepareStatement("truncate table purchaseinvoice");
                pres.executeUpdate();

                pres = con.prepareStatement("truncate table autoinsertofpurchaseinvoice");
                pres.executeUpdate();

                pres = con.prepareStatement("truncate table totalsumforautoinsert");
                pres.executeUpdate();

                getProductsFromPurchaseInvoice();
                idendificationOfProduct();

            }

            if (say == 1) {
                con = connect();
                pres = con.prepareStatement("truncate table purchaseinvoice");
                pres.executeUpdate();
                getProductsFromPurchaseInvoice();
                idendificationOfProduct();
                copyToTheMainTable();
            } else {
                copyToTheMainTable();
            }

        } catch (Exception ex) {
            ex.printStackTrace();

        }
    }

    public static Connection connect() {
        try {

            //Class.forName("com.mysql.jdbc.Driver");
            String url = ("jdbc:mysql://localhost:3306/database_Tacir");
            String usercategoryOfProduct = ("root");
            String password = ("dxdiag92");
            con = DriverManager.getConnection(url, usercategoryOfProduct, password);

        } catch (Exception ex) {

        }

        return con;

    }

    public static int findProductId() {
        int id = 0;
        try {
            con = connect();
            pres = con.prepareStatement("select c.* from mehsullar c order by id desc limit 1");
            rs = pres.executeQuery();
            rs.next();
            id = rs.getInt("id");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return id;
    }

    public static void getAllPossitions() throws IOException {

        JFileChooser openFileChooser = new JFileChooser();
        openFileChooser.setDialogTitle("Open File");
        openFileChooser.removeChoosableFileFilter(openFileChooser.getFileFilter());
        FileFilter filter = new FileNameExtensionFilter("Excel file (.xls)", "xls");
        openFileChooser.setFileFilter(filter);
        if (openFileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File inputFile = openFileChooser.getSelectedFile();
            try ( FileInputStream in = new FileInputStream(inputFile)) {

                HSSFWorkbook importedFile = new HSSFWorkbook(in);
                HSSFSheet sheet1 = importedFile.getSheetAt(0);

                Iterator<Row> rowIterator = sheet1.iterator();
                while (rowIterator.hasNext()) {
                    Row row = rowIterator.next();
                    Iterator<Cell> cellIterator = row.cellIterator();
                    while (cellIterator.hasNext()) {
                        Cell cell = cellIterator.next();

                        if (cell.getColumnIndex() == 1) {
                            String data = cell.getStringCellValue();
                            if (data.contains("ГВОЗДИКА")) {

                                if (qvozdika == 0) {

                                    System.out.println(data);
                                    con = connect();
                                    pres = con.prepareStatement("insert into category (categories) values (?)");
                                    pres.setString(1, data);
                                    pres.executeUpdate();
                                    qvozdika++;
                                    System.out.println("Oldu bu iw qaqaw sen deyendi");
                                }
                            }

                            if (data.contains("    ..,ЗЕЛЕНЬ")) {

                                System.out.println(data);
                                con = connect();
                                pres = con.prepareStatement("insert into category (categories) values (?)");
                                pres.setString(1, data);
                                pres.executeUpdate();
                                qvozdika++;
                                System.out.println("Oldu bu iw qaqaw sen deyendi");

                            }

                        } else {

                            if (cell.getColumnIndex() == 1) {

                                rowCount = cell.getRowIndex();
                                if (rowCount == 12) {
                                    if (rowCount <= 18) {
                                        String data = cell.getStringCellValue();
                                        if (data.contains("Гвоздика")) {
                                            String cat = "    ..,ГВОЗДИКА";
                                            System.out.println("Girdik burada qaqaw");
                                            pres = con.prepareStatement("select c.* from category c where categories = " + cat);
                                            rs = pres.executeQuery();
                                            rs.next();
                                            int id2 = rs.getInt("id");

                                            pres = con.prepareStatement("insert into mehsullar (Malin_adi, mainCategory) values (?, ?)");
                                            pres.setString(1, data);
                                            pres.setInt(2, id2);
                                            pres.executeUpdate();

                                            if (cell.getColumnIndex() == 2) {
                                                int index = cell.getRowIndex();
                                                double qiymet = cell.getNumericCellValue();
                                                if (index == 12) {
                                                    if (index <= 18) {
                                                        idProduct = findProductId();
                                                        pres = con.prepareStatement("update mehsullar set Alis_qiymeti = ? where id = " + idProduct);
                                                        pres.setDouble(1, qiymet);
                                                        pres.executeUpdate();
                                                        System.out.println(qiymet);
                                                        idProduct++;

                                                    }
                                                }

                                            }

                                        }

                                    }
                                }

                            }

                        }

                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void getProductsFromPurchaseInvoice() {

        int sayAdUcun = 1;
        int sayForNumberOfProduct = 1;
        int sayForPriceOfProduct = 1;
        int sayForTotalPriceOfProduct = 1;
        int sayForComentary = 0;
        File inputFile = null;
        JFileChooser openFileChooser = new JFileChooser();
        openFileChooser.setCurrentDirectory(new File("C:\\Копия Приходная накладная"));
        openFileChooser.setDialogTitle("Open File");
        openFileChooser.removeChoosableFileFilter(openFileChooser.getFileFilter());
        FileFilter filter = new FileNameExtensionFilter("Excel file (.xls)", "xls");
        openFileChooser.setFileFilter(filter);
        if (openFileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            try {
                inputFile = openFileChooser.getSelectedFile();
                try ( FileInputStream in = new FileInputStream(inputFile)) {

                    HSSFWorkbook importedFile = new HSSFWorkbook(in);
                    HSSFSheet sheet1 = importedFile.getSheetAt(0);

                    Iterator<Row> rowIterator = sheet1.iterator();
                    while (rowIterator.hasNext()) {
                        Row row = rowIterator.next();
                        Iterator<Cell> cellIterator = row.cellIterator();
                        while (cellIterator.hasNext()) {
                            Cell cell = cellIterator.next();

                            if (cell.getColumnIndex() == 1) {

                                int rowIndex = cell.getRowIndex();

                                try {

                                    if (rowIndex >= 9) {
                                        //String data = cell.getStringCellValue();
                                        double value = cell.getNumericCellValue();
                                        if (value == 0) {
                                            System.out.println("deyer 0-a beraberdir");
                                        } else {
                                            if (say == 0) {
                                                con = connect();
                                                pres = con.prepareStatement("insert into autoInsertOfPurchaseInvoice (say) values(?)");
                                                pres.setDouble(1, value);
                                                pres.executeUpdate();
                                                System.out.println("Bu index nomresidir..." + rowIndex);
                                                System.out.println("Bu ise idexdeki deyerdi..." + value);

                                            } else {
                                                try {

                                                    con = connect();
                                                    pres = con.prepareStatement("select * from autoInsertOfPurchaseInvoice order by say desc");
                                                    rs = pres.executeQuery();
                                                    rs.next();

                                                    say2 = rs.getInt("say");
                                                    pres = con.prepareStatement("insert into autoInsertOfPurchaseInvoice (say) values(?)");
                                                    pres.setInt(1, say2 + 1);
                                                    pres.executeUpdate();

                                                } catch (Exception ex) {
                                                    say2 = 1;
                                                    pres = con.prepareStatement("insert into autoInsertOfPurchaseInvoice (say) values(?)");
                                                    pres.setDouble(1, say2);
                                                    pres.executeUpdate();
                                                }

                                            }
                                        }

                                    }

                                } catch (Exception ex) {

                                }

                            }
                            if (cell.getColumnIndex() == 3) {

                                int rowIndex = cell.getRowIndex();

                                if (rowIndex >= 9) {
                                    String value = cell.getStringCellValue();
                                    if (value.equals("")) {

                                    } else {

                                        if (say == 0) {
                                            con = connect();
                                            pres = con.prepareStatement("update autoInsertOfPurchaseInvoice set productName=? where say= " + sayAdUcun);
                                            pres.setString(1, value);
                                            pres.executeUpdate();
                                            sayAdUcun++;
                                        } else {

                                            con = connect();
                                            pres = con.prepareStatement("select * from autoInsertOfPurchaseInvoice order by say desc");
                                            rs = pres.executeQuery();
                                            rs.next();

                                            say2 = rs.getInt("say");

                                            con = connect();
                                            pres = con.prepareStatement("update autoInsertOfPurchaseInvoice set productName=? where say= " + say2);
                                            pres.setString(1, value);
                                            pres.executeUpdate();
                                        }

                                    }

                                    if (value.equals("")) {

                                    } else {

                                        System.out.println("Bu index nomresidir..." + rowIndex);
                                        System.out.println("Bu ise idexdeki deyerdi..." + value);
                                    }

                                }

                            }
                            if (cell.getColumnIndex() == 30) {

                                int rowIndex = cell.getRowIndex();

                                if (rowIndex >= 9) {
                                    double value2 = cell.getNumericCellValue();
                                    if (value2 == 0) {

                                        sayFOrBosluq++;

                                    }
                                    if (say2 == 0) {
                                        double value = cell.getNumericCellValue();

                                        if (value == 0) {

                                        } else {
                                            con = connect();
                                            pres = con.prepareStatement("update autoInsertOfPurchaseInvoice set numberOfProduct=? where say= " + sayForNumberOfProduct);
                                            pres.setDouble(1, value);
                                            pres.executeUpdate();
                                            sayForNumberOfProduct++;
                                            System.out.println("Bu index nomresidir..." + rowIndex);
                                            System.out.println("Bu ise idexdeki deyerdi..." + value);
                                        }

                                    } else {

                                        if (sayFOrBosluq > 0) {

                                        } else {
                                            con = connect();
                                            pres = con.prepareStatement("select * from autoInsertOfPurchaseInvoice order by say desc");
                                            rs = pres.executeQuery();
                                            rs.next();

                                            say2 = rs.getInt("say");

                                            double value = cell.getNumericCellValue();

                                            if (value == 0) {
                                                sayFOrBosluq++;
                                            } else {
                                                con = connect();
                                                pres = con.prepareStatement("update autoInsertOfPurchaseInvoice set numberOfProduct=? where say= " + say2);
                                                pres.setDouble(1, value);
                                                pres.executeUpdate();
                                                sayForNumberOfProduct++;
                                                System.out.println("Bu index nomresidir..." + rowIndex);
                                                System.out.println("Bu ise idexdeki deyerdi..." + value);
                                            }

                                        }

                                    }

                                }

                            }
                            if (cell.getColumnIndex() == 36) {

                                int rowIndex = cell.getRowIndex();

                                if (rowIndex >= 9) {

                                    if (say == 0) {

                                        double value = cell.getNumericCellValue();

                                        if (value == 0) {
                                            sayFOrBosluq++;
                                        } else {
                                            con = connect();
                                            pres = con.prepareStatement("update autoInsertOfPurchaseInvoice set priceOfSale=? where say= " + sayForPriceOfProduct);
                                            pres.setDouble(1, value);
                                            pres.executeUpdate();
                                            sayForPriceOfProduct++;
                                            System.out.println("Bu index nomresidir..." + rowIndex);
                                            System.out.println("Bu ise idexdeki deyerdi..." + value);
                                        }

                                    } else {

                                        con = connect();
                                        pres = con.prepareStatement("select * from autoInsertOfPurchaseInvoice order by say desc");
                                        rs = pres.executeQuery();
                                        rs.next();

                                        say2 = rs.getInt("say");

                                        double value = cell.getNumericCellValue();

                                        if (value == 0) {
                                            sayFOrBosluq++;
                                        } else {

                                            if (sayFOrBosluq > 0) {

                                            } else {

                                                con = connect();
                                                pres = con.prepareStatement("update autoInsertOfPurchaseInvoice set priceOfSale=? where say= " + say2);
                                                pres.setDouble(1, value);
                                                pres.executeUpdate();
                                                sayForPriceOfProduct++;

                                                System.out.println("Bu index nomresidir..." + rowIndex);
                                                System.out.println("Bu ise idexdeki deyerdi..." + value);

                                            }

                                        }

                                    }

                                }

                            }

                            if (cell.getColumnIndex() == 37) {

                                int rowIndex = cell.getRowIndex();

                                if (sayForComentary > 0) {

                                } else {

                                    if (rowIndex == 5) {
                                        
                                        String data=null;
                                        try {
                                             data = cell.getStringCellValue();

                                            con = connect();
                                            pres = con.prepareStatement("insert into totalsumforautoinsert (commentary) values(?)");
                                            pres.setString(1, data);
                                            pres.executeUpdate();

                                        } catch (Exception ex) {
                                            ex.printStackTrace();

                                            int dataInt = (int) cell.getNumericCellValue();
                                            String data2 = Double.toString(dataInt);

                                            try {
                                                con = connect();
                                                pres = con.prepareStatement("insert into totalsumforautoinsert (commentary) values(?)");
                                                pres.setString(1, data2);
                                                pres.executeUpdate();

                                            } catch (Exception ex2) {

                                                ex2.printStackTrace();
                                            }

                                        }

                                        if (data == "") {
                                            sayForComentary++;
                                        }
                                    }

                                }

                            }

                            if (cell.getColumnIndex() == 38) {

                                int rowIndex = cell.getRowIndex();

                                try {

                                    if (rowIndex >= 9) {

                                        if (say == 0) {

                                            double value = cell.getNumericCellValue();

                                            if (value == 0) {

                                                sayFOrBosluq++;

                                            } else {

                                                if (sayFOrBosluq > 0) {

                                                } else {

                                                    con = connect();
                                                    pres = con.prepareStatement("update autoInsertOfPurchaseInvoice set total=?, PriceOfBuy = ? where say= " + sayForTotalPriceOfProduct);
                                                    pres.setDouble(1, value);
                                                    pres.setDouble(2, value);
                                                    pres.executeUpdate();
                                                    sayForTotalPriceOfProduct++;
                                                    System.out.println("Bu index nomresidir..." + rowIndex);
                                                    System.out.println("Bu ise idexdeki deyerdi..." + value);

                                                }

                                            }

                                        } else {

                                            con = connect();
                                            pres = con.prepareStatement("select * from autoInsertOfPurchaseInvoice order by say desc");
                                            rs = pres.executeQuery();
                                            rs.next();

                                            say2 = rs.getInt("say");

                                            double value = cell.getNumericCellValue();

                                            if (value == 0) {
                                                sayFOrBosluq++;
                                            } else {

                                                if (sayFOrBosluq > 0) {

                                                } else {

                                                    con = connect();
                                                    pres = con.prepareStatement("update autoInsertOfPurchaseInvoice set total=? where say= " + say2);
                                                    pres.setDouble(1, value);
                                                    pres.executeUpdate();
                                                    sayForTotalPriceOfProduct++;
                                                    System.out.println("Bu index nomresidir..." + rowIndex);
                                                    System.out.println("Bu ise idexdeki deyerdi..." + value);

                                                }

                                            }

                                        }

                                    }

                                } catch (Exception ex) {

                                }

                            }
//                        if (cell.getColumnIndex() == 38) {
//
//                            int rowIndex = cell.getRowIndex();
//
//                            try {
//
//                                if (rowIndex == 22) {
//                                    double value = cell.getNumericCellValue();
//                                    System.out.println("bu umumi meblegdir..."+ value);
//                                    con = connect();
//                                    pres = con.prepareStatement("insert into totalsumforautoinsert (total) values(?)");
//                                    pres.setDouble(1, value);
//                                    pres.executeUpdate();
//                                }
//
//                            } catch (Exception ex) {
//
//                            }
//
//                        }
                            if (cell.getColumnIndex() == 38) {

                                try {
                                    int rowIndex = cell.getRowIndex();
                                    String data = cell.getStringCellValue();

                                    String value = cell.getStringCellValue();

                                    String ss = value.substring(9, 12);
                                    double convertToDouble = Double.parseDouble(ss);

                                    System.out.println("Bu dollar kursudur..." + convertToDouble);
                                    con = connect();
                                    pres = con.prepareStatement("update totalsumforautoinsert set kursDollar=? where id = ?");
                                    pres.setDouble(1, convertToDouble);
                                    pres.setDouble(2, 1);
                                    pres.executeUpdate();

                                } catch (Exception ex) {

                                    //ex.printStackTrace();
                                    System.out.println("Bu olmadi qaqaw");
                                }

                            }

                        }

                    }

                } catch (Exception ex) {

                    ex.printStackTrace();
                }
                File sourceFile = inputFile;
                String sourceFile2 = inputFile.toString();
                String fileName = sourceFile2.substring(32);
                File destinationFile = new File("C:\\Копия Расходная накладная\\" + fileName);
                File destinationFile2 = new File("C:\\Главная Расходная накладная\\" + fileName);
                File destinationFile3 = new File("C:\\Главная Приходная накладная\\" + fileName);
                Files.copy(sourceFile.toPath(), destinationFile.toPath(), StandardCopyOption.COPY_ATTRIBUTES);
                Files.copy(sourceFile.toPath(), destinationFile2.toPath(), StandardCopyOption.COPY_ATTRIBUTES);
                Files.copy(sourceFile.toPath(), destinationFile3.toPath(), StandardCopyOption.COPY_ATTRIBUTES);
                File deleteFile = inputFile;
                deleteFile.delete();
            } catch (IOException ex) {

                ex.printStackTrace();
                File deleteFile = inputFile;
                deleteFile.delete();
            }
        }
    }

    public static void idendificationOfProduct() {

        int sayForId = 0;
        String name = null;
        String name2;
        int numberOfProduct2;

        try {
            con = connect();
            pres = con.prepareStatement("select * from autoInsertOfPurchaseInvoice");
            ResultSet rs2 = pres.executeQuery();

            while (rs2.next()) {

                name = rs2.getString("productName");
                int say555 = rs2.getInt("say");
                numberOfProduct2 = rs2.getInt("numberOfProduct");

                if (say == 1) {
                    findTheSameItems(name, say555, numberOfProduct2);
                }

                pres = con.prepareStatement("SELECT * FROM `mehsullar` where Malin_adi  =" + "'"+ name + "'");
                rs = pres.executeQuery();
                rs.next();
                int id = rs.getInt("id");
                pres = con.prepareStatement("update autoInsertOfPurchaseInvoice set id = ? where productName = " + "'" + name + "'");
                pres.setInt(1, id);
                pres.executeUpdate();
                System.out.println("Mehsulun id-si beraberdir = " + id);
            }

        } catch (Exception ex) {
            //ex.printStackTrace();
            try {
                con = connect();
                pres = con.prepareStatement("insert into mehsullar (Malin_adi, mainCategory, Miqdari, Alis_qiymeti, Satis_miqdari, Qaliq_say) values(?,?,?,?,?,?)");
                pres.setString(1,name);
                pres.setInt(2, 4);
                pres.setInt(3, 0);
                pres.setDouble(4, 0.0);
                pres.setDouble(5, 0.0);
                pres.setDouble(6, 0.0);
                pres.executeUpdate();

                //setProductCategory category = new setProductCategory();
                //category.txtProductName.setText(name);
                //category.setVisible(true);

            } catch (Exception ex2) {

                //ex2.printStackTrace();
            }

            idendificationOfProduct();
            //System.out.println(ex);
        }

    }

    public static void findTheSameItems(String name, int say, int numberOfProduct2) {

        try {
            con = connect();
            pres = con.prepareStatement("select * from autoInsertOfPurchaseInvoice");
            ResultSet rs3 = pres.executeQuery();

            while (rs3.next()) {
                String name2 = rs3.getString("productName");
                int say2 = rs3.getInt("say");

                if (name.equals(name2)) {

                    if (say == say2) {

                    } else {
                        if (say < say2) {

                            con = connect();
                            pres = con.prepareStatement("select * from autoInsertOfPurchaseInvoice where say = " + say2);
                            rs = pres.executeQuery();
                            while (rs.next()) {
                                int numberProduct = rs.getInt("numberOfProduct");
                                double price = rs.getDouble("priceOfSale");
                                pres = con.prepareStatement("delete from autoInsertOfPurchaseInvoice where say =  " + say2);
                                pres.executeUpdate();

                                pres = con.prepareStatement("update autoInsertOfPurchaseInvoice set numberOfProduct = numberOfProduct + " + numberProduct + " where say = " + say);
                                pres.executeUpdate();

                                int total = numberProduct + numberOfProduct2;

                                con = connect();
                                pres = con.prepareStatement("update autoInsertOfPurchaseInvoice set total = " + price + " * " + total + " where say = " + say);
                                pres.executeUpdate();

                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void copyToTheMainTable() {

        try {

            con = connect();
            pres = con.prepareStatement("select * from autoInsertOfPurchaseInvoice");
            rs = pres.executeQuery();

            while (rs.next()) {
                int say = rs.getInt("say");
                int id = rs.getInt("id");
                String name = rs.getString("productName");
                int numberOfProduct = rs.getInt("numberOfProduct");
                double priceOfSale = rs.getDouble("priceOfSale");
                double total = rs.getDouble("total");
                String kimden = rs.getString("kimden");
                String priceOfBuy = rs.getString("PriceOfBuy");
                //double priceOfBuy = rs.getDouble("PriceOfBuy");

                pres = con.prepareStatement("insert into purchaseinvoice (say, id, productName, numberOfProduct, priceOfSale, total, kimden, PriceOfBuy) values(?,?,?,?,?,?,?,?)");
                pres.setInt(1, say);
                pres.setInt(2, id);
                pres.setString(3, name);
                pres.setInt(4, numberOfProduct);
                pres.setDouble(5, priceOfSale);
                pres.setDouble(6, total);
                pres.setString(7, kimden);
                pres.setString(8, priceOfBuy);
                //pres.setDouble(8, priceOfBuy);
                pres.executeUpdate();

            }

        } catch (Exception ex) {

            ex.printStackTrace();

        }

    }

    public static void getAllProductsFromExcelFile() {

        int theLastId = 0;
        JFileChooser openFileChooser = new JFileChooser();
        openFileChooser.setDialogTitle("Open File");
        openFileChooser.removeChoosableFileFilter(openFileChooser.getFileFilter());
        FileFilter filter = new FileNameExtensionFilter("Excel file (.xls)", "xls");
        openFileChooser.setFileFilter(filter);
        if (openFileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File inputFile = openFileChooser.getSelectedFile();
            try ( FileInputStream in = new FileInputStream(inputFile)) {

                HSSFWorkbook importedFile = new HSSFWorkbook(in);
                HSSFSheet sheet1 = importedFile.getSheetAt(0);

                Iterator<Row> rowIterator = sheet1.iterator();
                while (rowIterator.hasNext()) {
                    Row row = rowIterator.next();
                    Iterator<Cell> cellIterator = row.cellIterator();
                    while (cellIterator.hasNext()) {
                        Cell cell = cellIterator.next();

                        if (cell.getColumnIndex() == 1) {
                            int rowIndex2 = cell.getRowIndex();

                            if (rowIndex2 <= 10) {

                                System.out.println("Ozudiki var");

                            } else {
                                String data = cell.getStringCellValue();
                                int id = cell.getRowIndex();
                                String name = cell.getStringCellValue();
                                if (id <= 3868) {
                                    if (id == qvozdika) {

                                        if (data.contains("    ..,ГВОЗДИКА")) {

                                            con = connect();
                                            pres = con.prepareStatement("insert into category (categories) values (?)");
                                            pres.setString(1, data);
                                            pres.executeUpdate();

                                        }
                                    }
                                    if (id == zelen) {

                                        if (cell.getStringCellValue().contains("    ..,ЗЕЛЕНЬ")) {
                                            con = connect();
                                            pres = con.prepareStatement("insert into category (categories) values (?)");
                                            pres.setString(1, data);
                                            pres.executeUpdate();
                                        }
                                    }

                                    if (id == liliya) {

                                        if (cell.getStringCellValue().contains("    ..,ЛИЛИЯ")) {
                                            con = connect();
                                            pres = con.prepareStatement("insert into category (categories) values (?)");
                                            pres.setString(1, data);
                                            pres.executeUpdate();
                                        }
                                    }
                                    String ad = cell.getStringCellValue();
                                    double rownum = cell.getRowIndex();
                                    if (cell.getStringCellValue().contains("    ..,РАЗНОЕ")) {
                                        con = connect();
                                        pres = con.prepareStatement("insert into category (categories) values (?)");
                                        pres.setString(1, data);
                                        pres.executeUpdate();
                                    }
                                    if (cell.getStringCellValue().contains("    ..,РОЗА ,МЕСТНОЕ")) {
                                        con = connect();
                                        pres = con.prepareStatement("insert into category (categories) values (?)");
                                        pres.setString(1, data);
                                        pres.executeUpdate();
                                    }
                                    if (cell.getStringCellValue().contains("    ..,РОЗА ,НА ВОДЕ")) {
                                        con = connect();
                                        pres = con.prepareStatement("insert into category (categories) values (?)");
                                        pres.setString(1, data);
                                        pres.executeUpdate();
                                    }
                                    if (cell.getStringCellValue().contains("    ..,СУХОЦВЕТЫ")) {
                                        con = connect();
                                        pres = con.prepareStatement("insert into category (categories) values (?)");
                                        pres.setString(1, data);
                                        pres.executeUpdate();
                                    }
                                    if (cell.getStringCellValue().contains("    ...,СИЛИКОНОВЫЕ ЦВЕТЫ")) {
                                        con = connect();
                                        pres = con.prepareStatement("insert into category (categories) values (?)");
                                        pres.setString(1, data);
                                        pres.executeUpdate();
                                    }
                                    if (cell.getStringCellValue().equals("    ..РОЗА ,КУСТОВАЯ НА ВОДЕ")) {
                                        con = connect();
                                        pres = con.prepareStatement("insert into category (categories) values (?)");
                                        pres.setString(1, data);
                                        pres.executeUpdate();
                                    }
                                    if (cell.getStringCellValue().equals("    ..РОЗА .ПОДМОСКВА")) {
                                        con = connect();
                                        pres = con.prepareStatement("insert into category (categories) values (?)");
                                        pres.setString(1, data);
                                        pres.executeUpdate();
                                    }
                                    if (cell.getStringCellValue().equals("    ..РОЗА .ПОДМОСКВА КУСТ")) {
                                        con = connect();
                                        pres = con.prepareStatement("insert into category (categories) values (?)");
                                        pres.setString(1, data);
                                        pres.executeUpdate();
                                    }
                                    if (cell.getStringCellValue().equals("    ..РОЗА КЕНИЯ")) {
                                        con = connect();
                                        pres = con.prepareStatement("insert into category (categories) values (?)");
                                        pres.setString(1, data);
                                        pres.executeUpdate();
                                    }
                                    if (cell.getStringCellValue().equals("    ..РОЗА КЕНИЯ КУСТОВАЯ")) {
                                        con = connect();
                                        pres = con.prepareStatement("insert into category (categories) values (?)");
                                        pres.setString(1, data);
                                        pres.executeUpdate();
                                    }
                                    if (cell.getStringCellValue().equals("    ..РОЗА КОЛУМБИЯ")) {
                                        con = connect();
                                        pres = con.prepareStatement("insert into category (categories) values (?)");
                                        pres.setString(1, data);
                                        pres.executeUpdate();
                                    }
                                    if (cell.getStringCellValue().equals("    ..РОЗА ЧЕХОВСКИЙ САД")) {
                                        con = connect();
                                        pres = con.prepareStatement("insert into category (categories) values (?)");
                                        pres.setString(1, data);
                                        pres.executeUpdate();
                                    }
                                    if (cell.getStringCellValue().equals("    ..РОЗА ЭКВАДОР")) {
                                        con = connect();
                                        pres = con.prepareStatement("insert into category (categories) values (?)");
                                        pres.setString(1, data);
                                        pres.executeUpdate();
                                    }
                                    if (cell.getStringCellValue().equals("    ..ТЮЛЬПАНЫ")) {
                                        con = connect();
                                        pres = con.prepareStatement("insert into category (categories) values (?)");
                                        pres.setString(1, data);
                                        pres.executeUpdate();
                                    }
                                    if (cell.getStringCellValue().equals("    ..ХРИЗАНТЕМА")) {
                                        con = connect();
                                        pres = con.prepareStatement("insert into category (categories) values (?)");
                                        pres.setString(1, data);
                                        pres.executeUpdate();
                                    }
                                    if (cell.getStringCellValue().equals("    .КОМНАТНЫЕ РАСТЕНИЯ")) {
                                        con = connect();
                                        pres = con.prepareStatement("insert into category (categories) values (?)");
                                        pres.setString(1, data);
                                        pres.executeUpdate();
                                    }
                                    if (cell.getStringCellValue().equals("    .УПАКОВКА")) {
                                        con = connect();
                                        pres = con.prepareStatement("insert into category (categories) values (?)");
                                        pres.setString(1, data);
                                        pres.executeUpdate();
                                    }
                                    if (cell.getStringCellValue().contains("    ВАЗЫ И КЕРАМИКА")) {
                                        con = connect();
                                        pres = con.prepareStatement("insert into category (categories) values (?)");
                                        pres.setString(1, data);
                                        pres.executeUpdate();
                                    }
                                    if (cell.getStringCellValue().contains("    ГРУНТЫ И УДОБРЕНИЯ")) {
                                        con = connect();
                                        pres = con.prepareStatement("insert into category (categories) values (?)");
                                        pres.setString(1, data);
                                        pres.executeUpdate();
                                    }
                                    if (cell.getStringCellValue().contains("    КОРЗИНА")) {
                                        con = connect();
                                        pres = con.prepareStatement("insert into category (categories) values (?)");
                                        pres.setString(1, data);
                                        pres.executeUpdate();
                                    }
                                    if (cell.getStringCellValue().contains("    МЯГКАЯ ИГРУШКА")) {
                                        con = connect();
                                        pres = con.prepareStatement("insert into category (categories) values (?)");
                                        pres.setString(1, data);
                                        pres.executeUpdate();
                                    }
                                    if (cell.getStringCellValue().contains("    ОТКРЫТКИ")) {
                                        con = connect();
                                        pres = con.prepareStatement("insert into category (categories) values (?)");
                                        pres.setString(1, data);
                                        pres.executeUpdate();
                                    }
                                    if (cell.getStringCellValue().contains("    ТАРА")) {
                                        con = connect();
                                        pres = con.prepareStatement("insert into category (categories) values (?)");
                                        pres.setString(1, data);
                                        pres.executeUpdate();
                                    }

                                    int rowIndex = cell.getRowIndex();
                                    String name2 = cell.getStringCellValue();
                                    if (rowIndex <= 18) {
                                        if (rowIndex == 11) {
                                            System.out.println("Bura 73-cu celldi");
                                        } else {
                                            String category = "    ..,ГВОЗДИКА";
                                            con = connect();
                                            pres = con.prepareStatement("select * from category where categories = " + "'" + category + "'");
                                            rs = pres.executeQuery();
                                            rs.next();
                                            int idCat = rs.getInt("id");
                                            con = connect();
                                            pres = con.prepareStatement("insert into mehsullar (Malin_adi, mainCategory) values (?,?)");
                                            pres.setString(1, name);
                                            pres.setInt(2, idCat);
                                            pres.executeUpdate();
                                        }
                                    } else {
                                        int rowIndex22 = cell.getRowIndex();
                                        String name22 = cell.getStringCellValue();
                                        if (rowIndex <= 72) {
                                            String category = "    ..,ЗЕЛЕНЬ";
                                            if (rowIndex == 19) {
                                                System.out.println("Bura 73-cu celldi");
                                            } else {
                                                con = connect();
                                                pres = con.prepareStatement("select * from category where categories = " + "'" + category + "'");
                                                rs = pres.executeQuery();
                                                rs.next();
                                                int idCat = rs.getInt("id");

                                                if (yoxla == 0) {
                                                    con = connect();
                                                    pres = con.prepareStatement("insert into mehsullar (Malin_adi, mainCategory) values (?,?)");
                                                    pres.setString(1, name2);
                                                    pres.setInt(2, idCat);
                                                    pres.executeUpdate();
                                                }
                                            }
                                        } else {

                                            rowIndex22 = cell.getRowIndex();
                                            name22 = cell.getStringCellValue();
                                            if (rowIndex <= 75) {
                                                if (rowIndex == 73) {
                                                    System.out.println("Bura 73-cu celldi");
                                                } else {
                                                    if (rowIndex > 73) {
                                                        String category = "    ..,ЛИЛИЯ";
                                                        con = connect();
                                                        pres = con.prepareStatement("select * from category where categories = " + "'" + category + "'");
                                                        rs = pres.executeQuery();
                                                        rs.next();
                                                        int idCat = rs.getInt("id");

                                                        if (yoxla == 0) {

                                                            con = connect();
                                                            pres = con.prepareStatement("insert into mehsullar (Malin_adi, mainCategory) values (?,?)");
                                                            pres.setString(1, name2);
                                                            pres.setInt(2, idCat);
                                                            pres.executeUpdate();

                                                        }

                                                    }

                                                }

                                            }
                                            rowIndex22 = cell.getRowIndex();
                                            name22 = cell.getStringCellValue();
                                            if (rowIndex <= 244) {
                                                if (rowIndex == 76) {
                                                    System.out.println("Bura 73-cu celldi");
                                                } else {
                                                    if (rowIndex > 76) {
                                                        String category = "    ..,РАЗНОЕ";
                                                        con = connect();
                                                        pres = con.prepareStatement("select * from category where categories = " + "'" + category + "'");
                                                        rs = pres.executeQuery();
                                                        rs.next();
                                                        int idCat = rs.getInt("id");

                                                        if (yoxla == 0) {

                                                            con = connect();
                                                            pres = con.prepareStatement("insert into mehsullar (Malin_adi, mainCategory) values (?,?)");
                                                            pres.setString(1, name2);
                                                            pres.setInt(2, idCat);
                                                            pres.executeUpdate();

                                                        }

                                                    }

                                                }

                                            }
                                            rowIndex22 = cell.getRowIndex();
                                            name22 = cell.getStringCellValue();
                                            if (rowIndex <= 246) {
                                                if (rowIndex == 245) {
                                                    System.out.println("Bura 73-cu celldi");
                                                } else {
                                                    if (rowIndex > 245) {
                                                        String category = "    ..,РОЗА ,МЕСТНОЕ";
                                                        con = connect();
                                                        pres = con.prepareStatement("select * from category where categories = " + "'" + category + "'");
                                                        rs = pres.executeQuery();
                                                        rs.next();
                                                        int idCat = rs.getInt("id");

                                                        if (yoxla == 0) {

                                                            con = connect();
                                                            pres = con.prepareStatement("insert into mehsullar (Malin_adi, mainCategory) values (?,?)");
                                                            pres.setString(1, name2);
                                                            pres.setInt(2, idCat);
                                                            pres.executeUpdate();

                                                        }

                                                    }

                                                }

                                            }
                                            rowIndex22 = cell.getRowIndex();
                                            name22 = cell.getStringCellValue();
                                            if (rowIndex <= 258) {
                                                if (rowIndex == 247) {
                                                    System.out.println("Bura 73-cu celldi");
                                                } else {
                                                    if (rowIndex > 247) {
                                                        String category = "    ..,РОЗА ,НА ВОДЕ";
                                                        con = connect();
                                                        pres = con.prepareStatement("select * from category where categories = " + "'" + category + "'");
                                                        rs = pres.executeQuery();
                                                        rs.next();
                                                        int idCat = rs.getInt("id");

                                                        if (yoxla == 0) {

                                                            con = connect();
                                                            pres = con.prepareStatement("insert into mehsullar (Malin_adi, mainCategory) values (?,?)");
                                                            pres.setString(1, name2);
                                                            pres.setInt(2, idCat);
                                                            pres.executeUpdate();

                                                        }

                                                    }

                                                }

                                            }
                                            rowIndex22 = cell.getRowIndex();
                                            name22 = cell.getStringCellValue();
                                            if (rowIndex <= 353) {
                                                if (rowIndex == 259) {
                                                    System.out.println("Bura 73-cu celldi");
                                                } else {
                                                    if (rowIndex > 259) {
                                                        String category = "    ..,СУХОЦВЕТЫ";
                                                        con = connect();
                                                        pres = con.prepareStatement("select * from category where categories = " + "'" + category + "'");
                                                        rs = pres.executeQuery();
                                                        rs.next();
                                                        int idCat = rs.getInt("id");

                                                        if (yoxla == 0) {

                                                            con = connect();
                                                            pres = con.prepareStatement("insert into mehsullar (Malin_adi, mainCategory) values (?,?)");
                                                            pres.setString(1, name2);
                                                            pres.setInt(2, idCat);
                                                            pres.executeUpdate();

                                                        }

                                                    }

                                                }

                                            }
                                            rowIndex22 = cell.getRowIndex();
                                            name22 = cell.getStringCellValue();
                                            if (rowIndex <= 634) {
                                                if (rowIndex == 354) {
                                                    System.out.println("Bura 73-cu celldi");
                                                } else {
                                                    if (rowIndex > 354) {

                                                        String category = "    ...,СИЛИКОНОВЫЕ ЦВЕТЫ";
                                                        con = connect();
                                                        pres = con.prepareStatement("select * from category where categories = " + "'" + category + "'");
                                                        rs = pres.executeQuery();
                                                        rs.next();
                                                        int idCat = rs.getInt("id");

                                                        if (yoxla == 0) {

                                                            con = connect();
                                                            pres = con.prepareStatement("insert into mehsullar (Malin_adi, mainCategory) values (?,?)");
                                                            pres.setString(1, name2);
                                                            pres.setInt(2, idCat);
                                                            pres.executeUpdate();

                                                        }
                                                    }

                                                }

                                            }
                                            rowIndex22 = cell.getRowIndex();
                                            name22 = cell.getStringCellValue();
                                            if (rowIndex <= 838) {
                                                if (rowIndex == 635) {
                                                    System.out.println("Bura 73-cu celldi");
                                                } else {
                                                    if (rowIndex > 635) {

                                                        String category = "    ..РОЗА ,КУСТОВАЯ НА ВОДЕ";
                                                        con = connect();
                                                        pres = con.prepareStatement("select * from category where categories = " + "'" + category + "'");
                                                        rs = pres.executeQuery();
                                                        rs.next();
                                                        int idCat = rs.getInt("id");

                                                        if (yoxla == 0) {

                                                            con = connect();
                                                            pres = con.prepareStatement("insert into mehsullar (Malin_adi, mainCategory) values (?,?)");
                                                            pres.setString(1, name2);
                                                            pres.setInt(2, idCat);
                                                            pres.executeUpdate();

                                                        }
                                                    }

                                                }

                                            }
                                            rowIndex22 = cell.getRowIndex();
                                            name22 = cell.getStringCellValue();
                                            if (rowIndex <= 844) {
                                                if (rowIndex == 839) {
                                                    System.out.println("Bura 73-cu celldi");
                                                } else {
                                                    if (rowIndex > 839) {

                                                        String category = "    ..РОЗА .ПОДМОСКВА";
                                                        con = connect();
                                                        pres = con.prepareStatement("select * from category where categories = " + "'" + category + "'");
                                                        rs = pres.executeQuery();
                                                        rs.next();
                                                        int idCat = rs.getInt("id");

                                                        if (yoxla == 0) {

                                                            con = connect();
                                                            pres = con.prepareStatement("insert into mehsullar (Malin_adi, mainCategory) values (?,?)");
                                                            pres.setString(1, name2);
                                                            pres.setInt(2, idCat);
                                                            pres.executeUpdate();

                                                        }
                                                    }

                                                }

                                            }
                                            rowIndex22 = cell.getRowIndex();
                                            name22 = cell.getStringCellValue();
                                            if (rowIndex <= 851) {
                                                if (rowIndex == 845) {
                                                    System.out.println("Bura 73-cu celldi");
                                                } else {
                                                    if (rowIndex > 845) {

                                                        String category = "    ..РОЗА .ПОДМОСКВА КУСТ";
                                                        con = connect();
                                                        pres = con.prepareStatement("select * from category where categories = " + "'" + category + "'");
                                                        rs = pres.executeQuery();
                                                        rs.next();
                                                        int idCat = rs.getInt("id");

                                                        if (yoxla == 0) {

                                                            con = connect();
                                                            pres = con.prepareStatement("insert into mehsullar (Malin_adi, mainCategory) values (?,?)");
                                                            pres.setString(1, name2);
                                                            pres.setInt(2, idCat);
                                                            pres.executeUpdate();

                                                        }
                                                    }

                                                }

                                            }
                                            rowIndex22 = cell.getRowIndex();
                                            name22 = cell.getStringCellValue();
                                            if (rowIndex <= 941) {
                                                if (rowIndex == 852) {
                                                    System.out.println("Bura 73-cu celldi");
                                                } else {
                                                    if (rowIndex > 852) {

                                                        String category = "    ..РОЗА КЕНИЯ";
                                                        con = connect();
                                                        pres = con.prepareStatement("select * from category where categories = " + "'" + category + "'");
                                                        rs = pres.executeQuery();
                                                        rs.next();
                                                        int idCat = rs.getInt("id");

                                                        if (yoxla == 0) {

                                                            con = connect();
                                                            pres = con.prepareStatement("insert into mehsullar (Malin_adi, mainCategory) values (?,?)");
                                                            pres.setString(1, name2);
                                                            pres.setInt(2, idCat);
                                                            pres.executeUpdate();

                                                        }
                                                    }

                                                }

                                            }
                                            rowIndex22 = cell.getRowIndex();
                                            name22 = cell.getStringCellValue();
                                            if (rowIndex <= 1009) {
                                                if (rowIndex == 943) {
                                                    System.out.println("Bura 73-cu celldi");
                                                } else {
                                                    if (rowIndex > 943) {

                                                        String category = "    ..РОЗА КЕНИЯ КУСТОВАЯ";
                                                        con = connect();
                                                        pres = con.prepareStatement("select * from category where categories = " + "'" + category + "'");
                                                        rs = pres.executeQuery();
                                                        rs.next();
                                                        int idCat = rs.getInt("id");

                                                        if (yoxla == 0) {

                                                            con = connect();
                                                            pres = con.prepareStatement("insert into mehsullar (Malin_adi, mainCategory) values (?,?)");
                                                            pres.setString(1, name2);
                                                            pres.setInt(2, idCat);
                                                            pres.executeUpdate();

                                                        }
                                                    }

                                                }

                                            }
                                            rowIndex22 = cell.getRowIndex();
                                            name22 = cell.getStringCellValue();
                                            if (rowIndex <= 1012) {
                                                if (rowIndex == 1010) {
                                                    System.out.println("Bura 73-cu celldi");
                                                } else {
                                                    if (rowIndex > 1010) {

                                                        String category = "    ..РОЗА КОЛУМБИЯ";
                                                        con = connect();
                                                        pres = con.prepareStatement("select * from category where categories = " + "'" + category + "'");
                                                        rs = pres.executeQuery();
                                                        rs.next();
                                                        int idCat = rs.getInt("id");

                                                        if (yoxla == 0) {

                                                            con = connect();
                                                            pres = con.prepareStatement("insert into mehsullar (Malin_adi, mainCategory) values (?,?)");
                                                            pres.setString(1, name2);
                                                            pres.setInt(2, idCat);
                                                            pres.executeUpdate();

                                                        }
                                                    }

                                                }

                                            }

                                            rowIndex22 = cell.getRowIndex();
                                            name22 = cell.getStringCellValue();
                                            if (rowIndex <= 1020) {
                                                if (rowIndex == 1012) {
                                                    System.out.println("Bura 73-cu celldi");
                                                } else {
                                                    if (rowIndex > 1012) {

                                                        String category = "    ..РОЗА ЧЕХОВСКИЙ САД";
                                                        con = connect();
                                                        pres = con.prepareStatement("select * from category where categories = " + "'" + category + "'");
                                                        rs = pres.executeQuery();
                                                        rs.next();
                                                        int idCat = rs.getInt("id");

                                                        if (yoxla == 0) {

                                                            con = connect();
                                                            pres = con.prepareStatement("insert into mehsullar (Malin_adi, mainCategory) values (?,?)");
                                                            pres.setString(1, name2);
                                                            pres.setInt(2, idCat);
                                                            pres.executeUpdate();

                                                        }
                                                    }

                                                }

                                            }

                                            rowIndex22 = cell.getRowIndex();
                                            name22 = cell.getStringCellValue();
                                            if (rowIndex <= 1465) {
                                                if (rowIndex == 1021) {
                                                    System.out.println("Bura 73-cu celldi");
                                                } else {
                                                    if (rowIndex > 1021) {

                                                        String category = "    ..РОЗА ЭКВАДОР";
                                                        con = connect();
                                                        pres = con.prepareStatement("select * from category where categories = " + "'" + category + "'");
                                                        rs = pres.executeQuery();
                                                        rs.next();
                                                        int idCat = rs.getInt("id");

                                                        if (yoxla == 0) {

                                                            con = connect();
                                                            pres = con.prepareStatement("insert into mehsullar (Malin_adi, mainCategory) values (?,?)");
                                                            pres.setString(1, name2);
                                                            pres.setInt(2, idCat);
                                                            pres.executeUpdate();

                                                        }
                                                    }

                                                }

                                            }
                                            rowIndex22 = cell.getRowIndex();
                                            name22 = cell.getStringCellValue();
                                            if (rowIndex <= 1484) {
                                                if (rowIndex == 1466) {
                                                    System.out.println("Bura 73-cu celldi");
                                                } else {
                                                    if (rowIndex > 1466) {

                                                        String category = "    ..ТЮЛЬПАНЫ";
                                                        con = connect();
                                                        pres = con.prepareStatement("select * from category where categories = " + "'" + category + "'");
                                                        rs = pres.executeQuery();
                                                        rs.next();
                                                        int idCat = rs.getInt("id");

                                                        if (yoxla == 0) {

                                                            con = connect();
                                                            pres = con.prepareStatement("insert into mehsullar (Malin_adi, mainCategory) values (?,?)");
                                                            pres.setString(1, name2);
                                                            pres.setInt(2, idCat);
                                                            pres.executeUpdate();

                                                        }
                                                    }

                                                }

                                            }

                                            rowIndex22 = cell.getRowIndex();
                                            name22 = cell.getStringCellValue();
                                            if (rowIndex <= 1559) {
                                                if (rowIndex == 1485) {
                                                    System.out.println("Bura 73-cu celldi");
                                                } else {
                                                    if (rowIndex > 1485) {

                                                        String category = "    ..ХРИЗАНТЕМА";
                                                        con = connect();
                                                        pres = con.prepareStatement("select * from category where categories = " + "'" + category + "'");
                                                        rs = pres.executeQuery();
                                                        rs.next();
                                                        int idCat = rs.getInt("id");

                                                        if (yoxla == 0) {

                                                            con = connect();
                                                            pres = con.prepareStatement("insert into mehsullar (Malin_adi, mainCategory) values (?,?)");
                                                            pres.setString(1, name2);
                                                            pres.setInt(2, idCat);
                                                            pres.executeUpdate();

                                                        }
                                                    }

                                                }

                                            }
                                            rowIndex22 = cell.getRowIndex();
                                            name22 = cell.getStringCellValue();
                                            if (rowIndex <= 1728) {
                                                if (rowIndex == 1560) {
                                                    System.out.println("Bura 73-cu celldi");
                                                } else {
                                                    if (rowIndex > 1560) {

                                                        String category = "    .КОМНАТНЫЕ РАСТЕНИЯ";
                                                        con = connect();
                                                        pres = con.prepareStatement("select * from category where categories = " + "'" + category + "'");
                                                        rs = pres.executeQuery();
                                                        rs.next();
                                                        int idCat = rs.getInt("id");

                                                        if (yoxla == 0) {

                                                            con = connect();
                                                            pres = con.prepareStatement("insert into mehsullar (Malin_adi, mainCategory) values (?,?)");
                                                            pres.setString(1, name2);
                                                            pres.setInt(2, idCat);
                                                            pres.executeUpdate();

                                                        }
                                                    }

                                                }

                                            }
                                            rowIndex22 = cell.getRowIndex();
                                            name22 = cell.getStringCellValue();
                                            if (rowIndex <= 2083) {
                                                if (rowIndex == 1729) {
                                                    System.out.println("Bura 73-cu celldi");
                                                } else {
                                                    if (rowIndex > 1729) {

                                                        String category = "    .УПАКОВКА";
                                                        con = connect();
                                                        pres = con.prepareStatement("select * from category where categories = " + "'" + category + "'");
                                                        rs = pres.executeQuery();
                                                        rs.next();
                                                        int idCat = rs.getInt("id");

                                                        if (yoxla == 0) {

                                                            con = connect();
                                                            pres = con.prepareStatement("insert into mehsullar (Malin_adi, mainCategory) values (?,?)");
                                                            pres.setString(1, name2);
                                                            pres.setInt(2, idCat);
                                                            pres.executeUpdate();

                                                        }
                                                    }

                                                }

                                            }
                                            rowIndex22 = cell.getRowIndex();
                                            name22 = cell.getStringCellValue();
                                            if (rowIndex <= 2591) {
                                                if (rowIndex == 2084) {
                                                    System.out.println("Bura 73-cu celldi");
                                                } else {
                                                    if (rowIndex > 2084) {

                                                        String category = "    ВАЗЫ И КЕРАМИКА";
                                                        con = connect();
                                                        pres = con.prepareStatement("select * from category where categories = " + "'" + category + "'");
                                                        rs = pres.executeQuery();
                                                        rs.next();
                                                        int idCat = rs.getInt("id");

                                                        if (yoxla == 0) {

                                                            con = connect();
                                                            pres = con.prepareStatement("insert into mehsullar (Malin_adi, mainCategory) values (?,?)");
                                                            pres.setString(1, name2);
                                                            pres.setInt(2, idCat);
                                                            pres.executeUpdate();

                                                        }
                                                    }

                                                }

                                            }
                                            rowIndex22 = cell.getRowIndex();
                                            name22 = cell.getStringCellValue();
                                            if (rowIndex <= 2764) {
                                                if (rowIndex == 2592) {
                                                    System.out.println("Bura 73-cu celldi");
                                                } else {
                                                    if (rowIndex > 2592) {

                                                        String category = "    ГРУНТЫ И УДОБРЕНИЯ";
                                                        con = connect();
                                                        pres = con.prepareStatement("select * from category where categories = " + "'" + category + "'");
                                                        rs = pres.executeQuery();
                                                        rs.next();
                                                        int idCat = rs.getInt("id");

                                                        if (yoxla == 0) {

                                                            con = connect();
                                                            pres = con.prepareStatement("insert into mehsullar (Malin_adi, mainCategory) values (?,?)");
                                                            pres.setString(1, name2);
                                                            pres.setInt(2, idCat);
                                                            pres.executeUpdate();

                                                        }
                                                    }

                                                }

                                            }
                                            rowIndex22 = cell.getRowIndex();
                                            name22 = cell.getStringCellValue();
                                            if (rowIndex <= 3419) {
                                                if (rowIndex == 2765) {
                                                    System.out.println("Bura 73-cu celldi");
                                                } else {
                                                    if (rowIndex > 2765) {

                                                        String category = "    КОРЗИНА";
                                                        con = connect();
                                                        pres = con.prepareStatement("select * from category where categories = " + "'" + category + "'");
                                                        rs = pres.executeQuery();
                                                        rs.next();
                                                        int idCat = rs.getInt("id");

                                                        if (yoxla == 0) {

                                                            con = connect();
                                                            pres = con.prepareStatement("insert into mehsullar (Malin_adi, mainCategory) values (?,?)");
                                                            pres.setString(1, name2);
                                                            pres.setInt(2, idCat);
                                                            pres.executeUpdate();

                                                        }
                                                    }

                                                }

                                            }
                                            rowIndex22 = cell.getRowIndex();
                                            name22 = cell.getStringCellValue();
                                            if (rowIndex <= 3719) {
                                                if (rowIndex == 2420) {
                                                    System.out.println("Bura 73-cu celldi");
                                                } else {
                                                    if (rowIndex > 3420) {

                                                        String category = "    МЯГКАЯ ИГРУШКА";
                                                        con = connect();
                                                        pres = con.prepareStatement("select * from category where categories = " + "'" + category + "'");
                                                        rs = pres.executeQuery();
                                                        rs.next();
                                                        int idCat = rs.getInt("id");

                                                        if (yoxla == 0) {

                                                            con = connect();
                                                            pres = con.prepareStatement("insert into mehsullar (Malin_adi, mainCategory) values (?,?)");
                                                            pres.setString(1, name2);
                                                            pres.setInt(2, idCat);
                                                            pres.executeUpdate();

                                                        }
                                                    }

                                                }

                                            }
                                            rowIndex22 = cell.getRowIndex();
                                            name22 = cell.getStringCellValue();
                                            if (rowIndex <= 3832) {
                                                if (rowIndex == 2720) {
                                                    System.out.println("Bura 73-cu celldi");
                                                } else {
                                                    if (rowIndex > 3720) {

                                                        String category = "    ОТКРЫТКИ";
                                                        con = connect();
                                                        pres = con.prepareStatement("select * from category where categories = " + "'" + category + "'");
                                                        rs = pres.executeQuery();
                                                        rs.next();
                                                        int idCat = rs.getInt("id");

                                                        if (yoxla == 0) {

                                                            con = connect();
                                                            pres = con.prepareStatement("insert into mehsullar (Malin_adi, mainCategory) values (?,?)");
                                                            pres.setString(1, name2);
                                                            pres.setInt(2, idCat);
                                                            pres.executeUpdate();

                                                        }
                                                    }

                                                }

                                            }
                                            rowIndex22 = cell.getRowIndex();
                                            name22 = cell.getStringCellValue();
                                            if (rowIndex <= 3868) {
                                                if (rowIndex == 3833) {
                                                    System.out.println("Bura 73-cu celldi");
                                                } else {
                                                    if (rowIndex > 3833) {

                                                        String category = "    ТАРА";
                                                        con = connect();
                                                        pres = con.prepareStatement("select * from category where categories = " + "'" + category + "'");
                                                        rs = pres.executeQuery();
                                                        rs.next();
                                                        int idCat = rs.getInt("id");

                                                        if (yoxla == 0) {

                                                            con = connect();
                                                            pres = con.prepareStatement("insert into mehsullar (Malin_adi, mainCategory) values (?,?)");
                                                            pres.setString(1, name2);
                                                            pres.setInt(2, idCat);
                                                            pres.executeUpdate();

                                                        }
                                                    }

                                                }

                                            }

                                        }
                                    }

                                }
                            }
                        }

                    }
                }
            } catch (Exception ex) {

                ex.printStackTrace();

            }

        }

    }

    public static void deleteDb() {
        try {

            con = connect();
            pres = con.prepareStatement("truncate table mehsullar");
            pres.executeUpdate();

            pres = con.prepareStatement("truncate table category");
            pres.executeUpdate();

        } catch (Exception ex) {

            ex.printStackTrace();
        }

    }

    public static void setProductPrice() {
        int id = 1;
        JFileChooser openFileChooser = new JFileChooser();
        openFileChooser.setDialogTitle("Open File");
        openFileChooser.removeChoosableFileFilter(openFileChooser.getFileFilter());
        FileFilter filter = new FileNameExtensionFilter("Excel file (.xls)", "xls");
        openFileChooser.setFileFilter(filter);
        if (openFileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File inputFile = openFileChooser.getSelectedFile();
            try ( FileInputStream in = new FileInputStream(inputFile)) {

                HSSFWorkbook importedFile = new HSSFWorkbook(in);
                HSSFSheet sheet1 = importedFile.getSheetAt(0);

                Iterator<Row> rowIterator = sheet1.iterator();
                while (rowIterator.hasNext()) {
                    Row row = rowIterator.next();
                    Iterator<Cell> cellIterator = row.cellIterator();
                    while (cellIterator.hasNext()) {
                        Cell cell = cellIterator.next();

                        int rowIndex = cell.getRowIndex();
                        int miqdari = 0;
                        if (cell.getColumnIndex() == 2) {
                            double qiymeti = cell.getNumericCellValue();

                            if (rowIndex <= 18) {
                                if (rowIndex == 11) {
                                    System.out.println("Bura 73-cu celldi");
                                } else {
                                    if (rowIndex >= 12) {
                                        con = connect();
                                        pres = con.prepareStatement("update mehsullar set Alis_qiymeti =?, Miqdari = ?, Satis_miqdari=?, Qaliq_say =? where id = " + id);

                                        pres.setDouble(1, qiymeti);
                                        pres.setDouble(2, miqdari);
                                        pres.setDouble(3, miqdari);
                                        pres.setDouble(4, miqdari);
                                        pres.executeUpdate();
                                        id++;
                                    }

                                }
                            }
                            if (rowIndex <= 72) {
                                if (rowIndex == 19) {
                                    System.out.println("Bura 73-cu celldi");
                                } else {
                                    if (rowIndex >= 19) {
                                        con = connect();
                                        pres = con.prepareStatement("update mehsullar set Alis_qiymeti =?, Miqdari = ?, Satis_miqdari=?, Qaliq_say =? where id = " + id);

                                        pres.setDouble(1, qiymeti);
                                        pres.setDouble(2, miqdari);
                                        pres.setDouble(3, miqdari);
                                        pres.setDouble(4, miqdari);
                                        pres.executeUpdate();
                                        id++;
                                    }

                                }
                            }
                            if (rowIndex <= 75) {
                                if (rowIndex == 73) {
                                    System.out.println("Bura 73-cu celldi");
                                } else {
                                    if (rowIndex >= 73) {
                                        con = connect();
                                        pres = con.prepareStatement("update mehsullar set Alis_qiymeti =?, Miqdari = ?, Satis_miqdari=?, Qaliq_say =? where id = " + id);

                                        pres.setDouble(1, qiymeti);
                                        pres.setDouble(2, miqdari);
                                        pres.setDouble(3, miqdari);
                                        pres.setDouble(4, miqdari);
                                        pres.executeUpdate();
                                        id++;
                                    }

                                }
                            }
                            if (rowIndex <= 244) {
                                if (rowIndex == 76) {
                                    System.out.println("Bura 73-cu celldi");
                                } else {
                                    if (rowIndex >= 76) {
                                        con = connect();
                                        pres = con.prepareStatement("update mehsullar set Alis_qiymeti =?, Miqdari = ?, Satis_miqdari=?, Qaliq_say =? where id = " + id);

                                        pres.setDouble(1, qiymeti);
                                        pres.setDouble(2, miqdari);
                                        pres.setDouble(3, miqdari);
                                        pres.setDouble(4, miqdari);
                                        pres.executeUpdate();
                                        id++;
                                    }

                                }
                            }
                            if (rowIndex <= 246) {
                                if (rowIndex == 245) {
                                    System.out.println("Bura 73-cu celldi");
                                } else {
                                    if (rowIndex >= 245) {
                                        con = connect();
                                        pres = con.prepareStatement("update mehsullar set Alis_qiymeti =? where id = " + id);

                                        pres = con.prepareStatement("update mehsullar set Alis_qiymeti =?, Miqdari = ?, Satis_miqdari=?, Qaliq_say =? where id = " + id);

                                        pres.setDouble(1, qiymeti);
                                        pres.setDouble(2, miqdari);
                                        pres.setDouble(3, miqdari);
                                        pres.setDouble(4, miqdari);
                                        pres.executeUpdate();
                                        id++;
                                    }

                                }
                            }
                            if (rowIndex <= 258) {
                                if (rowIndex == 247) {
                                    System.out.println("Bura 73-cu celldi");
                                } else {
                                    if (rowIndex >= 247) {
                                        con = connect();
                                        pres = con.prepareStatement("update mehsullar set Alis_qiymeti =?, Miqdari = ?, Satis_miqdari=?, Qaliq_say =? where id = " + id);

                                        pres.setDouble(1, qiymeti);
                                        pres.setDouble(2, miqdari);
                                        pres.setDouble(3, miqdari);
                                        pres.setDouble(4, miqdari);
                                        pres.executeUpdate();
                                        id++;
                                    }

                                }
                            }
                            if (rowIndex <= 353) {
                                if (rowIndex == 259) {
                                    System.out.println("Bura 73-cu celldi");
                                } else {
                                    if (rowIndex >= 259) {
                                        con = connect();
                                        pres = con.prepareStatement("update mehsullar set Alis_qiymeti =?, Miqdari = ?, Satis_miqdari=?, Qaliq_say =? where id = " + id);

                                        pres.setDouble(1, qiymeti);
                                        pres.setDouble(2, miqdari);
                                        pres.setDouble(3, miqdari);
                                        pres.setDouble(4, miqdari);
                                        pres.executeUpdate();
                                        id++;
                                    }

                                }
                            }
                            if (rowIndex <= 634) {
                                if (rowIndex == 354) {
                                    System.out.println("Bura 73-cu celldi");
                                } else {
                                    if (rowIndex >= 354) {
                                        con = connect();
                                        pres = con.prepareStatement("update mehsullar set Alis_qiymeti =?, Miqdari = ?, Satis_miqdari=?, Qaliq_say =? where id = " + id);

                                        pres.setDouble(1, qiymeti);
                                        pres.setDouble(2, miqdari);
                                        pres.setDouble(3, miqdari);
                                        pres.setDouble(4, miqdari);
                                        pres.executeUpdate();
                                        id++;
                                    }

                                }
                            }
                            if (rowIndex <= 838) {
                                if (rowIndex == 635) {
                                    System.out.println("Bura 73-cu celldi");
                                } else {
                                    if (rowIndex >= 635) {
                                        con = connect();
                                        pres = con.prepareStatement("update mehsullar set Alis_qiymeti =?, Miqdari = ?, Satis_miqdari=?, Qaliq_say =? where id = " + id);

                                        pres.setDouble(1, qiymeti);
                                        pres.setDouble(2, miqdari);
                                        pres.setDouble(3, miqdari);
                                        pres.setDouble(4, miqdari);
                                        pres.executeUpdate();
                                        id++;
                                    }

                                }
                            }
                            if (rowIndex <= 844) {
                                if (rowIndex == 839) {
                                    System.out.println("Bura 73-cu celldi");
                                } else {
                                    if (rowIndex >= 839) {
                                        con = connect();
                                        pres = con.prepareStatement("update mehsullar set Alis_qiymeti =?, Miqdari = ?, Satis_miqdari=?, Qaliq_say =? where id = " + id);

                                        pres.setDouble(1, qiymeti);
                                        pres.setDouble(2, miqdari);
                                        pres.setDouble(3, miqdari);
                                        pres.setDouble(4, miqdari);
                                        pres.executeUpdate();
                                        id++;
                                    }

                                }
                            }
                            if (rowIndex <= 851) {
                                if (rowIndex == 845) {
                                    System.out.println("Bura 73-cu celldi");
                                } else {
                                    if (rowIndex >= 845) {
                                        con = connect();
                                        pres = con.prepareStatement("update mehsullar set Alis_qiymeti =?, Miqdari = ?, Satis_miqdari=?, Qaliq_say =? where id = " + id);

                                        pres.setDouble(1, qiymeti);
                                        pres.setDouble(2, miqdari);
                                        pres.setDouble(3, miqdari);
                                        pres.setDouble(4, miqdari);
                                        pres.executeUpdate();
                                        id++;
                                    }

                                }
                            }
                            if (rowIndex <= 941) {
                                if (rowIndex == 852) {
                                    System.out.println("Bura 73-cu celldi");
                                } else {
                                    if (rowIndex >= 852) {
                                        con = connect();
                                        pres = con.prepareStatement("update mehsullar set Alis_qiymeti =?, Miqdari = ?, Satis_miqdari=?, Qaliq_say =? where id = " + id);

                                        pres.setDouble(1, qiymeti);
                                        pres.setDouble(2, miqdari);
                                        pres.setDouble(3, miqdari);
                                        pres.setDouble(4, miqdari);
                                        pres.executeUpdate();
                                        id++;
                                    }

                                }
                            }
                            if (rowIndex <= 1009) {
                                if (rowIndex == 942) {
                                    System.out.println("Bura 73-cu celldi");
                                } else {
                                    if (rowIndex >= 942) {
                                        con = connect();
                                        pres = con.prepareStatement("update mehsullar set Alis_qiymeti =?, Miqdari = ?, Satis_miqdari=?, Qaliq_say =? where id = " + id);

                                        pres.setDouble(1, qiymeti);
                                        pres.setDouble(2, miqdari);
                                        pres.setDouble(3, miqdari);
                                        pres.setDouble(4, miqdari);
                                        pres.executeUpdate();
                                        id++;
                                    }

                                }
                            }
                            if (rowIndex <= 1011) {
                                if (rowIndex == 1010) {
                                    System.out.println("Bura 73-cu celldi");
                                } else {
                                    if (rowIndex >= 1010) {
                                        con = connect();
                                        pres = con.prepareStatement("update mehsullar set Alis_qiymeti =?, Miqdari = ?, Satis_miqdari=?, Qaliq_say =? where id = " + id);

                                        pres.setDouble(1, qiymeti);
                                        pres.setDouble(2, miqdari);
                                        pres.setDouble(3, miqdari);
                                        pres.setDouble(4, miqdari);
                                        pres.executeUpdate();
                                        id++;
                                    }

                                }
                            }
                            if (rowIndex <= 1020) {
                                if (rowIndex == 1012) {
                                    System.out.println("Bura 73-cu celldi");
                                } else {
                                    if (rowIndex >= 1012) {
                                        con = connect();
                                        pres = con.prepareStatement("update mehsullar set Alis_qiymeti =?, Miqdari = ?, Satis_miqdari=?, Qaliq_say =? where id = " + id);

                                        pres.setDouble(1, qiymeti);
                                        pres.setDouble(2, miqdari);
                                        pres.setDouble(3, miqdari);
                                        pres.setDouble(4, miqdari);
                                        pres.executeUpdate();
                                        id++;
                                    }

                                }
                            }
                            if (rowIndex <= 1465) {
                                if (rowIndex == 1021) {
                                    System.out.println("Bura 73-cu celldi");
                                } else {
                                    if (rowIndex >= 1021) {
                                        con = connect();
                                        pres = con.prepareStatement("update mehsullar set Alis_qiymeti =?, Miqdari = ?, Satis_miqdari=?, Qaliq_say =? where id = " + id);

                                        pres.setDouble(1, qiymeti);
                                        pres.setDouble(2, miqdari);
                                        pres.setDouble(3, miqdari);
                                        pres.setDouble(4, miqdari);
                                        pres.executeUpdate();
                                        id++;
                                    }

                                }
                            }
                            if (rowIndex <= 1484) {
                                if (rowIndex == 1466) {
                                    System.out.println("Bura 73-cu celldi");
                                } else {
                                    if (rowIndex >= 1466) {
                                        con = connect();
                                        pres = con.prepareStatement("update mehsullar set Alis_qiymeti =?, Miqdari = ?, Satis_miqdari=?, Qaliq_say =? where id = " + id);

                                        pres.setDouble(1, qiymeti);
                                        pres.setDouble(2, miqdari);
                                        pres.setDouble(3, miqdari);
                                        pres.setDouble(4, miqdari);
                                        pres.executeUpdate();
                                        id++;
                                    }

                                }
                            }
                            if (rowIndex <= 1559) {
                                if (rowIndex == 1485) {
                                    System.out.println("Bura 73-cu celldi");
                                } else {
                                    if (rowIndex >= 1485) {
                                        con = connect();
                                        pres = con.prepareStatement("update mehsullar set Alis_qiymeti =?, Miqdari = ?, Satis_miqdari=?, Qaliq_say =? where id = " + id);

                                        pres.setDouble(1, qiymeti);
                                        pres.setDouble(2, miqdari);
                                        pres.setDouble(3, miqdari);
                                        pres.setDouble(4, miqdari);
                                        pres.executeUpdate();
                                        id++;
                                    }

                                }
                            }
                            if (rowIndex <= 1728) {
                                if (rowIndex == 1560) {
                                    System.out.println("Bura 73-cu celldi");
                                } else {
                                    if (rowIndex >= 1560) {
                                        con = connect();
                                        pres = con.prepareStatement("update mehsullar set Alis_qiymeti =?, Miqdari = ?, Satis_miqdari=?, Qaliq_say =? where id = " + id);

                                        pres.setDouble(1, qiymeti);
                                        pres.setDouble(2, miqdari);
                                        pres.setDouble(3, miqdari);
                                        pres.setDouble(4, miqdari);
                                        pres.executeUpdate();
                                        id++;
                                    }

                                }
                            }
                            if (rowIndex <= 2083) {
                                if (rowIndex == 1729) {
                                    System.out.println("Bura 73-cu celldi");
                                } else {
                                    if (rowIndex >= 1729) {
                                        con = connect();
                                        pres = con.prepareStatement("update mehsullar set Alis_qiymeti =?, Miqdari = ?, Satis_miqdari=?, Qaliq_say =? where id = " + id);

                                        pres.setDouble(1, qiymeti);
                                        pres.setDouble(2, miqdari);
                                        pres.setDouble(3, miqdari);
                                        pres.setDouble(4, miqdari);
                                        pres.executeUpdate();
                                        id++;
                                    }

                                }
                            }
                            if (rowIndex <= 2591) {
                                if (rowIndex == 2084) {
                                    System.out.println("Bura 73-cu celldi");
                                } else {
                                    if (rowIndex >= 2084) {
                                        con = connect();
                                        pres = con.prepareStatement("update mehsullar set Alis_qiymeti =?, Miqdari = ?, Satis_miqdari=?, Qaliq_say =? where id = " + id);

                                        pres.setDouble(1, qiymeti);
                                        pres.setDouble(2, miqdari);
                                        pres.setDouble(3, miqdari);
                                        pres.setDouble(4, miqdari);
                                        pres.executeUpdate();
                                        id++;
                                    }

                                }
                            }
                            if (rowIndex <= 2764) {
                                if (rowIndex == 2592) {
                                    System.out.println("Bura 73-cu celldi");
                                } else {
                                    if (rowIndex >= 2592) {
                                        con = connect();
                                        pres = con.prepareStatement("update mehsullar set Alis_qiymeti =?, Miqdari = ?, Satis_miqdari=?, Qaliq_say =? where id = " + id);

                                        pres.setDouble(1, qiymeti);
                                        pres.setDouble(2, miqdari);
                                        pres.setDouble(3, miqdari);
                                        pres.setDouble(4, miqdari);
                                        pres.executeUpdate();
                                        id++;
                                    }

                                }
                            }
                            if (rowIndex <= 3419) {
                                if (rowIndex == 2765) {
                                    System.out.println("Bura 73-cu celldi");
                                } else {
                                    if (rowIndex >= 2765) {
                                        con = connect();
                                        pres = con.prepareStatement("update mehsullar set Alis_qiymeti =?, Miqdari = ?, Satis_miqdari=?, Qaliq_say =? where id = " + id);

                                        pres.setDouble(1, qiymeti);
                                        pres.setDouble(2, miqdari);
                                        pres.setDouble(3, miqdari);
                                        pres.setDouble(4, miqdari);
                                        pres.executeUpdate();
                                        id++;
                                    }

                                }
                            }
                            if (rowIndex <= 3720) {
                                if (rowIndex == 3420) {
                                    System.out.println("Bura 73-cu celldi");
                                } else {
                                    if (rowIndex >= 3420) {
                                        con = connect();
                                        pres = con.prepareStatement("update mehsullar set Alis_qiymeti =?, Miqdari = ?, Satis_miqdari=?, Qaliq_say =? where id = " + id);

                                        pres.setDouble(1, qiymeti);
                                        pres.setDouble(2, miqdari);
                                        pres.setDouble(3, miqdari);
                                        pres.setDouble(4, miqdari);
                                        pres.executeUpdate();
                                        id++;
                                    }

                                }
                            }
                            if (rowIndex <= 3833) {
                                if (rowIndex == 3721) {
                                    System.out.println("Bura 73-cu celldi");
                                } else {
                                    if (rowIndex >= 3721) {
                                        con = connect();
                                        pres = con.prepareStatement("update mehsullar set Alis_qiymeti =?, Miqdari = ?, Satis_miqdari=?, Qaliq_say =? where id = " + id);

                                        pres.setDouble(1, qiymeti);
                                        pres.setDouble(2, miqdari);
                                        pres.setDouble(3, miqdari);
                                        pres.setDouble(4, miqdari);
                                        pres.executeUpdate();
                                        id++;
                                    }

                                }
                            }
                            if (rowIndex <= 3869) {
                                if (rowIndex == 3834) {
                                    System.out.println("Bura 73-cu celldi");
                                } else {
                                    if (rowIndex >= 3834) {
                                        con = connect();
                                        pres = con.prepareStatement("update mehsullar set Alis_qiymeti =?, Miqdari = ?, Satis_miqdari=?, Qaliq_say =? where id = " + id);

                                        pres.setDouble(1, qiymeti);
                                        pres.setDouble(2, miqdari);
                                        pres.setDouble(3, miqdari);
                                        pres.setDouble(4, miqdari);
                                        pres.executeUpdate();
                                        id++;
                                    }

                                }
                            }
                        }

                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

    }

}
