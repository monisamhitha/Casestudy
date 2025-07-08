package com.examly.service;

import com.examly.entity.Product;
import com.examly.util.DBConnectionUtil;
import java.sql.*;
import java.util.*;

public class ProductServiceImpl implements ProductService {

    public String addProduct(Product product) {
        if (product.getProductName().isEmpty() || product.getCategory().isEmpty() ||
            product.getStatus().isEmpty()) {
            return "Invalid product details.";
        }

        String sql = "INSERT INTO products(productName, category, quantity, price, status) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnectionUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, product.getProductName());
            ps.setString(2, product.getCategory());
            ps.setInt(3, product.getQuantity());
            ps.setDouble(4, product.getPrice());
            ps.setString(5, product.getStatus());
            ps.executeUpdate();
            return "Product added successfully!";
        } catch (SQLException e) {
            return "Error: " + e.getMessage();
        }
    }

    public String updateProduct(Product product) {
        String sql = "UPDATE products SET productName=?, category=?, quantity=?, price=?, status=? WHERE productId=?";
        try (Connection conn = DBConnectionUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, product.getProductName());
            ps.setString(2, product.getCategory());
            ps.setInt(3, product.getQuantity());
            ps.setDouble(4, product.getPrice());
            ps.setString(5, product.getStatus());
            ps.setInt(6, product.getProductId());
            int updated = ps.executeUpdate();
            return updated > 0 ? "Product updated successfully!" : "Product not found.";
        } catch (SQLException e) {
            return "Error: " + e.getMessage();
        }
    }

    public String deleteProduct(int productId) {
        String sql = "DELETE FROM products WHERE productId=?";
        try (Connection conn = DBConnectionUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, productId);
            int deleted = ps.executeUpdate();
            return deleted > 0 ? "Product deleted successfully!" : "Product not found.";
        } catch (SQLException e) {
            return "Error: " + e.getMessage();
        }
    }

    public Product getProductById(int productId) {
        String sql = "SELECT * FROM products WHERE productId=?";
        try (Connection conn = DBConnectionUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, productId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Product(rs.getInt(1), rs.getString(2), rs.getString(3),
                                   rs.getInt(4), rs.getDouble(5), rs.getString(6));
            }
        } catch (SQLException ignored) {}
        return null;
    }

    public List<Product> getAllProducts() {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM products";
        try (Connection conn = DBConnectionUtil.getConnection(); Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                list.add(new Product(rs.getInt(1), rs.getString(2), rs.getString(3),
                                     rs.getInt(4), rs.getDouble(5), rs.getString(6)));
            }
        } catch (SQLException ignored) {}
        return list;
    }

    public List<Product> searchByName(String name) {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE productName LIKE ?";
        try (Connection conn = DBConnectionUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + name + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Product(rs.getInt(1), rs.getString(2), rs.getString(3),
                                     rs.getInt(4), rs.getDouble(5), rs.getString(6)));
            }
        } catch (SQLException ignored) {}
        return list;
    }

    public List<Product> filterByCategory(String category) {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE category=?";
        try (Connection conn = DBConnectionUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, category);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Product(rs.getInt(1), rs.getString(2), rs.getString(3),
                                     rs.getInt(4), rs.getDouble(5), rs.getString(6)));
            }
        } catch (SQLException ignored) {}
        return list;
    }
}
