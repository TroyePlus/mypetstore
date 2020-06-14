package org.csu.mypetstore.persistence;

import org.csu.mypetstore.domain.Category;
import org.csu.mypetstore.domain.Item;
import org.csu.mypetstore.domain.Label;
import org.csu.mypetstore.domain.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.List;


@SpringBootTest
public class BackProductTest {
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ItemMapper itemMapper;

    @Test
    public void testCat_insert(){
        Category category = new Category();
        category.setCategoryId("TEST_CAT");
        category.setName("test_category");
        category.setDescription("Test to insert the category.");
        Assert.isTrue(categoryMapper.insertCategory(category)==1,"Category插入失败");
    }


    @Test
    public void testCat_update(){
        Category category = categoryMapper.getCategory("TEST_CAT");
        category.setName("test_update");
        category.setDescription("Update the category.");
        categoryMapper.updateCategory(category);
    }

    @Test
    public void testCat_delete(){
        Assert.isTrue(categoryMapper.deleteCategory("TEST_CAT")==1,"Category删除失败");
    }

    @Test
    public void testPro_insert(){
        Product product = new Product();
        product.setProductId("AA-BB-00");
        product.setCategoryId("CATS");
        product.setName("test_product");
        product.setDescription("Test to insert the product.");
        Assert.isTrue(productMapper.insertProduct(product)==1,"Product插入失败");
    }

    @Test
    public void testPro_update(){
        Product product = productMapper.getProduct("AA-BB-00");
        product.setName("update_product");
        product.setDescription("Test to update the product.");
        productMapper.updateProduct(product);
    }

    @Test
    void testPro_delete(){
        Assert.isTrue(productMapper.deleteProduct("AA-BB-00")==1,"Product删除失败");
    }

    @Test
    void testPro_SearchLikeName(){
        List<Product> list = productMapper.getProductListWithName("i");
        System.out.println(list);
    }

    @Test
    void testPro_ItemCountByProId(){
        List<Label> list = productMapper.getItemCountByProductId();
        System.out.println(list);
    }


    @Test
    public void testItem_insert(){
        Item item = new Item();
        item.setItemId("AAA-01");
        item.setStockQuantity(10);
        item.setProductId("AV-CB-01");
        item.setSupplierId(2);
        Assert.isTrue(itemMapper.insertItem(item)==1,"Item插入失败");
        itemMapper.insertInventoryRecord(item.getItemId(),10000);
    }

    @Test
    public void testItem_update(){
        Item item = itemMapper.getItem("AAA-01");
        item.setProductId("FI-FW-01");
        itemMapper.updateItem(item);
    }

    @Test void testItem_delete(){
        Assert.isTrue(itemMapper.deleteItem("AAA-01")==2,"Item删除失败");
    }
}