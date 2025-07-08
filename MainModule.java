package com.examly;

import com.examly.entity.Product;
import com.examly.service.ProductService;
import com.examly.service.ProductServiceImpl;

import java.util.List;
import java.util.Scanner;

public class MainModule {

    private static final Scanner scanner = new Scanner(System.in);
    private static final ProductService productService = new ProductServiceImpl();

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n---- Product Inventory Management ----");
            System.out.println("1. Add Product");
            System.out.println("2. Update Product");
            System.out.println("3. Delete Product");
            System.out.println("4. View All Products");
            System.out.println("5. Search by Product Name");
            System.out.println("6. Filter by Category");
            System.out.println("7. Exit");
            System.out.print("Choose an option: ");

            int option = Integer.parseInt(scanner.nextLine());

            switch (option) {
                case 1 -> addProduct();
                case 2 -> updateProduct();
                case 3 -> deleteProduct();
                case 4 -> viewAllProducts();
                case 5 -> searchByName();
                case 6 -> filterByCategory();
                case 7 -> {
                    System.out.println("Exiting... Thank you!");
                    return;
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void addProduct() {
        System.out.print("Enter Product Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Category: ");
        String category = scanner.nextLine();
        System.out.print("Enter Quantity: ");
        int quantity = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter Price: ");
        double price = Double.parseDouble(scanner.nextLine());
        System.out.print("Enter Status (Available / Out of Stock): ");
        String status = scanner.nextLine();

        Product product = new Product(name, category, quantity, price, status);
        System.out.println(productService.addProduct(product));
    }

    private static void updateProduct() {
        System.out.print("Enter Product ID to Update: ");
        int id = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter Product Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Category: ");
        String category = scanner.nextLine();
        System.out.print("Enter Quantity: ");
        int quantity = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter Price: ");
        double price = Double.parseDouble(scanner.nextLine());
        System.out.print("Enter Status: ");
        String status = scanner.nextLine();

        Product product = new Product(id, name, category, quantity, price, status);
        System.out.println(productService.updateProduct(product));
    }

    private static void deleteProduct() {
        System.out.print("Enter Product ID to Delete: ");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.println(productService.deleteProduct(id));
    }

    private static void viewAllProducts() {
        List<Product> products = productService.getAllProducts();
        if (products.isEmpty()) {
            System.out.println("No products available.");
        } else {
            for (Product p : products) {
                System.out.println(p);
            }
        }
    }

    private static void searchByName() {
        System.out.print("Enter Product Name to Search: ");
        String name = scanner.nextLine();
        List<Product> products = productService.searchByName(name);
        if (products.isEmpty()) {
            System.out.println("No products found.");
        } else {
            for (Product p : products) {
                System.out.println(p);
            }
        }
    }

    private static void filterByCategory() {
        System.out.print("Enter Category to Filter: ");
        String category = scanner.nextLine();
        List<Product> products = productService.filterByCategory(category);
        if (products.isEmpty()) {
            System.out.println("No products found.");
        } else {
            for (Product p : products) {
                System.out.println(p);
            }
        }
    }
}
