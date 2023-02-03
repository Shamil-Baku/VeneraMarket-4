/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.main;

import com.mycompany.DaoImple.AltKateqoriyalarDaoImple;
import com.mycompany.DaoImple.MehsullarDaoImple;
import com.mycompany.DaoImple.MovsumlerDaoImple;
import com.mycompany.DaoImple.QaimeDaoImple;
import com.mycompany.DaoImple.QiymetlerDaoImple;
import com.mycompany.DaoImple.SatilanMehsullarDaoImple;

import com.mycompany.DaoInter.AltKateqoriyalarDaoInter;
import com.mycompany.DaoInter.MehsullarDaoInter;
import com.mycompany.DaoInter.MovsumlerDaoInter;
import com.mycompany.DaoInter.QaimeDaoInter;
import com.mycompany.DaoInter.QiymetlerDaoInter;
import com.mycompany.DaoInter.SatilanMehsullarDaoInter;

/**
 *
 * @author samil
 */
public class Contex {

    public static MehsullarDaoInter instanceOfMehsullarDao() {

        return new MehsullarDaoImple();

    }
    
    
    public static SatilanMehsullarDaoInter instanceOfSatilanMehsullarDao() {

        return new SatilanMehsullarDaoImple();

    }

    public static MovsumlerDaoInter instanceOfMovsumlerDao() {

        return new MovsumlerDaoImple();

    }

    public static AltKateqoriyalarDaoInter instanceOfAltKateqoriyalarDao() {

        return new AltKateqoriyalarDaoImple();

    }

    public static QiymetlerDaoInter instanceOfQiymetlerDao() {

        return new QiymetlerDaoImple();

    }
    
     public static QaimeDaoInter instanceOfQaimetlerDao() {

        return new QaimeDaoImple();

    }

    

}
