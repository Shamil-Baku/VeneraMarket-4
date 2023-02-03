/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.DaoInter;

import com.mycompany.entity.Qiymetler;
import java.util.List;







/**
 *
 * @author samil
 */
public interface QiymetlerDaoInter  {
    
    public List<Qiymetler> getAllPrice();
    
    public List<Qiymetler> getPriceByCategoryAndSubCategoryAndSeasonIdAndByName(int CateqoryId, int SubCategoryId, int SeasonId, String name);
    
    
    
}
