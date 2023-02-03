/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.DaoImple;





import com.mycompany.DaoInter.AbstractDAO;
import com.mycompany.DaoInter.QiymetlerDaoInter;
import com.mycompany.entity.Qiymetler;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;



/**
 *
 * @author samil
 */
public class QiymetlerDaoImple extends AbstractDAO implements QiymetlerDaoInter {

    private Qiymetler getPrice(ResultSet rs) throws Exception {
        String id = rs.getString("id");
        String name = rs.getString("Malin_adi");
        String seasonId = rs.getString("Movsum_id");
        String Price = rs.getString("Satis_qiymeti");
        String category = rs.getString("Kateqoriya_id");
        String subCategory = rs.getString("Alt_kateqoriya_id");

        return new Qiymetler (id,name, Price, subCategory, category, seasonId);
    }

    @Override
    public List<Qiymetler> getAllPrice() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Qiymetler> getPriceByCategoryAndSubCategoryAndSeasonIdAndByName(int CateqoryId, int SubCategoryId, int SeasonId, String name) {
       
    
     List<Qiymetler> result = new ArrayList<>();

        try (Connection c2 = connect()) {

            Statement stmt = c2.createStatement();
            stmt.execute("SELECT\n"
                    + "	m.id, \n"
                    + " m.Satis_qiymeti, \n"
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
                    + "	m.Kateqoriya_id = " +CateqoryId+" \n"
                    + "	AND m.Alt_Kateqoriya_id = "+ SubCategoryId +" \n"
                    + "	AND m.Movsum_id = "+SeasonId+" \n"
                    + "	AND m.Malin_adi =  "+ name);
            ResultSet rs = stmt.getResultSet();

            while (rs.next()) {

                Qiymetler q = getPrice(rs);
                result.add(q);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    
    }

}
