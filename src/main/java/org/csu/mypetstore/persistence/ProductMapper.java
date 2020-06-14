package org.csu.mypetstore.persistence;

import org.csu.mypetstore.domain.Label;
import org.csu.mypetstore.domain.Product;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductMapper {
    List<Product> getProductListByCategory(String categoryId);

    Product getProduct(String productId);

    List<Product> searchProductList(String keywords);

    List<Product> getProductListWithName(String name);

    //  获取每个产品(Product)拥有的货品(Item)的数量
    List<Label> getItemCountByProductId();

    // 自动补全
    List<String> getProductName(String name);

    List<String> getAllName();

    int insertProduct(Product product);

    int updateProduct(Product product);

    int deleteProduct(String productId);
}
