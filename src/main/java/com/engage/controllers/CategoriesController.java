package com.engage.controllers;

import com.engage.models.Category;
import com.engage.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/categories")
public class CategoriesController {

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/")
    public @ResponseBody
    Iterable<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @PutMapping("/")
    public void addCategory(
            @RequestParam("name") String name,
            @RequestParam(value = "parent", required = false) Integer parentid) {
        Category category = new Category(name);
        if (parentid != null) {
            Optional<Category> parent = categoryRepository.findById(parentid);
            parent.ifPresent(category::setParent);
        }
        categoryRepository.save(category);
    }
    
    @PostMapping("/")
    public void updateCategory(
            @RequestParam Integer id,
            @RequestParam(required = false) String name) {
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        if (categoryOptional.isPresent()) {
            Category category = categoryOptional.get();
            if (name != null) {
                category.setName(name);
            }
            categoryRepository.save(category);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable("id") Integer id) {
        categoryRepository.deleteById(id);
    }
}