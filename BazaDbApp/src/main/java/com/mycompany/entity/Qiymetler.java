
package com.mycompany.entity;

/**
 *
 * @author samil
 */
public class Qiymetler {
  
  String id;
  String name;
  String Qiymet;
  String SubCategoryId;
  String CategoryID;
  String SeasonId;

    public Qiymetler() {
    }

    public Qiymetler(String id, String name, String Qiymet, String SubCategoryId, String CategoryID, String SeasonId) {
        this.id = id;
        this.name = name;
        this.Qiymet = Qiymet;
        this.SubCategoryId = SubCategoryId;
        this.CategoryID = CategoryID;
        this.SeasonId = SeasonId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
   
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQiymet() {
        return Qiymet;
    }

    public void setQiymet(String Qiymet) {
        this.Qiymet = Qiymet;
    }

    public String getSubCategoryId() {
        return SubCategoryId;
    }

    public void setSubCategoryId(String SubCategoryId) {
        this.SubCategoryId = SubCategoryId;
    }

    public String getCategoryID() {
        return CategoryID;
    }

    public void setCategoryID(String CategoryID) {
        this.CategoryID = CategoryID;
    }

    public String getSeasonId() {
        return SeasonId;
    }

    public void setSeasonId(String SeasonId) {
        this.SeasonId = SeasonId;
    }

    @Override
    public String toString() {
        return  ""+ id ;
    }

 

   

   

   

   

   
  
    
    
}
