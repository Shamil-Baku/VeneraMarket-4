/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package com.mycompany.qarisiqmallar.veneramarket;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Shamil
 */
public class AddThirdSubCategoryDialogForm extends javax.swing.JDialog {

    Connection con;
    PreparedStatement pres;

    public AddThirdSubCategoryDialogForm(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setScreenSize();
    }

    private void catNameKeyReleased(java.awt.event.KeyEvent evt) {
        int keyCode = evt.getKeyCode();
        System.out.println("Kod budur " + keyCode);

        if (keyCode == 10) {
            addNewCategory();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        catName = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("Kateqoriya Adi :");

        catName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                catNameKeyReleased(evt);
            }
        });

        jButton1.setText("Ok");

        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel1)
                                                .addGap(18, 18, 18)
                                                .addComponent(catName, javax.swing.GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE)))
                                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(catName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                                .addComponent(jButton1)
                                .addGap(16, 16, 16))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public Connection connect() {
        try {

            //Class.forName("com.mysql.jdbc.Driver");
            String url = ("jdbc:mysql://localhost:3306/mehsullar");
            String usercategoryOfProduct = ("root");
            String password = ("dxdiag92");
            con = DriverManager.getConnection(url, usercategoryOfProduct, password);

        } catch (SQLException ex) {

        }

        return con;

    }

    public void setScreenSize() {

        Toolkit toolkit = getToolkit();
        Dimension size = toolkit.getScreenSize();
        setLocation(size.width / 2 - getWidth() / 2, size.height / 2 - getHeight() / 2);
    }

    public void addNewCategory() {
        String SecondSubCatName = catName.getText();
        TreeView1.veriable = SecondSubCatName;

        try {
            Connection c = connect();
            pres = c.prepareStatement("select id from category where categories = " + "'" + fourthSubCat+ "'");
            ResultSet rsMainCat = pres.executeQuery();
            rsMainCat.next();
            int mainCatID = rsMainCat.getInt("id");
            System.out.println(mainCatID);

            pres = c.prepareStatement("select id from subcategory where `name` = " + "'" +thirdSubCat+ "'"+" and `index` = "+mainCatID);
            ResultSet rs = pres.executeQuery();
            rs.next();
            int idSubCat = rs.getInt("id");
            System.out.println(idSubCat);
 
            pres = c.prepareStatement("select id from 2ndsubcategory where name = " + "'" + secondSubName + "'"+ " and `index-gender` = "+idSubCat+" and `index-season` ="+mainCatID);
            ResultSet rsFourthCat = pres.executeQuery();
            rsFourthCat.next();
            int rsFourthCatID = rsFourthCat.getInt("id");
            System.out.println(rsFourthCatID);
            
            
            Connection c2 = connect();
            pres = c2.prepareStatement("insert into 3ndsubcategory (`name`, `index-gender`, `index-season`, `index-3rdSubCat`) values (?,?,?,?)");
            pres.setString(1, SecondSubCatName);
            pres.setInt(2, idSubCat);
            pres.setInt(3, mainCatID);
            pres.setInt(4, rsFourthCatID);
            pres.executeUpdate();
            System.out.println("Oldu");

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        //veriable.Load();
        JOptionPane.showMessageDialog(this, "Katrqoriya ugurla elave olundu");
        this.setVisible(false);

    }
    String subName = null;
    String secondSubName = null;
    String thirdSubCat = null;
    String fourthSubCat = null;

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        addNewCategory();
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
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AddCategoryDialogForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddCategoryDialogForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddCategoryDialogForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddCategoryDialogForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                AddThirdSubCategoryDialogForm dialog = new AddThirdSubCategoryDialogForm(new javax.swing.JFrame(), true);
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
    private javax.swing.JTextField catName;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
