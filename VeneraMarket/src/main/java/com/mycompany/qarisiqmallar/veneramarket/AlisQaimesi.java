/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.qarisiqmallar.veneramarket;

import com.itextpdf.text.DocumentException;
import com.mycompany.DaoInter.AltKateqoriyalarDaoInter;
import com.mycompany.DaoInter.MehsullarDaoInter;
import com.mycompany.DaoInter.QaimeDaoInter;
import com.mycompany.entity.AltKateqoriyalar;
import com.mycompany.entity.Mehsullar;
import com.mycompany.entity.Qaime;
import com.mycompany.main.Contex;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
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
public class AlisQaimesi extends javax.swing.JFrame {

    String kimden, qurumAdi;
    int i, qaimeN;

    Date date = new Date();
    Calendar calendar;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");
    String date2 = sdf.format(date);
    public MehsullarDaoInter mehDao = Contex.instanceOfMehsullarDao();
    public QaimeDaoInter mehDao3 = Contex.instanceOfQaimetlerDao();
    public AltKateqoriyalarDaoInter mehDao2 = Contex.instanceOfAltKateqoriyalarDao();

    Connection con;
    PreparedStatement pres;
    DefaultTableModel df;

    public AlisQaimesi() throws Exception {

        initComponents();
        txtAlisTarixi.setText(date2);
        connect();
        setBillingNumber();
        Toolkit toolkit = getToolkit();
        Dimension size = toolkit.getScreenSize();
        setLocation(size.width / 2 - getWidth() / 2, size.height / 2 - getHeight() / 2);
        txtKimden.requestFocus();
        load();
        qaimeninHesablanmasi();
    }

    public void connect() throws Exception {

        Class.forName("com.mysql.cj.jdbc.Driver");

        con = DriverManager.getConnection("jdbc:mysql://localhost/mehsullar", "root", "dxdiag92");

    }

    public void setBillingNumber() {

        Qaime sonuncuMehsul = mehDao3.getBillingNumber();

        if (sonuncuMehsul == null) {

            txtQaimeNum.setText("1");

        } else {

            int billinNumber = sonuncuMehsul.getQaimeNumber();

            if (billinNumber == 0) {

                billinNumber = 1;
                txtQaimeNum.setText(Integer.toString(billinNumber));
            } else {

                txtQaimeNum.setText(Integer.toString(billinNumber + 1));
            }

        }

    }

    private void ElaveEt() throws DocumentException, IOException {

        try {
            if (txtMalinAdi.getText().isBlank() || txtMiqdari.getText().isBlank() || txtMovsumId.getText().isBlank() || txtKateqoriyaID.getText().isBlank() || cbAltKateqoriyaID.getSelectedItem().toString().isBlank() || txtAlisTarixi.getText().isBlank()) {

                JOptionPane.showMessageDialog(this, "Zəhmət olmasa bütün məlumatları doldurun");
            } else {

                Mehsullar mehsul5 = mehDao.getTheLastMehsulById2();
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

                    pres = con.prepareStatement("insert into mehsullar ( id, Malin_adi, Miqdari, Alis_qiymeti,Satis_Miqdari,Satis_qiymeti, Satisin_toplam_deyeri, Qaliq_say, Movsum_id, Kateqoriya_id, Alt_kateqoriya_id, Alis_Tarixi, Alisin_toplam_deyer, Kimden, QurumAdi, QaimeNum ) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

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
                    pres.executeUpdate();

                    Mehsullar mehsul2 = mehDao.getTheLastMehsulById3();

                    String ss2 = mehsul2.getId2();

                    int sss2 = (mehsul2.getId());

                    int i = 0;

                    pres = con.prepareStatement("update mehsullar set Barcode=? where id =" + sss2);
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

                    if ("updated".equals(status)) {

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

                        pres = con.prepareStatement("insert into mehsullar ( id, Malin_adi, Miqdari, Alis_qiymeti,Satis_Miqdari,Satis_qiymeti, Satisin_toplam_deyeri, Qaliq_say, Movsum_id, Kateqoriya_id, Alt_kateqoriya_id, Alis_Tarixi, Alisin_toplam_deyer, Kimden, QurumAdi, QaimeNum ) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

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
                        pres.executeUpdate();

                        Mehsullar mehsul2 = mehDao.getTheLastMehsulById3();

                        String ss2 = mehsul2.getId2();

                        int sss2 = (mehsul2.getId());

                        int i = 0;

                        pres = con.prepareStatement("update mehsullar set Barcode=? where id =" + sss2);
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

                        Mehsullar mehsul = mehDao.getTheLastMehsulById2();
                        int id = mehsul5.getId();

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

                        pres = con.prepareStatement("insert into mehsullar ( id, Malin_adi, Miqdari, Alis_qiymeti,Satis_Miqdari,Satis_qiymeti, Satisin_toplam_deyeri, Qaliq_say, Movsum_id, Kateqoriya_id, Alt_kateqoriya_id, Alis_Tarixi, Alisin_toplam_deyer, Kimden, QurumAdi, QaimeNum ) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

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
                        pres.executeUpdate();

                        Mehsullar mehsul2 = mehDao.getTheLastMehsulById3();

                        String ss2 = mehsul2.getId2();

                        int sss2 = (mehsul2.getId());

                        int i = 0;

                        pres = con.prepareStatement("update mehsullar set Barcode=? where id =" + sss2);
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

                    }

                }

            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    public void MehsulFromCopyOfMehsullar() throws DocumentException, IOException {
        Mehsullar mehsul5 = mehDao.getTheLastMehsulById2();
        int IDMehsullar = (mehsul5.getId());
        int newId = IDMehsullar + 1;

        try {
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

            pres = con.prepareStatement("insert into mehsullar ( id, Malin_adi, Miqdari, Alis_qiymeti,Satis_Miqdari,Satis_qiymeti, Satisin_toplam_deyeri, Qaliq_say, Movsum_id, Kateqoriya_id, Alt_kateqoriya_id, Alis_Tarixi, Alisin_toplam_deyer, Kimden, QurumAdi, QaimeNum ) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

            pres.setInt(1, IDMehsullar + 1);
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
            pres.executeUpdate();

            Mehsullar mehsul2 = mehDao.getTheLastMehsulById3();

            String ss2 = mehsul2.getId2();

            int sss2 = (mehsul2.getId());

            int i = 0;

            pres = con.prepareStatement("update mehsullar set Barcode=? where id =" + sss2);
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
        } catch (SQLException ex) {
            Logger.getLogger(AlisQaimesi.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void mehSullardanTapilanID() throws DocumentException, IOException {

        try {
            Mehsullar mehsul5 = mehDao.getTheLastMehsulById();
            int IDMehsullar = (mehsul5.getId());

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

            pres = con.prepareStatement("insert into mehsullar ( id, Malin_adi, Miqdari, Alis_qiymeti,Satis_Miqdari,Satis_qiymeti, Satisin_toplam_deyeri, Qaliq_say, Movsum_id, Kateqoriya_id, Alt_kateqoriya_id, Alis_Tarixi, Alisin_toplam_deyer, Kimden, QurumAdi, QaimeNum ) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

            pres.setInt(1, IDMehsullar);
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
            pres.executeUpdate();

            Mehsullar mehsul2 = mehDao.getTheLastMehsulById3();

            String ss2 = mehsul2.getId2();

            int sss2 = (mehsul2.getId());

            int i = 0;

            pres = con.prepareStatement("update mehsullar set Barcode=? where id =" + sss2);
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
        } catch (SQLException ex) {
            Logger.getLogger(AlisQaimesi.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void load() {
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

            pres = con.prepareCall("select * from mehsullar");

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
                        v2.add(rs.getInt("Movsum_id"));
                        v2.add(rs.getInt("Kateqoriya_id"));
                        v2.add(rs.getInt("Alt_kateqoriya_id"));
                        v2.add(rs.getString("Alis_Tarixi"));
                        v2.add(rs.getDouble("CariAlisMeblegi"));
                        v2.add(rs.getString("Barcode"));

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
                        v2.add(rs.getInt("Movsum_id"));
                        v2.add(rs.getInt("Kateqoriya_id"));
                        v2.add(rs.getInt("Alt_kateqoriya_id"));
                        v2.add(rs.getString("Alis_Tarixi"));
                        v2.add(rs.getDouble("Alisin_toplam_deyer"));
                        v2.add(rs.getString("Barcode"));

                    }

                }
                df.addRow(v2);

            }

        } catch (Exception ex) {
            Logger.getLogger(Satici_Elave_Etmek.class.getName()).log(Level.SEVERE, null, ex);
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

    private void load2() {

        int a;
        try {
            pres = con.prepareCall("select * from mehsullar");

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
                    v2.add(rs.getDouble("Alisin_toplam_deyer"));
                    v2.add(rs.getString("Barcode"));

                }
                df.addRow(v2);

            }

        } catch (Exception ex) {
            Logger.getLogger(Satici_Elave_Etmek.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void deleteProduct() {

        df = (DefaultTableModel) tblMehsullar.getModel();

        int selected = tblMehsullar.getSelectedRow();

        String barcode = df.getValueAt(selected, 11).toString();

        Mehsullar mehsul = mehDao.getMehsulByBarcode2(barcode);
        String mehsulunAdi = mehsul.getName();

        int cavab = JOptionPane.showConfirmDialog(this, mehsulunAdi + " -adli mehsulu silmək istədiyinizdən əminsiniz?", "Diqqət!", JOptionPane.YES_NO_CANCEL_OPTION);

        if (cavab == 0) {

            try {

                //Sonra ise mehsullar table-dan
                pres = con.prepareStatement("delete from mehsullar where Barcode = ? ");

                pres.setString(1, barcode);

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
                txtAlisTarixi.setText("");

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

    }

    public void changeValueOfProduct() {

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

        try {

            pres = con.prepareStatement("update mehsullar set Malin_adi=?, Miqdari=?, Qaliq_say=?, Alis_qiymeti=?, Satis_qiymeti =?, Movsum_id=?, Kateqoriya_id=?, Alt_kateqoriya_id=?, Alis_Tarixi=?, Alisin_toplam_deyer=? where id =?");

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
            pres.setInt(11, id);
            pres.executeUpdate();

            load();
            JOptionPane.showMessageDialog(this, "Mehsulun melumatlari ugurla yenilendi");

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
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        btnYaddaSaxla = new javax.swing.JButton();
        txtCemMebleg = new javax.swing.JTextField();
        btnAdd = new javax.swing.JButton();
        txtBarcode = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        AddExistProduct = new javax.swing.JButton();

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
        txtAlisTarixi.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setText("Ümumi məbləğ :");

        txtUmumiMebleg.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        cbAltKateqoriyaID.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
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
                    .addComponent(txtSatisQiymeti, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE)
                    .addComponent(txtMovsumId, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE)
                    .addComponent(txtAlisQiymeti, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE)
                    .addComponent(txtQaliqSay, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE)
                    .addComponent(txtMiqdari, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE)
                    .addComponent(txtMalinAdi, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE)
                    .addComponent(txtAlisTarixi, javax.swing.GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE)
                    .addComponent(txtUmumiMebleg, javax.swing.GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE)
                    .addComponent(cbAltKateqoriyaID, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                .addContainerGap(18, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel11)
                    .addComponent(jLabel12)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtQaimeNum, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtQurum, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtKimden, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18))
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
                    .addGroup(jPanel2Layout.createSequentialGroup()
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
                "Mehsul ID", "Mehsul adi", "Miqdari", "Qaliq say", "Alış Qiyməti", "Satış Qiyməti", "Mövsüm İD", "Kateqoriya İD", "Alt Kateqoriya İD", "Tarix", "Ümumi məbləğ", "Barcode"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tblMehsullar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblMehsullarMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblMehsullar);

        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton2.setText("Sil");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton3.setText("Ləgv et");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        btnYaddaSaxla.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnYaddaSaxla.setText("Yadda Saxla");
        btnYaddaSaxla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnYaddaSaxlaActionPerformed(evt);
            }
        });

        txtCemMebleg.setBackground(new java.awt.Color(255, 0, 0));
        txtCemMebleg.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        txtCemMebleg.setForeground(new java.awt.Color(255, 255, 255));
        txtCemMebleg.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        btnAdd.setBackground(new java.awt.Color(0, 204, 0));
        btnAdd.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        btnAdd.setForeground(new java.awt.Color(255, 255, 255));
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

        AddExistProduct.setBackground(new java.awt.Color(0, 255, 0));
        AddExistProduct.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        AddExistProduct.setForeground(new java.awt.Color(255, 255, 255));
        AddExistProduct.setText("Mövcud Mehsullar");
        AddExistProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddExistProductActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton4)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 369, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(btnAdd)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtBarcode, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(AddExistProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnYaddaSaxla)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtCemMebleg, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 951, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(81, 81, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(AddExistProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtBarcode, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(txtCemMebleg, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnYaddaSaxla, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(70, Short.MAX_VALUE))
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

            try {
                ElaveEt();
            } catch (IOException ex) {
                Logger.getLogger(AlisQaimesi.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (DocumentException ex) {
            Logger.getLogger(AlisQaimesi.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnAddActionPerformed

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

    public void yaddaSaxla() {

        try {
            String kimden = txtKimden.getText();
            String qurum = txtQurum.getText();
            int qaimeNumresi = Integer.parseInt(txtQaimeNum.getText());
            i = qaimeNumresi;
            double qaimeMeblegi = Double.parseDouble(txtCemMebleg.getText());
            String tarix = txtAlisTarixi.getText();

            pres = con.prepareStatement("insert into alisqaimeleri (Kimden, Qurum, QaimeNomresi, QaimeMeblegi, Tarix ) values(?, ?, ?, ?, ?)");
            pres.setString(1, kimden);
            pres.setString(2, qurum);
            pres.setInt(3, qaimeNumresi);
            pres.setDouble(4, qaimeMeblegi);
            pres.setString(5, tarix);
            pres.executeUpdate();

            df = (DefaultTableModel) tblMehsullar.getModel();

            int rowCount = tblMehsullar.getRowCount();

            int rowCount2;
            rowCount2 = rowCount - rowCount;

            for (int j = 0; j < rowCount; j++) {

                String mehsulunAdi = (df.getValueAt(rowCount2, 1).toString());
                int miqdari = Integer.parseInt(df.getValueAt(rowCount2, 2).toString());
                int qaliqSay = Integer.parseInt(df.getValueAt(rowCount2, 3).toString());
                double alisQiymeti = Double.parseDouble(df.getValueAt(rowCount2, 4).toString());
                double satisQiymeti = Double.parseDouble(df.getValueAt(rowCount2, 5).toString());
                int movsumID = Integer.parseInt(df.getValueAt(rowCount2, 6).toString());
                int kateqoriyaID = Integer.parseInt(df.getValueAt(rowCount2, 7).toString());
                int AltkateqoriyaID = Integer.parseInt(df.getValueAt(rowCount2, 8).toString());
                String tarix2 = (df.getValueAt(rowCount2, 9).toString());
                double umumiMebleg = Double.parseDouble(df.getValueAt(rowCount2, 10).toString());
                String barcode = (df.getValueAt(rowCount2, 11).toString());

                if (movsumID != 0) {

                    pres = con.prepareStatement("insert into mehsullar ( Malin_adi, Miqdari, Alis_qiymeti, Satis_Miqdari, Satis_qiymeti, Satisin_toplam_deyeri, Qaliq_say, Movsum_id, Kateqoriya_id, Alt_kateqoriya_id, Alis_Tarixi, Alisin_toplam_deyer, Kimden, QurumAdi, QaimeNum, Barcode ) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

                    pres.setString(1, mehsulunAdi);
                    pres.setInt(2, miqdari);
                    pres.setDouble(3, alisQiymeti);
                    pres.setInt(4, 0);
                    pres.setDouble(5, satisQiymeti);
                    pres.setDouble(6, 0.0);
                    pres.setInt(7, miqdari);
                    pres.setInt(8, movsumID);
                    pres.setInt(9, kateqoriyaID);
                    pres.setInt(10, AltkateqoriyaID + 1);
                    pres.setString(11, tarix2);
                    pres.setDouble(12, umumiMebleg);
                    pres.setString(13, kimden);
                    pres.setString(14, qurum);
                    pres.setFloat(15, qaimeNumresi);
                    pres.setString(16, barcode);
                    pres.executeUpdate();

                } else {

                    pres = con.prepareStatement("update mehsullar set Malin_adi=?, Miqdari = Miqdari + ?, Qaliq_say=?, Alis_qiymeti=?, Satis_qiymeti =?, Alis_Tarixi=?, Alisin_toplam_deyer = Alisin_toplam_deyer + ? where Barcode =?");

                    pres.setString(1, mehsulunAdi);
                    pres.setInt(2, miqdari);
                    pres.setInt(3, qaliqSay);
                    pres.setDouble(4, alisQiymeti);
                    pres.setDouble(5, satisQiymeti);
                    pres.setString(6, tarix);
                    pres.setDouble(7, umumiMebleg);
                    pres.setString(8, barcode);
                    pres.executeUpdate();

                }
                rowCount2++;
            }

            pres = con.prepareStatement("truncate table mehsullar;");
            pres.executeUpdate();
            loadAfterSave();

            txtKimden.setText("");
            txtQurum.setText("");
            txtCemMebleg.setText("");
            txtAlisTarixi.setText(date2);

            setBillingNumber();

            pres = con.prepareStatement("truncate table qaimeadlari;");
            pres.executeUpdate();

            JOptionPane.showMessageDialog(this, qaimeNumresi + "№-li qaime yadda saxlanildi ");

            load();

        } catch (SQLException ex) {
            Logger.getLogger(AlisQaimesi.class.getName()).log(Level.SEVERE, null, ex);
        }

    }


    private void btnYaddaSaxlaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnYaddaSaxlaActionPerformed

        if (txtKimden.getText().isEmpty() || txtQurum.getText().isEmpty() || txtQaimeNum.getText().isEmpty()) {

            JOptionPane.showMessageDialog(this, "Zəhmət olmasa bütün məlumatları doldurun");
        } else {

            writeFileFromJtableToFile();
            yaddaSaxla();

        }


    }//GEN-LAST:event_btnYaddaSaxlaActionPerformed

    private void loadAfterSave() {

        int a;
        try {

            pres = con.prepareCall("select * from mehsullar");

            ResultSet rs = pres.executeQuery();

            if (rs.getString("Malin_adi") != null) {

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
                        v2.add(rs.getDouble("Alis_qiymeti"));
                        v2.add(rs.getDouble("Alisin_toplam_deyer"));
                        v2.add(rs.getDouble("Satis_qiymeti"));
                        v2.add(rs.getInt("Movsum_id"));
                        v2.add(rs.getInt("Kateqoriya_id"));
                        v2.add(rs.getInt("Alt_kateqoriya_id"));
                        v2.add(rs.getString("Alis_Tarixi"));
                        v2.add(rs.getString("Barcode"));
                        v2.add(rs.getString("Kimden"));
                        v2.add(rs.getString("QurumAdi"));
                        v2.add(rs.getFloat("QaimeNum"));

                    }
                    df.addRow(v2);

                }

            } else {

            }

        } catch (SQLException ex) {
            Logger.getLogger(Satici_Elave_Etmek.class.getName()).log(Level.SEVERE, null, ex);
        }

    }


    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed

        try {

            Qaime sonuncuMehsul = mehDao3.getBillingNumber();

            if (i != 0) {

                int billinNumber = sonuncuMehsul.getQaimeNumber();

                if (i == billinNumber) {

                    this.setVisible(false);

                } else {

                    int cavab = JOptionPane.showConfirmDialog(this, i + 1 + "№-li qaime yadda saxlanilmamisdir!\nYadda saxlamaq isteyirsinizmi?", "Diqqət!", JOptionPane.YES_NO_CANCEL_OPTION);
                    System.out.println(cavab);
                    if (cavab == 0) {
                        writeFileFromJtableToFile();
                        yaddaSaxla();
                        this.setVisible(false);
                    }

                    if (cavab == 1) {

                        int cavab2 = JOptionPane.showConfirmDialog(this, "Buna eminsiniz?", "Diqqet!", JOptionPane.YES_NO_OPTION);
                        System.out.println(cavab2);

                        if (cavab2 == 0) {

                            try {
                                pres = con.prepareStatement("truncate table mehsullar;");

                                pres.executeUpdate();
                                loadAfterSave();

                                txtKimden.setText("");
                                txtQurum.setText("");
                                txtAlisTarixi.setText(date2);
                                this.setVisible(false);

                            } catch (SQLException ex) {
                                ex.printStackTrace();
                            }
                        }

                    }

                }

            } else {

                Qaime ss = mehDao3.getBillingNumber();
                int sonqaimeNum = ss.getQaimeNumber();
                int qaimenomresi = Integer.parseInt(txtQaimeNum.getText());

                //int billinNumber = sonuncuMehsul2.getBillingNum();
                if (qaimenomresi == sonqaimeNum) {
                    this.setVisible(false);
                } else {

                    int cavab = JOptionPane.showConfirmDialog(this, qaimenomresi + "№-li qaime yadda saxlanilmamisdir!\nYadda saxlamaq isteyirsinizmi?", "Diqqət!", JOptionPane.YES_NO_CANCEL_OPTION);

                    if (cavab == 0) {
                        writeFileFromJtableToFile();
                        yaddaSaxla();
                        this.setVisible(false);
                    }
                    if (cavab == 1) {

                        int cavab2 = JOptionPane.showConfirmDialog(this, "Buna eminsiniz?", "Diqqet!", JOptionPane.YES_NO_OPTION);
                        System.out.println(cavab2);

                        if (cavab2 == 0) {

                            try {
                                pres = con.prepareStatement("truncate table mehsullar;");
                                pres.executeUpdate();

                                pres = con.prepareStatement("truncate table qaimeadlari;");
                                pres.executeUpdate();

                                loadAfterSave();
                                this.setVisible(false);

                            } catch (SQLException ex) {
                                ex.printStackTrace();
                            }

                        }
                        if (cavab == 1) {

                        }

                    }

                }
            }

        } catch (Exception ex) {

            this.setVisible(false);

            ex.printStackTrace();

        }


    }//GEN-LAST:event_jButton4ActionPerformed

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

    private void txtAlisQiymetiKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAlisQiymetiKeyReleased

        DecimalFormat dformater = new DecimalFormat("#.##");

        double miqdari = Double.parseDouble(txtMiqdari.getText());
        double alisqiymeti = Double.parseDouble(txtAlisQiymeti.getText());

        double cem = alisqiymeti * miqdari;

        String formattedresult = dformater.format(cem);
        txtUmumiMebleg.setText(formattedresult);

    }//GEN-LAST:event_txtAlisQiymetiKeyReleased

    private void txtMiqdariKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMiqdariKeyReleased

        DecimalFormat dformater = new DecimalFormat("#.##");

        double miqdari = Double.parseDouble(txtMiqdari.getText());
        double alisqiymeti = Double.parseDouble(txtAlisQiymeti.getText());

        double cem = alisqiymeti * miqdari;

        String formattedresult = dformater.format(cem);
        txtUmumiMebleg.setText(formattedresult);


    }//GEN-LAST:event_txtMiqdariKeyReleased

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

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        changeValueOfProduct();
        qaimeninHesablanmasi();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        deleteProduct();
        qaimeninHesablanmasi();

    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

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

    }//GEN-LAST:event_jButton3ActionPerformed

    private void AddExistProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddExistProductActionPerformed

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

            ExistingProducts movcudMehsullar = new ExistingProducts();

            movcudMehsullar.setVisible(true);
            this.setVisible(false);

        } catch (Exception ex) {
            Logger.getLogger(AlisQaimesi.class.getName()).log(Level.SEVERE, null, ex);
        }


    }//GEN-LAST:event_AddExistProductActionPerformed

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
            java.util.logging.Logger.getLogger(AlisQaimesi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AlisQaimesi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AlisQaimesi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AlisQaimesi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new AlisQaimesi().setVisible(true);
                } catch (Exception ex) {
                    Logger.getLogger(AlisQaimesi.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddExistProduct;
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnYaddaSaxla;
    private javax.swing.JComboBox<AltKateqoriyalar> cbAltKateqoriyaID;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
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
