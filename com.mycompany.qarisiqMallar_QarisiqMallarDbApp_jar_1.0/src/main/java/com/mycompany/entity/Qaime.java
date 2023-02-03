/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.entity;

/**
 *
 * @author Shamil
 */
public class Qaime {
    
    
    
    int qaimeNumber;
    String qaimeAdi;
    String qurum;
    
    
    public Qaime(){
        
        
    }
    
    public Qaime(int qaimeNumber, String qaimeAdi, String qurum){
        
        this.qaimeAdi=qaimeAdi;
        this.qaimeNumber=qaimeNumber;
        this.qurum=qurum;
        
        
    }

    public int getQaimeNumber() {
        return qaimeNumber;
    }

    protected void setQaimeNumber(int qaimeNumber) {
        this.qaimeNumber = qaimeNumber;
    }

    public String getQaimeAdi() {
        return qaimeAdi;
    }

    protected void setQaimeAdi(String qaimeAdi) {
        this.qaimeAdi = qaimeAdi;
    }

    public String getQurum() {
        return qurum;
    }

    protected void setQurum(String qurum) {
        this.qurum = qurum;
    }
    
    
    
    
    
}
