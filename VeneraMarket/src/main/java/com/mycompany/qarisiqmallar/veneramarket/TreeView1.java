package com.mycompany.qarisiqmallar.veneramarket;

import static com.mycompany.qarisiqmallar.veneramarket.TestClass.con;
import static com.mycompany.qarisiqmallar.veneramarket.TestClass.pres;
import static com.mycompany.qarisiqmallar.veneramarket.TestClass.rs;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

public class TreeView1 extends javax.swing.JFrame {

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

    public TreeView1() {
        initComponents();
        jTableMehsullar.setDragEnabled(true);
        jTableMehsullar.setTransferHandler(new SimpleListDTSManager111());
        jTree1.setDragEnabled(true);
        jTree1.setTransferHandler(new SimpleListDTSManager111());
        panelTreeView.setVisible(false);
        setMainBaseName();
        Load();
        setTime();
        findTheLastBiilNumber();
        txtSetPrice.disable();
        panelTest.setVisible(false);
        panelSearch.setVisible(false);
        txtNumOfCopies.disable();

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
                txtTime.setText(time);

                time2 = sdf2.format(dt);
                currentDate = time2;

            }
        });
        timer.start();

    }

    public void findTheLastBiilNumber() {

        try {
            Date dt = new Date();
            sdf2 = new SimpleDateFormat("yyyy");
            String time2 = sdf2.format(dt);

            System.out.println(currentDate);
            Connection c;
            c = connect();
            Statement stmt = c.createStatement();
            stmt.execute("select b.* from purchasebills b order by id desc limit 1");
            ResultSet rs = stmt.getResultSet();

            rs.next();
            int numberOfBill = rs.getInt("id");
            int thelastNumber = numberOfBill + 1;
            String lastnum = time2 + "-00000" + Integer.toString(thelastNumber);
            txtNumberOfBill.setText(lastnum);

            c.close();
            stmt.close();
            rs.close();

        } catch (Exception ex) {
            ex.printStackTrace();
            Date dt = new Date();
            sdf2 = new SimpleDateFormat("yyyy");
            String time2 = sdf2.format(dt);
            String lastnum = time2 + "-00000" + "1";
            txtNumberOfBill.setText(lastnum);

        }

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
            DefaultTableModel df = (DefaultTableModel) TreeView1.jTableMehsullar.getModel();
            int columnCount = df.getColumnCount();
            int[] selectedRow = jTableMehsullar.getSelectedRows();
            String values = "";
            int column = 0;

            for (int i = 0; i < selectedRow.length; i++) {

                int row = selectedRow[i];
                System.out.println("Bu Array elementidir.." + row);

                values = TreeView1.jTableMehsullar.getValueAt(row, column).toString();

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
            DefaultTableModel df = (DefaultTableModel) TreeView1.jTableMehsullar.getModel();
            int columnCount = df.getColumnCount();
            int[] selectedRow = jTableMehsullar.getSelectedRows();
            String values = "";
            int column = 0;

            for (int i = 0; i < selectedRow.length; i++) {

                int row = selectedRow[i];
                System.out.println("Bu Array elementidir.." + row);

                values = TreeView1.jTableMehsullar.getValueAt(row, column).toString();

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
            DefaultTableModel df = (DefaultTableModel) TreeView1.jTableMehsullar.getModel();
            int columnCount = df.getColumnCount();
            int[] selectedRow = jTableMehsullar.getSelectedRows();
            String values = "";
            int column = 0;

            for (int i = 0; i < selectedRow.length; i++) {

                int row = selectedRow[i];
                System.out.println("Bu Array elementidir.." + row);

                values = TreeView1.jTableMehsullar.getValueAt(row, column).toString();

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
            DefaultTableModel df = (DefaultTableModel) TreeView1.jTableMehsullar.getModel();
            int columnCount = df.getColumnCount();
            int[] selectedRow = jTableMehsullar.getSelectedRows();
            String values = "";
            int column = 0;

            for (int i = 0; i < selectedRow.length; i++) {

                int row = selectedRow[i];
                System.out.println("Bu Array elementidir.." + row);

                values = TreeView1.jTableMehsullar.getValueAt(row, column).toString();

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
            DefaultTableModel df = (DefaultTableModel) TreeView1.jTableMehsullar.getModel();
            int columnCount = df.getColumnCount();
            int[] selectedRow = jTableMehsullar.getSelectedRows();
            String values = "";
            int column = 0;

            for (int i = 0; i < selectedRow.length; i++) {

                int row = selectedRow[i];
                System.out.println("Bu Array elementidir.." + row);

                values = TreeView1.jTableMehsullar.getValueAt(row, column).toString();

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
        jPanel2 = new javax.swing.JPanel();
        txtNameAndSurename = new javax.swing.JTextField();
        txtCommentary = new javax.swing.JTextField();
        txtNumberOfBill = new javax.swing.JTextField();
        txtTime = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        panelTest = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        listNameOfClients = new javax.swing.JList<>();
        jButton3 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        rb_mergeOfFile = new javax.swing.JRadioButton();
        txtPastavsikName = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblYeniMehsullar = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        txtDollarkurs = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        lblImage = new javax.swing.JLabel();
        panelTreeView = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTree1 = new javax.swing.JTree();
        chcekOtbor = new javax.swing.JCheckBox();
        jButton5 = new javax.swing.JButton();
        cbOption = new javax.swing.JComboBox<>();
        txtSetPrice = new javax.swing.JTextField();
        panelSearch = new javax.swing.JPanel();
        txtSearch = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtCemMebleg = new javax.swing.JTextField();
        jButton7 = new javax.swing.JButton();
        cbNumberOfCopies = new javax.swing.JComboBox<>();
        txtNumOfCopies = new javax.swing.JTextField();

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

        open.setText("jMenuItem10");
        open.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openActionPerformed(evt);
            }
        });
        OptionsForProductsTable.add(open);

        correction.setText("jMenuItem10");
        correction.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                correctionActionPerformed(evt);
            }
        });
        OptionsForProductsTable.add(correction);

        delete.setText("jMenuItem10");
        delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteActionPerformed(evt);
            }
        });
        OptionsForProductsTable.add(delete);

        ShowOrPrintBarcode.setText("jMenuItem10");
        ShowOrPrintBarcode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ShowOrPrintBarcodeActionPerformed(evt);
            }
        });
        OptionsForProductsTable.add(ShowOrPrintBarcode);

        Cancel.setText("jMenuItem10");
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
        jLabel1.setText("Список товаров");

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

        jPanel2.setBackground(new java.awt.Color(153, 153, 255));
        jPanel2.setForeground(new java.awt.Color(51, 51, 51));

        txtNameAndSurename.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        txtNameAndSurename.setForeground(new java.awt.Color(0, 0, 0));
        txtNameAndSurename.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNameAndSurenameActionPerformed(evt);
            }
        });
        txtNameAndSurename.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                txtNameAndSurenamePropertyChange(evt);
            }
        });
        txtNameAndSurename.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNameAndSurenameKeyReleased(evt);
            }
        });

        txtCommentary.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        txtCommentary.setForeground(new java.awt.Color(51, 51, 51));
        txtCommentary.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        txtNumberOfBill.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        txtNumberOfBill.setForeground(new java.awt.Color(51, 51, 51));
        txtNumberOfBill.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        txtTime.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        txtTime.setForeground(new java.awt.Color(51, 51, 51));
        txtTime.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTime.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimeActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Имя и фамилия :");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Комментарий :");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("Номер накладной :");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 0));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("Дата создания :");

        listNameOfClients.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listNameOfClientsMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(listNameOfClients);

        javax.swing.GroupLayout panelTestLayout = new javax.swing.GroupLayout(panelTest);
        panelTest.setLayout(panelTestLayout);
        panelTestLayout.setHorizontalGroup(
            panelTestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)
        );
        panelTestLayout.setVerticalGroup(
            panelTestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 62, Short.MAX_VALUE)
        );

        jButton3.setText("+");
        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton3MouseClicked(evt);
            }
        });

        jButton6.setText("Auto Insert");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        rb_mergeOfFile.setText("Объединить документы");
        rb_mergeOfFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rb_mergeOfFileActionPerformed(evt);
            }
        });

        txtPastavsikName.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        txtPastavsikName.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { ";", "Оазис", "FLOWERS N1" }));
        txtPastavsikName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPastavsikNameActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 0, 0));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel12.setText("Поставщик :");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(panelTest, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(txtNameAndSurename, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtTime, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
                                .addComponent(txtNumberOfBill, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(txtCommentary, javax.swing.GroupLayout.Alignment.TRAILING))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(rb_mergeOfFile)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton6))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(79, 79, 79)
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtPastavsikName, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(12, 12, 12)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNameAndSurename, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelTest, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCommentary, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNumberOfBill, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTime, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton6)
                    .addComponent(rb_mergeOfFile))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPastavsikName, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        txtCommentary.getAccessibleContext().setAccessibleName("");
        txtNumberOfBill.getAccessibleContext().setAccessibleName("");
        txtTime.getAccessibleContext().setAccessibleName("");

        tblYeniMehsullar.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        tblYeniMehsullar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "№", "наименование товара", "количество", "Цена продажи", "Сумма", "id", "peiceBuy"
            }
        ));
        tblYeniMehsullar.setRowHeight(25);
        tblYeniMehsullar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tblYeniMehsullarKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tblYeniMehsullarKeyReleased(evt);
            }
        });
        jScrollPane3.setViewportView(tblYeniMehsullar);
        if (tblYeniMehsullar.getColumnModel().getColumnCount() > 0) {
            tblYeniMehsullar.getColumnModel().getColumn(0).setMinWidth(20);
            tblYeniMehsullar.getColumnModel().getColumn(0).setPreferredWidth(45);
            tblYeniMehsullar.getColumnModel().getColumn(0).setMaxWidth(70);
            tblYeniMehsullar.getColumnModel().getColumn(1).setMinWidth(200);
            tblYeniMehsullar.getColumnModel().getColumn(1).setPreferredWidth(300);
            tblYeniMehsullar.getColumnModel().getColumn(1).setMaxWidth(450);
            tblYeniMehsullar.getColumnModel().getColumn(2).setMinWidth(50);
            tblYeniMehsullar.getColumnModel().getColumn(2).setPreferredWidth(120);
            tblYeniMehsullar.getColumnModel().getColumn(2).setMaxWidth(150);
            tblYeniMehsullar.getColumnModel().getColumn(5).setMinWidth(0);
            tblYeniMehsullar.getColumnModel().getColumn(5).setPreferredWidth(0);
            tblYeniMehsullar.getColumnModel().getColumn(5).setMaxWidth(0);
            tblYeniMehsullar.getColumnModel().getColumn(6).setMinWidth(0);
            tblYeniMehsullar.getColumnModel().getColumn(6).setPreferredWidth(0);
            tblYeniMehsullar.getColumnModel().getColumn(6).setMaxWidth(1);
        }

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel2.setText("Категории");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel3.setText("Yeni Qaime..");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel5.setText("Cem");

        jButton1.setBackground(new java.awt.Color(255, 51, 0));
        jButton1.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Sil");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jButton2.setText("Print");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton4.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jButton4.setText("Ok");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        txtDollarkurs.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        txtDollarkurs.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtDollarkurs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDollarkursActionPerformed(evt);
            }
        });
        txtDollarkurs.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDollarkursKeyPressed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("По курсу:");

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
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelTreeViewLayout.setVerticalGroup(
            panelTreeViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTreeViewLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        chcekOtbor.setText("Отбор");

        jButton5.setText("Set Price");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        cbOption.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "По договора", "%", "рубл" }));
        cbOption.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbOptionActionPerformed(evt);
            }
        });

        txtSetPrice.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        txtSetPrice.setForeground(new java.awt.Color(51, 51, 51));
        txtSetPrice.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtSetPrice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSetPriceActionPerformed(evt);
            }
        });

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
                .addGap(0, 12, Short.MAX_VALUE)
                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panelSearchLayout.setVerticalGroup(
            panelSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelSearchLayout.createSequentialGroup()
                .addComponent(txtSearch)
                .addContainerGap())
        );

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel11.setText("Изображение продукта");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel4.setText("Qaime Melumatlari");

        txtCemMebleg.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        txtCemMebleg.setForeground(new java.awt.Color(0, 204, 51));
        txtCemMebleg.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jButton7.setText("Print Barcode");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        cbNumberOfCopies.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Auto", "Say teyin et" }));
        cbNumberOfCopies.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbNumberOfCopiesActionPerformed(evt);
            }
        });

        txtNumOfCopies.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNumOfCopies.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNumOfCopiesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(16, 16, 16)
                                .addComponent(jLabel2))
                            .addComponent(panelTreeView, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(26, 26, 26)
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(54, 54, 54)
                                .addComponent(jLabel1)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblImage, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton7))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane2)
                                .addContainerGap())))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(panelSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(129, 129, 129)
                                .addComponent(chcekOtbor, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane3)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jButton4)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jButton1)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(cbNumberOfCopies, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(txtNumOfCopies, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE))
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel10)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(txtDollarkurs, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(cbOption, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(txtSetPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 73, Short.MAX_VALUE)
                                        .addComponent(jLabel5)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtCemMebleg, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap())))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(panelTreeView, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(chcekOtbor)
                        .addGap(28, 28, 28))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(panelSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jLabel11)
                        .addGap(12, 12, 12)
                        .addComponent(lblImage, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 160, Short.MAX_VALUE)
                        .addComponent(jButton7)
                        .addGap(48, 48, 48)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(56, 56, 56)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtCemMebleg, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(txtNumOfCopies, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(cbNumberOfCopies, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(19, 19, 19)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtDollarkurs, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtSetPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(cbOption, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                .addGap(27, 27, 27))
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

                                String SecondsubCatName = node2.toString();
                                con = connect();
                                pres = con.prepareStatement("select * from 2ndsubcategory ss where ss.name = " + "'" + SecondsubCatName + "'");
                                rs = pres.executeQuery();
                                rs.next();

                                int SecondSubCatID = rs.getInt("id");

                                pres = con.prepareStatement("delete from 3ndsubcategory s where s.name = " + "'" + node1 + "'" + " and s.index =" + SecondSubCatID);
                                pres.executeUpdate();

                                JOptionPane.showMessageDialog(this, "Kateqoriya ugurla silindi");

                            }

                        } else {

                            model = (DefaultTreeModel) jTree1.getModel();
                            model.removeNodeFromParent(theLastComponent);
                            model.reload();

                            String subCatName = node2.toString();
                            con = connect();
                            pres = con.prepareStatement("select * from subcategory where name = " + "'" + subCatName + "'");
                            rs = pres.executeQuery();
                            rs.next();

                            int catID = rs.getInt("id");

                            pres = con.prepareStatement("delete from 2ndsubcategory s where s.name = " + "'" + node1 + "'" + " and s.index =" + catID);
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

    public void newEntery(java.awt.event.KeyEvent evt) {

        int s = evt.getKeyCode();

        if (s == 10) {

            try {

                df = (DefaultTableModel) tblYeniMehsullar.getModel();

                int selected = tblYeniMehsullar.getSelectedRow();

                int idTable = Integer.parseInt(df.getValueAt(selected, 0).toString());
                String name = (df.getValueAt(selected, 1).toString());
                int numberOfProduct = Integer.parseInt(df.getValueAt(selected, 2).toString());
                double price = Double.parseDouble(df.getValueAt(selected, 3).toString());
                double total = Double.parseDouble(df.getValueAt(selected, 4).toString());
                int id = Integer.parseInt(df.getValueAt(selected, 5).toString());

                double total2 = numberOfProduct * price;
                double roundedTotal = Math.round(total2 * 100.000) / 100.000;

//                con = connect();
//                pres = con.prepareStatement("select * from qaimemehsullari_copy1 where say =?");
//                pres.setInt(1, idTable);
//                rs = pres.executeQuery();
//                rs.next();
//                int say1 = rs.getInt("say");
//                int id1 = rs.getInt("id");
//                String productName = rs.getString("productName");
//                int numberOfProduct1 = rs.getInt("numberOfProduct");
//                double priceOfSale1 = rs.getDouble("priceOfSale");
//                double total = rs.getDouble("total");
//
//                pres = con.prepareStatement("insert into updatedProducts (say, id, productName, numberOfProduct, priceOfSale, total) values(?,?,?,?,?,?)");
//                pres.setInt(1, say1);
//                pres.setInt(2, id1);
//                pres.setString(3, productName);
//                pres.setInt(4, numberOfProduct1);
//                pres.setDouble(5, priceOfSale1);
//                pres.setDouble(6, total);
//                pres.executeUpdate();
                String query2 = "update qaimemehsullariTreeView set say = ?, productName = ?, numberOfProduct =?, priceOfSale = ?, total = ? where say = ? ";

                pres = con.prepareStatement(query2);

                pres.setInt(1, idTable);
                pres.setString(2, name);
                pres.setInt(3, numberOfProduct);
                pres.setDouble(4, price);
                pres.setDouble(5, roundedTotal);
                pres.setDouble(6, idTable);

                if (name.equals("Долг")) {

                    String clientName = txtNameAndSurename.getText();

                    String name2 = txtNameAndSurename.getText();
                    if (name2.isBlank()) {
                        JOptionPane.showMessageDialog(panelTest, "Zehmet olmasa musteri adini daxil edin!", "DIQQET!", HEIGHT);
                    } else {

                        con = connect();
                        pres = con.prepareStatement("select * from clients where NameAndSurename = " + "'" + clientName + "'");
                        rs = pres.executeQuery();
                        rs.next();
                        double percentage = rs.getDouble("PersentageOfSalePrice");

                        double total3 = price - percentage * price / 100;
                        double totalRounded = Math.round(total3 * 100.000) / 100.000;

                        String query3 = "update qaimemehsullari set say = ?, productName = ?, numberOfProduct =?, priceOfSale = ?, total = ?, PriceOfBuy = ? where say = ? ";

                        pres = con.prepareStatement(query3);

                        pres.setInt(1, idTable);
                        pres.setString(2, name);
                        pres.setInt(3, numberOfProduct);
                        pres.setDouble(4, price);
                        pres.setDouble(5, roundedTotal);
                        pres.setDouble(6, totalRounded * numberOfProduct);
                        pres.setDouble(7, idTable);

                        pres.executeUpdate();

                        con = connect();

                        //JOptionPane.showMessageDialog(this, "oldu bu is");
                        load();

                        try {

                        } catch (Exception ex) {

                            ex.printStackTrace();
                        }
                    }
                } else {

                    pres.executeUpdate();

                    con = connect();

                    //JOptionPane.showMessageDialog(this, "oldu bu is");
                    load();

                    try {

                    } catch (Exception ex) {

                        ex.printStackTrace();
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();

            }

        }

    }

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


    private void tblYeniMehsullarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblYeniMehsullarKeyPressed

        int s = evt.getKeyCode();

        if (s == 127) {

            detele();
            setTheCorrectNumberOfRow();
            load();
        }

    }//GEN-LAST:event_tblYeniMehsullarKeyPressed

    public void detele() {

        df = (DefaultTableModel) tblYeniMehsullar.getModel();

        int selected = tblYeniMehsullar.getSelectedRow();

        int id = Integer.parseInt(df.getValueAt(selected, 0).toString());

        try {

            Connection c = connect();

            String query = "select m.* from qaimemehsullariTreeView m where m.say = " + id;

            pres = c.prepareStatement(query);

            pres.executeQuery();

            ResultSet rs = pres.getResultSet();

            rs.next();
            int say = rs.getInt("say");
            String mehsulunAdi = rs.getString("productName");

            int cavab = JOptionPane.showConfirmDialog(this, "Silinən məhsulu geri qaytarmaq mümkün olmayacaq! \n" + mehsulunAdi + "-adli mehsulu silmək istədiyinizdən əminsiniz?", "Diqqət!", JOptionPane.YES_NO_CANCEL_OPTION);

            if (cavab == 0) {

                Connection c2 = connect();
                pres = c2.prepareStatement("delete from qaimemehsullariTreeView where say =  " + say);

                //pres.setInt(1, id);
                pres.executeUpdate();

                load();
                sebetinHesablanmasi();
                load();
                setTheCorrectNumberOfRow();
                JOptionPane.showMessageDialog(this, " " + mehsulunAdi + "-adli mehsul silindi");

                df = (DefaultTableModel) tblYeniMehsullar.getModel();

                int rowCount = df.getRowCount();
                if (rowCount == 0) {
                    txtCemMebleg.setText("");
                    txtSetPrice.setText("");
                    txtDollarkurs.setText("");
                    txtNameAndSurename.setText("");
                    txtCommentary.setText("");

                    JOptionPane.showMessageDialog(this, "Sebet bosdur!", "DIQQET!", HEIGHT);
                }

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

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        detele();
        load();
    }//GEN-LAST:event_jButton1ActionPerformed

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


    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        report();

    }//GEN-LAST:event_jButton2ActionPerformed

    DefaultListModel modelList = new DefaultListModel();

    private void txtNameAndSurenameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNameAndSurenameKeyReleased

        panelTest.setVisible(true);
        modelList.removeAllElements();
        String s = txtNameAndSurename.getText();

        try {
            Connection c = connect();
            pres = c.prepareCall("select * from contracors c where c.NameAndSurename like " + "'" + "%" + s + "%" + "'");

            ResultSet rs = pres.executeQuery();

            while (rs.next()) {

                modelList.addElement(rs.getString("NameAndSurename"));
                listNameOfClients.setModel(modelList);
            }

        } catch (Exception ex) {

            ex.printStackTrace();

        }


    }//GEN-LAST:event_txtNameAndSurenameKeyReleased

    public void addExpense() {

        int numberProduct = 0;
        String name2 = null;
        double totalPriceOfBuy, sumOfTotal = 0;
        double roundedTotal = 0.0;

        String name = txtNameAndSurename.getText();
        String catrgory = txtNameAndSurename.getText();
        String commentary = txtCommentary.getText();
        String time = txtTime.getText();

        try {

            con = connect();

            pres = con.prepareStatement("select * from qaimemehsullari");
            rs = pres.executeQuery();
            while (rs.next()) {
                totalPriceOfBuy = rs.getDouble("PriceOfBuy");
                sumOfTotal += totalPriceOfBuy;
                roundedTotal = Math.round(sumOfTotal * 100.000) / 100.000;

            }

            con = connect();
            pres = con.prepareStatement("insert into expenses (name, categoryOfExpense, commentary, date, totalExpense) values(?,?,?,?,?)");
            pres.setString(1, name);
            pres.setString(2, catrgory);
            pres.setString(3, commentary);
            pres.setString(4, time);
            pres.setDouble(5, roundedTotal);
            pres.executeUpdate();

            try {

                pres = con.prepareStatement("select * from updatedCapitalbudget order by id desc limit 1");
                rs = pres.executeQuery();

                rs.next();

                double capitalBudget = rs.getDouble("AmountOfCapitalBudget");

                double result = capitalBudget - roundedTotal;
                double roundedResult = Math.round(result * 100.000) / 100.000;

                pres = con.prepareStatement("insert into updatedCapitalbudget (AmountOfCapitalBudget, date) values(?,?)");
                pres.setDouble(1, roundedResult);
                pres.setString(2, currentDate);
                pres.executeUpdate();

            } catch (Exception ex) {

                System.out.println("Bura bosdur diger bazaya kecirem");
                pres = con.prepareStatement("select * from capitalbudget");
                rs = pres.executeQuery();

                rs.next();

                double capitalBudget = rs.getDouble("AmountOfCapitalBudget");

                double result = capitalBudget - roundedTotal;
                double roundedResult = Math.round(result * 100.000) / 100.000;

                pres = con.prepareStatement("insert into updatedCapitalbudget (AmountOfCapitalBudget, date) values(?,?)");
                pres.setDouble(1, roundedResult);
                pres.setString(2, currentDate);
                pres.executeUpdate();
            }

            JOptionPane.showMessageDialog(this, "Расход был успешно зарегистрирован");

            this.setVisible(false);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private void listNameOfClientsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listNameOfClientsMouseClicked

        String selectedClientName = listNameOfClients.getSelectedValue();
        txtNameAndSurename.setText(selectedClientName);
        modelList.removeAllElements();
        panelTest.setVisible(false);
        panelTreeView.setVisible(true);
        //addContractNumber();
    }//GEN-LAST:event_listNameOfClientsMouseClicked

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
            Logger.getLogger(TreeView1.class.getName()).log(Level.SEVERE, null, ex);
        }
        return contractNumber;
    }


    private void jButton3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseClicked

        CreateNewContractor newClient = new CreateNewContractor(this, true);
        newClient.setVisible(true);
    }//GEN-LAST:event_jButton3MouseClicked

    private void txtNameAndSurenameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNameAndSurenameActionPerformed

    }//GEN-LAST:event_txtNameAndSurenameActionPerformed

    private void txtNameAndSurenamePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_txtNameAndSurenamePropertyChange


    }//GEN-LAST:event_txtNameAndSurenamePropertyChange

    public void writeFileFromJtableToFile() {

        String qaimeAdi = txtNameAndSurename.getText();
        String qaimeNum = (txtNumberOfBill.getText());
        String extensionOfFile = ".txt";
        String tamAd = qaimeAdi + "-" + qaimeNum + extensionOfFile;
        String filePath = "C:\\Alis Qaimeleri\\";

        String pathName = filePath + tamAd;

        File file = new File(pathName);

        try {
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);

            for (int j = 0; j < tblYeniMehsullar.getRowCount(); j++) {
                for (int k = 0; k < tblYeniMehsullar.getColumnCount(); k++) {
                    bw.write(tblYeniMehsullar.getValueAt(j, k).toString() + ":");

                }

                bw.newLine();

            }

            bw.close();
            fw.close();

        } catch (IOException ex) {

            ex.printStackTrace();

        }

    }

    public void Save() {

        int numberProduct = 0;
        String name2 = null;
        double totalPriceOfBuy, sumOfTotal = 0;
        double roundedTotal = 0.0;
        con = connect();

        String nameAndSurename = txtNameAndSurename.getText();
        String numberOfContract = txtCommentary.getText();
        String billNumber = txtNumberOfBill.getText();
        double total = Double.parseDouble(txtCemMebleg.getText());
        double kursDollar = Double.parseDouble(txtDollarkurs.getText());
        String time = txtTime.getText();

        try {
            con = connect();

            pres = con.prepareStatement("select * from qaimemehsullaritreeview");
            rs = pres.executeQuery();
            while (rs.next()) {
                numberProduct = rs.getInt("numberOfProduct");
                name2 = rs.getString("productName");
                totalPriceOfBuy = rs.getDouble("PriceOfBuy");
                sumOfTotal += totalPriceOfBuy;
                roundedTotal = Math.round(sumOfTotal * 100.000) / 100.000;

            }
            Connection c = connect();
            pres = c.prepareStatement("insert into purchasebills (nameOfClient, NumberOfBill, numberOfContract, TotalSumOfBill, DateCreation, TypeOfDocument, InWarehouse, totalSummOfPriceOfbuy, dollarKurs, status, credit ) values(?, ?, ?, ?, ?, ?, ?,?,?,?,?)");
            pres.setString(1, nameAndSurename);
            pres.setString(2, billNumber);
            pres.setString(3, numberOfContract);
            pres.setDouble(4, total);
            pres.setString(5, time);
            pres.setString(6, "Расходная накладная");
            pres.setString(7, "✔");
            pres.setDouble(8, roundedTotal);
            pres.setDouble(9, kursDollar);
            pres.setString(10, "Непроверенный документ");
            pres.setDouble(11, total);
            pres.executeUpdate();

            String ID, Miqdari, Ümumi_Məbləğ;
            df = (DefaultTableModel) tblYeniMehsullar.getModel();

            pres = con.prepareStatement("select * from qaimemehsullaritreeview");
            rs = pres.executeQuery();

            while (rs.next()) {

                int id = rs.getInt("id");
                String name = rs.getString("productName");
                int number = rs.getInt("numberOfProduct");
                double priceOfProduct = rs.getDouble("priceOfSale");
                double total2 = rs.getDouble("total");

                String query2 = "update mehsullar set Miqdari = Miqdari + ?, Qaliq_say = Qaliq_say + ? where id = ?";

                pres = con.prepareStatement(query2);

                pres.setInt(1, number);
                pres.setInt(2, number);
                pres.setInt(3, id);
                pres.executeUpdate();

            }

            pres = c.prepareStatement("truncate table qaimemehsullaritreeview;");
            pres.executeUpdate();

//            con = connect();
//            pres = con.prepareStatement("truncate table autoinsertofpurchaseinvoice");
//            pres.executeUpdate();
//
//            con = connect();
//            pres = con.prepareStatement("truncate table totalsumforautoinsert");
//            pres.executeUpdate();
            load();

        } catch (Exception ex) {

            ex.printStackTrace();

        }

    }

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed

        String name = txtNameAndSurename.getText();
        String kursDollar = txtDollarkurs.getText();

        boolean yoxla = name.isBlank();
        if (yoxla == true) {

            JOptionPane.showMessageDialog(this, "Zehmet olmasa müşteri adını qeyd edin!", "DİQQET!!", HEIGHT);
            txtNameAndSurename.requestFocus();
        }

        boolean yoxlaKursDollar = kursDollar.isBlank();
        if (yoxlaKursDollar == true) {
            JOptionPane.showMessageDialog(this, "Zehmet olmasa valyuta mezennesini qeyd edin", "DİQQET!!", HEIGHT);
            txtDollarkurs.requestFocus();
        } else {

            if (name.equals("Брак")) {

                addExpense();
            }
            try {

                pres = con.prepareStatement("select * from qaimemehsullaritreeview");
                ResultSet rs5 = pres.executeQuery();

                while (rs5.next()) {

                    int id = rs5.getInt("id");
                    String name55 = rs5.getString("productName");
                    int number = rs5.getInt("numberOfProduct");
                    double priceOfProduct = rs5.getDouble("priceOfProductBuy");
                    double total2 = rs5.getDouble("total");

                    String query2 = "update mehsullar set Miqdari = Miqdari + ?, Qaliq_say = Qaliq_say + ?, Alis_qiymeti = ?, Alis_Tarixi =? where id = ?";

                    pres = con.prepareStatement(query2);

                    pres.setInt(1, number);
                    pres.setInt(2, number);
                    pres.setDouble(3, priceOfProduct);
                    pres.setString(4, time);
                    pres.setInt(5, id);

                    pres.executeUpdate();

                }

                pres = con.prepareStatement("truncate table qaimemehsullaritreeview;");
                pres.executeUpdate();

//                    con = connect();
//                    pres = con.prepareStatement("truncate table autoinsertofpurchaseinvoice");
//                    pres.executeUpdate();
//
//                    con = connect();
//                    pres = con.prepareStatement("truncate table totalsumforautoinsert");
//                    pres.executeUpdate();
//                    load();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            writeFileFromJtableToFile();
            Save();
            findTheLastBiilNumber();

            txtNameAndSurename.setText("");
            txtCommentary.setText("");
            txtCemMebleg.setText("");
            txtDollarkurs.setText("");
            JOptionPane.showMessageDialog(this, "Hazirdi Qaqaw");

        }


    }//GEN-LAST:event_jButton4ActionPerformed

    private void txtDollarkursActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDollarkursActionPerformed

    }//GEN-LAST:event_txtDollarkursActionPerformed

    private void txtTimeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTimeActionPerformed

    private void tblYeniMehsullarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblYeniMehsullarKeyReleased
        newEntery(evt);
        sebetinHesablanmasi();
    }//GEN-LAST:event_tblYeniMehsullarKeyReleased

    public void setPrice() {

        int selectedIndex = cbOption.getSelectedIndex();

        String name2 = txtNameAndSurename.getText();
        if (name2.isBlank()) {
            JOptionPane.showMessageDialog(panelTest, "Zehmet olmasa musteri adini daxil edin!", "DIQQET!", HEIGHT);
        } else {

            if (selectedIndex == 0) {

                String name = txtNameAndSurename.getText();

                try {

                    con = connect();
                    pres = con.prepareStatement("select * from clients where NameAndSurename = " + "'" + name + "'");
                    rs = pres.executeQuery();
                    rs.next();
                    double percentage = rs.getDouble("PersentageOfSalePrice");
                    String sign = rs.getString("OptionOnRubleAndPercentage");
                    pres = con.prepareStatement("select * from qaimemehsullari");
                    ResultSet rs2 = pres.executeQuery();

                    while (rs2.next()) {

                        double priceOfSale = rs2.getDouble("priceOfSale");
                        int id = rs2.getInt("id");
                        pres = con.prepareStatement("select * from mehsullar where id = " + id);
                        rs = pres.executeQuery();
                        rs.next();
                        double priceOfBuy = rs.getDouble("Alis_qiymeti");
                        double total = priceOfBuy + percentage * priceOfBuy / 100;
                        double roundedPriceOfSale = Math.round(total * 100.000) / 100.000;
                        pres = con.prepareStatement("update qaimemehsullari set priceOfSale = ? where id = ?");
                        pres.setDouble(1, roundedPriceOfSale);
                        pres.setInt(2, id);
                        pres.executeUpdate();

                        pres = con.prepareStatement("select * from qaimemehsullari where id = " + id);
                        rs = pres.executeQuery();
                        rs.next();
                        double priceofSale = rs.getDouble("priceOfSale");
                        int numberProduct = rs.getInt("NumberOfProduct");

                        double total2 = priceOfSale * numberProduct;
                        double roundedTotal = Math.round(total2 * 100.000) / 100.000;

                        pres = con.prepareStatement("update qaimemehsullari set total = ? where id = ?");
                        pres.setDouble(1, roundedTotal);
                        pres.setInt(2, id);
                        pres.executeUpdate();

                    }
                    load();
                    sebetinHesablanmasi();

                } catch (Exception ex) {

                    ex.printStackTrace();
                }

            }
            if (selectedIndex == 2) {

                double rubl = Double.parseDouble(txtSetPrice.getText());

                try {
                    pres = con.prepareStatement("select * from qaimemehsullari");
                    ResultSet rs2 = pres.executeQuery();

                    while (rs2.next()) {

                        double priceOfSale = rs2.getDouble("priceOfSale");
                        int id = rs2.getInt("id");

                        pres = con.prepareStatement("select * from mehsullar where id = " + id);
                        rs = pres.executeQuery();
                        rs.next();
                        double priceOfBuy = rs.getDouble("Alis_qiymeti");
                        double total = priceOfBuy + rubl;
                        double roundedPriceOfSale = Math.round(total * 100.000) / 100.000;

                        pres = con.prepareStatement("update qaimemehsullari set priceOfSale = ? where id = ?");
                        pres.setDouble(1, total);
                        pres.setInt(2, id);
                        pres.executeUpdate();

                        pres = con.prepareStatement("update qaimemehsullari set total = priceOfSale * NumberOfProduct where id = ?");
                        pres.setInt(1, id);
                        pres.executeUpdate();

                    }
                    load();
                    sebetinHesablanmasi();

                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
            if (selectedIndex == 1) {

                double rubl = Double.parseDouble(txtSetPrice.getText());

                try {
                    pres = con.prepareStatement("select * from qaimemehsullari");
                    ResultSet rs2 = pres.executeQuery();

                    while (rs2.next()) {

                        double priceOfSale = rs2.getDouble("priceOfSale");
                        int id = rs2.getInt("id");

                        pres = con.prepareStatement("select * from mehsullar where id = " + id);
                        rs = pres.executeQuery();
                        rs.next();
                        double priceOfBuy = rs.getDouble("Alis_qiymeti");
                        double total = priceOfBuy + rubl * priceOfBuy / 100;
                        double roundedPriceOfSale = Math.round(total * 100.000) / 100.000;

                        pres = con.prepareStatement("update qaimemehsullari set priceOfSale = ? where id = ?");
                        pres.setDouble(1, roundedPriceOfSale);
                        pres.setInt(2, id);
                        pres.executeUpdate();

                        pres = con.prepareStatement("update qaimemehsullari set total = priceOfSale * NumberOfProduct where id = ?");
                        pres.setInt(1, id);
                        pres.executeUpdate();

                        pres = con.prepareStatement("select * from qaimemehsullari where id = " + id);
                        rs = pres.executeQuery();
                        rs.next();
                        double priceofSale = rs.getDouble("priceOfSale");
                        int numberProduct = rs.getInt("NumberOfProduct");

                        double total2 = priceOfSale * numberProduct;
                        double roundedTotal = Math.round(total2 * 100.000) / 100.000;

                        pres = con.prepareStatement("update qaimemehsullari set total = ? where id = ?");
                        pres.setDouble(1, roundedTotal);
                        pres.setInt(2, id);
                        pres.executeUpdate();

                    }
                    load();
                    sebetinHesablanmasi();

                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }

        }

    }


    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed

        setPrice();

    }//GEN-LAST:event_jButton5ActionPerformed

    private void cbOptionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbOptionActionPerformed

        int selectedIndex = cbOption.getSelectedIndex();
        System.out.println(selectedIndex);

        if (selectedIndex == 1) {

            txtSetPrice.enable();
            txtSetPrice.requestFocus();

        }
        if (selectedIndex == 2) {

            txtSetPrice.enable();
            txtSetPrice.requestFocus();

        }
        if (selectedIndex == 0) {
            txtSetPrice.setText("");
            txtSetPrice.disable();
        }

    }//GEN-LAST:event_cbOptionActionPerformed

    private void txtSetPriceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSetPriceActionPerformed

        setPrice();
    }//GEN-LAST:event_txtSetPriceActionPerformed

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

        int a;
        try {

            Connection c = connect();
            pres = c.prepareCall("select * from mehsullar c where c.Malin_adi like  '%' " + "'" + text + "'" + " '%'");

            ResultSet rs2 = pres.executeQuery();

            while (rs2.next()) {

                ResultSetMetaData rd = rs2.getMetaData();
                a = rd.getColumnCount();
                df = (DefaultTableModel) jTableMehsullar.getModel();
                df.setRowCount(0);

                while (rs2.next()) {
                    Vector v2 = new Vector();

                    int id2 = rs2.getInt("id");
                    v2.add(id2);
                    String malinAdi = rs2.getString("Malin_adi");
                    v2.add(malinAdi);
                    v2.add(rs2.getInt("Qaliq_say"));
                    v2.add(rs2.getDouble("Alis_qiymeti"));
                    v2.add(rs2.getDouble("Satis_qiymeti"));
                    //v2.add(satisQiymeti);
                    v2.add(rs2.getString("Alis_Tarixi"));
                    v2.add(rs2.getString("Barcode"));

                    df.addRow(v2);

                }

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }//GEN-LAST:event_txtSearchKeyReleased

    private void jTableMehsullarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTableMehsullarKeyReleased

        panelSearch.setVisible(true);
        txtSearch.requestFocus();

    }//GEN-LAST:event_jTableMehsullarKeyReleased

    public void open() {

        panelSearch.setVisible(false);
        GetProductForTreeView getProduct = new GetProductForTreeView(this, true);

        df = (DefaultTableModel) jTableMehsullar.getModel();

        int selected = jTableMehsullar.getSelectedRow();

        id = Integer.parseInt(df.getValueAt(selected, 0).toString());
        name = (df.getValueAt(selected, 1).toString());
        int qaliqSay = Integer.parseInt(df.getValueAt(selected, 2).toString());
        double satisQiymeti = Double.parseDouble(df.getValueAt(selected, 4).toString());
        double alisQiymeti = Double.parseDouble(df.getValueAt(selected, 3).toString());
        String barcode = df.getValueAt(selected, 6).toString();

        getProduct.productName = name;
        getProduct.productID = id;
        getProduct.priceOfBuy = alisQiymeti;
        getProduct.txtProductName.setText(name);
        getProduct.txtFaktikMiqdar.setText(Integer.toString(qaliqSay));
        getProduct.txtProductPrice.setText(Double.toString(alisQiymeti));

        getProduct.setVisible(true);
        int say2 = getProduct.getNumber();
        if (getProduct.getNumber() == 0) {

        } else {

            Integer i = identification(id);

            if (i == 0) {

                try {
                    Connection c;
                    c = connect();
                    Statement stmt = c.createStatement();
                    stmt.execute("select m.* from qaimemehsullariTreeView m order by say desc limit 1");
                    ResultSet rs = stmt.getResultSet();
                    rs.next();

                    say = rs.getInt("say");

                    Connection c2 = connect();
                    String query = "insert into qaimemehsullariTreeView (say, id, productName, numberOfProduct, priceOfSale, total, PriceOfBuy, priceOfProductBuy ) values(?,?,?,?,?,?,?,?)";

                    pres = c2.prepareStatement(query);
                    pres.setInt(1, say + 1);
                    pres.setInt(2, id);
                    pres.setString(3, name);
                    pres.setInt(4, say2);
                    pres.setDouble(5, satisQiymeti);
                    pres.setDouble(6, alisQiymeti * say2);

                    if (name == "Долг") {

                        double total = priceOfSale - 10 * priceOfSale / 100;
                        double totalRounded = Math.round(total * 100.000) / 100.000;
                        pres.setDouble(7, totalRounded * say2 + say2);
                    } else {
                        pres.setDouble(7, satisQiymeti * say2);
                        pres.setDouble(8, alisQiymeti);

                    }

                    pres.executeUpdate();
                    sayAlinanMallar++;
                    load();
                    sebetinHesablanmasi();

                } catch (Exception ex) {

                    try {
                        say = 1;
                        Connection c2 = connect();
                        String query = "insert into qaimemehsullariTreeView (say, id, productName, numberOfProduct, priceOfSale, total, PriceOfBuy, priceOfProductBuy ) values(?,?,?,?,?,?,?,?)";

                        pres = c2.prepareStatement(query);
                        pres.setInt(1, say);
                        pres.setInt(2, id);
                        pres.setString(3, name);
                        pres.setInt(4, say2);
                        pres.setDouble(5, satisQiymeti);
                        pres.setDouble(6, alisQiymeti * say2);
                        if (name.equals("Долг")) {

                            double total = priceOfSale - 10 * priceOfSale / 100;
                            double totalRounded = Math.round(total * 100.000) / 100.000;
                            pres.setDouble(7, totalRounded * say2 + say2);
                        } else {
                            pres.setDouble(7, satisQiymeti * say2);  // burada + say2 ni pozdum
                            pres.setDouble(8, alisQiymeti);  // burada + say2 ni pozdum

                        }

                        pres.executeUpdate();
                        sayAlinanMallar++;
                        load();
                        sebetinHesablanmasi();
                        ex.printStackTrace();
                    } catch (Exception ex2) {

                        ex2.printStackTrace();
                    }

                }

            } else {

                try {

                    con = connect();
                    pres = con.prepareStatement("select * from qaimemehsullariTreeView where id = " + id);
                    rs = pres.executeQuery();
                    rs.next();
                    double priceOfSale = rs.getDouble("priceOfSale");
                    double priceOfBuy = rs.getDouble("priceOfProductBuy");

                    Connection c;
                    c = connect();
                    Statement stmt = c.createStatement();
                    stmt.execute("SELECT * FROM qaimemehsullariTreeView  where id =" + id);
                    ResultSet rs = stmt.getResultSet();
                    rs.next();
                    int numberOfProduct = rs.getInt("numberOfProduct");

                    pres = c.prepareStatement("update qaimemehsullariTreeView set numberOfProduct = numberOfProduct + ?, PriceOfBuy =? where id = ?");

                    pres.setInt(1, say2);
                    pres.setDouble(2, priceOfSale * (say2 + numberOfProduct));
                    pres.setInt(3, id);
                    pres.executeUpdate();

                    pres = c.prepareStatement("update qaimemehsullariTreeView set total = numberOfProduct * ? where id = ?");
                    pres.setDouble(1, priceOfBuy);
                    pres.setInt(2, id);
                    pres.executeUpdate();

                } catch (Exception ex) {

                    ex.printStackTrace();

                }
                load();
                sebetinHesablanmasi();

            }

        }

        String optionForCopy = cbNumberOfCopies.getSelectedItem().toString();
        if (!"Say teyin et".equals(optionForCopy)) {
            printOrShowBarcode(say2);
        } else {

            int manualNumberOfCopies = Integer.parseInt(txtNumOfCopies.getText());
            printOrShowBarcode(manualNumberOfCopies);
        }

    }


    private void jTableMehsullarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableMehsullarMouseClicked

        int clickSayi = evt.getClickCount();

        if (clickSayi == 2) {

            open();

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

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed

        String[] ss = {"Salam"};
        //GetProductsFromExcelFileToPurchaseInvoice autoInsertOfProduct = new GetProductsFromExcelFileToPurchaseInvoice();
        //autoInsertOfProduct.main(ss);
        //GetProductsFromExcelFileToPurchaseInvoice.sayFOrBosluq = 0;
        load();
        sebetinHesablanmasi();
        try {
            con = connect();
            pres = con.prepareStatement("select * from totalsumforautoinsert");
            rs = pres.executeQuery();
            rs.next();
            double kursDollar = rs.getDouble("kursDollar");
            String commentary = rs.getString("commentary");
            txtDollarkurs.setText(Double.toString(kursDollar));
            txtCommentary.setText((commentary));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void formKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyReleased

        panelSearch.setVisible(true);
        txtSearch.requestFocus();

    }//GEN-LAST:event_formKeyReleased

    private void rb_mergeOfFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rb_mergeOfFileActionPerformed

        boolean yoxla = rb_mergeOfFile.isSelected();
        if (yoxla == false) {
            // GetProductsFromExcelFileToPurchaseInvoice getFiles = new GetProductsFromExcelFileToPurchaseInvoice();
            //int say1 = getFiles.say = 0;
        } else {
            //GetProductsFromExcelFileToPurchaseInvoice getFiles = new GetProductsFromExcelFileToPurchaseInvoice();
            //int say1 = getFiles.say = 1;

        }

    }//GEN-LAST:event_rb_mergeOfFileActionPerformed

    private void txtDollarkursKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDollarkursKeyPressed

        int s = evt.getKeyCode();

        if (s == 10) {
            setPrice();
        }
    }//GEN-LAST:event_txtDollarkursKeyPressed

    private void txtPastavsikNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPastavsikNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPastavsikNameActionPerformed

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

        open();
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

    private void cbNumberOfCopiesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbNumberOfCopiesActionPerformed

        String optionForCopy = cbNumberOfCopies.getSelectedItem().toString();

        if (optionForCopy != "Auto") {
            txtNumOfCopies.enable();
            txtNumOfCopies.setText("");
            txtNumOfCopies.requestFocus();
        } else {
            txtNumOfCopies.setText("");
            txtNumOfCopies.disable();
        }

    }//GEN-LAST:event_cbNumberOfCopiesActionPerformed

    private void txtNumOfCopiesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumOfCopiesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNumOfCopiesActionPerformed

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

    public void report() {

        String name = txtNameAndSurename.getText();
        Double kursDollar2 = Double.parseDouble(txtDollarkurs.getText());

        if (name == null) {

            JOptionPane.showMessageDialog(this, "Zehmet olmasa musteri adini qeyd edin!", "DIQQET!", HEIGHT);
        }
        if (kursDollar2 == null) {
            JOptionPane.showMessageDialog(this, "Zehmet olmasa valuta kursunu qeyd edin!", "DIQQET!", HEIGHT);
        } else {

            DecimalFormat dformater = new DecimalFormat("#.###");
            try {
                int i = 0;
                int totalProductNumber, cem = 0;
                double totalPrice, totalP = 0;
                //Class.forName("com.mysql.jdbc.Driver");
                Connection c = connect();

                String projectPath = System.getProperty("user.dir");
                String realPath = projectPath.substring(0, 15);
                System.out.println("Proyektin islediyi path... " + projectPath);
                if (projectPath.contains("C:\\Users\\Shamil")) {

                    projectPath = "C:\\Users\\Shamil\\Downloads\\TestTreeView";

                }
                if (projectPath.contains("C:\\Users\\Tacir Aliyev")) {

                    projectPath = "C:\\Users\\Tacir Aliyev\\Documents\\TestTreeView";
                }
                String filePath = "\\src\\main\\java\\com\\mycompany\\qarisiqmallar\\veneramarket\\testReport_1.jrxml";
                JasperDesign jdesign = JRXmlLoader.load(projectPath + filePath);
                String totalPath = "C:\\git projects\\VeneraMarket\\VeneraMarket\\src\\main\\java\\com\\mycompany\\qarisiqmallar\\veneramarket\\testReport_1.jrxml";
                //String totalPath = "C:\\git projects\\VeneraMarket\\VeneraMarket\\src\\main\\java\\com\\mycompany\\testtreeview\\testReport_1.jrxml";
                String query = "select * from qaimemehsullaritreeview";
                pres = c.prepareStatement(query);
                ResultSet rs = pres.executeQuery();
                while (rs.next()) {

                    totalProductNumber = rs.getInt("numberOfProduct");
                    totalPrice = rs.getInt("total");
                    totalP += totalPrice;
                    cem += totalProductNumber;
                    i++;
                }

                String totallyPrice = Double.toString(totalP);
                String ss = Integer.toString(i);
                JasperReport jr;
                jr = JasperCompileManager.compileReport(jdesign);
                String kursDollar = txtDollarkurs.getText();
                String nameOfClient = txtNameAndSurename.getText();
                boolean yoxla = kursDollar.isBlank();
                boolean yoxla2 = nameOfClient.isBlank();

                if (yoxla == true) {

                    JOptionPane.showMessageDialog(this, "Zehmet olmasa, dollar kursunu qeyd edin", "Diqqet!", HEIGHT);
                } else {

                    if (yoxla2 == true) {

                        JOptionPane.showMessageDialog(this, "Zehmet olmasa, musteri adini qeyd edin!", "Diqqet!", HEIGHT);
                    } else {

                        double kursDol = Double.parseDouble(kursDollar);
//            double totalSumWithDollar = kursDol * 65;
//            String totalPriceWithDoll = Double.toString(totalSumWithDollar);
                        String time = txtTime.getText();
                        String totalNumberOfProduct = Integer.toString(cem);
                        String total = txtCemMebleg.getText() + " AZN.";
                        String total222 = txtCemMebleg.getText();
                        String totalForDouble = txtCemMebleg.getText();
                        double totalPriceDouble = Double.parseDouble(totalForDouble);
                        double totalSumWithDollar2 = totalPriceDouble / kursDol;
                        String finalSum = Double.toString(totalSumWithDollar2);
                        String commentary = txtCommentary.getText();
                        String pastavsik = txtPastavsikName.getSelectedItem().toString();
                        String billtNumber = txtNumberOfBill.getText();
                        HashMap<String, Object> parametrs;
                        parametrs = new HashMap<>();
                        parametrs.put("kusrDollarSon", kursDollar + "0000");
                        parametrs.put("nameOfClient", nameOfClient);
                        parametrs.put("sutunSayi", ss);
                        parametrs.put("cem", total);
                        parametrs.put("commentary", commentary);
                        parametrs.put("pastavsik", pastavsik);
                        parametrs.put("totalProductNumber", totalNumberOfProduct);
                        parametrs.put("numberOfBill", billtNumber);
                        parametrs.put("timeCreationOfBill", time);
                        parametrs.put("totalPrice", total222);
                        parametrs.put("totalWithDollar", finalSum);
                        //JasperReport jreport = JasperCompileManager.compileReport(jdesign);
                        JasperPrint jprint = JasperFillManager.fillReport(jr, parametrs, c);

                        JasperViewer.viewReport(jprint, false);

                    }

                }

            } catch (Exception ex) {
                Logger.getLogger(TreeView1.class.getName()).log(Level.SEVERE, null, ex);

            }

        }

    }

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
            Logger.getLogger(TreeView1.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(TreeView1.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static class CharToByteConverter {

        public static CharToByteConverter getConverter(String encoding) {
            return new CharToByteConverter();
        }
    }

    public void load() {

        DecimalFormat dformater = new DecimalFormat("#.##");

        int a;
        try {

            Connection c = connect();

            pres = c.prepareCall("select * from qaimemehsullariTreeView");

            ResultSet rs = pres.executeQuery();

            ResultSetMetaData rd = rs.getMetaData();
            a = rd.getColumnCount();
            df = (DefaultTableModel) tblYeniMehsullar.getModel();
            df.setRowCount(0);

            while (rs.next()) {
                Vector v2 = new Vector();
                for (int i = 0; i < a; i++) {

                    int sayi = rs.getInt("say");
                    int number = rs.getInt("numberOfProduct");
                    String adi = rs.getString("productName");
                    double cem = rs.getDouble("total");
                    double roundTotal = Math.round(cem * 100.000) / 100.000;
                    String formatedTotal = dformater.format(cem);
                    double qiymeti = rs.getDouble("priceOfProductBuy");
                    double roundPrice = Math.round(qiymeti * 100.000) / 100.000;
                    String formatedQiymet = dformater.format(qiymeti);
                    double total = roundTotal;
                    v2.add(rs.getInt("say"));
                    v2.add(rs.getString("productName"));
                    v2.add(rs.getInt("numberOfProduct"));
                    v2.add(rs.getDouble("priceOfSale"));
                    v2.add(roundTotal);
                    v2.add(rs.getInt("id"));
                    v2.add(rs.getDouble("PriceOfBuy"));
                }
                df.addRow(v2);

            }

//            int rowCount = df.getRowCount();
//            if (rowCount == 0) {
//                txtCemMebleg.setText("");
//                txtSetPrice.setText("");
//                txtDollarkurs.setText("");
//                txtNameAndSurename.setText("");
//                txtCommentary.setText("");
//            }
        } catch (Exception ex) {
            System.out.println(ex);

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

    public void loadForAutoInsert() {

        DecimalFormat dformater = new DecimalFormat("#.##");

        int a;
        try {

            Connection c = connect();

            pres = c.prepareCall("select * from autoInsertOfPurchaseInvoice");

            ResultSet rs = pres.executeQuery();

            ResultSetMetaData rd = rs.getMetaData();
            a = rd.getColumnCount();
            df = (DefaultTableModel) tblYeniMehsullar.getModel();
            df.setRowCount(0);

            while (rs.next()) {
                Vector v2 = new Vector();
                for (int i = 0; i < a; i++) {

                    int sayi = rs.getInt("say");
                    int number = rs.getInt("numberOfProduct");
                    String adi = rs.getString("productName");
                    double cem = rs.getDouble("total");
                    double roundTotal = Math.round(cem * 1000.000) / 1000.000;
                    String formatedTotal = dformater.format(cem);
                    double qiymeti = rs.getDouble("priceOfSale");
                    double roundPrice = Math.round(qiymeti * 1000.000) / 1000.000;
                    String formatedQiymet = dformater.format(qiymeti);
                    double total = roundTotal;
                    v2.add(rs.getInt("say"));
                    v2.add(rs.getString("productName"));
                    v2.add(rs.getInt("numberOfProduct"));
                    v2.add(roundPrice);
                    v2.add(roundTotal);
                    v2.add(rs.getInt("id"));
                    v2.add(rs.getDouble("PriceOfBuy"));
                }
                df.addRow(v2);

            }

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void loadForSerach() {

        DecimalFormat dformater = new DecimalFormat("#.##");

        int a;
        try {

            Connection c = connect();

            pres = c.prepareStatement("select * from qaimemehsullariForSerach");

            ResultSet rs = pres.executeQuery();

            ResultSetMetaData rd = rs.getMetaData();
            a = rd.getColumnCount();
            df = (DefaultTableModel) tblYeniMehsullar.getModel();
            df.setRowCount(0);

            while (rs.next()) {
                Vector v2 = new Vector();
                for (int i = 0; i < a; i++) {

                    //int sayi = rs.getInt("say");
                    int number = rs.getInt("numberOfProduct");
                    String adi = rs.getString("productName");
                    double cem = rs.getDouble("total");
                    double roundTotal = Math.round(cem * 1000.000) / 1000.000;
                    String formatedTotal = dformater.format(cem);
                    double qiymeti = rs.getDouble("priceOfSale");
                    double roundPrice = Math.round(qiymeti * 1000.000) / 1000.000;
                    String formatedQiymet = dformater.format(qiymeti);
                    double total = roundTotal;
                    //v2.add(rs.getInt("say"));
                    v2.add(rs.getString("productName"));
                    v2.add(rs.getInt("numberOfProduct"));
                    v2.add(roundPrice);
                    v2.add(roundTotal);
                    v2.add(rs.getInt("id"));
                    v2.add(rs.getDouble("PriceOfBuy"));
                }
                df.addRow(v2);

            }

        } catch (Exception ex) {
            System.out.println(ex);
        }

    }

    public void sebetinHesablanmasi() {

        DecimalFormat dformater = new DecimalFormat("#.##");
        df = (DefaultTableModel) tblYeniMehsullar.getModel();

        double toplam, cem = 0;

        for (int i = 0; i < df.getRowCount(); i++) {

            toplam = Double.parseDouble(df.getValueAt(i, 4).toString());
            cem += toplam;
            //String formattedGelir = dformater.format((cem));
            double roundedTotal = Math.round(cem * 100.000) / 100.000;
            txtCemMebleg.setText(Double.toString(roundedTotal));

        }

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

    public void addNewProduct() {

        int a;
        try {
            Connection con = connect();
            pres = con.prepareCall("SELECT * FROM mehsullar m  where m.id =" + " " + id);

            ResultSet rs = pres.executeQuery();

            ResultSetMetaData rd = rs.getMetaData();
            a = rd.getColumnCount();
            df = (DefaultTableModel) tblYeniMehsullar.getModel();
            df.setRowCount(0);

            while (rs.next()) {
                Vector v2 = new Vector();
                for (int i = 0; i < a; i++) {

                    v2.add(rs.getString("id"));
                    v2.add(rs.getString("Malin_adi"));
                    v2.add(rs.getInt("Satis_qiymeti"));
                    String miqdari = miqdariFromJDialog;
                    v2.add(miqdari);

                }
                df.addRow(v2);

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

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
                    Logger.getLogger(TreeView1.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InstantiationException ex) {
                    Logger.getLogger(TreeView1.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(TreeView1.class.getName()).log(Level.SEVERE, null, ex);
                } catch (UnsupportedLookAndFeelException ex) {
                    Logger.getLogger(TreeView1.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } //</editor-fold>

        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TreeView1().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem Cancel;
    private javax.swing.JPopupMenu OptionsForProductsTable;
    private javax.swing.JMenuItem ShowOrPrintBarcode;
    private javax.swing.JComboBox<String> cbNumberOfCopies;
    private javax.swing.JComboBox<String> cbOption;
    private static javax.swing.JCheckBox chcekOtbor;
    private javax.swing.JMenuItem correction;
    private javax.swing.JMenuItem delete;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
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
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JPopupMenu jPopupMenu2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    public static javax.swing.JTable jTableMehsullar;
    public static javax.swing.JTree jTree1;
    private javax.swing.JLabel lblImage;
    private javax.swing.JList<String> listNameOfClients;
    private javax.swing.JMenuItem open;
    private javax.swing.JPanel panelSearch;
    private javax.swing.JPanel panelTest;
    private javax.swing.JPanel panelTreeView;
    private javax.swing.JRadioButton rb_mergeOfFile;
    public javax.swing.JTable tblYeniMehsullar;
    public javax.swing.JTextField txtCemMebleg;
    private javax.swing.JTextField txtCommentary;
    private javax.swing.JTextField txtDollarkurs;
    private javax.swing.JTextField txtNameAndSurename;
    private javax.swing.JTextField txtNumOfCopies;
    private javax.swing.JTextField txtNumberOfBill;
    private javax.swing.JComboBox<String> txtPastavsikName;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtSetPrice;
    private javax.swing.JTextField txtTime;
    // End of variables declaration//GEN-END:variables
}
