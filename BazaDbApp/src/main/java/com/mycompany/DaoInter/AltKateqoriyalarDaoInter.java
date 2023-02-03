/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.DaoInter;

import com.mycompany.entity.AltKateqoriyalar;
import java.util.List;

/**
 *
 * @author samil
 */
public interface AltKateqoriyalarDaoInter {
    
    public List<AltKateqoriyalar> getAllAltKateqoriya();
    
    public List<AltKateqoriyalar> getAltKateqoriyaBySeasonIdQadin();
    public List<AltKateqoriyalar> getAltKateqoriyaBySeasonIdKisi();
    public List<AltKateqoriyalar> getAltKateqoriyaBySeasonIdUsaq();
    public List<AltKateqoriyalar> getAltKateqoriyaBySeasonIdQarisiq();
    
    public List<AltKateqoriyalar> getAltKateqoriyaByKateqoriyaId(int kateqoriyaId);
    
    public List<AltKateqoriyalar> getAltKateqoriyaByKateqoriyaId_Qadin(int kateqoriyaId);
    public List<AltKateqoriyalar> getAltKateqoriyaByKateqoriyaId_Usaq(int kateqoriyaId);
    public List<AltKateqoriyalar> getAltKateqoriyaByKateqoriyaId_Qarisiq(int kateqoriyaId);
}
