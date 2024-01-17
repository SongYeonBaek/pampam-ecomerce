package com.example.pampam.category.controller;

import com.example.pampam.category.model.request.PostInsertCategoryReq;
import com.example.pampam.category.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(value = "카테고리 컨트롤러 v1", tags = "카테고리 API")
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;
    @ApiOperation(value = "카테고리별 조회")
    @RequestMapping(method = RequestMethod.GET, value = "/search/region/{region}")
    public ResponseEntity<Object> searchRegion(@PathVariable String region) {
        return ResponseEntity.ok().body(categoryService.searchRegion(region));
    }
    @ApiOperation(value = "카테고리에 상품 추가")
    @RequestMapping(method = RequestMethod.POST, value = "/insert/{idx}")
    public ResponseEntity<Object> insertCategory(@PathVariable Long idx, @RequestBody PostInsertCategoryReq categoryReq) {
        return ResponseEntity.ok().body(categoryService.insertCategory(idx, categoryReq));
    }
}
