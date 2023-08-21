package com.nuguri.basic.repository.category;

import com.nuguri.basic.domain.category.QCategory;
import com.nuguri.basic.dto.category.CategoryListDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;

public class CategoryRepositoryImpl implements CategoryRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    public CategoryRepositoryImpl(EntityManager em){
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<CategoryListDto> findAllCategory() {
        List<CategoryListDto> categoryListDtoList = queryFactory
                .select(Projections.constructor(CategoryListDto.class,
                        QCategory.category.id,
                        QCategory.category.parent.id,
                        QCategory.category.categoryName
                ))
                .from(QCategory.category)
                .fetch();
        return categoryListDtoList;
    }
}
