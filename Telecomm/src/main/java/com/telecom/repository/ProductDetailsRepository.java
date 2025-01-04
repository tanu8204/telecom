package com.telecom.repository;

import com.telecom.model.ProductDetails;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductDetailsRepository {

    private static final String DB_URL = "jdbc:mysql://sql12.freesqldatabase.com:3306/sql12755644";
    private static final String DB_USER = "sql12755644";
    private static final String DB_PASSWORD = "QCWbG19xPC";

    public List<ProductDetails> findAll() throws SQLException {
        List<ProductDetails> products = new ArrayList<>();
        String query = "SELECT * FROM product_details";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                products.add(mapResultSetToProduct(resultSet));
            }
        }
        return products;
    }

    public ProductDetails findBySerialNumber(String serialNumber) throws SQLException {
        String query = "SELECT * FROM product_details WHERE serialNumber = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, serialNumber);  // Corrected parameter index
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToProduct(resultSet);
                }
            }
        }
        return null;
    }

    public void save(ProductDetails product) throws SQLException {
        String query = """
            INSERT INTO product_details (productName, description, productImage, productCategoryName, modelNumber,
            serialNumber, stockLevel, reorderPoint, supplierName, supplierMail, supplierContact, orderDate, 
            quantity, orderStatus)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {

            setProductDetailsToStatement(product, statement);
            statement.executeUpdate();
        }
    }

    public void update(ProductDetails product) throws SQLException {
        String query = """
            UPDATE product_details 
            SET productName = ?, description = ?, productImage = ?, productCategoryName = ?, modelNumber = ?,
                serialNumber = ?, stockLevel = ?, reorderPoint = ?, supplierName = ?, supplierMail = ?,
                supplierContact = ?, orderDate = ?, quantity = ?, orderStatus = ?
            WHERE serialNumber = ?
        """;

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {

            setProductDetailsToStatement(product, statement);
            statement.setString(15, product.getSerialNumber());  // Corrected parameter index for serialNumber
            statement.executeUpdate();
        }
    }

    public void delete(String serialNumber) throws SQLException {
        String query = "DELETE FROM product_details WHERE serialNumber = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, serialNumber);
            statement.executeUpdate();
        }
    }

    private ProductDetails mapResultSetToProduct(ResultSet rs) throws SQLException {
        ProductDetails product = new ProductDetails();
        product.setSerialNumber(rs.getString("serialNumber"));
        product.setProductName(rs.getString("productName"));
        product.setDescription(rs.getString("description"));
        product.setProductImage(rs.getString("productImage"));
        product.setProductCategoryName(rs.getString("productCategoryName"));
        product.setModelNumber(rs.getString("modelNumber"));
        product.setStockLevel(rs.getInt("stockLevel"));
        product.setReorderPoint(rs.getInt("reorderPoint"));
        product.setSupplierName(rs.getString("supplierName"));
        product.setSupplierMail(rs.getString("supplierMail"));
        product.setSupplierContact(rs.getLong("supplierContact"));
        
        // Fetch orderDate as String (assuming it's stored in the format 'YYYY-MM-DD')
        product.setOrderDate(rs.getString("orderDate"));
        product.setQuantity(rs.getInt("quantity"));
        product.setOrderStatus(rs.getString("orderStatus"));
        
        return product;
    }

    private void setProductDetailsToStatement(ProductDetails product, PreparedStatement statement) throws SQLException {
        statement.setString(1, product.getProductName());
        statement.setString(2, product.getDescription());
        statement.setString(3, product.getProductImage());
        statement.setString(4, product.getProductCategoryName());
        statement.setString(5, product.getModelNumber());
        statement.setString(6, product.getSerialNumber());
        statement.setInt(7, product.getStockLevel());
        statement.setInt(8, product.getReorderPoint());
        statement.setString(9, product.getSupplierName());
        statement.setString(10, product.getSupplierMail());
        statement.setLong(11, product.getSupplierContact());

        // Set orderDate as String (e.g., "YYYY-MM-DD")
        statement.setString(12, product.getOrderDate());  // Assuming orderDate is a String
        statement.setInt(13, product.getQuantity());
        statement.setString(14, product.getOrderStatus());
    }
}
