/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.qarisiqmallar.veneramarket;

import com.itextpdf.text.DocumentException;
import com.mycompany.DaoInter.AltKateqoriyalarDaoInter;
import com.mycompany.DaoInter.MehsullarDaoInter;

import com.mycompany.entity.AltKateqoriyalar;
import com.mycompany.entity.Mehsullar;
import com.mycompany.main.Contex;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
//import javax.annotation.Generated;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author samil
 */
public class Mehsul_Elave_etmek extends javax.swing.JFrame {

    public MehsullarDaoInter mehDao = Contex.instanceOfMehsullarDao();
    public AltKateqoriyalarDaoInter mehDao2 = Contex.instanceOfAltKateqoriyalarDao();

    java.util.Date date = new java.util.Date();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");
    String date22 = sdf.format(date);

    public Mehsul_Elave_etmek() throws Exception {
        initComponents();
        connect();
        load();
        txtAlisTarixi.setText(date22);
        txtAxtaris.requestFocus();
    }

    Connection con;
    PreparedStatement pres;
    DefaultTableModel df;

    public void connect() throws Exception {

        Class.forName("com.mysql.cj.jdbc.Driver");

        con = DriverManager.getConnection("jdbc:mysql://localhost/mehsullar", "root", "dxdiag92");

    }

    public void axtaris() {

        int typeOfSerach = jComboBox2.getSelectedIndex();
        
        String s = txtAxtaris.getText();
       

        int a;
        try {
            
            if (typeOfSerach==0) {
             pres = con.prepareCall("select * from mehsullar m where m.Malin_adi like " + "'" + "%" + s + "%" + "'");   
            }else{
             pres = con.prepareCall("select * from mehsullar m where m.Barcode like " + "'" + "%" + s + "%" + "'");   
                
            }
            
            

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
            Logger.getLogger(Satici_Elave_Etmek.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void load() {

        int a;
        try {
            pres = con.prepareCall("select * from mehsullar");

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
            Logger.getLogger(Satici_Elave_Etmek.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        Detallar = new javax.swing.JMenuItem();
        Sil = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtKateqoriyaID = new javax.swing.JTextField();
        txtMovsumId = new javax.swing.JTextField();
        txtAlisQiymeti = new javax.swing.JTextField();
        txtMiqdari = new javax.swing.JTextField();
        txtMalinAdi = new javax.swing.JTextField();
        txtAlisTarixi = new javax.swing.JTextField();
        btnAdd = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();
        AltKateqoriyaId = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtUmumiMebleg = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtSatisQiymeti = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtQaliqSay = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableMehsullar = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        txtCemMebleg = new javax.swing.JTextField();
        btnAnbar = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        txtAnbarCem = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        txtAxtaris = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        txtBarcode = new javax.swing.JTextField();

        Detallar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        Detallar.setText("Detallar");
        Detallar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DetallarActionPerformed(evt);
            }
        });
        jPopupMenu1.add(Detallar);

        Sil.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        Sil.setText("Sil");
        Sil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SilActionPerformed(evt);
            }
        });
        jPopupMenu1.add(Sil);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(51, 153, 255));

        jPanel1.setBackground(new java.awt.Color(153, 204, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));

        jLabel1.setBackground(new java.awt.Color(102, 102, 255));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Stock");
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jPanel2.setBackground(new java.awt.Color(51, 51, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Malın adı");
        jLabel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel5.setBackground(new java.awt.Color(255, 255, 255));
        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Miqdarı");
        jLabel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel8.setBackground(new java.awt.Color(255, 255, 255));
        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Alış qiyməti");
        jLabel8.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel6.setBackground(new java.awt.Color(255, 255, 255));
        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Mövsüm İD");
        jLabel6.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel7.setBackground(new java.awt.Color(255, 255, 255));
        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Kateqoriya İD");
        jLabel7.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel9.setBackground(new java.awt.Color(255, 255, 255));
        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Altkateqoriya İD");
        jLabel9.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel10.setBackground(new java.awt.Color(255, 255, 255));
        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Tarix");
        jLabel10.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        txtKateqoriyaID.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        txtKateqoriyaID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtKateqoriyaIDActionPerformed(evt);
            }
        });
        txtKateqoriyaID.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                txtKateqoriyaIDPropertyChange(evt);
            }
        });

        txtMovsumId.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        txtMovsumId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMovsumIdActionPerformed(evt);
            }
        });

        txtAlisQiymeti.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        txtAlisQiymeti.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAlisQiymetiActionPerformed(evt);
            }
        });
        txtAlisQiymeti.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtAlisQiymetiKeyReleased(evt);
            }
        });

        txtMiqdari.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        txtMiqdari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMiqdariActionPerformed(evt);
            }
        });

        txtMalinAdi.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        txtMalinAdi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMalinAdiActionPerformed(evt);
            }
        });

        txtAlisTarixi.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        txtAlisTarixi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAlisTarixiActionPerformed(evt);
            }
        });

        btnAdd.setBackground(new java.awt.Color(0, 153, 0));
        btnAdd.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnAdd.setForeground(new java.awt.Color(255, 255, 255));
        btnAdd.setText("Əlavə et");
        btnAdd.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnAdd.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(51, 51, 255));
        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Düzəliş et");
        jButton2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(255, 0, 0));
        jButton3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Sil");
        jButton3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(51, 51, 255));
        jButton4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("İmtina");
        jButton4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setBackground(new java.awt.Color(51, 51, 255));
        jButton5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setText("Bağla");
        jButton5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jLabel11.setBackground(new java.awt.Color(255, 255, 255));
        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Ümumi Məbləğ");
        jLabel11.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        txtUmumiMebleg.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        txtUmumiMebleg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUmumiMeblegActionPerformed(evt);
            }
        });

        jLabel12.setBackground(new java.awt.Color(255, 255, 255));
        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("Satış qiyməti");
        jLabel12.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        txtSatisQiymeti.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        txtSatisQiymeti.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSatisQiymetiActionPerformed(evt);
            }
        });

        jLabel13.setBackground(new java.awt.Color(255, 255, 255));
        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("Qaliq Say");
        jLabel13.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        txtQaliqSay.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        txtQaliqSay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtQaliqSayActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGap(28, 28, 28)
                                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(txtAlisTarixi, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(txtUmumiMebleg, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(AltKateqoriyaId, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(26, 26, 26)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtMalinAdi, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
                                            .addComponent(txtAlisQiymeti, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(txtSatisQiymeti)
                                            .addComponent(txtMovsumId)
                                            .addComponent(txtKateqoriyaID)
                                            .addComponent(txtMiqdari)
                                            .addComponent(txtQaliqSay)))))
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMalinAdi, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtMiqdari, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtQaliqSay, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtAlisQiymeti, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSatisQiymeti, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMovsumId, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtKateqoriyaID, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(AltKateqoriyaId, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtAlisTarixi, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtUmumiMebleg, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jTableMehsullar.setBackground(new java.awt.Color(153, 204, 255));
        jTableMehsullar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jTableMehsullar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "Mehsulun adi", "Miqdari", "Satis Miqdari", "QaliqSay", "Alis Qiymeti", "Satis Qiymeti", "MovsumİD", "KateqoriyaİD", "AltKateqoriyaİD", "Cemi", "Tarix", "Barcode"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Double.class, java.lang.Double.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Double.class, java.lang.Integer.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jTableMehsullar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableMehsullarMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jTableMehsullarMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(jTableMehsullar);
        if (jTableMehsullar.getColumnModel().getColumnCount() > 0) {
            jTableMehsullar.getColumnModel().getColumn(0).setPreferredWidth(50);
            jTableMehsullar.getColumnModel().getColumn(0).setMaxWidth(50);
            jTableMehsullar.getColumnModel().getColumn(1).setMinWidth(100);
            jTableMehsullar.getColumnModel().getColumn(1).setPreferredWidth(200);
            jTableMehsullar.getColumnModel().getColumn(1).setMaxWidth(500);
            jTableMehsullar.getColumnModel().getColumn(2).setMinWidth(20);
            jTableMehsullar.getColumnModel().getColumn(2).setPreferredWidth(50);
            jTableMehsullar.getColumnModel().getColumn(2).setMaxWidth(150);
            jTableMehsullar.getColumnModel().getColumn(4).setMinWidth(30);
            jTableMehsullar.getColumnModel().getColumn(4).setPreferredWidth(50);
            jTableMehsullar.getColumnModel().getColumn(4).setMaxWidth(150);
            jTableMehsullar.getColumnModel().getColumn(5).setMinWidth(30);
            jTableMehsullar.getColumnModel().getColumn(5).setPreferredWidth(80);
            jTableMehsullar.getColumnModel().getColumn(5).setMaxWidth(150);
            jTableMehsullar.getColumnModel().getColumn(6).setMinWidth(50);
            jTableMehsullar.getColumnModel().getColumn(6).setPreferredWidth(83);
            jTableMehsullar.getColumnModel().getColumn(6).setMaxWidth(150);
            jTableMehsullar.getColumnModel().getColumn(7).setMinWidth(2);
            jTableMehsullar.getColumnModel().getColumn(7).setPreferredWidth(2);
            jTableMehsullar.getColumnModel().getColumn(7).setMaxWidth(100);
            jTableMehsullar.getColumnModel().getColumn(8).setMinWidth(2);
            jTableMehsullar.getColumnModel().getColumn(8).setPreferredWidth(2);
            jTableMehsullar.getColumnModel().getColumn(8).setMaxWidth(100);
            jTableMehsullar.getColumnModel().getColumn(9).setMinWidth(2);
            jTableMehsullar.getColumnModel().getColumn(9).setPreferredWidth(2);
            jTableMehsullar.getColumnModel().getColumn(9).setMaxWidth(100);
            jTableMehsullar.getColumnModel().getColumn(10).setMinWidth(30);
            jTableMehsullar.getColumnModel().getColumn(10).setPreferredWidth(40);
            jTableMehsullar.getColumnModel().getColumn(10).setMaxWidth(150);
            jTableMehsullar.getColumnModel().getColumn(11).setPreferredWidth(110);
            jTableMehsullar.getColumnModel().getColumn(12).setMinWidth(50);
            jTableMehsullar.getColumnModel().getColumn(12).setPreferredWidth(100);
            jTableMehsullar.getColumnModel().getColumn(12).setMaxWidth(250);
        }

        jPanel3.setBackground(new java.awt.Color(51, 255, 51));
        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        txtCemMebleg.setFont(new java.awt.Font("Segoe UI", 1, 40)); // NOI18N
        txtCemMebleg.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        btnAnbar.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnAnbar.setText("Anbari Hesabla");
        btnAnbar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAnbarMouseClicked(evt);
            }
        });
        btnAnbar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnbarActionPerformed(evt);
            }
        });

        btnUpdate.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnUpdate.setText("Update");
        btnUpdate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnUpdateMouseClicked(evt);
            }
        });

        txtAnbarCem.setFont(new java.awt.Font("Segoe UI", 1, 40)); // NOI18N
        txtAnbarCem.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtAnbarCem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAnbarCemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(130, Short.MAX_VALUE)
                .addComponent(btnUpdate)
                .addGap(18, 18, 18)
                .addComponent(btnAnbar)
                .addGap(18, 18, 18)
                .addComponent(txtAnbarCem, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtCemMebleg, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCemMebleg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtAnbarCem))
                .addGap(0, 12, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAnbar, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(51, 255, 51));

        txtAxtaris.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtAxtaris.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtAxtaris.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtAxtarisKeyReleased(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 51, 51));
        jLabel2.setText("Axtarış..");

        jComboBox2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ada görə", "Barcoda görə" }));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtAxtaris, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtAxtaris, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Sonuncu mahsulun Barcdu");

        txtBarcode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBarcodeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(179, 179, 179)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(164, 164, 164)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtBarcode, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 475, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(40, 40, 40))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(txtBarcode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtKateqoriyaIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtKateqoriyaIDActionPerformed

        int kateqoriya = Integer.parseInt(txtKateqoriyaID.getText());

        if (kateqoriya == 1) {

            List<AltKateqoriyalar> m = mehDao2.getAltKateqoriyaByKateqoriyaId(kateqoriya);
            {
                jComboBox1.removeAllItems();
                for (int i = 0; i < m.size(); i++) {

                    jComboBox1.addItem(m.get(i));
                }

            }

        }
        if (kateqoriya == 2) {

            List<AltKateqoriyalar> m = mehDao2.getAltKateqoriyaByKateqoriyaId_Qadin(kateqoriya);
            {
                jComboBox1.removeAllItems();
                for (int i = 0; i < m.size(); i++) {

                    jComboBox1.addItem(m.get(i));
                }

            }

        }

        if (kateqoriya == 3) {

            List<AltKateqoriyalar> m = mehDao2.getAltKateqoriyaByKateqoriyaId_Usaq(kateqoriya);
            {
                jComboBox1.removeAllItems();
                for (int i = 0; i < m.size(); i++) {

                    jComboBox1.addItem(m.get(i));
                }

            }

        }

        if (kateqoriya == 4) {

            List<AltKateqoriyalar> m = mehDao2.getAltKateqoriyaByKateqoriyaId_Qarisiq(kateqoriya);
            {
                jComboBox1.removeAllItems();
                for (int i = 0; i < m.size(); i++) {

                    jComboBox1.addItem(m.get(i));
                }

            }

        }


    }//GEN-LAST:event_txtKateqoriyaIDActionPerformed

    private void txtMovsumIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMovsumIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMovsumIdActionPerformed

    private void txtAlisQiymetiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAlisQiymetiActionPerformed

        double miqdari = Double.parseDouble(txtMiqdari.getText());
        double qiymeti = Double.parseDouble(txtAlisQiymeti.getText());

        double cem = miqdari * qiymeti;
        txtUmumiMebleg.setText(Double.toString(cem));


    }//GEN-LAST:event_txtAlisQiymetiActionPerformed

    private void txtMiqdariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMiqdariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMiqdariActionPerformed

    private void txtMalinAdiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMalinAdiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMalinAdiActionPerformed

    private void txtAlisTarixiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAlisTarixiActionPerformed
        java.util.Date date = new java.util.Date();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");
        String date2 = sdf.format(date);

        txtAlisTarixi.setText(date2);
    }//GEN-LAST:event_txtAlisTarixiActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        try {
            if (txtMalinAdi.getText().isEmpty() & txtMiqdari.getText().isEmpty() & txtAlisQiymeti.getText().isEmpty() & txtMovsumId.getText().isEmpty() & txtKateqoriyaID.getText().isEmpty() & AltKateqoriyaId.getText().isEmpty() & txtAlisTarixi.getText().isEmpty()) {

                JOptionPane.showMessageDialog(this, "Zəhmət olmasa bütün məlumatları doldurun");
            } else {

                String malinadi = txtMalinAdi.getText();
                int miqdari = Integer.parseInt(txtMiqdari.getText());
                double qiymet = Double.parseDouble(txtAlisQiymeti.getText());
                int movsumId = Integer.parseInt(txtMovsumId.getText());
                int kateqoriyaId = Integer.parseInt(txtKateqoriyaID.getText());
                double satisQiymeti = Double.parseDouble(txtSatisQiymeti.getText());
                int altKateqoriya = jComboBox1.getSelectedIndex();
                String alisTarixi = txtAlisTarixi.getText();
                double umumiMebleg = Double.parseDouble(txtUmumiMebleg.getText());
                int Satis_Miqdari = 0;
                double Satisin_Toplam_Deyeri = 0;

                boolean yoxla = txtCemMebleg.getText().isEmpty();
                if (yoxla == false) {
                    double cemMebleg = Double.parseDouble(txtCemMebleg.getText());
                    double cemMeblegYeni = umumiMebleg + cemMebleg;
                    txtCemMebleg.setText(Double.toString(cemMeblegYeni));

                } else {
                    txtCemMebleg.setText(Double.toString(umumiMebleg));
                }

                pres = con.prepareStatement("insert into mehsullar (Malin_adi, Miqdari, Alis_qiymeti,Satis_Miqdari,Satis_qiymeti, Satisin_toplam_deyeri, Qaliq_say, Movsum_id, Kateqoriya_id, Alt_kateqoriya_id, Alis_Tarixi, Alisin_toplam_deyer ) values(?,?,?,?,?,?,?,?,?,?,?,?)");

                pres.setString(1, malinadi);
                pres.setInt(2, miqdari);
                pres.setDouble(3, qiymet);
                pres.setInt(4, Satis_Miqdari);
                pres.setDouble(5, satisQiymeti);
                pres.setDouble(6, Satisin_Toplam_Deyeri);
                pres.setDouble(7, miqdari);
                pres.setInt(8, movsumId);
                pres.setInt(9, kateqoriyaId);
                pres.setInt(10, altKateqoriya + 1);
                pres.setString(11, alisTarixi);
                pres.setDouble(12, umumiMebleg);
                pres.executeUpdate();
                load();

                Mehsullar mehsul = mehDao.getTheLastMehsulById();

                String ss = mehsul.getId2();

                int sss = (mehsul.getId());
               
                

                pres = con.prepareStatement("update  mehsullar set Barcode=? where id =" + ss);
                pres.setString(1, "1234500" + sss);
                pres.executeUpdate();
   
                txtBarcode.setText("1234500" + sss);
                load();
                String barcode = "1234500"+sss;
                
                String alis = Double.toString(satisQiymeti);
                alis.length();
                   

                String satisQiy = generateBarcode.test2(satisQiymeti);

                System.out.println(satisQiy);
                
                generateBarcode.main(barcode, malinadi, "****", "****", satisQiy);
                



                JOptionPane.showMessageDialog(this, "Mehsul ugurla elave olundu");

                txtMalinAdi.setText("");
                txtMiqdari.setText("");
                txtAlisQiymeti.setText("");
                txtSatisQiymeti.setText("");
                txtQaliqSay.setText("");
                txtMovsumId.setText("");
                txtKateqoriyaID.setText("");
                AltKateqoriyaId.setText("");
                txtAlisTarixi.setText("");
                txtUmumiMebleg.setText("");
                txtMalinAdi.requestFocus();

            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (DocumentException ex) {
            Logger.getLogger(Mehsul_Elave_etmek.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Mehsul_Elave_etmek.class.getName()).log(Level.SEVERE, null, ex);
        }


    }//GEN-LAST:event_btnAddActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        df = (DefaultTableModel) jTableMehsullar.getModel();

        int selected = jTableMehsullar.getSelectedRow();

        int id = Integer.parseInt(df.getValueAt(selected, 0).toString());

        String malinAdi = txtMalinAdi.getText();
        int miqdari = Integer.parseInt(txtMiqdari.getText());
        int qaliqSay = Integer.parseInt(txtQaliqSay.getText());
        double qiymet = Double.parseDouble(txtAlisQiymeti.getText());
        int movsumId = Integer.parseInt(txtMovsumId.getText());
        double satisQiymeti = Double.parseDouble(txtSatisQiymeti.getText());
        int kateqoriya = Integer.parseInt(txtKateqoriyaID.getText());
        int altKateqoriya = Integer.parseInt(AltKateqoriyaId.getText());
        String tarix = txtAlisTarixi.getText();
        double umumiMebleg = Double.parseDouble(txtUmumiMebleg.getText());

        try {
            pres = con.prepareStatement("update mehsullar set Malin_adi=?, Miqdari=?, Qaliq_say=?, Alis_qiymeti=?, Satis_qiymeti =?, Movsum_id=?, Kateqoriya_id=?, Alt_kateqoriya_id=?, Alis_Tarixi=?, Alisin_toplam_deyer=? where id =?");

            pres.setString(1, malinAdi);
            pres.setInt(2, miqdari);
            pres.setInt(3, qaliqSay);
            pres.setDouble(4, qiymet);
            pres.setDouble(5, satisQiymeti);
            pres.setInt(6, movsumId);
            pres.setInt(7, kateqoriya);
            pres.setInt(8, altKateqoriya);
            pres.setString(9, tarix);
            pres.setDouble(10, umumiMebleg);
            pres.setInt(11, id);
            pres.executeUpdate();
            load();
            JOptionPane.showMessageDialog(this, "Mehsulun melumatlari ugurla yenilendi");

            txtMalinAdi.setText("");
            txtMiqdari.setText("");
            txtAlisQiymeti.setText("");
            txtMovsumId.setText("");
            txtKateqoriyaID.setText("");
            AltKateqoriyaId.setText("");
            txtAlisTarixi.setText("");
            txtUmumiMebleg.setText("");
            txtMalinAdi.requestFocus();
            btnAdd.setVisible(true);

        } catch (SQLException ex) {
            Logger.getLogger(Satici_Elave_Etmek.class.getName()).log(Level.SEVERE, null, ex);
        }


    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTableMehsullarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableMehsullarMouseClicked

        df = (DefaultTableModel) jTableMehsullar.getModel();

        int selected = jTableMehsullar.getSelectedRow();

        int id = Integer.parseInt(df.getValueAt(selected, 0).toString());
        txtMalinAdi.setText(df.getValueAt(selected, 1).toString());
        txtMiqdari.setText(df.getValueAt(selected, 2).toString());
        txtQaliqSay.setText(df.getValueAt(selected, 4).toString());
        txtAlisQiymeti.setText(df.getValueAt(selected, 5).toString());
        txtSatisQiymeti.setText(df.getValueAt(selected, 6).toString());
        txtMovsumId.setText(df.getValueAt(selected, 7).toString());
        txtKateqoriyaID.setText(df.getValueAt(selected, 8).toString());
        AltKateqoriyaId.setText(df.getValueAt(selected, 9).toString());
        txtAlisTarixi.setText(df.getValueAt(selected, 11).toString());
        txtBarcode.setText(df.getValueAt(selected, 12).toString());
        txtUmumiMebleg.setText(df.getValueAt(selected, 10).toString());

        btnAdd.setVisible(false);


    }//GEN-LAST:event_jTableMehsullarMouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

        detele();

    }//GEN-LAST:event_jButton3ActionPerformed

    public void exploreAboutProduct() {

        try {
            df = (DefaultTableModel) jTableMehsullar.getModel();
            int selected = jTableMehsullar.getSelectedRow();

            int id = Integer.parseInt(df.getValueAt(selected, 0).toString());
            String malinAdi = (df.getValueAt(selected, 1).toString());

            Mehsullar mehsul = mehDao.getMehsulById(id);
            Mehsullar qaliq = mehDao.exploreMehsulQaliqById(id);
            Integer satisMiq = mehsul.getNumberOfProduct();
            
            Integer qaliqsay = qaliq.getHowMuchLeft();
            System.out.println("budu---> "+qaliqsay);
            pres = con.prepareStatement("truncate table detallar;");
            pres.executeUpdate();

            pres = con.prepareStatement("insert into detallar (DetalliArasdirilacaqMehsul, id, Miqdari, Qaliq_say ) values(?,?,?,?)");
            pres.setString(1, malinAdi);
            pres.setInt(2, id);
            pres.setInt(3, satisMiq);
            pres.setInt(4, qaliqsay);
            pres.executeUpdate();

            exploreAboutProduct ex = new exploreAboutProduct();
            ex.setVisible(true);

           

            

            
            
        } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public void detele(){
        
          df = (DefaultTableModel) jTableMehsullar.getModel();

            int selected = jTableMehsullar.getSelectedRow();

            int id = Integer.parseInt(df.getValueAt(selected, 0).toString());
            
            Mehsullar mehsul =mehDao.getMehsulById(id);
            String mehsulunAdi = mehsul.getName();
            
            int cavab = JOptionPane.showConfirmDialog(this, "Silinən məhsulu geri qaytarmaq mümkün olmayacaq! \n"+mehsulunAdi+"-adli mehsulu silmək istədiyinizdən əminsiniz?", "Diqqət!", JOptionPane.YES_NO_CANCEL_OPTION);
            
             if (cavab == 0) {
            
            try {
                pres = con.prepareStatement("delete from mehsullar where id = ? ");

                pres.setInt(1, id);

                pres.executeUpdate();

                load();

                JOptionPane.showMessageDialog(this,  " "+mehsulunAdi+"-adli mehsul silindi");

                txtMalinAdi.setText("");

                txtMiqdari.setText("");

                txtAlisQiymeti.setText("");

                txtMovsumId.setText("");

                txtKateqoriyaID.setText("");

                AltKateqoriyaId.setText("");

                txtAlisTarixi.setText("");

                txtUmumiMebleg.setText("");

                txtMalinAdi.requestFocus();

                btnAdd.setVisible(true);

            } catch (SQLException ex) {
                Logger.getLogger(Satici_Elave_Etmek.class.getName()).log(Level.SEVERE, null, ex);
            }

        }if (cavab ==1) {
            
        }if (cavab ==2) {
            
        }else{
            
            
        }


    }
    
    
    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed

        txtMalinAdi.setText("");
        txtMiqdari.setText("");
        txtAlisQiymeti.setText("");
        txtMovsumId.setText("");
        txtKateqoriyaID.setText("");
        AltKateqoriyaId.setText("");
        txtQaliqSay.setText("");
        txtSatisQiymeti.setText("");
        txtUmumiMebleg.setText("");
        txtMalinAdi.requestFocus();
        btnAdd.setVisible(true);

    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed

        this.setVisible(false);

    }//GEN-LAST:event_jButton5ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // String id = jComboBox1.getSelectedItem().toString();
        // AltKateqoriyaId.setText(id);
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void txtKateqoriyaIDPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_txtKateqoriyaIDPropertyChange

    }//GEN-LAST:event_txtKateqoriyaIDPropertyChange

    private void txtUmumiMeblegActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUmumiMeblegActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUmumiMeblegActionPerformed

    private void btnAnbarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnbarActionPerformed


    }//GEN-LAST:event_btnAnbarActionPerformed

    private void btnAnbarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAnbarMouseClicked
        DecimalFormat dformater =  new DecimalFormat("#.##");
        txtAnbarCem.setText("");
        df = (DefaultTableModel) jTableMehsullar.getModel();

        Double ID, Miqdari;

        for (int i = 0; i < df.getRowCount(); i++) {

            Miqdari = Double.parseDouble(df.getValueAt(i, 10).toString());
            boolean yoxla = txtAnbarCem.getText().isEmpty();

            if (yoxla != false) {
                String formattedGelir = dformater.format(Miqdari);
                txtAnbarCem.setText(Double.toString((double) Miqdari));
            } else {
                String formattedMebleg = dformater.format(Miqdari);
                double kohneMebleg = Double.parseDouble(txtAnbarCem.getText());
                String formattedMebleg2 = dformater.format(kohneMebleg);
                double cemmebleg = kohneMebleg + Miqdari;
                String formattedGelir = dformater.format(cemmebleg);
                txtAnbarCem.setText(formattedGelir);
            }

        }


    }//GEN-LAST:event_btnAnbarMouseClicked

    private void txtSatisQiymetiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSatisQiymetiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSatisQiymetiActionPerformed

    private void btnUpdateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUpdateMouseClicked
        load();
    }//GEN-LAST:event_btnUpdateMouseClicked

    private void txtAxtarisKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAxtarisKeyReleased
        axtaris();

    }//GEN-LAST:event_txtAxtarisKeyReleased

    private void txtAlisQiymetiKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAlisQiymetiKeyReleased
        double miqdari = Double.parseDouble(txtMiqdari.getText());
        double qiymeti = Double.parseDouble(txtAlisQiymeti.getText());

        double cem = miqdari * qiymeti;
        txtUmumiMebleg.setText(Double.toString(cem));
    }//GEN-LAST:event_txtAlisQiymetiKeyReleased

    private void txtQaliqSayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtQaliqSayActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtQaliqSayActionPerformed

    private void txtBarcodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBarcodeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBarcodeActionPerformed

    private void jTableMehsullarMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableMehsullarMouseReleased
        
        df = (DefaultTableModel) jTableMehsullar.getModel();

        int selected = jTableMehsullar.getSelectedRow();

        if (SwingUtilities.isRightMouseButton(evt)) {
            jPopupMenu1.show(jTableMehsullar, evt.getX(), evt.getY());
        }
        
        
        
        
        
    }//GEN-LAST:event_jTableMehsullarMouseReleased

    private void SilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SilActionPerformed
       detele();
    }//GEN-LAST:event_SilActionPerformed

    private void DetallarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DetallarActionPerformed
        exploreAboutProduct();
    }//GEN-LAST:event_DetallarActionPerformed

    private void txtAnbarCemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAnbarCemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAnbarCemActionPerformed

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
            java.util.logging.Logger.getLogger(Mehsul_Elave_etmek.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Mehsul_Elave_etmek.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Mehsul_Elave_etmek.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Mehsul_Elave_etmek.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                try {
                    new Mehsul_Elave_etmek().setVisible(true);
                } catch (Exception ex) {
                    Logger.getLogger(Mehsul_Elave_etmek.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField AltKateqoriyaId;
    private javax.swing.JMenuItem Detallar;
    private javax.swing.JMenuItem Sil;
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnAnbar;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JComboBox<AltKateqoriyalar> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableMehsullar;
    private javax.swing.JTextField txtAlisQiymeti;
    private javax.swing.JTextField txtAlisTarixi;
    private javax.swing.JTextField txtAnbarCem;
    private javax.swing.JTextField txtAxtaris;
    private javax.swing.JTextField txtBarcode;
    private javax.swing.JTextField txtCemMebleg;
    private javax.swing.JTextField txtKateqoriyaID;
    private javax.swing.JTextField txtMalinAdi;
    private javax.swing.JTextField txtMiqdari;
    private javax.swing.JTextField txtMovsumId;
    private javax.swing.JTextField txtQaliqSay;
    private javax.swing.JTextField txtSatisQiymeti;
    private javax.swing.JTextField txtUmumiMebleg;
    // End of variables declaration//GEN-END:variables
}
