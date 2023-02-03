/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.DaoImple;

import com.mycompany.DaoInter.AbstractDAO;
import com.mycompany.DaoInter.QaimeDaoInter;
import com.mycompany.entity.Mehsullar;
import com.mycompany.entity.Qaime;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author Shamil
 */
public class QaimeDaoImple extends AbstractDAO implements QaimeDaoInter {

    
    
    private Qaime getProduct(ResultSet rs) throws Exception {

        int numberofBill = rs.getInt("QaimeNomresi");
        String nameOfBill = rs.getString("Kimden");
        String Org = rs.getString("Qurum");
        
       

        return new Qaime(numberofBill, nameOfBill, Org);
    }
    
    
    
    
    
    
    
  
    @Override
    public Qaime getBillingNumber() {
       
    Qaime result = null;

        try {

            Connection c;
            c = connect();
            Statement stmt = c.createStatement();
            stmt.execute("select m.* from alisqaimeleri m order by QaimeNomresi desc limit 1");
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
    

