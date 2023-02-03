/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.DaoImple;

import com.mycompany.DaoInter.AbstractDAO;
import com.mycompany.DaoInter.MehsullarDaoInter;
import com.mycompany.entity.Mehsullar;

import java.sql.Connection;
import java.sql.PreparedStatement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author samil
 */
public class MehsullarDaoImple extends AbstractDAO implements MehsullarDaoInter {

    public MehsullarDaoImple() {

    }

    Connection con;
    PreparedStatement pres;
    DefaultTableModel df;

    /**
     *
     * @throws java.sql.SQLException
     * @throws java.lang.ClassNotFoundException
     * @throws Exception
     */
    private Mehsullar getProduct(ResultSet rs) throws Exception {

        int id = rs.getInt("id");

        String id2 = rs.getString("id");
        String barcode = rs.getString("Barcode");
        String name = rs.getString("Malin_adi");
        Integer number = rs.getInt("Miqdari");
        Integer priceOfBuying = rs.getInt("Alis_qiymeti");
        Double priceOfSell = rs.getDouble("Satis_qiymeti");
        String seasonId = rs.getString("Movsum_id");
        String category = rs.getString("Kateqoriya_id");
        String subCategory = rs.getString("Alt_kateqoriya_id");
        int billingNum = rs.getInt("QaimeNum");
        int sayi = rs.getInt("Miqdari");
        String status = rs.getString("Status");

        return new Mehsullar(id, id2, barcode, name, number, priceOfBuying, priceOfSell, seasonId, category, subCategory, billingNum, sayi, status);
    }

    private int salesQuantity(ResultSet rs) throws Exception {

        int id = rs.getInt("id");
        String name = rs.getString("Malin_adi");
        Integer number = rs.getInt("Miqdari");

        return (number);
    }

    private int creditQuantity(ResultSet rs) throws Exception {

        int id = rs.getInt("id");
        String name = rs.getString("Borca_goturduyu_mehsul");
        Integer number = rs.getInt("Miqdari");

        return (number);
    }

    private Mehsullar getProduct3(ResultSet rs) throws Exception {

        int id = rs.getInt("id");
        String name = rs.getString("Borca_goturduyu_mehsul");
        Integer number = rs.getInt("Miqdari");
        int mehsulid = rs.getInt("Mehsul_ID");

        return new Mehsullar(id, name, number, mehsulid);
    }

    private Mehsullar getProduct4(ResultSet rs) throws Exception {

        int qaliqsay = rs.getInt("Qaliq_say");

        return new Mehsullar(qaliqsay);
    }

//    private Mehsullar getProduct3(ResultSet rs) throws Exception {
//
//        int billingNum = rs.getInt("QaimeNomresi");
//
//        return new Mehsullar(billingNum);
//    }
    @Override
    public List<Mehsullar> getAllProduct() {
        List<Mehsullar> result = new ArrayList<>();

        try ( Connection c = connect()) {

            Statement stmt = c.createStatement();
            stmt.execute("SELECT\n"
                    + "	m.id,\n"
                    + "	m.Malin_adi,\n"
                    + "	m.Miqdari,\n"
                    + "	k.Malin_kateqoriyasi,\n"
                    + "	a.Alt_kateqoriyanin_adi,\n"
                    + "	m.Miqdari,\n"
                    + "	m.Alis_qiymeti,\n"
                    + "	m.Satis_qiymeti,\n"
                    + "	m.Satis_miqdari,\n"
                    + "	m.Menfeet,\n"
                    + "	m.Movsum_id,\n"
                    + "	m.Kateqoriya_id,\n"
                    + "	m.alt_kateqoriya \n"
                    + "	m.QaimeNum \n"
                    + "FROM\n"
                    + "	mehsullar_copy1 m\n"
                    + "	LEFT JOIN kateqoriylar k ON m.id = k.id\n"
                    + "	Left JOIN alt_kateqoriya a ON a.id = m.id;");
            ResultSet rs = stmt.getResultSet();

            while (rs.next()) {
                Mehsullar m = getProduct(rs);
                result.add(m);

            }

        } catch (Exception ex) {

            ex.printStackTrace();
        }

        return result;
    }

    @Override
    public List<Mehsullar> getMehsulByMovsumId(int movsumId) {

        List<Mehsullar> result = new ArrayList<>();

        try ( Connection c2 = connect()) {

            Statement stmt = c2.createStatement();
            stmt.execute("SELECT\n"
                    + "	m.id,\n"
                    + "	m.Alis_qiymeti,\n"
                    + "	m.Kateqoriya_id,\n"
                    + "	m.Alt_kateqoriya_id,\n"
                    + "	m.Malin_adi,\n"
                    + "	m.Satis_qiymeti,\n"
                    + "	m.Miqdari,\n"
                    + "	m.Movsum_id \n"
                    + "FROM\n"
                    + "	mehsullar m\n"
                    + "	LEFT JOIN movsumler m2 ON m.Movsum_id = m2.id \n"
                    + "WHERE\n"
                    + "	m2.id = " + movsumId);
            ResultSet rs = stmt.getResultSet();

            while (rs.next()) {

                Mehsullar m = getProduct(rs);
                result.add(m);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    @Override
    public Mehsullar getMehsulByCategoryId(int CategoryId) {

        Mehsullar result = null;

        try ( Connection c2 = connect()) {

            Statement stmt = c2.createStatement();
            stmt.execute("SELECT\n"
                    + "	m.id,\n"
                    + "	m.Malin_adi,\n"
                    + " m.Alis_qiymeti,\n"
                    + "	m.Satis_qiymeti,\n"
                    + " m.Kateqoriya_id,\n"
                    + " m.Alt_kateqoriya_id,\n"
                    + "	m.Miqdari,\n"
                    + "	m.Movsum_id\n"
                    + "FROM\n"
                    + "	mehsullar m\n"
                    + "	LEFT JOIN kateqoriylar k ON k.id = m.id \n"
                    + "WHERE\n"
                    + "	k.id = " + CategoryId);
            ResultSet rs = stmt.getResultSet();

            while (rs.next()) {

                result = getProduct(rs);

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;

    }

    @Override
    public List<Mehsullar> getMehsulByCategoryAndSeasonId(int CateqoryId, int SeasonId) {

        List<Mehsullar> result = new ArrayList<>();

        try ( Connection c2 = connect()) {

            Statement stmt = c2.createStatement();
            stmt.execute("SELECT\n"
                    + "	m.id,\n"
                    + "	m.Malin_adi,\n"
                    + "	m.Satis_qiymeti,\n"
                    + "	m.Alis_qiymeti,\n"
                    + "	m.Kateqoriya_id,\n"
                    + "	m.Alt_kateqoriya_id,\n"
                    + "	m.Miqdari,\n"
                    + "	m.Movsum_id,\n"
                    + "	m2.Movsumler,\n"
                    + "	k.Malin_kateqoriyasi,\n"
                    + "	k.id \n"
                    + "FROM\n"
                    + "	mehsullar m\n"
                    + "	LEFT JOIN kateqoriylar k ON m.Kateqoriya_id = k.id\n"
                    + "	RIGHT JOIN movsumler m2 ON m.Movsum_id = m2.id \n"
                    + "WHERE\n"
                    + "	m2.id = " + SeasonId + "\n"
                    + "	AND k.id = " + CateqoryId);
            ResultSet rs = stmt.getResultSet();

            while (rs.next()) {
                Mehsullar m = getProduct(rs);
                result.add(m);

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;

    }

    @Override
    public List<Mehsullar> getMehsulByCategoryAndSubCategoryAndSeasonId(int CateqoryId, int SubCategoryId, int SeasonId) {

        List<Mehsullar> result = new ArrayList<>();

        try ( Connection c2 = connect()) {

            Statement stmt = c2.createStatement();
            stmt.execute("SELECT\n"
                    + " m.id, \n"
                    + "	m.Malin_adi, \n"
                    + " m.Miqdari, \n"
                    + " m.Alis_qiymeti, \n"
                    + " m.Satis_qiymeti, \n"
                    + " m.Kateqoriya_id, \n"
                    + " m.Alt_kateqoriya_id, \n"
                    + " m.Movsum_id \n"
                    + "FROM\n"
                    + "	mehsullar m\n"
                    + "	where m.Kateqoriya_id =" + CateqoryId + " " + " and m.Alt_Kateqoriya_id=" + SubCategoryId + " " + "and m.Movsum_id = " + SeasonId);
            ResultSet rs = stmt.getResultSet();

            while (rs.next()) {
                Mehsullar m = getProduct(rs);
                result.add(m);

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;

    }

    @Override
    public List<Mehsullar> getMehsulByCategoryAndSubCategoryAndSeasonIdAndByName(int CateqoryId, int SubCategoryId, int SeasonId, String name) {

        List<Mehsullar> result = new ArrayList<>();

        try ( Connection c2 = connect()) {

            Statement stmt = c2.createStatement();
            stmt.execute("SELECT\n"
                    + "	m.Satis_qiymeti, \n"
                    + " m.Malin_adi, \n"
                    + " m.Miqdari, \n"
                    + " m.Alis_qiymeti, \n"
                    + " m.Movsum_id, \n"
                    + " m.Kateqoriya_id, \n"
                    + " m.Alt_kateqoriya_id, \n"
                    + " m.id \n"
                    + "FROM\n"
                    + "	mehsullar m \n"
                    + "WHERE\n"
                    + "	m.Kateqoriya_id = " + CateqoryId + " \n"
                    + "	AND m.Alt_Kateqoriya_id = " + SubCategoryId + " \n"
                    + "	AND m.Movsum_id = " + SeasonId + " \n"
                    + "	AND m.Malin_adi =  " + name);
            ResultSet rs = stmt.getResultSet();

            while (rs.next()) {
                Mehsullar m = getProduct(rs);
                result.add(m);

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;

    }

    @Override
    public boolean addProduct(Mehsullar p) {

        try ( Connection c = connect()) {
            PreparedStatement stmt = c.prepareStatement("insert into user(Malin_adi, Miqdari,Alis_qiymeti, Movsum_id,Kateqoriya_id, Alt_kateqoriya_id, Alis_Tarixi) values(?,?,?,?,?,?,?)");

            stmt.setString(1, p.getName());
            stmt.setInt(2, p.getNumberOfProduct());
            stmt.setDouble(3, p.getPriceOfBuying());
            stmt.setString(4, p.getSeasonId());
            stmt.setString(5, p.getCategoryId());
            stmt.setString(6, p.getSubCategoryId());
            stmt.setDate(7, p.getDate());

            return stmt.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }

    }

    @Override
    public Mehsullar getMehsulById(int id) {

        Mehsullar result = null;

        try {

            Connection c;
            c = connect();
            Statement stmt = c.createStatement();
            stmt.execute("SELECT * FROM mehsullar m  where m.id =" + " " + id);
            ResultSet rs = stmt.getResultSet();

            while (rs.next()) {

                result = getProduct(rs);

            }

        } catch (Exception ex) {
            ex.printStackTrace();

        }

        return result;
    }

    @Override
    public List<Mehsullar> search(String s) {

        List<Mehsullar> result = new ArrayList<>();

        try {

            Connection c;
            c = connect();
            Statement stmt = c.createStatement();
            stmt.execute("select * from mehsullar m where m.Malin_adi like " + "'" + "%" + s + "%" + "'");
            ResultSet rs = stmt.getResultSet();

            while (rs.next()) {
                Mehsullar m = getProduct(rs);
                result.add(m);

            }

        } catch (Exception ex) {
            ex.printStackTrace();

        }

        return result;

    }

    @Override
    public Mehsullar getMehsulByBarcode(String barcode) {

        Mehsullar result = null;

        try {

            Connection c;
            c = connect();
            Statement stmt = c.createStatement();
            stmt.execute("SELECT * FROM mehsullar m  where m.Barcode =" + " " + barcode);
            ResultSet rs = stmt.getResultSet();

            while (rs.next()) {

                result = getProduct(rs);

            }

        } catch (Exception ex) {
            ex.printStackTrace();

        }

        return result;

    }

    @Override
    public Mehsullar getTheLastMehsulById() {

        Mehsullar result = null;

        try {

            Connection c;
            c = connect();
            Statement stmt = c.createStatement();
            stmt.execute("select m.* from mehsullar m order by id desc limit 1");
            ResultSet rs = stmt.getResultSet();

            while (rs.next()) {

                result = getProduct(rs);

            }

        } catch (Exception ex) {
            ex.printStackTrace();

        }

        return result;

    }

    @Override
    public Mehsullar getTheLastMehsulById2() {

        Mehsullar result = null;

        try {

            Connection c;
            c = connect();
            Statement stmt = c.createStatement();
            stmt.execute("select m.* from mehsullar_copy1 m order by id desc limit 1");
            ResultSet rs = stmt.getResultSet();

            while (rs.next()) {

                result = getProduct(rs);

                if (result == null) {

                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();

        }

        return result;

    }

    @Override
    public Mehsullar getTheLastMehsulById3() {

        Mehsullar result = null;

        try {

            Connection c;
            c = connect();
            Statement stmt = c.createStatement();
            stmt.execute("select m.* from mehsullar_copy1 m order by id desc limit 1");
            ResultSet rs = stmt.getResultSet();

            while (rs.next()) {

                result = getProduct(rs);

            }

        } catch (Exception ex) {
            ex.printStackTrace();

        }

        return result;

    }

    @Override
    public Mehsullar getTheLastMehsulById4() {

        Mehsullar result = null;

        try {

            Connection c;
            c = connect();
            Statement stmt = c.createStatement();
            stmt.execute("select m.* from mehsullar_copy1 m order by Barcode desc limit 1");
            ResultSet rs = stmt.getResultSet();

            while (rs.next()) {

                result = getProduct(rs);

            }

        } catch (Exception ex) {
            ex.printStackTrace();

        }

        return result;

    }

    @Override
    public Integer getAllProductFromSebet(int id, double qiymeti) {

        Integer i = 0;

        try ( Connection c = connect()) {

            Statement stmt = c.createStatement();
            stmt.execute("select * from sebet");
            ResultSet rs = stmt.getResultSet();

            while (rs.next()) {
                int id1 = rs.getInt("id");
                int miqdari = rs.getInt("Miqdari");
                double umumiMebleg = rs.getDouble("Umumi_Mebleg");
                System.out.println(id1);
                if (id1 == id) {

                    try {
                        pres = c.prepareStatement("update sebet set Miqdari=?,Umumi_Mebleg=? where id =?");

                        pres.setInt(1, miqdari + 1);
                        pres.setDouble(2, umumiMebleg + qiymeti);
                        pres.setInt(3, id);
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

    /**
     *
     * @return
     */
    @Override
    public Mehsullar getTheLastBillingNum() {

        Mehsullar result = null;

        try {

            Connection c;
            c = connect();
            Statement stmt = c.createStatement();
            stmt.execute("select m.* from alisqaimeleri m order by QaimeNum desc limit 1");
            ResultSet rs = stmt.getResultSet();

            while (rs.next()) {

                result = getProduct(rs);

            }

        } catch (Exception ex) {
            ex.printStackTrace();

        }

        return result;

    }

    @Override
    public Integer getTheLastBillingNum2() {

        int i = 0;

        try {

            Connection c;
            c = connect();
            Statement stmt = c.createStatement();
            stmt.execute("select a.* from alisqaimeleri a order by QaimeNomresi desc limit 1");
            ResultSet rs = stmt.getResultSet();

            while (rs.next()) {

                i = rs.getInt("QaimeNomresi");

            }

        } catch (Exception ex) {
            ex.printStackTrace();

        }

        return i;

    }

    @Override
    public Mehsullar getProductByBillingNum(int billingNum) {

        Mehsullar result = null;

        try {

            Connection c;
            c = connect();
            Statement stmt = c.createStatement();
            Statement stmt2 = c.createStatement();
            Statement stmt3 = c.createStatement();
            stmt2.execute("truncate table qaimemehsullari;");
            stmt.execute("SELECT * FROM mehsullar m  where m.QaimeNum =" + billingNum);
            ResultSet rs = stmt.getResultSet();

            while (rs.next()) {

                int id1 = rs.getInt("id");
                String name = rs.getString("Malin_adi");
                int miqdari = rs.getInt("Miqdari");
                double alisQiymeti = rs.getDouble("Alis_qiymeti");
                double alisinToplamDeyeri = rs.getDouble("Alisin_toplam_deyer");
                double satisQiymeti = rs.getDouble("Satis_qiymeti");
                int movsumId = rs.getInt("Movsum_id");
                int kateqoriyaID = rs.getInt("Kateqoriya_id");
                int altKateqoriyaID = rs.getInt("Alt_kateqoriya_id");
                String tarix = rs.getString("Alis_Tarixi");
                String barcode = rs.getString("Barcode");
                String kimden = rs.getString("Kimden");
                String qurumAdi = rs.getString("QurumAdi");
                float qaimeNum = rs.getFloat("QaimeNum");

                try ( Connection c2 = connect()) {

                    pres = c.prepareStatement("insert into qaimemehsullari ( id, Malin_adi, Miqdari, Alis_qiymeti, Alisin_toplam_deyer,Satis_qiymeti, Movsum_id, Kateqoriya_id, Alt_kateqoriya_id, Barcode, Kimden, QurumAdi, QaimeNum, Alis_Tarixi ) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                    //ResultSet rss = stmt3.getResultSet();

                    pres.setInt(1, id1);
                    pres.setString(2, name);
                    pres.setInt(3, miqdari);
                    pres.setDouble(4, alisQiymeti);
                    pres.setDouble(5, alisinToplamDeyeri);
                    pres.setDouble(6, satisQiymeti);
                    pres.setInt(7, movsumId);
                    pres.setInt(8, kateqoriyaID);
                    pres.setInt(9, altKateqoriyaID);
                    pres.setString(10, barcode);
                    pres.setString(11, kimden);
                    pres.setString(12, qurumAdi);
                    pres.setFloat(13, qaimeNum);
                    pres.setString(14, tarix);
                    pres.executeUpdate();

                } catch (Exception ex) {
                    ex.printStackTrace();

                }

            }

        } catch (Exception ex) {

        }

        return result;

    }

    @Override
    public Mehsullar getTheLastBillingNum3() {

        Mehsullar result = null;

        try {

            Connection c;
            c = connect();
            Statement stmt = c.createStatement();
            stmt.execute("select m.* from mehsullar m order by QaimeNum desc limit 1");
            ResultSet rs = stmt.getResultSet();

            while (rs.next()) {

                result = getProduct(rs);

            }

        } catch (Exception ex) {
            ex.printStackTrace();

        }

        return result;

    }

    @Override
    public Mehsullar getMehsulByBarcode2(String barcode) {

        Mehsullar result = null;

        try {

            Connection c;
            c = connect();
            Statement stmt = c.createStatement();
            stmt.execute("SELECT * FROM mehsullar_copy1 m  where m.Barcode =" + " " + barcode);
            ResultSet rs = stmt.getResultSet();

            while (rs.next()) {

                result = getProduct(rs);

            }

        } catch (Exception ex) {
            ex.printStackTrace();

        }

        return result;

    }

    @Override
    public Mehsullar getAllProduct2() {

        Mehsullar result = null;

        try ( Connection c = connect()) {

            Statement stmt = c.createStatement();
            stmt.execute("SELECT\n"
                    + "	m.id,\n"
                    + "	m.Malin_adi,\n"
                    + "	m.Miqdari,\n"
                    + "	k.Malin_kateqoriyasi,\n"
                    + "	a.Alt_kateqoriyanin_adi,\n"
                    + "	m.Miqdari,\n"
                    + "	m.Alis_qiymeti,\n"
                    + "	m.Satis_qiymeti,\n"
                    + "	m.Satis_miqdari,\n"
                    + "	m.Menfeet,\n"
                    + "	m.Movsum_id,\n"
                    + "	m.Kateqoriya_id,\n"
                    + "	m.alt_kateqoriya \n"
                    + "	m.QaimeNum \n"
                    + "FROM\n"
                    + "	mehsullar_copy1 m\n"
                    + "	LEFT JOIN kateqoriylar k ON m.id = k.id\n"
                    + "	Left JOIN alt_kateqoriya a ON a.id = m.id;");
            ResultSet rs = stmt.getResultSet();

            while (rs.next()) {
                result = getProduct(rs);

            }

        } catch (Exception ex) {

            ex.printStackTrace();
        }

        return result;

    }

    @Override
    public Mehsullar getTheLastMehsulByIdAcilanQaime() {

        Mehsullar result = null;

        try {

            Connection c;
            c = connect();
            Statement stmt = c.createStatement();
            stmt.execute("select m.* from acilanqaime m order by id desc limit 1");
            ResultSet rs = stmt.getResultSet();

            while (rs.next()) {

                result = getProduct(rs);

            }

        } catch (Exception ex) {
            ex.printStackTrace();

        }

        return result;

    }

    @Override
    public Mehsullar getMehsulByBarcodeAcilanQaime(String barcode) {

        Mehsullar result = null;

        try {

            Connection c;
            c = connect();
            Statement stmt = c.createStatement();
            stmt.execute("SELECT * FROM acilanqaime m  where m.Barcode =" + " " + barcode);
            ResultSet rs = stmt.getResultSet();

            while (rs.next()) {

                result = getProduct(rs);

            }

        } catch (Exception ex) {
            ex.printStackTrace();

        }

        return result;

    }

    @Override
    public Mehsullar getMehsulByIdAcilanQaime(int id) {

        Mehsullar result = null;

        try {

            Connection c;
            c = connect();
            Statement stmt = c.createStatement();
            stmt.execute("SELECT * FROM acilanqaime m  where m.id =" + " " + id);
            ResultSet rs = stmt.getResultSet();

            while (rs.next()) {

                result = getProduct(rs);

            }

        } catch (Exception ex) {
            ex.printStackTrace();

        }

        return result;

    }

    @Override
    public Mehsullar getAllProduct3() {

        Mehsullar result = null;

        try ( Connection c = connect()) {

            Statement stmt = c.createStatement();
            stmt.execute("SELECT * from qaimededeyisiklik");
            ResultSet rs = stmt.getResultSet();

            while (rs.next()) {
                result = getProduct(rs);

            }

        } catch (Exception ex) {

            ex.printStackTrace();
        }

        return result;

    }

    @Override
    public Mehsullar getAllProductFromSebet() {

        Mehsullar result = null;

        try ( Connection c = connect()) {

            Statement stmt = c.createStatement();
            stmt.execute("SELECT * from sebet");
            ResultSet rs = stmt.getResultSet();

            while (rs.next()) {
                result = getProduct(rs);

            }

        } catch (Exception ex) {

            ex.printStackTrace();
        }

        return result;

    }

    @Override
    public double exploreProductSalesQuantityById(int id) {

        double say = 0;
        double netice = 0;

        try {

            Connection c;
            c = connect();
            Statement stmt = c.createStatement();
            stmt.execute("SELECT\n"
                    + "	s.Malin_adi,\n"
                    + " s.id, \n"
                    + " s.Miqdari, \n"
                    + "	s.Satis_Tarixi,\n"
                    + "	s.Borc_alanin_adi\n"
                    + "FROM\n"
                    + "	satilan_mallar s\n"
                    + "	where s.id = " + id);
            ResultSet rs = stmt.getResultSet();

            while (rs.next()) {
                say = salesQuantity(rs);
                netice += say;

            }

        } catch (Exception ex) {
            ex.printStackTrace();

        }

        return netice;

    }

    @Override
    public double exploreProductCreditQuantityById(int id) {

        double say = 0;
        double netice = 0;

        try {

            Connection c;
            c = connect();
            Statement stmt = c.createStatement();
            stmt.execute("SELECT\n"
                    + "	s.Borca_goturduyu_mehsul,\n"
                    + " s.Mehsul_ID, \n"
                    + " s.id, \n"
                    + " s.Miqdari, \n"
                    + "	s.Borc_alma_tarixi,\n"
                    + "	s.Borc_alanin_adi\n"
                    + "FROM\n"
                    + "	borclar_siyahisi s\n"
                    + "	where s.Mehsul_ID = " + id);
            ResultSet rs = stmt.getResultSet();

            while (rs.next()) {
                say = creditQuantity(rs);
                netice += say;
            }

        } catch (Exception ex) {
            ex.printStackTrace();

        }

        return netice;

    }

    @Override
    public Mehsullar exploreMehsulQaliqById(int id) {

        Mehsullar result = null;

        try {

            Connection c;
            c = connect();
            Statement stmt = c.createStatement();
            stmt.execute("SELECT\n"
                    + "	s.Qaliq_say\n"
                    + "	\n"
                    + "FROM\n"
                    + "	mehsullar s\n"
                    + "	where s.id = " + id);
            ResultSet rs = stmt.getResultSet();

            while (rs.next()) {
                result = getProduct4(rs);

            }

        } catch (Exception ex) {
            ex.printStackTrace();

        }

        return result;

    }

    @Override
    public Mehsullar getMehsulByCategoryAndSubCategoryAndSeasonId2(int CateqoryId, int SubCategoryId, int SeasonId) {
       
     Mehsullar result = null;

        try ( Connection c2 = connect()) {

            Statement stmt = c2.createStatement();
            stmt.execute("SELECT\n"
                    + " m.id, \n"
                    + "	m.Malin_adi, \n"
                    + " m.Miqdari, \n"
                    + " m.Alis_qiymeti, \n"
                    + " m.Satis_qiymeti, \n"
                    + " m.Kateqoriya_id, \n"
                    + " m.Alt_kateqoriya_id, \n"
                    + " m.Movsum_id \n"
                    + "FROM\n"
                    + "	mehsullar m\n"
                    + "	where m.Kateqoriya_id =" + CateqoryId + " " + " and m.Alt_Kateqoriya_id=" + SubCategoryId + " " + "and m.Movsum_id = " + SeasonId);
            ResultSet rs = stmt.getResultSet();

            while (rs.next()) {
                Mehsullar m = getProduct(rs);
                m.getName();

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;

    
    
    }

}

