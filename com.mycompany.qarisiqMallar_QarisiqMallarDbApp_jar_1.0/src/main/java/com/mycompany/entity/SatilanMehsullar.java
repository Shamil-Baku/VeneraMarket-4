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
public class SatilanMehsullar {
    private int satisID;
    private int id;
    private String id2;
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

    public SatilanMehsullar() {
    }

    public SatilanMehsullar( int satisID, int id, String id2, String name, Integer numberOfProduct, double priceOfBuying, double priceOfSell, String seasonId, String categoryId, String subCategoryId) {
        
        this.satisID = satisID;
        this.id = id;
        this.id2 = id2;
        this.name = name;
        this.numberOfProduct = numberOfProduct;
        this.priceOfBuying = priceOfBuying;
        this.priceOfSell = priceOfSell;
        this.seasonId = seasonId;
        this.categoryId = categoryId;
        this.subCategoryId = subCategoryId;
    }

    
    public SatilanMehsullar(int id, String id2, String name, Integer numberOfProduct, Integer priceOfBuying, double priceOfSell, Integer totalValueOfSell, Integer howMuchSold, Integer profit, Integer totalProfit, String seasonId, String categoryId, String subCategoryId) {
        this.id = id;
        this.name = name;
        this.numberOfProduct = numberOfProduct;
        this.priceOfBuying = priceOfBuying;
        this.priceOfSell = priceOfSell;
        this.totalValueOfSell = totalValueOfSell;
        this.howMuchSold = howMuchSold;
        this.profit = profit;
        this.totalProfit = totalProfit;
        this.seasonId = seasonId;
        this.categoryId = categoryId;
        this.subCategoryId = subCategoryId;
    }

    public int getSatisID() {
        return satisID;
    }

    public void setSatisID(int satisID) {
        this.satisID = satisID;
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

    
   
    
    
    
    
}
