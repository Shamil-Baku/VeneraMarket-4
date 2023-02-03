/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.qarisiqmallar.veneramarket;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import javax.swing.DefaultListModel;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Shamil
 */
public class PaymentForPurchaseInvoice extends javax.swing.JFrame {

    Connection con;
    PreparedStatement pres;
    ResultSet rs;
    DefaultTableModel df;
    Timer timer;
    String currentDate;
    SimpleDateFormat sdf = new SimpleDateFormat();
    SimpleDateFormat sdf2 = new SimpleDateFormat();

    String numberOfBill;
    String numberOfBillForCredit;
    String nameOfClient;
    String nameOfClientForCredir;
    double totalAmountOfCredit;

    public PaymentForPurchaseInvoice() {
        initComponents();
        setTime();
        loadTotalCreditsOfClients();
        loadAllCredits();
        panelMostDetailed.setVisible(false);
        panelName.setVisible(false);
        PanelInfo.setVisible(false);
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

    public void setTime() {

        timer = new Timer(0, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
                Date dt = new Date();
                sdf = new SimpleDateFormat("HH:mm:ss");
                sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                String time = sdf.format(dt);
                lblTime.setText(time);

                String time2 = sdf2.format(dt);
                currentDate = time2;

            }
        });
        timer.start();

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        Payment = new javax.swing.JMenuItem();
        Detallar = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        panelName = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        ListOfNames = new javax.swing.JList<>();
        txtName = new javax.swing.JTextField();
        txtNumberOfBill = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        PanelInfo = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        txtTotalOfBuy = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtPayed = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtTotalCredit = new javax.swing.JTextField();
        lblTime = new javax.swing.JLabel();
        panelMostDetailed = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableCreditAndPayment = new javax.swing.JTable();
        panelTotalİnfo = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblTotalCredits = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        txtTotalCredits = new javax.swing.JTextField();

        Payment.setText("jMenuItem1");
        Payment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PaymentActionPerformed(evt);
            }
        });
        jPopupMenu1.add(Payment);

        Detallar.setText("jMenuItem1");
        Detallar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DetallarActionPerformed(evt);
            }
        });
        jPopupMenu1.add(Detallar);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jPanel2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        ListOfNames.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ListOfNamesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(ListOfNames);

        javax.swing.GroupLayout panelNameLayout = new javax.swing.GroupLayout(panelName);
        panelName.setLayout(panelNameLayout);
        panelNameLayout.setHorizontalGroup(
            panelNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelNameLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panelNameLayout.setVerticalGroup(
            panelNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelNameLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 5, Short.MAX_VALUE))
        );

        txtName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNameActionPerformed(evt);
            }
        });
        txtName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNameKeyReleased(evt);
            }
        });

        txtNumberOfBill.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNumberOfBillActionPerformed(evt);
            }
        });
        txtNumberOfBill.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNumberOfBillKeyReleased(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Cari qaimə :");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Kontragent adı :");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(panelName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtNumberOfBill, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(16, 16, 16))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtName)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addComponent(panelName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNumberOfBill, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Итого закупочная сумма");

        txtTotalOfBuy.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        txtTotalOfBuy.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Итого оплата :");

        txtPayed.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        txtPayed.setForeground(new java.awt.Color(51, 255, 51));
        txtPayed.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Итого долг :");

        txtTotalCredit.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        txtTotalCredit.setForeground(new java.awt.Color(255, 0, 0));
        txtTotalCredit.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        javax.swing.GroupLayout PanelInfoLayout = new javax.swing.GroupLayout(PanelInfo);
        PanelInfo.setLayout(PanelInfoLayout);
        PanelInfoLayout.setHorizontalGroup(
            PanelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelInfoLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(PanelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelInfoLayout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jLabel4))
                    .addComponent(txtPayed, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTotalOfBuy, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(PanelInfoLayout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(jLabel3))
                    .addComponent(txtTotalCredit, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34))
            .addGroup(PanelInfoLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel5)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        PanelInfoLayout.setVerticalGroup(
            PanelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelInfoLayout.createSequentialGroup()
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(txtTotalOfBuy, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(PanelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(PanelInfoLayout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addComponent(txtPayed, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtTotalCredit, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 21, Short.MAX_VALUE))
        );

        lblTime.setFont(new java.awt.Font("Segoe UI", 2, 48)); // NOI18N
        lblTime.setForeground(new java.awt.Color(0, 0, 255));
        lblTime.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        tableCreditAndPayment = new JTable()

        {
            public boolean isCellEditable(int row, int column)

            {
                for(int i =0; i< tableCreditAndPayment.getRowCount(); i++)
                {
                    if(row == i)
                    {
                        return false;
                    }
                }
                return false;
            }
        };
        tableCreditAndPayment.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Qaimə nömrəsi", "Ümumi qaimə məbləği", "Ödəniş", "Qalıq borc", "Tarix"
            }
        ));
        tableCreditAndPayment.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableCreditAndPaymentMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tableCreditAndPaymentMouseReleased(evt);
            }
        });
        jScrollPane2.setViewportView(tableCreditAndPayment);

        javax.swing.GroupLayout panelMostDetailedLayout = new javax.swing.GroupLayout(panelMostDetailed);
        panelMostDetailed.setLayout(panelMostDetailedLayout);
        panelMostDetailedLayout.setHorizontalGroup(
            panelMostDetailedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMostDetailedLayout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1091, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelMostDetailedLayout.setVerticalGroup(
            panelMostDetailedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMostDetailedLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(12, Short.MAX_VALUE))
        );

        tblTotalCredits.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Kontragent adı", "Ümumi qaimə məbləği", "Ödəniş", "Qalıq borc"
            }
        ));
        tblTotalCredits.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblTotalCreditsMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblTotalCredits);

        javax.swing.GroupLayout panelTotalİnfoLayout = new javax.swing.GroupLayout(panelTotalİnfo);
        panelTotalİnfo.setLayout(panelTotalİnfoLayout);
        panelTotalİnfoLayout.setHorizontalGroup(
            panelTotalİnfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTotalİnfoLayout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 1101, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelTotalİnfoLayout.setVerticalGroup(
            panelTotalİnfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTotalİnfoLayout.createSequentialGroup()
                .addContainerGap(9, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jButton1.setText("Назад");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setText("Итого долг :");

        txtTotalCredits.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 469, Short.MAX_VALUE)
                        .addComponent(lblTime, javax.swing.GroupLayout.PREFERRED_SIZE, 367, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton1)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel6)
                                .addGap(18, 18, 18)
                                .addComponent(txtTotalCredits, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(panelMostDetailed, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(panelTotalİnfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(PanelInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(panelMostDetailed, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelTotalİnfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addComponent(lblTime, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(57, 57, 57)
                        .addComponent(PanelInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTotalCredits, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addContainerGap(58, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtNumberOfBillActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumberOfBillActionPerformed

        String selectedClientName = txtName.getText();
        String numberBill = txtNumberOfBill.getText();
        load2(selectedClientName, numberBill);


    }//GEN-LAST:event_txtNumberOfBillActionPerformed

    public void loadTotalCreditsOfClients() {

        try {
            con = connect();

            pres = con.prepareStatement("truncate table totaldebetsofcontractor");
            pres.executeUpdate();

            pres = con.prepareStatement("select * from contracors");
            ResultSet rs = pres.executeQuery();
            while (rs.next()) {
                String nameOfClient = rs.getString("NameAndSurename");

                pres = con.prepareStatement("insert into totaldebetsofcontractor (clientName) values(?)");
                pres.setString(1, nameOfClient);
                pres.executeUpdate();

                pres = con.prepareStatement("select * from purchasebills where nameOfClient = " + "'" + nameOfClient + "'" + " and credit > 0 and status != \"Непроверенный документ\"");
                ResultSet rs2 = pres.executeQuery();
                double totalCredits = 0;
                while (rs2.next()) {
                    double totalCredit = rs2.getDouble("TotalSumOfBill");

                    totalCredits += totalCredit;

                    pres = con.prepareStatement("update totaldebetsofcontractor set totallyCredit=? where clientName = ?");
                    pres.setDouble(1, totalCredits);
                    pres.setString(2, nameOfClient);
                    pres.executeUpdate();

                }

                pres = con.prepareStatement("select * from purchasebills where nameOfClient = " + "'" + nameOfClient + "'" + " and credit > 0 ");
                ResultSet rs3 = pres.executeQuery();
                double total = 0;
                while (rs3.next()) {
                    double totalPayment = rs3.getDouble("paid");

                    total += totalPayment;

                    pres = con.prepareStatement("update totaldebetsofcontractor set totallyPayment=? where clientName = ?");
                    pres.setDouble(1, total);
                    pres.setString(2, nameOfClient);
                    pres.executeUpdate();

                }

                pres = con.prepareStatement("select * from purchasebills where nameOfClient = " + "'" + nameOfClient + "'" + " and status != \"Непроверенный документ\"");
                ResultSet rs4 = pres.executeQuery();
                double totalCredit = 0;
                while (rs4.next()) {
                    double totalCre = rs4.getDouble("credit");

                    totalCredit += totalCre;

                    pres = con.prepareStatement("update totaldebetsofcontractor set totallyDebt=? where clientName = ?");
                    pres.setDouble(1, totalCredit);
                    pres.setString(2, nameOfClient);
                    pres.executeUpdate();

                }

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void loadAllCredits() {
        int a;
        try {
            con = connect();
            pres = con.prepareStatement("select * from totaldebetsofcontractor");
            ResultSet rs5 = pres.executeQuery();

            ResultSetMetaData rd = rs5.getMetaData();
            a = rd.getColumnCount();
            df = (DefaultTableModel) tblTotalCredits.getModel();
            df.setRowCount(0);
            double totalcredit;
            double result = 0;
            double roundedResult = 0;
            while (rs5.next()) {

                Vector v2 = new Vector();
                //for (int i = 0; i < a; i++) {

                String nameOfClient2 = rs5.getString("clientName");

                double totallyPayment = rs5.getDouble("totallyPayment");
                double totallyDebt = rs5.getDouble("totallyDebt");

                totalcredit = rs5.getDouble("totallyCredit");
                double resultMain = totalcredit - totallyPayment;
                result += resultMain;
                roundedResult = Math.round(result * 100.000) / 100.000;

                v2.add(nameOfClient2);
                v2.add(totalcredit);
                v2.add(totallyPayment);
                v2.add(totallyDebt);

                //}
                df.addRow(v2);
                txtTotalCredits.setText(Double.toString(roundedResult));
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void findContractorCredit() {

        panelName.setVisible(true);
        modelList.removeAllElements();
        String s = txtName.getText();

        try {
            Connection c = connect();
            pres = c.prepareCall("select * from contracors c where c.NameAndSurename like " + "'" + "%" + s + "%" + "'");

            ResultSet rs = pres.executeQuery();

            while (rs.next()) {

                modelList.addElement(rs.getString("NameAndSurename"));
                ListOfNames.setModel(modelList);
            }

        } catch (Exception ex) {

            ex.printStackTrace();

        }

    }

    public void loadAllCreditsOfClientSecond() {

        panelTotalİnfo.setVisible(false);
        panelMostDetailed.setVisible(true);
        PanelInfo.setVisible(true);

        String selectedClientName = txtName.getText();
        load(selectedClientName);
        sebetinHesablanmasi();

    }
    
    public void load(String selectedClientName) {

        int a;
        try {

            Connection c = connect();

            pres = c.prepareCall("select * from purchasebills where nameOfClient = " + "'" + selectedClientName + "'" + " and status != \"Непроверенный документ\" and credit > 0");

            ResultSet rs = pres.executeQuery();

            ResultSetMetaData rd = rs.getMetaData();
            a = rd.getColumnCount();
            df = (DefaultTableModel) tableCreditAndPayment.getModel();
            df.setRowCount(0);

            while (rs.next()) {
                Vector v2 = new Vector();
                for (int i = 0; i < a; i++) {

                    v2.add(rs.getString("NumberOfBill"));
                    v2.add(rs.getDouble("TotalSumOfBill"));
                    //v2.add(rs.getDouble("totalSummOfPriceOfbuy"));
                    v2.add(rs.getDouble("paid"));
                    v2.add(rs.getDouble("credit"));
                    v2.add(rs.getDate("DateCreation"));
                }
                df.addRow(v2);

            }

            sebetinHesablanmasi();
            panelName.setVisible(false);

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    

    private void txtNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNameActionPerformed

    private void tableCreditAndPaymentMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableCreditAndPaymentMouseClicked

        int clickCount = evt.getClickCount();

        if (clickCount == 2) {

            nameOfClient = txtName.getText();

            PayForPurchaseCredit pc = new PayForPurchaseCredit(this, true);

            df = (DefaultTableModel) tableCreditAndPayment.getModel();

            int selected = tableCreditAndPayment.getSelectedRow();

            numberOfBill = (df.getValueAt(selected, 0).toString());
            totalAmountOfCredit = Double.parseDouble(df.getValueAt(selected, 3).toString());
            pc.name = nameOfClient;
            pc.numberBill = numberOfBill;
            pc.txtCreditCurrentTime.setText(Double.toString(totalAmountOfCredit));
            pc.txtName.setText(nameOfClient);
            pc.txtNumberOfBill.setText((numberOfBill));

            pc.setVisible(true);

            double pay = Double.parseDouble(pc.txtAmountOfPay.getText());
            double change = Double.parseDouble(pc.txtChange.getText());
            System.out.println("Qaqaw oldu bu is" + pay);
            try {
                if (change <= 0.0) {
                    con = connect();
                    pres = con.prepareStatement("update purchasebills set status = ? where NumberOfBill = ?");
                    pres.setString(1, "Оплаченный!");
                    pres.setString(2, numberOfBill);
                    pres.executeUpdate();
                }

                con = connect();
                pres = con.prepareStatement("update purchasebills set paid = paid +? where NumberOfBill = ?");
                pres.setDouble(1, pay);
                pres.setString(2, numberOfBill);
                pres.executeUpdate();

                pres = con.prepareStatement("update purchasebills set credit = TotalSumOfBill - paid, TimeOfPay = ? where NumberOfBill = ?");

                pres.setString(1, currentDate);
                pres.setString(2, numberOfBill);
                pres.executeUpdate();

                pres = con.prepareStatement("select * from purchasebills where NumberOfBill =" + "'" + numberOfBill + "'");
                rs = pres.executeQuery();
                rs.next();
                String name = rs.getString("nameOfClient");
                String numBill = rs.getString("NumberOfBill");
                String numContract = rs.getString("numberOfContract");
                double totamSumOfBill = rs.getDouble("TotalSumOfBill");
                Date dataCreationOfCredit = rs.getDate("DateCreation");
                String whoChanged = rs.getString("whoChanged");
                String typeOfDocument = rs.getString("TypeOfDocument");
                Date dateOfChange = rs.getDate("DateOfChange");
                String whoMade = rs.getString("WhoMade");
                String inWarehouse = rs.getString("InWarehouse");
                double paid = rs.getDouble("paid");
                Date timeOfPay = rs.getDate("TimeOfPay");
                String whoReceivePay = rs.getString("whoReceivePay");
                double credit = rs.getDouble("credit");
                String paymentType = rs.getString("paymentType");
                String toWhomCreditCardPayed = rs.getString("toWhomCreditCardPayed");

                pres = con.prepareStatement("insert into paidpurchasebills (nameOfClient,NumberOfBill,numberOfContract,TotalSumOfBill,DateCreation,whoChanged,TypeOfDocument,DateOfChange,WhoMade,InWarehouse,paid,TimeOfPay,whoReceivePay,credit,paymentType,toWhomCreditCardPayed) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                pres.setString(1, name);
                pres.setString(2, numBill);
                pres.setString(3, numContract);
                pres.setDouble(4, totamSumOfBill);
                pres.setDate(5, (java.sql.Date) dataCreationOfCredit);
                pres.setString(6, whoChanged);
                pres.setString(7, typeOfDocument);
                pres.setString(8, currentDate);
                pres.setString(9, whoMade);
                pres.setString(10, inWarehouse);
                pres.setDouble(11, pay);
                pres.setDate(12, (java.sql.Date) timeOfPay);
                pres.setString(13, whoReceivePay);
                pres.setDouble(14, credit);
                pres.setString(15, paymentType);
                pres.setString(16, toWhomCreditCardPayed);
                pres.executeUpdate();

                String ad = pc.getQaimeAdi();
                String number = pc.getQaimeNum();
                String creditCurrent = pc.getCreditCurrentTime();
                String amountPayment = pc.getAmountOfPay();
                String typeOfPayment = pc.getTypePayment();

                System.out.println(ad + number + creditCurrent);

                //writeFileFromJtableToFile(ad, number, creditCurrent, amountPayment,typeOfPayment);
                //PayForCredit pc = new PayForCredit(this, false);
                String qaimeAdi = txtName.getText();
                //String qaimeNum = numberOfBill;
                String extensionOfFile = ".txt";
                String tamAd = qaimeAdi + "-" + numberOfBill + extensionOfFile;
                String filePath = "C:\\Alis Qaimeleri\\";

                String pathName = filePath + tamAd;

                File file = new File(pathName);

                try {
                    FileWriter fw = new FileWriter(file, true);
                    BufferedWriter bw = new BufferedWriter(fw);
                    bw.write(pc.getQaimeAdi() + "--" + pc.getQaimeNum().toString() + "--" + pc.getCreditCurrentTime() + "--" + pc.getAmountOfPay() + "--" + pc.getChange() + "--" + pc.getTypePayment() + "--" + currentDate);
                    bw.newLine();
                    bw.close();
                    fw.close();

                } catch (IOException ex) {

                    ex.printStackTrace();

                }

                try {

                    pres = con.prepareStatement("select * from updatedCapitalbudget order by id desc limit 1");
                    rs = pres.executeQuery();

                    rs.next();

                    double capitalBudget = rs.getDouble("AmountOfCapitalBudget");

                    double result = capitalBudget - pay;
                    double roundedResult = Math.round(result * 100.000) / 100.000;
                    double roundedPay = Math.round(pay * 100.000) / 100.000;

                    pres = con.prepareStatement("insert into updatedCapitalbudget (AmountOfCapitalBudget, date, `from`, `status`, processedValue) values(?,?,?,?,?)");
                    pres.setDouble(1, roundedResult);
                    pres.setString(2, currentDate);
                    pres.setString(3, "Alış qaiməsi üzrə " + name +"- tərəfindən ödəniş ");
                    pres.setString(4, "-");
                    pres.setDouble(5, roundedPay);
                    pres.executeUpdate();
                    
                } catch (Exception ex) {

                    System.out.println("Bura bosdur diger bazaya kecirem");
                    pres = con.prepareStatement("select * from capitalbudget");
                    rs = pres.executeQuery();

                    rs.next();

                    double capitalBudget = rs.getDouble("AmountOfCapitalBudget");

                    double result = capitalBudget - pay;
                    double roundedResult = Math.round(result * 100.000) / 100.000;

                    pres = con.prepareStatement("insert into updatedCapitalbudget (AmountOfCapitalBudget, date) values(?,?)");
                    pres.setDouble(1, roundedResult);
                    pres.setString(2, currentDate);
                    pres.executeUpdate();
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
        if (clickCount == 1) {
            String name = txtName.getText();
            df = (DefaultTableModel) tableCreditAndPayment.getModel();

            int selected2 = tableCreditAndPayment.getSelectedRow();

            numberOfBill = (df.getValueAt(selected2, 0).toString());
            nameOfClient = txtName.getText();
            try {

                pres = con.prepareStatement("truncate table billsway_copy1");
                pres.executeUpdate();

                pres = con.prepareStatement("insert into billsway_copy1 (QaimeNum, Kimden) values (?,?)");
                pres.setString(1, numberOfBill);
                pres.setString(2, name);
                pres.executeUpdate();

            } catch (Exception ex) {

                ex.printStackTrace();
            }

        }


    }//GEN-LAST:event_tableCreditAndPaymentMouseClicked

    public String getNameOfClient() {
        return nameOfClient;
    }

    public String getNumberOfBill() {
        return numberOfBill;
    }

    DefaultListModel modelList = new DefaultListModel();
    private void txtNameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNameKeyReleased

        panelName.setVisible(true);
        modelList.removeAllElements();
        String s = txtName.getText();

        try {
            Connection c = connect();
            pres = c.prepareCall("select * from contracors c where c.NameAndSurename like " + "'" + "%" + s + "%" + "'");

            ResultSet rs = pres.executeQuery();

            while (rs.next()) {

                modelList.addElement(rs.getString("NameAndSurename"));
                ListOfNames.setModel(modelList);
            }

        } catch (Exception ex) {

            ex.printStackTrace();

        }


    }//GEN-LAST:event_txtNameKeyReleased

    private void ListOfNamesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ListOfNamesMouseClicked

        String selectedClientName = ListOfNames.getSelectedValue();
        txtName.setText(selectedClientName);
        modelList.removeAllElements();
        panelName.setVisible(false);
        PanelInfo.setVisible(true);
        load(selectedClientName);


    }//GEN-LAST:event_ListOfNamesMouseClicked

    private void txtNumberOfBillKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumberOfBillKeyReleased

        String selectedClientName = txtName.getText();
        String numberBill = txtNumberOfBill.getText();
        load2(selectedClientName, numberBill);
    }//GEN-LAST:event_txtNumberOfBillKeyReleased


    private void PaymentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PaymentActionPerformed

        nameOfClient = txtName.getText();

        PayForPurchaseCredit pc = new PayForPurchaseCredit(this, true);

        df = (DefaultTableModel) tableCreditAndPayment.getModel();

        int selected = tableCreditAndPayment.getSelectedRow();

        numberOfBill = (df.getValueAt(selected, 0).toString());
        totalAmountOfCredit = Double.parseDouble(df.getValueAt(selected, 4).toString());
        pc.name = nameOfClient;
        pc.numberBill = numberOfBill;
        pc.txtCreditCurrentTime.setText(Double.toString(totalAmountOfCredit));
        pc.txtName.setText(nameOfClient);
        pc.txtNumberOfBill.setText((numberOfBill));

        pc.setVisible(true);

        double pay = Double.parseDouble(pc.txtAmountOfPay.getText());
        System.out.println("Qaqaw oldu bu is" + pay);

        try {
            con = connect();
            pres = con.prepareStatement("update bills set paid = paid +? where NumberOfBill = ?");
            pres.setDouble(1, pay);
            pres.setString(2, numberOfBill);
            pres.executeUpdate();

            pres = con.prepareStatement("update bills set credit = TotalSumOfBill - paid where NumberOfBill = ?");
            pres.setString(1, numberOfBill);
            pres.executeUpdate();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }//GEN-LAST:event_PaymentActionPerformed

    public void writeFileFromJtableToFile(String getQaimeAdi, String QaimeNum, String CreditCurrentTime, String AmountOfPay, String TypePayment) {

        PayForPurchaseCredit pc = new PayForPurchaseCredit(this, false);

        String qaimeAdi = txtName.getText();
        String qaimeNum = (txtNumberOfBill.getText());
        String extensionOfFile = ".txt";
        String tamAd = qaimeAdi + "-" + qaimeNum + extensionOfFile;
        String filePath = "C:\\bills\\";

        String pathName = filePath + tamAd;

        File file = new File(pathName);

        try {
            FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write(pc.getQaimeAdi() + ":" + pc.getQaimeNum().toString() + ":" + pc.getCreditCurrentTime() + ":" + pc.getAmountOfPay() + ":" + pc.getTypePayment());

            //bw.newLine();
            bw.close();
            fw.close();

        } catch (IOException ex) {

            ex.printStackTrace();

        }

    }


    private void tableCreditAndPaymentMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableCreditAndPaymentMouseReleased

        if (SwingUtilities.isRightMouseButton(evt)) {

            jPopupMenu1.show(tableCreditAndPayment, evt.getX(), evt.getY());

        }


    }//GEN-LAST:event_tableCreditAndPaymentMouseReleased

    private void DetallarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DetallarActionPerformed

        DetailedInfoAboutPurchasePayment pc = new DetailedInfoAboutPurchasePayment(this, true);

        try {

            con = connect();
            df = (DefaultTableModel) tableCreditAndPayment.getModel();

            int selected = tableCreditAndPayment.getSelectedRow();

            String qaimeYolu = "C:\\Purchase Bills\\";
            String qaimeAdi = txtName.getText();
            String billnum = (df.getValueAt(selected, 0).toString());
            String tamAd = qaimeYolu + qaimeAdi + "-" + billnum + ".txt";

            pc.txtName.setText(qaimeAdi);
            pc.txtNumberOfBill.setText(billnum);

            pres = con.prepareStatement("truncate table BillsWay;");
            pres.executeUpdate();

            pres = con.prepareStatement("insert into BillsWay (QaimeAdi, QaimeYolu, QaimeNum, KImden) values(?,?,?,?)");
            pres.setString(1, tamAd);
            pres.setString(2, qaimeYolu);
            pres.setString(3, billnum);
            pres.setString(4, qaimeAdi);

            pres.executeUpdate();

            pc.setVisible(true);

        } catch (Exception ex) {

            ex.printStackTrace();
        }

    }//GEN-LAST:event_DetallarActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        panelMostDetailed.setVisible(false);
        panelTotalİnfo.setVisible(true);
        loadTotalCreditsOfClients();
        loadAllCredits();
        panelName.setVisible(false);
        PanelInfo.setVisible(false);
        txtName.setText("");
    }//GEN-LAST:event_jButton1ActionPerformed

    private void tblTotalCreditsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblTotalCreditsMouseClicked

        df = (DefaultTableModel) tblTotalCredits.getModel();

        int selected = tblTotalCredits.getSelectedRow();

        String name = (df.getValueAt(selected, 0).toString());

        txtName.setText(name);

        findContractorCredit();
        //loadAllCreditsOfClient();
        loadAllCreditsOfClientSecond();
    }//GEN-LAST:event_tblTotalCreditsMouseClicked

    public void load2(String selectedClientName, String numberBill) {

        int a;
        try {

            Connection c = connect();

            pres = c.prepareCall("select * from purchasebills where nameOfClient = " + "'" + selectedClientName + "'" + " and NumberOfBill like " + "'" + "%" + numberBill + "%" + "'");

            ResultSet rs = pres.executeQuery();

            ResultSetMetaData rd = rs.getMetaData();
            a = rd.getColumnCount();
            df = (DefaultTableModel) tableCreditAndPayment.getModel();
            df.setRowCount(0);

            while (rs.next()) {
                Vector v2 = new Vector();
                for (int i = 0; i < a; i++) {

                    v2.add(rs.getString("NumberOfBill"));
                    v2.add(rs.getDouble("TotalSumOfBill"));
                    v2.add(rs.getDouble("paid"));
                    v2.add(rs.getDouble("credit"));
                    v2.add(rs.getDate("DateCreation"));
                }
                df.addRow(v2);

            }
            sebetinHesablanmasi();

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void sebetinHesablanmasi() {

        DecimalFormat dformater = new DecimalFormat("#.##");
        df = (DefaultTableModel) tableCreditAndPayment.getModel();

        double toplamAlis, cemAlisMeblegi = 0, toplamOdenis, CemOdenis = 0, ToplamBorc, CemBorc = 0;

        for (int i = 0; i < df.getRowCount(); i++) {

            toplamAlis = Double.parseDouble(df.getValueAt(i, 1).toString());
            toplamOdenis = Double.parseDouble(df.getValueAt(i, 2).toString());
            ToplamBorc = Double.parseDouble(df.getValueAt(i, 3).toString());
            cemAlisMeblegi += toplamAlis;
            //String formattedAlis = Double.toString((cemAlisMeblegi));
            double roundedAlis = Math.round(cemAlisMeblegi * 100.000) / 100.000;
            txtTotalOfBuy.setText(Double.toString(roundedAlis));
            CemOdenis += toplamOdenis;
            //String formatteOdenis = Double.toString((CemOdenis));
            double roundedCemOdenis = Math.round(CemOdenis * 100.000) / 100.000;
            txtPayed.setText(Double.toString(roundedCemOdenis));
            CemBorc += ToplamBorc;
            //String formattedBorc = Double.toString((CemBorc));
            double roundedCemBorc = Math.round(CemBorc * 100.000) / 100.000;
            txtTotalCredit.setText(Double.toString(roundedCemBorc));
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
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PaymentForPurchaseInvoice.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PaymentForPurchaseInvoice.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PaymentForPurchaseInvoice.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PaymentForPurchaseInvoice.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PaymentForPurchaseInvoice().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem Detallar;
    private javax.swing.JList<String> ListOfNames;
    private javax.swing.JPanel PanelInfo;
    private javax.swing.JMenuItem Payment;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblTime;
    private javax.swing.JPanel panelMostDetailed;
    private javax.swing.JPanel panelName;
    private javax.swing.JPanel panelTotalİnfo;
    private javax.swing.JTable tableCreditAndPayment;
    private javax.swing.JTable tblTotalCredits;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtNumberOfBill;
    private javax.swing.JTextField txtPayed;
    private javax.swing.JTextField txtTotalCredit;
    private javax.swing.JTextField txtTotalCredits;
    private javax.swing.JTextField txtTotalOfBuy;
    // End of variables declaration//GEN-END:variables
}
