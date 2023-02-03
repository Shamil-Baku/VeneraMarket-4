/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.qarisiqmallar.veneramarket;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultTreeModel;

/**
 *
 * @author Shamil
 */
public class Expenses extends javax.swing.JFrame {

    DefaultTreeModel model;
    DefaultTableModel df;
    Connection con;
    PreparedStatement pres;
    ResultSet rs;
    Timer timer;
    String currentDate;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat sdf2 = new SimpleDateFormat();

    public Expenses() {
        initComponents();
        setTime();
        getAllExpenseCategories();
        getAllNames();
    }

    public Connection connect() {
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

    public void setTime() {

        timer = new Timer(0, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
                Date dt = new Date();
                sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                String time = sdf.format(dt);
                txtTime.setText(time);

                String time2 = sdf2.format(dt);
                currentDate = time2;

            }
        });
        timer.start();

    }

    private void getAllExpenseCategories() {

        try {

            con = connect();
            pres = con.prepareStatement("select * from expensecategories");
            ResultSet rs2 = pres.executeQuery();

            while (rs2.next()) {

                String nameOfExpense = rs2.getString("categoryName");
                cbCategoryOfExpense.addItem(nameOfExpense);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private void getAllNames() {

        try {

            con = connect();
            pres = con.prepareStatement("select * from wherewentexpenses");
            ResultSet rs2 = pres.executeQuery();

            while (rs2.next()) {

                String nameOfExpense = rs2.getString("Name");
                cbName.addItem(nameOfExpense);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        cbCategoryOfExpense = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        txtTime = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        commentaryForExpense = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtTotalExpense = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        cbName = new javax.swing.JComboBox<>();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Ad ve Soyad :");
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Xercin tipi :");

        cbCategoryOfExpense.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Tarix : ");

        txtTime.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N

        jButton1.setText("OK");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Kommentariya : ");

        commentaryForExpense.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Mebleg : ");

        txtTotalExpense.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N

        jButton2.setText("+");
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        cbName.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N

        jButton3.setText("+");
        jButton3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING))
                                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(11, 11, 11)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtTotalExpense)
                            .addComponent(cbCategoryOfExpense, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(commentaryForExpense)
                            .addComponent(txtTime)
                            .addComponent(cbName, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbCategoryOfExpense, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(commentaryForExpense, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTotalExpense, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        addExpense();

    }//GEN-LAST:event_jButton1ActionPerformed

    public void addExpense() {

        String name = cbName.getSelectedItem().toString();
        String category = cbCategoryOfExpense.getSelectedItem().toString();

        if (name.equals("Seçim edin..")) {
            JOptionPane.showMessageDialog(this, "Zehmet olmasa - Ad ve Soyad xanasini doldurun!", "DİQQET!!", HEIGHT);
        }
        if (category.equals("Seçim edin..")) {
            JOptionPane.showMessageDialog(this, "Zehmet olmasa - Xercin tipi xanasini doldurun!", "DİQQET!!", HEIGHT);
        } else {
            if (!"Seçim edin..".equals(category) && !"Seçim edin..".equals(name)) {

                String catrgory = cbCategoryOfExpense.getSelectedItem().toString();
                String commentary = commentaryForExpense.getText();
                String time = txtTime.getText();
                double totalExpense = Double.parseDouble(txtTotalExpense.getText());

                try {

                    con = connect();
                    pres = con.prepareStatement("insert into expenses (name, categoryOfExpense, commentary, date, totalExpense) values(?,?,?,?,?)");
                    pres.setString(1, name);
                    pres.setString(2, catrgory);
                    pres.setString(3, commentary);
                    pres.setString(4, time);
                    pres.setDouble(5, totalExpense);
                    pres.executeUpdate();

                    try {

                        pres = con.prepareStatement("select * from updatedCapitalbudget order by id desc limit 1");
                        rs = pres.executeQuery();

                        rs.next();

                        double capitalBudget = rs.getDouble("AmountOfCapitalBudget");

                        double result = capitalBudget - totalExpense;
                        double roundedResult = Math.round(result * 100.000) / 100.000;
                        double roundedExpense = Math.round(totalExpense * 100.000) / 100.000;

                        pres = con.prepareStatement("insert into updatedCapitalbudget (AmountOfCapitalBudget, date, `from`, `status`, processedValue) values(?,?,?,?,?)");
                        pres.setDouble(1, roundedResult);
                        pres.setString(2, currentDate);
                        pres.setString(3, "Xerc mebleği " + name+ "- terefinden qeyd olundu");
                        pres.setString(4, "-");
                        pres.setDouble(5, roundedExpense);
                        pres.executeUpdate();

                    } catch (Exception ex) {

                        System.out.println("Bura bosdur diger bazaya kecirem");
                        pres = con.prepareStatement("select * from capitalbudget");
                        rs = pres.executeQuery();

                        rs.next();

                        double capitalBudget = rs.getDouble("AmountOfCapitalBudget");

                        double result = capitalBudget - totalExpense;
                        double roundedResult = Math.round(result * 100.000) / 100.000;

                        pres = con.prepareStatement("insert into updatedCapitalbudget (AmountOfCapitalBudget, date) values(?,?)");
                        pres.setDouble(1, roundedResult);
                        pres.setString(2, currentDate);
                        pres.executeUpdate();

                    }

                    JOptionPane.showMessageDialog(this, "Daxil edinlen mebleğ uğurla xercler siyahısına qeyd edildi!");

                    this.setVisible(false);

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }

    }


    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        addNewExpenseCategoryDialog newCategory = new addNewExpenseCategoryDialog(this, true);

        newCategory.setVisible(true);

        cbCategoryOfExpense.removeAllItems();
        getAllExpenseCategories();


    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

        AddNewNameDialog newCategory = new AddNewNameDialog(this, true);

        newCategory.setVisible(true);

        cbName.removeAllItems();
        getAllNames();


    }//GEN-LAST:event_jButton3ActionPerformed

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
            java.util.logging.Logger.getLogger(Expenses.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Expenses.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Expenses.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Expenses.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Expenses().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cbCategoryOfExpense;
    private javax.swing.JComboBox<String> cbName;
    private javax.swing.JTextField commentaryForExpense;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField txtTime;
    private javax.swing.JTextField txtTotalExpense;
    // End of variables declaration//GEN-END:variables
}
