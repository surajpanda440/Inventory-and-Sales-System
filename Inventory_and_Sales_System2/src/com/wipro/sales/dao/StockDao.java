
package com.wipro.sales.dao;

import com.wipro.sales.bean.Product;
import com.wipro.sales.util.DBUtil;

import java.sql.*;

public class StockDao {
    PreparedStatement ps;
    DBUtil obj = new DBUtil();
    Connection con = obj.getDBConnection();

    public int insertStock(Product sale) {
        int affectedrows = 0;
        try {
            ps = con.prepareStatement("insert into TBL_STOCK values (?, ?, ?, ?, ?)");
            ps.setString(1, sale.getProductID());
            ps.setString(2, sale.getProductName());
            ps.setInt(3, sale.getQuantityOnHand());
            ps.setDouble(4, sale.getProductUnitPrice());
            ps.setInt(5, sale.getReorderLevel());
            affectedrows = ps.executeUpdate();
            ps.executeQuery("commit");
            return affectedrows;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String generateProductID(String productName) {
        try {
            int nextID_from_seq = 0;
            String s = productName.substring(0, 2);
            ps = con.prepareStatement("select SEQ_PRODUCT_ID.nextval from dual");
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                nextID_from_seq = rs.getInt(1);

            //concatenate seq id and last 2 digits of year
            String productId = s + nextID_from_seq;
            //con.close();
            return productId;
        } catch (Exception e) {
            System.out.print(e);
        }
        return null;
    }

    public int updateStock(String productID, int soldQty) {
        int affectedrows = 0, currentQuan, remainingQuan;
        Product obj = new Product();
        try {
            ps = con.prepareStatement("select Quantity_On_Hand from TBL_STOCK where Product_ID = ?");
            ps.setString(1, productID);
            ResultSet rs = ps.executeQuery();
            int curQty = 0;
            if (rs.next()) {
                curQty = rs.getInt(1);
            }
            ps = con.prepareStatement("update tbl_stock set Quantity_On_Hand = ? where Product_ID = ?");
            ps.setInt(1, (curQty - soldQty));
            ps.setString(2, productID);
            affectedrows = ps.executeUpdate();
            ps.executeQuery("commit");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return affectedrows;
    }
    public Product getStock(String productID)
    {
        Product obj=new Product();
        try
        {
            ps=con.prepareStatement("select * from TBL_STOCK where Product_ID=?");
            ps.setString(1,productID);
            ResultSet rs=ps.executeQuery();
            if (rs.next())
            {
                obj.setProductID(rs.getString("Product_ID"));
                obj.setProductName(rs.getString("Product_Name"));
                obj.setQuantityOnHand(rs.getInt("Quantity_On_Hand"));
                obj.setProductUnitPrice(rs.getDouble("Product_Unit_Price"));
                obj.setReorderLevel(rs.getInt("Recorder_Level"));
            }
            return obj;
        }

        catch (SQLException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public int delStock(String productID)
    {
        int t=0,t1=0,t2;
        try
        {
            ps=con.prepareStatement("delete from TBL_SALES where Product_ID = ?");
            ps.setString(1, productID);
            t=ps.executeUpdate();
            ps.executeQuery("commit");
            ps=con.prepareStatement("delete from TBL_STOCK where Product_ID = ?");
            ps.setString(1, productID);
            t=ps.executeUpdate();
            ps.executeQuery("commit");
            //System.out.print("1");
            return 1;
            //con.close();
        }
        catch(Exception e)
        {
            System.out.print(e);
            //System.out.print("0D");
        }
        return 0;
    }
}
