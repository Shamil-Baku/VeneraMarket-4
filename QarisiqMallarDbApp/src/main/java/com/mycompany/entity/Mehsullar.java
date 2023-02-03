/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.entity;

import java.sql.Date;

/**
 *
 * @author samil
 */
public class Mehsullar {
    
    private int id;
    private int mehsulId;
    private int howMuchLeft;
    private String id2;
    private String barcode;
    private String name;
    private Integer numberOfProduct;
    private double priceOfBuying;
    private double priceOfSell;
    private double totalValueOfSell;
    private Integer howMuchSold;
    private double profit;
    private double totalProfit;
    private String seasonId;
    private String categoryId;
    private String subCategoryId;
    private Date date;
    private int billingNum;
    private String status;
    
    private int howMuch;

    public Mehsullar() {
        
    }

    public Mehsullar(int id, String id2, String barcode, String name, Integer numberOfProduct, double priceOfBuying, double priceOfSell, String seasonId, String categoryId, String subCategoryId, int billingNum, int howMuch, String status) {
        
        this.id = id;
        this.id2 = id2;
        this.barcode = barcode;
        this.name = name;
        this.numberOfProduct = numberOfProduct;
        this.priceOfBuying = priceOfBuying;
        this.priceOfSell = priceOfSell;
        this.seasonId = seasonId;
        this.categoryId = categoryId;
        this.subCategoryId = subCategoryId;
        this.billingNum = billingNum;
        this.howMuch = howMuch;
        this.status = status;
        
    }
    
    public Mehsullar(int howMuchLeft) {

        this.howMuchLeft = howMuchLeft;

    }

    
     public Mehsullar(int id, String name, Integer number) {
        this.id = id;
        this.name = name;
        this.numberOfProduct = number;

    }

    public Mehsullar(int id, String name, Integer number, int mehsulId) {
        this.id = id;
        this.name = name;
        this.numberOfProduct = number;
        this.mehsulId = mehsulId;

    }
    
    String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getId2() {
        return id2;
    }

    public void setId2(String id2) {
        this.id2 = id2;
    }
    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumberOfProduct() {
        return numberOfProduct;
    }

    public void setNumberOfProduct(Integer numberOfProduct) {
        this.numberOfProduct = numberOfProduct;
    }

    public double getPriceOfBuying() {
        return priceOfBuying;
    }

    public void setPriceOfBuying(Integer priceOfBuying) {
        this.priceOfBuying = priceOfBuying;
    }

    public double getPriceOfSell() {
        return priceOfSell;
    }

    public void setPriceOfSell(double priceOfSell) {
        this.priceOfSell = priceOfSell;
    }

    public double getTotalValueOfSell() {
        return totalValueOfSell;
    }

    public void setTotalValueOfSell(Integer totalValueOfSell) {
        this.totalValueOfSell = totalValueOfSell;
    }

    public Integer getHowMuchSold() {
        return howMuchSold;
    }

    public void setHowMuchSold(Integer howMuchSold) {
        this.howMuchSold = howMuchSold;
    }

    public double getProfit() {
        return profit;
    }

    public void setProfit(Integer profit) {
        this.profit = profit;
    }

    public double getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(Integer totalProfit) {
        this.totalProfit = totalProfit;
    }

    public String getSeasonId() {
        return seasonId;
    }

    public void setSeasonId(String seasonId) {
        this.seasonId = seasonId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(String subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    
    

    @Override
    public String toString() {
        return   name ;
    
    }

    public int getBillingNum() {
        return billingNum;
    }

    public void setBillingNum(int billingNum) {
        this.billingNum = billingNum;
    }

    public int getHowMuch() {
        return howMuch;
    }

    public void setHowMuch(int howMuch) {
        this.howMuch = howMuch;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getMehsulId() {
        return mehsulId;
    }

    public void setMehsulId(int mehsulId) {
        this.mehsulId = mehsulId;
    }

    public int getHowMuchLeft() {
        return howMuchLeft;
    }

    public void setHowMuchLeft(int howMuchLeft) {
        this.howMuchLeft = howMuchLeft;
    }

    
   
    
    
    
    
}
