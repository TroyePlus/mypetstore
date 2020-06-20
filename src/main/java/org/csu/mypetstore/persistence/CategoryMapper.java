package org.csu.mypetstore.persistence;

import org.csu.mypetstore.domain.Category;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryMapper {

    List<Category> getCategoryList();

    List<String> getAllCategoryId();

    Category getCategory(String categoryId);

    int insertCategory(Category category);

    int updateCategory(Category category);

    int deleteCategory(String categoryId);
}
