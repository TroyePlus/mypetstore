package org.csu.mypetstore.service;

import cn.hutool.core.util.StrUtil;
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

    public List<String> getAllCategoryId(){
        return categoryMapper.getAllCategoryId();
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

    public int updateProduct(Product product, String productId){
        return product==null ? 0 : productMapper.updateProduct(product,productId);
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
    public int insertItem(Item item){
        if(StrUtil.isBlank(item.getItemId()) || StrUtil.isBlank(item.getProductId())
        || item.getQuantity()<=1)
            return 0;

        item.setStockQuantity(item.getQuantity());
        itemMapper.insertInventoryRecord(item.getItemId(),item.getQuantity());
        return itemMapper.insertItem(item);
    }

    @Transactional
    public int updateItem(Item item, String itemId){
        if(itemId==null||itemId.isEmpty() )
            return 0;
        String id = item.getItemId();
        if(!id.equals(itemId))    //如果改变了itemId
            itemMapper.updateInventoryItemId(id, itemId);
        return itemMapper.updateItem(item, itemId);
    }

    //更新商品状态(上架/下架): P--上架, T--下架
    public int updateItemStatus(String itemId, String status){
        if(itemId==null || itemId.isEmpty()){
            return 0;
        }
        if(status==null || status.isEmpty()){
            return 0;
        }

        return status.equals("P") || status.equals("T") ? itemMapper.updateItemStatus(itemId,status) : 0;
    }

    public int deleteItem(String itemId){
        return itemId ==null || itemId.isEmpty() ? 0 : itemMapper.deleteItem(itemId);
    }

    @Transactional
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

    public List<String> getAllProductId(){
        return productMapper.getAllProductId();
    }

    public List<Label> getItemCountGroupByProductId() {
        return productMapper.getItemCountByProductId();
    }

    public int getStockQuantity(String itemId){return itemMapper.getStockQuantity(itemId);}

    public void updateStockQuantity(String itemId,int stockQuantity){itemMapper.updateStockQuantity(itemId,stockQuantity);}

    public String getCategoryByItemId(String itemId){return itemMapper.getCategoryByItemId(itemId);}

    public String getProductIdByItemId(String itemId){
        return itemMapper.getProductIdByItemId(itemId);
    }
}
