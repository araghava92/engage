package com.engage.repositories;

import com.engage.models.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductsRepository extends CrudRepository<Product, Integer> {
    List<Product> findProductByCategoryId(int id);
}