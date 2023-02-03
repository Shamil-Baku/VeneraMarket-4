package com.mycompany.qarisiqmallar.veneramarket;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static java.awt.image.ImageObserver.HEIGHT;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

public class PurchaseInvoice extends javax.swing.JFrame {

    String miqdariFromJDialog;
    DefaultTreeModel model;
    DefaultTableModel df;
    DefaultMutableTreeNode products = new DefaultMutableTreeNode("Products");
    Timer timer;
    String year;
    SimpleDateFormat sdf = new SimpleDateFormat();
    SimpleDateFormat sdf2 = new SimpleDateFormat();
    String path;
    File f;
    String path55 = null;
    ImageIcon format = null;
    int s = 0;
    byte[] pimage = null;
    String sayForOtbor;

    public PurchaseInvoice() {
        initComponents();
        connect();
        //txtAlisTarixi.setText(date2);
        setScreenSize();
        readBillFromFile();
        //bringProductsFromFileIntoDb();
        Load();
        //sebetinHesablanmasi();
        setTime();
        findTheLastBiilNumber();
        panelSearch.setVisible(false);
        //addContractNumber();
        panelTest.setVisible(false);

    }

    Connection con;
    PreparedStatement pres;
    ResultSet rs;
    int say = 0;

    public Connection connect() {
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

    public void setScreenSize() {

        Toolkit toolkit = getToolkit();
        Dimension size = toolkit.getScreenSize();
        setLocation(size.width / 2 - getWidth() / 2, size.height / 2 - getHeight() / 2);

    }

    private void readBillFromFile() {

        try {

            pres = con.prepareCall("select * from qaimeyolu");

            rs = pres.executeQuery();

            while (rs.next()) {
                String qaimeNum = (rs.getString("QaimeNum"));
                String numberOfContract = (rs.getString("NumberOFContract"));
                String QaimeAdi = (rs.getString("QaimeAdi"));
                String qaimeYolu = (rs.getString("QaimeYolu"));
                String kimden = (rs.getString("Kimden"));
                String totalsum = (rs.getString("total"));
                String dateCreation = (rs.getString("date"));
                String tamAd2 = qaimeYolu + QaimeAdi;

                txtNumberOfBill.setText((qaimeNum));
                //txtCommentary.setText(numberOfContract);
                txtNameAndSurename.setText(kimden);
                txtTime.setText(dateCreation);

                File file = new File(tamAd2);

                FileReader fr = new FileReader(file);
                BufferedReader br = new BufferedReader(fr);

                df = (DefaultTableModel) tblYeniMehsullar.getModel();
                Object[] lines = br.lines().toArray();

                for (int i = 0; i < lines.length; i++) {
                    String[] row = lines[i].toString().split(":");
                    df.addRow(row);
                }
            }

        } catch (Exception ex) {

            ex.printStackTrace();
        }
    }

    public void bringProductsFromFileIntoDb() {

        try {
            pres = con.prepareStatement("truncate table PurchaseInvoice;");
            pres.executeUpdate();

            df = (DefaultTableModel) tblYeniMehsullar.getModel();

            int setirSayi = tblYeniMehsullar.getRowCount();

            for (int i = 0; i < setirSayi; i++) {

                int id = Integer.parseInt(df.getValueAt(i, 0).toString());
                String mehsulAdi = df.getValueAt(i, 1).toString();
                int miqdari = Integer.parseInt(df.getValueAt(i, 2).toString());
                double alisQiymeti = Double.parseDouble(df.getValueAt(i, 3).toString());
                double umumiMebleg = Double.parseDouble(df.getValueAt(i, 4).toString());
                String kimden = txtNameAndSurename.getText();
                String qurum = txtCommentary.getText();
                String qaimeNum = (txtNumberOfBill.getText());
                try {
                    pres = con.prepareCall("insert into PurchaseInvoice ( productName, numberOfProduct, priceOfSale, total, kimden) values (?,?,?,?,?)");

                    //pres.setInt(1, id);
                    pres.setString(1, mehsulAdi);
                    pres.setInt(2, miqdari);
                    pres.setDouble(3, alisQiymeti);
                    pres.setDouble(4, umumiMebleg);
                    pres.setString(5, kimden);
//                    pres.setString(7, qurum);
//                    pres.setString(8, qaimeNum);
                    pres.executeUpdate();

                } catch (SQLException ex) {

                    ex.printStackTrace();

                }

            }
        } catch (SQLException ex) {

            ex.printStackTrace();

        }
        load();
    }
        String currentDate;
    public void setTime() {

        timer = new Timer(0, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
                Date dt = new Date();
                
                sdf = new SimpleDateFormat("HH:mm:ss");
                sdf2 = new SimpleDateFormat("yyyy-MM-dd");

//                String time = sdf.format(dt);
//                lblTime.setText(time);

                String time2 = sdf2.format(dt);
                
                
                String time3 = sdf2.format(dt);
                txtTime.setText(time3);
                currentDate = txtTime.getText();
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
            stmt.execute("select b.* from purchaseBills b order by id desc limit 1");
            ResultSet rs = stmt.getResultSet();
            
            
         
            rs.next();
                int numberOfBill = rs.getInt("id");
                int thelastNumber = numberOfBill + 1;
                String lastnum = time2 + "-00000"+ Integer.toString(thelastNumber);
                txtNumberOfBill.setText(lastnum);
            

        } catch (Exception ex) {
            ex.printStackTrace();
            Date dt = new Date();
            sdf2 = new SimpleDateFormat("yyyy");
            String time2 = sdf2.format(dt);
            String lastnum = time2 + "-00000"+ "1";
            txtNumberOfBill.setText(lastnum);

        }

        
        
    }

    public void load3(String season, String category, String subCategory) {

        int a;
        try {
            Connection c = connect();
            pres = c.prepareCall("select id, Malin_adi,Miqdari,Satis_miqdari,Qaliq_say, Alis_qiymeti,Satis_qiymeti,Movsum_id,Kateqoriya_id,Alt_kateqoriya_id,Alisin_toplam_deyer,Alis_Tarixi,Barcode from mehsullar where Movsum_id =" + "'" + season + "'" + "and Kateqoriya_id = " + "'" + category + "'" + "and Alt_kateqoriya_id = " + "'" + subCategory + "'" + "");

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

    public void load4() {

        int a;
        try {
            Connection c = connect();
            pres = c.prepareCall("select id, Malin_adi,Miqdari,Satis_miqdari,Qaliq_say, Alis_qiymeti,Satis_qiymeti,Alis_Tarixi,Barcode from mehsullar");

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
                    //v2.add(rs.getInt("Miqdari"));
                    //v2.add(rs.getInt("Satis_miqdari"));
                    v2.add(rs.getInt("Qaliq_say"));
                    v2.add(rs.getDouble("Alis_qiymeti"));
                    v2.add(rs.getDouble("Satis_qiymeti"));
                    //v2.add(rs.getString("Alis_Tarixi"));
                    //v2.add(rs.getString("Barcode"));

                }
                df.addRow(v2);
            }

        } catch (Exception ex) {
            System.out.println(ex);
        }

    }

    public void load5(String season) {

        try {

            con = connect();
            pres = con.prepareStatement("truncate table qaimemehsullariForSerach");
            pres.executeUpdate();

        } catch (Exception ex) {

            ex.printStackTrace();
        }
        int a;
        try {

            boolean yoxla = chcekOtbor.isSelected();

            if (yoxla == true) {

                Connection c = connect();
                pres = c.prepareStatement("select id, Malin_adi,Miqdari,Satis_miqdari,Qaliq_say, Alis_qiymeti,Satis_qiymeti,mainCategory,subCategory,2ndSubCategory,Alisin_toplam_deyer,Alis_Tarixi,Barcode from mehsullar where mainCategory =" + "'" + season + "'" + " order by Malin_adi");
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
                        v2.add(rs.getInt("Satis_qiymeti"));
                        v2.add(rs.getString("Alis_Tarixi"));
                        v2.add(rs.getString("Barcode"));
                    }
                    df.addRow(v2);
                }

                pres = c.prepareStatement("select id, Malin_adi,Miqdari,Satis_miqdari,Qaliq_say, Alis_qiymeti,Satis_qiymeti,mainCategory,subCategory,2ndSubCategory,Alisin_toplam_deyer,Alis_Tarixi,Barcode from mehsullar where mainCategory =" + "'" + season + "'" + " order by Malin_adi");
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

                    pres = c.prepareStatement("insert into qaimemehsullariForSerach (id, productName,QaliqSay,PriceOfBuy, priceOfSale, tarix, Barcode) values (?,?,?,?,?,?,?)");

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

                Connection c = connect();
                pres = c.prepareStatement("select id, Malin_adi,Miqdari,Satis_miqdari,Qaliq_say, Alis_qiymeti,Satis_qiymeti,mainCategory,subCategory,2ndSubCategory,Alisin_toplam_deyer,Alis_Tarixi,Barcode from mehsullar where mainCategory =" + "'" + season + "'" + " and Qaliq_say " + sayForOtbor + " order by Malin_adi");
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
                        v2.add(rs.getInt("Satis_qiymeti"));
                        v2.add(rs.getString("Alis_Tarixi"));
                        v2.add(rs.getString("Barcode"));
                    }
                    df.addRow(v2);
                }

                pres = c.prepareStatement("select id, Malin_adi,Miqdari,Satis_miqdari,Qaliq_say, Alis_qiymeti,Satis_qiymeti,mainCategory,subCategory,2ndSubCategory,Alisin_toplam_deyer,Alis_Tarixi,Barcode from mehsullar where mainCategory =" + "'" + season + "'" + " and Qaliq_say " + sayForOtbor + " order by Malin_adi");
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

                    pres = c.prepareStatement("insert into qaimemehsullariForSerach (id, productName, QaliqSay, PriceOfBuy, priceOfSale, tarix, Barcode) values (?,?,?,?,?,?,?)");

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

    public void load6(String mainCatId, String subCatId) {

        int a;
        try {
            Connection c = connect();
            pres = c.prepareCall("select id, Malin_adi,Miqdari,Satis_miqdari,Qaliq_say, Alis_qiymeti,Satis_qiymeti,mainCategory,subCategory,"
                    + "2ndSubCategory,Alisin_toplam_deyer,Alis_Tarixi,Barcode from mehsullar where subCategory =" + "'" + subCatId + "'" + 
                            "and mainCategory = " + "'" + mainCatId + "'");

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
                    v2.add(rs.getString("Alis_Tarixi"));
                    v2.add(rs.getString("Barcode"));
                }
                df.addRow(v2);

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

//   
    public void Load() {
        try {
            Connection c = connect();

            pres = c.prepareStatement("select categories, id from category");
            ResultSet rs = pres.executeQuery();

            while (rs.next()) {
                String mainCatName = rs.getString("categories");
                int id = rs.getInt("id");
                DefaultMutableTreeNode dmtn = new DefaultMutableTreeNode(mainCatName);

                Connection c2 = connect();

                pres = c2.prepareStatement("select * from subcategory s where s.`index` = " + "'" + id + "'");
                ResultSet rs2 = pres.executeQuery();
                while (rs2.next()) {
                    String subName = rs2.getString("name");
                    int subId = rs2.getInt("id");
                    DefaultMutableTreeNode dmtn2 = new DefaultMutableTreeNode(subName);

                    Connection c23 = connect();

                    pres = c23.prepareStatement("select * from mehsullar s where s.`2ndSubCategory` = " + "'" + subId + "'");
                    ResultSet rs23 = pres.executeQuery();
                    while (rs23.next()) {
                        String subName2 = rs23.getString("Malin_adi");
                        DefaultMutableTreeNode dmtn23 = new DefaultMutableTreeNode(subName2);
                        //dmtn2.add(dmtn23);

                    }

                    dmtn.add(dmtn2);
                }
                products.add(dmtn);

            }

        } catch (Exception ex) {
            System.out.println(ex);
        }

        model = (DefaultTreeModel) jTree1.getModel();
        model.setRoot(products);
        jTree1.setModel(model);
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
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        txtNameAndSurename = new javax.swing.JTextField();
        txtCommentary = new javax.swing.JTextField();
        txtNumberOfBill = new javax.swing.JTextField();
        txtTime = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        panelTest = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        listNameOfClients = new javax.swing.JList<>();
        jButton3 = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblYeniMehsullar = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lblImage = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        txtCemMebleg = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTree1 = new javax.swing.JTree();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableMehsullar = new javax.swing.JTable();
        jButton5 = new javax.swing.JButton();
        txtDollarkurs = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        rb_mergeOfFile = new javax.swing.JRadioButton();
        chcekOtbor = new javax.swing.JRadioButton();
        panelSearch = new javax.swing.JPanel();
        txtSearch = new javax.swing.JTextField();
        rbtnDontRefreshPrices = new javax.swing.JRadioButton();

        jMenuItem1.setText("Kateqoriya elave et");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItem1);

        jMenuItem4.setText("Adini Deyisdir");
        jPopupMenu1.add(jMenuItem4);

        jMenuItem2.setText("Sil");
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
        jPopupMenu2.add(jMenuItem7);

        jMenuItem8.setText("Sil");
        jPopupMenu2.add(jMenuItem8);

        jMenuItem9.setText("Legv et");
        jPopupMenu2.add(jMenuItem9);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("Список товаров");

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

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Имя и фамилия :");

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
            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        panelTestLayout.setVerticalGroup(
            panelTestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTestLayout.createSequentialGroup()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jButton3.setText("+");
        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton3MouseClicked(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 0, 0));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel12.setText("Комментарий :");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9)
                    .addComponent(jLabel6)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtTime, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtNameAndSurename)
                            .addComponent(panelTest, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtCommentary, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE))
                        .addGap(11, 11, 11)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtNumberOfBill, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNameAndSurename, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panelTest, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtCommentary, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNumberOfBill, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(55, 55, 55)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTime, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(44, 44, 44))
        );

        txtCommentary.getAccessibleContext().setAccessibleName("");
        txtNumberOfBill.getAccessibleContext().setAccessibleName("");
        txtTime.getAccessibleContext().setAccessibleName("");

        tblYeniMehsullar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "№", "наименование товара", "количество", "Цена продажи", "Сумма", "ID"
            }
        ));
        tblYeniMehsullar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tblYeniMehsullarKeyPressed(evt);
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
        }

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setText("Категории");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel3.setText("Yeni Qaime..");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel4.setText("Qaime Melumatlari");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 51, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 35, Short.MAX_VALUE)
        );

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel10.setText("Изображение продукта");

        txtCemMebleg.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        txtCemMebleg.setForeground(new java.awt.Color(0, 204, 51));
        txtCemMebleg.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel5.setText("Cem");

        jButton4.setText("Ok");
        jButton4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jButton2.setText("Print");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(255, 51, 0));
        jButton1.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Sil");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("root");
        jTree1.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        jTree1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTree1MouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jTree1MouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(jTree1);

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
        jTableMehsullar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "Malin Adi", "Qaliq Say", "Alis Qiymeti", "Satis Qiymeti"
            }
        ));
        jTableMehsullar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableMehsullarMouseClicked(evt);
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
            jTableMehsullar.getColumnModel().getColumn(1).setMinWidth(150);
            jTableMehsullar.getColumnModel().getColumn(1).setPreferredWidth(200);
            jTableMehsullar.getColumnModel().getColumn(1).setMaxWidth(300);
            jTableMehsullar.getColumnModel().getColumn(2).setMinWidth(50);
            jTableMehsullar.getColumnModel().getColumn(2).setPreferredWidth(70);
            jTableMehsullar.getColumnModel().getColumn(2).setMaxWidth(150);
            jTableMehsullar.getColumnModel().getColumn(3).setMinWidth(100);
            jTableMehsullar.getColumnModel().getColumn(3).setPreferredWidth(100);
            jTableMehsullar.getColumnModel().getColumn(3).setMaxWidth(150);
            jTableMehsullar.getColumnModel().getColumn(4).setMinWidth(100);
            jTableMehsullar.getColumnModel().getColumn(4).setPreferredWidth(100);
            jTableMehsullar.getColumnModel().getColumn(4).setMaxWidth(150);
        }

        jButton5.setText("Auto insert");
        jButton5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        txtDollarkurs.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel11.setText("По курсу:");

        rb_mergeOfFile.setText("Объединить документы");
        rb_mergeOfFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rb_mergeOfFileActionPerformed(evt);
            }
        });

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
            .addGroup(panelSearchLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(txtSearch)
                .addContainerGap())
        );
        panelSearchLayout.setVerticalGroup(
            panelSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSearchLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        rbtnDontRefreshPrices.setText("Не обновлять цены");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(297, 297, 297)
                                .addComponent(jLabel10))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(286, 286, 286)
                                .addComponent(lblImage, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(174, 346, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(txtDollarkurs, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCemMebleg))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(rbtnDontRefreshPrices)
                                .addGap(18, 18, 18)
                                .addComponent(rb_mergeOfFile))
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 739, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(103, 103, 103)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel2)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(panelSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(18, 18, 18)
                                        .addComponent(chcekOtbor))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane2)))
                        .addGap(23, 23, 23))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rb_mergeOfFile)
                            .addComponent(rbtnDontRefreshPrices)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(lblImage, javax.swing.GroupLayout.DEFAULT_SIZE, 249, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(chcekOtbor)
                                .addGap(14, 14, 14))
                            .addComponent(panelSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCemMebleg, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel5)
                                .addComponent(txtDollarkurs, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel11)
                                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap(113, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents


    private void jTree1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTree1MouseClicked
        mouseClicked();
    }//GEN-LAST:event_jTree1MouseClicked

    public void mouseClicked() {

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
            DefaultMutableTreeNode treeModel = (DefaultMutableTreeNode) jTree1.getSelectionPath().getLastPathComponent();

            String node1 = treeModel.getUserObject().toString();
            System.out.println("node1" + node1.toString());
            try {
                TreeNode node2 = treeModel.getParent();
                if (node2.toString() != null) {
                    try {
                        TreeNode node3 = node2.getParent();
                        if (node3 != null) {

                            TreeNode node4 = node3.getParent();
                            if (node4 != null) {

                                String season = node3.toString();

                                switch (season) {
                                    case "Yaz":
                                        season = "1";
                                        break;
                                    case "Yay":
                                        season = "2";
                                        break;
                                    case "Payiz":
                                        season = "3";
                                        break;
                                    case "Qis":
                                        season = "4";
                                        break;
                                }

                                String category = node2.toString();

                                switch (category) {
                                    case "kisi":
                                        category = "1";
                                        break;
                                    case "qadin":
                                        category = "2";
                                        break;
                                    case "usaq":
                                        category = "3";
                                        break;
                                    case "qarisiq":
                                        category = "4";
                                        break;
                                }

                                String sub = node1.toString();

                                switch (sub) {
                                    case "Salvarlar":
                                        sub = "1";
                                        break;
                                    case "Koynekler":
                                        sub = "2";
                                        break;
                                    case "Godekceler":
                                        sub = "3";
                                        break;
                                    case "Kurtkalar":
                                        sub = "4";
                                        break;

                                }
                                load3(season, category, sub);
                            } else {
                                // bura
                                String subCatId = null;
                                String mainCategoryId = null;
                                String category2 = node2.toString();

                                Connection c = connect();
                                pres = c.prepareStatement("select * from category where categories = " + "'" + category2 + "'");
                                ResultSet rs = pres.executeQuery();
                                while (rs.next()) {
                                    mainCategoryId = rs.getString("id");

                                }
                                String category = node1.toString();

                                Connection c2 = connect();
                                pres = c2.prepareStatement("select * from subcategory where name = " + "'" + category + "'" + " and `index` =" + mainCategoryId);
                                ResultSet rs2 = pres.executeQuery();
                                while (rs2.next()) {
                                    subCatId = rs2.getString("id");

                                }
                                load6(mainCategoryId, subCatId);
                            }

                        } else {
                            String season = node1.toString();
                            try {
                                Connection c = connect();
                                pres = c.prepareCall("select * from category where categories = " + "'" + season + "'");
                                ResultSet rs = pres.executeQuery();
                                while (rs.next()) {
                                    String mainCategoryId = rs.getString("id");
                                    load5(mainCategoryId);
                                }
                            } catch (Exception ex) {
                                System.out.println(ex);
                            }

                        }

                    } catch (Exception ex) {
                        TreeNode node3 = node2.getParent();
                        load5(node1);
                    }

                }

            } catch (Exception ex) {
                load4();
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

                    if (treeModel.getChildCount() == 1 || treeModel.getChildCount() == 0) {
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
    String veriable;

    public void addCategory() {

        try {
            DefaultMutableTreeNode treeModel = (DefaultMutableTreeNode) jTree1.getSelectionPath().getLastPathComponent();
            TreeNode node2 = treeModel.getParent();
            veriable = treeModel.toString();
            if (veriable != null) {
                if (node2 == null) {
                    System.out.println("Bura girdim");
                    //dispose();
                    AddCategory addcat = new AddCategory();
                    addcat.setVisible(true);
                    System.out.println("Amma bu iwde oldu");
                } else {

                    AddSubCategory addcat = new AddSubCategory();
                    //dispose();
                    addcat.subName = veriable;
                    addcat.setVisible(true);
                    System.out.println("Qaqaw bu iw oldu");

                }

            }

        } catch (Exception ex) {

        }

    }
    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        addCategory();

    }//GEN-LAST:event_jMenuItem1ActionPerformed

    String node1;
    String stNode2;
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
            obj.mainCat = stNode2;
            obj.subCat = node1;
            mouseClicked();
            obj.setVisible(true);
        } catch (Exception ex) {

        }

    }


    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        addCategory();
    }//GEN-LAST:event_jMenuItem5ActionPerformed
    int sayAlinanMallar = 1;
    public int id;
    public int say2;
    public static int say3;
    public String name;
    public double priceOfSale;
    public static double priceOfBuy;
    private void jTableMehsullarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableMehsullarMouseClicked

        int clickSayi = evt.getClickCount();

        if (clickSayi == 2) {
            GetProduct getProduct = new GetProduct(this, true);

            df = (DefaultTableModel) jTableMehsullar.getModel();

            int selected = jTableMehsullar.getSelectedRow();

            id = Integer.parseInt(df.getValueAt(selected, 0).toString());
            priceOfSale = Double.parseDouble(df.getValueAt(selected, 4).toString());
            name = (df.getValueAt(selected, 1).toString());
            int number = Integer.parseInt(df.getValueAt(selected, 2).toString());
            int remaininAmount = Integer.parseInt(df.getValueAt(selected, 2).toString());
            priceOfBuy = Double.parseDouble(df.getValueAt(selected, 3).toString());

            getProduct.productName = name;
            getProduct.productID = id;
            getProduct.txtProductName.setText(name);
            getProduct.txtFaktikMiqdar.setText(Integer.toString(number));
            getProduct.txtProductPrice.setText(Double.toString(priceOfBuy));

            getProduct.setVisible(true);
            getProduct.txtProductPrice.setText(Double.toString(priceOfBuy));

            if (getProduct.getNumber() == 0) {

            } else {

                Integer i = identification(id, priceOfBuy);

                if (i == 0) {

                    try {
                        Connection c;
                        c = connect();
                        Statement stmt = c.createStatement();
                        stmt.execute("select m.* from purchaseinvoice m order by say desc limit 1");
                        ResultSet rs = stmt.getResultSet();
                        rs.next();

                        say = rs.getInt("say");

                        Connection c2 = connect();
                        String query = "insert into purchaseinvoice (say, id, productName, numberOfProduct, priceOfSale, total) values(?,?,?,?,?,?)";
                        int say2 = getProduct.getNumber();
                        pres = c2.prepareStatement(query);
                        pres.setInt(1, say + 1);
                        pres.setInt(2, id);
                        pres.setString(3, name);
                        pres.setInt(4, say2);
                        pres.setDouble(5, priceOfBuy);
                        pres.setDouble(6, priceOfBuy * say2);
                        pres.executeUpdate();
                        sayAlinanMallar++;
                        load();
                        sebetinHesablanmasi();

                    } catch (Exception ex) {

                        try {
                            say = 1;
                            Connection c2 = connect();
                            String query = "insert into purchaseinvoice (say, id, productName, numberOfProduct, priceOfSale, total) values(?,?,?,?,?,?)";
                            say2 = getProduct.getNumber();

                            pres = c2.prepareStatement(query);
                            pres.setInt(1, say);
                            pres.setInt(2, id);
                            pres.setString(3, name);
                            pres.setInt(4, say2);
                            pres.setDouble(5, priceOfBuy);
                            pres.setDouble(6, priceOfBuy * say2);
                            pres.executeUpdate();
                            sayAlinanMallar++;
                            load();
                            sebetinHesablanmasi();
                        } catch (Exception ex2) {

                            ex2.printStackTrace();
                        }

                    }

                } else {

                    try {

                        con = connect();

                        Statement stmt = con.createStatement();
                        stmt.execute("SELECT * FROM purchaseinvoice  where id =" + id);
                        ResultSet rs = stmt.getResultSet();
                        rs.next();
                        double numberOfProduct = rs.getInt("numberOfProduct");

                        pres = con.prepareStatement("update purchaseinvoice set numberOfProduct = numberOfProduct + ? where id = ?");

                        pres.setInt(1, say3);
                        pres.setInt(2, id);
                        pres.executeUpdate();

                        pres = con.prepareStatement("SELECT * FROM purchaseinvoice  where id =" + id);
                        rs = pres.executeQuery();
                        rs.next();
                        double numberOfProduct2 = rs.getInt("numberOfProduct");

                        pres = con.prepareStatement("update purchaseinvoice set total = ? where id = ?");

                        pres.setDouble(1, numberOfProduct2 * priceOfBuy);
                        pres.setInt(2, id);
                        pres.executeUpdate();

                    } catch (Exception ex) {

                        ex.printStackTrace();

                    }
                    load();
                    sebetinHesablanmasi();

                }

            }

        } else {
            if (clickSayi == 1) {

                df = (DefaultTableModel) jTableMehsullar.getModel();

                int selected = jTableMehsullar.getSelectedRow();

                id = Integer.parseInt(df.getValueAt(selected, 0).toString());

                try {

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

                    //JOptionPane.showMessageDialog(panelTest, "DIQQET!", "Olmadida qaqaw", HEIGHT);
                }

            }
        }


    }//GEN-LAST:event_jTableMehsullarMouseClicked

    public void loadİmage() {

        try {

            byte[] imagedata = rs.getBytes("imagePath");
            format = new ImageIcon(imagedata);
            Image mm = format.getImage();
            Image img2 = mm.getScaledInstance(lblImage.getWidth(), lblImage.getHeight(), Image.SCALE_SMOOTH);
            ImageIcon image = new ImageIcon(img2);

            lblImage.setIcon(image);

        } catch (SQLException ex) {

            ex.printStackTrace();

        }

    }

    public Integer identification(int id, double qiymeti) {

        Integer i = 0;

        try ( Connection c = connect()) {
            GetProduct getProduct = new GetProduct(this, true);
            int numberOfProduct = (getProduct.number);
            String numberOfProduct2 = (getProduct.txtMiqdar.getText());
            int ss = numberOfProduct;
            Statement stmt = c.createStatement();
            stmt.execute("select * from PurchaseInvoice");
            ResultSet rs = stmt.getResultSet();

            while (rs.next()) {
                int id1 = rs.getInt("id");
                int miqdari = rs.getInt("numberOfProduct");
                double umumiMebleg = rs.getDouble("total");
                System.out.println(id1);
                if (id1 == id) {

                    try {
                        pres = c.prepareStatement("update PurchaseInvoice set numberOfProduct = ? where id =?");

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
            load();
        }

    }//GEN-LAST:event_tblYeniMehsullarKeyPressed

    public void detele() {

        df = (DefaultTableModel) tblYeniMehsullar.getModel();

        int selected = tblYeniMehsullar.getSelectedRow();

        int id = Integer.parseInt(df.getValueAt(selected, 0).toString());

        try {

            Connection c = connect();

            String query = "select m.* from PurchaseInvoice m where m.say = " + id;

            pres = c.prepareStatement(query);

            pres.executeQuery();

            ResultSet rs = pres.getResultSet();

            rs.next();
            int say = rs.getInt("say");
            String mehsulunAdi = rs.getString("productName");

            int cavab = JOptionPane.showConfirmDialog(this, "Silinən məhsulu geri qaytarmaq mümkün olmayacaq! \n" + mehsulunAdi + "-adli mehsulu silmək istədiyinizdən əminsiniz?", "Diqqət!", JOptionPane.YES_NO_CANCEL_OPTION);

            if (cavab == 0) {

                Connection c2 = connect();
                pres = c2.prepareStatement("delete from PurchaseInvoice where say =  " + say);
                pres.executeUpdate();

                Connection c3 = connect();
                pres = c3.prepareStatement("delete from autoinsertofpurchaseinvoice where say =  " + say);
                pres.executeUpdate();

                load();
                sebetinHesablanmasi();
                load();
                setTheCorrectNumberOfRow();
                JOptionPane.showMessageDialog(this, " " + mehsulunAdi + "-adli mehsul silindi");

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
            pres = con.prepareStatement("select * from PurchaseInvoice");
            rs = pres.executeQuery();

            while (rs.next()) {
                int ilk = rs.getInt("say");

                if (ilk == saySiraSayi) {

                    pres = con.prepareStatement("select * from PurchaseInvoice");
                    rs = pres.executeQuery();

                    while (rs.next()) {

                        if (yoxla == 0) {
                            pres = con.prepareStatement("update PurchaseInvoice set say = " + sayBura + " where say =" + sayBura);
                            pres.executeUpdate();
                            sayBura++;
                            yoxla++;
                            saySiraSayi++;
                        } else {

                            int sayYoxla = rs.getInt("say");
                            if (sayYoxla > sayBura) {
                                int checkNumber = sayYoxla - sayBura;
                                int TheCorrectNumber = sayYoxla - checkNumber;
                                pres = con.prepareStatement("update PurchaseInvoice set say = " + sayBura + " where say =" + sayYoxla);
                                pres.executeUpdate();
                                sayBura++;
                                yoxla++;
                                saySiraSayi++;
                            }
                            if (sayBura == sayYoxla) {

                                sayBura++;

                            }

                        }

                    }

                } else {

                    pres = con.prepareStatement("select * from PurchaseInvoice");
                    rs = pres.executeQuery();
                    rs.next();
                    pres = con.prepareStatement("update PurchaseInvoice set say = " + sayIlk + " where say = " + ilk);
                    pres.executeUpdate();
                    setTheCorrectNumberOfRow();
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void sil() {

        df = (DefaultTableModel) tblYeniMehsullar.getModel();

        int selected = tblYeniMehsullar.getSelectedRow();

        int id = Integer.parseInt(df.getValueAt(selected, 0).toString());

        try {
            Connection c = connect();
            pres = c.prepareStatement("delete from PurchaseInvoice where say = ? ");

            pres.setInt(1, id);
            pres.executeUpdate();
            load();

            df = (DefaultTableModel) tblYeniMehsullar.getModel();
            if (df.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "Səbət boşdur!");

            } else {

                sebetinHesablanmasi();

            }

            JOptionPane.showMessageDialog(this, "Mehsul silindi");

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(this, "Silmek istediyiniz \n mehsulu secin!");
            //ex.printStackTrace();

        }

    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        detele();
        load();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        report();

//        try {    
//                Class.forName("com.mysql.cj.jdbc.Driver");                
//                Connection c= connect();
//                String path="C:\\Users\\Shamil\\OneDrive\\Документы\\NetBeansProjects\\TestTreeView\\src\\main\\java\\com\\mycompany\\testtreeview\\Nakladnoy2.jrxml";
//                JasperReport jr;
//                jr = JasperCompileManager.compileReport(path);        
//                JasperPrint  jp = JasperFillManager.fillReport(jr,null,c);
//                JasperViewer.viewReport(jp,false);
//} catch (Exception e) 
//{
//            JOptionPane.showMessageDialog(null, "error "+e);
//}
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

    private void listNameOfClientsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listNameOfClientsMouseClicked

        String selectedClientName = listNameOfClients.getSelectedValue();
        txtNameAndSurename.setText(selectedClientName);
        modelList.removeAllElements();
        panelTest.setVisible(false);
        //addContractNumber();
    }//GEN-LAST:event_listNameOfClientsMouseClicked

    public void addContractNumber() {

        try {

            Connection c;
            c = connect();
            Statement stmt = c.createStatement();
            stmt.execute("select b.* from purchaseBills b order by id");
            ResultSet rs = stmt.getResultSet();

            while (rs.next()) {

                int numberOfBill = rs.getInt("id");
                int thelastNumber = numberOfBill + 1;
                String lastnum = "2022-00000" + Integer.toString(thelastNumber);
                txtNumberOfBill.setText(lastnum);
            }

        } catch (Exception ex) {

            int thelastNumber = 1;
            String lastnum = "2022-00000" + Integer.toString(thelastNumber);
            txtNumberOfBill.setText(lastnum);

        }

        int thelastNumber = 1;
        String lastnum = "2022-00000" + Integer.toString(thelastNumber);
        txtNumberOfBill.setText(lastnum);
    }


    private void jButton3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseClicked

        CreateNewContractor newContractor = new CreateNewContractor(this, true);
        newContractor.setVisible(true);


    }//GEN-LAST:event_jButton3MouseClicked

    private void txtNameAndSurenameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNameAndSurenameActionPerformed

//        try {
//            String nameOfClient = txtNameAndSurename.getText();
//            Connection c = connect();
//
//            String query = "SELECT c.NumberOfContract FROM `clients` c where c.NameAndSurename = " + "'" + nameOfClient + "'";
//            pres = c.prepareStatement(query);
//            ResultSet rs = pres.executeQuery();
//
//            rs.next();
//            String contractNumber = rs.getString("NumberOfContract");
//            txtNumberOfContract.setText(contractNumber);
//
//        } catch (Exception ex) {
//            Logger.getLogger(PurchaseInvoice.class.getName()).log(Level.SEVERE, null, ex);
//        }

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

        String nameAndSurename = txtNameAndSurename.getText();
        String numberOfContract = txtCommentary.getText();
        String billNumber = txtNumberOfBill.getText();
        double kursDollar = Double.parseDouble(txtDollarkurs.getText());
        double total = Double.parseDouble(txtCemMebleg.getText());
        String time = txtTime.getText();

        try {

            Connection c = connect();
            pres = c.prepareStatement("insert into purchaseBills (nameOfClient, NumberOfBill, numberOfContract, TotalSumOfBill, DateCreation, TypeOfDocument, dollarKurs, status ) values(?, ?, ?, ?, ?, ?, ?, ? )");
            pres.setString(1, nameAndSurename);
            pres.setString(2, billNumber);
            pres.setString(3, numberOfContract);
            pres.setDouble(4, total);
            pres.setString(5, time);
            pres.setString(6, "Приходная накладная");
            pres.setDouble(7, kursDollar);
            pres.setString(8, "Непроверенный документ");
            pres.executeUpdate();

            pres = con.prepareStatement("select * from purchaseinvoice");
            rs = pres.executeQuery();

            String ID, Miqdari, Ümumi_Məbləğ;
            df = (DefaultTableModel) tblYeniMehsullar.getModel();

            boolean yoxla = rbtnDontRefreshPrices.isSelected();

            if (yoxla != true) {

                while (rs.next()) {

                    int id = rs.getInt("id");
                    String name = rs.getString("productName");
                    int number = rs.getInt("numberOfProduct");
                    double priceOfProduct = rs.getDouble("priceOfSale");
                    double total2 = rs.getDouble("total");

                    String query2 = "update mehsullar set Miqdari = Miqdari + ?, Qaliq_say = Qaliq_say + ?, Alis_qiymeti = ? where id = ?";

                    pres = con.prepareStatement(query2);

                    pres.setInt(1, number);
                    pres.setInt(2, number);
                    pres.setDouble(3, priceOfProduct);
                    pres.setInt(4, id);
                    pres.executeUpdate();

                }
            } else {
                while (rs.next()) {

                    int id = rs.getInt("id");
                    String name = rs.getString("productName");
                    int number = rs.getInt("numberOfProduct");
                   // double priceOfProduct = rs.getDouble("priceOfSale");
                    double total2 = rs.getDouble("total");

                    String query2 = "update mehsullar set Miqdari = Miqdari + ?, Qaliq_say = Qaliq_say + ? where id = ?";

                    pres = con.prepareStatement(query2);

                    pres.setInt(1, number);
                    pres.setInt(2, number);
                    pres.setInt(3, id);
                    pres.executeUpdate();

                }

            }
            pres = c.prepareStatement("truncate table PurchaseInvoice;");
            pres.executeUpdate();

            con = connect();
            pres = con.prepareStatement("truncate table autoinsertofpurchaseinvoice");
            pres.executeUpdate();

            con = connect();
            pres = con.prepareStatement("truncate table totalsumforautoinsert");
            pres.executeUpdate();

            load();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed

        writeFileFromJtableToFile();
        Save();
        findTheLastBiilNumber();
        txtNameAndSurename.setText("");
        txtCommentary.setText("");
        txtCemMebleg.setText("");

        JOptionPane.showMessageDialog(this, "Hazirdi Qaqaw");
//        Main main = new Main();
//        main.setVisible(false);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed

        String[] ss = {"Salam"};
        GetProductsFromExcelFileToPurchaseInvoice1 autoInsertOfProduct = new GetProductsFromExcelFileToPurchaseInvoice1();
        autoInsertOfProduct.main(ss);
        GetProductsFromExcelFileToPurchaseInvoice1.sayFOrBosluq = 0;
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
            String commentary1 = txtCommentary.getText();

            if (commentary1.equals("")) {
                txtCommentary.setText((commentary));
                pres = con.prepareStatement("truncate table totalsumforautoinsert");
                pres.executeUpdate();
            } else {

                txtCommentary.setText((commentary1 + "+" + commentary));
                pres = con.prepareStatement("truncate table totalsumforautoinsert");
                pres.executeUpdate();

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void rb_mergeOfFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rb_mergeOfFileActionPerformed

        boolean yoxla = rb_mergeOfFile.isSelected();
        if (yoxla == false) {
            GetProductsFromExcelFileToPurchaseInvoice1 getFiles = new GetProductsFromExcelFileToPurchaseInvoice1();
            int say1 = getFiles.say = 0;
        } else {
            GetProductsFromExcelFileToPurchaseInvoice1 getFiles = new GetProductsFromExcelFileToPurchaseInvoice1();
            int say1 = getFiles.say = 1;

        }


    }//GEN-LAST:event_rb_mergeOfFileActionPerformed

    private void jTableMehsullarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTableMehsullarKeyReleased

        panelSearch.setVisible(true);
        txtSearch.requestFocus();
    }//GEN-LAST:event_jTableMehsullarKeyReleased

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased

        txtSearch.requestFocus();
        String text = txtSearch.getText();

        int a;
        try {

            Connection c = connect();
            pres = c.prepareCall("select * from qaimemehsullariForSerach c where c.productName like " + "'" + "%" + text + "%" + "'");

            ResultSet rs2 = pres.executeQuery();

            while (rs2.next()) {

                ResultSetMetaData rd = rs2.getMetaData();
                a = rd.getColumnCount();
                df = (DefaultTableModel) jTableMehsullar.getModel();
                df.setRowCount(0);

                while (rs2.next()) {
                    Vector v2 = new Vector();
                    for (int i = 0; i < a; i++) {

                        int id2 = rs2.getInt("id");
                        v2.add(id2);
                        String malinAdi = rs2.getString("productName");
                        v2.add(malinAdi);
                        v2.add(rs2.getInt("QaliqSay"));
                        v2.add(rs2.getDouble("PriceOfBuy"));
                        v2.add(rs2.getInt("priceOfSale"));
                        //v2.add(satisQiymeti);
                        v2.add(rs2.getString("tarix"));
                        v2.add(rs2.getString("Barcode"));
                    }
                    df.addRow(v2);

                }

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }//GEN-LAST:event_txtSearchKeyReleased

    public void processWithProducts() {

        try {

            con = connect();
            pres = con.prepareStatement("");

        } catch (Exception ex) {

            ex.printStackTrace();

        }

    }

    public void report() {

        String name = txtNameAndSurename.getText();
        String kursDollar2 = txtDollarkurs.getText();

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
            System.out.println("Proyektin islediyi path... "+projectPath);
            if (projectPath.contains("C:\\Users\\Shamil")) {
                
                projectPath ="C:\\Users\\Shamil\\Downloads\\TestTreeView";
                
            }
            if (projectPath.contains("C:\\Users\\Tacir Aliyev")) {
                
                projectPath ="C:\\Users\\Tacir Aliyev\\Documents\\TestTreeView";
            }
                String filePath = "\\src\\main\\java\\com\\mycompany\\testtreeview\\testReport_1_1_1_1.jrxml";
                JasperDesign jdesign = JRXmlLoader.load(projectPath+filePath);
                
                String query = "select * from purchaseinvoice";
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
                String kursDollar = txtDollarkurs.getText() + "000";
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
                        String total = txtCemMebleg.getText() + " руб.";
                        String total222 = txtCemMebleg.getText();
                        String totalForDouble = txtCemMebleg.getText();
                        double totalPriceDouble = Double.parseDouble(totalForDouble);
                        double totalSumWithDollar2 = totalPriceDouble / kursDol;
                        String finalSum = Double.toString(totalSumWithDollar2);
                        String commentary = txtCommentary.getText();
                        String pastavsik = "Алиев Таджир";
                        String billtNumber = txtNumberOfBill.getText();
                        HashMap<String, Object> parametrs;
                        parametrs = new HashMap<>();
                        parametrs.put("kusrDollarSon", kursDollar);
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

    public void load() {

        int a;
        try {

            Connection c = connect();

            pres = c.prepareCall("select * from purchaseinvoice");

            ResultSet rs = pres.executeQuery();

            ResultSetMetaData rd = rs.getMetaData();
            a = rd.getColumnCount();
            df = (DefaultTableModel) tblYeniMehsullar.getModel();
            df.setRowCount(0);

            while (rs.next()) {
                Vector v2 = new Vector();
                for (int i = 0; i < a; i++) {

                    v2.add(rs.getInt("say"));
                    v2.add(rs.getString("productName"));
                    v2.add(rs.getInt("numberOfProduct"));
                    v2.add(rs.getDouble("priceOfSale"));
                    v2.add(rs.getDouble("total"));
                    v2.add(rs.getInt("id"));
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
                    Logger.getLogger(PurchaseInvoice.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InstantiationException ex) {
                    Logger.getLogger(PurchaseInvoice.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(PurchaseInvoice.class.getName()).log(Level.SEVERE, null, ex);
                } catch (UnsupportedLookAndFeelException ex) {
                    Logger.getLogger(PurchaseInvoice.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } //</editor-fold>

        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PurchaseInvoice().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton chcekOtbor;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
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
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JPopupMenu jPopupMenu2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable jTableMehsullar;
    private javax.swing.JTree jTree1;
    private javax.swing.JLabel lblImage;
    private javax.swing.JList<String> listNameOfClients;
    private javax.swing.JPanel panelSearch;
    private javax.swing.JPanel panelTest;
    public javax.swing.JRadioButton rb_mergeOfFile;
    private javax.swing.JRadioButton rbtnDontRefreshPrices;
    public javax.swing.JTable tblYeniMehsullar;
    private javax.swing.JTextField txtCemMebleg;
    private javax.swing.JTextField txtCommentary;
    private javax.swing.JTextField txtDollarkurs;
    private javax.swing.JTextField txtNameAndSurename;
    private javax.swing.JTextField txtNumberOfBill;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtTime;
    // End of variables declaration//GEN-END:variables
}
