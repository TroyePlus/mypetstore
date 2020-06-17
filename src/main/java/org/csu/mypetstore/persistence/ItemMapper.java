package org.csu.mypetstore.persistence;

import org.apache.ibatis.annotations.Param;
import org.csu.mypetstore.domain.Item;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ItemMapper {
    void updateInventoryQuantity(Map<String, Object> param);

    int getInventoryQuantity(String itemId);

    List<Item> getItemListByProduct(String productId);

    List<Item> getItemListWithItemId(String itemId);

    Item getItem(String itemId);

    int getStockQuantity(String itemId);

    void updateStockQuantity(String itemId,int decreaseQuantity);

    String getCategoryByItemId(String itemId);

    int insertItem(Item item);

    int insertInventoryRecord(@Param("itemId")String itemId,@Param("quantity")int quantity);

    int updateItem(Item item);

    int deleteItem(String itemId);
}
