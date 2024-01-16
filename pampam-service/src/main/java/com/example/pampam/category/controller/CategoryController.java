package com.example.pampam.category.controller;

import com.example.pampam.category.model.request.PostInsertCategoryReq;
import com.example.pampam.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    @RequestMapping(method = RequestMethod.GET, value = "/search/region/{region}")
    public ResponseEntity<Object> searchRegion(@PathVariable String region) {
        return ResponseEntity.ok().body(categoryService.searchRegion(region));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/insert/{idx}")
    public ResponseEntity<Object> insertCategory(@PathVariable Long idx, @RequestBody PostInsertCategoryReq categoryReq) {
        return ResponseEntity.ok().body(categoryService.insertCategory(idx, categoryReq));
    }
}
