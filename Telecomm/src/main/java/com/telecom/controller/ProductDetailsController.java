package com.telecom.controller;

import com.telecom.model.ProductDetails;
import com.telecom.service.ProductDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductDetailsController {

    @Autowired
    private ProductDetailsService service;
    
    @GetMapping("/public/hello")
    public String publicEndpoint() {
        return "This is a public endpoint!";
    }
    
    @GetMapping
    public List<ProductDetails> getAllProducts() throws SQLException {
        return service.getAllProducts();
    }

    @GetMapping("/{serialNumber}")
    public ProductDetails getProductBySerialNumber(@PathVariable String serialNumber) throws SQLException {
        return service.getProductBySerialNumber(serialNumber);
    }

    @PostMapping
    public String addProduct(@RequestBody ProductDetails product) throws SQLException {
        service.addProduct(product);
        return "Product added successfully!";
    }

    @PutMapping("/{serialNumber}")
    public String updateProduct(@PathVariable String serialNumber, @RequestBody ProductDetails product) throws SQLException {
        product.setSerialNumber(serialNumber);
        service.updateProduct(product);
        return "Product updated successfully!";
    }

    @DeleteMapping("/{serialNumber}")
    public String deleteProduct(@PathVariable String serialNumber) throws SQLException {
        service.deleteProduct(serialNumber);
        return "Product deleted successfully!";
    }
}

//Host: sql12.freesqldatabase.com
//Database name: sql12755644
//Database user: sql12755644
//Database password: QCWbG19xPC
//Port number: 3306
