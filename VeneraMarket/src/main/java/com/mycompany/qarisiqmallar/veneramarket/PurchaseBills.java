/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.qarisiqmallar.veneramarket;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static java.awt.image.ImageObserver.HEIGHT;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author Shamil
 */
public class PurchaseBills extends javax.swing.JFrame {

    /**
     * Creates new form Main
     */
    public PurchaseBills() {
        initComponents();
        getAllBills();
        addMenu();
        setScreenSize();
        setTime();
        buttonGroup1.add(today);
        buttonGroup1.add(yesterday);
        buttonGroup1.add(lastWeek);
        buttonGroup1.add(lastMonth);
        today.doClick();
    }

    JMenuBar menuBar;
    JMenu fileMenu;
    JMenu editMenu;
    JMenu helpMenu;
    JMenuItem Stock;
    JMenuItem Products;
    JMenuItem Saticilar;
    JMenuItem Kassa;
    JMenuItem Deyisme;
    JMenuItem BorclarlaEmeliyyat;
    JMenuItem Alış_Qaimeleri;

    PreparedStatement pres;
    Statement stmt;
    ResultSet rs;
    Connection con;
    DefaultTableModel df;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
    String tamAd;
    int transfer = 0;
    int purchaseInvoice = 0;
    Integer selected1 = null;
    String currentDate;
    Timer timer;

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

    public void setScreenSize() {

        Toolkit toolkit = getToolkit();
        Dimension size = toolkit.getScreenSize();
        setLocation(size.width / 2 - getWidth() / 2, size.height / 2 - getHeight() / 2);

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

                String time2 = sdf2.format(dt);
                currentDate = time2;

            }
        });
        timer.start();

    }

    public void addMenu() {
        menuBar = new JMenuBar();

        //venera = new ImageIcon("C:\\Users\\samil\\OneDrive\\Изображения\\Camera Roll\\Icon file.jpg");
        fileMenu = new JMenu("File");
        editMenu = new JMenu("edit");
        helpMenu = new JMenu("Help");

        Stock = new JMenuItem("Məhsul əlavə et");
        Alış_Qaimeleri = new JMenuItem("Alış Qaimeleri");
        Saticilar = new JMenuItem("Satici elave et");
        Kassa = new JMenuItem("Kassa");
        Deyisme = new JMenuItem("Dəyişmə Əməliyyatı");
        BorclarlaEmeliyyat = new JMenuItem("Borclarla Əməliyyat");
        Products = new JMenuItem("Məhsullar");

        Stock.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == Stock) {

                    try {
                        TreeView1 mehsul = new TreeView1();
                        mehsul.setVisible(true);

                    } catch (Exception ex) {
                        Logger.getLogger(PurchaseBills.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            }
        });

//        Alış_Qaimeleri.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                if (e.getSource() == Alış_Qaimeleri) {
//
//                    try {
//                        AlisQaimeleri mehsul = new AlisQaimeleri();
//                        mehsul.setVisible(true);
//
//                    } catch (Exception ex) {
//                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//
//                }
//            }
//        });
//
//        Kassa.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//
//                if (e.getSource() == Kassa) {
//                    try {
//                        Kassa kassa = new Kassa();
//                        kassa.setVisible(true);
//
//                    } catch (Exception ex) {
//                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                }
//
//            }
//        });
//        Saticilar.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//
//                if (e.getSource() == Saticilar) {
//                    try {
//                        Satici_Elave_Etmek satici = new Satici_Elave_Etmek();
//                        satici.setVisible(true);
//
//                    } catch (Exception ex) {
//                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                }
//
//            }
//        });
//
//        Deyisme.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                if (e.getSource() == Deyisme) {
//
//                    try {
//                        MehsulDeyisdirilmesi mehsuldeyisme = new MehsulDeyisdirilmesi();
//                        mehsuldeyisme.setVisible(true);
//
//                    } catch (Exception ex) {
//                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//
//                }
//            }
//        });
//        BorclarlaEmeliyyat.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                if (e.getSource() == BorclarlaEmeliyyat) {
//
//                    try {
//                        BorclarlaEmeliyyat borclarlaEmeliyyat = new BorclarlaEmeliyyat();
//                        borclarlaEmeliyyat.setVisible(true);
//
//                    } catch (Exception ex) {
//                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//
//                }
//            }
//        });
//
//        Products.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                if (e.getSource() == Products) {
//
//                    try {
//                        Mehsul_Elave_etmek products = new Mehsul_Elave_etmek();
//                        products.setVisible(true);
//
//                    } catch (Exception ex) {
//                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//
//                }
//            }
//        });
        fileMenu.add(Stock);
        fileMenu.add(Saticilar);
        fileMenu.add(Kassa);
        fileMenu.add(Deyisme);
        fileMenu.add(BorclarlaEmeliyyat);

        editMenu.add(Alış_Qaimeleri);
        editMenu.add(Products);

        //Stock.setIcon(venera);
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(helpMenu);
        this.setJMenuBar(menuBar);
        this.setVisible(true);

    }

    public void getAllBills() {

        int a;
        try {

            con = connect();

            pres = con.prepareStatement("select * from alisqaimeleri");
            ResultSet rs = pres.executeQuery();

            ResultSetMetaData rd = rs.getMetaData();
            a = rd.getColumnCount();
            df = (DefaultTableModel) tblBills.getModel();
            df.setRowCount(0);

            while (rs.next()) {

                Vector v2 = new Vector();
                for (int i = 0; i < a; i++) {
                    v2.add(rs.getInt("id"));
                    v2.add(rs.getString("Kimden"));
                    v2.add(rs.getString("QaimeNomresi"));
                    v2.add(rs.getString("Qurum"));
                    v2.add(rs.getDouble("QaimeMeblegi"));
                    v2.add(rs.getString("KimTerefinden"));
                    v2.add(rs.getString("Tarix"));
                    v2.add(rs.getString("TypeOfDocument"));
                    v2.add(rs.getString("YenilenmeTarixi"));
                    v2.add(rs.getString("KimTerefinden"));

                }
                df.addRow(v2);

            }

        } catch (Exception ex) {
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        Перенести = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblBills = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblPdoducts = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        today = new javax.swing.JRadioButton();
        yesterday = new javax.swing.JRadioButton();
        lastWeek = new javax.swing.JRadioButton();
        lastMonth = new javax.swing.JRadioButton();
        secondDate = new com.toedter.calendar.JDateChooser();
        firstDate = new com.toedter.calendar.JDateChooser();
        txtNameOfClient = new javax.swing.JTextField();
        cbOptionsForSerach = new javax.swing.JComboBox<>();
        jButton5 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        rbtnOtherBase = new javax.swing.JRadioButton();
        txtTotalOfBuy = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        Перенести.setText("jMenuItem1");
        Перенести.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ПеренестиActionPerformed(evt);
            }
        });
        jPopupMenu1.add(Перенести);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                formMouseEntered(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 204));
        jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel1MouseEntered(evt);
            }
        });

        tblBills = new JTable()

        {
            public boolean isCellEditable(int row, int column)

            {
                for(int i =0; i< tblBills.getRowCount(); i++)
                {
                    if(row == i)
                    {
                        return false;
                    }
                }
                return false;
            }
        };
        tblBills.setForeground(new java.awt.Color(51, 51, 51));
        tblBills.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "id", "Имя организации", "Накладной номер", "Комментарий", "Общая сумма накладная", "Кто создал", "Дата создания", "Тип документа", "Дата изменения", "Кто изменил", "Курс доллара", "Статус"
            }
        ));
        tblBills.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblBillsMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                tblBillsMouseEntered(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblBillsMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tblBills);
        if (tblBills.getColumnModel().getColumnCount() > 0) {
            tblBills.getColumnModel().getColumn(0).setMinWidth(50);
            tblBills.getColumnModel().getColumn(0).setPreferredWidth(50);
            tblBills.getColumnModel().getColumn(0).setMaxWidth(70);
            tblBills.getColumnModel().getColumn(1).setMinWidth(100);
            tblBills.getColumnModel().getColumn(1).setPreferredWidth(100);
            tblBills.getColumnModel().getColumn(1).setMaxWidth(300);
            tblBills.getColumnModel().getColumn(2).setMinWidth(100);
            tblBills.getColumnModel().getColumn(2).setPreferredWidth(100);
            tblBills.getColumnModel().getColumn(2).setMaxWidth(250);
            tblBills.getColumnModel().getColumn(3).setMinWidth(150);
            tblBills.getColumnModel().getColumn(3).setPreferredWidth(150);
            tblBills.getColumnModel().getColumn(3).setMaxWidth(250);
            tblBills.getColumnModel().getColumn(4).setMinWidth(150);
            tblBills.getColumnModel().getColumn(4).setPreferredWidth(150);
            tblBills.getColumnModel().getColumn(4).setMaxWidth(250);
            tblBills.getColumnModel().getColumn(6).setMinWidth(100);
            tblBills.getColumnModel().getColumn(6).setPreferredWidth(100);
            tblBills.getColumnModel().getColumn(6).setMaxWidth(250);
        }

        tblPdoducts.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "№", "Наименование товара", "Количество", "Цена покупки", "Сумма"
            }
        ));
        jScrollPane2.setViewportView(tblPdoducts);
        if (tblPdoducts.getColumnModel().getColumnCount() > 0) {
            tblPdoducts.getColumnModel().getColumn(0).setMinWidth(50);
            tblPdoducts.getColumnModel().getColumn(0).setPreferredWidth(50);
            tblPdoducts.getColumnModel().getColumn(0).setMaxWidth(100);
        }

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        today.setText("Cегодня");
        today.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                todayActionPerformed(evt);
            }
        });

        yesterday.setText("Вчера");
        yesterday.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                yesterdayActionPerformed(evt);
            }
        });

        lastWeek.setText("Прошлая неделя");
        lastWeek.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lastWeekActionPerformed(evt);
            }
        });

        lastMonth.setText("Прошлый месяц");
        lastMonth.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lastMonthActionPerformed(evt);
            }
        });

        txtNameOfClient.setText("Найти..");
        txtNameOfClient.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                txtNameOfClientMouseEntered(evt);
            }
        });
        txtNameOfClient.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNameOfClientKeyReleased(evt);
            }
        });

        cbOptionsForSerach.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "По названию организации", "По комментарию" }));

        jButton5.setText("Поиск..");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jPanel5.setPreferredSize(new java.awt.Dimension(178, 66));

        jButton1.setText("Update");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton3.setText("Переместить к рас. нак");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("Переместить к пере. нак");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        rbtnOtherBase.setText("Другая база");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rbtnOtherBase))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton4)))
                .addContainerGap(8, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rbtnOtherBase))
                .addContainerGap(11, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(today)
                    .addComponent(yesterday))
                .addGap(29, 29, 29)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lastMonth)
                    .addComponent(lastWeek))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(firstDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(secondDate, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNameOfClient, javax.swing.GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE)
                    .addComponent(cbOptionsForSerach, 0, 1, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 383, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(firstDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(secondDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                            .addComponent(txtNameOfClient, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(cbOptionsForSerach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButton5))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(today)
                            .addComponent(lastWeek))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(yesterday)
                            .addComponent(lastMonth))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jButton2.setBackground(new java.awt.Color(255, 0, 0));
        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Удалить");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Приходные накладные");
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(439, 439, 439)
                        .addComponent(txtTotalOfBuy, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 294, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(txtTotalOfBuy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
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
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        getAllBills();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void yesterdayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_yesterdayActionPerformed

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = new GregorianCalendar();
        String ss = sdf.format(cal.getTime());
        cal.add(Calendar.DAY_OF_MONTH, -1);

        String lastThreeDay = sdf.format(cal.getTime());
        loadForYesterday(lastThreeDay);


    }//GEN-LAST:event_yesterdayActionPerformed

    public void loadForYesterday(String sss) {

        double totalOfBill, totBil = 0.0;
        double totalPriceOfBuy = 0.0;
        double totBuy = 0.0;
        double roundedTotalOfBuy = 0.0;
        double roundedTotalOfSale = 0.0;

        int a;
        try {

            con = connect();

            pres = con.prepareStatement("select * from purchasebills where DateCreation = " + "'" + sss + "'");
            ResultSet rs = pres.executeQuery();

            ResultSetMetaData rd = rs.getMetaData();
            a = rd.getColumnCount();
            df = (DefaultTableModel) tblBills.getModel();
            df.setRowCount(0);

            while (rs.next()) {

                Vector v2 = new Vector();

                v2.add(rs.getInt("id"));
                v2.add(rs.getString("nameOfClient"));
                v2.add(rs.getString("NumberOfBill"));
                v2.add(rs.getString("numberOfContract"));
                v2.add(rs.getDouble("TotalSumOfBill"));
                v2.add(rs.getString("WhoMade"));
                v2.add(rs.getString("DateCreation"));
                v2.add(rs.getString("TypeOfDocument"));
                v2.add(rs.getString("DateOfChange"));
                v2.add(rs.getString("whoChanged"));
                v2.add(rs.getString("dollarKurs"));
                v2.add(rs.getString("status"));
                totalOfBill = rs.getDouble("TotalSumOfBill");
                totBil += totalOfBill;
                roundedTotalOfSale = Math.round(totBuy * 100.000) / 100.000;
                totalPriceOfBuy = rs.getDouble("TotalSumOfBill");
                totBuy += totalPriceOfBuy;
                roundedTotalOfBuy = Math.round(totBuy * 100.000) / 100.000;

                df.addRow(v2);
                txtTotalOfBuy.setText(Double.toString(roundedTotalOfBuy));

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


    private void tblBillsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblBillsMouseClicked
        String commentary = null;
        int clickSayi = evt.getClickCount();

        if (clickSayi == 2) {
            try {
                con = connect();
                df = (DefaultTableModel) tblBills.getModel();

                int selected = tblBills.getSelectedRow();

                String qaimeYolu = "C:\\Alis Qaimeleri\\";
                String qaimeAdi = (df.getValueAt(selected, 1).toString());
                String billnum = (df.getValueAt(selected, 2).toString());
                String contcactNum = (df.getValueAt(selected, 3).toString());
                String total = (df.getValueAt(selected, 4).toString());
                String timeCreation = (df.getValueAt(selected, 6).toString());
                tamAd = qaimeAdi + "-" + billnum + ".txt";

                pres = con.prepareStatement("truncate table qaimeyolu;");
                pres.executeUpdate();

                pres = con.prepareStatement("insert into qaimeyolu (QaimeAdi, QaimeYolu, NumberOFContract, QaimeNum, Kimden, total, date) values(?,?,?,?,?,?,?)");
                pres.setString(1, tamAd);
                pres.setString(2, qaimeYolu);
                pres.setString(3, contcactNum);
                pres.setString(4, billnum);
                pres.setString(5, qaimeAdi);
                pres.setString(6, total);
                pres.setString(7, timeCreation);
                pres.executeUpdate();

                //Mehsullar mm = mehDao.getProductByBillingNum(billingNum);
                //readBillFromFile();
                OpenBillPurchase ss = new OpenBillPurchase();

                ss.path = tamAd;

                ss.setVisible(true);

            } catch (Exception ex) {

                ex.printStackTrace();

            }

        }
        if (clickSayi == 1) {

            if (transfer == 1) {

                try {
                    con = connect();
                    df = (DefaultTableModel) tblBills.getModel();

                    int selected = tblBills.getSelectedRow();

                    String qaimeYolu = "C:\\Alis Qaimeleri\\";
                    String qaimeAdi = (df.getValueAt(selected, 1).toString());
                    String billnum = (df.getValueAt(selected, 2).toString());
                    String contcactNum = (df.getValueAt(selected, 3).toString());
                    String total = (df.getValueAt(selected, 4).toString());
                    String timeCreation = (df.getValueAt(selected, 6).toString());
                    String kursDollar = (df.getValueAt(selected, 10).toString());
                    tamAd = qaimeAdi + "-" + billnum + ".txt";

                    pres = con.prepareStatement("select * from qaimeyolu");
                    rs = pres.executeQuery();
                    while (rs.next()) {
                        commentary = rs.getString("NumberOFContract");
                    }

                    pres = con.prepareStatement("update qaimeyolu set QaimeAdi=?, QaimeYolu=?, QaimeNum=?, kimden=?, total=?, date=?, kursDollar=?");
                    pres.setString(1, tamAd);
                    pres.setString(2, qaimeYolu);
                    pres.setString(3, billnum);
                    pres.setString(4, qaimeAdi);
                    pres.setString(5, total);
                    pres.setString(6, timeCreation);
                    pres.setString(7, kursDollar);
                    pres.executeUpdate();

                    pres = con.prepareStatement(" update qaimeyolu set NumberOFContract = ?");
                    pres.setString(1, commentary + "+" + contcactNum);
                    pres.executeUpdate();

                    if (purchaseInvoice == 1) {

//                        PurchaseInvoicesForCombainingPurchaseInvoices sss = new PurchaseInvoicesForCombainingPurchaseInvoices();
//                        sss.path = tamAd;
//                        transfer = 0;
//                        sss.setVisible(true);

                    } else {
//                        SaleInvoiceForCombinedInvoice ss = new SaleInvoiceForCombinedInvoice();
//                        ss.path = tamAd;
//                        transfer = 0;
//                        ss.setVisible(true);

                    }

                } catch (Exception ex) {

                    ex.printStackTrace();

                }

            }

        }
        oneClick();
        readBillFromFile();
    }//GEN-LAST:event_tblBillsMouseClicked

    private void readBillFromFile() {

        String[] row = {"salam"};

        try {

            pres = con.prepareCall("select * from qaimeyolu");

            ResultSet rs = pres.executeQuery();

            while (rs.next()) {
                String qaimeNum = (rs.getString("QaimeNum"));
                String numberOfContract = (rs.getString("NumberOFContract"));
                String QaimeAdi = (rs.getString("QaimeAdi"));
                String qaimeYolu = (rs.getString("QaimeYolu"));
                String kimden = (rs.getString("Kimden"));
                String totalsum = (rs.getString("total"));
                String dateCreation = (rs.getString("date"));
                String tamAd2 = qaimeYolu + QaimeAdi;

                File file = new File(tamAd2);

                FileReader fr = new FileReader(file);
                BufferedReader br = new BufferedReader(fr);

                df = (DefaultTableModel) tblPdoducts.getModel();
                df.setRowCount(0);
                //df.setColumnCount(0);
                Object[] lines = br.lines().toArray();
                Vector v2 = new Vector();
                for (int i = 0; i < lines.length; i++) {
                    v2.add(row = lines[i].toString().split(":"));
                    System.out.println(br.toString());
                    df.addRow(row);
                }

            }

        } catch (Exception ex) {

            ex.printStackTrace();
        }
    }

    public void oneClick() {

        try {

            con = connect();
            df = (DefaultTableModel) tblBills.getModel();

            int selected = tblBills.getSelectedRow();

            String qaimeYolu = "C:\\Alis Qaimeleri\\";
            String qaimeAdi = (df.getValueAt(selected, 1).toString());
            String billnum = (df.getValueAt(selected, 2).toString());
            String contcactNum = (df.getValueAt(selected, 3).toString());
            String total = (df.getValueAt(selected, 4).toString());
            String timeCreation = (df.getValueAt(selected, 6).toString());
            tamAd = qaimeAdi + "-" + billnum + ".txt";

            pres = con.prepareStatement("truncate table qaimeyolu;");
            pres.executeUpdate();

            pres = con.prepareStatement("insert into qaimeyolu (QaimeAdi, QaimeYolu, NumberOFContract, QaimeNum, Kimden, total, date) values(?,?,?,?,?,?,?)");
            pres.setString(1, tamAd);
            pres.setString(2, qaimeYolu);
            pres.setString(3, contcactNum);
            pres.setString(4, billnum);
            pres.setString(5, qaimeAdi);
            pres.setString(6, total);
            pres.setString(7, timeCreation);
            pres.executeUpdate();

        } catch (Exception ex) {

            ex.printStackTrace();
        }

    }


    private void formMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseEntered
        //getAllBills();

    }//GEN-LAST:event_formMouseEntered

    private void jPanel1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseEntered

        boolean yoxla = txtNameOfClient.hasFocus();
        String yoxla2 = txtNameOfClient.getText();

        if (yoxla != true) {

            if (yoxla2.equals("Найти..")) {

            } else {

                txtNameOfClient.setText("Найти..");

            }

        }

        System.out.println(yoxla);
    }//GEN-LAST:event_jPanel1MouseEntered

    private void tblBillsMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblBillsMouseEntered
        //getAllBills();
    }//GEN-LAST:event_tblBillsMouseEntered

    private void todayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_todayActionPerformed

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = new GregorianCalendar();
        String ss = sdf.format(cal.getTime());
        cal.add(Calendar.DAY_OF_MONTH, -1);

        String today = sdf.format(cal.getTime());
        loadToday(today);

    }//GEN-LAST:event_todayActionPerformed

    private void lastWeekActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lastWeekActionPerformed
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = new GregorianCalendar();
        String ss = sdf.format(cal.getTime());
        cal.add(Calendar.DAY_OF_MONTH, -7);

        String lastThreeDay = sdf.format(cal.getTime());
        loadForLastWeek(lastThreeDay);

    }//GEN-LAST:event_lastWeekActionPerformed

    private void lastMonthActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lastMonthActionPerformed

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = new GregorianCalendar();
        String ss = sdf.format(cal.getTime());
        cal.add(Calendar.DAY_OF_MONTH, -31);

        String lastThreeDay = sdf.format(cal.getTime());
        loadForLastWeek(lastThreeDay);
    }//GEN-LAST:event_lastMonthActionPerformed

    private void ПеренестиActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ПеренестиActionPerformed

        try {
            con = connect();
            df = (DefaultTableModel) tblBills.getModel();

            int selected = tblBills.getSelectedRow();

            String qaimeYolu = "C:\\Alis Qaimeleri\\";
            String qaimeAdi = (df.getValueAt(selected, 1).toString());
            String billnum = (df.getValueAt(selected, 2).toString());
            String contcactNum = (df.getValueAt(selected, 3).toString());
            String total = (df.getValueAt(selected, 4).toString());
            String timeCreation = (df.getValueAt(selected, 6).toString());
            String kursD = (df.getValueAt(selected, 10).toString());
            tamAd = qaimeAdi + "-" + billnum + ".txt";

            pres = con.prepareStatement("truncate table qaimeyolu;");
            pres.executeUpdate();

            pres = con.prepareStatement("insert into qaimeyolu (QaimeAdi, QaimeYolu, NumberOFContract, QaimeNum, Kimden, total, date, kursDollar) values(?,?,?,?,?,?,?,?)");
            pres.setString(1, tamAd);
            pres.setString(2, qaimeYolu);
            pres.setString(3, contcactNum);
            pres.setString(4, billnum);
            pres.setString(5, qaimeAdi);
            pres.setString(6, total);
            pres.setString(7, timeCreation);
            pres.setString(8, kursD);
            pres.executeUpdate();

            //Mehsullar mm = mehDao.getProductByBillingNum(billingNum);
            //readBillFromFile();
//            SaleInvoice ss = new SaleInvoice();

//            ss.path = tamAd;
//
//            ss.setVisible(true);

        } catch (Exception ex) {

            ex.printStackTrace();

        }


    }//GEN-LAST:event_ПеренестиActionPerformed

    private void tblBillsMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblBillsMouseReleased

        if (SwingUtilities.isRightMouseButton(evt)) {

            int clickSayi = evt.getClickCount();

            jPopupMenu1.show(tblBills, evt.getX(), evt.getY());

        }

    }//GEN-LAST:event_tblBillsMouseReleased

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        setPurchaseInvoicePath();
        bringProductsFromFileIntoDb();
        deleteBill();

    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

        transfer = 1;
        try {
            pres = con.prepareStatement("truncate table combinedPurchaseInvoices;");
            pres.executeUpdate();

            con = connect();
            df = (DefaultTableModel) tblBills.getModel();

            int selected = tblBills.getSelectedRow();

            String qaimeYolu = "C:\\Alis Qaimeleri\\";
            String qaimeAdi = (df.getValueAt(selected, 1).toString());
            String billnum = (df.getValueAt(selected, 2).toString());
            String contcactNum = (df.getValueAt(selected, 3).toString());
            String total = (df.getValueAt(selected, 4).toString());
            String timeCreation = (df.getValueAt(selected, 6).toString());
            tamAd = qaimeAdi + "-" + billnum + ".txt";

            pres = con.prepareStatement("truncate table qaimeyolu;");
            pres.executeUpdate();

            pres = con.prepareStatement("insert into qaimeyolu (QaimeAdi, QaimeYolu, NumberOFContract, QaimeNum, Kimden, total, date) values(?,?,?,?,?,?,?)");
            pres.setString(1, tamAd);
            pres.setString(2, qaimeYolu);
            pres.setString(3, contcactNum);
            pres.setString(4, billnum);
            pres.setString(5, qaimeAdi);
            pres.setString(6, total);
            pres.setString(7, timeCreation);
            pres.executeUpdate();

            //Mehsullar mm = mehDao.getProductByBillingNum(billingNum);
            //readBillFromFile();
//            SaleInvoiceForCombinedInvoice ss = new SaleInvoiceForCombinedInvoice();

//            ss.path = tamAd;

        } catch (Exception ex) {

            ex.printStackTrace();

        }


    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed

        transfer = 1;
        purchaseInvoice = 1;

        try {
            pres = con.prepareStatement("truncate table combinedPurchaseInvoices;");
            pres.executeUpdate();

            con = connect();
            df = (DefaultTableModel) tblBills.getModel();

            int selected = tblBills.getSelectedRow();

            String qaimeYolu = "C:\\Alis Qaimeleri\\";
            String qaimeAdi = (df.getValueAt(selected, 1).toString());
            String billnum = (df.getValueAt(selected, 2).toString());
            String contcactNum = (df.getValueAt(selected, 3).toString());
            String total = (df.getValueAt(selected, 4).toString());
            String timeCreation = (df.getValueAt(selected, 6).toString());
            tamAd = qaimeAdi + "-" + billnum + ".txt";

            pres = con.prepareStatement("truncate table qaimeyolu;");
            pres.executeUpdate();

            pres = con.prepareStatement("insert into qaimeyolu (QaimeAdi, QaimeYolu, NumberOFContract, QaimeNum, Kimden, total, date) values(?,?,?,?,?,?,?)");
            pres.setString(1, tamAd);
            pres.setString(2, qaimeYolu);
            pres.setString(3, contcactNum);
            pres.setString(4, billnum);
            pres.setString(5, qaimeAdi);
            pres.setString(6, total);
            pres.setString(7, timeCreation);
            pres.executeUpdate();

            //Mehsullar mm = mehDao.getProductByBillingNum(billingNum);
            //readBillFromFile();
//            SaleInvoiceForCombinedInvoice ss = new SaleInvoiceForCombinedInvoice();

//            ss.path = tamAd;

        } catch (Exception ex) {

            ex.printStackTrace();

        }


    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed

        Date firstDate2 = firstDate.getDate();
        Date secondDate2 = secondDate.getDate();

        System.out.println(firstDate2);
        System.out.println(secondDate2);

        if (firstDate2 != null && secondDate2 != null) {
            System.out.println("Beli qaqaw tarix null dan ferqlidir. Budur.. " + firstDate2);

            String firstDate1 = sdf.format(firstDate.getDate());
            String secondDAte = sdf.format(secondDate.getDate());

            betweenTwoDate(firstDate1, secondDAte);

        }

    }//GEN-LAST:event_jButton5ActionPerformed

    private void txtNameOfClientKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNameOfClientKeyReleased

        String nameOfClient = txtNameOfClient.getText();

        boolean yoxla = txtNameOfClient.hasFocus();

        System.out.println(yoxla);

        boolean checkLastMonth = lastMonth.isSelected();
        boolean checkLastWeek = lastWeek.isSelected();
        boolean chechYesterday = yesterday.isSelected();
        boolean checkToday = today.isSelected();
        Date firstDate2 = firstDate.getDate();
        Date secondDate2 = secondDate.getDate();

        System.out.println(firstDate2);
        System.out.println(secondDate2);

        if (firstDate2 != null && secondDate2 != null) {
            System.out.println("Beli qaqaw tarix null dan ferqlidir. Budur.. " + firstDate2);

            String firstDate1 = sdf.format(firstDate.getDate());
            String secondDAte = sdf.format(secondDate.getDate());

            betweenTwoDatesAndWithName(firstDate1, secondDAte, nameOfClient);

        } else {

            if (checkToday == true) {
                getTodaysBillsWithName(nameOfClient);
                //getTodaysExpenses();
            }
            if (chechYesterday == true) {

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Calendar cal = new GregorianCalendar();
                String ss = sdf.format(cal.getTime());
                cal.add(Calendar.DAY_OF_MONTH, -1);
                String lastThreeDay = sdf.format(cal.getTime());
                loadForYesterdayWithName(nameOfClient, lastThreeDay);
                //getYesterdaysExpenses();
            }
            if (checkLastWeek == true) {

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Calendar cal = new GregorianCalendar();
                String ss = sdf.format(cal.getTime());
                cal.add(Calendar.DAY_OF_MONTH, -7);
                String lastThreeDay = sdf.format(cal.getTime());
                loadForAweek(lastThreeDay, nameOfClient);

            }
            if (checkLastMonth == true) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Calendar cal = new GregorianCalendar();
                String ss = sdf.format(cal.getTime());
                cal.add(Calendar.DAY_OF_MONTH, -30);
                String lastThreeDay = sdf.format(cal.getTime());
                loadForAweek(lastThreeDay, nameOfClient);
                //getTheLastMonthsExpenses();
            }
        }


    }//GEN-LAST:event_txtNameOfClientKeyReleased

    private void txtNameOfClientMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtNameOfClientMouseEntered

        txtNameOfClient.setText("");


    }//GEN-LAST:event_txtNameOfClientMouseEntered

    public void getTodaysBillsWithName(String name) {

        loadForTodayWithName(name);

    }

    public void getYesterdaysBillsWithName(String name) {

        loadForTodayWithName(name);

    }

    public void loadForTodayWithName(String nameClient) {

        String option = cbOptionsForSerach.getSelectedItem().toString();
        int a;
        double allPurchaseBillsTotalSum = 0;
        double roundedTotalSumOfBills = 0;
        try {

            con = connect();
            if (option.equals("По комментарию")) {
                pres = con.prepareStatement("select * from purchasebills where DateCreation = CURRENT_DATE and numberOfContract like " + "'" + "%" + nameClient + "%" + "'");
            }
            if (option.equals("По названию организации")) {
                pres = con.prepareStatement("select * from purchasebills where DateCreation = CURRENT_DATE and nameOfClient like " + "'" + "%" + nameClient + "%" + "'");
            }
            ResultSet rs = pres.executeQuery();
            ResultSetMetaData rd = rs.getMetaData();
            a = rd.getColumnCount();
            df = (DefaultTableModel) tblBills.getModel();
            df.setRowCount(0);

            while (rs.next()) {
                Vector v2 = new Vector();
                int id = rs.getInt("id");
                String nameOfContractor = rs.getString("nameOfClient");
                String numberOfPurchaseBill = rs.getString("NumberOfBill");
                String commentary = rs.getString("numberOfContract");
                double totalSummOfBill = rs.getDouble("TotalSumOfBill");
                allPurchaseBillsTotalSum += totalSummOfBill;
                roundedTotalSumOfBills = Math.round(allPurchaseBillsTotalSum * 100.000) / 100.000;
                String whoCreated = rs.getString("whoChanged");
                String dateCreation = rs.getString("DateCreation");
                String typeOfDocument = rs.getString("TypeOfDocument");
                Date dateChange = rs.getDate("DateOfChange");
                String whoChanged = rs.getString("whoChanged");
                double kursDollar = rs.getDouble("dollarKurs");
                String status = rs.getString("status");

                v2.add(id);
                v2.add(nameOfContractor);
                v2.add(numberOfPurchaseBill);
                v2.add(commentary);
                v2.add(totalSummOfBill);
                v2.add(whoCreated);
                v2.add(dateCreation);
                v2.add(typeOfDocument);
                v2.add(dateChange);
                v2.add(whoChanged);
                v2.add(kursDollar);
                v2.add(status);
                df.addRow(v2);

            }

            txtTotalOfBuy.setText(Double.toString(roundedTotalSumOfBills));

        } catch (Exception ex) {

            ex.printStackTrace();

        }
    }

    public void loadForYesterdayWithName(String nameClient, String date) {

        String option = cbOptionsForSerach.getSelectedItem().toString();
        int a;
        double allPurchaseBillsTotalSum = 0;
        double roundedTotalSumOfBills = 0;
        try {

            con = connect();

            con = connect();
            if (option.equals("По комментарию")) {
                pres = con.prepareStatement("select * from purchasebills where DateCreation = " + "'" + date + "'" + " and numberOfContract like " + "'" + "%" + nameClient + "%" + "'");
            }
            if (option.equals("По названию организации")) {
                pres = con.prepareStatement("select * from purchasebills where DateCreation = " + "'" + date + "'" + " and nameOfClient like " + "'" + "%" + nameClient + "%" + "'");
            }
            ResultSet rs = pres.executeQuery();
            ResultSetMetaData rd = rs.getMetaData();
            a = rd.getColumnCount();
            df = (DefaultTableModel) tblBills.getModel();
            df.setRowCount(0);

            while (rs.next()) {
                Vector v2 = new Vector();
                int id = rs.getInt("id");
                String nameOfContractor = rs.getString("nameOfClient");
                String numberOfPurchaseBill = rs.getString("NumberOfBill");
                String commentary = rs.getString("numberOfContract");
                double totalSummOfBill = rs.getDouble("TotalSumOfBill");
                allPurchaseBillsTotalSum += totalSummOfBill;
                roundedTotalSumOfBills = Math.round(allPurchaseBillsTotalSum * 100.000) / 100.000;
                String whoCreated = rs.getString("whoChanged");
                String dateCreation = rs.getString("DateCreation");
                String typeOfDocument = rs.getString("TypeOfDocument");
                Date dateChange = rs.getDate("DateOfChange");
                String whoChanged = rs.getString("whoChanged");
                double kursDollar = rs.getDouble("dollarKurs");
                String status = rs.getString("status");

                v2.add(id);
                v2.add(nameOfContractor);
                v2.add(numberOfPurchaseBill);
                v2.add(commentary);
                v2.add(totalSummOfBill);
                v2.add(whoCreated);
                v2.add(dateCreation);
                v2.add(typeOfDocument);
                v2.add(dateChange);
                v2.add(whoChanged);
                v2.add(kursDollar);
                v2.add(status);
                df.addRow(v2);

            }

            txtTotalOfBuy.setText(Double.toString(roundedTotalSumOfBills));

        } catch (Exception ex) {

            ex.printStackTrace();

        }
    }

    public void betweenTwoDate(String firstDate, String secondDate) {

        int a;
        try {

            con = connect();

            pres = con.prepareStatement("select * from purchasebills where DateCreation BETWEEN " + "'" + firstDate + "'" + " and " + "'" + secondDate + "'");
            ResultSet rs = pres.executeQuery();

            ResultSetMetaData rd = rs.getMetaData();
            a = rd.getColumnCount();
            df = (DefaultTableModel) tblBills.getModel();
            df.setRowCount(0);

            double allPurchaseBillsTotalSum = 0;
            double roundedTotalSumOfBills = 0;

            while (rs.next()) {
                Vector v2 = new Vector();
                int id = rs.getInt("id");
                String nameOfContractor = rs.getString("nameOfClient");
                String numberOfPurchaseBill = rs.getString("NumberOfBill");
                String commentary = rs.getString("numberOfContract");
                double totalSummOfBill = rs.getDouble("TotalSumOfBill");
                allPurchaseBillsTotalSum += totalSummOfBill;
                roundedTotalSumOfBills = Math.round(allPurchaseBillsTotalSum * 100.000) / 100.000;
                String whoCreated = rs.getString("whoChanged");
                String dateCreation = rs.getString("DateCreation");
                String typeOfDocument = rs.getString("TypeOfDocument");
                Date dateChange = rs.getDate("DateOfChange");
                String whoChanged = rs.getString("whoChanged");
                double kursDollar = rs.getDouble("dollarKurs");
                String status = rs.getString("status");

                v2.add(id);
                v2.add(nameOfContractor);
                v2.add(numberOfPurchaseBill);
                v2.add(commentary);
                v2.add(totalSummOfBill);
                v2.add(whoCreated);
                v2.add(dateCreation);
                v2.add(typeOfDocument);
                v2.add(dateChange);
                v2.add(whoChanged);
                v2.add(kursDollar);
                v2.add(status);
                df.addRow(v2);
            }

            txtTotalOfBuy.setText(Double.toString(roundedTotalSumOfBills));

        } catch (Exception ex) {

            ex.printStackTrace();
        }
    }

    public void betweenTwoDatesAndWithName(String firstDate, String secondDate, String nameOfClient) {

        String option = cbOptionsForSerach.getSelectedItem().toString();

        int a;
        try {

            con = connect();

            if (option.equals("По комментарию")) {
                pres = con.prepareStatement("select * from purchasebills where DateCreation BETWEEN " + "'" + firstDate + "'" + " and " + "'" + secondDate + "'" + " and numberOfContract like " + "'" + "%" + nameOfClient + "%" + "'");
            }
            if (option.equals("По названию организации")) {
                pres = con.prepareStatement("select * from purchasebills where DateCreation BETWEEN " + "'" + firstDate + "'" + " and " + "'" + secondDate + "'" + " and nameOfClient like " + "'" + "%" + nameOfClient + "%" + "'");
            }
            ResultSet rs = pres.executeQuery();

            ResultSetMetaData rd = rs.getMetaData();
            a = rd.getColumnCount();
            df = (DefaultTableModel) tblBills.getModel();
            df.setRowCount(0);

            double allPurchaseBillsTotalSum = 0;
            double roundedTotalSumOfBills = 0;

            while (rs.next()) {
                Vector v2 = new Vector();
                int id = rs.getInt("id");
                String nameOfContractor = rs.getString("nameOfClient");
                String numberOfPurchaseBill = rs.getString("NumberOfBill");
                String commentary = rs.getString("numberOfContract");
                double totalSummOfBill = rs.getDouble("TotalSumOfBill");
                allPurchaseBillsTotalSum += totalSummOfBill;
                roundedTotalSumOfBills = Math.round(allPurchaseBillsTotalSum * 100.000) / 100.000;
                String whoCreated = rs.getString("whoChanged");
                String dateCreation = rs.getString("DateCreation");
                String typeOfDocument = rs.getString("TypeOfDocument");
                Date dateChange = rs.getDate("DateOfChange");
                String whoChanged = rs.getString("whoChanged");
                double kursDollar = rs.getDouble("dollarKurs");
                String status = rs.getString("status");

                v2.add(id);
                v2.add(nameOfContractor);
                v2.add(numberOfPurchaseBill);
                v2.add(commentary);
                v2.add(totalSummOfBill);
                v2.add(whoCreated);
                v2.add(dateCreation);
                v2.add(typeOfDocument);
                v2.add(dateChange);
                v2.add(whoChanged);
                v2.add(kursDollar);
                v2.add(status);
                df.addRow(v2);
            }

            txtTotalOfBuy.setText(Double.toString(roundedTotalSumOfBills));

        } catch (Exception ex) {

            ex.printStackTrace();
        }
    }

    public void loadForAweek(String secondDate, String nameOfClient) {
        String option = cbOptionsForSerach.getSelectedItem().toString();
        int a;
        try {

            con = connect();

            con = connect();
            if (option.equals("По комментарию")) {
                pres = con.prepareStatement("select * from purchasebills where DateCreation BETWEEN " + "'" + secondDate + "'" + "and CURRENT_DATE " + " and numberOfContract like " + "'" + "%" + nameOfClient + "%" + "'");
            }
            if (option.equals("По названию организации")) {
                pres = con.prepareStatement("select * from purchasebills where DateCreation BETWEEN " + "'" + secondDate + "'" + "and CURRENT_DATE " + " and nameOfClient like " + "'" + "%" + nameOfClient + "%" + "'");
            }

            ResultSet rs = pres.executeQuery();

            ResultSetMetaData rd = rs.getMetaData();
            a = rd.getColumnCount();
            df = (DefaultTableModel) tblBills.getModel();
            df.setRowCount(0);

            double allPurchaseBillsTotalSum = 0;
            double roundedTotalSumOfBills = 0;

            while (rs.next()) {
                Vector v2 = new Vector();
                int id = rs.getInt("id");
                String nameOfContractor = rs.getString("nameOfClient");
                String numberOfPurchaseBill = rs.getString("NumberOfBill");
                String commentary = rs.getString("numberOfContract");
                double totalSummOfBill = rs.getDouble("TotalSumOfBill");
                allPurchaseBillsTotalSum += totalSummOfBill;
                roundedTotalSumOfBills = Math.round(allPurchaseBillsTotalSum * 100.000) / 100.000;
                String whoCreated = rs.getString("whoChanged");
                String dateCreation = rs.getString("DateCreation");
                String typeOfDocument = rs.getString("TypeOfDocument");
                Date dateChange = rs.getDate("DateOfChange");
                String whoChanged = rs.getString("whoChanged");
                double kursDollar = rs.getDouble("dollarKurs");
                String status = rs.getString("status");

                v2.add(id);
                v2.add(nameOfContractor);
                v2.add(numberOfPurchaseBill);
                v2.add(commentary);
                v2.add(totalSummOfBill);
                v2.add(whoCreated);
                v2.add(dateCreation);
                v2.add(typeOfDocument);
                v2.add(dateChange);
                v2.add(whoChanged);
                v2.add(kursDollar);
                v2.add(status);
                df.addRow(v2);
            }

            txtTotalOfBuy.setText(Double.toString(roundedTotalSumOfBills));

        } catch (Exception ex) {

            ex.printStackTrace();
        }
    }

    public void bringProductsFromFileIntoDb() {

        try {
            pres = con.prepareStatement("truncate table openpurchasedinvoice");
            pres.executeUpdate();

            OpenBillPurchase open = new OpenBillPurchase();

        } catch (SQLException ex) {

            ex.printStackTrace();

        }

    }

    public void deleteBill() {

        df = (DefaultTableModel) tblBills.getModel();
        int selected = tblBills.getSelectedRow();
        int id2 = Integer.parseInt(df.getValueAt(selected, 0).toString());
        String name = (df.getValueAt(selected, 1).toString());
        double totalPurchaseSumm = Double.parseDouble(df.getValueAt(selected, 4).toString());
        String numberOfBill = (df.getValueAt(selected, 2).toString());
        String status = (df.getValueAt(selected, 11).toString());

        try {

            con = connect();
            pres = con.prepareStatement("select * from openpurchasedinvoice");
            rs = pres.executeQuery();

            while (rs.next()) {

                int id = rs.getInt("id");
                double say = rs.getDouble("numberOfProduct");
                double total = rs.getDouble("total");

                pres = con.prepareStatement("update mehsullar set Qaliq_say = Qaliq_say - " + say + ", Miqdari = Miqdari - " + say + " where id = " + id);
                pres.executeUpdate();
            }

            con = connect();
            pres = con.prepareStatement("delete from purchasebills where id =  " + id2);
            pres.executeUpdate();

            if (status.equals("Оплаченный!")) {

                con = connect();
                pres = con.prepareStatement("delete from paidpurchasebills where NumberOfBill =  " +"'"+numberOfBill+"'");
                pres.executeUpdate();

                pres = con.prepareStatement("select * from updatedCapitalbudget order by id desc limit 1");
                rs = pres.executeQuery();

                rs.next();

                double capitalBudget = rs.getDouble("AmountOfCapitalBudget");

                double result = capitalBudget + totalPurchaseSumm;
                double roundedResult = Math.round(result * 100.000) / 100.000;

                pres = con.prepareStatement("insert into updatedCapitalbudget (AmountOfCapitalBudget, date) values(?,?)");
                pres.setDouble(1, roundedResult);
                pres.setString(2, currentDate);
                pres.executeUpdate();
            }

            boolean yoxlaBugun = today.isSelected();
            boolean yoxlaDunen = yesterday.isSelected();
            boolean yoxlaHefte = lastWeek.isSelected();
            boolean yoxlaAy = lastMonth.isSelected();

            if (yoxlaBugun == true) {
                getTodaysBills();
                JOptionPane.showMessageDialog(this, "Накладной с именем -" + name + " успешно удален.", "DIQQET!", HEIGHT);
            }
            if (yoxlaDunen == true) {
                getYesterdayBills();
                JOptionPane.showMessageDialog(this, "Накладной с именем -" + name + " успешно удален.", "DIQQET!", HEIGHT);
            }
            if (yoxlaHefte == true) {
                lastWeek();
                JOptionPane.showMessageDialog(this, "Накладной с именем -" + name + " успешно удален.", "DIQQET!", HEIGHT);
            }
            if (yoxlaAy == true) {
                getMonthBills();
                JOptionPane.showMessageDialog(this, "Накладной с именем -" + name + " успешно удален.", "DIQQET!", HEIGHT);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void setPurchaseInvoicePath() {

        try {
            con = connect();
            df = (DefaultTableModel) tblBills.getModel();

            int selected = tblBills.getSelectedRow();

            String qaimeYolu = "C:\\Alis Qaimeleri\\";
            String qaimeAdi = (df.getValueAt(selected, 1).toString());
            String billnum = (df.getValueAt(selected, 2).toString());
            String contcactNum = (df.getValueAt(selected, 3).toString());
            String total = (df.getValueAt(selected, 4).toString());
            String timeCreation = (df.getValueAt(selected, 6).toString());
            tamAd = qaimeAdi + "-" + billnum + ".txt";

            pres = con.prepareStatement("truncate table qaimeyolu;");
            pres.executeUpdate();

            pres = con.prepareStatement("insert into qaimeyolu (QaimeAdi, QaimeYolu, NumberOFContract, QaimeNum, Kimden, total, date) values(?,?,?,?,?,?,?)");
            pres.setString(1, tamAd);
            pres.setString(2, qaimeYolu);
            pres.setString(3, contcactNum);
            pres.setString(4, billnum);
            pres.setString(5, qaimeAdi);
            pres.setString(6, total);
            pres.setString(7, timeCreation);
            pres.executeUpdate();

        } catch (Exception ex) {

            ex.printStackTrace();

        }

    }

    public void getMonthBills() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = new GregorianCalendar();
        String ss = sdf.format(cal.getTime());
        cal.add(Calendar.DAY_OF_MONTH, -31);

        String lastThreeDay = sdf.format(cal.getTime());
        loadForLastWeek(lastThreeDay);
    }

    public void lastWeek() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = new GregorianCalendar();
        String ss = sdf.format(cal.getTime());
        cal.add(Calendar.DAY_OF_MONTH, -7);

        String lastThreeDay = sdf.format(cal.getTime());
        loadForLastWeek(lastThreeDay);

    }

    public void getTodaysBills() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = new GregorianCalendar();
        String ss = sdf.format(cal.getTime());
        cal.add(Calendar.DAY_OF_MONTH, -1);

        String lastThreeDay = sdf.format(cal.getTime());
        loadToday(lastThreeDay);
    }

    public void getYesterdayBills() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = new GregorianCalendar();
        String ss = sdf.format(cal.getTime());
        cal.add(Calendar.DAY_OF_MONTH, -1);
        String lastThreeDay = sdf.format(cal.getTime());
        loadForYesterday(lastThreeDay);

    }

    public void loadForLastWeek(String sss) {

        double totalOfBill, totBil = 0.0;
        double totalPriceOfBuy = 0.0;
        double totBuy = 0.0;
        double roundedTotalOfBuy = 0.0;
        double roundedTotalOfSale = 0.0;

        int a;
        try {

            con = connect();

            pres = con.prepareStatement("select * from purchasebills where DateCreation BETWEEN " + "'" + sss + "'" + " and CURRENT_DATE");
            ResultSet rs = pres.executeQuery();

            ResultSetMetaData rd = rs.getMetaData();
            a = rd.getColumnCount();
            df = (DefaultTableModel) tblBills.getModel();
            df.setRowCount(0);

            while (rs.next()) {

                Vector v2 = new Vector();

                v2.add(rs.getInt("id"));
                v2.add(rs.getString("nameOfClient"));
                v2.add(rs.getString("NumberOfBill"));
                v2.add(rs.getString("numberOfContract"));
                v2.add(rs.getDouble("TotalSumOfBill"));
                v2.add(rs.getString("WhoMade"));
                v2.add(rs.getString("DateCreation"));
                v2.add(rs.getString("TypeOfDocument"));
                v2.add(rs.getString("DateOfChange"));
                v2.add(rs.getString("whoChanged"));
                v2.add(rs.getString("dollarKurs"));
                String status = rs.getString("status");
                v2.add(status);
                totalOfBill = rs.getDouble("TotalSumOfBill");
                totBil += totalOfBill;
                roundedTotalOfSale = Math.round(totBuy * 100.000) / 100.000;
                totalPriceOfBuy = rs.getDouble("TotalSumOfBill");
                totBuy += totalPriceOfBuy;
                roundedTotalOfBuy = Math.round(totBuy * 100.000) / 100.000;

                df.addRow(v2);
                txtTotalOfBuy.setText(Double.toString(roundedTotalOfBuy));
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void loadToday(String sss) {

        double totalOfBill, totBil = 0.0;
        double totalPriceOfBuy = 0.0;
        double totBuy = 0.0;
        double roundedTotalOfBuy = 0.0;
        double roundedTotalOfSale = 0.0;

        int a;
        try {

            con = connect();

            pres = con.prepareStatement("select * from purchasebills where DateCreation = CURRENT_DATE");
            ResultSet rs = pres.executeQuery();

            ResultSetMetaData rd = rs.getMetaData();
            a = rd.getColumnCount();
            df = (DefaultTableModel) tblBills.getModel();
            df.setRowCount(0);

            while (rs.next()) {

                Vector v2 = new Vector();

                v2.add(rs.getInt("id"));
                v2.add(rs.getString("nameOfClient"));
                v2.add(rs.getString("NumberOfBill"));
                v2.add(rs.getString("numberOfContract"));
                v2.add(rs.getDouble("TotalSumOfBill"));
                v2.add(rs.getString("WhoMade"));
                String date = rs.getString("DateCreation");
                v2.add(date);
                v2.add(rs.getString("TypeOfDocument"));
                v2.add(rs.getString("DateOfChange"));
                v2.add(rs.getString("whoChanged"));
                v2.add(rs.getDouble("dollarKurs"));
                v2.add(rs.getString("status"));

                totalOfBill = rs.getDouble("TotalSumOfBill");
                totBil += totalOfBill;
                roundedTotalOfSale = Math.round(totBil * 100.000) / 100.000;
                totalPriceOfBuy = rs.getDouble("TotalSumOfBill");
                totBuy += totalPriceOfBuy;
                roundedTotalOfBuy = Math.round(totBuy * 100.000) / 100.000;

                txtTotalOfBuy.setText(Double.toString(roundedTotalOfBuy));
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
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PurchaseBills.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PurchaseBills.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PurchaseBills.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PurchaseBills.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PurchaseBills().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cbOptionsForSerach;
    private com.toedter.calendar.JDateChooser firstDate;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JRadioButton lastMonth;
    private javax.swing.JRadioButton lastWeek;
    public static javax.swing.JRadioButton rbtnOtherBase;
    private com.toedter.calendar.JDateChooser secondDate;
    private javax.swing.JTable tblBills;
    private javax.swing.JTable tblPdoducts;
    private javax.swing.JRadioButton today;
    private javax.swing.JTextField txtNameOfClient;
    private javax.swing.JTextField txtTotalOfBuy;
    private javax.swing.JRadioButton yesterday;
    private javax.swing.JMenuItem Перенести;
    // End of variables declaration//GEN-END:variables
}
