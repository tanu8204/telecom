package com.telecom.service;

import com.telecom.model.ProductDetails;
import com.telecom.repository.ProductDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class ProductDetailsService {

    @Autowired
    private ProductDetailsRepository repository;

    public List<ProductDetails> getAllProducts() throws SQLException {
        return repository.findAll();
    }

    public ProductDetails getProductBySerialNumber(String serialNumber) throws SQLException {
        return repository.findBySerialNumber(serialNumber);
    }

    public void addProduct(ProductDetails product) throws SQLException {
        repository.save(product);
    }

    public void updateProduct(ProductDetails product) throws SQLException {
        repository.update(product);
    }

    public void deleteProduct(String serialNumber) throws SQLException {
        repository.delete(serialNumber);
    }
}
