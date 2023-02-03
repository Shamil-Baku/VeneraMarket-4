/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.DaoInter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

/**
 *
 * @author samil
 */
public abstract class AbstractDAO {
 Connection con;
    PreparedStatement pres;
    public Connection connect() throws Exception {

        Class.forName("com.mysql.cj.jdbc.Driver");

        String url = ("jdbc:mysql://localhost:3306/mehsullar");
        String username = ("root");
        String password = ("dxdiag92");
        Connection c = DriverManager.getConnection(url, username, password);
        return c;

    }

}
