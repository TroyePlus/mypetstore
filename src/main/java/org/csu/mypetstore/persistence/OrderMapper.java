package org.csu.mypetstore.persistence;

import org.csu.mypetstore.domain.Delivery;
import org.csu.mypetstore.domain.LineItem;
import org.csu.mypetstore.domain.Order;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Repository
public interface OrderMapper {
    List<Order> getOrdersByUsername(String username);

    Order getOrder(int orderId);

    List<Order> getAllOrders();

    void insertOrder(Order order);

    void insertOrderStatus(Order order);

    List<Order> getOrdersByCategory(String catid);

    List<LineItem> getLineItemsByOrder(int orderid);

    void deleteOrderById(int orderId);

    void deleteOrderStatusById(int orderId);

    void deleteLineItemsById(int orderId);

    void updateOrderStatus(int orderId,String status);

    void updateLineItemCount(int lineNumber,int count);

    void updateTotalPrice(int orderId, int totalPrice);

    void insertDelivery(Delivery delivery);

    Delivery getDeliveryByOrderId(int orderId);

}
