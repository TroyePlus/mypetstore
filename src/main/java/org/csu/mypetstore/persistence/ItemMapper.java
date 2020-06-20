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

    int updateInventoryItemId(@Param("id")String id, @Param("initId")String initId);

    List<Item> getItemListByProduct(String productId);

    List<Item> getItemListWithItemId(String itemId);

    Item getItem(String itemId);

    int getStockQuantity(String itemId);

    void updateStockQuantity(String itemId,int stockQuantity);

    String getCategoryByItemId(String itemId);

    String getProductIdByItemId(String itemId);

    int insertItem(Item item);

    int insertInventoryRecord(@Param("itemId")String itemId,@Param("quantity")int quantity);

    int updateItem(@Param("item")Item item, @Param("itemId")String itemId);

    int updateItemStatus(@Param("itemId")String itemId,@Param("status")String status);

    int deleteItem(String itemId);

    int deleteItems(List<String> itemId);
}
