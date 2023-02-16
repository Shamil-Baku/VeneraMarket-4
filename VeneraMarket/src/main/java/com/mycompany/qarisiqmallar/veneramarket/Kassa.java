/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.qarisiqmallar.veneramarket;

import com.mycompany.DaoInter.MehsullarDaoInter;
import com.mycompany.entity.Mehsullar;
import com.mycompany.main.Contex;
import java.sql.Connection;
// import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import org.bouncycastle.math.Primes;

/**
 *
 * @author samil
 */
public class Kassa extends javax.swing.JFrame {

    public MehsullarDaoInter mehDao = Contex.instanceOfMehsullarDao();

    public Kassa() throws Exception {
        initComponents();
        connect();
        jPanel2.setVisible(false);
        buttonGroup1.add(rbBugun);
        buttonGroup1.add(dunen);
        buttonGroup1.add(sonUcGun);
        buttonGroup1.add(sonBirHefte);
        buttonGroup1.add(sonBirAy);
        rbBugun.doClick();
        Date dt = new Date();
        sdf = new SimpleDateFormat("HH:mm:ss");
        sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        txtBillNumberForPrint.disable();
        time = sdf2.format(dt);

    }
    Date date = new Date();

    Connection con;
    PreparedStatement pres;
    DefaultTableModel df;
    Statement stmt;
    SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

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

    public void getTodaysExpensesWithTwoDatesWithNames(String dateStart, String dateEnd, String nameOfExpenseOrCandidate) {
        int a;
        double netExpense = 0;
        try {

            String selectedOption = cbOption.getSelectedItem().toString();

            if (selectedOption.equals("Сделать выбор...")) {
                JOptionPane.showMessageDialog(this, "Zehmet olmasa seçim edin!", "DİQQET!", HEIGHT);
            }
            if (selectedOption.equals("Ad ve Soyada gore")) {
                con = connect();
                pres = con.prepareStatement("select * from expenses where date BETWEEN " + "'" + dateStart + "'" + " and " + "'" + dateEnd + "'" + " and name like " + "'" + "%" + nameOfExpenseOrCandidate + "%" + "'");
                rs = pres.executeQuery();
            }
            if (selectedOption.equals("Xercin tipine gore")) {
                con = connect();
                pres = con.prepareStatement("select * from expenses where date BETWEEN " + "'" + dateStart + "'" + " and " + "'" + dateEnd + "'" + " and categoryOfExpense like " + "'" + "%" + nameOfExpenseOrCandidate + "%" + "'");
                rs = pres.executeQuery();
            }
            if (selectedOption.equals("Komentariye gore")) {
                con = connect();
                pres = con.prepareStatement("select * from expenses where date BETWEEN " + "'" + dateStart + "'" + " and " + "'" + dateEnd + "'" + " and commentary like " + "'" + "%" + nameOfExpenseOrCandidate + "%" + "'");
                rs = pres.executeQuery();
            }

            ResultSet rs = pres.executeQuery();

            ResultSetMetaData rd = rs.getMetaData();
            a = rd.getColumnCount();
            df = (DefaultTableModel) jtableExpenses.getModel();
            df.setRowCount(0);

            while (rs.next()) {
                Vector v2 = new Vector();
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String option = rs.getString("categoryOfExpense");
                String commentary = rs.getString("commentary");
                Date date2 = rs.getDate("date");
                double totalPrice = rs.getDouble("totalExpense");

                v2.add(id);
                v2.add(name);
                v2.add(option);
                v2.add(commentary);
                v2.add(date2);
                v2.add(totalPrice);
                netExpense += totalPrice;
                df.addRow(v2);
            }

            txtTotalExpenses.setText(Double.toString(netExpense));

        } catch (Exception ex) {

            ex.printStackTrace();

        }

    }

    public void getYesterdaysExpensesWithName(String date, String nameOfExpenseOrCandidate) {
        int a;
        double netExpense = 0;
        try {

            String selectedOption = cbOption.getSelectedItem().toString();

            if (selectedOption.equals("Сделать выбор...")) {
                JOptionPane.showMessageDialog(this, "Пожалуйста сделайте выбор!", "Внимание!", HEIGHT);
            }
            if (selectedOption.equals("По названию (ФИО)")) {
                con = connect();
                pres = con.prepareStatement("select * from expenses where date = " + "'" + date + "'" + " and name like " + "'" + "%" + nameOfExpenseOrCandidate + "%" + "'");
                rs = pres.executeQuery();
            }
            if (selectedOption.equals("По пункт расходов")) {
                con = connect();
                pres = con.prepareStatement("select * from expenses where date = " + "'" + date + "'" + " and categoryOfExpense like " + "'" + "%" + nameOfExpenseOrCandidate + "%" + "'");
                rs = pres.executeQuery();
            }
            if (selectedOption.equals("По комментарию")) {
                con = connect();
                pres = con.prepareStatement("select * from expenses where date = " + "'" + date + "'" + " and commentary like " + "'" + "%" + nameOfExpenseOrCandidate + "%" + "'");
                rs = pres.executeQuery();
            }

            ResultSet rs = pres.executeQuery();

            ResultSetMetaData rd = rs.getMetaData();
            a = rd.getColumnCount();
            df = (DefaultTableModel) jtableExpenses.getModel();
            df.setRowCount(0);

            while (rs.next()) {
                Vector v2 = new Vector();
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String option = rs.getString("categoryOfExpense");
                String commentary = rs.getString("commentary");
                Date date2 = rs.getDate("date");
                double totalPrice = rs.getDouble("totalExpense");

                v2.add(id);
                v2.add(name);
                v2.add(option);
                v2.add(commentary);
                v2.add(date2);
                v2.add(totalPrice);
                netExpense += totalPrice;
                df.addRow(v2);
            }

            txtTotalExpenses.setText(Double.toString(netExpense));

        } catch (Exception ex) {

            ex.printStackTrace();

        }

    }

    public void getTodaysExpensesWithName() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = new GregorianCalendar();
        String ss = sdf.format(cal.getTime());
        cal.add(Calendar.DAY_OF_MONTH, -7);
        String lastThreeDay = sdf.format(cal.getTime());
        String clientName = txtAxtaris.getText();
        getTodaysExpensesWithName(clientName);

    }

    public void getTodaysExpensesWithName(String nameOfExpenseOrCandidate) {
        int a;
        double netExpense = 0;
        try {

            String selectedOption = cbOption.getSelectedItem().toString();
            if (selectedOption.equals("Seçim edin..")) {
                JOptionPane.showMessageDialog(this, "Zehmet olmasa seçim edin", "DİQQET!", HEIGHT);
            }
            if (selectedOption.equals("Ad ve Soyada gore")) {
                con = connect();
                pres = con.prepareStatement("select * from expenses where date = CURRENT_DATE " + " and name like " + "'" + "%" + nameOfExpenseOrCandidate + "%" + "'");
                rs = pres.executeQuery();
            }
            if (selectedOption.equals("Xercin tipine gore")) {
                con = connect();
                pres = con.prepareStatement("select * from expenses where date = CURRENT_DATE " + " and categoryOfExpense like " + "'" + "%" + nameOfExpenseOrCandidate + "%" + "'");
                rs = pres.executeQuery();
            }
            if (selectedOption.equals("Komentariye gore")) {
                con = connect();
                pres = con.prepareStatement("select * from expenses where date = CURRENT_DATE " + " and commentary like " + "'" + "%" + nameOfExpenseOrCandidate + "%" + "'");
                rs = pres.executeQuery();
            }

            ResultSetMetaData rd = rs.getMetaData();
            a = rd.getColumnCount();
            df = (DefaultTableModel) jtableExpenses.getModel();
            df.setRowCount(0);

            while (rs.next()) {
                Vector v2 = new Vector();

                int id = rs.getInt("id");
                String name = rs.getString("name");
                String option = rs.getString("categoryOfExpense");
                String commentary = rs.getString("commentary");
                Date date2 = rs.getDate("date");
                double totalPrice = rs.getDouble("totalExpense");

                v2.add(id);
                v2.add(name);
                v2.add(option);
                v2.add(commentary);
                v2.add(date2);
                v2.add(totalPrice);
                netExpense += totalPrice;
                df.addRow(v2);

            }

            txtTotalExpenses.setText(Double.toString(netExpense));

        } catch (Exception ex) {

            ex.printStackTrace();

        }

    }

    public void getTheLastWeeksExpensesWithName(String date, String nameOfExpenseOrCandidate) {
        int a;
        double netExpense = 0;
        try {

            String selectedOption = cbOption.getSelectedItem().toString();
            if (selectedOption.equals("Seçim edin..")) {
                JOptionPane.showMessageDialog(this, "Zehmet olmasa seçim edin", "DİQQET!", HEIGHT);
            }
            if (selectedOption.equals("Ad ve Soyada gore")) {
                con = connect();
                pres = con.prepareStatement("select * from expenses where date BETWEEN " + "'" + date + "'" + " and CURRENT_DATE" + " and name like " + "'" + "%" + nameOfExpenseOrCandidate + "%" + "'");
                rs = pres.executeQuery();
            }
            if (selectedOption.equals("Xercin tipine gore")) {
                con = connect();
                pres = con.prepareStatement("select * from expenses where date BETWEEN " + "'" + date + "'" + " and CURRENT_DATE" + " and categoryOfExpense like " + "'" + "%" + nameOfExpenseOrCandidate + "%" + "'");
                rs = pres.executeQuery();
            }
            if (selectedOption.equals("Komentariye gore")) {
                con = connect();
                pres = con.prepareStatement("select * from expenses where date BETWEEN " + "'" + date + "'" + " and CURRENT_DATE" + " and commentary like " + "'" + "%" + nameOfExpenseOrCandidate + "%" + "'");
                rs = pres.executeQuery();
            }

            ResultSetMetaData rd = rs.getMetaData();
            a = rd.getColumnCount();
            df = (DefaultTableModel) jtableExpenses.getModel();
            df.setRowCount(0);

            while (rs.next()) {
                Vector v2 = new Vector();
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String option = rs.getString("categoryOfExpense");
                String commentary = rs.getString("commentary");
                Date date2 = rs.getDate("date");
                double totalPrice = rs.getDouble("totalExpense");

                v2.add(id);
                v2.add(name);
                v2.add(option);
                v2.add(commentary);
                v2.add(date2);
                v2.add(totalPrice);
                netExpense += totalPrice;
                df.addRow(v2);
            }

            txtTotalExpenses.setText(Double.toString(netExpense));

        } catch (Exception ex) {

            ex.printStackTrace();

        }

    }

    public void startSearchForExpenses() {

        String nameOfClient = txtAxtaris.getText();

        boolean checkLastMonth = sonBirAy.isSelected();
        boolean checkLastWeek = sonBirHefte.isSelected();
        boolean checLastThreeDay = sonUcGun.isSelected();
        boolean chechYesterday = dunen.isSelected();
        boolean checkToday = rbBugun.isSelected();
        Date firstDate2 = ilkTarix.getDate();
        Date secondDate2 = sonTarix.getDate();

        System.out.println(firstDate2);
        System.out.println(secondDate2);

        if (firstDate2 != null && secondDate2 != null) {
            System.out.println("Beli qaqaw tarix null dan ferqlidir. Budur.. " + firstDate2);

            String firstDate1 = sdf.format(ilkTarix.getDate());
            String secondDAte = sdf.format(sonTarix.getDate());

            getTodaysExpensesWithTwoDatesWithNames(firstDate1, secondDAte, nameOfClient);

            double profit = Double.parseDouble(UmumiSatis.getText());
            double expenses = Double.parseDouble(txtTotalExpenses.getText());

            double result = profit - expenses;
            double roundedResult = Math.round(result * 100.000) / 100.000;
            txtTotalProfit.setText(Double.toString(roundedResult));

        } else {

            if (checkToday == true) {

                getTodaysExpensesWithName();
            }
            if (chechYesterday == true) {

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Calendar cal = new GregorianCalendar();
                String ss = sdf.format(cal.getTime());
                cal.add(Calendar.DAY_OF_MONTH, -1);
                String lastThreeDay = sdf.format(cal.getTime());

                getYesterdaysExpensesWithName(lastThreeDay, nameOfClient);
            }
            if (checkLastWeek == true) {

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Calendar cal = new GregorianCalendar();
                String ss = sdf.format(cal.getTime());
                cal.add(Calendar.DAY_OF_MONTH, -7);
                String lastWeek = sdf.format(cal.getTime());
                getTheLastWeeksExpensesWithName(lastWeek, nameOfClient);
            }
            if (checkLastMonth == true) {

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Calendar cal = new GregorianCalendar();
                String ss = sdf.format(cal.getTime());
                cal.add(Calendar.DAY_OF_MONTH, -30);
                String lastWeek = sdf.format(cal.getTime());
                getTheLastWeeksExpensesWithName(lastWeek, nameOfClient);;
            }

        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        rbBugun = new javax.swing.JRadioButton();
        sonUcGun = new javax.swing.JRadioButton();
        sonBirHefte = new javax.swing.JRadioButton();
        sonBirAy = new javax.swing.JRadioButton();
        UmumiSatis = new javax.swing.JTextField();
        btnHesabla = new javax.swing.JButton();
        txtKassa = new javax.swing.JTextField();
        dunen = new javax.swing.JRadioButton();
        ilkTarix = new com.toedter.calendar.JDateChooser();
        sonTarix = new com.toedter.calendar.JDateChooser();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel3 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        txtTotalProfit = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtTotalExpenses = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtCapitalBudget = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtTotalBudget = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jButton7 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        listNameOfClients = new javax.swing.JList<>();
        txtAxtaris = new javax.swing.JTextField();
        txtPayment = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        cbOption = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        cBoxOptionFoorSearch = new javax.swing.JComboBox<>();
        cBoxOptionForBill = new javax.swing.JComboBox<>();
        txtBillNumberForPrint = new javax.swing.JTextField();
        jButton9 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblGelirCedveli = new javax.swing.JTable();
        jButton5 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtableExpenses = new javax.swing.JTable();
        jButton8 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 0, 153));

        jPanel1.setBackground(new java.awt.Color(255, 51, 51));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));

        rbBugun.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        rbBugun.setForeground(new java.awt.Color(255, 255, 255));
        rbBugun.setText("Bugun");
        rbBugun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbBugunActionPerformed(evt);
            }
        });
        rbBugun.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                rbBugunKeyPressed(evt);
            }
        });

        sonUcGun.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        sonUcGun.setForeground(new java.awt.Color(255, 255, 255));
        sonUcGun.setText("Son 3 gun");
        sonUcGun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sonUcGunActionPerformed(evt);
            }
        });

        sonBirHefte.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        sonBirHefte.setForeground(new java.awt.Color(255, 255, 255));
        sonBirHefte.setText("Son 1hefte");
        sonBirHefte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sonBirHefteActionPerformed(evt);
            }
        });

        sonBirAy.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        sonBirAy.setForeground(new java.awt.Color(255, 255, 255));
        sonBirAy.setText("Son 1 ay");
        sonBirAy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sonBirAyActionPerformed(evt);
            }
        });

        UmumiSatis.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        UmumiSatis.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        UmumiSatis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UmumiSatisActionPerformed(evt);
            }
        });

        btnHesabla.setText("Hesabla");
        btnHesabla.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnHesablaMouseClicked(evt);
            }
        });
        btnHesabla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHesablaActionPerformed(evt);
            }
        });

        txtKassa.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        txtKassa.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        dunen.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        dunen.setForeground(new java.awt.Color(255, 255, 255));
        dunen.setText("Dunen");
        dunen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dunenActionPerformed(evt);
            }
        });

        ilkTarix.setDateFormatString("yyyy-MM-dd");

        sonTarix.setDateFormatString("yyyy-MM-dd");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("-Tarixdən");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("-Qədər");
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Tarix seç..");

        jButton6.setText("Təmizlə");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Cari gelir - Kassa");

        txtTotalProfit.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        txtTotalProfit.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTotalProfit.setMaximumSize(new java.awt.Dimension(179, 40));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Ümumi Xercler");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Xalis Gəlir");

        txtTotalExpenses.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        txtTotalExpenses.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Ümumi Büdce");

        txtCapitalBudget.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        txtCapitalBudget.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Kapital budce");

        txtTotalBudget.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Xeyir");

        jButton7.setText("Redakte et");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        listNameOfClients.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listNameOfClientsMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(listNameOfClients);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 79, Short.MAX_VALUE)
        );

        txtAxtaris.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAxtarisActionPerformed(evt);
            }
        });
        txtAxtaris.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtAxtarisKeyReleased(evt);
            }
        });

        txtPayment.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        txtPayment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPaymentActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Ödeniş");

        cbOption.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seçim edin..", "Ad ve Soyada gore", "Xercin tipine gore", "Komentariye gore" }));

        jLabel11.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Ne axtarılsın..");

        jLabel12.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Axtar..");

        jLabel13.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Xerc üçün seçim..");

        cBoxOptionFoorSearch.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Müşteri axtar..", "Xerc axtar..", "Mehsul axtar.." }));

        cBoxOptionForBill.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Sonuncu satiş", "Çek nömreine esasen" }));
        cBoxOptionForBill.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cBoxOptionForBillActionPerformed(evt);
            }
        });

        jButton9.setText("Çek çap edin");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jSeparator1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(rbBugun, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(dunen))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(sonBirHefte)
                                    .addComponent(sonUcGun)))
                            .addComponent(sonBirAy))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(sonTarix, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE)
                                    .addComponent(ilkTarix, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE)
                                        .addGap(24, 24, 24)))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton7)
                                    .addComponent(txtCapitalBudget, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(20, 20, 20)
                                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(9, 9, 9)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtKassa, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(UmumiSatis, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtTotalExpenses, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtTotalProfit, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel9)
                                        .addGap(59, 59, 59)
                                        .addComponent(jLabel5)
                                        .addGap(54, 54, 54)
                                        .addComponent(jLabel6)
                                        .addGap(39, 39, 39)))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtTotalBudget, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel7)
                                        .addGap(12, 12, 12))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(btnHesabla)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton6))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(cBoxOptionForBill, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton9))
                            .addComponent(txtBillNumberForPrint, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbOption, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(28, 28, 28)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel11)
                                    .addComponent(jLabel12))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtAxtaris, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
                                    .addComponent(cBoxOptionFoorSearch, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtPayment, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addGap(45, 45, 45)))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jLabel9)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtKassa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCapitalBudget, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(UmumiSatis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTotalExpenses, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTotalProfit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTotalBudget, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel3)
                            .addGap(4, 4, 4)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(ilkTarix, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel1))
                            .addGap(4, 4, 4)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel2)
                                .addComponent(sonTarix, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(rbBugun)
                                .addComponent(sonUcGun))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(dunen)
                                .addComponent(sonBirHefte))
                            .addGap(4, 4, 4)
                            .addComponent(sonBirAy))))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton6)
                            .addComponent(btnHesabla)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton7)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtAxtaris, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel12))
                                .addGap(6, 6, 6)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel11)
                                    .addComponent(cBoxOptionFoorSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(cBoxOptionForBill, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton9))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtBillNumberForPrint, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbOption, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13)))
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(txtPayment, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jScrollPane1.setBackground(new java.awt.Color(255, 51, 51));

        tblGelirCedveli.setBackground(new java.awt.Color(153, 255, 255));
        tblGelirCedveli.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        tblGelirCedveli.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        tblGelirCedveli.setForeground(new java.awt.Color(51, 51, 51));
        tblGelirCedveli.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Malin Adi", "Ödenişin növü", "Miqdarı", "Cem Mebleğ", "Alis Qiymeti", "Satis Qiymeti", "Umumi Satis Miqdari", "Satis Tarixi", "Gelir", "Umumi Xeyir", "Qismen_Odenis", "Qaytarilan_Mehsulun_Meblegi", "Musteriye_Geri_Odenis", "Borc alanin adi", "Yeni goturulen mehsul", "Borcdan-Gelen_Mebleg", "Kassir", "Çek nömresi", "Satış-İD", "Borc-ID"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Object.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.String.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.String.class, java.lang.Double.class, java.lang.Double.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tblGelirCedveli.setRowHeight(28);
        tblGelirCedveli.setShowGrid(true);
        tblGelirCedveli.setShowHorizontalLines(false);
        jScrollPane1.setViewportView(tblGelirCedveli);
        if (tblGelirCedveli.getColumnModel().getColumnCount() > 0) {
            tblGelirCedveli.getColumnModel().getColumn(0).setMinWidth(30);
            tblGelirCedveli.getColumnModel().getColumn(0).setPreferredWidth(40);
            tblGelirCedveli.getColumnModel().getColumn(0).setMaxWidth(80);
            tblGelirCedveli.getColumnModel().getColumn(1).setPreferredWidth(200);
            tblGelirCedveli.getColumnModel().getColumn(3).setPreferredWidth(50);
            tblGelirCedveli.getColumnModel().getColumn(4).setPreferredWidth(110);
            tblGelirCedveli.getColumnModel().getColumn(7).setPreferredWidth(120);
            tblGelirCedveli.getColumnModel().getColumn(9).setPreferredWidth(40);
        }

        jButton5.setText("Bagla");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton4.setText("Ləğv et");

        jButton2.setText("Sil");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Duzelis et");

        jButton1.setText("Update");

        jtableExpenses.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jtableExpenses.setForeground(new java.awt.Color(51, 51, 51));
        jtableExpenses.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "№", "Ad Soyad", "Xercin tipi", "Kommentari", "Tarix", "Ümumi mebleğ"
            }
        ));
        jtableExpenses.setToolTipText("");
        jtableExpenses.setSelectionBackground(new java.awt.Color(0, 255, 0));
        jtableExpenses.setShowVerticalLines(true);
        jScrollPane2.setViewportView(jtableExpenses);
        if (jtableExpenses.getColumnModel().getColumnCount() > 0) {
            jtableExpenses.getColumnModel().getColumn(0).setPreferredWidth(40);
            jtableExpenses.getColumnModel().getColumn(0).setMaxWidth(40);
        }

        jButton8.setText("Geri qaytarilma");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1364, Short.MAX_VALUE)
            .addComponent(jScrollPane2)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 359, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton5)
                        .addComponent(jButton8))
                    .addComponent(jButton1))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(6, 6, 6))
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    public String setDay(int day) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = new GregorianCalendar();
        String ss = sdf.format(cal.getTime());
        cal.add(Calendar.DAY_OF_MONTH, -day);
        String returnedDay = sdf.format(cal.getTime());
        return returnedDay;

    }

    DefaultListModel modelList = new DefaultListModel();

    public void searchForTheCllient() {

        jPanel2.setVisible(true);
        modelList.removeAllElements();
        String s = txtAxtaris.getText();

        try {
            Connection c = connect();
            pres = c.prepareStatement("select * from clients c where c.NameAndSurename like " + "'" + "%" + s + "%" + "'");

            ResultSet rs = pres.executeQuery();

            while (rs.next()) {

                modelList.addElement(rs.getString("NameAndSurename"));
                listNameOfClients.setModel(modelList);
            }

        } catch (Exception ex) {

            ex.printStackTrace();

        }

    }

    public void getTodaysExpenses() {
        int a;
        double netExpense = 0;
        try {
            con = connect();
            pres = con.prepareStatement("select * from expenses where date = CURRENT_DATE");
            rs = pres.executeQuery();

            ResultSet rs = pres.executeQuery();

            ResultSetMetaData rd = rs.getMetaData();
            a = rd.getColumnCount();
            df = (DefaultTableModel) jtableExpenses.getModel();
            df.setRowCount(0);

            while (rs.next()) {
                Vector v2 = new Vector();

                int id = rs.getInt("id");
                String name = rs.getString("name");
                String option = rs.getString("categoryOfExpense");
                String commentary = rs.getString("commentary");
                Date date2 = rs.getDate("date");
                double totalPrice = rs.getDouble("totalExpense");

                v2.add(id);
                v2.add(name);
                v2.add(option);
                v2.add(commentary);
                v2.add(date2);
                v2.add(totalPrice);
                netExpense += totalPrice;
                df.addRow(v2);

            }

            //txtTotalExpenses.setText(Double.toString(netExpense));
        } catch (Exception ex) {

            ex.printStackTrace();

        }

    }

    public void getExpenses(String date) {
        int a;
        double netExpense = 0;
        try {
            con = connect();
            pres = con.prepareStatement("select * from expenses where date = CURRENT_DATE and " + "'" + date + "'");
            rs = pres.executeQuery();

            ResultSet rs = pres.executeQuery();

            ResultSetMetaData rd = rs.getMetaData();
            a = rd.getColumnCount();
            df = (DefaultTableModel) jtableExpenses.getModel();
            df.setRowCount(0);

            while (rs.next()) {
                Vector v2 = new Vector();

                int id = rs.getInt("id");
                String name = rs.getString("name");
                String option = rs.getString("categoryOfExpense");
                String commentary = rs.getString("commentary");
                Date date2 = rs.getDate("date");
                double totalPrice = rs.getDouble("totalExpense");

                v2.add(id);
                v2.add(name);
                v2.add(option);
                v2.add(commentary);
                v2.add(date2);
                v2.add(totalPrice);
                netExpense += totalPrice;
                df.addRow(v2);

            }

            //txtTotalExpenses.setText(Double.toString(netExpense));
        } catch (Exception ex) {

            ex.printStackTrace();

        }

    }


    private void sonUcGunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sonUcGunActionPerformed

        txtPayment.setText("");
        txtAxtaris.setText("");

        int day = 3;
        UmumiSatis.removeAll();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = new GregorianCalendar();
        String ss = sdf.format(cal.getTime());
        cal.add(Calendar.DAY_OF_MONTH, -3);

        String sss = sdf.format(cal.getTime());

        //  System.out.println(ss);
        // System.out.println(sss);
        int a;
        try {

            pres = con.prepareCall("SELECT\n"
                    + "	s.id,\n"
                    + "	s.Malin_adi,\n"
                    + "	s.Miqdari,\n"
                    + "	s.Umumi_Mebleg AS Satis_Meblegi_Cem,\n"
                    + "	s.QiemenOdenis,\n"
                    + "	s.Qaytarilan_Mehsul_Miqdari,\n"
                    + "	s.Musteriye_Geri_Odenis,\n"
                    + "	s.Borc_Alanin_Adi,\n"
                    + "	s.Yeni_goturulen_Mebleg,\n"
                    + "	s.Borcdan_Gelen,\n"
                    + "	s.OdenisinNovu,\n"
                    + "	s.cekNomresi,\n"
                    + "	s.ActiveUser,\n"
                    + "	s.Satis_ID,\n"
                    + " m.Satis_miqdari, \n"
                    + "	m.Alis_qiymeti,\n"
                    + " m.Satisin_toplam_deyeri, \n"
                    + "	s.Satis_qiymeti,\n"
                    + "	m.Satis_miqdari AS Umumi_Satis_Miqdari,\n"
                    + "	s.Satis_Tarixi,\n"
                    + "	( s.Miqdari * s.Satis_qiymeti - s.Miqdari * m.Alis_qiymeti ) AS Xeyir, (m.Satisin_toplam_deyeri - m.Alis_qiymeti* m.Satis_miqdari) as Umumi_Xeyir \n"
                    + "FROM\n"
                    + "	satilan_mallar s\n"
                    + "	LEFT JOIN mehsullar m ON m.id = s.id \n"
                    + "WHERE\n"
                    + "	DATE(s.Satis_Tarixi) BETWEEN" + "'" + sss + "'" + "and CURRENT_DATE");

            ResultSet rs = pres.executeQuery();

            ResultSetMetaData rd = rs.getMetaData();
            a = rd.getColumnCount();
            df = (DefaultTableModel) tblGelirCedveli.getModel();
            df.setRowCount(0);

            while (rs.next()) {
                Vector v2 = new Vector();
                for (int i = 0; i < a; i++) {
                    v2.add(rs.getInt("id"));
                    v2.add(rs.getString("Malin_adi"));
                    v2.add(rs.getString("OdenisinNovu"));
                    v2.add(rs.getDouble("Miqdari"));
                    v2.add(rs.getDouble("Satis_Meblegi_Cem"));
                    v2.add(rs.getDouble("Alis_qiymeti"));
                    v2.add(rs.getDouble("Satis_qiymeti"));
                    v2.add(rs.getInt("Umumi_Satis_Miqdari"));
                    v2.add(rs.getString("Satis_Tarixi"));
                    v2.add(rs.getDouble("Xeyir"));
                    v2.add(rs.getDouble("Umumi_Xeyir"));
                    v2.add(rs.getDouble("QiemenOdenis"));
                    v2.add(rs.getDouble("Qaytarilan_Mehsul_Miqdari"));
                    v2.add(rs.getDouble("Musteriye_Geri_Odenis"));
                    v2.add(rs.getString("Borc_Alanin_Adi"));
                    v2.add(rs.getDouble("Yeni_goturulen_Mebleg"));
                    v2.add(rs.getDouble("Borcdan_Gelen"));
                    v2.add(rs.getString("ActiveUser"));
                    v2.add(rs.getFloat("cekNomresi"));
                    v2.add(rs.getFloat("Satis_ID"));
                }
                df.addRow(v2);

            }

            //getCapitalBudget();
            getExpenses(day);
            //indecators();

        } catch (Exception ex) {
            ex.printStackTrace();

        }


    }//GEN-LAST:event_sonUcGunActionPerformed

    public void getInfoAboutClientsPaymentForLastThreeDays() {

        String nameClient = txtAxtaris.getText();
        double qismenOdenis = 0;
        double allpartialPayment = 0;

        int day = 3;
        UmumiSatis.removeAll();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = new GregorianCalendar();
        String ss = sdf.format(cal.getTime());
        cal.add(Calendar.DAY_OF_MONTH, -3);

        String sss = sdf.format(cal.getTime());

        //  System.out.println(ss);
        // System.out.println(sss);
        int a;
        try {

            pres = con.prepareCall("SELECT\n"
                    + "	s.id,\n"
                    + "	s.Malin_adi,\n"
                    + "	s.Miqdari,\n"
                    + "	s.Umumi_Mebleg AS Satis_Meblegi_Cem,\n"
                    + "	s.QiemenOdenis,\n"
                    + "	s.Qaytarilan_Mehsul_Miqdari,\n"
                    + "	s.Musteriye_Geri_Odenis,\n"
                    + "	s.Borc_Alanin_Adi,\n"
                    + "	s.Yeni_goturulen_Mebleg,\n"
                    + "	s.Borcdan_Gelen,\n"
                    + "	s.OdenisinNovu,\n"
                    + "	s.cekNomresi,\n"
                    + "	s.ActiveUser,\n"
                    + " m.Satis_miqdari, \n"
                    + "	m.Alis_qiymeti,\n"
                    + " m.Satisin_toplam_deyeri, \n"
                    + "	s.Satis_qiymeti,\n"
                    + "	m.Satis_miqdari AS Umumi_Satis_Miqdari,\n"
                    + "	s.Satis_Tarixi,\n"
                    + "	( s.Miqdari * s.Satis_qiymeti - s.Miqdari * m.Alis_qiymeti ) AS Xeyir, (m.Satisin_toplam_deyeri - m.Alis_qiymeti* m.Satis_miqdari) as Umumi_Xeyir \n"
                    + "FROM\n"
                    + "	satilan_mallar s\n"
                    + "	LEFT JOIN mehsullar m ON m.id = s.id \n"
                    + "WHERE\n"
                    + "	DATE(s.Satis_Tarixi) BETWEEN" + "'" + sss + "'" + "and CURRENT_DATE" + " and s.Borc_Alanin_Adi = " + "'" + nameClient + "'");

            ResultSet rs = pres.executeQuery();

            ResultSetMetaData rd = rs.getMetaData();
            a = rd.getColumnCount();
            df = (DefaultTableModel) tblGelirCedveli.getModel();
            df.setRowCount(0);

            while (rs.next()) {
                Vector v2 = new Vector();
                v2.add(rs.getInt("id"));
                v2.add(rs.getString("Malin_adi"));
                v2.add(rs.getString("OdenisinNovu"));
                v2.add(rs.getDouble("Miqdari"));
                v2.add(rs.getDouble("Satis_Meblegi_Cem"));
                v2.add(rs.getDouble("Alis_qiymeti"));
                v2.add(rs.getDouble("Satis_qiymeti"));
                v2.add(rs.getInt("Umumi_Satis_Miqdari"));
                v2.add(rs.getString("Satis_Tarixi"));
                v2.add(rs.getDouble("Xeyir"));
                v2.add(rs.getDouble("Umumi_Xeyir"));
                v2.add(rs.getDouble("QiemenOdenis"));
                v2.add(rs.getDouble("Qaytarilan_Mehsul_Miqdari"));
                v2.add(rs.getDouble("Musteriye_Geri_Odenis"));
                v2.add(rs.getString("Borc_Alanin_Adi"));
                v2.add(rs.getDouble("Yeni_goturulen_Mebleg"));
                v2.add(rs.getDouble("Borcdan_Gelen"));
                v2.add(rs.getString("ActiveUser"));
                v2.add(rs.getFloat("cekNomresi"));

                qismenOdenis = (rs.getDouble("QiemenOdenis"));
                allpartialPayment += qismenOdenis;

                df.addRow(v2);
            }

            txtPayment.setText(Double.toString(allpartialPayment));

        } catch (Exception ex) {
            ex.printStackTrace();

        }

    }


    private void rbBugunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbBugunActionPerformed

        txtPayment.setText("");
        txtAxtaris.setText("");

        int a;
        try {

            pres = con.prepareCall("SELECT\n"
                    + "	s.id,\n"
                    + "	s.Malin_adi,\n"
                    + "	s.Miqdari,\n"
                    + "	s.Umumi_Mebleg AS Satis_Meblegi_Cem,\n"
                    + "	s.QiemenOdenis,\n"
                    + "	s.Qaytarilan_Mehsul_Miqdari,\n"
                    + "	s.Musteriye_Geri_Odenis,\n"
                    + "	s.Borc_Alanin_Adi,\n"
                    + "	s.Yeni_goturulen_Mebleg,\n"
                    + "	s.Borcdan_Gelen,\n"
                    + "	s.Satis_ID,\n"
                    + "	s.BorcID,\n"
                    + " m.Satis_miqdari, \n"
                    + "	m.Alis_qiymeti,\n"
                    + " m.Satisin_toplam_deyeri, \n"
                    + "	s.Satis_qiymeti,\n"
                    + "	s.OdenisinNovu,\n"
                    + "	s.cekNomresi,\n"
                    + "	s.ActiveUser,\n"
                    + "	m.Satis_miqdari AS Umumi_Satis_Miqdari,\n"
                    + "	s.Satis_Tarixi,\n"
                    + "	( s.Miqdari * s.Satis_qiymeti - s.Miqdari * m.Alis_qiymeti ) AS Xeyir, (m.Satisin_toplam_deyeri - m.Alis_qiymeti* m.Satis_miqdari) as Umumi_Xeyir \n"
                    + "FROM\n"
                    + "	satilan_mallar s\n"
                    + "	LEFT JOIN mehsullar m ON m.id = s.id \n"
                    + "WHERE Date (s.Satis_Tarixi) = CURRENT_DATE");

            ResultSet rs = pres.executeQuery();

            ResultSetMetaData rd = rs.getMetaData();
            a = rd.getColumnCount();
            df = (DefaultTableModel) tblGelirCedveli.getModel();
            df.setRowCount(0);

            while (rs.next()) {
                Vector v2 = new Vector();
                for (int i = 0; i < a; i++) {
                    v2.add(rs.getInt("id"));
                    v2.add(rs.getString("Malin_adi"));
                    v2.add(rs.getString("OdenisinNovu"));
                    v2.add(rs.getDouble("Miqdari"));
                    v2.add(rs.getDouble("Satis_Meblegi_Cem"));
                    v2.add(rs.getDouble("Alis_qiymeti"));
                    v2.add(rs.getDouble("Satis_qiymeti"));
                    v2.add(rs.getInt("Umumi_Satis_Miqdari"));
                    v2.add(rs.getString("Satis_Tarixi"));
                    v2.add(rs.getDouble("Xeyir"));
                    v2.add(rs.getDouble("Umumi_Xeyir"));
                    v2.add(rs.getDouble("QiemenOdenis"));
                    v2.add(rs.getDouble("Qaytarilan_Mehsul_Miqdari"));
                    v2.add(rs.getDouble("Musteriye_Geri_Odenis"));
                    v2.add(rs.getString("Borc_Alanin_Adi"));
                    v2.add(rs.getDouble("Yeni_goturulen_Mebleg"));
                    v2.add(rs.getDouble("Borcdan_Gelen"));
                    v2.add(rs.getString("ActiveUser"));
                    v2.add(rs.getFloat("cekNomresi"));
                    v2.add(rs.getFloat("Satis_ID"));
                    v2.add(rs.getInt("BorcID"));

                }
                df.addRow(v2);

            }

            //getCapitalBudget();
            getTodaysExpenses();
            //indecators();

        } catch (SQLException ex) {
            ex.printStackTrace();

        }

    }//GEN-LAST:event_rbBugunActionPerformed

    public void getInfoAboutClientsPaymentForToday() {

        String nameClient = txtAxtaris.getText();
        double qismenOdenis = 0;
        double allpartialPayment = 0;

        int a;
        try {

            pres = con.prepareCall("SELECT\n"
                    + "	s.id,\n"
                    + "	s.Malin_adi,\n"
                    + "	s.Miqdari,\n"
                    + "	s.Umumi_Mebleg AS Satis_Meblegi_Cem,\n"
                    + "	s.QiemenOdenis,\n"
                    + "	s.Qaytarilan_Mehsul_Miqdari,\n"
                    + "	s.Musteriye_Geri_Odenis,\n"
                    + "	s.Borc_Alanin_Adi,\n"
                    + "	s.Yeni_goturulen_Mebleg,\n"
                    + "	s.Borcdan_Gelen,\n"
                    + "	s.OdenisinNovu,\n"
                    + "	s.cekNomresi,\n"
                    + "	s.ActiveUser,\n"
                    + " m.Satis_miqdari, \n"
                    + "	m.Alis_qiymeti,\n"
                    + " m.Satisin_toplam_deyeri, \n"
                    + "	s.Satis_qiymeti,\n"
                    + "	m.Satis_miqdari AS Umumi_Satis_Miqdari,\n"
                    + "	s.Satis_Tarixi,\n"
                    + "	( s.Miqdari * s.Satis_qiymeti - s.Miqdari * m.Alis_qiymeti ) AS Xeyir, (m.Satisin_toplam_deyeri - m.Alis_qiymeti* m.Satis_miqdari) as Umumi_Xeyir \n"
                    + "FROM\n"
                    + "	satilan_mallar s\n"
                    + "	LEFT JOIN mehsullar m ON m.id = s.id \n"
                    + "WHERE Date (s.Satis_Tarixi) = CURRENT_DATE and s.Borc_Alanin_Adi = " + "'" + nameClient + "'");

            ResultSet rs = pres.executeQuery();

            ResultSetMetaData rd = rs.getMetaData();
            a = rd.getColumnCount();
            df = (DefaultTableModel) tblGelirCedveli.getModel();
            df.setRowCount(0);

            while (rs.next()) {
                Vector v2 = new Vector();
                v2.add(rs.getInt("id"));
                v2.add(rs.getString("Malin_adi"));
                v2.add(rs.getString("OdenisinNovu"));
                v2.add(rs.getDouble("Miqdari"));
                v2.add(rs.getDouble("Satis_Meblegi_Cem"));
                v2.add(rs.getDouble("Alis_qiymeti"));
                v2.add(rs.getDouble("Satis_qiymeti"));
                v2.add(rs.getInt("Umumi_Satis_Miqdari"));
                v2.add(rs.getString("Satis_Tarixi"));
                v2.add(rs.getDouble("Xeyir"));
                v2.add(rs.getDouble("Umumi_Xeyir"));
                v2.add(rs.getDouble("QiemenOdenis"));
                v2.add(rs.getDouble("Qaytarilan_Mehsul_Miqdari"));
                v2.add(rs.getDouble("Musteriye_Geri_Odenis"));
                v2.add(rs.getString("Borc_Alanin_Adi"));
                v2.add(rs.getDouble("Yeni_goturulen_Mebleg"));
                v2.add(rs.getDouble("Borcdan_Gelen"));
                v2.add(rs.getString("ActiveUser"));
                v2.add(rs.getFloat("cekNomresi"));

                qismenOdenis = (rs.getDouble("QiemenOdenis"));
                allpartialPayment += qismenOdenis;

                df.addRow(v2);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();

        }

        txtPayment.setText(Double.toString(allpartialPayment));

    }

    public void indecators() {

        try {

            if (!UmumiSatis.getText().isEmpty() || txtTotalExpenses.getText().isEmpty()) {

                double profit = Double.parseDouble(UmumiSatis.getText());
                double expenses = Double.parseDouble(txtTotalExpenses.getText());

                double result = profit - expenses;
                double roundedResult = Math.round(result * 100.000) / 100.000;
                txtTotalProfit.setText(Double.toString(roundedResult));

            }

        } catch (Exception ex) {
            ex.printStackTrace();

        }

    }

    public void getExpenses(int day) {

        String retunedDay = setDay(day);
        getExpenses(retunedDay);

    }

    public void getCapitalBudget() {

        try {

            pres = con.prepareStatement("select * from capitalbudget");
            ResultSet rs3 = pres.executeQuery();
            rs3.next();

            double capitalBudget = rs3.getDouble("AmountOfCapitalBudget");
            double roundedDouble = Math.round(capitalBudget * 100.000) / 100.000;
            txtCapitalBudget.setText(Double.toString(roundedDouble));

            pres = con.prepareStatement("select * from updatedCapitalbudget order by id desc limit 1");
            ResultSet rs4 = pres.executeQuery();
            while (rs4.next()) {
                double UpdatedcapitalBudget = rs4.getDouble("AmountOfCapitalBudget");
                double roundedUpdatedBudget = Math.round(UpdatedcapitalBudget * 100.000) / 100.000;
                txtTotalBudget.setText(Double.toString(roundedUpdatedBudget));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


    private void sonBirHefteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sonBirHefteActionPerformed

        txtPayment.setText("");
        txtAxtaris.setText("");
        int day = 7;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = new GregorianCalendar();
        cal.add(Calendar.DAY_OF_MONTH, -7);
        String sss = sdf.format(cal.getTime());

        // txtKassa.removeAll();
        // System.out.println(sss);
        int a;
        try {

            pres = con.prepareCall("SELECT\n"
                    + "	s.id,\n"
                    + "	s.Malin_adi,\n"
                    + "	s.Miqdari,\n"
                    + "	s.Umumi_Mebleg AS Satis_Meblegi_Cem,\n"
                    + "	s.QiemenOdenis,\n"
                    + "	s.Qaytarilan_Mehsul_Miqdari,\n"
                    + "	s.Musteriye_Geri_Odenis,\n"
                    + "	s.Borc_Alanin_Adi,\n"
                    + "	s.Yeni_goturulen_Mebleg,\n"
                    + "	s.Borcdan_Gelen,\n"
                    + "	s.OdenisinNovu,\n"
                    + "	s.cekNomresi,\n"
                    + "	s.ActiveUser,\n"
                    + "	s.Satis_ID,\n"
                    + " m.Satis_miqdari, \n"
                    + "	m.Alis_qiymeti,\n"
                    + " m.Satisin_toplam_deyeri, \n"
                    + "	s.Satis_qiymeti,\n"
                    + "	m.Satis_miqdari AS Umumi_Satis_Miqdari,\n"
                    + "	s.Satis_Tarixi,\n"
                    + "	( s.Miqdari * s.Satis_qiymeti - s.Miqdari * m.Alis_qiymeti ) AS Xeyir, (m.Satisin_toplam_deyeri - m.Alis_qiymeti* m.Satis_miqdari) as Umumi_Xeyir \n"
                    + "FROM\n"
                    + "	satilan_mallar s\n"
                    + "	LEFT JOIN mehsullar m ON m.id = s.id \n"
                    + "WHERE\n"
                    + "	s.Satis_Tarixi BETWEEN" + "'" + sss + "'" + "and CURRENT_DATE");

            ResultSet rs = pres.executeQuery();

            ResultSetMetaData rd = rs.getMetaData();
            a = rd.getColumnCount();
            df = (DefaultTableModel) tblGelirCedveli.getModel();
            df.setRowCount(0);

            while (rs.next()) {
                Vector v2 = new Vector();
                for (int i = 0; i < a; i++) {
                    v2.add(rs.getInt("id"));
                    v2.add(rs.getString("Malin_adi"));
                    v2.add(rs.getString("OdenisinNovu"));
                    v2.add(rs.getDouble("Miqdari"));
                    v2.add(rs.getDouble("Satis_Meblegi_Cem"));
                    v2.add(rs.getDouble("Alis_qiymeti"));
                    v2.add(rs.getDouble("Satis_qiymeti"));
                    v2.add(rs.getInt("Umumi_Satis_Miqdari"));
                    v2.add(rs.getString("Satis_Tarixi"));
                    v2.add(rs.getDouble("Xeyir"));
                    v2.add(rs.getDouble("Umumi_Xeyir"));
                    v2.add(rs.getDouble("QiemenOdenis"));
                    v2.add(rs.getDouble("Qaytarilan_Mehsul_Miqdari"));
                    v2.add(rs.getDouble("Musteriye_Geri_Odenis"));
                    v2.add(rs.getString("Borc_Alanin_Adi"));
                    v2.add(rs.getDouble("Yeni_goturulen_Mebleg"));
                    v2.add(rs.getDouble("Borcdan_Gelen"));
                    v2.add(rs.getString("ActiveUser"));
                    v2.add(rs.getFloat("cekNomresi"));
                    v2.add(rs.getFloat("Satis_ID"));
                }
                df.addRow(v2);

            }

            //getCapitalBudget();
            getExpenses(day);
            //indecators();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }//GEN-LAST:event_sonBirHefteActionPerformed

    public void getInfoAboutClientsPaymentForLastWeek() {

        String nameClient = txtAxtaris.getText();
        double qismenOdenis = 0;
        double allpartialPayment = 0;

        int day = 7;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = new GregorianCalendar();
        cal.add(Calendar.DAY_OF_MONTH, -7);
        String sss = sdf.format(cal.getTime());

        // txtKassa.removeAll();
        // System.out.println(sss);
        int a;
        try {

            pres = con.prepareCall("SELECT\n"
                    + "	s.id,\n"
                    + "	s.Malin_adi,\n"
                    + "	s.Miqdari,\n"
                    + "	s.Umumi_Mebleg AS Satis_Meblegi_Cem,\n"
                    + "	s.QiemenOdenis,\n"
                    + "	s.Qaytarilan_Mehsul_Miqdari,\n"
                    + "	s.Musteriye_Geri_Odenis,\n"
                    + "	s.Borc_Alanin_Adi,\n"
                    + "	s.Yeni_goturulen_Mebleg,\n"
                    + "	s.Borcdan_Gelen,\n"
                    + "	s.OdenisinNovu,\n"
                    + "	s.cekNomresi,\n"
                    + "	s.ActiveUser,\n"
                    + " m.Satis_miqdari, \n"
                    + "	m.Alis_qiymeti,\n"
                    + " m.Satisin_toplam_deyeri, \n"
                    + "	s.Satis_qiymeti,\n"
                    + "	m.Satis_miqdari AS Umumi_Satis_Miqdari,\n"
                    + "	s.Satis_Tarixi,\n"
                    + "	( s.Miqdari * s.Satis_qiymeti - s.Miqdari * m.Alis_qiymeti ) AS Xeyir, (m.Satisin_toplam_deyeri - m.Alis_qiymeti* m.Satis_miqdari) as Umumi_Xeyir \n"
                    + "FROM\n"
                    + "	satilan_mallar s\n"
                    + "	LEFT JOIN mehsullar m ON m.id = s.id \n"
                    + "WHERE\n"
                    + "	DATE(s.Satis_Tarixi) BETWEEN" + "'" + sss + "'" + "and CURRENT_DATE and s.Borc_Alanin_Adi = " + "'" + nameClient + "'");

            ResultSet rs = pres.executeQuery();

            ResultSetMetaData rd = rs.getMetaData();
            a = rd.getColumnCount();
            df = (DefaultTableModel) tblGelirCedveli.getModel();
            df.setRowCount(0);

            while (rs.next()) {
                Vector v2 = new Vector();

                v2.add(rs.getInt("id"));
                v2.add(rs.getString("Malin_adi"));
                v2.add(rs.getString("OdenisinNovu"));
                v2.add(rs.getDouble("Miqdari"));
                v2.add(rs.getDouble("Satis_Meblegi_Cem"));
                v2.add(rs.getDouble("Alis_qiymeti"));
                v2.add(rs.getDouble("Satis_qiymeti"));
                v2.add(rs.getInt("Umumi_Satis_Miqdari"));
                v2.add(rs.getString("Satis_Tarixi"));
                v2.add(rs.getDouble("Xeyir"));
                v2.add(rs.getDouble("Umumi_Xeyir"));
                v2.add(rs.getDouble("QiemenOdenis"));
                v2.add(rs.getDouble("Qaytarilan_Mehsul_Miqdari"));
                v2.add(rs.getDouble("Musteriye_Geri_Odenis"));
                v2.add(rs.getString("Borc_Alanin_Adi"));
                v2.add(rs.getDouble("Yeni_goturulen_Mebleg"));
                v2.add(rs.getDouble("Borcdan_Gelen"));
                v2.add(rs.getString("ActiveUser"));
                v2.add(rs.getFloat("cekNomresi"));

                qismenOdenis = (rs.getDouble("QiemenOdenis"));
                allpartialPayment += qismenOdenis;

                df.addRow(v2);
            }

            txtPayment.setText(Double.toString(allpartialPayment));

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


    private void sonBirAyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sonBirAyActionPerformed

        txtPayment.setText("");
        txtAxtaris.setText("");
        int day = 30;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = new GregorianCalendar();
        cal.add(Calendar.DAY_OF_MONTH, -30);
        String sss = sdf.format(cal.getTime());

        // System.out.println(sss);
        int a;
        try {

            pres = con.prepareCall("SELECT\n"
                    + "	s.id,\n"
                    + "	s.Malin_adi,\n"
                    + "	s.Miqdari,\n"
                    + "	s.Umumi_Mebleg AS Satis_Meblegi_Cem,\n"
                    + "	s.QiemenOdenis,\n"
                    + "	s.Qaytarilan_Mehsul_Miqdari,\n"
                    + "	s.Musteriye_Geri_Odenis,\n"
                    + "	s.Borc_Alanin_Adi,\n"
                    + "	s.Yeni_goturulen_Mebleg,\n"
                    + "	s.Borcdan_Gelen,\n"
                    + "	s.Satis_ID,\n"
                    + " m.Satis_miqdari, \n"
                    + "	m.Alis_qiymeti,\n"
                    + " m.Satisin_toplam_deyeri, \n"
                    + "	s.Satis_qiymeti,\n"
                    + "	s.OdenisinNovu,\n"
                    + "	s.cekNomresi,\n"
                    + "	s.ActiveUser,\n"
                    + "	m.Satis_miqdari AS Umumi_Satis_Miqdari,\n"
                    + "	s.Satis_Tarixi,\n"
                    + "	( s.Miqdari * s.Satis_qiymeti - s.Miqdari * m.Alis_qiymeti ) AS Xeyir, (m.Satisin_toplam_deyeri - m.Alis_qiymeti* m.Satis_miqdari) as Umumi_Xeyir \n"
                    + "FROM\n"
                    + "	satilan_mallar s\n"
                    + "	LEFT JOIN mehsullar m ON m.id = s.id \n"
                    + "WHERE\n"
                    + "	s.Satis_Tarixi BETWEEN" + "'" + sss + "'" + "and CURRENT_DATE");

            ResultSet rs = pres.executeQuery();

            ResultSetMetaData rd = rs.getMetaData();
            a = rd.getColumnCount();
            df = (DefaultTableModel) tblGelirCedveli.getModel();
            df.setRowCount(0);

            while (rs.next()) {
                Vector v2 = new Vector();
                for (int i = 0; i < a; i++) {
                    v2.add(rs.getInt("id"));
                    v2.add(rs.getString("Malin_adi"));
                    v2.add(rs.getString("OdenisinNovu"));
                    v2.add(rs.getDouble("Miqdari"));
                    v2.add(rs.getDouble("Satis_Meblegi_Cem"));
                    v2.add(rs.getDouble("Alis_qiymeti"));
                    v2.add(rs.getDouble("Satis_qiymeti"));
                    v2.add(rs.getInt("Umumi_Satis_Miqdari"));
                    v2.add(rs.getString("Satis_Tarixi"));
                    v2.add(rs.getDouble("Xeyir"));
                    v2.add(rs.getDouble("Umumi_Xeyir"));
                    v2.add(rs.getDouble("QiemenOdenis"));
                    v2.add(rs.getDouble("Qaytarilan_Mehsul_Miqdari"));
                    v2.add(rs.getDouble("Musteriye_Geri_Odenis"));
                    v2.add(rs.getString("Borc_Alanin_Adi"));
                    v2.add(rs.getDouble("Yeni_goturulen_Mebleg"));
                    v2.add(rs.getDouble("Borcdan_Gelen"));
                    v2.add(rs.getString("ActiveUser"));
                    v2.add(rs.getFloat("cekNomresi"));
                    v2.add(rs.getFloat("Satis_ID"));
                }
                df.addRow(v2);

            }

            //getCapitalBudget();
            getExpenses(day);
            //indecators();

        } catch (Exception ex) {
            ex.printStackTrace();

        }

    }//GEN-LAST:event_sonBirAyActionPerformed

    public void getInfoAboutClientsPaymentForTheLastMonth() {

        String nameClient = txtAxtaris.getText();
        double qismenOdenis = 0;
        double allpartialPayment = 0;

        int day = 30;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = new GregorianCalendar();
        cal.add(Calendar.DAY_OF_MONTH, -30);
        String sss = sdf.format(cal.getTime());

        // System.out.println(sss);
        int a;
        try {

            pres = con.prepareCall("SELECT\n"
                    + "	s.id,\n"
                    + "	s.Malin_adi,\n"
                    + "	s.Miqdari,\n"
                    + "	s.Umumi_Mebleg AS Satis_Meblegi_Cem,\n"
                    + "	s.QiemenOdenis,\n"
                    + "	s.Qaytarilan_Mehsul_Miqdari,\n"
                    + "	s.Musteriye_Geri_Odenis,\n"
                    + "	s.Borc_Alanin_Adi,\n"
                    + "	s.Yeni_goturulen_Mebleg,\n"
                    + "	s.Borcdan_Gelen,\n"
                    + "	s.OdenisinNovu,\n"
                    + "	s.cekNomresi,\n"
                    + "	s.ActiveUser,\n"
                    + " m.Satis_miqdari, \n"
                    + "	m.Alis_qiymeti,\n"
                    + " m.Satisin_toplam_deyeri, \n"
                    + "	s.Satis_qiymeti,\n"
                    + "	m.Satis_miqdari AS Umumi_Satis_Miqdari,\n"
                    + "	s.Satis_Tarixi,\n"
                    + "	( s.Miqdari * s.Satis_qiymeti - s.Miqdari * m.Alis_qiymeti ) AS Xeyir, (m.Satisin_toplam_deyeri - m.Alis_qiymeti* m.Satis_miqdari) as Umumi_Xeyir \n"
                    + "FROM\n"
                    + "	satilan_mallar s\n"
                    + "	LEFT JOIN mehsullar m ON m.id = s.id \n"
                    + "WHERE\n"
                    + "	DATE(s.Satis_Tarixi) BETWEEN " + "'" + sss + "'" + " and CURRENT_DATE and Borc_Alanin_Adi = " + "'" + nameClient + "'");

            ResultSet rs = pres.executeQuery();

            ResultSetMetaData rd = rs.getMetaData();
            a = rd.getColumnCount();
            df = (DefaultTableModel) tblGelirCedveli.getModel();
            df.setRowCount(0);

            while (rs.next()) {
                Vector v2 = new Vector();
                v2.add(rs.getInt("id"));
                v2.add(rs.getString("Malin_adi"));
                v2.add(rs.getString("OdenisinNovu"));
                v2.add(rs.getDouble("Miqdari"));
                v2.add(rs.getDouble("Satis_Meblegi_Cem"));
                v2.add(rs.getDouble("Alis_qiymeti"));
                v2.add(rs.getDouble("Satis_qiymeti"));
                v2.add(rs.getInt("Umumi_Satis_Miqdari"));
                v2.add(rs.getString("Satis_Tarixi"));
                v2.add(rs.getDouble("Xeyir"));
                v2.add(rs.getDouble("Umumi_Xeyir"));
                v2.add(rs.getDouble("QiemenOdenis"));
                v2.add(rs.getDouble("Qaytarilan_Mehsul_Miqdari"));
                v2.add(rs.getDouble("Musteriye_Geri_Odenis"));
                v2.add(rs.getString("Borc_Alanin_Adi"));
                v2.add(rs.getDouble("Yeni_goturulen_Mebleg"));
                v2.add(rs.getDouble("Borcdan_Gelen"));
                v2.add(rs.getString("ActiveUser"));
                v2.add(rs.getFloat("cekNomresi"));

                qismenOdenis = (rs.getDouble("QiemenOdenis"));
                allpartialPayment += qismenOdenis;

                df.addRow(v2);
            }

            txtPayment.setText(Double.toString(allpartialPayment));

        } catch (Exception ex) {
            ex.printStackTrace();

        }

    }

    public void secilenTarixler() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        Calendar cal = new GregorianCalendar();
//        cal.add(Calendar.DAY_OF_MONTH, -30);
//        String sss = sdf.format(cal.getTime());

        Date birinciTarix = ilkTarix.getDate();
        Date ikinciTarix = sonTarix.getDate();
        String sss = sdf.format(ilkTarix.getDate());
        String sss2 = sdf.format(sonTarix.getDate());

        System.out.println(sss);
        System.out.println(sss2);

        UmumiSatis.removeAll();
        // System.out.println(sss);
        int a;
        try {

            pres = con.prepareCall("SELECT\n"
                    + "	s.id,\n"
                    + "	s.Malin_adi,\n"
                    + "	s.Miqdari,\n"
                    + "	s.Umumi_Mebleg AS Satis_Meblegi_Cem,\n"
                    + "	s.QiemenOdenis,\n"
                    + "	s.Qaytarilan_Mehsul_Miqdari,\n"
                    + "	s.Musteriye_Geri_Odenis,\n"
                    + "	s.Borc_Alanin_Adi,\n"
                    + "	s.Yeni_goturulen_Mebleg,\n"
                    + "	s.Borcdan_Gelen,\n"
                    + "	s.OdenisinNovu,\n"
                    + "	s.cekNomresi,\n"
                    + "	s.ActiveUser,\n"
                    + "	s.Satis_ID,\n"
                    + " m.Satis_miqdari, \n"
                    + "	m.Alis_qiymeti,\n"
                    + " m.Satisin_toplam_deyeri, \n"
                    + "	s.Satis_qiymeti,\n"
                    + "	m.Satis_miqdari AS Umumi_Satis_Miqdari,\n"
                    + "	s.Satis_Tarixi,\n"
                    + "	( s.Miqdari * s.Satis_qiymeti - s.Miqdari * m.Alis_qiymeti ) AS Xeyir, (m.Satisin_toplam_deyeri - m.Alis_qiymeti* m.Satis_miqdari) as Umumi_Xeyir \n"
                    + "FROM\n"
                    + "	satilan_mallar s\n"
                    + "	LEFT JOIN mehsullar m ON m.id = s.id \n"
                    + "WHERE\n"
                    + "	s.Satis_Tarixi BETWEEN" + "'" + sss + "'" + "and" + "'" + sss2 + "'");

            ResultSet rs = pres.executeQuery();

            ResultSetMetaData rd = rs.getMetaData();
            a = rd.getColumnCount();
            df = (DefaultTableModel) tblGelirCedveli.getModel();
            df.setRowCount(0);

            while (rs.next()) {
                Vector v2 = new Vector();
                for (int i = 0; i < a; i++) {
                    v2.add(rs.getInt("id"));
                    v2.add(rs.getString("Malin_adi"));
                    v2.add(rs.getString("OdenisinNovu"));
                    v2.add(rs.getDouble("Miqdari"));
                    v2.add(rs.getDouble("Satis_Meblegi_Cem"));
                    v2.add(rs.getDouble("Alis_qiymeti"));
                    v2.add(rs.getDouble("Satis_qiymeti"));
                    v2.add(rs.getInt("Umumi_Satis_Miqdari"));
                    v2.add(rs.getString("Satis_Tarixi"));
                    v2.add(rs.getDouble("Xeyir"));
                    v2.add(rs.getDouble("Umumi_Xeyir"));
                    v2.add(rs.getDouble("QiemenOdenis"));
                    v2.add(rs.getDouble("Qaytarilan_Mehsul_Miqdari"));
                    v2.add(rs.getDouble("Musteriye_Geri_Odenis"));
                    v2.add(rs.getString("Borc_Alanin_Adi"));
                    v2.add(rs.getDouble("Yeni_goturulen_Mebleg"));
                    v2.add(rs.getDouble("Borcdan_Gelen"));
                    v2.add(rs.getString("ActiveUser"));
                    v2.add(rs.getFloat("cekNomresi"));
                    v2.add(rs.getFloat("Satis_ID"));
                }
                df.addRow(v2);

            }

        } catch (Exception ex) {
            ex.printStackTrace();

        }

    }

    private void btnHesablaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHesablaMouseClicked

        calculateAll();

    }//GEN-LAST:event_btnHesablaMouseClicked

    public void calculateAll() {

        getCapitalBudget();
        UmumiSatis.setText("");
        txtTotalProfit.setText("");
        DecimalFormat dformater = new DecimalFormat("#.##");

        df = (DefaultTableModel) tblGelirCedveli.getModel();

        Double SatisQiymeti, borcdanGelen, Gelir, Miqdari, musteriyeGeriQaytarilanMebleg, qismenOdenis, GeriQaytarilanMehsulunMebleg;

        for (int i = 0; i < df.getRowCount(); i++) {

            Gelir = Double.parseDouble(df.getValueAt(i, 9).toString());

            String formattedGelir = dformater.format(Gelir);
            double roundedGelir = Math.round(Gelir * 100.000) / 100.000;

            boolean yoxla = UmumiSatis.getText().isEmpty();

            if (yoxla != false) {
                UmumiSatis.setText(Double.toString(roundedGelir));
            } else {

                double kohneMebleg = Double.parseDouble(UmumiSatis.getText());

                String formattedKohneMebleg = dformater.format(kohneMebleg);
                double roundedMebleg = Math.round(kohneMebleg * 100.000) / 100.000;

                double netice = roundedMebleg + roundedGelir;

                String formatterNetice = dformater.format(netice);
                double roundedNetice = Math.round(netice * 100.000) / 100.000;

                UmumiSatis.setText(Double.toString(roundedNetice));
            }

        }
        txtKassa.setText("");
        for (int i = 0; i < df.getRowCount(); i++) {

            SatisQiymeti = Double.parseDouble(df.getValueAt(i, 6).toString());
            Miqdari = Double.parseDouble(df.getValueAt(i, 3).toString());
            musteriyeGeriQaytarilanMebleg = Double.parseDouble(df.getValueAt(i, 13).toString());
            GeriQaytarilanMehsulunMebleg = Double.parseDouble(df.getValueAt(i, 15).toString());
            qismenOdenis = Double.parseDouble(df.getValueAt(i, 11).toString());
            borcdanGelen = Double.parseDouble(df.getValueAt(i, 16).toString());

            boolean yoxla1 = txtKassa.getText().isEmpty();

            if (yoxla1 != false) {

                double umumiSatis = SatisQiymeti * Miqdari - musteriyeGeriQaytarilanMebleg + qismenOdenis - (borcdanGelen);

                String formatted = dformater.format(umumiSatis);
                double roundedGelir = Math.round(umumiSatis * 100.000) / 100.000;

                txtKassa.setText(Double.toString(roundedGelir));

            } else {

                double kohneMebleg = Double.parseDouble(txtKassa.getText());
                double umumiSatis = SatisQiymeti * Miqdari;

                if (umumiSatis == qismenOdenis) {
                    double sonNetice = kohneMebleg + umumiSatis - GeriQaytarilanMehsulunMebleg - borcdanGelen + qismenOdenis;
                    String formatted = dformater.format(sonNetice);
                    double roundedSonNetice = Math.round(sonNetice * 100.000) / 100.000;
                    txtKassa.setText(Double.toString(roundedSonNetice));

                } else {

                    double sonNetice = kohneMebleg + umumiSatis - GeriQaytarilanMehsulunMebleg + qismenOdenis - borcdanGelen;
                    String formatted = dformater.format(sonNetice);
                    double roundedSonNetice = Math.round(sonNetice * 100.000) / 100.000;
                    txtKassa.setText(Double.toString(roundedSonNetice));
                }

            }

        }

        getExpensesIndecator();
        indecators();

    }

    public void getExpensesIndecator() {

        double expenseSum;
        double totalExpenseSum = 0;
        df = (DefaultTableModel) jtableExpenses.getModel();
        txtTotalExpenses.setText("");

        for (int i = 0; i < df.getRowCount(); i++) {

            expenseSum = Double.parseDouble(df.getValueAt(i, 5).toString());
            totalExpenseSum += expenseSum;

        }
        txtTotalExpenses.setText(Double.toString(totalExpenseSum));
    }

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed

        this.setVisible(false);

    }//GEN-LAST:event_jButton5ActionPerformed

    public void returnProductToTheCreditsList() {

        String id, tarix, satisID, malinAdi, borcID, borcAlaninAdi;
        double qismenOdenis = 0;

        df = (DefaultTableModel) tblGelirCedveli.getModel();
        int selected = tblGelirCedveli.getSelectedRow();

        id = df.getValueAt(selected, 0).toString();
        malinAdi = df.getValueAt(selected, 1).toString();
        String tamAd = malinAdi.substring(8);
        tarix = df.getValueAt(selected, 8).toString();
        borcAlaninAdi = df.getValueAt(selected, 14).toString();
        satisID = df.getValueAt(selected, 19).toString();
        borcID = df.getValueAt(selected, 20).toString();
        qismenOdenis = Double.parseDouble(df.getValueAt(selected, 11).toString());

        try {
            con = connect();
            pres = con.prepareStatement("update borclar_siyahisi set Qismen_odenis = Qismen_odenis - ?, Qaliq_borc = Qaliq_borc + ? where Borca_goturduyu_mehsul = " + "'" + tamAd + "'" + " and id = " + borcID);
            pres.setDouble(1, qismenOdenis);
            pres.setDouble(2, qismenOdenis);
            int result = pres.executeUpdate();

            if (result == 1) {
                JOptionPane.showMessageDialog(this, "Müşteri -" + borcAlaninAdi + "-in " + tamAd + "- adli mehsuldan " + qismenOdenis + "-AZN mebleğde ödenişi geri qaytarıldı!");
            }
            if (result == 0) {
                JOptionPane.showMessageDialog(this, "Emeliyyat ugursuz oldu!", "DIQQET!", HEIGHT);
            }

            pres = con.prepareStatement("update satilan_mallar set Malin_adi = ?, QiemenOdenis = ?, BorcID = ? where BorcID = ?");
            pres.setString(1, "Ödeniş geri qaytarıldı -" + tamAd);
            pres.setDouble(2, 0.0);
            pres.setInt(3, 0);
            pres.setString(4, borcID);
            pres.executeUpdate();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void tamOdenisiGeriQaytarmaq() {
        // Deyisenler yaradilir
        String id, tarix, satisID, malinAdi, borcID, borcAlaninAdi;
        double miqdari, satisQiymeti, cemMebleg, qismenOdenis = 0;
        int mehsulID;

        //Aktiv istifadeci tapilir
        String activeUser = activeUser();

        df = (DefaultTableModel) tblGelirCedveli.getModel();
        int selected = tblGelirCedveli.getSelectedRow();

        mehsulID = Integer.parseInt(df.getValueAt(selected, 0).toString());
        malinAdi = df.getValueAt(selected, 1).toString();
        String tamAd = malinAdi.substring(8);
        tarix = df.getValueAt(selected, 8).toString();
        borcAlaninAdi = df.getValueAt(selected, 14).toString();
        satisID = df.getValueAt(selected, 19).toString();
        borcID = df.getValueAt(selected, 20).toString();
        qismenOdenis = Double.parseDouble(df.getValueAt(selected, 11).toString());
        miqdari = Double.parseDouble(df.getValueAt(selected, 3).toString());
        satisQiymeti = Double.parseDouble(df.getValueAt(selected, 6).toString());
        cemMebleg = Double.parseDouble(df.getValueAt(selected, 4).toString());
        double qaliqBorc = cemMebleg - (cemMebleg - qismenOdenis);

        try {
            con = connect();
            pres = con.prepareStatement("insert into borclar_siyahisi (Borc_alanin_adi, Borca_goturduyu_mehsul, Mehsul_ID, Miqdari, Qiymeti, Umumi_mebleg, Qismen_odenis, Qaliq_borc, Borc_alma_tarixi, Kassir, ActiveUser ) values(?,?,?,?,?,?,?,?,?,?,?)");
            pres.setString(1, borcAlaninAdi);
            pres.setString(2, tamAd + "- geri qaytarildi");
            pres.setInt(3, mehsulID);
            pres.setDouble(4, miqdari);
            pres.setDouble(5, satisQiymeti);
            pres.setDouble(6, cemMebleg);
            pres.setDouble(7, qismenOdenis);
            pres.setDouble(8, qaliqBorc);
            pres.setString(9, time);
            pres.setString(10, activeUser);
            pres.setString(11, activeUser);
            int result = pres.executeUpdate();

            System.out.println(result);

            if (result == 1) {
                JOptionPane.showMessageDialog(this, "Müşteri -" + borcAlaninAdi + "-in " + tamAd + "- adli mehsuldan tam ödenilmiş " + qismenOdenis + "-AZN mebleğ geri qaytarıldı!");
            }
            if (result == 0) {
                JOptionPane.showMessageDialog(this, "Emeliyyat ugursuz oldu!", "DIQQET!", HEIGHT);
            }

            pres = con.prepareStatement("update satilan_mallar set Malin_adi = ?, id = ?, QiemenOdenis = ?, BorcID = ? where BorcID = ?");
            pres.setString(1, "Ödeniş geri qaytarıldı -" + tamAd);
            pres.setDouble(2, 0.0);
            pres.setDouble(3, 0);
            pres.setInt(4, 0);
            pres.setString(5, borcID);
            pres.executeUpdate();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
    String time;

    public void time() {

    }

    public String activeUser() {
        String activeUser = null;
        String activeUserName = null;
        String activeUserSurename = null;

        try {
            String status = "Active";
            pres = con.prepareStatement("select * from users where status = " + "'" + status + "'");
            ResultSet rsForActiveUser = pres.executeQuery();

            rsForActiveUser.next();

            activeUserName = rsForActiveUser.getString("UserName");
            activeUserSurename = rsForActiveUser.getString("UserSureName");

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        activeUser = activeUserName + activeUserSurename;
        return activeUser;
    }

    public void returnProductToTheStock() {

        String id, tarix, satisID, malinAdi, borcID;

        try {

            df = (DefaultTableModel) tblGelirCedveli.getModel();
            int selected = tblGelirCedveli.getSelectedRow();

            id = df.getValueAt(selected, 0).toString();
            tarix = df.getValueAt(selected, 8).toString();
            satisID = df.getValueAt(selected, 19).toString();
            malinAdi = df.getValueAt(selected, 1).toString();
            borcID = df.getValueAt(selected, 20).toString();

            pres = con.prepareStatement("update satilan_mallar set Malin_adi = ?, id = ?, QiemenOdenis = ?, BorcID = ? where id = ?");
            pres.setString(1, "Satış geri qaytarıldı -" + malinAdi);
            pres.setDouble(2, 0.0);
            pres.setDouble(3, 0);
            pres.setInt(4, 0);
            pres.setString(5, id);
            pres.executeUpdate();

            geriQaytarma();

            JOptionPane.showMessageDialog(this, "Mehsul ugurla silindi!");

        } catch (SQLException ex) {
            System.out.println("Gosterilen mehsul satilan mallar siyahisinda yoxdur!");
        }

    }

    public void geriQaytarma() {
        try {
            connect();

            String id, miqdari, umumiMebleg;

            df = (DefaultTableModel) tblGelirCedveli.getModel();
            int selected = tblGelirCedveli.getSelectedRow();

            id = df.getValueAt(selected, 0).toString();
            miqdari = df.getValueAt(selected, 3).toString();
            umumiMebleg = df.getValueAt(selected, 4).toString();

            String query2 = "update mehsullar set Satis_miqdari = Satis_miqdari - ?,  Satisin_toplam_deyeri = Satisin_toplam_deyeri - ?, Qaliq_say = Qaliq_say + ? where id = ?";

            pres = con.prepareStatement(query2);

            pres.setString(1, miqdari);
            pres.setString(2, umumiMebleg);
            pres.setString(3, miqdari);
            pres.setString(4, id);
            pres.executeUpdate();

        } catch (Exception ex) {
            System.out.println(ex);
        }

    }


    private void dunenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dunenActionPerformed

        txtPayment.setText("");
        txtAxtaris.setText("");

        int day = 1;
        UmumiSatis.removeAll();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = new GregorianCalendar();
        String ss = sdf.format(cal.getTime());
        cal.add(Calendar.DAY_OF_MONTH, -1);

        String sss = sdf.format(cal.getTime());

        //  System.out.println(ss);
        // System.out.println(sss);
        int a;
        try {

            pres = con.prepareCall("SELECT\n"
                    + "	s.id,\n"
                    + "	s.Malin_adi,\n"
                    + "	s.Miqdari,\n"
                    + "	s.Umumi_Mebleg AS Satis_Meblegi_Cem,\n"
                    + "	s.QiemenOdenis,\n"
                    + "	s.Qaytarilan_Mehsul_Miqdari,\n"
                    + "	s.Musteriye_Geri_Odenis,\n"
                    + "	s.Borc_Alanin_Adi,\n"
                    + "	s.Yeni_goturulen_Mebleg,\n"
                    + "	s.Borcdan_Gelen,\n"
                    + "	s.Satis_ID,\n"
                    + " m.Satis_miqdari, \n"
                    + "	m.Alis_qiymeti,\n"
                    + " m.Satisin_toplam_deyeri, \n"
                    + "	s.Satis_qiymeti,\n"
                    + "	s.OdenisinNovu,\n"
                    + "	s.cekNomresi,\n"
                    + "	s.ActiveUser,\n"
                    + "	m.Satis_miqdari AS Umumi_Satis_Miqdari,\n"
                    + "	s.Satis_Tarixi,\n"
                    + "	( s.Miqdari * s.Satis_qiymeti - s.Miqdari * m.Alis_qiymeti ) AS Xeyir, (m.Satisin_toplam_deyeri - m.Alis_qiymeti* m.Satis_miqdari) as Umumi_Xeyir \n"
                    + "FROM\n"
                    + "	satilan_mallar s\n"
                    + "	LEFT JOIN mehsullar m ON m.id = s.id \n"
                    + "WHERE Date (s.Satis_Tarixi) = " + "'" + sss + "'");

            ResultSet rs = pres.executeQuery();

            ResultSetMetaData rd = rs.getMetaData();
            a = rd.getColumnCount();
            df = (DefaultTableModel) tblGelirCedveli.getModel();
            df.setRowCount(0);

            while (rs.next()) {
                Vector v2 = new Vector();
                for (int i = 0; i < a; i++) {
                    v2.add(rs.getInt("id"));
                    v2.add(rs.getString("Malin_adi"));
                    v2.add(rs.getString("OdenisinNovu"));
                    v2.add(rs.getDouble("Miqdari"));
                    v2.add(rs.getDouble("Satis_Meblegi_Cem"));
                    v2.add(rs.getDouble("Alis_qiymeti"));
                    v2.add(rs.getDouble("Satis_qiymeti"));
                    v2.add(rs.getInt("Umumi_Satis_Miqdari"));
                    v2.add(rs.getString("Satis_Tarixi"));
                    v2.add(rs.getDouble("Xeyir"));
                    v2.add(rs.getDouble("Umumi_Xeyir"));
                    v2.add(rs.getDouble("QiemenOdenis"));
                    v2.add(rs.getDouble("Qaytarilan_Mehsul_Miqdari"));
                    v2.add(rs.getDouble("Musteriye_Geri_Odenis"));
                    v2.add(rs.getString("Borc_Alanin_Adi"));
                    v2.add(rs.getDouble("Yeni_goturulen_Mebleg"));
                    v2.add(rs.getDouble("Borcdan_Gelen"));
                    v2.add(rs.getString("ActiveUser"));
                    v2.add(rs.getFloat("cekNomresi"));
                    v2.add(rs.getFloat("Satis_ID"));
                }
                df.addRow(v2);

            }

            //getCapitalBudget();
            getExpenses(day);
            //indecators();

        } catch (Exception ex) {
            ex.printStackTrace();

        }

    }//GEN-LAST:event_dunenActionPerformed

    public void getInfoAboutClientsPaymentForYesterday() {

        String nameClient = txtAxtaris.getText();
        double qismenOdenis = 0;
        double allpartialPayment = 0;

        int day = 1;
        UmumiSatis.removeAll();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = new GregorianCalendar();
        String ss = sdf.format(cal.getTime());
        cal.add(Calendar.DAY_OF_MONTH, -1);

        String sss = sdf.format(cal.getTime());

        //  System.out.println(ss);
        // System.out.println(sss);
        int a;
        try {

            pres = con.prepareCall("SELECT\n"
                    + "	s.id,\n"
                    + "	s.Malin_adi,\n"
                    + "	s.Miqdari,\n"
                    + "	s.Umumi_Mebleg AS Satis_Meblegi_Cem,\n"
                    + "	s.QiemenOdenis,\n"
                    + "	s.Qaytarilan_Mehsul_Miqdari,\n"
                    + "	s.Musteriye_Geri_Odenis,\n"
                    + "	s.Borc_Alanin_Adi,\n"
                    + "	s.Yeni_goturulen_Mebleg,\n"
                    + "	s.Borcdan_Gelen,\n"
                    + "	s.OdenisinNovu,\n"
                    + "	s.cekNomresi,\n"
                    + "	s.ActiveUser,\n"
                    + " m.Satis_miqdari, \n"
                    + "	m.Alis_qiymeti,\n"
                    + " m.Satisin_toplam_deyeri, \n"
                    + "	s.Satis_qiymeti,\n"
                    + "	m.Satis_miqdari AS Umumi_Satis_Miqdari,\n"
                    + "	s.Satis_Tarixi,\n"
                    + "	( s.Miqdari * s.Satis_qiymeti - s.Miqdari * m.Alis_qiymeti ) AS Xeyir, (m.Satisin_toplam_deyeri - m.Alis_qiymeti* m.Satis_miqdari) as Umumi_Xeyir \n"
                    + "FROM\n"
                    + "	satilan_mallar s\n"
                    + "	LEFT JOIN mehsullar m ON m.id = s.id \n"
                    + "WHERE Date (s.Satis_Tarixi) = " + "'" + sss + "'" + " and Borc_Alanin_Adi = " + "'" + nameClient + "'");

            ResultSet rs = pres.executeQuery();

            ResultSetMetaData rd = rs.getMetaData();
            a = rd.getColumnCount();
            df = (DefaultTableModel) tblGelirCedveli.getModel();
            df.setRowCount(0);

            while (rs.next()) {
                Vector v2 = new Vector();
                v2.add(rs.getInt("id"));
                v2.add(rs.getString("Malin_adi"));
                v2.add(rs.getString("OdenisinNovu"));
                v2.add(rs.getDouble("Miqdari"));
                v2.add(rs.getDouble("Satis_Meblegi_Cem"));
                v2.add(rs.getDouble("Alis_qiymeti"));
                v2.add(rs.getDouble("Satis_qiymeti"));
                v2.add(rs.getInt("Umumi_Satis_Miqdari"));
                v2.add(rs.getString("Satis_Tarixi"));
                v2.add(rs.getDouble("Xeyir"));
                v2.add(rs.getDouble("Umumi_Xeyir"));
                v2.add(rs.getDouble("QiemenOdenis"));
                v2.add(rs.getDouble("Qaytarilan_Mehsul_Miqdari"));
                v2.add(rs.getDouble("Musteriye_Geri_Odenis"));
                v2.add(rs.getString("Borc_Alanin_Adi"));
                v2.add(rs.getDouble("Yeni_goturulen_Mebleg"));
                v2.add(rs.getDouble("Borcdan_Gelen"));
                v2.add(rs.getString("ActiveUser"));
                v2.add(rs.getFloat("cekNomresi"));

                qismenOdenis = (rs.getDouble("QiemenOdenis"));
                allpartialPayment += qismenOdenis;

                df.addRow(v2);
            }

        } catch (Exception ex) {
            ex.printStackTrace();

        }

        txtPayment.setText(Double.toString(allpartialPayment));

    }


    private void btnHesablaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHesablaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnHesablaActionPerformed

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked

        secilenTarixler();


    }//GEN-LAST:event_jLabel2MouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        df = (DefaultTableModel) tblGelirCedveli.getModel();

        int selected = tblGelirCedveli.getSelectedRow();

        int id = Integer.parseInt(df.getValueAt(selected, 0).toString());

        Mehsullar mehsul = mehDao.getMehsulById(id);

        String mehsulunAdi = mehsul.getName();

        int cavab = JOptionPane.showConfirmDialog(this, "Silinən məhsulu geri qaytarmaq mümkün olmayacaq! \n" + mehsulunAdi + "-adli mehsulu silmək istədiyinizdən əminsiniz?", "Diqqət!", JOptionPane.YES_NO_CANCEL_OPTION);

        if (cavab == 0) {

            try {
                pres = con.prepareStatement("delete from satilan_mallar where id = ? ");

                pres.setInt(1, id);

                pres.executeUpdate();

                // load();
                JOptionPane.showMessageDialog(this, " " + mehsulunAdi + "-adli mehsul silindi");

            } catch (SQLException ex) {
                Logger.getLogger(Satici_Elave_Etmek.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        if (cavab == 1) {

        }
        if (cavab == 2) {

        } else {

        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed

        UmumiSatis.setText("");
        txtKassa.setText("");
        txtTotalBudget.setText("");
        txtTotalProfit.setText("");
        txtTotalExpenses.setText("");
        txtCapitalBudget.setText("");
        txtPayment.setText("");
        txtAxtaris.setText("");

    }//GEN-LAST:event_jButton6ActionPerformed


    private void rbBugunKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rbBugunKeyPressed


    }//GEN-LAST:event_rbBugunKeyPressed

    private void UmumiSatisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UmumiSatisActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_UmumiSatisActionPerformed
    ResultSet rs;
    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed

        createNewCapitalBudget add = new createNewCapitalBudget(this, true);

        add.setVisible(true);
        try {

            con = connect();
            pres = con.prepareStatement("select * from capitalbudget");
            rs = pres.executeQuery();

            rs.next();
            double capitalBudget = rs.getDouble("AmountOfCapitalBudget");
            txtCapitalBudget.setText(Double.toString(capitalBudget));

            con = connect();
            pres = con.prepareStatement("select AmountOfCapitalBudget from updatedcapitalbudget order by id desc limit 1");

            ResultSet rs2 = pres.executeQuery();

            rs2.next();

            double currentCapitalBudget = rs2.getDouble("AmountOfCapitalBudget");
            txtTotalBudget.setText(Double.toString(currentCapitalBudget));

        } catch (Exception e) {
            e.printStackTrace();
        }


    }//GEN-LAST:event_jButton7ActionPerformed

    private void txtAxtarisKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAxtarisKeyReleased

        String searchOption = cBoxOptionFoorSearch.getSelectedItem().toString();

        if (searchOption.equals("Xerc axtar..")) {
            startSearchForExpenses();
            calculateAll();
        }
        if (searchOption.equals("Müşteri axtar..")) {
            searchForTheCllient();
        }
        if (searchOption.equals("Mehsul axtar..")) {
            findProduct();
            calculateAll();
        }


    }//GEN-LAST:event_txtAxtarisKeyReleased

    public void findProduct() {

        String find = txtAxtaris.getText();

        boolean checkLastMonth = sonBirAy.isSelected();
        boolean checkLastWeek = sonBirHefte.isSelected();
        boolean checLastThreeDay = sonUcGun.isSelected();
        boolean chechYesterday = dunen.isSelected();
        boolean checkToday = rbBugun.isSelected();
        Date firstDate2 = ilkTarix.getDate();
        Date secondDate2 = sonTarix.getDate();

        Calendar cal = new GregorianCalendar();

        if (firstDate2 != null && secondDate2 != null) {
            System.out.println("Beli qaqaw tarix null dan ferqlidir. Budur.. " + firstDate2);

            String firstDate1 = sdf.format(ilkTarix.getDate());
            String secondDAte = sdf.format(sonTarix.getDate());

        } else {

            if (checkToday == true) {

                findToday(find);
            }
            if (chechYesterday == true) {

                cal.add(Calendar.DAY_OF_MONTH, -1);
                String lastThreeDay = sdf.format(cal.getTime());
                findYesterday(find, lastThreeDay);

            }
            if (checLastThreeDay == true) {

                cal.add(Calendar.DAY_OF_MONTH, -3);
                String lastThreeDay = sdf.format(cal.getTime());
                findByTime(find, lastThreeDay);

            }
            if (checkLastWeek == true) {

                cal.add(Calendar.DAY_OF_MONTH, -7);
                String theLastWeek = sdf.format(cal.getTime());
                findByTime(find, theLastWeek);

            }
            if (checkLastMonth == true) {

                cal.add(Calendar.DAY_OF_MONTH, -30);
                String theLastMonth = sdf.format(cal.getTime());
                findByTime(find, theLastMonth);

            }

        }

    }

    public void findToday(String findProduct) {

        try {
            int a;
            pres = con.prepareStatement("SELECT\n"
                    + "	s.id,\n"
                    + "	s.Malin_adi,\n"
                    + "	s.Miqdari,\n"
                    + "	s.Umumi_Mebleg AS Satis_Meblegi_Cem,\n"
                    + "	s.QiemenOdenis,\n"
                    + "	s.Qaytarilan_Mehsul_Miqdari,\n"
                    + "	s.Musteriye_Geri_Odenis,\n"
                    + "	s.Borc_Alanin_Adi,\n"
                    + "	s.Yeni_goturulen_Mebleg,\n"
                    + "	s.Borcdan_Gelen,\n"
                    + " m.Satis_miqdari, \n"
                    + "	m.Alis_qiymeti,\n"
                    + " m.Satisin_toplam_deyeri, \n"
                    + "	s.Satis_qiymeti,\n"
                    + "	s.OdenisinNovu,\n"
                    + "	s.cekNomresi,\n"
                    + "	s.ActiveUser,\n"
                    + "	m.Satis_miqdari AS Umumi_Satis_Miqdari,\n"
                    + "	s.Satis_Tarixi,\n"
                    + "	( s.Miqdari * s.Satis_qiymeti - s.Miqdari * m.Alis_qiymeti ) AS Xeyir, (m.Satisin_toplam_deyeri - m.Alis_qiymeti* m.Satis_miqdari) as Umumi_Xeyir \n"
                    + "FROM\n"
                    + "	satilan_mallar s\n"
                    + "	LEFT JOIN mehsullar m ON m.id = s.id \n"
                    + "WHERE Date (s.Satis_Tarixi) = CURRENT_DATE and s.Malin_adi like '%' " + "'" + findProduct + "'" + " '%'");

            ResultSet rs = pres.executeQuery();

            ResultSetMetaData rd = rs.getMetaData();
            a = rd.getColumnCount();
            df = (DefaultTableModel) tblGelirCedveli.getModel();
            df.setRowCount(0);

            while (rs.next()) {
                Vector v2 = new Vector();
                for (int i = 0; i < a; i++) {
                    v2.add(rs.getInt("id"));
                    v2.add(rs.getString("Malin_adi"));
                    v2.add(rs.getString("OdenisinNovu"));
                    v2.add(rs.getDouble("Miqdari"));
                    v2.add(rs.getDouble("Satis_Meblegi_Cem"));
                    v2.add(rs.getDouble("Alis_qiymeti"));
                    v2.add(rs.getDouble("Satis_qiymeti"));
                    v2.add(rs.getInt("Umumi_Satis_Miqdari"));
                    v2.add(rs.getString("Satis_Tarixi"));
                    v2.add(rs.getDouble("Xeyir"));
                    v2.add(rs.getDouble("Umumi_Xeyir"));
                    v2.add(rs.getDouble("QiemenOdenis"));
                    v2.add(rs.getDouble("Qaytarilan_Mehsul_Miqdari"));
                    v2.add(rs.getDouble("Musteriye_Geri_Odenis"));
                    v2.add(rs.getString("Borc_Alanin_Adi"));
                    v2.add(rs.getDouble("Yeni_goturulen_Mebleg"));
                    v2.add(rs.getDouble("Borcdan_Gelen"));
                    v2.add(rs.getString("ActiveUser"));
                    v2.add(rs.getFloat("cekNomresi"));

                }
                df.addRow(v2);

            }

        } catch (Exception ex) {

            ex.printStackTrace();

        }

    }

    public void findYesterday(String findProduct, String date) {

        try {
            int a;
            pres = con.prepareStatement("SELECT\n"
                    + "	s.id,\n"
                    + "	s.Malin_adi,\n"
                    + "	s.Miqdari,\n"
                    + "	s.Umumi_Mebleg AS Satis_Meblegi_Cem,\n"
                    + "	s.QiemenOdenis,\n"
                    + "	s.Qaytarilan_Mehsul_Miqdari,\n"
                    + "	s.Musteriye_Geri_Odenis,\n"
                    + "	s.Borc_Alanin_Adi,\n"
                    + "	s.Yeni_goturulen_Mebleg,\n"
                    + "	s.Borcdan_Gelen,\n"
                    + " m.Satis_miqdari, \n"
                    + "	m.Alis_qiymeti,\n"
                    + " m.Satisin_toplam_deyeri, \n"
                    + "	s.Satis_qiymeti,\n"
                    + "	s.OdenisinNovu,\n"
                    + "	s.cekNomresi,\n"
                    + "	s.ActiveUser,\n"
                    + "	m.Satis_miqdari AS Umumi_Satis_Miqdari,\n"
                    + "	s.Satis_Tarixi,\n"
                    + "	( s.Miqdari * s.Satis_qiymeti - s.Miqdari * m.Alis_qiymeti ) AS Xeyir, (m.Satisin_toplam_deyeri - m.Alis_qiymeti* m.Satis_miqdari) as Umumi_Xeyir \n"
                    + "FROM\n"
                    + "	satilan_mallar s\n"
                    + "	LEFT JOIN mehsullar m ON m.id = s.id \n"
                    + "WHERE Date (s.Satis_Tarixi) = " + "'" + date + "'" + " and s.Malin_adi like '%' " + "'" + findProduct + "'" + " '%'");

            ResultSet rs = pres.executeQuery();

            ResultSetMetaData rd = rs.getMetaData();
            a = rd.getColumnCount();
            df = (DefaultTableModel) tblGelirCedveli.getModel();
            df.setRowCount(0);

            while (rs.next()) {
                Vector v2 = new Vector();
                for (int i = 0; i < a; i++) {
                    v2.add(rs.getInt("id"));
                    v2.add(rs.getString("Malin_adi"));
                    v2.add(rs.getString("OdenisinNovu"));
                    v2.add(rs.getDouble("Miqdari"));
                    v2.add(rs.getDouble("Satis_Meblegi_Cem"));
                    v2.add(rs.getDouble("Alis_qiymeti"));
                    v2.add(rs.getDouble("Satis_qiymeti"));
                    v2.add(rs.getInt("Umumi_Satis_Miqdari"));
                    v2.add(rs.getString("Satis_Tarixi"));
                    v2.add(rs.getDouble("Xeyir"));
                    v2.add(rs.getDouble("Umumi_Xeyir"));
                    v2.add(rs.getDouble("QiemenOdenis"));
                    v2.add(rs.getDouble("Qaytarilan_Mehsul_Miqdari"));
                    v2.add(rs.getDouble("Musteriye_Geri_Odenis"));
                    v2.add(rs.getString("Borc_Alanin_Adi"));
                    v2.add(rs.getDouble("Yeni_goturulen_Mebleg"));
                    v2.add(rs.getDouble("Borcdan_Gelen"));
                    v2.add(rs.getString("ActiveUser"));
                    v2.add(rs.getFloat("cekNomresi"));

                }
                df.addRow(v2);

            }

        } catch (Exception ex) {

            ex.printStackTrace();

        }

    }

    public void findByTime(String findProduct, String date) {

        try {
            int a;
            pres = con.prepareStatement("SELECT\n"
                    + "	s.id,\n"
                    + "	s.Malin_adi,\n"
                    + "	s.Miqdari,\n"
                    + "	s.Umumi_Mebleg AS Satis_Meblegi_Cem,\n"
                    + "	s.QiemenOdenis,\n"
                    + "	s.Qaytarilan_Mehsul_Miqdari,\n"
                    + "	s.Musteriye_Geri_Odenis,\n"
                    + "	s.Borc_Alanin_Adi,\n"
                    + "	s.Yeni_goturulen_Mebleg,\n"
                    + "	s.Borcdan_Gelen,\n"
                    + " m.Satis_miqdari, \n"
                    + "	m.Alis_qiymeti,\n"
                    + " m.Satisin_toplam_deyeri, \n"
                    + "	s.Satis_qiymeti,\n"
                    + "	s.OdenisinNovu,\n"
                    + "	s.cekNomresi,\n"
                    + "	s.ActiveUser,\n"
                    + "	m.Satis_miqdari AS Umumi_Satis_Miqdari,\n"
                    + "	s.Satis_Tarixi,\n"
                    + "	( s.Miqdari * s.Satis_qiymeti - s.Miqdari * m.Alis_qiymeti ) AS Xeyir, (m.Satisin_toplam_deyeri - m.Alis_qiymeti* m.Satis_miqdari) as Umumi_Xeyir \n"
                    + "FROM\n"
                    + "	satilan_mallar s\n"
                    + "	LEFT JOIN mehsullar m ON m.id = s.id \n"
                    + " where DATE (s.Satis_Tarixi) BETWEEN " + "'" + date + "'" + " and CURRENT_DATE and s.Malin_adi like '%' " + "'" + findProduct + "'" + " '%'");

            ResultSet rs = pres.executeQuery();

            ResultSetMetaData rd = rs.getMetaData();
            a = rd.getColumnCount();
            df = (DefaultTableModel) tblGelirCedveli.getModel();
            df.setRowCount(0);

            while (rs.next()) {
                Vector v2 = new Vector();

                v2.add(rs.getInt("id"));
                v2.add(rs.getString("Malin_adi"));
                v2.add(rs.getString("OdenisinNovu"));
                v2.add(rs.getDouble("Miqdari"));
                v2.add(rs.getDouble("Satis_Meblegi_Cem"));
                v2.add(rs.getDouble("Alis_qiymeti"));
                v2.add(rs.getDouble("Satis_qiymeti"));
                v2.add(rs.getInt("Umumi_Satis_Miqdari"));
                v2.add(rs.getString("Satis_Tarixi"));
                v2.add(rs.getDouble("Xeyir"));
                v2.add(rs.getDouble("Umumi_Xeyir"));
                v2.add(rs.getDouble("QiemenOdenis"));
                v2.add(rs.getDouble("Qaytarilan_Mehsul_Miqdari"));
                v2.add(rs.getDouble("Musteriye_Geri_Odenis"));
                v2.add(rs.getString("Borc_Alanin_Adi"));
                v2.add(rs.getDouble("Yeni_goturulen_Mebleg"));
                v2.add(rs.getDouble("Borcdan_Gelen"));
                v2.add(rs.getString("ActiveUser"));
                v2.add(rs.getFloat("cekNomresi"));

                df.addRow(v2);

            }

        } catch (Exception ex) {

            ex.printStackTrace();

        }

    }


    private void listNameOfClientsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listNameOfClientsMouseClicked

        boolean yoxlaToday = rbBugun.isSelected();
        boolean yoxlaYesterday = dunen.isSelected();
        boolean yoxlaTheLastThreeDay = sonUcGun.isSelected();
        boolean yoxlaTheLastWeek = sonBirHefte.isSelected();
        boolean yoxlaTheLastMonth = sonBirAy.isSelected();

        String selectedClientName = listNameOfClients.getSelectedValue();
        txtAxtaris.setText(selectedClientName);

        if (yoxlaToday == true) {
            getInfoAboutClientsPaymentForToday();
            jPanel2.setVisible(false);
        }
        if (yoxlaYesterday == true) {
            getInfoAboutClientsPaymentForYesterday();
            jPanel2.setVisible(false);
        }
        if (yoxlaTheLastThreeDay == true) {
            getInfoAboutClientsPaymentForLastThreeDays();
            jPanel2.setVisible(false);
        }
        if (yoxlaTheLastWeek == true) {
            getInfoAboutClientsPaymentForLastWeek();
            jPanel2.setVisible(false);
        }
        if (yoxlaTheLastMonth == true) {
            getInfoAboutClientsPaymentForTheLastMonth();
            jPanel2.setVisible(false);
        }


    }//GEN-LAST:event_listNameOfClientsMouseClicked

    private void txtAxtarisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAxtarisActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAxtarisActionPerformed

    private void txtPaymentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPaymentActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPaymentActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed

        df = (DefaultTableModel) tblGelirCedveli.getModel();
        int selected = tblGelirCedveli.getSelectedRow();

        String TypeOfPayment = df.getValueAt(selected, 2).toString();
        int id = Integer.parseInt(df.getValueAt(selected, 0).toString());
        int borcID = Integer.parseInt(df.getValueAt(selected, 20).toString());

        if (TypeOfPayment.equals("Nağd")) {
            if (id == 0) {
                JOptionPane.showMessageDialog(this, "Bu mehsul satışı artıq geri qaytarılmışdı");
                return;
            }
            returnProductToTheStock();
            refreshAfterUpdate();
            calculateAll();
        }
        if (TypeOfPayment.equals("Borcdan")) {

            if (id == 0) {
                if (borcID == 0) {
                    JOptionPane.showMessageDialog(this, "Bu ödeniş artıq geri qaytarılmışdı");
                    return;
                }
                returnProductToTheCreditsList();
                refreshAfterUpdate();
                calculateAll();
            }
            if (id != 0) {
                if (borcID == 0) {
                    JOptionPane.showMessageDialog(this, "Bu ödeniş artıq geri qaytarılmışdı");
                    return;
                }
                tamOdenisiGeriQaytarmaq();
                refreshAfterUpdate();
                calculateAll();
            }

        }


    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        int saySebetUcun = 1;
        double allTolat =0;
        String optionForPaymentBill = cBoxOptionForBill.getSelectedItem().toString();

        if (optionForPaymentBill.equals("Sonuncu satiş")) {
            try {
                
                pres = con.prepareStatement("truncate table sebet");
                pres.executeUpdate();
                
                con = connect();
                pres = con.prepareStatement("select * from satilan_mallar order by cekNomresi desc limit 1");
                ResultSet rs = pres.executeQuery();

                rs.next();

                float billNumber = rs.getFloat("cekNomresi");
                System.out.println("Sonuncu cek nomresi budu---->" + billNumber);

                pres = con.prepareStatement("select * from satilan_mallar where cekNomresi = " + billNumber);
                ResultSet rsForProducts = pres.executeQuery();

                while (rsForProducts.next()) {
                    String mehsul = rsForProducts.getString("Malin_adi");
                    int mehsulID = rsForProducts.getInt("id");
                    int miqdari = rsForProducts.getInt("Miqdari");
                    double total = rsForProducts.getDouble("Umumi_Mebleg");
                    double satisQiymeti = rsForProducts.getDouble("Satis_qiymeti");
                    
                    allTolat+=total;
                    
                    pres = con.prepareStatement(
                            "insert into sebet ( id2, id, Malin_adi, Miqdari, Satis_qiymeti, Umumi_Mebleg, Tarix ) values(?,?,?,?,?,?,? )");

                    pres.setInt(1, saySebetUcun);
                    pres.setInt(2, mehsulID);
                    pres.setString(3, mehsul);
                    pres.setInt(4, miqdari);
                    pres.setDouble(5, satisQiymeti);
                    pres.setDouble(6, total);
                    pres.setString(7, time);
                    pres.executeUpdate();
                    saySebetUcun++;

                }
                
                printRecipe(allTolat);
                
                pres = con.prepareStatement("truncate table sebet");
                pres.executeUpdate();
                
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        if (optionForPaymentBill.equals("Çek nömreine esasen")) {

            try {
                
                pres = con.prepareStatement("truncate table sebet");
                pres.executeUpdate();
                
                float billNumber = Float.parseFloat(txtBillNumberForPrint.getText());

                pres = con.prepareStatement("select * from satilan_mallar where cekNomresi = " + billNumber);
                ResultSet rsForProducts = pres.executeQuery();

                while (rsForProducts.next()) {
                    String mehsul = rsForProducts.getString("Malin_adi");
                    int mehsulID = rsForProducts.getInt("id");
                    int miqdari = rsForProducts.getInt("Miqdari");
                    double total = rsForProducts.getDouble("Umumi_Mebleg");
                    double satisQiymeti = rsForProducts.getDouble("Satis_qiymeti");
                    allTolat+=total;

                    pres = con.prepareStatement(
                            "insert into sebet ( id2, id, Malin_adi, Miqdari, Satis_qiymeti, Umumi_Mebleg, Tarix ) values(?,?,?,?,?,?,? )");

                    pres.setInt(1, saySebetUcun);
                    pres.setInt(2, mehsulID);
                    pres.setString(3, mehsul);
                    pres.setInt(4, miqdari);
                    pres.setDouble(5, satisQiymeti);
                    pres.setDouble(6, total);
                    pres.setString(7, time);
                    pres.executeUpdate();
                    saySebetUcun++;
                    
                }

                printRecipe(allTolat);
                
                pres = con.prepareStatement("truncate table sebet");
                pres.executeUpdate();
                
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }


    }//GEN-LAST:event_jButton9ActionPerformed

    private void cBoxOptionForBillActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cBoxOptionForBillActionPerformed
        
        
        
        
        
    }//GEN-LAST:event_cBoxOptionForBillActionPerformed

    
    public void printRecipe(double total) {
        String printerName;
        String currency = "AZN";
        String totalSum = Double.toString(total);
        
        boolean yoxla = totalSum.contains(".");
        if (yoxla == true) {
            currency = "0 qepik";
        } else {
            currency = ".00 AZN";
        }

        String projectPath = System.getProperty("user.dir");
        System.out.println(projectPath);
        String filePath = "\\src\\main\\java\\com\\mycompany\\qarisiqmallar\\veneramarket\\test444_2.jrxml";
        System.out.println(filePath);

        JasperDesign jdesign;
        try {
            Connection c = connect();
            jdesign = JRXmlLoader.load(projectPath + filePath);
            JasperReport jr = null;

            HashMap<String, Object> parametrs;
            parametrs = new HashMap<>();
            parametrs.put("date", time);
            parametrs.put("totalSum", totalSum + currency);

            if (projectPath.equals("C:\\git projects\\VeneraMarket-4\\VeneraMarket")) {
                printerName = "TSC TDP-225";
            } else {
                printerName = "Xprinter XP-365B";
            }

            jr = JasperCompileManager.compileReport(jdesign);

            JasperPrint jprint = JasperFillManager.fillReport(jr, parametrs, c);

            SilentPrint ss = new SilentPrint();
            SilentPrint2 sp = new SilentPrint2();
            //ss.printReport(jr, productPrice, parametrs, c);

            sp.PrintReportToPrinter(jprint, printerName, 1);

            //JasperViewer.viewReport(jprint, false);
        } catch (JRException ex) {
            Logger.getLogger(TreeView1.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    
    public void refreshAfterUpdate() {

        boolean checkLastMonth = sonBirAy.isSelected();
        boolean checkLastWeek = sonBirHefte.isSelected();
        boolean checLastThreeDay = sonUcGun.isSelected();
        boolean chechYesterday = dunen.isSelected();
        boolean checkToday = rbBugun.isSelected();

        if (checkToday == true) {
            rbBugun.doClick();
        }
        if (chechYesterday == true) {
            dunen.doClick();
        }
        if (checLastThreeDay == true) {
            sonUcGun.doClick();
        }
        if (checkLastWeek == true) {
            sonBirHefte.doClick();
        }
        if (checkLastMonth == true) {
            sonBirAy.doClick();
        }
    }

    public void getInfoAboutClientsPayment() {

        try {

            con = connect();

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
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Kassa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Kassa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Kassa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Kassa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Kassa().setVisible(true);
                } catch (Exception ex) {
                    Logger.getLogger(Kassa.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField UmumiSatis;
    private javax.swing.JButton btnHesabla;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cBoxOptionFoorSearch;
    private javax.swing.JComboBox<String> cBoxOptionForBill;
    private javax.swing.JComboBox<String> cbOption;
    private javax.swing.JRadioButton dunen;
    private com.toedter.calendar.JDateChooser ilkTarix;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable jtableExpenses;
    private javax.swing.JList<String> listNameOfClients;
    private javax.swing.JRadioButton rbBugun;
    private javax.swing.JRadioButton sonBirAy;
    private javax.swing.JRadioButton sonBirHefte;
    private com.toedter.calendar.JDateChooser sonTarix;
    private javax.swing.JRadioButton sonUcGun;
    private javax.swing.JTable tblGelirCedveli;
    private javax.swing.JTextField txtAxtaris;
    private javax.swing.JTextField txtBillNumberForPrint;
    private javax.swing.JTextField txtCapitalBudget;
    private javax.swing.JTextField txtKassa;
    private javax.swing.JTextField txtPayment;
    private javax.swing.JTextField txtTotalBudget;
    private javax.swing.JTextField txtTotalExpenses;
    private javax.swing.JTextField txtTotalProfit;
    // End of variables declaration//GEN-END:variables
}
