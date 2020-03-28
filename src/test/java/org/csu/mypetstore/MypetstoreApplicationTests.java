package org.csu.mypetstore;

import org.csu.mypetstore.domain.Category;
import org.csu.mypetstore.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@SpringBootTest
@MapperScan("org.csu.mypetstore.persistence")
class MypetstoreApplicationTests {
    @Autowired
    private CategoryService categoryService;

    @Test
    void contextLoads() {
    }

    @Test
    void testCategory(){
        Category category = categoryService.getCategory("BIRDS");
        System.out.println(category.getCategoryId() +" "+ category.getDescription());

    }

}
