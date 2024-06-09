package com.example.pampam.category.controller;

import com.example.pampam.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;

    @RequestMapping(method = RequestMethod.GET, value = "/list")
    public ResponseEntity<Object> getCategories() {
        return ResponseEntity.ok().body(categoryService.getCategories());
    }

    @RequestMapping(method = RequestMethod.POST, value = "/register")
    public ResponseEntity<Object> registerCategory() {
        return ResponseEntity.ok().body(categoryService.registerCategory());
    }
}
