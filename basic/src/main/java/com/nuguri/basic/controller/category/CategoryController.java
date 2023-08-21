package com.nuguri.basic.controller.category;

import com.nuguri.basic.dto.response.ResponseDto;
import com.nuguri.basic.service.category.CategoryService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @ApiOperation(value = "전체 카테고리 목록")
    @GetMapping("/list")
    public ResponseEntity findAllCategoryList(){
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.findAllCategory());
    }
}
