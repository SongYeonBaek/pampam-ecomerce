package com.example.pampam.category.controller;

import com.example.pampam.category.model.request.PostRegisterCategory;
import com.example.pampam.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;

    @RequestMapping(method = RequestMethod.GET, value = "/search/type/{categoryType}")
    public ResponseEntity<Object> searchRegion(@PathVariable String categoryType) {
        return ResponseEntity.ok().body(categoryService.searchRegion(categoryType));
    }
    @RequestMapping(method = RequestMethod.POST, value = "/insert/{productIdx}")
    public ResponseEntity<Object> insertCategory(@PathVariable Long productIdx, @RequestBody PostRegisterCategory categoryType) {
        return ResponseEntity.ok().body(categoryService.insertCategory(productIdx, categoryType));
    }
}
