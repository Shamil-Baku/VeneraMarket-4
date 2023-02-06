package com.mycompany.qarisiqmallar.veneramarket;

import static com.mycompany.qarisiqmallar.veneramarket.TestClass.con;
import static com.mycompany.qarisiqmallar.veneramarket.TestClass.pres;
import static com.mycompany.qarisiqmallar.veneramarket.TestClass.rs;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.TransferHandler;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.fonts.FontFace;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.apache.batik.gvt.font.GVTFontFamily;

public class ProductCategories extends javax.swing.JFrame implements WindowListener {

    String miqdariFromJDialog;
    DefaultTreeModel model;
    static DefaultTableModel df;
    DefaultMutableTreeNode products = new DefaultMutableTreeNode();
    Timer timer;
    String currentDate;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss");

    File f;
    String path55 = null;
    ImageIcon format = null;
    int s = 0;
    byte[] pimage = null;

    static String sayForOtbor;

    public ProductCategories() {
        initComponents();
        jTableMehsullar.setDragEnabled(true);
        jTableMehsullar.setTransferHandler(new SimpleListDTSManager111());
        jTree1.setDragEnabled(true);
        jTree1.setTransferHandler(new SimpleListDTSManager111());

        setMainBaseName();
        Load();
        setTime();
        addWindowListener(this);
        txtSearch.requestFocus();
        // findTheLastBiilNumber();
//        txtSetPrice.disable();
//        panelTest.setVisible(false);
//        panelSearch.setVisible(false);
//        txtNumOfCopies.disable();

    }

    static Connection con;
    static PreparedStatement pres;
    static ResultSet rs;
    int say = 0;

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

    public void setMainBaseName() {

        try {

            con = connect();
            pres = con.prepareStatement("select main.mainBaseName from mainbasenames main");
            rs = pres.executeQuery();

            rs.next();

            String mainBaseName = rs.getString("mainBaseName");

            products = new DefaultMutableTreeNode(mainBaseName);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
    String time;
    String time2;

    public void setTime() {

        timer = new Timer(0, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
                Date dt = new Date();
                //sdf = new SimpleDateFormat("HH:mm:ss");

                time = sdf.format(dt);
                //txtTime.setText(time);

                time2 = sdf2.format(dt);
                currentDate = time2;

            }
        });
        timer.start();

    }

    public void load3(String season, String category, String subCategory) {

        int a;
        try {
            con = connect();
            pres = con.prepareCall("select id, Malin_adi,Miqdari,Satis_miqdari,Qaliq_say, Alis_qiymeti,Satis_qiymeti,Movsum_id,Kateqoriya_id,Alt_kateqoriya_id,Alisin_toplam_deyer,Alis_Tarixi,Barcode from mehsullar where Movsum_id =" + "'" + season + "'" + "and Kateqoriya_id = " + "'" + category + "'" + "and Alt_kateqoriya_id = " + "'" + subCategory + "'" + "");

            ResultSet rs = pres.executeQuery();

            ResultSetMetaData rd = rs.getMetaData();
            a = rd.getColumnCount();
            df = (DefaultTableModel) jTableMehsullar.getModel();
            df.setRowCount(0);

            while (rs.next()) {
                Vector v2 = new Vector();
                for (int i = 0; i < a; i++) {
                    v2.add(rs.getInt("id"));
                    v2.add(rs.getString("Malin_adi"));
                    v2.add(rs.getInt("Miqdari"));
                    v2.add(rs.getInt("Satis_miqdari"));
                    v2.add(rs.getInt("Qaliq_say"));
                    v2.add(rs.getDouble("Alis_qiymeti"));
                    v2.add(rs.getDouble("Satis_qiymeti"));
                    v2.add(rs.getInt("Movsum_id"));
                    v2.add(rs.getInt("Kateqoriya_id"));
                    v2.add(rs.getInt("Alt_kateqoriya_id"));
                    v2.add(rs.getDouble("Alisin_toplam_deyer"));
                    v2.add(rs.getString("Alis_Tarixi"));
                    v2.add(rs.getString("Barcode"));

                }
                df.addRow(v2);

            }

        } catch (Exception ex) {
            System.out.println(ex);
        }

    }

    public static void loadAllProducts() {

        con = connect();
        try {

            pres = con.prepareStatement("truncate table qaimemehsullariForSerach");
            pres.executeUpdate();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        int a;
        try {

            boolean yoxla = chcekOtbor.isSelected();

            if (yoxla == true) {

                pres = con.prepareStatement("select id, Malin_adi,Miqdari,Satis_miqdari,Qaliq_say, Alis_qiymeti,Satis_qiymeti,Alis_Tarixi,Barcode from mehsullar order by Malin_adi");

                ResultSet rs = pres.executeQuery();
                ResultSetMetaData rd = rs.getMetaData();
                a = rd.getColumnCount();
                df = (DefaultTableModel) jTableMehsullar.getModel();
                df.setRowCount(0);

                while (rs.next()) {
                    Vector v2 = new Vector();

                    int id2 = rs.getInt("id");
                    String malinAdi = rs.getString("Malin_adi");
                    int qaliqSay = (rs.getInt("Qaliq_say"));
                    double alisQiymeti = (rs.getDouble("Alis_qiymeti"));
                    double satisQiymeti = (rs.getDouble("Satis_qiymeti"));
                    Date alisTarixi = (rs.getDate("Alis_Tarixi"));
                    String barcode = (rs.getString("Barcode"));

                    v2.add(id2);
                    v2.add(malinAdi);
                    v2.add(qaliqSay);
                    v2.add(alisQiymeti);
                    v2.add(satisQiymeti);
                    v2.add(alisTarixi);
                    v2.add(barcode);
                    df.addRow(v2);
                }

//                pres = con.prepareStatement("select id, Malin_adi,Miqdari,Satis_miqdari,Qaliq_say, Alis_qiymeti,Satis_qiymeti,Alis_Tarixi,Barcode from mehsullar order by Malin_adi");
//
//                ResultSet rs2 = pres.executeQuery();
//
//                while (rs2.next()) {
//
//                    int id2 = rs2.getInt("id");
//                    String malinAdi = rs2.getString("Malin_adi");
//                    int qaliqSay = (rs2.getInt("Qaliq_say"));
//                    double alisQiymeti = (rs2.getDouble("Alis_qiymeti"));
//                    double satisQiymeti = (rs2.getDouble("Satis_qiymeti"));
//                    Date alisTarixi = (rs2.getDate("Alis_Tarixi"));
//                    String barcode = (rs2.getString("Barcode"));
//
//                    pres = con.prepareStatement("insert into qaimemehsullariForSerach (id, productName,QaliqSay,PriceOfBuy, priceOfSale, tarix, Barcode) values (?,?,?,?,?,?,?)");
//
//                    pres.setInt(1, id2);
//                    pres.setString(2, malinAdi);
//                    pres.setInt(3, qaliqSay);
//                    pres.setDouble(4, alisQiymeti);
//                    pres.setDouble(5, satisQiymeti);
//                    pres.setDate(6, (java.sql.Date) alisTarixi);
//                    pres.setString(7, barcode);
//
//                    pres.executeUpdate();
//                }
            } else {

                pres = con.prepareCall("select id, Malin_adi,Miqdari,Satis_miqdari,Qaliq_say, Alis_qiymeti,Satis_qiymeti,Alis_Tarixi,Barcode from mehsullar where Qaliq_say " + sayForOtbor + " order by Malin_adi");

                ResultSet rs = pres.executeQuery();
                ResultSetMetaData rd = rs.getMetaData();
                a = rd.getColumnCount();
                df = (DefaultTableModel) jTableMehsullar.getModel();
                df.setRowCount(0);

                while (rs.next()) {
                    Vector v2 = new Vector();

                    int id2 = rs.getInt("id");
                    String malinAdi = rs.getString("Malin_adi");
                    int qaliqSay = (rs.getInt("Qaliq_say"));
                    double alisQiymeti = (rs.getDouble("Alis_qiymeti"));
                    double satisQiymeti = (rs.getDouble("Satis_qiymeti"));
                    Date alisTarixi = (rs.getDate("Alis_Tarixi"));
                    String barcode = (rs.getString("Barcode"));

                    v2.add(id2);
                    v2.add(malinAdi);
                    v2.add(qaliqSay);
                    v2.add(alisQiymeti);
                    v2.add(satisQiymeti);
                    v2.add(alisTarixi);
                    v2.add(barcode);

//                    pres = con.prepareStatement("insert into qaimemehsullariForSerach (id, productName,QaliqSay,PriceOfBuy, priceOfSale, tarix, Barcode) values (?,?,?,?,?,?,?)");
//
//                    pres.setInt(1, id2);
//                    pres.setString(2, malinAdi);
//                    pres.setInt(3, qaliqSay);
//                    pres.setDouble(4, alisQiymeti);
//                    pres.setDouble(5, satisQiymeti);
//                    pres.setDate(6, (java.sql.Date) alisTarixi);
//                    pres.setString(7, barcode);
//                    pres.executeUpdate();
                    df.addRow(v2);
                }

            }

        } catch (Exception ex) {
            System.out.println(ex);
        }
        System.out.println("Bu iw bitdi");
    }

    public static void loadForSeasons(String season) {

        con = connect();
        try {

            pres = con.prepareStatement("truncate table qaimemehsullariForSerach");
            pres.executeUpdate();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try {

            boolean yoxla = chcekOtbor.isSelected();

            if (yoxla == true) {

                pres = con.prepareStatement("select id, Malin_adi,Miqdari,Satis_miqdari,Qaliq_say, Alis_qiymeti,Satis_qiymeti,mainCategory,subCategory,2ndSubCategory,Alisin_toplam_deyer,Alis_Tarixi,Barcode from mehsullar where mainCategory =" + "'" + season + "'" + " order by Malin_adi");
                ResultSet rs = pres.executeQuery();

                ResultSetMetaData rd = rs.getMetaData();
                df = (DefaultTableModel) jTableMehsullar.getModel();
                df.setRowCount(0);

                while (rs.next()) {

                    Vector v2 = new Vector();

                    int id2 = rs.getInt("id");

                    String malinAdi = rs.getString("Malin_adi");

                    int qaliqSay = rs.getInt("Qaliq_say");

                    double AlisQiy = rs.getDouble("Alis_qiymeti");

                    double satisQiy = rs.getDouble("Satis_qiymeti");

                    String date = rs.getString("Alis_Tarixi");

                    String barcode = rs.getString("Barcode");

                    v2.add(id2);
                    v2.add(malinAdi);
                    v2.add(qaliqSay);
                    v2.add(AlisQiy);
                    v2.add(satisQiy);
                    v2.add(date);
                    v2.add(barcode);

                    df.addRow(v2);

//                    pres = con.prepareStatement("insert into qaimemehsullariForSerach (id, productName,QaliqSay,PriceOfBuy, priceOfSale, tarix, Barcode) values (?,?,?,?,?,?,?)");
//
//                    pres.setInt(1, id2);
//                    pres.setString(2, malinAdi);
//                    pres.setInt(3, qaliqSay);
//                    pres.setDouble(4, AlisQiy);
//                    pres.setDouble(5, satisQiy);
//                    pres.setString(6, date);
//                    pres.setString(7, barcode);
//
//                    pres.executeUpdate();
                }

//                pres = con.prepareStatement("select id, Malin_adi,Miqdari,Satis_miqdari,Qaliq_say, Alis_qiymeti,Satis_qiymeti,mainCategory,subCategory,2ndSubCategory,Alisin_toplam_deyer,Alis_Tarixi,Barcode from mehsullar where mainCategory =" + "'" + season + "'" + " order by Malin_adi");
//                ResultSet rs2 = pres.executeQuery();
//
//                while (rs2.next()) {
//
//                    int id = rs2.getInt("id");
//                    String adi = rs2.getString("Malin_adi");
//                    int qaliqSay = rs2.getInt("Qaliq_say");
//                    double AlisQiy = rs2.getDouble("Alis_qiymeti");
//                    double satisQiy = rs2.getDouble("Satis_qiymeti");
//                    Date date = rs2.getDate("Alis_Tarixi");
//                    String barcode = rs2.getString("Barcode");
//
//                    pres = con.prepareStatement("insert into qaimemehsullariForSerach (id, productName,QaliqSay,PriceOfBuy, priceOfSale, tarix, Barcode) values (?,?,?,?,?,?,?)");
//
//                    pres.setInt(1, id);
//                    pres.setString(2, adi);
//                    pres.setInt(3, qaliqSay);
//                    pres.setDouble(4, AlisQiy);
//                    pres.setDouble(5, satisQiy);
//                    pres.setDate(6, (java.sql.Date) date);
//                    pres.setString(7, barcode);
//
//                    pres.executeUpdate();
//
//                }
            } else {

                pres = con.prepareStatement("select id, Malin_adi,Miqdari,Satis_miqdari,Qaliq_say, Alis_qiymeti,Satis_qiymeti,mainCategory,subCategory,2ndSubCategory,Alisin_toplam_deyer,Alis_Tarixi,Barcode from mehsullar where mainCategory =" + "'" + season + "'" + " and Qaliq_say " + sayForOtbor + " order by Malin_adi");
                ResultSet rs = pres.executeQuery();

                ResultSetMetaData rd = rs.getMetaData();
                df = (DefaultTableModel) jTableMehsullar.getModel();
                df.setRowCount(0);

                while (rs.next()) {
                    Vector v2 = new Vector();
                    int id2 = rs.getInt("id");
                    v2.add(id2);
                    String malinAdi = rs.getString("Malin_adi");
                    v2.add(malinAdi);
                    int qaliqSay = rs.getInt("Qaliq_say");
                    v2.add(qaliqSay);
                    double AlisQiy = rs.getDouble("Alis_qiymeti");
                    v2.add(AlisQiy);
                    double satisQiy = rs.getDouble("Satis_qiymeti");
                    v2.add(satisQiy);
                    String date = rs.getString("Alis_Tarixi");
                    v2.add(date);
                    String barcode = rs.getString("Barcode");
                    v2.add(barcode);
                    df.addRow(v2);

//                    pres = con.prepareStatement("insert into qaimemehsullariForSerach (id, productName, QaliqSay, PriceOfBuy, priceOfSale, tarix, Barcode) values (?,?,?,?,?,?,?)");
//
//                    pres.setInt(1, id2);
//                    pres.setString(2, malinAdi);
//                    pres.setInt(3, qaliqSay);
//                    pres.setDouble(4, AlisQiy);
//                    pres.setDouble(5, satisQiy);
//                    pres.setString(6, date);
//                    pres.setString(7, barcode);
//
//                    pres.executeUpdate();
                }

//                pres = con.prepareStatement("select id, Malin_adi,Miqdari,Satis_miqdari,Qaliq_say, Alis_qiymeti,Satis_qiymeti,mainCategory,subCategory,2ndSubCategory,Alisin_toplam_deyer,Alis_Tarixi,Barcode from mehsullar where mainCategory =" + "'" + season + "'" + " and Qaliq_say " + sayForOtbor + " order by Malin_adi");
//                ResultSet rs2 = pres.executeQuery();
//
//                while (rs2.next()) {
//
//                    int id = rs2.getInt("id");
//                    String adi = rs2.getString("Malin_adi");
//                    int qaliqSay = rs2.getInt("Qaliq_say");
//                    double AlisQiy = rs2.getDouble("Alis_qiymeti");
//                    double satisQiy = rs2.getDouble("Satis_qiymeti");
//                    Date date = rs2.getDate("Alis_Tarixi");
//                    String barcode = rs2.getString("Barcode");
//
//                    pres = con.prepareStatement("insert into qaimemehsullariForSerach (id, productName, QaliqSay, PriceOfBuy, priceOfSale, tarix, Barcode) values (?,?,?,?,?,?,?)");
//
//                    pres.setInt(1, id);
//                    pres.setString(2, adi);
//                    pres.setInt(3, qaliqSay);
//                    pres.setDouble(4, AlisQiy);
//                    pres.setDouble(5, satisQiy);
//                    pres.setDate(6, (java.sql.Date) date);
//                    pres.setString(7, barcode);
//
//                    pres.executeUpdate();
//
//                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    public static void loadForSecondSubCategory(int season, int category, int subCategory) {

        con = connect();
        try {

            pres = con.prepareStatement("truncate table qaimemehsullariForSerach");
            pres.executeUpdate();

        } catch (Exception ex) {

            ex.printStackTrace();
        }
        int a;
        try {

            boolean yoxla = chcekOtbor.isSelected();

            if (yoxla == true) {

                pres = con.prepareStatement("select id, Malin_adi,Miqdari,Satis_miqdari,Qaliq_say, Alis_qiymeti,Satis_qiymeti,"
                        + "mainCategory,subCategory,2ndSubCategory,Alisin_toplam_deyer,Alis_Tarixi,Barcode from mehsullar"
                        + " where mainCategory =" + "'" + season + "'" + " and subCategory = " + category
                        + " and 2ndSubCategory = " + subCategory + " order by Malin_adi");
                ResultSet rs = pres.executeQuery();

                ResultSetMetaData rd = rs.getMetaData();
                a = rd.getColumnCount();
                df = (DefaultTableModel) jTableMehsullar.getModel();
                df.setRowCount(0);

                while (rs.next()) {

                    Vector v2 = new Vector();
                    for (int i = 0; i < a; i++) {

                        int id2 = rs.getInt("id");
                        v2.add(id2);
                        String malinAdi = rs.getString("Malin_adi");
                        v2.add(malinAdi);
                        v2.add(rs.getInt("Qaliq_say"));
                        v2.add(rs.getDouble("Alis_qiymeti"));
                        v2.add(rs.getDouble("Satis_qiymeti"));
                        v2.add(rs.getString("Alis_Tarixi"));
                        v2.add(rs.getString("Barcode"));
                    }
                    df.addRow(v2);

                }

                pres = con.prepareStatement("select id, Malin_adi,Miqdari,Satis_miqdari,Qaliq_say, Alis_qiymeti,Satis_qiymeti,"
                        + "mainCategory,subCategory,2ndSubCategory,Alisin_toplam_deyer,Alis_Tarixi,Barcode from mehsullar"
                        + " where mainCategory =" + "'" + season + "'" + " and subCategory = " + category
                        + " and 2ndSubCategory = " + subCategory + " order by Malin_adi");

                ResultSet rs2 = pres.executeQuery();

                while (rs2.next()) {

                    int id = rs2.getInt("id");
                    String adi = rs2.getString("Malin_adi");
//                    int miq = rs2.getInt("Miqdari");
//                    int satisMiq = rs2.getInt("Satis_miqdari");
                    int qaliqSay = rs2.getInt("Qaliq_say");
                    double AlisQiy = rs2.getDouble("Alis_qiymeti");
                    double satisQiy = rs2.getDouble("Satis_qiymeti");
                    int MainCat = rs2.getInt("mainCategory");
                    int subCat = rs2.getInt("subCategory");
                    int secondCat = rs2.getInt("2ndSubCategory");
                    double total = rs2.getDouble("Alisin_toplam_deyer");
                    Date date = rs2.getDate("Alis_Tarixi");
                    String barcode = rs2.getString("Barcode");

                    pres = con.prepareStatement("insert into qaimemehsullariForSerach (id, productName,QaliqSay,PriceOfBuy, priceOfSale, tarix, Barcode) values (?,?,?,?,?,?,?)");

                    pres.setInt(1, id);
                    pres.setString(2, adi);
//                    pres.setInt(3, miq);
//                    pres.setInt(4, satisMiq);
                    pres.setInt(3, qaliqSay);
                    pres.setDouble(4, AlisQiy);
                    pres.setDouble(5, satisQiy);
                    pres.setDate(6, (java.sql.Date) date);
                    pres.setString(7, barcode);

                    pres.executeUpdate();

                }

            } else {

                pres = con.prepareStatement("select id, Malin_adi,Miqdari,Satis_miqdari,Qaliq_say, Alis_qiymeti,Satis_qiymeti,"
                        + "mainCategory,subCategory,2ndSubCategory,Alisin_toplam_deyer,Alis_Tarixi,Barcode from mehsullar"
                        + " where mainCategory =" + "'" + season + "'" + " and subCategory = " + category
                        + " and 2ndSubCategory = " + subCategory + " and Qaliq_say " + sayForOtbor + " order by Malin_adi");
                ResultSet rs = pres.executeQuery();

                ResultSetMetaData rd = rs.getMetaData();
                a = rd.getColumnCount();
                df = (DefaultTableModel) jTableMehsullar.getModel();
                df.setRowCount(0);

                while (rs.next()) {

                    Vector v2 = new Vector();
                    for (int i = 0; i < a; i++) {

                        int id2 = rs.getInt("id");
                        v2.add(id2);
                        String malinAdi = rs.getString("Malin_adi");
                        v2.add(malinAdi);
                        v2.add(rs.getInt("Qaliq_say"));
                        v2.add(rs.getDouble("Alis_qiymeti"));
                        v2.add(rs.getDouble("Satis_qiymeti"));
                        v2.add(rs.getString("Alis_Tarixi"));
                        v2.add(rs.getString("Barcode"));
                    }
                    df.addRow(v2);

                }

                pres = con.prepareStatement("select id, Malin_adi,Miqdari,Satis_miqdari,Qaliq_say, Alis_qiymeti,Satis_qiymeti,"
                        + "mainCategory,subCategory,2ndSubCategory,Alisin_toplam_deyer,Alis_Tarixi,Barcode from mehsullar"
                        + " where mainCategory =" + "'" + season + "'" + " and subCategory = " + category
                        + " and 2ndSubCategory = " + subCategory + " and Qaliq_say " + sayForOtbor + " order by Malin_adi");

                ResultSet rs2 = pres.executeQuery();

                while (rs2.next()) {

                    int id = rs2.getInt("id");
                    String adi = rs2.getString("Malin_adi");
                    int qaliqSay = rs2.getInt("Qaliq_say");
                    double AlisQiy = rs2.getDouble("Alis_qiymeti");
                    double satisQiy = rs2.getDouble("Satis_qiymeti");
                    int MainCat = rs2.getInt("mainCategory");
                    int subCat = rs2.getInt("subCategory");
                    int secondCat = rs2.getInt("2ndSubCategory");
                    double total = rs2.getDouble("Alisin_toplam_deyer");
                    Date date = rs2.getDate("Alis_Tarixi");
                    String barcode = rs2.getString("Barcode");

                    pres = con.prepareStatement("insert into qaimemehsullariForSerach (id, productName, QaliqSay, PriceOfBuy, priceOfSale, tarix, Barcode) values (?,?,?,?,?,?,?)");

                    pres.setInt(1, id);
                    pres.setString(2, adi);
                    pres.setInt(3, qaliqSay);
                    pres.setDouble(4, AlisQiy);
                    pres.setDouble(5, satisQiy);
                    pres.setDate(6, (java.sql.Date) date);
                    pres.setString(7, barcode);

                    pres.executeUpdate();

                }

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public static void loadForThirdSubCategory(int season, int category, int subCategory, int thirdSubCategory) {

        con = connect();
        try {

            pres = con.prepareStatement("truncate table qaimemehsullariForSerach");
            pres.executeUpdate();

        } catch (Exception ex) {

            ex.printStackTrace();
        }
        int a;
        try {

            boolean yoxla = chcekOtbor.isSelected();

            if (yoxla == true) {

                pres = con.prepareStatement("select id, Malin_adi,Miqdari,Satis_miqdari,Qaliq_say, Alis_qiymeti,Satis_qiymeti,"
                        + "mainCategory,subCategory,2ndSubCategory,Alisin_toplam_deyer,Alis_Tarixi,Barcode from mehsullar"
                        + " where mainCategory = " + season + " and subCategory = " + category
                        + " and 2ndSubCategory = " + subCategory + " and 3ndSubCategory = " + thirdSubCategory + " order by Malin_adi");
                ResultSet rs = pres.executeQuery();

                ResultSetMetaData rd = rs.getMetaData();
                a = rd.getColumnCount();
                df = (DefaultTableModel) jTableMehsullar.getModel();
                df.setRowCount(0);

                while (rs.next()) {

                    Vector v2 = new Vector();
                    for (int i = 0; i < a; i++) {

                        int id2 = rs.getInt("id");
                        v2.add(id2);
                        String malinAdi = rs.getString("Malin_adi");
                        v2.add(malinAdi);
                        v2.add(rs.getInt("Qaliq_say"));
                        v2.add(rs.getDouble("Alis_qiymeti"));
                        v2.add(rs.getDouble("Satis_qiymeti"));
                        v2.add(rs.getString("Alis_Tarixi"));
                        v2.add(rs.getString("Barcode"));
                    }
                    df.addRow(v2);

                }

                pres = con.prepareStatement("select id, Malin_adi,Miqdari,Satis_miqdari,Qaliq_say, Alis_qiymeti,Satis_qiymeti,"
                        + "mainCategory,subCategory,2ndSubCategory,Alisin_toplam_deyer,Alis_Tarixi,Barcode from mehsullar"
                        + " where mainCategory = " + season + " and subCategory = " + category
                        + " and 2ndSubCategory = " + subCategory + " and 3ndSubCategory = " + thirdSubCategory + " order by Malin_adi");

                ResultSet rs2 = pres.executeQuery();

                while (rs2.next()) {

                    int id = rs2.getInt("id");
                    String adi = rs2.getString("Malin_adi");
//                    int miq = rs2.getInt("Miqdari");
//                    int satisMiq = rs2.getInt("Satis_miqdari");
                    int qaliqSay = rs2.getInt("Qaliq_say");
                    double AlisQiy = rs2.getDouble("Alis_qiymeti");
                    double satisQiy = rs2.getDouble("Satis_qiymeti");
                    int MainCat = rs2.getInt("mainCategory");
                    int subCat = rs2.getInt("subCategory");
                    int secondCat = rs2.getInt("2ndSubCategory");
                    double total = rs2.getDouble("Alisin_toplam_deyer");
                    Date date = rs2.getDate("Alis_Tarixi");
                    String barcode = rs2.getString("Barcode");

                    pres = con.prepareStatement("insert into qaimemehsullariForSerach (id, productName,QaliqSay,PriceOfBuy, priceOfSale, tarix, Barcode) values (?,?,?,?,?,?,?)");

                    pres.setInt(1, id);
                    pres.setString(2, adi);
//                    pres.setInt(3, miq);
//                    pres.setInt(4, satisMiq);
                    pres.setInt(3, qaliqSay);
                    pres.setDouble(4, AlisQiy);
                    pres.setDouble(5, satisQiy);
                    pres.setDate(6, (java.sql.Date) date);
                    pres.setString(7, barcode);

                    pres.executeUpdate();

                }

            } else {

                pres = con.prepareStatement("select id, Malin_adi,Miqdari,Satis_miqdari,Qaliq_say, Alis_qiymeti,Satis_qiymeti,"
                        + "mainCategory,subCategory,2ndSubCategory,Alisin_toplam_deyer,Alis_Tarixi,Barcode from mehsullar"
                        + " where mainCategory =" + "'" + season + "'" + " and subCategory = " + category
                        + " and 2ndSubCategory = " + subCategory + " and 3ndSubCategory = " + thirdSubCategory + " and Qaliq_say " + sayForOtbor + " order by Malin_adi");

                ResultSet rs = pres.executeQuery();

                ResultSetMetaData rd = rs.getMetaData();
                a = rd.getColumnCount();
                df = (DefaultTableModel) jTableMehsullar.getModel();
                df.setRowCount(0);

                while (rs.next()) {

                    Vector v2 = new Vector();
                    for (int i = 0; i < a; i++) {

                        int id2 = rs.getInt("id");
                        v2.add(id2);
                        String malinAdi = rs.getString("Malin_adi");
                        v2.add(malinAdi);
                        v2.add(rs.getInt("Qaliq_say"));
                        v2.add(rs.getDouble("Alis_qiymeti"));
                        v2.add(rs.getDouble("Satis_qiymeti"));
                        v2.add(rs.getString("Alis_Tarixi"));
                        v2.add(rs.getString("Barcode"));
                    }
                    df.addRow(v2);

                }

                pres = con.prepareStatement("select id, Malin_adi,Miqdari,Satis_miqdari,Qaliq_say, Alis_qiymeti,Satis_qiymeti,"
                        + "mainCategory,subCategory,2ndSubCategory,Alisin_toplam_deyer,Alis_Tarixi,Barcode from mehsullar"
                        + " where mainCategory =" + "'" + season + "'" + " and subCategory = " + category
                        + " and 2ndSubCategory = " + subCategory + " and 3ndSubCategory = " + thirdSubCategory + " and Qaliq_say " + sayForOtbor + " order by Malin_adi");

                ResultSet rs2 = pres.executeQuery();

                while (rs2.next()) {

                    int id = rs2.getInt("id");
                    String adi = rs2.getString("Malin_adi");
                    int qaliqSay = rs2.getInt("Qaliq_say");
                    double AlisQiy = rs2.getDouble("Alis_qiymeti");
                    double satisQiy = rs2.getDouble("Satis_qiymeti");
                    int MainCat = rs2.getInt("mainCategory");
                    int subCat = rs2.getInt("subCategory");
                    int secondCat = rs2.getInt("2ndSubCategory");
                    double total = rs2.getDouble("Alisin_toplam_deyer");
                    Date date = rs2.getDate("Alis_Tarixi");
                    String barcode = rs2.getString("Barcode");

                    pres = con.prepareStatement("insert into qaimemehsullariForSerach (id, productName, QaliqSay, PriceOfBuy, priceOfSale, tarix, Barcode) values (?,?,?,?,?,?,?)");

                    pres.setInt(1, id);
                    pres.setString(2, adi);
                    pres.setInt(3, qaliqSay);
                    pres.setDouble(4, AlisQiy);
                    pres.setDouble(5, satisQiy);
                    pres.setDate(6, (java.sql.Date) date);
                    pres.setString(7, barcode);

                    pres.executeUpdate();

                }

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public static void loadForGender(String mainCatId, String subCatId) {

        int a;
        try {
            con = connect();
            pres = con.prepareCall("select id, Malin_adi,Miqdari,Satis_miqdari,Qaliq_say, Alis_qiymeti,Satis_qiymeti,mainCategory,subCategory,2ndSubCategory,Alisin_toplam_deyer,Alis_Tarixi,Barcode from mehsullar where subCategory =" + "'" + subCatId + "'" + "and mainCategory = " + "'" + mainCatId + "'");

            ResultSet rs = pres.executeQuery();

            ResultSetMetaData rd = rs.getMetaData();
            a = rd.getColumnCount();
            df = (DefaultTableModel) jTableMehsullar.getModel();
            df.setRowCount(0);

            while (rs.next()) {
                Vector v2 = new Vector();
                v2.add(rs.getInt("id"));
                v2.add(rs.getString("Malin_adi"));
                v2.add(rs.getInt("Qaliq_say"));
                v2.add(rs.getDouble("Alis_qiymeti"));
                v2.add(rs.getDouble("Satis_qiymeti"));
                v2.add(rs.getString("Alis_Tarixi"));
                v2.add(rs.getString("Barcode"));
                df.addRow(v2);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

//   
    public void Load() {

        DefaultMutableTreeNode dmtn = null;
        DefaultMutableTreeNode dmtn2 = null;
        DefaultMutableTreeNode dmtn3 = null;
        DefaultMutableTreeNode dmtn4 = null;

        ResultSet rs;
        ResultSet rs2 = null;
        ResultSet rs23 = null;
        ResultSet rs24 = null;

        con = connect();

        try {

            pres = con.prepareStatement("select categories, id from category");
            rs = pres.executeQuery();

            while (rs.next()) {
                String mainCatName = rs.getString("categories");
                int id = rs.getInt("id");
                dmtn = new DefaultMutableTreeNode(mainCatName);

                pres = con.prepareStatement("select * from subcategory s where s.`index` = " + "'" + id + "'");
                rs2 = pres.executeQuery();
                while (rs2.next()) {
                    String subName = rs2.getString("name");
                    int subId = rs2.getInt("id");
                    dmtn2 = new DefaultMutableTreeNode(subName);
                    dmtn.add(dmtn2);

                    pres = con.prepareStatement("select * from 2ndsubcategory s where s.`index-gender` = " + "'" + subId + "'" + " and s.`index-season` = " + id);
                    rs23 = pres.executeQuery();
                    while (rs23.next()) {
                        String secondSubName2 = rs23.getString("name");
                        int secondSubID = rs23.getInt("id");
                        dmtn3 = new DefaultMutableTreeNode(secondSubName2);
                        dmtn2.add(dmtn3);

                        pres = con.prepareStatement("select * from 3ndsubcategory s where s.`index-3rdSubCat` = " + "'" + secondSubID + "'" + " and s.`index-gender` = " + subId + " and s.`index-season` = " + id);
                        rs24 = pres.executeQuery();
                        while (rs24.next()) {
                            String secondSubName3 = rs24.getString("name");
                            int subCat3ID = rs24.getInt("id");
                            dmtn4 = new DefaultMutableTreeNode(secondSubName3);
                            //dmtn2.add(dmtn23);
                            dmtn3.add(dmtn4);

                        }

                    }

                    //dmtn.add(dmtn2);
                }

                products.add(dmtn);

            }

        } catch (Exception ex) {
            System.out.println(ex);
            String message = ex.getMessage();
            if (message.equals("new child is null")) {
                dmtn2.add(dmtn3);
                dmtn.add(dmtn2);
            }

        }

        JTree tree = new JTree();

        model = (DefaultTreeModel) tree.getModel();
        jTree1.setModel(model);
        model.setRoot(products);
        jTree1.setModel(model);
    }

    public static int updateForThirdSubCategory(int categoryID, int subCategoryID, int secondSubCategoryID, int thirdSubCategoryID, JComponent c) {

        int idProduct = 0;
        try {

            jTableMehsullar = (JTable) c;
            DefaultTableModel df = (DefaultTableModel) ProductCategories.jTableMehsullar.getModel();
            int columnCount = df.getColumnCount();
            int[] selectedRow = jTableMehsullar.getSelectedRows();
            String values = "";
            int column = 0;

            for (int i = 0; i < selectedRow.length; i++) {

                int row = selectedRow[i];
                System.out.println("Bu Array elementidir.." + row);

                values = ProductCategories.jTableMehsullar.getValueAt(row, column).toString();

                idProduct = Integer.parseInt(values);
                System.out.println("Mehsul ID-si ----> " + idProduct);

                con = connect();

                pres = con.prepareStatement("update mehsullar set mainCategory =?, subCategory = ?, 2ndSubCategory = ?, 3ndSubCategory = ? where id = " + idProduct);

                pres.setInt(1, categoryID);
                pres.setInt(2, subCategoryID);
                pres.setInt(3, secondSubCategoryID);
                pres.setInt(4, thirdSubCategoryID);
                pres.executeUpdate();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return idProduct;
    }

    public static int updateForCategoryIDSubcategoryIDSecondSubCategory(int categoryID, int subCategoryID, int secondSubCategoryID, JComponent c) {

        int idProduct = 0;
        try {

            jTableMehsullar = (JTable) c;
            DefaultTableModel df = (DefaultTableModel) ProductCategories.jTableMehsullar.getModel();
            int columnCount = df.getColumnCount();
            int[] selectedRow = jTableMehsullar.getSelectedRows();
            String values = "";
            int column = 0;

            for (int i = 0; i < selectedRow.length; i++) {

                int row = selectedRow[i];
                System.out.println("Bu Array elementidir.." + row);

                values = ProductCategories.jTableMehsullar.getValueAt(row, column).toString();

                idProduct = Integer.parseInt(values);
                System.out.println("Mehsul ID-si ----> " + idProduct);

                con = connect();

                pres = con.prepareStatement("update mehsullar set mainCategory =?, subCategory = ?, 2ndSubCategory = ? where id = " + idProduct);

                pres.setInt(1, categoryID);
                pres.setInt(2, subCategoryID);
                pres.setInt(3, secondSubCategoryID);
                pres.executeUpdate();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return idProduct;

    }

    public static int updateForCategoryIDSubcategoryIDSecondSubCategory(int categoryID, int subCategoryID, int secondSubCategoryID, int thirdSubCategoryID, JComponent c) {

        int idProduct = 0;
        try {

            jTableMehsullar = (JTable) c;
            DefaultTableModel df = (DefaultTableModel) ProductCategories.jTableMehsullar.getModel();
            int columnCount = df.getColumnCount();
            int[] selectedRow = jTableMehsullar.getSelectedRows();
            String values = "";
            int column = 0;

            for (int i = 0; i < selectedRow.length; i++) {

                int row = selectedRow[i];
                System.out.println("Bu Array elementidir.." + row);

                values = ProductCategories.jTableMehsullar.getValueAt(row, column).toString();

                idProduct = Integer.parseInt(values);
                System.out.println("Mehsul ID-si ----> " + idProduct);

                con = connect();

                pres = con.prepareStatement("update mehsullar set mainCategory =?, subCategory = ?, 2ndSubCategory = ?, 3ndsubcategory = ? where id = " + idProduct);

                pres.setInt(1, categoryID);
                pres.setInt(2, subCategoryID);
                pres.setInt(3, secondSubCategoryID);
                pres.setInt(3, thirdSubCategoryID);
                pres.executeUpdate();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return idProduct;

    }

    public static void deleteSelectedItemsFromTable(JComponent c, int action) {

        if (action == TransferHandler.MOVE) {

            JTable srcList = (JTable) c;
            int[] rows = srcList.getSelectedRows();
            DefaultTableModel srcDLModel = (DefaultTableModel) srcList.getModel();
            for (int a = rows.length; a > 0; a--) {
                srcDLModel.removeRow(rows[a - 1]);
                System.out.println("Remove etdik");
            }
        }

    }

    public static void updateForCategoryIDSubcategoryID(int categoryID, int subCategoryID, JComponent c) {

        int idProduct = 0;
        try {

            jTableMehsullar = (JTable) c;
            DefaultTableModel df = (DefaultTableModel) ProductCategories.jTableMehsullar.getModel();
            int columnCount = df.getColumnCount();
            int[] selectedRow = jTableMehsullar.getSelectedRows();
            String values = "";
            int column = 0;

            for (int i = 0; i < selectedRow.length; i++) {

                int row = selectedRow[i];
                System.out.println("Bu Array elementidir.." + row);

                values = ProductCategories.jTableMehsullar.getValueAt(row, column).toString();

                idProduct = Integer.parseInt(values);
                System.out.println("Mehsul ID-si ----> " + idProduct);

                con = connect();

                pres = con.prepareStatement("update mehsullar set mainCategory =?, subCategory = ? where id = " + idProduct);

                pres.setInt(1, categoryID);
                pres.setInt(2, subCategoryID);
                pres.executeUpdate();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public static void updateForCategoryID(int categoryID, JComponent c) {

        int idProduct = 0;
        try {

            jTableMehsullar = (JTable) c;
            DefaultTableModel df = (DefaultTableModel) ProductCategories.jTableMehsullar.getModel();
            int columnCount = df.getColumnCount();
            int[] selectedRow = jTableMehsullar.getSelectedRows();
            String values = "";
            int column = 0;

            for (int i = 0; i < selectedRow.length; i++) {

                int row = selectedRow[i];
                System.out.println("Bu Array elementidir.." + row);

                values = ProductCategories.jTableMehsullar.getValueAt(row, column).toString();

                idProduct = Integer.parseInt(values);
                System.out.println("Mehsul ID-si ----> " + idProduct);

                con = connect();

                pres = con.prepareStatement("update mehsullar set mainCategory =? where id = " + idProduct);

                pres.setInt(1, categoryID);
                pres.executeUpdate();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jPopupMenu2 = new javax.swing.JPopupMenu();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenuItem9 = new javax.swing.JMenuItem();
        OptionsForProductsTable = new javax.swing.JPopupMenu();
        open = new javax.swing.JMenuItem();
        correction = new javax.swing.JMenuItem();
        delete = new javax.swing.JMenuItem();
        ShowOrPrintBarcode = new javax.swing.JMenuItem();
        Cancel = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableMehsullar = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        lblImage = new javax.swing.JLabel();
        panelTreeView = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTree1 = new javax.swing.JTree();
        chcekOtbor = new javax.swing.JCheckBox();
        panelSearch = new javax.swing.JPanel();
        txtSearch = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jButton7 = new javax.swing.JButton();
        jComboBox2 = new javax.swing.JComboBox<>();

        jMenuItem1.setText("Kateqoriya elave et");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItem1);

        jMenuItem4.setText("Adini Deyisdir");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItem4);

        jMenuItem2.setText("Sil");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItem2);

        jMenuItem3.setText("Legv et");
        jPopupMenu1.add(jMenuItem3);

        jMenuItem5.setText("Kateqoriya elave et");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jPopupMenu2.add(jMenuItem5);

        jMenuItem6.setText("Mehsul elave et");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jPopupMenu2.add(jMenuItem6);

        jMenuItem7.setText("Adini Deyisdir");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jPopupMenu2.add(jMenuItem7);

        jMenuItem8.setText("Sil");
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        jPopupMenu2.add(jMenuItem8);

        jMenuItem9.setText("Legv et");
        jPopupMenu2.add(jMenuItem9);

        open.setText("Aç");
        open.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openActionPerformed(evt);
            }
        });
        OptionsForProductsTable.add(open);

        correction.setText("Düzəliş et");
        correction.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                correctionActionPerformed(evt);
            }
        });
        OptionsForProductsTable.add(correction);

        delete.setText("Sil");
        delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteActionPerformed(evt);
            }
        });
        OptionsForProductsTable.add(delete);

        ShowOrPrintBarcode.setText("Barkodu göstər/Çap et");
        ShowOrPrintBarcode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ShowOrPrintBarcodeActionPerformed(evt);
            }
        });
        OptionsForProductsTable.add(ShowOrPrintBarcode);

        Cancel.setText("Bağla");
        OptionsForProductsTable.add(Cancel);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                formKeyReleased(evt);
            }
        });

        jPanel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jPanel1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jPanel1KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jPanel1KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jPanel1KeyTyped(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("Mehsullar siyahisi");

        jTableMehsullar = new JTable()

        {
            public boolean isCellEditable(int row, int column)

            {
                for(int i =0; i< jTableMehsullar.getRowCount(); i++)
                {
                    if(row == i)
                    {
                        return false;
                    }
                }
                return false;
            }
        };
        jTableMehsullar.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jTableMehsullar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Malin Adi", "Qaliq Say", "Alis Qiymeti", "Satis Qiymeti", "Tarix", "Barcode"
            }
        ));
        jTableMehsullar.setRowHeight(25);
        jTableMehsullar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableMehsullarMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jTableMehsullarMouseReleased(evt);
            }
        });
        jTableMehsullar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTableMehsullarKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(jTableMehsullar);
        if (jTableMehsullar.getColumnModel().getColumnCount() > 0) {
            jTableMehsullar.getColumnModel().getColumn(0).setMinWidth(30);
            jTableMehsullar.getColumnModel().getColumn(0).setPreferredWidth(50);
            jTableMehsullar.getColumnModel().getColumn(0).setMaxWidth(100);
            jTableMehsullar.getColumnModel().getColumn(1).setMinWidth(0);
            jTableMehsullar.getColumnModel().getColumn(1).setPreferredWidth(350);
            jTableMehsullar.getColumnModel().getColumn(1).setMaxWidth(500);
            jTableMehsullar.getColumnModel().getColumn(2).setMinWidth(50);
            jTableMehsullar.getColumnModel().getColumn(2).setPreferredWidth(70);
            jTableMehsullar.getColumnModel().getColumn(2).setMaxWidth(150);
            jTableMehsullar.getColumnModel().getColumn(3).setMinWidth(100);
            jTableMehsullar.getColumnModel().getColumn(3).setPreferredWidth(70);
            jTableMehsullar.getColumnModel().getColumn(3).setMaxWidth(150);
            jTableMehsullar.getColumnModel().getColumn(4).setMinWidth(100);
            jTableMehsullar.getColumnModel().getColumn(4).setPreferredWidth(100);
            jTableMehsullar.getColumnModel().getColumn(4).setMaxWidth(150);
            jTableMehsullar.getColumnModel().getColumn(5).setMinWidth(100);
            jTableMehsullar.getColumnModel().getColumn(5).setPreferredWidth(100);
            jTableMehsullar.getColumnModel().getColumn(5).setMaxWidth(150);
            jTableMehsullar.getColumnModel().getColumn(6).setMinWidth(100);
            jTableMehsullar.getColumnModel().getColumn(6).setPreferredWidth(100);
            jTableMehsullar.getColumnModel().getColumn(6).setMaxWidth(150);
        }

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel2.setText("Kateqoriyalar");

        lblImage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblImage.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jTree1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jTree1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("root");
        jTree1.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        jTree1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jTree1.setName(""); // NOI18N
        jTree1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTree1MouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jTree1MouseReleased(evt);
            }
        });
        jTree1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTree1KeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(jTree1);

        javax.swing.GroupLayout panelTreeViewLayout = new javax.swing.GroupLayout(panelTreeView);
        panelTreeView.setLayout(panelTreeViewLayout);
        panelTreeViewLayout.setHorizontalGroup(
            panelTreeViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTreeViewLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 349, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelTreeViewLayout.setVerticalGroup(
            panelTreeViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTreeViewLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 491, Short.MAX_VALUE)
                .addContainerGap())
        );

        chcekOtbor.setText("Отбор");

        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout panelSearchLayout = new javax.swing.GroupLayout(panelSearch);
        panelSearch.setLayout(panelSearchLayout);
        panelSearchLayout.setHorizontalGroup(
            panelSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelSearchLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(txtSearch, javax.swing.GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE))
        );
        panelSearchLayout.setVerticalGroup(
            panelSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelSearchLayout.createSequentialGroup()
                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel11.setText("Mehsul tesviri");

        jButton7.setText("Print Barcode");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jComboBox2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jComboBox2.setForeground(new java.awt.Color(51, 51, 51));
        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ada göre", "Barkoda göre" }));
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(16, 16, 16)
                                .addComponent(jLabel2))
                            .addComponent(panelTreeView, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblImage, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 907, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(160, 160, 160)
                                .addComponent(jLabel1))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton7)
                                .addGap(18, 18, 18)
                                .addComponent(chcekOtbor, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(panelSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(23, 23, 23)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(chcekOtbor)
                                .addComponent(jButton7))
                            .addComponent(panelSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(94, 94, 94))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(panelTreeView, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel11)
                        .addGap(8, 8, 8)
                        .addComponent(lblImage, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(22, 22, 22))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents


    private void jTree1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTree1MouseClicked
        mouseClicked();
    }//GEN-LAST:event_jTree1MouseClicked

    public void updateCategoryName() {

        DefaultMutableTreeNode theLastComponent = (DefaultMutableTreeNode) jTree1.getSelectionPath().getLastPathComponent();

        try {

            String node1 = theLastComponent.getUserObject().toString();
            TreeNode node2 = theLastComponent.getParent();
            if (node2 != null) {

                TreeNode node3 = node2.getParent();
                if (node3 != null) {

                    TreeNode node4 = node3.getParent();

                    if (node4 != null) {

                        TreeNode node5 = node4.getParent();

                        if (node5 != null) {

                            TreeNode node6 = node5.getParent();
                            if (node6 != null) {

                            } else {

                                updateCategoryNameDialogForm newCatName = new updateCategoryNameDialogForm(this, true);
                                newCatName.setVisible(true);
                                if (newCategoryName.isBlank() || newCategoryName.isEmpty()) {

                                } else {

                                    theLastComponent.setUserObject(newCategoryName);
                                    model = (DefaultTreeModel) jTree1.getModel();
                                    model.reload();

                                    String gender = node3.toString();
                                    String season = node4.toString();

                                    String SecondsubCatName = node2.toString();
                                    con = connect();

                                    pres = con.prepareStatement("select * from category where categories =" + "'" + season + "'");
                                    rs = pres.executeQuery();
                                    rs.next();
                                    int seasonID = rs.getInt("id");

                                    pres = con.prepareStatement("select * from subcategory sss where name =" + "'" + gender + "'" + " and sss.index = " + seasonID);
                                    rs = pres.executeQuery();
                                    rs.next();
                                    int genderID = rs.getInt("id");

                                    pres = con.prepareStatement("select * from 2ndsubcategory  where name = " + "'" + SecondsubCatName + "'" + " and `index-gender` =" + genderID + " and `index-season` = " + seasonID);
                                    rs = pres.executeQuery();
                                    rs.next();

                                    int SecondSubCatID = rs.getInt("id");

                                    pres = con.prepareStatement("update 3ndsubcategory  set name =? where name = " + "'" + node1 + "'" + " and `index-gender` =" + genderID + " and `index-season` = " + seasonID + " and `index-3rdSubCat` = " + SecondSubCatID);
                                    pres.setString(1, newCategoryName);
                                    pres.executeUpdate();

                                }

                            }

                        } else {

                            updateCategoryNameDialogForm newCatName = new updateCategoryNameDialogForm(this, true);
                            newCatName.setVisible(true);
                            if (newCategoryName.isBlank() || newCategoryName.isEmpty()) {

                            } else {

                                theLastComponent.setUserObject(newCategoryName);
                                model = (DefaultTreeModel) jTree1.getModel();
                                model.reload();

                                String subCatName = node2.toString();
                                con = connect();
                                pres = con.prepareStatement("select * from subcategory where name = " + "'" + subCatName + "'");
                                rs = pres.executeQuery();
                                rs.next();

                                int catID = rs.getInt("id");

                                pres = con.prepareStatement("update 2ndsubcategory s set s.name =? where s.name = " + "'" + node1 + "'" + " and s.index =" + catID);
                                pres.setString(1, newCategoryName);
                                pres.executeUpdate();

                            }

                        }

                    } else {

                        updateCategoryNameDialogForm newCatName = new updateCategoryNameDialogForm(this, true);
                        newCatName.setVisible(true);

                        if (newCategoryName.isBlank() || newCategoryName.isEmpty()) {

                        } else {

                            theLastComponent.setUserObject(newCategoryName);
                            model = (DefaultTreeModel) jTree1.getModel();
                            model.reload();

                            String catName = node2.toString();
                            con = connect();
                            pres = con.prepareStatement("select * from category where categories = " + "'" + catName + "'");
                            rs = pres.executeQuery();
                            rs.next();

                            int catID = rs.getInt("id");

                            pres = con.prepareStatement("update subcategory s set s.name =? where s.name = " + "'" + node1 + "'" + " and s.index =" + catID);
                            pres.setString(1, newCategoryName);
                            pres.executeUpdate();

                        }

                    }

                } else {
                    System.out.println("Node2 bosdur");
                    updateCategoryNameDialogForm newCatName = new updateCategoryNameDialogForm(this, true);
                    newCatName.setVisible(true);

                    if (newCategoryName.isBlank() || newCategoryName.isEmpty()) {

                    } else {

                        theLastComponent.setUserObject(newCategoryName);
                        model = (DefaultTreeModel) jTree1.getModel();
                        model.reload();

                        con = connect();
                        pres = con.prepareStatement("update category set categories = ? where categories = " + "'" + node1 + "'");
                        pres.setString(1, newCategoryName);
                        pres.executeUpdate();

                    }

                }
            } else {

                updateCategoryNameDialogForm newCatName = new updateCategoryNameDialogForm(this, true);
                newCatName.setVisible(true);

                if (newCategoryName.isBlank() || newCategoryName.isEmpty()) {

                } else {

                    theLastComponent.setUserObject(newCategoryName);
                    model = (DefaultTreeModel) jTree1.getModel();
                    model.reload();

                    con = connect();
                    pres = con.prepareStatement("update mainbasenames set mainBaseName = ? where id = 1");
                    pres.setString(1, newCategoryName);
                    pres.executeUpdate();

                }

            }

        } catch (Exception ex) {

            String errorMessage = ex.getMessage();

            if (errorMessage.equals("Cannot invoke \"String.isBlank()\" because \"com.mycompany.qarisiqmallar.veneramarket.TreeView1.newCategoryName\" is null")) {

            } else {

                ex.printStackTrace();
            }

        }

    }

    public void deleteCategoryName() {

        DefaultMutableTreeNode theLastComponent = (DefaultMutableTreeNode) jTree1.getSelectionPath().getLastPathComponent();

        try {

            String node1 = theLastComponent.getUserObject().toString();
            TreeNode node2 = theLastComponent.getParent();
            if (node2 != null) {

                TreeNode node3 = node2.getParent();
                if (node3 != null) {

                    TreeNode node4 = node3.getParent();

                    if (node4 != null) {

                        TreeNode node5 = node4.getParent();

                        if (node5 != null) {

                            TreeNode node6 = node5.getParent();
                            if (node6 != null) {

                            } else {
                                model = (DefaultTreeModel) jTree1.getModel();
                                model.removeNodeFromParent(theLastComponent);
                                model.reload();

                                String mainCatNAme = node4.toString();
                                con = connect();
                                pres = con.prepareStatement("select * from category ss where ss.categories = " + "'" + mainCatNAme + "'");
                                rs = pres.executeQuery();
                                rs.next();

                                int mainCatID = rs.getInt("id");

                                String genderName = node3.toString();
                                con = connect();
                                pres = con.prepareStatement("select * from subcategory s where name = " + "'" + genderName + "'" + " and s.index = " + mainCatID);
                                rs = pres.executeQuery();
                                rs.next();

                                int genderID = rs.getInt("id");

                                String SecondsubCatName = node2.toString();
                                con = connect();
                                pres = con.prepareStatement("select * from 2ndsubcategory where name = " + "'" + SecondsubCatName + "'" + " and `index-season` =" + mainCatID + " and `index-gender` = " + genderID);
                                rs = pres.executeQuery();
                                rs.next();

                                int SecondSubCatID = rs.getInt("id");

                                pres = con.prepareStatement("delete from 3ndsubcategory s where s.name = " + "'" + node1 + "'" + " and `index-3rdSubCat` =" + SecondSubCatID + " and `index-season` =" + mainCatID + " and `index-gender` = " + genderID);
                                pres.executeUpdate();

                                JOptionPane.showMessageDialog(this, "Kateqoriya ugurla silindi");

                            }

                        } else {

                            model = (DefaultTreeModel) jTree1.getModel();
                            model.removeNodeFromParent(theLastComponent);
                            model.reload();

                            String mainCatNAme = node3.toString();
                            con = connect();
                            pres = con.prepareStatement("select * from category ss where ss.categories = " + "'" + mainCatNAme + "'");
                            rs = pres.executeQuery();
                            rs.next();

                            int mainCatID = rs.getInt("id");

                            String genderName = node2.toString();
                            con = connect();
                            pres = con.prepareStatement("select * from subcategory s where name = " + "'" + genderName + "'" + " and s.index = " + mainCatID);
                            rs = pres.executeQuery();
                            rs.next();

                            int genderID = rs.getInt("id");

                            pres = con.prepareStatement("delete from 2ndsubcategory where name = " + "'" + node1 + "'" + " and `index-season` =" + mainCatID + " and `index-gender` = " + genderID);
                            pres.executeUpdate();

                            JOptionPane.showMessageDialog(this, "Kateqoriya ugurla silindi");

                        }

                    } else {

                        model = (DefaultTreeModel) jTree1.getModel();
                        model.removeNodeFromParent(theLastComponent);
                        model.reload();

                        String catName = node2.toString();
                        con = connect();
                        pres = con.prepareStatement("select * from category where categories = " + "'" + catName + "'");
                        rs = pres.executeQuery();
                        rs.next();

                        int catID = rs.getInt("id");

                        pres = con.prepareStatement("delete from subcategory s where s.name = " + "'" + node1 + "'" + " and s.index =" + catID);
                        pres.executeUpdate();

                        JOptionPane.showMessageDialog(this, "Kateqoriya ugurla silindi");

                    }

                } else {
                    model = (DefaultTreeModel) jTree1.getModel();
                    model.removeNodeFromParent(theLastComponent);
                    model.reload();

                    con = connect();
                    pres = con.prepareStatement("delete from category where categories = " + "'" + node1 + "'");
                    pres.executeUpdate();

                    JOptionPane.showMessageDialog(this, "Kateqoriya ugurla silindi");

                }
            } else {

                model = (DefaultTreeModel) jTree1.getModel();
                model.removeNodeFromParent(theLastComponent);
                model.reload();

                con = connect();
                pres = con.prepareStatement("delete from mainbasenames where id = 1");
                pres.executeUpdate();

                JOptionPane.showMessageDialog(this, "Kateqoriya ugurla silindi");

            }

        } catch (Exception ex) {
            ex.printStackTrace();

        }

    }

    public static void mouseClicked() {

        boolean yoxla = chcekOtbor.isSelected();
        if (yoxla == true) {
            sayForOtbor = "<=0";
            System.out.println(sayForOtbor);
        } else {

            if (yoxla == false) {
                sayForOtbor = " > 0";
                System.out.println(sayForOtbor);
            }
        }

        try {
            DefaultMutableTreeNode theLastComponent = (DefaultMutableTreeNode) jTree1.getSelectionPath().getLastPathComponent();

            String node1 = theLastComponent.getUserObject().toString();

            try {
                TreeNode node2 = theLastComponent.getParent();
                if (node2.toString() != null) {
                    try {
                        TreeNode node3 = node2.getParent();
                        if (node3 != null) {

                            TreeNode node4 = node3.getParent();
                            if (node4 != null) {

                                TreeNode node5 = node4.getParent();
                                if (node5 != null) {

                                    TreeNode node6 = node5.getParent();
                                    if (node6 != null) {

                                    } else {

                                        con = connect();
                                        pres = con.prepareStatement("select * from category where categories = " + "'" + node4 + "'");
                                        rs = pres.executeQuery();
                                        rs.next();
                                        int categoryID = rs.getInt("id");

                                        pres = con.prepareStatement("select * from subcategory where name = " + "'" + node3 + "'" + " and `index` =" + categoryID);
                                        rs = pres.executeQuery();
                                        rs.next();
                                        int subCategoryID = rs.getInt("id");

                                        pres = con.prepareStatement("select * from 2ndsubcategory where name = " + "'" + node2 + "'" + " and `index-gender` = " + subCategoryID + " and `index-season` = " + categoryID);
                                        rs = pres.executeQuery();
                                        rs.next();
                                        int secondSubCategoryID = rs.getInt("id");

                                        pres = con.prepareStatement("select * from 3ndsubcategory where name = " + "'" + node1 + "'" + " and `index-gender` = " + subCategoryID + " and `index-season` = " + categoryID + " and `index-3rdSubCat` = " + secondSubCategoryID);
                                        rs = pres.executeQuery();
                                        rs.next();
                                        int thirdSubCategoryID = rs.getInt("id");

                                        loadForThirdSubCategory(categoryID, subCategoryID, secondSubCategoryID, thirdSubCategoryID);

                                    }

                                } else {

                                    con = connect();
                                    pres = con.prepareStatement("select * from category where categories = " + "'" + node3 + "'");
                                    rs = pres.executeQuery();
                                    rs.next();
                                    int categoryID = rs.getInt("id");

                                    pres = con.prepareStatement("select * from subcategory where name = " + "'" + node2 + "'" + " and `index` =" + categoryID);
                                    rs = pres.executeQuery();
                                    rs.next();
                                    int subCategoryID = rs.getInt("id");

                                    pres = con.prepareStatement("select * from 2ndsubcategory where name = " + "'" + node1 + "'" + " and `index-gender` = " + subCategoryID + " and `index-season` = " + categoryID);
                                    rs = pres.executeQuery();
                                    rs.next();
                                    int secondSubCategoryID = rs.getInt("id");

                                    loadForSecondSubCategory(categoryID, subCategoryID, secondSubCategoryID);

                                }

                            } else {
                                // bura
                                String subCatId = null;
                                String mainCategoryId = null;
                                String category2 = node2.toString();

//                                int category4 = node2.getChildCount();
//                                TreeNode category3 = node2.getChildAt(1);
                                pres = con.prepareStatement("select * from category where categories = " + "'" + category2 + "'");
                                ResultSet rsMainCatID = pres.executeQuery();
                                while (rsMainCatID.next()) {
                                    mainCategoryId = rsMainCatID.getString("id");

                                }

                                String category = node1.toString();

                                pres = con.prepareStatement("select * from subcategory where name = " + "'" + category + "'" + " and `index` =" + mainCategoryId);
                                ResultSet rsSubCatID = pres.executeQuery();
                                while (rsSubCatID.next()) {
                                    subCatId = rsSubCatID.getString("id");
                                }

                                loadForGender(mainCategoryId, subCatId);
                            }

                        } else {
                            String season = node1.toString();
                            try {

                                pres = con.prepareCall("select * from category where categories = " + "'" + season + "'");
                                ResultSet rsCatID = pres.executeQuery();
                                while (rsCatID.next()) {
                                    String mainCategoryId = rsCatID.getString("id");
                                    loadForSeasons(mainCategoryId);
                                }

                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }

                        }

                    } catch (Exception ex) {
                        ex.printStackTrace();
                        TreeNode node3 = node2.getParent();
                        loadForSeasons(node1);
                    }

                }

            } catch (Exception ex) {
                loadAllProducts();
            }

        } catch (Exception ex) {
            System.out.println("LastTreePathCompanent tapilmadi..");

        }

    }


    private void jTree1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTree1MouseReleased

        if (SwingUtilities.isRightMouseButton(evt)) {
            try {
                DefaultMutableTreeNode treeModel = (DefaultMutableTreeNode) jTree1.getSelectionPath().getLastPathComponent();

                if (treeModel.toString() != null) {

                    if (treeModel.getChildCount() == 0) {
                        jPopupMenu2.show(jTree1, evt.getX(), evt.getY());
                    } else {
                        jPopupMenu1.show(jTree1, evt.getX(), evt.getY());
                    }

                } else {
                    System.out.println("olmadi");
                }

            } catch (Exception ex) {
                System.out.println("Olmadi");
            }

        }

    }//GEN-LAST:event_jTree1MouseReleased
    public static String veriable;

    public void addCategory() {

        try {
            DefaultMutableTreeNode treeModel = (DefaultMutableTreeNode) jTree1.getSelectionPath().getLastPathComponent();
            TreeNode node2 = treeModel.getParent();
            if (node2 == null) {
                try {
                    veriable = treeModel.toString();
                    System.out.println("Bura girdim");
                    AddCategoryDialogForm addcat = new AddCategoryDialogForm(this, true);
                    addcat.subName = veriable;
                    //addcat.secondSubName = node2.toString();
                    addcat.setVisible(true);
                    if (addcat.subName.equals(veriable)) {

                    } else {

                        reloadTreeViewForAddCategory(treeModel);
                    }

                } catch (Exception ex) {
                    String message = ex.getMessage();
                    if (message.equals("Cannot invoke \"String.equals(Object)\" because \"addcat.subName\" is null")) {

                    } else {
                        ex.printStackTrace();
                    }
                }

            }

            TreeNode node3 = node2.getParent();
            if (node3 == null) {
                try {
                    veriable = treeModel.toString();
                    AddSubCategoryDialogForm addcat = new AddSubCategoryDialogForm(this, true);
                    addcat.subName = veriable;
                    addcat.secondSubName = treeModel.toString();
                    addcat.setVisible(true);
                    if (addcat.subName.equals(veriable)) {
                    } else {
                        reloadTreeViewForAddCategory(treeModel);
                    }
                } catch (Exception ex) {
                    String message = ex.getMessage();
                    if (message.equals("Cannot invoke \"String.equals(Object)\" because \"addcat.subName\" is null")) {
                    } else {
                        ex.printStackTrace();
                    }
                }

            }
            TreeNode node4 = node3.getParent();
            if (node4 == null) {
                try {

                    veriable = treeModel.toString();
                    AddSecondSubCategoryDialogForm addcat = new AddSecondSubCategoryDialogForm(this, true);
                    addcat.subName = veriable;
                    addcat.secondSubName = treeModel.toString();
                    addcat.thirdSubCat = node2.toString();
                    addcat.setVisible(true);
                    if (addcat.subName.equals(veriable)) {
                    } else {
                        reloadTreeViewForAddCategory(treeModel);
                    }
                } catch (Exception ex) {
                    String message = ex.getMessage();
                    if (message.equals("Cannot invoke \"String.equals(Object)\" because \"addcat.subName\" is null")) {
                    } else {
                        ex.printStackTrace();
                    }
                }
            }
            TreeNode node5 = node4.getParent();
            if (node5 == null) {

                try {

                    veriable = treeModel.toString();
                    AddThirdSubCategoryDialogForm addcat = new AddThirdSubCategoryDialogForm(this, true);
                    addcat.subName = veriable;
                    addcat.secondSubName = treeModel.toString();
                    addcat.thirdSubCat = node2.toString();
                    addcat.fourthSubCat = node3.toString();
                    addcat.setVisible(true);
                    if (addcat.subName.equals(veriable)) {

                    } else {

                        reloadTreeViewForAddCategory(treeModel);
                    }

                } catch (Exception ex) {
                    String message = ex.getMessage();
                    if (message.equals("Cannot invoke \"String.equals(Object)\" because \"addcat.subName\" is null")) {
                    } else {
                        ex.printStackTrace();
                    }
                }

            } else {
                JOptionPane.showMessageDialog(this, "Siz 5-ci kateqoriya limitine çatdınız!", "DIQQET!", HEIGHT);
            }
            veriable = treeModel.toString();

        } catch (Exception ex) {
            //ex.printStackTrace();
        }

    }

    public void reloadTreeViewForAddCategory(DefaultMutableTreeNode treeModel) {

        DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(veriable);
        treeModel.add(newNode);
        model = (DefaultTreeModel) jTree1.getModel();
        model.reload();
        System.out.println("Model ugurla yenilendi");

    }

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        addCategory();

    }//GEN-LAST:event_jMenuItem1ActionPerformed

    String node1;
    String stNode2;
    String stNode3;
    String stNode4;
    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed

        Ok();
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    public void Ok() {

        try {
            NewProduct obj = new NewProduct(this, true);
            DefaultMutableTreeNode treeModel = (DefaultMutableTreeNode) jTree1.getSelectionPath().getLastPathComponent();
            node1 = treeModel.getUserObject().toString();
            TreeNode node2 = treeModel.getParent();
            stNode2 = node2.toString();
            TreeNode node3 = node2.getParent();

            if (node3 != null) {
                stNode3 = node2.getParent().toString();
                TreeNode node4 = node3.getParent();

                if (node4 != null) {
                    stNode4 = node3.getParent().toString();
                    TreeNode node5 = node4.getParent();

                    if (node5 != null) {
                        stNode4 = node3.getParent().toString();
                        TreeNode node6 = node5.getParent();

                        if (node6 != null) {
                            stNode4 = node3.getParent().toString();
                            TreeNode node7 = node6.getParent();
                        } else {

                            System.out.println("Birinci 7 oldu");
                            obj.mainCat = stNode4;
                            obj.subCat = stNode3;
                            obj.secondSubCat = stNode2;
                            obj.thirdSubCat = node1;
                            obj.optionForAddingProcess = "addToTheThirdSubCat";
                            mouseClicked();
                            obj.setVisible(true);

                        }
                    } else {

                        System.out.println("Men duz duwdum");
                        obj.mainCat = stNode4;
                        obj.subCat = stNode3;
                        obj.secondSubCat = stNode2;
                        obj.thirdSubCat = node1;
                        obj.optionForAddingProcess = "addToTheSecondSubCat";
                        mouseClicked();
                        obj.setVisible(true);

                    }
                } else {
                    obj.mainCat = stNode4;
                    obj.subCat = stNode3;
                    obj.secondSubCat = stNode2;
                    obj.thirdSubCat = node1;
                    obj.optionForAddingProcess = "addToTheSubCat";
                    mouseClicked();
                    obj.setVisible(true);

                }

            } else {
                obj.mainCat = stNode4;
                obj.subCat = stNode3;
                obj.secondSubCat = stNode2;
                obj.thirdSubCat = node1;
                obj.optionForAddingProcess = "addToTheMainCat";
                mouseClicked();
                obj.setVisible(true);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        addCategory();
    }//GEN-LAST:event_jMenuItem5ActionPerformed
    int sayAlinanMallar = 1;
    public int id;
    public String name;
    public double priceOfSale;

    public Integer identification(int id) {

        Integer i = 0;

        try ( Connection c = connect()) {
            GetProduct getProduct = new GetProduct(this, true);
            int numberOfProduct = (getProduct.number);
            String numberOfProduct2 = (getProduct.txtMiqdar.getText());
            int ss = numberOfProduct;
            Statement stmt = c.createStatement();
            stmt.execute("select * from qaimemehsullaritreeview");
            ResultSet rs = stmt.getResultSet();

            while (rs.next()) {
                int id1 = rs.getInt("id");
                int miqdari = rs.getInt("numberOfProduct");
                double umumiMebleg = rs.getDouble("total");
                System.out.println(id1);
                if (id1 == id) {

                    try {
                        pres = c.prepareStatement("update qaimemehsullaritreeview set numberOfProduct = ? where id =?");

                        pres.setInt(1, miqdari + ss);
//                      pres.setDouble(2, umumiMebleg + qiymeti);
                        pres.setInt(2, id);
                        pres.executeUpdate();
                        i++;
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }

                }

            }

        } catch (Exception ex) {

            ex.printStackTrace();
        }

        return i;

    }

    public void deteleProductFromList() {

        df = (DefaultTableModel) jTableMehsullar.getModel();

        int selected = jTableMehsullar.getSelectedRow();

        int id = Integer.parseInt(df.getValueAt(selected, 0).toString());

        try {

            Connection c = connect();

            String query = "select m.* from mehsullar m where m.id = " + id;

            pres = c.prepareStatement(query);

            pres.executeQuery();

            ResultSet rs = pres.getResultSet();

            rs.next();
            int idProduct = rs.getInt("id");
            String mehsulunAdi = rs.getString("Malin_adi");

            int cavab = JOptionPane.showConfirmDialog(this, "Silinən məhsulu geri qaytarmaq mümkün olmayacaq! \n" + mehsulunAdi + "-adli mehsulu silmək istədiyinizdən əminsiniz?", "Diqqət!", JOptionPane.YES_NO_CANCEL_OPTION);

            if (cavab == 0) {

                Connection c2 = connect();
                pres = c2.prepareStatement("delete from mehsullar where id =  " + idProduct);

                pres.executeUpdate();

                df.removeRow(selected);

                JOptionPane.showMessageDialog(this, " " + mehsulunAdi + "-adli mehsul silindi");

                df = (DefaultTableModel) jTableMehsullar.getModel();

            }
            if (cavab == 1) {

            }
            if (cavab == 2) {

            } else {

            }
        } catch (Exception ex) {
            ex.printStackTrace();

        }

    }

    public void setTheCorrectNumberOfRow() {

        int sayBura = 1;
        int sayIlk = 1;
        int yoxla = 0;
        int saySiraSayi = 1;
        try {

            con = connect();
            pres = con.prepareStatement("select * from qaimemehsullariTreeView");
            rs = pres.executeQuery();

            while (rs.next()) {
                int ilk = rs.getInt("say");

                if (ilk == saySiraSayi) {

                    pres = con.prepareStatement("select * from qaimemehsullariTreeView");
                    rs = pres.executeQuery();

                    while (rs.next()) {

                        if (yoxla == 0) {
                            pres = con.prepareStatement("update qaimemehsullariTreeView set say = " + sayBura + " where say =" + sayBura);
                            pres.executeUpdate();
                            sayBura++;
                            yoxla++;
                            saySiraSayi++;
                        } else {
                            int sayYoxla = rs.getInt("say");
                            pres = con.prepareStatement("update qaimemehsullariTreeView set say = " + sayBura + " where say =" + sayYoxla);
                            pres.executeUpdate();
                            sayBura++;
                            yoxla++;
                            saySiraSayi++;
                        }

                    }

                } else {

                    pres = con.prepareStatement("select * from qaimemehsullariTreeView");
                    rs = pres.executeQuery();
                    rs.next();
                    pres = con.prepareStatement("update qaimemehsullariTreeView set say = " + sayIlk + " where say = " + ilk);
                    pres.executeUpdate();
                    setTheCorrectNumberOfRow();
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    DefaultListModel modelList = new DefaultListModel();

    public String addContractNumber(String nameOfClient) {

        String contractNumber = null;
        try {
            //String nameOfClient = txtNameAndSurename.getText();
            Connection c = connect();

            String query = "SELECT c.NumberOfContract FROM `clients` c where c.NameAndSurename = " + "'" + nameOfClient + "'";
            pres = c.prepareStatement(query);
            ResultSet rs = pres.executeQuery();

            rs.next();
            contractNumber = rs.getString("NumberOfContract");
            //txtCommentary.setText(contractNumber);

        } catch (Exception ex) {
            Logger.getLogger(ProductCategories.class.getName()).log(Level.SEVERE, null, ex);
        }
        return contractNumber;
    }


    private void jPanel1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPanel1KeyReleased

        System.out.println("Beli qaqaw sen deyendi ele");


    }//GEN-LAST:event_jPanel1KeyReleased

    private void jPanel1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPanel1KeyPressed
        System.out.println("Beli qaqaw sen deyendi ele");
    }//GEN-LAST:event_jPanel1KeyPressed

    private void jPanel1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPanel1KeyTyped
        System.out.println("Beli qaqaw sen deyendi ele");
    }//GEN-LAST:event_jPanel1KeyTyped

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased

        txtSearch.requestFocus();
        String text = txtSearch.getText();
        int typeOfSerach = jComboBox2.getSelectedIndex();

        int a;
        try {

            Connection c = connect();
            if (typeOfSerach == 0) {
                pres = con.prepareCall("select * from mehsullar m where m.Malin_adi like " + "'" + "%" + text + "%" + "'");
            } else {
                pres = con.prepareCall("select * from mehsullar m where m.Barcode = " + "'"+text+"'");

            }

            ResultSet rs2 = pres.executeQuery();

            ResultSetMetaData rd = rs2.getMetaData();
            a = rd.getColumnCount();
            df = (DefaultTableModel) jTableMehsullar.getModel();
            df.setRowCount(0);

            while (rs2.next()) {
                Vector v2 = new Vector();
                for (int i = 0; i < a; i++) {
                    int id2 = rs2.getInt("id");
                    v2.add(id2);
                    String malinAdi = rs2.getString("Malin_adi");
                    v2.add(malinAdi);
                    v2.add(rs2.getInt("Qaliq_say"));
                    v2.add(rs2.getDouble("Alis_qiymeti"));
                    v2.add(rs2.getDouble("Satis_qiymeti"));
                    v2.add(rs2.getString("Alis_Tarixi"));
                    v2.add(rs2.getString("Barcode"));
                }
                df.addRow(v2);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }//GEN-LAST:event_txtSearchKeyReleased

    private void jTableMehsullarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTableMehsullarKeyReleased

        panelSearch.setVisible(true);
        txtSearch.requestFocus();

    }//GEN-LAST:event_jTableMehsullarKeyReleased


    private void jTableMehsullarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableMehsullarMouseClicked

        int clickSayi = evt.getClickCount();

        if (clickSayi == 2) {

            // open();
        } else {
            if (clickSayi == 1) {

                panelSearch.setVisible(false);
                txtSearch.setText("");
                df = (DefaultTableModel) jTableMehsullar.getModel();

                int selected = jTableMehsullar.getSelectedRow();

                id = Integer.parseInt(df.getValueAt(selected, 0).toString());

                try {
                    con = connect();
                    pres = con.prepareStatement("select * from mehsullar m where m.id =? ");
                    pres.setInt(1, id);
                    rs = pres.executeQuery();
                    if (rs.next()) {
                        byte[] imagedata = rs.getBytes("imagePath");
                        format = new ImageIcon(imagedata);
                        Image mm = format.getImage();
                        Image img2 = mm.getScaledInstance(lblImage.getWidth(), lblImage.getHeight(), Image.SCALE_SMOOTH);
                        ImageIcon image = new ImageIcon(img2);
                        lblImage.setIcon(image);
                    }

                } catch (Exception ex) {
                    String message = ex.getMessage();

                    if ("Cannot read the array length because \"imagedata\" is null".equals(message)) {
                        lblImage.setText("Sekil yoxdur!");
                    } else {
                        ex.printStackTrace();
                    }
                }

            }
        }
    }//GEN-LAST:event_jTableMehsullarMouseClicked

    private void formKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyReleased

        panelSearch.setVisible(true);
        txtSearch.requestFocus();

    }//GEN-LAST:event_formKeyReleased

    private void jTree1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTree1KeyReleased

        panelSearch.setVisible(true);
        txtSearch.requestFocus();
    }//GEN-LAST:event_jTree1KeyReleased

    private void jTableMehsullarMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableMehsullarMouseReleased

        if (SwingUtilities.isRightMouseButton(evt)) {

            int clickSayi = evt.getClickCount();

            OptionsForProductsTable.show(jTableMehsullar, evt.getX(), evt.getY());

        }


    }//GEN-LAST:event_jTableMehsullarMouseReleased

    private void openActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openActionPerformed

        //  open();
    }//GEN-LAST:event_openActionPerformed

    private void deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteActionPerformed

        deteleProductFromList();

    }//GEN-LAST:event_deleteActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        printOrShowBarcode();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void ShowOrPrintBarcodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ShowOrPrintBarcodeActionPerformed
        printOrShowBarcode();
    }//GEN-LAST:event_ShowOrPrintBarcodeActionPerformed

    private void correctionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_correctionActionPerformed

        EditProducts edit = new EditProducts(this, false);

        int selectedRow = 0;
        int id = 0;
        String nameOfProduct = null;
        int qaliqSay = 0;
        double priceBuy = 0;
        double priceSale = 0;
        String barcode = null;
        String date = null;
        String productName1 = null;
//        EditProducts edit = new EditProducts(this, true);
        try {

            df = (DefaultTableModel) jTableMehsullar.getModel();
            selectedRow = jTableMehsullar.getSelectedRow();
            id = Integer.parseInt(df.getValueAt(selectedRow, 0).toString());
            nameOfProduct = df.getValueAt(selectedRow, 1).toString();
            qaliqSay = Integer.parseInt(df.getValueAt(selectedRow, 2).toString());
            priceBuy = Double.parseDouble(df.getValueAt(selectedRow, 3).toString());
            priceSale = Double.parseDouble(df.getValueAt(selectedRow, 4).toString());
            try {
                date = (df.getValueAt(selectedRow, 5).toString());
            } catch (Exception ex) {
                barcode = df.getValueAt(selectedRow, 6).toString();
            }
            barcode = df.getValueAt(selectedRow, 6).toString();

            edit.txtName.setText(nameOfProduct);
            edit.txtQaliqSay.setText(Integer.toString(qaliqSay));
            edit.txtPriceBuy.setText(Double.toString(priceBuy));
            edit.txtPriceSale.setText(Double.toString(priceSale));
            edit.InputID = id;
            edit.txtBarcode.setText(barcode);
            edit.txtDate.setText(date);

            edit.setVisible(true);
//
//               productName1 = edit.getProductName();
//
//            if (productName1.equals(null)) {
//
//            } else {
//                int qaliqSay1 = edit.getQaliqsay();
//                double priceOfBuy1 = edit.getPriceBuy();
//                double priceOfSale1 = edit.getPriceSale();
//                String barcode1 = edit.getBarcode();
//
//                con = connect();
//                pres = con.prepareStatement("update mehsullar set Malin_adi = ?, Qaliq_say=?, Alis_qiymeti=?, Satis_qiymeti=?, Barcode=? where id = " + id);
//                pres.setString(1, productName1);
//                pres.setInt(2, qaliqSay1);
//                pres.setDouble(3, priceOfBuy1);
//                pres.setDouble(4, priceOfSale1);
//                pres.setString(5, barcode1);
//                pres.executeUpdate();
//
//                TreeView1.mouseClicked();
//
//                JOptionPane.showMessageDialog(this, "Mehsul melumatlari ugurla yenilendi");
//
//            }
//
        } catch (Exception ex2) {

            edit.txtName.setText(nameOfProduct);
            edit.txtQaliqSay.setText(Integer.toString(qaliqSay));
            edit.txtPriceBuy.setText(Double.toString(priceBuy));
            edit.txtPriceSale.setText(Double.toString(priceSale));
            edit.InputID = id;
            edit.txtDate.setText(date);
            edit.txtBarcode.setText(barcode);

            edit.setVisible(true);

        }
//
//            if (productName1.equals(null)) {
//
//            } else {
//            
//            
//            ex2.printStackTrace();
//
//            edit.txtName.setText(nameOfProduct);
//            edit.txtQaliqSay.setText(Integer.toString(qaliqSay));
//            edit.txtPriceBuy.setText(Double.toString(priceBuy));
//            edit.txtPriceSale.setText(Double.toString(priceSale));
//            edit.txtBarcode.setText(barcode);
//
//            edit.setVisible(true);
//
//            String productName2 = edit.getProductName();
//
//            if (productName2.equals(null)) {
//
//            } else {
//
//                int qaliqSay2 = edit.getQaliqsay();
//                double priceOfBuy2 = edit.getPriceBuy();
//                double priceOfSale = edit.getPriceSale();
//                String barcode2 = edit.getBarcode();
//
//                try {
//
//                    con = connect();
//                    pres = con.prepareStatement("update mehsullar set Malin_adi = ?, Qaliq_say=?, Alis_qiymeti=?, Satis_qiymeti=?, Barcode=? where id = " + id);
//                    pres.setString(1, productName2);
//                    pres.setInt(2, qaliqSay2);
//                    pres.setDouble(3, priceOfBuy2);
//                    pres.setDouble(4, priceOfSale);
//                    pres.setString(5, barcode2);
//                    pres.executeUpdate();
//                    TreeView1.mouseClicked();
//
//                } catch (Exception ex) {
//                    ex.printStackTrace();
//                }
//
//                JOptionPane.showMessageDialog(this, "Mehsul melumatlari ugurla yenilendi");
//
//            }
//        }
//        }

    }//GEN-LAST:event_correctionActionPerformed
    public static String newCategoryName = null;


    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed

        deleteCategoryName();

    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        updateCategoryName();
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed

        updateCategoryName();
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed

        deleteCategoryName();
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        txtSearch.requestFocus();
    }//GEN-LAST:event_jComboBox2ActionPerformed

    public void printOrShowBarcode(int say) {

        String currency;
        df = (DefaultTableModel) jTableMehsullar.getModel();
        int selectedRow = jTableMehsullar.getSelectedRow();

        int id = Integer.parseInt(jTableMehsullar.getValueAt(selectedRow, 0).toString());
        String productName = jTableMehsullar.getValueAt(selectedRow, 1).toString();
        String productPrice = jTableMehsullar.getValueAt(selectedRow, 4).toString();
        String barcode = jTableMehsullar.getValueAt(selectedRow, 6).toString();

        if (productPrice.contains(".0")) {
            currency = "0 AZN";
        } else {
            currency = "0 qepik";
        }

        String productColor = "******";
        String productSize = "******";
        String projectPath = "C:\\GitHubProject\\VeneraMarket\\VeneraMarket\\src\\main\\java\\com\\mycompany\\qarisiqmallar\\veneramarket\\BarcodePrinter.jrxml";
        JasperDesign jdesign;
        try {
            Connection c = connect();
            jdesign = JRXmlLoader.load(projectPath);
            JasperReport jr = null;

            HashMap<String, Object> parametrs;
            parametrs = new HashMap<>();
            parametrs.put("barcodeNumber", barcode);
            parametrs.put("productName", productName);
            parametrs.put("productSize", productSize);
            parametrs.put("productColor", productColor);
            parametrs.put("productPrice", productPrice + currency);
            String printerName = "Xprinter XP-365B";

            jr = JasperCompileManager.compileReport(jdesign);

            JasperPrint jprint = JasperFillManager.fillReport(jr, parametrs, c);

            SilentPrint ss = new SilentPrint();
            SilentPrint2 sp = new SilentPrint2();
            //ss.printReport(jr, productPrice, parametrs, c);

            sp.PrintReportToPrinter(jprint, printerName, say);

//            JasperPrint jprint = JasperFillManager.fillReport(jr, parametrs, c);
//            
//            JasperViewer jv = new JasperViewer(jprint, false);
//            jv.setZoomRatio((float)3.5);
//            jv.setTitle("Mehsul haqqında melumat");
//                    
//            jv.setVisible(true);
//            
//            
//            //JasperViewer.viewReport(jprint, false);
        } catch (JRException ex) {
            Logger.getLogger(ProductCategories.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void printOrShowBarcode() {

        df = (DefaultTableModel) jTableMehsullar.getModel();
        int selectedRow = jTableMehsullar.getSelectedRow();

        int id = Integer.parseInt(jTableMehsullar.getValueAt(selectedRow, 0).toString());
        String productName = jTableMehsullar.getValueAt(selectedRow, 1).toString();
        String productPrice = jTableMehsullar.getValueAt(selectedRow, 4).toString();
        String barcode = jTableMehsullar.getValueAt(selectedRow, 6).toString();
        String currency = null;

        boolean yoxla = productPrice.contains(".0");
        if (yoxla != true) {
            currency = "0 qepik";
        } else {
            currency = "0 AZN";
        }

        String productColor = "******";
        String productSize = "******";
        String projectPath = System.getProperty("user.dir");
        String filePath = "\\src\\main\\java\\com\\mycompany\\qarisiqmallar\\veneramarket\\BarcodePrinter.jrxml";
        String theCorrectFilePath = projectPath + filePath;
        System.out.println(theCorrectFilePath);
        System.out.println("C:\\git projects\\VeneraMarket-3\\VeneraMarket\\src\\main\\java\\com\\mycompany\\qarisiqmallar\\veneramarket\\BarcodePrinter.jrxml");
        JasperDesign jdesign;
        try {
            Connection c = connect();
            jdesign = JRXmlLoader.load(theCorrectFilePath);
            JasperReport jr = null;

            HashMap<String, Object> parametrs;
            parametrs = new HashMap<>();
            parametrs.put("barcodeNumber", barcode);
            parametrs.put("productName", productName);
            parametrs.put("productSize", productSize);
            parametrs.put("productColor", productColor);
            parametrs.put("productPrice", productPrice + currency);
            parametrs.put("currency", currency);
            String printerName = "Xprinter XP-365B";

            jr = JasperCompileManager.compileReport(jdesign);
            JasperPrint jprint = JasperFillManager.fillReport(jr, parametrs, c);
            JasperViewer jv = new JasperViewer(jprint, false);
            jv.setZoomRatio((float) 3.5);
            jv.setTitle("Mehsul haqqında melumat");

            jv.setVisible(true);

        } catch (JRException ex) {
            Logger.getLogger(ProductCategories.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public void clearBarcodeText() {

         Main.txtBarcode_reader.setText("");
    }

    @Override
    public void windowOpened(WindowEvent e) {
       
        clearBarcodeText();
    
    }

    @Override
    public void windowClosing(WindowEvent e) {
        
        this.dispose();
    }

    @Override
    public void windowClosed(WindowEvent e) {
       
    }

    @Override
    public void windowIconified(WindowEvent e) {
        
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        
    }

    @Override
    public void windowActivated(WindowEvent e) {
        
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        this.dispose();
    }

    public static class CharToByteConverter {

        public static CharToByteConverter getConverter(String encoding) {
            return new CharToByteConverter();
        }
    }

    public interface FontFamilyResolver {

        /**
         * Resolves a font family name into a GVTFontFamily. If the font family
         * cannot be resolved then null will be returned.
         *
         * @param familyName The Font Family name to resolve
         *
         * @return A resolved GVTFontFamily or null if the font family could not
         * be resolved.
         */
        GVTFontFamily resolve(String familyName);

        GVTFontFamily resolve(String familyName, FontFace fontFace);

        GVTFontFamily loadFont(InputStream in, FontFace fontFace) throws Exception;

        GVTFontFamily getDefault();

        GVTFontFamily getFamilyThatCanDisplay(char c);

    }

    public String deyisen() {

        try {
            DefaultMutableTreeNode treeModel = (DefaultMutableTreeNode) jTree1.getSelectionPath().getLastPathComponent();
            TreeNode node2 = treeModel.getParent();
            veriable = node2.toString();
            if (veriable != null) {

                AddSubCategory addcat = new AddSubCategory();
                addcat.setVisible(true);

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return veriable;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
            if ("metal".equals(info.getName())) {
                try {
                    javax.swing.UIManager.setLookAndFeel(info.getName());
                    break;
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(ProductCategories.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InstantiationException ex) {
                    Logger.getLogger(ProductCategories.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(ProductCategories.class.getName()).log(Level.SEVERE, null, ex);
                } catch (UnsupportedLookAndFeelException ex) {
                    Logger.getLogger(ProductCategories.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } //</editor-fold>

        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ProductCategories().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem Cancel;
    private javax.swing.JPopupMenu OptionsForProductsTable;
    private javax.swing.JMenuItem ShowOrPrintBarcode;
    private static javax.swing.JCheckBox chcekOtbor;
    private javax.swing.JMenuItem correction;
    private javax.swing.JMenuItem delete;
    private javax.swing.JButton jButton7;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JPopupMenu jPopupMenu2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    public static javax.swing.JTable jTableMehsullar;
    public static javax.swing.JTree jTree1;
    private javax.swing.JLabel lblImage;
    private javax.swing.JMenuItem open;
    private javax.swing.JPanel panelSearch;
    private javax.swing.JPanel panelTreeView;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
