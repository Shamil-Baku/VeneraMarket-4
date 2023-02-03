/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.DaoImple;

import com.mycompany.DaoInter.AbstractDAO;
import com.mycompany.DaoInter.SatilanMehsullarDaoInter;
import com.mycompany.entity.Mehsullar;
import com.mycompany.entity.SatilanMehsullar;

import java.sql.Connection;
import java.sql.PreparedStatement;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author samil
 */
public class SatilanMehsullarDaoImple extends AbstractDAO implements SatilanMehsullarDaoInter {

    public SatilanMehsullarDaoImple() {
        
    }
    
    Connection con;
    PreparedStatement pres;
    DefaultTableModel df;

    private SatilanMehsullar getProduct(ResultSet rs) throws Exception {
        
        int satisID = rs.getInt("Satis_ID");
        int id = rs.getInt("id");
        String id2 = rs.getString("id");
        String name = rs.getString("Malin_adi");
        Integer number = rs.getInt("Miqdari");
        Integer priceOfBuying = rs.getInt("Alis_qiymeti");
        Double priceOfSell = rs.getDouble("Satis_qiymeti");
        String seasonId = rs.getString("Movsum_id");
        String category = rs.getString("Kateqoriya_id");
        String subCategory = rs.getString("Alt_kateqoriya_id");

        return new SatilanMehsullar( satisID, id, id2, name, number, priceOfBuying, priceOfSell, seasonId, category, subCategory);
    }


    @Override
    public List<SatilanMehsullar> getAllProduct() {
        List<SatilanMehsullar> result = new ArrayList<>();

        try (Connection c = connect()) {

            Statement stmt = c.createStatement();
            stmt.execute("SELECT\n"
                    + "	m.id,\n"
                    + "	m.Malin_adi,\n"
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
                    + "FROM\n"
                    + "	Mehsullar m\n"
                    + "	LEFT JOIN kateqoriylar k ON m.id = k.id\n"
                    + "	Left JOIN alt_kateqoriya a ON a.id = m.id;");
            ResultSet rs = stmt.getResultSet();

            while (rs.next()) {
                SatilanMehsullar m = getProduct(rs);
                result.add(m);
                
                
            }

        } catch (Exception ex) {

            ex.printStackTrace();
        }

        return result;
    }

    @Override
    public List<SatilanMehsullar> getMehsulByMovsumId(int movsumId) {

        List<SatilanMehsullar> result = new ArrayList<>();

        try (Connection c2 = connect()) {

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

                SatilanMehsullar m = getProduct(rs);
                result.add(m);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    @Override
    public SatilanMehsullar getMehsulByCategoryId(int CategoryId) {

        SatilanMehsullar result = null;

        try (Connection c2 = connect()) {

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
    public List<SatilanMehsullar> getMehsulByCategoryAndSeasonId(int CateqoryId, int SeasonId) {

        List<SatilanMehsullar> result = new ArrayList<>();

        try (Connection c2 = connect()) {

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
                SatilanMehsullar m = getProduct(rs);
                result.add(m);

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;

    }

    @Override
    public List<SatilanMehsullar> getMehsulByCategoryAndSubCategoryAndSeasonId(int CateqoryId, int SubCategoryId, int SeasonId) {

        List<SatilanMehsullar> result = new ArrayList<>();

        try (Connection c2 = connect()) {

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
                SatilanMehsullar m = getProduct(rs);
                result.add(m);

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;

    }

    @Override
    public List<SatilanMehsullar> getMehsulByCategoryAndSubCategoryAndSeasonIdAndByName(int CateqoryId, int SubCategoryId, int SeasonId, String name) {

        List<SatilanMehsullar> result = new ArrayList<>();

        try (Connection c2 = connect()) {

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
                SatilanMehsullar m = getProduct(rs);
                result.add(m);

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;

    }

    @Override
    public boolean addProduct(SatilanMehsullar p) {

        try (Connection c = connect()) {
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
    public SatilanMehsullar getMehsulById(int id) {

        SatilanMehsullar result = null;

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
    public List<SatilanMehsullar> search (String s) {
       
        List<SatilanMehsullar> result = new ArrayList<>();

        try {

            Connection c;
            c = connect();
            Statement stmt = c.createStatement();
            stmt.execute("select * from mehsullar m where m.Malin_adi like " +"'"+"%"+s+"%"+"'");
            ResultSet rs = stmt.getResultSet();

            while (rs.next()) {
            SatilanMehsullar m = getProduct(rs);
                result.add(m);

            }

        } catch (Exception ex) {
            ex.printStackTrace();

        }

        return result;

}

    @Override
    public SatilanMehsullar getMehsulByBarcode(String barcode) {
       
     SatilanMehsullar result = null;

        try {

            Connection c;
            c = connect();
            Statement stmt = c.createStatement();
            stmt.execute("SELECT * FROM satilan_mallar m  where m.Barcode =" + " " + barcode);
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
    public SatilanMehsullar getTheLastMehsulById() {
        
     SatilanMehsullar result = null;
     
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

  
}