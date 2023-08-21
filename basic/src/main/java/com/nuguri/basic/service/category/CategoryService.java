package com.nuguri.basic.service.category;

import com.nuguri.basic.dto.category.CategoryListDto;
import com.nuguri.basic.repository.category.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<CategoryListDto> findAllCategory(){
        return categoryRepository.findAllCategory();
    }

}
