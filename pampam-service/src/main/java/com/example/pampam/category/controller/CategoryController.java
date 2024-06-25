package com.example.pampam.category.controller;

import com.example.pampam.category.model.request.PostRegisterCategoryReq;
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
    public ResponseEntity<Object> searchRegion(@RequestHeader(value = "Authorization") String token, @PathVariable String categoryType) {
        return ResponseEntity.ok().body(categoryService.searchCategoryType(token, categoryType));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/list")
    public ResponseEntity<Object> getCategoryList(@RequestHeader(value = "Authorization") String token) {
        return ResponseEntity.ok().body(categoryService.getCategoryList(token));
    }
    @RequestMapping(method = RequestMethod.POST, value = "/insert/{productIdx}")
    public ResponseEntity<Object> insertCategory(@RequestHeader(value = "Authorization") String token, @PathVariable Long productIdx, @RequestBody PostRegisterCategoryReq categoryType) {
        return ResponseEntity.ok().body(categoryService.insertCategory(token, productIdx, categoryType));
    }
}
