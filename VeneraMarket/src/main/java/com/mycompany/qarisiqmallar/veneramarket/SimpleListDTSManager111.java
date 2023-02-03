/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qarisiqmallar.veneramarket;

import static com.mycompany.qarisiqmallar.veneramarket.TreeView1.jTableMehsullar;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.TransferHandler;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

/**
 *
 * @author Shamil
 */
public class SimpleListDTSManager111 extends TransferHandler {

    JTree.DropLocation DropLocation;

    Connection con;
    PreparedStatement pres;
    ResultSet rs;

    public Connection connect() {
        try {
            //Class.forName("com.mysql.jdbc.Driver");
            String url = ("jdbc:mysql://localhost:3306/mehsullar");
            String usercategoryOfProduct = ("root");
            String password = ("dxdiag92");
            con = DriverManager.getConnection(url, usercategoryOfProduct, password);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return con;

    }

    /**
     *
     * @param c
     * @return
     */
    @Override
    public int getSourceActions(JComponent c) {

        System.out.println("Bu iw oldu qaqaw");
        return (TransferHandler.COPY_OR_MOVE);
    }

    @Override
    protected Transferable createTransferable(JComponent c) {

        String data = "";
        try {

            jTableMehsullar = (JTable) c;
            DefaultTableModel df = (DefaultTableModel) jTableMehsullar.getModel();
            int columnCount = df.getColumnCount();
            int[] selectedRow = jTableMehsullar.getSelectedRows();
            int selectedRowIndex = jTableMehsullar.getSelectedRow();
            String values = "";
            int cixilan = 0;

            for (int j = 0; j < selectedRow.length; j++) {
                int selectedRoww = (selectedRow.length) - (selectedRow.length - cixilan);

                cixilan++;

                for (int i = 0; i < columnCount; i++) {
                    values = values + jTableMehsullar.getValueAt(selectedRowIndex, i).toString() + ":";
                    if (i == (columnCount - 1)) {
                        if (i == (columnCount - 1)) {
                            if ((selectedRoww + 1) != selectedRow.length) {
                                values = values + jTableMehsullar.getValueAt(selectedRowIndex, i).toString() + "\n";
                            }
                        }
                    }
                }
            }

            String finalValues[] = {values};

            for (Object str : finalValues) {
                data = data + str;
            }
            String trim = data.trim();
            System.out.println(data);
            System.out.println("createTransferable kecdik");

        } catch (Exception ex) {

            String errorMessage = ex.getMessage();

            JOptionPane.showMessageDialog(c, "Zehmet olmasa mehsul melumatlerini tam doldurun! \n" + errorMessage, "DIQQET!", MOVE);

        }
        return (new StringSelection(data));
    }

    @Override
    public boolean canImport(TransferSupport support) {

        Boolean procced = false;

        if (support.isDataFlavorSupported(DataFlavor.stringFlavor)) {
            procced = true;
        }

        System.out.println("CanImportu kecdik");
        return procced;

    }

    @Override
    public boolean importData(TransferSupport support) {

        System.out.println("ImportData-ni kecdik");

        Transferable exportTfr = support.getTransferable();
        String exportedData = exportTfr.toString();
        String[] listData = {exportedData};

        try {
            //String String = null;

            exportedData = (String) exportTfr.getTransferData(DataFlavor.stringFlavor);
            listData = exportedData.split("\n");

        } catch (UnsupportedFlavorException unsup) {

            System.out.println("Unsupported Exception");
            unsup.printStackTrace();

        } catch (IOException ioEx) {
            System.out.println("IOException");
            ioEx.printStackTrace();
        }

        JTree DropTarget = (JTree) support.getComponent();
        DropLocation = (JTree.DropLocation) support.getDropLocation();

        System.out.println("DropLocation = " + DropLocation);

        return true;

    }

    @Override
    protected void exportDone(JComponent c, Transferable data, int action) {

        try {

            System.out.println("Remove olmadi");

            DefaultMutableTreeNode treeModel = (DefaultMutableTreeNode) TreeView1.jTree1.getSelectionPath().getLastPathComponent();
            System.out.println(treeModel);

            String node1 = treeModel.getUserObject().toString();
            System.out.println("node1" + node1.toString());

            TreeNode node2 = treeModel.getParent();
            if (node2.toString() != null) {
                try {
                    TreeNode node3 = node2.getParent();
                    if (node3 != null) {

                        TreeNode node4 = node3.getParent();
                        if (node4 != null) {

                            TreeNode node5 = node4.getParent();
                            if (node5 != null) {

                                TreeNode node6 = node5.getParent();
                                if (node6 != null) {

                                } else {

                                    System.out.println("Men bura girdim");

                                    con = connect();
                                    pres = con.prepareStatement("select * from category where categories = " + "'" + node4 + "'");
                                    rs = pres.executeQuery();
                                    rs.next();
                                    int categoryID = rs.getInt("id");

                                    pres = con.prepareStatement("select * from subcategory where name = " + "'" + node3 + "'" + " and `index` =" + categoryID);
                                    rs = pres.executeQuery();
                                    rs.next();
                                    int subCategoryID = rs.getInt("id");

                                    pres = con.prepareStatement("select * from 2ndsubcategory where name = " + "'" + node2 + "'" + " and `index-gender` = " + subCategoryID + " and `index-season` = " + categoryID);
                                    rs = pres.executeQuery();
                                    rs.next();
                                    int secondSubCategoryID = rs.getInt("id");

                                    pres = con.prepareStatement("select * from 3ndsubcategory where name = " + "'" + node1 + "'" + " and `index-gender` = " + subCategoryID + " and `index-season` = " + categoryID + " and `index-3rdSubCat` = " + secondSubCategoryID);
                                    rs = pres.executeQuery();
                                    rs.next();
                                    int thirdSubCategoryID = rs.getInt("id");

                                    System.out.println("Metoda daxil oldum..");

                                    TreeView1.updateForThirdSubCategory(categoryID, subCategoryID, secondSubCategoryID, thirdSubCategoryID, c);

                                    TreeView1.deleteSelectedItemsFromTable(c, action);
                                }

                            } else {

                                con = connect();
                                pres = con.prepareStatement("select * from category where categories = " + "'" + node3 + "'");
                                rs = pres.executeQuery();
                                rs.next();
                                int categoryID = rs.getInt("id");

                                pres = con.prepareStatement("select * from subcategory where name = " + "'" + node2 + "'" + " and `index` =" + categoryID);
                                rs = pres.executeQuery();
                                rs.next();
                                int subCategoryID = rs.getInt("id");

                                pres = con.prepareStatement("select * from 2ndsubcategory where name = " + "'" + node1 + "'" + " and `index-gender` = " + subCategoryID + " and `index-season` = " + categoryID);
                                rs = pres.executeQuery();
                                rs.next();
                                int secondSubCategoryID = rs.getInt("id");

                                TreeView1.updateForCategoryIDSubcategoryIDSecondSubCategory(categoryID, subCategoryID, secondSubCategoryID, c);

                                TreeView1.deleteSelectedItemsFromTable(c, action);
                            }

                        } else {
                            // bura
                            int subCatId = 0;
                            int mainCategoryId = 0;
                            String category2 = node2.toString();

                            Connection c1 = connect();
                            pres = c1.prepareStatement("select * from category where categories = " + "'" + category2 + "'");
                            ResultSet rs = pres.executeQuery();
                            while (rs.next()) {
                                mainCategoryId = rs.getInt("id");
                            }
                            String category = node1.toString();

                            Connection c2 = connect();
                            pres = c2.prepareStatement("select * from subcategory where name = " + "'" + category + "'" + " and `index` =" + mainCategoryId);
                            ResultSet rs2 = pres.executeQuery();
                            while (rs2.next()) {
                                subCatId = rs2.getInt("id");
                            }
                            TreeView1.updateForCategoryIDSubcategoryID(mainCategoryId, subCatId, c);
                            TreeView1.deleteSelectedItemsFromTable(c, action);
                        }

                    } else {
                        String season = node1.toString();
                        try {
                            Connection c3 = connect();
                            pres = c3.prepareCall("select * from category where categories = " + "'" + season + "'");
                            ResultSet rs = pres.executeQuery();
                            while (rs.next()) {
                                int mainCategoryId = rs.getInt("id");
                                TreeView1.updateForCategoryID(mainCategoryId, c);
                                TreeView1.deleteSelectedItemsFromTable(c, action);
                            }
                        } catch (Exception ex) {
                            System.out.println(ex);
                        }

                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                    TreeNode node3 = node2.getParent();
                    System.out.println("Heleki mumkun deyil");
                }

            }

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Siz kokdesiniz");
        }

    }

}
