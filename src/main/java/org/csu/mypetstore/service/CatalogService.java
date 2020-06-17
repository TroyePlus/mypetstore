package org.csu.mypetstore.service;

import org.csu.mypetstore.domain.Category;
import org.csu.mypetstore.domain.Item;
import org.csu.mypetstore.domain.Label;
import org.csu.mypetstore.domain.Product;
import org.csu.mypetstore.persistence.CategoryMapper;
import org.csu.mypetstore.persistence.ItemMapper;
import org.csu.mypetstore.persistence.LineItemMapper;
import org.csu.mypetstore.persistence.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.*;
import java.util.List;

@Service
public class CatalogService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private ProductMapper productMapper;


    public List<Category> getCategoryList() {
        return categoryMapper.getCategoryList();
    }

    public List<String> getCategoryId(){
        return categoryMapper.getCategoryId();
    }

    public Category getCategory(String categoryId) {
        return categoryMapper.getCategory(categoryId);
    }

    public void insertCategory(Category category){
        categoryMapper.insertCategory(category);
    }

    public void updateCategory(Category category){
        categoryMapper.updateCategory(category);
    }

    public void deleteCategory(String categoryId){
        categoryMapper.deleteCategory(categoryId);
    }

    public Product getProduct(String productId) {
        return productMapper.getProduct(productId);
    }

    public List<Product> getProductListByCategory(String categoryId) {
        return productMapper.getProductListByCategory(categoryId);
    }

    // TODO enable using more than one keyword
    public List<Product> searchProductList(String keyword) {
        return productMapper.searchProductList("%" + keyword.toLowerCase() + "%");
    }

    public int insertProduct(Product product){
        return product==null ? 0 : productMapper.insertProduct(product);
    }

    public int updateProduct(Product product){
        return product==null ? 0 : productMapper.updateProduct(product);
    }

    public int deleteProduct(String productId){
        return productId==null||productId.isEmpty() ? 0 : productMapper.deleteProduct(productId);
    }

    public List<Item> getItemListByProduct(String productId) {
        return itemMapper.getItemListByProduct(productId);
    }

    public List<Item> getItemListWithId(String itemId){
        String id = itemId==null || itemId.isEmpty() ? null : itemId;
        return itemMapper.getItemListWithItemId(id);
    }

    public Item getItem(String itemId) {
        return itemMapper.getItem(itemId);
    }

    @Transactional
    public void insertItem(Item item, int quantity){
        itemMapper.insertItem(item);
        itemMapper.insertInventoryRecord(item.getItemId(),quantity);
    }

    public int updateItem(Item item){
         return itemMapper.updateItem(item);
    }

    public int updateItemStatus(String itemId, String status){
        if(itemId==null || itemId.isEmpty()){
            return 0;
        }
        if(status==null || status.isEmpty()){
            return 0;
        }

        return status.equals("P") || status.equals("S") ? itemMapper.updateItemStatus(itemId,status) : 0;
    }

    public int deleteItem(String itemId){
        return itemId ==null || itemId.isEmpty() ? 0 : itemMapper.deleteItem(itemId);
    }

    public int deleteItems(List<String> idList){
        return idList == null || idList.isEmpty() ? 0 : itemMapper.deleteItems(idList);
    }

    public boolean isItemInStock(String itemId) {
        return itemMapper.getInventoryQuantity(itemId) > 0;
    }

    //自动补全
    public List<String> getProductName(String name){
        return productMapper.getProductName(name);
    }

    public List<String> getAllName(){
        return productMapper.getAllName();
    }

    public List<Product> getProductWithName(String name){
        String searchName = null==name || name.isEmpty() || name.trim().isEmpty() ? null : name.trim();
        return productMapper.getProductListWithName(searchName);
    }

    public List<Label> getItemCountGroupByProductId() {
        return productMapper.getItemCountByProductId();
    }

    public int getStockQuantity(String itemId){return itemMapper.getStockQuantity(itemId);}

    public void updateStockQuantity(String itemId,int decreaseQuantity){itemMapper.updateStockQuantity(itemId,decreaseQuantity);}

    //
    public String getCategoryByItemId(String itemId){return itemMapper.getCategoryByItemId(itemId);}
}
