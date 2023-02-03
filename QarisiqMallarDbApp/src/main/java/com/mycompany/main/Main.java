/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.main;

import com.mycompany.DaoInter.MehsullarDaoInter;
import com.mycompany.DaoInter.QaimeDaoInter;
import com.mycompany.DaoInter.SatilanMehsullarDaoInter;
import com.mycompany.entity.Mehsullar;

/**
 *
 * @author samil
 */
public class Main {
    
    
    public static void main(String[] args) throws Exception {
        
       
        MehsullarDaoInter mehDao2 = Contex.instanceOfMehsullarDao();
    
        Mehsullar mm = mehDao2.getTheLastMehsulById();
        int miqdari = mm.getId();
        
   System.out.println(miqdari);
   
          
   
   
    }
    
    
    
}
