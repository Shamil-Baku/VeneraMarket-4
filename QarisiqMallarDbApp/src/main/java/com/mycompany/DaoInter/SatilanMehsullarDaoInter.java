
package com.mycompany.DaoInter;

import com.mycompany.entity.Mehsullar;
import com.mycompany.entity.SatilanMehsullar;
import java.sql.Date;
import java.util.List;



/**
 *
 * @author samil
 */
public interface SatilanMehsullarDaoInter {

    public List<SatilanMehsullar> getAllProduct();
    
    public List<SatilanMehsullar> search(String s);

    public List<SatilanMehsullar> getMehsulByMovsumId(int MovsumId);

    public SatilanMehsullar getMehsulByCategoryId(int id);
    
    public SatilanMehsullar getMehsulById(int id);
    public SatilanMehsullar getTheLastMehsulById();
    public SatilanMehsullar getMehsulByBarcode(String barcode);

    public List<SatilanMehsullar> getMehsulByCategoryAndSeasonId(int CateqoryId, int SeasonId);
    
    public List<SatilanMehsullar> getMehsulByCategoryAndSubCategoryAndSeasonId(int CateqoryId, int SubCategoryId, int SeasonId);
    
    public List<SatilanMehsullar> getMehsulByCategoryAndSubCategoryAndSeasonIdAndByName(int CateqoryId, int SubCategoryId, int SeasonId, String name);

    
    public boolean addProduct(SatilanMehsullar p);
    

    
}
