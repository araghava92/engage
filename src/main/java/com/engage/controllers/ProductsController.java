package com.engage.controllers;

import com.engage.models.Category;
import com.engage.models.Product;
import com.engage.repositories.CategoryRepository;
import com.engage.repositories.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/products")
public class ProductsController {

    @Autowired
    private ProductsRepository productsRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/")
    public @ResponseBody
    Iterable<Product> getAllProductsForCategory(
            @RequestParam("categoryId") Integer id) {
        return productsRepository.findProductByCategoryId(id);
    }

    @PutMapping("/")
    public void addProduct(
            @RequestParam("name") String name,
            @RequestParam("UPC") String UPC,
            @RequestParam("price") BigDecimal price,
            @RequestParam("categoryId") Integer categoryId) {

        Optional<Category> category = categoryRepository.findById(categoryId);
        if (category.isPresent()) {
            Product product = new Product(name, UPC, price, category.get());
            productsRepository.save(product);
        }
    }


    @PostMapping("/")
    public void updateCategory(
            @RequestParam Integer id,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "UPC", required = false) String UPC,
            @RequestParam(value = "price", required = false) BigDecimal price) {
        Optional<Product> productOptional = productsRepository.findById(id);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            if (name != null) {
                product.setName(name);
            }
            if (UPC != null) {
                product.setUPC(UPC);
            }
            if (name != null) {
                product.setPrice(price);
            }
            productsRepository.save(product);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable("id") Integer id) {
        productsRepository.deleteById(id);
    }
}