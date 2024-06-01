package com.wipro.sales.main;

import com.wipro.sales.bean.Product;
import com.wipro.sales.bean.Sales;
import com.wipro.sales.bean.SalesReport;
import com.wipro.sales.service.Administrator;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;

public class SalesApplication {
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        Administrator administrator = new Administrator();

        while (true) {
            System.out.println("Main Menu:");
            System.out.println("1. Add a Product");
            System.out.println("2. Add a Sale");
            System.out.println("3. View Sales Report");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    addProduct(administrator);
                    break;
                case 2:
                    addSale(administrator);
                    break;
                case 3:
                    viewSalesReport(administrator);
                    break;
                case 4:
                    System.out.println("Exiting the program. Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void addProduct(Administrator administrator) {
        Scanner scanner = new Scanner(System.in);
        Product product = new Product();
        System.out.print("Enter Product ID: ");
        product.setProductID(scanner.nextLine());
        System.out.print("Enter Product Name: ");
        product.setProductName(scanner.nextLine());
        int quantityOnHand = 0;
        boolean isValidInput = false;
        while (!isValidInput) {
            System.out.print("Enter Quantity on Hand: ");
            String input = scanner.next();
            try {
                quantityOnHand = Integer.parseInt(input);
                isValidInput = true;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }
        product.setQuantityOnHand(quantityOnHand);
        System.out.print("Enter Product Unit Price: ");
        product.setProductUnitPrice(scanner.nextDouble());
        System.out.print("Enter Reorder Level: ");
        product.setReorderLevel(scanner.nextInt());
        administrator.insertStock(product);
    }

    private static void addSale(Administrator administrator) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        Sales sales = new Sales();
        System.out.print("Enter Sales ID: ");
        sales.setSalesID(scanner.next());
        System.out.print("Enter Sales Date (YYYY-MM-DD): ");
        String dateInput = scanner.next();
        java.util.Date salesDate;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            salesDate = dateFormat.parse(dateInput);
        } catch (Exception e) {
            System.out.println("Invalid date format. Please enter date in YYYY-MM-DD format.");
            return;
        }
        java.sql.Date sqlDate = new java.sql.Date(salesDate.getTime());
        sales.setSalesDate(sqlDate);

        System.out.print("Enter Product ID: ");
        sales.setProductID(scanner.next());
        System.out.print("Enter Quantity Sold: ");
        sales.setQuantitySold(scanner.nextInt());
        System.out.print("Enter Sales Price Per Unit: ");
        sales.setSalesPricePerUnit(scanner.nextDouble());
        administrator.insertSales(sales);
    }

    private static void viewSalesReport(Administrator administrator) {
        ArrayList<SalesReport> arr = administrator.getSalesReport();
        if (arr.isEmpty()) {
            System.out.println("No sales report available.");
            return;
        }

        System.out.println("Sales Report:");
        System.out.println("-------------------------------------------------------------------------");
        System.out.printf("%-10s %-12s %-10s %-20s %-15s %-20s %-15s %-15s%n",
                "Sales ID", "Sales Date", "Product ID", "Product Name", "Quantity Sold",
                "Product Unit Price", "Sales Price", "Profit Amount");
        System.out.println("-------------------------------------------------------------------------");
        for (SalesReport report : arr) {
            System.out.printf("%-10s %-12s %-10s %-20s %-15d %-20.2f %-15.2f %-15.2f%n",
                    report.getSalesID(), report.getSalesDate(), report.getProductID(),
                    report.getProductName(), report.getQuantitySold(), report.getProductUnitPrice(),
                    report.getSalesPricePerUnit(), report.getProfitAmount());
        }
        System.out.println("-------------------------------------------------------------------------");
    }
}

