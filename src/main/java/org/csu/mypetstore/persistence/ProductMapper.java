package org.csu.mypetstore.persistence;

import org.apache.ibatis.annotations.Param;
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

    List<String> getAllProductId();

    // 自动补全
    List<String> getProductName(String name);

    List<String> getAllName();

    int insertProduct(Product product);

    int updateProduct(@Param("product")Product product,@Param("productId")String productId);

    int updateDescn(@Param("id")String id,@Param("image")String image, @Param("text")String text);

    int deleteProduct(String productId);
}
