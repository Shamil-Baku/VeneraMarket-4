
package com.mycompany.DaoInter;

import com.mycompany.entity.Mehsullar;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;



/**
 *
 * @author samil
 */
public interface MehsullarDaoInter {

    public List<Mehsullar> getAllProduct();
    public Mehsullar getAllProduct2();
    public Mehsullar getAllProduct3();
    public Mehsullar getAllProductFromSebet();
    public Integer getAllProductFromSebet(int id, double qiymeti);
    
    public List<Mehsullar> search(String s);

    public List<Mehsullar> getMehsulByMovsumId(int MovsumId);

    public Mehsullar getMehsulByCategoryId(int id);
    
    public Mehsullar getMehsulById(int id);
    public double exploreProductSalesQuantityById(int id);
    public double exploreProductCreditQuantityById(int id);
    public Mehsullar exploreMehsulQaliqById(int id);
    public Mehsullar getMehsulByIdAcilanQaime(int id);
    public Mehsullar getTheLastMehsulById();
    public Mehsullar getTheLastMehsulById2();
    public Mehsullar getTheLastMehsulById3();
    public Mehsullar getTheLastMehsulById4();
    public Mehsullar getTheLastMehsulByIdAcilanQaime();
    public Mehsullar getTheLastBillingNum();
    public Integer getTheLastBillingNum2();
    public Mehsullar getTheLastBillingNum3();
    public Mehsullar getProductByBillingNum(int billingNum);
    public Mehsullar getMehsulByBarcode(String barcode);
    public Mehsullar getMehsulByBarcode2(String barcode);
    public Mehsullar getMehsulByBarcodeAcilanQaime(String barcode);

    public List<Mehsullar> getMehsulByCategoryAndSeasonId(int CateqoryId, int SeasonId);
    
    public List<Mehsullar> getMehsulByCategoryAndSubCategoryAndSeasonId(int CateqoryId, int SubCategoryId, int SeasonId);
    public Mehsullar getMehsulByCategoryAndSubCategoryAndSeasonId2(int CateqoryId, int SubCategoryId, int SeasonId);
    
    public List<Mehsullar> getMehsulByCategoryAndSubCategoryAndSeasonIdAndByName(int CateqoryId, int SubCategoryId, int SeasonId, String name);

    
    public boolean addProduct(Mehsullar p);
    

    
}
