package com.nuguri.basic.repository.category;

import com.nuguri.basic.dto.category.CategoryListDto;

import java.util.List;

public interface CategoryRepositoryCustom {
    List<CategoryListDto> findAllCategory();
}
