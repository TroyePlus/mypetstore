package org.csu.mypetstore.persistence;

import org.apache.ibatis.annotations.Param;
import org.csu.mypetstore.domain.Category;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryMapper {

    List<Category> getCategoryList();

    List<String> getAllCategoryId();

    Category getCategory(String categoryId);

    int insertCategory(Category category);

    int updateCategory(@Param("category")Category category,@Param("id")String id);

    int deleteCategory(String categoryId);

    int updateDescn(@Param("id")String id, @Param("image")String image);
}
