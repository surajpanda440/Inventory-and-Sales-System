
package com.wipro.sales.bean;

import java.util.Date;

public class Sales {
    private String salesID;
    private java.util.Date SalesDate;
    private String productID;
    private int quantitySold;
    private double salesPricePerUnit;

    public String getSalesID() {
        return salesID;
    }

    public void setSalesID(String salesID) {
        this.salesID = salesID;
    }

    public Date getSalesDate() {
        return SalesDate;
    }

    public void setSalesDate(Date salesDate) {
        SalesDate = salesDate;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public int getQuantitySold() {
        return quantitySold;
    }

    public void setQuantitySold(int quantitySold) {
        this.quantitySold = quantitySold;
    }

    public double getSalesPricePerUnit() {
        return salesPricePerUnit;
    }

    public void setSalesPricePerUnit(double salesPricePerUnit) {
        this.salesPricePerUnit = salesPricePerUnit;
    }
}
