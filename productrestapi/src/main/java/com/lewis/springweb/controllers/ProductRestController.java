package com.lewis.springweb.controllers;

import com.lewis.springweb.entities.Product;
import com.lewis.springweb.repos.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductRestController {

    @Autowired
    ProductRepository repository;

    @GetMapping(value="/products/")
    public List<Product> getProducts(){
        return repository.findAll();
    }

    @GetMapping(value="/products/{id}")
    public Product getProduct(@PathVariable("id") int id){
        return repository.findById(id).get();
    }

    @PostMapping(value="/products/")
    public Product createProduct(@RequestBody Product product){
        return repository.save(product);
    }

    @PutMapping(value="/products/")
    public Product putProduct(@RequestBody Product product){
        return repository.save(product);
    }

    @DeleteMapping(value="/product/{id}")
    public void deleteProduct(@PathVariable("id") int id){
        repository.deleteById(id);
    }
}
