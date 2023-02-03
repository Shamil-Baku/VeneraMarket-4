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
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author samil
 */
public class AlisQaimesiMehsullar extends javax.swing.JFrame {

    int i, qaimeN, qlobalMiqdar;

    Date date = new Date();
    Calendar calendar;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");
    String date2 = sdf.format(date);

    String path;
    public MehsullarDaoInter mehDao = Contex.instanceOfMehsullarDao();
    public AltKateqoriyalarDaoInter mehDao2 = Contex.instanceOfAltKateqoriyalarDao();
    Connection con;
    PreparedStatement pres;
    static DefaultTableModel df;

    public AlisQaimesiMehsullar() throws Exception {
        initComponents();
        connect();
        txtAlisTarixi.setText(date2);
        setScreenSize();
        readBillFromFile();
        bringProductsFromFileIntoDb();
        qaimeninHesablanmasi();
        //setBillingNumber();
        //readBillFromFile();

    }

    public void setScreenSize() {

        Toolkit toolkit = getToolkit();
        Dimension size = toolkit.getScreenSize();
        setLocation(size.width / 2 - getWidth() / 2, size.height / 2 - getHeight() / 2);

    }

    public void connect() throws Exception {

        Class.forName("com.mysql.cj.jdbc.Driver");

        con = DriverManager.getConnection("jdbc:mysql://localhost/mehsullar", "root", "dxdiag92");

    }

//    public void setBillingNumber() {
//
//        Mehsullar sonuncuMehsul = mehDao.getTheLastBillingNum();
//
//        if (sonuncuMehsul == null) {
//
//            txtQaimeNum.setText("1");
//
//        } else {
//
//            int billinNumber = sonuncuMehsul.getBillingNum();
//
//            if (billinNumber == 0) {
//
//                billinNumber = 1;
//                txtQaimeNum.setText(Integer.toString(billinNumber));
//            } else {
//
//                txtQaimeNum.setText(Integer.toString(billinNumber + 1));
//            }
//
//        }
//
//    }
    public void yaddaSaxla() {

        try {
            String kimden = txtKimden.getText();
            String qurum = txtQurum.getText();
            int qaimeNumresi = Integer.parseInt(txtQaimeNum.getText());
            i = qaimeNumresi;
            double qaimeMeblegi = Double.parseDouble(txtCemMebleg.getText());
            String tarix = txtAlisTarixi.getText();

            pres = con.prepareStatement("update alisqaimeleri set Kimden=?, Qurum=?, QaimeNomresi=?, QaimeMeblegi=?, YenilenmeTarixi=?, Status=? where QaimeNomresi=?");
            pres.setString(1, kimden);
            pres.setString(2, qurum);
            pres.setInt(3, qaimeNumresi);
            pres.setDouble(4, qaimeMeblegi);
            pres.setString(5, tarix);
            pres.setString(6, "Yenilendi");
            pres.setInt(7, qaimeNumresi);
            pres.executeUpdate();

            pres = con.prepareCall("select * from qaimededeyisiklik");

            ResultSet rs = pres.executeQuery();

            while (rs.next()) {

                int id = rs.getInt("id");
                String malinAdi = rs.getString("Malin_adi");
                int miqdari = rs.getInt("Miqdari");
                double alisQiymeti = rs.getDouble("Alis_qiymeti");
                double satisQiymeti = rs.getDouble("Satis_qiymeti");
                double umumiMebleg = rs.getDouble("Alisin_toplam_deyeri");
                int movsumID = rs.getInt("Movsum_id");
                int kateqoriyaID = rs.getInt("Kateqoriya_id");
                int altKateqoriyaID = rs.getInt("Alt_kateqoriya_id");
                String status = rs.getString("Status");
                String kohneStatus = rs.getString("SilinmezdenOncekiStatusu");
                String satisTarixi = rs.getString("Alis_Tarixi");
                String barcode = rs.getString("Barcode");

                if ("Plus".equals(status)) {

                    pres = con.prepareStatement("update mehsullar set Malin_adi=?, Miqdari = Miqdari + ?, Qaliq_say=Qaliq_say+?, Alis_qiymeti=?, Satis_qiymeti =?, Alis_Tarixi=?, Alisin_toplam_deyer = Alisin_toplam_deyer + ? where id =?");

                    double alisinToplamDeyeri = miqdari * alisQiymeti;

                    pres.setString(1, malinAdi);
                    pres.setInt(2, miqdari);
                    pres.setInt(3, miqdari);
                    pres.setDouble(4, alisQiymeti);
                    pres.setDouble(5, satisQiymeti);
                    pres.setString(6, tarix);
                    pres.setDouble(7, alisinToplamDeyeri);
                    pres.setInt(8, id);
                    pres.executeUpdate();

                }
                if ("Minus".equals(status)) {

                    pres = con.prepareStatement("update mehsullar set Malin_adi=?, Miqdari = Miqdari - ?, Qaliq_say=Qaliq_say-?, Alis_qiymeti=?, Satis_qiymeti =?, Alis_Tarixi=?, Alisin_toplam_deyer = Alisin_toplam_deyer - ? where id =?");

                    double alisinToplamDeyeri = miqdari * alisQiymeti;

                    pres.setString(1, malinAdi);
                    pres.setInt(2, miqdari);
                    pres.setInt(3, miqdari);
                    pres.setDouble(4, alisQiymeti);
                    pres.setDouble(5, satisQiymeti);
                    pres.setString(6, tarix);
                    pres.setDouble(7, alisinToplamDeyeri);
                    pres.setInt(8, id);
                    pres.executeUpdate();

                }
                if ("Silindi".equals(status)) {

                    if ("Kohne".equals(kohneStatus) || "Yenilendi".equals(kohneStatus)) {

                        Mehsullar mm = mehDao.getMehsulById(id);
                        int faktikMiqdar = mm.getHowMuch();

                        if (miqdari < faktikMiqdar) {

                            pres = con.prepareStatement("update mehsullar set Malin_adi=?, Miqdari = Miqdari - ?, Qaliq_say=Qaliq_say-?, Alis_qiymeti=?, Satis_qiymeti =?, Alis_Tarixi=?, Alisin_toplam_deyer = Alisin_toplam_deyer - ? where id =?");

                            double alisinToplamDeyeri = miqdari * alisQiymeti;

                            pres.setString(1, malinAdi);
                            pres.setInt(2, miqdari);
                            pres.setInt(3, miqdari);
                            pres.setDouble(4, alisQiymeti);
                            pres.setDouble(5, satisQiymeti);
                            pres.setString(6, tarix);
                            pres.setDouble(7, alisinToplamDeyeri);
                            pres.setInt(8, id);
                            pres.executeUpdate();

                        }
                        if (miqdari >= faktikMiqdar) {

                            pres = con.prepareStatement("delete from mehsullar where id = ?");

                            pres.setInt(1, id);

                            pres.executeUpdate();

                        }

                    }

//                    
//                    
//                    
//                    
//                    pres = con.prepareStatement("update mehsullar set Malin_adi=?, Miqdari = Miqdari - ?, Qaliq_say=Qaliq_say+?, Alis_qiymeti=?, Satis_qiymeti =?, Alis_Tarixi=?, Alisin_toplam_deyer = Alisin_toplam_deyer - ? where id =?");
//
//                    double alisinToplamDeyeri = miqdari * alisQiymeti;
//
//                    pres.setString(1, malinAdi);
//                    pres.setInt(2, miqdari);
//                    pres.setInt(3, miqdari);
//                    pres.setDouble(4, alisQiymeti);
//                    pres.setDouble(5, satisQiymeti);
//                    pres.setString(6, tarix);
//                    pres.setDouble(7, alisinToplamDeyeri);
//                    pres.setInt(8, id);
//                    pres.executeUpdate();
//
//                }
//
//            }
//
//            df = (DefaultTableModel) tblMehsullar.getModel();
//
//            int rowCount = tblMehsullar.getRowCount();
//
//            int rowCount2;
//            rowCount2 = rowCount - rowCount;
//
//            for (int j = 0; j < rowCount; j++) {
//
//                int id = (Integer.parseInt(df.getValueAt(rowCount2, 0).toString()));
//                String mehsulunAdi = (df.getValueAt(rowCount2, 1).toString());
//                int miqdari = Integer.parseInt(df.getValueAt(rowCount2, 2).toString());
//                int qaliqSay = Integer.parseInt(df.getValueAt(rowCount2, 3).toString());
//                double alisQiymeti = Double.parseDouble(df.getValueAt(rowCount2, 4).toString());
//                double satisQiymeti = Double.parseDouble(df.getValueAt(rowCount2, 5).toString());
//                int movsumID = Integer.parseInt(df.getValueAt(rowCount2, 6).toString());
//                int kateqoriyaID = Integer.parseInt(df.getValueAt(rowCount2, 7).toString());
//                int AltkateqoriyaID = Integer.parseInt(df.getValueAt(rowCount2, 8).toString());
//                String tarix2 = (df.getValueAt(rowCount2, 9).toString());
//                double umumiMebleg = Double.parseDouble(df.getValueAt(rowCount2, 10).toString());
//                String barcode = (df.getValueAt(rowCount2, 11).toString());
//                String status = (df.getValueAt(rowCount2, 12).toString());
//
//                if ("Yenilendi".equals(status)) {
////                    Mehsullar mm = mehDao.getMehsulById(id);
////                    int qaliqsay = mm.getHowMuch();
//
//                    Mehsullar mm2 = mehDao.getMehsulByIdAcilanQaime(id);
//                    int qaliqsayKohne = mm2.getHowMuch();
//
//                    if (miqdari > qaliqsayKohne) {
//
//                        pres = con.prepareStatement("update mehsullar set Malin_adi=?, Miqdari = Miqdari + ?, Qaliq_say=?, Alis_qiymeti=?, Satis_qiymeti =?, Alis_Tarixi=?, Alisin_toplam_deyer = Alisin_toplam_deyer + ? where Barcode =?");
//
//                        pres.setString(1, mehsulunAdi);
//                        pres.setInt(2, miqdari);
//                        pres.setInt(3, qaliqSay);
//                        pres.setDouble(4, alisQiymeti);
//                        pres.setDouble(5, satisQiymeti);
//                        pres.setString(6, tarix);
//                        pres.setDouble(7, umumiMebleg);
//                        pres.setString(8, barcode);
//                        pres.executeUpdate();
//
//                    }
//                    if (qaliqsayKohne > miqdari) {
//
//                        pres = con.prepareStatement("update mehsullar set Malin_adi=?, Miqdari = Miqdari - ?, Qaliq_say=?, Alis_qiymeti=?, Satis_qiymeti =?, Alis_Tarixi=?, Alisin_toplam_deyer = Alisin_toplam_deyer - ? where Barcode =?");
//
//                        pres.setString(1, mehsulunAdi);
//                        pres.setInt(2, miqdari);
//                        pres.setInt(3, qaliqSay);
//                        pres.setDouble(4, alisQiymeti);
//                        pres.setDouble(5, satisQiymeti);
//                        pres.setString(6, tarix);
//                        pres.setDouble(7, umumiMebleg);
//                        pres.setString(8, barcode);
//                        pres.executeUpdate();
//
//                    }
//                } else {
//
//                }
//                rowCount2++;
                }
                if ("Elave olundu".equals(status)) {

                    pres = con.prepareStatement("insert into mehsullar ( Malin_adi, Miqdari, Alis_qiymeti, Satis_Miqdari, Satis_qiymeti, Satisin_toplam_deyeri, Qaliq_say, Movsum_id, Kateqoriya_id, Alt_kateqoriya_id, Alis_Tarixi, Alisin_toplam_deyer, Kimden, QurumAdi, QaimeNum, Barcode ) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

                    pres.setString(1, malinAdi);
                    pres.setInt(2, miqdari);
                    pres.setDouble(3, alisQiymeti);
                    pres.setInt(4, 0);
                    pres.setDouble(5, satisQiymeti);
                    pres.setDouble(6, 0.0);
                    pres.setInt(7, miqdari);
                    pres.setInt(8, movsumID);
                    pres.setInt(9, kateqoriyaID);
                    pres.setInt(10, altKateqoriyaID + 1);
                    pres.setString(11, satisTarixi);
                    pres.setDouble(12, umumiMebleg);
                    pres.setString(13, kimden);
                    pres.setString(14, qurum);
                    pres.setFloat(15, qaimeNumresi);
                    pres.setString(16, barcode);
                    pres.executeUpdate();

                }

                pres = con.prepareStatement("truncate table acilanqaime;");
                pres.executeUpdate();
                load();

                txtKimden.setText("");
                txtQurum.setText("");
                txtCemMebleg.setText("");
                txtAlisTarixi.setText(date2);

                //setBillingNumber();
                pres = con.prepareStatement("truncate table qaimeadlari;");
                pres.executeUpdate();

                pres = con.prepareStatement("truncate table qaimededeyisiklik;");
                pres.executeUpdate();

                JOptionPane.showMessageDialog(this, qaimeNumresi + "№-li qaime yadda saxlanildi ");

                load();
            }
        } catch (SQLException ex) {
            Logger.getLogger(AlisQaimesi.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void writeFileFromJtableToFile() {

        String qaimeAdi = txtKimden.getText();
        int qaimeNum = Integer.parseInt(txtQaimeNum.getText());
        String extensionOfFile = ".txt";
        String tamAd = qaimeAdi + "-" + qaimeNum + extensionOfFile;
        String filePath = "C:\\Alis Qaimeleri\\";

        String pathName = filePath + tamAd;

        File file = new File(pathName);

        try {
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);

            for (int j = 0; j < tblMehsullar.getRowCount(); j++) {
                for (int k = 0; k < tblMehsullar.getColumnCount(); k++) {
                    bw.write(tblMehsullar.getValueAt(j, k).toString() + ":");

                }
                bw.newLine();

            }

            bw.close();
            fw.close();

        } catch (IOException ex) {
            Logger.getLogger(AlisQaimesi.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void ElaveEt() throws DocumentException, IOException {

        try {
            if (txtMalinAdi.getText().isBlank() || txtMiqdari.getText().isBlank() || txtMovsumId.getText().isBlank() || txtKateqoriyaID.getText().isBlank() || cbAltKateqoriyaID.getSelectedItem().toString().isBlank() || txtAlisTarixi.getText().isBlank()) {

                JOptionPane.showMessageDialog(this, "Zəhmət olmasa bütün məlumatları doldurun");
            } else {

                Mehsullar mehsul5 = mehDao.getTheLastMehsulByIdAcilanQaime();
                // int sayId = mehsul5.getId();

                if (mehsul5 == null) {

                    Mehsullar mehsul = mehDao.getTheLastMehsulById();
                    int id = mehsul.getId();

                    String kimden = txtKimden.getText();
                    String qurum = txtQurum.getText();

                    float qaimenomresi = Float.parseFloat(txtQaimeNum.getText());

                    String malinadi = txtMalinAdi.getText();
                    int miqdari = Integer.parseInt(txtMiqdari.getText());
                    double qiymet = Double.parseDouble(txtAlisQiymeti.getText());
                    double satisQiymeti = Double.parseDouble(txtSatisQiymeti.getText());
                    int kateqoriyaId = Integer.parseInt(txtKateqoriyaID.getText());
                    int movsumId = Integer.parseInt(txtMovsumId.getText());
                    int altKateqoriya = cbAltKateqoriyaID.getSelectedIndex();
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

                    pres = con.prepareStatement("insert into acilanqaime ( id, Malin_adi, Miqdari, Alis_qiymeti,Satis_Miqdari,Satis_qiymeti, Satisin_toplam_deyeri, Qaliq_say, Movsum_id, Kateqoriya_id, Alt_kateqoriya_id, Alis_Tarixi, UmumiMebleg, Kimden, QurumAdi, QaimeNum, Status ) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

                    pres.setInt(1, id + 1);
                    pres.setString(2, malinadi);
                    pres.setInt(3, miqdari);
                    pres.setDouble(4, qiymet);
                    pres.setInt(5, Satis_Miqdari);
                    pres.setDouble(6, satisQiymeti);
                    pres.setDouble(7, Satisin_Toplam_Deyeri);
                    pres.setDouble(8, miqdari);
                    pres.setInt(9, movsumId);
                    pres.setInt(10, kateqoriyaId);
                    pres.setInt(11, altKateqoriya + 1);
                    pres.setString(12, alisTarixi);
                    pres.setDouble(13, umumiMebleg);
                    pres.setString(14, kimden);
                    pres.setString(15, qurum);
                    pres.setFloat(16, qaimenomresi);
                    pres.setString(17, "Elave olundu");
                    pres.executeUpdate();

                    Mehsullar mehsul2 = mehDao.getTheLastMehsulById3();

                    String ss2 = mehsul2.getId2();

                    int sss2 = (mehsul2.getId());

                    int i = 0;

                    pres = con.prepareStatement("update acilanqaime set Barcode=? where id =" + sss2);
                    pres.setString(1, "1234500" + sss2);
                    pres.executeUpdate();
                    txtBarcode.setText("1234500" + sss2);

                    String barcode = "1234500" + sss2;

                    load();

                    String alis = Double.toString(satisQiymeti);
                    alis.length();

                    String satisQiy = generateBarcode.test2(satisQiymeti);

                    System.out.println(satisQiy);

                    generateBarcode.main(barcode, malinadi, "****", "****", satisQiy);

                    JOptionPane.showMessageDialog(this, "Mehsul ugurla elave olundu");

                    txtMalinAdi.setText("");
                    txtMiqdari.setText("");
                    txtQaliqSay.setText("");
                    txtAlisQiymeti.setText("");
                    txtSatisQiymeti.setText("");
                    txtQaliqSay.setText("");
                    txtMovsumId.setText("");
                    txtKateqoriyaID.setText("");
                    cbAltKateqoriyaID.removeAllItems();

                    txtUmumiMebleg.setText("");
                    txtMalinAdi.requestFocus();

                } else {

                    String status = (mehsul5.getStatus());

                    if ("Kohne".equals(status) || "Yenilendi".equals(status)) {

                        Mehsullar mehsul = mehDao.getTheLastMehsulById();
                        int id = mehsul.getId();

                        String kimden = txtKimden.getText();
                        String qurum = txtQurum.getText();

                        float qaimenomresi = Float.parseFloat(txtQaimeNum.getText());

                        String malinadi = txtMalinAdi.getText();
                        int miqdari = Integer.parseInt(txtMiqdari.getText());
                        double qiymet = Double.parseDouble(txtAlisQiymeti.getText());
                        double satisQiymeti = Double.parseDouble(txtSatisQiymeti.getText());
                        int kateqoriyaId = Integer.parseInt(txtKateqoriyaID.getText());
                        int movsumId = Integer.parseInt(txtMovsumId.getText());
                        int altKateqoriya = cbAltKateqoriyaID.getSelectedIndex();
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

                        pres = con.prepareStatement("insert into acilanqaime ( id, Malin_adi, Miqdari, Alis_qiymeti,Satis_Miqdari,Satis_qiymeti, Satisin_toplam_deyeri, Qaliq_say, Movsum_id, Kateqoriya_id, Alt_kateqoriya_id, Alis_Tarixi, UmumiMebleg, Kimden, QurumAdi, QaimeNum, Status) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

                        pres.setInt(1, id + 1);
                        pres.setString(2, malinadi);
                        pres.setInt(3, miqdari);
                        pres.setDouble(4, qiymet);
                        pres.setInt(5, Satis_Miqdari);
                        pres.setDouble(6, satisQiymeti);
                        pres.setDouble(7, Satisin_Toplam_Deyeri);
                        pres.setDouble(8, miqdari);
                        pres.setInt(9, movsumId);
                        pres.setInt(10, kateqoriyaId);
                        pres.setInt(11, altKateqoriya + 1);
                        pres.setString(12, alisTarixi);
                        pres.setDouble(13, umumiMebleg);
                        pres.setString(14, kimden);
                        pres.setString(15, qurum);
                        pres.setFloat(16, qaimenomresi);
                        pres.setString(17, "Elave olundu");
                        pres.executeUpdate();

                        Mehsullar mehsul2 = mehDao.getTheLastMehsulByIdAcilanQaime();

                        String ss2 = mehsul2.getId2();

                        int sss2 = (mehsul2.getId());

                        int i = 0;

                        pres = con.prepareStatement("update acilanqaime set Barcode=? where id =" + sss2);
                        pres.setString(1, "1234500" + sss2);
                        pres.executeUpdate();
                        txtBarcode.setText("1234500" + sss2);

                        String barcode = "1234500" + sss2;

                        pres = con.prepareStatement("insert into qaimedeDeyisiklik ( id, Malin_adi, Miqdari, Alis_qiymeti, Satis_Miqdari, Satis_qiymeti, Satisin_toplam_deyeri, Qaliq_say, Movsum_id, Kateqoriya_id, Alt_kateqoriya_id, Alis_Tarixi, Alisin_toplam_deyeri, Kimden, QurumAdi, QaimeNum, Barcode, Status ) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

                        pres.setInt(1, id + 1);
                        pres.setString(2, malinadi);
                        pres.setDouble(3, miqdari);
                        pres.setDouble(4, qiymet);
                        pres.setInt(5, 0);
                        pres.setDouble(6, satisQiymeti);
                        pres.setDouble(7, 0.0);
                        pres.setInt(8, miqdari);
                        pres.setInt(9, movsumId);
                        pres.setInt(10, kateqoriyaId);
                        pres.setInt(11, altKateqoriya + 1);
                        pres.setString(12, alisTarixi);
                        pres.setDouble(13, umumiMebleg);
                        pres.setString(14, kimden);
                        pres.setString(15, qurum);
                        pres.setFloat(16, qaimenomresi);
                        pres.setString(17, barcode);
                        pres.setString(18, "Elave olundu");
                        pres.executeUpdate();

                        load();

                        String alis = Double.toString(satisQiymeti);
                        alis.length();

                        String satisQiy = generateBarcode.test2(satisQiymeti);

                        System.out.println(satisQiy);

                        generateBarcode.main(barcode, malinadi, "****", "****", satisQiy);

                        JOptionPane.showMessageDialog(this, "Mehsul ugurla elave olundu");

                        txtMalinAdi.setText("");
                        txtMiqdari.setText("");
                        txtQaliqSay.setText("");
                        txtAlisQiymeti.setText("");
                        txtSatisQiymeti.setText("");
                        txtQaliqSay.setText("");
                        txtMovsumId.setText("");
                        txtKateqoriyaID.setText("");
                        cbAltKateqoriyaID.removeAllItems();

                        txtUmumiMebleg.setText("");
                        txtMalinAdi.requestFocus();

                    } else {

                        Mehsullar mehsul = mehDao.getTheLastMehsulByIdAcilanQaime();
                        int id = mehsul.getId();

                        String kimden = txtKimden.getText();
                        String qurum = txtQurum.getText();

                        float qaimenomresi = Float.parseFloat(txtQaimeNum.getText());

                        String malinadi = txtMalinAdi.getText();
                        int miqdari = Integer.parseInt(txtMiqdari.getText());
                        double qiymet = Double.parseDouble(txtAlisQiymeti.getText());
                        double satisQiymeti = Double.parseDouble(txtSatisQiymeti.getText());
                        int kateqoriyaId = Integer.parseInt(txtKateqoriyaID.getText());
                        int movsumId = Integer.parseInt(txtMovsumId.getText());
                        int altKateqoriya = cbAltKateqoriyaID.getSelectedIndex();
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

                        pres = con.prepareStatement("insert into acilanqaime ( id, Malin_adi, Miqdari, Alis_qiymeti,Satis_Miqdari,Satis_qiymeti, Satisin_toplam_deyeri, Qaliq_say, Movsum_id, Kateqoriya_id, Alt_kateqoriya_id, Alis_Tarixi, UmumiMebleg, Kimden, QurumAdi, QaimeNum, Status ) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

                        pres.setInt(1, id + 1);
                        pres.setString(2, malinadi);
                        pres.setInt(3, miqdari);
                        pres.setDouble(4, qiymet);
                        pres.setInt(5, Satis_Miqdari);
                        pres.setDouble(6, satisQiymeti);
                        pres.setDouble(7, Satisin_Toplam_Deyeri);
                        pres.setDouble(8, miqdari);
                        pres.setInt(9, movsumId);
                        pres.setInt(10, kateqoriyaId);
                        pres.setInt(11, altKateqoriya + 1);
                        pres.setString(12, alisTarixi);
                        pres.setDouble(13, umumiMebleg);
                        pres.setString(14, kimden);
                        pres.setString(15, qurum);
                        pres.setFloat(16, qaimenomresi);
                        pres.setString(17, "Elave olundu");
                        pres.executeUpdate();

                        Mehsullar mehsul2 = mehDao.getTheLastMehsulByIdAcilanQaime();

                        String ss2 = mehsul2.getId2();

                        int sss2 = (mehsul2.getId());

                        int i = 0;

                        pres = con.prepareStatement("update acilanqaime set Barcode=? where id =" + sss2);
                        pres.setString(1, "1234500" + sss2);
                        pres.executeUpdate();
                        txtBarcode.setText("1234500" + sss2);

                        String barcode = "1234500" + sss2;

                        pres = con.prepareStatement("insert into qaimedeDeyisiklik ( id, Malin_adi, Miqdari, Alis_qiymeti, Satis_Miqdari, Satis_qiymeti, Satisin_toplam_deyeri, Qaliq_say, Movsum_id, Kateqoriya_id, Alt_kateqoriya_id, Alis_Tarixi, Alisin_toplam_deyer, Kimden, QurumAdi, QaimeNum, Barcode, Status ) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

                        pres.setInt(1, id + 1);
                        pres.setString(2, malinadi);
                        pres.setDouble(3, miqdari);
                        pres.setDouble(4, qiymet);
                        pres.setInt(5, 0);
                        pres.setDouble(6, satisQiymeti);
                        pres.setDouble(7, 0.0);
                        pres.setInt(8, miqdari);
                        pres.setInt(9, movsumId);
                        pres.setInt(10, kateqoriyaId);
                        pres.setInt(11, altKateqoriya + 1);
                        pres.setString(12, alisTarixi);
                        pres.setDouble(13, umumiMebleg);
                        pres.setString(14, kimden);
                        pres.setString(15, qurum);
                        pres.setFloat(16, qaimenomresi);
                        pres.setString(17, barcode);
                        pres.setString(18, "Elave olundu");
                        pres.executeUpdate();

                        load();

                        String alis = Double.toString(satisQiymeti);
                        alis.length();

                        String satisQiy = generateBarcode.test2(satisQiymeti);

                        System.out.println(satisQiy);

                        generateBarcode.main(barcode, malinadi, "****", "****", satisQiy);

                        JOptionPane.showMessageDialog(this, "Mehsul ugurla elave olundu");

                        txtMalinAdi.setText("");
                        txtMiqdari.setText("");
                        txtQaliqSay.setText("");
                        txtAlisQiymeti.setText("");
                        txtSatisQiymeti.setText("");
                        txtQaliqSay.setText("");
                        txtMovsumId.setText("");
                        txtKateqoriyaID.setText("");
                        cbAltKateqoriyaID.removeAllItems();

                        txtUmumiMebleg.setText("");
                        txtMalinAdi.requestFocus();

                    }

                }

            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    public void qaimeninHesablanmasi() {

        DecimalFormat dformater = new DecimalFormat("#.##");
        df = (DefaultTableModel) tblMehsullar.getModel();

        double toplam, cem = 0;

        for (int i = 0; i < df.getRowCount(); i++) {

            toplam = Double.parseDouble(df.getValueAt(i, 10).toString());
            cem += toplam;
            String formattedGelir = dformater.format(cem);
            txtCemMebleg.setText(formattedGelir);

        }

    }

    private void load() {

        try {

            pres = con.prepareCall("select * from qaimeadlari");

            ResultSet rs = pres.executeQuery();

            while (rs.next()) {
                txtKimden.setText(rs.getString("qaimeAdi"));
                txtQurum.setText(rs.getString("Qurum"));
                txtQaimeNum.setText(Integer.toString(rs.getInt("QaimeNum")));

            }

        } catch (Exception ex) {

        }

        int a;
        try {

            pres = con.prepareCall("select * from acilanqaime");

            ResultSet rs = pres.executeQuery();

            ResultSetMetaData rd = rs.getMetaData();
            a = rd.getColumnCount();
            df = (DefaultTableModel) tblMehsullar.getModel();
            df.setRowCount(0);

            while (rs.next()) {
                Vector v2 = new Vector();
                for (int i = 0; i < a; i++) {

                    if ("updated".equals(rs.getString("Status"))) {

                        v2.add(rs.getInt("id"));
                        v2.add(rs.getString("Malin_adi"));
                        v2.add(rs.getInt("Miqdari"));
                        v2.add(rs.getInt("Qaliq_say"));
                        v2.add(rs.getDouble("Alis_qiymeti"));
                        v2.add(rs.getDouble("Satis_qiymeti"));
//                        v2.add(rs.getInt("Movsum_id"));
//                        v2.add(rs.getInt("Kateqoriya_id"));
//                        v2.add(rs.getInt("Alt_kateqoriya_id"));
                        v2.add(rs.getString("Alis_Tarixi"));
                        v2.add(rs.getDouble("UmumiMebleg"));
                        v2.add(rs.getString("Barcode"));
                        v2.add(rs.getString("Status"));

                    } else {

                        if (rs.getString("Kimden") != null & rs.getString("QurumAdi") != null || rs.getInt("QaimeNum") != 0) {

                            txtKimden.setText(rs.getString("Kimden"));
                            txtQurum.setText(rs.getString("QurumAdi"));
                            txtQaimeNum.setText(Integer.toString(rs.getInt("QaimeNum")));

                        }

                        v2.add(rs.getInt("id"));
                        v2.add(rs.getString("Malin_adi"));
                        v2.add(rs.getInt("Miqdari"));
                        v2.add(rs.getInt("Qaliq_say"));
                        v2.add(rs.getDouble("Alis_qiymeti"));
                        v2.add(rs.getDouble("Satis_qiymeti"));
//                        v2.add(rs.getInt("Movsum_id"));
//                        v2.add(rs.getInt("Kateqoriya_id"));
//                        v2.add(rs.getInt("Alt_kateqoriya_id"));
                        v2.add(rs.getString("Alis_Tarixi"));
                        v2.add(rs.getDouble("UmumiMebleg"));
                        v2.add(rs.getString("Barcode"));
                        v2.add(rs.getString("Status"));

                    }

                }
                df.addRow(v2);

            }

        } catch (Exception ex) {
            Logger.getLogger(Satici_Elave_Etmek.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void load2() {

        try {

            pres = con.prepareCall("select * from qaimeadlari");

            ResultSet rs = pres.executeQuery();

            while (rs.next()) {
                txtKimden.setText(rs.getString("qaimeAdi"));
                txtQurum.setText(rs.getString("Qurum"));
                txtQaimeNum.setText(Integer.toString(rs.getInt("QaimeNum")));

            }

        } catch (Exception ex) {

        }

        int a;
        try {

            pres = con.prepareCall("select * from acilanqaime");

            ResultSet rs = pres.executeQuery();

            ResultSetMetaData rd = rs.getMetaData();
            a = rd.getColumnCount();
            df = (DefaultTableModel) tblMehsullar.getModel();
            df.setRowCount(0);

            while (rs.next()) {
                Vector v2 = new Vector();
                for (int i = 0; i < a; i++) {

                    v2.add(rs.getInt("id"));
                    v2.add(rs.getString("Malin_adi"));
                    v2.add(rs.getInt("Miqdari"));
                    v2.add(rs.getInt("Qaliq_say"));
                    v2.add(rs.getDouble("Alis_qiymeti"));
                    v2.add(rs.getDouble("Satis_qiymeti"));
                    v2.add(rs.getInt("Movsum_id"));
                    v2.add(rs.getInt("Kateqoriya_id"));
                    v2.add(rs.getInt("Alt_kateqoriya_id"));
                    v2.add(rs.getString("Alis_Tarixi"));
                    v2.add(rs.getDouble("UmumiMebleg"));
                    v2.add(rs.getString("Barcode"));
                    v2.add(rs.getString("Status"));

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

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtMalinAdi = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtMiqdari = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtQaliqSay = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtAlisQiymeti = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtSatisQiymeti = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtMovsumId = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtAlisTarixi = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtUmumiMebleg = new javax.swing.JTextField();
        cbAltKateqoriyaID = new javax.swing.JComboBox<>();
        txtKateqoriyaID = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        txtKimden = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtQurum = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtQaimeNum = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblMehsullar = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        txtCancel = new javax.swing.JButton();
        btnYaddaSaxla = new javax.swing.JButton();
        txtCemMebleg = new javax.swing.JTextField();
        btnAdd = new javax.swing.JButton();
        txtBarcode = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        btnExist = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(0, 0, 255));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jPanel1.setBackground(new java.awt.Color(0, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Mehsulun adi :");

        txtMalinAdi.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("Miqdari :");

        txtMiqdari.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtMiqdari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMiqdariActionPerformed(evt);
            }
        });
        txtMiqdari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtMiqdariKeyReleased(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("Qaliq say :");

        txtQaliqSay.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setText("Alış Qiyməti :");

        txtAlisQiymeti.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
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

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setText("Satış Qiyməti :");

        txtSatisQiymeti.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setText("Mövsüm İD :");

        txtMovsumId.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setText("Kateqoriya İD :");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setText("Alt Kateqoriya İD :");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setText("Tarix :");

        txtAlisTarixi.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setText("Ümumi məbləğ :");

        txtUmumiMebleg.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        cbAltKateqoriyaID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbAltKateqoriyaIDActionPerformed(evt);
            }
        });

        txtKateqoriyaID.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtKateqoriyaID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtKateqoriyaIDActionPerformed(evt);
            }
        });
        txtKateqoriyaID.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtKateqoriyaIDKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel7)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtSatisQiymeti, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtMovsumId, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtAlisQiymeti, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtQaliqSay, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtMiqdari, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtMalinAdi, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtAlisTarixi)
                    .addComponent(txtUmumiMebleg)
                    .addComponent(cbAltKateqoriyaID, 0, 211, Short.MAX_VALUE)
                    .addComponent(txtKateqoriyaID))
                .addGap(35, 35, 35))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtKateqoriyaID, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbAltKateqoriyaID, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtMalinAdi, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtMiqdari, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtQaliqSay, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtAlisQiymeti, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtSatisQiymeti, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtMovsumId, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(96, 96, 96)
                        .addComponent(txtAlisTarixi, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtUmumiMebleg, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jPanel2.setBackground(new java.awt.Color(0, 255, 255));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel11.setText("Kimdən :");

        txtKimden.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel12.setText("Qurum adı :");

        txtQurum.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel13.setText("Qaimə nömrəsi :");

        txtQaimeNum.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel11)
                    .addComponent(jLabel12)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtQaimeNum, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtQurum, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtKimden, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addComponent(txtKimden, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtQurum, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtQaimeNum, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        tblMehsullar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mehsul ID", "Mehsul adi", "Miqdari", "Qaliq Say", "Alis Qiymeti", "Satış Qiyməti", "Mövsüm İD", "Kateqoriya İD", "Alt Kateqoriya İD", "Tarix", "Umumi Mebleg", "Barcode", "Status"
            }
        ));
        tblMehsullar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblMehsullarMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblMehsullar);
        if (tblMehsullar.getColumnModel().getColumnCount() > 0) {
            tblMehsullar.getColumnModel().getColumn(0).setMinWidth(50);
            tblMehsullar.getColumnModel().getColumn(0).setPreferredWidth(50);
            tblMehsullar.getColumnModel().getColumn(0).setMaxWidth(500);
            tblMehsullar.getColumnModel().getColumn(1).setMinWidth(100);
            tblMehsullar.getColumnModel().getColumn(1).setPreferredWidth(100);
            tblMehsullar.getColumnModel().getColumn(1).setMaxWidth(500);
            tblMehsullar.getColumnModel().getColumn(2).setMinWidth(50);
            tblMehsullar.getColumnModel().getColumn(2).setPreferredWidth(100);
            tblMehsullar.getColumnModel().getColumn(2).setMaxWidth(500);
            tblMehsullar.getColumnModel().getColumn(3).setMinWidth(100);
            tblMehsullar.getColumnModel().getColumn(3).setPreferredWidth(100);
            tblMehsullar.getColumnModel().getColumn(3).setMaxWidth(500);
            tblMehsullar.getColumnModel().getColumn(4).setMinWidth(100);
            tblMehsullar.getColumnModel().getColumn(4).setPreferredWidth(100);
            tblMehsullar.getColumnModel().getColumn(4).setMaxWidth(200);
            tblMehsullar.getColumnModel().getColumn(5).setMinWidth(100);
            tblMehsullar.getColumnModel().getColumn(5).setPreferredWidth(100);
            tblMehsullar.getColumnModel().getColumn(5).setMaxWidth(500);
            tblMehsullar.getColumnModel().getColumn(6).setMinWidth(50);
            tblMehsullar.getColumnModel().getColumn(6).setPreferredWidth(100);
            tblMehsullar.getColumnModel().getColumn(6).setMaxWidth(500);
            tblMehsullar.getColumnModel().getColumn(7).setMinWidth(100);
            tblMehsullar.getColumnModel().getColumn(7).setPreferredWidth(100);
            tblMehsullar.getColumnModel().getColumn(7).setMaxWidth(500);
            tblMehsullar.getColumnModel().getColumn(8).setMinWidth(100);
            tblMehsullar.getColumnModel().getColumn(8).setPreferredWidth(100);
            tblMehsullar.getColumnModel().getColumn(8).setMaxWidth(500);
            tblMehsullar.getColumnModel().getColumn(9).setMinWidth(100);
            tblMehsullar.getColumnModel().getColumn(9).setPreferredWidth(150);
            tblMehsullar.getColumnModel().getColumn(9).setMaxWidth(500);
            tblMehsullar.getColumnModel().getColumn(10).setMinWidth(100);
            tblMehsullar.getColumnModel().getColumn(10).setPreferredWidth(100);
            tblMehsullar.getColumnModel().getColumn(10).setMaxWidth(200);
            tblMehsullar.getColumnModel().getColumn(11).setMinWidth(100);
            tblMehsullar.getColumnModel().getColumn(11).setPreferredWidth(150);
            tblMehsullar.getColumnModel().getColumn(11).setMaxWidth(500);
            tblMehsullar.getColumnModel().getColumn(12).setMinWidth(100);
            tblMehsullar.getColumnModel().getColumn(12).setPreferredWidth(100);
            tblMehsullar.getColumnModel().getColumn(12).setMaxWidth(500);
        }

        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon("C:\\GitHubProject\\VeneraMarket\\VeneraMarket\\VeneraMarket\\src\\main\\java\\com\\mycompany\\qarisiqmallar\\veneramarket\\Button-Refresh-icon.png")); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        btnDelete.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnDelete.setIcon(new javax.swing.ImageIcon("C:\\GitHubProject\\VeneraMarket\\VeneraMarket\\VeneraMarket\\src\\main\\java\\pictures\\delete-file-icon.png")); // NOI18N
        btnDelete.setText("Sil");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        txtCancel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtCancel.setText("Ləgv et");
        txtCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCancelActionPerformed(evt);
            }
        });

        btnYaddaSaxla.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnYaddaSaxla.setText("Yadda Saxla");
        btnYaddaSaxla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnYaddaSaxlaActionPerformed(evt);
            }
        });

        txtCemMebleg.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        txtCemMebleg.setForeground(new java.awt.Color(255, 0, 0));
        txtCemMebleg.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        btnAdd.setBackground(new java.awt.Color(0, 255, 0));
        btnAdd.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        btnAdd.setForeground(new java.awt.Color(255, 255, 255));
        btnAdd.setIcon(new javax.swing.ImageIcon("C:\\GitHubProject\\VeneraMarket\\VeneraMarket\\VeneraMarket\\src\\main\\java\\com\\mycompany\\qarisiqmallar\\veneramarket\\Button-Add-icon.png")); // NOI18N
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(255, 51, 51));
        jButton4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("X");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        btnExist.setBackground(new java.awt.Color(51, 255, 0));
        btnExist.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnExist.setForeground(new java.awt.Color(255, 255, 255));
        btnExist.setText("Mövcud Mehsullar");
        btnExist.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExistActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 358, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnAdd)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtBarcode, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnExist)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtCancel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 361, Short.MAX_VALUE)
                        .addComponent(btnYaddaSaxla)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtCemMebleg, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton4))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnExist, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtBarcode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(72, 72, 72))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnAdd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(72, 72, 72))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtCancel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txtCemMebleg, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnYaddaSaxla, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(btnDelete, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbAltKateqoriyaIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbAltKateqoriyaIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbAltKateqoriyaIDActionPerformed

    private void txtKateqoriyaIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtKateqoriyaIDActionPerformed
        int kateqoriya = Integer.parseInt(txtKateqoriyaID.getText());

        if (kateqoriya == 1) {

            List<AltKateqoriyalar> m = mehDao2.getAltKateqoriyaByKateqoriyaId(kateqoriya);
            {
                cbAltKateqoriyaID.removeAllItems();
                for (int i = 0; i < m.size(); i++) {

                    cbAltKateqoriyaID.addItem(m.get(i));
                }

            }

        }
        if (kateqoriya == 2) {

            List<AltKateqoriyalar> m = mehDao2.getAltKateqoriyaByKateqoriyaId_Qadin(kateqoriya);
            {
                cbAltKateqoriyaID.removeAllItems();
                for (int i = 0; i < m.size(); i++) {

                    cbAltKateqoriyaID.addItem(m.get(i));
                }

            }

        }

        if (kateqoriya == 3) {

            List<AltKateqoriyalar> m = mehDao2.getAltKateqoriyaByKateqoriyaId_Usaq(kateqoriya);
            {
                cbAltKateqoriyaID.removeAllItems();
                for (int i = 0; i < m.size(); i++) {

                    cbAltKateqoriyaID.addItem(m.get(i));
                }

            }

        }

        if (kateqoriya == 4) {

            List<AltKateqoriyalar> m = mehDao2.getAltKateqoriyaByKateqoriyaId_Qarisiq(kateqoriya);
            {
                cbAltKateqoriyaID.removeAllItems();
                for (int i = 0; i < m.size(); i++) {

                    cbAltKateqoriyaID.addItem(m.get(i));
                }

            }

        }

    }//GEN-LAST:event_txtKateqoriyaIDActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        try {
            ElaveEt();
        } catch (DocumentException ex) {
            Logger.getLogger(AlisQaimesiMehsullar.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AlisQaimesiMehsullar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnYaddaSaxlaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnYaddaSaxlaActionPerformed

        writeFileFromJtableToFile();
        yaddaSaxla();

    }//GEN-LAST:event_btnYaddaSaxlaActionPerformed

    private void readBillFromFile() throws FileNotFoundException {

        try {

            pres = con.prepareCall("select * from qaimeyolu");

            ResultSet rs = pres.executeQuery();

            while (rs.next()) {
                String qaimeNum = (rs.getString("QaimeNum"));
                String qurum = (rs.getString("Qurum"));
                String QaimeAdi = (rs.getString("QaimeAdi"));
                String qaimeYolu = (rs.getString("QaimeYolu"));
                String kimden = (rs.getString("Kimden"));
                String tamAd2 = qaimeYolu + QaimeAdi;

                txtQaimeNum.setText((qaimeNum));
                txtQurum.setText(qurum);
                txtKimden.setText(kimden);

                File file = new File(tamAd2);

                FileReader fr = new FileReader(file);
                BufferedReader br = new BufferedReader(fr);

                df = (DefaultTableModel) tblMehsullar.getModel();
                Object[] lines = br.lines().toArray();

                for (int i = 0; i < lines.length; i++) {
                    String[] row = lines[i].toString().split(":");
                    df.addRow(row);
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(AlisQaimesiMehsullar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void bringProductsFromFileIntoDb() {

        try {
            pres = con.prepareStatement("truncate table acilanqaime;");
            pres.executeUpdate();

            df = (DefaultTableModel) tblMehsullar.getModel();

            int setirSayi = tblMehsullar.getRowCount();

            for (int i = 0; i < setirSayi; i++) {

                int id = Integer.parseInt(df.getValueAt(i, 0).toString());
                String mehsulAdi = df.getValueAt(i, 1).toString();
                int miqdari = Integer.parseInt(df.getValueAt(i, 2).toString());
                String qaliqSayi = (df.getValueAt(i, 3).toString());
                double alisQiymeti = Double.parseDouble(df.getValueAt(i, 4).toString());
                double satisQiymeti = Double.parseDouble(df.getValueAt(i, 5).toString());
                //String movsumID = (df.getValueAt(i, 6).toString());
                //int kateqoriyaID = Integer.parseInt(df.getValueAt(i, 7).toString());
                //int altKateqoriyaID = Integer.parseInt(df.getValueAt(i, 8).toString());
                //String tarix = df.getValueAt(i, 9).toString();
                ///double umumiMebleg = Double.parseDouble(df.getValueAt(i, 10).toString());
                //String barcode = df.getValueAt(i, 11).toString();
                String kimden = txtKimden.getText();
                String qurum = txtQurum.getText();
                String qaimeNum = (txtQaimeNum.getText());
                try {
                    pres = con.prepareCall("insert into acilanqaime ( id, Malin_adi, Miqdari, Qaliq_say, Alis_qiymeti, Satis_qiymeti, Kimden, QurumAdi, QaimeNum, Status) values (?,?,?,?,?,?,?,?,?,?,?,?,?)");

                    pres.setInt(1, id);
                    pres.setString(2, mehsulAdi);
                    pres.setInt(3, miqdari);
                    pres.setString(4, qaliqSayi);
                    pres.setDouble(5, alisQiymeti);
                    pres.setDouble(6, satisQiymeti);
//                    pres.setDouble(7, umumiMebleg);
//                    pres.setString(8, barcode);
//                    pres.setString(9, movsumID);
//                    pres.setInt(10, kateqoriyaID);
//                    pres.setInt(11, altKateqoriyaID);
//                    pres.setString(12, tarix);
                    pres.setString(13, kimden);
                    pres.setString(14, qurum);
                    pres.setString(15, qaimeNum);
                    pres.setString(16, "Kohne");
                    pres.executeUpdate();

                } catch (SQLException ex) {
                    Logger.getLogger(AlisQaimesiMehsullar.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        } catch (SQLException ex) {
            Logger.getLogger(AlisQaimesiMehsullar.class.getName()).log(Level.SEVERE, null, ex);
        }
        load();
    }


    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed

        Mehsullar mm = mehDao.getAllProduct3();

        if (mm == null) {
            this.setVisible(false);
        }
        if (mm != null) {
            int cavab = JOptionPane.showConfirmDialog(this, i + 1 + "Qaimede edilenn deyişiklikler yadda saxlanılmamışdır!\nYadda saxlamaq isteyirsinizmi?", "Diqqət!", JOptionPane.YES_NO_CANCEL_OPTION);

            if (cavab == 0) {
                writeFileFromJtableToFile();
                yaddaSaxla();
                this.setVisible(false);
            }
            if (cavab == 1) {
                try {
                    pres = con.prepareStatement("truncate table acilanqaime;");
                    pres.executeUpdate();
                    load();

                    txtKimden.setText("");
                    txtQurum.setText("");
                    txtCemMebleg.setText("");
                    txtAlisTarixi.setText(date2);

                    //setBillingNumber();
                    pres = con.prepareStatement("truncate table qaimeadlari;");
                    pres.executeUpdate();

                    pres = con.prepareStatement("truncate table qaimededeyisiklik;");
                    pres.executeUpdate();
                    this.setVisible(false);
                } catch (SQLException ex) {
                    Logger.getLogger(AlisQaimesiMehsullar.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }


    }//GEN-LAST:event_jButton4ActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed

        df = (DefaultTableModel) tblMehsullar.getModel();

        int selected = tblMehsullar.getSelectedRow();

        df = (DefaultTableModel) tblMehsullar.getModel();

        int id = Integer.parseInt(df.getValueAt(selected, 0).toString());
        String KohneStatus = (df.getValueAt(selected, 12).toString());
        String malinAdi = txtMalinAdi.getText();
        int miqdari = Integer.parseInt(txtMiqdari.getText());
        int qaliqSay = Integer.parseInt(txtQaliqSay.getText());
        double qiymet = Double.parseDouble(txtAlisQiymeti.getText());
        int movsumId = Integer.parseInt(txtMovsumId.getText());
        double satisQiymeti = Double.parseDouble(txtSatisQiymeti.getText());
        int kateqoriya = Integer.parseInt(txtKateqoriyaID.getText());
        int altKateqoriya = cbAltKateqoriyaID.getSelectedIndex();
        String tarix = txtAlisTarixi.getText();
        double umumiMebleg = Double.parseDouble(txtUmumiMebleg.getText());

        String barcode = df.getValueAt(selected, 11).toString();

        Mehsullar mehsul = mehDao.getMehsulByBarcodeAcilanQaime(barcode);
        String mehsulunAdi = mehsul.getName();

        int cavab = JOptionPane.showConfirmDialog(this, mehsulunAdi + " -adli mehsulu silmək istədiyinizdən əminsiniz?", "Diqqət!", JOptionPane.YES_NO_CANCEL_OPTION);

        if (cavab == 0) {

            try {

                // Evvelce Mehsullar table-dan silir
                pres = con.prepareStatement("delete from acilanqaime where Barcode = ? ");

                pres.setString(1, barcode);

                pres.executeUpdate();

                pres = con.prepareStatement("insert into qaimedeDeyisiklik ( id, Malin_adi, Miqdari, Alis_qiymeti, Satis_qiymeti, Status, UmumiMebleg, SilinmezdenOncekiStatusu ) values(?,?,?,?,?,?,?, ?)");

                pres.setInt(1, id);
                pres.setString(2, malinAdi);
                pres.setInt(3, miqdari);
                pres.setDouble(4, qiymet);
                pres.setDouble(5, satisQiymeti);
                pres.setString(6, "Silindi");
                pres.setDouble(7, umumiMebleg);
                pres.setString(8, KohneStatus);
                pres.executeUpdate();

                load();

                JOptionPane.showMessageDialog(this, " " + mehsulunAdi + "-adli mehsul silindi");

                txtMalinAdi.setText("");

                txtMiqdari.setText("");

                txtAlisQiymeti.setText("");

                txtMovsumId.setText("");

                txtQaliqSay.setText("");

                txtSatisQiymeti.setText("");

                txtKateqoriyaID.setText("");

                //AltKateqoriyaId.setText("");
                txtUmumiMebleg.setText("");

                txtMalinAdi.requestFocus();

                btnAdd.setVisible(true);

            } catch (SQLException ex) {
                Logger.getLogger(Satici_Elave_Etmek.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        if (cavab == 1) {

        }
        if (cavab == 2) {

        } else {

        }


    }//GEN-LAST:event_btnDeleteActionPerformed

    private void txtCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCancelActionPerformed

        txtMalinAdi.setText("");
        txtMiqdari.setText("");
        txtAlisQiymeti.setText("");
        txtMovsumId.setText("");
        txtKateqoriyaID.setText("");
        txtQaliqSay.setText("");
        txtSatisQiymeti.setText("");
        txtUmumiMebleg.setText("");
        txtMalinAdi.requestFocus();
        btnAdd.setVisible(true);


    }//GEN-LAST:event_txtCancelActionPerformed

    private void txtMiqdariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMiqdariActionPerformed

    }//GEN-LAST:event_txtMiqdariActionPerformed

    private void txtAlisQiymetiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAlisQiymetiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAlisQiymetiActionPerformed

    private void txtMiqdariKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMiqdariKeyReleased

        DecimalFormat dformater = new DecimalFormat("#.##");

        double miqdari = Double.parseDouble(txtMiqdari.getText());
        double alisqiymeti = Double.parseDouble(txtAlisQiymeti.getText());

        double cem = alisqiymeti * miqdari;

        String formattedresult = dformater.format(cem);
        txtUmumiMebleg.setText(formattedresult);


    }//GEN-LAST:event_txtMiqdariKeyReleased

    private void txtAlisQiymetiKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAlisQiymetiKeyReleased

        DecimalFormat dformater = new DecimalFormat("#.##");

        double miqdari = Double.parseDouble(txtMiqdari.getText());
        double alisqiymeti = Double.parseDouble(txtAlisQiymeti.getText());

        double cem = alisqiymeti * miqdari;

        String formattedresult = dformater.format(cem);
        txtUmumiMebleg.setText(formattedresult);


    }//GEN-LAST:event_txtAlisQiymetiKeyReleased

    private void txtKateqoriyaIDKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKateqoriyaIDKeyReleased

        int kateqoriya = Integer.parseInt(txtKateqoriyaID.getText());

        if (kateqoriya == 1) {

            List<AltKateqoriyalar> m = mehDao2.getAltKateqoriyaByKateqoriyaId(kateqoriya);
            {
                cbAltKateqoriyaID.removeAllItems();
                for (int i = 0; i < m.size(); i++) {

                    cbAltKateqoriyaID.addItem(m.get(i));
                }

            }

        }
        if (kateqoriya == 2) {

            List<AltKateqoriyalar> m = mehDao2.getAltKateqoriyaByKateqoriyaId_Qadin(kateqoriya);
            {
                cbAltKateqoriyaID.removeAllItems();
                for (int i = 0; i < m.size(); i++) {

                    cbAltKateqoriyaID.addItem(m.get(i));
                }

            }

        }

        if (kateqoriya == 3) {

            List<AltKateqoriyalar> m = mehDao2.getAltKateqoriyaByKateqoriyaId_Usaq(kateqoriya);
            {
                cbAltKateqoriyaID.removeAllItems();
                for (int i = 0; i < m.size(); i++) {

                    cbAltKateqoriyaID.addItem(m.get(i));
                }

            }

        }

        if (kateqoriya == 4) {

            List<AltKateqoriyalar> m = mehDao2.getAltKateqoriyaByKateqoriyaId_Qarisiq(kateqoriya);
            {
                cbAltKateqoriyaID.removeAllItems();
                for (int i = 0; i < m.size(); i++) {

                    cbAltKateqoriyaID.addItem(m.get(i));
                }

            }

        }


    }//GEN-LAST:event_txtKateqoriyaIDKeyReleased

    public void changeValueOfProduct() {
        int say = 0;
        df = (DefaultTableModel) tblMehsullar.getModel();

        int selected = tblMehsullar.getSelectedRow();

        int id = Integer.parseInt(df.getValueAt(selected, 0).toString());
        String malinAdi = txtMalinAdi.getText();
        int miqdari = Integer.parseInt(txtMiqdari.getText());
        int qaliqSay = Integer.parseInt(txtQaliqSay.getText());
        double qiymet = Double.parseDouble(txtAlisQiymeti.getText());
        int movsumId = Integer.parseInt(txtMovsumId.getText());
        double satisQiymeti = Double.parseDouble(txtSatisQiymeti.getText());
        int kateqoriya = Integer.parseInt(txtKateqoriyaID.getText());
        int altKateqoriya = cbAltKateqoriyaID.getSelectedIndex();
        String tarix = txtAlisTarixi.getText();
        double umumiMebleg = Double.parseDouble(txtUmumiMebleg.getText());

        int idtb = Integer.parseInt(df.getValueAt(selected, 0).toString());
        String mehsulAdi = df.getValueAt(selected, 1).toString();
        int miqdaritb = Integer.parseInt(df.getValueAt(selected, 2).toString());
        int qaliqSayi = Integer.parseInt(df.getValueAt(selected, 3).toString());

        try {

            pres = con.prepareCall("select * from qaimededeyisiklik ");
            pres.execute();
            ResultSet rs = pres.getResultSet();

            Mehsullar mm = mehDao.getAllProduct3();

            if (mm != null) {

                while (rs.next()) {

                    String malinadi = rs.getString("Malin_adi");
                    int id2 = rs.getInt("id");

                    if (malinadi.isBlank()) {

                        System.out.println("qaqaw bu olmadi");

                    }

                    if (id2 == id) {

                        if (miqdari > miqdaritb) {

                            int yeniMiqdar = miqdari - miqdaritb;
                            pres = con.prepareStatement("update qaimededeyisiklik set Malin_adi=?, Miqdari=?, Alis_qiymeti=?, Satis_qiymeti =?, Status=? where id =?");

                            pres.setString(1, malinAdi);
                            pres.setInt(2, yeniMiqdar);
                            pres.setDouble(3, qiymet);
                            pres.setDouble(4, satisQiymeti);
                            pres.setString(5, "Plus");
                            pres.setInt(6, id2);
                            pres.executeUpdate();
                            say++;
                        }

                    }
                    if (id2 == id) {

                        if (miqdari < miqdaritb) {

                            int yeniMiqdar = miqdaritb - miqdari;
                            pres = con.prepareStatement("update qaimedeDeyisiklik set Malin_adi=?, Miqdari=?, Alis_qiymeti=?, Satis_qiymeti =?, Status=? where id =?");

                            pres.setString(1, malinAdi);
                            pres.setInt(2, yeniMiqdar);
                            pres.setDouble(3, qiymet);
                            pres.setDouble(4, satisQiymeti);
                            pres.setString(5, "Minus");
                            pres.setInt(6, id2);
                            pres.executeUpdate();
                            say++;
                        }

                    }
                }

            }
            if (say == 0) {
                say = 0;
            } else {
                say = 1;
            }

            if (miqdari > miqdaritb & say == 0) {
                int yeniMiqdar = miqdari - miqdaritb;
                double yeniUmumiMebleg = yeniMiqdar * qiymet;
                pres = con.prepareStatement("insert into qaimedeDeyisiklik ( id, Malin_adi, Miqdari, Alis_qiymeti, Satis_qiymeti, Status, UmumiMebleg) values(?,?,?,?,?,?,?)");

                pres.setInt(1, id);
                pres.setString(2, malinAdi);
                pres.setInt(3, yeniMiqdar);
                pres.setDouble(4, qiymet);
                pres.setDouble(5, satisQiymeti);
                pres.setString(6, "Plus");
                pres.setDouble(7, yeniUmumiMebleg);
                pres.executeUpdate();
            }

            if (miqdari < miqdaritb & say == 0) {
                int yeniMiqdar = miqdaritb - miqdari;
                double yeniUmumiMebleg = yeniMiqdar * qiymet;
                try {
                    pres = con.prepareStatement("insert into qaimedeDeyisiklik ( id, Malin_adi, Miqdari, Alis_qiymeti, Satis_qiymeti, Status, UmumiMebleg) values(?,?,?,?,?,?,?)");

                    pres.setInt(1, id);
                    pres.setString(2, malinAdi);
                    pres.setInt(3, yeniMiqdar);
                    pres.setDouble(4, qiymet);
                    pres.setDouble(5, satisQiymeti);
                    pres.setString(6, "Minus");
                    pres.setDouble(7, yeniUmumiMebleg);
                    pres.executeUpdate();

                } catch (SQLException ex) {
                    Logger.getLogger(AlisQaimesiMehsullar.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

//                if (miqdari < miqdaritb) {
//                    
//                    int yeniMiqdar = miqdari - miqdaritb;
//                    double yeniUmumiMebleg = yeniMiqdar * qiymet;
//
//                    pres = con.prepareStatement("insert into qaimedeDeyisiklik ( id, Malin_adi, Miqdari, Alis_qiymeti, Satis_qiymeti, Status, UmumiMebleg) values(?,?,?,?,?,?)");
//
//                    pres.setInt(1, id);
//                    pres.setString(2, malinAdi);
//                    pres.setInt(3, yeniMiqdar);
//                    pres.setDouble(4, qiymet);
//                    pres.setDouble(5, satisQiymeti);
//                    pres.setString(6, "Minus");
//                    pres.setDouble(7, yeniUmumiMebleg);
//                    pres.executeUpdate();
//                }
//
//                if (miqdari > miqdaritb) {
//                    int yeniMiqdar = miqdari - miqdaritb;
//
//                    try {
//                        pres = con.prepareStatement("insert into qaimedeDeyisiklik ( id, Malin_adi, Miqdari, Alis_qiymeti, Satis_qiymeti, Status) values(?,?,?,?,?,?)");
//
//                        pres.setInt(1, id);
//                        pres.setString(2, malinAdi);
//                        pres.setInt(3, yeniMiqdar);
//                        pres.setDouble(4, qiymet);
//                        pres.setDouble(5, satisQiymeti);
//                        pres.setString(6, "Plus");
//                        pres.executeUpdate();
//
//                    } catch (SQLException ex) {
//                        Logger.getLogger(AlisQaimesiMehsullar.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                }
            try {

                pres = con.prepareStatement("update acilanqaime set Malin_adi=?, Miqdari=?, Qaliq_say=?, Alis_qiymeti=?, Satis_qiymeti =?, Movsum_id=?, Kateqoriya_id=?, Alt_kateqoriya_id=?, Alis_Tarixi=?, UmumiMebleg=?, Status=? where id =?");

                pres.setString(1, malinAdi);
                pres.setInt(2, miqdari);
                pres.setInt(3, qaliqSay);
                pres.setDouble(4, qiymet);
                pres.setDouble(5, satisQiymeti);
                pres.setInt(6, movsumId);
                pres.setInt(7, kateqoriya);
                pres.setInt(8, altKateqoriya + 1);
                pres.setString(9, tarix);
                pres.setDouble(10, umumiMebleg);
                pres.setString(11, "Yenilendi");
                pres.setInt(12, id);
                pres.executeUpdate();

                load2();

                txtMalinAdi.setText("");
                txtMiqdari.setText("");
                txtAlisQiymeti.setText("");
                txtSatisQiymeti.setText("");
                txtMovsumId.setText("");
                txtQaliqSay.setText("");
                txtKateqoriyaID.setText("");
                //cbAltKateqoriyaID.set("");
                // txtAlisTarixi.setText("");
                txtUmumiMebleg.setText("");
                txtMalinAdi.requestFocus();
                btnAdd.setVisible(true);

            } catch (SQLException ex) {
                Logger.getLogger(Satici_Elave_Etmek.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (SQLException ex) {
            Logger.getLogger(Satici_Elave_Etmek.class.getName()).log(Level.SEVERE, null, ex);
        }

    }


    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        changeValueOfProduct();
        qaimeninHesablanmasi();
        load2();
        JOptionPane.showMessageDialog(this, "Mehsulun melumatlari ugurla yenilendi");

    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnExistActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExistActionPerformed

        String kimden = txtKimden.getText();
        String qurumAdi = txtQurum.getText();
        int qaimeNum = Integer.parseInt(txtQaimeNum.getText());

        try {

            pres = con.prepareStatement("truncate table qaimeadlari;");
            pres.executeUpdate();

            pres = con.prepareStatement("insert into qaimeadlari (qaimeAdi, Qurum, QaimeNum) values(?,?,?)");
            pres.setString(1, kimden);
            pres.setString(2, qurumAdi);
            pres.setInt(3, qaimeNum);
            pres.executeUpdate();

            ExistingProductsAcilanQaime movcudMehsullar = new ExistingProductsAcilanQaime();

            movcudMehsullar.setVisible(true);
            this.setVisible(false);

        } catch (Exception ex) {
            Logger.getLogger(AlisQaimesi.class.getName()).log(Level.SEVERE, null, ex);
        }


    }//GEN-LAST:event_btnExistActionPerformed

    private void tblMehsullarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblMehsullarMouseClicked

        df = (DefaultTableModel) tblMehsullar.getModel();

        int selected = tblMehsullar.getSelectedRow();

        int id = Integer.parseInt(df.getValueAt(selected, 0).toString());
        txtMalinAdi.setText(df.getValueAt(selected, 1).toString());
        txtMiqdari.setText(df.getValueAt(selected, 2).toString());
        txtQaliqSay.setText(df.getValueAt(selected, 3).toString());
        txtAlisQiymeti.setText(df.getValueAt(selected, 4).toString());
        txtSatisQiymeti.setText(df.getValueAt(selected, 5).toString());
        txtMovsumId.setText(df.getValueAt(selected, 6).toString());
        txtKateqoriyaID.setText(df.getValueAt(selected, 7).toString());
        cbAltKateqoriyaID.setSelectedItem(df.getValueAt(selected, 8).toString());
        txtAlisTarixi.setText(df.getValueAt(selected, 9).toString());
        //txtBarcode.setText(df.getValueAt(selected, 12).toString());
        txtUmumiMebleg.setText(df.getValueAt(selected, 10).toString());

        btnAdd.setVisible(false);

    }//GEN-LAST:event_tblMehsullarMouseClicked

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
            java.util.logging.Logger.getLogger(AlisQaimesiMehsullar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AlisQaimesiMehsullar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AlisQaimesiMehsullar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AlisQaimesiMehsullar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new AlisQaimesiMehsullar().setVisible(true);
                } catch (Exception ex) {
                    Logger.getLogger(AlisQaimesiMehsullar.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnExist;
    private javax.swing.JButton btnYaddaSaxla;
    private javax.swing.JComboBox<AltKateqoriyalar> cbAltKateqoriyaID;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton4;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblMehsullar;
    private javax.swing.JTextField txtAlisQiymeti;
    private javax.swing.JTextField txtAlisTarixi;
    private javax.swing.JTextField txtBarcode;
    private javax.swing.JButton txtCancel;
    private javax.swing.JTextField txtCemMebleg;
    private javax.swing.JTextField txtKateqoriyaID;
    private javax.swing.JTextField txtKimden;
    private javax.swing.JTextField txtMalinAdi;
    private javax.swing.JTextField txtMiqdari;
    private javax.swing.JTextField txtMovsumId;
    private javax.swing.JTextField txtQaimeNum;
    private javax.swing.JTextField txtQaliqSay;
    private javax.swing.JTextField txtQurum;
    private javax.swing.JTextField txtSatisQiymeti;
    private javax.swing.JTextField txtUmumiMebleg;
    // End of variables declaration//GEN-END:variables
}
