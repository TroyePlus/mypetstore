package org.csu.mypetstore.persistence;

import com.alibaba.fastjson.JSON;
import org.csu.mypetstore.controller.CatalogController;
import org.csu.mypetstore.domain.Category;
import org.csu.mypetstore.domain.Item;
import org.csu.mypetstore.domain.Label;
import org.csu.mypetstore.domain.Product;
import org.csu.mypetstore.service.CatalogService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@SpringBootTest
public class BackProductTest {
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private CatalogService catalogService;

    @Test
    void test_getCatIdTree(){
        System.out.println(JSON.toJSONString(catalogService.getAllCatIdTree()));
    }

    @Test
    void test_getCat(){
//        System.out.println(catalogService.getCategory("FISH"));
        System.out.println(catalogService.getCategoryList());
    }

    @Test
    void test_getItemListWithStatus(){
        System.out.println(catalogService.getItemListByProduct("AV-CB-01"));
    }

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
        categoryMapper.updateCategory(category,"CATS");
    }

    @Test
    public void testCat_delete(){
        Assert.isTrue(categoryMapper.deleteCategory("TEST_CAT")==1,"Category删除失败");
    }

    @Test
    public void testCat_changeDescn() {
        List<Category> categories = categoryMapper.getCategoryList();
        String image;

        for(Category category : categories){
            String [] temp = category.getDescription().split("\"");
            image = temp[1];
            categoryMapper.updateDescn(category.getCategoryId(),image);
        }
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
        productMapper.updateProduct(product,"aaa");
    }

    @Test
    public void testPro_changeDescn(){
       List<Product> products = productMapper.getProductListWithName(null);

        CatalogController.processProductDescription(products);

        for(Product product : products)
            productMapper.updateDescn(product.getProductId(),product.getDescriptionImage(),
                    product.getDescriptionText());
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
    void testPro_getCatId(){
        System.out.println(categoryMapper.getAllCategoryId());
    }

    @Test
    void testItem_getWithIdLike(){
        System.out.println(itemMapper.getItemListWithItemId(null));
    }

    @Test
    public void testItem_insert(){
        Item item = new Item();
        item.setItemId("AAA-01");
        item.setStockQuantity(10);
        item.setProductId("AV-CB-01");
        item.setSupplierId(2);
        itemMapper.insertItem(item);
        itemMapper.insertInventoryRecord(item.getItemId(),500);
    }

    @Test
    public void testItem_productId(){
        System.out.println(itemMapper.getProductIdByItemId("AAA-01"));
    }

    @Test
    public void testItem_update(){
        String initId = "CCC-03";
        Item item = itemMapper.getItem(initId);
        String id = "CCC-04";
        item.setItemId(id);
        item.setProductId("FI-SW-02");
        item.setListPrice(new BigDecimal("66.00"));
        item.setUnitCost(new BigDecimal("44.00"));
        item.setStatus("T");
        item.setAttribute1("B");
        item.setAttribute2("C");
        item.setAttribute3("D");
        itemMapper.updateItem(item,initId);
        itemMapper.updateInventoryItemId(id,initId);
    }

    @Test
    void test_updateStatus()
    {
        System.out.println(itemMapper.updateItemStatus("black","T"));
    }

    @Test void testItem_delete(){
        Assert.isTrue(itemMapper.deleteItem("AAA-01")==2,"Item删除失败");
    }

    @Test
    void testItem_delete_in_batches(){
        List<String> idList = new ArrayList<>();
        idList.add("bb");
        idList.add("bbb");
        idList.add("bbbb");

        System.out.println(itemMapper.deleteItems(idList));
    }
}