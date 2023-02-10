/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package com.mycompany.qarisiqmallar.veneramarket;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultTreeModel;

/**
 *
 * @author Shamil
 */
public class NewProduct extends javax.swing.JDialog {

    // public MehsullarDaoInter mehDao = Contex.instanceOfMehsullarDao();
    String mehsulAdi, mainCat, subCat, secondSubCat, thirdSubCat, optionForAddingProcess;
    double priceOfBuy;
    double priceOfSell;

    Connection con;
    PreparedStatement pres;
    DefaultTreeModel model;
    DefaultTableModel df;

    String path;
    File f;
    String path55 = null;
    ImageIcon format = null;
    String filename;
    int s = 0;
    byte[] pimage = null;

    public NewProduct(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        Toolkit toolkit = getToolkit();
        Dimension size = toolkit.getScreenSize();
        setLocation(size.width / 2 - getWidth() / 2, size.height / 2 - getHeight() / 2);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        txtMehsulAdi = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        txtSatisQiymeti = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtAlisQiymeti = new javax.swing.JTextField();
        panelImage = new javax.swing.JPanel();
        lbl_Image = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        txtFilePath = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("Mehsul adi :");

        jButton1.setText("Elave et");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel2.setText("Alis Qiymeti :");

        jLabel3.setText("Satis Qiymeti :");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtSatisQiymeti, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtMehsulAdi, javax.swing.GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
                                .addComponent(txtAlisQiymeti)))))
                .addGap(13, 13, 13))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(txtMehsulAdi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtAlisQiymeti, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSatisQiymeti, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(15, 15, 15))
        );

        lbl_Image.setBackground(new java.awt.Color(255, 255, 0));
        lbl_Image.setForeground(new java.awt.Color(102, 255, 0));
        lbl_Image.setBorder(new javax.swing.border.MatteBorder(null));

        javax.swing.GroupLayout panelImageLayout = new javax.swing.GroupLayout(panelImage);
        panelImage.setLayout(panelImageLayout);
        panelImageLayout.setHorizontalGroup(
            panelImageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_Image, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panelImageLayout.setVerticalGroup(
            panelImageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelImageLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbl_Image, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jButton2.setText("Выбирать..");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelImage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 52, Short.MAX_VALUE)
                        .addComponent(jButton2)
                        .addGap(9, 9, 9))
                    .addComponent(txtFilePath))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelImage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtFilePath, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2)
                .addGap(14, 14, 14))
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public String getMehsulAdi() {
        return mehsulAdi;
    }

    public Connection connect() {
        try {

            //Class.forName("com.mysql.jdbc.Driver");
            String url = ("jdbc:mysql://localhost:3306/mehsullar");
            String usercategoryOfProduct = ("root");
            String password = ("dxdiag92");
            con = DriverManager.getConnection(url, usercategoryOfProduct, password);

        } catch (Exception ex) {

        }

        return con;

    }

    public void createNewProduct() {

        try {

            mehsulAdi = this.txtMehsulAdi.getText();
            priceOfBuy = Double.parseDouble(this.txtAlisQiymeti.getText());
            priceOfSell = Double.parseDouble(this.txtSatisQiymeti.getText());
            
            if (priceOfBuy >= priceOfSell) {
                
                JOptionPane.showMessageDialog(this, "Alış qiyməti satış qiymətindən böyük vəya ona bərabər ola bilməz");
                return;
            }

            if (optionForAddingProcess.equals("addToTheMainCat")) {
                addProducToTheMainCategory_1();
                addConfirmProductToTheMainCat(mainCatID);
                addBarcode();
                JOptionPane.showMessageDialog(this, "Yeni mehsul ugurla elave olundu");
                this.dispose();
            }
            if (optionForAddingProcess.equals("addToTheSubCat")) {
                addProductToTheSubCat();
                addConfirmProductToTheSubCat(mainCatID, subCatID);
                addBarcode();
                JOptionPane.showMessageDialog(this, "Yeni mehsul ugurla elave olundu");
                this.dispose();

            }
            if (optionForAddingProcess.equals("addToTheSecondSubCat")) {

                addProductToTheSecondSubCat();
                addConfirmProductToTheSubCat(mainCatID, subCatID, SecondSubCatID);
                addBarcode();
                JOptionPane.showMessageDialog(this, "Yeni mehsul ugurla elave olundu");
                this.dispose();
            }
            if (optionForAddingProcess.equals("addToTheThirdSubCat")) {

                System.out.println("Men 7ye duwdum");
                addProductToTheThirdSubCat();
                addConfirmProductToTheSubCat(mainCatID, subCatID, SecondSubCatID, thirdSubCatID);
                addBarcode();
                JOptionPane.showMessageDialog(this, "Yeni mehsul ugurla elave olundu");
                this.dispose();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        createNewProduct();

//        try {
//            mehsulAdi = this.txtMehsulAdi.getText();
//            priceOfBuy = Double.parseDouble(this.txtAlisQiymeti.getText());
//            priceOfSell = Double.parseDouble(this.txtSatisQiymeti.getText());
//            Connection c2 = connect();
//
//            pres = c2.prepareStatement("select * from category where categories = " + "'" + mainCat + "'");
//            ResultSet rs2 = pres.executeQuery();
//
//            rs2.next();
//            String mainCatName = rs2.getString("categories");
//            int mainCatID = rs2.getInt("id");
//
//            pres = c2.prepareStatement("select * from subcategory where name = " + "'" + subCat + "'");
//            ResultSet rs3 = pres.executeQuery();
//
//            rs3.next();
//            String subCatName = rs3.getString("name");
//            int subCatID = rs3.getInt("id");
//
//            pres = c2.prepareStatement("select * from 2ndsubcategory where name = " + "'" + secondSubCat + "'");
//            ResultSet rs4 = pres.executeQuery();
//
//            rs4.next();
//            String SecondSubCatName = rs4.getString("name");
//            int SecondSubCatID = rs4.getInt("id");
//
//            pres = c2.prepareStatement("select * from 3ndsubcategory where name = " + "'" + thirdSubCat + "'");
//            ResultSet rs5 = pres.executeQuery();
//
//            rs5.next();
//            String thirdSubCatName = rs5.getString("name");
//            int thirdSubCatName2 = rs5.getInt("id");
//
//            try {
//
//                System.out.println("Image path.." + filename);
//                System.out.println("Image name.." + f.getName());
//                File f = new File(filename);
//                InputStream is = new FileInputStream(f);
//
//                Connection c = connect();
//                pres = c.prepareStatement("insert into mehsullar (Malin_adi,mainCategory, subCategory, 2ndsubcategory,3ndsubcategory, Alis_qiymeti, Satis_qiymeti, Alisin_toplam_deyer, Satis_miqdari, Satisin_toplam_deyeri,Qaliq_say,imagePath ) values(?,?,?,?,?,?,?,?,?,?,?)");
//                pres.setString(1, mehsulAdi);
//                pres.setInt(2, mainCatID);
//                pres.setInt(3, subCatID);
//                pres.setInt(4, SecondSubCatID);
//                pres.setInt(5, thirdSubCatName2);
//                pres.setDouble(6, priceOfBuy);
//                pres.setDouble(7, priceOfSell);
//                pres.setDouble(8, 0.0);
//                pres.setDouble(9, 0.0);
//                pres.setDouble(10, 0.0);
//                pres.setDouble(11, 0.0);
//                pres.setBlob(12, is);
//                pres.executeUpdate();
//
//            } catch (Exception ex) {
//
//                ex.printStackTrace();
//
//                Connection c = connect();
//                pres = c.prepareStatement("insert into mehsullar (Malin_adi,mainCategory, subCategory, 2ndsubcategory,3ndsubcategory, Alis_qiymeti, Satis_qiymeti, Alisin_toplam_deyer, Satis_miqdari, Satisin_toplam_deyeri,Qaliq_say ) values(?,?,?,?,?,?,?,?,?,?,?)");
//                pres.setString(1, mehsulAdi);
//                pres.setInt(2, mainCatID);
//                pres.setInt(3, subCatID);
//                pres.setInt(4, SecondSubCatID);
//                pres.setInt(5, thirdSubCatName2);
//                pres.setDouble(6, priceOfBuy);
//                pres.setDouble(7, priceOfSell);
//                pres.setDouble(8, 0.0);
//                pres.setDouble(9, 0.0);
//                pres.setDouble(10, 0.0);
//                pres.setDouble(11, 0.0);
//                pres.executeUpdate();
//
//            }
//            try {
//
//                Connection c = connect();
//                Statement stmt = c.createStatement();
//                stmt.execute("select m.* from mehsullar m order by id desc limit 1");
//                ResultSet rs = stmt.getResultSet();
//
//                while (rs.next()) {
//
//                    int id = rs.getInt("id");
//
//                    pres = c.prepareStatement("update mehsullar set Barcode=? where id =" + id);
//                    pres.setString(1, "1234500" + id);
//                    pres.executeUpdate();
//
//                }
//
//            } catch (Exception ex) {
//                ex.printStackTrace();
//
//            }
//
//            JOptionPane.showMessageDialog(this, "Yeni mehsul ugurla elave olundu");
//            TreeView1.mouseClicked();
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//
//        this.dispose();

    }//GEN-LAST:event_jButton1ActionPerformed

    public void addProductToTheThirdSubCat() {

        try {
            con = connect();

            pres = con.prepareStatement("select * from category where categories = " + "'" + mainCat + "'");
            ResultSet rs2 = pres.executeQuery();

            rs2.next();
            String mainCatName = rs2.getString("categories");
            mainCatID = rs2.getInt("id");

            pres = con.prepareStatement("select * from subcategory where name = " + "'" + subCat + "'" + " and `index` = " + mainCatID);
            ResultSet rs3 = pres.executeQuery();

            rs3.next();
            String subCatName = rs3.getString("name");
            subCatID = rs3.getInt("id");

            pres = con.prepareStatement("select * from 2ndsubcategory where name = " + "'" + secondSubCat + "'" + " and `index-gender` = " + subCatID + " and `index-season` = " + mainCatID);
            ResultSet rs4 = pres.executeQuery();

            rs4.next();
            String SecondSubCatName = rs4.getString("name");
            SecondSubCatID = rs4.getInt("id");

            pres = con.prepareStatement("select * from 3ndsubcategory where name = " + "'" + thirdSubCat + "'" + " and `index-gender` = " + subCatID + " and `index-season` = " + mainCatID + " and `index-3rdSubCat` = " + SecondSubCatID);
            ResultSet rs5 = pres.executeQuery();

            rs5.next();
            String thirdSubCatName = rs5.getString("name");
            thirdSubCatID = rs5.getInt("id");

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void addProductToTheSubCat() {

        try {

            con = connect();

            pres = con.prepareStatement("select * from category where categories = " + "'" + secondSubCat + "'");
            ResultSet rs2 = pres.executeQuery();

            rs2.next();
            String mainCatName = rs2.getString("categories");
            mainCatID = rs2.getInt("id");

            pres = con.prepareStatement("select * from subcategory where name = " + "'" + thirdSubCat + "'");
            ResultSet rs3 = pres.executeQuery();

            rs3.next();
            String subCatName = rs3.getString("name");
            subCatID = rs3.getInt("id");

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    int SecondSubCatID = 0;
    int thirdSubCatID = 0;

    int mainCatID = 0;
    int subCatID = 0;

    public void addProducToTheMainCategory_1() {

        con = connect();
        try {

            pres = con.prepareStatement("select * from category where categories = " + "'" + thirdSubCat + "'");
            ResultSet rs2 = pres.executeQuery();

            rs2.next();
            String mainCatName = rs2.getString("categories");
            mainCatID = rs2.getInt("id");

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void addProductToTheSecondSubCat() {

        try {

            con = connect();

            pres = con.prepareStatement("select * from category where categories = " + "'" + subCat + "'");
            ResultSet rs2 = pres.executeQuery();

            rs2.next();
            String mainCatName = rs2.getString("categories");
            mainCatID = rs2.getInt("id");

            pres = con.prepareStatement("select * from subcategory where name = " + "'" + secondSubCat + "'");
            ResultSet rs3 = pres.executeQuery();

            rs3.next();
            String subCatName = rs3.getString("name");
            subCatID = rs3.getInt("id");

            pres = con.prepareStatement("select * from 2ndsubcategory where name = " + "'" + thirdSubCat + "'");
            ResultSet rs4 = pres.executeQuery();

            rs4.next();
            String SecondSubCatName = rs4.getString("name");
            SecondSubCatID = rs4.getInt("id");

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void addConfirmProductToTheMainCat(int mainCatID) {

        try {

            System.out.println("Image path.." + filename);
            System.out.println("Image name.." + f.getName());
            File f = new File(filename);
            InputStream is = new FileInputStream(f);

            Connection c = connect();
            pres = c.prepareStatement("insert into mehsullar (Malin_adi,mainCategory, subCategory, 2ndSubCategory, 3ndSubCategory, Alis_qiymeti, Satis_qiymeti, Alisin_toplam_deyer, Satis_miqdari, Satisin_toplam_deyeri,Qaliq_say,imagePath, Miqdari, Movsum_id, Kateqoriya_id, Alt_kateqoriya_id ) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            pres.setString(1, mehsulAdi);
            pres.setInt(2, mainCatID);
            pres.setInt(3, subCatID);
            pres.setInt(4, 0);
            pres.setInt(5, 0);
            pres.setDouble(6, priceOfBuy);
            pres.setDouble(7, priceOfSell);
            pres.setDouble(8, 0.0);
            pres.setDouble(9, 0.0);
            pres.setDouble(10, 0.0);
            pres.setDouble(11, 0.0);
            pres.setBlob(12, is);
            pres.setDouble(13, 0.0);
            pres.setInt(14, 0);
            pres.setInt(15, 0);
            pres.setInt(16, 0);
            pres.executeUpdate();

        } catch (Exception ex) {

            ex.printStackTrace();

            Connection c = connect();
            try {
                pres = c.prepareStatement("insert into mehsullar (Malin_adi,mainCategory, subCategory, 2ndSubCategory, 3ndSubCategory, Alis_qiymeti, Satis_qiymeti, Alisin_toplam_deyer, Satis_miqdari, Satisin_toplam_deyeri,Qaliq_say, Miqdari, Movsum_id, Kateqoriya_id, Alt_kateqoriya_id ) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

                pres.setString(1, mehsulAdi);
                pres.setInt(2, mainCatID);
                pres.setInt(3, 0);
                pres.setInt(4, 0);
                pres.setInt(5, 0);
                pres.setDouble(6, priceOfBuy);
                pres.setDouble(7, priceOfSell);
                pres.setDouble(8, 0.0);
                pres.setDouble(9, 0.0);
                pres.setDouble(10, 0.0);
                pres.setDouble(11, 0.0);
                pres.setDouble(12, 0.0);
                pres.setInt(13, 0);
                pres.setInt(14, 0);
                pres.setInt(15, 0);
                pres.executeUpdate();

            } catch (Exception ex1) {
                ex1.printStackTrace();
            }

        }

    }

    public void addConfirmProductToTheSubCat(int mainCatID, int subCatID) {

        try {

            System.out.println("Image path.." + filename);
            System.out.println("Image name.." + f.getName());
            File f = new File(filename);
            InputStream is = new FileInputStream(f);

            Connection c = connect();
            pres = c.prepareStatement("insert into mehsullar (Malin_adi,mainCategory, subCategory, 2ndsubcategory,3ndsubcategory, Alis_qiymeti, Satis_qiymeti, Alisin_toplam_deyer, Satis_miqdari, Satisin_toplam_deyeri,Qaliq_say,imagePath, Miqdari, Movsum_id, Kateqoriya_id, Alt_kateqoriya_id ) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            pres.setString(1, mehsulAdi);
            pres.setInt(2, mainCatID);
            pres.setInt(3, subCatID);
            pres.setInt(4, 0);
            pres.setInt(5, 0);
            pres.setDouble(6, priceOfBuy);
            pres.setDouble(7, priceOfSell);
            pres.setDouble(8, 0.0);
            pres.setDouble(9, 0.0);
            pres.setDouble(10, 0.0);
            pres.setDouble(11, 0.0);
            pres.setBlob(12, is);
            pres.setDouble(13, 0.0);
            pres.setInt(14, 0);
            pres.setInt(15, 0);
            pres.setInt(16, 0);
            pres.executeUpdate();

        } catch (Exception ex) {

            ex.printStackTrace();

            Connection c = connect();
            try {
                pres = c.prepareStatement("insert into mehsullar (Malin_adi,mainCategory, subCategory, 2ndsubcategory,3ndsubcategory, Alis_qiymeti, Satis_qiymeti, Alisin_toplam_deyer, Satis_miqdari, Satisin_toplam_deyeri,Qaliq_say, Miqdari, Movsum_id, Kateqoriya_id, Alt_kateqoriya_id ) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

                pres.setString(1, mehsulAdi);
                pres.setInt(2, mainCatID);
                pres.setInt(3, subCatID);
                pres.setInt(4, 0);
                pres.setInt(5, 0);
                pres.setDouble(6, priceOfBuy);
                pres.setDouble(7, priceOfSell);
                pres.setDouble(8, 0.0);
                pres.setDouble(9, 0.0);
                pres.setDouble(10, 0.0);
                pres.setDouble(11, 0.0);
                pres.setDouble(12, 0.0);
                pres.setInt(13, 0);
                pres.setInt(14, 0);
                pres.setInt(15, 0);
                pres.executeUpdate();

            } catch (Exception ex1) {
                ex1.printStackTrace();
            }

        }

    }

    public void addConfirmProductToTheSubCat(int mainCatID, int subCatID, int secondSubCatID) {

        try {

            System.out.println("Image path.." + filename);
            System.out.println("Image name.." + f.getName());
            File f = new File(filename);
            InputStream is = new FileInputStream(f);

            Connection c = connect();
            pres = c.prepareStatement("insert into mehsullar (Malin_adi,mainCategory, subCategory, 2ndsubcategory,3ndsubcategory, Alis_qiymeti, Satis_qiymeti, Alisin_toplam_deyer, Satis_miqdari, Satisin_toplam_deyeri,Qaliq_say,imagePath, Miqdari, Movsum_id, Kateqoriya_id, Alt_kateqoriya_id ) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            pres.setString(1, mehsulAdi);
            pres.setInt(2, mainCatID);
            pres.setInt(3, subCatID);
            pres.setInt(4, secondSubCatID);
            pres.setInt(5, 0);
            pres.setDouble(6, priceOfBuy);
            pres.setDouble(7, priceOfSell);
            pres.setDouble(8, 0.0);
            pres.setDouble(9, 0.0);
            pres.setDouble(10, 0.0);
            pres.setDouble(11, 0.0);
            pres.setBlob(12, is);
            pres.setDouble(13, 0.0);
            pres.setInt(14, 0);
            pres.setInt(15, 0);
            pres.setInt(16, 0);
            pres.executeUpdate();

        } catch (Exception ex) {

            ex.printStackTrace();

            Connection c = connect();
            try {
                pres = c.prepareStatement("insert into mehsullar (Malin_adi,mainCategory, subCategory, 2ndsubcategory,3ndsubcategory, Alis_qiymeti, Satis_qiymeti, Alisin_toplam_deyer, Satis_miqdari, Satisin_toplam_deyeri,Qaliq_say, Miqdari, Movsum_id, Kateqoriya_id, Alt_kateqoriya_id ) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

                pres.setString(1, mehsulAdi);
                pres.setInt(2, mainCatID);
                pres.setInt(3, subCatID);
                pres.setInt(4, secondSubCatID);
                pres.setInt(5, 0);
                pres.setDouble(6, priceOfBuy);
                pres.setDouble(7, priceOfSell);
                pres.setDouble(8, 0.0);
                pres.setDouble(9, 0.0);
                pres.setDouble(10, 0.0);
                pres.setDouble(11, 0.0);
                pres.setDouble(12, 0.0);
                pres.setInt(13, 0);
                pres.setInt(14, 0);
                pres.setInt(15, 0);
                pres.executeUpdate();

            } catch (Exception ex1) {
                ex1.printStackTrace();
            }

        }

    }

    public void addConfirmProductToTheSubCat(int mainCatID, int subCatID, int secondSubCatID, int thirdSubCatID) {

        try {

            System.out.println("Image path.." + filename);
            System.out.println("Image name.." + f.getName());
            File f = new File(filename);
            InputStream is = new FileInputStream(f);

            Connection c = connect();
            pres = c.prepareStatement("insert into mehsullar (Malin_adi,mainCategory, subCategory, 2ndsubcategory,3ndsubcategory, Alis_qiymeti, Satis_qiymeti, Alisin_toplam_deyer, Satis_miqdari, Satisin_toplam_deyeri,Qaliq_say,imagePath, Miqdari, Movsum_id, Kateqoriya_id, Alt_kateqoriya_id ) values(?,?,?,?,?,?,?,?,?,?,?,?,?)");
            pres.setString(1, mehsulAdi);
            pres.setInt(2, mainCatID);
            pres.setInt(3, subCatID);
            pres.setInt(4, secondSubCatID);
            pres.setInt(5, thirdSubCatID);
            pres.setDouble(6, priceOfBuy);
            pres.setDouble(7, priceOfSell);
            pres.setDouble(8, 0.0);
            pres.setDouble(9, 0.0);
            pres.setDouble(10, 0.0);
            pres.setDouble(11, 0.0);
            pres.setBlob(12, is);
            pres.setDouble(13, 0.0);
            pres.setInt(14, 0);
            pres.setInt(15, 0);
            pres.setInt(16, 0);
            pres.executeUpdate();

        } catch (Exception ex) {

            ex.printStackTrace();

            Connection c = connect();
            try {
                pres = c.prepareStatement("insert into mehsullar (Malin_adi,mainCategory, subCategory, 2ndsubcategory, 3ndsubcategory, Alis_qiymeti, Satis_qiymeti, Alisin_toplam_deyer, Satis_miqdari, Satisin_toplam_deyeri,Qaliq_say, Miqdari, Movsum_id, Kateqoriya_id, Alt_kateqoriya_id ) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

                pres.setString(1, mehsulAdi);
                pres.setInt(2, mainCatID);
                pres.setInt(3, subCatID);
                pres.setInt(4, secondSubCatID);
                pres.setInt(5, thirdSubCatID);
                pres.setDouble(6, priceOfBuy);
                pres.setDouble(7, priceOfSell);
                pres.setDouble(8, 0.0);
                pres.setDouble(9, 0.0);
                pres.setDouble(10, 0.0);
                pres.setDouble(11, 0.0);
                pres.setDouble(12, 0.0);
                pres.setInt(13, 0);
                pres.setInt(14, 0);
                pres.setInt(15, 0);
                pres.executeUpdate();

            } catch (Exception ex1) {
                ex1.printStackTrace();
            }

        }

    }

    public void addBarcode() {

        try {

            Connection c = connect();
            Statement stmt = c.createStatement();
            stmt.execute("select m.* from mehsullar m order by id desc limit 1");
            ResultSet rs = stmt.getResultSet();

            while (rs.next()) {

                int id = rs.getInt("id");
                String newBarcode = "1234500"+id;
                String checkedNewBarcode = TestClass.setNewBarcode(newBarcode, id);

                pres = c.prepareStatement("update mehsullar set Barcode=? where id =" + id);
                pres.setString(1, checkedNewBarcode);
                pres.executeUpdate();

            }
            ProductCategories.mouseClicked();

        } catch (Exception ex) {
            ex.printStackTrace();

        }

    }

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        //chooseImage();
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("C:\\Users\\Tacir Aliyev\\Downloads"));
        chooser.setDialogTitle("Şəkili seçin");
        FileFilter filter = new FileNameExtensionFilter("Image Files(JPEG, JPG, PNG,)", "JPEG", "JPG", "PNG");
        chooser.setFileFilter(filter);
        chooser.showOpenDialog(null);
        f = chooser.getSelectedFile();
        filename = f.getAbsolutePath();
        String imagePath = filename;
        //Image getAbsolutePath = null;
        try {

            BufferedImage img = null;
            img = ImageIO.read(new File(f.getAbsolutePath()));
            Image img2 = img.getScaledInstance(lbl_Image.getWidth(), lbl_Image.getHeight(), Image.SCALE_SMOOTH);
            format = new ImageIcon(img2);
            lbl_Image.setIcon(format);

        } catch (Exception ex) {

        }

        filename = f.getAbsolutePath();
        txtFilePath.setText(f.toString());

    }//GEN-LAST:event_jButton2ActionPerformed

    public void chooseImage() {

        JFileChooser chooser = new JFileChooser();
        FileFilter filter = new FileNameExtensionFilter("files", ImageIO.getReaderFileSuffixes());
        chooser.setFileFilter(filter);
        chooser.showOpenDialog(null);
        File file = chooser.getSelectedFile();
//        String fileName = file.getAbsolutePath();
//        String imagePath = fileName;

        try {

            BufferedImage img = null;
            img = ImageIO.read(new File(file.getAbsolutePath()));
            Image img1 = img.getScaledInstance(lbl_Image.getWidth(), lbl_Image.getHeight(), Image.SCALE_SMOOTH);
            format = new ImageIcon(img1);
            lbl_Image.setIcon(format);
        } catch (Exception ex) {

        }
        filename = file.getAbsolutePath();

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                NewProduct dialog = new NewProduct(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lbl_Image;
    private javax.swing.JPanel panelImage;
    private javax.swing.JTextField txtAlisQiymeti;
    private javax.swing.JTextField txtFilePath;
    private javax.swing.JTextField txtMehsulAdi;
    private javax.swing.JTextField txtSatisQiymeti;
    // End of variables declaration//GEN-END:variables
}
