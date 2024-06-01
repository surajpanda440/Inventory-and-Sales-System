package com.wipro.sales.service;

import com.wipro.sales.bean.Product;
import com.wipro.sales.bean.Sales;
import com.wipro.sales.bean.SalesReport;
import com.wipro.sales.dao.SalesDao;
import com.wipro.sales.dao.StockDao;
import com.wipro.sales.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;

public class Administrator {
    StockDao stockdao = new StockDao();
    SalesDao salesDao = new SalesDao();
    PreparedStatement ps;
    Connection con = (new DBUtil()).getDBConnection();
    public String insertStock(Product stockobj){
        int check = 0;
        String prodId = "";
        if(stockobj!=null && stockobj.getProductName().length() > 2){
            prodId = stockdao.generateProductID(stockobj.getProductName());
            stockobj.setProductID(prodId);
            check = stockdao.insertStock(stockobj);
        }
        if(check==1){
            return prodId;
        }
        else{
            return "Data not valid for insertion";
        }
    }

    public String deleteStock(String ProductID){
        int check = stockdao.delStock(ProductID);
        if(check == 1){
            return "deleted";
        }
        else{
            return "record cannot be deleted";
        }
    }

    public String insertSales(Sales salesobj) throws SQLException {
        int check = 0;
        if(salesobj== null){
            return "Object not Valid for Insertion";
        }
        if(salesobj.getProductID() == null){
            return "Unknown Product for sales";
        }
        int quantity = 0;
        ps= con.prepareStatement("select Quantity_On_Hand from TBL_STOCK where Product_ID=?");
        ps.setString(1,salesobj.getProductID());
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            quantity = rs.getInt(1);
        }
        if(quantity < salesobj.getQuantitySold()){
            return "Not enough stock on hand for sales";
        }
        java.sql.Date date = new Date(salesobj.getSalesDate().getTime());
        java.sql.Date currentDate=new java.sql.Date(new java.util.Date().getTime());
        if(date.compareTo(currentDate)>0)
        {
            return "Invalid Date";
        }
        String salesid = salesDao.generateSalesID(salesobj.getSalesDate());
        salesobj.setSalesID(salesid);
        check = salesDao.insertSales(salesobj);
        if(check == 1){
            return "Sales Completed";
        }
        else{
            return "Error";
        }
    }

    public ArrayList<SalesReport> getSalesReport(){
        return salesDao.getSalesReport();
    }
}