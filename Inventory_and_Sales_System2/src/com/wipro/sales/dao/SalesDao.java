
package com.wipro.sales.dao;

import com.wipro.sales.bean.Sales;
import com.wipro.sales.bean.SalesReport;
import com.wipro.sales.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;

public class SalesDao {
    DBUtil obj  = new DBUtil();
    Connection con  = obj.getDBConnection();
    PreparedStatement stmt;

    public int insertSales(Sales sales){
        int affectedrows = 0;
        try{
            stmt = con.prepareStatement("Insert into tbl_sales values (?, ?, ?, ?, ?)");
            stmt.setString(1 , sales.getSalesID());
            stmt.setDate(2, (Date) sales.getSalesDate());
            stmt.setString(3 , sales.getProductID());
            stmt.setInt(4 , sales.getQuantitySold());
            stmt.setDouble(5 , sales.getSalesPricePerUnit());

            affectedrows=stmt.executeUpdate();
            stmt.executeQuery("commit");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return  affectedrows;

    }

    public String generateSalesID(java.util.Date salesDate) {
        try {
            java.sql.Date dateext = new java.sql.Date(salesDate.getTime());
            int sequence = 0;
            Calendar cal = Calendar.getInstance();
            cal.setTime(dateext);
            int year = cal.get(Calendar.YEAR);

            stmt = con.prepareStatement("select seq_sales_id.nextval from dual");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                sequence = rs.getInt(1);
            }
            String salesId = "" + (year % 100) + sequence;
            return salesId;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<SalesReport> getSalesReport(){
        ArrayList<SalesReport> sale = new ArrayList<SalesReport>();
        try{
            stmt = con.prepareStatement("select * from v_sales_report");
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                SalesReport obj = new SalesReport();
                obj.setSalesID(rs.getString("sales_id"));
                obj.setSalesDate(rs.getDate("sales_date"));
                obj.setProductID(rs.getString("product_id"));
                obj.setProductName(rs.getString("product_name"));
                obj.setQuantitySold(rs.getInt("quantity_sold"));
                obj.setProductUnitPrice(rs.getDouble("product_unit_price"));
                obj.setSalesPricePerUnit(rs.getDouble("sales_price_per_unit"));
                obj.setProfitAmount(rs.getDouble("profit_amount"));
                sale.add(obj);
            }
            return sale;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    }

