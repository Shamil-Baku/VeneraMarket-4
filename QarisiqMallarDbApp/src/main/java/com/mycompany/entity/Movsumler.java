/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.entity;

/**
 *
 * @author samil
 */
public class Movsumler {
    
    private String id;
    private String MovsumAdi;

    public Movsumler() {
    }

    public Movsumler(String id, String MovsumAdi) {
        this.id = id;
        this.MovsumAdi = MovsumAdi;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMovsumAdi() {
        return MovsumAdi;
    }

    public void setMovsumAdi(String MovsumAdi) {
        this.MovsumAdi = MovsumAdi;
    }

    @Override
    public String toString() {
        return "Movsumler{" + "id=" + id + ", MovsumAdi=" + MovsumAdi + '}';
    }
    
    
}
