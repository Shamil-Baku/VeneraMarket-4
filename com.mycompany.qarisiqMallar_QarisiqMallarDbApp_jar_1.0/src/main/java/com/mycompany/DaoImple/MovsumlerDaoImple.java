/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.DaoImple;

import com.mycompany.DaoInter.AbstractDAO;
import com.mycompany.DaoInter.MovsumlerDaoInter;
import com.mycompany.entity.Movsumler;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author samil
 */
public class MovsumlerDaoImple extends AbstractDAO implements MovsumlerDaoInter {

    private Movsumler getSeason(ResultSet rs) throws SQLException {

        String id = rs.getString("id");
        String MovsumAdi = rs.getString("Movsumler");

        return new Movsumler(id, MovsumAdi);

    }

    @Override
    public List<Movsumler> getAllSeasons() {
        
        
            List<Movsumler> result = new ArrayList<>();
            
            
        try (Connection c = connect()) {
             
            Statement stmt = c.createStatement();
            stmt.execute("SELECT * FROM `movsumler`");
            ResultSet rs = stmt.getResultSet();
            
            while(rs.next()){
                
              Movsumler m = getSeason(rs);
              result.add(m);
            }
            
            
            
        } catch (Exception ex) {
            Logger.getLogger(MovsumlerDaoImple.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return result;
    }

}
