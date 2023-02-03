package com.mycompany.DaoImple;

import com.mycompany.DaoInter.AbstractDAO;
import com.mycompany.DaoInter.AltKateqoriyalarDaoInter;
import com.mycompany.entity.AltKateqoriyalar;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author samil
 */
public class AltKateqoriyalarDaoImple extends AbstractDAO implements AltKateqoriyalarDaoInter {

    private AltKateqoriyalar getSubCategory(ResultSet rs) throws Exception {

        int id = rs.getInt("id");
        String name = rs.getString("Alt_kateqoriyanin_adi");

        return new AltKateqoriyalar(id, name);

    }

    @Override
    public List<AltKateqoriyalar> getAllAltKateqoriya() {

        List<AltKateqoriyalar> result = new ArrayList<>();

        try (Connection c = connect()) {

            Statement stmt = c.createStatement();
            stmt.execute("");

            ResultSet rs = stmt.getResultSet();
            while (rs.next()) {
                AltKateqoriyalar m = getSubCategory(rs);
                result.add(m);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    @Override
    public List<AltKateqoriyalar> getAltKateqoriyaBySeasonIdQadin() {

        List<AltKateqoriyalar> result = new ArrayList<>();

        try (Connection c = connect()) {

            Statement stmt = c.createStatement();
            stmt.execute("SELECT a.Alt_kateqoriyanin_adi, a.id FROM alt_kateqoriya_qadin a ");

            ResultSet rs = stmt.getResultSet();
            while (rs.next()) {
                AltKateqoriyalar m = getSubCategory(rs);
                result.add(m);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;

    }

    @Override
    public List<AltKateqoriyalar> getAltKateqoriyaByKateqoriyaId(int kateqoriyaId) {

        List<AltKateqoriyalar> result = new ArrayList<>();

        try (Connection c = connect()) {

            Statement stmt = c.createStatement();
            stmt.execute("SELECT\n"
                    + "	a.Alt_kateqoriyanin_adi, \n"
                    + " a.id \n"
                    + "FROM\n"
                    + "	alt_kateqoriya a\n"
                    + "	LEFT JOIN kateqoriylar k ON a.Kateqoriya_id = k.id \n"
                    + "WHERE\n"
                    + "	k.id = " + kateqoriyaId);

            ResultSet rs = stmt.getResultSet();
            while (rs.next()) {
                AltKateqoriyalar a = getSubCategory(rs);
                result.add(a);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;

    }

    @Override
    public List<AltKateqoriyalar> getAltKateqoriyaByKateqoriyaId_Qadin(int kateqoriyaId) {

        List<AltKateqoriyalar> result = new ArrayList<>();

        try (Connection c = connect()) {

            Statement stmt = c.createStatement();
            stmt.execute("SELECT\n"
                    + "	a.Alt_kateqoriyanin_adi, \n"
                    + " a.id \n"
                    + "FROM\n"
                    + "	alt_kateqoriya_qadin a\n"
                    + "	LEFT JOIN kateqoriylar k ON a.Kateqoriya_id = k.id \n"
                    + "WHERE\n"
                    + "	k.id = " + kateqoriyaId);

            ResultSet rs = stmt.getResultSet();
            while (rs.next()) {
                AltKateqoriyalar a = getSubCategory(rs);
                result.add(a);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;

    }

    @Override
    public List<AltKateqoriyalar> getAltKateqoriyaBySeasonIdKisi() {

        List<AltKateqoriyalar> result = new ArrayList<>();

        try (Connection c = connect()) {

            Statement stmt = c.createStatement();
            stmt.execute("SELECT a.Alt_kateqoriyanin_adi, a.id FROM alt_kateqoriya a  ");

            ResultSet rs = stmt.getResultSet();
            while (rs.next()) {
                AltKateqoriyalar m = getSubCategory(rs);
                result.add(m);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;

    }

    @Override
    public List<AltKateqoriyalar> getAltKateqoriyaBySeasonIdUsaq() {
        List<AltKateqoriyalar> result = new ArrayList<>();

        try (Connection c = connect()) {

            Statement stmt = c.createStatement();
            stmt.execute("SELECT a.Alt_kateqoriyanin_adi, a.id FROM alt_kateqoriya_usaq a  ");

            ResultSet rs = stmt.getResultSet();
            while (rs.next()) {
                AltKateqoriyalar m = getSubCategory(rs);
                result.add(m);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;

    }

    @Override
    public List<AltKateqoriyalar> getAltKateqoriyaByKateqoriyaId_Usaq(int kateqoriyaId) {

        List<AltKateqoriyalar> result = new ArrayList<>();

        try (Connection c = connect()) {

            Statement stmt = c.createStatement();
            stmt.execute("SELECT\n"
                    + "	a.Alt_kateqoriyanin_adi, \n"
                    + " a.id \n"
                    + "FROM\n"
                    + "	alt_kateqoriya_usaq a\n"
                    + "	LEFT JOIN kateqoriylar k ON a.Kateqoriya_id = k.id \n"
                    + "WHERE\n"
                    + "	k.id = " + kateqoriyaId);

            ResultSet rs = stmt.getResultSet();
            while (rs.next()) {
                AltKateqoriyalar a = getSubCategory(rs);
                result.add(a);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;

    }

    @Override
    public List<AltKateqoriyalar> getAltKateqoriyaBySeasonIdQarisiq() {

        List<AltKateqoriyalar> result = new ArrayList<>();

        try (Connection c = connect()) {

            Statement stmt = c.createStatement();
            stmt.execute("SELECT a.Alt_kateqoriyanin_adi, a.id FROM alt_kateqoriya_qarisiq a  ");

            ResultSet rs = stmt.getResultSet();
            while (rs.next()) {
                AltKateqoriyalar m = getSubCategory(rs);
                result.add(m);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;

    }

    @Override
    public List<AltKateqoriyalar> getAltKateqoriyaByKateqoriyaId_Qarisiq(int kateqoriyaId) {

        List<AltKateqoriyalar> result = new ArrayList<>();

        try (Connection c = connect()) {

            Statement stmt = c.createStatement();
            stmt.execute("SELECT\n"
                    + "	a.Alt_kateqoriyanin_adi, \n"
                    + " a.id \n"
                    + " FROM\n"
                    + "	alt_kateqoriya_qarisiq a \n"
                    + "	LEFT JOIN kateqoriylar k ON a.Kateqoriya_id = k.id \n"
                    + " WHERE\n"
                    + "	k.id = " + kateqoriyaId);

            ResultSet rs = stmt.getResultSet();
            while (rs.next()) {
                AltKateqoriyalar a = getSubCategory(rs);
                result.add(a);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;

    }

}
